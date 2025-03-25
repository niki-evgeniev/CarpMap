package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.ContactDTO;
import com.example.carpmap.Models.Entity.Contact;
import com.example.carpmap.Repository.ContactRepository;
import com.example.carpmap.Service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Logger logger;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactDTO contactDTO;
    private Contact contact;

    @BeforeEach
    void setUp() {
        contactDTO = new ContactDTO();
        contactDTO.setName("Test User");
        contactDTO.setEmail("test@example.com");
        contactDTO.setMessage("Test Message");

        contact = new Contact();
        contact.setName("Test User");
        contact.setEmail("test@example.com");
        contact.setMessage("Test Message");
    }

    @Test
    void saveContact_ShouldSetCurrentDateTime() {
        // Arrange
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Act
        contactService.saveContact(contactDTO);

        // Assert
        verify(contactRepository).save(argThat(savedContact -> 
            savedContact.getAddedDate() != null &&
            savedContact.getAddedDate().isBefore(LocalDateTime.now().plusSeconds(1)) &&
            savedContact.getAddedDate().isAfter(LocalDateTime.now().minusSeconds(1))
        ));
    }

    @Test
    void saveContact_ShouldMapAllFields() {
        // Arrange
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Act
        contactService.saveContact(contactDTO);

        // Assert
        verify(contactRepository).save(argThat(savedContact -> 
            savedContact.getName().equals("Test User") &&
            savedContact.getEmail().equals("test@example.com") &&
            savedContact.getMessage().equals("Test Message")
        ));
    }

    @Test
    void saveContact_ShouldHandleEmptyMessage() {
        // Arrange
        contactDTO.setMessage("");
        contact.setMessage("");
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Act
        boolean result = contactService.saveContact(contactDTO);

        // Assert
        assertTrue(result);
        verify(contactRepository).save(argThat(savedContact -> 
            savedContact.getMessage().isEmpty()
        ));
    }

    @Test
    void saveContact_ShouldHandleNullFields() {
        // Arrange
        ContactDTO emptyDTO = new ContactDTO();
        Contact emptyContact = new Contact();
        when(modelMapper.map(emptyDTO, Contact.class)).thenReturn(emptyContact);
        when(contactRepository.save(any(Contact.class))).thenReturn(emptyContact);

        // Act
        boolean result = contactService.saveContact(emptyDTO);

        // Assert
        assertTrue(result);
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void saveContact_ShouldHandleLongMessage() {
        // Arrange
        String longMessage = "a".repeat(1000); // дълго съобщение
        contactDTO.setMessage(longMessage);
        contact.setMessage(longMessage);
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Act
        boolean result = contactService.saveContact(contactDTO);

        // Assert
        assertTrue(result);
        verify(contactRepository).save(argThat(savedContact -> 
            savedContact.getMessage().length() == 1000
        ));
    }
}
