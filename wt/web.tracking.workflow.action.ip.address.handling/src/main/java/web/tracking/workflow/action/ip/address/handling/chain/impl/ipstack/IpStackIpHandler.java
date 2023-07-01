package web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.ApplicationContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import web.tracking.configuration.ConfigurationUtility;
import web.tracking.core.APIHitRate;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.IpDetailApiMetaDataRepository;
import web.tracking.db.dto.IpAddressDetail;
import web.tracking.db.dto.IpDetailApiMetaData;
import web.tracking.workflow.action.ip.address.handling.chain.Context;
import web.tracking.workflow.action.ip.address.handling.chain.IpHandlerChain;

public class IpStackIpHandler implements IpHandlerChain {

  private IpHandlerChain handler;

  public IpStackIpHandler() {
  }

  public void addNextHandler(IpHandlerChain handler) {
    this.handler = handler;
  }

  public IpHandlerChain getHandler() {
    return handler;
  }

  public IpAddressDetail execute(Context context, String ipAddress) {
    ApplicationContext applicationContext =
        (ApplicationContext) context.get(TrackingConstants.SPRING_APPLICATION_CONTEXT);
   
    try {
      String companyId = (String) context.get(TrackingConstants.COMPANY_ID);
      IpAddressDetail ipAddressDetail =
          getIpAddressDetail(applicationContext, companyId, ipAddress);
      if (ipAddressDetail != null ) {
        return ipAddressDetail;
      }
      if (limitExceed(applicationContext)) {
        if (this.getNextHandler() != null) {
          return this.getNextHandler().execute(context, ipAddress);
        } else {
          return null;
        }
      } else {
        increamentAPIAccessCounter(applicationContext);
      }
      return ipAddressDetail;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
  }

  private void increamentAPIAccessCounter(ApplicationContext applicationContext) {
    String apiName = getApiName();
    IpDetailApiMetaDataRepository apiMetaDataRepository =
        (IpDetailApiMetaDataRepository) applicationContext
            .getBean(IpDetailApiMetaDataRepository.class);
    IpDetailApiMetaData apiMetaData = apiMetaDataRepository.findByApiName(apiName);
    if (apiMetaData == null) {
      apiMetaData = new IpDetailApiMetaData();
      apiMetaData.setApiName(apiName);
      apiMetaData.setCounter(0);

      apiMetaData.setHitRate(getHitRate());
      apiMetaData.setApiAccessLimit(getApiAccessLimit());
      apiMetaData.setResponseTypeClass(getResponseTypeClass());
    }
    apiMetaData.setLastAccessTime(Calendar.getInstance().getTime());
    apiMetaData.setCounter(apiMetaData.getCounter() + 1);
    apiMetaDataRepository.save(apiMetaData);
  }

  private String getResponseTypeClass() {
    return null;
  }

  private int getApiAccessLimit() {
    return 10;
  }

  private APIHitRate getHitRate() {
    return APIHitRate.MONTHLY;
  }

  protected boolean limitExceed(ApplicationContext applicationContext) {
    String apiName = getApiName();
    IpDetailApiMetaDataRepository apiMetaDataRepository =
        (IpDetailApiMetaDataRepository) applicationContext
            .getBean(IpDetailApiMetaDataRepository.class);
    IpDetailApiMetaData apiMetaData = apiMetaDataRepository.findByApiName(apiName);

    if (apiMetaData == null) {
      return false;
    }
    if (apiMetaData.getCounter() < apiMetaData.getApiAccessLimit()) {
      return false;
    }

    return true;
  }

  protected String getApiName() {
    return "ipstack";
  }

  private IpAddressDetail getIpAddressDetail(ApplicationContext applicationContext,
      String companyId, String ipAddress) throws ClientProtocolException, IOException {

    ConfigurationUtility configurationUtility =
        applicationContext.getBean(ConfigurationUtility.class);

    IpAddressDetail addressDetail = null;
    if (!isIPRestricted(configurationUtility, companyId, ipAddress)) {
      String API_KEY = configurationUtility.get(companyId, "IpHandling", "access_key");
      String IP_DETAIL_URL =
          configurationUtility.get(companyId, "IpHandling", "ip.stack.url") + API_KEY;
      String url = String.format(IP_DETAIL_URL, ipAddress);
      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(url);
      CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
      String response = parseResponse(httpResponse);
      // print result
      System.out.println(response.toString());
      httpClient.close();
      addressDetail = transformResponse(response);
    }

    return addressDetail;
  }

  protected boolean isIPRestricted(ConfigurationUtility configurationUtility, String companyId,
      String ipAddress) {
    boolean result = false;
    String restrictedHosts =
        configurationUtility.get(companyId, "IpHandling", "restricted.ip.addresses");
    if (StringUtils.isNotBlank(restrictedHosts)) {
      String[] array = restrictedHosts.split("|");
      for (String restrictedHost : array) {
        if (ipAddress.equalsIgnoreCase(restrictedHost)) {
          result = true;
          break;
        }
      } 
    }
    return result;
  }

  protected IpAddressDetail transformResponse(String response) {
    IpAddressDetail addressDetail = new IpAddressDetail();
    if (StringUtils.isNotBlank(response)) {
      Gson gson = new GsonBuilder().create();
      IpStackResponse ipStackResponse = gson.fromJson(response, IpStackResponse.class);
      if (ipStackResponse != null) {

        addressDetail.setCity(ipStackResponse.getCity());
        addressDetail.setContinentCode(ipStackResponse.getContinent_code());
        addressDetail.setContinentName(ipStackResponse.getContinent_name());
        addressDetail.setCountryCode(ipStackResponse.getCountry_code());
        addressDetail.setCountryName(ipStackResponse.getCountry_name());
        addressDetail.setIp(ipStackResponse.getIp());
        if (ipStackResponse.getConnection() != null) {
          String ispName = ipStackResponse.getConnection().getIsp();
          addressDetail.setISP(StringUtils.isNotBlank(ispName));
          addressDetail.setIspName(ispName);
        } else {
          addressDetail.setISP(false);
        }
        addressDetail.setLatitude(String.valueOf(ipStackResponse.getLatitude()));
        addressDetail.setLongitude(String.valueOf(ipStackResponse.getLongitude()));
        addressDetail.setRegionCode(ipStackResponse.getRegion_code());
        addressDetail.setRegionName(ipStackResponse.getRegion_name());
        addressDetail.setType(ipStackResponse.getType());
        addressDetail.setZip(ipStackResponse.getZip());

      }
    }
    return addressDetail;
  }

  protected String parseResponse(CloseableHttpResponse httpResponse) throws IOException {
    StringBuffer response = new StringBuffer();
    if (httpResponse != null) {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        response.append(inputLine);
      }
      reader.close();
    }
    return response.toString();
  }

  @Override
  public IpHandlerChain getNextHandler() {
    return handler;
  }

}
