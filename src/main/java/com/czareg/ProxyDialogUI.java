package com.czareg;

import java.io.IOException;
import java.util.Optional;

import com.czareg.model.ProxyData;
import com.czareg.utils.PropertiesHandler;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ProxyDialogUI {

	private static final String ICON_FILENAME = "cogwheel.png";

	public void open() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		setDialogWindowProperties(dialog);
		TextField serverTextField = new TextField();
		serverTextField.requestFocus();
		TextField portTextField = new TextField();
		setFieldsValues(serverTextField, portTextField);
		GridPane grid = createGrid(serverTextField, portTextField);
		dialog.getDialogPane().setContent(grid);

		setResultConverter(dialog, serverTextField, portTextField);

		Optional<Pair<String, String>> result = dialog.showAndWait();

		processResult(result);
	}

	private void setFieldsValues(TextField serverTextField, TextField portTextField) {
		ProxyData proxyData;
		try {
			proxyData = PropertiesHandler.readProxyData();
			serverTextField.setText(proxyData.getServer());
			portTextField.setText(String.valueOf(proxyData.getPort()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processResult(Optional<Pair<String, String>> result) {
		result.ifPresent(pair -> {
			try {
				ProxyData proxyData = new ProxyData(pair.getKey(), pair.getValue());
				PropertiesHandler.writeProxyData(proxyData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void setResultConverter(Dialog<Pair<String, String>> dialog, TextField serverTextField,
			TextField portTextField) {
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return new Pair<>(serverTextField.getText(), portTextField.getText());
			}
			return null;
		});
	}

	private void setDialogWindowProperties(Dialog<Pair<String, String>> dialog) {
		setIcon(dialog);
		dialog.setTitle("Proxy Settings");
		dialog.setHeaderText("Enter your proxy server and port.");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
	}

	private void setIcon(Dialog<Pair<String, String>> dialog) {
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(ICON_FILENAME));
	}

	private GridPane createGrid(TextField server, TextField port) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		grid.add(new Label("Server:"), 0, 0);
		grid.add(server, 1, 0);
		grid.add(new Label("Port:"), 0, 1);
		grid.add(port, 1, 1);
		return grid;
	}
}