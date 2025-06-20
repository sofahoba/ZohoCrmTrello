package com.flakTechTask.zohoCrmTrello.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flakTechTask.zohoCrmTrello.component.TrelloProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class TrelloService {

    @Autowired
    private TrelloProperties trello;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public String createFullBoard(String boardName) {
        try {

            // create board
            String boardUrl = String.format(
                    "https://api.trello.com/1/boards/?name=%s&key=%s&token=%s",
                    boardName.replace(" ", "+"),
                    trello.getKey(),
                    trello.getToken()
            );

            String boardResponse = restTemplate.postForObject(URI.create(boardUrl), null, String.class);
            JsonNode boardJson = mapper.readTree(boardResponse);
            String boardId = boardJson.get("id").asText();

            // create lists
            String[] lists = {"To Do", "In Progress", "Done"};
            String listId = null;

            for (int i = 0; i < lists.length; i++) {
                String listUrl = String.format(
                        "https://api.trello.com/1/lists?name=%s&idBoard=%s&key=%s&token=%s",
                        lists[i].replace(" ", "+"),
                        boardId,
                        trello.getKey(),
                        trello.getToken()
                );

                String listResponse = restTemplate.postForObject(URI.create(listUrl), null, String.class);
                JsonNode listJson = mapper.readTree(listResponse);

                if (i == 0) {
                    listId = listJson.get("id").asText();
                }
            }

            //  adding cards to fin
            String[] cards = {"Kickoff Meeting Scheduled", "Requirements Gathering", "System Setup"};
            for (String card : cards) {
                String cardUrl = String.format(
                        "https://api.trello.com/1/cards?name=%s&idList=%s&key=%s&token=%s",
                        card.replace(" ", "+"),
                        listId,
                        trello.getKey(),
                        trello.getToken()
                );

                System.out.println("🃏 Creating card: " + card);
                restTemplate.postForObject(URI.create(cardUrl), null, String.class);
            }

            return boardId;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating Trello board", e);
        }
    }
}
