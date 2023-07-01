package web.tracking.controller.account;

public enum AccountRole {
	ADMIN("Role_Admin", "Administrator"), USER("Role_User", "User");

//	ANONYMOUS("Role_Anonymous","Anonymous");
	private final String role;
	private final String displayName;

	private AccountRole(String role, String displayName) {
		this.role = role.toUpperCase();
		this.displayName = displayName;
	}

	public String getRole() {
		return role;
	}

	public String getDisplayName() {
		return displayName;
	}

}
