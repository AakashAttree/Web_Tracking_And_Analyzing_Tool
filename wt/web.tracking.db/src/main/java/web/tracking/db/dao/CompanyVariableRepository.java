package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.CompanyVariableDTO;
@Repository
public interface CompanyVariableRepository extends MongoRepository<CompanyVariableDTO, String>{
	List<CompanyVariableDTO> findByCompanyId(String companyId);
}
