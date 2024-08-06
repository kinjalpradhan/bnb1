package com.bnb1.service;

import com.bnb1.entity.AppUser;
import com.bnb1.exception.UserExists;
import com.bnb1.payload.AppUserDto;
import com.bnb1.payload.LoginDto;
import com.bnb1.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{


    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    @Override
    public AppUserDto createUser(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);

        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if (opEmail.isPresent()){
            throw new UserExists("Email Id Exists");
        }

        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
        if (opUsername.isPresent()){
            throw new UserExists("Username Exists");
        }

        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(10));
        appUser.setPassword(hashpw);

        AppUser savedData = appUserRepository.save(appUser);
        AppUserDto dto = mapToDto(savedData);
        return dto;

    }

    @Override
    public boolean verifyLogin(LoginDto loginDto) {
       // Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        Optional<AppUser> opUser = appUserRepository.findByEmailOrUsername(loginDto.getUsername(), loginDto.getUsername());

        if (opUser.isPresent()){
            AppUser appUser = opUser.get();
            return BCrypt.checkpw( loginDto.getPassword() , appUser.getPassword());

        }
        return false;
    }


    AppUser mapToEntity(AppUserDto dto){
        AppUser entity = new AppUser();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        return entity;
    }

    AppUserDto mapToDto(AppUser appUser){
        AppUserDto dto = new AppUserDto();
        dto.setId(appUser.getId());
        dto.setName(appUser.getName());
        dto.setEmail(appUser.getEmail());
        dto.setPassword(appUser.getPassword());
        dto.setUsername(appUser.getUsername());
        return dto;
    }
}
