package demo.test.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private ComboBox<String> fontSizeComboBox;

    private TimerController timerController;

    @FXML
    private TextField minutesTextField;

    @FXML
    private TextField breakMinutesField;

    public void setTimerController(TimerController timerController) {
        this.timerController = timerController;
    }

    @FXML
    private void initialize() {
        fontSizeComboBox.getItems().addAll("14", "18", "22", "26", "30");
    }

    @FXML
    private void applySettings() {
        String selectedSize = fontSizeComboBox.getValue();
        if (selectedSize != null && timerController != null) {
            timerController.setFontSize(Integer.parseInt(selectedSize));
        }
        Stage stage = (Stage) fontSizeComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void applyTimerSettings() {
        try {

            int minutes = Integer.parseInt(minutesTextField.getText());
            timerController.setTimerSettings(minutes);
        } catch (NumberFormatException e) {
            // Handle invalid input, e.g., show an error message
            System.out.println("Invalid input for minutes");
        }
    }

    @FXML
    private void applyBreakSettings() {
        try {
            int minutes = Integer.parseInt(breakMinutesField.getText());
            timerController.setBreakSettings(minutes);
        } catch (NumberFormatException e) {
            // Handle invalid input, e.g., show an error message
            System.out.println("Invalid input for minutes");
        }
    }
}
