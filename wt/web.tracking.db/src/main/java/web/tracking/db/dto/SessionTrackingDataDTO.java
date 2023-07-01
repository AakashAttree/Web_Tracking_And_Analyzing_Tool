package web.tracking.db.dto;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "session-tracking")
public class SessionTrackingDataDTO {
 
  @Id
  private String sessionId;
  private String companyId;
  private String createdBy;
  private String modifiedBy;
  private LocalDateTime createdTS;
  private LocalDateTime modifiedTS;
  private Boolean closed;
  private Map<String, Object> data;
  private String page;
  private Boolean customer;
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

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public Boolean isClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }

  public Boolean getCustomer() {
    return customer;
  }

  public void setCustomer(Boolean customer) {
    this.customer = customer;
  }
}
