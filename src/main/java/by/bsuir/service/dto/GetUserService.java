package by.bsuir.service.dto;

import by.bsuir.dto.user.FriendWithTasksDto;
import by.bsuir.dto.user.GetUserDto;
import by.bsuir.dto.user.UserProfileDto;
import by.bsuir.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface GetUserService {
    UserProfileDto getProfile();

    List<User> getUserFriendsByEmails(User user, List<String> friendsEmail);

    List<FriendWithTasksDto> getFriendsWithTasks(LocalDateTime time);

    List<GetUserDto> getAll();
}
