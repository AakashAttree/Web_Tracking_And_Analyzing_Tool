package web.tracking.controller.tracking;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.SessionTrackingDataRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.ts.producer.Producer;


@RestController
public class TrackingController {
	@Autowired(required=true)
	private Producer producer;
	@Autowired
	private SessionTrackingDataRepository saveTrackingInfo;
	private static int counter =0;
	@RequestMapping(value="/track")
	public void saveTask(@RequestBody(required=false) Object obj, HttpServletRequest httpServletRequest,HttpServletResponse response)
	{

	    try {
	      counter++;
	      WebTrackingDAO<SessionTrackingDataDTO> sessionTrackingDataDAO = new WebTrackingDAO<SessionTrackingDataDTO>();
	      boolean isGetRequest = false;
	      String requestMethod = httpServletRequest.getMethod();
	      Map<String, String> requestObj = (Map) obj;
	      if ("GET".equalsIgnoreCase(requestMethod)) {
	        isGetRequest = true;
	        requestObj = new HashMap<>();
	        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
	        if (parameterNames != null) {
	          while (parameterNames.hasMoreElements()) {
	            String paramName = parameterNames.nextElement();
	            String paramValue = httpServletRequest.getParameter(paramName);
	            requestObj.put(paramName, paramValue);
	          }
	        }
	      }

	      Map<String, Object> map = new HashMap<String, Object>();
	      map.put(TrackingConstants.REQUEST_OBJECT, requestObj);
	      HttpSession httpSession = httpServletRequest.getSession();

	      // ---------------------------
	      SessionTrackingDataDTO trackingData = new SessionTrackingDataDTO();

	      synchronized (httpSession) {
	        String sessionId = (String) httpSession.getAttribute(TrackingConstants.SESSION_ID);
	        if (sessionId != null) {
	          Cookie cookie = new Cookie(TrackingConstants.SESSION_ID, sessionId);
	          response.addCookie(cookie);
	        } else {
	          if (httpServletRequest.getCookies() != null) {
	            for (Cookie cookie : httpServletRequest.getCookies()) {
	              if (TrackingConstants.SESSION_ID.equals(cookie.getName())) {
	                httpSession.setAttribute(TrackingConstants.SESSION_ID, cookie.getValue());
	                sessionId = cookie.getValue();
	              }
	            }
	          }
	        }
	        trackingData.setSessionId(sessionId);
	        trackingData = sessionTrackingDataDAO.findRecord(saveTrackingInfo, trackingData);
	        LocalDateTime now = LocalDateTime.now();
	        if(trackingData != null) {
	          if(trackingData.isClosed()) {
	            sessionId = null;
	            httpSession.removeAttribute(TrackingConstants.SESSION_ID);
	            Cookie cookie = new Cookie(TrackingConstants.SESSION_ID, sessionId);
	            response.addCookie(cookie);
	          }
	          else {
	            trackingData.setModifiedTS(now);
	            trackingData.setSessionId(sessionId);
	          }
	        }
	        else {
	          trackingData = new SessionTrackingDataDTO();
	          trackingData.setCreatedTS(now);
	          trackingData.setModifiedTS(now);
	        }

	        try {
	          String companyId = requestObj.get("id");
	          if (companyId != null) {
	            companyId = new String(Base64.getUrlDecoder().decode(companyId.getBytes()));
	          }
	          if (companyId != null) {
	            trackingData.setCompanyId(companyId);
	          }

	          if (requestObj.containsKey("page")) {
	            String page = new String(Base64.getUrlDecoder().decode(requestObj.get("page")));
	            trackingData.setPage(page);
	          }
	          trackingData.setClosed(false);
	          SessionTrackingDataDTO savedTrackingData = saveTrackingInfo.save(trackingData);

	          if (savedTrackingData.getSessionId() != null) {
	            trackingData.setSessionId(savedTrackingData.getSessionId());
	            Cookie cookie = new Cookie(TrackingConstants.SESSION_ID, savedTrackingData.getSessionId());
	            response.addCookie(cookie);
	            httpSession.setAttribute(TrackingConstants.SESSION_ID, savedTrackingData.getSessionId());
	            map.put(TrackingConstants.SESSION_ID, savedTrackingData.getSessionId());
	          }
	        } catch (Exception e) {
	          e.printStackTrace();
	        }

	        if (httpSession.getAttribute(TrackingConstants.REQUEST_INDEX) == null)
	          httpSession.setAttribute(TrackingConstants.REQUEST_INDEX, new AtomicInteger(0));
	      }
	      // ----------------------------

	      AtomicInteger atomicInteger = (AtomicInteger) httpSession.getAttribute(TrackingConstants.REQUEST_INDEX);

	      map.put(TrackingConstants.REQUEST_INDEX, atomicInteger.incrementAndGet());
	      setDetails(httpServletRequest, map);
	      map.put(TrackingConstants.IP_ADDRESS, getClientIp(httpServletRequest));

	      trackingData.setData(map);
	      System.out.println("Total number of request received: " + counter);
	      producer.addInQueue(trackingData);
	      return;

	    } catch (Exception e) {
	      // TODO: handle exception
	      e.printStackTrace();
	    }
	  
	}
	private static String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}
	private void setDetails(HttpServletRequest httpServletRequest,
			Map<String, Object> map) {
		map.put(TrackingConstants.REQUEST_PARAM,httpServletRequest.getParameterMap());

		Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
		Map<String, String> headers = new HashMap<>();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			String value = httpServletRequest.getHeader(key);
			headers.put(key, value);
		}

		map.put(TrackingConstants.REQUEST_HEADER,headers);

		if(httpServletRequest.getCookies() != null){
			Map<String, String> cookies = new HashMap<>();
			for(Cookie cookie: httpServletRequest.getCookies()){
				cookies.put(cookie.getName(), cookie.getValue());
			}

			map.put(TrackingConstants.COOKIES,cookies);
		}

	}
}
