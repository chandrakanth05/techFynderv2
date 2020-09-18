package com.techfynder.service;

import com.techfynder.util.GenericRes;

public abstract class TechFynder {
	
	public <T> GenericRes<T> success(T t) {
		return GenericRes.<T>builder().data(t).message("success").build();
	}

	public <T> GenericRes<T> error(String msg) {
		// return error(msg, null);
		return GenericRes.<T>builder().message(msg).status(false).build();
	}

}
