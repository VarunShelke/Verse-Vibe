package edu.isr.versevibe.controllers;

import edu.isr.versevibe.service.index.IndexManagementService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Resource(name = "indexManagementService")
    private IndexManagementService indexManagementService;

    @PostMapping("/index")
    public boolean createIndex() {
        indexManagementService.indexDocuments();
        return true;
    }
}
