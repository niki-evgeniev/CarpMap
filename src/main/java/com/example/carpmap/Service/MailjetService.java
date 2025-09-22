package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Mail.MailSendDTO;
import jakarta.validation.Valid;

public interface MailjetService {
    String sendEmail(@Valid MailSendDTO mailSendDTO);

}
