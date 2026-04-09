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
//        mailSendDTO.setTextContent(mailSendDTO.getHtmlContent());
        String plainText = mailSendDTO.getHtmlContent() != null
                ? mailSendDTO.getHtmlContent()
                .replaceAll("<[^>]*>", " ")  // маха HTML тагове
                .replaceAll("&nbsp;", " ")
                .replaceAll("&amp;", "&")
                .replaceAll("\\s+", " ")      // слива излишни интервали
                .trim()
                : "";

        Map<String, Object> body = Map.of(
                "Messages", List.of(
                        Map.of(
                                "From", Map.of("Email", fromEmail, "Name", fromName),
                                "To", List.of(Map.of("Email", mailSendDTO.getToEmail(), "Name", mailSendDTO.getToName())),
                                "Sender", Map.of("Email", fromEmail, "Name", fromName),
                                "Subject", mailSendDTO.getSubject(),
                                "TextPart", plainText,   // plain text
                                "HTMLPart", wrapHtml(mailSendDTO.getHtmlContent())   ,  // rich HTML
                                "TrackOpens", "disabled",   // ← добави това
                                "TrackClicks", "disabled"   // ← и това

                        )
                )
        );

        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        return request;
    }

    private String wrapHtml(String content) {
        return """
                    <div style='font-family:Arial,sans-serif; font-size:16px; line-height:1.6; color:#333; max-width:600px; margin:0 auto; padding:20px;'>
                        <p>%s</p>
                        <hr style='border:none; border-top:1px solid #eee; margin:20px 0;'/>
                        <p style='font-size:12px; color:#999;'>
                            Това съобщение е изпратено от carpmap.bg<br/>
                            При въпроси: info@carpmap.bg
                        </p>
                    </div>
                """.formatted(content);
    }
}

