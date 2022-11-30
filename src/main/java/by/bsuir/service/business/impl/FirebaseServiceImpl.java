package by.bsuir.service.business.impl;

import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public boolean updateEmail(String email, String firebaseId) {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(firebaseId)
                .setEmail("user@example.com");
        try {
            FirebaseAuth.getInstance().updateUser(request);
            return true;
        } catch (FirebaseAuthException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
