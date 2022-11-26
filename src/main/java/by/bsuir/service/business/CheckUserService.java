package by.bsuir.service.business;

import by.bsuir.entity.security.SecurityUserFirebase;

import java.io.IOException;

public interface CheckUserService {

    boolean checkUserFact(SecurityUserFirebase authorizationServiceUser);

    boolean checkUserEnjoyPack(SecurityUserFirebase authorizationServiceUser);
}
