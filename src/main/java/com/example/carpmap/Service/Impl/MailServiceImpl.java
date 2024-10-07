package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import com.example.carpmap.Models.Entity.Contact;
import com.example.carpmap.Repository.ContactRepository;
import com.example.carpmap.Service.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public MailServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<MailDetailsDTO> getAllContactMail(Pageable pageable) {
        Page<Contact> findAllContact = contactRepository.findAll(pageable);
        Page<MailDetailsDTO> allContact = findAllContact.map(contact -> {
            return modelMapper.map(contact, MailDetailsDTO.class);
        });
        return allContact;
    }
}
