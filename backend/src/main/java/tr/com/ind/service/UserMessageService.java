package tr.com.ind.service;

import tr.com.ind.dto.UserMessageDto;
import tr.com.ind.dto.UserMessageDtoConverter;
import tr.com.ind.model.UserMessage;
import tr.com.ind.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
public class UserMessageService {
    private final UserMessageRepository userMessageRepository;
    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }
    public Optional<UserMessage> getUserMessageById(Long id) {
        return userMessageRepository.findById(id);
    }
    // User Messaj oluşturma
    public UserMessageDto insertUserMessage(UserMessageDto userMessageDto) {
          UserMessage userMessage = UserMessageDtoConverter.convertToEntity(userMessageDto);
          UserMessage savedMessage = userMessageRepository.save(userMessage);
          return UserMessageDtoConverter.convertToDto(savedMessage);
    }
    public UserMessageDto updateUserMessage(Long id, UserMessageDto userMessageDto) {
        UserMessage userMessage = userMessageRepository.findById(id).orElseThrow(() -> new ResolutionException("UserMessage not found with id " + id));
        userMessage.setText(userMessageDto.getText());
        userMessage.setReceiver_id(userMessageDto.getReceiver_id());
        userMessage.setSender_id(userMessageDto.getSender_id());
        userMessage.setMessage_id(userMessageDto.getMessage_id());
        UserMessage updatedMessage = userMessageRepository.save(userMessage);
        return UserMessageDtoConverter.convertToDto(updatedMessage);
    }
    public void deleteUserMessage(Long id) {
        if (userMessageRepository.existsById(id)) {
            userMessageRepository.deleteById(id);
        } else {
            throw new ResolutionException("UserMessage not found with id " + id);
        }
    }
}
