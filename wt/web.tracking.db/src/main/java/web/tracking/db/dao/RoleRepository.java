package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.RoleDTO;
@Repository
public interface RoleRepository extends MongoRepository<RoleDTO, String> {
	List<RoleDTO> findByCompId(String compId);
}
