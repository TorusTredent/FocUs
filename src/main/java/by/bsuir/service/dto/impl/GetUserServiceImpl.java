package by.bsuir.service.dto.impl;

import by.bsuir.dto.user.FriendDto;
import by.bsuir.dto.user.FriendWithTasksDto;
import by.bsuir.dto.user.GetUserDto;
import by.bsuir.dto.user.UserProfileDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetRankService;
import by.bsuir.service.dto.GetTaskService;
import by.bsuir.service.dto.GetUserService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static by.bsuir.entity.enums.user.USER_STATUS.ACTIVE;
import static by.bsuir.exception.enums.ERROR_CODE.TASK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetUserServiceImpl implements GetUserService {

    private final SecurityService securityService;
    private final UserService userService;
    private final TaskService taskService;
    private final GetTaskService getTaskService;
    private final GetRankService getRankService;

    @Autowired
    public GetUserServiceImpl(SecurityService securityService, UserService userService, TaskService taskService, GetTaskService getTaskService, GetRankService getRankService) {
        this.securityService = securityService;
        this.userService = userService;
        this.taskService = taskService;
        this.getTaskService = getTaskService;
        this.getRankService = getRankService;
    }

    @Override
    public UserProfileDto getProfile() {
        User user = userService.findByFirebaseId(getUid());

        List<Task> tasksByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), user.getEnjoyPackTime(), user.getEndPackTime());

        return UserProfileDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .rank(getRankService.getRank())
                .numberOfCompletedTask(getTaskService.getNumberOfCompletedTasks(tasksByDate))
                .build();
    }

    @Override
    public List<User> getUserFriendsByEmails(User user, List<String> friendsEmail) {
        return friendsEmail.stream()
                .map(email -> user.getFollowers().stream()
                        .filter(friend -> friend.getEmail().equals(email))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(String.format("User with email %s not found", email), NOT_FOUND)))
                .toList();
    }

    @Override
    public List<FriendWithTasksDto> getFriendsWithTasks(LocalDateTime time) {
        User user = userService.findByFirebaseId(getUid());

        List<User> followers = user.getFollowers();
        List<User> blockedList = user.getBlackList();

        return mapToFriendDto(user, followers, blockedList, time);
    }

    @Override
    public List<GetUserDto> getAll() {
        User user = userService.findByFirebaseId(getUid());
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            throw new BusinessException("Users not found", TASK_NOT_FOUND);
        }

        return mapToGetUserDto(user, users);
    }

    private List<GetUserDto> mapToGetUserDto(User auth, List<User> users) {
        return users.stream()
                .filter(user -> user.getUser_status() == ACTIVE)
                .filter(user -> !user.equals(auth))
                .map(user -> GetUserDto.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .user_status(user.getUser_status())
                        .user_role(user.getUser_role())
                        .friend(auth.getFollowers().stream()
                                .anyMatch(follower -> follower.equals(user)))
                        .build())
                .toList();
    }


    private List<FriendWithTasksDto> mapToFriendDto(User auth, List<User> followers, List<User> blackList, LocalDateTime time) {
        return followers.stream()
                .filter(follower -> follower.getUser_status() == ACTIVE)
                .filter(follower -> blackList.stream()
                        .noneMatch(blocked -> blocked.equals(follower)))
                .sorted(Comparator.comparing(User::getUsername))
                .map(follower -> FriendWithTasksDto.builder()
                                .friendDto(FriendDto.builder()
                                .email(follower.getEmail())
                                .username(follower.getUsername())
                                .build())
                                .friendTaskDtos(getTaskService.mapToFriendsTaskDto(follower, auth, time))
                                .build())
                .toList();
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
