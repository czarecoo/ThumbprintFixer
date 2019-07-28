package com.czareg.model;

public enum StringFormat {
	UPPERCASE {
		@Override
		public String convertToFormat(String text) {
			return text.toUpperCase();
		}
	},
	LOWERCASE {
		@Override
		public String convertToFormat(String text) {
			return text.toLowerCase();
		}
	},
	MIXEDCASE {
		@Override
		public String convertToFormat(String text) {
			return text;
		}
	};

	public abstract String convertToFormat(String text);
}
