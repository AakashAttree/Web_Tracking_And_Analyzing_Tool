package web.tracking.db.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.IpAddressDetail;
@Repository
public interface IpAddressDetailRepository extends MongoRepository<IpAddressDetail, String> {
	IpAddressDetail findByIp(String ipAddress);
}
