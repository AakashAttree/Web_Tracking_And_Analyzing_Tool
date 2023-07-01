package web.tracking.db.dto;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account-activities")
public class EndUserActivies {
  @Id
  private String id;
  private String endUserId;
  private String companyId;
  private String activityType;
  private String requestId;
  private LocalDateTime createdTS;
  private LocalDateTime modifiedTS;

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

  private Map<String, Integer> activities;

  public String getEndUserId() {
    return endUserId;
  }

  public void setEndUserId(String endUserId) {
    this.endUserId = endUserId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public Map<String, Integer> getActivities() {
    return activities;
  }

  public void setActivities(Map<String, Integer> activities) {
    this.activities = activities;
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

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

}
