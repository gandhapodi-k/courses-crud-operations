package com.crudProj.bootApplication.course;

import com.crudProj.bootApplication.course.controller.CourseController;
import com.crudProj.bootApplication.course.model.Course;
import com.crudProj.bootApplication.course.service.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseApplicationTests {

	@Autowired
	private CourseController courseController;

	@InjectMocks
	private CourseServiceImpl courseService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper objectMapper;

	List<Course>testData;

	private String textData;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUP(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		MockitoAnnotations.initMocks(this);
		objectMapper = new ObjectMapper();
	}

	private String testDataPrep(String fileName){
		try{
			InputStream inputStream = new ClassPathResource(fileName).getInputStream();
			objectMapper = new ObjectMapper();
			testData = Arrays.asList(objectMapper.readValue(inputStream, Course[].class));
			textData = objectMapper.writeValueAsString(testData);
		}catch (Exception e){

		}
		return textData;
	}

	private String testDataPrep_singleObject(String fileName){
		try{
			InputStream inputStream = new ClassPathResource(fileName).getInputStream();

			Course testData = objectMapper.readValue(inputStream, Course.class);
			textData = objectMapper.writeValueAsString(testData);
		}catch (Exception e){

		}
		return textData;
	}


	@Test
	public void testCreateCourse_happyPath() throws Exception {

		String input=testDataPrep("create_course_request.json");
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.POST,"/v01/api/courses");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
				.contentType("application/json").
				content(input)).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertNotNull(str);
		Assertions.assertEquals("course created successfully",str);
	}


	@Test
	@Order(1)
	public void testCreateCourse_exception() throws Exception {

		String input=testDataPrep("create_course_badRequest.json");
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.POST,"/v01/api/courses");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
				.contentType("application/json").
				content(input)).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertNotNull(str);
		Assertions.assertEquals("invalid Request data",str);
		Assertions.assertEquals(response.getStatus(),HttpStatus.UNPROCESSABLE_ENTITY.value());
	}

	@Test
	public void testUpdateCourse_happyPath() throws Exception {

		String input=testDataPrep_singleObject("update_course_request.json");
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.PUT,"/v01/api/courses/1001");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
				.contentType("application/json")
				.content(input)).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Course courseObj = new ObjectMapper().readValue(str, Course.class);
		Assertions.assertNotNull(str);
		Assertions.assertEquals(courseObj.getId(),new Long(1001));
	}

	@Test
	public void testUpdateCourse_exception() throws Exception {
		long id=1099;
		String input=testDataPrep_singleObject("update_course_request.json");
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.PUT,"/v01/api/courses/"+id);
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
				.contentType("application/json")
				.content(input)).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertNotNull(str);
		Assertions.assertEquals("No Course found with given id "+id,str);
		Assertions.assertEquals(response.getStatus(),HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void testGetCourse_happyPath() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET,"/v01/api/courses/1001");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
				.contentType("application/json"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Course courseObj = new ObjectMapper().readValue(str, Course.class);
		Assertions.assertNotNull(str);
		Assertions.assertEquals(courseObj.getId(),new Long(1001));
		Assertions.assertEquals(response.getStatus(),HttpStatus.OK.value());
	}

	@Test
	public void testGetCourse_exception() throws Exception {
		long id=1079;
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET,"/v01/api/courses/"+id);
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
						.contentType("application/json"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertNotNull(str);
		Assertions.assertEquals("No Course found with given id "+id,str);
		Assertions.assertEquals(response.getStatus(),HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void testGetAllCourses_happyPath() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET,"/v01/api/courses");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
						.contentType("application/json"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		List<Course> courseList=Arrays.asList(objectMapper.readValue(str, Course[].class));
		Assertions.assertNotNull(str);
		Assertions.assertNotEquals(courseList.size(),0);
		Assertions.assertEquals(response.getStatus(),HttpStatus.OK.value());
	}

	@Test
	public void testDeleteCourses_happyPath() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.DELETE,"/v01/api/courses/1001");
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
						.contentType("application/json"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertEquals("",str);
		Assertions.assertEquals(response.getStatus(),HttpStatus.OK.value());
	}

	@Test
	public void testDeleteCourses_exception() throws Exception {
		long id=1059;
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.DELETE,"/v01/api/courses/"+id);
		MvcResult result=mockMvc.perform(requestBuilder.accept("application/json")
						.contentType("application/json"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String str= response.getContentAsString();
		Assertions.assertNotNull(str);
		Assertions.assertEquals("No Course found with given id "+id,str);
		Assertions.assertEquals(response.getStatus(),HttpStatus.NOT_FOUND.value());
	}

}
