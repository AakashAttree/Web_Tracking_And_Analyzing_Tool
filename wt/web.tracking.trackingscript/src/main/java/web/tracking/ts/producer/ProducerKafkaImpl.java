package web.tracking.ts.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import web.tracking.db.dto.SessionTrackingDataDTO;

@Service
public class ProducerKafkaImpl implements Producer {

	private static final String TOPIC_NAME = "tracking-data";
	@Autowired
	private KafkaTemplate<Object, Object> template;

	@Override
	public void addInQueue(SessionTrackingDataDTO trackingData) {
		String messageBody = new Gson().toJson(trackingData);
		template.send(TOPIC_NAME, messageBody);
	}

}
