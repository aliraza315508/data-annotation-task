package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String url =
                "https://docs.google.com/document/d/e/2PACX-1vSvM5gDlNvt7npYHhp_XfsJvuntUhq184By5xO_pA4b_gCWeXb6dM6ZxwN8rE6S4ghUsCj2VKR21oEP/pub";

        decodeSecretMessage(url);
    }


    public static void decodeSecretMessage(String url) throws Exception {

        // Step 1: Retrieve webpage
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        String html = response.body();


        // Step 2: Parse HTML
        Document document = Jsoup.parse(html);

        Elements rows = document.select("tr");


        // Step 3: Convert rows into Java objects
        List<DecodingSecret> secrets = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {

            Element row = rows.get(i);

            Elements cells = row.getElementsByTag("td");

            if (cells.size() >= 3) {

                int x =
                        Integer.parseInt(cells.get(0).text());

                String character =
                        cells.get(1).text();

                int y =
                        Integer.parseInt(cells.get(2).text());

                secrets.add(
                        new DecodingSecret(x, y, character)
                );
            }
        }


        // Step 4: Build grid
        char[][] grid =
                GridBuilder.buildGrid(secrets);


        // Step 5: Print grid
        GridBuilder.printGrid(grid);
    }
}