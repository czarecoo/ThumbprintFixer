package com.czareg;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.czareg.model.StringFormat;
import com.czareg.utils.ThumbprintMaker;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class ThumbprintFixerUI extends Application {

	private static final String ICON_FILENAME = "icon.png";
	private static final String APPLICATION_TITLE = "Thumbprint Fixer";
	private static final String BUTTON_TEXT = "Fix Thumbprint";
	@FXML
	private TextField textField;
	@FXML
	private Button fixButton;
	@FXML
	private Button settingsButton;
	@FXML
	private ToggleGroup group;
	@FXML
	private RadioButton radioButtonUppercase;
	@FXML
	private RadioButton radioButtonLowercase;
	@FXML
	private RadioButton radioButtonMixedcase;

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
		Scene scene = new Scene(root, 580, 60);
		stage.setTitle(APPLICATION_TITLE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(ICON_FILENAME));
		stage.show();
	}

	@FXML
	private void initialize() {
		configureTextfield();
		innitializeGroupListener();
		innitializeFixButtonOnClickAction();
		innitializeSettingsButtonOnClickAction();
	}

	private void innitializeSettingsButtonOnClickAction() {
		settingsButton.setOnAction((ActionEvent e) -> {
			new ProxyDialogUI().open();
		});
	}

	private void innitializeGroupListener() {
		radioButtonUppercase.setUserData(StringFormat.UPPERCASE);
		radioButtonLowercase.setUserData(StringFormat.LOWERCASE);
		radioButtonMixedcase.setUserData(StringFormat.MIXEDCASE);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (newValue.getUserData() != null) {
					ThumbprintMaker.format = (StringFormat) newValue.getUserData();
					if (textField != null) {
						textField.setText(ThumbprintMaker.applyCurrentFormat(textField.getText()));
					}
				}
			}
		});
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
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		if (clipboard.hasString()) {
			textField.setText(clipboard.getString());
		}
	}

	private void makeThumbprint(String userInput) {
		CreateThumbprintTask task = new CreateThumbprintTask(userInput);

		task.setOnRunning((succeesesEvent) -> {
			fixButton.setDisable(true);
			fixButton.setText("Processing.");
		});

		task.setOnSucceeded((succeededEvent) -> {
			fixButton.setDisable(false);
			fixButton.setText(BUTTON_TEXT);

			String thumbprint = task.getValue();
			textField.setText(thumbprint);
			copyResultToClipboard(thumbprint);
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