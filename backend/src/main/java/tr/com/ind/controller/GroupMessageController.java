package tr.com.ind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.ind.dto.GroupMessageDto;
import tr.com.ind.model.GroupMessage;
import tr.com.ind.service.GroupMessageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/groupmessage")
public class GroupMessageController {

    private final GroupMessageService groupMessageService;

    public GroupMessageController(GroupMessageService groupMessageService) {
        this.groupMessageService = groupMessageService;
    }

    // Tüm mesajları listele
    @GetMapping
    public List<GroupMessage> getAllGroupMessages() {
        return groupMessageService.getAllGroupMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMessage> getGroupMessageById(@PathVariable Long id) {
        Optional<GroupMessage> groupMessage = groupMessageService.getGroupMessageById(id);
        if (groupMessage.isPresent()) {
            return ResponseEntity.ok(groupMessage.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertMessage/{id}")
    public ResponseEntity<GroupMessageDto> insertUserMessage(@RequestBody GroupMessageDto groupMessageDto) {
        GroupMessageDto createdGroupMessage = groupMessageService.insertGroupMessage(groupMessageDto);
        return ResponseEntity.ok(createdGroupMessage);
    }

    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<GroupMessageDto> updateUserMessage(@PathVariable Long id, @RequestBody GroupMessageDto groupMessageDto) {
        GroupMessageDto updatedGroupMessage = groupMessageService.updateGroupMessage(id, groupMessageDto);
        return ResponseEntity.ok(updatedGroupMessage);
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<GroupMessageDto> deleteUserMessage(@PathVariable Long id) {
        groupMessageService.deleteGroupMessage(id);
        return ResponseEntity.noContent().build();
    }
}
