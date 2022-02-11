package sebapd.zadanie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class Service {

    String path = "C:\\Poland business news\\news.txt";
    RestTemplate restTemplate;

    public Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String getPolandNews() throws IOException {
        String data = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?country=pl&category=business&apiKey=0f5b4a48c0d3471d80c79526538dba01", String.class);
        JSONObject obj = new JSONObject(data);
        String  parsedArticles = parse(obj);
        Files.writeString(Paths.get(path), parsedArticles); // If append to end of file select append option
        return parsedArticles;
    }

    private String parse(JSONObject object) {
        StringBuilder parsedArticles = new StringBuilder();
        JSONArray articles = object.getJSONArray("articles");
        for (int i = 0; i < articles.length(); i++) {

            String author = articles.getJSONObject(i).optString("author", "No author");
            String title = articles.getJSONObject(i).optString("title", "No title");
            String description = articles.getJSONObject(i).optString("description", "No description");

            String parsedSingleArticle = title + ":" + description + ":" + author;
            parsedArticles.append(parsedSingleArticle).append(System.getProperty("line.separator"));
        }
        return parsedArticles.toString();
    }
}
