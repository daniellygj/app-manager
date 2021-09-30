package com.appManager.controller;

import com.appManager.controller.filter.AppFilter;
import com.appManager.model.AppModel;
import com.appManager.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @PostMapping("/create")
    public AppModel createApp(@RequestBody AppModel appModel) {
        return appService.createApp(appModel);
    }

    @GetMapping("/find-by-name-and-type")
    public List<AppModel> findByNameAndType(AppFilter appFilter) {
        return appService.findByNameAndType(appFilter);
    }

    @GetMapping("/cheapest-by-type/{type}")
    public AppModel getCheapestByType(@PathVariable("type") String type) {
        return appService.findCheaperByType(type);
    }
}
