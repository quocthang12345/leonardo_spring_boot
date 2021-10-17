package com.leonardo.api;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.document.UserDocs;
import com.leonardo.service.IUserService;
import com.leonardo.util.JwtTokenProvider;

@RestController
@RequestMapping("/api")
public class UserInfoAPI {
		
    	@Autowired
    	private JwtTokenProvider tokenProvider;
    	
    	@Autowired
    	private IUserService userService;
		
		
	 	@GetMapping("/user")
	    public UserDocs user(HttpServletRequest request) {
	 		
	 		String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            	String id = tokenProvider.getIdFromJWT(jwt);
            	UserDocs user = userService.findById(id);
            	return user;
            	
            }	
	        return null;
	    }
	 	
	 	@GetMapping("/userOauth2")
	    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
	        return Collections.singletonMap("name", principal.getAttribute("name"));
	    }
	 	
	    private String getJwtFromRequest(HttpServletRequest request) {
	        String bearerToken = request.getHeader("Authorization");
	        // Kiểm tra xem header Authorization có chứa thông tin jwt không
	        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7);
	        }
	        return null;
	    }
	    
	    
}
