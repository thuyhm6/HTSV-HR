<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style media="print">.Noprint { DISPLAY: none }</style>

<title><%--个人信息--%><spring:message code="hr.viewCondSql.title.GERENXINXI"/>&gt;<%--人事记录卡--%><spring:message code="rp.report.title.personalrecord"/></title>
<center class=noprint>
	<OBJECT id="WebBrowser" height="0" width="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" VIEWASTEXT></OBJECT>
	<input onclick="document.all.WebBrowser.ExecWB(6,1)" type="button" value="<spring:message code='rp.report.title.print'/>"/><%--打印--%>
	<input onclick="document.all.WebBrowser.ExecWB(6,6)" type="button" value="<spring:message code='rp.report.title.directprint'/>"/><%--直接打印--%>
    <input onclick="document.all.WebBrowser.ExecWB(8,1)" type="button" value="<spring:message code='rp.report.title.printset'/>"/><%--页面设置--%>
    <input onclick="document.all.WebBrowser.ExecWB(7,1)" type="button" value="<spring:message code='rp.report.title.printpreview'/>"/>&nbsp;<%--打印预览--%>
</center>

<body>
	<table width="98%" border="1" cellpadding="0" cellspacing="0" class="dr_d">
		<tr height="30px">
			
		</tr>
		<tr height="30px">
			<td colspan="10" align="center" class="info_content_01">
				<font size="5"><b><%--员&nbsp;&nbsp;工&nbsp;&nbsp;登&nbsp;&nbsp;记&nbsp;&nbsp;表--%>
					<spring:message code="rp.report.title.hrminforecordtable"/>
				</b></font>
			</td>
		</tr>
		<tr height="20px">
			<td colspan="10" align="center" class="info_content_01">
				<font size="3"><b><%--基本信息--%><spring:message code="hr.viewCondSql.title.JIBENXINXI"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="10%" class="info_title_01" nowrap="nowrap"><%--工号--%>
				<spring:message code="public.title.empId"/>
			</td>
			<td align="center" width="10%" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.EMPID }</td>
			<td align="center" width="10%" class="info_title_01" nowrap="nowrap"><%--姓名--%>
				<spring:message code="public.title.name"/>
			</td>
			<td align="center" width="10%" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.LOCAL_NAME }</td>
			<td align="center" width="15%" class="info_title_01" nowrap="nowrap"><%--民族--%>
				<spring:message code="hr.viewPersonalInfo.title.NATION_NAME"/>
			</td>
			<td align="center" width="20%" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.NATION }</td>
			<td align="center" width="25%" rowspan="7">
				<img align="middle" id="${photoId}" src="${PhotoPath}" style='width:150px;heigh=150px;'/>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--性别--%>
				<spring:message code="hr.viewPersonalInfo.title.SEX"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.SEX }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--出生日期--%>
				<spring:message code="hr.viewPersonalInfo.title.DOB"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.DOB }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--婚姻状况--%>
				<spring:message code="hr.viewCondSql.title.HUNYINZHUANGKUANG"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.MARITAL }</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--部门--%>
				<spring:message code="public.title.deptName"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.DEPARTMENT }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--职(岗)位--%>
				<spring:message code="public.title.positionName"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.POSITION }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--职责--%>
				<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.DUTY }</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--入司日期--%>
				<spring:message code="hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.JOIN_COMPANY_DATE }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--入职日期--%>
				<spring:message code="main.home.message.ruzhiriqi"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.DATE_STARTED }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--参加工作起始日期--%>
				<spring:message code="hr.viewPersonalInfo.title.EXP_DATE"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.EXP_DATE }</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--籍贯--%>
				<spring:message code="hr.viewPersonalInfo.title.BORNPLACE_NAME"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.BORNPLACE }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--身份证号--%>
				<spring:message code="sys.affirm.title.idNumber"/>
			</td>
			<td align="center" width="" class="info_content_01" colspan="3" nowrap="nowrap">&nbsp;${personRecord.IDCARD_NO }</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--家庭住址--%>
				<spring:message code="rp.report.title.homeaddress"/>
			</td>
			<td align="center" width="" class="info_content_01" colspan="5" nowrap="nowrap">&nbsp;${personRecord.HOME_ADDRESS }</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--户籍所在--%>
				<spring:message code="rp.report.title.bornaddress"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.REG_PLACE }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--户口性质--%>
				<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.REG_TYPE }</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--联系方式--%>
				<spring:message code="hr.viewWorkInfo.title.TEL"/>
			</td>
			<td align="center" width="" class="info_content_01" nowrap="nowrap">&nbsp;${personRecord.HOME_PHONE }</td>
		</tr>
		
		<tr>
			<td align="center" height="30px" class="info_content_00" colspan="7">
				<font size="3"><b><%--教育背景--%><spring:message code="rp.report.title.educationinfo"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--开始日期--%>
				<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--结束日期--%>
				<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--毕业学校--%>
				<spring:message code="hr.viewPersonalInfo.title.SCHOOLTAG"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--专业--%>
				<spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--学历--%>
				<spring:message code="hr.viewPersonalInfo.title.DEGREE_NAME"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--所在地--%>
				<spring:message code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS"/>
			</td>
		</tr>
		<c:forEach items="${educationList}" var="oneResult">
			<tr height="20px">
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.START_DATE }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.END_DATE }</td>
				<td align="center" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.INSTITUTION_NAME }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.SUBJECT }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.DEGREENAME }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.SCHOOL_ADDRESS }</td>
			</tr>
		</c:forEach>
		<c:if test="${educationListCnt < 4}">
			<c:forEach var="i" begin="1" end="${4 - educationListCnt}">
				<tr height="20px">
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<tr>
			<td align="center" height="30px" class="info_content_00" colspan="7">
				<font size="3"><b><%--合同签订--%><spring:message code="rp.report.title.contractinfo"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--开始日期--%>
				<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--结束日期--%>
				<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--合同类别--%>
				<spring:message code="rp.report.title.contracttype"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--合同长度(月)--%>
				<spring:message code="rp.report.title.contractmonthlength"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--是否续签--%>
				<spring:message code="rp.report.title.contractsignYN"/>
			</td>	
		</tr>
		<c:forEach items="${contractList }" var="oneResult" varStatus="i">
			<tr height="20px">
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.START_CONTRACT_DATE }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">${oneResult.END_CONTRACT_DATE }</td>
				<td align="center" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.CONTRACT }</td>
				<td align="center" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.CONTRACT_PERIOD }</td>
				<td align="center" class="info_content_01" nowrap="nowrap">
					<c:if test="${oneResult.RENEWABLE eq '0' }">Y</c:if>
					<c:if test="${oneResult.RENEWABLE ne '0' }">N</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${contractListCnt < 3 }">
			<c:forEach var="i" begin="1" end="${ 3 - contractListCnt }">
				<tr height="20px">
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<tr>
			<td align="center" height="30px" class="info_content_00" colspan="7">
				<font size="3"><b><%--公司内经历--%><spring:message code="rp.report.title.experienceinfoinside"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--开始日期--%>
				<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--结束日期--%>
				<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--所在部门--%>
				<spring:message code="rp.report.title.department"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--岗位--%>
				<spring:message code="rp.report.title.dutyinfo"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--职务--%>
				<spring:message code="org.orgManage.title.post"/>
			</td>
		</tr>
		<c:forEach items="${expInList}" var="oneResult">
			<tr height="20px">
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.START_DATE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.END_DATE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.DEPARTMENT}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.POSITION}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.POST}</td>
			</tr>
		</c:forEach>
		<c:if test="${expInListCnt < 3}">
			<c:forEach var="i" begin="1" end="${3 - expInListCnt}" step="1">
				<tr height="20px">
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<tr>
			<td align="center" height="30px" class="info_content_00" colspan="7">
				<font size="3"><b><%--公司外经历--%><spring:message code="rp.report.title.experienceinfooutside"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--开始日期--%>
				<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--结束日期--%>
				<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--工作单位--%>
				<spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--岗位--%>
				<spring:message code="rp.report.title.dutyinfo"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--负责业务--%>
				<spring:message code="hr.viewWorkInfo.title.DUTY"/>
			</td>
		</tr>
		<c:forEach items="${expOutList}" var="oneResult">
			<tr height="20px">
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.START_DATE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.END_DATE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.CPNY_NAME}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.POSITION}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.DUTY}</td>
			</tr>
		</c:forEach>
		<c:if test="${expOutListCnt < 3}">
			<c:forEach var="i" begin="1" end="${3 - expOutListCnt}" step="1">
				<tr height="20px">
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<tr>
			<td align="center" height="30px" class="info_content_00" colspan="7">
				<font size="3"><b><%--家庭信息--%><spring:message code="rp.report.title.familyinfo"/></b></font>
			</td>
		</tr>
		<tr height="20px">
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--称谓--%>
				<spring:message code="rp.report.title.familynamed"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--姓名--%>
				<spring:message code="public.title.name"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--出生日期--%>
				<spring:message code="main.home.message.chushengriqi"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap" colspan="2"><%--工作单位--%>
				<spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/>
			</td>
			<td align="center" width="" class="info_title_01" nowrap="nowrap"><%--联系方式--%>
				<spring:message code="hr.viewWorkInfo.title.TEL"/>
			</td>
		</tr>
		<c:forEach items="${familyList }" var="oneResult">
			<tr height="20px">
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.FAM_TYPE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.FAM_NAME}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.FAM_BORNDATE}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap" colspan="2">${oneResult.FAM_COMPANY_NAME}</td>
				<td align="center" width="" class="info_content_01" nowrap="nowrap">${oneResult.FAM_PHONE}</td>
			</tr>
		</c:forEach>
		<c:if test="${familyListCnt < 3}">
			<c:forEach var="i" begin="1" end="${3 - familyListCnt }" step="1">
				<tr height="20px">
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap" colspan="2">&nbsp;</td>
					<td class="info_content_01" nowrap="nowrap">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>