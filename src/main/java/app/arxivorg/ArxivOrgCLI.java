package app.arxivorg;

import app.arxivorg.model.Archive;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.*;

public class ArxivOrgCLI {
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

            Archive archive = new Archive();
            File file = new File("atomFile1.xml");
            archive.addArticles(file);
            if (line.hasOption("p")) {
                final String date = line.getOptionValue("p");
                archive.dateFilter(date);
            }

            if (line.hasOption("c")) {
                String category = line.getOptionValue("c");
                archive.categoryFilter(category);
            }

            for (int i = 0; i < archive.getAllArticles().size(); i++) {
                if (archive.getArticle(i).isSelected()) {
                    System.out.println(i+1 + ". " + archive.getArticle(i).getTitle());
                    System.out.println("Authors: " + archive.getArticle(i).getAuthors().getData());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("Sorry, I can't do anything yet ! (Read: " + scanner.nextLine() + ")");
        scanner.close();
    }
}
