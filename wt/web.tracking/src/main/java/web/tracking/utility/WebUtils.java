package web.tracking.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import web.tracking.db.dto.EndUserActivies;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.security.userdetails.WebTrackingUserDetails;

public class WebUtils {
  private static final String DATERANGE = "daterange";
  private static final String CURRENTWEEK = "currentweek";
  private static final String TODAY = "today";
  private static final List<String> DAYS = Arrays.asList(
      "Monday".toUpperCase(),
      "TUESDAY".toUpperCase(),
      "Wednesday".toUpperCase()
      ,"Thursday".toUpperCase()
      ,"Friday".toUpperCase()
      ,"Saturday".toUpperCase()
      ,"Sunday".toUpperCase()
  );
  private WebUtils() {
    
  }

  public static void printSessionAttributes(HttpSession httpSession) {
    Enumeration<String> enumeration = httpSession.getAttributeNames();
    while (enumeration.hasMoreElements()) {
      System.out.println("--------------Session--------------------");
      String element = enumeration.nextElement();
      System.out.println("Element : " + element);
      System.out.println("Element value: " + httpSession.getAttribute(element));
    }
  }

  public static void printRequestAttributes(HttpServletRequest request) {
    Enumeration<String> enumeration = request.getAttributeNames();
    while (enumeration.hasMoreElements()) {
      System.out.println("---------------Request-------------------");
      String element = enumeration.nextElement();
      System.out.println("Element : " + element);
      System.out.println("Element value: " + request.getAttribute(element));
    }
  }

  public static synchronized void setCompanyInfo(HttpSession httpSession) {
    WebTrackingUserDetails details = (WebTrackingUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    if (details != null) {
      httpSession.setAttribute("compId", details.getCompId());
    }
  }

  public static DateOption getDates(String dateOption) {
    DateOption dateOption2 = new DateOption();
    LocalDateTime now = LocalDateTime.now();
    if(dateOption != null) {

      if(TODAY.equalsIgnoreCase(dateOption)) {
        dateOption2.setFromDate(now.withHour(0).withMinute(0).withSecond(1));
        dateOption2.setToDate(now.withHour(23).withMinute(59).withSecond(59));
      }
      else if(CURRENTWEEK.equalsIgnoreCase(dateOption)) {
        dateOption2.setFromDate(now.minusWeeks(1).withHour(0).withMinute(0).withSecond(1));
        dateOption2.setToDate(now.withHour(23).withMinute(59).withSecond(59));
      }
      else if(dateOption.contains(DATERANGE)) {
        //YYYY-MM-DD
        String[] array = dateOption.split("\\.");
        String strStartDate = array[1];
        String strEndDate = array[2];
        String[] dateValues = strStartDate.split("-");
        int year = Integer.parseInt(dateValues[0]);
        int month = Integer.parseInt(dateValues[1]);
        int day = Integer.parseInt(dateValues[2]);
        dateOption2.setFromDate(now.withYear(year).withMonth(month).withDayOfMonth(day).withHour(0).withMinute(0).withSecond(1));
        
        dateValues = strEndDate.split("-");
        year = Integer.parseInt(dateValues[0]);
        month = Integer.parseInt(dateValues[1]);
        day = Integer.parseInt(dateValues[2]);
        dateOption2.setToDate(now.withYear(year).withMonth(month).withDayOfMonth(day).withHour(23).withMinute(59).withSecond(59));
      }
    }
    else {

      dateOption2.setFromDate(now.withHour(0).withMinute(0).withSecond(1));
      dateOption2.setToDate(now.withHour(23).withMinute(59).withSecond(59));
    }
    return dateOption2;
  }

 

  public static Comparator<List<Object>> getDateOptionComparator(String dateOption){
    Comparator<List<Object>> comparator = null;
    if(TODAY.equalsIgnoreCase(dateOption)) {
      comparator = (List<Object> o1, List<Object> o2)->Integer.valueOf((String)o2.get(0)).compareTo(Integer.valueOf((String)o1.get(0)));
    }
    else  if(CURRENTWEEK.equalsIgnoreCase(dateOption)) {
      comparator = (List<Object> o1, List<Object> o2)-> DAYS.indexOf(o1.get(0))-DAYS.indexOf(o2.get(0));
    }
    else if(dateOption.contains(DATERANGE)) {
      comparator = (List<Object> o1, List<Object> o2) -> LocalDate.parse((String)o1.get(0)).compareTo(LocalDate.parse((String)o2.get(0)));
    }
    return comparator;
  }

  public static  Function<RequestTrackingDataDTO, String> getDateOptionKey(String dateOption) {
    return trackingData -> {
      if(TODAY.equalsIgnoreCase(dateOption)) {
        return String.valueOf(trackingData.getCreatedTS().getHour());
      }
      else  if(CURRENTWEEK.equalsIgnoreCase(dateOption)) {
        return String.valueOf(trackingData.getCreatedTS().getDayOfWeek());
      }
      else if(dateOption.contains(DATERANGE)) {
        return String.valueOf(LocalDate.of(trackingData.getCreatedTS().getYear(), trackingData.getCreatedTS().getMonth(), trackingData.getCreatedTS().getDayOfMonth()));
      }
      return null;
    };
  }

  public static Function<EndUserActivies, String> getEndUserDateOptionKey(String dateOption) {
    return trackingData -> {
      if(TODAY.equalsIgnoreCase(dateOption)) {
        return String.valueOf(trackingData.getCreatedTS().getHour());
      }
      else  if(CURRENTWEEK.equalsIgnoreCase(dateOption)) {
        return String.valueOf(trackingData.getCreatedTS().getDayOfWeek());
      }
      else if(dateOption.contains(DATERANGE)) {
        return String.valueOf(LocalDate.of(trackingData.getCreatedTS().getYear(), trackingData.getCreatedTS().getMonth(), trackingData.getCreatedTS().getDayOfMonth()));
      }
      return null;
    };
  }
}
