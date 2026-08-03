<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//申请项目通知
function tongzhi(DEPTNO,PARAM_NO,CPNDID,PARAM_DATA_NO,PARAM_DATA_NO_OTHER){
	$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/pa/insurance/applyInform?",
			 data: 'DEPTNO=' + DEPTNO+'&PARAM_NO='+PARAM_NO+'&CPNDID='+CPNDID+'&PARAM_DATA_NO='+PARAM_DATA_NO+'&PARAM_DATA_NO_OTHER='+PARAM_DATA_NO_OTHER,
			 dataType:"json",
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					alertMsg.correct(data.message);
					var dialog = $.pdialog.getCurrent();
         			$.pdialog.reload(dialog.data("url"))
				}else{
					if(data.result=="2"){
						alertMsg.info(data.message);
					}else{
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});
		return false;
}
</script>
<div class="pageContent" id="tongzhi">
	<table class="table" width="100%" layoutH="60">
		<thead>
			<tr>
				<th width="180"><spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人--></th>
				<th width="80"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="80"><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:--></th>
				<th width="80"><spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:--></th>
				<th width="80"><spring:message code="pa.insurance.title.startMonth"/><!--开始月--></th>
				<th width="80"><spring:message code="pa.insurance.title.endMonth"/><!--结束月--></th>
				<th width="80"><spring:message code="pa.insurance.title.dataValue"/><!--数值--></th>
				<th width="80">是否通知</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${unifySuitCompanyList}" var="item" >
				<tr>
				<td>${item.COMPANY_NAME }</td>
				<td>${item.APPLY_ITEM_NAME }</td>
				<td>${item.DIFFERENTIATE_ITEM_ONE }</td>
				<td>${item.DIFFERENTIATE_ITEM_TWO }</td>
				<td>${item.START_MONTH}</td>
				<td>${item.END_MONTH}</td>
				<td>${item.DEFAULT_VAL }</td>
				<td style="text-align: center">
					<c:if test="${item.APPLY_INFORM_MARK eq 1}"><b><font color="green">以通知</font></b></c:if>
					<c:if test="${AFFIRM_FLAG eq '1' and item.APPLY_INFORM_MARK ne 1}">
					<b><font color="blue"><a href="#" onclick="tongzhi('${item.DEPTNO}','${item.PARAM_NO}','${item.CPNDID }','${PARAM_DATA_NO}','${item.PARAM_DATA_NO_OTHER}');">通知</a></font></b>
					</c:if>
				
				</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>
	
</div>