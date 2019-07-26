package com.czareg;

import com.czareg.utils.ThumbprintMaker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ThumbprintFixerUI extends Application {

	@Override
	public void start(Stage stage) {
		HBox controlsHBox = createControls();
		StackPane stackPane = new StackPane(controlsHBox);
		Scene scene = new Scene(stackPane, 540, 50);
		stage.setTitle("Thumbprint Fixer");
		stage.setScene(scene);
		stage.getIcons().add(new Image("icon.png"));
		stage.show();
	}

	private HBox createControls() {
		final TextField textField = new TextField();
		textField.setMinWidth(400.0);
		textField.setTooltip(new Tooltip("Enter thumbprint without colons."));
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		if (clipboard.hasString()) {
			textField.setText(clipboard.getString());
		} else {
			textField.setPromptText("Enter thumbprint here.");
			textField.requestFocus();
		}

		Button btn = new Button("Fix thumbprint");
		btn.setOnAction(createAction(textField));
		HBox hb = new HBox();
		hb.getChildren().addAll(textField, btn);
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private EventHandler<ActionEvent> createAction(final TextField textField) {
		return new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!textField.getText().isBlank()) {
					textField.setText(ThumbprintMaker.make(textField.getText()));
				}
			}
		};
	}

	public static void main(String[] args) {
		launch();
	}
}