package web.tracking.workflow.action.referrer.handler.factory;

import web.tracking.workflow.action.referrer.handler.strategy.ReferrerURLHandlerStrategy;
import web.tracking.workflow.action.referrer.handler.strategy.impl.DoNothingHandler;
import web.tracking.workflow.action.referrer.handler.strategy.impl.FacebookReferrerURLHandlerStrategy;
import web.tracking.workflow.action.referrer.handler.strategy.impl.GoogleReferrerURLHandlerStrategy;

public class ReferrerURLHandlerStrategyFactory {

	
	public ReferrerURLHandlerStrategy getReferrerHandler(String domainName){
		System.out.println(domainName);
		if(domainName.toLowerCase().contains("facebook")){
			return new FacebookReferrerURLHandlerStrategy();
		}
		else if(domainName.toLowerCase().contains("google")){
		  return new GoogleReferrerURLHandlerStrategy();
		}
		return new DoNothingHandler(null, null);
	}
	
}
