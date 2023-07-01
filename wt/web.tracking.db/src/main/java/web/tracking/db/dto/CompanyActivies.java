package web.tracking.db.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company-activities")
public class CompanyActivies {
  @Id
  private String id;
  private String companyId;
  private LocalDateTime createdTS;
  private LocalDateTime modifiedTS;
  private String activityType;

  public LocalDateTime getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(LocalDateTime createdTS) {
    this.createdTS = createdTS;
  }

  public LocalDateTime getModifiedTS() {
    return modifiedTS;
  }

  public void setModifiedTS(LocalDateTime modifiedTS) {
    this.modifiedTS = modifiedTS;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }
}
