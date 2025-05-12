package tr.com.ind.service;

import org.springframework.stereotype.Service;
import tr.com.ind.dto.GroupMessageDto;
import tr.com.ind.dto.GroupMessageDtoConverter;
import tr.com.ind.model.GroupMessage;
import tr.com.ind.repository.GroupMessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GroupMessageService {
    private final GroupMessageRepository groupMessageRepository;
    public GroupMessageService(GroupMessageRepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }
    // Tüm mesajları listele.
    public List<GroupMessage> getAllGroupMessages() {
        return groupMessageRepository.findAll();
    }
    // Grup Mesajın ID'sini return eder.
    public Optional<GroupMessage> getGroupMessageById(Long id) {
        return groupMessageRepository.findById(id);
    }
    // Grup Mesaj oluşturma
    public GroupMessageDto insertGroupMessage(GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = GroupMessageDtoConverter.convertToEntity(groupMessageDto);
        GroupMessage savedMessage = groupMessageRepository.save(groupMessage);
        return GroupMessageDtoConverter.convertToDto(savedMessage);
    }
    public GroupMessageDto updateGroupMessage(Long id, GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = groupMessageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        groupMessage.setId(groupMessageDto.getId());
        groupMessage.setText(groupMessageDto.getText());
        groupMessage.setGroup_id(groupMessageDto.getGroup_id());
        groupMessage.setMessage_id(groupMessageDto.getMessage_id());
        GroupMessage updatedMessage = groupMessageRepository.save(groupMessage);
        return GroupMessageDtoConverter.convertToDto(updatedMessage);
    }
    public void deleteGroupMessage(Long id) {
        groupMessageRepository.deleteById(id);
    }
}
