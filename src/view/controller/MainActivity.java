package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import decoder.DecoderInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import utils.Echoer;

import java.io.File;

public class MainActivity extends MainActivityFramework {
    @FXML
    private JFXListView<Object> propertiesList;
    //    @FXML private JFXButton fileButton;
    //    @FXML private JFXButton helpButton;
    @FXML
    private AnchorPane window;
    @FXML
    private JFXButton playButton;
    @FXML
    private Label nameLabel;
    @FXML
    private JFXProgressBar progressBar;
    //    @FXML private JFXRadioButton listOption;
    //    @FXML private JFXRadioButton dataOption;
    private DecoderInterface dekoder;

    @FXML
    protected void playMusic(ActionEvent event) {
        super.playMusic();
    }

    @FXML
    void openFile(ActionEvent event) {
        try {
            openFile(getChooser().showOpenDialog(window.getScene().getWindow()));
        } catch (Exception ignored) {
        }
    }

    private void openFile(File file) {
        propertiesList.getItems().remove(0, propertiesList.getItems().size());
        openFile(file.getPath());
        nameLabel.setText(file.getName());
    }

    @FXML
    void openHelp(ActionEvent event) {
        openGitHub();
    }

    @NotNull
    @Override
    public DecoderInterface getDekoder() {
        return dekoder;
    }

    @Override
    public void setDekoder(@NotNull DecoderInterface decoderInterface) {
        dekoder = decoderInterface;
    }

    @NotNull
    @Override
    public JFXButton getPlayButton() {
        return playButton;
    }

    @Override
    public void setProgress(double i) {
        progressBar.setProgress(i / 1000 / dekoder.getTotalTime());
    }

    @NotNull
    @Override
    public Echoer printer() {
        return msg -> propertiesList.getItems().add(msg);
    }

    @FXML
    void initialize() {
        try {
            openFile(new File(getManager().read().get(0)));
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
