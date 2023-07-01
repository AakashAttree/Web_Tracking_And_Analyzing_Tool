package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.AccountDTO;
@Repository
public interface AccountRepository extends MongoRepository<AccountDTO, String>{
	List<AccountDTO> findByCompId(String compId);
	
	AccountDTO findByUserName(String userName);
}
