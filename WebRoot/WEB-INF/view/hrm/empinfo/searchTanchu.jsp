<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var idvalue="${idvalue}";
	idvalue=idvalue.replace(/'/g,"");
	if(idvalue!=null&&idvalue!=""){
		var arrayidvalue=idvalue.split(',');
		for(var i=0;i<arrayidvalue.length;i++){
			$('#ss_'+arrayidvalue[i]).attr('checked','checked');
		}
	}
});
	function changeUrlToNavTabNum(url,param,menu_code,menu_name){
		url = encodeURI(encodeURI(url)) ;
		navTabNum(url,'',menu_code,menu_name);
		
	}
function xuanzhong(codeno){
	var a=$('#ss_'+codeno).prop('checked');
	if(a==true){
		$('#ss_'+codeno).removeAttr('checked');
	}else{
		$('#ss_'+codeno).attr('checked','checked');
	}
	
}
function queding(){
	var arrayno= "";
	var arrayname="";
	 $("#aa input[name=codename]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	var a="'";
	        	arrayno +=a+ $(this).val() + "',";
	        	arrayname +=$('#name_'+$(this).val()).text()+",";
	        } //获取被选中的值
	    });
	 var status="${status}";
	 var nameid="${nameid}";
	 var typeFlag="${typeFlag}";
	 arrayno=arrayno.substring(0,arrayno.length-1);
	 //arrayname=arrayname.replace(/\s/g, " ");
	 arrayname=arrayname.trim();
	 arrayname=arrayname.replaceAll(",			  ",",");
	 arrayname=arrayname.substring(0,arrayname.length-1);
	 if(typeFlag=="Y"){
		 $('#'+status).attr('value',arrayname);
		 $('#'+nameid).attr('value',arrayno);
	 }else{
		 $('#'+status).html(arrayname);
		 $('#'+nameid).attr('value',arrayno);
	 }
	 
	 
		$.pdialog.closeCurrent();
	 
}
</script>
<div  class="pageContent" style="overflow-x: auto;overflow-y: auto">

			<table id="aa" class="user_table" width="100%" border="1" cellpadding="0" cellspacing="0" layoutH="50" targetType="dialog">
			<c:forEach items="${empInfo }" var="e" varStatus="i">
			  <tr onclick="xuanzhong('${e.CODE_NO}')" >
			  <td width="5%" class="td_type">
			  <input onclick="xuanzhong('${e.CODE_NO}')" type="checkbox" name="codename" id="ss_${e.CODE_NO }" value="${e.CODE_NO }" >
			  <a id="name_${e.CODE_NO }">
			  ${e.CODE_NAME }</a>
			  </td>
			  </tr>
			</c:forEach>
			</table>
			<input type="button" value="<spring:message code='hrm.empinfo.CONFIRM' />" onclick="queding()" style="font-size: 15px;margin-top: 20px;">
			
</div>
