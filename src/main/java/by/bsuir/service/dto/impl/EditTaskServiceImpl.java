package by.bsuir.service.dto.impl;

import by.bsuir.dto.task.CreateTaskDto;
import by.bsuir.dto.task.UpdateTaskDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.entity.enums.task.TASK_STATUS;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditTaskService;
import by.bsuir.service.dto.GetCategoryService;
import by.bsuir.service.dto.GetUserService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static by.bsuir.entity.enums.task.TASK_STATUS.ACTIVE;
import static by.bsuir.entity.enums.task.TASK_STATUS.REMOVED;
import static by.bsuir.entity.enums.task.TASK_TYPE.CUSTOM;
import static by.bsuir.exception.enums.ERROR_CODE.TASK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EditTaskServiceImpl implements EditTaskService {

    private final SecurityService securityService;
    private final UserService userService;
    private final TaskService taskService;
    private final GetUserService getUserService;
    private final GetCategoryService getCategoryService;

    public EditTaskServiceImpl(SecurityService securityService, UserService userService,
                               TaskService taskService, GetUserService getUserService,
                               GetCategoryService getCategoryService) {
        this.securityService = securityService;
        this.userService = userService;
        this.taskService = taskService;
        this.getUserService = getUserService;
        this.getCategoryService = getCategoryService;
    }

    @Override
    public boolean create(CreateTaskDto createTaskDto) {
        User user = userService.findByFirebaseId(getUid());

        if (createTaskDto.getDeadline().isBefore(createTaskDto.getCreate())) {
            throw new BusinessException(String.format("Deadline date %s and Creation date %s input incorrect",
                    createTaskDto.getDeadline(), createTaskDto.getCreate()));
        }

        Task task = taskService.save(Task.builder()
                .createDate(createTaskDto.getCreate())
                .name(createTaskDto.getName())
                .author(user)
                .taskPriority(createTaskDto.getTaskPriority())
                .deadLine(createTaskDto.getDeadline())
                .category(getCategoryService.getCategoriesByNames(user, createTaskDto.getCategoryNames()))
                .friends(getUserService.getUserFriendsByEmails(user, createTaskDto.getFriendsEmail()))
                .taskPrivacy(createTaskDto.getTask_privacy())
                .taskType(CUSTOM)
                .taskStatus(ACTIVE)
                .build());

        user.getTasks().add(task);

        userService.save(user);
        return true;
    }

    @Override
    @Modifying
    @Transactional
    public boolean deleteById(Long taskId) {
        User user = userService.findByFirebaseId(getUid());
        Task task = taskService.findById(taskId);
        if (!task.getAuthor().equals(user)) {
            throw new BusinessException(String.format("Task with id %s not found", taskId), TASK_NOT_FOUND, NOT_FOUND);
        }
        task.setTaskStatus(REMOVED);
        taskService.save(task);
        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean updateTask(UpdateTaskDto updateTaskDto) {
        Task task = taskService.findById(updateTaskDto.getTaskId());
        User user = userService.findByFirebaseId(getUid());

        if (!task.getAuthor().equals(user)) {
            throw new BusinessException(String.format("Task with id %s not found", updateTaskDto.getTaskId()), TASK_NOT_FOUND, NOT_FOUND);
        }
        if (task.getCreateDate().isAfter(updateTaskDto.getDeadline())) {
            throw new BusinessException("Date input incorrect");
        }

        List<User> friends = getUserService.getUserFriendsByEmails(user, updateTaskDto.getFriendsEmails());

        task.setTaskPriority(updateTaskDto.getTaskPriority());
        task.setTaskPrivacy(updateTaskDto.getTaskPrivacy());
        task.setFriends(friends);
        task.setName(updateTaskDto.getName());
        task.setDeadLine(updateTaskDto.getDeadline());

        return true;
    }

    @Override
    @Modifying
    @Transactional
    public boolean updateTaskStatus(Long taskId, TASK_STATUS taskStatus) {
        User user = userService.findByFirebaseId(getUid());
        Task task = taskService.findById(taskId);
        if (!task.getAuthor().equals(user)) {
            throw new BusinessException(String.format("Task with id %s not found", taskId), TASK_NOT_FOUND, NOT_FOUND);
        }

        task.setTaskStatus(taskStatus);

        return true;
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
