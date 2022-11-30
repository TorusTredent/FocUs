package by.bsuir.controller.entity;

import by.bsuir.dto.pack.rank.CreateRankDto;
import by.bsuir.dto.pack.rank.UpdateRankDto;
import by.bsuir.service.dto.EditRankService;
import by.bsuir.service.dto.GetRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rank")
@Slf4j
public class RankController {

    private final GetRankService getRankService;
    private final EditRankService editRankService;

    @Autowired
    public RankController(GetRankService getRankService, EditRankService editRankService) {
        this.getRankService = getRankService;
        this.editRankService = editRankService;
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody CreateRankDto createRankDto,
                                             @RequestParam Long packId) {
        editRankService.create(createRankDto, packId);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestParam Long rankId, @RequestParam Long packId) {
        editRankService.delete(rankId, packId);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateRankDto updateRankDto) {
        editRankService.update(updateRankDto);
        return new ResponseEntity<>(OK);
    }
}
