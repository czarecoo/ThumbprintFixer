package com.czareg;

import com.czareg.utils.ThumbprintGetter;
import com.czareg.utils.ThumbprintMaker;

import javafx.concurrent.Task;

public class CreateThumbprintTask extends Task<String> {
	private String userInput;

	public CreateThumbprintTask(String userInput) {
		this.userInput = userInput;
	}

	@Override
	protected String call() {
		String thumbprint = userInput;
		try {
			thumbprint = ThumbprintGetter.get(userInput);
		} catch (Exception e) {
		}
		return ThumbprintMaker.make(thumbprint);
	}
}