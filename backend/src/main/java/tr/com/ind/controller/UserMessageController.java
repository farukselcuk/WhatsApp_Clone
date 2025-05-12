package tr.com.ind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.ind.dto.UserMessageDto;
import tr.com.ind.model.UserMessage;
import tr.com.ind.service.UserMessageService;

import java.util.Optional;
import java.util.List;

// Gelen ve gönderdiği mesajları listeleyebilme.

@RestController
@RequestMapping("/api/v1/usermessage")
public class UserMessageController {

    private final UserMessageService userMessageService;

    @Autowired
    public UserMessageController(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMessage> getUserMessageById(@PathVariable Long id) {
        Optional<UserMessage> userMessage = userMessageService.getUserMessageById(id);
        if (userMessage.isPresent()) {
            return ResponseEntity.ok(userMessage.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertMessage/{id}")
    public ResponseEntity<UserMessageDto> insertUserMessage(@RequestBody UserMessageDto userMessageDto) {
        UserMessageDto createdUserMessage = userMessageService.insertUserMessage(userMessageDto);
        return ResponseEntity.ok(createdUserMessage);
    }

    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<UserMessageDto> updateUserMessage(@PathVariable Long id, @RequestBody UserMessageDto userMessageDto) {
        UserMessageDto updatedUserMessage = userMessageService.updateUserMessage(id, userMessageDto);
        return ResponseEntity.ok(updatedUserMessage);
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<UserMessageDto> deleteUserMessage(@PathVariable Long id) {
        userMessageService.deleteUserMessage(id);
        return ResponseEntity.noContent().build();
    }

}
