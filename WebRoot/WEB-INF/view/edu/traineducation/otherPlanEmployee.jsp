<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 $(document).ready(function(){
		var empid="${checkotherplanempid}";
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
	var old_localname="";
	 $("#tearcherTable input[name=teaCheck]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	    empid += $(this).val() + ",";
	        	    localname +=$(this).attr('valuename') +",";
	        	
	        } //获取被选中的值
	    });
		 empid=empid.substring(0,empid.length-1);
		 localname=localname.substring(0,localname.length-1);
		 $('#zixuanname').html(localname);
         $('#FREE_EMPLOYEE_NAME').attr('value',localname);
         $('#FREE_EMPLOYEE_EMPID').attr('value',empid);
         $('#actcount').html(splitMethod(empid)+splitMethod("${checkplanempid}"));
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
function BasicSousuo(){
	var empidname=$('#des_empid_name').val();
	var DEPTNO=$('#DEPTNO').val();
	var fenlei=$('#fenlei').val();
	$('#BasicSousuo').attr('href','/edu/traineducation/otherPlanEmployee?empidname='+empidname+'&DEPTNO='+DEPTNO+'&fenlei='+fenlei);
	
}
</script>
<div   class="pageHeader" >
		<div class="searchBar">
			<table class="searchContent" >
			    <td ><spring:message code="edu.planManager.GONGHAOXINGMING.a"/><!--工号/姓名--></td>
	            <td ><input type="text" name="des_empid_name" id="des_empid_name"></td>
	            <td ><spring:message code="edu.planManager.PAIXUFANGSHI.a"/><!--排序方式:--> </td>
	            <td > 
	              <select name="fenlei"  id="fenlei">
	                 <option value="">--<spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择-->--</option>
	                 <option value="L"  <c:if test="${fenlei eq 'L'}">selected</c:if> ><spring:message code="edu.planManager.ANXINGMINGPAIXU.a"/><!--按姓名排序--></option>
	                 <option value="E"  <c:if test="${fenlei eq 'E'}">selected</c:if>  ><spring:message code="edu.planManager.ANSHEHAOPAIXU.a"/><!--按社号排序--></option>
	              </select>
	            </td>
	            <input type="hidden" name="DEPTNO"  id="DEPTNO" value="${DEPTNO}">
			</table>
		</div>
</div>
<div class="pageContent" layoutH="10">
                <ul style="padding-left: 400px;">
                     <li >                                                                             
						<a class="buttonActive" id="BasicSousuo" href="#" onclick="BasicSousuo()" rel="xuanzi" target="dialog" mask="true" width="600" height="400">
							<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
						</a>
					</li>
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
		<span>Total:${otherPlanEmployeeListCount }</span>
		<tr>
		<td class="td_title" width="5%"><input type="checkbox" name="teaAllCheck" id="teaAllCheck" >NO</td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.LOCAL_NAME"/><!--姓名--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.dept"/><!--部门--></td>
		</tr>
		<c:forEach items="${otherPlanEmployeeList}" var="d" varStatus="i">
		<tr onclick="childCheck1('${d.EMPID }')">
		<td class="td_type"  width="5%" ><input type="checkbox" onclick="childCheck1('${d.EMPID }')" id="teaCheck_${d.EMPID }" name="teaCheck" value="${d.EMPID }" valuename="${d.LOCAL_NAME }">${i.count }</td>
		<td class="td_type"  width="5%" >${d.EMPID }</td>
		<td class="td_type"  width="5%" >${d.LOCAL_NAME }</td>
		<td class="td_type"  width="5%" >${d.ORG_NAME_LOCAL }</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		
				</div>
</div>
