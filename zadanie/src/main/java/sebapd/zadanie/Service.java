package sebapd.zadanie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class Service {

    @Value("${apiKey}")
    private String apiKey;
    @Value("${apiUrl}")
    private  String apiUrl;
    @Value("${filePath}")
    private  String filePath;
    private final RestTemplate restTemplate;
    private final Parser parser;

    public Service(RestTemplate restTemplate, Parser parser) {
        this.restTemplate = restTemplate;
        this.parser = parser;
    }

    @GetMapping("/")
    public String getPolandNews() throws IOException {
        String data = restTemplate.getForObject(apiUrl + apiKey, String.class);
        String parsedArticles = parser.parse(data);
        Files.writeString(Paths.get(filePath), parsedArticles); // If append to end of file select append option
        return parsedArticles;
    }
}
