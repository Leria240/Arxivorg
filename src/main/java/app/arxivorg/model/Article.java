package app.arxivorg.model;


import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class Article {
    private String id;
    private String updated;
    private String published;
    private String title;
    private String summary;
    private Authors authors;
    private URL URL_pageArxiv;
    private URL URL_PDF;
    private List<String> category;
    private boolean favoriteItem;
    private boolean selected;

    public Article(String id, String updated, String published, String title,
                   String summary, Authors authors,
                   URL URL_pageArxiv, URL URL_PDF, List<String> category) {
        this.id = id;
        this.updated = updated;
        this.published = published;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.URL_pageArxiv = URL_pageArxiv;
        this.URL_PDF = URL_PDF;
        this.category = category;
        this.favoriteItem = false;
        this.selected = true;
    }

    public String getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public String getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public Authors getAuthors() {
        return authors;
    }

    public URL getURL_pageArxiv() {
        return URL_pageArxiv;
    }

    public URL getURL_PDF() {
        return URL_PDF;
    }

    public List<String> getCategory() {
        return category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String mainInformations() {
        String authors = "Authors: " + getAuthors().toString();
        String id = "ArXiv: " + getId().substring(21);
        return "- " + title + "\n\t" + authors + "\n\t" + id;
    }

    public String toString() {
        return "Title: " + this.getTitle() + "\n\t" +
                "Authors: " + this.getAuthors().toString() + "\n\n" +
                "Summary: " + this.getSummary() + "\n\n" +
                "A link to the ArXiv page: " + getURL_pageArxiv();
    }

    public boolean isFavoriteItem() {
        return favoriteItem;
    }

    public void changeFavoriteItem() {
        favoriteItem = !favoriteItem;
    }

    public void download() {
        URL url = this.getURL_PDF();
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get("../../someArticle.pdf"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final int BUFFER_SIZE = 4096;

    /**
     * Downloads a file from a URL
     *
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public void downloadFile(String saveDir)
            throws IOException {
        URL url = this.getURL_PDF();
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                String fileURL = this.getURL_PDF().toString();
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            /*
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

             */

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    public void downloadIO() {

        InputStream inputStream;
        FileOutputStream fileOS;

        try {
            inputStream = getURL_PDF().openStream();
            fileOS = new FileOutputStream("../../someArticle.pdf");
            int i = IOUtils.copy(inputStream, fileOS);
            System.out.println(i + " ok ");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



