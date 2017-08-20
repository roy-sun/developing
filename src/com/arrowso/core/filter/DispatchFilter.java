package com.arrowso.core.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jmx.support.ObjectNameManager;

import com.arrowso.core.conf.Application;
import com.arrowso.core.unit.InvokingHandle;
import com.arrowso.core.unit.JsonUnit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DispatchFilter implements Filter {

	private static ApplicationContext applicationContext;
	
	@Override
	public void destroy() {
		System.out.println("on destory");		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		ServletOutputStream outputStream = response.getOutputStream();
		String servletPath = request.getServletPath();
	//	String dispatchName = "callService";
		String token = request.getParameter("token");
		if(!verify(token)){
			outputStream.write(("login error.").getBytes("UTF-8"));
			return;
		}
			
		String params = request.getParameter("params");
	//	if(servletPath.startsWith("/"+dispatchName)){
			String[] callServiceInfo = servletPath.split("/");
			if(callServiceInfo.length>=3){
				String serviceName = callServiceInfo[1];
				String methodName = callServiceInfo[2];
				Object bean = applicationContext.getBean(serviceName);
					try {
						outputStream.write(invoke(bean,methodName,params));
					} catch (Exception e) {
						outputStream.write(("error:"+e.getMessage()).getBytes("UTF-8"));
					}
			}
	//	}
	}
	
	private boolean verify(String token){
		return true;
	}

	private byte[] invoke(Object service,String methodName,String params) throws Exception{
		Class<? extends Object> beanClass = service.getClass();
		Method[] methods = beanClass.getMethods();
		for(Method method:methods){
			if(methodName.equals(method.getName())){
				try {
					Object[] parseParam = InvokingHandle.parseParam(params,method.getParameterTypes());
					Object returnValue;
					if(parseParam!=null)
						returnValue = method.invoke(service,parseParam);
					else
						returnValue = method.invoke(service);
					return JsonUnit.getObjectMapper().writeValueAsBytes(returnValue);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		throw new Exception("call Service error.");
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		applicationContext = new AnnotationConfigApplicationContext(Application.class);
	}

}
