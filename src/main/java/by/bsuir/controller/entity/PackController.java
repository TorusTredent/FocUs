package by.bsuir.controller.entity;

import by.bsuir.dto.pack.CreatePackDto;
import by.bsuir.dto.pack.UpdatePackDto;
import by.bsuir.service.dto.EditPackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/pack")
@Slf4j
public class PackController {

    private final EditPackService editPackService;

    @Autowired
    public PackController(EditPackService editPackService) {
        this.editPackService = editPackService;
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
    public ResponseEntity<HttpStatus> deeletePack(@RequestParam Long packId) {
        editPackService.deletePack(packId);
        return new ResponseEntity<>(OK);
    }
}
