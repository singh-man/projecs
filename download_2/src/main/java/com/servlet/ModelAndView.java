package com.servlet;

import java.util.Map;

import com.servlet.ForUrl.ParamScope;

public class ModelAndView<T> {
	
	private String view;
	
	private Map<String, T> modelAndData;
	
	private ParamScope scope;
	
	public ModelAndView(String view, Map<String, T> modelAndData, ParamScope scope) {
		super();
		this.view = view;
		this.modelAndData = modelAndData;
		this.scope = scope;
	}
	
	public String getView() {
		return view;
	}

	public Map<String, T> getModelAndData() {
		return modelAndData;
	}
	
	public ParamScope getScope() {
		return scope;
	}

}

