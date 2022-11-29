package by.bsuir.repository;

import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;
import by.bsuir.entity.enums.pack.PACK_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    @Query(value = "select p from packs p where p.pack_type = ?1")
    Optional<Pack> findByPack_type(@Param("pack_type") PACK_TYPE pack_type);

    boolean existsByName(String name);
}
