package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ArxivOrgControllerTest {

    Archive archive = Archive.archiveFile2;
    int numberOfArticles = archive.getAllArticles().size();



    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().
                getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }
/*
    @Test
    public void displayGUITest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();
        ChoiceBox<String> categories = robot.lookup("#categories").query();
        DatePicker period = robot.lookup("#period").query();
        int nbOfCategories = archive.getPossibleCategories().size();

        Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");
        Assertions.assertThat(listView.getSelectionModel().getSelectionMode()).isEqualTo(SelectionMode.MULTIPLE);
        Assertions.assertThat(categories.getValue()).isEqualTo(" All categories");
        Assertions.assertThat(categories.getItems().size()).isEqualTo(nbOfCategories);
        Assertions.assertThat(period.getValue()).isEqualTo(LocalDate.now().minusYears(50));
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);
    }



    @Test
    public void displayArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        Button results = robot.lookup("#results").queryButton();
        String selectedArticle1_info = archive.getSelectedArticle(0).mainInformations();
        String selectedArticle2_info = archive.getSelectedArticle(1).mainInformations();
        String selectedArticle7_info = archive.getSelectedArticle(7).mainInformations();

        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);

        archive.getArticle(7).setSelected(false);
        archive.getArticle(34).setSelected(false);
        robot.clickOn(results);

        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles - 2);
        Assertions.assertThat(listView.getItems().get(0)).isEqualTo(selectedArticle1_info);
        Assertions.assertThat(listView.getItems().get(1)).isEqualTo(selectedArticle2_info);
        Assertions.assertThat(listView.getItems().get(7)).isNotEqualTo(selectedArticle7_info);

        archive.selectAll();
        robot.clickOn(results);
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);

    }

/*
    @Test
    public void displayMetadataTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();
        CheckBox favorite = robot.lookup("#favorite").query();


        listView.getSelectionModel().select(12);
        Assertions.assertThat(listView.getSelectionModel().getSelectedIndex()).isEqualTo(12);
        Assertions.assertThat(metadata).hasText(archive.getArticle(12).toString());
        Assertions.assertThat(favorite.isIndeterminate()).isNotEqualTo(archive.getArticle(12).isSelected());

        listView.getSelectionModel().select(0);
        Assertions.assertThat(listView.getSelectionModel().getSelectedIndex()).isEqualTo(0);
        Assertions.assertThat(metadata).hasText(archive.getArticle(0).toString());
        //robot.clickOn(favorite);
        Assertions.assertThat(favorite.isIndeterminate()).isNotEqualTo(archive.getArticle(0).isSelected());

    }


    @Test
    public void updateFavoriteItemTest(){

    }


    @Test
    public void downloadArticleTest(){

    }


    @Test
    public void applyFilterTest(FxRobot robot){
        TextArea metadata = robot.lookup("#metadata").query();
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea keywords = robot.lookup("#keywords").query();
        Button results = robot.lookup("#results").queryButton();

        keywords.setText("void");
        //robot.clickOn(results);

        List<Article> articleSelected = new ArrayList<>();
        for(Article article: archive.getAllArticles()){
            if(article.isSelected()){
                articleSelected.add(article);
            }
        }
        Assertions.assertThat(listView).hasExactlyNumItems(articleSelected.size());
        Assertions.assertThat(listView.getItems().get(0)).
                isEqualTo(articleSelected.get(0).mainInformations());
        Assertions.assertThat(listView.getItems().get(articleSelected.size()-1)).
                isEqualTo(articleSelected.get(articleSelected.size()-1).mainInformations());
        //Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");

        keywords.clear();
        //robot.clickOn(results);
    }

 */






}