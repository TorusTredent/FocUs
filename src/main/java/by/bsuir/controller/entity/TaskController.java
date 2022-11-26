package by.bsuir.controller.entity;

import by.bsuir.dto.task.CreateTaskDto;
import by.bsuir.dto.util.DateDto;
import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.service.dto.EditTaskService;
import by.bsuir.service.dto.GetTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    private final GetTaskService getTaskService;
    private final EditTaskService editTaskService;

    @Autowired
    public TaskController(GetTaskService getTaskService, EditTaskService editTaskService) {
        this.getTaskService = getTaskService;
        this.editTaskService = editTaskService;
    }

    @GetMapping("/get/stat")
    public ResponseEntity<TaskStatDto> getTaskStat(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getTaskService.getTaskStatByWeek(dateDto.getDate()), OK);
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<List<TaskDto>> getAll(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getTaskService.getAll(dateDto.getDate()), OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createTask(@RequestBody CreateTaskDto createTaskDto) {
        editTaskService.create(createTaskDto);
        return new ResponseEntity<>(OK);
    }
}
