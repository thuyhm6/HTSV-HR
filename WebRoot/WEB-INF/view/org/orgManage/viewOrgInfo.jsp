<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>OrgChart</title>
<!-- CSS -->
<link rel="stylesheet" href='/resources/css/bootstrap.min.css'/>
<link rel="stylesheet" href='/resources/css/jquery.jOrgChart.css'/>
<!-- JS -->
<script src="/resources/js/jquery.jOrgChart.js" type="text/javascript"></script>
<script type='text/javascript'>
jQuery(document).ready(function(){
	$.ajax({
		url: "/org/orgManage/viewOrgInfoData",
		type: 'POST',
		dataType: 'JSON',
		data: {seach_DEPTNO: $("#seach_DEPTNO").val(),seach_DEPT_LEVEL:$("#seach_DEPT_LEVEL").val()},
	 	success: function(result){
			var showlist = $("<ul id='org' style='display:none'></ul>");
	      	$("#jOrgChart").html("").append(showlist);
	    	showall(result, showlist);
	   		$("#org").jOrgChart( {
	          	chartElement : '#jOrgChart',//指定在某个dom生成jorgchart
	          	dragAndDrop : false //设置是否可拖动
	      	});
	 	}
	});
});
function showDept(){
	
	//数据返回
	$.ajax({
		url: "/org/orgManage/viewOrgInfoData?seach_DEPTNO=" + $("[syslong='viewOrgInfoInfoForSearchchange_seachDept']").val() + "&seach_DEPT_LEVEL=" + $("#seach_DEPT_LEVEL").val(),
		type: 'POST',
		dataType: 'JSON',
		data: {seach_DEPTNO: $("#seach_DEPTNO").val(),seach_DEPT_LEVEL:$("#seach_DEPT_LEVEL").val()},
	 	success: function(result){
			var showlist = $("<ul id='org' style='display:none'></ul>");
	      	$("#jOrgChart").html("").append(showlist);
	    	showall(result, showlist);
	   		$("#org").jOrgChart( {
	          	chartElement : '#jOrgChart',//指定在某个dom生成jorgchart
	          	dragAndDrop : false //设置是否可拖动
	      	});
	   		changeLayout();
	 	}
	});
}
//menu_list为json数据
//parent为要组合成html的容器
function showall(menu_list, parent) {
	$.each(menu_list, function(index, val) {
    	if($("#" + val.PARENT_DEPT_NO).length>0 ){
    		$("#" + val.PARENT_DEPT_NO).append("<li><div class='no_pic'><div class='info'>" + val.PNAME + "(" + val.EMP_CNT + ")" + "</div><div class='info manager'><a href=\"#\" onclick=\"openEmpInfoWin('" + val.DEPTNO + "')\">" + val.LOCAL_NAME + "</a></div><div class='info manager'>" + val.POST_GRADE_NAME + "</div></div><div class='info pic'><img id=\"pic_" + val.DEPTNO + "\" src=\"" + val.PHOTO_PATH + "\" onerror=\"changePic(this.id);\" /></div><ul id='" + val.DEPTNO + "'></ul></li>");
       	}else{
    		parent.append($("<li></li>").append("<div class='no_pic'><div class='info'>" + val.PNAME + "(" + val.EMP_CNT + ")" + "</div><div class='info manager'><a href=\"#\" onclick=\"openEmpInfoWin('" + val.DEPTNO + "')\">" + val.LOCAL_NAME + "</a></div><div class='info manager'>" + val.POST_GRADE_NAME + "</div></div><div class='info pic'><img id=\"pic_" + val.DEPTNO + "\" src=\"" + val.PHOTO_PATH + "\" onerror=\"changePic(this.id);\" /></div>").append("<ul id='" + val.DEPTNO + "'></ul>"));
     	}
  	});
}
function changePic(photo){
	document.getElementById(photo).src = '/resources/photo/default.jpg';
}
function changeLayout(){
	var info = $("#MANAGER_INFO").attr("checked");
	var pic = $("#PIC_INFO").attr("checked");


	$(".node").css("height","30px").css("width","90px");
	$(".manager").css("display","none");
	$(".pic").css("display","none");
	$(".info").css("line-height","14px");
	if(info == "checked"){
		$(".node").css("height","60px");
		$(".manager").css("display","");
	}
	if(pic == "checked"){
		$(".info").css("line-height","20px");
		$(".node").css("height","70px").css("width","160px");
		$(".manager").css("display","");
		$(".pic").css("display","");
	}
}

function openEmpInfoWin(deptno){//部门信息
	$.pdialog.open("/org/orgManage/viewCurrentOrgDetailInfo?DEPTNO=" + deptno, "viewCurrentOrgDetailInfo", "<spring:message code='org.title.DEPT_INFO' />", {width:500,height:500,mask:true});
}
</script>  
<body>
<div class="pageHeader">
	<form id="viewContractInfoForSearch"
		onsubmit="return navTabSearch(this);"
		action="/org/orgManage/viewOrgInfo" method="post">
		<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG"
			value="1" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" 
							id="viewOrgInfoInfoForSearchchange_seachDept" />
						<ait:deptTreeIcon name="seach_DEPTNO" 
							id="viewOrgInfoInfoForSearchchange_seachDept"
							selected="${DEPTNO}" />
					</td>
					<%-- <td>
						<spring:message code="org.title.Hierarchy" /><!-- 层级 -->
					</td>
					<td>
						<input type="text" name="seach_DEPT_LEVEL" id="seach_DEPT_LEVEL"/>
					</td> --%>
					<td>
						<input type="checkbox" id="MANAGER_INFO" value="1" checked onclick="changeLayout()"><span><spring:message code="org.orgManage.DEPARTMENT_INFORMATION.Z" /><!-- 部门长信息 -->
						<input type="checkbox" id="PIC_INFO" value="2" checked onclick="changeLayout()"><span><spring:message code="org.orgManage.DEPARTMENT_PHOTO.Z" /><!-- 部门长照片 -->
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="showDept()">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li><a class="buttonActive" onclick="print()" href="#"> <span><spring:message code="org.title.PRINT" /><!-- 打印 --></span> </a><br>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent"  layoutH="80">
	<div id="jOrgChart" sysLong='printDiv'>
	</div>
</div>
</body>
</html>