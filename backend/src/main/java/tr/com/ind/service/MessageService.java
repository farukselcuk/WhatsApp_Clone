package tr.com.ind.service;

import tr.com.ind.dto.MessageDto;
import tr.com.ind.dto.MessageDtoConverter;
import tr.com.ind.model.Message;
import tr.com.ind.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    // Tüm mesajları listele.
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Mesajın ID'sini return eder.
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    // Mesaj oluşturma
    public MessageDto insertMessage(MessageDto messageDto) {
        Message message = MessageDtoConverter.convertToEntity(messageDto);
        Message savedMessage = messageRepository.save(message);
        return MessageDtoConverter.convertToDto(savedMessage);
    }
    public MessageDto updateMessage(Long id, MessageDto messageDto) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        message.setText(messageDto.getText());
        message.setActive(true);
        message.setCreateTime(LocalDateTime.now());
        Message updatedMessage = messageRepository.save(message);
        return MessageDtoConverter.convertToDto(updatedMessage);
    }
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
