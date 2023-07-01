package web.tracking.workflow.starter;

import org.springframework.context.ApplicationContext;

public class WebTrackingWorkflowStarter implements Runnable {

	private ApplicationContext applicationContext;

	public WebTrackingWorkflowStarter(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
	}

	@Override
	public void run() {
		try {
			/*
			 * StandardWorkflow standardWorkflow = (StandardWorkflow) applicationContext
			 * .getBean(TrackingConstants.WEB_TRACKING_WORKFLOW); Map<String, Object>
			 * parameters = new HashMap<>();
			 * parameters.put(TrackingConstants.SPRING_APPLICATION_CONTEXT,
			 * applicationContext);
			 * standardWorkflow.processWorkflow(TrackingConstants.WEB_TRACKING_WORKFLOW,
			 * parameters);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
