package web.tracking.db.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SocialMediaActivities")
public class SocialMediaActivityDTO {

  @Id
  private String id;
  private String compId;
  private String socialMediaName;
  private String activityType;
  private String page;
  private Integer count = null;
  private LocalDateTime createdTS;

 

  public LocalDateTime getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(LocalDateTime createdTS) {
    this.createdTS = createdTS;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCompId() {
    return compId;
  }

  public void setCompId(String compId) {
    this.compId = compId;
  }

  public String getSocialMediaName() {
    return socialMediaName;
  }

  public void setSocialMediaName(String socialMediaName) {
    this.socialMediaName = socialMediaName;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }


}
