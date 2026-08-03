<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">


function photoShow(empid,personId,cpnyID){
	
	document.getElementById("photoHref").href="/hrm/empinfo/phothChange?EMPID="+empid+"&PERSON_ID="+personId+"&CPNY_ID="+cpnyID;
	
	document.getElementById("photoHref").click();

}

function changePic1(photo){
	document.getElementById(photo).src = '/resources/photo/default.jpg';
}

</script>

<input type="hidden" id="isEssSystem" value="${isEssSystem }"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="140">
	<tr>
		<td valign="top">
		<br/>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			<td class="td_title">
						姓名
					</td>
					<td class="td_type" width="15%" colspan='3'>
						
							${personInfo.LOCAL_NAME }(${personInfo.CHINESE_PINYIN })
						
					</td>
					<td class="td_title" >
						<spring:message code="hrm.empinfo.empid" />
					</td>
					<td class="td_type" width="15%">
						<c:if test="${isEssSystem ne '1'}">
							<!--<input id="EMPID" name="EMPID" type="text" value="${personInfo.EMPID }" size="10" onkeydown="F_HR_SubmitKeyClick2(event, this.value,'','','${request_navTabId}')" />
						-->
							${personInfo.EMPID }
						</c:if>
						<c:if test="${isEssSystem eq '1'}">
							${personInfo.EMPID }
						</c:if>
							<input type="hidden" name="PERSON_ID" value="${personInfo.PERSON_ID }" >
							<a id="onck" name="onck"  href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
					</td>
					
				</tr>
				
				<tr>
					<td class="td_title" >
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门  -->
					</td>
					<td class="td_type" width="15%">
							${personInfo.DEPTNO_NAME }
					</td>
					<td class="td_title">
						职级
					</td>
					<td class="td_type" width="15%">
                        ${personInfo.POST_GRADE_NO_NAME}
					</td>
					<td class="td_title">
						S-Band
					</td>
					<td class="td_type">
						${personInfo.S_BAND }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						主要业务
					</td>
					<td class="td_type">
						${personInfo.MAIN_BUSINESS }
					</td>
						<td class="td_title">
						员工状态
					</td>
					<td class="td_type">
						${personInfo.EMP_OFFICE_NAME }
					</td>
				</tr>
				<tr>
					</td>
						<td class="td_title">
						员工类型
					</td>
					<td class="td_type">
					${personInfo.EMP_TYPE_CODE_NAME }
					</td>
					</td>
						<td class="td_title">
						部门长
					</td>
					<td class="td_type">
						${personInfo.HEAD_DEPARTMENT }
					</td>
					<td class="td_title">
						<spring:message code="sys.affirm.title.duty"/>
						<!--职责 -->
					</td>
					<td class="td_type">
                        ${personInfo.POSITION_NO}
					</td>
				</tr>
				<tr>
				<td class="td_title">
						成本中心
					</td>
					<td class="td_type">
                         ${personInfo.COST_CENTER}
					</td>
				
				<td class="td_title">
						产品类型
					</td>
					<td class="td_type">
                         ${personInfo.PRODUCT_TYPE_NAME}
					</td>
					<td class="td_title">
						班组类型
					</td>
					<td class="td_type">
                         ${personInfo.SHIFT_NO_NAME}
					</td>
					
				</tr>
				<tr>
				 <td  class="td_title">
						入社日期
					</td>
					<td class="td_type" >
						${personInfo.DATE_STARTED }
					</td>
					<td  class="td_title">
						经历期间
					</td>
					<td class="td_type" >
						${personInfo.EXPERIENCE }月
					</td>
					<td  class="td_title">
						在职期间
					</td>
					<td class="td_type" >
						<span id="worktime"></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


