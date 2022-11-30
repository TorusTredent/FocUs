package by.bsuir.entity;

import by.bsuir.entity.enums.pack.PACK_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "packs")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(updatable = false)
    private User author;

    private String photoPath;
    private String name;
    private String description;
    private int daysBeforeOverwriting;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Rank> ranks;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<User> followers;

    @Enumerated(value = EnumType.STRING)
    private PACK_TYPE pack_type;
}
