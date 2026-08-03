<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitSync(flag,no) {
	alertMsg.confirm("确定要同步吗？",
  		  	{okCall:function(){
		  	$.ajax({
  			  url: '/sys/empMapping/updateEmpMapping?FLAG=' + flag + '&NO=' + no,
  			  cache: false,
  			  success: function(responseText){
  				if (responseText.statusCode == 200){
  					alertMsg.info("同步成功！");
  					var dialog = $.pdialog.getCurrent();
         			$.pdialog.reload(dialog.data("url"));
  					navTabSearch($("#viewEmpInfo_sy0490"));
  				}else{
  					alertMsg.error("同步失败！");
  				}
  			  }
  			});
  	}});
	return false;
}
</script>
<div class="pageContent">
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<!-- 显示年假与调休 -->
							<table  class="user_table" width="100%">
								   	<tr>
								   		<td class="td_title" width="10%" style="text-align:center"><spring:message
											code="hr.enpinfo.title.EMP.EMPNUMBER" /> <!--社号--></td>
										<td class="td_title" width="10%" style="text-align:center"><spring:message
											code="public.title.name" /> <!--姓名--></td>
										<td class="td_title" width="14%" style="text-align:center"><spring:message
											code="public.title.deptName" /> <!--部门--></td>
										<td class="td_title" width="13%" style="text-align:center"><spring:message
											code="hr.viewWorkInfo.title.DUTY" /> <!--职责--></td>
										<td class="td_title" width="13%" style="text-align:center"><spring:message
											code="hr.enpinfo.title.EMP.TYPE" /> <!--人员类型--></td>
										<td class="td_title" width="10%" style="text-align:center">旧社号</td> 
										<td class="td_title" width="10%" style="text-align:center"><!-- 在职区分： --> <spring:message
										code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /></td> 
										<td class="td_title" width="10%" style="text-align:center">年假</td> 
										<td class="td_title" width="10%" style="text-align:center">调休</td> 
								   	</tr>
								   	<tr>
									   	<td style="td_type td_center">${empInfo.EMPID}</td>
										<td style="td_type td_center">${empInfo.LOCAL_NAME}</td>
										<td style="td_type td_center">${empInfo.DEPT_NAME}</td>
										<td class='td_type td_center' >${empInfo.POSITION}</td>
										<td class='td_type td_center' >${empInfo.EMP_TYPE_NAME}</td>
										<td class='td_type td_center' >${empInfo.OLD_EMPID}</td>
										<td class='td_type td_center' >${empInfo.STATUS_NAME}</td>
										<td class='td_type td_center' >${empInfo.SYNC_VAC}</td>
										<td class='td_type td_center' >${empInfo.SYNC_TX}</td>
								   	</tr>
							</table>
						</td>
					</tr>
					</table>
</div>

<div class="pageContent" >
				<table class="user_table" width="100%" border="0">
					<tr>
						<td class="td_title"  width="100%" style="padding:15px;">
						</td>
					</tr>
					</table>
</div>


<div class="pageContent">
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<!-- 显示年假与调休 -->
							<table  class="user_table" width="100%">
								   	<tr>
								   		<td width="10%" class="td_title" style="text-align:center">社号</td>
								   		<td width="45%" class="td_title" style="text-align:center">入社日期</td>
								   		<td width="45%" class="td_title" style="text-align:center">年假基准日期</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center">(新)${empInfo.EMPID }</td>
								   		<td class="td_type td_center">${empInfo.DATE_STARTED }</td>
								   		<td class="td_type td_center">${empInfo.YY_VAC_STD_DATE }</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center">(旧)${empInfo.OLD_EMPID }</td>
								   		<td class="td_type td_center">${empInfo.OLD_DATE_STARTED }</td>
								   		<td class="td_type td_center">${empInfo.OLD_YY_VAC_STD_DATE }</td>
								   	</tr>
							</table>
						</td>
					</tr>
					</table>
</div>

<div class="pageContent" >
				<table class="user_table" width="100%" border="0">
					<tr>
						<td class="td_title"  width="100%" style="padding:15px;">
						</td>
					</tr>
					</table>
</div>

<div class="pageContent">
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<!-- 显示年假与调休 -->
							<table  class="user_table" width="100%">
								   	<tr>
								   		<td width="10%" class="td_title" rowspan="3" style="text-align:center">社号</td>
								   		<td width="45%" class="td_title" colspan="6" style="text-align:center">年假</td>
								   		<td width="45%" class="td_title" colspan="3" style="text-align:center">调休</td>
								   	</tr>
								   	<tr>
								   		<td class="td_title" colspan="2" style="text-align:center">法定年假</td>
								   		<td class="td_title" colspan="2" style="text-align:center">福利年假</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">调休时数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用时数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余时数</td>
								   	</tr>
								   	<tr>
								   		<td class="td_title" style="text-align:center">本年年假</td>
								   		<td class="td_title" style="text-align:center">移年年假</td>
								   		<td class="td_title" style="text-align:center">福利年假</td>
								   		<td class="td_title" style="text-align:center">福利年假调整</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center" width="10%">(新)${empInfo.EMPID }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.TOT_VAC_CNT1 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.LAST_YEAR_VAC1 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.TOT_VAC_CNT2 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.ADD_VAC }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.USE_VAC }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoNew.SURPLUS_VAC }</td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoNew.TX_TOTAL * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoNew.TX_USE * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoNew.TX_SHENGYU * 8 }" maxFractionDigits="1"/></td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center" width="10%">(旧)${empInfo.OLD_EMPID }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.TOT_VAC_CNT1 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.LAST_YEAR_VAC1 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.TOT_VAC_CNT2 }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.ADD_VAC }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.USE_VAC }</td>
								   		<td class="td_type td_center" width="10%">${empVacInfoOld.SURPLUS_VAC }</td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoOld.TX_TOTAL * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoOld.TX_USE * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="10%"><fmt:formatNumber type="number" value="${empVacInfoOld.TX_SHENGYU * 8 }" maxFractionDigits="1"/></td>
								   	</tr>
							</table>
						</td>
					</tr>
					</table>
	<div class="formBar">
		<ul>
			<c:if test="${empInfo.SYNC_VAC eq '未同步'}">
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!--保存-->
							<button type="button" onclick="submitSync(1,${empInfo.NO})">
								同步年假
							</button>
						</div>
					</div>
				</li>
			</c:if>
			<c:if test="${empInfo.SYNC_TX eq '未同步'}">
				<li>
					<div class="button">
						<div class="buttonContent"><!--提交-->
							<button type="button" onclick="submitSync(2,${empInfo.NO})">
								同步调休
							</button>
						</div>
					</div>
				</li>
			</c:if>
		</ul>
	</div>
</div>