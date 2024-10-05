package com.project.controller;

import com.project.domain.NewsArticle;
import com.project.repository.NewsArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.service.NewsArticleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/crawler")
public class NewsCralwerController {

    @Autowired
    private NewsArticleService newsArticleService;

    // Endpoint to save updated news data
    @PostMapping("/save")
    public ResponseEntity<?> crawlNews() {
        log.trace("Elasticsearch crawlNews ");
        newsArticleService.crawlingForJustIn();
        return ResponseEntity.ok("Article saved successfully");
    }

    // Endpoint to retrieve all news data
    @GetMapping("/all")
    public List<NewsArticle> getAllNews() {
        return newsArticleService.getAllNews();
    }

}
