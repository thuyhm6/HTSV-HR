 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工在职证明--%>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
<style type="text/css">
	td {
		text-align: center;
	}
</style>
</head>   
                           
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=GongZiHuiZongBiao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   
   <table width="1000" border="1">
	  <tr>
	    <td colspan="28"><font size="+4">乐天（中国）食品有限公司2011年11月考勤工资汇总表</font></td>
	  </tr>
	  <tr>
	    <td colspan="4" rowspan="2">分类</td>
	    <td rowspan="2">人数</td>
	    <td colspan="11">工  资  构  成  项</td>
	    <td rowspan="2">应得合计</td>
	    <td colspan="4">扣款项</td>
	    <td rowspan="2">工资总额</td>
	    <td colspan="4">保  险  福  利  项</td>
	    <td rowspan="2">税金</td>
	    <td rowspan="2">实发金额</td>
	  </tr>
	  <tr>
	    <td>基本工资</td>
	    <td>住房补贴</td>
	    <td>交通费</td>
	    <td>职贴</td>
	    <td>岗贴</td>
	    <td>加班费</td>
	    <td>夜班费</td>
	    <td>福利费</td>
	    <td>全勤补贴</td>
	    <td>其他</td>
	    <td>管理费</td>
	    <td>病假</td>
	    <td>事假</td>
	    <td>其他</td>
	    <td>补扣</td>
	    <td>医疗</td>
	    <td>养老</td>
	    <td>失业</td>
	    <td>住房</td>
	  </tr>
	  <tr>
	    <td rowspan="27"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>正</p><p>式</p><p>工</p></td>
	    <td rowspan="14">生产部</td>
	    <td rowspan="8">直接</td>
	    <%int num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
	    <c:forEach items="${viewPaSummarizeList}" var = "temp">
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0421'}">
	    	<%
	    	if(0!=num){
	    	%>	
	    		<tr>
	    	<%	
	    	}
	    	%>
		    	<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
		    	<%
		    	if(0!=num){
		    	%>	
		    		</tr>
		    	<%	
		    	}
		    	num=1;
		    	%>
	    	</c:if>
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0426'}">
	    		<%
		    	if(0!=num){
		    	%>	
		    		<tr>
		    	<%	
		    	}
		    	%>
		    	<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
		    	<%
		    	if(0!=num){
		    	%>	
		    		</tr>
		    	<%	
		    	}
		    	num=1;
		    	%>
	    	</c:if>
	    </c:forEach>
	  </tr>  
	  <tr style="background-color: yellow">
	    <td>直接小计</td>
	    <c:forEach items="${zhengShiShengChanXiaoJi}" var = "temp">
	    	<c:if test="${temp.PRODUCTION_DISTINGUISH=='3328'}">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <c:forEach items="${viewPaSummarizeQiTaList}" var = "temp">
	    	<c:if test="${temp.DEPTNO=='C0419'}">
	    		<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <c:forEach items="${viewPaSummarizeQiTaList}" var = "temp">
	    	<c:if test="${temp.DEPTNO=='C0418'}">
	    		<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <c:forEach items="${viewPaSummarizeQiTaList}" var = "temp">
	    	<c:if test="${temp.DEPTNO=='C043'}">
	    		<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <c:forEach items="${viewPaSummarizeQiTaList}" var = "temp">
	    	<c:if test="${temp.DEPTNO=='C047'}">
	    		<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	  	<td style="background-color: white">&nbsp;</td>
	    <td>间接小计</td>
	    <c:forEach items="${zhengShiShengChanXiaoJi}" var = "temp">
	    	<c:if test="${temp.PRODUCTION_DISTINGUISH==null}">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	    <td colspan="2">合计</td>
	    <c:forEach items="${zhengShiShengChanXiaoJi}" var = "temp">
	    	<c:if test="${temp.PRODUCTION_DISTINGUISH=='ZONGJI'}">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td rowspan="11">管理部</td>
	    <td rowspan="11">间接</td>
	    <%num = 0; %>
	    <c:forEach items="${zhengShiGuanLi}" var = "temp">
	    	<%
	    	if(0!=num){
	    	%>	
	    		<tr>
	    	<%	
	    	}
	    	%>
	    		<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSEUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCETANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHTHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
				<%
		    	if(0!=num){
		    	%>	
		    		</tr>
		    	<%	
		    	}
		    	num=1;
		    	%>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td>外籍领导</td>
	    <c:forEach items="${zhuZaiYuan}" var = "temp">
			    <td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
	    </c:forEach>
	  </tr>	
	  <tr  style="background-color: yellow">
	    <td>小计</td>
	    <c:forEach items="${zhengShiGuanLiXiaoJi}" var = "temp">
	    	<c:forEach items="${zhuZaiYuan}" var = "item">
		    	<td>${temp.RS + item.RS}</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S + item.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S + item.HOUSE_SUBSIDIES}</td>
		    	<td>${temp.TRANSPORT_FEE_S + item.TRANSPORT_FEE}</td>
		    	<td>${temp.RANK_ALLOWANCE_S + item.RANK_ALLOWANCE}</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S + item.JOB_ALLOWANCE_STANDARD}</td>
		    	<td>${temp.TOTAL_OT_FEE_S + item.TOTAL_OT_FEE}</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S + item.NIGHT_SHIFT_FEE}</td>
		    	<td>${temp.WELFARE_S + item.WELFARE}</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S + item.FULL_ATTENDANCE_ALLOWANCE}</td>
		    	<td>${temp.QITABUZHU_S + item.QITABUZHU}</td>
		    	<td>${temp.MANAGEMENT_FEE_S + item.MANAGEMENT_FEE}</td>
		    	<td>${temp.YINGDEHEJI_S + item.YINGDEHEJI}</td>
		    	<td>${temp.SICK_CHARGE_BACK_S + item.SICK_CHARGE_BACK}</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S + item.LEAVE_CHARGE_BACK}</td>
		    	<td>${temp.QITAKOU_S + item.QITAKOU}</td>
		    	<td>${temp.BUKOU_S + item.BUKOU}</td>
		    	<td>${temp.GONGZIZONGE_S + item.GONGZIZONGE}</td>
		    	<td>${temp.MEDICAL_PERSONAL_S + item.MEDICAL_PERSONAL}</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S + item.ENDOWMENT_PERSONAL}</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S + item.UNEMPLOYMENT_PERSONAL}</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S + item.HOUSE_FUNDING_PERSONAL}</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S + item.PERSONAL_INCOME_TAX}</td>
		    	<td>${temp.SHIFAJINE_S+ item.SHIFAJINE }</td>
		    </c:forEach>	
	    </c:forEach>
	  </tr>
	   <tr style="background-color: yellow">
	    <td colspan="3">中方合计</td>
	    <c:forEach items="${zhengShiGuanLiXiaoJi}" var = "temp">
	    	<c:forEach items="${zhengShiShengChanXiaoJi}" var = "item">
	    		<c:if test="${item.PRODUCTION_DISTINGUISH=='ZONGJI'}">
			    	<td>${temp.RS + item.RS}</td>
			    	<td>${temp.DESERVE_BASIC_WAGE_S + item.DESERVE_BASIC_WAGE_S }</td>
			    	<td>${temp.HOUSE_SUBSIDIES_S + item.HOUSE_SUBSIDIES_S}</td>
			    	<td>${temp.TRANSPORT_FEE_S + item.TRANSPORT_FEE_S}</td>
			    	<td>${temp.RANK_ALLOWANCE_S + item.RANK_ALLOWANCE_S}</td>
			    	<td>${temp.JOB_ALLOWANCE_STANDARD_S + item.JOB_ALLOWANCE_STANDARD_S}</td>
			    	<td>${temp.TOTAL_OT_FEE_S + item.TOTAL_OT_FEE_S}</td>
			    	<td>${temp.NIGHT_SHIFT_FEE_S + item.NIGHT_SHIFT_FEE_S}</td>
			    	<td>${temp.WELFARE_S + item.WELFARE_S}</td>
			    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S + item.FULL_ATTENDANCE_ALLOWANCE_S}</td>
			    	<td>${temp.QITABUZHU_S + item.QITABUZHU_S}</td>
			    	<td>${temp.MANAGEMENT_FEE_S + item.MANAGEMENT_FEE_S}</td>
			    	<td>${temp.YINGDEHEJI_S + item.YINGDEHEJI_S}</td>
			    	<td>${temp.SICK_CHARGE_BACK_S + item.SICK_CHARGE_BACK_S}</td>
			    	<td>${temp.LEAVE_CHARGE_BACK_S + item.LEAVE_CHARGE_BACK_S}</td>
			    	<td>${temp.QITAKOU_S + item.QITAKOU_S}</td>
			    	<td>${temp.BUKOU_S + item.BUKOU_S}</td>
			    	<td>${temp.GONGZIZONGE_S + item.GONGZIZONGE_S}</td>
			    	<td>${temp.MEDICAL_PERSONAL_S + item.MEDICAL_PERSONAL_S}</td>
			    	<td>${temp.ENDOWMENT_PERSONAL_S + item.ENDOWMENT_PERSONAL_S}</td>
			    	<td>${temp.UNEMPLOYMENT_PERSONAL_S + item.UNEMPLOYMENT_PERSONAL_S}</td>
			    	<td>${temp.HOUSE_FUNDING_PERSONAL_S + item.HOUSE_FUNDING_PERSONAL_S}</td>
			    	<td>${temp.PERSONAL_INCOME_TAX_S + item.PERSONAL_INCOME_TAX_S}</td>
			    	<td>${temp.SHIFAJINE_S+ item.SHIFAJINE_S }</td>
		    	</c:if>
		    </c:forEach>	
	    </c:forEach>
	  </tr>	
	  <tr style="background-color: yellow">
	    <td colspan="3">合计</td>
	    <c:forEach items="${zhengShiShengChanXiaoJi}" var = "temp">
	    	<c:if test="${temp.PRODUCTION_DISTINGUISH=='ZONGJI'}">
		    	<c:forEach items="${zhengShiGuanLiXiaoJi}" var = "item">
		    		<c:forEach items="${zhuZaiYuan}" var = "temp2">
				    	<td>${temp.RS + item.RS + temp2.RS}</td>
				    	<td>${temp.DESERVE_BASIC_WAGE_S + item.DESERVE_BASIC_WAGE_S + temp2.DESERVE_BASIC_WAGE }</td>
				    	<td>${temp.HOUSE_SUBSIDIES_S  + item.HOUSE_SUBSIDIES_S + temp2.HOUSE_SUBSIDIES  }</td>
				    	<td>${temp.TRANSPORT_FEE_S  + item.TRANSPORT_FEE_S + temp2.TRANSPORT_FEE}</td>
				    	<td>${temp.RANK_ALLOWANCE_S  + item.RANK_ALLOWANCE_S  + temp2.RANK_ALLOWANCE  }</td>
				    	<td>${temp.JOB_ALLOWANCE_STANDARD_S  + item.JOB_ALLOWANCE_STANDARD_S + temp2.JOB_ALLOWANCE_STANDARD }</td>
				    	<td>${temp.TOTAL_OT_FEE_S  + item.TOTAL_OT_FEE_S + temp2.TOTAL_OT_FEE }</td>
				    	<td>${temp.NIGHT_SHIFT_FEE_S  + item.NIGHT_SHIFT_FEE_S + temp2.NIGHT_SHIFT_FEE }</td>
				    	<td>${temp.WELFARE_S  + item.WELFARE_S+ temp2.WELFARE  }</td>
				    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S   + item.FULL_ATTENDANCE_ALLOWANCE_S+ temp2.FULL_ATTENDANCE_ALLOWANCE }</td>
				    	<td>${temp.QITABUZHU_S   + item.QITABUZHU_S+ temp2.QITABUZHU }</td>
				    	<td>${temp.MANAGEMENT_FEE_S  + item.MANAGEMENT_FEE_S+ temp2.MANAGEMENT_FEE  }</td>
				    	<td>${temp.YINGDEHEJI_S   + item.YINGDEHEJI_S+ temp2.YINGDEHEJI  }</td>
				    	<td>${temp.SICK_CHARGE_BACK_S   + item.SICK_CHARGE_BACK_S+ temp2.SICK_CHARGE_BACK  }</td>
				    	<td>${temp.LEAVE_CHARGE_BACK_S   + item.LEAVE_CHARGE_BACK_S+ temp2.LEAVE_CHARGE_BACK  }</td>
				    	<td>${temp.QITAKOU_S   + item.QITAKOU_S+ temp2.QITAKOU  }</td>
				    	<td>${temp.BUKOU_S   + item.BUKOU_S+ temp2.BUKOU }</td>
				    	<td>${temp.GONGZIZONGE_S   + item.GONGZIZONGE_S+ temp2.GONGZIZONGE  }</td>
				    	<td>${temp.MEDICAL_PERSONAL_S   + item.MEDICAL_PERSONAL_S+ temp2.MEDICAL_PERSONAL }</td>
				    	<td>${temp.ENDOWMENT_PERSONAL_S   + item.ENDOWMENT_PERSONAL_S+ temp2.ENDOWMENT_PERSONAL }</td>
				    	<td>${temp.UNEMPLOYMENT_PERSONAL_S   + item.UNEMPLOYMENT_PERSONAL_S + temp2.UNEMPLOYMENT_PERSONAL }</td>
				    	<td>${temp.HOUSE_FUNDING_PERSONAL_S   + item.HOUSE_FUNDING_PERSONAL_S+ temp2.HOUSE_FUNDING_PERSONAL }</td>
				    	<td>${temp.PERSONAL_INCOME_TAX_S  + item.PERSONAL_INCOME_TAX_S + temp2.PERSONAL_INCOME_TAX }</td>
				    	<td>${temp.SHIFAJINE_S   + item.SHIFAJINE_S+ temp2.SHIFAJINE }</td>
				    </c:forEach>		
		    	</c:forEach>
	    	</c:if>
	    </c:forEach>
	  </tr>
	  <tr>
	    <td rowspan="16"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>劳</p><p>务</p><p>工</p></td>
	    <td rowspan="11">生产部</td>
	    <td rowspan="8">直接</td>
	    <%num = 0; %>
	    <c:forEach items="${viewPaSummarizeLaoWuList}" var = "temp">
	    <c:if test="${temp.DEPTNO!='C043'}">
	    	<%
	    	if(0!=num){
	    	%>	
	    		<tr>
	    	<%	
	    	}
	    	%>
		    	<td>${temp.DEPT_NAME }</td>
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
		    	<td>${temp.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE }</td>
		    	<td>${temp.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK }</td>
		    	<td>${temp.QITAKOU }</td>
		    	<td>${temp.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE }</td>
		    	<td>${temp.MEDICAL_PERSONAL }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX }</td>
		    	<td>${temp.SHIFAJINE }</td>
		    	<%
		    	if(0!=num){
		    	%>	
		    		</tr>
		    	<%	
		    	}
		    	num=1;
		    	%>
		    	</c:if>
	    </c:forEach>
	  </tr>
	 
	  <tr style="background-color: yellow">
	    <td>直接小计</td>
	    <c:forEach items="${viewPaSummarizeLaoWuXiaoJiList}" var = "temp">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	    </c:forEach>
	  </tr>
	  <tr>
	  				<td>&nbsp;</td>
	    	<c:forEach items="${viewPaSummarizeLaoWuList}" var = "temp">
	    		<c:if test="${temp.DEPTNO=='C043'}">
	    			<td>${temp.DEPT_NAME }</td>
	    			<td>${temp.RS }</td>
			    	<td>${temp.DESERVE_BASIC_WAGE }</td>
			    	<td>${temp.HOUSE_SUBSIDIES }</td>
			    	<td>${temp.TRANSPORT_FEE }</td>
			    	<td>${temp.RANK_ALLOWANCE }</td>
			    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
			    	<td>${temp.TOTAL_OT_FEE }</td>
			    	<td>${temp.NIGHT_SHIFT_FEE }</td>
			    	<td>${temp.WELFARE }</td>
			    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
			    	<td>${temp.QITABUZHU }</td>
			    	<td>${temp.MANAGEMENT_FEE }</td>
			    	<td>${temp.YINGDEHEJI }</td>
			    	<td>${temp.SICK_CHARGE_BACK }</td>
			    	<td>${temp.LEAVE_CHARGE_BACK }</td>
			    	<td>${temp.QITAKOU }</td>
			    	<td>${temp.BUKOU }</td>
			    	<td>${temp.GONGZIZONGE }</td>
			    	<td>${temp.MEDICAL_PERSONAL }</td>
			    	<td>${temp.ENDOWMENT_PERSONAL }</td>
			    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
			    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
			    	<td>${temp.PERSONAL_INCOME_TAX }</td>
			    	<td>${temp.SHIFAJINE }</td>
	    		</c:if>
	    		
	    </c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	  			<td style="background-color: white">&nbsp;</td>
    			<td>间接小计</td>
	    <c:forEach items="${viewPaSummarizeLaoWuList}" var = "temp">
	    		<c:if test="${temp.DEPTNO=='C043'}">
	    			<td>${temp.RS }</td>
			    	<td>${temp.DESERVE_BASIC_WAGE }</td>
			    	<td>${temp.HOUSE_SUBSIDIES }</td>
			    	<td>${temp.TRANSPORT_FEE }</td>
			    	<td>${temp.RANK_ALLOWANCE }</td>
			    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
			    	<td>${temp.TOTAL_OT_FEE }</td>
			    	<td>${temp.NIGHT_SHIFT_FEE }</td>
			    	<td>${temp.WELFARE }</td>
			    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
			    	<td>${temp.QITABUZHU }</td>
			    	<td>${temp.MANAGEMENT_FEE }</td>
			    	<td>${temp.YINGDEHEJI }</td>
			    	<td>${temp.SICK_CHARGE_BACK }</td>
			    	<td>${temp.LEAVE_CHARGE_BACK }</td>
			    	<td>${temp.QITAKOU }</td>
			    	<td>${temp.BUKOU }</td>
			    	<td>${temp.GONGZIZONGE }</td>
			    	<td>${temp.MEDICAL_PERSONAL }</td>
			    	<td>${temp.ENDOWMENT_PERSONAL }</td>
			    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
			    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
			    	<td>${temp.PERSONAL_INCOME_TAX }</td>
			    	<td>${temp.SHIFAJINE }</td>
	    		</c:if>
	    		
	    </c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	  	<td style="background-color: white">&nbsp;</td>
	    <td>合计</td>
	    <c:forEach items="${viewPaSummarizeLaoWuXiaoJiList}" var = "temp">
	    	<c:forEach items="${viewPaSummarizeLaoWuList}" var = "item">
	    	<c:if test="${item.DEPTNO=='C043'}">
		    	<td>${temp.RS + item.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S  + item.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S  + item.HOUSE_SUBSIDIES }</td>
		    	<td>${temp.TRANSPORT_FEE_S  + item.TRANSPORT_FEE }</td>
		    	<td>${temp.RANK_ALLOWANCE_S  + item.RANK_ALLOWANCE }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S  + item.JOB_ALLOWANCE_STANDARD }</td>
		    	<td>${temp.TOTAL_OT_FEE_S  + item.TOTAL_OT_FEE }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S  + item.NIGHT_SHIFT_FEE }</td>
		    	<td>${temp.WELFARE_S + item.WELFARE  }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S + item.FULL_ATTENDANCE_ALLOWANCE  }</td>
		    	<td>${temp.QITABUZHU_S  + item.QITABUZHU }</td>
		    	<td>${temp.MANAGEMENT_FEE_S + item.MANAGEMENT_FEE  }</td>
		    	<td>${temp.YINGDEHEJI_S  + item.YINGDEHEJI }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S + item.SICK_CHARGE_BACK  }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S + item.LEAVE_CHARGE_BACK  }</td>
		    	<td>${temp.QITAKOU_S  + item.QITAKOU }</td>
		    	<td>${temp.BUKOU_S  + item.BUKOU }</td>
		    	<td>${temp.GONGZIZONGE_S + item.GONGZIZONGE  }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S + item.MEDICAL_PERSONAL  }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S  + item.ENDOWMENT_PERSONAL }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S  + item.UNEMPLOYMENT_PERSONAL }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S  + item.HOUSE_FUNDING_PERSONAL }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S + item.PERSONAL_INCOME_TAX  }</td>
		    	<td>${temp.SHIFAJINE_S  + item.SHIFAJINE }</td>
		    	</c:if>
		    </c:forEach>	
	    </c:forEach>
	  </tr>
	  <tr>
	    <td rowspan="4">管理部</td>
	    <td rowspan="4">间接</td>
	    <%num = 0; %>
	    	<c:forEach items="${laoWuGuanLiJianJieList}" var = "temp">
	    		<%
		    	if(0!=num){
		    	%>	
		    		<tr>
		    	<%	
		    	}
		    	%>
		    		<td>${temp.DEPT_NAME }</td>
		    		<td>${temp.RS }</td>
			    	<td>${temp.DESERVE_BASIC_WAGE }</td>
			    	<td>${temp.HOUSE_SUBSIDIES }</td>
			    	<td>${temp.TRANSPORT_FEE }</td>
			    	<td>${temp.RANK_ALLOWANCE }</td>
			    	<td>${temp.JOB_ALLOWANCE_STANDARD }</td>
			    	<td>${temp.TOTAL_OT_FEE }</td>
			    	<td>${temp.NIGHT_SHIFT_FEE }</td>
			    	<td>${temp.WELFARE }</td>
			    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE }</td>
			    	<td>${temp.QITABUZHU }</td>
			    	<td>${temp.MANAGEMENT_FEE }</td>
			    	<td>${temp.YINGDEHEJI }</td>
			    	<td>${temp.SICK_CHARGE_BACK }</td>
			    	<td>${temp.LEAVE_CHARGE_BACK }</td>
			    	<td>${temp.QITAKOU }</td>
			    	<td>${temp.BUKOU }</td>
			    	<td>${temp.GONGZIZONGE }</td>
			    	<td>${temp.MEDICAL_PERSONAL }</td>
			    	<td>${temp.ENDOWMENT_PERSONAL }</td>
			    	<td>${temp.UNEMPLOYMENT_PERSONAL }</td>
			    	<td>${temp.HOUSE_FUNDING_PERSONAL }</td>
			    	<td>${temp.PERSONAL_INCOME_TAX }</td>
			    	<td>${temp.SHIFAJINE }</td>
			    <%
		    	if(0!=num){
		    	%>	
		    		</tr>
		    	<%	
		    	}
		    	num=1;
		    	%>	
	  		</c:forEach>
	  </tr>
	   <tr style="background-color: yellow">
	    <td >小计</td>
	    	<c:forEach items="${laoWuGuanLiJianJieXiaoJiList}" var = "temp">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	    	</c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	    <td colspan="3">劳务工合计</td>
	    <c:forEach items="${viewPaSummarizeLaoWuXiaoJiList}" var = "temp">
	    	<c:forEach items="${viewPaSummarizeLaoWuList}" var = "item">
	    		<c:if test="${item.DEPTNO=='C043'}">
	    			<c:forEach items="${laoWuGuanLiJianJieXiaoJiList}" var = "temp2">
				    	<td>${temp.RS + item.RS + temp2.RS }</td>
				    	<td>${temp.DESERVE_BASIC_WAGE_S  + item.DESERVE_BASIC_WAGE + temp2.DESERVE_BASIC_WAGE_S  }</td>
				    	<td>${temp.HOUSE_SUBSIDIES_S  + item.HOUSE_SUBSIDIES + temp2.HOUSE_SUBSIDIES_S  }</td>
				    	<td>${temp.TRANSPORT_FEE_S  + item.TRANSPORT_FEE + temp2.TRANSPORT_FEE_S   }</td>
				    	<td>${temp.RANK_ALLOWANCE_S  + item.RANK_ALLOWANCE  + temp2.RANK_ALLOWANCE_S  }</td>
				    	<td>${temp.JOB_ALLOWANCE_STANDARD_S  + item.JOB_ALLOWANCE_STANDARD  + temp2.JOB_ALLOWANCE_STANDARD_S }</td>
				    	<td>${temp.TOTAL_OT_FEE_S  + item.TOTAL_OT_FEE  + temp2.TOTAL_OT_FEE_S  }</td>
				    	<td>${temp.NIGHT_SHIFT_FEE_S  + item.NIGHT_SHIFT_FEE  + temp2.NIGHT_SHIFT_FEE_S  }</td>
				    	<td>${temp.WELFARE_S + item.WELFARE  + temp2.WELFARE_S   }</td>
				    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S + item.FULL_ATTENDANCE_ALLOWANCE  + temp2.FULL_ATTENDANCE_ALLOWANCE_S   }</td>
				    	<td>${temp.QITABUZHU_S  + item.QITABUZHU  + temp2.QITABUZHU_S  }</td>
				    	<td>${temp.MANAGEMENT_FEE_S + item.MANAGEMENT_FEE  + temp2.MANAGEMENT_FEE_S  }</td>
				    	<td>${temp.YINGDEHEJI_S  + item.YINGDEHEJI  + temp2.YINGDEHEJI_S  }</td>
				    	<td>${temp.SICK_CHARGE_BACK_S + item.SICK_CHARGE_BACK  + temp2.SICK_CHARGE_BACK_S   }</td>
				    	<td>${temp.LEAVE_CHARGE_BACK_S + item.LEAVE_CHARGE_BACK  + temp2.LEAVE_CHARGE_BACK_S  }</td>
				    	<td>${temp.QITAKOU_S  + item.QITAKOU + temp2.QITAKOU_S  }</td>
				    	<td>${temp.BUKOU_S  + item.BUKOU + temp2.BUKOU_S }</td>
				    	<td>${temp.GONGZIZONGE_S + item.GONGZIZONGE  + temp2.GONGZIZONGE_S   }</td>
				    	<td>${temp.MEDICAL_PERSONAL_S + item.MEDICAL_PERSONAL  + temp2.MEDICAL_PERSONAL_S  }</td>
				    	<td>${temp.ENDOWMENT_PERSONAL_S  + item.ENDOWMENT_PERSONAL  + temp2.ENDOWMENT_PERSONAL_S  }</td>
				    	<td>${temp.UNEMPLOYMENT_PERSONAL_S  + item.UNEMPLOYMENT_PERSONAL  + temp2.UNEMPLOYMENT_PERSONAL_S  }</td>
				    	<td>${temp.HOUSE_FUNDING_PERSONAL_S  + item.HOUSE_FUNDING_PERSONAL + temp2.HOUSE_FUNDING_PERSONAL_S  }</td>
				    	<td>${temp.PERSONAL_INCOME_TAX_S + item.PERSONAL_INCOME_TAX   + temp2.PERSONAL_INCOME_TAX_S  }</td>
				    	<td>${temp.SHIFAJINE_S  + item.SHIFAJINE + temp2.SHIFAJINE_S  }</td>
		    		</c:forEach>
		    	</c:if>
		    </c:forEach>	
	    </c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	    <td colspan="4">全公司中方总计</td>
	    	<c:forEach items="${quanGongSiZhongFang}" var = "temp">
		    	<td>${temp.RS }</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S }</td>
		    	<td>${temp.TRANSPORT_FEE_S }</td>
		    	<td>${temp.RANK_ALLOWANCE_S }</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S }</td>
		    	<td>${temp.TOTAL_OT_FEE_S }</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S }</td>
		    	<td>${temp.WELFARE_S }</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S }</td>
		    	<td>${temp.QITABUZHU_S }</td>
		    	<td>${temp.MANAGEMENT_FEE_S }</td>
		    	<td>${temp.YINGDEHEJI_S }</td>
		    	<td>${temp.SICK_CHARGE_BACK_S }</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S }</td>
		    	<td>${temp.QITAKOU_S }</td>
		    	<td>${temp.BUKOU_S }</td>
		    	<td>${temp.GONGZIZONGE_S }</td>
		    	<td>${temp.MEDICAL_PERSONAL_S }</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S }</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S }</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S }</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S }</td>
		    	<td>${temp.SHIFAJINE_S }</td>
	  		</c:forEach>
	  </tr>
	  <tr style="background-color: yellow">
	    <td colspan="4">全公司总计</td>
	    <c:forEach items="${quanGongSiZhongFang}" var = "temp">
	    	<c:forEach items="${zhuZaiYuan}" var = "item">
		    	<td>${temp.RS + item.RS}</td>
		    	<td>${temp.DESERVE_BASIC_WAGE_S + item.DESERVE_BASIC_WAGE }</td>
		    	<td>${temp.HOUSE_SUBSIDIES_S + item.HOUSE_SUBSIDIES}</td>
		    	<td>${temp.TRANSPORT_FEE_S + item.TRANSPORT_FEE}</td>
		    	<td>${temp.RANK_ALLOWANCE_S + item.RANK_ALLOWANCE}</td>
		    	<td>${temp.JOB_ALLOWANCE_STANDARD_S + item.JOB_ALLOWANCE_STANDARD}</td>
		    	<td>${temp.TOTAL_OT_FEE_S + item.TOTAL_OT_FEE}</td>
		    	<td>${temp.NIGHT_SHIFT_FEE_S + item.NIGHT_SHIFT_FEE}</td>
		    	<td>${temp.WELFARE_S + item.WELFARE}</td>
		    	<td>${temp.FULL_ATTENDANCE_ALLOWANCE_S + item.FULL_ATTENDANCE_ALLOWANCE}</td>
		    	<td>${temp.QITABUZHU_S + item.QITABUZHU}</td>
		    	<td>${temp.MANAGEMENT_FEE_S + item.MANAGEMENT_FEE}</td>
		    	<td>${temp.YINGDEHEJI_S + item.YINGDEHEJI}</td>
		    	<td>${temp.SICK_CHARGE_BACK_S + item.SICK_CHARGE_BACK}</td>
		    	<td>${temp.LEAVE_CHARGE_BACK_S + item.LEAVE_CHARGE_BACK}</td>
		    	<td>${temp.QITAKOU_S + item.QITAKOU}</td>
		    	<td>${temp.BUKOU_S + item.BUKOU}</td>
		    	<td>${temp.GONGZIZONGE_S + item.GONGZIZONGE}</td>
		    	<td>${temp.MEDICAL_PERSONAL_S + item.MEDICAL_PERSONAL}</td>
		    	<td>${temp.ENDOWMENT_PERSONAL_S + item.ENDOWMENT_PERSONAL}</td>
		    	<td>${temp.UNEMPLOYMENT_PERSONAL_S + item.UNEMPLOYMENT_PERSONAL}</td>
		    	<td>${temp.HOUSE_FUNDING_PERSONAL_S + item.HOUSE_FUNDING_PERSONAL}</td>
		    	<td>${temp.PERSONAL_INCOME_TAX_S + item.PERSONAL_INCOME_TAX}</td>
		    	<td>${temp.SHIFAJINE_S + item.SHIFAJINE}</td>
	  		</c:forEach>
	  	</c:forEach>
	  </tr>
	  </table>
	  <table>
	  <tr></tr><tr></tr>
	  <tr>
	  	<td colspan="3" style="border: 0">&nbsp;</td>
	    <td><font size="+3">经理：</font></td>
	    <td colspan="4">&nbsp;</td>
	    <td><font size="+3">部长：</font></td>
	    <td colspan="4">&nbsp;</td>
	    <td><font size="+3">科长：</font></td>
	    <td colspan="4">&nbsp;</td>
	    <td><font size="+3">制表人：</font></td>
	    <td colspan="4">&nbsp;</td>
	    <td><font size="+3">制表日期：</font></td>
	    <td colspan="3"><font size="+3">${date }</font></td>
	  </tr>
	  </table>
	
</body>
</html>