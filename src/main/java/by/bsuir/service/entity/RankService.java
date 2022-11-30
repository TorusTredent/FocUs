package by.bsuir.service.entity;

import by.bsuir.entity.Rank;

import java.util.List;

public interface RankService {

    List<Rank> saveAll(List<Rank> ranks);

    Rank save(Rank rank);

    Rank findById(Long rankId);

    void delete(Rank rank);
}
