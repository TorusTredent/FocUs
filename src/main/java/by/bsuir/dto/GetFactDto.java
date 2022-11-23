package by.bsuir.dto;

import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFactDto {

    private String fact;
}
