package web.tracking.ts.producer;

import web.tracking.db.dto.SessionTrackingDataDTO;

public interface Producer {
	public void addInQueue(SessionTrackingDataDTO trackingData);

}
