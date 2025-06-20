package com.flakTechTask.zohoCrmTrello;

import com.flakTechTask.zohoCrmTrello.component.TrelloProperties;
import com.flakTechTask.zohoCrmTrello.component.ZohoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ZohoProperties.class, TrelloProperties.class})
public class ZohoCrmTrelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZohoCrmTrelloApplication.class, args);
	}

}
