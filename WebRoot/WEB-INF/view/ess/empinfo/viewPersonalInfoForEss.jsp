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

	document.getElementById("photoHref").href = "/ess/empinfo/phothChange?EMPID="
			+ empid + "&PERSON_ID=" + personId + "&CPNY_ID=" + cpnyID;

	document.getElementById("photoHref").click();

}

function changePic1(photo) {
	document.getElementById(photo).src = '/resources/photo/default.jpg';
}
function openOnRight(url, relId) {

	$("#right_open").attr("href", url);
	$("#right_open").attr("rel", relId);
	$("#right_open").click();
}
</script>
			<input type="hidden" id="isEssSystem" value="${isEssSystem }" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				height="140">
				<tr>
					<td>
						<h1>
						<!--基本信息 --><spring:message code="ess.empInfo.essential_information" />
						</h1>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<div>
							<%@ include
								file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess1.jsp"%>
						</div>
					</td>
				</tr>
				<!-- end -->
				<!-- 个人事项 -->
				<tr>
					<td width="100%">
						</br>
						<table width="99%">
							<tr>
								<td width="90%">
									<h1>
									<!--个人事项--><spring:message code="ess.empInfo.personal_matters" />
									</h1>
								</td>
								<td>
									<a class="buttonActive" href="/ess/empinfo/updatePersonalInfo"
										target="dialog" width="1000" height="400" mask="true"> <span><!--修正 --><spring:message code="ess.empInfo.correct" /></span>
									</a>
								</td>
							</tr>
						</table>
						</br>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<tr>
								<td class="td_title" width="10%">
								<!--生日--><spring:message code="ess.empInfo.birth" />
								</td>
								<td class="td_type" width="15%">
								${personInfo.DOB}	
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.viewPersonalInfo.title.SEX" />
									<!--性别-->
								</td>
								<td class="td_type" width="15%">
									${personInfo.SEXCODE_NAME}
								</td>
								<td class="td_title" width="10%">
								<!--婚姻状况--><spring:message code="ess.empInfo.marital_status" />
								</td>
								<td class="td_type" width="15%">
									${personInfo.MARITAL_STATUS_NAME }
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.viewPersonalInfo.title.NATION_NAME" />
									<!--民族 -->
								</td>
								<td class="td_type" width="15%">
									${personInfo.NATION_NAME}
								</td>
							</tr>
							<tr>
								<td class="td_title" width="10%">
								<!-- 家庭电话 --><spring:message code="ess.empInfo.home_phone" />
								</td>
								<td class="td_type" width="15%">
									${personInfo.HOME_PHONE}
								</td>
								<td class="td_title" width="10%">
								<!--公司电话--><spring:message code="sys.basicMaint.title.companyTelPhoneNo" />
								</td>
								<td class="td_type" width="15%">
									${personInfo.OFFICE_PHONE}
								</td>
								<td class="td_title" width="10%">
								<!--手机号码--><spring:message code="hrm.empinfo.CELLPHONE" />
								</td>
								<td class="td_type" width="15%">
									${personInfo.CELLPHONE }
								</td>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分--></td>
								<td class="td_type" width="15%">
									${personInfo.RESIDENTIAL_DISTINCTION_NAME }
								</td>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<td class="td_title" width="10%"></td>
								<td class="td_type" width="15%"></td>
								</c:if>
							</tr>
							<tr>
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
								<td class="td_type" width="15%">
									${personInfo.EMAIL_SECOND}
								</td>
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
								<td class="td_type" width="15%">
									${personInfo.EMAIL}
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /> <!--国籍-->
								</td>
								<td class="td_type" width="15%">
									${personInfo.NATIONALITY_NAME}
								</td>
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
								<td class="td_type" width="15%">
									${personInfo.SING_ID }
								</td>
							</tr>
							<c:if test="${LoginUser.cpnyId eq 'HAE'}">
							<tr>
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
								<td class="td_type" width="15%">
									${personInfo.FILE_LOCATION}</td>
								<td class="td_title" width="10%"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
								<td class="td_type" width="15%">
									${personInfo.FILE_ENTER}</td>	
								<td class="td_title" width="10%"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
								<td class="td_type" width="15%">
									${personInfo.FILE_OUT}</td>	
								<td class="td_title" width="10%"></td>
								<td class="td_type" width="15%"></td>
							</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<!-- 地址信息 -->
				<tr>
					<td width="100%">
						</br>
						<table width="99%">
							<tr>
								<td width="90%">
									<h1>
									<!--地址信息--><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
									</h1>
								</td>
								<td>
									<a class="buttonActive" href="/ess/empinfo/viewAddressInfo"
										target="dialog" width="1000" height="400" mask="true"> <span><!--添加 --><spring:message code="ess.empInfo.insert" /></span>
									</a>
								</td>
							</tr>
						</table>
						</br>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<tr>
								<td class="td_title" width="10%">
								<!--地址类型--><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
								</td>
								<td class="td_title" width="10%">
									<spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" />
									<!--有效开始日期-->
								</td>
								<td class="td_title" width="10%">
								<!--地址--><spring:message code="ess.empInfo.address" />
								</td>
							</tr>
							<c:forEach items="${addressList}" var="item"
								varStatus="i">
								<tr>
									<td class="td_type">
											<a mask="true" style="color: blue;"
											href="/ess/empinfo/updateAddressInfo?ADDRESS_NO=${item.ADDRESS_NO}"
											target="dialog" title="<spring:message code='hrm.empinfo.FAM_ADDRESS_TYPE' />">${item.ADDRESS_TYPE_NAME}</a><!-- 地址类型 -->
									</td>
									<td class="td_type">
										${item.EFFECTIVE_START_DATE}
									</td>
									<td class="td_type">
										${item.ADDRESS_CONTENT}
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			<!--家庭事项  -->
				<tr>
					<td width="100%">
						</br>

						<table width="99%">
							<tr>
								<td width="90%">
									<h1>
									<!--家庭成员信息--><spring:message code="ess.empInfo.family_member_information" />
									</h1>

								</td>
								<td>
									<a mask="true" class="buttonActive"
										href='/ess/empinfo/viewHomeRelationInfo' target="dialog"
										width="800" height="420"><span><!--添加 --><spring:message code="ess.empInfo.insert" /></span> </a>
								</td>
							</tr>
						</table>
						</br>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<tr>
								<td class="td_title" width='20%'>
									<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
									<!--关系-->
								</td>
								<td class="td_title" width='20%'>
									<spring:message code="inct.salesman.Name" />
									<!--姓名-->
								</td>
								<td class="td_title" width='20%'>
									<spring:message code="empsubject.sexName" />
									<!--性别-->
								</td>
								<td class="td_title" width='20%'>
								<!--出生日期--><spring:message code="hrm.empinfo.FAM_BORNDATE" />
								</td>
								<td class="td_title" width='20%'>
								<!--家庭电话--><spring:message code="hrm.empinfo.FAM_FAMILY_PHONE" />
								</td>
							</tr>
							<c:forEach items="${homeRelationList}" var="item" varStatus="i">
								<tr>
									<td class="td_type">
										<a mask="true" style="color: blue;"href="/ess/empinfo/updateHomeRelation?FAMILY_NO=${item.FAMILY_NO}"
										target="dialog" width="800" height="420" title="<spring:message code='ess.empInfo.family_member_information' />">${item.FAM_TYPE_NAME}</a><!-- 家庭成员信息 -->
									</td>
									<td class="td_type">
									${item.FAM_NAME}
									</td>
									<td class="td_type">
									${item.GENDER_NAME}
									</td>
									<td class="td_type">
									${item.FAM_BORNDATE}	
									</td>
									<td class="td_type">
									${item.FAM_FAMILY_PHONE}	
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<!-- 紧急联系人-->
				<tr>
					<td width="100%">
						</br>

						<table width="99%">
							<tr>
								<td width="90%">
									<h1>
									<!--紧急联系人--><spring:message code="ess.empInfo.urgent_contact_person" />
									</h1>
								</td>
								<td>
									<a mask="true" class="buttonActive"
										href='/ess/empinfo/viewEmergencyAddressInfo' target="dialog"><span><!--添加 --><spring:message code="ess.empInfo.insert" /></span>
									</a>
								</td>
							</tr>
						</table>
						</br>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="user_table">
							<tr>
								<td class="td_title" width='20%'>
									<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
									<!--姓名-->
								</td>
								<td class="td_title" width='20%'>
									<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
									<!--关系-->
								</td>
								<td class="td_title" width='20%'>
									<spring:message code="hr.viewRelation.title.FAM_PHONE" />
									<!--联系电话-->
								</td>
								<td class="td_title" width='20%'>
									<!-- E-Mail --><spring:message code="hrm.empinfo.EMAIL" />
								</td>
								<td class="td_title" width='20%'>
								<!--地址--><spring:message code="ess.empInfo.address" />
								</td>
								<td class="td_title" width='20%'>
								<!--主要联络处与否--><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" />
								</td>
							</tr>
							<c:forEach items="${emergencyAddressList}" var="item"
								varStatus="i">
								<tr>
									<td class="td_type">
											<a mask="true" style="color: blue;"
											href="/ess/empinfo/updateEmergencyAddress?EMERGENCY_NO=${item.EMERGENCY_NO}"
											target="dialog" title="<spring:message code='ess.empInfo.urgent_contact_person' />">${item.EMER_NAME}</a><!-- 紧急联系人 -->
									</td>
									<td class="td_type">
										${item.EMER_TYPE_NAME}
									
									</td>
									<td class="td_type">
										${item.EMER_PHONE}
									</td>
<!-- 									<td class="td_type"> -->
<%-- 										${item.EMER_PHONE_SECOND} --%>
<!-- 									</td> -->
									<td class="td_type">
										${item.EMER_EMAIL}
									</td>
									<td class="td_type">
										${item.EMER_ADDRESS}
									</td>
									<td class="td_type">
										<input type="checkbox" disabled="disabled" <c:if test="${item.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input>
									</td>
								</tr>
							</c:forEach>
				</tr>
			</table>

		</div>
	</div>