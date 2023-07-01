package web.tracking.db.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.RequestTrackingDataDTO;
@Repository
public interface RequestTrackingDataRepository extends MongoRepository<RequestTrackingDataDTO, String> {
 List<RequestTrackingDataDTO> findBySessionId(String sessionId);	
 List<RequestTrackingDataDTO> findBySessionIdAndIpProcessed(String sessionId, Boolean processed);	
 List<RequestTrackingDataDTO> findByCompanyIdAndCreatedTSBetween(String companyId, 
     LocalDateTime fromDate, LocalDateTime toDate);
}
