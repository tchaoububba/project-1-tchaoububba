package com.revature.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DispatcherServlet extends HttpServlet {
	Logger LOGGER = Logger.getLogger(DispatcherServlet.class);

	/**
	 * Java 2.x compatibility
	 */
	private static final long serialVersionUID = 55343653124378175L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object data = RequestHelper.process(request);
		
		/* If what the controllers return is a String, we forward to an HTML file. */
		if(data instanceof String) {
			String path = (String) data;
			LOGGER.trace(path);
			
			request.getRequestDispatcher(path).forward(request, response);
		} else {
			/* Else, we marshall the given POJO */
			response.getWriter().write(new ObjectMapper().writeValueAsString(data));
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
