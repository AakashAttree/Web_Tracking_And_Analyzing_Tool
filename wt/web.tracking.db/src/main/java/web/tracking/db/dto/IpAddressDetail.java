package web.tracking.db.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "IpAddressDetail")
public class IpAddressDetail {
  @Id
  private String id;
  private String ip;
  private String type;
  private String continentCode;
  private String continentName;
  private String countryCode;
  private String countryName;
  private String regionCode;
  private String regionName;
  private String city;
  private String zip;
  private String latitude;
  private String longitude;
  private Boolean isISP;
  private String ispName;

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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContinentCode() {
    return continentCode;
  }

  public void setContinentCode(String continentCode) {
    this.continentCode = continentCode;
  }

  public String getContinentName() {
    return continentName;
  }

  public void setContinentName(String continentName) {
    this.continentName = continentName;
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

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public boolean isISP() {
    return isISP;
  }

  public void setISP(boolean isISP) {
    this.isISP = isISP;
  }

  public String getIspName() {
    return ispName;
  }

  public void setIspName(String ispName) {
    this.ispName = ispName;
  }


}
