package by.bsuir.service.dto.impl;

import by.bsuir.dto.user.UpdateUserDto;
import by.bsuir.entity.Pack;
import by.bsuir.entity.User;
import by.bsuir.entity.enums.user.USER_STATUS;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.service.business.CheckUserService;
import by.bsuir.service.business.FirebaseService;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditUserService;
import by.bsuir.service.dto.GetPackService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static by.bsuir.exception.enums.ERROR_CODE.PACK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EditUserServiceImpl implements EditUserService {

    private final SecurityService securityService;
    private final UserService userService;
    private final CheckUserService checkUserService;
    private final FirebaseService firebaseService;
    private final GetPackService getPackService;
    private final PackService packService;

    @Autowired
    public EditUserServiceImpl(SecurityService securityService, UserService userService, CheckUserService checkUserService, FirebaseService firebaseService, GetPackService getPackService, PackService packService) {
        this.securityService = securityService;
        this.userService = userService;
        this.checkUserService = checkUserService;
        this.firebaseService = firebaseService;
        this.getPackService = getPackService;
        this.packService = packService;
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

        if (userService.existsByEmail(updateUserDto.getEmail())) {
            throw new BusinessException(String.format("User with email %s is already exist", updateUserDto.getEmail()));
        }

        if (!updateUserDto.getEmail().equals(user.getEmail())) {
            firebaseService.updateEmail(updateUserDto.getEmail(), user.getFirebaseId());
        }

        user.setEmail(updateUserDto.getEmail());

        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean deleteUserProfile() {
        User user = userService.findByFirebaseId(getUid());
        user.setUser_status(USER_STATUS.REMOVED);
        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean follow(String email) {
        User user = userService.findByFirebaseId(getUid());
        User sub = userService.findByEmail(email);

        if (checkUserService.checkUserInList(user.getSubscriptions(), sub)) {
            throw new BusinessException(String.format("User with email %s is already sub", email));
        }

        sub.getFollowers().add(user);
        user.getSubscriptions().add(sub);

        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean unfollow(String email) {
        User user = userService.findByFirebaseId(getUid());
        User sub = userService.findByEmail(email);

        if (!checkUserService.checkUserInList(user.getSubscriptions(), sub)) {
            throw new BusinessException(String.format("User with email %s is already unsub", email));
        }

        sub.getFollowers().remove(user);
        user.getSubscriptions().remove(sub);

        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean installPackByName(String name) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = getPackService.getPackByName(name);

        user.setUsedPack(pack);

        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean uninstallPackByName(String name) {
        User user = userService.findByFirebaseId(getUid());
        if (!packService.existByName(name)) {
            throw new BusinessException(String.format("Pack with name %s", name), PACK_NOT_FOUND, NOT_FOUND);
        }
        Pack defaultPack = getPackService.getDefaultPack();
        user.setUsedPack(defaultPack);

        return true;
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
