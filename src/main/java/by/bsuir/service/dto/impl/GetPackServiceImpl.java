package by.bsuir.service.dto.impl;

import by.bsuir.dto.pack.GetPackDto;
import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetPackService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetPackServiceImpl implements GetPackService {

    private final PackService packService;
    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public GetPackServiceImpl(PackService packService, SecurityService securityService, UserService userService) {
        this.packService = packService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    public Pack getPackById(Long packId) {
        return packService.findPackById(packId);
    }

    @Override
    public Pack getPackByName(String name) {
        return packService.findPackByName(name);
    }

    @Override
    public Pack getDefaultPack() {
        return packService.findPackWithDefaultType();
    }

    @Override
    public List<GetPackDto> getAll() {
        User user = userService.findByFirebaseId(getUid());
        List<Pack> packs = packService.getAll();

        if (packs == null || packs.isEmpty()) {
            throw new BusinessException("Packs not found", NOT_FOUND);
        }

        return mapToGetPackDtos(packs, user.getUsedPack().getName());
    }

    @Override
    public GetPackDto getPackDtoById(Long id) {
        User user = userService.findByFirebaseId(getUid());
        Pack pack = getPackById(id);

        return mapToGetPackDto(pack, user.getUsedPack().getName());
    }

    @Override
    public Pack getPackByAuthorAndId(User user, Long packId) {
        return packService.findPackByAuthorAndId(user, packId);
    }

    private List<GetPackDto> mapToGetPackDtos(List<Pack> packs, String usedPack) {
        return packs.stream()
                .map(pack -> mapToGetPackDto(pack, usedPack))
                .toList();
    }

    private GetPackDto mapToGetPackDto(Pack pack, String usedPack) {
        return GetPackDto.builder()
                .id(pack.getId())
                .photoPath(pack.getPhotoPath())
                .daysBeforeOverwriting(pack.getDaysBeforeOverwriting())
                .name(pack.getName())
                .pack_type(pack.getPack_type())
                .description(pack.getDescription())
                .ranks(pack.getRanks())
                .countOfFollowers(pack.getFollowers().size())
                .authorUsername(pack.getAuthor().getUsername())
                .authorEmail(pack.getAuthor().getEmail())
                .isUsed(pack.getName().equals(usedPack))
                .build();
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
