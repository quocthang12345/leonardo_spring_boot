package com.leonardo.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.convert.UserConvert;
import com.leonardo.document.RoleDocs;
import com.leonardo.document.UserDetailImpl;
import com.leonardo.document.UserDocs;
import com.leonardo.document.dto.UserDTO;
import com.leonardo.document.resource.ERole;
import com.leonardo.document.resource.LoginRequest;
import com.leonardo.repository.UserRepository;
import com.leonardo.service.IRoleService;
import com.leonardo.service.IUserService;
import com.leonardo.util.JwtTokenProvider;

@RestController
@RequestMapping("/api")
public class LoginAndRegisterAPI {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepo;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserConvert userConvert;
    
    @Autowired
    private IRoleService roleService;
	
	@PostMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			}
			,
			path = {"/login"}
	)
    public String authenticateUser(@RequestBody LoginRequest loginRequest) {

		UserDocs user = userRepo.findOneByUsernameAndStatus(loginRequest.getUsername(), 1);
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        		);
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((UserDetailImpl) authentication.getPrincipal());
        return jwt;
    }
	
	@PostMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/register"}
	)
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDto) {
		UserDocs user = userConvert.toDocs(userDto);
		if(user != null) {
			Set<RoleDocs> roles = new HashSet<>();
			RoleDocs userRole = roleService.findByRoleName(ERole.ROLE_USER.name());
			roles.add(userRole);
			user.setRoles(roles);
			user.setStatus(1);
			boolean isRegister = userService.RegisterUser(user);
			if(isRegister) return new ResponseEntity<String>("Register Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Register Failed", HttpStatus.BAD_REQUEST);
        
    }
}
