<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var a = "";
	$.ajax( {
		type : 'post',
		dataType : 'json',
		url : '/hrm/empinfo/searchTitlename',
		data : {
			a : a
		},
		success : function(data) {
			var searchTitlename = data.searchTitlename;
			if (searchTitlename != "") {
				var str = '<select id="titleName" name="titleName" >';
				var s = '';
				for ( var i = 0; i < searchTitlename.length; i++) {
					var searchno = searchTitlename[i]['SEARCHNO'];
					var titlename = searchTitlename[i]['TITLENAME'];
					s = s + '<option value="' + searchno + '">' + titlename
							+ '</option>';
				}
				str = str + s + '</select>';
				$('#titlenamelist',navTab.getCurrentPanel()).html(str);
			}
		}
	});

	$("#employeejiansuo",navTab.getCurrentPanel()).click(function(){
		var form = $("#employeeSearchResults",navTab.getCurrentPanel());
		navTab.openTabPost('employeeSearchResults_NAVTAB',form.attr('action'),{title:'<spring:message code="hr.viewCondSql.title.SOUSUOJIEGUO" />', data:form.serializeArray()});//搜索结果//先打开一个navTab方法  
	});
});

function zailizhiqufen() {
	var arrayno = "";
	$("input[name=emp_office]",navTab.getCurrentPanel()).each(function() { //遍历table里的全部checkbox
				if ($(this).attr("checked")) { //如果被选中
					arrayno += $(this).val() + ",";
				} //获取被选中的值
			});
	arrayno = arrayno.substring(0, arrayno.length - 1);
	$('#EMP_OFFICE',navTab.getCurrentPanel()).attr('value', arrayno);
}
</script>
<div class="pageHeader">
	<form id="employeeSearchResults" onsubmit="return navTabSearch(this);"  method="post"
		action="/hrm/empinfo/employeeSearchResults">
		<div class="searchBar">
			<table class="user_table" width="100%" border="1" cellpadding="2"
				cellspacing="1">
				<tr>
					<td width="5%" class="td_title">
						<spring:message code="hrm.empinfo.SEARCH_CRITERIA" /><!-- 搜索条件 -->
					</td>
					<td width="5%" class="td_title">
						<spring:message code="hrm.empinfo.TEMPLATE_NAME" /><!-- 模板名称 -->
					</td>
					<td width="5%" class="td_type">
						<div id="titlenamelist" style="float: left;">
							<select id="demoselect">
							</select>
						</div>
						<a class="buttonActive" style="float: left; margin-left: 10px;"
							href="/hrm/empinfo/editTitleName" target="dialog" mask="true"
							width="800" height="600"> <span><spring:message code="button.delete" /><!-- 删除 --></span> </a>
					</td>
					<!-- <td width="5%" class="td_type" >
	<a href="/hrm/empinfo/employeeSearchResultsTanchu" lookupGroup="person">
    <input style="width:60px;" type="button" value="检索" ></a>
	</td>  -->

					<td width="5%" class="td_type">
						<a href="/hrm/empinfo/additionalResultsTanchu" 
							lookupGroup="person"> <input class="" type="button"
								value="<spring:message code='hrm.empinfo.APPEND_RESULT_ITEM.Z' />"> </a><!-- 追加结果项目 -->
					</td>

				</tr>
				<tr>
					<td width="5%" class="td_title">
						<spring:message code="hrm.empinfo.SEARCH_SCOPE" /><!-- 搜索范围 -->
					</td>
					<td width="5%" class="td_title">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 -->
					</td>
					<td width="5%" class="td_type">
						<ait:deptTreeMulti id="DEPTNO" name="DEPTNO_Multi" limit="ar" selectedNm="${DEPTNO_Multi}" selected="${DEPTNO}"></ait:deptTreeMulti>
					</td>
					<td width="5%" class="td_type">
						<input type="checkbox" name="emp_office" id="" checked="checked"
							onclick="zailizhiqufen()" value="15119">
						<spring:message code="hrm.empinfo.JOB" /><!-- 在职 -->&nbsp&nbsp
						<input type="checkbox" name="emp_office" id=""
							onclick="zailizhiqufen()" value="15120">
						<spring:message code="hrm.empinfo.RESIGN" /><!-- 退职 -->
						<input type="hidden" name="EMP_OFFICE" id="EMP_OFFICE"
							value="15119">
					</td>
				</tr>
			</table>

		</div>
		<div class="subBar" style="margin-top: 20px; padding-left: 1100px;">
			<ul>
				<li>
					<a class="buttonActive" href="#" id="employeejiansuo"><span> <spring:message code="hrm.empinfo.SEARCH" /><!-- 搜索 --> </span> </a>
				</li>

			</ul>
		</div>

		<div style="width: 87%; margin-left: 61px; padding-top: 20px;">
			<table class="user_table" width="100%" border="1" cellpadding="2"
				cellspacing="1">
				<tr>
					<td width="2%" class="td_title" style="text-align: left;">
						<spring:message code="hrm.empinfo.SEARCH_ITEM" /><!-- 搜索项目 -->
					</td>
					<td width="5%" class="td_title" style="text-align: left;">
						<spring:message code="hrm.empinfo.SEARCH_CRITERIA" /><!-- 搜索条件 -->
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.name" /><!-- 姓名 -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="LOCAL_NAME" name="LOCAL_NAME" value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.empid" /><!-- 社号 -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="EMPID" name="EMPID" value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hr.assignment.group" /><!-- 职群 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14015812&firstFlag=N&status=zhiqun&nameid=POST_FAMILY"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="zhiqun"></span>
						<input id="POST_FAMILY" name="POST_FAMILY" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=400098&firstFlag=N&status=zhuyaoyewu&nameid=MAIN_BUSINESS"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="zhuyaoyewu"></span>
						<input id="MAIN_BUSINESS" name="MAIN_BUSINESS" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=13864&firstFlag=N&status=yuangongleixing&nameid=EMP_TYPE_CODE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="yuangongleixing"></span>
						<input id="EMP_TYPE_CODE" name="EMP_TYPE_CODE" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!-- 国籍 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=870&firstFlag=N&status=guoji&nameid=NATIONALITY_CODE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="guoji"></span>
						<input id="NATIONALITY_CODE" name="NATIONALITY_CODE" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.DEGREE_CODE" /><!-- 学历 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=13769&firstFlag=N&status=xueli&nameid=DEGREE_CODE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="xueli"></span>
						<input id="DEGREE_CODE" name="DEGREE_CODE" type="hidden" value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.SEXCODE" /><!-- 性别 -->
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=1324&firstFlag=N&status=xingbie&nameid=SEXCODE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="xingbie"></span>
						<input id="SEXCODE" name="SEXCODE" type="hidden" value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.FAM_BORNDATE" /><!-- 出生日期 -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="DOB_START_DATE" name="DOB_START_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.START_DATE}" style="float: left;" />
						<div style="float: left;">
							~
						</div>
						<input type="text" id="DOB_END_DATE" name="DOB_END_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.END_DATE}" style="float: left;" />
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.AGE" /><!-- 年龄 -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="age_start" name="age_start" value="">
						~
						<input type="text" id="age_end" name="age_end" value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="DATE_STARTED" name="DATE_STARTED"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.START_DATE}" style="float: left;" />
						<div style="float: left;">
							~
						</div>
						<input type="text" id="DATE_END" name="DATE_END" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.END_DATE}" style="float: left;" />
					</td>
				</tr>
				
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.recruitManage.LEAVE_DATE" /> <!-- 离职日期  -->
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="DATE_LEFT_START" name="DATE_LEFT_START"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy'})"
							value="${personInfo.START_DATE}" style="float: left;" />
						<div style="float: left;">
							~
						</div>
						<input type="text" id="DATE_LEFT_END" name="DATE_LEFT_END"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy'})"
							value="${personInfo.END_DATE}" style="float: left;" />
					</td>
				</tr>
				
				<!--<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.contract.POSITION_NO" /> 职责 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14014036&firstFlag=N&status=zhize&nameid=POSITION_NO"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="zhize"></span>
						<input id="POSITION_NO" name="POSITION_NO" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.DUTY_NO" /> 岗位 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14015640&firstFlag=N&status=gangwei&nameid=DUTY_NO"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="gangwei"></span>
						<input id="DUTY_NO" name="DUTY_NO" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.recruitManage.EMP_COMMON_TYPE_CODE" /> 员工所属 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14015585&firstFlag=N&status=yuangongsuoshu&nameid=EMPLOYEE_OWNED"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="yuangongsuoshu"></span>
						<input id="EMPLOYEE_OWNED" name="EMPLOYEE_OWNED" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="liang.hr.viewPersonalInfo.title.HIRE_PATH" /> 招聘渠道 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14015265&firstFlag=N&status=zhaopinqudao&nameid=RECRUIT_TYPE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="zhaopinqudao"></span>
						<input id="RECRUIT_TYPE" name="RECRUIT_TYPE" type="hidden"
							value="">
					</td>
				</tr>
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.contract.Rank" /> 职级 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=14015578&firstFlag=N&status=zhiji1&nameid=POST_GRADE_NO"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="zhiji1"></span>
						<input id="POST_GRADE_NO" name="POST_GRADE_NO" type="hidden" value="">
					</td>
				</tr>
                <tr>
					<td width="2%" class="td_type">
						<spring:message code="hr.viewCondSql.title.SHENGRI" /> 生日 
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="BIRTHDAY" name="BIRTHDAY"
							class="Wdate" onClick="WdatePicker({dateFmt:'MM'})"
							value="${personInfo.START_DATE}" style="float: left;" />
					</td>
				</tr>
				
				<tr>
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.END_PROBATION_DATE" /> 试用终止日期 
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="START_PROBATION_DATE"
							name="START_PROBATION_DATE" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd.MM.yyyy'})"
							value="${personInfo.START_DATE}" style="float: left;" />
						<div style="float: left;">
							~
						</div>
						<input type="text" id="END_PROBATION_DATE"
							name="END_PROBATION_DATE" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd.MM.yyyy'})"
							value="${personInfo.END_DATE}" style="float: left;" />
					</td>
				</tr>
				<tr style="display:none">
					<td width="2%" class="td_type">
						<spring:message code="hrm.empinfo.PRODUCT_TYPE" /> 产品类型 
					</td>
					<td width="5%" class="td_type">
						<a class=""
							href="/hrm/empinfo/searchTanchu?PARENT_CODE_NO=400216&firstFlag=N&status=chanpinleixing&nameid=PRODUCT_TYPE"
							lookupGroup="person"> <input type="text"> <input
								type="button" value="......"> </a>
						<span id="chanpinleixing"></span>
						<input id="PRODUCT_TYPE" name="PRODUCT_TYPE" type="hidden"
							value="">
					</td>
				</tr>

				<tr style="display:none">
					<td width="2%" class="td_type">
						在职年限
					</td>
					<td width="5%" class="td_type">
						<input type="text" id="START_LIMIT"
							name="START_LIMIT" style="float: left;" />
						<div style="float: left;">
							~
						</div>
						<input type="text" id="END_LIMIT"
							name="END_LIMIT" style="float: left;" />(月)
					</td>
				</tr>

			--></table>
		</div>
	</form>
</div>
