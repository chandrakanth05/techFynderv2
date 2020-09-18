package com.techfynder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techfynder.model.Currency;
import com.techfynder.service.CurrencyService;
import com.techfynder.util.GenericRes;

@RestController
@RequestMapping("/forex")
public class CurrencyController {
	
	@Autowired
	private CurrencyService currencyService;
	
	@GetMapping("/all")
	public List<Currency> getAllBaseCurrency(){
		return currencyService.getAllBaseCurrency();
	}
	
	@GetMapping("/convert-dollar-to-any")
	public GenericRes<?> convertDollarToAny(@RequestParam(value="amount") Double amount,
										@RequestParam(value="base_currency") String from,@RequestParam(value="conversion_curency") String to) {
		return currencyService.convertDollarToAny(amount,from,to);
	}
	
}
