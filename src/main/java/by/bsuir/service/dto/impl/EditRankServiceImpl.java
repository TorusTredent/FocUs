package by.bsuir.service.dto.impl;

import by.bsuir.entity.Rank;
import by.bsuir.service.dto.EditRankService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditRankServiceImpl implements EditRankService {

    private final RankService rankService;

    @Autowired
    public EditRankServiceImpl(RankService rankService) {
        this.rankService = rankService;
    }

    @Override
    public List<Rank> saveAll(List<Rank> ranks) {
        return rankService.saveAll(ranks);
    }
}
