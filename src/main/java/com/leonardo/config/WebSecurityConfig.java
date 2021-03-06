package com.leonardo.config;

import java.util.Arrays;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.leonardo.filter.JwtAuthenticationFilter;
import com.leonardo.security.CustomUserDetailService;
import com.leonardo.security.oauth2.CustomOAuth2UserService;
import com.leonardo.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.leonardo.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.leonardo.security.oauth2.OAuth2AuthenticationSuccessHandler;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
/* v?? ???ng d???ng ???????c thi???t k??? theo ki???u FE call API t??? BE n??n ko c?? l??u tr??? th??ng tin d??? li???u t???i session BE 
 * => d???ng stateless 
 * => s??? d???ng cookies + encoding ????? thao t??c v???i request t??? provider t???i source c??ng nh?? URL chuy???n h?????ng
 * */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService userDetail;
	
	@Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	
	
	 @Bean
	 public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter(); //filter request ????? check token ????ng nh???p
	 }
	 
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }
	
	 @Bean
	 public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
	    return new HttpCookieOAuth2AuthorizationRequestRepository(); // c???u h??nh cookies cho ????ng nh???p Oauth2
	 }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.cors()
    	 .and()
    	 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // C???u h??nh stateless cho session
         .and()
         .csrf().disable()
             .authorizeRequests()
             .antMatchers("/",
                     "/error",
                     "/favicon.ico",
                     "/**/*.png",
                     "/**/*.gif",
                     "/**/*.svg",
                     "/**/*.jpg",
                     "/**/*.html",
                     "/**/*.css",
                     "/**/*.js").permitAll() // cho ph??p truy c???p v??o t??i nguy??n
             .antMatchers("/oauth2/**").permitAll()
             .antMatchers("/api/login").permitAll()
             .antMatchers("/ws/**").permitAll()
             .antMatchers("/app/**").permitAll()
             .antMatchers("/topic/**").permitAll()
             .antMatchers("/api/verify**").permitAll()
             .antMatchers("/api/sendVerifyEmail**").permitAll()
             .antMatchers("/api/register").permitAll()// Cho ph??p t???t c??? m???i ng?????i truy c???p v??o ?????a ch??? n??y
             .antMatchers("/api/getCollectionFashion/**").permitAll()
             .antMatchers("/api/getMenu").permitAll()
             .antMatchers("/api/getListJourney").permitAll()
             .anyRequest().authenticated() // T???t c??? c??c request kh??c ?????u c???n ph???i x??c th???c m???i ???????c truy c???p
             .and()
             .oauth2Login()
             .authorizationEndpoint()
                 .baseUri("/oauth2/authorize") // set base URI cho y??u c???u x??c th??c Oauth2
                 .authorizationRequestRepository(cookieAuthorizationRequestRepository()) // set Cookies l??u tr??? cho y??u c???u x??c th???c
                 .and()
             .redirectionEndpoint()
                 .baseUri("/oauth2/callback/*")// set base URI cho y??u c???u cho request chuy???n h?????ng
                 .and()
             .userInfoEndpoint()
                 .userService(customOAuth2UserService) // get info c???a user th??ng qua service custom
                 .and()
             .successHandler(oAuth2AuthenticationSuccessHandler) // C??c thao t??c khi x??c th???c th??nh c??ng
             .failureHandler(oAuth2AuthenticationFailureHandler) // C??c thao t??c n???u x??c th???c th???t b???i
    	 	 .and().exceptionHandling(e -> e
    	                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
    	             ).oauth2Login(); 
    	 	
         http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);// th??m filter tr?????c m???i request truy c???p v??o t??i nguy??n c???a server
//    	http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // encoding password 
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetail) // Cung c??p userservice cho spring security
            .passwordEncoder(passwordEncoder()); // cung c???p password encoder
       
    }
    
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type","X-Frame-Options"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTION"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	

}