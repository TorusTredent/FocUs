package by.bsuir.entity;

import by.bsuir.entity.enums.task.TASK_PRIORITY;
import by.bsuir.entity.enums.task.TASK_PRIVACY;
import by.bsuir.entity.enums.task.TASK_STATUS;
import by.bsuir.entity.enums.task.TASK_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Category> category;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @OneToOne
    @JoinColumn(updatable = false, nullable = false)
    private User author;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<User> friends;

    @Enumerated(value = EnumType.STRING)
    private TASK_PRIORITY taskPriority;

    @Enumerated(value = EnumType.STRING)
    private TASK_STATUS taskStatus;

    @Enumerated(value = EnumType.STRING)
    private TASK_PRIVACY taskPrivacy;

    @Enumerated(value = EnumType.STRING)
    private TASK_TYPE taskType;
}
