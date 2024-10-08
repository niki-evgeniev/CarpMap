package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MailService {

    Page<MailDetailsDTO> getAllContactMail(Pageable pageable);

    MailDetailsDTO getDetailsMail(Long id);

    boolean deleteMail(Long id);
}
