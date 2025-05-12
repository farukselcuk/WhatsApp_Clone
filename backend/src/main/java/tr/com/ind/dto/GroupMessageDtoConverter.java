package tr.com.ind.dto;

import tr.com.ind.model.GroupMessage;

public class GroupMessageDtoConverter {
    public static GroupMessageDto convertToDto(GroupMessage groupMessage){
        return new GroupMessageDto(
                groupMessage.getId(),
                groupMessage.getText(),
                groupMessage.getPhoto(),
                groupMessage.getGroup_id().getCreatedDate(),
                groupMessage.getGroup_id(),
                groupMessage.getMessage_id()
        );
    }
    // text
    public static GroupMessage convertToEntity(GroupMessageDto dto) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setId(dto.getId());
        groupMessage.setText(dto.getText());
        groupMessage.setPhoto(dto.getPhoto());
        groupMessage.setGroup_id(dto.getGroup_id());
        groupMessage.setMessage_id(dto.getMessage_id());
        return groupMessage;
    }
}
