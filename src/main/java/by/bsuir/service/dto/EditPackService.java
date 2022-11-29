package by.bsuir.service.dto;

import by.bsuir.dto.pack.CreatePackDto;
import by.bsuir.dto.pack.UpdatePackDto;

public interface EditPackService {
    boolean createPack(CreatePackDto createPackDto);

    boolean update(UpdatePackDto updatePackDto);

    boolean deletePack(Long packId);
}
