package web.tracking.controller.request;

public class CompanyPojo {
	private String companyId;
	private String name;
	private String domainName;
	private String oper;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public boolean isAddRequest() {
		return getOper() != null && getOper().equalsIgnoreCase("add") ? true : false;
	}

	public boolean isUpdateRequest() {
		return getOper() != null && getOper().equalsIgnoreCase("edit") ? true : false;
	}
}
