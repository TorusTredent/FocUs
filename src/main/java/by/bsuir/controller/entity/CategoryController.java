package by.bsuir.controller.entity;

import by.bsuir.dto.category.SaveCategoryDto;
import by.bsuir.dto.category.UpdateCategoryDto;
import by.bsuir.service.dto.EditCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final EditCategoryService editCategoryService;

    @Autowired
    public CategoryController(EditCategoryService editCategoryService) {
        this.editCategoryService = editCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> save(@RequestBody SaveCategoryDto saveCategoryDto) {
        editCategoryService.save(saveCategoryDto);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestParam Long categoryId) {
        editCategoryService.deleteById(categoryId);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateCategoryDto updateCategoryDto) {
        editCategoryService.update(updateCategoryDto);
        return new ResponseEntity<>(OK);
    }
}
