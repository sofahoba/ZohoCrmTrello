package com.flakTechTask.zohoCrmTrello.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flakTechTask.zohoCrmTrello.component.ZohoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ZohoCRMService {

    @Autowired private ZohoAuthService authService;
    @Autowired private ZohoProperties zoho;
    @Autowired private RestTemplate restTemplate;
    @Autowired private TrelloService trelloService;

    private final ObjectMapper mapper = new ObjectMapper();

    public void pollAndSyncDeals() {
        try {
//            System.out.println("### Starting to poll Zoho Deals###");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Zoho-oauthtoken " + zoho.getAccessToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    zoho.getApiBaseUrl() + "/Deals",
                    HttpMethod.GET,
                    entity,
                    String.class);

            JsonNode data = mapper.readTree(response.getBody()).path("data");

            for (JsonNode deal : data) {
                String stage = deal.path("Stage").asText();
                String type = deal.path("Type").asText();
                String boardId = deal.path("Project_Board_ID__c").asText(null);
                String dealId = deal.path("id").asText();
                String dealName = deal.path("Deal_Name").asText();


                if ("Project Kickoff".equals(stage)
                        && "New Implementation Project".equals(type)
                        && (boardId == null || boardId.isBlank())) {

                    String createdBoardId = trelloService.createFullBoard("Project: " + dealName);
                    updateProjectBoardId(dealId, createdBoardId);
                }
            }

        } catch (HttpClientErrorException.Unauthorized e) {
//            System.out.println("Access token expired");
            authService.refreshAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process deals", e);
        }
    }

    public void updateProjectBoardId(String dealId, String boardId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Zoho-oauthtoken " + zoho.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = String.format("{" +
                            "\"data\": [{\"id\": \"%s\", \"Project_Board_ID__c\": \"%s\"}]}" ,
                    dealId, boardId);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
        } catch (Exception e) {
            System.out.println("!!! Failed to update Zoho deal:");
            e.printStackTrace();
        }
    }
}
