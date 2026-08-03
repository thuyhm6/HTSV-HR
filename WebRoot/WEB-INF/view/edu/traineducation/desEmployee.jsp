<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		var empid="${EMPID}";
		if(empid!=''){
			var array=empid.split(',');
			for(var i=0;i<array.length;i++){
				$('#eduCheck_'+array[i]).attr('checked','checked');
			}
		}
		$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 270,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
                "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",//查询不到相关数据！
                "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",//表中无数据存在！
                "sSearch": "<spring:message code='ess.message.rapid_screening'/>"//快速筛选
            } //多语言配置
		});
});

$('#eduAllCheck').click(function(){  
    $('input[name="eduCheck"]').prop("checked",this.checked);  
});
function baocun(){
	var empid= "";
	var localname="";
	var localname01="";
	var old_empid= "";
	var old_localname="";
	var old_localname01="";
	var new_empid= "";
	var new_localname="";
	var new_localname01="";
	var doc01=$('#zhEmployee');
	 $("#eduTable input[name=eduCheck]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	    empid += $(this).val() + ",";
	        	    localname += $(this).attr('valuename') + ",";
	        	    var localname_d = $(this).val();
	        	    var localname_d_2 = $(this).attr('valuename');
	        	    localname01 += "<span id='empid_"+localname_d+"'>"+ $(this).attr('valuename') +
	        	    "<img  src='/resources/images/train003.gif' alt='<spring:message code="edu.planManager.QINGCHU.a"/>' onclick='deleteLocalName("+localname_d+")'></span>";//清除
	        	
	        } //获取被选中的值
	    });
		 empid=empid.substring(0,empid.length-1);
		 localname=localname.substring(0,localname.length-1);
		 old_empid = $('#empidEmployee').val();
		 if(old_empid == ''){
		    new_empid = empid;
		 }else{
		    new_empid = old_empid+','+empid;
		 }
		 old_localname = $('#empidEmployeeName').val();
		 if(old_localname == ''){
		    new_localname = localname;
		 }else{
		    new_localname = old_localname+','+localname;
		 }
		 //定义old_localname01
		 old_localname01 = $('#empidEmployeeName').val();
		 var arr1 = old_localname01.split(",");
		 var arr2 = old_empid.split(",");
		 var idEmpid = '';
		 var old_localnametrs = "";
			if(zhEmployee != "" && zhEmployee != null)
		 $.each(arr1,function(n,value) { 
		      idEmpid =arr2[n]; 
		      old_localnametrs += "<span id='empid_"+idEmpid+"'>"+ value +
	        	    "<img  src='/resources/images/train003.gif' alt='<spring:message code="edu.planManager.QINGCHU.a"/>' onclick='deleteLocalName("+idEmpid+")'></span>";//清除
		 });
		 if(old_localname01 == ''){
		    new_localname01 = localname01;
		 }else{
		    new_localname01 = old_localnametrs+localname01;
		 }
         $('#zhEmployee').html(new_localname01);
         $('#empidEmployee').attr('value',new_empid);
         $('#empidEmployeeName').attr('value',new_localname);
         $.pdialog.closeCurrent();
	 
}
function quxiao(){
	 $.pdialog.closeCurrent();
}
function childCheck(count){
	
	var ischeck=$('#eduCheck_'+count).prop('checked');
	if(ischeck==true){
		$('#eduCheck_'+count).removeAttr('checked');
	}else{
		$('#eduCheck_'+count).attr('checked','checked');
	}
}
function DESSousuo(){
	var empidname=$('#des_empid_name').val();
	var DEPTNO=$('#DEPTNO').val();
	var fenlei=$('#fenlei').val();
	$('#des_sousuo').attr('href','/edu/traineducation/desEmployee?empidname='+empidname+'&DEPTNO='+DEPTNO+'&fenlei='+fenlei);
	
}

</script>
<%-- 
	<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
	  <td class="td_title" width="5%">工号/姓名</td>
	  <td class="td_type"  width="5%" ><input type="text" name="des_empid_name" id="des_empid_name"></td>
	  <td class="td_title" width="5%"></td>
	  <td class="td_type"  width="5%" ></td>
	  
	</table>
--%>	
<div   class="pageHeader" >
		<div class="searchBar">
			<table class="searchContent" >
			    <td ><spring:message code="edu.planManager.GONGHAOXINGMING.a"/><!--工号/姓名--></td>
	            <td ><input type="text" name="des_empid_name" id="des_empid_name"></td>
	            <td > <spring:message code="edu.planManager.PAIXUFANGSHI.a"/><!--排序方式:--></td>
	            <td > 
	              <select name="fenlei"  id="fenlei">
	                 <option value=""><spring:message code="edu.planManager.PAIXUFANGSHI.a"/><!----请选择----></option>
	                 <option value="L"  <c:if test="${fenlei eq 'L'}">selected</c:if> ><spring:message code="edu.planManager.ANXINGMINGPAIXU.a"/><!--按姓名排序--></option>
	                 <option value="E"  <c:if test="${fenlei eq 'E'}">selected</c:if>  ><spring:message code="edu.planManager.ANSHEHAOPAIXU.a"/><!--按社号排序--></option>
	              </select>
	            </td>
	            <input type="hidden" name="DEPTNO"  id="DEPTNO" value="${DEPTNO}">
			</table>
		</div>
</div>
<div class="pageContent" layoutH="5">
   <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 10px; line-height: 30px;">Total:${fn:length(desEmployeeList)}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                 <ul style="padding-left: 420px;">
                    <li >
						<a class="buttonActive" id="des_sousuo" href="#" onclick="DESSousuo()" rel="zhiding" target="dialog" mask="true" width="600" height="400">
							<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
						</a>
					</li>
					<li >
						<a class="buttonActive" href="#" onclick="baocun()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
					<li >
						<a class="buttonActive"  href="#" onclick="quxiao()">
							<span><spring:message code="hrm.empinfo.CANCLE"/><!--取消--></span>
						</a>
					</li>
				</ul>
<table id="eduTable"  class="orderList" width="100%"  > 
		<thead>
				<tr>
				    <th width="2%"> 
				       <input type="checkbox" name="eduAllCheck" id="eduAllCheck">
				    </th>
				    <th  ><!--NO-->
						NO
					</th>
					<th   style="text-align: center" ><!--社号-->
						<spring:message code="ess.infoApply.EMPID"/><!--社号-->
					</th>
					<th   style="text-align: center"><!--申请人-->
						<spring:message code="ess.infoApply.NAME"/><!--姓名-->
					</th>
					<th   style="text-align: center"><!--部门-->
						<spring:message code="ess.infoApply.DEPT"/><!--部门-->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${desEmployeeList}" var="d" varStatus="i">	 
					<tr onclick="childCheck('${d.EMPID }')">
					    <td  width="2%" style="text-align: center">
					        <input type="checkbox" onclick="childCheck('${d.EMPID }')" id="eduCheck_${d.EMPID }" name="eduCheck" value="${d.EMPID }" valuename="${d.LOCAL_NAME }">
					    </td>
					    <td  style="text-align: center">
                            ${i.count}
					    </td>
					    <td  style="text-align: center">
					       ${d.EMPID }
					    </td>
					    <td  style="text-align: center">
					        ${d.LOCAL_NAME }
					    </td>
					    <td  style="text-align: center">
					        ${d.ORG_NAME_LOCAL }
					    </td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div>
