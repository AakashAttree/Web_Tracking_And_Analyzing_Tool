package web.tracking.workflow.context.impl;

import java.util.HashMap;
import java.util.Map;

import web.tracking.workflow.context.Context;

public class StandardContext implements Context {
	private Map<String, Object> context;

	/**

	* Create context object based.
	*
	* @param parameters
	*/
	public StandardContext(Map<String, Object> parameters) {
	if (parameters == null) {
	this.context = new HashMap<String, Object>();
	} else {
	this.context = parameters;
	}
	}

	@Override
	public Object getAttribute(String name) {
	return context.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
	context.put(name, value);
	}
}
