 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<style type="text/css">
.style4 {
	font-size: 18px;
	font-weight: bold;
}
.style5 {font-size: 14px}

</style>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人事信息</title>
<style type="text/css">
	td {
		text-align: center;
		font-size: 13;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=_personalInfoExcel.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	 <tr align="center" valign="middle">
       <th colspan="10">
			员工基础信息
		</th>
    </tr>
	<tr>
		<td class="td_title" height="30" nowrap>法人代码</td>
	    <td class="td_type" nowrap="nowrap">${personInfo.CPNY_ID}</td>
		<td class="td_title" height="30" nowrap>社号</td>
		<td class="td_type" nowrap="nowrap">${personInfo.EMPID}</td>
		<td class="td_title" height="30" nowrap>英文名</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.CHINESE_PINYIN}</td>
		<td class="td_title" height="30" nowrap>中文名</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.LOCAL_NAME}</td>
	</tr>
	<tr>
		<td class="td_title" height="30" nowrap>部门</td>
	    <td class="td_type" nowrap="nowrap">${personInfo.DEPTNO_NAME}</td>
		<td class="td_title" height="30" nowrap>职位</td>
		<td class="td_type" nowrap="nowrap">${personInfo.DUTY_NO}</td>
		<td class="td_title" height="30" nowrap>职责</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.POSITION_NO}</td>
		<td class="td_title" height="30" nowrap>职级（级号）</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.POST_GRADE_NO}</td>
	</tr>
	<tr>
		<td class="td_title" height="30" nowrap>职群</td>
	    <td class="td_type" nowrap="nowrap">${personInfo.JOB_FAM}</td>
		<td class="td_title" height="30" nowrap>任期类型</td>
		<td class="td_type" nowrap="nowrap">${personInfo.STATUS_NAME}</td>
		<td class="td_title" height="30" nowrap>LGE集团入职日期</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.JOIN_BLOC_DATE}</td>
		<td class="td_title" height="30" nowrap>法人入职日期</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.DATE_STARTED}</td>
	</tr>
	<tr>
		<td class="td_title" height="30" nowrap>试用期开始日期 </td>
	    <td class="td_type" nowrap="nowrap">${personInfo.PROB_STRT_DATE}</td>
		<td class="td_title" height="30" nowrap>试用期结束日期 </td>
		<td class="td_type" nowrap="nowrap">${personInfo.END_PROBATION_DATE}</td>
		<td class="td_title" height="30" nowrap>试用期比例</td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.PROB_PAY_RAT}</td>
		<td class="td_title" height="30" nowrap>离职日期 </td>
		<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.DATE_LEFT}</td>
	</tr>
</table>
<br>
<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
			 <tr align="center" valign="middle">
       <th colspan="8">基本信息</th>
    </tr>
			<tr>
				<td class="td_title" height="30" nowrap>国籍</td>
				<td class="td_type" nowrap="nowrap">${personInfo.NATIONALITY_CODE }</td>
				<td class="td_title" height="30" nowrap>性别</td>
				<td class="td_type" nowrap="nowrap">${personInfo.SEX_NAME }</td>
				<td class="td_title" height="30" nowrap>出生年月日</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.DOB }</td>
				<td class="td_title" height="30" nowrap>学位</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.FINAL_DEGREE_CODE}</td>
			</tr>

			<tr>
				<td class="td_title" height="30" nowrap>结婚纪念日</td>
				<td class="td_type" nowrap="nowrap">${personInfo.WEDD_DATE }</td>
				<td class="td_title" height="30" nowrap>身份证号</td>
				<td class="td_type" nowrap="nowrap">${personInfo.IDCARD_NO }</td>
				<td class="td_title" height="30" nowrap>ID卡号</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.ID_CARD_NO }</td>
				<td class="td_title" height="30" nowrap>邮箱</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.EMAIL }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap>家庭电话</td>
				<td class="td_type" nowrap="nowrap">${personInfo.HOME_PHONE}</td>
				<td class="td_title" height="30" nowrap>办公电话</td>
				<td class="td_type" nowrap="nowrap">${personInfo.OFFICE_PHONE}</td>
				<td class="td_title" height="30" nowrap>手机号码</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.CELLPHONE }</td>
				<td class="td_title" height="30" nowrap>户口性质</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.REG_TYPE_CODE}</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap>户口所在地</td>
				<td class="td_type" colspan="9">${personInfo.REG_PLACE }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="rp.report.title.homeaddress" /> <!--家庭住址--></td>
				<td class="td_type" colspan="9">${personInfo.IDCARD_ADDR }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.HOME_ADDRESS" /> <!--现住址--></td>
				<td class="td_type" colspan="6">${personInfo.HOME_ADDRESS }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewCondSql.title.YOUBIAN" /> <!--邮编--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.POSTALCODE }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.BORNPLACE_NAME" /> <!--籍贯--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.BORNPLACE_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.NATION_NAME" /> <!--民族 --></td>
				<td class="td_type" nowrap="nowrap">${personInfo.NATION_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.POLITY_NAME" /> <!--政治面貌 --></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.POLITY_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" /> <!--是否共产党员-->
				</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.WHETHER_COMMUNIST_NAME }</td>
			</tr>

			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.shengao" /> <!--身高--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.HEIGHT }cm</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.tizhong" /> <!--体重 -->
				<td class="td_type" nowrap="nowrap">${personInfo.WEIGHT }kg</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewHealth.title.BLOOD_TYPE_NAME" /> <!--血型 --></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.BLOOD_TYPE_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewCondSql.titleSHIFOUCANJI" /> <!--是否残疾--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.DISABILITY_YN_NAME }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.zhaopinlaiyuan" /> <!--招聘来源--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.RECRUITMENT_SOURCE_TYPE_NAME }
				</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPromote.title.RESIGN_REASON" /> <!--离职原因 --></td>
				<td class="td_type" nowrap="nowrap">${personInfo.LEAVE_REASON }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.jiangchengbeizhu" /> <!--奖惩备注 -->
				</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.REMARK }</td>
				<td class="td_title" height="30" nowrap></td>
				<td class="td_type" nowrap="nowrap" colspan="2"></td>
			</tr>
		</table>
<br>
 <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
			
			 <tr align="center" valign="middle">
       <th colspan="10"><span class="style4">
			工资信息
		</span></th>
    </tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.empinfo.pay.type.num" /> <!--工资级号--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.PAY_GRADE }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.empinfo.pay.type.num.leave" /> <!--工资级号等级--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.PAY_STEP }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="ess.viewpersonalpainfo.jibengongzi" /> <!--基本工资--></td>
				<td class="td_type" nowrap="nowrap" colspan="2"> ${personInfo.BASE_PAY}</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.empinfo.pay.VARIABLE_SALARY_MONTH" /> <!--变动工资--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.VARB_PAY }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.nianxin" /> <!--年薪--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.ANSAL}</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.nyinhangdaima" /> <!--银行代码--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.BANK_ID }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="pa.wagebase.title.openAccountBanks" /> <!--开户行--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.CARD_NAME}</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="rp.report.title.bankcardno" /> <!-- 银行账号 --></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.CARD_NO}</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.feiyongnyinhangdaima" /> <!--费用银行代码-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.EXPNS_BANK_CD }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.feiyongkaihuhang" /> <!--费用开户行-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.EXPNS_BANK_BRNCH_NM }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.feiyongyinhangzhanghao" /> <!--费用银行账号-->
				</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.EXPNS_BANK_ACCT_NO}</td>
				<td class="td_title" height="30" nowrap></td>
				<td class="td_type" nowrap="nowrap" colspan="2"></td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.aixinjijinzhifufangshi" /> <!--爱心基金支付方式-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.LOVE_FUND_PAYMENT_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.shifouzhifuaixinjijin" /> <!--是否支付爱心基金-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.IF_PAYMENT_LOVE_FUND_NAME }</td>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.fudanfangzubiaozhi" /> <!--负担房租标志-->
				</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.IF_PAYMENT_RENT_NAME }</td>
				<td class=td_title><spring:message
					code="hr.viewPersonalInfo.title.fudanyiliaofeibiaozhi" /> <!--负担医疗费标志--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.IF_PAYMENT_MEDICAL_NAME }</td>
			</tr>
			<tr>
				<td class="td_title" height="30" nowrap><spring:message
					code="hr.viewPersonalInfo.title.fudanjiaoyufeibiaozhi" /> <!--负担教育费标志-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.IF_PAYMENT_EDUCATION_NAME }</td>
				<td class="td_title" height="30" nowrap></td>
				<td class="td_type" nowrap="nowrap"></td>
				<td class="td_title" height="30" nowrap></td>
				<td class="td_type" nowrap="nowrap" colspan="2"></td>
				<td class=td_title></td>
				<td class="td_type" nowrap="nowrap" colspan="2"></td>
			</tr>
		</table>   
<br>
 <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
 			
 			<tr align="center" valign="middle">
       <th colspan="10"><span class="style4">
			工作信息
		</span></th>
    </tr>
			<tr>
				<td class="td_title" height="30" nowrap height="30"><spring:message
					code="hr.viewPersonalInfo.title.shifouFSE" /> <!--是否FSE--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.FSE_YN}</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.gongzuoleixing.chr" /> <!--工作类型(CHR)-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.PROMTR_WORK_NAME}</td>
				<td class="td_title" height="30" nowrap ><spring:message
					code="hr.viewPersonalInfo.title.banhao" /> <!--班号--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.SHIFT_NO}</td>
				<td class="td_title" height="30" nowrap></td>
				<td class="td_type" nowrap="nowrap" colspan="2"></td>
			</tr>
			<tr>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.renyuanleixing.chr" /> <!--人员类型(CHR)-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.EMP_TYPE_NAME }</td>
				<td class="td_title" height="30" nowrap height="30">人员类型生效日期</td>
				<td class="td_type" nowrap="nowrap" >${personInfo.EMP_TYPE_START_DATE }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.gongzuodiqu" /> <!--工作地区--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.WORK_AREA_NAME }</td>
				<td class="td_title" height="30">福利地区(公积金)</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.INSRAREA_ID_INS_NAME }</td>
			</tr>
			<tr>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.laodongshoucebianhao" /> <!--劳动手册编号-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.MANUAL_NUM }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.sheneigongling" /> <!--社内工龄--></td>
				<td class="td_type" nowrap="nowrap"><c:if
					test="${personInfo.INNER_WORK_YEAR ne 0 and personInfo.INNER_WORK_YEAR ne null }">${personInfo.INNER_WORK_YEAR}<spring:message
						code="liang.hr.viewWorkInfo.title.YEAR" />
				</c:if> <c:if
					test="${personInfo.INNER_WORK_MONTH ne 0 and personInfo.INNER_WORK_MONTH ne null}">${personInfo.INNER_WORK_MONTH}<spring:message
						code="hr.viewPersonalInfo.title.WORKINFO_MONTH" />
				</c:if></td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.shewaigongling" /> <!--社外工龄--></td>
				<td class="td_type" nowrap="nowrap" colspan="2"><c:if
					test="${personInfo.YEAR_AGE_LIMIT ne 0 and personInfo.YEAR_AGE_LIMIT ne null }">${personInfo.YEAR_AGE_LIMIT}<spring:message
						code="liang.hr.viewWorkInfo.title.YEAR" />
				</c:if> <c:if
					test="${personInfo.MONTH_AGE_LIMIT ne 0 and personInfo.MONTH_AGE_LIMIT ne null}">${personInfo.MONTH_AGE_LIMIT}<spring:message
						code="hr.viewPersonalInfo.title.WORKINFO_MONTH" />
				</c:if></td>
				<td class=td_title height="30">福利地区(保险)</td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.INSRAREA_ID_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.baoxiangongsi" /> <!--保险公司--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.INSURANCE_COMPANY_NAME }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" /> <!--保险类型-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.INSURANCE_TYPE_CODE_NAME }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.nianjiajizhun"  /> <!--年假基准--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.YY_VAC_STD_DATE }</td>
				<td class=td_title height="30"><spring:message
					code="hr.viewPersonalInfo.title.fengongsi.daqu" /> <!--分公司(大区)--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.PAY_AREA_CD_NAME }</td>
			</tr>
			<tr>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.chanpin" /> <!--产品--></td>
				<td class="td_type" nowrap="nowrap">${personInfo.PROD_TP_NAME }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu" /> <!--促销员所属-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.PROMTR_TP_NAME }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.xingjijibie" /> <!--星级级别--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.STAR_TP_NAME }</td>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.shifoujianmai" /> <!--是否兼卖--></td>
				<td class="td_type" nowrap="nowrap" colspan="2">${personInfo.PART_TIME_YN_NAME }</td>
			</tr>
			<tr>
				<td class="td_title" height="30"><spring:message
					code="hr.viewPersonalInfo.title.shifougongjiancuxiaoyuan" /> <!--是否共建促销员-->
				</td>
				<td class="td_type" nowrap="nowrap">${personInfo.COMM_YN_NAME }</td>
				<td class="td_title" height="30">评价类型</td>
				<td class="td_type" nowrap="nowrap">${personInfo.EVS_TYPE_NAME }</td>
				<td class="td_title" height="30">兼卖产品</td>
				<td class="td_type" colspan="5">
				<c:if test="${personInfo.PART_TIME_YN_NAME eq 'Y'}">
					<c:forEach items="${productList}" var="item" varStatus="i">
						<c:if test="${i.count eq 1}">${item.CONTENT }</c:if>
						<c:if test="${i.count ne 1}">,${item.CONTENT }</c:if>
					</c:forEach>
				</c:if>		
				</td>
			</tr>
		</table>
   <br>
  
   <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
       <th colspan="10">学习经历</th>
    </tr>
			<thead>
				<tr>
					<th  height="30"><spring:message
						code="hr.viewPersonalInfo.title.INSTITUTION_NAME" /> <!--学校名--></th>
					<th  height="30" colspan="2"><spring:message
						code="hr.viewPersonalInfo.title.SUBJECTNAME" /> <!--专业--></th>
					<th  height="30" colspan="2"><spring:message
						code="liang.hr.viewPersonalInfo.title.START_DATE" /> <!--入学年月-->
					</th>
					<th  height="30" colspan="2"><spring:message
						code="liang.hr.viewPersonalInfo.title.END_DATE" /> <!--毕业年月--></th>
					<th  height="30" colspan="3"><spring:message
						code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" /> <!--所在地--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${educationList}" var="item">
					<tr target="sid" rel="${item.EDUC_NO}">
						<td class='td_left'>${item.INSTITUTION_NAME}</td>
						<td class='td_left'  colspan="2">${item.SUBJECT}</td>
						<td class='td_center' colspan="2">${item.START_DATE}</td>
						<td class='td_center' colspan="2">${item.END_DATE}</td>
						<td class='td_center' colspan="3">${item.SITE_PROVINCE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
 <br>   	
 <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
 			 <tr align="center" valign="middle">
       			<th colspan="10">
					发令信息</th>
  			 </tr>
			<thead>
				<tr>
					<th  height="30" colspan="3"><spring:message code="hr.assignment.type" /> <!--发令类型-->
					</th>
					<th><spring:message code="hr.enpinfo.title.EMP.EXPDATE" /> <!--发令日期-->
					</th>
					<th><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!--部门--></th>
					<th height="30"><spring:message
						code="hr.viewPersonalInfo.title.POSITION_NAME" /> <!--职位--></th>
					<th><spring:message code="hr.viewPersonalInfo.title.DUTY_NAME" />
					<!--职责--></th>
					<th><spring:message code="hr.assignment.zhiji" /> <!--职级(级号)-->
					</th>
					<th colspan="2"><spring:message code="hr.assignment.group" /> <!--职群--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${assignmentList}" var="item" varStatus="i">
					<tr target="sid" rel="${item.ASSIGNMENT_ID}">
						<td class='td_center' colspan="3">
						${item.ASSIGNMENT_TYPE_1ST}--${item.ASSIGNMENT_TYPE_2ND}</td>
						<td class='td_center'>${item.ASSIGNMENT_START_DATE}</td>
						<td class='td_center'>${item.ORG_NAME_LOCAL}</td>
						<td class='td_center'>${item.JOB_DETAIL}</td>
						<td  class='td_center'>${item.POSITION}</td>
						<td class='td_center'>${item.GRADE}</td>
						<td class='td_center' colspan="2">${item.JOB_FAMILY}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
<br>		
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
 			 <tr align="center" valign="middle">
       			<th colspan="10">
					合同信息</th>
  			 </tr>
			<thead>
				<tr>
					<th height="30" colspan="2"><spring:message
						code="hr.viewContract.title.CONTRACT_TYPE_NAME" /> <!--合同类型--></th>
					<th height="30"><spring:message
						code="hr.viewPersonalInfo.title.TOTAL_PERIOD" /> <!--合同次数--></th>
					<th height="30" colspan="2"> <spring:message
						code="zxc.hr.contract.CONTRACT_START_DATE" /> <!--开始日--></th>
					<th height="30" colspan="2"><%--
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													--%> <spring:message
						code="zxc.hr.contract.CONTRACT_END_DATE" /> <!--结束日--></th>
					<th  height="30" colspan="3"><spring:message
						code="hr.viewPromote.title.REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contracList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.CONTRACT_NO}">
						<td colspan="2">${item.CONTRACT_TYPE_NAME}</td>
						<td class='td_center'>${item.TOTAL_PERIOD }</td>
						<td class='td_center' colspan="2">${item.START_CONTRACT_DATE}</td>
						<td class='td_center' colspan="2">${item.END_CONTRACT_DATE}</td>
						<td colspan="3">${item.REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
<br>		
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">工作经历</th>
   		 </tr>
			<thead>
				<tr>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewTranslate.title.PUBLIC_START_DATE" /> <!--开始时间--></th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewTranslate.title.PUBLIC_END_DATE" /> <!--结束时间--></th>
					<th width="80" height="30" colspan="2" nowrap><spring:message
						code="hr.viewWorkInfo.title.CPNY_NAME" /> <!--工作单位--></th>
					<th width="80" height="30" ><spring:message
						code="hr.viewWorkInfo.title.DEPT_NAME" /> <!--部门--></th>
					<th width="80" height="30" colspan="2" nowrap><spring:message
						code="public.title.positionName" /> <!--职位--></th>
					<th width="80" height="30" nowrap><spring:message
						code="sys.postManage.title.postGrade" /> <!--职级--></th>
					<th width="80" height="30" colspan="2" nowrap><spring:message
						code="hr.viewPersonalInfo.title.gongzidaiyu" /> <!--工资待遇--></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${workExperienceList}" var="item" varStatus="i">

					<tr target="personId" rel="${item.HEALTH_NO}">
						<td class='td_center'>${item.START_DATE}</td>
						<td class='td_center'>${item.END_DATE}</td>
						<td class='td_center' colspan="2">${item.CPNY_NAME}</td>
						<td class='td_center' >${item.DEPT_NAME}</td>
						<td class='td_center' colspan="2">${item.POSITION}</td>
						<td class='td_center'>${item.DUTY}</td>
						<td class='td_center' colspan="2">${item.PAYROLL}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
	<br>	
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">培训</th>
   		 </tr>
			<thead>
				<tr>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.COURSE_NAME" /> <!--课程名--></th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.SELECT_MUST" /> <!--选择/必选  --></th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE" /> <!--培训区分  -->
					</th>
					<th width="10%" height="30"  nowrap><spring:message
						code="zxc.hr.contract.CONTRACT_START_DATE" /> <!--起始日期--></th>
					<th width="10%" height="30" nowrap><spring:message
						code="zxc.hr.contract.CONTRACT_END_DATE" /> <!--终止日期--></th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.INSTITUTION_NAME" /> <!--培训机关-->
					</th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.TRAINING_METHOD" /> <!--培训方法-->
					</th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.TRAINING_TIME" /> <!--培训时间--></th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.TRAINING_RESULT" /> <!--培训结果-->
					</th>
					<th width="10%" height="30" nowrap><spring:message
						code="liang.hr.viewTraining.title.REMARKS" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${trainingInfoList}" var="item" varStatus="i">

					<tr target="trainNo" rel="${item.TRAIN_NO}">
						<td class='td_left'>${item.COURSE_NAME}</td>
						<td class='td_center'>${item.MUST_NAME}</td>
						<td class='td_center'>${item.TRAINING_DIFFERENTIATE_NAME}</td>
						<td class='td_center' >${item.START_DATE}</td>
						<td class='td_center' >${item.END_DATE}</td>
						<td class='td_left'>${item.INSTITUTION_NAME}</td>
						<td class='td_center'>${item.TRAINING_METHOD_NAME}</td>
						<td class='td_center'>${item.TRAINING_TIME}</td>
						<td class='td_center'>${item.TRAINING_RESULT}</td>
						<td class='td_left'>${item.REMARKS}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>  
		  <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">家庭成员</th>
   		 </tr>
			<thead>
				<tr>
					<th width="100" height="30" colspan="2"><spring:message
						code="hr.viewRelation.title.FAM_TYPE_NAME" /> <!--关系--></th>
					<th width="100" height="30" colspan="3"><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名--></th>
					<th width="100" height="30" colspan="2"><spring:message
						code="hr.viewPersonalInfo.title.DOB" /> <!--出生日期--></th>
					<th width="100" height="30" colspan="3"><spring:message
						code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME" /> <!--单位名称--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${homeRelationList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.FAMILY_NO}">
						<td class='td_center' colspan="2">${item.FAM_TYPE_NAME}</td>
						<td class='td_center' colspan="3">${item.FAM_NAME}</td>
						<td class='td_center' colspan="2">${item.FAM_BORNDATE}</td>
						<td class='td_center' colspan="3">${item.FAM_COMPANY_NAME}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		  <br>
		  <table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">资格证信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="100" height="30" colspan="2"><spring:message
						code="hr.viewCompetence.title.QUAL_NAME" /> <!--资格证名称--></th>
					<th width="100" height="30"><spring:message
						code="hr.viewCompetence.title.QUAL_LEVEL_NAME" /> <!--证件级别--></th>
					<th width="100" height="30" ><spring:message
						code="hr.viewCompetence.title.QUAL_GRADE_NAME" /> <!--职称--></th>
					<th width="100" height="30" colspan="2" ><spring:message
						code="hr.viewCompetence.title.QUAL_INSTITUTE" /> <!--发证处--></th>
					<th width="100" height="30" colspan="2"><spring:message
						code="hr.viewCompetence.title.DATE_OBTAINED" /> <!--取证日期--></th>
					<th width="100" height="30" style="display: none"><spring:message
						code="hr.viewCompetence.title.VALIDITY_DATE" /> <!--有效期--></th>
					<th width="100" height="30"><spring:message
						code="hr.viewPersonalInfo.title.jintiebiaozhun" /> <!--津贴标准--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${qualificationList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.QUAL_NO}">
						<td class='td_left' colspan="2">${item.QUAL_NAME}</td>
						<td class='td_center'>${item.QUAL_LEVEL_NAME}</td>
						<td class='td_center' >${item.QUAL_GRADE_NAME}</td>
						<td class='td_left' colspan="2">${item.QUAL_INSTITUTE}</td>
						<td class='td_center' colspan="2">${item.DATE_OBTAINED}</td>
						<td class='td_center' style="display: none">${item.VALIDITY_DATE }</td>
						<td class='td_center'>${item.QUAL_REMARK}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    <br>	
    	<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">紧急联系人</th>
   		 </tr>
			<thead>
				<tr>
					<th width="100" height="30" colspan="2"><spring:message
						code="hr.viewRelation.title.FAM_TYPE_NAME" /> <!--关系--></th>
					<th width="100" height="30" colspan="3"><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名--></th>
					<th width="100" height="30" colspan="5"><spring:message
						code="hr.viewRelation.title.FAM_PHONE" /> <!--联系电话--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${familyList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.FAMILY_NO}">
						<td class='td_center' height="30" colspan="2">${item.FAM_TYPE_NAME}</td>
						<td class='td_center' height="30" colspan="3">${item.FAM_NAME}</td>
						<td class="td_center" height="30" colspan="5">${item.FAM_PHONE}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
    <br>	
    	
    	<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">评价信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="10%" height="30"><spring:message
						code="hr.viewEvaluate.title.EV_PERIOD" /> <!--评价期间--></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewEvaluate.title.EV_ACHI" /> <!--업적  --></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewEvaluate.title.EV_ATTI" /> <!--태도 --></th>
					<th width="10%" height="30" > <spring:message
						code="hr.viewEvaluate.title.EV_ABIL" /> <!--능력--></th>
					<th width="10%" height="30" ><spring:message
						code="hr.viewEvaluate.title.EV_MARK" /> <!--评价分数--></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewEvaluate.title.EV_GRADE_NAME" /> <!--评价等级--></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewSuggestion.title.Suggestion" /> <!--意见--></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewFinalSequence.title.FinalSequence" /> <!--最终顺位--></th>
					<th width="10%" height="30"><spring:message
						code="hr.viewTotalPeople.title.TotalPeople" /> <!--总职级员人数--></th>
					<th width="10%" height="30"><spring:message
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
						<td class='td_center' >${item.EV_ABIL}</td>
						<td class='td_center' >${item.EV_MARK}</td>
						<td class='td_center'>${item.EV_GRADE_NAME}</td>
						<td class='td_left'>${item.SUGGESTION }</td>
						<td class='td_center'>${item.FINAL_SEQUENCE }</td>
						<td class='td_center'>${item.TOTAL_PEOPLE }</td>
						<td class='td_left'>${item.EV_REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		<br>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">外国语信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="100" colspan="2" height="30"><spring:message
						code="hr.viewLanguage.KAOSHIDATE" /> <!--考试日期--></th>
					<th width="100" colspan="3" height="30"><spring:message
						code="hr.viewCompetence.title.EXAM_NAME" /> <!--考试名--></th>
					<th width="100" height="30"><spring:message
						code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME" /> <!--等级--></th>
					<th width="100" height="30"><spring:message
						code="hr.viewCompetence.title.MARK" /> <!--分数--></th>
					<th width="100" height="30" colspan="3"><spring:message
						code="hr.viewPersonalInfo.title.jintiebiaozhun" /> <!--津贴标准--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${languageLevelList}" var="item" varStatus="i">

					<tr target="sid" rel="${item.LANGUAGE_NO}">
						<td class='td_center' colspan="2">${fn:substring(item.KAOSHIDATE,0, 10)}
						</td>
						<td class='td_center' colspan="3">${item.EXAM_NAME}</td>
						<td class='td_center'>${item.LANGUAGE_LEVEL_NAME}</td>
						<td class='td_center'>${item.MARK}</td>
						<td class='td_center' colspan="3">${item.ALLWANCE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">残疾证信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="80" height="30" colspan="2"><spring:message
						code="hr.viewDisabled.title.DISABLED_TYPE" /> <!--残疾类型--></th>
					<th width="80" height="30" colspan="2"><spring:message
						code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" /> <!--  残疾认定日期-->
					</th>
					<th width="80" height="30" colspan="2"><spring:message
						code="liang.hr.viewDisabled.title.DISABILITY_VALIDITY" /> <!--  有效期-->
					</th>
					<th width="80" height="30" colspan="4"><spring:message
						code="hr.viewDisabled.title.DISABLED_REMARK" /> <!--  备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${disabilityinfoList}" var="item" varStatus="i">

					<tr target="healthNo" rel="${item.T_ID}">
						<td class='td_left' colspan="2">${item.DISABILITY_TYPE_NAME}</td>
						<td class='td_center' colspan="2">${item.ADDDATE}</td>
						<td class='td_center' colspan="2">${item.DISABILITY_VALIDITY_NAME}</td>
						<td class='td_left' colspan="4">${item.REMARK}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
		<br>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">工会信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE" />
					<!--公会内部职责--></th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" /> <!--入会日期-->
					</th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" /> <!--退会日期-->
					</th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG" /> <!--参加 工会与否-->
					</th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG" /> <!--会费支付状态-->
					</th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE" /> <!--支付方式-->
					</th>
					<th width="80" height="30" colspan="4"><spring:message
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
						<td class='td_left' colspan="4">${item.REMARK}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		<br>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">派遣地信息</th>
   		 </tr>
			<thead>
				<tr>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.falingbianhao" /> <!--发令编号--></th>
					<th width="80" height="30" nowrap><spring:message
						code="display.emp.ben.transdate" /> <!--发令日期--></th>
					<th width="80" height="30" nowrap><spring:message
						code="display.emp.ben.effectivedate" /> <!--生效日期--></th>
					<th width="80" height="30" nowrap><spring:message
						code="hr.viewPersonalInfo.title.gongzuodiqu" /> <!--工作地区--></th>
					<th width="80" height="30" colspan="2"><spring:message
						code="display.emp.ben.or.sendtoadministrator" /> <!--派遣地--></th>
					<!--<th width="80" height="30" nowrap>
													<spring:message code="display.emp.ben.sendtype" />
													派遣类型
												</th>
												-->
					<th width="80" height="30" colspan="4"><spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" /> <!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${expInsideList}" var="item" varStatus="i">
					<tr target="healthNo" rel="${item.EXP_INSIDE_NO}">
						<td >${item.EXP_INSIDE_NO}</td>
						<td class='td_center' width="80">${item.CREATE_DATE}</td>
						<td class='td_center' width="80">${item.START_DATE}</td>
						<td class='td_center' width="80">${item.WORK_AREA}</td>
						<td class='td_center' width="80" colspan="2">${item.SENDADDRESS}</td>
						<td class='td_left' colspan="4">${item.REMARK}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		
	<br>	
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="user_table">
   		 <tr align="center" valign="middle">
      		 <th colspan="10">辅助信息</th>
   		 </tr>
			<thead>
				<tr>
					<c:forEach items="${assistList}" var="item" varStatus="i">
						<th width="80" height="30" colspan="10" nowrap>${item.TITLE}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${assistList}" var="item" varStatus="i">
						<td class='td_center' width="80" colspan="10">${item.CONTENT}</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
</body>
</html>