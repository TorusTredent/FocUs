package by.bsuir.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FriendWithTasksDto {

    private FriendDto friendDto;
    private List<FriendTaskDto> friendTaskDtos;
}
