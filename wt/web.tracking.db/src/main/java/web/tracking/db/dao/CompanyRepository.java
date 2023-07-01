package web.tracking.db.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.CompanyDTO;
@Repository
public interface CompanyRepository extends MongoRepository<CompanyDTO, String>{
}

