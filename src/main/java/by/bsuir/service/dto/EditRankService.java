package by.bsuir.service.dto;

import by.bsuir.entity.Rank;

import java.util.List;

public interface EditRankService {

    List<Rank> saveAll(List<Rank> ranks);
}
