package demo.test.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

public class SettingsController {

    @FXML
    private ComboBox<String> fontSizeComboBox;

    private TimerController timerController;

    @FXML
    private TextField minutesTextField;

    @FXML
    private TextField breakMinutesField;

    @FXML
    private CheckBox autoStartPomodorosCheckBox;
    @FXML
    private CheckBox autoStartBreaksCheckBox;

    private Preferences preferences;

    public SettingsController() {
        preferences = Preferences.userNodeForPackage(SettingsController.class);
    }

    public void setTimerController(TimerController timerController) {
        this.timerController = timerController;
        loadSettings();
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
            System.out.println("Invalid input for minutes");
        }
    }

    @FXML
    private void applyBreakSettings() {
        try {
            int minutes = Integer.parseInt(breakMinutesField.getText());
            timerController.setBreakSettings(minutes);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for minutes");
        }
    }

    @FXML
    private void applyAutoStartSettings() {
        if (timerController != null) {
            timerController.setAutoStartPomodoros(autoStartPomodorosCheckBox.isSelected());
            timerController.setAutoStartBreaks(autoStartBreaksCheckBox.isSelected());
            saveSettings();
        }
    }

    private void saveSettings() {
        Preferences prefs = Preferences.userNodeForPackage(SettingsController.class);
        prefs.put("fontSize", fontSizeComboBox.getValue());
        prefs.putInt("minutes", Integer.parseInt(minutesTextField.getText()));
        prefs.putInt("breakMinutes", Integer.parseInt(breakMinutesField.getText()));
        prefs.putBoolean("autoStartPomodoros", autoStartPomodorosCheckBox.isSelected());
        prefs.putBoolean("autoStartBreaks", autoStartBreaksCheckBox.isSelected());
    }

    private void loadSettings() {
        Preferences prefs = Preferences.userNodeForPackage(SettingsController.class);
        fontSizeComboBox.setValue(prefs.get("fontSize", "14"));
        minutesTextField.setText(String.valueOf(prefs.getInt("minutes", 25)));
        breakMinutesField.setText(String.valueOf(prefs.getInt("breakMinutes", 5)));
        autoStartPomodorosCheckBox.setSelected(prefs.getBoolean("autoStartPomodoros", false));
        autoStartBreaksCheckBox.setSelected(prefs.getBoolean("autoStartBreaks", false));
    }

    @FXML
    private void applyAllSettings() {
        applySettings();
        applyTimerSettings();
        applyBreakSettings();
        applyAutoStartSettings();
    }
}