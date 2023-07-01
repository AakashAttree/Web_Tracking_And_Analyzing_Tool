package web.tracking.db.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.CompanyActivies;

@Repository
public interface CompanyActiviesRepository extends MongoRepository<CompanyActivies, String> {
	List<CompanyActivies> findByCompanyId(String companyId);
	List<CompanyActivies> findByCompanyIdAndCreatedTSBetween(String companyId, 
	    LocalDateTime fromDate, LocalDateTime toDate);
}
