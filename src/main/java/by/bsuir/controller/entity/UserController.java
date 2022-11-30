package by.bsuir.controller.entity;

import by.bsuir.dto.user.FriendWithTasksDto;
import by.bsuir.dto.user.GetUserDto;
import by.bsuir.dto.user.UpdateUserDto;
import by.bsuir.dto.util.DateDto;
import by.bsuir.dto.user.UserProfileDto;
import by.bsuir.service.dto.EditUserService;
import by.bsuir.service.dto.GetUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final EditUserService editUserService;
    private final GetUserService getUserService;

    @Autowired
    public UserController(EditUserService editUserService, GetUserService getUserService) {
        this.editUserService = editUserService;
        this.getUserService = getUserService;
    }

    @PostMapping("/set-start-date")
    public ResponseEntity<HttpStatus> setDate(@RequestBody DateDto dateDto) {
        editUserService.setUserDate(dateDto.getDate());
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile() {
        return new ResponseEntity<>(getUserService.getProfile(), OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<HttpStatus> updateProfile(@RequestBody UpdateUserDto updateUserDto){
        editUserService.updateProfile(updateUserDto);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteProfile() {
        editUserService.deleteUserProfile();
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/friends/day")
    public ResponseEntity<List<FriendWithTasksDto>> getFriendsTasks(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getUserService.getFriendsWithTasks(dateDto.getDate()), OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<GetUserDto>> getAll() {
        return new ResponseEntity<>(getUserService.getAll(), OK);
    }

    @PostMapping("/follow")
    public ResponseEntity<HttpStatus> follow(@RequestParam String email) {
        editUserService.follow(email);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<HttpStatus> unfollow(@RequestParam String email) {
        editUserService.unfollow(email);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/pack/install")
    public ResponseEntity<HttpStatus> installPack(@RequestParam String name) {
        editUserService.installPackByName(name);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/pack/uninstall")
    public ResponseEntity<HttpStatus> uninstallPack(@RequestParam String name) {
        editUserService.uninstallPackByName(name);
        return new ResponseEntity<>(OK);
    }
}
