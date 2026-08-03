<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<table class="searchContent" width="100%">
			<tr>
				<td width="20%" class="td_title">法人:</td>
				<td width="30%">${tempEmpTr.CPNY_ID}</td>
				<td width="20%" class="td_title">部门:</td>
				<td width="30%">${tempEmpTr.DEPT_NAME}</td>
			</tr>
			<tr>
				<td width="20%" class="td_title">社编:</td>
				<td width="30%">${tempEmpTr.EMPID}</td>
				<td width="20%" class="td_title">姓名:</td>
				<td width="30%">${tempEmpTr.LOCAL_NAME}</td>
			</tr>
			<tr>
				<td width="20%" class="td_title">发令日期:</td>
				<td colspan="2" width="80%">${tempEmpTr.START_DATE}</td>
			</tr>
			<tr>
				<td width="20%" class="td_title">发令原因:</td>
				<td colspan="2" width="80%">${tempEmpTr.TRANSFER_ORDER_REASON}</td>
			</tr>
		</table>
</div>
<div class="pageContent">	
	
	<table class="user_table" width="100%" layoutH="140" cellspacing="0" cellpadding="0" >
		<tr>
			<td width="20%" class="td_title" style="text-align:center">变更项目</td>
			<td width="40%" class="td_title" style="text-align:center">变更前</td>
			<td width="40%" class="td_title" style="text-align:center">变更为</td>
		</tr>
		<c:choose>
		<c:when test="${tempEmpTr.TRANS_CODE == 15861 }">	
		<tr>		
			<td width="20%" class="td_title" style="text-align:center">部门</td>
			<td width="40%" class="td_center">[${tempEmpTr.OLD_DEPTNO}]${tempEmpTr.OLD_DEPTNM}</td>
			<td width="40%" class="td_center">[${tempEmpTr.CUR_DEPTNO}]${tempEmpTr.CUR_DEPTNM}</td>
		</tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 278706 }">
		<tr>
			<td width="20%" class="td_title" style="text-align:center">人员类型</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_EMP_TYPE_NAME}</td>
			<td width="40%" class="td_center">${tempEmpTr.CUR_EMP_TYPE_NAME}</td>
		</tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 278705 }">
		<tr>
			<td width="20%" class="td_title" style="text-align:center">班号</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_SHIFT_NO}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_SHIFT_NO}</td>
	    </tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 278720 }">
		<tr>
			<td width="20%" class="td_title" style="text-align:center">职责</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_POSITION_NO}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_POSITION_NO}</td>
		</tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 278704 }">
		<tr>
		    <td width="20%" class="td_title" style="text-align:center">级号</td>
		    <td width="40%" class="td_center">${tempEmpTr.OLD_PAY_GRADE}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_PAY_GRADE}</td>
		</tr>
		<tr>
		    <td width="20%" class="td_title" style="text-align:center">级号等级</td>
		    <td width="40%" class="td_center">${tempEmpTr.OLD_PAY_STEP}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_PAY_STEP}</td>
		</tr>
		<tr>
		    <td width="20%" class="td_title" style="text-align:center">基本工资</td>
		    <td width="40%" class="td_center">${tempEmpTr.OLD_BASE_PAY}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_BASE_PAY}</td>
		</tr>
		<tr>
		    <td width="20%" class="td_title" style="text-align:center">变动工资</td>
		    <td width="40%" class="td_center">${tempEmpTr.OLD_VARB_PAY}</td>
		    <td width="40%" class="td_center">${tempEmpTr.CUR_VARB_PAY}</td>
		</tr>
		<tr>
			<td width="20%" class="td_title" style="text-align:center">年薪</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_ANSAL}</td>
			<td width="40%" class="td_center">${tempEmpTr.CUR_ANSAL}</td>
		</tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 14013588 }">	
		<tr>		
			<td width="20%" class="td_title" style="text-align:center">ID卡号</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_ID_CARD_NO}</td>
			<td width="40%" class="td_center">${tempEmpTr.CUR_ID_CARD_NO}</td>
		</tr>
		</c:when>
		<c:when test="${tempEmpTr.TRANS_CODE == 14013616 }">	
		<tr>		
			<td width="20%" class="td_title" style="text-align:center">职务</td>
			<td width="40%" class="td_center">${tempEmpTr.OLD_JOB_TITLE_CD}</td>
			<td width="40%" class="td_center">${tempEmpTr.CUR_JOB_TITLE_CD}</td>
		</tr>
		</c:when>
	</c:choose>
	</table>
	<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" id="btnClose" name="btnClose" class="close">
							关闭</button>
						</div>
					</div>
				</li>
		</ul>
	</div>
</div>