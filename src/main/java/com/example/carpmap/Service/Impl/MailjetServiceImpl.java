package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Mail.MailSendDTO;
import com.example.carpmap.Models.Entity.SendEmail;
import com.example.carpmap.Repository.SendEmailRepository;
import com.example.carpmap.Service.MailjetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class MailjetServiceImpl implements MailjetService {
    @Value("${mailjet.api-key}")
    private String apiKey;

    @Value("${mailjet.secret-key}")
    private String secretKey;

    @Value("${mailjet.from-email}")
    private String fromEmail;

    @Value("${mailjet.from-name}")
    private String fromName;

    private final SendEmailRepository sendEmailRepository;

    public MailjetServiceImpl(SendEmailRepository sendEmailRepository) {
        this.sendEmailRepository = sendEmailRepository;
    }

    @Override
    public String sendEmail(MailSendDTO mailSendDTO) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = mapMailjetEmail(mailSendDTO, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.mailjet.com/v3.1/send",
                    request,
                    String.class
            );

            String responseBody = response.getBody();

            if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
                if (responseBody.contains("\"Status\":\"success\"")) {
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.setEmail(mailSendDTO.getToEmail());
                    sendEmail.setMessage(mailSendDTO.getHtmlContent());
                    sendEmail.setSubject(mailSendDTO.getSubject());
                    sendEmailRepository.save(sendEmail);
                    return "SUCCESS";
                } else {
                    return "ERROR";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR (Exception): " + e.getMessage();
        }
        return "Error in mailjet server";
    }

    private HttpEntity<Object> mapMailjetEmail(MailSendDTO mailSendDTO, HttpHeaders headers) {
        mailSendDTO.setTextContent(mailSendDTO.getHtmlContent());
        Map<String, Object> body = Map.of(
                "Messages", List.of(
                        Map.of(
                                "From", Map.of("Email", fromEmail, "Name", fromName),
                                "To", List.of(Map.of("Email", mailSendDTO.getToEmail(), "Name", mailSendDTO.getToName())),
                                "Subject", mailSendDTO.getSubject(),
                                "TextPart", mailSendDTO.getTextContent(),   // plain text
                                "HTMLPart", "<div style='font-size:16px;'>" + mailSendDTO.getHtmlContent() + "</div>"    // rich HTML
                        )
                )
        );

        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        return request;
    }

}

