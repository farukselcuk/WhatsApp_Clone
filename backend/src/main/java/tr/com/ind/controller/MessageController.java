package tr.com.ind.controller;

import tr.com.ind.dto.MessageDto;
import tr.com.ind.model.Message;
import tr.com.ind.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;

    }
    // Tüm mesajları listele
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    // Belirli bir mesajı kimliğine (id) göre almak için kullanılan bir HTTP GET isteği işlemini tanımlar.
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Insert Message Create yapıyoruz.
    @PostMapping("/insertMessage")
    public ResponseEntity<MessageDto> insertMessage(@RequestBody MessageDto messageDto) {
        MessageDto createdMessage = messageService.insertMessage(messageDto);
        return ResponseEntity.ok(createdMessage);
    }

    // Update Message
    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto) {
        MessageDto updateMessage = messageService.updateMessage(id,messageDto);
        return ResponseEntity.ok(updateMessage);
    }
    // DELETE Message
    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<MessageDto> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
