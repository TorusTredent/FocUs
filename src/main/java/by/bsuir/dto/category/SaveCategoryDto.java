package by.bsuir.dto.category;

import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCategoryDto {

    private String name;
    private String textColor;
    private String textBackgroundColor;
}
