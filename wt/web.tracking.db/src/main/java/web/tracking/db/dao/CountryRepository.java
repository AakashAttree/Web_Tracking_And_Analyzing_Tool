package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.CountryDTO;
@Repository
public interface CountryRepository extends MongoRepository<CountryDTO, String> {
	List<CountryDTO> findByCompId(String compId);
	CountryDTO findByCompIdAndCountryCode(String compId, String countryCode);
	CountryDTO findByCompIdAndCountryName(String compId, String countryName);
}
