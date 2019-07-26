package com.czareg;

import com.czareg.model.StringFormat;
import com.czareg.utils.ThumbprintMaker;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThumbprintFixerUI extends Application {

	private static final String UPPERCASE = "Uppercase";
	private static final String LOWERCASE = "Lowercase";
	private static final String MIXED = "Mixed";
	private static final String LABEL_TEXT = "Enter thumbprint without colons.";
	private static final String TEXTFIELD_PROPMPT_TEXT = "Enter thumbprint here.";
	private static final String BUTTON_TEXT = "Fix thumbprint";
	private static final String ICON_FILENAME = "icon.png";
	private static final String APPLICATION_TITLE = "Thumbprint Fixer";
	private TextField textField;

	@Override
	public void start(Stage stage) {
		HBox controlsHBox = createTextFieldAndButtonHBox();
		HBox radioBtnsHBox = createRadioHBox();
		VBox vbox = new VBox();
		vbox.getChildren().addAll(controlsHBox, radioBtnsHBox);
		vbox.setAlignment(Pos.CENTER);
		StackPane stackPane = new StackPane(vbox);
		Scene scene = new Scene(stackPane, 540, 80);
		stage.setTitle(APPLICATION_TITLE);
		stage.setScene(scene);
		stage.getIcons().add(new Image(ICON_FILENAME));
		stage.show();
	}

	private HBox createRadioHBox() {
		final ToggleGroup group = new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (newValue.getUserData() != null) {
					ThumbprintMaker.format = (StringFormat) newValue.getUserData();
					if (textField != null) {
						textField.setText(ThumbprintMaker.applyCurrentFormat(textField.getText()));
					}
				}
			}
		});
		RadioButton rb1 = new RadioButton(UPPERCASE);
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb1.setUserData(StringFormat.UPPERCASE);
		RadioButton rb2 = new RadioButton(LOWERCASE);
		rb2.setToggleGroup(group);
		rb2.setUserData(StringFormat.LOWERCASE);
		RadioButton rb3 = new RadioButton(MIXED);
		rb3.setToggleGroup(group);
		rb3.setUserData(StringFormat.MIXED);
		HBox hb = new HBox();
		hb.getChildren().addAll(rb1, rb2, rb3);
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private HBox createTextFieldAndButtonHBox() {
		textField = new TextField();
		configureTextfield(textField);
		Button fixButton = new Button(BUTTON_TEXT);
		fixButton.setOnAction(createAction(textField));
		HBox hb = new HBox();
		hb.getChildren().addAll(textField, fixButton);
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private void configureTextfield(final TextField textField) {
		textField.setMinWidth(400.0);
		textField.setTooltip(new Tooltip(LABEL_TEXT));
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		if (clipboard.hasString()) {
			textField.setText(clipboard.getString());
		} else {
			textField.setPromptText(TEXTFIELD_PROPMPT_TEXT);
		}
	}

	private EventHandler<ActionEvent> createAction(final TextField textField) {
		return new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!textField.getText().isBlank()) {
					String thumbprint = ThumbprintMaker.make(textField.getText());
					textField.setText(thumbprint);
					final Clipboard clipboard = Clipboard.getSystemClipboard();
					final ClipboardContent content = new ClipboardContent();
					content.putString(thumbprint);
					clipboard.setContent(content);
				}
				textField.requestFocus();
			}
		};
	}

	public static void main() {
		launch();
	}
}