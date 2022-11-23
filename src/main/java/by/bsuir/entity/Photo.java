package by.bsuir.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String photoTitle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    private String extension;

    private String downloadLink;
}


