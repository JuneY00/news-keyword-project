package com.project.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "news")
public class NewsArticle {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String url;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String topic;

    @Field(type = FieldType.Text)
    private String synopsis;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime publishedAt;

    /* Constructors */
    public NewsArticle() {}

    public NewsArticle(String url, String title, String topic, String synopsis, LocalDateTime publishedAt) {
        this.url = url;
        this.title = title;
        this.topic = topic;
        this.synopsis = synopsis;
        this.publishedAt = publishedAt;
    }
}
