package by.bsuir.service.dto.impl;

import by.bsuir.entity.Pack;
import by.bsuir.service.dto.GetPackService;
import by.bsuir.service.entity.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPackServiceImpl implements GetPackService {

    private final PackService packService;

    @Autowired
    public GetPackServiceImpl(PackService packService) {
        this.packService = packService;
    }

    @Override
    public Pack getPackById(Long packId) {
        return packService.findPackById(packId);
    }
}
