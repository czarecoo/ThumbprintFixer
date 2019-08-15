package com.czareg.tests.bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import com.czareg.ThumbprintFixerUI;

import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

public class ThumbprintFixerUIBotTests extends ApplicationTest {
	@Override
	public void start(Stage stage) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		clipboard.clear();
		new ThumbprintFixerUI().start(stage);
	}

	private void waitForProcessingToFinish() {
		sleep(5000);
	}

	@Test
	public void shouldContainAllAppControls() {
		FxAssert.verifyThat("#fixButton", LabeledMatchers.hasText("Fix thumbprint"));
		FxAssert.verifyThat("#textField",
				(TextField tf) -> tf.getTooltip().getText().equals("Enter thumbprint without colons."));
		FxAssert.verifyThat("#radioButtonUppercase", LabeledMatchers.hasText("Uppercase"));
		FxAssert.verifyThat("#radioButtonLowercase", LabeledMatchers.hasText("Lowercase"));
		FxAssert.verifyThat("#radioButtonMixedcase", LabeledMatchers.hasText("Mixedcase"));
	}

	@ParameterizedTest(name = "{0} => uppercase({1})")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	public void clickingOnButtonShouldCreateUppercaseFormattedThumbprint(String input, String expected) {
		clickOn("#radioButtonUppercase");
		clickOn("#textField").write(input);
		clickOn("#fixButton");
		waitForProcessingToFinish();
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(expected.toUpperCase()));
		clickOn("#radioButtonUppercase");
	}

	@ParameterizedTest(name = "{0} => lowercase({1})")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	public void clickingOnButtonShouldCreateLowercaseFormattedThumbprint(String input, String expected) {
		clickOn("#radioButtonLowercase");
		clickOn("#textField").write(input);
		clickOn("#fixButton");
		waitForProcessingToFinish();
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(expected.toLowerCase()));
		clickOn("#radioButtonUppercase");
	}

	@ParameterizedTest(name = "{0} => mixedcase({1})")
	@CsvFileSource(resources = "/testCases.csv", delimiter = ';')
	public void clickingOnButtonShouldCreateMixedcaseFormattedThumbprint(String input, String expected) {
		clickOn("#radioButtonMixedcase");
		clickOn("#textField").write(input);
		clickOn("#fixButton");
		waitForProcessingToFinish();
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(expected));
		clickOn("#radioButtonUppercase");
	}

	@ParameterizedTest(name = "{0} => uppercase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void clickingOnRadioButtonUppercaseShouldFormatTextFieldText(String input) {
		clickOn("#radioButtonLowercase");
		clickOn("#textField").write(input);
		clickOn("#radioButtonUppercase");
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(input.toUpperCase()));
		clickOn("#radioButtonUppercase");
	}

	@ParameterizedTest(name = "{0} => lowercase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void clickingOnRadioButtonLowercaseShouldFormatTextFieldText(String input) {
		clickOn("#radioButtonUppercase");
		clickOn("#textField").write(input);
		clickOn("#radioButtonLowercase");
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(input.toLowerCase()));
		clickOn("#radioButtonUppercase");
	}

	@ParameterizedTest(name = "{0} => mixedcase({0})")
	@ValueSource(strings = { "UPPERCASE", "lowercase", "MiXeDcAsE" })
	public void clickingOnRadioButtonMixedcaseShouldFormatTextFieldText(String input) {
		clickOn("#radioButtonUppercase");
		clickOn("#textField").write(input);
		clickOn("#radioButtonMixedcase");
		FxAssert.verifyThat("#textField", (TextField tf) -> tf.getText().equals(input));
		clickOn("#radioButtonUppercase");
	}

}
