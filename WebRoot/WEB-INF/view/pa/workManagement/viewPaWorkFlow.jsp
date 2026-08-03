<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function executeProcess() {
		var processType="";
		var flag=false;
		var arLockIsNotLock = false ;
		$("input[name='processType']").each(function(){
			if($(this).attr("checked") == "checked"){
				processType = $(this).val();
				flag = true;
				if(processType == 'arMonthCal'&& '${paWorkInfo.AR_LOCK_FLAG}'==0)
					arLockIsNotLock = true ;
			}
		});
		//提示：当月考勤还未锁定
		if(arLockIsNotLock){
			alert('<spring:message code="pa.viewPaWorkFlow.TISHIDANGYUEKAOQINHAIWEISUODING.C" />');
		}
		//请先选择要执行的流程
		if(flag == false){
			alertMsg.error("<spring:message code='org.title.SELECT_EXECUTE' />");
			return false;
		}
		//确定要执行吗
		alertMsg.confirm("<spring:message code='org.title.IS_SELECT_EXECUTE' />？",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/pa/workManagement/execPaWorkFlow',
	  				//data:{type:processType,PAY_SCHEDULE_NO:$("#PAY_SCHEDULE_NO").val()},
	  				data:$('#searchViewPaWorkFlowForm').serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: fillErrorMessage,
	  				error: DWZ.ajaxError
	  			});
	  	}
	  	});
		return false;
	}
	//确定要关闭考勤申请吗？':'确定要解除考勤申请的关闭吗？
	function exeArLock(flag){
		var msgTxt = flag==1?'<spring:message code="pa.viewPaWorkFlow.QUEDINGYAOGUANBIKAOQINSHENQINGMA.C" />？':'<spring:message code="pa.viewPaWorkFlow.QUEDINGYAOJIECHUKAOQINSHENQINGDEGUANBIMA.C" />？';
		if(flag == 1)
			$("#arLockFlag", navTab.getCurrentPanel()).val('arLockYes');
		else
			$("#arLockFlag", navTab.getCurrentPanel()).val('arLockNo');
		$("input[name='processType']").each(function(){
			if($(this).attr("checked") == "checked"){
				$(this).attr("checked",false);
			}
		});
		
		alertMsg.confirm(msgTxt,
	  		  	{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/pa/workManagement/execPaWorkFlow',
	  				//data:{type:processType,PAY_SCHEDULE_NO:$("#PAY_SCHEDULE_NO").val()},
	  				data:$('#searchViewPaWorkFlowForm').serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: fillErrorMessage,
	  				error: DWZ.ajaxError
	  			});
	  	}
	  	});
		return false;
	}
	function fillErrorMessage(json){
		//DWZ.ajaxDone(json);
		
		if (json.statusCode == DWZ.statusCode.ok){
			DWZ.ajaxDone(json);
			uploadfy_destory();
			if (json.formId){
				navTabSearch($("#" + json.formId,json.navTabId));
			}  else { //重新载入当前navTab页面
				navTabPageBreak({}, json.rel);
			}
			
			if ("closeCurrent" == json.callbackType) {
				setTimeout(function(){navTab.closeCurrentTab();}, 100);
			} else if ("forward" == json.callbackType) {
				navTab.reload(json.forwardUrl);
			}
		}else{
			alert(json.message);
		}
	}
</script>
<div class="pageHeader">
	<form id="searchViewPaWorkFlowForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaWorkFlow" method="post" >
		<input type="hidden" id="arLockFlag" name="arLockFlag" value="">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO" onchange="$('#searchViewPaWorkFlowForm').submit();">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO }" >${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive" onclick="executeProcess()" href="#">
							<span><!--工作执行--><spring:message code="org.title.WORK_EXECUTE" /></span>
						</a>
					</li>
					<c:choose>
						<c:when test="${paWorkInfo.AR_LOCK_FLAG == 0 }">
							<li>
								<a class="buttonActive" onclick="exeArLock(1)" href="#">
									<span><!--考勤锁定--><spring:message code="pa.viewPaWorkFlow.KAOQINSUODING.C" /></span>
								</a>
							</li>
						</c:when>
						<c:when test="${paWorkInfo.AR_LOCK_FLAG == 1 }">
							<li>
								<a class="buttonActive" onclick="exeArLock(0)" href="#">
									<span><!--考勤解锁--><spring:message code="pa.viewPaWorkFlow.KAOQINJIESUO.C" /></span>
								</a>
							</li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
		<tr>
			<td class="td_title"  width="10%"><!--工资期间--><spring:message code="pa.detailYearCountInfoLeft.GONGZIQIJIAN.b" /></td>
			<td class="td_type" width="25%" style="text-align: center">${paWorkInfo.HR_START_DATE} ～ ${paWorkInfo.HR_END_DATE}
			<%-- <input type="text"  name="HR_START_DATE" 
				format="yyyy-MM-dd" readonly="true" value="${paWorkInfo.HR_START_DATE}"
				size="20" />
					-
				<input type="text" name="HR_END_DATE" 
				format="yyyy-MM-dd" readonly="true" value="${paWorkInfo.HR_END_DATE}"
				size="20" /> --%>
			</td>
			<td class="td_title"  width="10%"><!--考勤期间--><spring:message code="pa.viewPaWorkFlow.KAOQINQIJIAN.C" /></td>
			<td class="td_type" width="25%" style="text-align: center">${paWorkInfo.AR_START_DATE} ～ ${paWorkInfo.AR_END_DATE}
			<%-- <input type="text"  name="AR_START_DATE" 
				format="yyyy-MM-dd" readonly="true" value="${paWorkInfo.AR_START_DATE}"
				size="20" />
					-
				<input type="text" name="AR_END_DATE" 
				format="yyyy-MM-dd" readonly="true" value="${paWorkInfo.AR_END_DATE}"
				size="20" /> --%>
			</td>
			<td class="td_title"  width="10%"><!--工资计算人数--><spring:message code="pa.viewPaWorkFlow.GONGZIJISUANRENSHU.C" /></td>
			<td class="td_type" width="20%" style="text-align: center">${PAY_OBJ_NUM}<%-- <input type="text"  name="OBJ_NUM" readonly="true" value="${PAY_OBJ_NUM}"/> --%>
			</td>
		</tr>
	</table>
	<div class="pageHeader">
	<div class="searchBar" style="height:560px;line-height:360px;overflow:auto;overflow-x:hidden;background:url('/resources/images/pa_work_flow.jpg') no-repeat;">
		<div style="width:220px;margin-left:44px;margin-top:140px;">
			<font  ><b>*&nbsp;&nbsp;<!--生成工资对象--><spring:message code="pa.viewPaWorkFlow.SHENGCHENGGONGZIDUIXIANG.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:180px;margin-left:58px;margin-top:38px;">
			<input type="checkbox" id="createPaObj_pa0813" name="processType" value="createPaObj" style="vertical-align:middle ;"/>&nbsp;
			<c:if test="${paWorkInfo.OBJ_CREATE_FLAG eq '1'}">
				<span onclick="$('#createPaObj_pa0813').attr('checked')=='checked'?$('#createPaObj_pa0813').removeAttr('checked'):$('#createPaObj_pa0813').attr('checked','checked');" ><font color="#0A258F" style="vertical-align:middle ;cursor: pointer;" ><!--对象者生成--><spring:message code="pa.viewPaWorkFlow.DUIXIANGZHESHENGCHENG.C" /></font></span>
			</c:if>
			<c:if test="${paWorkInfo.OBJ_CREATE_FLAG ne '1'}">
				<span onclick="$('#createPaObj_pa0813').attr('checked')=='checked'?$('#createPaObj_pa0813').removeAttr('checked'):$('#createPaObj_pa0813').attr('checked','checked');"><font color="black" style="vertical-align:middle ;cursor: pointer;" ><!--对象者生成--><spring:message code="pa.viewPaWorkFlow.DUIXIANGZHESHENGCHENG.C" /></font></span>
			</c:if>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/pa/workManagement/viewPaWorkFlowOperationRecordList?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO }&FLOW_STEP=1" target="dialog" mask="true" 
							width="600" title="<spring:message code='pa.viewPaWorkFlow.GONGZILIUCHENGCAOZUOJILU.C' />"
							height="300" style="text-decoration:none ;">
							<img title="<spring:message code='pa.viewPaWorkFlow.CHAKANCAOZUOJILU.C' />" style="vertical-align:middle ;"src="/resources/css/dwzUI/themes/hub/images/main/icon11.gif">
						</a>
		</div>
		<div style="width:220px;margin-left:34px;margin-top:127px;">
			<font  ><b>*&nbsp;&nbsp;<!--生成月考勤记录--><spring:message code="pa.viewPaWorkFlow.SHENGCHENGYUEKAOQINJILU.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:200px;margin-left:36px;margin-top:40px;">
			<input type="checkbox" id="arMonthCal_pa0813" name="processType" value="arMonthCal" style="vertical-align:middle ;"/>&nbsp;
			<c:if test="${paWorkInfo.AR_MONTH_CAL_FLAG eq '1'}">
				<span onclick="$('#arMonthCal_pa0813').attr('checked')=='checked'?$('#arMonthCal_pa0813').removeAttr('checked'):$('#arMonthCal_pa0813').attr('checked','checked');" ><font color="#0A258F" style="vertical-align:middle ;cursor: pointer;"><!--月考勤汇总--><spring:message code="ar.viewararmonthcalculate.title.yuekaoqinhuizong" /></font></span>
			</c:if>
			<c:if test="${paWorkInfo.AR_MONTH_CAL_FLAG ne '1'}">
				<span onclick="$('#arMonthCal_pa0813').attr('checked')=='checked'?$('#arMonthCal_pa0813').removeAttr('checked'):$('#arMonthCal_pa0813').attr('checked','checked');" ><font color="black" style="vertical-align:middle ;cursor: pointer;"><!--月考勤汇总--><spring:message code="ar.viewararmonthcalculate.title.yuekaoqinhuizong" /></font></span>
			</c:if>
			
			<a href="/pa/workManagement/viewPaWorkFlowOperationRecordList?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO }&FLOW_STEP=2" target="dialog" mask="true" 
							width="600" title="<spring:message code='pa.viewPaWorkFlow.GONGZILIUCHENGCAOZUOJILU.C' />"
							height="300" style="text-decoration:none ;">
							<img title="<spring:message code='pa.viewPaWorkFlow.CHAKANCAOZUOJILU.C' />" style="vertical-align:middle ;"src="/resources/css/dwzUI/themes/hub/images/main/icon11.gif">
						</a>
		</div>
		<div style="width:220px;margin-left:323px;margin-top:-328px;">
			<font  ><b>*&nbsp;&nbsp;<!--生成月工资记录--><spring:message code="pa.viewPaWorkFlow.SHENGCHENGYUEGONGZIJILU.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:180px;margin-left:338px;margin-top:40px;">
			<input type="checkbox" id="paMonthCal_pa0813" name="processType" value="paMonthCal" style="vertical-align:middle ;"/>&nbsp;
			<c:if test="${paWorkInfo.PA_CAL_FLAG eq '1'}">
				<span onclick="$('#paMonthCal_pa0813').attr('checked')=='checked'?$('#paMonthCal_pa0813').removeAttr('checked'):$('#paMonthCal_pa0813').attr('checked','checked');" ><font color="#0A258F" style="vertical-align:middle ;cursor: pointer;"><!--工资计算--><spring:message code="pa.salary.title.salarycalculation" /></font></span>
			</c:if>
			<c:if test="${paWorkInfo.PA_CAL_FLAG ne '1'}">
				<span onclick="$('#paMonthCal_pa0813').attr('checked')=='checked'?$('#paMonthCal_pa0813').removeAttr('checked'):$('#paMonthCal_pa0813').attr('checked','checked');" ><font color="black" style="vertical-align:middle ;cursor: pointer;"><!--工资计算--><spring:message code="pa.salary.title.salarycalculation" /></font></span>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/pa/workManagement/viewPaWorkFlowOperationRecordList?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO }&FLOW_STEP=3" target="dialog" mask="true" 
							width="600" title="<spring:message code='pa.viewPaWorkFlow.GONGZILIUCHENGCAOZUOJILU.C' />"
							height="300" style="text-decoration:none ;">
							<img title="<spring:message code='pa.viewPaWorkFlow.CHAKANCAOZUOJILU.C' />" style="vertical-align:middle ;"src="/resources/css/dwzUI/themes/hub/images/main/icon11.gif">
						</a>
		</div>
		<div style="width:160px;margin-left:332px;margin-top:120px;">
			<font  ><b>*&nbsp;&nbsp;<!--维护工资手工调整项目--><spring:message code="pa.viewPaWorkFlow.WEIHUGONGZISHOUGONGTIAOZHENGXIANGMU.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:170px;margin-left:332px;margin-top:35px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=2','pageNum=1&menuNo=14013764&navTabId=pa0512','pa0512','<spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" />');">
				<font  color="white"><!--支付调整项目--><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></font></a>
			</div>
		</div>
		<div style="width:170px;margin-left:332px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=4','pageNum=1&menuNo=2568&navTabId=pa0231','pa0231','<spring:message code="pa.viewPaMain.KOUCHUTIAOZHENGXIANGMU.C" />');">
				<font  color="white"><!--扣除调整项目--><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></font></a>
			</div>
		</div>
		<div style="width:170px;margin-left:332px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/viewPaEmpAccount','pageNum=1&menuNo=2561&navTabId=pa0818&firstView=Y','pa0818','<spring:message code="hr.viewCondSql.title.ZHANGHUXINXI" />');">
				<font  color="white"><!--账户信息--><spring:message code="hr.viewCondSql.title.ZHANGHUXINXI" /></font></a>
			</div>
		</div>
		<div style="width:120px;margin-left:624px;margin-top:-335px;">
			<font ><b>*&nbsp;&nbsp;<!--工资核查--><spring:message code="pa.viewPaWorkFlow.GONGZIHECHA.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:33px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/monthPersonCountInfoList','pageNum=1&menuNo=14013768&navTabId=pa1011','pa1011','<spring:message code="pa.viewPaWorkFlow.HUANBIRENYUANXIANZHENG.C" />');">
				<font  color="white"><!--环比人员现状--><spring:message code="pa.viewPaWorkFlow.HUANBIRENYUANXIANZHENG.C" /></font></a>
				<!-- <font  >环比人员现状</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:21px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/paView/viewPaMonthChain','pageNum=1&menuNo=14013769&navTabId=pa1012','pa1012','<spring:message code="pa.viewPaResult.YUEHUANBIXIANGMUMINGXI.b" />');">
				<font  color="white"><!--环比项目明细--><spring:message code="pa.viewPaWorkFlow.HUANBIXIANGMUMINGXI.C" /></font></a>
				<!-- <font  >环比项目明细</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:21px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/viewVerificationList','pageNum=1&menuNo=14013770&navTabId=pa1013','pa1013','<spring:message code="pa.viewPaWorkFlow.FALINGHEDUI.C" />');">
				<font  color="white"><!--发令核对--><spring:message code="pa.viewPaWorkFlow.FALINGHEDUI.C" /></font></a> 
				<!-- <font  >发令核对</font>-->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailPersonCountInfo','pageNum=1&menuNo=14013771&navTabId=pa1014','pa1014','<spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" />');">
				<font  color="white"><!--工资详细明细--><spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" /></font></a>
			</div>
			<!--<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/dayPersonCountInfoList','pageNum=1&menuNo=14013772&navTabId=pa1015','pa1015','<spring:message code="pa.viewPaWorkFlow.RIBIEXIANGMUHEDUI.C" />');">
				<font  color="white">日别项目核对<spring:message code="pa.viewPaWorkFlow.RIBIEXIANGMUHEDUI.C" /></font></a>
				 <font  >日别项目核对</font> 
			</div>-->
		</div>
		<div style="width:170px;margin-left:608px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailPersonCountInfoLeft','pageNum=1&menuNo=14013773&navTabId=pa1016','pa1016','<spring:message code="pa.detailPersonCountInfoLeft.GERENBIEHEDUI.b" />');">
				<font  color="white"><!--个人别核对--><spring:message code="pa.detailPersonCountInfoLeft.GERENBIEHEDUI.b" /></font></a>
				<!-- <font  >个人别核对</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailItemCountInfo','pageNum=1&menuNo=14013774&navTabId=pa1017','pa1017','<spring:message code="pa.viewPaResult.XIANGMUBIEHEDUI.b" />');">
				<font  color="white"><!--项目别核对--><spring:message code="pa.viewPaResult.XIANGMUBIEHEDUI.b" /></font></a>
				<!-- <font  >项目别核对</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailItemDifCountInfo','pageNum=1&menuNo=14013775&navTabId=pa1018','pa1018','<spring:message code="pa.viewPaWorkFlow.BIANDONGXIANGHEDUI.C" />');">
				<font  color="white"><!--变动项核对--><spring:message code="pa.viewPaWorkFlow.BIANDONGXIANGHEDUI.C" /></font></a>
				<!-- <font  >变动项核对</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:608px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/viewResultConfirmList','pageNum=1&menuNo=14013776&navTabId=pa1019','pa1019','<spring:message code="pa.viewPaResult.JIEGUOQUEREN.b" />');">
				<font  color="white"><!--结果核对--><spring:message code="pa.viewPaWorkFlow.JIEGUOHEDUI.C" /></font></a>
				<!-- <font  >结果核对</font> -->
			</div>
		</div>
		<div style="width:220px;margin-left:856px;margin-top:-340px;">
			<font  ><b>*&nbsp;&nbsp;<!--锁定工资 防止误操作--><spring:message code="pa.viewPaWorkFlow.SUODINGGONGZIFANGZHIWUCAOZUO.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:180px;margin-left:890px;margin-top:39px;">
			<!-- <input type="checkbox" id="paConfirm_pa0813" name="processType" value="paConfirm" style="vertical-align:middle ;"/>&nbsp; -->
			<c:if test="${paWorkInfo.PA_CONFIRM_FLAG eq '1'}">
				<input type="checkbox" id="paConfirm_pa0813" name="processType" value="paUnConfirm" style="vertical-align:middle ;"/>&nbsp;
				<span onclick="$('#paConfirm_pa0813').attr('checked')=='checked'?$('#paConfirm_pa0813').removeAttr('checked'):$('#paConfirm_pa0813').attr('checked','checked');" ><font color="#0A258F" style="vertical-align:middle ;cursor: pointer;"><!--取消锁定--><spring:message code="pa.viewPaWorkFlow.QUXIAOSUODING.C" /></font></span>
			</c:if>
			<c:if test="${paWorkInfo.PA_CONFIRM_FLAG ne '1'}">
				<input type="checkbox" id="paConfirm_pa0813" name="processType" value="paConfirm" style="vertical-align:middle ;"/>&nbsp;
				<span onclick="$('#paConfirm_pa0813').attr('checked')=='checked'?$('#paConfirm_pa0813').removeAttr('checked'):$('#paConfirm_pa0813').attr('checked','checked');" ><font color="black" style="vertical-align:middle ;cursor: pointer;"><!--工资锁定--><spring:message code="ar.viewarprogress.title.gongzisuoding" /></font></span>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/pa/workManagement/viewPaWorkFlowOperationRecordList?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO }&FLOW_STEP=4,6" target="dialog" mask="true" 
							width="600" title="<spring:message code='pa.viewPaWorkFlow.GONGZILIUCHENGCAOZUOJILU.C' />"
							height="300" style="text-decoration:none ;">
							<img title="<spring:message code='pa.viewPaWorkFlow.CHAKANCAOZUOJILU.C' />" style="vertical-align:middle ;"src="/resources/css/dwzUI/themes/hub/images/main/icon11.gif">
						</a>
		</div>
		<div style="width:220px;margin-left:890px;margin-top:119px;">
			<font  ><b>*&nbsp;&nbsp;<!--工资报表查看--><spring:message code="pa.viewPaWorkFlow.GONGZIBAOBIAOCHAKAN.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:33px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailmonthCountInfoLeft','pageNum=1&menuNo=14013778&navTabId=pa0132','pa0132','<spring:message code="pa.viewPaMain.YUEGONGZIMINGXI.C" />');">
				<font  color="white"><!--查看月工资--><spring:message code="pa.viewPaWorkFlow.CHAKANYUEGONGZI.C" /></font></a>
				<!-- <font  >查看月工资</font> -->
			</div>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/detailYearCountInfoLeft','pageNum=1&menuNo=14013779&navTabId=pa0133','pa0133','<spring:message code="pa.viewPaMain.NIANGONGZIMINGXI.C" />');">
				<font  color="white"><!--查看年工资--><spring:message code="pa.viewPaWorkFlow.CHAKANNIANGONGZI.C" /></font></a>
				<!-- <font  >查看年工资</font> -->
			</div>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/payStub','pageNum=1&menuNo=14013777&navTabId=pa0131','pa0131','<spring:message code="pa.viewPaMain.GONGZITIAO.C" />');">
				<font  color="white"><!--查看工资条--><spring:message code="pa.viewPaWorkFlow.CHAKANGONGZITIAO.C" /></font></a>
				<!-- <font  >查看工资条</font> -->
			</div>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/viewPaResultList','pageNum=1&menuNo=14013780&navTabId=pa0134','pa0134','<spring:message code="pa.viewPaMain.ZHIFUHEJIGEREN.C" />');">
				<font  color="white"><!--支付合计(个人)--><spring:message code="pa.viewPaWorkFlow.ZHIFUHEJIGEREN.C" /></font></a>
				<!-- <font  >支付合计(个人)</font> -->
			</div>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/pa/workManagement/viewDeptPaResultList','pageNum=1&menuNo=14013781&navTabId=pa0135','pa0135','<spring:message code="pa.viewPaMain.ZHIFUHEJIBUMEN.C" />');">
				<font  color="white"><!--支付合计(部门)--><spring:message code="pa.viewPaWorkFlow.ZHIFUHEJIBUMEN.C" /></font></a>
				<!-- <font  >支付合计(部门)</font> -->
			</div>
		</div>
		<div style="width:140px;margin-left:886px;margin-top:22px;">
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;" onclick="navTabNum('/report/ar/viewArReportsList','pageNum=1&menuNo=14013782&navTabId=pa0136','pa0136','<spring:message code="pa.viewPaMain.GONGZIBAOBIAO.C" />');">
				<font  color="white"><!--查看工资报表--><spring:message code="pa.viewPaWorkFlow.CHAKANGONGZIBAOBIAO.C" /></font></a>
				<!-- <font  >查看工资报表</font> -->
			</div>
		</div>
		<div style="width:170px;margin-left:1192px;margin-top:-262px;">
			<font  ><b>*&nbsp;&nbsp;<!--工资开放员工可查看--><spring:message code="pa.viewPaWorkFlow.GONGZIKAIFANGYUANGONGKECHAKAN.C" />&nbsp;&nbsp;*</b></font>
		</div>
		<div style="width:180px;margin-left:1168px;margin-top:39px;">
			<!-- <input type="checkbox" id="paOpen_pa0813" name="processType" value="paOpen" style="vertical-align:middle ;"/>&nbsp; -->
			<c:if test="${paWorkInfo.PA_OPEN_FLAG eq '1'}">
				<input type="checkbox" id="paOpen_pa0813" name="processType" value="paUnOpen" style="vertical-align:middle ;"/>&nbsp;
				<span onclick="$('#paOpen_pa0813').attr('checked')=='checked'?$('#paOpen_pa0813').removeAttr('checked'):$('#paOpen_pa0813').attr('checked','checked');" ><font color="#0A258F" style="vertical-align:middle ;cursor: pointer;"><!--取消开放--><spring:message code="pa.viewPaWorkFlow.QUXIAOKAIFANG.C" /></font></span>
			</c:if>
			<c:if test="${paWorkInfo.PA_OPEN_FLAG ne '1'}">
				<input type="checkbox" id="paOpen_pa0813" name="processType" value="paOpen" style="vertical-align:middle ;"/>&nbsp;
				<span onclick="$('#paOpen_pa0813').attr('checked')=='checked'?$('#paOpen_pa0813').removeAttr('checked'):$('#paOpen_pa0813').attr('checked','checked');" ><font color="black" style="vertical-align:middle ;cursor: pointer;"><!--工资开放--><spring:message code="pa.salary.title.salaryOpen" /></font></span>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/pa/workManagement/viewPaWorkFlowOperationRecordList?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO }&FLOW_STEP=5,7" target="dialog" mask="true" 
							width="600" title="<spring:message code='pa.viewPaWorkFlow.GONGZILIUCHENGCAOZUOJILU.C' />"
							height="300" style="text-decoration:none ;">
							<img title="<spring:message code='pa.viewPaWorkFlow.CHAKANCAOZUOJILU.C' />" style="vertical-align:middle ;"src="/resources/css/dwzUI/themes/hub/images/main/icon11.gif">
						</a>
		</div>
	</div>
	</form>
</div>
