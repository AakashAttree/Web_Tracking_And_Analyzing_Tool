package web.tracking.db.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.IpDetailApiMetaData;

@Repository
public interface IpDetailApiMetaDataRepository extends MongoRepository<IpDetailApiMetaData, String> {
	IpDetailApiMetaData findByApiName(String apiName);
}
