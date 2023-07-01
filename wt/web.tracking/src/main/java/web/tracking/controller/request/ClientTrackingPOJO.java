package web.tracking.controller.request;

public class ClientTrackingPOJO {
	private String id;
	private String pageName;
	private String script;
	private String createdBy;
	private String createdOn;
	private String modifiedBy;
	private String modifiedOn;
	private String companyId;
//	private String[] variablesIds;
	private String oper;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isAddRequest() {
		return getOper() != null && getOper().equalsIgnoreCase("add") ? true : false;
	}

	public boolean isUpdateRequest() {
		return getOper() != null && getOper().equalsIgnoreCase("edit") ? true : false;
	}
	/*
	 * public String[] getVariablesIds() { return variablesIds; } public void
	 * setVariablesIds(String[] variablesIds) { this.variablesIds = variablesIds; }
	 */
}
