package web.tracking.workflow.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import web.tracking.workflow.Workflow;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;
import web.tracking.workflow.context.impl.StandardContext;

public class StandardWorkflow implements Workflow,

ApplicationContextAware {

private final Log LOG = LogFactory.getLog(StandardWorkflow.class);

private static final String ACTION = "action";

private Map<String, List<WorkflowAction>> workflowActions;

private ApplicationContext applicationContext;

	@Override
	public boolean processWorkflow(String workflofName, Map<String, Object> parameters) {

		Context context = new StandardContext(parameters);
		
		List<WorkflowAction> actions = getWorkflowActions(workflofName);

		for (WorkflowAction action : actions) {

			try {

				if(!action.doAction(context)){
					break;
				}

			} catch (Exception e) {

				StringBuilder message = new StringBuilder(
						"Failed to complete action:" + action.toString());

				message.append('\n');

				message.append(e.getMessage());

				LOG.error(message.toString());
e.printStackTrace();
				return false;

			}

		}

		return true;

	}
	private List<WorkflowAction> getWorkflowActions(String actionName) {

		List<WorkflowAction> actions = workflowActions.get(actionName);

		if (actions == null || actions.isEmpty()) {

			LOG.error("There is no defined action for " + actionName);

			throw new IllegalArgumentException(
					"There is no defined action for "+ actionName);

		}

		return actions;

	}
	@Override

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException{
		this.applicationContext = applicationContext;

	}
	// Getter/Setter

	public Map<String, List<WorkflowAction>> getWorkflowActions() {

		return workflowActions;

	}
	public void setWorkflowActions(

			Map<String, List<WorkflowAction>> workflowActions) {

		this.workflowActions = workflowActions;

	}

}
