 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=OrgPersonStatus.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<div class="pageContent" id="paResultCenter" >
	<table width="100%">
		<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:left">
						<spring:message code="ess.viewpersonalpainfo.yuangongxinxi"/><!--人员基本信息-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--员工工号-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--员工姓名-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--员工职级-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="is.company.title.PERSON_TYPE"/><!--人员类型-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="sys.affirm.title.duty"/><!--员工工号-->
								</td>
							</tr>
							
							<tr>
								<td class="td_type" style="text-align:center">
									${view_EMPID}<!--员工工号-->
								</td>
								<td class="td_type" style="text-align:center">
									${view_LOCALNAME}<!--员工姓名-->
								</td>
								<td class="td_type" style="text-align:center">
									${linkMap.POST_GRADE_NAME}<!--员工职级-->
								</td>
								<td class="td_type" style="text-align:center">
									${linkMap.NATIONALITY_NAME}<!--人员类型-->
								</td>
								<td class="td_type" style="text-align:center">
									${linkMap.DUTY_NAME}<!--员工职责-->
								</td>
							</tr>
							
						</table>
					</div>
				</div>
			</td>
			</tr>
			
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:left">
						<spring:message code="pa.salary.title.attendanceBasicInfo"/><!--考勤基本信息-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" style="text-align:center" rowspan="2">
									<spring:message code="ar.excelexport.title.armonth"/><!--考勤月-->
								</td>
								
								<td class="td_title" style="text-align:center" colspan="3">
									<spring:message code="ess.viewpersonalpainfo.zhuznyouxiangmuzu"/><!--专有项目组-->
								</td>
								<td class="td_title" style="text-align:center" colspan="4">
									<spring:message code="ess.viewpersonalpainfo.jiabanxiangmuzu"/><!--加班项目组-->
								</td>
								<td class="td_title" style="text-align:center" colspan="9">
									<spring:message code="ess.viewpersonalpainfo.xiujiaxiangmuzu"/><!--休假项目组-->
								</td>
	
							</tr>
							<tr>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.monthwork.title.Lateness"/><!--迟到-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.monthwork.title.EarlyLeave"/><!--早退-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.monthwork.title.kuanggong"/><!--旷工-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.pingrijiabanheji"/><!--平日加班合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.zhoumojiabanheji"/><!--周末加班合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.jiejiarijiabanheji"/><!--节假日加班合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.zongtiaoxiu"/><!--总调休-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.shijia"/><!--事假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.viewArAdjustRest.title.yiyongtiaoxiushu"/><!--已用调休-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.viewArAnnualStandard.title.fadininjia"/><!--法定年假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.viewArAnnualStandard.title.fulininjia"/><!--福利年假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.chuchai"/><!--出差-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.bingjia"/><!--病假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.hunjia"/><!--婚假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.chanjia"/><!--产假-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ar.menu.title.sangjia"/><!--丧假-->
								</td>
	
							</tr>
							<tr>
								<td class="td_type" style="text-align:center" >
									${addProList.PA_MONTH}<!--考勤月-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.CHIDAO}<!--迟到-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.ZAOTUI}<!--早退-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.KUANGGONG}<!--旷工-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.PINGRIJIABAN}<!--平日加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.ZHOUMO}<!--周末加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									0<!--节假日加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.TIAOXIU}<!--总调休-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.SHIJIA}<!--事假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.YIYONGTIAOXIU}<!--已用调休-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.SHIYONG_NIANJIA_DAY}<!--法定年假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.SHIYONG_FULI_DAY}<!--福利年假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.CHUCHAI_DAY}<!--出差-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.BINGJIA}<!--病假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.HUNJIA_DAY}<!--婚假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.CHANJIA}<!--产假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${miProList.SANGJIA_DAY}<!--丧假-->
								</td>
	
							</tr>
							
						</table>
					</div>
				</div>
			</td>
			</tr>
			
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:left">
						<spring:message code="pa.salary.title.salaryItemList"/><!--工资项目列表-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title"  style="text-align:center" width="12%">
									<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.PA_MONTH}<!--工资月-->
								</td>
								<td class="td_title"  style="text-align:center" width="12%">
									<spring:message code="pa.insurance.title.zhizejintie"/><!--职责津贴-->
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.DUTY_FEE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="12%">
									<spring:message code="pa.insurance.title.jiaotongwucanbuzhu"/><!--交通/午餐补助-->
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.TRAFFIC_LUNCH_FEE}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="12%">
									<spring:message code="pa.insurance.title.nianzhongjiangchunjieweiwenjin"/><!--年终奖/春节慰问金-->
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.TAX_YEAR_BONUS}<!--年终奖/春节慰问金-->
								</td>
							</tr>
							
						</table>
					</div>
				</div>
			</td>
			</tr>
			
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:left">
						<spring:message code="pa.insurance.title.baoxianfulijikoukuanxiangmu"/><!--保险福利及税金扣款项目-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" style="text-align:center" colspan="6">
									<spring:message code="pa.insurance.title.gongsibufen"/><!--公司部分-->
								</td>
								<td class="td_title" style="text-align:center" colspan="4">
									<spring:message code="pa.insurance.title.gerenbufen"/><!--个人部分-->
								</td>
							</tr>
							
							<tr>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yanglaobaoxian"/><!--养老保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_ENDOWMENT}<!--养老保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yiliaobaoxian"/><!--医疗保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_MEDICAL}<!--医疗保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shiyebaoxian"/><!--失业保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_UNEMPLOY}<!--失业保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yanglaobaoxian"/><!--养老保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISP_ENDOWMENT}<!--养老保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yiliaobaoxian"/><!--医疗保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISP_MEDICAL}<!--医疗保险-->
								</td>
							</tr>
							<tr>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shengyubaoxian"/><!--生育保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_FERTILITY}<!--生育保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongshangbaoxian"/><!--工伤保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_INJURY}<!--工伤保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongjijin"/><!--公积金-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISC_FUND}<!--公积金-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shiyebaoxian"/><!--失业保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISP_UNEMPLOY}<!--失业保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongjijin"/><!--公积金-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.ISP_FUND}<!--公积金-->
								</td>
							</tr>
							
							<tr>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.heji"/><!--合计-->
								</td>
								<td class="td_type" style="text-align:center" width="12%" colspan="5">
									${addProList.ISC_TOTAL}<!--合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									<spring:message code="ess.viewpersonalpainfo.heji"/><!--合计-->
								</td>
								<td class="td_type" style="text-align:center" width="12%" colspan="3">
									${addProList.ISP_TOTAL}<!--合计-->
								</td>
							</tr>
							
						</table>
					</div>
				</div>
			</td>
			</tr>
			
			
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:left">
						<spring:message code="pa.salary.title.shifagongzil"/><!--实发工资列表-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title"  style="text-align:center" width="15%">
									<spring:message code="pa.low.adjust.yingfagongzi"/><!--应发工资-->
								</td>
								<td class="td_type" style="text-align:center" width="15%">
									${addProList.GROSS_PAY}<!--应发工资-->
								</td>
								<td class="td_title"  style="text-align:center" width="15%">
									<spring:message code="pa.low.adjust.gerenfulikouchu"/><!--个人福利扣除-->
								</td>
								<td class="td_type" style="text-align:center" width="15%">
									&nbsp;<!--个人福利扣除-->
								</td>
								<td class="td_title"  style="text-align:center" width="15%">
									<spring:message code="pa.title.message.suodeshui"/><!--所得税-->
								</td>
								<td class="td_type" style="text-align:center" width="15%">
									${addProList.TAX}<!--所得税-->
								</td>
							</tr>
							
							<tr>
								<td class="td_title"  style="text-align:center">
									<spring:message code="pa.low.adjust.yingfagongzi"/><!--年终奖/春节慰问金所得税-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.END_YEAR_TAX}<!--年终奖/春节慰问金所得税-->
								</td>
								<td class="td_title"  style="text-align:center">
									<spring:message code="ess.viewpersonalpainfo.shifagongzi"/><!--实发工资-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.REAL_PAY}<!--实发工资-->
								</td>
								<td class="td_title"  style="text-align:center">
									<spring:message code="pa.title.message.gongjijin"/><!--公积金-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.ISC_FUND}<!--公积金-->
								</td>
							</tr>
							
						</table>
					</div>
				</div>
			</td>
			</tr>
			
			<tr>
			<td width="100%">
				<div class="panel" style="text-align:left">
					<h1>
						<spring:message code="pa.title.message.shougongtiaozhengxiangmuliebiao"/><!--手工调整项目列表-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" style="text-align:center">
									<spring:message code="sys.postManage.title.chineseName"/><!--中文名称-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="rp.report.title.amount"/><!--金额-->  
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="pa.title.pa.excel.yingyongyue"/><!--应用月-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="pa.title.pa.excel.guoqiyue"/><!--过期月-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewBadArchives.title.REMARK"/><!--备注-->
								</td>
							</tr>
							<tr>
								<td class="td_type" style="text-align:center">
									&nbsp;<!--中文名称-->
								</td>
								<td class="td_type" style="text-align:center">
									&nbsp;<!--金额-->  
								</td>
								<td class="td_type" style="text-align:center">
									&nbsp;<!--应用月-->
								</td>
								<td class="td_type" style="text-align:center">
									&nbsp;<!--过期月-->
								</td>
								<td class="td_type" style="text-align:center">
									&nbsp;<!--备注-->
								</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>