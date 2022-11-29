package by.bsuir.service.dto.impl;

import by.bsuir.dto.pack.CreatePackDto;
import by.bsuir.dto.pack.UpdatePackDto;
import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;
import by.bsuir.entity.User;
import by.bsuir.entity.enums.pack.PACK_TYPE;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.CheckPackService;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditPackService;
import by.bsuir.service.dto.EditRankService;
import by.bsuir.service.dto.GetPackService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static by.bsuir.exception.enums.ERROR_CODE.PACK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EditPackServiceImpl implements EditPackService {

    private final SecurityService securityService;
    private final UserService userService;
    private final PackService packService;
    private final GetPackService getPackService;
    private final CheckPackService checkPackService;
    private final EditRankService editRankService;

    @Autowired
    public EditPackServiceImpl(SecurityService securityService, UserService userService, PackService packService, GetPackService getPackService, CheckPackService checkPackService, EditRankService editRankService) {
        this.securityService = securityService;
        this.userService = userService;
        this.packService = packService;
        this.getPackService = getPackService;
        this.checkPackService = checkPackService;
        this.editRankService = editRankService;
    }

    @Override
    public boolean createPack(CreatePackDto createPackDto) {
        if (packService.existByName(createPackDto.getName())) {
            throw new BusinessException("Pack name is already exist");
        }
        if (checkPackService.isRankStartWithZero(createPackDto.getCreateRankDtos())) {
            throw new BusinessException("Bad min or max completed tasks");
        }
        User user = userService.findByFirebaseId(getUid());
        List<Rank> ranks = editRankService.saveAll(createPackDto.getCreateRankDtos().stream()
                .map(rank -> Rank.builder()
                        .name(rank.getName())
                        .minCompletedTasks(rank.getMinCompletedTasks())
                        .maxCompletedTasks(rank.getMaxCompletedTasks())
                        .build())
                .toList());
        packService.save(Pack.builder()
                .author(user)
                .ranks(ranks)
                .pack_type(PACK_TYPE.CUSTOM)
                .description(createPackDto.getDescription())
                .daysBeforeOverwriting(createPackDto.getDaysBeforeOverwriting())
                .name(createPackDto.getName())
                .build());
        return true;
    }

    @Override
    public boolean update(UpdatePackDto updatePackDto) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = getPackService.getPackById(updatePackDto.getPackId());

        if (!userService.existsByUserIdAndPack(user.getId(), pack)) {
            throw new BusinessException(String.format("User with id %s don't have pack with id %s", user.getId(), updatePackDto.getPackId()),
                    PACK_NOT_FOUND, NOT_FOUND);
        }

        pack.setName(updatePackDto.getName());
        pack.setDescription(updatePackDto.getDescription());
        pack.setDaysBeforeOverwriting(updatePackDto.getDaysBeforeOverwriting());
        packService.save(pack);
        return true;
    }

    @Override
    public boolean deletePack(Long packId) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = getPackService.getPackById(packId);

        if (!pack.getAuthor().equals(user)) {
            throw new BusinessException(String.format("User with id %s don't have pack with id %s", user.getId(), packId),
                    PACK_NOT_FOUND, NOT_FOUND);
        }

        pack.setFollowers(new ArrayList<>());
        pack.setRanks(new ArrayList<>());

        packService.save(pack);
        packService.delete(pack);
        return true;
    }
    private String getUid() {
        return securityService.getUser().getUid();
    }
}
