package web.tracking.db.dao;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class WebTrackingDAO<T>
{


	public T findRecord(MongoRepository<T, String> mongoRepository, T t){
		Example<T> example =  Example.of(t, ExampleMatcher.matching());
		List<T> findAll = mongoRepository.findAll(example);
		t = (findAll != null && !findAll.isEmpty()) ? findAll.get(0) : null;
		return t;
	}
	
	public List<T> findAllRecord(MongoRepository<T, String> mongoRepository, T t){
		Example<T> example =  Example.of(t, ExampleMatcher.matching());
		List<T> ts = mongoRepository.findAll(example);
		return ts;
	}
}
