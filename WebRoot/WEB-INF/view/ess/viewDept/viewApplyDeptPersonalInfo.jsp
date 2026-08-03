<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">


	<div class="pageFormContent" layoutH="56">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
	    <tr>
	       <td rowspan="6" width="10%" class="td_type" > <img  width="116px" src='${personInfoTemp.PHOTO_PATH}'></td>
		   <td class="td_title" width="13%"> 工号</td>
		   <td class="td_type" width="13%">${personInfoTemp.EMPID}</td>
           <td class="td_title" width="13%">  部门 </td>
		   <td class="td_type" colspan="3" width="13%"> ${personInfoTemp.DEPT_NAME}</td>
		 </tr>
		 <tr>
		   <td class="td_title" width="13%">姓名</td>
		   <td class="td_type" width="13%"> ${LOCAL_NAME}( ${personInfoTemp.EMPID})</td>
           <td class="td_title" width="13%">机种区分</td>
		   <td class="td_type" width="13%"></td>
		   <td class="td_title" width="13%">工程 </td>
		   <td class="td_type"  width="13%"></td>
		</tr>
		<tr>
	       <td class="td_title" width="13%">等级</td>
           <td class="td_type" width="13%">${personInfoTemp.POST_GRADE_NO}</td>
           <td class="td_title" width="13%">职责</td>
           <td class="td_type" width="13%">${personInfoTemp.POSITION_NO}</td>
           <td class="td_title" width="13%"></td>
           <td class="td_type" width="13%"></td>
         </tr>
		<tr>
          <td class="td_title" width="13%">在职状态</td>
          <td class="td_type" width="13%">${personInfoTemp.EMP_OFFICE}</td>
		  <td class="td_title" width="13%">入社日期</td>
          <td class="td_type" width="13%">${personInfoTemp.DATE_STARTED}</td>
		  <td class="td_title" width="13%">员工类型</td>
          <td class="td_type" width="13%">${personInfoTemp.EMP_TYPE_CODE}</td>
		</tr>
		<tr>
          <td class="td_title" width="13%">试用与否</td>
          <td class="td_type" width="13%">${personInfoTemp.EMP_TYPE_CODE_DIF}</td>
		  <td class="td_title" width="13%">试用终止日期</td>
          <td class="td_type" width="13%">${personInfoTemp.END_PROBATION_DATE}</td>
		  <td class="td_title" width="13%">最初入社日</td>
          <td class="td_type" width="13%">${personInfoTemp.TIME_STARTED}</td>
		</tr>
		<tr>
          <td class="td_title" width="13%">停职开始日期</td>
          <td class="td_type" width="13%">${personInfoTemp.SUSPENSION_START_DATE}</td>
		  <td class="td_title" width="13%">退社日期</td>
          <td class="td_type" width="13%">  ${personInfoTemp.DATE_LEFT}</td>
		  <td class="td_title" width="13%"></td>
          <td class="td_type" width="13%"></td>
		</tr>
		</table>

		<div id="createTable" width="100%"></div>

		<input type="hidden" name="count" id="count" value="1">
	</div>


</div>