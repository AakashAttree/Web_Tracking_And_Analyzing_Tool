package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import web.tracking.db.dto.SocialMediaActivityDTO;
@Repository
public interface SocialMediaRepository extends MongoRepository<SocialMediaActivityDTO, String> {
  List<SocialMediaActivityDTO> findByPageAndCompId(String page, String compId);
  List<SocialMediaActivityDTO> findBySocialMediaNameAndCompId(String page, String compId);
  List<SocialMediaActivityDTO> findByCompId(String compId);
}
