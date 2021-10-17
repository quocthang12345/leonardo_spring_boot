package com.leonardo.security.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import com.leonardo.util.CookiesUtil;

public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"; 
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int cookieExpireSeconds = 180;
    
    /* 
     * hàm loadAuthorizationRequest sử dụng load request Oauth2 từ phía provider để trích xuất các attribute được gửi 
     * sử dụng CookiesUtil được create tại package util để deserializa cũng như encoding request với Base64
     * */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookiesUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookiesUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null); 
    }

    /* 
     * hàm saveAuthorizationRequest sử dụng lưu request Oauth2 từ phía provider để trích xuất các attribute được gửi 
     * (đây là hàm đầu tiên được gọi khi request provider gửi tới server)
     * lưu URI chuyển hướng được encoding
     * */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
        	CookiesUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        	CookiesUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookiesUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookiesUtil.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
        	CookiesUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    /* 
     * hàm removeAuthorizationRequest sử dụng load request Oauth2 và remove khỏi cookies 
     * (đây là hàm được gọi khi có yêu cầu load attribute cho server)
     * */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }
    /* 
     * hàm removeAuthorizationRequest remove tất cả khỏi cookies 
     * */
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
    	CookiesUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    	CookiesUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
