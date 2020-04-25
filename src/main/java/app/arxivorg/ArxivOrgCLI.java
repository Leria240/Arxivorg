package app.arxivorg;

import app.arxivorg.model.Archive;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.cli.*;
import org.w3c.dom.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ArxivOrgCLI extends Application {
    public static void main(String[] args) throws ParseException{

        System.out.println("Welcome to the arXiv organizer!");
        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);

        args = scanner.nextLine().split(" ");
        //System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        final Option periodOption = Option.builder("p")
                .longOpt("period")
                .hasArg(true)
                .desc("period of articles")
                .required(false)
                .build();

        final Option categoryOption = Option.builder("c")
                .longOpt("category")
                .hasArg(true)
                .desc("category of articles")
                .required(false)
                .build();

        final Options options = new Options();
        options.addOption(periodOption);
        options.addOption(categoryOption);

        final CommandLineParser parser = new DefaultParser();

        try {

            final CommandLine line = parser.parse(options, args);
            Archive archive = new Archive("http://export.arxiv.org/api/query?search_query=all&start=0&max_results=10&sortBy=lastUpdatedDate&sortOrder=descending");

            if (line.hasOption("p")) {
                final String dateString = line.getOptionValue("p");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateString,formatter);
                archive.dateFilter(date);
            }

            if (line.hasOption("c")) {
                String category = line.getOptionValue("c");
                archive.categoryFilter(category);
            }
            if (args[0].equals("list")) {
                for (int i = 0; i < archive.getAllArticles().size(); i++) {
                    System.out.println(i + 1 + ". " + archive.getArticle(i).getTitle());
                    System.out.println("Authors: " + archive.getArticle(i).getAuthors().getData().toString()
                            .replaceAll("\\[", "")
                            .replaceAll("]", ""));
                }
            }
            if(args[0].equals("download")){
                launch(ArxivOrgCLI.class);
                String path = archive.downloadArticles(archive.getAllArticles());
                System.out.println("Download " + archive.getAllArticles().size() + " files to " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Sorry, I can't do anything yet ! (Read: " + scanner.nextLine() + ")");
        scanner.close();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}