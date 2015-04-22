package ua.edu.greenberg.client.controller;

public enum ButtonNames {
	BUTTON_LOGIN("Login"),
	BUTTON_SEND("Send message"),
	BUTTON_SEND_PRIVATE("Private message");
	
	private String buttonName;
	
	ButtonNames(String buttonName) {
		this.buttonName = buttonName;
	}
	
	public String getTypeValue() {
		return buttonName;
	}
	static public ButtonNames getType(String pType) throws RuntimeException {
		for (ButtonNames type: ButtonNames.values()) {
			if (type.getTypeValue().equals(pType)) {
				return type;
			}
		}
		throw new RuntimeException("Unknow name of the button");
	}
}
