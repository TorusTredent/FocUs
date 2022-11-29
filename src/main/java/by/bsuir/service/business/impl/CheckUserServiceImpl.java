package by.bsuir.service.business.impl;

import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.CheckUserService;
import by.bsuir.service.business.FactService;
import by.bsuir.service.business.TranslatorService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static by.bsuir.entity.enums.LANGUAGES.ENGLISH;
import static by.bsuir.entity.enums.LANGUAGES.RUSSIAN;
import static java.time.Month.JANUARY;

@Service
public class CheckUserServiceImpl implements CheckUserService {

    private final UserService userService;
    private final FactService factService;
    private final TranslatorService translatorService;


    @Autowired
    public CheckUserServiceImpl(UserService userService, FactService factService, TranslatorService translatorService) {
        this.userService = userService;
        this.factService = factService;
        this.translatorService = translatorService;
    }

    @Override
    public boolean checkUserFact(SecurityUserFirebase authorizationServiceUser) {
        User user = userService.findByFirebaseId(authorizationServiceUser.getUid());
        if (user.getCreationOfFactOfDay() == null) {
            changeFact(user);
            return true;
        }
        if (user.getCreationOfFactOfDay().getMonth().equals(LocalDateTime.now().getMonth())) {
            if (user.getCreationOfFactOfDay().getDayOfMonth() < LocalDateTime.now().getDayOfMonth()) {
                changeFact(user);
                return true;
            }
        } else {
            if (user.getCreationOfFactOfDay().getMonth().getValue() < LocalDateTime.now().getMonth().getValue() ||
                    (user.getCreationOfFactOfDay().getMonth().getValue() == 12 && LocalDateTime.now().getMonth() == JANUARY)) {
                changeFact(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserEnjoyPack(SecurityUserFirebase authorizationServiceUser) {
        User user = userService.findByFirebaseId(authorizationServiceUser.getUid());
        if (user.getEnjoyPackTime() == null) {
            overwritingEnjoyPackTime(user);
            return true;
        }
        if (user.getEndPackTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
                user.getEndPackTime().getYear() == LocalDateTime.now().getYear()) {
            overwritingEnjoyPackTime(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserInList(List<User> list, User user) {
        return list.stream().anyMatch(user::equals);
    }


    private void overwritingEnjoyPackTime(User user) {
        user.setEnjoyPackTime(LocalDateTime.now());
        user.setEndPackTime(LocalDateTime.now().plusDays(user.getUsedPack().getDaysBeforeOverwriting()));
        userService.save(user);
    }

    private void changeFact(User user) {
        String fact = factService.getFact();
        String translateFact = translatorService.translateText(ENGLISH.getMessage(), RUSSIAN.getMessage(), fact);
        user.setFactOfDay(translateFact);
        user.setCreationOfFactOfDay(LocalDateTime.now());
        userService.save(user);
    }
}
