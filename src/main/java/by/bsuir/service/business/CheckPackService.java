package by.bsuir.service.business;

import by.bsuir.dto.pack.rank.CreateRankDto;

import java.util.List;

public interface CheckPackService {
    boolean isRankStartWithZero(List<CreateRankDto> createRankDtos);
}
