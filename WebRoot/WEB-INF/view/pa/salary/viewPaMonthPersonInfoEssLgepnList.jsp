<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//判断工资是否开放
	function getSalaryDispark() {

		var paMonth = $("#seach_year_ar0106", navTab.getCurrentPanel()).val()
				+ $("#seach_month_ar0106", navTab.getCurrentPanel()).val();
		$
				.ajax({
					cache : false,
					type : 'post',
					async : false,
					url : "/ess/infoView/getSalaryDispark?",
					data : 'paMonth=' + paMonth,
					dataType : "json",
					success : function(data) {
						if (data.no != 0) {
							$("#viewarmonthpersoninfo",
									navTab.getCurrentPanel()).submit();
						} else {
							alertMsg
									.error('<spring:message code="liang.pa.salary.title.salary_NotDispark"/>');//工资没有开放
						}
					}
				});
		return false;
	}
</script>
<div class="pageHeader">
	<form id="viewarmonthpersoninfo" name="viewarmonthpersoninfo"
		onsubmit="return navTabSearch(this);"
		action="/pa/salary/viewPaMonthPersonInfoEssLgepnList?pageNum=1&numPerPage=0"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 考勤月 --> <spring:message code='ar.excelexport.title.armonth' />:
					</td>

					<td><ait:date yearName="seach_year_ar0106"
							yearSelected="${year_ar0106}" monthName="seach_month_ar0106"
							monthSelected="${month_ar0106}" /></td>

					<td>
						<!-- 工号/姓名 --<spring:message code='public.title.empIdAndName'/>:</td>
				<td> 
					<!-- <input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>-->
						<input name="seach_PERSON_ID" type="hidden" id="seach_PERSON_ID"
						value="${supervisorId}" />
					</td>

					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="button" onclick="getSalaryDispark();">
												<!-- 查询 -->
												<spring:message code="button.search" />
											</button>
										</div>
									</div></li>

							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	
	<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="ess.viewpersonalpainfo.yuangongxinxi"/><!--人员基本信息-->
					</h1>
	</div>				
<table class="user_table" width="99%" border="1" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td class="td_title" style="text-align:center" width="10%">公司</td>
    <td style="text-align: center" width="18%">${PnItem.CPNY_ID }</td>
    <td class="td_title" style="text-align:center" width="10%">工资月</td>
    <td style="text-align: center" width="18%">${PnItem.PA_MONTH }</td>
    <td class="td_title" style="text-align:center" width="10%">部门名称</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.DEPT_NAME }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">社号</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.EMPID }</td>
    <td class="td_title" style="text-align:center" width="10%">姓名</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.LOCAL_NAME }</td>
    <td class="td_title" style="text-align:center" width="10%">入职日期</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.DATE_STARTED }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">人员类型</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.EMP_TYPE_NAME }</td>
    <td class="td_title" style="text-align:center" width="10%">职责</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.DUTY_NO }</td>
    <td class="td_title" style="text-align:center" width="10%">职位</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.POSITION_NO }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">职级</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.POST_GRADE_NO }</td>
    <td class="td_title" style="text-align:center" width="10%">工资级号</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PAY_GRADE }</td>
    <td class="td_title" style="text-align:center" width="10%">工资级号等级</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PAY_STEP }</td>
  </tr>
</table>

<div class="panel">
	<h1 style="text-align:center">
		<spring:message code="pa.salary.title.attendanceBasicInfo"/><!--考勤基本信息-->
    </h1>
</div>    
<table class="user_table" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
    <td class="td_title" style="text-align:center" width="10%">应出勤天数</td>
    <td style="text-align: center" width="18%">${PnItem.SCHEDULED_DAYS }</td>
    <td class="td_title" style="text-align:center" width="10%">实际出勤天数</td>
    <td style="text-align: center" width="18%">${PnItem.ACTUAL_WORK_DAYS }</td>
    <td class="td_title" style="text-align:center" width="10%">出差天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHUCHAI_DAY }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">平时加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PAY_PINGSHI_OT }</td>
    <td class="td_title" style="text-align:center" width="10%">周末加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PAY_ZM_OT }</td>
    <td class="td_title" style="text-align:center" width="10%">法定加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PAY_FADING_OT }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">事假时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.SHIJIA_HOURS }</td>
    <td class="td_title" style="text-align:center" width="10%">病假时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.BINGJIA_HOURS }</td>
    <td class="td_title" style="text-align:center" width="10%">旷工小时</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.KUANGGONG_HOURS }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">出差</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHUCHAI_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">调休时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.TIAOXIU_HOURS }</td>
    <td class="td_title" style="text-align:center" width="10%">迟到早退转旷工天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHIDAOZUITUI_ZHUAN }</td>
  </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">年假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.SHIYONG_NIANJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">夜班次数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.YEBAN_CISHU }</td>
    <td class="td_title" style="text-align:center" width="10%">迟到次数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHIDAO_CISHU }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">早退次数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ZAOTUI_CISHU }</td>
    <td class="td_title" style="text-align:center" width="10%">全年年假</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.TOT_VAC_CNT }</td>
    <td class="td_title" style="text-align:center" width="10%">已使用年假</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.DEL_VAC }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">婚假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.HUNJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">丧假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.SANGJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">产前检查假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHANQIAN_DAY }</td>
  </tr>
   <tr>
    <td class="td_title" style="text-align:center" width="10%">产假天数</td>
    <td style="text-align: center" width="18%">${PnItem.CHANJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">哺乳假天数</td>
    <td style="text-align: center" width="18%">${PnItem.BULUJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">流产假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.LIUCHANJIA_DAY }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">工伤假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.GONGSHANGJIA_DAY }</td>
    <td class="td_title" style="text-align:center" width="10%">外出时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.WAICHU_HOURS }</td>
    <td class="td_title" style="text-align:center" width="10%">培训时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PEIXUN_HOURS }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">追溯平时加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ZHUISU_PINGSHI_OT }</td>
    <td class="td_title" style="text-align:center" width="10%">追溯周末加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ZHUISU_ZHOUMO_OT }</td>
    <td class="td_title" style="text-align:center" width="10%">追溯法定加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ZHUISU_FADONG_OT }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">陪产假天数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.PEICHANJIA_DAYS }</td>
    <td class="td_title" style="text-align:center" width="10%">公假时数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.GONGJIA_HOURS }</td>
    <td class="td_title" style="text-align:center" width="10%">漏刷卡次数</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.LOUSHUAKA_CISHU }</td>
  </tr>
</table><div class="panel">
					<h1 style="text-align:center">
						工资项目列表
					</h1>
</div>					
<table class="user_table" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
    <td class="td_title" style="text-align:center" width="10%">基本工资</td>
    <td style="text-align: center" width="18%">${PnItem.BASE_SALARY }</td>
    <td class="td_title" style="text-align:center" width="10%">变动/成果工资</td>
    <td style="text-align: center" width="18%">${PnItem.CHANGE_SALARY }</td>
    <td class="td_title" style="text-align:center" width="10%">平时加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.WORK_OT_FEE }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">周末加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.WEEKEND_OT_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">法定加班</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.HOLIDAYS_OT_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">职责津贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.DUTY_FEE }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">特殊职责津贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_SPECIAL_DUTY_TESHU }</td>
    <td class="td_title" style="text-align:center" width="10%">岗位津贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.POST_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">技术岗位津贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.TECH_POST_MONEY }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">值班费</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_ONDUTY_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">夜班津贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.NIGHT_WORK_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">辛劳补贴</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHAOSHI_FEE }</td>
  </tr>
   <tr>
    <td class="td_title" style="text-align:center" width="10%">上月加班调整</td>
    <td style="text-align: center" width="18%">${PnItem.LAST_OT_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">奖金</td>
    <td style="text-align: center" width="18%">${PnItem.P_BONUS }</td>
    <td class="td_title" style="text-align:center" width="10%">考核等级</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_ASSESS_GRADE }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">年终奖/春节慰问金</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_END_YEAR_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">旷工扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ABSENTEEISM_REDUCE }</td>
    <td class="td_title" style="text-align:center" width="10%">病假扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.SICK_REDUCE }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">事假扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.LEAVE_REDUCE }</td>
    <td class="td_title" style="text-align:center" width="10%">迟到扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.CHIDAO_REDUCE }</td>
    <td class="td_title" style="text-align:center" width="10%">早退扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.ZAOTUI_REDUCE }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">未打卡扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.NO_CARD_REDUCE }</td>
    <td class="td_title" style="text-align:center" width="10%">迟到早退转旷工扣减</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.TURN_ABS_REDUCE }</td>
    <td class="td_title" style="text-align:center" width="10%">应发工资</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.GROSS_PAY }</td>
  </tr>
   <tr>
    <td class="td_title" style="text-align:center" width="10%">工会费(个人)</td>
    <td style="text-align: center" width="18%">${PnItem.SOCIETY_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">爱心基金</td>
    <td style="text-align: center" width="18%">${PnItem.LOVE_FEE }</td>
    <td class="td_title" style="text-align:center" width="10%">实发工资</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.NET_PAY }</td>
    </tr>
</table>
<c:if test="${count > 0}">
<div class="panel">
					<h1 style="text-align:center">
						工资输入项目
					</h1>
</div>					
<table class="user_table" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
  <c:if test="${PnItem.EESH_COREMAN_FEE ne '0'}">
  <tr>
    <td class="td_title" style="text-align:center" width="10%">EESH Coreman补贴</td>
    <td width="90%">
    <c:forEach items="${PnItem.EESHLIST}" var="item" varStatus="i">	
	    &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
    </td>
  </tr>
  </c:if>
   <c:if test="${PnItem.P_TRAFFIC_FSE ne '0'}">
    <tr>  
    <td class="td_title" style="text-align:center" width="10%">FSE交通费</td>
    <td width="90%">
    <c:forEach items="${PnItem.P_TRAFFIC_FSELIST}" var="item" varStatus="i">	
	    &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
    </td>
    </tr>
  </c:if>
   <c:if test="${PnItem.P_LOVE_FEE ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">爱心基金</td>
     <td width="90%">
    <c:forEach items="${PnItem.P_LOVE_FEELIST}" var="item" varStatus="i">	
	    &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_SUBSIDY ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">补贴</td>
     <td width="90%">
    <c:forEach items="${PnItem.P_SUBSIDYLIST}" var="item" varStatus="i">	
	    &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.WORK_AGE_MONEY ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">工龄补贴</td>
       <td width="90%">
    <c:forEach items="${PnItem.WORK_AGE_MONEYLIST}" var="item" varStatus="i">	
	    &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_ADJUST_FEE ne 0}">
    <tr>
    <td class="td_title" style="text-align: center" width="10%">工资调整(+)</td>
    <td width="90%">
       <c:forEach items="${PnItem.P_ADJUST_FEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_ADJUST_MINUS_FEE ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">工资调整(-)</td>
    <td width="90%">
       <c:forEach items="${PnItem.P_ADJUST_MINUS_FEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_FIXATION_OTFEE ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">固定加班费</td>
    <td width="90%">
       <c:forEach items="${PnItem.P_FIXATION_OTFEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.AR_MEMBER_MONEY ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">考勤员补贴</td>
     <td width="90%">
       <c:forEach items="${PnItem.AR_MEMBER_MONEYLIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
	</tr>
	</c:if>
   <c:if test="${PnItem.P_JIABAN_MONTY ne '0'}">
	<tr>
    <td class="td_title" style="text-align:center" width="10%">司机加班费</td>
    <td width="90%">
       <c:forEach items="${PnItem.P_JIABAN_MONTYLIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_LEFT_FEE ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">退社补偿金</td>
     <td width="90%">
       <c:forEach items="${PnItem.P_LEFT_FEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
   <c:if test="${PnItem.P_PD_FEE ne '0'}">
    <tr>
    <td class="td_title" style="text-align:center" width="10%">研发津贴</td>
     <td width="90%">
       <c:forEach items="${PnItem.P_PD_FEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
  </tr>
  </c:if>
   <c:if test="${PnItem.P_HOUSE_FEE ne '0'}">
   <tr>
    <td class="td_title" style="text-align:center" width="10%">住房补贴</td>
    <td width="90%">
       <c:forEach items="${PnItem.P_HOUSE_FEELIST}" var="item" varStatus="i">	
          &nbsp;&nbsp;<span class="td_title">${item.REMARK}:</span>${item.RETURN_VALUE }&nbsp;&nbsp;&nbsp;&nbsp;
       </c:forEach>
    </td>
    </tr>
    </c:if>
</table>
</c:if>
<div class="panel">
			
					<h1 style="text-align:center">
						保险福利及税金扣款项目
					</h1>
</div>					
<table class="user_table" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
    <td class="td_title" style="text-align:center" width="10%">个人所得税</td>
    <td style="text-align: center" width="18%">${PnItem.TAX }</td>
    <td class="td_title" style="text-align:center" width="10%">年终/慰问金所得税</td>
    <td style="text-align: center" width="18%">${PnItem.END_YEAR_TAX }</td>
    <td class="td_title" style="text-align:center" width="10%">保险补扣(个人)</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_IS_AJUST_PER }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">保险补扣(公司)</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.P_IS_AJUST_COR }</td>
    <td class="td_title" style="text-align:center" width="10%">公积金补扣个人(+)</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_FUND_AJUST_JIA_PER }</td>
    <td class="td_title" style="text-align:center" width="10%">公积金补扣公司(+)</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_FUND_AJUST_JIA_C  }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">个人失业保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_UNEMPLOY_PER }</td>
    <td class="td_title" style="text-align:center" width="10%">个人公积金</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_FUND_RER }</td>
    <td class="td_title" style="text-align:center" width="10%">公司医疗保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_MEDICAL_COR  }</td>
	</tr>
	<tr>
    <td class="td_title" style="text-align:center" width="10%">公司失业保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_UNEMPLOY_COR }</td>
    <td class="td_title" style="text-align:center" width="10%">公司工伤保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_INJURY_COR }</td>
    <td class="td_title" style="text-align:center" width="10%">个人养老保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_ENDOWMENT_PER }</td>
  </tr>
   <tr>
    <td class="td_title" style="text-align:center" width="10%">个人医疗保险</td>
    <td style="text-align: center" width="18%">${PnItem.IS_MEDICAL_PER }</td>
    <td class="td_title" style="text-align:center" width="10%">个人大额大病保险</td>
    <td style="text-align: center" width="18%">${PnItem.IS_SERIOUS_PER }</td>
    <td class="td_title" style="text-align:center" width="10%">公司生育保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_FERTILITY_COR }</td>
    </tr>
    <tr>
    <td class="td_title" style="text-align:center" width="10%">公司公积金</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_FUND_COR  }</td>
    <td class="td_title" style="text-align:center" width="10%">公司养老保险</td>
    <td class="td_type" style="text-align: center" width="18%">${PnItem.IS_ENDOWMENT_COR }</td>
    <td class="td_title" style="text-align:center" width="10%"></td>
    <td class="td_type" style="text-align: center" width="18%"></td>
    </tr>
</table>
</div>
