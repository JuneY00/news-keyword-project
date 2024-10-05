package com.project.repository;

import com.project.domain.NewsArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface NewsArticleRepository extends ElasticsearchRepository<NewsArticle, String> {
    Optional<NewsArticle> findByUrl(String url);
    Optional<NewsArticle> findByTitle(String title);
    List<NewsArticle> findAll();

}
