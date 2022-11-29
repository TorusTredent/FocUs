package by.bsuir.service.dto;

import by.bsuir.dto.user.UpdateUserDto;

import java.time.LocalDateTime;

public interface EditUserService {

    boolean setUserDate(LocalDateTime date);

    boolean updateProfile(UpdateUserDto updateUserDto);

    boolean deleteUserProfile();

    boolean follow(String email);

    boolean unfollow(String email);
}
