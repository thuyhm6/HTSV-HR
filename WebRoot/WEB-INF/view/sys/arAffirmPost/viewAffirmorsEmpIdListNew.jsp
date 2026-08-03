<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/**
 * 开始加载部门树
 */
var setting_sy0482_add_viewAffirmor = { 
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	data: {
		key: {
			children:"children",
		    name:"CONTENT",
		    title:""
		},
		simpleData: {
			enable:true,
		    idKey:"DEPTNO",
		    pIdKey:"PARENT_DEPT_NO",
		    rootPId: ""  
		}
	},
	callback: {
		beforeClick: function(treeId, treeNode) {
			var deptName = $("#deptName_sy0482_add_0_viewAffirmors").val();
			if(deptName == treeNode.CONTENT){
				$("#deptName_sy0482_add_0_viewAffirmors").attr("value", '');
				$("#seach_DEPTNO_viewAffirmors").attr("value",'');
			}else{
				$("#deptName_sy0482_add_0_viewAffirmors").attr("value", treeNode.CONTENT);
				$("#seach_DEPTNO_viewAffirmors").attr("value",treeNode.DEPTNO);
			}
		}
	}
};
function showTree_sy0482_add_viewAffirmors(obj) {
	var cityObj = $("#"+obj.id);
	var cityOffset = $("#"+obj.id).offset();
	$("#deptContent_sy0482_add_viewAffirmors").offset({
		top:cityOffset.top + "px", 
		left:cityOffset.left + cityObj.outerHeight() + "px" 
	}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown_sy0482_add_viewAffirmors);
}
function hideMenu_sy0482_add_viewAffirmors() {
	$("#deptContent_sy0482_add_viewAffirmors").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown_sy0482_add_viewAffirmors);
}
function onBodyDown_sy0482_add_viewAffirmors(event) {
	var str=event.target.id;
	if(str.indexOf("switch")==-1&&str.indexOf("_span")>-1){
		if(str!="deptContent_sy0482_add_viewAffirmors"){
			hideMenu_sy0482_add_viewAffirmors();
		}
	}
}
//加载部门树结束
$(document).ready(function(){
	$.fn.zTree.init($("#deptTree_sy0482_add_viewAffirmors"), setting_sy0482_add_viewAffirmor, '');
});
</script>
<div class="pageHeader">
	<form method="post" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm" 
		action="/sys/arAffirmPost/viewAffirmorsEmpIdListNew?navTabId=${request_navTabId}&empId=${empId}&personId=${personId}&empName=${empName}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<div class="searchBar" >
			<table class="searchContent">
				<tr>
					<td>
						 <spring:message code="public.title.empId"/><!--工号-->/<spring:message code="public.title.name"/><!--姓名-->：<input type="text" name="seach_EMPID" value="${EMPID}"/>
					</td>
					<td>
						 
					</td>
					<td>
						<!-- 部门：<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" /> -->
						  <input id="deptName_sy0482_add_0_viewAffirmors" name ="seach_DEPTNAME" type="text" onclick="showTree_sy0482_add_viewAffirmors(this);" readonly class="required"  value="${DEPTNAME}"/> 
					  	  <input id="seach_DEPTNO_viewAffirmors" name="seach_DEPTNO" type="hidden" sysLong="sy0482"  value="${DEPTNO}"/>	
					  	  <div id="deptContent_sy0482_add_viewAffirmors"  style="display:none; position: absolute;z-index:100;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
								<ul id="deptTree_sy0482_add_viewAffirmors" class="ztree" style="margin-top:0; width:300px;">
								</ul>
						 </div>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="sys.affirm.title.idNumber"/><!--身份证号--></th>
				<th width="80"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="sys.affirm.title.confirm"/><!--确认--></th>
			</tr>
		</thead>
		<tbody>
		<script type="text/javascript">
			var init=1;
			function checkTrAffirmorsEmp(personId,empId,localName,deptName,letter){
				var personIdStr="btnSelect"+personId;
				if(init==1){
				  document.getElementById(personIdStr).click();
				  init=init+1;
				}
				//checkAffirmor(personId,empId,localName,deptName,letter);
			}
		</script>
			<c:forEach items="${empList}" var="item">
				<tr target="EMPID" rel="${item.EMPID}" onclick="checkTrAffirmorsEmp('${item.PERSON_ID}','${item.EMPID}','${item.LOCAL_NAME}','${item.DEPTNAME}','');">
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.DEPTNAME}</td>
					<td>
						<a class="btnSelect" id="btnSelect${item.PERSON_ID}" href="javascript:
							$.bringBack({'${personId}':'${item.PERSON_ID}',	'${empId}':'${item.EMPID}','${empName}':'[${item.EMPID}]-${item.LOCAL_NAME}'
							})" title="<spring:message code='sys.affirm.title.findBackTo'/>"><spring:message code="public.title.choose"/><!--选择--></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" 
		action="/sys/arAffirmPost/viewAffirmorsEmpIdListNew?navTabId=${request_navTabId}&empId=${empId}&personId=${personId}&empName=${empName}">
		<div class="panelBar">
			<div class="pages">
				<span><spring:message code="public.title.view"/><!-- 显示 --></span>
					<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
						<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
						<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
						<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					</select>
				<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
			</div>
			<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>
	</form>
</div>