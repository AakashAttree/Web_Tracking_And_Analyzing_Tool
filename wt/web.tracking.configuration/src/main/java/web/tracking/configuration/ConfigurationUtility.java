package web.tracking.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.tracking.core.StringUtils;
import web.tracking.db.dao.CompanyConfigRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.CompanyConfigDTO;

@Service
public class ConfigurationUtility {
  @Autowired
  CompanyConfigRepository companyConfigRepository;
  private static final ConcurrentHashMap<String, Map<String, Map<String, String>>> ALL_CONFIGS =
      new ConcurrentHashMap<>();

  public String get(String companyId, String moduleName, String name) {

    String value = null;

    if (ALL_CONFIGS.containsKey(companyId)) {
      Map<String, Map<String, String>> companyConfigMap = ALL_CONFIGS.get(companyId);

      if (companyConfigMap.containsKey(moduleName)) {
        Map<String, String> configMap = companyConfigMap.get(moduleName);

        if (configMap.containsKey(name)) {
          value = configMap.get(name);
        } else {
          value = loadConfigFromDB(companyId, moduleName, name);
          if (StringUtils.isBlank(value)) {
            alertMessingProperty(companyId, moduleName, name);
          } else {
            configMap.put(name, value);
          }
        }
      } else {
        value = loadConfigFromDB(companyId, moduleName, name);
        if (StringUtils.isBlank(value)) {
          alertMessingProperty(companyId, moduleName, name);
        } else {
          Map<String, String> configMap = new HashMap<>();
          configMap.put(name, value);
          companyConfigMap.put(moduleName, configMap);
        }
      }

    } else {
      value = loadConfigFromDB(companyId, moduleName, name);
      if (StringUtils.isBlank(value)) {
        alertMessingProperty(companyId, moduleName, name);
      } else {

        Map<String, Map<String, String>> companyConfigMap = new HashMap<>();
        Map<String, String> configMap = new HashMap<>();
        configMap.put(name, value);
        companyConfigMap.put(moduleName, configMap);
        ALL_CONFIGS.put(companyId, companyConfigMap);
      }
    }
    return value;

  }

  private void alertMessingProperty(String companyId, String moduleName, String name) {
    System.out.println("============================================");
    System.out.println(
        companyId + "====================" + moduleName + "========================" + name);
    System.out.println("============================================");

  }

  protected String loadConfigFromDB(String companyId, String moduleName, String name) {
    String value = null;
    WebTrackingDAO<CompanyConfigDTO> webTrackingDAO = new WebTrackingDAO<>();
    CompanyConfigDTO companyConfigDTO = new CompanyConfigDTO();

    companyConfigDTO.setCompanyId(companyId);
    // companyConfigDTO.setModuleName(moduleName);
    companyConfigDTO.setName(moduleName);

    CompanyConfigDTO companyConfigDB =
        webTrackingDAO.findRecord(companyConfigRepository, companyConfigDTO);

    if (companyConfigDB != null) {
      CompanyConfigDTO companyConfigDTO2 = new CompanyConfigDTO();
      companyConfigDTO2.setCompanyId(companyId);
      companyConfigDTO2.setMasterConfigId(companyConfigDB.getId());
      companyConfigDTO2.setName(name);

      CompanyConfigDTO resulltRecord =
          webTrackingDAO.findRecord(companyConfigRepository, companyConfigDTO2);
      if (resulltRecord != null) {
        value = resulltRecord.getValue();
      }
    }
    return value;
  }

  public static void refresh(String companyId) {
    ALL_CONFIGS.remove(companyId);
  }


}
