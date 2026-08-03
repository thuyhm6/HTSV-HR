<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script language="javascript">
	function  trimsp(str)
	{
    	for(var  i  =  0  ;  i<str.length  &&  str.charAt(i)=="  "  ;  i++  )  ;
   		for(var  j  =str.length;  j>0  &&  str.charAt(j-1)=="  "  ;  j--)  ;
    	if(i>j)  return  "";  
    	return  str.substring(i,j);  
	}
	function ajaxtestExcel(form) {
		alert(form);
		var sform = document.getElementById(form);
		alert(sform);
		alert(sform.action);
		$.ajax({
			type: sform.method || 'POST',
			url:"/disc/autoExcel/testExcel",
			data:sform.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(){
				//alert(可以导出);
			},
				//callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		}
	function exportAutoImportExcel() {
        $("#runSql").submit();
	}
	function exportLOtImportExcel2(form,callback){
		 var returnVal = window.showModalDialog("/disc/sqlparam/encryptExcelForSql.jsp", null,"dialogHeight:200px;dialogWidth:300px;scroll:no;status:yes;help:no");
 	    if(returnVal==undefined ||returnVal=='')
 	    	{
 	    		return;
 	    	}
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 	$form = $(form);
		$form.password.value=returnVal;
		$form.attr("action","/disc/autoExcel/exportLOtImportExcel");
		$form.submit();
	}
</script>
<div class="pageContent">
<a id="importExcelDialog_orgSql" href="#" target="dialog" mask="true">
<span id="orgLinkasdf" style="display: none">
</span></a> 
	<form id="runSql" name="runSql" method="post" 
		action="/disc/autoExcel/exportLOtImportExcel">
		<div class="searchBar" layoutH="90">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
				<td class="td_title" style="width:20%"><!-- SQL序号 --><spring:message code="disc.autoExcel.SQL_NO.Z" />：</td>
				<td class="td_type" style="width:80%">
					<input readonly type="text" name="SQL_SEQMEAN" id="SQL_SEQMEAN"   value="${master.SQL_SEQ}"/>
				</td>
			</tr>
			<tr>
				<td class="td_title"><!-- SQL模块 --><spring:message code="disc.autoExcel.SQL_MODULE.Z" />：</td>
				<td  class="td_type">
					<input  readonly type="text" name="PGM_NM" id="PGM_NM"   value="${master.PGM_NM}"/>
				</td>
			</tr>
			<tr>
				<td class="td_title"><!-- SQL名字 --><spring:message code="disc.autoExcel.SQL_NAME.Z" />：</td>
				<td  class="td_type">
					<input readonly  type="text" name="SQL_NM"id="SQL_NM"     value="${master.SQL_NM}"/>
				</td>
			</tr>
		</table>
		<table class="searchContent" id="selectvalue" name="selectvalue">
					<thead>
							<tr> 
						    <th width="100">
						    	<!-- 参数 --><spring:message code="disc.autoExcel.PARAMETER.Z" />
						    </th>
							<th width="100">
								<!-- 英语描述 --><spring:message code="disc.autoExcel.ENGLISH_DESCRIPTION.Z" />
							</th>
							<th width="100">
								<!-- 越南语描述 --><spring:message code="disc.autoExcel.VIETNAMESE_DESCRIPTION.Z" />
							</th>
							<th width="100">
								<!-- 数值 --><spring:message code="pa.insurance.title.dataValue" />
							</th>
						</tr>
					</thead> 
					<tbody>
					

		        	