<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="panel">
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION" />
			<!--员工基础信息-->
			<!-- 직원기초정보 -->
		</h1>
		<div>

			<script type="text/javascript">

function photoShow(empid, personId, cpnyID) {

	document.getElementById("photoHref").href = "/hrm/empinfo/phothChange?EMPID="
			+ empid + "&PERSON_ID=" + personId + "&CPNY_ID=" + cpnyID;

	document.getElementById("photoHref").click();

}

function changePic1(photo) {
	document.getElementById(photo).src = '/resources/photo/default.jpg';
}
</script>

			<input type="hidden" id="isEssSystem" value="${isEssSystem }" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				height="140">
				<tr>
					<td>
						<h1>基本信息</h1> 
					</td>
					
				</tr>
				<tr>
					<!--<td class="user_photo" valign="top">
			<p>
				<img align="middle" id="${photoId}" src="${PhotoPath}" onerror="changePic1(this.id);"/>
				
				<a id="photoHref" name="photoHref"  href="#" target="dialog" mask="true"  title="<spring:message code='hr.viewPersonalInfo.title.UPLOADPHOTO'/>" ></a><%--上传照片--%>
				
				<%--<input type="button" onclick="photoShow(${personInfo.EMPID},${personInfo.PERSON_ID})" value="上传照片" /> --%>
				
				<input type="button" onclick="photoShow('${personInfo.EMPID}','${personInfo.PERSON_ID}','${personInfo.CPNY_ID}')" value="<spring:message code='hr.viewPersonalInfo.title.UPLOADPHOTO'/>" />
			</p>
		</td>
		-->


					<td valign="top">
						<br />
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<!-- start -->

							<tr>
								<td class="td_title" width="10%">
									<spring:message code="hr.empinfo.name" />
									<!--姓名 -->
								</td>
								<td class="td_type" width="15%">
									<c:if test="${isEssSystem ne '1'}">
										<!--<input id="LOCAL_NAME" name="LOCAL_NAME" type="text" value="${personInfo.LOCAL_NAME }" size="10" onkeydown="F_HR_SubmitKeyClick('',this.value,'','${request_navTabId}')" /><br />
						-->
						${personInfo.LOCAL_NAME }
						</c:if>
									<c:if test="${isEssSystem eq '1'}">
							${personInfo.LOCAL_NAME }
						</c:if>
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.empinfo.empid" />
									<!--员工号 -->
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
									<input type="hidden" name="PERSON_ID"
										value="${personInfo.PERSON_ID }">
									<a id="onck" name="onck"
										href="/hrm/empinfo/viewEmpIdList?pageNum=1"
										lookupGroup="person" width="950"></a>
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.empinfo.english.name" />
									<!--英文姓名 -->
								</td>
								<td class="td_type" width="15%">
									${personInfo.CHINESE_PINYIN}
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.empinfo.chinese.name" />
									<!--中文姓名-->
								</td>
								<td class="td_type" width="15%">
									<c:if test="${isEssSystem ne '1'}">
										<!--<input id="LOCAL_NAME" name="LOCAL_NAME" type="text" value="${personInfo.LOCAL_NAME }" size="10" onkeydown="F_HR_SubmitKeyClick('',this.value,'','${request_navTabId}')" /><br />
						-->
						${personInfo.LOCAL_NAME }
						</c:if>
									<c:if test="${isEssSystem eq '1'}">
							${personInfo.LOCAL_NAME }
						</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<!-- end -->
				<!-- 个人事项 -->
				<tr>
					<td>
						<h1>个人事项</h1> 
					</td>
				<td ><div align="right"><a  href='#'>修正</a></div></td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<!-- row1 -->
							<tr>
								<td class="td_title" width="10%">
									<spring:message code="hr.viewPersonalInfo.title.DOB" />
									<!--出生日期-->
								</td>
								<td class="td_type" width="15%">
									${personInfo.DOB }
								</td>

								<td class="td_title" width="10%">
									<spring:message code="hr.viewPersonalInfo.title.SEX" />
									<!--性别-->
								</td>
								<td class="td_type"  width="15%">
									${personInfo.SEX_NAME }
								</td>
								<td class="td_title" width="10%">
									结婚
									<!--性别-->
								</td>
								<td class="td_type" width="15%">
									${personInfo.MARITAL_STATUS_CODE }
								</td>
<td class="td_title" width="10%"><spring:message
					code="hr.viewHire.title.HOME_PHONE" /> <!-- 联系电话 --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
							</tr>
							<tr>
							<td class="td_title" width="10%">移动电话（社会）<!-- 移动电话（社会）--></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
				<td class="td_title" width="10%">mobile(personal) <!-- mobile(personal) --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
				<td class="td_title" width="10%">家庭电话 <!-- 家庭电话 --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
				<td class="td_title" width="10%">E-Mail <!-- E-Mail --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
							</tr>
<tr>
                <td class="td_title" width="10%">E-Mail(2nd) <!-- E-Mail(2nd) --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
				<td class="td_title" width="10%">EagleM ID <!-- EagleM ID --></td>
				<td class="td_type" width="15%">${personInfo.HOME_PHONE}</td>
				 <td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.NATION_NAME" /> <!--民族 --></td>
				<td class="td_type" >${personInfo.NATION_NAME }</td>
				<td class="td_title">住宅分区<!--住宅分区--></td>
				<td class="td_type" colspan="1">${personInfo.HOME_ADDRESS }</td>
				
</tr>
<tr>
<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /> <!--国籍--></td>
					<td class="td_type">${personInfo.NATIONALITY_NAME }</td>
					<td class="td_title">档案所在地<!--档案所在地--></td>
					<td class="td_type"></td>
					<td class="td_title">档案转入<!--档案转入--></td>
					<td class="td_type"></td>
					<td class="td_title">档案转出 <!--档案转出 --></td>
					<td class="td_type"></td>
					

</tr>
						</table>
					</td>
				</tr>
				<!-- 地址信息 -->
				<tr>
				
					<td>
					
						<h1>地址信息</h1> 
						
					</td>
					<td ><div align="right"><a class='add' href='/hrm/empinfo/viewAddressInfo' target="navTab"> 添加</a></div></td>
					
				</tr>
				
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<tr>
							<td class="td_title" width='20%'>地址类型<!--档案转入--></td>
							<td class="td_title" width='20%'>有效开始日期<!--档案转入--></td>
							<td class="td_title" width='80%'>地址<!--档案转入--></td>
							</tr>
							<c:forEach items="${addressList}" var="item">
							<tr>
							<td class="td_type">${item.ADDRESS_TYPE}</td>
							<td class="td_type">${item.EFFECTIVE_START_DATE}</td>
							<td class="td_type">${item.ADDRESS_CONTENT}</td>
							</tr>
							</c:forEach>
							</table>
					</td>
				</tr>
				<!--家庭事项  -->
				<tr>
					<td>
						<h1>家庭事项</h1>
					</td>
							<td ><div align="right"><a class='add' href='/hrm/empinfo/viewHomeRelationInfo' target="navTab">添加</a></div></td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							
							<tr>
							<td class="td_title" width='20%'><spring:message code="hr.viewRelation.title.FAMILY_RELATIONS" /><!--家庭关系--></td>
							<td class="td_title" width='20%'><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /><!--姓名--></td>
							<td class="td_title" width='20%'>性别<!--性别--></td>
							<td class="td_title" width='20%'><spring:message
						code="hr.viewPersonalInfo.title.DOB" /><!--出生日期--></td>
							<td class="td_title" width='20%'>家庭电话<!--家庭电话--></td>
							</tr>
								<c:forEach items="${homeRelationList}" var="item" varStatus="i">
							<tr>
							<td class="td_type">${item.FAM_TYPE_NAME}</td>
							<td class="td_type">${item.FAM_NAME}</td>
							<td class="td_type">${item.GENDER}</td>
							<td class="td_type">${item.FAM_BORNDATE}</td>
							<td class="td_type">${item.FAM_PHONE}</td>
							</tr>
							</c:forEach>
							</table>
					</td>
				</tr>
				<!-- 紧急联系地址 -->
				<tr>
					<td>
					<h1>紧急联系地址</h1>
					</td>
							<td ><div align="right"><a target="navTab" href='/hrm/empinfo/viewFamilyInfo'> 添加</a></div></td>
				</tr>

				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							
							<tr>
							<td class="td_title" width='20%'><spring:message
						code="hr.viewRelation.title.FAM_TYPE_NAME" /><!--关系--></td>
							<td class="td_title" width='20%'><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /><!--姓名--></td>
							<td class="td_title" width='20%'><spring:message
						code="hr.viewRelation.title.FAM_PHONE" /><!--联系电话--></td>
							<td class="td_title" width='20%'>电话号码<!--电话号码--></td>
							<td class="td_title" width='20%'>E-Mail<!--E-Mail--></td>
							<td class="td_title" width='20%'>主要联络处与否<!--E-Mail--></td>
							</tr>
							<c:forEach items="${emergencyAddressList}" var="item" varStatus="i">
							<tr>
							<td class="td_type">${item.EMER_TYPE_CODE}</td>
							<td class="td_type">${item.EMER_NAME}</td>
							<td class="td_type">${item.EMER_PHONE}</td>
							<td class="td_type">${item.EMER_PHONE_SECOND}</td>
							<td class="td_type">${item.EMER_EMAIL}</td>
							<td class="td_type">${item.EMERGENCY_NO}</td>
							</tr>
							</c:forEach>
							
							</table>
					</td>
				</tr>

			</table>
		</div>
	</div>