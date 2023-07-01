package web.tracking.db.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class CountryDTO {
  @Id
  private String id;
  private String compId;
  private String countryCode;
  private String countryName;
  private Integer count;

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

  public String getCompId() {
    return compId;
  }

  public void setCompId(String compId) {
    this.compId = compId;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
