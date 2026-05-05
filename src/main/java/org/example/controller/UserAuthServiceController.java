package org.example.controller;

import org.antlr.v4.runtime.misc.Pair;
import org.example.dto.LoginRequestDto;
import org.example.dto.SignUpRequestDto;
import org.example.dto.UserResponseDto;
import org.example.models.User;
import org.example.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthServiceController {

    @Autowired
    IUserAuthService userAuthService;

    // SignUp
    @PostMapping("/singUp")
    public UserResponseDto createUser(@RequestBody SignUpRequestDto signUpRequestDto){
        return from(userAuthService.createUser(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName(), signUpRequestDto.getPhoneNumber()));
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto){
        Pair<User, String> pair =  userAuthService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()); // will return the JWT

        User user = pair.a;
        String token = pair.b;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, token);
        headers.add("Generated-By: ","Naman Patni");

        //ResponseEntity<UserResponseDto> responseDtoResponseEntity = new ResponseEntity<>(from(user), headers, );
        return new ResponseEntity<UserResponseDto>(from(user),headers, HttpStatus.OK);
//        Pair<User,String> response = userAuthService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        User user = response.a;
//        String token = response.b;
//
//        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
//        headers.add(HttpHeaders.SET_COOKIE,token);
//        headers.add("Generated-By","Anurag Khanna");
//
//        return new ResponseEntity<UserResponseDto>(from(user),headers, HttpStatus.OK);
        //return null;

    }

    private UserResponseDto from(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getName());
        return userResponseDto;
    }

}
