package by.bsuir.controller.entity;

import by.bsuir.service.dto.GetFactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/fact")
@Slf4j
public class FactController {

    private final GetFactService getFactService;

    @Autowired
    public FactController(GetFactService getFactService) {
        this.getFactService = getFactService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> getFactDay() {
        return new ResponseEntity<>(getFactService.getFact(), OK);
    }
}
