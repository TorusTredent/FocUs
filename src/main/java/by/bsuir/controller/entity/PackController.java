package by.bsuir.controller.entity;

import by.bsuir.dto.pack.CreatePackDto;
import by.bsuir.dto.pack.GetPackDto;
import by.bsuir.dto.pack.UpdatePackDto;
import by.bsuir.service.dto.EditPackService;
import by.bsuir.service.dto.GetPackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/pack")
@Slf4j
public class PackController {

    private final EditPackService editPackService;
    private final GetPackService getPackService;

    @Autowired
    public PackController(EditPackService editPackService, GetPackService getPackService) {
        this.editPackService = editPackService;
        this.getPackService = getPackService;
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createPack(@RequestBody CreatePackDto createPackDto) {
        editPackService.createPack(createPackDto);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updatePack(@RequestBody UpdatePackDto updatePackDto) {
        editPackService.update(updatePackDto);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deletePack(@RequestParam Long packId) {
        editPackService.deletePack(packId);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<GetPackDto>> getAll() {
        return new ResponseEntity<>(getPackService.getAll(), OK);
    }

    @GetMapping("/get")
    public ResponseEntity<GetPackDto> getById(@RequestParam Long id) {
        return new ResponseEntity<>(getPackService.getPackDtoById(id), OK);
    }
}
