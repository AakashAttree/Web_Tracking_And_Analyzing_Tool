package web.tracking.controller.request;

import java.util.List;

public class CompanyReportPOJO {
	private List<Object[]> companyActivies;
	private List<Object[]> endUserActivies;
	private List<Object[]> geoActivities;
	private List<List<Object>> companyActiviesTrend;
	private List<List<Object>> endUserActiviesTrend;
	
	public List<Object[]> getCompanyActivies() {
		return companyActivies;
	}

	public void setCompanyActivies(List<Object[]> list) {
		this.companyActivies = list;
	}

	public List<Object[]> getEndUserActivies() {
		return endUserActivies;
	}

	public void setEndUserActivies(List<Object[]> endUserActivies) {
		this.endUserActivies = endUserActivies;
	}

	public List<Object[]> getGeoActivities() {
		return geoActivities;
	}

	public void setGeoActivities(List<Object[]> geoActivities) {
		this.geoActivities = geoActivities;
	}

  public void setCompanyActiviesTrend(List<List<Object>> list) {
    this.companyActiviesTrend = list;
    
  }

  public List<List<Object>> getCompanyActiviesTrend() {
    return companyActiviesTrend;
  }

  public List<List<Object>> getEndUserActiviesTrend() {
    return endUserActiviesTrend;
  }

  public void setEndUserActiviesTrend(List<List<Object>> endUserActiviesTrend) {
    this.endUserActiviesTrend = endUserActiviesTrend;
  }

}
