package com.ja.learning.hrms.service;

import java.util.HashMap;
import java.util.Map;

import com.ja.learning.hrms.exception.HrmsException;

/**
 * 
 * @author Jiby
 *This interface is for methods to populate default values and validations
 *Example in department list
 */
public interface GetDataServices {

	
	
	public Map<Integer,String> getDepartments() throws HrmsException;

}
