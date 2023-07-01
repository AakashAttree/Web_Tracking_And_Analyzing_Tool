package web.tracking.workflow.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.tracking.core.TrackingConstants;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.action.impl.CheckSessionElegbiltyWorkflowAction;
import web.tracking.workflow.action.impl.GetSessionDetailsWorkflowAction;
import web.tracking.workflow.action.impl.ProcessClosedSessionWorkflowAction;
import web.tracking.workflow.action.impl.UpdateRequestInfoWorkflowAction;
import web.tracking.workflow.action.impl.UpdateSessionInfoWorkflowAction;
import web.tracking.workflow.action.ip.address.handling.chain.IpAddressWorkflowAction;
import web.tracking.workflow.action.referrer.handler.ReferrerTrakingWorkflowAction;
import web.tracking.workflow.impl.StandardWorkflow;
@Configuration
public class WorkflowConfiguration {

	@Bean(name = TrackingConstants.REQUSET_WEB_TRACKING_WORKFLOW)
	public StandardWorkflow getRequestWorkflow() {

		List<WorkflowAction> workflowActions = new ArrayList<>();
		workflowActions.add(new IpAddressWorkflowAction());
		workflowActions.add(new ReferrerTrakingWorkflowAction());
		workflowActions.add(new UpdateRequestInfoWorkflowAction());

		Map<String, List<WorkflowAction>> map = new HashMap<>();
		map.put(TrackingConstants.REQUSET_WEB_TRACKING_WORKFLOW, workflowActions);
		StandardWorkflow standardWorkflow = new StandardWorkflow();
		standardWorkflow.setWorkflowActions(map);
		return standardWorkflow;
	}

	@Bean(name = TrackingConstants.WEB_TRACKING_WORKFLOW)
	public StandardWorkflow getWorkflow() {

		List<WorkflowAction> workflowActions = new ArrayList<>();

		workflowActions.add(new CheckSessionElegbiltyWorkflowAction());
		workflowActions.add(new GetSessionDetailsWorkflowAction());
		workflowActions.add(new ProcessClosedSessionWorkflowAction());
		workflowActions.add(new UpdateSessionInfoWorkflowAction());

		Map<String, List<WorkflowAction>> map = new HashMap<>();
		map.put(TrackingConstants.WEB_TRACKING_WORKFLOW, workflowActions);

		StandardWorkflow standardWorkflow = new StandardWorkflow();
		standardWorkflow.setWorkflowActions(map);
		return standardWorkflow;
	}


}
