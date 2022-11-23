package by.bsuir.service.business.impl;

import by.bsuir.dto.GetFactDto;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.FactService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class FactServiceImpl implements FactService {

    @Value("${FACT_API_URL}")
    private String FACT_API_URL;

    @Value("${FACT_API_KEY}")
    private String FACT_API_KEY;

    @Override
    public String getFact(){
        JsonNode root;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-Api-Key", FACT_API_KEY);

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                FACT_API_URL, HttpMethod.GET, mapHttpEntity, String.class);
        if (response.getBody() == null) {
            throw new BusinessException("Fact bad connection");
        }
        return response.getBody().substring(11, response.getBody().indexOf("\"}"));
    }
}
