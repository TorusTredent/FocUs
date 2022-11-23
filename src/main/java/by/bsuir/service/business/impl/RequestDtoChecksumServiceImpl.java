package by.bsuir.service.business.impl;

import by.bsuir.service.business.RequestDtoChecksumService;
import org.apache.commons.codec.digest.XXHash32;
import org.springframework.stereotype.Service;

import java.util.zip.Checksum;

@Service
public class RequestDtoChecksumServiceImpl implements RequestDtoChecksumService {
    @Override
    public boolean validate(String token, String email, String checksum) {
        return getChecksum(token,email).equals(checksum);
    }

    @Override
    public String getChecksum(String token, String email) {
        Checksum checksum = new XXHash32();
        byte[] bytes = (token + email).getBytes();
        checksum.update(bytes,0,bytes.length);
        return Long.toString(checksum.getValue());
    }
}
