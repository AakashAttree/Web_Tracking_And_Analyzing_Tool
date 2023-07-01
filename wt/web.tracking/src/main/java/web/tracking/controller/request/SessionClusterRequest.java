package web.tracking.controller.request;

public class SessionClusterRequest {
  private String id;
  private String sessionId;
  private Integer numberOfRequests;
  private Integer averageRequestTime;
  private Integer totalDurationOfSession;
  private Integer numberOfDistinctPageVisit;
  private Integer numberOfDistinctActivities;
  private Boolean knownContact;
  private Boolean hasSocialMediaId;
  private String cluster;
  private Boolean customer;
  private String contactsInSession;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getSessionId() {
    return sessionId;
  }
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  public Integer getNumberOfRequests() {
    return numberOfRequests;
  }
  public void setNumberOfRequests(Integer numberOfRequests) {
    this.numberOfRequests = numberOfRequests;
  }
  public Integer getAverageRequestTime() {
    return averageRequestTime;
  }
  public void setAverageRequestTime(Integer averageRequestTime) {
    this.averageRequestTime = averageRequestTime;
  }
  public Integer getTotalDurationOfSession() {
    return totalDurationOfSession;
  }
  public void setTotalDurationOfSession(Integer totalDurationOfSession) {
    this.totalDurationOfSession = totalDurationOfSession;
  }
  public Integer getNumberOfDistinctPageVisit() {
    return numberOfDistinctPageVisit;
  }
  public void setNumberOfDistinctPageVisit(Integer numberOfDistinctPageVisit) {
    this.numberOfDistinctPageVisit = numberOfDistinctPageVisit;
  }
  public Integer getNumberOfDistinctActivities() {
    return numberOfDistinctActivities;
  }
  public void setNumberOfDistinctActivities(Integer numberOfDistinctActivities) {
    this.numberOfDistinctActivities = numberOfDistinctActivities;
  }
  public Boolean getKnownContact() {
    return knownContact;
  }
  public void setKnownContact(Boolean knownContact) {
    this.knownContact = knownContact;
  }
  public Boolean getHasSocialMediaId() {
    return hasSocialMediaId;
  }
  public void setHasSocialMediaId(Boolean hasSocialMediaId) {
    this.hasSocialMediaId = hasSocialMediaId;
  }
  public String getCluster() {
    return cluster;
  }
  public void setCluster(String cluster) {
    this.cluster = cluster;
  }
  public Boolean getCustomer() {
    return customer;
  }
  public void setCustomer(Boolean customer) {
    this.customer = customer;
  }
  public String getContactsInSession() {
    return contactsInSession;
  }
  public void setContactsInSession(String contactsInSession) {
    this.contactsInSession = contactsInSession;
  }
}
