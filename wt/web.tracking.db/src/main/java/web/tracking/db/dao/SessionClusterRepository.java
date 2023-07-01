package web.tracking.db.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import web.tracking.db.dto.SessionClusterDTO;
@Repository
public interface SessionClusterRepository extends MongoRepository<SessionClusterDTO, String>{
  List<SessionClusterDTO> findBySessionId(String sessionId);
}
