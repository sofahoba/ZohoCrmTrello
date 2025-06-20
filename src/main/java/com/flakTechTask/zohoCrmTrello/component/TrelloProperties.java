package com.flakTechTask.zohoCrmTrello.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "trello.api")
public class TrelloProperties {
    private String key;
    private String token;
}
