package repository;

import domain.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsArticleRepositry extends JpaRepository<NewsArticle,Long> {
    Optional<NewsArticle> findByUrl(String url);
}
