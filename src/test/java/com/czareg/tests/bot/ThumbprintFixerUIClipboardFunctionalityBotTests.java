package com.czareg.tests.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

import com.czareg.ThumbprintFixerUI;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

class ThumbprintFixerUIClipboardFunctionalityBotTests extends ApplicationTest {
	final static String BAD_THUMBPRINT_FROM_CLIPBOARD = "test123tEst";
	final static String FIXED_THUMBPRINT = "E1:23:E";
	private Clipboard clipboard;

	@Override
	public void start(Stage stage) {
		clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(BAD_THUMBPRINT_FROM_CLIPBOARD);
		clipboard.setContent(content);
		new ThumbprintFixerUI().start(stage);
	}

	@Test
	public void clickingOnButtonShouldCreateUppercaseFormattedThumbprint() {
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(BAD_THUMBPRINT_FROM_CLIPBOARD));
		clickOn("#fixButton");
		sleep(5000);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clipboard = Clipboard.getSystemClipboard();
				assertEquals(FIXED_THUMBPRINT, clipboard.getString());
			}
		});
	}
}
