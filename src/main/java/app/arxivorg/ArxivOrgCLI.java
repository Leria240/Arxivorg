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
        System.out.println("Welcome to the arXiv organizer! Type '-h' if you need help and 'close' to end the program");
        System.out.println("Input your command: ");
        parseOptions();
    }

    public static void parseOptions() {
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().split(" ");
        //System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        final Option categoryOption = Option.builder("c")
                .longOpt("category")
                .hasArg(true)
                .desc("sort by category")
                .required(false)
                .build();

        final Option keywordOption = Option.builder("k")
                .longOpt("keyword")
                .hasArg(true)
                .desc("sort by keyword")
                .required(false)
                .build();

        final Option authorOption = Option.builder("a")
                .longOpt("author")
                .hasArg(true)
                .desc("sort by author(s)")
                .required(false)
                .build();

        final Option periodOption = Option.builder("p")
                .longOpt("period")
                .hasArg(true)
                .desc("sort by period")
                .required(false)
                .build();

        final Option helpOption = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .required(false)
                .build();

        final Options options = new Options();
        options.addOption(periodOption);
        options.addOption(categoryOption);
        options.addOption(keywordOption);
        options.addOption(authorOption);
        options.addOption(helpOption);

        final CommandLineParser parser = new DefaultParser();

        try {
            String category = "";
            String keyword = "";
            String authors ="";

            final CommandLine line = parser.parse(options, args);
            Archive archive = new Archive();

            if (line.hasOption("h")) {
                System.out.println("Welcome to the help menu! Please type the command or option you need help with.");
                System.out.println("Commands: 'download', 'list'");
                System.out.println("Options: '-a', '-p', '-k', '-c'");
                System.out.println("If you want to end this program, type 'close'");
                String input = scanner.nextLine();

                if (input.equals("download")) {
                    System.out.println("This command downloads articles by using arxiv's API. Example: download -c cs.CL to C:\\Users\\YourUser\\Desktop" + "\n");
                }

                if (input.equals("list")) {
                    System.out.println("This command lists articles by using arxiv's API.  Example: list -a Ronen,Tamari -p 2020-01-01" + "\n");
                }

                if (input.equals("-a")) {
                    System.out.println("This option filters articles by author's names. This option needs an argument. Author names must be separated by commas and not spaces Example: list -a Ronen,Tamari" + "\n");
                }

                if (input.equals("-c")) {
                    System.out.println("This option filters articles by category. Example: list -c cs.CL" + "\n");
                }

                if (input.equals("-p")) {
                    System.out.println("This option filters articles by date. Enter a date to get articles published after this date. Example: list -c cs.CL -p 2015-01-01" + "\n");
                }

                if (input.equals("-k")) {
                    System.out.println("This option filters articles by keyword in the title and summary. Example: list -k electron" + "\n");
                }

                if (input.equals("close")){
                    System.exit(0);
                }
            }

            if (line.hasOption("c")) {
                category = line.getOptionValue("c");
            }

            if (line.hasOption("k")) {
                keyword = line.getOptionValue("k");
            }

            if (line.hasOption("a")) {
                authors = line.getOptionValue("a");
            }

            archive.filters(category,authors,keyword,keyword);

            if (line.hasOption("p")) {
                final String dateString = line.getOptionValue("p");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateString,formatter);
                archive.dateFilter(date);
            }

            if (args[0].equals("list")) {
                for (int i = 0; i < archive.getAllArticles().size(); i++) {
                    System.out.println(i + 1 + ". " + archive.getArticle(i).getTitle());
                    System.out.println("Authors: " + archive.getArticle(i).getAuthors().getData().toString()
                            .replaceAll("\\[", "")
                            .replaceAll("]", ""));
                }
                System.out.println();
            }

            if(args[0].equals("download")){
                //launch(ArxivOrgCLI.class);
                String path = archive.downloadArticlesCLI(archive.getAllArticles(), args[args.length-1]);
                System.out.println("Downloaded " + archive.getAllArticles().size() + " files to " + path);
            }

            if(args[0].equals("close")){
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        parseOptions();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}