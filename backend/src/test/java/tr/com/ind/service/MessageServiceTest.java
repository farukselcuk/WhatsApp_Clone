package tr.com.ind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tr.com.ind.dto.MessageDto;
import tr.com.ind.dto.MessageDtoConverter;
import tr.com.ind.model.Message;
import tr.com.ind.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    // Test edilen sınıf.
    @InjectMocks
    private MessageService sut; // System Under Test (Test Edilen Sistem)

    // Mock nesneleri oluşturuyoruz.
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageDtoConverter messageDtoConverter;

    // Testler başlamadan önce mock nesneleri başlatıyoruz.
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Tüm mesajların listelendiğini test eden yöntem.
    @Test
    @DisplayName("Get all messages")
    void should_get_all_messages() {
        // Test verileri
        Message message1 = new Message(1L, "Hello", null, true, LocalDateTime.now(), null);
        Message message2 = new Message(2L, "World", null, true, LocalDateTime.now(), null);
        List<Message> messages = Arrays.asList(message1, message2);

        // Mock repository'den tüm mesajları döndürmesini sağlıyoruz.
        when(messageRepository.findAll()).thenReturn(messages);

        // Test edilen sınıfın yöntemini çağırıyoruz.
        List<Message> result = sut.getAllMessages();

        // Sonuçları kontrol ediyoruz.
        assertEquals(2, result.size());
        verify(messageRepository, times(1)).findAll();
    }

    // Belirli bir ID'ye sahip mesajın getirildiğini test eden yöntem.
    @Test
    @DisplayName("Get message by ID")
    void should_get_message_by_id() {
        Long id = 1L;
        Message message = new Message(id, "Hello", null, true, LocalDateTime.now(), null);

        // Mock repository'den belirli ID'ye sahip mesajı döndürmesini sağlıyoruz.
        when(messageRepository.findById(id)).thenReturn(Optional.of(message));

        // Test edilen sınıfın yöntemini çağırıyoruz.
        Optional<Message> result = sut.getMessageById(id);

        // Sonuçları kontrol ediyoruz.
        assertTrue(result.isPresent());
        assertEquals("Hello", result.get().getText());
        verify(messageRepository, times(1)).findById(id);
    }

    // Message Create Test Fonksiyonu.
    @Test
    @DisplayName("Insert message")
    void should_insert_message() {
        // Test verilerini hazırlıyoruz.(Constructorlar)
        MessageDto messageDto = new MessageDto(null, "Hello", true, LocalDateTime.now(), null, "status", "message");
        Message message = new Message("Hello", null, true, LocalDateTime.now(), null);
        Message savedMessage = new Message(1L, "Hello", null, true, LocalDateTime.now(), null);

        // Mock converter ve repository'den beklenen dönüşleri sağlıyoruz.
        when(messageDtoConverter.convertToEntity(messageDto)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(savedMessage);
        when(messageDtoConverter.convertToDto(savedMessage)).thenReturn(messageDto);

        // Test edilen sınıfın fonksiyonunu çağırıyoruz.
        MessageDto result = sut.insertMessage(messageDto);

        // Sonuçları kontrol ediyoruz.
        assertEquals("Hello", result.getText());
        verify(messageRepository, times(1)).save(message);
    }

    // Update Fonksiyonu
    @Test
    @DisplayName("Update message")
    void should_update_message() {
        Long id = 1L;
        MessageDto messageDto = new MessageDto(null, "Updated", true, LocalDateTime.now(), null, "status", "message");
        Message message = new Message(id, "Hello", null, true, LocalDateTime.now(), null);

        // Mock repository'den belirli ID'ye sahip mesajı döndürmesini ve güncellenmiş mesajı kaydetmesini sağlıyoruz.
        when(messageRepository.findById(id)).thenReturn(Optional.of(message));
        when(messageRepository.save(message)).thenReturn(message);
        when(messageDtoConverter.convertToDto(message)).thenReturn(messageDto);

        // Test edilen sınıfın fonksiyonunu çağırıyoruz.
        MessageDto result = sut.updateMessage(id, messageDto);

        // Sonuçları kontrol ediyoruz.
        assertEquals("Updated", result.getText());
        verify(messageRepository, times(1)).findById(id);
        verify(messageRepository, times(1)).save(message);
    }

    // Delete Fonksiyonu
    @Test
    @DisplayName("Delete message")
    void should_delete_message() {
        Long id = 1L;

        // Mock repository'den belirli ID'ye sahip mesajı silmesini sağlıyoruz.
        doNothing().when(messageRepository).deleteById(id);

        // Test edilen sınıfın fonksiyonunu çağırıyoruz.
        sut.deleteMessage(id);

        // Silme işleminin gerçekleştiğini kontrol ediyoruz.
        verify(messageRepository, times(1)).deleteById(id);
    }
}
