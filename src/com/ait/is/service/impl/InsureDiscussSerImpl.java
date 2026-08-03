package com.ait.is.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.InsureDiscussDao;
import com.ait.is.service.InsureDiscussSer;
@Service
public class InsureDiscussSerImpl implements InsureDiscussSer{
	
	@Autowired
	InsureDiscussDao insureDiscussDao;

	@Override
	public void paBenCompuationCreateApplyBz(Map map) {
		insureDiscussDao.paBenCompuationCreateApplyBz(map);
		
	}

	@Override
	public int checkExistsForPaBenApplyBz(Map param) {
		
		return insureDiscussDao.checkExistsForPaBenApplyBz(param);
	}

	@Override
	public int checkPaBenPaymentInfoBz(Map param) {
		
		return insureDiscussDao.checkPaBenPaymentInfoBz(param);
	}

	@Override
	public int checkPaCalBz(Map map) {
		
		return insureDiscussDao.checkPaCalBz(map);
	}

	@Override
	public void paBenCompuationCancelApplyBz(Map map) {
		insureDiscussDao.paBenCompuationCancelApplyBz(map);
	}

	@Override
	public List getPaBenManageLastCurrBz(Map param) {
		
		return insureDiscussDao.getPaBenManageLastCurrBz(param);
	}

	@Override
	public List getPaBenPaymentInfoListBz(Map param) {
		
		return insureDiscussDao.getPaBenPaymentInfoListBz(param);
	}

	@Override
	public List<Map> getAdjustValueBz(Map param) {
		
		return insureDiscussDao.getAdjustValueBz(param);
	}

}
