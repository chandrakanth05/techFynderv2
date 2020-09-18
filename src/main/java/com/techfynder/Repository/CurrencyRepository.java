package com.techfynder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.techfynder.model.Currency;

public interface CurrencyRepository extends MongoRepository<Currency, String>{

}
