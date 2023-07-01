package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.RegionDTO;
@Repository
public interface RegionRepository extends MongoRepository<RegionDTO, String> {
	List<RegionDTO> findByCompId(String compId);
	List<RegionDTO> findByCompIdAndCountryName(String compId,String countryName);
	List<RegionDTO> findByCompIdAndCountryCode(String compId,String countryCode);
	
	List<RegionDTO> findByCompIdAndCountryNameAndRegionName(String compId,String countryName, String regionName);
	List<RegionDTO> findByCompIdAndCountryCodeAndRegionCode(String compId,String countryCode, String regionCode);
}
