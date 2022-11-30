package by.bsuir.repository;

import by.bsuir.entity.Task;
import liquibase.pro.packaged.T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findAllByAuthorIdAndAndCreateDateIsAfter(Long userId, LocalDateTime time);

    @Query(value = "from tasks t where t.createDate >= :startDate AND t.createDate <= :endDate AND t.author.id = :userId")
    Optional<List<Task>> getAllBetweenDates(@Param("userId")Long userId,
                                            @Param("startDate")LocalDateTime startDate,
                                            @Param("endDate")LocalDateTime endDate);
}
