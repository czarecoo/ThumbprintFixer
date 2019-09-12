package com.czareg;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czareg.utils.PropertiesHandler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class ThumbprintFixerUI extends Application {
	static final Logger LOG = LoggerFactory.getLogger(ThumbprintFixerUI.class);
	private static final String ICON_FILENAME = "icon.png";
	private static final String APPLICATION_TITLE = "Thumbprint Fixer";
	private static final String BUTTON_TEXT = "Fix Thumbprint";
	@FXML
	private TextField textField;
	@FXML
	private Button fixButton;
	@FXML
	private CheckBox copyCheckbox;

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
		Scene scene = new Scene(root, 540, 60);
		stage.setTitle(APPLICATION_TITLE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(ICON_FILENAME));
		LOG.info("Started UI");
		stage.show();
	}

	@FXML
	private void initialize() {
		configureTextfield();
		innitializeFixButtonOnClickAction();
		copyCheckbox.setSelected(PropertiesHandler.readCopyToClipboard());
		copyCheckbox.selectedProperty()
				.addListener((observable, oldValue, newValue) -> PropertiesHandler.writeCopyToClipboard(newValue));
	}

	private void innitializeFixButtonOnClickAction() {
		fixButton.setOnAction((ActionEvent e) -> {
			if (!textField.getText().isEmpty()) {
				makeThumbprint(textField.getText());
			}
			textField.requestFocus();
		});
	}

	private void configureTextfield() {
		String defaultUrl = PropertiesHandler.readDefaultUrl();
		if (defaultUrl != null) {
			textField.setText(defaultUrl);
		}
	}

	private void makeThumbprint(String userInput) {
		CreateThumbprintTask task = new CreateThumbprintTask(userInput);

		task.setOnRunning(succeesesEvent -> {
			fixButton.setDisable(true);
			fixButton.setText("Processing.");
			LOG.info("Creating thumbprint from user input: {}", userInput);
		});

		task.setOnSucceeded(succeededEvent -> {
			fixButton.setDisable(false);
			fixButton.setText(BUTTON_TEXT);
			String thumbprint = task.getValue();
			textField.setText(thumbprint);
			LOG.info("Setting created thumbprint: {} to textfield", thumbprint);
			PropertiesHandler.writeDefaultUrl(userInput);
			if (copyCheckbox.isSelected()) {
				copyResultToClipboard(thumbprint);
			}
		});

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.execute(task);
		executorService.shutdown();
	}

	private void copyResultToClipboard(String thumbprint) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(thumbprint);
		clipboard.setContent(content);
	}

	public static void main() {
		launch(ThumbprintFixerUI.class);
	}
}