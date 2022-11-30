package by.bsuir.service.dto;

import by.bsuir.dto.pack.rank.CreateRankDto;
import by.bsuir.dto.pack.rank.UpdateRankDto;
import by.bsuir.entity.Rank;

import java.util.List;

public interface EditRankService {

    List<Rank> saveAll(List<Rank> ranks);

    boolean create(CreateRankDto createRankDto, Long packId);

    boolean delete(Long rankId, Long packId);

    boolean update(UpdateRankDto updateRankDto);
}
