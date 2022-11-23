package by.bsuir.entity;

import by.bsuir.entity.enums.category.CATEGORY_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String textColor;
    private String textBackgroundColor;

    @Enumerated(value = EnumType.STRING)
    private CATEGORY_TYPE category_type;
}
