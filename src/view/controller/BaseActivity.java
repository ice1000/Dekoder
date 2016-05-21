package view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import utils.MainDecoder;

/**
 *  @author ice1000
 * Created by asus1 on 2016/5/21.
 */

public class BaseActivity {
	@FXML
	protected JFXListView<Object> propertiesList;
	@FXML
	protected JFXButton fileButton;
	@FXML
	protected JFXButton helpButton;
	@FXML
	protected AnchorPane window;
	@FXML
	protected JFXButton playButton;

	class Dekoder extends MainDecoder {

		public Dekoder(String file) {
			super(file);
		}

		@Override
		public void echo(@NotNull String msg) {
			propertiesList.getItems().add(msg);
		}
	}
}
