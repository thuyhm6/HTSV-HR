<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var icon = 0;
	var loggedAccount = null;
	//定时触发验证方法
	$(document).ready(function() {

		//setInterval("checkSession()", 1000 * 1860);
	});
	//验证session是否失效
	function checkSession() {

		if (icon == 0) {
			$.ajax({
				type : 'POST',
				url : "/login/validateSession/checkSession",
				cache : false,
				dataType : "text",
				success : function(result) {
					if (result !== "0") {
						//$("#loginAgain").css("display","block");
						alert('<spring:message code="hrm.alert.empinfo.session_invalid"/>');//session已失效 请重新登陆！
						window.opener = null;
						window.open("", "_self");
						window.close();
						icon = 1;
					}
				}
			});
		}
	}
</script>
<c:set value="" var="HR_SUB_MENU_CODE" />
<c:set value="" var="AR_SUB_MENU_CODE" />
<c:set value="" var="PA_SUB_MENU_CODE" />
<c:set value="" var="ORG_SUB_MENU_CODE" />
<c:set value="" var="SY_SUB_MENU_CODE" />
<c:set value="" var="EDU_SUB_MENU_CODE" />
<c:set value="" var="EVS_SUB_MENU_CODE" />
<c:forEach var="menuInfo" items="${leftList}" varStatus="i">
	<c:choose>
		<c:when test="${menuInfo.MENU_CODE == 'hr0000'}">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${HR_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="HR_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${HR_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="HR_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'ar0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${AR_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="AR_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${AR_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="AR_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'pa0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${PA_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="PA_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${PA_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="PA_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'org0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${ORG_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="ORG_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${ORG_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="ORG_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'sy0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${SY_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="SY_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${SY_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="SY_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'edu0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${EDU_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="EDU_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${EDU_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="EDU_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:when test="${menuInfo.MENU_CODE == 'evs0000' }">
			<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
				<c:choose>
					<c:when test="${EVS_SUB_MENU_CODE == '' }">
						<c:set value="${menuInfo2.MENU_CODE }" var="EVS_SUB_MENU_CODE" />
					</c:when>
					<c:otherwise>
						<c:set value="${EVS_SUB_MENU_CODE}+${menuInfo2.MENU_CODE }"
							var="EVS_SUB_MENU_CODE" />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
	</c:choose>
	<%-- <c:if test="${menuInfo.MENU_CODE != 'ess0000'}">
		<li id="nav_${menuInfo.MENU_CODE}" class="selected"><a href="#"
			onclick="menuControl('${menuInfo.MENU_CODE}','${SUB_MENU_CODE}','${menuInfo.MENU_URL}','pageNum=1&menuNo=${menuInfo.MENU_NO}&navTabId=${menuInfo.MENU_CODE}','${menuInfo.MENU_NAME}');">
				<span> ${menuInfo.MENU_NAME} -- ${menuInfo.MENU_CODE} </span>
		</a></li>
	</c:if> --%>
	<%-- <c:set value="${menuInfo.MENU_NAME}" var="MENU_NAME" /> --%>
</c:forEach>
<div class="navTab-panel tabsPageContent layoutBox">
	<div class="page unitBox">
		<div
			style="margin: auto; width: 1920px;height: 890px; background: url('/resources/images/1024px.jpg') no-repeat;">
			<div style="margin-left: 0px; margin-top: 0px; float: left;">
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1 or isRecruitUser == 1 or isInformationUser== 1}">
						<div
							style="width: 110px; margin-left: 502px; margin-top: 265px; height: 110px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('hr0000','${HR_SUB_MENU_CODE}','/hrm/empinfo/viewHrMain','pageNum=1&menuNo=2538&navTabId=hr0000','<spring:message code="hrm.empinfo.HR_system"/>');"><!-- 人事系统 -->
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 110px; ">
										<%-- <font color="white" style="font-size: 18px;font-weight: bold;"><!-- 人 事 --><spring:message code="sys.mainHub.RENSHI.b"/><br><br><!-- 管 理 --><spring:message code="sys.mainHub.GUANLI.b"/></font> --%>
										<font color="white" style="font-size: 18px;font-weight: bold;"><!-- 人 事 -->HR Profile</font>
										</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: 502px; margin-top: 265px; height: 110px; float: left;">
							<div>
								<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 110px; ">
									<font color="#CCCCCC"><!-- 组 织 -->HR Profile</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isOrgUser == 1}">
						<div
							style="width: 110px; margin-left: -320px; margin-top: 295px; height: 40px; float: left;">
							<div>
								 	<a href="#" style="text-decoration: none;"
									onclick="menuControl('org0000','${ORG_SUB_MENU_CODE}','/org/orgManage/viewOrgIndex','pageNum=1&menuNo=2581&navTabId=org0000','<spring:message code="hrm.empinfo.organization_system"/>');"><!-- 组织系统 -->   
							 
							<%-- 	<a href="#" style="text-decoration: none;"
									onclick=" menuControl('org0000','${ORG_SUB_MENU_CODE}','/org/orgManage/viewResumeProcess','pageNum=1&menuNo=2581&navTabId=org0000','改编流程');">--%>
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px;" >
										<%-- <font color="black" style="font-size: 16px;font-weight: bold;"><!-- 组 织 --><spring:message code="sys.mainHub.ZUZHI.b"/></font>--%>
										<font color="white" style="font-weight: bold;"><!-- 组 织 -->Organization</font>
									</div> 
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: -320px; margin-top: 295px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
									<font color="#CCCCCC"><!-- 组 织 -->Organization</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isTraUser == 1}">
						<div
							style="width: 110px; margin-left: -262px; margin-top: 155px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('edu0000','edu0100+edu0200+edu0300+edu0400+edu0500','/edu/traineducation/viewTrainEducation','pageNum=1&menuNo=14014453&navTabId=edu0000','<spring:message code="edu.viewTrainEducation.PEIXUNJIAOYU.a"/>');">
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
										<font color="white" style="font-weight: bold;"><!-- 培训 --><!-- Training -->Training</font>
									</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: -262px; margin-top: 155px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
									<font color="#CCCCCC"><!-- 培训 --><!-- Training -->Training</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
						<div
							style="width: 110px; margin-left: -108px; margin-top: 90px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('ar0000','${AR_SUB_MENU_CODE}','/ar/attendanceSettings/viewArNavigationPage','pageNum=1&menuNo=2436&navTabId=ar0000','<spring:message code="hrm.empinfo.Time_Attendance"/>');"><!-- 考勤系统 -->
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
										<font color="white" style="font-weight: bold;"><!-- 考 勤 -->Attendance</font>
									</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: -108px; margin-top: 90px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
									<font color="#CCCCCC"><!-- 考 勤 -->Attendance</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isWageUser == 1 or isBaoXianUser == 1}">
						<div
							style="width: 110px; margin-left: 55px; margin-top: 156px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('pa0000','${PA_SUB_MENU_CODE}','/pa/salary/viewPaMain','pageNum=1&menuNo=2391&navTabId=pa0000','<spring:message code="hrm.empinfo.wage_sysrem"/>');"><!-- 工资系统 -->
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
										<font color="white" style="font-weight: bold;"><!-- 工 资 -->Payroll</font>
									</div>
								</a>
									<!-- <a href="#" style="text-decoration: none;"
									onclick="navTabNum('/pa/workManagement/viewPaWorkFlow','pageNum=1&menuNo=14013761&navTabId=pa0813','pa0813','计算流程');">
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 40px; font-size: 16px;">
										<font color="white">Payroll</font>
									</div> -->
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: 55px; margin-top: 156px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
									<font color="#CCCCCC"><!-- 工 资 -->Payroll</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isEvsUser == 1 }">
						<div
							style="width: 110px; margin-left: -65px; margin-top: 295px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('evs0000','${EVS_SUB_MENU_CODE}','/evs/manage/viewEvsIndex','pageNum=1&menuNo=14015021&navTabId=evs0000&evsType=performance','<spring:message code="login.PINGJIAGUANLI.Z" />');"><!-- 评价管理 -->
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
										<font color="white" style="font-weight: bold;"><!-- 业绩考核 -->Evaluation</font>
									</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: -65px; margin-top: 295px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 70px; font-size: 16px;">
									<font color="#CCCCCC"><!-- 业绩考核 -->Evaluation</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<%-- <c:choose>
					<c:when
						test="${isSuperUser == 1 or isSuperHrUser == 1 or isEvsUser == 1}">
						<div
							style="width: 110px; margin-left: 752px; margin-top: -195px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('evs0000','${EVS_SUB_MENU_CODE}','/evs/manage/viewEvsIndex','pageNum=1&menuNo=14015021&navTabId=evs0000&evsType=ability','评价管理');">
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 40px; font-size: 16px;">
										<font color="white">力量考核</font>
									</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: 752px; margin-top: -195px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 40px; font-size: 16px;">
									<font color="#CCCCCC">力量考核</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${ LoginUser.cpnyName == 'SST'and ( isSuperUser == 1 or isSuperHrUser == 1 or isEvsUser == 1 ) }">
						<div
							style="width: 110px; margin-left: 888px; margin-top: -195px; height: 40px; float: left;">
							<div>
								<a href="#" style="text-decoration: none;"
									onclick="menuControl('evs0000','${EVS_SUB_MENU_CODE}','/evs/manage/viewEvsIndex','pageNum=1&menuNo=14015021&navTabId=evs0000&evsType=probation','评价管理');">
									<div
										style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 40px; font-size: 16px;">
										<font color="white">试用期考核</font>
									</div>
								</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="width: 110px; margin-left: 888px; margin-top: -195px; height: 40px; float: left;">
							<div>
								<div
									style="display: table-cell; vertical-align: middle; text-align: center; width: 110px; height: 40px; font-size: 16px;">
									<font color="#CCCCCC">试用期考核</font>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose> --%>
				
				
			</div>
			<div
				style="width: 630px; margin-left: -560px; margin-top: 450px; float: left;">
				<div
					style="width: 150px; margin-left: -20px; margin-top: 80px; height: 11px; float: left;">
					<%-- <div style="font-size: 8px;"><spring:message code="hrm.empinfo.emp_statistics"/><!-- 人员统计 --></div> --%>
					<div ><font style="font-size: 18px;" color="#278e9e">Distinguish</font></div>
				</div>
				<div
					style="width: 100px; margin-left: 10px; margin-top: 80px; height: 11px; float: left;">
					<div ><font style="font-size: 18px;" color="#278e9e">Last Year</font>
						<%-- <font color="#278e9e"><spring:message code="hrm.empinfo.last_year"/><!-- 上一年度 --></font> --%>
					</div>
				</div>
				<div
					style="width: 100px; margin-left: 25px; margin-top: 80px; height: 11px; float: left;">
					<div ><font style="font-size: 18px;" color="#278e9e">Last Month</font>
					</div>
				</div>
				<div
					style="width: 150px; margin-left: 35px; margin-top: 80px; height: 11px; float: left;">
					<div ><font style="font-size: 18px;" color="#278e9e">Current Month</font>
					</div>
				</div>
				<%-- <c:choose>
					<c:when test="${LoginUser.cpnyName == 'TSTO' }"> --%>
				<div
					style="width: 200px; margin-left: -570px; margin-top: 118px; height: 11px; float: left;">
					<div><font style="font-size: 16px;" >Total Of Employee</font></div>
					<%-- <div style="font-size:16px;"><spring:message code="hrm.empinfo.totalemp_number"/><!-- 员工总人数 --></div> --%>
				</div>
				<div
					style="width: 100px; margin-left: -380px; margin-top: 118px; height: 11px; float: left;">
					<div >
						<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastYear&TYPE=TOTAL" mask="true"  target="dialog">
						<span style="font-size:16px;">${totalEmpCountLastYear }</span> </a>
					</div>
				</div>
				<div
					style="width: 100px; margin-left: -255px; margin-top: 118px; height: 11px; float: left;">
					<div >
						<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastMonth&TYPE=TOTAL" mask="true"  target="dialog">
						<span style="font-size:16px;">${totalEmpCountLastMonth }</span> </a>
					</div>
				</div>
				<div
					style="width: 100px; margin-left: -100px; margin-top: 118px; height: 11px; float: left;">
					<div >
						<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=currMonth&TYPE=TOTAL" mask="true"  target="dialog">
						<span style="font-size:16px;">${totalEmpCountCurrMonth }</span> </a>
					</div>
				</div>
				<div
					style="width: 200px; margin-left: -570px; margin-top: 155px; height: 11px; float: left;">
					<%-- <div style="font-size:16px;"><spring:message code="hrm.empinfo.Jobemp_number"/><!-- 在职员工人数 --></div> --%>
					<div><font style="font-size: 16px;" >Working</font></div>
				</div>
				<div
					style="width: 100px; margin-left: -380px; margin-top: 155px; height: 11px; float: left;">
					<div >
					<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastYear&TYPE=INCUMBENCY" mask="true"  target="dialog">
						<span style="font-size:16px;">${InCpnyTotalEmpCountLastYear }</span> </a></div>
				</div>
				<div
					style="width: 100px; margin-left: -255px; margin-top: 155px; height: 11px; float: left;">
					<div >
					<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastMonth&TYPE=INCUMBENCY" mask="true"  target="dialog">
						<span style="font-size:16px;">${InCpnyTotalEmpCountLastMonth}</span> </a></div>
				</div>
				<div
					style="width: 100px; margin-left: -100px; margin-top: 155px; height: 11px; float: left;">
					<div >
					<a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=currMonth&TYPE=INCUMBENCY" mask="true"  target="dialog">
						<span style="font-size:16px;">${InCpnyTotalEmpCountCurrMonth }</span></a></div>
				</div>
				<div
					style="width: 200px; margin-left: -570px; margin-top: 192px; height: 11px; float: left;">
					<%-- <div style="font-size:16px;"><spring:message code="hrm.empinfo.retiredemp_number"/><!-- 退职员工人数 --></div> --%>
					<div><font style="font-size: 16px;" >Resigned</font></div>
				</div>
				<div
					style="width: 100px; margin-left: -380px; margin-top: 192px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastYear&TYPE=QUIT" mask="true"  target="dialog">
						<span style="font-size:16px;">${LeftManTotalEmpCountLastYear }</span></a></div>
				</div>
				<div
					style="width: 100px; margin-left: -255px; margin-top: 192px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastMonth&TYPE=QUIT" mask="true"  target="dialog">
						<span style="font-size:16px;">${LeftManTotalEmpCountLastMonth }</span></a></div>
				</div>
				<div
					style="width: 100px; margin-left: -100px; margin-top: 192px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=currMonth&TYPE=QUIT" mask="true"  target="dialog">
						<span style="font-size:16px;">${LeftManTotalEmpCountCurrMonth }</span></a></div>
				</div>
				<div
					style="width: 200px; margin-left: -570px; margin-top: 226px; height: 11px; float: left;">
					<%-- <div style="font-size:16px;"><spring:message code="hrm.empinfo.new_Entryemp_number"/><!-- 新入职员工人数 --></div> --%>
					<div><font style="font-size: 16px;" >Newcomer</font></div>
				</div>
				<div
					style="width: 100px; margin-left: -380px; margin-top: 226px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastYear&TYPE=NEW" mask="true"  target="dialog">
						<span style="font-size:16px;">${NewManTotalEmpCountLastYear }</span></a></div>
				</div>
				<div
					style="width: 100px; margin-left: -255px; margin-top: 226px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=lastMonth&TYPE=NEW" mask="true"  target="dialog">
						<span style="font-size:16px;">${NewManTotalEmpCountLastMonth }</span></a></div>
				</div>
				<div
					style="width: 100px; margin-left: -100px; margin-top: 226px; height: 11px; float: left;">
					<div ><a style="cursor: pointer;" width="800" height="400" href="/login/getPersonInfo?FLAG=currMonth&TYPE=NEW" mask="true"  target="dialog">
						<span style="font-size:16px;">${NewManTotalEmpCountCurrMonth }</span></a></div>
				</div>
				<%-- </c:when>
					<c:otherwise>
						<div
							style="width: 100px; margin-left: -540px; margin-top: 138px; height: 11px; float: left;">
							<div style="font-size:16px; ">员工总人数</div>
						</div>
						<div
							style="width: 100px; margin-left: -345px; margin-top: 138px; height: 11px; float: left;">
							<div style="font-size:16px; ">276</div>
						</div>
						<div
							style="width: 100px; margin-left: -215px; margin-top: 138px; height: 11px; float: left;">
							<div style="font-size:16px; ">0</div>
						</div>
						<div
							style="width: 100px; margin-left: -100px; margin-top: 138px; height: 11px; float: left;">
							<div style="font-size:16px; ">291</div>
						</div>
						<div
							style="width: 100px; margin-left: -540px; margin-top: 162px; height: 11px; float: left;">
							<div style="font-size:16px; ">在职员工人数</div>
						</div>
						<div
							style="width: 100px; margin-left: -345px; margin-top: 162px; height: 11px; float: left;">
							<div style="font-size:16px; ">262</div>
						</div>
						<div
							style="width: 100px; margin-left: -215px; margin-top: 162px; height: 11px; float: left;">
							<div style="font-size:16px; ">0</div>
						</div>
						<div
							style="width: 100px; margin-left: -100px; margin-top: 162px; height: 11px; float: left;">
							<div style="font-size:16px; ">273</div>
						</div>
						<div
							style="width: 100px; margin-left: -540px; margin-top: 186px; height: 11px; float: left;">
							<div style="font-size:16px; ">退职员工人数</div>
						</div>
						<div
							style="width: 100px; margin-left: -345px; margin-top: 186px; height: 11px; float: left;">
							<div style="font-size:16px; ">50</div>
						</div>
						<div
							style="width: 100px; margin-left: -215px; margin-top: 186px; height: 11px; float: left;">
							<div style="font-size:16px; ">0</div>
						</div>
						<div
							style="width: 100px; margin-left: -100px; margin-top: 186px; height: 11px; float: left;">
							<div style="font-size:16px; ">0</div>
						</div>
						<div
							style="width: 100px; margin-left: -540px; margin-top: 208px; height: 11px; float: left;">
							<div style="font-size:16px; ">新入职员工人数</div>
						</div>
						<div
							style="width: 100px; margin-left: -345px; margin-top: 208px; height: 11px; float: left;">
							<div style="font-size:16px; ">111</div>
						</div>
						<div
							style="width: 100px; margin-left: -215px; margin-top: 208px; height: 11px; float: left;">
							<div style="font-size:16px; ">0</div>
						</div>
						<div
							style="width: 100px; margin-left: -100px; margin-top: 208px; height: 11px; float: left;">
							<div style="font-size:16px; ">1</div>
						</div>
					</c:otherwise>
				</c:choose> --%>
			</div>
			<div
				style="width: 170px; margin-left: 309px; margin-top: 90px; float: left;">
				<div
					style="width: 150px; margin-left: -20px; margin-top: 42px; height: 11px; float: left;">
					<div><font style="font-size: 16px;" >To Do / Check</font></div>
				</div>
<%-- 				<div
					style="width: 170px; margin-left: 0px; margin-top: 15px; height: 11px; float: left;">
					<a href="#" style="text-decoration: none;"
						onclick="navTabNum('/hrm/approve/viewEssApplyInfo','pageNum=1&menuNo=14014333&navTabId=sy0501','sy0501','<spring:message code="hrm.empinfo.emp_Information_change_management"/>');"><span>*<!-- 员工信息改变管理 -->
							Change Request</span> (<span style="font-weight: bold">${getApplyNumber.NUMB}</span>)</a>
				</div> --%>
				<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
					<div
						style="width: 200px; margin-left: -20px; margin-top: 20px; height: 11px; float: left;">
						<div>
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/hrm/contractInfo/viewNOContractInfo','specialNumPerPage=20&pageNum=1&menuNo=5308&navTabId=hr0305','hr0305','<spring:message code="hrm.empinfo.contract_sign"/>');"><span style="font-size: 14px;" >*
								<spring:message code="hrm.empinfo.no_sign_contract"/><!-- 未签合同 --> (<span style="font-weight: bold">${getNotExistsContractCnt}</span>)
						</span></a>
						</div>
					</div>
					<div
						style="width: 200px; margin-left: -20px; margin-top: 10px; height: 11px; float: left;">
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/hrm/contractInfo/viewExpiredContract','specialNumPerPage=20&pageNum=1&menuNo=2558&navTabId=hr0301','hr0301','<spring:message code="hrm.empinfo.contract_renew"/>');"><span style="font-size: 14px;">*
								<spring:message code="hrm.empinfo.Expiration_contract"/><!-- 合同到期 --> (<span style="font-weight: bold">${getContractCnt}</span>)
						</span></a>
					</div>
					<div
						style="width: 200px; margin-left: -20px; margin-top: 10px; height: 11px; float: left;">
						<a href="/edu/traineducation/photoMissing"
							style="text-decoration: none;" rel="photomissing" target="navTab"><span style="font-size: 14px;">*
								<spring:message code="login.PHOTO_MISSING.Z"/><!-- Photo Missing --></span> (<span style="font-weight: bold">${getPersonalPhotoNullNumber.NUMB}</span>)</a>
					</div>
					<!--<c:if test="${LoginUser.cpnyId eq 'HTSV' || LoginUser.cpnyId eq 'SPC_SH'}">
					<div
						style="width: 170px; margin-left: 0px; margin-top: 10px; height: 11px; float: left;">
						<a href="/hrm/contractInfo/viewExpiredIdCard"
							style="text-decoration: none;"  target="navTab"><span>*
								<spring:message code="hrm.empinfo.Expiration_IdCard"/> 身份证到期 </span> (<span style="font-weight: bold">${getIdCardCnt}</span>)</a>
					</div>
					</c:if>
					--><%-- <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<div
						style="width: 200px; margin-left: -20px; margin-top: 10px; height: 11px; float: left;">
						<a href="/hrm/contractInfo/viewPaNotImport" style="text-decoration: none;"  target="navTab">
							<span>*<!--工资未导入--><spring:message code="sys.mainHub.GONGZIWEIDAORU.b"/></span> (<span style="font-weight: bold">${getPaNotImportCnt}
							</span>)
						</a>
					</div>
					</c:if> --%>
					<%--  <c:if test="${defaultRoleGroupName eq 'Coordinator' or defaultRoleGroupName eq 'Management'}"> --%>
					<div
						style="width: 200px; margin-left: -20px; margin-top: 10px; height: 11px; float: left;">
						<a href="/hrm/contractInfo/viewBecomeRegularWarn" style="text-decoration: none;"  target="navTab">
							<span style="font-size: 14px;">*&nbsp;<!--转正提醒--><spring:message code="sys.mainHub.ZHUANZHENGTIXING.b"/></span> (<span style="font-weight: bold">${getBecomeRegulerWarn}
							</span>)
						</a>
					</div>
					<%-- </c:if> --%>
				</c:if>
			</div>
			<div
				style="width: 130px; margin-left: -6px; margin-top: 90px; float: left;">
				<div
					style="width: 130px; margin-left: 0px; margin-top: 42px; height: 11px; float: left;">
					<span style="font-size: 16px;">System tools<!-- 外部链接 --></span>
				</div>
				<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
					<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or LoginUser.adminID eq '35448253' or LoginUser.adminID eq '35451298'}">
					<div
						style="width: 170px; margin-left: 0px; margin-top: 20px; height: 11px; float: left;">
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/sys/rightsManagement/viewLoginUser','pageNum=1&menuNo=2342&navTabId=sy0502','sy0502','<spring:message code="hrm.empinfo.User_privilege_management"/>');"><span style="font-size: 14px;">*<!-- 用户权限管理 -->
								<spring:message code="hrm.empinfo.User_privilege_management"/><!-- 用户权限管理 --></span></a>
					</div>
					</c:if>
					<div
						style="width: 170px; margin-left: 0px; margin-top: 10px; height: 11px; float: left;">
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/sys/affirmSpecial/viewAffirmSpecialList','pageNum=1&menuNo=14013486&navTabId=sy0010sp','sy0010sp','<spring:message code="hrm.empinfo.SpecialCuttingLine"/>');"><span style="font-size: 14px;">*<!-- 用户权限管理 -->
								<spring:message code="hrm.empinfo.SpecialCuttingLine"/><!-- 特殊决裁线设置 --></span></a>
					</div>
					<div
						style="width: 170px; margin-left: 0px; margin-top: 10px; height: 11px; float: left;">
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/sys/arAffirm/viewAffirmSearchList','pageNum=1&menuNo=218499&navTabId= sy0485','sy0485','<spring:message code="hrm.empinfo.SelectSpecialCuttingLine"/>');"><span style="font-size: 14px;">*<!-- 用户权限管理 -->
								<spring:message code="hrm.empinfo.SelectSpecialCuttingLine"/><!-- 特殊决裁线设置 --></span></a>
					</div>
				</c:if>

					<div
						style="width: 170px; margin-left: 0px; margin-top: 10px; height: 11px; float: left;">
						<a href="#" style="text-decoration: none;"
							onclick="navTabNum('/evs/manage/viewFileRoomList','pageNum=1&menuNo=2343&navTabId=FILE_ROOM','FILE_ROOM','<spring:message code="hrm.empinfo.Reference_room"/>');"><span style="font-size: 14px;">*<!-- 资料室 -->
								<spring:message code="hrm.empinfo.Reference_room"/><!-- 资料室 --></span></a>
					</div>
				<%--<c:if test="${fn:contains(LoginUser.adminID,'111111')}">
					<div style="width: 170px; margin-left: 0px; margin-top: 10px; height: 11px; float: left;">
					<a class="updateparam" href="${run_url}" target="dialog" width="650" height="600"  rel="exceldlog" mask="true">
						<a style="text-decoration: none;" class="edit" href="/disc/autoExcel/runSql?SQL_SEQ=299" target="dialog" width="650" height="600"  rel="exceldlog" mask="true">* <spring:message code="hrm.login.DIANPKAOQIN.Z"/><!-- 店铺考勤使用情况 --></a>
					</div>
				</c:if>--%>
					<%-- 	<div style="width: 130px; margin-left: 0px; margin-top: 15px; height: 11px; float: left;">
							<a href="#" style="text-decoration: none;"
						onclick="navTabNum('/ess/arConfirm/viewLeaveConfirmList','seach_CONFIRM_FLAG=0&pageNum=1&menuNo=14015695&navTabId=ar0903','ar0903','休假人事确认');"><span>*
							休假人事确认 (<span style="font-weight: bold">${getLeaveConfirmCnt}</span>)
					</span></a>
						</div>
						<div style="width: 130px; margin-left: 0px; margin-top: 15px; height: 11px; float: left;">
							<a href="#" style="text-decoration: none;"
						onclick="navTabNum('/ess/arConfirm/viewPOtApplyInfoConfirmList','seach_CONFIRM_FLAG=0&pageNum=1&menuNo=14015693&navTabId=ar0902','ar0902','加班人事确认');"><span>*
							加班人事确认 (<span style="font-weight: bold">${getOtConfirmCnt }</span>)
					</span></a>
						</div>
						<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
						<div style="width: 150px; margin-left: 0px; margin-top: 15px; height: 11px; float: left;">
							<a href="#" style="text-decoration: none;"
						onclick="navTabNum('/ess/tempEmp/viewTempEmpConfirmList','seach_ACTIVITY=0&pageNum=1&menuNo=14015696&navTabId=ar0904','ar0904','小时工入离职人事确认');"><span>*
							小时工入离职人事确认 (<span style="font-weight: bold">${getTempConfirmCnt }</span>)
					</span></a>
						</div>
						</c:if>
						<div style="width: 150px; margin-left: 0px; margin-top: 15px; height: 11px; float: left;">
							<a href="#" style="text-decoration: none;"
						onclick="navTabNum('/ess/tempEmp/viewEmpLeftConfirmList','seach_ACTIVITY=0&pageNum=1&menuNo=14016232&navTabId=ar0906','ar0906','正式工离职人事确认');"><span>*
							正式工离职人事确认 (<span style="font-weight: bold">${getStartedLeftConfirmCnt }</span>)
					</span></a>
						</div> --%>
			</div>
		</div>
	</div>
</div>
<c:if test="${isSuperUser == 1 && LoginUser.username ne 'HQ'}">
	<div style="width: 150px; height: 20px; float: left;">
		<div>
			<a href="#" style="text-decoration: none;"
				onclick="menuControl('sy0000','${SY_SUB_MENU_CODE}','/sys/rightsManagement/viewRolesGroup?SYS_TYPE=0','pageNum=1&menuNo=2463&navTabId=sy0000','<spring:message code="hrm.empinfo.system_management"/>');"><!-- 系统管理 -->
				<div
					style="display: table-cell; vertical-align: middle; text-align: center; width: 150px; height: 40px; font-size: 16px;">
					<spring:message code="hrm.empinfo.system_management"/><!-- 系统管理 --></div>
			</a>
		</div>
	</div>
</c:if>

