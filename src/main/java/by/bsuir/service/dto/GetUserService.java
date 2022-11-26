package by.bsuir.service.dto;

import by.bsuir.dto.user.UserProfileDto;
import by.bsuir.entity.User;

import java.util.List;

public interface GetUserService {
    UserProfileDto getProfile();

    List<User> getUserFriendsByEmails(User user, List<String> friendsEmail);
}
