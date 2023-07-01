package web.tracking.db.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
public class CompanyDTO {
  @Id
  private String id;
  private String name;
  private String domainName;
  private Boolean referrerTrackingEnabled = true;
  private Boolean ipTrackingEnabled = true;

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
    if (id != null && (id.trim().length() == 0 || id.equalsIgnoreCase("_empty"))) {
      id = null;
    }
    this.id = id;
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

  public boolean isReferrerTrackingEnabled() {
    return referrerTrackingEnabled;
  }

  public void setReferrerTrackingEnabled(boolean referrerTrackingEnabled) {
    this.referrerTrackingEnabled = referrerTrackingEnabled;
  }

  public boolean isIpTrackingEnabled() {
    return ipTrackingEnabled;
  }

  public void setIpTrackingEnabled(boolean ipTrackingEnabled) {
    this.ipTrackingEnabled = ipTrackingEnabled;
  }


}
