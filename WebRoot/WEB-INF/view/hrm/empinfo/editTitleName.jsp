<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$('#titAllCheck').click(function(){  
    $('input[name="titCheck"]').prop("checked",this.checked);  
});
function baocuntit(){
	var searchno="";
	var titlename="";
	 $("#titTable input[name=titCheck]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	searchno += $(this).val() + ",";
	        	titlename +=$(this).attr('valuename') +",";
	        	
	        } //获取被选中的值
	    });
	     searchno=searchno.substring(0,searchno.length-1);
	     titlename=titlename.substring(0,titlename.length-1);
	     
		 if(searchno!=''&&titlename!=''){
			 $('#searchno').attr('value',searchno);
			 $('#titlename').attr('value',titlename);
			 validateDeleteResumeInfoCallbacktit('deleteTitlename',navTabAjaxDone);
		 }else{
			 alert("<spring:message code='hr.alert.message.viewPersonalInfo.checkBoxForChecked.shanchu' />");//请先选择信息再进行删除操作
		 } 
}
function validateDeleteResumeInfoCallbacktit(form,callback){
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteTitlename',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
		  	quxiaotit();
  	}});
	
	return false;
}
function quxiaotit(){
	 $.pdialog.closeCurrent();
}
function titchildCheck(count){
	
	var ischeck=$('#titCheck_'+count).prop('checked');
	if(ischeck==true){
		$('#titCheck_'+count).removeAttr('checked');
	}else{
		$('#titCheck_'+count).attr('checked','checked');
	}
}
</script>
<div class="pageContent" layoutH="10">
<form id="deleteTitlename" method="post" action="/hrm/empinfo/deleteTitlename" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
		<table id="titTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<input type="hidden" name="searchno" id="searchno" value="">
		<input type="hidden" name="titlename" id="titlename" value="">
		<tr>
		<td class="td_title" width="5%" style="text-align: center"><input type="checkbox" name="titAllCheck" id="titAllCheck">NO</td>
		<td class="td_title" width="5%" style="text-align: center"><!-- 模板名称 --><spring:message code="hrm.empinfo.TEMPLATE_NAME"/></td>
		</tr>
		<c:forEach items="${editTitleName}" var="d" varStatus="i">
		<tr onclick="titchildCheck('${d.SEARCHNO }')">
		<td class="td_type"  width="5%" ><input type="checkbox" onclick="titchildCheck('${d.SEARCHNO }')" id="titCheck_${d.SEARCHNO }" name="titCheck" value="${d.SEARCHNO }" valuename="${d.TITLENAME }">${i.count }</td>
		<td class="td_type"  width="5%" >${d.TITLENAME }</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		<ul>
					<li>
						<a class="buttonActive" href="#" onclick="baocuntit()">
							<span><!-- 确定 --><spring:message code="hrm.empinfo.CONFIRM" /></span>
						</a>
					</li>
					<li >
						<a class="buttonActive"  href="#" onclick="quxiaotit()">
							<span><!-- 取消 --><spring:message code="hrm.empinfo.CANCLE"/></span>
						</a>
					</li>
				</ul>
				</div>
				</form>
</div>
