package web.tracking.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.tracking.db.dao.CompanyConfigRepository;
import web.tracking.db.dto.CompanyConfigDTO;

@Service
public class LoadConfigurationInDB {

  @Autowired
  CompanyConfigRepository companyConfigRepository;

  public boolean loadConfiguration(String companyId) throws IOException {
    boolean completed = false;

    String[] files = {"script.properties", "iphandling.properties", "referrer.properties"};

    for (String fileName : files) {

      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

      if(inputStream != null) {
        Properties properties = new Properties();
        properties.load(inputStream);
        if (!properties.isEmpty()) {
          CompanyConfigDTO moduleConfigDTO = null;
          String moduleName =
              properties.getProperty("module", fileName.substring(0, fileName.indexOf(".")));
          moduleConfigDTO = companyConfigRepository.findByCompanyIdAndMasterConfigIdAndName(companyId,
              "NIL", moduleName);
          if (moduleConfigDTO == null) {
            moduleConfigDTO = new CompanyConfigDTO();
            moduleConfigDTO.setCompanyId(companyId);
            moduleConfigDTO.setMasterConfigId("NIL");
            moduleConfigDTO.setName(moduleName);
            companyConfigRepository.save(moduleConfigDTO);
          }
          Enumeration<Object> enumeration = properties.keys();
          if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
              String key = (String) enumeration.nextElement();
              String value = (String) properties.get(key);
              if (key != null && value != null) {
                CompanyConfigDTO configDTO = null;
                configDTO = companyConfigRepository.findByCompanyIdAndMasterConfigIdAndName(companyId,
                    moduleConfigDTO.getId(), key);
                if (configDTO == null) {
                  configDTO = new CompanyConfigDTO();
                  configDTO.setCompanyId(companyId);
                  configDTO.setMasterConfigId(moduleConfigDTO.getId());
                  configDTO.setName(key);
                  configDTO.setValue(value);
                  companyConfigRepository.save(configDTO);
                }
              }
            }
          }
        } else {
          System.out.println("File not found:" + fileName);
        }
      }
    }
    return completed;
  }

}
