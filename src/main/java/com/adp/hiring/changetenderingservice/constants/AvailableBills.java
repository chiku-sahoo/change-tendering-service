package com.adp.hiring.changetenderingservice.constants;

import static com.adp.hiring.changetenderingservice.constants.ErrorMessages.INVALID_BILL_VALUE_ERROR_MSG;

public enum AvailableBills {
	$1(1), $2(2), $5(5), $10(10), $20(20), $50(50), $100(100);

	private final double bill;

	AvailableBills(double value) {
		bill = value;
	}

	public double getBillValue() {
		return this.bill;
	}

	public static AvailableBills valueOf(double value) {
		for (var v : AvailableBills.values())
			if (v.getBillValue() == value)
				return v;

		throw new IllegalArgumentException(INVALID_BILL_VALUE_ERROR_MSG);
	}

}
