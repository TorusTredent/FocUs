package by.bsuir.service.entity;

import by.bsuir.entity.Pack;
import by.bsuir.entity.Rank;

import java.util.List;

public interface PackService {

    Pack findPackWithDefaultType();

    boolean existByName(String name);

    Pack save(Pack pack);

    Pack findPackById(Long packId);

    void delete(Pack pack);
}
