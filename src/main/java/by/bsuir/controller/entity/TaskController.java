package by.bsuir.controller.entity;

import by.bsuir.dto.task.CreateTaskDto;
import by.bsuir.dto.task.UpdateTaskDto;
import by.bsuir.dto.util.DateDto;
import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.entity.enums.task.TASK_STATUS;
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
    
    @GetMapping("/get/all/week")
    public ResponseEntity<List<TaskDto>> getAll(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getTaskService.getAllInWeek(dateDto.getDate()), OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createTask(@RequestBody CreateTaskDto createTaskDto) {
        editTaskService.create(createTaskDto);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/get/all/month")
    public ResponseEntity<List<TaskDto>> getTasksByMonth(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getTaskService.getAllInMonth(dateDto.getDate()), OK);
    }

    @GetMapping("/get/all/year")
    public ResponseEntity<List<TaskDto>> getTasksByYear(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(getTaskService.getAllInYear(dateDto.getDate()), OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteTask(@RequestParam Long taskId) {
        editTaskService.deleteById(taskId);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateTask(@RequestBody UpdateTaskDto updateTaskDto) {
        editTaskService.updateTask(updateTaskDto);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/update/status")
    public ResponseEntity<HttpStatus> updateTaskStatus(@RequestParam Long taskId,
                                                       @RequestParam TASK_STATUS taskStatus) {
        editTaskService.updateTaskStatus(taskId, taskStatus);
        return new ResponseEntity<>(OK);
    }
}
