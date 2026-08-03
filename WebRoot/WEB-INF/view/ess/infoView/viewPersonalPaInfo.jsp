<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function doPersonalPaInfoExport(from){
    var $from = $("#viewpersonalpainfo"); 
    var url ="/ess/infoView/viewPersonalPaInfoExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function doPersonalPaInfo(a,navTabId){
    var $from = $("#viewpersonalpainfo");
    alertMsg.confirm("Do you want to export?", {
		okCall: function(){ doPersonalPaInfoExport($from);}});
  } 
		//通过选择的保险月 查询出发放日期的list
// function getSalaryProvideDateEss(){
// 	var paMonth = $("#essYear",navTab.getCurrentPanel()).val() + $("#essMonth",navTab.getCurrentPanel()).val();
// 	var sel = $("#essGIVE_DATE",navTab.getCurrentPanel());
// 	sel.empty();
// 	$.ajax({
// 		 cache: false,
// 		 type: 'post',
// 		 async:false,
// 		 url: "/ess/infoView/getSalaryProvideDateEss?",
// 		 data: 'paMonth=' + paMonth,
// 		 dataType:"json",
// 		 success: function(data) {
//		 sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
// 			$.each(data, function(key,value){
// 					if($(data).size() > 0){
//  							sel.append('<option value='+key+'>'+value+'</option>'); 
// 					}
// 			});
// 		 }
// 	});
// }

//  $(document).ready(function(){
//  	getSalaryProvideDateEss();
//  	});

function viewPersonalPaInfoPrint() { 
	var paMonth = $("#essYear",navTab.getCurrentPanel()).val() + $("#essMonth",navTab.getCurrentPanel()).val();
 	$.ajax({
 		 cache: false,
 		 type: 'post',
 		 async:false,
 		 url: "/ess/infoView/getSalaryDispark?",
 		 data: 'paMonth=' + paMonth+'&GIVE_DATE='+giveDate,
 		 dataType:"json",
 		 success: function(data) {
 			if(data.no!=0){
					theUrl="/ess/infoView/viewPersonalPaInfoPrint?essYear=${essYear}&essMonth=${essMonth}&essGIVE_DATE=${essGIVE_DATE}";
			   //alert(theUrl);
 			     var name="searchPersonRecord";
 			     var features = "toolbar=no,location=no,directory=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,left=300,top=0,resizable=yes,scrollbars=yes,width=750,height=700";
 			     window.open(theUrl,name,features);
 			}else{
 				alertMsg.error('<spring:message code="liang.pa.salary.title.salary_NotDispark"/>');//工资没有开放
			}
 		 }
	});
	return false;	
}
//判断工资是否开放
function getSalaryDispark(){

	var paMonth = $("#essYear",navTab.getCurrentPanel()).val() + $("#essMonth",navTab.getCurrentPanel()).val();
 	$.ajax({
 		 cache: false,
 		 type: 'post',
 		 async:false,
 		 url: "/ess/infoView/getSalaryDispark?",
 		 data: 'paMonth=' + paMonth,
 		 dataType:"json",
 		 success: function(data) {
 			if(data.no!=0){
				$("#viewpersonalpainfo",navTab.getCurrentPanel()).submit();
 			}else{
 				alertMsg.error('<spring:message code="liang.pa.salary.title.salary_NotDispark"/>');//工资没有开放
 			}
 		 }
 	});
 	return false;	
}
</script>
<div class="pageHeader">
	<form id="viewpersonalpainfo" onsubmit="return navTabSearch(this);" action="/ess/infoView/viewPersonalPaInfo" method="post">
		<div class="searchBar">
			<table  width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				<tr>
					<td class="td_title" width="20%" style="text-align:center"><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td style="text-align:center" width="20%">
						${view_EMPID} / ${view_LOCALNAME}
					</td>
					<td width="20%" class="td_title" style="text-align:center">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
					</td>
					<td width="20%" style="text-align:center">
						<ait:date yearName="essYear" yearSelected="${essYear}" monthName="essMonth" monthSelected="${essMonth}" onChange="getSalaryProvideDateEss();" />
					</td>
					<td width="20%" style="text-align:center">&nbsp;&nbsp;
					    <%--<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
						<select id="essGIVE_DATE" name="essGIVE_DATE" class="select" >
							<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
						</select>--%>
						<button type="button" onclick="getSalaryDispark();"><spring:message code="button.search"/></button>	
						<%-- 确实要导出这些记录吗?   --%> 
					<a class="add" onclick="doPersonalPaInfo(this,'${request_navTabId}')">
					<span><%-- 导出EXCEL --%><spring:message code="pa.insurance.title.excelExport"/></span></a>
					<%-- 确实要导出这些记录吗?   --%> 
                	<%--<a class="add" onclick="viewPersonalPaInfoPrint();">
                    <span> 打印 打印</span></a>--%>
					</td>
				</tr>
			</table>
		</div>
	</form>	
</div>

<div class="pageContent" id="paResultCenter" >
	<table width="100%">
		<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="ess.viewpersonalpainfo.yuangongxinxi"/><!--人员基本信息-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0" class="user_table">
							<tr>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--员工工号-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--员工姓名-->
								</td>
								<td class="td_title" style="text-align:center">
									英文名称<!--员工姓名-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="is.company.title.PERSON_TYPE"/><!--人员类型-->
								</td>
								<td class="td_title" style="text-align:center">
									<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--员工职级-->
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
									${addProList.ENGLISH_NAME}<!--员工姓名-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.EMP_TYPE_NAME}<!--人员类型-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.POST_GRADE_NO}<!--员工职级-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.DUTY_NO}<!--员工职责-->
								</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:center" colspan="3">
									部门(组织)名称
								</td>
<!-- 								<td class="td_title" style="text-align:center"> -->
<!-- 									所属支社 -->
<!-- 								</td> -->
								<td class="td_title" style="text-align:center">
									入社日期
								</td>
								<td class="td_title" style="text-align:center">
									退社日期
								</td>
								<td class="td_title" style="text-align:center">
									职务
								</td>
							</tr>
							<tr>
								<td class="td_type" style="text-align:center" colspan="3">
									${addProList.DEPTNAME}<!--员工工号-->
								</td>
<!-- 								<td class="td_type" style="text-align:center"> -->
<!-- 									${addProList.BRANCH_NAME}员工姓名 -->
<!-- 								</td> -->
								<td class="td_type" style="text-align:center">
									${addProList.DATE_STARTED}<!--员工职级-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.DATE_LEFT}<!--人员类型-->
								</td>
								<td class="td_type" style="text-align:center">
									${addProList.POST_NO}<!--员工职责-->
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
					<h1 style="text-align:center">
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
								<td class="td_title" style="text-align:center" colspan="3">
									<spring:message code="ess.viewpersonalpainfo.jiabanxiangmuzu"/><!--加班项目组-->
								</td>
								<td class="td_title" style="text-align:center" colspan="5">
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
									<spring:message code="ar.menu.title.shijia"/><!--事假-->
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
							</tr>
							<tr>
								<td class="td_type" style="text-align:center" >
									${addProList.PA_MONTH}<!--考勤月-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.CHIDAO}<!--迟到-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.ZAOTUI}<!--早退-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.KUANGGONG}<!--旷工-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.PAY_PINGSHI_OT}<!--平日加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.PAY_ZM_OT}<!--周末加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.PAY_FD_OT}<!--节假日加班合计-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.SHIJIA}<!--事假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.FD_NIANJIA}<!--法定年假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.FL_NIANJIA}<!--福利年假-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.CHUCHAI}<!--出差-->
								</td>
								<td class="td_type" style="text-align:center" >
									${addProList.BINGJIA}<!--病假-->
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
					<h1 style="text-align:center">
						<spring:message code="pa.salary.title.salaryItemList"/><!--工资项目列表-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title"  style="text-align:center" width="7%">
									基本工资
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.BASE_SALARY}
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									绩效奖金
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.PERFORMANCE_BONUS}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									销售提成
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.SALE_BONUS}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									勤续手当
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.AGE_FEE}<!--年终奖/春节慰问金-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									通讯补贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.COMMUMICATION_FEE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									交通午餐补贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.TRAFFIC_LUNCH_FEE}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									住房补贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_HOUSE_FEE}<!--年终奖/春节慰问金-->
								</td>
							</tr>
							<tr>
								<td class="td_title"  style="text-align:center" width="7%">
									节日补贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_FESTIVEL_FEE}<!--工资月-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									岗位津贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_POST_FEE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									职责津贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.DUTY_FEE}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									派遣津贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.DISPATCH_FEE}<!--年终奖/春节慰问金-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									资格证津贴
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.CERTIFICATION_FEE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									加班费合计
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.OT_FEE_TOTAL}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									SPOT奖金
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_SPOT_BONUS}<!--年终奖/春节慰问金-->
								</td>
							</tr>
							<tr>
								<td class="td_title"  style="text-align:center" width="7%">
									年终奖
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_END_YEAR_FEE}<!--工资月-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									RETENTION_INCENTIVE
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_RET_INCENTIVE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									退社补偿金
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.P_LEFT_FEE}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									迟到早退扣减
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.EARLY_REDUCE}<!--年终奖/春节慰问金-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									事假扣款
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.LEAVE_REDUCE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									病假扣款
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.SICK_REDUCE}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									旷工扣款
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.ABSENTEEISM_REDUCE}<!--年终奖/春节慰问金-->
								</td>
							</tr>
							<tr>
								<td class="td_title"  style="text-align:center" width="7%">
									产假扣款
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.MATER_REDUCE}<!--工资月-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									病假工资
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.SICK_SALARY}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									考勤扣款合计
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.AR_DEDUCT_TOTAL}<!--交通/午餐补助-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									爱心基金
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.LOVE_FEE}<!--年终奖/春节慰问金-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									工会费
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.SOCIETY_FEE}<!--职责津贴-->
								</td>
								<td class="td_title"  style="text-align:center" width="7%">
									应发工资
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.GROSS_PAY}<!--交通/午餐补助-->
								</td>
								<td class="td_title" style="text-align:center" >
									个人所得税
								</td>
								<td class="td_type" style="text-align:center" width="7%">
									${addProList.TAX}<!--合计-->
								</td>
								
							</tr>
							<tr>
							 <td class="td_title"  style="text-align:center">
									实得工资
								</td>
								<td class="td_type" style="text-align:left" colspan="13">
									${addProList.REAL_PAY}<!--年终奖/春节慰问金-->
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
					<h1 style="text-align:center">
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
									${addProList.IS_ENDOWMENT_COR}<!--养老保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yiliaobaoxian"/><!--医疗保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_UNEMPLOY_COR}<!--医疗保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shiyebaoxian"/><!--失业保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_MEDICAL_COR}<!--失业保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yanglaobaoxian"/><!--养老保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_ENDOWMENT_PER}<!--养老保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.yiliaobaoxian"/><!--医疗保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_MEDICAL_PER}<!--医疗保险-->
								</td>
							</tr>
							<tr>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shengyubaoxian"/><!--生育保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_FERTILITY_COR}<!--生育保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongshangbaoxian"/><!--工伤保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_INJURY_COR}<!--工伤保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongjijin"/><!--公积金-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_FUND_COR}<!--公积金-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.shiyebaoxian"/><!--失业保险-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_UNEMPLOY_PER}<!--失业保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									<spring:message code="pa.title.message.gongjijin"/><!--公积金-->
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_FUND_RER}<!--公积金-->
								</td>
							</tr>
							
							<tr>
								<td class="td_title" style="text-align:center" >
									保险补扣
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.IS_AJUST_COR}<!--合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									其他保险
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.IS_OTHER_COR}<!--合计-->
								</td>
								<td class="td_title" style="text-align:center" >
									五险一金
								</td>
								<td class="td_type" style="text-align:center" width="12%">
									${addProList.IS_TOTAL_COR}<!--合计-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									保险补扣
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.P_IS_AJUST_PER}<!--失业保险-->
								</td>
								<td class="td_title"  style="text-align:center" width="10%">
									大额大病保险
								</td>
								<td class="td_type" style="text-align:center" width="10%">
									${addProList.IS_SERIOUS_PER}<!--公积金-->
								</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:center" >
									保险地区
								</td>
								<td class="td_type" style="text-align:left" width="12%" colspan="9">
									${addProList.CONTENT}<!--合计-->
								</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
			</tr>
	</table>
</div>