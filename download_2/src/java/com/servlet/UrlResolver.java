package com.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.ForUrl.ParamScope;
import com.task.AdminTask;
import com.task.UserTask;

public class UrlResolver {

	private Set<Object> handlers;
	private Map<String, MethodAndObject> urlAndMethod;

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private void init() {
		handlers.add(new AdminTask());
		handlers.add(new UserTask());
	}

	public UrlResolver() throws InstantiationException, IllegalAccessException {
		urlAndMethod = new HashMap<String, UrlResolver.MethodAndObject>();
		handlers = new HashSet<Object>();
		init();
		for(Object o : handlers) {
			Method[] methods = o.getClass().getMethods();
			for(Method m : methods) {
				if(m.isAnnotationPresent(ForUrl.class)) {
					ForUrl t = m.getAnnotation(ForUrl.class);
					urlAndMethod.put(t.url(), new MethodAndObject(m, o));
				}
			}
		}
	}

	public Object processUrl(String url, HttpServletRequest req, HttpServletResponse res) {
		MethodAndObject mao = urlAndMethod.get(url);
		try {
			Method m = mao.method;
			Annotation[][] parameterAnnotations = m.getParameterAnnotations();
			Class<?>[] parameterTypes = m.getParameterTypes();
			Object[] o = new Object[parameterTypes.length];
			int i=-1;
			for(Annotation[] annotations : parameterAnnotations) {
				Class<?> parameterType = parameterTypes[++i];

				if(req.getClass() == parameterType) {
					o[i] = req;
					continue;
				} if(res.getClass() == parameterType) {
					o[i] = res;
					continue;
				}

				for(Annotation annotation : annotations) {
					if(annotation instanceof ForUrl) {
						ForUrl myAnnotation = (ForUrl) annotation;
						o[i] = getParamFromReqSessOrCtx(req, myAnnotation.param());
					}
				}
			}
			Object returningObject = m.invoke(mao.instance, o);

			if(returningObject instanceof ModelAndView) {
				ModelAndView mav = (ModelAndView) returningObject;
				Map<String, Object> modelAndData = mav.getModelAndData();
				for(Entry<String, Object> entry : modelAndData.entrySet()) {
					setParamToReqSessOrCtx(req, entry.getKey(), entry.getValue(), mav.getScope());
					req.setAttribute(entry.getKey(), entry.getValue());
				}
			}

			return returningObject;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private class MethodAndObject {
		private Method method;
		private Object instance;

		public MethodAndObject(Method method, Object instance) {
			super();
			this.method = method;
			this.instance = instance;
		}
	}

	private void setParamToReqSessOrCtx(HttpServletRequest req, String param, Object data, ParamScope scope) {

		switch(scope) {
			case REQUEST:
				req.setAttribute(param, data);
				break;
			case SESSION:
				req.getSession(false).setAttribute(param, data);
				break;
			case APPLICATION:
				servletContext.setAttribute(param, data);
				break;
			default:

		}
	}

	private Object getParamFromReqSessOrCtx(HttpServletRequest req, String param) {
		Object reqAttr = req.getParameter(param);
		if(reqAttr != null)
			return reqAttr;

		if(req.getSession(false) != null) {
			Object sessAttr = req.getSession(false).getAttribute(param);
			if(sessAttr != null) 
				return sessAttr;
		}

		Object ctxAttr = servletContext.getAttribute(param);
		if(ctxAttr != null)
			return ctxAttr;

		return null;
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Method[] methods = AdminTask.class.getMethods();
		for(Method m : methods) {
			if(m.isAnnotationPresent(ForUrl.class)) {
				ForUrl t = m.getAnnotation(ForUrl.class);
				System.out.println(t.url());
				System.out.println(m);
			}
		}

		UrlResolver ur = new UrlResolver();
		System.out.println(ur.urlAndMethod);
	}
}
