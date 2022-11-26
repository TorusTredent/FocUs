package by.bsuir.repository;

import by.bsuir.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findAllByAuthorIdAndAndCreateDateIsAfter(Long userId, LocalDateTime time);

    Optional<List<Task>> findAllByAuthorIdAndAndCreateDateIsBetween(Long userId, LocalDateTime after, LocalDateTime before);
}
