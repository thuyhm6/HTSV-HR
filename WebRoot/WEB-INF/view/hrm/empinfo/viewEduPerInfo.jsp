<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!-- jjy maskedinput -->
<script src="/resources/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
<script type="text/javascript">

<%--页面加载完更新采用路径--%>
	
function validateCallbackEditEduPerInfo(form, callback) {

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>',{
			okCall:function(){
				$.ajax({
		    		type: form.method || 'POST',
		    		url:$form.attr("action"),
		    		data:$form.serializeArray(),
		    		dataType:"json",
		    		cache: false,
		    		success: callback || DWZ.ajaxDone,
		    		error: DWZ.ajaxError
		    	});
		}});
     	return false;
}
$(document).ready(function() {
	var stateCd=$('#seach_STATENM_viewEduPerInfo').val();
	var cityCd0=$('#hCITYNM_viewEduPerInfo').val();
	var region0=$('#hREGION_viewEduPerInfo').val();
	changeState(stateCd,cityCd0);
	
	$('#seach_STATENM_viewEduPerInfo').live('change',function(){
		var st=$('#seach_STATENM_viewEduPerInfo').val();
		changeState(st,cityCd0);
		changeCity(null, null);
	});
	
	var cityCd=$('#seach_CITYNM_viewEduPerInfo select').val();
	changeCity(cityCd0, region0);
	
	$('#seach_CITYNM_viewEduPerInfo select').live('change',function(){
		var st=$('#seach_CITYNM_viewEduPerInfo select').val();
		changeCity(st, region0);
	});
});

function changeState(stateCd, cityCd){
	$.ajaxSettings.global = false;
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_CITYNM_viewEduPerInfo").html(data);
			$("#seach_CITYNM_viewEduPerInfo #seach_CITYNM").bind("change",function(){
					var workArea = $("#seach_CITYNM_viewEduPerInfo #seach_CITYNM").val();
					var workAreatext = $("#seach_CITYNM_viewEduPerInfo #seach_CITYNM option:selected").text();
					$("#WORK_AREA_viewEduPerInfo").val(workArea);
					$("#WORK_AREA_viewEduPerInfo_span").html(workAreatext);
				});
		}
	});
	$.ajaxSettings.global = true;
}

function changeCity(cityCd, region){
	$.ajaxSettings.global = false;
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=REGION&parentNo='+cityCd+"&selected="+region+'&name=seach_REGION',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_REGION_viewEduPerInfo").html(data);
			$("#seach_REGION_viewEduPerInfo #seach_REGION").bind("change",function(){
				var workArea = $("#seach_REGION_viewEduPerInfo #seach_REGION").val();
				var workAreatext = $("#seach_REGION_viewEduPerInfo #seach_REGION option:selected").text();
				$("#WORK_AREA_viewEduPerInfo").val(workArea);
				$("#WORK_AREA_viewEduPerInfo_span").html(workAreatext);
			});
		}
	});
	$.ajaxSettings.global = true;
}

function displayProduct(){
	var flag = $("#COMM_YN").val();
	if(flag == 'Y'){
		$("#PART_TIME_YN_NAME_PRODUCT").css("display","");
	} else {
		$("#PART_TIME_YN_NAME_PRODUCT").css("display","none");
	}
}
</script>
<div class="pageContent" style="position:absolute; height:576px; overflow-y:auto">
	<form method="post" action="/hrm/empinfo/editEduPerInfo" class="pageForm required-validate"
		onsubmit="return validateCallbackEditEduPerInfo(this,dialogAjaxDone);">
		<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
        <input TYPE="hidden" NAME="isEssSystem" VALUE="${isEssSystem}" >
	<div class="panel collapse" >
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION"/>
			<!--个人基础信息-->
		</h1>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
					<td class="td_title" >
						<spring:message code="hr.empinfo.cpny_id"/>
						<!--法人代码 -->
					</td>
					<td class="td_type" width="15%">
					${personInfo.CPNY_ID}
					</td>
					<td class="td_title" >
						<spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/>
						<!--社号  -->
					</td>
					<td class="td_type" width="15%">
							${personInfo.EMPID }
					</td>
					<td class="td_title">
						<spring:message code="hr.empinfo.english.name"/>
						<!--英文姓名 -->
					</td>
					<td class="td_type">
						${personInfo.CHINESE_PINYIN}
					</td>
					<td class="td_title">
						<spring:message
							code="hr.empinfo.chinese.name" />
						<!--中文姓名-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.LOCAL_NAME }
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
						<spring:message code="heranr.trans.POSITIONNO"/>
						<!--职位-->
					</td>
					<td class="td_type">
                        ${personInfo.POST_NAME}
					</td>
					<td class="td_title">
						<spring:message code="sys.affirm.title.duty"/>
						<!--职责 -->
					</td>
					<td class="td_type">
						${personInfo.DUTY_NAME }
					</td>
					<td class="td_title">
						<spring:message
							code="hr.assignment.zhiji" />
						<!--职级(级号)-->
					</td>
					<td class="td_type" width="15%">
                        ${personInfo.POST_GRADE_NAME}
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.assignment.group"/>
						<!--职群-->
					</td>
					<td class="td_type">
						${personInfo.JOB_FAM_NAME}
					</td>
					<td class="td_title">
						<spring:message
							code="hr.empinfo.term.type" />
						<!--任期类型-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.STATUS_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.empinfo.lge.join.date" />
						<!--LGE集团入职日期-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.JOIN_BLOC_DATE }
					</td>
					<td  class="td_title">
						<spring:message code="hr.empinfo.cpny.join.date"/>
						<!--法人入职日期-->
					</td>
					<td class="td_type" >
						${personInfo.DATE_STARTED }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.enpinfo.title.EMP.PROBATION_START_DATE"/>
						<!--试用期开始日期-->
					</td>
					<td class="td_type">
						${personInfo.PROB_STRT_DATE }
					</td>
					<td class="td_title">
							<spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE"/>
						<!--试用期结束日期-->
					</td>
					<td class="td_type">
						 ${personInfo.END_PROBATION_DATE }
					</td>
					<td class="td_title">
						<spring:message code="hr.enpinfo.title.EMP.PROBATION_PAY_RATE" />
						<!--试用期比例-->
					</td>
					<td class="td_type">
						${personInfo.PROB_PAY_RAT }
					</td>
					<td class="td_title">
						<spring:message code="pa.insurance.title.resignDate"/>
						<!--离职日期-->
					</td>
					<td class="td_type">
						${personInfo.DATE_LEFT } 
					</td>
				</tr>
			</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	 
	 
		<div style="clear: both;"></div>
			<div class="panel collapse">
			<h1>
					<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" />
					<!--个人信息--><!-- 개인기초정보 -->
			</h1>
			 <div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
											class="user_table">
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
													<!--国籍-->
												</td>
												<td class="td_type">
													${personInfo.NATIONALITY_CODE }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.SEX" />
													<!--性别-->
												</td>
												<td class="td_type">
													${personInfo.SEX_NAME }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
													<!--出生年月日-->
												</td>
												<td class="td_type">
													${personInfo.DOB }
												</td>
												<td class="td_title">
													学位
												</td>
												<td class="td_type">
													${personInfo.FINAL_DEGREE_NAME}
												</td>
											</tr>
												
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.merry.date" />
													<!--结婚纪念日-->
												</td>
												<td class="td_type">
													${personInfo.WEDD_DATE }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
													<!--身份证号-->
												</td>
												<td class="td_type">
													${personInfo.IDCARD_NO }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD"/>
													<!--ID卡号-->
												</td>
												<td class="td_type">
													${personInfo.ID_CARD_NO }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.email.chinese" />
													<!--邮箱-->
												</td>
												<td class="td_type">
													${personInfo.EMAIL }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewHire.title.HOME_PHONE" />
													<!-- 家庭电话 -->
												</td>
												<td class="td_type">
													${personInfo.HOME_PHONE}
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.work.phone" />
													<!-- 办公电话 -->
												</td>
												<td class="td_type">
													${personInfo.OFFICE_PHONE}
												</td>
												<td class="td_title">
													<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
													<!--手机号码-->
												</td>
												<td class="td_type">
													${personInfo.CELLPHONE }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
													<!--户口性质-->
												</td>
												<td class="td_type" width="15%">
													${personInfo.REG_TYPE_CODE}
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
													<!--户口所在地-->
												</td>
												<td class="td_type" colspan="7">
													${personInfo.REG_PLACE }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
													<!--现住址(邮编)-->
												</td>
												<td class="td_type" colspan="5">
													${personInfo.HOME_ADDRESS }
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewCondSql.title.YOUBIAN" />
													<!--邮编-->
												</td>
												<td class="td_type">
													${personInfo.POSTALCODE }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="rp.report.title.homeaddress" />
													<!--家庭住址-->
												</td>
												<td class="td_type" colspan="7">
													<input type="text" id="IDCARD_ADDR" name="IDCARD_ADDR"  size="60" value="${personInfo.IDCARD_ADDR }"/>
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.BORNPLACE_NAME" />
													<!--籍贯-->
												</td>
												<td class="td_type">
													<ait:SelectSyCodeByCpnyID name="BORNPLACE_CODE" parentNo="774" cnpyID="${defaultCpny}" limit="all" selected="${personInfo.BORNPLACE_CODE}"/>
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.NATION_NAME" />
													<!--民族 -->
												</td>
												<td class="td_type" width="15%">
													<ait:SelectSyCodeByCpnyID name="NATION_CODE" parentNo="210942" cnpyID="${defaultCpny}" selected="${personInfo.NATION_CODE }" limit="all" />
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.POLITY_NAME" />
													<!--政治面貌 -->
												</td>
												<td class="td_type" width="15%">
													<ait:SelectSyCodeByCpnyID name="POLITY_CODE" parentNo="210938" cnpyID="${defaultCpny}" selected="${personInfo.POLITY_CODE }" limit="all" />
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" />
													<!--是否共产党员-->
												</td>
												<td class="td_type">
													<ait:SelectSyCodeByCpnyID name="WHETHER_COMMUNIST" id="WHETHER_COMMUNIST" 
						                                    parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.WHETHER_COMMUNIST }" limit="all" />
												</td>
											</tr>
											
											
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.shengao" />
													<!--身高-->
												</td>
												<td class="td_type">
													<input type="text" id="HEIGHT" name="HEIGHT"  size="10" value="${personInfo.HEIGHT }"/>cm
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.tizhong" />
													<!--体重 -->
												</td>
												<td class="td_type" width="15%">
													<input type="text" id="WEIGHT" name="WEIGHT"  size="10" value="${personInfo.WEIGHT }"/>kg
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewHealth.title.BLOOD_TYPE_NAME" />
													<!--血型 -->
												</td>
												<td class="td_type" width="15%">
													<ait:SelectSyCodeByCpnyID name="BLOOD_TYPE" id="BLOOD_TYPE" 
						                                    parentNo="4573" cnpyID="${defaultCpny}" selected="${personInfo.BLOOD_TYPE }" limit="all" />
												</td>
												<td class="td_title">
													<spring:message code="hr.viewCondSql.titleSHIFOUCANJI" />
													<!--是否残疾-->
												</td>
												<td class="td_type">
													<ait:SelectSyCodeByCpnyID name="DISABILITY_YN" id="DISABILITY_YN" 
						                                    parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.DISABILITY_YN }" limit="all" />
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.zhaopinlaiyuan" />
													<!--招聘来源-->
												</td>
												<td class="td_type">
													<ait:SelectSyCodeByCpnyID name="RECRUITMENT_SOURCE_TYPE" id="RECRUITMENT_SOURCE_TYPE" 
						                                    parentNo="3306" cnpyID="${defaultCpny}" selected="${personInfo.RECRUITMENT_SOURCE_TYPE }" limit="all" />
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPromote.title.RESIGN_REASON" />
													<!--离职原因 -->
												</td>
												<td class="td_type" width="15%">
													<input type="text" id="LEAVE_REASON" name="LEAVE_REASON"  size="30" value="${personInfo.LEAVE_REASON }"/>
												</td>
												<td class="td_title">
													实际离职日期
												</td> 
												<td class="td_type">
													<input type="text" id="C_DATE_LEFT" name="C_DATE_LEFT" class="date" format="yyyy-MM-dd" yearstart="-100" yearend="10" 
																size="10" value="${personInfo.C_DATE_LEFT }"/>
													<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.jiangchengbeizhu" />
													<!--奖惩备注 -->
												</td>
												<td class="td_type" width="15%">
													<input type="text" id="REMARK" name="REMARK"  size="30" value="${personInfo.REMARK }"/>
												</td>
											</tr>
										</table>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="panel collapse">
			<h1>
				<spring:message code="hr.viewPersonalInfo.title.payinfo" />
									<!--工资信息 -->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="user_table">
										<tr>
											<td class="td_title">
												<spring:message code="hr.empinfo.pay.type.num" />
												<!--工资级号-->
											</td>
											<td class="td_type">
												${personInfo.PAY_GRADE }
											</td>
											<td class="td_title">
												<spring:message code="hr.empinfo.pay.type.num.leave"/>
												<!--工资级号等级-->
											</td>
											<td class="td_type">
												 ${personInfo.PAY_STEP }
											</td>
											<td class="td_title">
												<spring:message code="ess.viewpersonalpainfo.jibengongzi"/>
												<!--基本工资-->
											</td>
											<td class="td_type">
												${personInfo.BASE_PAY}
											</td>
											<td class="td_title">
												<spring:message code="hr.empinfo.pay.VARIABLE_SALARY_MONTH"/>
												<!--变动工资-->
											</td>
											<td class="td_type">
												${personInfo.VARB_PAY } 
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.nianxin" />
												<!--年薪-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.ANSAL}
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.nyinhangdaima" />
												<!--银行代码-->
											</td>
											<td class="td_type">
												${personInfo.BANK_ID }
											</td>
											<td class="td_title">
												<spring:message code="pa.wagebase.title.openAccountBanks"/>
												<!--开户行-->
											</td>
											<td class="td_type">
												${personInfo.CARD_NAME}
											</td>
											<td class="td_title">
												<spring:message code="rp.report.title.bankcardno"/>
												<!-- 银行账号 -->
											</td>
											<td class="td_type">
												${personInfo.CARD_NO}
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.feiyongnyinhangdaima"/>
												<!--费用银行代码-->
											</td>
											<td class="td_type">
												 ${personInfo.EXPNS_BANK_CD }
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.feiyongkaihuhang" />
												<!--费用开户行-->
											</td>
											<td class="td_type">
												${personInfo.EXPNS_BANK_BRNCH_NM }
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.feiyongyinhangzhanghao" />
												<!--费用银行账号-->
											</td>
											<td class="td_type" width="8%">
												${personInfo.EXPNS_BANK_ACCT_NO}
											</td>
											<td class="td_title">
											</td>
											<td class="td_type">
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.aixinjijinzhifufangshi" />
												<!--爱心基金支付方式-->
											</td>
											<td class="td_type"> 
												<ait:SelectSyCodeByCpnyID name="LOVE_FUND_PAYMENT_TYPE" id="LOVE_FUND_PAYMENT_TYPE" 
						                              parentNo="211654" cnpyID="${defaultCpny}" selected="${personInfo.LOVE_FUND_PAYMENT_TYPE }" limit="all" />
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.shifouzhifuaixinjijin" />
												<!--是否支付爱心基金-->
											</td>
											<td class="td_type" >
												<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_LOVE_FUND" id="IF_PAYMENT_LOVE_FUND" 
						                              parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.IF_PAYMENT_LOVE_FUND }" limit="all" />
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.fudanfangzubiaozhi" />
												<!--负担房租标志-->
											</td>
											<td class="td_type" width="15%">
												<select name="IF_PAYMENT_RENT">
													<option value="E" <c:if test="${personInfo.IF_PAYMENT_RENT eq 'E' }">selected</c:if>>个人</option>
													<option value="C" <c:if test="${personInfo.IF_PAYMENT_RENT eq 'C' }">selected</c:if>>公司</option>
												</select>
											</td>
											<td class=td_title><spring:message
													code="hr.viewPersonalInfo.title.fudanyiliaofeibiaozhi" />
												<!--负担医疗费标志--></td>
											<td class="td_type">
												<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_MEDICAL" id="IF_PAYMENT_MEDICAL" 
						                              parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.IF_PAYMENT_MEDICAL }" limit="all" />
											 </td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.fudanjiaoyufeibiaozhi" />
												<!--负担教育费标志-->
											</td>
											<td class="td_type">
												<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_EDUCATION" id="IF_PAYMENT_EDUCATION" 
						                              parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.IF_PAYMENT_MEDICAL }" limit="all" />
											</td>
											<td class="td_title">
											</td>
											<td class="td_type" >
											</td>
											<td class="td_title">
											</td>
											<td class="td_type" width="15%">
											</td>
											<td class=td_title></td>
											<td class="td_type">
											 </td>
										</tr>
									</table>
			</div>
		</div>
	
		<div style="clear: both;"></div>
		<div class="panel collapse">
								<h1>
									<spring:message
										code="hr.viewPersonalInfo.title.workinfo" />
									<!--工作信息 -->
								</h1>

								<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="user_table">
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.shifouFSE" />
												<!--是否FSE-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.FSE_YN}
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.gongzuoleixing.chr" />
												<!--工作类型(CHR)-->
											</td>
											<td class="td_type" width="8%">
		 										<ait:SelectSyCodeByCpnyID id="PROMTR_WORK_TP" name="PROMTR_WORK_TP" parentNo="211554" selected="${personInfo.PROMTR_WORK_TP}" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.banhao"/>
												<!--班号-->
											</td>
											<td class="td_type">
												${personInfo.SHIFT_NO}
											</td>
											<td class="td_title" rowspan="2">
												<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu" />
												<!--工作地区-->
											</td>
											<td class="td_type">
		 										<input type="hidden" id="WORK_AREA_viewEduPerInfo" name="WORK_AREA" value="${personInfo.WORK_AREA}"/>
		 										<span id="WORK_AREA_viewEduPerInfo_span">${personInfo.WORK_AREA_NAME}</span>
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.renyuanleixing.chr" />
												<!--人员类型(CHR)-->
											</td>
											<td class="td_type" >
												<ait:SelectSyCodeByCpnyID id="EMP_TYPE_CODE" name="EMP_TYPE_CODE" parentNo="1368" selected="${personInfo.EMP_TYPE_CODE}" cnpyID="${defaultCpny}" limit="all" disabled="true"/>
												<input type="hidden" id="EMP_TYPE_CODE" name="EMP_TYPE_CODE" value="${personInfo.EMP_TYPE_CODE}" > 
											</td>
											<td class="td_title">
												人员类型生效日期
											</td>
											<td class="td_type">
												<input type="text" id="EMP_TYPE_START_DATE" name="EMP_TYPE_START_DATE"
															size="10" value="${personInfo.EMP_TYPE_START_DATE }" readonly="readonly"/>
												<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
											</td>
											<td class=td_title>福利地区(公积金)</td>
											<td class="td_type">
		 										<ait:SelectSyCodeByCpnyID id="INSRAREA_ID_INS" name="INSRAREA_ID_INS" parentNo="216736" selected="${personInfo.INSRAREA_ID_INS}" cnpyID="${defaultCpny}" limit="all" orderType="PY"/>
											 </td>
											<td class="td_type">
		 										<ait:SelectState id="seach_STATENM_viewEduPerInfo" name="seach_STATENM_viewEduPerInfo" type="STATE" parentNo="" selected="${STATENM}" limit="all" orderType="PY"/>
		 										<span id="seach_CITYNM_viewEduPerInfo"><select></select></span>
		 										<span id="seach_REGION_viewEduPerInfo"><select></select></span>
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.laodongshoucebianhao" />
											<!--劳动手册编号-->
											</td>
											<td class="td_type">
													<input type="text" id="MANUAL_NUM" name="MANUAL_NUM"  size="20" value="${personInfo.MANUAL_NUM }"/>
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.sheneigongling" />
												<!--社内工龄-->
											</td>
											<td class="td_type" >
												<c:if test="${personInfo.INNER_WORK_YEAR ne 0 and personInfo.INNER_WORK_YEAR ne null }">${personInfo.INNER_WORK_YEAR}<spring:message code="liang.hr.viewWorkInfo.title.YEAR" /></c:if>
												<c:if test="${personInfo.INNER_WORK_MONTH ne 0 and personInfo.INNER_WORK_MONTH ne null}">${personInfo.INNER_WORK_MONTH}<spring:message code="hr.viewPersonalInfo.title.WORKINFO_MONTH" /></c:if>
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.shewaigongling" />
												<!--社外工龄-->
											</td>
											<td class="td_type" width="15%">
													<input type="text" id="OUTER_WORK_YEAR" name="OUTER_WORK_YEAR"  size="20" value="${personInfo.OUTER_WORK_YEAR }"/>
													<spring:message
													code="hr.viewPersonalInfo.title.tishixinxi.zaiwaigongling" />
											</td>
											<td class=td_title>福利地区(保险)</td>
											<td class="td_type">
		 										<ait:SelectSyCodeByCpnyID id="INSRAREA_ID" name="INSRAREA_ID" parentNo="216736" selected="${personInfo.INSRAREA_ID}" cnpyID="${defaultCpny}" limit="all" orderType="PY"/>
											 </td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.baoxiangongsi" />
											<!--保险公司-->
											</td>
											<td class="td_type">
												<select name="INSURANCE_COMPANY">
													<option value="">请选择</option>
													<c:forEach items="${corpList}" var="item" varStatus="i">
														<option value="${item.CP_NO }" <c:if test="${item.CP_NO eq personInfo.INSURANCE_COMPANY }">selected</c:if>>${item.CP_NAME }</option>
													</c:forEach>
												</select>
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" />
												<!--保险类型-->
											</td>
											<td class="td_type" >
		 										<ait:SelectSyCodeByCpnyID id="INSURANCE_TYPE_CODE" name="INSURANCE_TYPE_CODE" parentNo="483" selected="${personInfo.INSURANCE_TYPE_CODE}" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.nianjiajizhun" />
												<!--年假基准-->
											</td>
											<td class="td_type" width="15%">
												<input type="text" id="YY_VAC_STD_DATE" name="YY_VAC_STD_DATE" class="date" format="yyyy-MM-dd" yearstart="-100" yearend="10" 
															size="10" value="${personInfo.YY_VAC_STD_DATE }"/>
												<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
											</td>
											<td class=td_title><spring:message
													code="hr.viewPersonalInfo.title.fengongsi.daqu" />
												<!--分公司(大区)--></td>
											<td class="td_type">
												${personInfo.PAY_AREA_CD_NAME }
											 </td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.chanpin" />
											<!--产品-->
											</td>
											<td class="td_type">
												<ait:ComboSyCodeDescByCpnyID id="PROD_TP"
														name="PROD_TP" parentNo="211424"
														selected="${personInfo.PROD_TP }" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu" />
												<!--促销员所属-->
											</td>
											<td class="td_type" >
		 										<ait:SelectSyCodeByCpnyID id="PROMTR_TP" name="PROMTR_TP" parentNo="211837" selected="${personInfo.PROMTR_TP }" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.xingjijibie" />
												<!--星级级别-->
											</td>
											<td class="td_type" width="15%">
		 										<ait:SelectSyCodeByCpnyID id="STAR_TP" name="STAR_TP" parentNo="215954" selected="${personInfo.STAR_TP }" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title"><spring:message
													code="hr.viewPersonalInfo.title.shifoujianmai" />
												<!--是否兼卖--></td>
											<td class="td_type">
												<ait:SelectSyCodeByCpnyID name="PART_TIME_YN" id="PART_TIME_YN" 
						                              parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.PART_TIME_YN }" limit="all" />
											 </td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.shifougongjiancuxiaoyuan" />
											<!--是否共建促销员-->
											</td>
											<td class="td_type">
												<ait:SelectSyCodeByCpnyID name="COMM_YN" id="COMM_YN" 
						                              parentNo="123224" cnpyID="${defaultCpny}" selected="${personInfo.COMM_YN }" limit="all" onChangeName="displayProduct()"/>
											</td>
											<td class="td_title">
												评价类型
											</td>
											<td class="td_type" >
											<ait:ComboSyCodeDescByCpnyID id="EVS_TYPE_CODE"
													name="EVS_TYPE_CODE" parentNo="14895"
													selected="${personInfo.EVS_TYPE_CODE }" cnpyID="${defaultCpny}" limit="all"/>
											</td>
											<td class="td_title">兼卖产品</td>
											<td class="td_type" colspan="3">
												<span id="PART_TIME_YN_NAME_PRODUCT">
												<c:if test="${personInfo.PART_TIME_YN_NAME eq 'Y'}">
													<c:forEach items="${productList}" var="item" varStatus="i">
														<c:if test="${i.count eq 1}">${item.CONTENT }</c:if>
														<c:if test="${i.count ne 1}">,${item.CONTENT }</c:if>
													</c:forEach>
												</c:if>
												</span>
											</td>
										</tr>
									</table>
								</div>
							</div>
		<!-- ----------------------------------按钮------------------------------------------------ -->
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
	</div>
    </form>
	</div>
