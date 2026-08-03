package com.ait.affirm.dao;

public interface AffirmInfoToLGEPDAO {
	public void insertAffirmInfo(Object object)  throws Exception;
	
	public void insertAffirmInfoNoDate(Object object)  throws Exception;
	
	public void insertAffirmInfoDelegate(Object object)  throws Exception;
	
	public void crateAffirm(Object mapARD, Object mapARI, Object mapABY)  throws Exception;

	public void affirm(Object mapAAI, Object mapABY)  throws Exception;

	public void affirmF(Object mapAAI, Object mapAAF)  throws Exception;
}
