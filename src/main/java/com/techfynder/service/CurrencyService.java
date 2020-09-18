package com.techfynder.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.techfynder.Repository.CurrencyRepository;
import com.techfynder.model.Currency;
import com.techfynder.util.GenericRes;
import com.techfynder.util.NullUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CurrencyService extends TechFynder{

	@Autowired
	private CurrencyRepository curRepo;
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	public List<Currency> getAllBaseCurrency(){
		return curRepo.findAll();
	}
	
	public GenericRes<?> convertDollarToAny(Double amount, String from, String to){
			Double d = 0.0;
		try{
			/**
			 * Validating input value
			 */
			if(!(NullUtil.isValid(amount) && (NullUtil.isValid(from) && NullUtil.isValid(to) ))){
				return error("Input parameters amount and type should not be null");	
			}
			
			/**
			 *  Calling Third Party API Currency Conversion
			 */
			Double valu  = getCurrecnyConversionApi(from,to); 
			System.out.println("Conversion Currency Value"+valu);
			d = amount*valu;
			
			/**
			 * based on type we are reading records from db
			 */
			Query query = new Query();
			query.addCriteria(Criteria.where("convertion_currency").regex(to));
			Currency cur = mongoTemplate.find(query, Currency.class).parallelStream().findAny().orElse(null);
			/**
			 * Updating existing currency value with new currency value.
			 */
			if(NullUtil.isValid(cur)){
				Update update = new Update(); 
				update.set("convertion_value", valu);
				mongoTemplate.updateFirst(query, update, Update.class);
			}else{
				/**
				 * Insert new record in currency table.
				 */
				Currency obj = Currency.builder()
								.baseCurrency(from)
								.baseValue(1.0)
								.convertionCurrency(to)
								.convertionValue(valu)
								.build();
				
				curRepo.save(obj);
			}
			/**
			 * Then every thing goes correct then show success message;
			 */
			return success(d);
		}catch(Exception e){
			e.printStackTrace();
			log.info("Exceptin occured in convertDollarToAny()"+e.getMessage());
			return error("Something went wrong"+e.getMessage());
		}
	}
	
	
	public Double getCurrecnyConversionApi(String baseCurrency , String conversionCurrency){
		Double d = 0.0;
		try{
			// Setting URL
			String url_str = "https://v6.exchangerate-api.com/v6/cfa7f8cb53d3cde38e869526/latest/"+baseCurrency;

			// Making Request
			URL url = new URL(url_str);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();

			InputStreamReader reader = new InputStreamReader((InputStream) request.getContent());
			
			// Convert to JSON
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			JSONObject resut = (JSONObject) jsonObject.get("conversion_rates");
			
			d = (Double) resut.get(conversionCurrency);
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("Exception occured at getCurrecnyConversionApi()"+e.getMessage());
		}
		return d;
		
	}
	
	
}
