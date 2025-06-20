package com.flakTechTask.zohoCrmTrello.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "zoho")
public class ZohoProperties {
    private String tokenUrl;
    private String apiBaseUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authUrl;
    private String scopes;
    private String accessToken;
    private String refreshToken;
}
