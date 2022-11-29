package by.bsuir.service.dto.impl;

import by.bsuir.dto.user.UpdateUserDto;
import by.bsuir.entity.User;
import by.bsuir.entity.enums.user.USER_STATUS;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditUserService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class EditUserDtoServiceImpl implements EditUserService {

    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public EditUserDtoServiceImpl(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @Transactional
    @Modifying
    public boolean setUserDate(LocalDateTime date) {
        User user = userService.findByFirebaseId(getUid());

        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        LocalDateTime startOfCurrentWeek = date.with((TemporalAdjuster) TemporalAdjusters.previousOrSame(firstDayOfWeek));

        user.setStartOfWeek(startOfCurrentWeek);
        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean updateProfile(UpdateUserDto updateUserDto) {
        User user = userService.findByFirebaseId(getUid());

        user.setUsername(updateUserDto.getUsername());
        user.setEmail(updateUserDto.getEmail());

        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean deleteUserProfile() {
        User user= userService.findByFirebaseId(getUid());
        user.setUser_status(USER_STATUS.REMOVED);
        return true;
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
