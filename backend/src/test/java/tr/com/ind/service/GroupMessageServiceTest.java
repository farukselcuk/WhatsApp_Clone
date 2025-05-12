package tr.com.ind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.com.ind.dto.GroupMessageDto;
import tr.com.ind.dto.GroupMessageDtoConverter;
import tr.com.ind.model.GroupMessage;
import tr.com.ind.model.Role;
import tr.com.ind.model.User;
import tr.com.ind.repository.GroupMessageRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GroupMessageServiceTest {

    @Mock
    private GroupMessageRepository groupMessageRepository;

    @Mock
    private GroupMessageDtoConverter groupMessageDtoConverter;

    @InjectMocks
    private GroupMessageService groupMessageService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // User nesnelerini test için oluşturuyoruz
        user1 = new User(1L, "123456789", "password1", "John", "Doe", "active", null, true, LocalDateTime.now(), Role.USER, null, null, null, null);
        user2 = new User(2L, "987654321", "password2", "Jane", "Doe", "active", null, true, LocalDateTime.now(), Role.USER, null, null, null, null);
    }

    @Test
    void testGetAllGroupMessages() {
        // Test verisi olarak kullanılacak GroupMessage nesnelerini oluşturuyoruz
        GroupMessage groupMessage1 = new GroupMessage(1L, "Message 1", null,user1, user2);
        GroupMessage groupMessage2 = new GroupMessage(2L, "Message 2",null, user2, user1);

        // Mock davranışlarını ayarlıyoruz
        when(groupMessageRepository.findAll()).thenReturn(Arrays.asList(groupMessage1, groupMessage2));

        // Service metodu çağrılır
        List<GroupMessage> result = groupMessageService.getAllGroupMessages();

        // Beklenen ve gerçek sonuçları karşılaştırıyoruz
        assertEquals(2, result.size());
        assertEquals(groupMessage1, result.get(0));
        assertEquals(groupMessage2, result.get(1));

        // Mock nesnesinin metodunun çağrılıp çağrılmadığını kontrol ederiz
        verify(groupMessageRepository, times(1)).findAll();
    }

    @Test
    void testGetGroupMessageById() {
        // Test verisi olarak kullanılacak GroupMessage nesnesini oluşturuyoruz
        Long id = 1L;
        GroupMessage groupMessage = new GroupMessage(id, "Message", null,user1, user2);

        // Mock davranışlarını ayarlıyoruz
        when(groupMessageRepository.findById(id)).thenReturn(Optional.of(groupMessage));

        // Service metodu çağrılır
        Optional<GroupMessage> result = groupMessageService.getGroupMessageById(id);

        // Beklenen ve gerçek sonuçları karşılaştırıyoruz
        assertEquals(groupMessage, result.orElse(null));

        // Mock nesnesinin metodunun çağrılıp çağrılmadığını kontrol ederiz
        verify(groupMessageRepository, times(1)).findById(id);
    }

    @Test
    void testInsertGroupMessage() {
        // Test verisi olarak kullanılacak GroupMessageDto ve GroupMessage nesnelerini oluşturuyoruz
        GroupMessageDto groupMessageDto = new GroupMessageDto(null, "New Message", null,LocalDateTime.now(), user1, user2);
        GroupMessage groupMessage = new GroupMessage(null, "New Message", null,user1, user2);
        GroupMessage savedGroupMessage = new GroupMessage(1L, "New Message", null,user1, user2);

        // Mock davranışlarını ayarlıyoruz
        when(groupMessageDtoConverter.convertToEntity(groupMessageDto)).thenReturn(groupMessage);
        when(groupMessageRepository.save(groupMessage)).thenReturn(savedGroupMessage);
        when(groupMessageDtoConverter.convertToDto(savedGroupMessage)).thenReturn(new GroupMessageDto(1L, "New Message", null,LocalDateTime.now(), user1, user2));

        // Service metodu çağrılır
        GroupMessageDto result = groupMessageService.insertGroupMessage(groupMessageDto);

        // Beklenen ve gerçek sonuçları karşılaştırıyoruz
        assertEquals(1L, result.getId());
        assertEquals("New Message", result.getText());
        assertEquals(user1, result.getGroup_id());
        assertEquals(user2, result.getMessage_id());

        // Mock nesnelerinin metodlarının çağrılıp çağrılmadığını kontrol ederiz
        verify(groupMessageDtoConverter, times(1)).convertToEntity(groupMessageDto);
        verify(groupMessageRepository, times(1)).save(groupMessage);
        verify(groupMessageDtoConverter, times(1)).convertToDto(savedGroupMessage);
    }

    @Test
    void testUpdateGroupMessage() {
        // Test verisi olarak kullanılacak GroupMessageDto ve GroupMessage nesnelerini oluşturuyoruz
        Long id = 1L;
        GroupMessageDto groupMessageDto = new GroupMessageDto(id, "Updated Message", null,LocalDateTime.now(), user1, user2);
        GroupMessage existingGroupMessage = new GroupMessage(id, "Old Message", null,user1, user2);

        // Mock davranışlarını ayarlıyoruz
        when(groupMessageRepository.findById(id)).thenReturn(Optional.of(existingGroupMessage));
        when(groupMessageRepository.save(existingGroupMessage)).thenReturn(existingGroupMessage);
        when(groupMessageDtoConverter.convertToDto(existingGroupMessage)).thenReturn(groupMessageDto);

        // Service metodu çağrılır
        GroupMessageDto result = groupMessageService.updateGroupMessage(id, groupMessageDto);

        // Beklenen ve gerçek sonuçları karşılaştırıyoruz
        assertEquals(id, result.getId());
        assertEquals("Updated Message", result.getText());

        // Mock nesnelerinin metodlarının çağrılıp çağrılmadığını kontrol ederiz
        verify(groupMessageRepository, times(1)).findById(id);
        verify(groupMessageRepository, times(1)).save(existingGroupMessage);
        verify(groupMessageDtoConverter, times(1)).convertToDto(existingGroupMessage);
    }

    @Test
    void testDeleteGroupMessage() {
        // Silinecek mesajın ID'sini belirliyoruz
        Long id = 1L;

        // Service metodu çağrılır
        groupMessageService.deleteGroupMessage(id);

        // Mock nesnesinin metodunun çağrılıp çağrılmadığını kontrol ederiz
        verify(groupMessageRepository, times(1)).deleteById(id);
    }
}
