package quinzical.controllers.practice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import quinzical.TTS;

public class CorrectController extends ConfirmController {

    @FXML
    private Button next;
    @FXML
    private Label correctLabel;

    /**
     * Initializes the scene
     */
    @FXML
    public void initialize() {
        super.initialize();
        TTS.getInstance().speak("Correct!");
    }

    /**
     * This method is invoked when the user click the next button in correct scene.
     * It will switch to the question selection interface.
     * @param e the event that was triggered
     */
    @FXML
    private void backToQuestion(MouseEvent e) {
        backToPractice(e);
    }
}