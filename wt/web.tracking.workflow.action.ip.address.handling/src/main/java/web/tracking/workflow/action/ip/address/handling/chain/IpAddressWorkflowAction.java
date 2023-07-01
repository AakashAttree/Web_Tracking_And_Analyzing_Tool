package web.tracking.workflow.action.ip.address.handling.chain;

import java.util.Map;
import org.springframework.context.ApplicationContext;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.CompanyRepository;
import web.tracking.db.dao.CountryRepository;
import web.tracking.db.dao.IpAddressDetailRepository;
import web.tracking.db.dao.RegionRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.CompanyDTO;
import web.tracking.db.dto.CountryDTO;
import web.tracking.db.dto.IpAddressDetail;
import web.tracking.db.dto.RegionDTO;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;

public class IpAddressWorkflowAction implements WorkflowAction {


  @Override
  public boolean doAction(Context context) throws Exception {
    System.out.println(this.getClass().getName());
    ApplicationContext applicationContext =
        (ApplicationContext) context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);

    RequestTrackingDataDTO requestDTO =
        (RequestTrackingDataDTO) context.getAttribute(TrackingConstants.REQUEST_DTO);
    String companyId = requestDTO.getCompanyId();
    CompanyRepository companyRepository =
        (CompanyRepository) applicationContext.getBean(CompanyRepository.class);
    WebTrackingDAO<CompanyDTO> companyDAO = new WebTrackingDAO<CompanyDTO>();
    CompanyDTO companyDTO = new CompanyDTO();
    companyDTO.setId(requestDTO.getCompanyId());
    companyDTO = companyDAO.findRecord(companyRepository, companyDTO);
    if (!companyDTO.isIpTrackingEnabled()) {
      return true;
    }

    if (StringUtils.isNotBlank(companyId)) {
      context.setAttribute(TrackingConstants.COMPANY_ID, companyId);
    }

    System.out.println(requestDTO);
    Map<String, Object> data = requestDTO.getData();

    if (data != null) {
      Object objIPAddress = data.get(TrackingConstants.IP_ADDRESS);
      if (objIPAddress != null) {
        String ipAddress = String.valueOf(objIPAddress);
        IpAddressDetail ipAddressDetail = fetchIpAddressDetailFromDB(applicationContext, ipAddress);
        if (ipAddressDetail == null) {
          ipAddressDetail = getIpAddressDetail(applicationContext, ipAddress, companyId);
          saveIpAddressDetailInDB(applicationContext, ipAddressDetail);
        }


        if (ipAddressDetail != null) {

          if (StringUtils.isNotBlank(ipAddressDetail.getRegionCode())) {
            RegionRepository regionRepository = applicationContext.getBean(RegionRepository.class);
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setCompId(companyId);
            regionDTO.setRegionCode(ipAddressDetail.getRegionCode());
            regionDTO.setRegionName(ipAddressDetail.getRegionName());
            regionDTO.setCountryCode(ipAddressDetail.getCountryCode());
            regionDTO.setCountryName(ipAddressDetail.getCountryName());
            regionDTO = setRegionCount(regionRepository, regionDTO);
            regionRepository.save(regionDTO);
          }
          if (StringUtils.isNotBlank(ipAddressDetail.getCountryCode())) {
            CountryRepository countryRepository =
                applicationContext.getBean(CountryRepository.class);
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCompId(companyId);
            countryDTO.setCountryCode(ipAddressDetail.getCountryCode());
            countryDTO.setCountryName(ipAddressDetail.getCountryName());
            countryDTO = setCountryCount(countryRepository, countryDTO);
            countryRepository.save(countryDTO);
          }
        }
      }
    }
    requestDTO.setIpProcessed(true);
    return true;
  }

  private CountryDTO setCountryCount(CountryRepository countryRepository, CountryDTO countryDTO) {
    WebTrackingDAO<CountryDTO> webTrackingDAO = new WebTrackingDAO<>();
    CountryDTO existingValue = webTrackingDAO.findRecord(countryRepository, countryDTO);
    if (existingValue != null) {
      existingValue.setCount(existingValue.getCount() + 1);
      return existingValue;
    } else {
      countryDTO.setCount(1);
      return countryDTO;
    }

  }

  protected RegionDTO setRegionCount(RegionRepository regionRepository, RegionDTO regionDTO) {
    WebTrackingDAO<RegionDTO> webTrackingDAO = new WebTrackingDAO<>();
    RegionDTO existingRegion = webTrackingDAO.findRecord(regionRepository, regionDTO);
    if (existingRegion != null) {
      existingRegion.setCount(existingRegion.getCount() + 1);
      return existingRegion;
    } else {
      regionDTO.setCount(1);
      return regionDTO;
    }
  }

  private IpAddressDetail getIpAddressDetail(ApplicationContext applicationContext,
      String ipAddress, String companyId) {
    ChainInitializer chainInitializer = new ChainInitializer();
    web.tracking.workflow.action.ip.address.handling.chain.Context context =
        new web.tracking.workflow.action.ip.address.handling.chain.Context();
    context.put(TrackingConstants.SPRING_APPLICATION_CONTEXT, applicationContext);
    context.put(TrackingConstants.COMPANY_ID, companyId);
    IpHandlerChain chain = chainInitializer.getChain();
    return chain.execute(context, ipAddress);
  }

  private void saveIpAddressDetailInDB(ApplicationContext applicationContext,
      IpAddressDetail ipAddressDetail) {
    if (ipAddressDetail != null) {
      IpAddressDetailRepository addressDetailRepository =
          applicationContext.getBean(IpAddressDetailRepository.class);
      addressDetailRepository.save(ipAddressDetail);
    }
  }

  private IpAddressDetail fetchIpAddressDetailFromDB(ApplicationContext applicationContext,
      String ipAddress) {
    IpAddressDetail addressDetail = null;
    IpAddressDetailRepository addressDetailRepository =
        applicationContext.getBean(IpAddressDetailRepository.class);
    addressDetail = addressDetailRepository.findByIp(ipAddress);
    return addressDetail;
  }



  /*
   * public static void main(String[] args) { IpAddressWorkflowAction action = new
   * IpAddressWorkflowAction(); String myIP = "106.202.54.194";
   * 
   * try { action.getIpAddressDetail(myIP); } catch (IOException e) { // TODO Auto-generated catch
   * block e.printStackTrace(); } }
   */

}
