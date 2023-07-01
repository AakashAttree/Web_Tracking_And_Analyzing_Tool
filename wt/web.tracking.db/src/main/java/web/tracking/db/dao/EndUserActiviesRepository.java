package web.tracking.db.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import web.tracking.db.dto.EndUserActivies;

@Repository
public interface EndUserActiviesRepository extends MongoRepository<EndUserActivies, String> {
  EndUserActivies findByEndUserId(String endUserId);
  List<EndUserActivies> findByCompanyId(String companyId);
  List<EndUserActivies> findByCompanyIdAndCreatedTSBetween(String companyId, 
      LocalDateTime fromDate, LocalDateTime toDate);
  EndUserActivies findByRequestId(String requestId);
}
