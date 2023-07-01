package web.tracking.db.dto;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "request-tracking")
public class RequestTrackingDataDTO {
  @Id
  private String requestId;
  private String sessionId;
  private String companyId;
  private Map<String, Object> data;
  private String page;
  private Boolean ipProcessed;
  private Boolean referrerProcessed;
  private String ipDetailId;
  private String socialMediaId;
  private String searchWordId;
  private String searchStringId;

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

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
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

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public boolean isIpProcessed() {
    return ipProcessed;
  }

  public void setIpProcessed(boolean ipProcessed) {
    this.ipProcessed = ipProcessed;
  }

  public boolean isReferrerProcessed() {
    return referrerProcessed;
  }

  public void setReferrerProcessed(boolean referrerProcessed) {
    this.referrerProcessed = referrerProcessed;
  }

  public String getIpDetailId() {
    return ipDetailId;
  }

  public void setIpDetailId(String ipDetailId) {
    this.ipDetailId = ipDetailId;
  }

  public String getSocialMediaId() {
    return socialMediaId;
  }

  public void setSocialMediaId(String socialMediaId) {
    this.socialMediaId = socialMediaId;
  }

  public String getSearchWordId() {
    return searchWordId;
  }

  public void setSearchWordId(String searchWordId) {
    this.searchWordId = searchWordId;
  }

  public String getSearchStringId() {
    return searchStringId;
  }

  public void setSearchStringId(String searchStringId) {
    this.searchStringId = searchStringId;
  }


}
