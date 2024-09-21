package domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class NewsArticle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String url;

    @Column(length = 300)
    private String title;

    @Column(length = 100)
    private String topic;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    private LocalDateTime publishedAt;

    /* Contructors */
    public NewsArticle(){}

    public NewsArticle(String url, String title, String topic, String synopsis, LocalDateTime publishedAt){
        this.url = url;
        this.title = title;
        this.topic = topic;
        this.synopsis = synopsis;
        this.publishedAt = publishedAt;
    }
    /* Getter and Setter */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

}