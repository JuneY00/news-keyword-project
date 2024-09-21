package service;
import domain.NewsArticle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.NewsArticleRepositry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class NewsArticleService
{
    @Autowired
    private static NewsArticleRepositry newsArticleRepositry;

    public static void crawlingForJustIn() {
        int offset = 0;
        int size = 250;
        int total = 250;

        // Headers for simulating browser request
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";

        String root = "https://www.abc.net.au/";
        String apiUrl = String.format("https://www.abc.net.au/news-web/api/loader/channelrefetch?name=PaginationArticlesFuture&documentId=10719976&prepareParams=%%7B%%22imagePosition%%22%%3A%%7B%%22mobile%%22%%3A%%22right%%22%%2C%%22tablet%%22%%3A%%22right%%22%%2C%%22desktop%%22%%3A%%22right%%22%%7D%%7D&future=true&offset=%d&size=%d&total=%d", offset, size, total);

        try {
            // Make the HTTP request
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", userAgent);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray collection = jsonResponse.getJSONArray("collection");

                for (int i = 0; i < collection.length(); i++) {
                    try {
                        JSONObject article = collection.getJSONObject(i);

                        // Extract article URL
                        String articleUrl = root + article.getString("link");

                        // Extract article title
                        String title = article.getString("title");

                        // Extract article last update time
                        LocalDateTime parsedDatetime = LocalDateTime.parse(article.getJSONObject("dates").getString("lastUpdated"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                        // Extract article topic
                        String articleTopic = article.getJSONArray("tags").getJSONObject(0).getString("title");

                        // Extract article synopsis
                        String synopsis = article.getJSONObject("synopsis").getJSONObject("descriptor").
                                getJSONArray("children").getJSONObject(0).getJSONArray("children").
                                getJSONObject(0).getString("content");

                        // Simulate saving the article (replace with actual database logic)
                        saveOrUpdateArticle(articleUrl, title, articleTopic, synopsis, parsedDatetime);

                    } catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("Failed to retrieve content. HTTP response code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveOrUpdateArticle(String url, String title, String topic, String synopsis, LocalDateTime publishedAt){
        // check if the article already exists in the database
        Optional<NewsArticle> optionalNewsArticle = newsArticleRepositry.findByUrl(url);

        if(optionalNewsArticle.isPresent()){
            NewsArticle article = optionalNewsArticle.get();

            // check if any of the fields have changed
            if(!article.getTitle().equals(title)||
                !article.getSynopsis().equals(synopsis)||
                !article.getTopic().equals(topic)||
                !article.getPublishedAt().equals(publishedAt)){

                // update the article fields
                article.setTitle(title);
                article.setTopic(topic);
                article.setSynopsis(synopsis);
                article.setPublishedAt(publishedAt);

                newsArticleRepositry.save(article);
                System.out.println("Article '" + title + "' updated in the database.");
            } else {
                System.out.println("Article '" + title + "' already exists and is up to date.");
            }
        }else{
            // create and save a new article
            NewsArticle newArticle = new NewsArticle(url, title, topic, synopsis, publishedAt);
            newsArticleRepositry.save(newArticle);
            System.out.println("Article '" + title + "' saved to the database.");
        }

    }
}
