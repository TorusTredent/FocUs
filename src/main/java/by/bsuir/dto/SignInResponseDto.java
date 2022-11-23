package by.bsuir.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInResponseDto {
    private String idToken;
    private String email;
    private String refreshToken;
    private String expiresIn;
    private String localId;
    private boolean registered;
}