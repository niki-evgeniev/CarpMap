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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
        contactDTO.setMessage("Hello!");

        contact = new Contact();
        contact.setName("Test User");
        contact.setEmail("test@example.com");
        contact.setMessage("Hello!");
        contact.setAddedDate(LocalDateTime.now());
    }

    @Test
    void saveContact_ShouldReturnTrue_WhenContactIsSaved() {
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        boolean result = contactService.saveContact(contactDTO);
        assertTrue(result, "Expected saveContact() to return true");
        verify(contactRepository, times(1)).save(contact);
        verify(modelMapper, times(1)).map(contactDTO, Contact.class);
    }

    @Test
    void saveContact_ShouldLogMessage_WhenContactIsSaved() {
        when(modelMapper.map(contactDTO, Contact.class)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        contactService.saveContact(contactDTO);
//        verify(logger, times(1)).info("Successful added new CONTACT");
        verify(logger, times(1)).info("Successful added new CONTACT");
    }
}
