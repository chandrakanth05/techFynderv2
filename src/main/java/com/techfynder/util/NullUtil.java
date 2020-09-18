package com.techfynder.util;

import java.util.Collection;
import java.util.Objects;

public class NullUtil {

	
	public static boolean isValid(String s) {
		return s != null &&  !s.trim().isEmpty() && !s.equalsIgnoreCase("null") && !s.equalsIgnoreCase("");
	}

	public static boolean isValid(Number s) {
		return s != null && s.doubleValue() > -1  ;
	}
	
	public static boolean isValid(Collection<?> cols){
		return cols!=null && !cols.isEmpty();
	}
	
	public static boolean isValid(Object o) {
		return  !Objects.isNull(o);
	}

}
