package com.ait.affirm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ait.affirm.dao.AffirmInfoToLGEPDAO;
import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.web.util.MailManager;
import com.ait.web.util.StringUtil;

/**
 * 发送裁决命令到LGEP
 * @author Administrator
 *
 */
@Service
@SuppressWarnings("unchecked")
public class AffirmInfoToLGEPSerImpl implements AffirmInfoToLGEPSer{
	
	/**
	 * 发送lgep的系统标识
	 * LGEP系统中chrs2.0专属ID
	 */
    @Value("${LGEP.system.id}")
	private String SYSTEM_ID;
	
	/**
	 * 删除与SYSTEM_PK相同的裁决信息
	 */
	private static String ARD = "ARD";
	
	/**
	 * 审批邀请：第一次插入此命令（如：加班申请）
	 */
	private static String ARI = "ARI";
	
	/**
	 * 指定下次审批人
	 */
	private static String ABY = "ABY";
	
	/**
	 * 批准,退回(Action)
	 */
	private static String AAI = "AAI";
	
	/**
	 * 审批最终完成，最后一个裁决人通过，或者有裁决人否决
	 */
	private static String AAF = "AAF";

	/**
	 * 委任命令
	 */
	private static String DLI = "DLI";
	
	/**
	 * 批准：用于 c2 AAI命令的值
	 */
	private static String PASS = "批准";
	
	/**
	 * 否决：用于 c2 AAI命令的值
	 */
	private static String REJECT = "否决";
	
	/**
	 * 否决：用于 c2 AAI命令的值
	 */
	private static String CHECK_RESULT = "CHECK";

	/**
	 * 审批邀请：用于 c2 ABY命令的值
	 */
	private static String AFFIRM = "审批邀请";

	/**
	 * check邀请：用于 c2 ABY命令的值
	 */
	private static String CHECK = "CHECK邀请";
	
	/**
	 * 服务器ip:端口号
	 */
    @Value("${serverIp}")
	private String SERVER_IP;
    
	@Autowired
	MailManager mailManger;
	
	@Autowired
	AffirmInfoToLGEPDAO affirmInfoToLGEPDAOImpl;
	
	/**
	 * 决裁：
	 * 	1、决裁完成:
	 * 		a.插入决裁情况（AAI）
	 * 		b.插入决裁结束命令（AAF）
	 * 	2、插入新的决裁项目(ARI)
	 * 		a.插入决裁情况（AAI）
	 * 		b.插入一级决裁人(ABY)
	 */
	public void affirm(LinkedHashMap paramMap) {
		try {
			LinkedHashMap mapAAI = composeAAI(instanceMap(),paramMap);
			if(paramMap.get("FINISH") == null){
				LinkedHashMap mapABY = composeABY(instanceMap(),paramMap);
				affirmInfoToLGEPDAOImpl.affirm(mapAAI,mapABY);
				
				mailManger.sendAffirmMail(paramMap.get("APPLY_TYPE").toString(), paramMap.get("CURRENT_AFFIRM_ID").toString());
			}else{
				LinkedHashMap mapAAF = composeAAF(instanceMap(),paramMap);
				affirmInfoToLGEPDAOImpl.affirmF(mapAAI,mapAAF);
				
				mailManger.sendResultMail(paramMap.get("APPLY_TYPE").toString(), paramMap.get("CREATED_BY").toString(), Integer.parseInt(paramMap.get("AFFIRM_FLAG").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建新裁决邀请：
	 * 	1、先删除SYSTEM_PK重复的邀请(ARD)
	 * 	2、插入新的决裁项目(ARI)
	 * 	3、插入一级决裁人(ABY)
	 */
	public void crateAffirm(LinkedHashMap paramMap) {
		try {
			LinkedHashMap mapARD = composeARD(instanceMap(),paramMap);
			LinkedHashMap mapARI = composeARI(instanceMap(),paramMap);
			LinkedHashMap mapABY = composeABY(instanceMap(),paramMap);
			
			affirmInfoToLGEPDAOImpl.crateAffirm(mapARD,mapARI,mapABY);
			//发送邮件
			mailManger.sendAffirmMail(paramMap.get("APPLY_TYPE").toString(), paramMap.get("CURRENT_AFFIRM_ID").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * CHECK：
	 * 		AAI
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	public void check(LinkedHashMap paramMap) {
		try {
			paramMap.put("CHECK_FLAG", "Y");
			affirmInfoToLGEPDAOImpl.insertAffirmInfo(composeAAI(instanceMap(),paramMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建check邀请：
	 * 	1、插入check人(ABY)
	 *     APPLY_NO:申请的seq
	 *	   APPLY_TYPE：申请类型（数字代码）
	 *	   AFFIRM_LEVEL：ess_check_no
	 *	   PRE_AFFIRM_EMPID: check人person_id
	 *	   ABY_URL: check页面url
	 */
	public void crateCheck(LinkedHashMap paramMap) {
		try {
			affirmInfoToLGEPDAOImpl.insertAffirmInfoNoDate(composeABYCheck(instanceMap(),paramMap));
			//发送邮件
			mailManger.sendCheckMail(paramMap.get("APPLY_TYPE").toString(), paramMap.get("PRE_AFFIRM_EMPID").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除审批信息：
	 * 	       未审批的申请删除后需要向LGEP发送删除命令。
	 */
	public void deleteAffirm(LinkedHashMap paramMap) {
		try {
			affirmInfoToLGEPDAOImpl.insertAffirmInfoNoDate(composeARD(instanceMap(),paramMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 创建委任：
	 * 	1、APROVAL_EMPID:当前审批人社号
	 *	   DELEGATE_EMPID：替换人社号
	 *	   START_DATE:创建时间 yyyy-mm-dd
	 */
	public void crateDelegate(LinkedHashMap paramMap) {
		try {
			affirmInfoToLGEPDAOImpl.insertAffirmInfoDelegate(composeDLI(instanceMap(),paramMap,"APD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消委任：
	 * 	1、APROVAL_EMPID:当前审批人社号
	 *	   DELEGATE_EMPID：替换人社号
	 *	   START_DATE:创建时间 yyyy-mm-dd
	 */
	public void deleteDelegate(LinkedHashMap paramMap) {
		try {
			affirmInfoToLGEPDAOImpl.insertAffirmInfoDelegate(composeDLI(instanceMap(),paramMap,"RESET_APD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap instanceMap(){
		LinkedHashMap map = new LinkedHashMap();
		map.put("SYSTEM_ID", SYSTEM_ID);
		map.put("SYSTEM_PK", "");
		map.put("NO", -1);
		map.put("C1", "");
		map.put("C2", "");
		map.put("C5", "");
		map.put("C4", "");
		map.put("C3", "");
		map.put("N2", -1);
		map.put("N1", -1);
		map.put("SABUN2", "");
		map.put("SABUN1", "");
		map.put("URL2", "");
		map.put("URL1", "");
		map.put("MSG", "");
		return map;
	}
	
	/**
	 * 组装ARD命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeARD(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", ARD);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));
		return  commandMap;
	}
	
	/**
	 * 组装ARI命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeARI(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", ARI);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));
		commandMap.put("C1", "01");
		commandMap.put("C2", paramMap.get("APPLY_TYPE_NAME"));
		commandMap.put("C5", paramMap.get("APPLY_TYPE_NAME"));
		commandMap.put("C3", paramMap.get("APPLY_TITLE"));
		commandMap.put("DATE1", "SYSDATE");
		commandMap.put("SABUN1", StringUtil.checkNull(paramMap.get("APPLY_EMPID"),"XT902"));
		commandMap.put("URL1", paramMap.get("ARI_URL").toString().replace("{serverIp}", SERVER_IP));
		return commandMap;
	}
	
	/**
	 * 组装ABY命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeABY(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", ABY);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));
		commandMap.put("NO", paramMap.get("AFFIRM_LEVEL"));
		commandMap.put("C1", "01");
		if(paramMap.get("CHECK")==null){
			commandMap.put("C2", AFFIRM);
		}else{
			commandMap.put("C2", CHECK);
		}
		commandMap.put("SABUN1", StringUtil.checkNull(paramMap.get("PRE_AFFIRM_EMPID"), "XT902")); 
		commandMap.put("URL1", paramMap.get("ABY_URL").toString().replace("{serverIp}", SERVER_IP));
		return commandMap;
	}
	

	/**
	 * 组装ABY Check命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeABYCheck(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", ABY);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));
		commandMap.put("NO", paramMap.get("AFFIRM_LEVEL"));
		commandMap.put("C1", "01");
		commandMap.put("C2", CHECK);
		commandMap.put("SABUN1", StringUtil.checkNull(paramMap.get("PRE_AFFIRM_EMPID"),"XT902")); 
		commandMap.put("URL1", paramMap.get("ABY_URL").toString().replace("{serverIp}", SERVER_IP));
		return commandMap;
	}
	
	/**
	 * 组装AAI命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeAAI(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", AAI);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));

		if(paramMap.get("CHECK_FLAG") == null){
			if(paramMap.get("FINISH") == null){
				commandMap.put("NO", Integer.parseInt(paramMap.get("AFFIRM_LEVEL").toString()) - 1 );
			}else{
				commandMap.put("NO", Integer.parseInt(paramMap.get("AFFIRM_LEVEL").toString()));
			}
			if("1".equals(paramMap.get("AFFIRM_FLAG").toString())){
				commandMap.put("C2", PASS);
			} else {
				commandMap.put("C2", REJECT);
			}
		}else{
			commandMap.put("NO", Integer.parseInt(paramMap.get("AFFIRM_LEVEL").toString()));
			commandMap.put("C2", CHECK_RESULT);
		}
		if(paramMap.get("FINISH") == null){
			commandMap.put("C1", "02");
		} else {
			commandMap.put("C1", "03");
		}
		commandMap.put("DATE1", "SYSDATE");
		commandMap.put("SABUN1", StringUtil.checkNull(paramMap.get("AFFIRM_EMPID"),"XT902"));
		commandMap.put("URL1", paramMap.get("AAI_URL").toString().replace("{serverIp}", SERVER_IP));
		return commandMap;
	}
	
	/**
	 * 组装AAF命令参数
	 * @param commandMap
	 * @param paramMap
	 */
	private LinkedHashMap composeAAF(LinkedHashMap commandMap, LinkedHashMap paramMap){
		commandMap.put("COMMAND", AAF);
		commandMap.put("SYSTEM_PK", paramMap.get("APPLY_NO") + "-" + paramMap.get("APPLY_TYPE"));
		commandMap.put("URL1", paramMap.get("AAF_URL").toString().replace("{serverIp}", SERVER_IP));
		return commandMap;
	}
	

	/**
	 * 组装DLI命令参数
	 * @param commandMap
	 * @param paramMap
	 * @param status 1、APD: 生成新委托.
	 *				 2、RESET_APD: 取消委托.
	 */
	private LinkedHashMap composeDLI(LinkedHashMap commandMap, LinkedHashMap paramMap,String status){
		commandMap.put("COMMAND", DLI);
		commandMap.put("C1", status);
		commandMap.put("SABUN1", StringUtil.checkNull(paramMap.get("APROVAL_EMPID"),"XT902"));
		commandMap.put("SABUN2", StringUtil.checkNull(paramMap.get("DELEGATE_EMPID"),"XT902"));
		commandMap.put("DATE1", StringUtil.checkNull(paramMap.get("START_DATE")));
		return commandMap;
	}
	
	/**
	 * 决裁：
	 * 	1、决裁完成:
	 * 		a.插入决裁情况（AAI）
	 * 		b.插入决裁结束命令（AAF）
	 * 	2、插入新的决裁项目(ARI)
	 * 		a.插入决裁情况（AAI）
	 * 		b.插入一级决裁人(ABY)
	 */
	public void affirmWithReqDetail(LinkedHashMap paramMap,List paramList) {
		try {
			LinkedHashMap mapAAI = composeAAI(instanceMap(),paramMap);
			LinkedHashMap reqInfoMap = this.makeHtmlTableForMst(paramList);	
			if(paramMap.get("FINISH") == null){
				LinkedHashMap mapABY = composeABY(instanceMap(),paramMap);
				affirmInfoToLGEPDAOImpl.affirm(mapAAI,mapABY);
				
				mailManger.sendAffirmMail(paramMap.get("APPLY_TYPE").toString(), paramMap.get("CURRENT_AFFIRM_ID").toString());
			}else{
				LinkedHashMap mapAAF = composeAAF(instanceMap(),paramMap);
				affirmInfoToLGEPDAOImpl.affirmF(mapAAI,mapAAF);
				
				mailManger.sendResultMailWithReqDetail(
						paramMap.get("APPLY_TYPE").toString()
						, paramMap.get("CREATED_BY").toString()
						, Integer.parseInt(paramMap.get("AFFIRM_FLAG").toString())
						, reqInfoMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap makeHtmlTableForMst(List reqList){
		LinkedHashMap rMap = new LinkedHashMap();
		String  reqMst= "";
		LinkedHashMap map = (LinkedHashMap)reqList.get(0);
		reqMst = "申请日期："+map.get("REQ_DATE").toString();
		String reqDtl = "<table width=\"100%\" border=\"1\">";
		reqDtl += "<thead> <tr>";
		reqDtl += "<th width=\"100\">促销员姓名</th>";
		reqDtl += "<th width=\"100\">身份证号</th>";
		reqDtl += "<th width=\"100\">社编</th>";			
		reqDtl += "</tr> </thead>";
		reqDtl += "<tbody>";		
		for(int i=0; i<reqList.size();i++){
			LinkedHashMap iMap = (LinkedHashMap) reqList.get(i);
			reqDtl += "<tr>";
			reqDtl += "  <td>"+iMap.get("LOCAL_NAME")+"</td>";
			reqDtl += "  <td>"+iMap.get("IDCARD_NO")+"</td>";
			reqDtl += "  <td>"+iMap.get("EMPID")+"</td>";
			reqDtl += "</tr>";
		}	
		reqDtl += "</tbody>";
		reqDtl += "</table> ";
		rMap.put("reqMst", reqMst);
		rMap.put("reqDtl", reqDtl);
		return rMap;
	}
}
