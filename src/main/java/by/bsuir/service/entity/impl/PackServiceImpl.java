package by.bsuir.service.entity.impl;

import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;
import by.bsuir.entity.enums.pack.PACK_TYPE;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.repository.PackRepository;
import by.bsuir.service.entity.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.bsuir.exception.enums.ERROR_CODE.PACK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PackServiceImpl implements PackService {

    private final PackRepository packRepository;

    @Autowired
    public PackServiceImpl(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Override
    public Pack findPackWithDefaultType() {
        return packRepository.findByPack_type(PACK_TYPE.DEFAULT)
                .orElseThrow(() -> new BusinessException("No default pack found", NOT_FOUND));
    }

    @Override
    public boolean existByName(String name) {
        return packRepository.existsByName(name);
    }

    @Override
    public Pack save(Pack pack) {
        return packRepository.save(pack);
    }

    @Override
    public Pack findPackById(Long packId) {
        return packRepository.findById(packId)
                .orElseThrow(() -> new BusinessException(String.format("Pack with id %s not found", packId), PACK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public void delete(Pack pack) {
        packRepository.delete(pack);
    }
}
