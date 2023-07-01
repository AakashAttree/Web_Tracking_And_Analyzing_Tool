package web.tracking.db.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.SearchStringDTO;
@Repository
public interface SearchStringRepository extends MongoRepository<SearchStringDTO, String> {

}
