package web.tracking.db.dto;

import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import web.tracking.core.APIHitRate;

@Document(collection = "IpDetailApiMetaData")
public class IpDetailApiMetaData {

  @Id
  private String id;
  private String apiName;
  private APIHitRate hitRate;
  private String responseTypeClass;
  private int apiAccessLimit;
  private int counter;
  private Date lastAccessTime;

  private String createdBy;
  private String modifiedBy;
  private LocalDateTime createdTS;
  private LocalDateTime modifiedTS;


  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public APIHitRate getHitRate() {
    return hitRate;
  }

  public void setHitRate(APIHitRate hitRate) {
    this.hitRate = hitRate;
  }

  public String getResponseTypeClass() {
    return responseTypeClass;
  }

  public void setResponseTypeClass(String responseTypeClass) {
    this.responseTypeClass = responseTypeClass;
  }

  public int getApiAccessLimit() {
    return apiAccessLimit;
  }

  public void setApiAccessLimit(int apiAccessLimit) {
    this.apiAccessLimit = apiAccessLimit;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public Date getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }


}
