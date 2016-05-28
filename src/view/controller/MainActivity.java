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
	@FXML
	private AnchorPane window;
	@FXML
	private JFXButton playButton;
	@FXML
	private Label nameLabel;
	@FXML
	private JFXProgressBar progressBar;
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

	@Override
	protected void openFile(@NotNull File file) {
		propertiesList.getItems().remove(0, propertiesList.getItems().size());
		super.openFile(file);
	}

	@FXML
	void openHelp(ActionEvent event) {
		openGitHub();
	}

	@Override
	public DecoderInterface getDekoder() {
		return dekoder;
	}

	@Override
	public void setDekoder(DecoderInterface decoderInterface) {
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
		init();
	}

	@Override
	public void setTitleText(@NotNull String string) {
		nameLabel.setText(string);
	}
}
