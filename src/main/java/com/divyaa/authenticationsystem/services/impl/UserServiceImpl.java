package com.divyaa.authenticationsystem.services.impl;

import com.divyaa.authenticationsystem.beans.NewUserBean;
import com.divyaa.authenticationsystem.datastore.models.UserModel;
import com.divyaa.authenticationsystem.datastore.repositories.UserRepository;
import com.divyaa.authenticationsystem.dtos.UserDTO;
import com.divyaa.authenticationsystem.security.JwtUtil;
import com.divyaa.authenticationsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

import static com.divyaa.authenticationsystem.util.StringConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Divyaa P
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Long createUser(NewUserBean userDetails) {
        // map the user details to model
        UserModel newUser = new UserModel(userDetails.getUsername(),
                userDetails.getEmailId(), bCryptPasswordEncoder.encode(userDetails.getPassword()));
        // save it in the db
        newUser = userRepository.save(newUser);
        return newUser.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByNameAndActiveTrue(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return new User(user.get().getName(), user.get().getPassword(), true, true,
                true, true, new ArrayList<>());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<UserModel> user = userRepository.findByNameAndActiveTrue(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return new ModelMapper().map(user.get(), UserDTO.class);
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        userRepository.updateRefreshToken(userDTO.getName(), userDTO.getRefreshToken());
        return "success";
    }

    @Override
    public String getNewAccessToken(String accessToken, String refreshToken, HttpServletResponse httpServletResponse) {
        if(jwtUtil.hasExpired(accessToken)){
            Optional<UserModel> user = userRepository
                    .findByIdAndRefreshTokenAndActiveTrue(Long.parseLong(jwtUtil.getUserId(refreshToken)), refreshToken);
            if(user.isPresent()){
                httpServletResponse.setHeader(AUTHORIZATION, TOKEN_PREFIX+jwtUtil.getAccessToken(user.get().getId().toString()));
                return "Success";
            }
            return "User doesn't exist";
        }
        return "Access token has not expired";
    }
}
