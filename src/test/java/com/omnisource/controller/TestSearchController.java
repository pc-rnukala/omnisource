package com.omnisource.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class TestSearchController {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testSearchProducts() throws Exception {
		long startTime = System.currentTimeMillis();
		ResultActions resultActions = mockMvc.perform(post(
				APIRequestMappings.SEARCH_PRODUCT).param("fts", "").param(
				"filters", ""));
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		MvcResult result = resultActions.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String resultStr = response.getContentAsString();
		Assert.assertNotNull(resultStr);
		String userGuidValue = JsonPath.read(resultStr,
				"$.spData.user.userGuid");
		String userGuid = userGuidValue;
		Assert.assertNotNull(userGuid);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		result = resultActions.andReturn();
		response = result.getResponse();
		resultStr = response.getContentAsString();
		Assert.assertNotNull(resultStr);
		System.out.println("TimeTaken: "
				+ (System.currentTimeMillis() - startTime) + "");
	}
}
