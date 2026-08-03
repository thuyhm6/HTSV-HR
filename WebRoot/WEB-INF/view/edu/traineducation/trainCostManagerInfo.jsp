<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
	changeCost();
});
function changeCost(){
	var teachercost=parseInt($('#TEACHER_COST').val());
	var materialcost=parseInt($('#MATERIAL_COST').val());
	var fieldcost=parseInt($('#FIELD_COST').val());
	var foodcost=parseInt($('#FOOD_COST').val());
	var staycost=parseInt($('#STAY_COST').val());
	var traffice=parseInt($('#TRAFFIC_COST').val());
	var visacost=parseInt($('#VISA_COST').val());
	var othercost=parseInt($('#OTHER_COST').val());
	var totalcount=parseInt($('#TOTAL_COUNT').val());
	var totalfeiyong=teachercost+materialcost+fieldcost+foodcost+staycost+traffice+visacost+othercost;
	var avgscore=Math.round(totalfeiyong/totalcount);
	$('#totalScore').html(avgscore);
	if("${CPNY_ID}"=='HAE'){
		var zhijiejingfei=teachercost+materialcost+fieldcost;
		$('#zhijiejingfei').html(zhijiejingfei);
		$('#jianjiejingfei').html(totalfeiyong-zhijiejingfei);
	}
	
	
}

/**
 * 删除附件
 */
function deleteAttListCost(id,val,callback){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']").each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}

	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

</script>
<div class="pageContent" layoutH="10">
	<form method="post" id="trainCostManagerInfo" action="/edu/traineducation/updateTrainCostManagerInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<input type="hidden" name="COST_NO" id="COST_NO" value="${trainCostManagerInfo.COST_NO }">
		<input type="hidden" name="TOTAL_COUNT" id="TOTAL_COUNT" value="${trainCostManagerInfo.TOTAL_COUNT }">
		<div class="formBar" style="padding-right:50px;">
			<ul> 
			    <li>
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=163&COST_NO=${trainCostManagerInfo.COST_NO }">
							<span><spring:message code="pa.insurance.title.excelExport"/><!--Excel导出--></span>
						</a>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="ess.message.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.JIANGSHIFEI.a"/><!--讲师费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="TEACHER_COST" id="TEACHER_COST" value="${trainCostManagerInfo.TEACHER_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.JIAOCAIFEI.a"/><!--教材费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="MATERIAL_COST" id="MATERIAL_COST" value="${trainCostManagerInfo.MATERIAL_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.CHANGDIFEI.a"/><!--场地费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="FIELD_COST" id="FIELD_COST" value="${trainCostManagerInfo.FIELD_COST }" onchange="changeCost()">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.CANYINFEI.a"/><!--餐饮费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="FOOD_COST" id="FOOD_COST" value="${trainCostManagerInfo.FOOD_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.ZHUSUFEI.a"/><!--住宿费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="STAY_COST" id="STAY_COST" value="${trainCostManagerInfo.STAY_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.JIAOTONGFEI.a"/><!--交通费--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="TRAFFIC_COST" id="TRAFFIC_COST" value="${trainCostManagerInfo.TRAFFIC_COST }" onchange="changeCost()">
		</td>
		</tr>
		
		<tr>
		<td class="td_type"  width="20%" colspan='6' style="height:15px;">
		</td>
		</tr>
		
		<tr>
		<td class="td_title"  width="1%" colspan='6' style="text-align: center;"><spring:message code="edu.trainCostMANAGER.QITAFEIYONG.a"/><!--其它费用--></td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.QIANZHENGJIXIANGGUANFEIYONG.a"/><!--签证及相关费用--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="VISA_COST" id="VISA_COST" value="${trainCostManagerInfo.VISA_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.QITAFEIYONG.a"/><!--其它费用--></td>
		<td class="td_type"  width="20%">
		<input type="text" min="0" name="OTHER_COST" id="OTHER_COST" value="${trainCostManagerInfo.OTHER_COST }" onchange="changeCost()">
		</td>
		<td class="td_title" width="1%"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注--></td>
		<td class="td_type"  width="20%">
		<input type="text" name="REMARK" id="REMARK" value="${trainCostManagerInfo.REMARK }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.FEIYONGHEJIRENJUN.a"/><!--费用合计(人均)--></td>
		<td class="td_type"  width="20%">
		<span id="totalScore" style="color:red;"></span>
		</td>
		<td class="td_title" width="1%"><spring:message code="hr.viewBadArchives.title.FILE"/><!--附件--></td>
		<td class="td_type" width="20%" colspan="3">
			<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="button.add"/><!--添加--></span>
			</a>
			<a class="w_button" href="#" onclick="deleteAttListCost('trainCostManagerInfo','/edu/traineducation/trainCostManagerInfo?PERSON_ID=${PERSON_ID}&COST_NO=${trainCostManagerInfo.COST_NO }',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
			<table id="fileTable" class="list" width="100%">
				<thead>
				</thead>
				<tbody>
				<c:forEach items="${trainCostManagerInfo.fileList}" var="item" varStatus="i">
						<tr>
							<td class='td_center'>
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
							<input type="hidden" name="fileUrl" value="${item.FILE_PATH }">
							<input type="hidden" name="fileName" value="${item.FILE_NAME }">
							</td>
							<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
		<%-- <td class="td_type"  width="20%" colspan='3'>
		<a class="w_button" href="#" onclick="uploadAttDialog_new('trainCostManagerInfo','/edu/traineducation/trainCostManagerInfo?PERSON_ID=${PERSON_ID}$COST_NO=${trainCostManagerInfo.COST_NO }','${trainCostManagerInfo.COST_NO }','eduCostManager','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
		<a class="w_button" href="#" onclick="deleteAttListCost('trainCostManagerInfo','/edu/traineducation/trainCostManagerInfo?PERSON_ID=${PERSON_ID}&COST_NO=${trainCostManagerInfo.COST_NO }',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
		<c:forEach items="${trainCostManagerInfo.fileList}" var="item" varStatus="i">
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a>
	    </c:forEach>
		</td> --%>
		</tr>
		<c:if test="${CPNY_ID=='HAE' }">
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.ZHIJIEJINGFEI.a"/><!--直接经费--></td>
		<td class="td_type"  width="20%" colspan='3'>
		<span id="zhijiejingfei" style="color:red;"></span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainCostMANAGER.JIANJIEJINGFEI.a"/><!--间接经费--></td>
		<td class="td_type"  width="20%">
		<span id="jianjiejingfei" style="color:red;"></span>
		</td>
		</tr>
		</c:if>
		</table>
		</div>
		
	</form>
</div>
