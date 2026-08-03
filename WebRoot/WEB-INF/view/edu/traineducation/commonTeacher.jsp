<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 $(document).ready(function(){
		var empid="${alempid}";
		if(empid!=''){
			var array=empid.split(',');
			for(var i=0;i<array.length;i++){
				$('#teaCheck_'+array[i]).attr('checked','checked');
			}
		}
		
});

$('#teaAllCheck').click(function(){  
    $('input[name="teaCheck"]').prop("checked",this.checked);  
});
function baocun1(){
	var empid= "";
	var localname="";
	 $("#tearcherTable input[name=teaCheck]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	    empid += $(this).val() + ",";
	        	    localname +=$(this).attr('valuename') +",";
	        	
	        } //获取被选中的值
	    });
		 empid=empid.substring(0,empid.length-1);
		 localname=localname.substring(0,localname.length-1);
		 $('#pingjiateacher').html(localname);
         $('#EVA_TEACHER_NAME').attr('value',localname);
         $('#EVA_TEACHER_EMPID').attr('value',empid);
         $.pdialog.closeCurrent();
	 
}
function quxiao1(){
	 $.pdialog.closeCurrent();
}
function childCheck1(count){
	
	var ischeck=$('#teaCheck_'+count).prop('checked');
	if(ischeck==true){
		$('#teaCheck_'+count).removeAttr('checked');
	}else{
		$('#teaCheck_'+count).attr('checked','checked');
	}
} 
</script>
<div class="pageContent" layoutH="10">
                <ul style="padding-left: 470px;">
					<li>
						<a class="buttonActive" href="#" onclick="baocun1()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
					<li >
						<a class="buttonActive"  href="#" onclick="quxiao1()">
							<span><spring:message code="hrm.empinfo.CANCLE"/><!--取消--></span>
						</a>
					</li>
				</ul>
		<table id="tearcherTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<span>Total:${commonTeacherListCount }</span>
		<tr>
		<td class="td_title" width="5%"><input type="checkbox" name="teaAllCheck" id="teaAllCheck" checked="checked">NO</td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.LOCAL_NAME"/><!--姓名--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.dept"/><!--部门--></td>
		</tr>
		<c:forEach items="${commonTeacherList}" var="d" varStatus="i">
		<tr onclick="childCheck1('${d.EMPID }')">
		<td class="td_type"  width="5%" ><input type="checkbox" onclick="childCheck1('${d.EMPID }')" id="teaCheck_${d.EMPID }" name="teaCheck" value="${d.EMPID }" valuename="${d.TEACHER_NAME }">${i.count }</td>
		<td class="td_type"  width="5%" >${d.EMPID }</td>
		<td class="td_type"  width="5%" >${d.TEACHER_NAME }</td>
		<td class="td_type"  width="5%" >${d.ORG_NAME_LOCAL }</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		
				</div>
</div>
