package com.leonardo.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.leonardo.document.UserDetailImpl;
import com.leonardo.exception.BadRequestException;
import com.leonardo.security.UserPrincipal;
import com.leonardo.util.CookiesUtil;
import com.leonardo.util.JwtTokenProvider;

import static com.leonardo.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	    private JwtTokenProvider tokenProvider;

//	    private AppProperties appProperties;

	    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


//	    @Autowired
//	    OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider, AppProperties appProperties,
//	                                       HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
//	        this.tokenProvider = tokenProvider;
//	        this.appProperties = appProperties;
//	        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
//	    }
	    
	    @Autowired
	    OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider,
	                                       HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
	        this.tokenProvider = tokenProvider;
	        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	    }
	    
	    
	    /* 
	     * h??m onAuthenticationSuccessc ???????c g???i khi x??c th???c th??nh c??ng
	     * clear attribute x??c th???c v?? cookies ???????c l??u 
	     * */
	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	        String targetUrl = determineTargetUrl(request, response, authentication);

	        if (response.isCommitted()) {
	            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
	            return;
	        }

	        clearAuthenticationAttributes(request, response);
	        getRedirectStrategy().sendRedirect(request, response, targetUrl);
	    }

	    /* 
	     * h??m determineTargetUrl ???????c g???i ????? sinh url ????ch ????? chuy???n h?????ng ???ng d???ng
	     * t???o token login d???a tr??n principal sau khi x??c th???c v?? ???????c ????nh k??m v??o l??m param cho uri chuy???n h?????ng
	     * */
	    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	        Optional<String> redirectUri = CookiesUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
	                .map(Cookie::getValue);

//	        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//	            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//	        }
	        
//	        if(redirectUri.isPresent()) {
//	            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//	        }

	        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

	        String token = tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());

	        return UriComponentsBuilder.fromUriString(targetUrl)
	                .queryParam("token", token)
	                .build().toUriString();
	    }

	    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
	        super.clearAuthenticationAttributes(request);
	        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	    }

//	    private boolean isAuthorizedRedirectUri(String uri) {
//	        URI clientRedirectUri = URI.create(uri);
//
//	        return appProperties.getOauth2().getAuthorizedRedirectUris()
//	                .stream()
//	                .anyMatch(authorizedRedirectUri -> {
//	                    // Only validate host and port. Let the clients use different paths if they want to
//	                    URI authorizedURI = URI.create(authorizedRedirectUri);
//	                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//	                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//	                        return true;
//	                    }
//	                    return false;
//	                });
//	    }
}
