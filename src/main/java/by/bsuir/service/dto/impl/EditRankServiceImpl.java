package by.bsuir.service.dto.impl;

import by.bsuir.dto.pack.rank.CreateRankDto;
import by.bsuir.dto.pack.rank.UpdateRankDto;
import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditRankService;
import by.bsuir.service.dto.GetPackService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.RankService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditRankServiceImpl implements EditRankService {

    private final RankService rankService;
    private final GetPackService getPackService;
    private final PackService packService;
    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public EditRankServiceImpl(RankService rankService, GetPackService getPackService, PackService packService, UserService userService, SecurityService securityService) {
        this.rankService = rankService;
        this.getPackService = getPackService;
        this.packService = packService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    public List<Rank> saveAll(List<Rank> ranks) {
        return rankService.saveAll(ranks);
    }

    @Override
    public boolean create(CreateRankDto createRankDto, Long packId) {
        Pack pack = getPackService.getPackById(packId);

        List<Rank> ranks = pack.getRanks();

        if (!checkForExistName(createRankDto.getName(), ranks) || !checkForRightCountsTask(createRankDto.getMinCompletedTasks(), createRankDto.getMaxCompletedTasks(), ranks)) {
            throw new BusinessException("Bad rank parameters");
        }

        Rank rank = rankService.save(Rank.builder()
                .name(createRankDto.getName())
                .minCompletedTasks(createRankDto.getMinCompletedTasks())
                .maxCompletedTasks(createRankDto.getMaxCompletedTasks())
                .build());
        pack.getRanks().add(rank);
        packService.save(pack);

        return true;
    }

    @Override
    public boolean delete(Long rankId, Long packId) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = packService.findPackByAuthorAndId(user, packId);
        Rank rank = rankService.findById(rankId);

        if (!pack.getRanks().contains(rank)) {
            throw new BusinessException("Rank parameters input incorrect");
        }

        List<Rank> ranks = pack.getRanks().stream()
                .sorted(Comparator.comparing(Rank::getMaxCompletedTasks))
                .toList();
        if (!ranks.get(ranks.size() - 1).equals(rank)) {
            throw new BusinessException("Rank is not the last");
        }

        pack.getRanks().remove(rank);
        packService.save(pack);
        rankService.delete(rank);
        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean update(UpdateRankDto updateRankDto) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = getPackService.getPackByAuthorAndId(user, updateRankDto.getPackId());
        Rank rank = rankService.findById(updateRankDto.getId());

        if (!pack.getRanks().contains(rank)) {
            throw new BusinessException("Rank parameters input incorrect");
        }

        rank.setName(updateRankDto.getName());

        return true;
    }

    private boolean checkForExistName(String name, List<Rank> ranks) {
        return ranks.stream()
                .noneMatch(rank -> rank.getName().equals(name));
    }

    private boolean checkForRightCountsTask(Long minTask, Long maxTask, List<Rank> ranks) {
        for (int i = 0; i < ranks.size(); i++) {
            if (i == ranks.size() - 1) {
                if (ranks.get(i).getMaxCompletedTasks() + 1 == minTask) {
                    return true;
                }
            }
            if (ranks.get(i).getMaxCompletedTasks() + 1 == minTask && ranks.get(i + 1).getMinCompletedTasks() - 1 == maxTask) {
                return true;
            }
        }
        return false;
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
