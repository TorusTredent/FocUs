package by.bsuir.service.business.impl;

import by.bsuir.dto.TranslatorResponse;
import by.bsuir.service.business.TranslatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static by.bsuir.entity.enums.LANGUAGES.ENGLISH;
import static by.bsuir.entity.enums.LANGUAGES.RUSSIAN;

@Service
public class TranslatorServiceImpl implements TranslatorService {

    @Value("${TRANSLATOR_SECRET_KEY}")
    private String TRANSLATOR_SECRET_KEY;

    @Value("${TRANSLATOR_API_URL}")
    private String TRANSLATOR_API_URL;

    @Override
    public String translateText(String fromLang, String toLang, String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);

        String url = TRANSLATOR_API_URL.replace("{secret_key}", TRANSLATOR_SECRET_KEY)
                                        .replace("{lang-from}", fromLang)
                                        .replace("{lang-to}", toLang);

        return Objects.requireNonNull(restTemplate.postForObject(url, mapHttpEntity, TranslatorResponse.class)).getResult();
    }
}
