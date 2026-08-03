<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		var str = jzTitle();
		$('#jianjieTitle',navTab.getCurrentPanel()).html(str);
		//计算年龄
		var birthyear = "${personInfo.DOB}";
		if (birthyear != "") {
			var myDate = new Date();
			var year = myDate.getFullYear();
			var fourbirthyear = birthyear.substring(0, 4);
			var time = parseInt(year) - parseInt(fourbirthyear);
			$('#agepersonalinfo',navTab.getCurrentPanel()).html(time + "<spring:message code="hrm.empinfo.AGE1" />");
		}

		//计算在职期间
		var date1 = "${personInfo.DATE_STARTED}";
		var time1 = "${personInfo.WORK_TIME}";
		var year1 = parseInt(time1) / 365;
		var day = parseInt(time1) % 365;
		year1 = Math.floor(year1);
		if (year1 < 1) {
			year1 = 0;
		}
		var month1 = day / 30;
		month1 = Math.floor(month1);
		if (month1 < 1) {
			month1 = 0;
		}
		var totaltime = year1 + "<spring:message code='hrm.empinfo.YEAR' />" + month1 + "<spring:message code='hrm.empinfo.MONTH' /> ";

		var date2 = "${personInfo.TIME_STARTED}";
		var time2 = "${personInfo.WORK_TIME_SECOND}";
		var year2 = parseInt(time2) / 365;
		var day1 = parseInt(time2) % 365;
		year2 = Math.floor(year2);
		if (year2 < 1) {
			year2 = 0;
		}
		var month2 = day1 / 30;
		month2 = Math.floor(month2);
		if (month2 < 1) {
			month2 = 0;
		}
		var totaltime2 = year2 + "<spring:message code='hrm.empinfo.YEAR' />" + month2 + "<spring:message code='hrm.empinfo.MONTH' /> ";
		var totaltimefinal = "";
		if (date2 != "") {
			totaltimefinal = date1 + "(" + totaltime + ")(" + totaltime2
					+ " from" + date2 + ")";
		} else {
			if (date1 != "") {
				totaltimefinal = date1 + "(" + totaltime + ")";
			}
		}
		$('#worktime',navTab.getCurrentPanel()).html(totaltimefinal);

		//计算总工龄
		var totaltime = "";
		var shewaigongling = "${personInfo.SHEWAIGONGLING}";
		if (shewaigongling == '') {
			shewaigongling = '0';
		}
		var sheneigongling = "${personInfo.WORK_TIME}";
		if (sheneigongling == '') {
			sheneigongling = '0';
		}
		if (shewaigongling != '' && sheneigongling != '') {
			var time1 = parseInt(shewaigongling) + parseInt(sheneigongling);
			var year1 = parseInt(time1) / 365;
			var day = parseInt(time1) % 365;
			year1 = Math.floor(year1);
			if (year1 < 1) {
				year1 = 0;
			}
			var month1 = day / 30;
			month1 = Math.floor(month1);
			if (month1 < 1) {
				month1 = 0;
			}

			totaltime = year1 + "<spring:message code='hrm.empinfo.YEAR' />" + month1 + "<spring:message code='hrm.empinfo.MONTH1' /> ";
			$('#totalWorkAge',navTab.getCurrentPanel()).html(totaltime);
			totaltime = encodeURI(encodeURI(totaltime));
		}

		$('#viewEmpInfoAge',navTab.getCurrentPanel())
				.attr(
						'href',
						'/hrm/empinfo/viewEmpInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&totalWorkAge=' + totaltime);
	});
	function fangdajingPER(flag) {
		var name = encodeURI(encodeURI($('#seach_KEYPER',navTab.getCurrentPanel()).val()));
		$('#fangdaPER',navTab.getCurrentPanel())
				.attr(
						'href',
						'/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPersonalInfo&seach_KEY=' + name);
		if (flag == 'onkeyup')
			$('#fangdaPER',navTab.getCurrentPanel()).click();
	}
	function jzTitle() {
		var gg = "${personInfo.LOCAL_NAME }";
		var hh = "${personInfo.EMPID }";
		var ll = "${personInfo.POST_GRADE_NO_NAME}";
		var mm = "${personInfo.RANK_STATISTICS_NAME}";
		var nn = "${personInfo.COST_CENTER}";
		var oo = "${personInfo.EMP_OFFICE_NAME }";
		var str = "";
		if (gg != '') {
			str = str + gg;
		}
		if (hh != '') {
			str = str + " / " + hh;
		}
		if (ll != '') {
			str = str + " / " + ll;
		}
		if (mm != '') {
			str = str + "(" + mm + ")";
		}
		if (nn != '') {
			str = str + " / " + nn;
		}
		if (oo != '') {
			str = str + " / " + oo;
		}
		return str;
	}
</script>
<div class="pageHeader">
	<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="width: 10%">
					<!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
				</td>
				<td class="td_type" style="width: 10%"><input type="text"
					name="seach_KEYPER" id="seach_KEYPER" value="${KEY}"
					onkeydown="javascript:if(event.keyCode == 13)fangdajingPER('onkeyup');" />
				</td>
				<td class="td_type"><a class="btnLook" id="fangdaPER"
					onclick="fangdajingPER()" href="#" lookupGroup="person"> </a> <span
					style="margin-left: 50px;" id="jianjieTitle"></span></td>
			</tr>
		</table>
	</div>
</div>
<div class="formBar"><!-- <input style="margin-left: 1240px;" type="button" value="查询"  onclick="navTabNum('/hrm/empinfo/viewPersonalInfo','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','综合简介');"> -->
<ul class="toolBar">
	<li id="editLi"><a class="edit" href="#"
		onclick="navTabNum('/hrm/empinfo/viewPersonalInfo?PERSON_ID=${personInfo.PERSON_ID}','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','综合简介');">
	<span><spring:message code="button.search" /><!--查询--></span> </a></li>
 	<li id="editLi">
			<%-- <a class="edit"
				href="/hrm/report/payReport04?checkVal=report6&EMPID_STR=${personInfo.PERSON_ID}&LANG=${language }&filename=card"><span><!-- 人事卡 --><spring:message code="hrm.empinfo.personnel_card" /></span>
			</a> --%>
			<a class="edit" onclick="navTabNum('/hrm/empinfo/viewHTSVCardInfoList?&PERSON_ID=${personInfo.PERSON_ID}&seach_KEY=${personInfo.EMPID}','pageNum=1&menuNo=90000442&navTabId=hr3101','hr3101','<spring:message code="hrm.empinfo.personnel_card" />');">
				<span>
					<!-- 人事卡 --><spring:message code="hrm.empinfo.personnel_card" />
				</span>
			</a>
	</li>
	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
		<li id="editLi"><a class="edit" id="viewEmpInfoAge"
			href="/hrm/empinfo/viewEmpInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
			target="navTab" rel="viewEmpInfo" title="<spring:message code="hrm.empinfo.COMPANYINFORMATIONIN" />"><span> <spring:message
			code="button.update" /> <!-- 修改 --></span> </a></li>
	</c:if>
</ul>
</div>
<div>
<div class="panel collapse">
<h1><spring:message code="hrm.empinfo.COMPANYINFORMATIONIN" /><!--员工基础信息--></h1>
<div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	height="140">
	<tr>
		<td valign="top"><br />
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
			<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">	
				<td class="td_title" rowspan="8" width='170px'><img
					id='orgImage' name='orgImage' src='${personInfo.PHOTO_PATH}'
					border=1 style='width: 170px; height: 226px;'></td>
			</c:if>		
			<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">	
				<td class="td_title" rowspan="9" width='170px'><img
					id='orgImage' name='orgImage' src='${personInfo.PHOTO_PATH}'
					border=1 style='width: 170px; height: 226px;'></td>
			</c:if>	
				<td class="td_title"><spring:message code="hrm.empinfo.name" />
				<!--姓名--></td>
				<td class="td_type" width="30%">${personInfo.LOCAL_NAME } <c:if
					test="${not empty personInfo.ENGLISH_NAME}">(${personInfo.ENGLISH_NAME })</c:if>
				</td>
				<td class="td_title"><spring:message code="hrm.empinfo.empid" />
				<!--社号--></td>
				<td class="td_type" width="30%"><c:if
					test="${isEssSystem ne '1'}">
							${personInfo.EMPID }
					</c:if> <c:if test="${isEssSystem eq '1'}">
							${personInfo.EMPID }
					</c:if> <input type="hidden" name="PERSON_ID"
					value="${personInfo.PERSON_ID }"> <a id="onck" name="onck"
					href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person"
					width="950"></a></td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门  --></td>
				<td class="td_type" width="30%">${personInfo.DEPTNO_NAME }</td>
				<td class="td_title"><spring:message code="hrm.contract.Rank" />
				<!--职级--></td>
				<td class="td_type">${personInfo.POST_GRADE_NO_NAME}</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message code="org.title.MAIN_BUSINESS" /><!--主要业务--></td>
				<td class="td_type">${personInfo.MAIN_BUSINESS_NAME }&nbsp;
									&nbsp;(${personInfo.MAIN_BUSINESS_DES})</td>
				<td class="td_title"><spring:message code="hrm.contract.POSITION_NO" /><!--职责--></td>
				<td class="td_type" width="30%">${personInfo.POSITION_NO_NAME}</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" /><!--成本中心--></td>
				<td class="td_type">${personInfo.COST_CENTER}</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<td class="td_title"><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /><!--班组类型--></td>
				<td class="td_type" width="30%">${personInfo.SHIFT_NO_NAME}</td>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<td class="td_title"><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z" /><!--年资等级--></td>
				<td class="td_type" width="30%">${personInfo.PAY_STEP_NO_NAME}</td>
				</c:if>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.EMP_TYPE_NAME" /> <!--员工类型--></td>
				<td class="td_type">${personInfo.EMP_TYPE_NAME }</td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.HEAD_DEPARTMENT" /> <!--部门长--></td>
				<td class="td_type">${personInfo.HEAD_DEPARTMENT }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.DATE_STARTED" /> <!--入社日期--></td>
				<td class="td_type">${personInfo.DATE_STARTED }</td>
				<td class="td_title"><spring:message
					code="ess.infoApply.renzhizhuangtai" /> <!--任职状态--></td>
				<td class="td_type">${personInfo.EMP_OFFICE_NAME }</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.JINGLI_QIJIAN.Z" /> <!--经历期间--></td>
				<td class="td_type">${personInfo.EXPERIENCE} <spring:message code="display.mutual.month" /></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.WORK_DATE_COUNT" /> <!--在职期间--></td>
				<td class="td_type"><span>${personInfo.WORK_TIME}</span>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
</div>
<div style="clear: both;"></div>
<div class="tabs" currentIndex="${tabsSelected }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
	<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
		<li><a href="javascript:;"><span>${menu.MENU_NAME } </span> </a></li>
	</c:forEach>
</ul>
</div>
</div>

<div class="tabsContent" id="displaycheckbox"><c:forEach
	items="${menuThirdList}" var="menu" varStatus="i">
	<c:if test="${menu.MENU_NO eq '2540'}">
		<!-- 基础信息 -->
		<div>
		<div style="display: block;" id="displaycheckbox_1"><!-- isEssSystem的值不为‘1’，则是业务系统,否则为ESS系统, 以下均是!-->

		<div class="formBar">
		<ul class="toolBar">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<li id="editLi"><a class="edit"
					href="/hrm/empinfo/viewHrPersonalInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
					target="navTab" rel="viewHrPersonalInfo" title="<spring:message
				code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" />"><span>
				<spring:message code="button.update" /> </span> </a></li>
		</c:if>
		</ul>
		</div>
		<div>
		<div class="panel collapse">
		<h1><spring:message
			code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" /> <!--个人信息 -->
		<!-- 개인기초정보 --></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title">
					<spring:message code="pa.insurance.title.idNumber" /><!-- 身份证号 -->
				</td>
				<td class="td_type" width="20%">${personInfo.IDCARD_NO }</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.award_date" /><!--获证日期-->
				</td>
				<td class="td_type" width="25%">${personInfo.IDCARD_START_DATE}</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td class="td_title">
						<spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 -->
					</td>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td class="td_title">
						<spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE" /><!-- 签发地 --></td>
					</c:if>
				<td class="td_type" width="25%">${personInfo.ISSUING_AUTHORITY }</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
				</td>
				<td class="td_type" width="20%">${personInfo.CV_UPDATE_STATUS_NAME }</td>
			</tr>
			<tr>
				<td class="td_title">
					<spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期-->
				</td>
				<td class="td_type" width="25%">${personInfo.DOB}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.AGE" /><!--年龄-->
				</td>
				<td class="td_type" width="25%">${personInfo.AGE}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.ORIGIN.Z" /><!--出生地-->
				</td>
				<td class="td_type" width="25%">${personInfo.ORIGIN}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.SEXCODE" /><!--性别-->
				</td>
				<td class="td_type" width="25%">${personInfo.SEXCODE_NAME }</td>
			</tr>
			<tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td class="td_title"><spring:message
					code="hr.viewCondSql.title.ZUIZHONGXUEXIAO" /> <!--最终学校--></td>
				<td class="td_type" width="20%">${personInfo.INSTITUTION_NAME}</td>
				</c:if>
				<td class="td_title"><spring:message
					code="hrm.empinfo.FINALLY_DEGREE_CODE" /> <!--最终学历--></td>
				<td class="td_type" width="20%">${personInfo.DEGREE_CODE}</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /> <!--国籍--></td>
				<td class="td_type" width="20%">${personInfo.NATIONALITY_NAME }</td>
				<td class="td_title"><spring:message
					code="hr.viewPersonalInfo.title.NATION_NAME" /> <!--民族 --></td>
				<td class="td_type" width="20%">${personInfo.NATION_NAME }</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td class="td_title"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></td>
					<td class="td_type" width="15%">${personInfo.RELIGION }</td>
				</c:if>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.CELLPHONE" /> <!--手机--></td>
				<td class="td_type" width="20%">${personInfo.CELLPHONE}</td>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" />
					<!--个人邮箱--></td>
					<td class="td_type" width="20%">${personInfo.EMAIL_SECOND}</td>
				</c:if>
				<td class="td_title"><spring:message
					code="hrm.empinfo.MARITAL_STATUS_NAME" /> <!--婚姻状态--></td>
				<td class="td_type" width="20%">${personInfo.MARITAL_STATUS_NAME }</td>
				<td class="td_title">
					<spring:message code="hr.viewCondSql.title.JIEHUNRIQI" /><!--结婚日期-->
				</td>
				<td class="td_type" width="25%">${personInfo.WEDDING_DATE}</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" /><!--政治面貌-->
				</td>
				<td class="td_type" width="25%">${personInfo.POLITICAL_OUTLOOK_NAME}</td>
				</c:if>
			</tr>
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<tr>
				<td class="td_title">
					<spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否-->
				</td>
				<td class="td_type" width="25%">${personInfo.EXIST_SINGLE_NAME}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID-->
				</td>
				<td class="td_type" width="25%">${personInfo.SING_ID}</td>
				<td class="td_title">
					<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话-->
				</td>
				<td class="td_type" width="20%">${personInfo.OFFICE_PHONE}</td>
				<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" />
				<!--公司邮箱--></td>
				<td class="td_type" width="20%">${personInfo.EMAIL}</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.HUJIDIZHI" /> <!--户口所在地--></td>
				<td class="td_type" width="20%">${personInfo.REG_PLACE}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_LOCATION}</td>
				<td class="td_title">
					<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_ENTER}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_OUT}</td>
			</tr>
			<tr>
				<td class="td_title">
					<spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 -->
				</td>
				<td class="td_type" width="20%">${personInfo.HOME_PHONE}</td>
				<td class="td_title"><spring:message code="ess.empInfo.height" /><!--身高--></td>
				<td class="td_type" width="25%">${personInfo.HEIGHT}</td>
				<td class="td_title"><spring:message code="ess.empInfo.weight" /><!--体重--></td>
				<td class="td_type" width="25%">${personInfo.WEIGHT}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分-->
				</td>
				<td class="td_type" width="20%">${personInfo.RESIDENTIAL_DISTINCTION_NAME}</td>
			</tr>	
			</c:if>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<tr>
				<td class="td_title">
					<spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否-->
				</td>
				<td class="td_type" width="25%">${personInfo.EXIST_SINGLE_NAME}</td>
				<td class="td_title">
					<spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 -->
				</td>
				<td class="td_type" width="20%">${personInfo.HOME_PHONE}</td>
				<td class="td_title">
					<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话-->
				</td>
				<td class="td_type" width="20%">${personInfo.OFFICE_PHONE}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分-->
				</td>
				<td class="td_type" width="20%">${personInfo.RESIDENTIAL_DISTINCTION_NAME}</td>
			</tr>
			<tr>
				<td class="td_title">
					<spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID-->
				</td>
				<td class="td_type" width="25%">${personInfo.SING_ID}</td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.HUJIDIZHI" /> <!--户口所在地--></td>
				<td class="td_type" width="20%">${personInfo.REG_PLACE}</td>
				<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" />
				<!--个人邮箱--></td>
				<td class="td_type" width="20%">${personInfo.EMAIL_SECOND}</td>
				<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" />
				<!--公司邮箱--></td>
				<td class="td_type" width="20%">${personInfo.EMAIL}</td>
			</tr>
			<tr style="display:none">
				<td class="td_title">
					<spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_LOCATION}</td>
				<td class="td_title">
					<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_ENTER}</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出-->
				</td>
				<td class="td_type" width="25%">${personInfo.FILE_OUT}</td>
				<td class="td_title"></td>
				<td class="td_type" width="25%"></td>
			</tr>
			<tr>
				<td class="td_title">
					<spring:message code="hrm.empinfo.ARMY_OR_NOT.Z" /><!--参军与否-->
				</td>
				<td class="td_type" width="25%"><input type="checkbox" disabled="disabled" <c:if test="${personInfo.ARMY_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input></td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z" /><!--障碍与否-->
				</td>
				<td class="td_type" width="25%"><input type="checkbox" disabled="disabled" <c:if test="${personInfo.OBSTACLE_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input></td>
				<td class="td_title"></td>
				<td class="td_type" width="20%"></td>
				<td class="td_title"></td>
				<td class="td_type" width="20%"></td>
			</tr>
		</c:if>
		</table>
		</div>
		</div>
		</div>
		
		<div class="formBar">
		<ul class="toolBar">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewEmergencyAddress?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewEmergencyAddress" title="<spring:message code="hrm.empinfo.JINJILIANLUO_DIZHI.Z" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
		</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.JINJILIANLUO_DIZHI.Z" /> <!--紧急联络地址--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.FAM_TYPE_CODE_NAME" /> <!--关系--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.FAM_NAME" /> <!--姓名--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.FAM_PHONE" /> <!--联系电话--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.LIANXI_DIANHUA_TWO.Z" /> <!--联系电话2--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.EMAIL" />
				<!--E-Mail--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" />
				<!--主要联络处与否--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.FAM_ADDRESS" /> <!--地址--></td>
			</tr>
			<c:forEach items="${hrEmergencyAddressList}" var="e">
				<tr>
					<td class="td_type">${e.EMER_TYPE_CODE_NAME}</td>
					<td class="td_type">${e.EMER_NAME}</td>
					<td class="td_type">${e.EMER_PHONE}</td>
					<td class="td_type">${e.EMER_PHONE_SECOND}</td>
					<td class="td_type">${e.EMER_EMAIL}</td>
					<td class="td_type"><input type="checkbox" disabled="disabled" <c:if test="${e.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input></td>
					<td class="td_type">${e.EMER_ADDRESS}</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>

		<div class="formBar">
			<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<li id="editLi">
					<a class="edit"
						href="/hrm/empinfo/viewAddressMatters?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
						target="navTab" rel="viewAddressMatters" title="<spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />">
						<span><spring:message code="button.update" /> </span> 
					</a>
				</li>
			</c:if>
			</ul>
		</div>
		<div class="panel collapse">
			<h1><!-- 地址类型 --><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" /></h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table">
					<tr>
						<td class="td_title" width="30%" style="text-align:center">
							<!-- 地址类型 --><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
						</td>
						<td class="td_title" width="30%" style="text-align:center">
							<!-- 有效开始日 --><spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" />
						</td>
						<td class="td_title" width="40%" style="text-align:center">
							<!-- 地址 --> <spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
						</td>
					</tr>
					<c:forEach items="${hrAddressMattersList}" var="m">
						<tr>
							<td class="td_type">
								${m.ADDRESS_TYPE_NAME}
							</td>
							<td class="td_type">
								${m.EFFECTIVE_START_DATE}
							</td>
							<td class="td_type">
								${m.ADDRESS_CONTENT}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
							
		<div class="formBar">
		<ul class="toolBar">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewFamily?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewFamily" title="<spring:message code="hrm.empinfo.Family_member_information" />"><span> <spring:message
				code="button.update" /> </span> </a></li>
		</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Family_member_information" />
		<!--家庭成员信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /> <!--关系--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_NAME" /> <!--姓名--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_BORNDATE" /> <!--出生日期--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_PHONE" /> <!--联系电话--></td>
                <td class="td_title" style="text-align:center"><spring:message code="liang.hr.viewPersonalInfo.title.EMAIL" /> <!--EMAIL--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_COMPANY_NAME" /> <!--工作单位--></td>
                <td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.DEGREE_CODE" /> <!--学历--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_ADDRESS" /> <!--地址--></td>
			</tr>
			<c:forEach items="${hrFamilyList }" var="f">
				<tr>
					<td class="td_type">${f.FAM_TYPE_CODE_NAME }</td>
					<td class="td_type">${f.FAM_NAME }</td>
					<td class="td_type">${f.FAM_BORNDATE }</td>
					<td class="td_type">${f.FAM_PHONE }</td>
					<td class="td_type">${f.FAM_EMAIL }</td>
					<td class="td_type">${f.FAM_COMPANY_NAME }</td>
					<td class="td_type">${f.FAM_EDUCATION_NAME }</td>
					<td class="td_type">${f.FAM_ADDRESS }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		
		</div>
		</div>

	</c:if>
	<c:if test="${menu.MENU_NO eq '14013949'}">
		<div style="display: block;" id="displaycheckbox_1"><div class="formBar">
							<ul class="toolBar">
						<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
								<li id="editLi"><a class="edit"
									href="/hrm/empinfo/viewStartPoint?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									target="navTab" rel="hr0204" title="<spring:message code="hrm.empinfo.The_person" />"><span> <spring:message
												code="button.update" />
									</span> </a></li>
						</c:if>
							</ul>
						</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.The_person" /> <!--个人发令--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.starter_START_DATE" /> <!--发令日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="org.title.EXPERIENCE_TYPE_NAME" /> <!--发令区分--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.TRANS_REASON" /> <!--发令原因--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.ORG_NAME_LOCAL" /> <!--部门--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.contract.Rank" />
				<!--职级--></td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!--主要业务--></td>
				<td class="td_title" style="text-align:center">
					<spring:message code="ess.infoApply.renzhizhuangtai" /><!--任职状态--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.EMP_TYPE_CODE_NAME" /> <!--员工类型--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="org.title.MINISTER" /> <!--部门长--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="org.title.POSITION_NO" /> <!--职责--></td>
				<td class="td_title" style="text-align:center">
					<spring:message code="org.title.COST_CENTER" /><!--成本中心--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="org.title.REMARK" /> <!--备注--></td>
			</tr>
			<c:forEach items="${hrStartPointList}" var="s">
				<tr>
					<td class="td_type">${s.START_DATE }</td>
					<td class="td_type">${s.TRANS_CODE_NAME}</td>
					<td class="td_type">${s.TRANS_RESOURCE_NAME}</td>
					<td class="td_type">${s.DEPTNAME }</td>
					<td class="td_type">${s.POST_GRADE_NO_NAME}</td>
					<td class="td_type">${s.MAIN_BUSINESS_NAME }</td>
					<td class="td_type">${s.EMP_OFFICE_NAME }</td>
					<td class="td_type">${s.EMP_TYPE_CODE_NAME }</td>
					<td class="td_type">${s.HEAD_DEPARTMENT_NAME }</td>
					<td class="td_type">${s.POSITION_NO_NAME }</td>
					<td class="td_type">${s.COST_CENTER_NAME }</td>
					<td class="td_type">${s.REMARK }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		
		
		<!--<br/>
		合同  
		<div class="panel collapse">
		<h1><spring:message code="hrm.contract.Contract" />合同</h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
					<td class="td_title" >
						<spring:message code="hrm.contract.CONTRACT_TYPE" />合同类型
					</td>
					<td class="td_title" >
						<spring:message code="hrm.contract.Contract_TOTAL_PERIOD" />合同次数
					</td>
					<td class="td_title" >
						<spring:message code="hrm.contract.START_CONTRACT_DATE" />合同开始
					</td>
					<td class="td_title" >
						<spring:message code="hrm.contract.END_CONTRACT_DATE" />合同结束
					</td>
				</tr>
			<c:forEach items="${contracList}" var="item">
					<tr>
						<td class="td_type" >
							${item.CONTRACT_TYPE_CODE}
						</td>

						<td class="td_type" >
							${item.TOTAL_PERIOD}
						</td>
						<td class="td_type" >
							${item.START_CONTRACT_DATE}
						</td>

						<td class="td_type" >
							${item.END_CONTRACT_DATE}
						</td>

					</tr>
				</c:forEach>
		</table>
		</div>
		</div>
		-->
		

		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewExperiencePoint?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewExperiencePoint" title="<spring:message code="hrm.recruitManage.Experience_issues" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.recruitManage.Experience_issues" />
		<!--经历事项--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.COMPANY_NAME" /><!--公司名称-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.recruitManage.DATE_STARTED" /><!--入职日期-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.recruitManage.LEAVE_DATE" /><!--离职日期-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="ess.infoApply.DEPT" /><!--部门-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!--月薪-->
				</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<td class="td_title" style="text-align:center">
					<spring:message code="hr.viewCondSql.title.LIZHIYUANYIN" /><!--离职事由-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="ar.viewarcardrecord.title.beizhu" /><!--备注-->
				</td>
				</c:if>
			</tr>
			
			<c:forEach items="${hrExperiencePointList}" var="e">
				<tr>
					<td class="td_type">${e.CPNY_NAME }</td>
					<td class="td_type">${e.START_DATE }</td>
					<td class="td_type">${e.END_DATE }</td>
					<td class="td_type">${e.DEPT_NAME }</td>
					<td class="td_type">${e.PAY_YEAR }</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td class="td_type">${e.RESIGN_REASON }</td>
					<td class="td_type">${e.REMARK }</td>
					</c:if>
				</tr>
				
			</c:forEach>
		</table>
		</div>
		</div>

	</div>
	</c:if>
	<c:if test="${menu.MENU_NO eq '14014294'}">
		<div style="display: block;" id="displaycheckbox_1">
		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<li id="editLi"><a class="edit"
					href="/hrm/empinfo/viewEducationMatter?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
					target="navTab" rel="viewEducationMatter" title="<spring:message code="hrm.empinfo.Education_information" />"><span>
				<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Education_information" /><!--学历信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.recruitManage.INSTITUTION_NAME" /><!--学校名--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.DEGREE_CODE" /><!--学历--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.recruitManage.SUBJECT" /><!--专业--></td>	
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.recruitManage.START_DATE" /><!--入学日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.recruitManage.END_DATE" /><!--毕业日期--></td>
			</tr>
			
			<c:forEach items="${hrEducationMatterList }" var="e">
				<tr>
					<td class="td_type">${e.INSTITUTION_NAME }</td>
					<td class="td_type">${e.DEGREE_CODE_NAME }</td>
					<td class="td_type">${e.SUBJECT }</td>
					<td class="td_type">${e.START_DATE }</td>
					<td class="td_type">${e.END_DATE }</td>
				</tr>
				
			</c:forEach>
		</table>
		</div>
		</div>

		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewBidMatter?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewBidMatter" title="<spring:message code="hrm.empinfo.Qualification_information" />"><span> <spring:message
				code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Qualification_information" /><!--资格信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.Qualification_grade" /><!--等级--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.award_date" /><!--获证日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.Valid_date" /><!--有效日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.Certificate_number" /><!--证书编号--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.Issuing_authority" /><!--发证机关--></td>
			</tr>
			<c:forEach items="${viewBidMatter }" var="b">
				<tr>
					<td class="td_type">${b.QUAL_NAME }</td>
					<td class="td_type">${b.QUAL_LEVEL }</td>
					<td class="td_type">${b.DATE_OBTAINED }</td>
					<td class="td_type">${b.VALIDITY_DATE }</td>
					<td class="td_type">${b.QUAL_CARD_NO }</td>
					<td class="td_type">${b.QUAL_INSTITUTE }</td>
					
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>


		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewForeignLanguage?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewForeignLanguage" title="<spring:message code="hrm.empinfo.Foreign_language_ability" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Foreign_language_ability" /><!--外语能力--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.LANGUAGE" /><!--语言-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.comprehensive" /><!--综合-->
				</td>
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.LISTEN.Z" /><!--听-->
				</td>
			</c:if>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.SAY" /><!--说-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.READ" /><!--读-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.WRITE" /><!--写-->
				</td>
			</tr>
			<c:forEach items="${viewForeignLanguage }" var="f">
				<tr>
					<td class="td_type">${f.LANGUAGE_TYPE }</td>
					<td class="td_type">${f.COMBINED_NAME }</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td style="text-align: left">${f.LISTEN_NAME }</td>
					</c:if>
					<td class="td_type">${f.SAY_NAME }</td>
					<td class="td_type">${f.READ_NAME }</td>
					<td class="td_type">${f.WRITE_NAME }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		<!--
		<div class="formBar">
		<ul class="toolBar">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewTrainingBasic?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewTrainingBasic" title="<spring:message code="hrm.empinfo.Training_information" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
		</ul>
		</div>
		-->
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Training_information" /><!--培训信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.TRAIN_ADDRESS" /><!--培训地点--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.training_distinction" /><!--培训区分--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.TRAIN_curriculum" /><!--培训课程--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.Training_form" /><!--培训形式--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.START_DATE" /><!--培训开始日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.END_DATE" /><!--培训结束日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.TRAINING_RESULT" /><!--培训结果--></td>
			</tr>
			<c:forEach items="${viewTrain }" var="t">
				<tr>
					<td class="td_type">${t.TRAIN_ADDRESS }</td>
					<td class="td_type">${t.TRAIN_DIFF_CODE_NAME }</td>
					<td class="td_type">${t.COURSE_NAME_CODE }</td>
					<td class="td_type">${t.TRAIN_FORM_CODE_NAME}</td>
					<td class="td_type">${t.IMPLE_START_DATE }</td>
					<td class="td_type">${t.IMPLE_END_DATE }</td>
					<td class="td_type">${t.EVA_RESULT }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>

		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '14014295'}">
		<div style="display: block;" id="displaycheckbox_1">
		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewRecognition?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewRecognition" title="<spring:message code="hrm.empinfo.Reward_INFORMATION" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Reward_INFORMATION" /><!--奖励信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.praise_prize" /><!--表扬得奖--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="hrm.empinfo.praise_prize_date" /><!--奖励日期--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="ess.empInfo.awarding_authority" /><!--授予机关--></td>
				<td class="td_title" style="text-align:center"><spring:message
					code="ess.empInfo.bonus" /><!--奖金--></td>
			</tr>
			<c:forEach items="${viewRecognition }" var="r">
				<tr>
					<td class="td_type">${r.REWARD_TYPE_NAME }</td>
					<td class="td_type">${r.REWARD_DATE }</td>
					<td class="td_type">${r.REWARD_CNPY }</td>
					<td class="td_type">${r.REWARD }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>


		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewPunishment?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewPunishment" title="<spring:message code="hrm.empinfo.Discipline_INFORMATION" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Discipline_INFORMATION" /><!--惩戒信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.punishment_day" /><!-- 惩罚日-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.punishment_code" /><!-- 惩罚代码-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.relieve_day" /><!-- 解除日-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.punishment_organ_name" /><!-- 惩罚机关名-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.pay_cut_start_date" /><!-- 减薪开始日-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.pay_cut_end_date" /><!-- 减薪结束日-->
				</td>
			</tr>
			
			<c:forEach items="${viewPunishment }" var="p">
				<tr>
					<td class="td_type">${p.PUNISH_DATE }</td>
					<td class="td_type">${p.PUNISH_CODE_NAME }</td>
					<td class="td_type">${p.RELEASE_DATE }</td>
					<td class="td_type">${p.PUNISH_DEPARTMENT }</td>
					<td class="td_type">${p.PAYCUT_START_DATE }</td>
					<td class="td_type">${p.PAYCUT_END_DATE }</td>
				</tr>
				
			</c:forEach>
		</table>
		</div>
		</div>

		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '14014296'}">
		<div style="display: block;" id="displaycheckbox_1">
		<div class="formBar">
		<!--<ul class="toolBar">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewEvaluateInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewEvaluateInfo" title="<spring:message code="hrm.empinfo.Evaluation_message" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
		</ul>
		--></div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.Evaluation_items" /><!--评价事项--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.Evaluation_year" /><!--评价年度--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.January" /><!--1月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.February" /><!--2月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.March" /><!--3月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.April" /><!--4月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.May" /><!--5月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.June" /><!--6月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.July" /><!--7月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.August" /><!--8月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.September" /><!--9月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.October" /><!--10月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.November" /><!--11月--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.December" /><!--12月--></td>
				<td class="td_title"><spring:message code="hr.viewEvaluate.title.EV_ABIL"/><!--能力--></td>
			</tr>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align: center">${item.EVS_YEAR}</td>
					<td style="text-align: center">${item.EVS_MONTH1}</td>
					<td style="text-align: center">${item.EVS_MONTH2 }</td>
					<td style="text-align: center">${item.EVS_MONTH3 }</td>
					<td style="text-align: center">${item.EVS_MONTH4 }</td>
					<td style="text-align: center">${item.EVS_MONTH5 }</td>
					<td style="text-align: center">${item.EVS_MONTH6 }</td>
					<td style="text-align: center">${item.EVS_MONTH7 }</td>
					<td style="text-align: center">${item.EVS_MONTH8 }</td>
					<td style="text-align: center">${item.EVS_MONTH9}</td>
					<td style="text-align: center">${item.EVS_MONTH10}</td>
					<td style="text-align: center">${item.EVS_MONTH11}</td>
					<td style="text-align: center">${item.EVS_MONTH12}</td>
					<td style="text-align: center">${item.EVS_MONTH13}</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '14014297'}">
		<div style="display: block;" id="displaycheckbox_1">

		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewSpecialMatter?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
				target="navTab" rel="viewSpecialMatter" title="<spring:message code="ar.viewEmpInfoListTanchu.TEJISHIXIANG.b" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="ar.viewEmpInfoListTanchu.TEJISHIXIANG.b" /><!--特记事项--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center">
					<spring:message code="inct.salesman.createTime"/><!-- 注册时间 -->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hr.hrm.empinfo.INFOR_DIS_CODE.Z"/><!-- 信息区分代码 -->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hr.hrm.empinfo.GENERATION_TITLE.Z"/><!-- 生成题目 -->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者-->
				</td>
				<td class="td_title" style="text-align:center">
					<spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间-->
				</td>
			</tr>
			<c:forEach items="${viewSpecialMatter }" var="s">
				<tr>
					<td class="td_type">${s.REGISTRATION_DATE }</td>
					<td class="td_type">${s.INFOR_DIS_CODE_NAME }</td>
					<td class="td_type">${s.GENERATION_TITLE }</td>
					<c:if test="${not empty s.UPDATED_BY}">
						<td style="text-align: left">${s.UPDATED_BY
						}&nbsp;&nbsp;${s.UPDATED_IP }</td>
						<td style="text-align: left">${s.UPDATE_DATE}</td>
					</c:if>
					<c:if test="${empty s.UPDATED_BY}">
						<td style="text-align: left">${s.CREATED_BY
						}&nbsp;&nbsp;${s.CREATED_IP }</td>
						<td style="text-align: left">${s.CREATE_DATE }</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>

		</div>
	</c:if>

	<c:if test="${menu.MENU_NO eq '14014298'}">
		<div style="display: block;" id="displaycheckbox_1">
		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewPassportPerson?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&flag=1"
				target="navTab" rel="viewPassportPerson" title="<spring:message code="hrm.recruitManage.DOCUMENT_information" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.recruitManage.DOCUMENT_information" /><!--证件信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.recruitManage.IDCARD_PNAME" /><!--证件人姓名--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.recruitManage.IDCARD_NAME" /><!--证件名称--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.recruitManage.IDCARD_NO" /><!--证件号码--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.recruitManage.IDCARD_ENDTIME" /><!--证件到期日期--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.recruitManage.OFFICE_CODE" /><!--发证机关--></td>
				<td class="td_title" style="text-align:center"><spring:message code="hrm.empinfo.REMARK" /><!--备注--></td>
			</tr>
			<c:forEach items="${viewPassportPerson }" var="pp">
				<tr>
					<td class="td_type">${pp.RELATION_NAME }</td>
					<td class="td_type">${pp.RELATION }</td>
					<td class="td_type">${pp.CERTIFICATE_TYPE_CODE }</td>
					<td class="td_type">${pp.CERTIFICATE_NUM }</td>
					<td class="td_type">${pp.CERTIFICATE_DATE }</td>
					<td class="td_type">${pp.OFFICE_CODE }</td>
					<td class="td_type">${pp.REMARK }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		<c:if test="${LoginUser.cpnyId eq 'HTSV111'}">
		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
			<li id="editLi"><a class="edit"
				href="/hrm/empinfo/viewPassportFamily?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }&flag=2"
				target="navTab" rel="viewPassportFamily" title="<spring:message code="hrm.empinfo.FAMILY_PASSPORT_INFORMATION" />"><span>
			<spring:message code="button.update" /> </span> </a></li>
			</c:if>
		</ul>
		</div>
		<div class="panel collapse">
		<h1><spring:message code="hrm.empinfo.FAMILY_PASSPORT_INFORMATION" /><!--家人护照信息--></h1>
		<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.name" /><!--姓名--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.PASSPORT_NO" /><!--护照号码--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.passport's_period_validity" /><!--护照有效期--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.IDCARD_NO" /><!--身份证号码--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.Residence_permit_number" /><!--居留许可证号码--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.Valid_period_residence_permit" /><!--居留许可证有效期--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
			</tr>
			<c:forEach items="${viewPassportFamily }" var="pp">
				<tr>
					<td class="td_type">${pp.RELATION }</td>
					<td class="td_type">${pp.RELATION_NAME }</td>
					<td class="td_type">${pp.PASSPORTNUM }</td>
					<td class="td_type">${pp.PASSPORT_DATE }</td>
					<td class="td_type">${pp.IDCARD_NO }</td>
					<td class="td_type">${pp.RESI_PERMIT_NUM }</td>
					<td class="td_type">${pp.RESI_PERMIT_DATE }</td>
					<c:if test="${not empty pp.UPDATED_BY}">
						<td style="text-align: left">${pp.UPDATED_BY
						}&nbsp;&nbsp;${pp.UPDATED_IP }</td>
						<td style="text-align: left">${pp.UPDATE_DATE}</td>
					</c:if>
					<c:if test="${empty pp.UPDATED_BY}">
						<td style="text-align: left">${pp.CREATED_BY
						}&nbsp;&nbsp;${pp.CREATED_IP }</td>
						<td style="text-align: left">${pp.CREATE_DATE }</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
	</c:if>
		</div>
	</c:if>
</c:forEach></div>
</div>
</div>