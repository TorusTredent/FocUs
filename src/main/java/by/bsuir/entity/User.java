package by.bsuir.entity;

import by.bsuir.entity.enums.user.USER_ROLE;
import by.bsuir.entity.enums.user.USER_STATUS;
import by.bsuir.entity.enums.user.USER_TASK_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firebaseId;
    private String username;
    private String email;
    private String factOfDay;
    private LocalDateTime creationOfFactOfDay;
    private LocalDateTime enjoyPackTime;
    private LocalDateTime endPackTime;
    private LocalDateTime startOfWeek;

    @ManyToOne
    @JoinColumn(name = "photo_path_id")
    private Photo photoPath;

    @OneToOne
    private Pack usedPack;

    @OneToMany
    private List<Pack> customPack;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<User> followers;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<User> subscriptions;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Task> tasks;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Category> category;

    @ElementCollection
    @CollectionTable(name = "tasks_user_task_status_mapping")
    @MapKeyJoinColumn(name = "task_id")
    @Enumerated(value = EnumType.STRING)
    private Map<Task, USER_TASK_STATUS> userTaskStatusMap;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<ClientApp> clientApps;

    @Enumerated(value = EnumType.STRING)
    private USER_ROLE user_role;

    @Enumerated(value = EnumType.STRING)
    private USER_STATUS user_status;
}
