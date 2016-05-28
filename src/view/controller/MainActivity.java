package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import decoder.DecoderInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	private JFXProgressBar progressBar;
	@FXML
	private JFXListView<Object> filesList;
	private DecoderInterface dekoder;

	@FXML
	protected void playMusic(ActionEvent event) {
		super.playMusic();
	}

	@FXML
	void openFile(ActionEvent event) {
		openFile();
	}

	@Override
	protected void openFile() {
		openFile(getChooser().showOpenDialog(window.getScene().getWindow()));
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
	public void setProgress(long i) {
		progressBar.setProgress(i / 1000.0 / dekoder.getTotalTime());
	}

	@NotNull
	@Override
	public Echoer propertiesPrinter() {
		return msg -> propertiesList.getItems().add(msg);
	}

	@NotNull
	@Override
	public Echoer filesPrinter() {
		return msg -> filesList.getItems().add(msg);
	}

	@Override
	@FXML
	protected void initialize() {
		super.initialize();
	}

	@FXML
	protected void stopPlaying(ActionEvent event) {
		super.stopPlaying();
	}
}
