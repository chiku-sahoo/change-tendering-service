package com.adp.hiring.changetenderingservice.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adp.hiring.changetenderingservice.service.impl.ChangeServiceImpl;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ChangeServiceApi.class, ChangeServiceImpl.class })
@WebMvcTest(ChangeServiceApi.class)
@SpringBootTest
public class ChangeServiceApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	private void test_RequestChangeApi() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/change-service/$request-change")
				.queryParam("bill", "10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();

		String res = result.getResponse().getContentAsString();
		assertNotNull(res);
	}

}
