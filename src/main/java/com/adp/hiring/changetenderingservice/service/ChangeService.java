package com.adp.hiring.changetenderingservice.service;

import com.adp.hiring.changetenderingservice.models.ChangeResponse;

public interface ChangeService {

	ChangeResponse requestChange(Double billAmount, Double preferredCoin);

}
