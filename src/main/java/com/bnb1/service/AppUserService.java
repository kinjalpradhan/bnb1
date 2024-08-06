package com.bnb1.service;

import com.bnb1.entity.AppUser;
import com.bnb1.payload.AppUserDto;
import com.bnb1.payload.LoginDto;

public interface AppUserService {
    AppUserDto createUser(AppUserDto appUserDto);

    boolean verifyLogin(LoginDto loginDto);
}
