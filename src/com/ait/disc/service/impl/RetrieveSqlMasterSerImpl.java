package com.ait.disc.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.disc.dao.RetrieveSqlMasterDao;
import com.ait.disc.service.RetrieveSqlMasterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.ReadFile;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
import com.ibatis.sqlmap.engine.mapping.parameter.InlineParameterMapParser;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.mapping.sql.SqlText;
import com.ibatis.sqlmap.engine.type.TypeHandlerFactory;


@Service
public class RetrieveSqlMasterSerImpl implements RetrieveSqlMasterSer {

	@Autowired
	private RetrieveSqlMasterDao retrievesqlmasterdao;

	/* 
	* Title: getSqlMasterList
	* Description:查询自动下载excel列表
	* @author 孙鹏  
	* @date 2014年10月22日 上午9:16:38  
	* @param request
	* @return
	* @throws SQLException 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#getSqlMasterList(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public List getSqlMasterList(HttpServletRequest request)
			throws SQLException {
		List returnlist=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("language", Messages.getLanguage(request));
		
		//paramMap.put("SUBSD_CD", admin.getCpnyId());
		//paramMap.put("PGM_NM",admin.getDeptNo());
		//paramMap.put("PGM_NM","PAY");
		
		/*if (UiUtil.getPageNum(request) > 0){
			returnlist=this.retrievesqlmasterdao.getRetrieveSqlMasterList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		}
		else{
			returnlist=this.retrievesqlmasterdao.getRetrieveSqlMasterList(paramMap);
		}*/
		returnlist=this.retrievesqlmasterdao.getRetrieveSqlMasterList(paramMap);
		return returnlist;
	}

	/* 
	* Title: getSqlMasterListCnt
	* Description:查询excel报表的数量
	* @author 孙鹏  
	* @date 2014年10月22日 上午11:08:47  
	* @param request
	* @return
	* @throws SQLException 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#getSqlMasterListCnt(javax.servlet.http.HttpServletRequest) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int getSqlMasterListCnt(HttpServletRequest request)
			throws Exception {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		//paramMap.put("CPNY_ID", admin.getCpnyId());
		ListCnt =  this.retrievesqlmasterdao.getRetrieveSqlMasterListCnt(paramMap);
		
		return ListCnt;
	}

	/* 
	* Title: insertSqlMaster
	* Description:sql报表插入
	* @author 孙鹏  
	* @date 2014年10月22日 下午4:38:34  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#insertSqlMaster(javax.servlet.http.HttpServletRequest) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int insertSqlMaster(HttpServletRequest request) throws Exception {
		int returnInt =0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    if (paramMap.get("SQL_STAT") != null
	                && ((String) paramMap.get("SQL_STAT")).equalsIgnoreCase("Y")) {
	        } else {
	        	paramMap.put("SQL_STAT", "N");
	        }
		paramMap.put("UPDT_USER", admin.getUserNo());//操作人
		//paramMap.put("RGST_DTIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		//paramMap.put("UPDT_DTIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int SQL_SEQ=this.retrievesqlmasterdao.insertSqlMaster(paramMap);//
		if(SQL_SEQ>0){
			paramMap.put("SQL_SEQ", SQL_SEQ);
			returnInt=this.upMasterToParam(paramMap);//插入报表param参数
		}
		return returnInt;
	}

	/* 
	* Title: getSqlMaster
	* Description:
	* @author 孙鹏  
	* @date 2014年10月23日 上午9:57:44  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#getSqlMaster(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public Object getSqlMaster(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String PGM_NMurl=(String) paramMap.get("PGM_NMurl");
		paramMap.put("language", Messages.getLanguage(request));
		List list=this.retrievesqlmasterdao.getSqlMaster(paramMap);
		Map master=new HashMap();
		if(list.size()>0){
			paramMap= (Map) list.get(0);
		}
		paramMap.put("PGM_NMurl", PGM_NMurl);
		Clob SQL_STMT = ((Clob)paramMap.get("SQL_STMT"));
		if(null==SQL_STMT||SQL_STMT.equals("")){
			paramMap.put("SQL_STMT", "");
		}else{
		paramMap.put("SQL_STMT", SQL_STMT.getSubString(1, (int)SQL_STMT.length()));
		}
		return paramMap;
	}

	/* 
	* Title: updateSqlMaster
	* Description:报表更新
	* @author 孙鹏  
	* @date 2014年10月23日 上午9:57:48  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#updateSqlMaster(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int updateSqlMaster(HttpServletRequest request) throws Exception {
		int returnInt =0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    if (paramMap.get("SQL_STAT") != null
                && ((String) paramMap.get("SQL_STAT")).equalsIgnoreCase("Y")) {
        } else {
        	paramMap.put("SQL_STAT", "N");
        }
		paramMap.put("UPDT_USER", admin.getUserNo());//操作人
		returnInt=this.retrievesqlmasterdao.updateSqlMaster(paramMap);//
		if(returnInt>0){
			returnInt=this.upMasterToParam(paramMap);//插入报表param参数
		}
		return returnInt;
		
	}
    /** 
    * @Title: upMasterToParam 
    * @Description: TODO 将sql报表的sql语句的条件，插入到sql参数表中
    * @param @param reuqest
    * @param @return
    * @param @throws Exception    
    * @return int    
    * @throws 
    */
    protected int  upMasterToParam(Map paramMap) throws Exception{
    	int returnInt =0;
    	
    	List masterList= (List) this.retrievesqlmasterdao.getSqlMaster(paramMap);
    	Map inputData=(Map) masterList.get(0);
    	Clob SQL_STMT = ((Clob)inputData.get("SQL_STMT"));
    	if(null==SQL_STMT || SQL_STMT.equals("")){
    		inputData.put("SQL_STMT", "");
    	}else{
    	inputData.put("SQL_STMT", SQL_STMT.getSubString(1, (int)SQL_STMT.length()));
    	}
        InlineParameterMapParser inlineParam = new com.ibatis.sqlmap.engine.mapping.parameter.InlineParameterMapParser();
        SqlText sqlText = inlineParam.parseInlineParameterMap(new TypeHandlerFactory(), inputData.get("SQL_STMT").toString(),null);
        ParameterMapping[] mappingArray = sqlText.getParameterMappings();
        ArrayList paramList = new ArrayList() ;
        returnInt=this.retrievesqlmasterdao.updateSqlParamUseN(paramMap);
        if(returnInt>0){
            for (int i=0;i<mappingArray.length;i++){
                int j = 0 ;
                if (!paramList.contains(mappingArray[i].getPropertyName())){
                    paramList.add(j,mappingArray[i].getPropertyName());
                    //if exists in database
                    inputData.put("PARAM",mappingArray[i].getPropertyName());
                    Map oneparam =null;
                    oneparam = this.retrievesqlmasterdao.getSqlParam(inputData);
                    UserConfiguration config = UserConfiguration.getInstance("/typecode.properties");
                    
                    //将 配置的保留字 自动补充参数。
                    String paramType = "report.parameter.type.gudingcanshu";
                	paramType=config.getString(paramType);
                	String paramEndesc=null;
                	String paramCndesc=null;
                	String paramSQLtype=null;
                	String paramSQLtypeDesc=null;
                	String paramSoctcd=null;
                	String[] strtype=paramType.split(",");
                	for(String type:strtype){
                		if(type.equals(inputData.get("PARAM"))){//固定类型被发现
                			paramEndesc=inputData.get("PARAM")+".ENDESC";//获取固定熟悉的porpect的地址
                			paramCndesc=inputData.get("PARAM")+".CNDESC";
                			paramSQLtype=inputData.get("PARAM")+".SQLTP";
                			paramSQLtypeDesc=inputData.get("PARAM")+".SQLTPDESC";
                			paramSoctcd=inputData.get("PARAM")+".SOCTCD";
                			
                			paramEndesc=config.getString(paramEndesc);
                            paramCndesc=config.getString(paramCndesc);
                            paramSQLtype=config.getString(paramSQLtype);
                            paramSQLtypeDesc=config.getString(paramSQLtypeDesc);
                            paramSoctcd=config.getString(paramSoctcd);
                            if(oneparam == null ){
	                            inputData.put("EN_SQL_PARAM_DESC", paramEndesc);
	                            inputData.put("CN_SQL_PARAM_DESC", paramCndesc);
	                            inputData.put("SQL_PARAM_TP", paramSQLtype);
	                            inputData.put("SQL_PARAM_TP_DESC", paramSQLtypeDesc);
	                            inputData.put("SORT_CD", paramSoctcd);
                            }else{
	                            oneparam.put("EN_SQL_PARAM_DESC", paramEndesc);
	                            oneparam.put("CN_SQL_PARAM_DESC", paramCndesc);
	                            oneparam.put("SQL_PARAM_TP", paramSQLtype);
	                            oneparam.put("SQL_PARAM_TP_DESC", paramSQLtypeDesc);
	                            oneparam.put("SORT_CD", paramSoctcd);
                            }
                            break;
                		}
                	}
                    if (oneparam == null){
                    	                   	                   
	                    	returnInt=this.retrievesqlmasterdao.insertSqlParam(inputData);
	                    	inputData.put("EN_SQL_PARAM_DESC", "");
	                    	inputData.put("CN_SQL_PARAM_DESC", "");
	                    	inputData.put("SQL_PARAM_TP", "");
	                    	inputData.put("SQL_PARAM_TP_DESC", "");
	                    	inputData.put("SORT_CD", "");
                    	
                    }
                    else {

                    	returnInt=this.retrievesqlmasterdao.updateSqlParam(oneparam);
                    }
                    j++;
                }
            }
        }
    	return returnInt;
    }
	/* 
	* Title: updateSqlParam
	* Description:更新sql参数表
	* @author 孙鹏  
	* @date 2014年10月23日 上午9:57:51  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#updateSqlParam(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int updateSqlParam(HttpServletRequest request) throws Exception {
		int returnInt =0;
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			if (count==0){
				return 1;
			}
			for (int i = 0; i < count; i++) {
				if(request.getParameter("PARAM"+i)!=null){
					paramMap.put("UPDT_USER", admin.getUserNo());//操作人
					paramMap.put("SQL_PARAM_NO", request.getParameter("SQL_PARAM_NO"+i));//
					paramMap.put("PARAM", request.getParameter("PARAM"+i));//
					paramMap.put("EN_SQL_PARAM_DESC", request.getParameter("EN_SQL_PARAM_DESC"+i));//
					paramMap.put("CN_SQL_PARAM_DESC", request.getParameter("CN_SQL_PARAM_DESC"+i));//
					paramMap.put("SQL_PARAM_TP", request.getParameter("seach_SQL_PARAM_TP"+i));//
					paramMap.put("SQL_PARAM_TP_DESC", request.getParameter("SQL_PARAM_TP_DESC"+i));//
					paramMap.put("SORT_CD", request.getParameter("SORT_CD"+i));//
				 returnInt=this.retrievesqlmasterdao.updateSqlParam(paramMap);//
				}
			}
			} catch (Exception e) {
					e.printStackTrace();
					return returnInt;
			}
			return returnInt;
	}

	@Override
	public List getSqlParam(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertSqlParam(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/* 
	* Title: getSqlParamList
	* Description:查询报表参数列表
	* @author 孙鹏  
	* @date 2014年10月23日 下午2:20:56  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#getSqlParamList(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public List getSqlParamList(HttpServletRequest request) throws Exception {
		List returnlist=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		returnlist=this.retrievesqlmasterdao.getSqlParamList(paramMap);
		return returnlist;
	}

	/* 
	* Title: deleteSqlMaster
	* Description:报表删除。外加参数一并删除
	* @author 孙鹏  
	* @date 2014年10月23日 下午7:59:29  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#deleteSqlMaster(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int deleteSqlMaster(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			//String id = request.getParameter("SQL_SEQ");
			//if (ids != null && ids.length > 0) {
				//for (String id : ids) {
					//paramMap.put("SQL_SEQ", id);
					this.retrievesqlmasterdao.deleteSqlMaster(paramMap);
					this.retrievesqlmasterdao.deleteSqlParam(paramMap);
				//}
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		
		return 1;
	}

	/* 
	* Title: deleteSqlParamList
	* Description:删除报表参数数据
	* @author 孙鹏  
	* @date 2014年10月28日 上午10:51:23  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.service.RetrieveSqlMasterSer#deleteSqlParamList(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int deleteSqlParamList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
