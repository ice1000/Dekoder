package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import decoder.DecoderInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import utils.Echoer;

import java.io.File;
import java.util.ArrayList;

/**
 * @author ice1000
 *         java interface of javafx
 *         Created by asus1 on 2016/5/21.
 */

public class MainActivity extends MainActivityFramework {
    @FXML
    private JFXListView<Object> propertiesList;
    @FXML
    private JFXButton fileButton;
    @FXML
    private JFXButton helpButton;
    @FXML
    private AnchorPane window;
    @FXML
    private JFXButton playButton;
    @FXML
    private Label nameLabel;
    @FXML
    private JFXRadioButton listOption;
    @FXML
    private JFXRadioButton dataOption;
    private DecoderInterface dekoder;

    @FXML
    protected void playMusic(ActionEvent event) {
        super.playMusic();
    }

    @FXML
    void openFile(ActionEvent event) {
        openFile(getChooser().showOpenDialog(window.getScene().getWindow()));
    }

    private void openFile(File file) {
        openFile(file.getPath());
        nameLabel.setText(file.getName());
        propertiesList.getItems().removeAll();
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

    private class Printer implements Echoer {
        @Override
        public void echo(@NotNull String msg) {
            propertiesList.getItems().add(msg);
        }
    }

    @NotNull
    @Override
    public Echoer printer() {
        return new Printer();
    }

    @FXML
    void initialize() {
        ArrayList<String> s = getManager().read();
        try {
            openFile(new File(s.get(s.size() - 1)));
            s.clear();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
