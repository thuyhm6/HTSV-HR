package com.ait.sys.service;

import java.util.List;

public interface SyLanguageSer {
	
	@SuppressWarnings("unchecked")
	public List getSyLanguageListByActivity();
	
	@SuppressWarnings("unchecked")
	public List getSyLanguageNameListByActivity(Object object);
}
