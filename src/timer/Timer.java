package timer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 *
 * @author guillermogallegogonzalez
 */
public class Timer extends HBox implements Initializable {

    private int STARTTIME = 0;
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    @FXML
    private Label timerLabel;
    @FXML
    private Button button;

    public Timer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Timer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void updateTime() {
        int seconds = timeSeconds.get();
        Alert a = new Alert(AlertType.INFORMATION);
        if(seconds == 1){
            a.setTitle("Temporizador Finalizado");
            a.setContentText("El temporizador ha llegado a su fin");
            a.show();
            button.setDisable(false);
        }
        timeSeconds.set(seconds - 1);
    }

    public void setTime(int time){
        this.STARTTIME  = time;
    }
    
    public void handle(ActionEvent event) {
        button.setDisable(true); // Deshabilitamos el botón para evitar errores
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime()));
        timeline.setCycleCount(STARTTIME); // Creamos un blucle
        timeSeconds.set(STARTTIME);
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timerLabel.textProperty().bind(timeSeconds.asString());
    }

}
