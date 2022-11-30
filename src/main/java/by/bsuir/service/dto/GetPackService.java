package by.bsuir.service.dto;

import by.bsuir.dto.pack.GetPackDto;
import by.bsuir.entity.Pack;
import by.bsuir.entity.User;

import java.util.List;

public interface GetPackService {

    Pack getPackById(Long packId);

    Pack getPackByName(String name);

    Pack getDefaultPack();

    List<GetPackDto> getAll();

    GetPackDto getPackDtoById(Long id);

    Pack getPackByAuthorAndId(User user, Long packId);
}
