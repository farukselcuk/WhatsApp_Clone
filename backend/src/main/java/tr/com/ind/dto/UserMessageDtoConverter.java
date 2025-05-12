package tr.com.ind.dto;

import tr.com.ind.model.UserMessage;

public class UserMessageDtoConverter {
    public static UserMessageDto convertToDto(UserMessage userMessage){
        return new UserMessageDto(
                userMessage.getId(),
                userMessage.getText(),
                userMessage.getPhoto(),
                userMessage.getReceiver_id(),
                userMessage.getSender_id(),
                userMessage.getMessage_id()
        );
    }

    public static UserMessage convertToEntity(UserMessageDto dto) {
        UserMessage userMessage = new UserMessage();
        userMessage.setId(dto.getId());
        userMessage.setText(dto.getText());
        userMessage.setPhoto(dto.getPhoto());
        userMessage.setReceiver_id(dto.getReceiver_id());
        userMessage.setSender_id(dto.getSender_id());
        userMessage.setMessage_id(dto.getMessage_id());
        return userMessage;
    }

}
