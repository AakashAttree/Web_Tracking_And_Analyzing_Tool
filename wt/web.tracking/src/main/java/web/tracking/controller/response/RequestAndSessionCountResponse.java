package web.tracking.controller.response;

public class RequestAndSessionCountResponse {
  private Long sessionCount;
  private Long requestCount;
  public Long getSessionCount() {
    return sessionCount;
  }
  public void setSessionCount(Long sessionCount) {
    this.sessionCount = sessionCount;
  }
  public RequestAndSessionCountResponse(Long sessionCount, Long requestCount) {
    super();
    this.sessionCount = sessionCount;
    this.requestCount = requestCount;
  }
  public Long getRequestCount() {
    return requestCount;
  }
  public void setRequestCount(Long requestCount) {
    this.requestCount = requestCount;
  }

}
