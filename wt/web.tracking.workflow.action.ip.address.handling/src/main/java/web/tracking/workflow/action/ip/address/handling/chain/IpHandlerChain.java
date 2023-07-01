package web.tracking.workflow.action.ip.address.handling.chain;

import web.tracking.db.dto.IpAddressDetail;

public interface IpHandlerChain {

	void addNextHandler(IpHandlerChain handler);
	
	IpAddressDetail execute(Context context, String ipAddress);
	
	IpHandlerChain getNextHandler();
	
}
