package by.bsuir.controller.entity;

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
}
