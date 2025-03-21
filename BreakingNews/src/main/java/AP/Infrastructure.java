package AP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;


    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        parseInformation(); // Auto-parse after fetching
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private String getInformation() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("!!Exception : " + e.getMessage());
        }
        return null;
    }

    private void parseInformation() {
        if (JSONRESULT == null || JSONRESULT.isEmpty()) {
            System.out.println("No data to parse!");
            return;
        }
        newsList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(JSONRESULT);
            JSONArray articles = jsonResponse.getJSONArray("articles");

            for (int i = 0; i < Math.min(articles.length(), 20); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.optString("title", "No Title Available");
                String description = article.optString("description", "No Description Available");

                // `source` is a nested object, so we need to extract "name"
                JSONObject sourceObject = article.optJSONObject("source");
                String sourceName = (sourceObject != null) ? sourceObject.optString("name", "Unknown Source") : "Unknown Source";

                String author = article.optString("author", "Unknown Author");
                String url = article.optString("url", "No URL Available");
                String publishedAt = article.optString("publishedAt", "Unknown Date");

                // Create a News object and add it to the list
                newsList.add(new News(title, description, sourceName, author, url, publishedAt));
            }

        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }

    public void displayNewsList() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0 ; i < newsList.size() ; i++) {           //displaying news titles
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
        System.out.println("Enter the number of news you want to read in full: (press 0 to exit)");
        while (true){   //keeps asking until we get a valid input
            try {
                int userInput = scanner.nextInt();
                if (userInput == 0) {
                    System.exit(0);
                }
                else if (userInput > 0 && userInput <= newsList.size()) {
                    System.out.println("\n" + newsList.get(userInput - 1)); // prints selected news
                    break; // exits loop after valid selection
                }
                else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + newsList.size() + ": ");     //if the input is out of bound
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please enter a number: ");       //if the input is not an int
                scanner.next();         //clearing the value of invalid input
            }
        }
    }
}