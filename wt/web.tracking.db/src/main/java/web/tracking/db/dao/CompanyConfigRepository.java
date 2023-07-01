package web.tracking.db.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import web.tracking.db.dto.CompanyConfigDTO;

public interface CompanyConfigRepository extends MongoRepository<CompanyConfigDTO, String> {
  public List<CompanyConfigDTO> findByCompanyIdAndMasterConfigId(String companyId,
      String masterConfigId);

  public CompanyConfigDTO findByCompanyIdAndMasterConfigIdAndName(String companyId,
      String masterConfigId, String name);
  
  public List<CompanyConfigDTO> findByCompanyId(String companyId);

}

