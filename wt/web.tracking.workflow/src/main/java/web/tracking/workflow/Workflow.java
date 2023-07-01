package web.tracking.workflow;

import java.util.Map;

public interface Workflow {
	/**

	* Method for processing workflow.

	*

	* @param parameters

	* maps of object which are needed for workflow processing

	* @return true in case that workflow is done without errors otherwise false

	*/

	public boolean processWorkflow(String name, Map<String, Object> parameters);
}
