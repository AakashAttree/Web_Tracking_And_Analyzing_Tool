package web.tracking.workflow.action.ip.address.handling.chain;

import web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack.IpStackIpHandler;

public class ChainInitializer {

	public IpHandlerChain getChain(){
		IpStackIpHandler handler = new IpStackIpHandler();
		handler.addNextHandler(null);
		
		return handler;
	}
	
}
