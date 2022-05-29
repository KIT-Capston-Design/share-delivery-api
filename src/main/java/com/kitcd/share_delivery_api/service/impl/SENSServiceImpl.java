package com.kitcd.share_delivery_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.dto.sens.MessageDTO;
import com.kitcd.share_delivery_api.dto.sens.SMSRequestDTO;
import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;
import com.kitcd.share_delivery_api.service.SENSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SENSServiceImpl implements SENSService {

    @Value("${open-api.naver-sms.service-id}") private final String serviceId;
    @Value("${open-api.naver-sms.access-key}") private final String accessKey;
    @Value("${open-api.naver-sms.secret-key}") private final String secretKey;
    @Value("${open-api.naver-sms.sender-phone-number}")private final String senderPhoneNumber;

    public SMSResponseDTO sendSMS(String recipientPhoneNumber, String content){
        final String origin = "https://sens.apigw.ntruss.com"; // Naver SENS
        final String uri = "/sms/v2/services/" + serviceId + "/messages";

        final String currentTime = Long.toString(System.currentTimeMillis());
        final String commonContent = "share-delivery ";

        List<MessageDTO> messages = new ArrayList<>();
        messages.add(new MessageDTO(recipientPhoneNumber, content));

        SMSRequestDTO smsReq = SMSRequestDTO.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(senderPhoneNumber)
                .content(commonContent)
                .messages(messages)
                .build();

        try{
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", currentTime);
            headers.set("x-ncp-iam-access-key", accessKey);

            String signature = makeSignature(uri, currentTime);
            headers.set("x-ncp-apigw-signature-v2", signature);    // 시그니처 서명

            // SMSRequestDTO to json body
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(smsReq);

            // header + body
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(new URI(origin + uri), httpEntity, SMSResponseDTO.class);

        } catch (URISyntaxException | JsonProcessingException | NoSuchAlgorithmException | InvalidKeyException e){
            log.error("SMS 발송 준비 중 예외 발생", e);
            return null;
        }
    }

    private String makeSignature(String uri, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        final String space = " ";					// one space
        final String newLine = "\n";					// new line
        final String method = "POST";

        String message = method + space + uri +
                newLine +
                timestamp +
                newLine +
                accessKey;

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(rawHmac);
    }

}
