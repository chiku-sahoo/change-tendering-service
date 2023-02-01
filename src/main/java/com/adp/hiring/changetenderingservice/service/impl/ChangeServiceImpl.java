package com.adp.hiring.changetenderingservice.service.impl;

import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INSUFFICIENT_COINS_ERROR_MSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.hiring.changetenderingservice.constants.AvailableBills;
import com.adp.hiring.changetenderingservice.constants.AvailableCoins;
import com.adp.hiring.changetenderingservice.exceptions.InsufficientChangeException;
import com.adp.hiring.changetenderingservice.exceptions.InvalidBillValueException;
import com.adp.hiring.changetenderingservice.exceptions.InvalidDenominationException;
import com.adp.hiring.changetenderingservice.models.ChangeResponse;
import com.adp.hiring.changetenderingservice.service.ChangeService;

@Service
public class ChangeServiceImpl implements ChangeService {

	@Autowired
	private TreeMap<AvailableCoins, Integer> availableDenominations;

	@Override
	public ChangeResponse requestChange(Double billAmount, Double preferredCoin) {
		var bill = validateAndGetAvailableBill(billAmount);
		var preferredDenomination = validateAndGetPreferredDenom(preferredCoin);
		var denominations = getChange(billAmount, preferredDenomination);
		return new ChangeResponse(bill.name(), denominations);
	}

	private Map<AvailableCoins, Integer> getChange(double amount, AvailableCoins preferredDenomination) {
		var changeDenominations = new HashMap<AvailableCoins, Integer>();

		var denominations = new ArrayList<>(availableDenominations.descendingKeySet());
		if (null != preferredDenomination) {
			denominations.remove(preferredDenomination);
			denominations.add(0, preferredDenomination);
		}

		for (AvailableCoins denomination : denominations) {
			if (amount <= 0)
				break;
			var numOfCoinsRequired = (int) (amount / denomination.getCoinValue());
			var numOfCoinsAvailable = availableDenominations.get(denomination);
			numOfCoinsRequired = numOfCoinsRequired <= numOfCoinsAvailable ? numOfCoinsRequired : numOfCoinsAvailable;
			if (numOfCoinsRequired > 0) {
				amount -= numOfCoinsRequired * denomination.getCoinValue();
				changeDenominations.put(denomination, numOfCoinsRequired);
			}
		}

		if (amount > 0)
			throw new InsufficientChangeException(INSUFFICIENT_COINS_ERROR_MSG);

		debitToAvailableDenominations(changeDenominations);
		return changeDenominations;
	}

	private void debitToAvailableDenominations(Map<AvailableCoins, Integer> changeDenominations) {
		changeDenominations.forEach((k, v) -> availableDenominations.put(k, availableDenominations.get(k) - v));
	}

	private AvailableCoins validateAndGetPreferredDenom(Double preferredCoin) {
		try {
			if (null != preferredCoin)
				return AvailableCoins.valueOf(preferredCoin);
		} catch (IllegalArgumentException e) {
			throw new InvalidDenominationException(e.getMessage());
		}
		return null;
	}

	private AvailableBills validateAndGetAvailableBill(Double billAmount) {
		try {
			return AvailableBills.valueOf(billAmount);
		} catch (IllegalArgumentException e) {
			throw new InvalidBillValueException(e.getMessage());
		}
	}

}
