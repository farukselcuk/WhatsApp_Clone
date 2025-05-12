package tr.com.ind.service;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tr.com.ind.dto.UserMessageDto;
import tr.com.ind.dto.UserMessageDtoConverter;
import tr.com.ind.model.Role;
import tr.com.ind.model.User;
import tr.com.ind.model.UserMessage;
import tr.com.ind.repository.UserMessageRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserMessageServiceTest {

    @InjectMocks
    private UserMessageService sut; // Test Edilen Sistem

    @Mock
    private UserMessageRepository userMessageRepository; // Mocklanan UserMessageRepository

    @Mock
    private UserMessageDtoConverter userMessageDtoConverter; // Mocklanan UserMessageDtoConverter

    @Captor
    private ArgumentCaptor<UserMessage> userMessageCaptor; // ArgumentCaptor ile UserMessage yakalamak için kullanılır

    private User sender;
    private User receiver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Mockito ile mock nesneleri başlatır

        // User nesnelerini test için oluşturuyoruz
        sender = new User(1L, "123456789", "password1", "John", "Doe", "active", null, true, LocalDateTime.now(), Role.USER, null, null, null, null);
        receiver = new User(2L, "987654321", "password2", "Jane", "Doe", "active", null, true, LocalDateTime.now(), Role.USER, null, null, null, null);
    }


    @Test
    @DisplayName("UserMessage'ı ID'ye göre getir")
    void should_get_user_message_by_id() {

        Long id = 1L;
        UserMessage userMessage = new UserMessage(id, "Test Message", null,receiver, sender, null);

        when(userMessageRepository.findById(id)).thenReturn(Optional.of(userMessage)); // ID'ye göre userMessage'ı döndürür


        Optional<UserMessage> result = sut.getUserMessageById(id);


        assertEquals("Test Message", result.get().getText());
        verify(userMessageRepository, times(1)).findById(id); // findById çağrısını doğrular
    }

    @Test
    @DisplayName("UserMessage ekle")
    void should_insert_user_message() {

        UserMessageDto userMessageDto = new UserMessageDto(null, "Test Message", null,receiver, sender, null); // DTO nesnesi oluşturulur
        UserMessage userMessage = new UserMessage(null, "Test Message",null, receiver, sender, null); // Dönüştürülen entity nesnesi
        UserMessage savedUserMessage = new UserMessage(1L, "Test Message",null, receiver, sender, null); // Kaydedilmiş entity nesnesi

        when(userMessageDtoConverter.convertToEntity(userMessageDto)).thenReturn(userMessage); // DTO'yu entity'ye dönüştürür
        when(userMessageRepository.save(userMessage)).thenReturn(savedUserMessage); // UserMessage kaydeder
        when(userMessageDtoConverter.convertToDto(savedUserMessage)).thenReturn(new UserMessageDto(1L, "Test Message",null, receiver, sender, null)); // Entity'yi DTO'ya dönüştürür


        UserMessageDto result = sut.insertUserMessage(userMessageDto);


        assertEquals("Test Message", result.getText());
        assertEquals(receiver, result.getReceiver_id());
        assertEquals(sender, result.getSender_id());
        verify(userMessageRepository, times(1)).save(userMessage); // save() çağrısını doğrular
    }

    @Test
    @DisplayName("UserMessage güncelle")
    void should_update_user_message() {

        Long id = 1L;
        UserMessageDto userMessageDto = new UserMessageDto(id, "Updated Message", null,receiver, sender, null); // DTO nesnesi oluşturulur
        UserMessage userMessage = new UserMessage(id, "Test Message",null, receiver, sender, null); // Mevcut UserMessage nesnesi

        when(userMessageRepository.findById(id)).thenReturn(Optional.of(userMessage)); // ID'ye göre userMessage'ı bulur
        when(userMessageRepository.save(userMessage)).thenReturn(userMessage); // Güncellenmiş UserMessage kaydeder
        when(userMessageDtoConverter.convertToDto(userMessage)).thenReturn(userMessageDto); // Güncellenmiş UserMessage'ı DTO'ya dönüştürür


        UserMessageDto result = sut.updateUserMessage(id, userMessageDto);


        assertEquals("Updated Message", result.getText());
        assertEquals(receiver, result.getReceiver_id());
        assertEquals(sender, result.getSender_id());
        verify(userMessageRepository, times(1)).findById(id); // findById çağrısını doğrular
        verify(userMessageRepository, times(1)).save(userMessage); // save() çağrısını doğrular
    }

    @Test
    @DisplayName("UserMessage sil")
    void should_delete_user_message() {

        Long id = 1L;

        when(userMessageRepository.existsById(id)).thenReturn(true); // ID'ye göre UserMessage'ın var olup olmadığını kontrol eder
        doNothing().when(userMessageRepository).deleteById(id); // ID'ye göre UserMessage'ı siler


        sut.deleteUserMessage(id);


        verify(userMessageRepository, times(1)).deleteById(id); // deleteById çağrısını doğrular
    }
}
