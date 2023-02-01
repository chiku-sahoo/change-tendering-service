package com.adp.hiring.changetenderingservice.service;

import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INSUFFICIENT_COINS_ERROR_MSG;
import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_BILL_VALUE_ERROR_MSG;
import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_DENOMINATION_ERROR_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.adp.hiring.changetenderingservice.constants.AvailableCoins;
import com.adp.hiring.changetenderingservice.exceptions.InsufficientChangeException;
import com.adp.hiring.changetenderingservice.exceptions.InvalidBillValueException;
import com.adp.hiring.changetenderingservice.exceptions.InvalidDenominationException;
import com.adp.hiring.changetenderingservice.service.impl.ChangeServiceImpl;

@SpringBootTest
public class ChangeServiceImplTest {

	@InjectMocks
	ChangeServiceImpl changeService;

	@Spy
	private static TreeMap<AvailableCoins, Integer> availableDenominations;

	@BeforeAll
	public static void init() {
		availableDenominations = new TreeMap<>(
				Map.of(AvailableCoins.¢1, 10, AvailableCoins.¢5, 10, AvailableCoins.¢10, 10, AvailableCoins.¢25, 10));
	}

	@Test
	@Order(1)
	public void testRequestChange_whenValidInput_ReturnExactChange_1() {
		var changeResponse = changeService.requestChange(2.0, 0.25);
		assertNotNull(changeResponse);
		assertEquals(changeResponse.getDenominations().get(AvailableCoins.¢25), 8);
	}

	@Test
	@Order(2)
	public void testRequestChange_whenValidInput_ReturnExactChange_2() {
		var changeResponse = changeService.requestChange(1.0, null);
		assertNotNull(changeResponse);
		assertEquals(changeResponse.getDenominations().get(AvailableCoins.¢25), 2);
		assertEquals(changeResponse.getDenominations().get(AvailableCoins.¢10), 5);
	}

	@Test
	@Order(3)
	public void testRequestChange_whenInvalidBill_shouldThrowError() {

		InvalidBillValueException ex = assertThrows(InvalidBillValueException.class, () -> {
			var changeResponse = changeService.requestChange(25.0, null);
			assertNotNull(changeResponse);
		});

		assertEquals(INVALID_BILL_VALUE_ERROR_MSG, ex.getMessage());
	}

	@Test
	@Order(4)
	public void testRequestChange_whenInvalidPreferredDenominationSelected_shouldThrowError() {

		InvalidDenominationException ex = assertThrows(InvalidDenominationException.class, () -> {
			var changeResponse = changeService.requestChange(100.0, 0.35);
			assertNotNull(changeResponse);
		});

		assertEquals(INVALID_DENOMINATION_ERROR_MSG, ex.getMessage());
	}

	@Test
	@Order(5)
	public void testRequestChange_whenInsufficentCoins_shouldThrowError() {

		InsufficientChangeException ex = assertThrows(InsufficientChangeException.class, () -> {
			var changeResponse = changeService.requestChange(100.0, null);
			assertNotNull(changeResponse);
		});

		assertEquals(INSUFFICIENT_COINS_ERROR_MSG, ex.getMessage());
	}

}
