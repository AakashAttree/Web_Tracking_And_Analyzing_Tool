package web.tracking.controller.request;

public enum VariableType {
	EMAIL("email"), CAMPAIGN("campaign");

	private VariableType(String newType) {
		this.type = newType;
	}

	private final String type;

	public String getType() {
		return type;
	}
}
