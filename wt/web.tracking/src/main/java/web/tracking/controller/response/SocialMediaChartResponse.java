package web.tracking.controller.response;

import java.util.List;

public class SocialMediaChartResponse {
  private List<List<Object>> socialMediaTrend;

  public SocialMediaChartResponse(List<List<Object>> socialMediaTrend) {
    super();
    this.socialMediaTrend = socialMediaTrend;
  }

  public List<List<Object>> getSocialMediaTrend() {
    return socialMediaTrend;
  }

  public void setSocialMediaTrend(List<List<Object>> socialMediaTrend) {
    this.socialMediaTrend = socialMediaTrend;
  }
}
