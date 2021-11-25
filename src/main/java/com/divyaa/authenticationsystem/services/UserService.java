package com.divyaa.authenticationsystem.services;

import com.divyaa.authenticationsystem.beans.NewUserBean;
import com.divyaa.authenticationsystem.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Divyaa P
 */
public interface UserService extends UserDetailsService {

    Long createUser(NewUserBean userDetails);

    UserDTO getUserByUsername(String username);

    String updateUser(UserDTO userDTO);

    String getNewAccessToken(String accessToken, String refreshToken, HttpServletResponse httpServletResponse);
}
