package com.leonardo.api;

import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
public class LoginAndRegisterAPI {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserService userService;
	

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserConvert userConvert;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private JavaMailSender mailSender;
	
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
			user.setVerifyCode(RandomString.make(64));
			user.setStatus(1);
			boolean isRegister = userService.RegisterUser(user);
			if(isRegister) {
				boolean isSendEmailVerify = sendMailToVerify(user);
				if(isSendEmailVerify) return new ResponseEntity<String>("Register Success", HttpStatus.OK); 
				else return new ResponseEntity<String>("Register Failed", HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<String>("Register Failed", HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<String>("Register Failed", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/verify"}
	)
    public boolean checkVerifyCode(@RequestParam(value = "code") String code) {
		  UserDocs user = userService.findOneByVerifyCode(code);
		  if(user == null || user.getEmailVerified()) {
			  return false;
		  }
		  user.setVerifyCode(code);
		  user.setEmailVerified(true);
		  userService.updateUser(user);
		  return true;
    }
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/sendVerifyEmail"}
	)
    public boolean sendEmailVerifyAgain(@RequestParam(value = "token") String token) {
		if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
        	String id = tokenProvider.getIdFromJWT(token);
        	UserDocs user = userService.findById(id);
        	sendMailToVerify(user);
        	return true;
        }
	  return false;
    }
	
	public boolean sendMailToVerify(UserDocs user) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String toAddress = user.getEmail();
	    String fromAddress = "quocthang1100@gmail.com";
	    String senderName = "Leonardo Shop";
	    String subject = "Verify Your Registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Leonardo Shop.";
	    content = content.replace("[[name]]", user.getFullname());
	    String verifyURL = "http://localhost:3000" + "/verify?execution=VERIFY_EMAIL&code=" + user.getVerifyCode();     
	    content = content.replace("[[URL]]", verifyURL);	    
	    try {
	    	helper.setFrom(fromAddress, senderName);
		    helper.setTo(toAddress);
		    helper.setSubject(subject);  
		    helper.setText(content, true);
	    }catch(Exception e) {
	    	return false;
	    }
		mailSender.send(message);
		return true;
	}
}
