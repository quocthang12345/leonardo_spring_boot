package com.leonardo.security.oauth2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leonardo.document.RoleDocs;
import com.leonardo.document.UserDocs;
import com.leonardo.document.resource.AuthProvider;
import com.leonardo.document.resource.ERole;
import com.leonardo.exception.OAuth2AuthenticationProcessingException;
import com.leonardo.repository.UserRepository;
import com.leonardo.security.UserPrincipal;
import com.leonardo.security.oauth2.social.OAuth2UserInfo;
import com.leonardo.security.oauth2.social.OAuth2UserInfoFactory;
import com.leonardo.service.impl.RoleService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	/* 
     * hàm loadUser sử dụng load và truy cập vào user để thêm hoặc update user trong db sau khi xác thực thành công 
     * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest); 

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<UserDocs> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        UserDocs user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
           // kiểm tra xem cùng một tài khoản nhưng khác provider thì log ra lỗi để người sủ dụng đúng provider để login
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) { 
                throw new OAuth2AuthenticationProcessingException("Looks like you are signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes()); // tạo principal trong hệ thống
    }

    private UserDocs registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    	UserDocs user = new UserDocs();
    	Set<RoleDocs> roles = new HashSet<>();
		RoleDocs userRole = roleService.findByRoleName(ERole.ROLE_USER.name());
		roles.add(userRole);
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setRoles(roles);       
        return userRepository.save(user);
    }

    private UserDocs updateExistingUser(UserDocs existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
