package tr.com.ind.dto;
import tr.com.ind.model.Message;

public class MessageDtoConverter {
    public static MessageDto convertToDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setText(message.getText());
        dto.setActive(message.isActive());
        dto.setCreateTime(message.getCreateTime());
        return dto;
    }

    public static Message convertToEntity(MessageDto dto) {
        Message message = new Message();
        message.setId(dto.getId());
        message.setText(dto.getText());
        message.setActive(true);
        message.setCreateTime(dto.getCreateTime());
        return message;
    }
}
