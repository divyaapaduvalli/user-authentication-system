package com.divyaa.authenticationsystem.cotnrollers;

import com.divyaa.authenticationsystem.beans.NewUserBean;
import com.divyaa.authenticationsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Divyaa P
 */
@RestController
@RequestMapping("/v1/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewUserBean newUserBean){
        return new ResponseEntity<>(userService.createUser(newUserBean), HttpStatus.OK);
    }

    @GetMapping("/users/refresh-access-token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody String expiredAccessToken,
                                               @RequestHeader("refresh-token") String refreshToken,
                                               HttpServletResponse httpServletResponse){
        return new ResponseEntity<>(userService.getNewAccessToken(expiredAccessToken, refreshToken, httpServletResponse), HttpStatus.OK);
    }
}
