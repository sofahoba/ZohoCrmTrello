package com.flakTechTask.zohoCrmTrello.services;

import com.flakTechTask.zohoCrmTrello.component.ZohoProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ZohoAuthService {
    private static final Logger log = LoggerFactory.getLogger(ZohoAuthService.class);

    @Autowired
    private ZohoProperties zoho;

    @Autowired
    private RestTemplate restTemplate;

    public String refreshAccessToken() {
        log.info("### Refreshing Zoho access token...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", zoho.getClientId());
        params.add("client_secret", zoho.getClientSecret());
        params.add("refresh_token", zoho.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(zoho.getTokenUrl(), request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String newAccessToken = (String) response.getBody().get("access_token");
            zoho.setAccessToken(newAccessToken);
            log.info("### Zoho access token refreshed successfully: {}", newAccessToken);
            return newAccessToken;
        }

        log.error("!!!! Failed to refresh Zoho access token. Status: {}", response.getStatusCode());
        throw new RuntimeException("Failed to refresh Zoho access token");
    }

}
