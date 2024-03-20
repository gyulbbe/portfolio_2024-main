package com.hg.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public interface EmailDao {

	public HashMap<String, String> addEmail(HashMap<String, String> params);
	
}