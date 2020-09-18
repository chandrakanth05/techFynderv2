package com.techfynder.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Currency {
	
	@Id
	private String id;
	private String baseCurrency;
	private Double baseValue; 
	private String convertionCurrency;
	private Double convertionValue;
	
	
	public Currency() {
		super();
	}


	public Currency(String baseCurrency, Double baseValue, String convertionCurrency, Double convertionValue) {
		super();
		this.baseCurrency = baseCurrency;
		this.baseValue = baseValue;
		this.convertionCurrency = convertionCurrency;
		this.convertionValue = convertionValue;
	}

	
	
}
