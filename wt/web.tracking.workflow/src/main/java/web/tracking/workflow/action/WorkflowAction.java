package web.tracking.workflow.action;

import web.tracking.workflow.context.Context;

public interface WorkflowAction {
	/**

	* Execute action.

	*

	* @param context

	* @throws Exception

	*/

	public boolean doAction(Context context) throws Exception;

}
