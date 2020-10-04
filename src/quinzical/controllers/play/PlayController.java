package quinzical.controllers.play;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import quinzical.Category;
import quinzical.Database;
import quinzical.Question;
import quinzical.SceneChanger;
import quinzical.controllers.VoiceSettingsChangeable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayController extends VoiceSettingsChangeable {

    @FXML
    private Label cat1Label, cat2Label, cat3Label, cat4Label, cat5Label, winnings;

    @FXML
    private Button cat1value100Button, cat1value200Button, cat1value300Button, cat1value400Button, cat1value500Button,
                    cat2value100Button, cat2value200Button, cat2value300Button, cat2value400Button, cat2value500Button,
                    cat3value100Button, cat3value200Button, cat3value300Button, cat3value400Button, cat3value500Button,
                    cat4value100Button, cat4value200Button, cat4value300Button, cat4value400Button, cat4value500Button,
                    cat5value100Button, cat5value200Button, cat5value300Button, cat5value400Button, cat5value500Button ;

    private final Database _database = Database.getInstance();
    private final List<Category> _questionData = _database.getQuestionData();
    private final List<Button> _buttons = new ArrayList<Button>();

    /**
     * Initialize the question board scene
     */
    @FXML
    public void initialize() {
        super.initialize();
        setLabels();
        initButtons();
        winnings.setText("$" + Database.getInstance().getWinnings());
    }

    /**
     * Display the category names
     */
    private void setLabels() {
        cat1Label.setText(_questionData.get(0).getCategoryName());
        cat2Label.setText(_questionData.get(1).getCategoryName());
        cat3Label.setText(_questionData.get(2).getCategoryName());
        cat4Label.setText(_questionData.get(3).getCategoryName());
        cat5Label.setText(_questionData.get(4).getCategoryName());
    }

    /**
     * Set each button to a specified question and disable if the question is not available
     */
    private void initButtons() {
        Collections.addAll(_buttons, cat1value100Button, cat1value200Button, cat1value300Button, cat1value400Button, cat1value500Button,
                cat2value100Button, cat2value200Button, cat2value300Button, cat2value400Button, cat2value500Button,
                cat3value100Button, cat3value200Button, cat3value300Button, cat3value400Button, cat3value500Button,
                cat4value100Button, cat4value200Button, cat4value300Button, cat4value400Button, cat4value500Button,
                cat5value100Button, cat5value200Button, cat5value300Button, cat5value400Button, cat5value500Button);
        for (Button button: _buttons) {
            int[] parsed = parseButtonID(button);

            Question questionToAsk = _questionData.get(parsed[0]-1).findQuestionByValue(parsed[1]);

            if (!questionToAsk.isAvailable()) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }
        }
    }

    /**
     * Invoked when someone clicks a question button. Asks the question associated with the button and
     * switches to that scene
     * @param e the source of the button click
     */
    public void askQuestion(MouseEvent e) {
        Button buttonSource = (Button) e.getSource();
        int[] parsed = parseButtonID(buttonSource);

        // Get all question data
        Category category = _questionData.get(parsed[0]-1);
        Question question = category.findQuestionByValue(parsed[1]);

        // Save data
        question.setCompleted(true);
        question.setAvailable(false);
        Database.getInstance().addCompletedFile(category.getCategoryName(), question.getValueString());

        // Set next question available
        if (parsed[1] != 500) {
            int nextValue = parsed[1]+100;
            category.findQuestionByValue(nextValue).setAvailable(true);
        }

        try {
            // Initialise controller with specific question and answer fields
            AskQuestionController controller = new AskQuestionController(question, category.getCategoryName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../scenes/play/AskQuestion.fxml"));
            loader.setController(controller);

            // Change scene
            Parent askQuestion = loader.load();
            SceneChanger.changeScene(e, askQuestion);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Get the category number and question value from the ID of a button
     * @param button the button to be parsed
     * @return category and question value in an array
     */
    private int[] parseButtonID(Button button) {
        int category = Integer.parseInt(button.getId().split("-")[0]);
        int value = Integer.parseInt(button.getId().split("-")[1]);
        return new int[]{category, value};
    }

    /**
     * Invoked when the back arrow is clicked. Goes back to start page
     * @param e source of button click
     */
    public void back(MouseEvent e) {
        try {
            // Load start page scene
            Parent startPage = FXMLLoader.load(getClass().getResource("../../scenes/StartPage.fxml"));
            SceneChanger.changeScene(e, startPage);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Prompts user if they want to reset the game when the button is clicked
     * @param e source of the button click
     */
    public void reset(MouseEvent e) {
        try {
            // Load reset prompt page scene
            Parent startPage = FXMLLoader.load(getClass().getResource("../../scenes/play/ResetPrompt.fxml"));
            SceneChanger.changeScene(e, startPage);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
