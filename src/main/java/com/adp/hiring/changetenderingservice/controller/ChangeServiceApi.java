package com.adp.hiring.changetenderingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adp.hiring.changetenderingservice.models.ChangeResponse;
import com.adp.hiring.changetenderingservice.service.ChangeService;

@RestController
@RequestMapping("/change-service")
public class ChangeServiceApi {

	@Autowired
	private ChangeService changeService;

	@GetMapping("/$request-change")
	public ResponseEntity<ChangeResponse> requestChange(@RequestParam(name = "bill", required = true) Double bill,
			@RequestParam(name = "preferred_denomination", required = false) Double preferredDenomination) {
		var changeResponse = changeService.requestChange(bill, preferredDenomination);
		return ResponseEntity.ok(changeResponse);
	}

}
