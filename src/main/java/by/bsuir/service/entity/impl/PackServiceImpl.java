package by.bsuir.service.entity.impl;

import by.bsuir.entity.Pack;
import by.bsuir.entity.enums.pack.PACK_TYPE;
import by.bsuir.exception.BusinessException;
import by.bsuir.repository.PackRepository;
import by.bsuir.service.entity.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
