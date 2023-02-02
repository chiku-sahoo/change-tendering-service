package com.adp.hiring.changetenderingservice.controller;

import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INSUFFICIENT_COINS_ERROR_MSG;
import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_BILL_VALUE_ERROR_MSG;
import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_DENOMINATION_ERROR_MSG;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adp.hiring.changetenderingservice.service.ChangeService;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeServiceApiTest {

	@Mock
	private ChangeService changeService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void test_RequestChangeApi_whenValidInput_shouldReturnSuccessResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/change-service/$request-change").queryParam("bill", "10")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.bill", is("$10")));

	}

	@Test
	@Order(2)
	public void test_RequestChangeApi_whenInsufficientCoins_shouldReturnErrorResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/change-service/$request-change").queryParam("bill", "100")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.message", is(INSUFFICIENT_COINS_ERROR_MSG)));

	}

	@Test
	@Order(3)
	public void test_RequestChangeApi_whenInvalidBillIsEntered_shouldReturnErrorResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/change-service/$request-change").queryParam("bill", "55")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(INVALID_BILL_VALUE_ERROR_MSG)));

	}

	@Test
	@Order(4)
	public void test_RequestChangeApi_whenInvalidPreferredDenominationIsSelected_shouldReturnErrorResponse()
			throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/change-service/$request-change").queryParam("bill", "100")
				.queryParam("preferred_denomination", "0.30").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(INVALID_DENOMINATION_ERROR_MSG)));

	}

}
