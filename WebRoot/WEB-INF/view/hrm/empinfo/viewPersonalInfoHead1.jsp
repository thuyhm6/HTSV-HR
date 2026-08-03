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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="user_photo">
			<p>
				<img align="middle" id="${photoId}" src="${PhotoPath}" onerror="changePic1(this.id);"/>
				
				<a id="photoHref" name="photoHref"  href="#" target="dialog" mask="true"  title="<spring:message code='hr.viewPersonalInfo.title.UPLOADPHOTO'/>" ></a><%--上传照片--%>
				
				<%--<input type="button" onclick="photoShow(${personInfo.EMPID},${personInfo.PERSON_ID})" value="上传照片" /> --%>
				
				<input type="button" onclick="photoShow('${personInfo.EMPID}','${personInfo.PERSON_ID}','${personInfo.CPNY_ID}')" value="<spring:message code='hr.viewPersonalInfo.title.UPLOADPHOTO'/>" />
			</p>
		</td> 
		<td>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
				<tr>
					<td class="td_title" >
						<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
						<!--社号  -->
					</td>
					<td class="td_type" width="15%">
						<input id="EMPID" name="EMPID" type="text" value="${personInfo.EMPID }" size="10" onkeydown="F_HR_SubmitKeyClick(this.value,'','','${request_navTabId}')" />
						<input type="hidden" name="PERSON_ID" value="${personInfo.PERSON_ID }" >
						<a id="onck" name="onck"  href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
						<!--职级(GGS)-->
					</td>
					<td class="td_type">
						 ${personInfo.POST_GRADE_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.JOIN_BLOC_DATE"/>
						<!--集团入职日期  -->
					</td>
					<td class="td_type">
						${personInfo.JOIN_BLOC_DATE }
					</td>
					<td  class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.manpower_distinguish"/>
						<!--人力区分-->
					</td>
					<td>
						${personInfo.manpower_distinguish }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewHire.title.LOCALNAMEANDPINYIN"/>
						<!--姓名  (拼音)-->
					</td>
					<td class="td_type">
						<input id="LOCAL_NAME" name="LOCAL_NAME" type="text" value="${personInfo.LOCAL_NAME }" size="10" onkeydown="F_HR_SubmitKeyClick('',this.value,'','${request_navTabId}')" /><br />
						(${personInfo.CHINESE_PINYIN})
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)  -->
					</td>
					<td class="td_type">
						 ${personInfo.POST_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.JOIN_COMPANY_DATE"/>
						<!--子公司入司日期  -->
					</td>
					<td class="td_type">
						${personInfo.JOIN_COMPANY_DATE }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
						<!--员工类型  -->
					</td>
					<td class="td_type" width="15%">
						${personInfo.EMP_TYPE_NAME }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
						<!--身份证号-->
					</td>
					<td class="td_type">
						<input id="IDCARD_NO" name="IDCARD_NO" type="text" value="${personInfo.IDCARD_NO}" size="20" onkeydown="F_HR_SubmitKeyClick('','',this.value,'${request_navTabId}')" />
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门-->
					</td>
					<td class="td_type">
						<!--部门查询-->
						${personInfo.DEPTNAME }
						<!--<input type="button" name="seachByDept" onclick="seachByDept('${request_navTabId}');" value="<spring:message code='hr.viewPersonalInfo.title.DEPTSELECT'/>" title="按部门别进行员工信息查询" />-->
					</td>
					<td class="td_title">
						<spring:message
							code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE" />
						<!--现部门异动日期-->
					</td>
					<td class="td_type">
						${personInfo.NOW_DEPARTMENT_DATE }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/>
						<!--在职区分-->
					</td>
					<td class="td_type">
						${personInfo.EMP_OFFICE_NAME }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.SEX" />
						<!--性别-->
					</td>
					<td class="td_type">
						${personInfo.SEX }
					</td>
					<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
						<!--职责-->
					</td>
					<td class="td_type">
						 ${personInfo.DUTY_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.PROMOTION_DATE"/>
						<!--升职日-->
					</td>
					<td>
						${personInfo.PROMOTION_DATE}
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.REC_SOURCE"/>
						<!--采用路径-->
					</td>
					<td class="td_type">
						${personInfo.REC_SOURCE_DETAIL_NAME}
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
					</td>
					<td class="td_type">
						${personInfo.DOB }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
						<!--职(岗)位-->
					</td>
					<td class="td_type">
						 ${personInfo.POSITION_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
						<!--离职日期-->
					</td>
					<td class="td_type">
						${personInfo.DATE_LEFT }
					</td>
					<td  class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.PROBATION_FINISH_DATE"/>
						<!--试用结束日-->
					</td>
					<td>
						${personInfo.PROBATION_FINISH_DATE}
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


