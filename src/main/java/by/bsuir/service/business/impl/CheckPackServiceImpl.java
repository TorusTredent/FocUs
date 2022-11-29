package by.bsuir.service.business.impl;

import by.bsuir.dto.pack.CreateRankDto;
import by.bsuir.service.business.CheckPackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPackServiceImpl implements CheckPackService {


    @Override
    public boolean isRankStartWithZero(List<CreateRankDto> createRankDtos) {
        return createRankDtos.stream()
                .anyMatch(rank -> rank.getMinCompletedTasks() == 0 && rank.getMaxCompletedTasks() > 0);
    }
}
