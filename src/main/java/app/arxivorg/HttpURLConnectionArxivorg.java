package app.arxivorg;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionArxivorg {

    public static void main(String[]args) throws Exception {

        HttpURLConnectionArxivorg http = new HttpURLConnectionArxivorg();
        http.sendGet("http://export.arxiv.org/api/query?search_query=cat:cs.CL&start=0&max_results=10");

    }

    public Document sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        connection.setRequestProperty("User-Agent", "application/atom+xml");

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            System.out.println(response.toString());

            //créer un document xml à partir du string
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(new InputSource(new StringReader(response.toString())));

        }
    }


}
