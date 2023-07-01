package web.tracking.controller.request;

public class CompanyVariableRequestObject extends RequestObject {

	private String name;
	private String requestParameter;
	private String defaultValue;
	private String includeInReport;
	private String description;
	private String uniqueId;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequestParameter() {
		return requestParameter;
	}

	public void setRequestParameter(String requestParameter) {
		this.requestParameter = requestParameter;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getIncludeInReport() {
		return includeInReport;
	}

	public void setIncludeInReport(String includeInReport) {
		this.includeInReport = includeInReport;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
