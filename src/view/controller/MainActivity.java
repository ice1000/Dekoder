package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import decoder.WAVDecoder;
import utils.DecoderInterface;

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
	private File file;
	private DecoderInterface dekoder;

	@FXML
	void playMusic(ActionEvent event) {
		if (dekoder != null) {
			dekoder.play();
		}
	}

	@FXML
	void openFile(ActionEvent event) {
		file = getChooser().showOpenDialog(window.getScene().getWindow());
//		dekoder = choose(file.getPath());
		dekoder = new Dekoder(file.getPath());
		nameLabel.setText(file.getName());
		propertiesList.getItems().removeAll();
	}

	@FXML
	void openHelp(ActionEvent event) {
		openGitHub();
	}

	private DecoderInterface choose(String filePath) {
		String type = filePath.split(File.separator)[filePath.split(File.separator).length - 1];
		switch (type) {
			case "wav":
				return new Dekoder(filePath);
			default:
				return null;
		}
	}

	private class Dekoder extends WAVDecoder {
		private Dekoder(String file) {
			super(file);
		}

		@Override
		public void echo(@NotNull String msg) {
			propertiesList.getItems().add(msg);
		}
	}
}
