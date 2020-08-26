package studiranje.ip.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Зрно за размјену информација и порука по веб апликацији. 
 * @author mirko
 * @version 1.0
 */
public class InformationBean implements Serializable{
	private static final long serialVersionUID = 5129073040000969735L;
	private String messageSource = "";
	private HashMap<String, String> messagesMap = new HashMap<>();
	private HashMap<String, Throwable> exceptionsMap = new HashMap<>(); 
	private String annotation = ""; 
	
	public String getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(String messageSource) {
		if(messageSource==null) messageSource = "";
		this.messageSource = messageSource;
	}
	
	public void includeMessageContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(messageSource.contentEquals("")) return; 
		RequestDispatcher dispatcher = req.getRequestDispatcher(messageSource);
		dispatcher.include(req, resp);
	}
	
	public void forwardMessageContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(messageSource.contentEquals("")) return; 
		RequestDispatcher dispatcher = req.getRequestDispatcher(messageSource);
		dispatcher.forward(req, resp);
	}
	
	public synchronized void setMessage(String id, String message) {
		messagesMap.put(id, message); 
	}
	
	public synchronized String getMessage(String id) {
		String message = messagesMap.get(id);
		return message==null?"":message;
	}
	
	public synchronized void removeMessage(String id) {
		messagesMap.remove(id);
	}
	
	public synchronized void setException(String id, Throwable message) {
		exceptionsMap.put(id, message); 
	}
	
	public synchronized Throwable getException(String id) {
		return exceptionsMap.get(id);
	}
	
	public synchronized void removeException(String id) {
		exceptionsMap.remove(id); 
	}
	
	public synchronized String getMessageAndReset(String id) {
		try {
			String message = messagesMap.get(id);
			return message==null?"":message;
		}finally {
			reset();
		}
	}
	
	public synchronized Throwable getExceptionAndReset(String id) {
		try {
			return exceptionsMap.get(id);
		}finally {
			reset();
		}
	}
	
	public void reset() {
		exceptionsMap.clear();
		messagesMap.clear();
		messageSource=""; 
		annotation = "";
	}
	
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
}
