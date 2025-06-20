package com.flakTechTask.zohoCrmTrello.component;

import com.flakTechTask.zohoCrmTrello.services.TrelloService;
import com.flakTechTask.zohoCrmTrello.services.ZohoCRMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

    @Autowired private ZohoCRMService zohoCRMService;

    @Scheduled(fixedDelay = 300000)
    public void sync() {
        zohoCRMService.pollAndSyncDeals();
    }
}
