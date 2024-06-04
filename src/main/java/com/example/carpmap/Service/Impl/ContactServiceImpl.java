package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.ContactDTO;
import com.example.carpmap.Models.Entity.Contact;
import com.example.carpmap.Repository.ContactRepository;
import com.example.carpmap.Service.ContactService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_CONTACT;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveContact(ContactDTO contactDTO) {
        Contact addNewContact = modelMapper.map(contactDTO, Contact.class);
        addNewContact.setAddedDate(LocalDateTime.now());
        contactRepository.save(addNewContact);
        System.out.println(SUCCESSFUL_ADD_CONTACT);
        LOGGER.info(SUCCESSFUL_ADD_CONTACT);
        return true;
    }
}
