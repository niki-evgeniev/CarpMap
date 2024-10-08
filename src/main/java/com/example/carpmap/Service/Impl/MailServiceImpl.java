package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import com.example.carpmap.Models.Entity.Contact;
import com.example.carpmap.Repository.ContactRepository;
import com.example.carpmap.Service.MailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.carpmap.Cammon.ErrorMessages.ERROR_MAIL_WITH_ID_NOT_FOUND;
import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_DELETE_MAIL;

@Service
public class MailServiceImpl implements MailService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

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

    @Override
    public MailDetailsDTO getDetailsMail(Long id) {

        Optional<Contact> findContactMail = contactRepository.findById(id);

        if (findContactMail.isPresent()) {
            Contact readedContact = findContactMail.get();
            readedContact.setRead(true);
            contactRepository.save(readedContact);
            MailDetailsDTO mailDetailsDTO = modelMapper.map(findContactMail, MailDetailsDTO.class);
            return mailDetailsDTO;
        }
        String errorMassage = String.format(ERROR_MAIL_WITH_ID_NOT_FOUND, id);
        LOGGER.error(errorMassage);
        return null;
    }

    @Override
    public boolean deleteMail(Long id) {
        Optional<Contact> findToDeleteMail = contactRepository.findById(id);
        if (findToDeleteMail.isPresent()){
            contactRepository.deleteById(id);
            String errMsg = String.format(SUCCESSFUL_DELETE_MAIL, findToDeleteMail.get().getId());
            LOGGER.error(errMsg);
            return true;
        }
        return false;
    }
}
