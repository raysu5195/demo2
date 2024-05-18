package demo.test.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.IOException;

public class TimerController {

    @FXML
    private Button startButton;

    @FXML
    private ComboBox<String> timeComboBox;

    private int workTimeSeconds = 25 * 60; // Default work time: 25 minutes
    private int breakTimeSeconds = 5 * 60; // Default break time: 5 minutes
    private int currentTimeSeconds = workTimeSeconds;
    private boolean isWorking = true;
    private boolean isCounting = false;
    private Timeline timeline;

    @FXML
    private Label timerText;

    @FXML
    protected void start() {
        startTimer();
    }

    @FXML
    private void updateTimeSettings() {
        String selectedTime = timeComboBox.getValue();
        if (selectedTime.equals("50 minutes")) {
            workTimeSeconds = 50 * 60;
            currentTimeSeconds = workTimeSeconds;
        } else {
            workTimeSeconds = 25 * 60; // Default work time: 25 minutes
            currentTimeSeconds = workTimeSeconds;
        }
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        int minutes = currentTimeSeconds / 60;
        int remainingSeconds = currentTimeSeconds % 60;
        timerText.setText(String.format("%02d:%02d", minutes, remainingSeconds));
    }

    @FXML
    private void startTimer() {
        if (isCounting){
            return;
        }
        else{
            isCounting = true;
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            currentTimeSeconds--;
            if (currentTimeSeconds <= 0) {
                switchTimer();

            }
            updateTimerLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void resetTimer() {
        if (timeline != null) {
            isCounting = false;
            timeline.stop();
        }
        currentTimeSeconds = workTimeSeconds;
        isWorking = true;
        updateTimerLabel();
        startButton.setText("Start");
    }

    private void switchTimer() {
        if (isWorking) {
            currentTimeSeconds = breakTimeSeconds;
        } else {
            currentTimeSeconds = workTimeSeconds;
        }
        isWorking = !isWorking;
    }

    @FXML
    private void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Settings");

            SettingsController controller = fxmlLoader.getController();
            controller.setTimerController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFontSize(int size) {
        timerText.setFont(new Font(size));
    }

    public void setTimerSettings(int minutes) {
        this.currentTimeSeconds = minutes * 60;
        this.workTimeSeconds = minutes * 60;
        updateTimerLabel();
    }

    public void setBreakSettings(int minutes) {
        this.breakTimeSeconds = minutes * 60;
        updateTimerLabel();
    }
}
