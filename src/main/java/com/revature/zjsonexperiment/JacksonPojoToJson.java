package com.revature.zjsonexperiment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.Employee;
import com.revature.repository.RequestRepositoryJdbc;

/**
 * Convert Java to Json using Jackson API
 * 
 * @author Ramesh Fadatare
 *
 */
public class JacksonPojoToJson {
	private static final Logger LOGGER = Logger.getLogger(RequestRepositoryJdbc.class);

//    public static void fileCreater() throws IOException {
//    	JacksonPojoToJson pathObject = new JacksonPojoToJson();
//    	String path = pathObject.getClass().getClassLoader().getResource(".").getPath();
//    	LOGGER.trace(path);
//    	
//    	LOGGER.trace("We got to our POJO to JSON file method");
//        // Create ObjectMapper
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        // create a post
//        Post post = new Post();
//        post.setTitle("Jackson JSON API Guide(Marker)");
//        post.setId(100L);
//        post.setDescription("Post about Jackson JSON API");
//        post.setContent("HTML content here");
//        post.setLastUpdatedAt(new Date());
//        post.setPostedAt(new Date());
//        // create some predefined tags
//        Set < Tag > tags = new HashSet < > ();
//        Tag java = new Tag(1L, "Java");
//        Tag jackson = new Tag(2L, "Jackson");
//        Tag json = new Tag(3L, "JSON");
//        tags.add(java);
//        tags.add(jackson);
//        tags.add(json);
//        // set tags to post
//        post.setTags(tags);
//        // Convert object to JSON string
//        String postJson = mapper.writeValueAsString(post);
//        System.out.println(postJson);
//        // Save JSON string to file
//        LOGGER.trace("Saving file at post.json");
//        FileOutputStream fileOutputStream = new FileOutputStream(path+"../../post.json");
//        mapper.writeValue(fileOutputStream, post);
//        fileOutputStream.close();
//    }

//    public static void createEmployeeJsonFile(HttpServletRequest request) throws IOException {
//    	// Find path of class while executing (in Tomcat)
//    	JacksonPojoToJson pathObject = new JacksonPojoToJson();
//    	String path = pathObject.getClass().getClassLoader().getResource(".").getPath();
//    	LOGGER.trace(path);
//    	
//		// Create ObjectMapper
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        // create an employee
//        Employee employee = new Employee();
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getEmployeeId() != 0) {
//        	employee.setEmployeeId(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getEmployeeId());
//        }
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getFirstName() != null) {
//        	employee.setFirstName(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getFirstName());
//        }
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getLastName() != null) {
//        	employee.setLastName(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getLastName());
//        }
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getUsername() != null) {
//        	employee.setUsername(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getUsername());
//        }
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getPassword() != null) {
//        	employee.setPassword(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getPassword());
//        }
//        if (((Employee)(request.getSession().getAttribute("loggedEmployee"))).getTitle() != null) {
//        	employee.setTitle(((Employee)(request.getSession().getAttribute("loggedEmployee"))).getTitle());
//        }
//        
////        // create some predefined tags
////        Set < Tag > tags = new HashSet < > ();
////        Tag java = new Tag(1L, "Java");
////        Tag jackson = new Tag(2L, "Jaaaaackson");
////        Tag json = new Tag(3L, "JSON");
////        tags.add(java);
////        tags.add(jackson);
////        tags.add(json);
////        // set tags to post
////        employee.setTags(tags);
////        // Convert object to JSON string
//        String postJson = mapper.writeValueAsString(employee);
//        System.out.println(postJson);
//        // Save JSON string to file
//        FileOutputStream fileOutputStream = new FileOutputStream(path+"../../post.json");
//        mapper.writeValue(fileOutputStream, employee);
//        fileOutputStream.close();
//	}

	public static void createEmployeeJsonFile(Employee employee) throws IOException {
		// Find path of class while executing (in Tomcat)
		JacksonPojoToJson pathObject = new JacksonPojoToJson();
		String path = pathObject.getClass().getClassLoader().getResource(".").getPath();
		LOGGER.trace(path);

		// Create ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

//        // create some predefined tags
//        Set < Tag > tags = new HashSet < > ();
//        Tag java = new Tag(1L, "Java");
//        Tag jackson = new Tag(2L, "Jaaaaackson");
//        Tag json = new Tag(3L, "JSON");
//        tags.add(java);
//        tags.add(jackson);
//        tags.add(json);
//        // set tags to post
//        employee.setTags(tags);
//        // Convert object to JSON string
		if (employee != null) {
			String postJson = mapper.writeValueAsString(employee);
			System.out.println(postJson);
			// Save JSON string to file
			FileOutputStream fileOutputStream = new FileOutputStream(path + "../../post.json");
			mapper.writeValue(fileOutputStream, employee);
			fileOutputStream.close();
		}
	}
	
	public static void createEmployeeJsonListFile(List<Employee> employees) throws IOException {
		// Find path of class while executing (in Tomcat)
		JacksonPojoToJson pathObject = new JacksonPojoToJson();
		String path = pathObject.getClass().getClassLoader().getResource(".").getPath();
		LOGGER.trace(path);

		// Create ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

//        // create some predefined tags
//        Set < Tag > tags = new HashSet < > ();
//        Tag java = new Tag(1L, "Java");
//        Tag jackson = new Tag(2L, "Jaaaaackson");
//        Tag json = new Tag(3L, "JSON");
//        tags.add(java);
//        tags.add(jackson);
//        tags.add(json);
//        // set tags to post
//        employee.setTags(tags);
//        // Convert object to JSON string
		if (employees != null) {
			String postJson = mapper.writeValueAsString(employees);
			System.out.println(postJson);
			// Save JSON string to file
			FileOutputStream fileOutputStream = new FileOutputStream(path + "../../post.txt");
			mapper.writeValue(fileOutputStream, employees);
			fileOutputStream.close();
		}
	}
}