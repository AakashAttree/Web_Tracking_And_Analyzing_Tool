package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.ClientTrackingInfoDTO;
@Repository
public interface ClientTrackingInfoRepository  extends MongoRepository<ClientTrackingInfoDTO, String>{
	public List<ClientTrackingInfoDTO> findByClientId(String clientId);
}
