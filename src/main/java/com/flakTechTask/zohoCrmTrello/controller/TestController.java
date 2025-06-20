package com.flakTechTask.zohoCrmTrello.controller;

import com.flakTechTask.zohoCrmTrello.services.ZohoCRMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private ZohoCRMService zohoCRMService;


    @GetMapping("/sync")
    public ResponseEntity<String> testSync() {
        zohoCRMService.pollAndSyncDeals();
        return ResponseEntity.ok("Sync triggered");
    }
}
