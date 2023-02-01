package com.adp.hiring.changetenderingservice.models;

import java.util.Map;

import com.adp.hiring.changetenderingservice.constants.AvailableCoins;

public class ChangeResponse {

	private String bill;
	private Map<AvailableCoins, Integer> denominations;

	public ChangeResponse(String bill, Map<AvailableCoins, Integer> denominations) {
		this.bill = bill;
		this.denominations = denominations;
	}

	public String getBill() {
		return bill;
	}

	public Map<AvailableCoins, Integer> getDenominations() {
		return denominations;
	}

}
