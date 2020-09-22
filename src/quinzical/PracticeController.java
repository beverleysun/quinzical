package quinzical;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PracticeController extends PlayController{

    private final Database _database = Database.getInstance();
    private final List<Category> _practiceQuestionData = _database.getPracticeQuestionData();


    @FXML
    private FlowPane categoryFlowPane;
    Button categoryButton;
    List<Button> categorizations;

    public void initialize() throws IOException {
        Parent answer = FXMLLoader.load(getClass().getResource("QuitPrompt.fxml"));
        categorizations = new ArrayList<>();
        for (Category category : _practiceQuestionData ) {

            categoryButton = new Button(category.getCategoryName());
            categoryButton.setOnMouseClicked(new SceneChanger(answer));


            categoryButton.getStyleClass().add("purple-button");
            categoryButton.getStyleClass().add("white-text-fill");

            categorizations.add(categoryButton);
            categoryFlowPane.getChildren().add(categoryButton);
        }
    }

}
