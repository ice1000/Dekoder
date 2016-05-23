package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import data.DatabaseManager;
import decoder.MP3Decoder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import decoder.WAVDecoder;
import decoder.DecoderInterface;
import utils.Echoer;

import java.io.File;

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
	private File file;
	private DecoderInterface dekoder;
	private DatabaseManager manager;

	@FXML
	@Override
	protected void playMusic(@NotNull ActionEvent event) {
		super.playMusic(event);
	}

	@FXML
	void openFile(ActionEvent event) {
		file = getChooser().showOpenDialog(window.getScene().getWindow());
		if(manager == null)
			manager = new DatabaseManager();
		manager.write(file.getPath());
		dekoder = choose(file.getPath());
		if(dekoder == null)
			return;
		dekoder.init();
		nameLabel.setText(file.getName());
		propertiesList.getItems().removeAll();
	}

	@FXML
	void openHelp(ActionEvent event) {
		openGitHub();
	}

	private DecoderInterface choose(String filePath) {
		Printer p = new Printer();
		if(filePath.endsWith("wav"))
			return new WAVDecoder(filePath, p);
		else if(filePath.endsWith("mp3"))
			return new MP3Decoder(filePath, p);
		else
			return null;
	}

	@NotNull
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

	private class Printer extends Echoer {
		@Override
		public void echo(@NotNull String msg) {
			propertiesList.getItems().add(msg);
		}
	}
}
