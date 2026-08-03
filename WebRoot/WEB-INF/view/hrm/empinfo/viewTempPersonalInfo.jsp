<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
<hr>
<div style="float:right;">
<a  class="buttonActive"  href="/hrm/informationRetrieval/viewPersonalInfoExcel?PERSON_ID=${personInfo.PERSON_ID}">
			<SPAN>人事信息导出</SPAN>
		</a>
</div>
<div class="panel">
<h1><spring:message
	code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION" /> <!--员工基础信息--><!-- 직원기초정보 -->
</h1>

<div><%@ include
	file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead2.jsp"%>
</div>
</div>
<div style="clear: both;"></div>
<div class="tabs" currentIndex="${tabsSelected }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
	<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
		<li><a href="javascript:;"><span>${menu.MENU_NAME } </span> </a>
		</li>
	</c:forEach>
</ul>
</div>
</div>

<div class="tabsContent" id="displaycheckbox"><c:forEach
	items="${menuThirdList}" var="menu" varStatus="i">
	<c:if test="${menu.MENU_NO eq '2540' || menu.MENU_NO eq '2419'}">
		<!-- 基础信息 -->
		<div>
		<div style="display: block;" id="displaycheckbox_1"><!-- isEssSystem的值不为‘1’，则是业务系统,否则为ESS系统, 以下均是!-->
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="${i.index}" var="tabsSelected" />
			<c:set value="hrm/empinfo/viewPersonalInfo" var="actionUrl" />
			<c:set value="1250" var="edit_width" />
			<c:set value="630" var="edit_height" />
			<c:choose>
			<c:when test="${personInfo.MGT_SYSTEM eq 'G' }">
			<c:set
				value="/hrm/empinfo/viewEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_rel" />
			<c:set
				value="/hrm/empinfo/viewEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
			</c:when>
			<c:otherwise>
				<c:set
				value="/hrm/empinfo/viewTempEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_rel" />
				<c:set
				value="/hrm/empinfo/viewTempEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
			</c:otherwise>
			</c:choose>
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if> 
		<c:if test="${isEssSystem eq '1'}">
			<c:set value="${i.index}" var="tabsSelected" />
			<c:set value="hrm/empinfo/viewPersonalInfo" var="actionUrl" />
			<c:set value="1250" var="edit_width" />
			<c:set value="630" var="edit_height" />
			<c:set
				value="/hrm/empinfo/viewTempEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=1"
				var="edit_rel" />
			<c:set
				value="/hrm/empinfo/viewTempEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=1"
				var="edit_Url" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<div class="panel">
		<h1><spring:message
			code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" /> <!--个人信息 --><!-- 개인기초정보 -->
		</h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /> <!--国籍--></td>
				<td class="td_type" width="15%">${personInfo.NATIONALITY_CODE }</td>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.SEX" /> <!--性别--></td>
				<td class="td_type" width="15%">${personInfo.SEX_NAME }</td>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.DOB" /> <!--出生年月日--></td>
				<td class="td_type" width="15%">${personInfo.DOB }</td>
				<td class="td_title" width="10%">学位</td>
				<td class="td_type" width="15%">${personInfo.FINAL_DEGREE_NAME}</td>
			</tr>

			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.merry.date" /> <!--结婚纪念日--></td>
				<td class="td_type">${personInfo.WEDD_DATE }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.IDCARD_NO" /> <!--身份证号--></td>
				<td class="td_type">${personInfo.IDCARD_NO }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.IDCARD" /> <!--ID卡号--></td>
				<td class="td_type">${personInfo.ID_CARD_NO }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.email.chinese" /> <!--邮箱--></td>
				<td class="td_type">${personInfo.EMAIL }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
				<td class="td_type">${personInfo.HOME_PHONE}</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.work.phone" /> <!-- 办公电话 --></td>
				<td class="td_type">${personInfo.OFFICE_PHONE}</td>
				<td class="td_title"><spring:message
					code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /> <!--手机号码-->
				</td>
				<td class="td_type">${personInfo.CELLPHONE }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.REG_TYPE_NAME" /> <!--户口性质--></td>
				<td class="td_type" width="15%">${personInfo.REG_TYPE_CODE}</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.REG_PLACE" /> <!--户口所在地--></td>
				<td class="td_type" colspan="7">${personInfo.REG_PLACE }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="rp.report.title.homeaddress" /> <!--家庭住址--></td>
				<td class="td_type" colspan="7">${personInfo.IDCARD_ADDR }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.HOME_ADDRESS" /> <!--现住址--></td>
				<td class="td_type" colspan="3">${personInfo.HOME_ADDRESS }</td>
				<td class="td_title"><spring:message
					code="hr.viewCondSql.title.YOUBIAN" /> <!--邮编--></td>
				<td class="td_type">${personInfo.POSTALCODE }</td>
				<td class="td_title">系统位置</td>
				<td class="td_type" width="15%"><c:if test="${personInfo.MGT_SYSTEM eq 'C'}">CHRS系统</c:if>
						<c:if test="${personInfo.MGT_SYSTEM eq 'G'}">GHRS系统</c:if></td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.BORNPLACE_NAME" /> <!--籍贯--></td>
				<td class="td_type">${personInfo.BORNPLACE_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.NATION_NAME" /> <!--民族 --></td>
				<td class="td_type" width="15%">${personInfo.NATION_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.POLITY_NAME" /> <!--政治面貌 --></td>
				<td class="td_type" width="15%">${personInfo.POLITY_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" /> <!--是否共产党员-->
				</td>
				<td class="td_type">${personInfo.WHETHER_COMMUNIST_NAME }</td>
			</tr>


			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.shengao" /> <!--身高--></td>
				<td class="td_type">${personInfo.HEIGHT }cm</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.tizhong" /> <!--体重 -->
				<td class="td_type">${personInfo.WEIGHT }kg</td>
				<td class="td_title"><spring:message
					code="hr.viewHealth.title.BLOOD_TYPE_NAME" /> <!--血型 --></td>
				<td class="td_type">${personInfo.BLOOD_TYPE_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewCondSql.titleSHIFOUCANJI" /> <!--是否残疾--></td>
				<td class="td_type">${personInfo.DISABILITY_YN_NAME }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.zhaopinlaiyuan" /> <!--招聘来源--></td>
				<td class="td_type">${personInfo.RECRUITMENT_SOURCE_TYPE_NAME }
				</td>
				<td class="td_title"><spring:message
					code="hr.viewPromote.title.RESIGN_REASON" /> <!--离职原因 --></td>
				<td class="td_type" width="15%">${personInfo.LEAVE_REASON }</td>
				<td class="td_title">实际离职日期</td> 
				<td class="td_type" width="15%">${personInfo.C_DATE_LEFT }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.jiangchengbeizhu" /> <!--奖惩备注 -->
				</td>
				<td class="td_type" width="15%">${personInfo.REMARK }</td>
			</tr>
		</table>
		</div>
		</div>
		</div>

		<div class="panel">
		<h1><spring:message code="hr.viewPersonalInfo.title.payinfo" />
		<!--工资信息 --></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">

			<tr>
				<td class="td_title"><spring:message
					code="hr.empinfo.pay.type.num" /> <!--工资级号--></td>
				<td class="td_type">${personInfo.PAY_GRADE }</td>
				<td class="td_title"><spring:message
					code="hr.empinfo.pay.type.num.leave" /> <!--工资级号等级--></td>
				<td class="td_type">${personInfo.PAY_STEP }</td>
				<td class="td_title"><spring:message
					code="ess.viewpersonalpainfo.jibengongzi" /> <!--基本工资--></td>
				<td class="td_type">${personInfo.BASE_PAY}</td>
				<td class="td_title"><spring:message
					code="hr.empinfo.pay.VARIABLE_SALARY_MONTH" /> <!--变动工资--></td>
				<td class="td_type">${personInfo.VARB_PAY }</td>
			</tr>
			<tr>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.nianxin" /> <!--年薪--></td>
				<td class="td_type" width="15%">${personInfo.ANSAL}</td>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.nyinhangdaima" /> <!--银行代码--></td>
				<td class="td_type" width="15%">${personInfo.BANK_ID }</td>
				<td class="td_title" width="10%"><spring:message
					code="pa.wagebase.title.openAccountBanks" /> <!--开户行--></td>
				<td class="td_type" width="15%">${personInfo.CARD_NAME}</td>
				<td class="td_title" width="10%"><spring:message
					code="rp.report.title.bankcardno" /> <!-- 银行账号 --></td>
				<td class="td_type" width="15%">${personInfo.CARD_NO}</td>
			</tr>
			<%-- <tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.feiyongnyinhangdaima" /> <!--费用银行代码-->
				</td>
				<td class="td_type">${personInfo.EXPNS_BANK_CD }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.feiyongkaihuhang" /> <!--费用开户行-->
				</td>
				<td class="td_type">${personInfo.EXPNS_BANK_BRNCH_NM }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.feiyongyinhangzhanghao" /> <!--费用银行账号-->
				</td>
				<td class="td_type">${personInfo.EXPNS_BANK_ACCT_NO}</td>
				<td class="td_title"></td>
				<td class="td_type"></td>
			</tr> --%>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.aixinjijinzhifufangshi" /> <!--爱心基金支付方式-->
				</td>
				<td class="td_type">${personInfo.LOVE_FUND_PAYMENT_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.shifouzhifuaixinjijin" /> <!--是否支付爱心基金-->
				</td>
				<td class="td_type">${personInfo.IF_PAYMENT_LOVE_FUND_NAME }</td>
				<td class="td_title"><%-- <spring:message
					code="hr.viewPersonalInfo.title.fudanfangzubiaozhi" /> <!--负担房租标志--> --%>
				</td>
				<td class="td_type"><%-- ${personInfo.IF_PAYMENT_RENT_NAME } --%></td>
				<td class=td_title><%-- <spring:message
					code="hr.viewPersonalInfo.title.fudanyiliaofeibiaozhi" /> <!--负担医疗费标志--> --%></td>
				<td class="td_type"><%-- ${personInfo.IF_PAYMENT_MEDICAL_NAME } --%></td>
			</tr>
			<%-- <tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.fudanjiaoyufeibiaozhi" /> <!--负担教育费标志-->
				</td>
				<td class="td_type">${personInfo.IF_PAYMENT_EDUCATION_NAME }</td>
				<td class="td_title"></td>
				<td class="td_type"></td>
				<td class="td_title"></td>
				<td class="td_type"></td>
				<td class=td_title></td>
				<td class="td_type"></td>
			</tr> --%>
		</table>
		</div>
		</div>

		<div class="panel">
		<h1><spring:message code="hr.viewPersonalInfo.title.workinfo" />
		<!--工作信息 --></h1>

		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.shifouFSE" /> <!--是否FSE--></td>
				<td class="td_type" width="15%">${personInfo.FSE_YN}</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.gongzuoleixing.chr" /> <!--工作类型(CHR)-->
				</td>
				<td class="td_type">${personInfo.PROMTR_WORK_NAME}</td>
				<td class="td_title" width="10%"><spring:message
					code="hr.viewPersonalInfo.title.banhao" /> <!--班号--></td>
				<td class="td_type" width="15%">${personInfo.SHIFT_NO}</td>
				<td class="td_title" width="10%"></td>
				<td class="td_type" width="15%"></td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.renyuanleixing.chr" /> <!--人员类型(CHR)-->
				</td>
				<td class="td_type">${personInfo.EMP_TYPE_NAME }</td>
				<td class="td_title" width="10%"><!-- 人员类型生效日期 --></td>
				<td class="td_type" width="15%"><%-- ${personInfo.EMP_TYPE_START_DATE } --%></td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.gongzuodiqu" /> <!--工作地区--></td>
				<td class="td_type">${personInfo.WORK_AREA_NAME }</td>
				<td class="td_title">福利地区(公积金)</td>
				<td class="td_type" width="15%">${personInfo.INSRAREA_ID_INS_NAME }</td>
			</tr>
			<tr>                                                                                                               
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.laodongshoucebianhao" /> <!--劳动手册编号-->
				</td>
				<td class="td_type">${personInfo.MANUAL_NUM }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.sheneigongling" /> <!--社内工龄--></td>
				<td class="td_type"><c:if
					test="${personInfo.INNER_WORK_YEAR ne 0 and personInfo.INNER_WORK_YEAR ne null }">${personInfo.INNER_WORK_YEAR}<spring:message
						code="liang.hr.viewWorkInfo.title.YEAR" />
				</c:if> <c:if
					test="${personInfo.INNER_WORK_MONTH ne 0 and personInfo.INNER_WORK_MONTH ne null}">${personInfo.INNER_WORK_MONTH}<spring:message
						code="hr.viewPersonalInfo.title.WORKINFO_MONTH" />
				</c:if></td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.shewaigongling" /> <!--社外工龄--></td>
				<td class="td_type" width="15%"><c:if
					test="${personInfo.YEAR_AGE_LIMIT ne 0 and personInfo.YEAR_AGE_LIMIT ne null }">${personInfo.YEAR_AGE_LIMIT}<spring:message
						code="liang.hr.viewWorkInfo.title.YEAR" />
				</c:if> <c:if
					test="${personInfo.MONTH_AGE_LIMIT ne 0 and personInfo.MONTH_AGE_LIMIT ne null}">${personInfo.MONTH_AGE_LIMIT}<spring:message
						code="hr.viewPersonalInfo.title.WORKINFO_MONTH" />
				</c:if></td>
				<td class=td_title>福利地区(保险)</td>
				<td class="td_type">${personInfo.INSRAREA_ID_NAME}</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.baoxiangongsi" /> <!--保险公司--></td>
				<td class="td_type">${personInfo.INSURANCE_COMPANY_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" /> <!--保险类型-->
				</td>
				<td class="td_type">${personInfo.INSURANCE_TYPE_CODE_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.nianjiajizhun" /> <!--年假基准--></td>
				<td class="td_type">${personInfo.YY_VAC_STD_DATE }</td>
				<td class=td_title><spring:message
					code="hr.viewPersonalInfo.title.fengongsi.daqu" /> <!--分公司(大区)--></td>
				<td class="td_type">${personInfo.PAY_AREA_CD_NAME }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.chanpin" /> <!--产品--></td>
				<td class="td_type">${personInfo.PROD_TP_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu" /> <!--促销员所属-->
				</td>
				<td class="td_type">${personInfo.PROMTR_TP_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.xingjijibie" /> <!--星级级别--></td>
				<td class="td_type">${personInfo.STAR_TP_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.shifoujianmai" /> <!--是否兼卖--></td>
				<td class="td_type">${personInfo.PART_TIME_YN_NAME }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.shifougongjiancuxiaoyuan" /> <!--是否共建促销员-->
				</td>
				<td class="td_type">${personInfo.COMM_YN_NAME }</td>
				<%-- <td class="td_title">评价类型</td>
				<td class="td_type">${personInfo.EVS_TYPE_NAME }</td> --%>
				<td class="td_title">兼卖产品</td>
				<td class="td_type" colspan="3">
				<c:if test="${personInfo.PART_TIME_YN_NAME eq 'Y'}">
					<c:forEach items="${productList}" var="item" varStatus="i">
						<c:if test="${i.count eq 1}">${item.CONTENT }</c:if>
						<c:if test="${i.count ne 1}">,${item.CONTENT }</c:if>
					</c:forEach>
					<font color="red">[<a href="/hrm/empinfo/viewUpdateEmpProduct?PERSON_ID=${personInfo.PERSON_ID}" target="dialog" mask="true" width="600" height="280"
							rel="viewUpdateEmpProduct"><font color="red">修改</font></a>]</font>
				</c:if>		
				</td>
			</tr>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '123188' || menu.MENU_NO eq '2420'}">

		<div style="display: block;" id="displaycheckbox_2"><c:set
			value="${i.index}" var="tabsSelected" />
		<div class="panel">
		<h1><spring:message code="hr.viewPersonalInfo.title.SCHOOLTAG" />
		<!--毕业学校--><!-- 학력 --></h1>
		<div id="edudiv">
		<table class="table" width="101%" nowrapTD="false">
			<thead>
				<tr>
					<th width="50"><spring:message
						code="hr.viewPersonalInfo.title.INSTITUTION_NAME" /> <!--学校名--></th>
					<th width="50"><spring:message
						code="hr.viewPersonalInfo.title.SUBJECTNAME" /> <!--专业--></th>
					<th width="50"><spring:message
						code="liang.hr.viewPersonalInfo.title.START_DATE" /> <!--入学年月-->
					</th>
					<th width="50"><spring:message
						code="liang.hr.viewPersonalInfo.title.END_DATE" /> <!--毕业年月--></th>
					<th width="50"><spring:message
						code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" /> <!--所在地--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${educationList}" var="item">
					<tr target="sid" rel="${item.EDUC_NO}">
						<td class='td_left'>${item.INSTITUTION_NAME}</td>
						<td class='td_left'>${item.SUBJECT}</td>
						<td class='td_center'>${item.START_DATE}</td>
						<td class='td_center'>${item.END_DATE}</td>
						<td class='td_center'>${item.SITE_PROVINCE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '2541' || menu.MENU_NO eq '2524'}">
		<!-- 发令信息 -->
		<div style="display: block;" id="displaycheckbox_3">
		<div class="panel">
		<h1><spring:message code="hr.viewPromote.title.MATTERS_TO_THE" />
		<!--发令事项--><!-- 발령 --></h1>
		<div id="edudiv">
		<table class="table table-border-lrt" width="101%">
			<thead>
				<tr>
					<th><spring:message code="hr.assignment.type" /> <!--发令类型-->
					</th>
					<th><spring:message code="hr.enpinfo.title.EMP.EXPDATE" /> <!--发令日期-->
					</th>
					<th><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					<!--部门--></th>
					<th><spring:message
						code="hr.viewPersonalInfo.title.POSITION_NAME" /> <!--职位--></th>
					<th><spring:message code="hr.viewPersonalInfo.title.DUTY_NAME" />
					<!--职责--></th>
					<th><spring:message code="hr.assignment.zhiji" /> <!--职级(级号)-->
					</th>
					<th><spring:message code="hr.assignment.group" /> <!--职群--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${assignmentList}" var="item" varStatus="i">
					<tr target="sid" rel="${item.ASSIGNMENT_ID}">
						<td class='td_center'>
						${item.ASSIGNMENT_TYPE_1ST}--${item.ASSIGNMENT_TYPE_2ND}</td>
						<td>${item.ASSIGNMENT_START_DATE}</td>
						<td>${item.ORG_NAME_LOCAL}</td>
						<td>${item.JOB_DETAIL}</td>
						<td>${item.POSITION}</td>
						<td>${item.GRADE}</td>
						<td>${item.JOB_FAMILY}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '123189' || menu.MENU_NO eq '2428'}">
		<div><!-- 合同  --><!-- 계약 -->
		<div style="display: block;" id="displaycheckbox_4">


		<div class="panel">
		<h1><spring:message
			code="hr.viewContract.title.CONTRACT_INFORMATION" /> <!--契约--></h1>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewContract.title.CONTRACT_TYPE_NAME" /> <!--合同类型--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.TOTAL_PERIOD" /> <!--合同次数--></th>
					<th width="100"><%--
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_START_DATE" />
													--%> <spring:message
						code="zxc.hr.contract.CONTRACT_START_DATE" /> <!--开始日--></th>
					<th width="100"><%--
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													--%> <spring:message
						code="zxc.hr.contract.CONTRACT_END_DATE" /> <!--结束日--></th>
					<th width="100"><spring:message
						code="hr.viewPromote.title.REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contracList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.CONTRACT_NO}">
						<td>${item.CONTRACT_TYPE_NAME}</td>
						<td class='td_center'>${item.TOTAL_PERIOD }</td>
						<td class='td_center'>${item.START_CONTRACT_DATE}</td>
						<td class='td_center'>${item.END_CONTRACT_DATE}</td>
						<%--
													<td>
														<c:if test="${empty item.END_CONTRACT_DATE}">
															<spring:message code="hr.viewContract.title.NO_ENDDATE" />
															<!--无固定期限-->
														</c:if>
														<c:if test="${not empty item.END_CONTRACT_DATE}">${item.CONTRACT_PERIOD}</c:if>
													</td>
													--%>
						<td>${item.REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>

		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '2551' || menu.MENU_NO eq '2421'}">
		<div><!-- 司外经历 -->
		<div style="display: block;" id="displaycheckbox_5"><c:set
			value="/hrm/empinfo/viewWorkInfo" var="turn_to_url" /> <c:set
			value="${i.index}" var="tabsSelected" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1100" var="add_width" />
			<c:set value="440" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />

			<c:set value="800" var="delete_width" />
			<c:set value="300" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set
				value="/hrm/empinfo/deleteWorkExpreience?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />

			<c:set value="dialog" var="edit_tab" />
			<c:set value="1100" var="edit_width" />
			<c:set value="440" var="edit_height" />
			<c:set
				value="/hrm/empinfo/updateWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>
		<div class="panel">
		<h1><spring:message code="hr.viewWorkInfo.title.WORK_EXPERIENCE" />
		<!--工作经历--><!-- 사외경력 --></h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set var="toolbarInfo" value="${toolbarInfogongzuo}" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="80"><spring:message
						code="hr.viewTranslate.title.PUBLIC_START_DATE" /> <!--开始时间--></th>
					<th width="80"><spring:message
						code="hr.viewTranslate.title.PUBLIC_END_DATE" /> <!--结束时间--></th>
					<th width="80"><spring:message
						code="hr.viewWorkInfo.title.CPNY_NAME" /> <!--工作单位--></th>
					<th width="80"><spring:message
						code="hr.viewWorkInfo.title.DEPT_NAME" /> <!--部门--></th>
					<th width="80"><spring:message
						code="public.title.positionName" /> <!--职位--></th>
					<th width="80"><spring:message
						code="sys.postManage.title.postGrade" /> <!--职级--></th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.gongzidaiyu" /> <!--工资待遇--></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${workExperienceList}" var="item" varStatus="i">

					<tr target="personId" rel="${item.HEALTH_NO}">
						<td class='td_center'>${item.START_DATE}</td>
						<td class='td_center'>${item.END_DATE}</td>
						<td class='td_center'>${item.CPNY_NAME}</td>
						<td class='td_center'>${item.DEPT_NAME}</td>
						<td class='td_center'>${item.POSITION}</td>
						<td class='td_center'>${item.DUTY}</td>
						<td class='td_center'>${item.PAYROLL}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '2545' || menu.MENU_NO eq '2422'}">
		<div><!-- 培训信息 -->
		<div style="display: block;" id="displaycheckbox_6"><!--培训信息-->
		<c:set value="${i.index}" var="tabsSelected" /> <c:set
			value="/hrm/empinfo/viewTraining" var="turn_to_url" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1250" var="add_width" />
			<c:set value="440" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewTrainingInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />



			<c:set value="850" var="delete_width" />
			<c:set value="300" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set
				value="/hrm/empinfo/deleteTraining?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />


			<c:set value="dialog" var="edit_tab" />
			<c:set value="1250" var="edit_width" />
			<c:set value="440" var="edit_height" />

			<c:set
				value="/hrm/empinfo/updateTrainingInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>
		<div class="panel">
		<h1><spring:message
			code="hr.viewTraining.title.NAVIGATION_TRAINING" /> <!--培训信息--><!-- 교육 -->
		</h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set var="toolbarInfo" value="${toolbarInfopeixun}" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.COURSE_NAME" /> <!--课程名--></th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.SELECT_MUST" /> <!--选择/必选  --></th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE" /> <!--培训区分  -->
					</th>
					<th width="80"><spring:message
						code="zxc.hr.contract.CONTRACT_START_DATE" /> <!--起始日期--></th>
					<th width="80"><spring:message
						code="zxc.hr.contract.CONTRACT_END_DATE" /> <!--终止日期--></th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.INSTITUTION_NAME" /> <!--培训机关-->
					</th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.TRAINING_METHOD" /> <!--培训方法-->
					</th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.TRAINING_TIME" /> <!--培训时间--></th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.TRAINING_RESULT" /> <!--培训结果-->
					</th>
					<th width="80"><spring:message
						code="liang.hr.viewTraining.title.REMARKS" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${trainingInfoList}" var="item" varStatus="i">

					<tr target="trainNo" rel="${item.TRAIN_NO}">
						<td class='td_left'>${item.COURSE_NAME}</td>
						<td class='td_center'>${item.MUST_NAME}</td>
						<td class='td_center'>${item.TRAINING_DIFFERENTIATE_NAME}</td>
						<td class='td_center'>${item.START_DATE}</td>
						<td class='td_center'>${item.END_DATE}</td>
						<td class='td_left'>${item.INSTITUTION_NAME}</td>
						<td class='td_center'>${item.TRAINING_METHOD_NAME}</td>
						<td class='td_center'>${item.TRAINING_TIME}</td>
						<td class='td_center'>${item.TRAINING_RESULT}</td>
						<td class='td_left'>${item.REMARKS}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>



	<c:if test="${menu.MENU_NO eq '123190' || menu.MENU_NO eq '2423'}">
		<div><!-- 家人联系 --> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_7"><!--<c:if test="${isEssSystem ne '1'}"></c:if>
								<c:set value="1250" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
								value="/hrm/empinfo/viewHomeRelationInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem}"
								var="add_Url" />
	
								<c:set value="1200" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
	
								<c:set
								value="/hrm/empinfo/deleteHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem}"
								var="delete_Url" />
	
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1200" var="edit_width" />
								<c:set value="440" var="edit_height" />
								<c:set
								value="/hrm/empinfo/updateHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem}"
								var="edit_Url" />-->

		<div class="panel">
		<h1><spring:message code="hr.viewRelation.title.FAMILY_RELATIONS" />
		<!--家人关系--><!-- 가족관계 --></h1>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewRelation.title.FAM_TYPE_NAME" /> <!--关系--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名--></th>
					<%--<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
													<!--身份证号码-->
												</th>
												--%>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.DOB" /> <!--出生日期--></th>
					<%--<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
													<!--地址-->
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PHONE" />
													<!--联系电话-->
												</th>
												
												<th width="100">
													<spring:message code="hr.viewRelation.title.LIVE_YN_NAME" />
													<!--一起居住与否-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME" />
													<!--是否紧急联系人-->
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PERSON_ID" />
													<!--亲属员工号-->
												</th>
											--%>
					<%--<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.FAMILY_YINYANGRILI" />
													<!--阴/阳历区分-->
												</th>
											
												--%>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME" /> <!--单位名称--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${homeRelationList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.FAMILY_NO}">
						<td class='td_center'>${item.FAM_TYPE_NAME}</td>
						<td class='td_center'>${item.FAM_NAME}</td>
						<%--<td>
														${item.FAM_IDCARD}
													</td>
													--%>
						<td class='td_center'>${item.FAM_BORNDATE}</td>
						<%--<td>
														${item.FAM_ADDRESS}
													</td>
													<td>
														${item.FAM_PHONE}
													</td>
													
													<td>
														${item.LIVE_YN_NAME}
													</td>
													<td>
														${item.EMERGENCY_CONTACT_YN_NAME}
													</td>
													<td>
														${item.FAM_PERSON_ID}
													</td>
												--%>
						<%--<td>
														${item.FAM_YINYANGLINAME }
													</td>
													--%>
						<td class='td_center'>${item.FAM_COMPANY_NAME}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

		</div>
		</div>
		</div>
		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '2554' || menu.MENU_NO eq '2424'}">
		<div><!-- 资格信息 --> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_8"><c:set
			var="toolbarInfo" value="${toolbarInfozige}" /> <!--   <c:set value="/hrm/empinfo/viewCompetence" var="turn_to_url" />  -->


		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1290" var="add_width" />
			<c:set value="330" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />


			<c:set value="900" var="delete_width" />
			<c:set value="550" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set
				value="/hrm/empinfo/deleteCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />

			<c:set value="dialog" var="edit_tab" />
			<c:set value="1200" var="edit_width" />
			<c:set value="600" var="edit_height" />
			<c:set
				value="/hrm/empinfo/updateCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>

		<div class="panel">
		<h1><spring:message code="hr.viewCompetence.title.CREDENTIALS" />
		<!--资格证书--><!-- 자격증 --></h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>

		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.QUAL_NAME" /> <!--资格证名称--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.QUAL_LEVEL_NAME" /> <!--证件级别--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.QUAL_GRADE_NAME" /> <!--职称--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.QUAL_INSTITUTE" /> <!--发证处--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.DATE_OBTAINED" /> <!--取证日期--></th>
					<th width="100" style="display: none"><spring:message
						code="hr.viewCompetence.title.VALIDITY_DATE" /> <!--有效期--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.jintiebiaozhun" /> <!--津贴标准--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${qualificationList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.QUAL_NO}">
						<td class='td_left'>${item.QUAL_NAME}</td>
						<td class='td_center'>${item.QUAL_LEVEL_NAME}</td>
						<td class='td_center'>${item.QUAL_GRADE_NAME}</td>
						<td class='td_left'>${item.QUAL_INSTITUTE}</td>
						<td class='td_center'>${item.DATE_OBTAINED}</td>
						<td class='td_center' style="display: none">
						${item.VALIDITY_DATE }</td>
						<td class='td_center'>${item.QUAL_REMARK}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>

		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '123191' || menu.MENU_NO eq '2425'}">
		<div><!--紧急联系   --> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_9"><!--<c:if test="${isEssSystem ne '1'}"> </c:if>-->
		<c:set value="1250" var="add_width" /> <c:set value="440"
			var="add_height" /> <c:set value="dialog" var="add_tab" /> <c:set
			value="/hrm/empinfo/viewFamilyInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem }"
			var="add_Url" /> <c:set value="1200" var="delete_width" /> <c:set
			value="300" var="delete_height" /> <c:set value="dialog"
			var="delete_tab" /> <c:set value="0" var="delete_range" /> <c:set
			value="0" var="delete_mask_exit" /> <c:set value="true"
			var="delete_mask" /> <c:set
			value="/hrm/empinfo/deleteFamily?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem }"
			var="delete_Url" /> <c:set value="dialog" var="edit_tab" /> <c:set
			value="1200" var="edit_width" /> <c:set value="440"
			var="edit_height" /> <c:set
			value="/hrm/empinfo/updateFamilyInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&isEssSystem=${isEssSystem }"
			var="edit_Url" />

		<div class="panel">
		<h1><spring:message
			code="heran.hr.viewLanguage.EMERGCENCYCONTACT" /> <!--紧急联系--><!-- 비상연락 -->
		</h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}"> <c:set
			var="toolbarInfo" value="${toolbarInfoshehui}" /> <%@ include
			file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewRelation.title.FAM_TYPE_NAME" /> <!--关系--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名--></th>
					<!--<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
													身份证号码
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
													出生日期
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
													地址
												</th>
												-->
					<th width="100"><spring:message
						code="hr.viewRelation.title.FAM_PHONE" /> <!--联系电话--></th>
					<!--<th width="100">
													<spring:message
														code="hr.viewRelation.title.FAM_COMPANY_NAME" />
													工作单位/职(岗)位
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.LIVE_YN_NAME" />
													一起居住与否
												</th>
												<th width="100">
													<spring:message
														code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME" />
													是否紧急联系人
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PERSON_ID" />
													亲属员工号
												</th>
											-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${familyList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.FAMILY_NO}">
						<td class='td_center'>${item.FAM_TYPE_NAME}</td>
						<td class='td_center'>${item.FAM_NAME}</td>
						<!--<td>
														${item.FAM_IDCARD}
													</td>
													<td>
														${item.FAM_BORNDATE}
													</td>
													<td>
														${item.FAM_ADDRESS}
													</td>
													-->
						<td class="td_center">${item.FAM_PHONE}</td>
						<!--<td>
														${item.FAM_COMPANY_NAME}
													</td>
													<td>
														${item.LIVE_YN_NAME}
													</td>
													<td>
														${item.EMERGENCY_CONTACT_YN_NAME}
													</td>
													<td>
														${item.FAM_PERSON_ID}
													</td>
												-->
					</tr>

				</c:forEach>

			</tbody>
		</table>

		</div>
		</div>

		</div>
		</div>

	</c:if>
	<c:if test="${menu.MENU_NO eq '2542' || menu.MENU_NO eq '2426'}">
		<div><!-- 评价  평가 --> <c:set value="${i.index}"
			var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_10">
		<div class="panel">

		<h1><spring:message
			code="hr.viewEvaluate.title.EVALUATEIMFORMATION" /> <!--评价信息--><!-- 평가 -->
		</h1>
		<%
			// request.setAttribute( "toolbarInfo", request.getAttribute("toolbarInfopingjia")) ;
		%> <c:set value="/hrm/empinfo/viewEvaluate" var="turn_to_url" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1200" var="add_width" />
			<c:set value="400" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewEvsInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />

			<c:set value="900" var="delete_width" />
			<c:set value="400" var="delete_height" />

			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set value="0" var="delete_range" />
			<c:set value="dialog" var="delete_tab" />
			<c:set
				value="/hrm/empinfo/deleteEvs?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />


			<c:set value="1300" var="edit_width" />
			<c:set value="400" var="edit_height" />
			<c:set
				value="/hrm/empinfo/viewEditEvsInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
			<c:set var="toolbarInfo" value="${toolbarInfopingjia}" />

			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_PERIOD" /> <!--评价期间--></th>

					<!--<th width="100">  -->
					<%--<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME" />--%>
					<!--评价类型-->
					<!--</th>  -->
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_ACHI" /> <!--업적  --></th>
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_ATTI" /> <!--태도 --></th>
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_ABIL" /> <!--능력--></th>
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_MARK" /> <!--评价分数--></th>
					<th width="100"><spring:message
						code="hr.viewEvaluate.title.EV_GRADE_NAME" /> <!--评价等级--></th>
					<th width="100"><spring:message
						code="hr.viewSuggestion.title.Suggestion" /> <!--意见--></th>
					<th width="100"><spring:message
						code="hr.viewFinalSequence.title.FinalSequence" /> <!--最终顺位--></th>
					<th width="100"><spring:message
						code="hr.viewTotalPeople.title.TotalPeople" /> <!--总职级员人数--></th>
					<th width="100"><spring:message
						code="hr.viewPromote.title.REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${EvsInfo}" var="item" varStatus="i">

					<tr target="PERSON_ID"
						rel="${item.PERSON_ID}&EV_PERIOD=${item.EV_PERIOD}">
						<td class='td_center'>${item.EV_PERIOD}</td>
						<!--<td class='td_left'>  -->
						<!--${item.EV_TYPE_NAME}-->
						<!-- </td> -->
						<td class='td_center'>${item.EV_ACHI}</td>
						<td class='td_center'>${item.EV_ATTI}</td>
						<td class='td_center'>${item.EV_ABIL}</td>
						<td class='td_center'>${item.EV_MARK}</td>
						<td class='td_center'>${item.EV_GRADE_NAME}</td>
						<td class='td_left'>${item.SUGGESTION }</td>
						<td class='td_center'>${item.FINAL_SEQUENCE }</td>
						<td class='td_center'>${item.TOTAL_PEOPLE }</td>
						<td class='td_left'>${item.EV_REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '2543' || menu.MENU_NO eq '123427' }">
		<div><!-- 奖励/惩戒 --><!-- 상벌 -->
		<div style="display: block;" id="displaycheckbox_4">
		<div class="panel">
		<h1><spring:message code="hr.viewReward.title.REWARD" /> <!--奖励--><!-- 포상 -->
		</h1>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewReward.title.REWARD_DATE" /> <!--奖励日期--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> <!--部门--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.POSITION_NAME" /> <!--职(岗)位--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.POST_NAME" /> <!--职级名称（职务）--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.REWARD_TYPE_NAME" /> <!--奖励类型--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.REWARD_BONUS" /> <!--奖励金额--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.REWARD_CONTENTS" /> <!--功绩内容--></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rewardList}" var="item" varStatus="i">

					<tr target="PERSON_ID"
						rel="${item.PERSON_ID}&REWARD_DATE=${item.REWARD_DATE}">
						<td class="td_center">${item.REWARD_DATE}</td>
						<td>${item.DEPTNAME}</td>
						<td class="td_center">${item.POSITION_NAME}</td>
						<td class="td_center">${item.POST_NAME}</td>
						<td class="td_center">${item.REWARD_TYPE_NAME}</td>
						<td class="td_center">${item.REWARD_BONUS}</td>
						<td>${item.REWARD_CONTENTS}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>





		<div class="panel">
		<h1><spring:message code="hr.viewReward.title.PUNISH" /> <!--惩戒--><!-- 징계 -->
		</h1>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewReward.title.DATE_PUNISHED" /> <!--惩戒日期--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> <!--部门--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.POSITION_NAME" /> <!--职(岗)位--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.POST_NAME" /> <!--职级名称（职务）--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.PUN_TYPE_NAME" /> <!--惩戒方式--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.PUN_BONUS" /> <!--惩戒金额--></th>
					<th width="100"><spring:message
						code="hr.viewReward.title.PUN_CONTENTS" /> <!--惩戒事由--></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${punishmentList}" var="item" varStatus="i">

					<tr target="PERSON_ID"
						rel="${item.PERSON_ID}&PUN_TYPE_ID=${item.PUN_TYPE_ID}&DATE_PUNISHED=${item.DATE_PUNISHED }">
						<td class="td_center">${item.DATE_PUNISHED}</td>
						<td>${item.DEPTNAME}</td>
						<td class="td_center">${item.POSITION_NAME}</td>
						<td class="td_center">${item.POST_NAME}</td>
						<td class="td_center">${item.PUN_TYPE_NAME}</td>
						<td class="td_center"><fmt:formatNumber
							value="${item.PUN_BONUS}" pattern="#,##0.00" /></td>
						<td>${item.REWARD_CONTENTS}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '123192' || menu.MENU_NO eq '2427'}">
		<div><!-- 外国语 --><!-- 외국어 --> <c:set value="${i.index}"
			var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_11">
		<div class="panel">
		<h1><spring:message
			code="hr.viewCompetence.title.FOREIGN_LANGUAGE" /> <!--外国语--></h1>
		<%
			// request.setAttribute( "toolbarInfo", request.getAttribute("toolbarInfopingjia")) ;
		%> <c:set value="/hrm/empinfo/viewEvaluate" var="turn_to_url" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="900" var="add_width" />
			<c:set value="400" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewLanguageLevelInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />

			<c:set value="900" var="delete_width" />
			<c:set value="400" var="delete_height" />

			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set value="0" var="delete_range" />
			<c:set value="dialog" var="delete_tab" />
			<c:set
				value="/hrm/empinfo/deleteLanguage?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />


			<c:set value="900" var="edit_width" />
			<c:set value="400" var="edit_height" />
			<c:set
				value="/hrm/empinfo/updateLanguage?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
			<c:set var="toolbarInfo" value="${toolbarInfopingjia}" />

			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="100"><spring:message
						code="hr.viewLanguage.KAOSHIDATE" /> <!--考试日期--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.EXAM_NAME" /> <!--考试名--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME" /> <!--等级--></th>
					<th width="100"><spring:message
						code="hr.viewCompetence.title.MARK" /> <!--分数--></th>
					<th width="100"><spring:message
						code="hr.viewPersonalInfo.title.jintiebiaozhun" /> <!--津贴标准--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${languageLevelList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.LANGUAGE_NO}">
						<td class='td_center'>${fn:substring(item.KAOSHIDATE,0, 10)}
						</td>
						<td class='td_center'>${item.EXAM_NAME}</td>
						<td class='td_center'>${item.LANGUAGE_LEVEL_NAME}</td>
						<td class='td_center'>${item.MARK}</td>
						<td class='td_center'>${item.ALLWANCE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '123193' || menu.MENU_NO eq '2429'}">
		<div><!-- 残疾 信息--> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_12"><!--<c:set value="/hrm/empinfo/viewHealth" var="turn_to_url" />-->


		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1100" var="add_width" />
			<c:set value="440" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewDisabledInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />

			<c:set value="800" var="delete_width" />
			<c:set value="300" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set
				value="/hrm/empinfo/deleteDisabled?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />

			<c:set value="dialog" var="edit_tab" />
			<c:set value="1100" var="edit_width" />
			<c:set value="440" var="edit_height" />

			<c:set
				value="/hrm/empinfo/updateDisabledInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>
		<div class="panel">
		<h1><spring:message code="hr.viewDisabled.title.DISABLED_INFO" />
		<!--残疾信息--><!-- 장애 --></h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set var="toolbarInfo" value="${toolbarInfodisability}" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="80"><spring:message
						code="hr.viewDisabled.title.DISABLED_TYPE" /> <!--残疾类型--></th>
					<th width="80"><spring:message
						code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" /> <!--  残疾认定日期-->
					</th>
					<%-- <th width="80">
													<spring:message code="hr.viewDisabled.title.DISABLED_DATE_CLOSED" />
													<!-- 残疾结束日期 -->
												</th> --%>
					<th width="80"><spring:message
						code="liang.hr.viewDisabled.title.DISABILITY_VALIDITY" /> <!--  有效期-->
					</th>
					<th width="80"><spring:message
						code="hr.viewDisabled.title.DISABLED_REMARK" /> <!--  备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${disabilityinfoList}" var="item" varStatus="i">

					<tr target="healthNo" rel="${item.T_ID}">
						<td class='td_left'>${item.DISABILITY_TYPE_NAME}</td>
						<td class='td_center'>${item.ADDDATE}</td>
						<%-- <td class='td_center'>
														${item.QUITDATE}
													</td> --%>
						<td class='td_center'>${item.DISABILITY_VALIDITY_NAME}</td>
						<td class='td_left'>${item.REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '123194' || menu.MENU_NO eq '2430'}">
		<div><!-- 工会 --> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_13"><c:set
			value="/hrm/empinfo/viewTrade" var="turn_to_url" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1100" var="add_width" />
			<c:set value="440" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewTradeunionInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />

			<c:set value="800" var="delete_width" />
			<c:set value="300" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set
				value="/hrm/empinfo/deleteTradeunion?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />
			<c:set value="dialog" var="edit_tab" />
			<c:set value="1100" var="edit_width" />
			<c:set value="440" var="edit_height" />
			<c:set
				value="/hrm/empinfo/updateTradeunion?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>
		<div class="panel">
		<h1><spring:message code="hr.viewHealth.title.TRADEUNIONTITLE" />
		<!--工会信息--><!-- -노조 --></h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set var="toolbarInfo" value="${toolbartradeunion}" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<%--<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
													<!--序号-->
												</th>
												--%>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE" />
					<!--公会内部职责--></th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" /> <!--入会日期-->
					</th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" /> <!--退会日期-->
					</th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG" /> <!--参加 工会与否-->
					</th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG" /> <!--会费支付状态-->
					</th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE" /> <!--支付方式-->
					</th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tradeUnionList}" var="item" varStatus="i">

					<tr target="healthNo" rel="${item.HEALTH_NO}">
						<td>${item.RES}</td>
						<td class='td_center' width="80">
						${fn:substring(item.ADDDATE,0, 10)}</td>
						<td class='td_center' width="80">
						${fn:substring(item.QUITDATE,0, 10)}</td>
						<td class='td_center' width="80">${item.JOIN_FLAG}</td>
						<td class='td_center' width="80">${item.PAY_FLAG}</td>
						<td class='td_center' width="80">${item.PAY_TYPE}</td>
						<td class='td_left'>${item.REMARK}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>


	<c:if test="${menu.MENU_NO eq '2491' || menu.MENU_NO eq '2430'}">
		<div><!-- 工会 --> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_13">
		<div class="panel">
		<h1><spring:message code="display.emp.ben.or.sendtoadministrator" />
		<!--派遣地--></h1>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.falingbianhao" /> <!--发令编号--></th>
					<th width="80"><spring:message
						code="display.emp.ben.transdate" /> <!--发令日期--></th>
					<th width="80"><spring:message
						code="display.emp.ben.effectivedate" /> <!--生效日期--></th>
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.gongzuodiqu" /> <!--工作地区--></th>
					<th width="80"><spring:message
						code="display.emp.ben.or.sendtoadministrator" /> <!--派遣地--></th>
					<!--<th width="80">
													<spring:message code="display.emp.ben.sendtype" />
													派遣类型
												</th>
												-->
					<th width="80"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${expInsideList}" var="item" varStatus="i">
					<tr target="healthNo" rel="${item.EXP_INSIDE_NO}">
						<td>${item.EXP_INSIDE_NO}</td>
						<td class='td_center' width="80">${item.CREATE_DATE}</td>
						<td class='td_center' width="80">${item.START_DATE}</td>
						<td class='td_center' width="80">${item.WORK_AREA}</td>
						<td class='td_center' width="80">${item.SENDADDRESS}</td>
						<td class='td_left'>${item.REMARK}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '216001'}">
		<div><a id="importExcelDialog" href="#" target="dialog"
			mask="true"></a> <c:set value="${i.index}" var="tabsSelected" />
		<div style="display: block;" id="displaycheckbox_17"><c:set
			value="/hrm/empinfo/viewAssistList" var="turn_to_url" /> <c:if
			test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set value="1100" var="add_width" />
			<c:set value="440" var="add_height" />
			<c:set value="dialog" var="add_tab" />
			<c:set
				value="/hrm/empinfo/viewAssistInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
				var="add_Url" />
			<c:set value="800" var="delete_width" />
			<c:set value="300" var="delete_height" />
			<c:set value="dialog" var="delete_tab" />
			<c:set value="0" var="delete_range" />
			<c:set value="0" var="delete_mask_exit" />
			<c:set value="true" var="delete_mask" />
			<c:set
				value="/hrm/empinfo/deleteAssist?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="delete_Url" />
			<c:set value="dialog" var="edit_tab" />
			<c:set value="1100" var="edit_width" />
			<c:set value="440" var="edit_height" />
			<c:set
				value="/hrm/empinfo/updateAssist?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				var="edit_Url" />
		</c:if>
		<div class="panel">
		<h1><spring:message code="hr.viewPersonalInfo.title.fuzhuxinxi" />
		<!--辅助信息--></h1>
		<c:if test="${isEssSystem ne '1' and personInfo.EMP_OFFICE ne '15120'}">
			<c:set var="toolbarInfo" value="${toolAssist}" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		</c:if>
		<div>
		<table class="table" width="101%">
			<thead>
				<tr>
					<c:forEach items="${assistList}" var="item" varStatus="i">
						<th width="80">${item.TITLE}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${assistList}" var="item" varStatus="i">
						<td class='td_center' width="80">${item.CONTENT}</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
		</div>
		</div>
		</div>
		</div>
	</c:if>
</c:forEach></div>
<div class="tabsFooter">
<div class="tabsFooterContent"></div>
</div>
</div>
</div>
