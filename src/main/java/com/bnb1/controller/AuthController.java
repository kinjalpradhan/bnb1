package com.bnb1.controller;

import com.bnb1.payload.AppUserDto;
import com.bnb1.entity.AppUser;
import com.bnb1.payload.LoginDto;
import com.bnb1.service.AppUserService;
import com.bnb1.service.AppUserServiceImpl;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class  AuthController {


    private AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    //http://localhost:8080/api/v1/auth
    @PostMapping
    public ResponseEntity<AppUserDto> createUser(
            @RequestBody AppUserDto appUserDto
            ){

        AppUserDto userDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(
            @RequestBody LoginDto loginDto
    ){
        boolean  status = appUserService.verifyLogin(loginDto);
        if(status) {
            return new ResponseEntity<>("Logged In Successfully", HttpStatus.OK);

        }else {
        return new ResponseEntity<>("Invalid Username/Password", HttpStatus.UNAUTHORIZED);
    }


    }
}
