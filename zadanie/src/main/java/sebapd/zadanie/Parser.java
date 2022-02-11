package sebapd.zadanie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Parser {
    public String parse(String data) {
        StringBuilder parsedArticles = new StringBuilder();
        JSONObject obj = new JSONObject(data);
        JSONArray articles = obj.getJSONArray("articles");
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
