package com.adp.hiring.changetenderingservice.config;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adp.hiring.changetenderingservice.constants.AvailableCoins;

@Configuration
public class Configurations {

	@Value("${app.initial.coins.loaded}")
	private int numberOfCoins;

	@Bean("availableDenominations")
	public TreeMap<AvailableCoins, Integer> getAvailableDenominations() {
		return new TreeMap<>(Map.of(AvailableCoins.¢1, numberOfCoins, AvailableCoins.¢5, numberOfCoins,
				AvailableCoins.¢10, numberOfCoins, AvailableCoins.¢25, numberOfCoins));
	}

}
