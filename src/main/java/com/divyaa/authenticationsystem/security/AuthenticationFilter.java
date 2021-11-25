package com.divyaa.authenticationsystem.security;

import com.divyaa.authenticationsystem.beans.LoginRequestBean;
import com.divyaa.authenticationsystem.dtos.UserDTO;
import com.divyaa.authenticationsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.divyaa.authenticationsystem.util.StringConstants.REFRESH_TOKEN;
import static com.divyaa.authenticationsystem.util.StringConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Divyaa P
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(UserService userService, Environment environment, JwtUtil jwtUtil,
                                String loginUrl, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        this.jwtUtil = jwtUtil;
        super.setFilterProcessesUrl(loginUrl);
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestBean loginCredentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestBean.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCredentials.getUsername(), loginCredentials.getPassword(), new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDTO userDetails = userService.getUserByUsername(username);
        response.setHeader(AUTHORIZATION, TOKEN_PREFIX + jwtUtil.getAccessToken(userDetails.getId().toString()));
        String refreshToken = jwtUtil.getRefreshToken(userDetails.getId().toString());
        response.setHeader(REFRESH_TOKEN, refreshToken);
        userDetails.setRefreshToken(refreshToken);
        userService.updateUser(userDetails);
    }
}
