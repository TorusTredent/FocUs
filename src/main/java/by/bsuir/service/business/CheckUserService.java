package by.bsuir.service.business;

import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;

import java.util.List;

public interface CheckUserService {

    boolean checkUserFact(SecurityUserFirebase authorizationServiceUser);

    boolean checkUserEnjoyPack(SecurityUserFirebase authorizationServiceUser);

    boolean checkUserInList(List<User> subs, User user);

    boolean checkUserPack(SecurityUserFirebase authorizationServiceUser);
}
