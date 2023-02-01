package com.adp.hiring.changetenderingservice.constants;

import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_DENOMINATION_ERROR_MSG;

public enum AvailableCoins {

	¢1(0.01), ¢5(0.05), ¢10(0.10), ¢25(0.25);

	private final double coinValue;

	AvailableCoins(double value) {
		coinValue = value;
	}

	public double getCoinValue() {
		return this.coinValue;
	}

	public static AvailableCoins valueOf(double value) {
		for (var v : AvailableCoins.values())
			if (v.getCoinValue() == value)
				return v;

		throw new IllegalArgumentException(INVALID_DENOMINATION_ERROR_MSG);
	}

}
