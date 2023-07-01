package web.tracking.core;

import java.util.regex.Pattern;

public interface TrackingConstants {
  Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
  String REQUEST_OBJECT = "REQUEST_OBJECT";
  String SESSION_ID = "SessionId";
  String HTTP_SESSION = "HttpSession";
  String IP_ADDRESS = "ip-address";
  String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  String VARIABLES = "CUSTOM_VARIABLES";
  String REQUEST_INDEX = "REQUEST_INDEX";
  String REQUEST_PARAM = "REQUEST_PARAM";
  String REQUEST_HEADER = "REQUEST_HEADER";
  String COOKIES = "REQUEST_HEADER";
  String REQUEST_OBJECT_TYPE = "REQUEST_OBJECT";
  String WEB_TRACKING_WORKFLOW = "WEB_TRACKING_WORKFLOW";
  String WEB_TRACKING_WORKFLOW_ACTIVITIES = "WEB_TRACKING_WORKFLOW_ACTIVITIES";
  String SPRING_APPLICATION_CONTEXT = "SPRING_APPLICATION_CONTEXT";
  String COMPLETED_SESSIONS = "COMPLETED_SESSIONS";
  String REQUSET_WEB_TRACKING_WORKFLOW = "REQUSET_WEB_TRACKING_WORKFLOW";
  String REQUEST_DTO = "REQUEST_DTO";
  String REQUEST_OBJECT_REFERRURL = "referrURL";
  String COMPANY_ID = "companyid";
}
