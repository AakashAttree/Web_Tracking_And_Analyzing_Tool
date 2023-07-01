package web.tracking.db.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import web.tracking.db.dto.SessionTrackingDataDTO;
@Repository
public interface SessionTrackingDataRepository extends MongoRepository<SessionTrackingDataDTO, String>{
    List<SessionTrackingDataDTO> findByModifiedTSLessThan(LocalDateTime time);
	List<SessionTrackingDataDTO> findByClosedIsFalse();
	List<SessionTrackingDataDTO> findByClosed(Boolean closed);
	List<SessionTrackingDataDTO> findByCompanyIdAndClosed(String companyId, Boolean closed);
  List<SessionTrackingDataDTO> findByCompanyIdAndCreatedTSBetween(String companyId, LocalDateTime fromDate,
      LocalDateTime toDate);
}
