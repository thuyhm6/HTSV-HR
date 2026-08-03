<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
function pageFromSea(a){
	var seach_PERSON_ID=$("#seach_PERSON_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_PERSON_ID",navTab.getCurrentPanel()).val();
	
	var seach_AR_MONTH=$("#seach_AR_MONTH",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AR_MONTH",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApplyLeave/viewApplyLeaveInfoList?seach_FLAG=${FLAG}&seach_PERSON_ID="+seach_PERSON_ID
			+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}

function delLeaveApplyCallback(form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("SINGLE_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApplyLeave/delLeaveApplyInBatch");
    alertMsg.confirm ("确定要批量删除吗?",{
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewApplyLeaveInfoList"));
						alertMsg.correct(data.message);
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
        }});
	return false;
}

function delOvertimeApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要删除吗?")){	  
		$.ajax({
		  url: '/ess/infoApplyLeave/delLeaveApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveInfoList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
function cancelLeaveApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApplyLeave/cancelLeaveApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveInfoList);
			}else{
				alert("取消失败！");
			}
		  }
		});
	}
}

function xiaojiaLeaveApplyNoAffirm(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{
		name: 'APPLY_TYPE',
		value: 'PERSON'
	});
	alertMsg.confirm ("确定要销假吗?",{
		okCall:function(){
		$.ajax({
			  url: '/ess/infoApplyLeave/addXiaojiaLeaveApplyNoAffirm',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					alertMsg.correct("销假成功!");
					//页面重载
					navTabSearch(document.viewApplyLeaveInfoList);
				}else{
					alertMsg.error("销假失败!");
				}
			  }
			});
		}});	  
}

function xiaojiaLeaveApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{
		name: 'APPLY_TYPE',
		value: 'PERSON'
	});
	alertMsg.confirm ("确定要申请销假吗?",{
		okCall:function(){
		$.ajax({
			  url: '/ess/infoApplyLeave/addXiaojiaLeaveApply',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					alertMsg.correct("销假申请成功!");
					//页面重载
					navTabSearch(document.viewApplyLeaveInfoList);
				}else{
					alertMsg.error("销假申请失败!");
				}
			  }
			});
		}});	  
}
//Leave类型联动查询
$(document).ready(function(){
	$("#seach_APPLY_TYPE_NO").bind('change',function(){
		if($("#seach_APPLY_TYPE_NO").val() == 218112){
			$('#seach_APPLY_TYPE_CODE').html("<option value='218112'>销假</option>");
		}else{
			ajaxAdd_add_ess0240(-1);
		}
	});
	
	if($("#seach_APPLY_TYPE_NO").val() != ''){
		if($("#seach_APPLY_TYPE_NO").val() == 218112){
			$('#seach_APPLY_TYPE_CODE').html("<option value='218112'>销假</option>");
		}else{
			var APPLY_TYPE_CODE = $("#seach_APPLY_TYPE_CODE_TEMP").val();
			ajaxAdd_add_ess0240(APPLY_TYPE_CODE);
		}
	}
});
var ajaxGet_add_ess0240;
function ajaxAdd_add_ess0240(APPLY_TYPE_CODE) {
		if (ajaxGet_add_ess0240 != null) {
			ajaxGet_add_ess0240.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_ess0240 = $.ajax( {
			type : "POST",
			url : "/ess/infoApplyLeave/getCodeList",
			data : { APPLY_TYPE_NO : $("#seach_APPLY_TYPE_NO").val()},
			dataType : "json",
			success : function(data) {
				$('#seach_APPLY_TYPE_CODE').html("");
				var html = '<option value="">全部</option>';
				if (typeof (data['codeList']) != "undefined") {
					$.each(data['codeList'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#seach_APPLY_TYPE_CODE').html(html);
				if(APPLY_TYPE_CODE != -1){
					$("#seach_APPLY_TYPE_CODE").val(APPLY_TYPE_CODE);
				}
			}
		}); 
		$.ajaxSettings.global = true;
}
/**function downloadImportTemplate_viweapplyleavebatchess0240(){
	var url = "/ess/infoApplyLeave/exportBatchLeaveModule?navTabId=ess0240";
	document.getElementById("exportExcel_viweapplyleavebatchess0240").href=encodeURI(url);
}
function excelimport_viewapplyleavebatchess0240(){
	$("#importExcelDialog_ess0240").attr('href','/pa/excelImport/importExcelData?importFunName=/importLeaveTempess0240&LEAVE_TYPE=ess0240');
	$("#importExcelDialog_ess0240").click();
}*/
		function printTure()   //打印函数
		 {
		    document.getElementById("toolmenu").style.display="none"; 
		    window.print();
		    //document.getElementById("toolmenu").style.display="";
		}
</script>

<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
		    <li>                    
				<a class="add" href="javascript:printTure();" 
					target="navTab" ><span>打印考勤申请明细信息</span></a>
			</li>
</div>
		<table class="table" width="100%" layoutH="235" nowrapTD="false">
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
						考勤类型
					</th>
					<th><!--申请日期-->
						开始日期
					</th>
					<th><!--申请日期-->
						结束日期
					</th>
					<th><!--申请时长-->
						开始时间
					</th>
					<th><!--申请时长-->
						结束时间
					</th>
					<th><!--时长-->
						时间
					</th>
					<th><!--考勤类型-->
						Leave/销假
					</th>
					<th><!--决裁情况-->
						审批状态
					</th>
					<th><!--是否取消-->
						是否取消
					</th>
					<th><!--输入者-->
						输入值者
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">	
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${leaveApply.LEAVE_TYPE_NAME}</td>
						<td style="text-align: center">${leaveApply.FROM_DATE}</td>
						<td style="text-align: center">${leaveApply.TO_DATE}</td>
						<td style="text-align: center">${leaveApply.FROM_TIME}</td>
						<td style="text-align: center">${leaveApply.TO_TIME}</td>
						<td style="text-align: center">${leaveApply.APPLY_LENGTH}</td>
						<td style="text-align: center">
							<c:if test="${leaveApply.LEAVE_TYPE_NAME eq '销假' }">销假</c:if>
							<c:if test="${leaveApply.LEAVE_TYPE_NAME ne '销假' }">Leave</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${leaveApply.AFFIRM_FLAG eq '-1'}">
								暂存
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '0'}">
								提交
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '1'}">
								通过
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '2'}">
								否决
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '3'}">
								撤销
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '4'}">
								审批中
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '5'}">
								发令
							</c:if>
						</td>	
						<td style="text-align: center">
						</td>
						<td style="text-align: center">${leaveApply.LOCAL_NAME} ${leaveApply.CREATED_IP}[${leaveApply.CHINESE_PINYIN}]</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>