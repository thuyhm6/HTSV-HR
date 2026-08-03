<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function add(checkbox)
{
     var $form=$("#empSearch");
     var localName=checkbox.title;
     var empid=$(checkbox).val();
     if(checkbox.checked){
 	 	$form.find("#displayItem").append("<option value='"+empid+"'>"+localName+"</option>");  //添加
	 }else{
		$("#displayItem option[value='"+empid+"']").remove();  //删除
 	 }
 	 for(i=0;i<=document.all("displayItem").length - 1 ; i++ ){
 	 	var s = s+","+document.empSearch.displayItem.options(i).value;
 	 }
	 var a = s.substr(10,s.length);
	 $("#searchForm").find("input[name='eidsForSearch']").attr("value",a);
	 $("#pagerForm").find("input[name='eidsForSearch1']").attr("value",a);
	 document.getElementById("eidsForSearch1").value=a;
 }

function clearALL(){
	var obj = document.getElementById('displayItem');
	var length = obj.length;
	for (var j=length-1; j>=0; j--){
		obj.options.remove(j);
	}
	var oCheckBox = document.getElementsByName("d1");
    for (var i = 0; i < oCheckBox.length; i++) {
       var e = oCheckBox[i];
       e.checked = false;
    }
}


</script>


<div class="pageHeader">

	<form id="searchForm" method="post" action="/hrm/transferOrder/viewEmpSearchList?navTabId=${request_navTabId}&&theType=${theType}" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
			
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--社号--><input type="text" name="seach_EMPID" value="${EMPID}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名--><input type="text" name="seach_LOCAL_NAME" value="${LOCAL_NAME}"/>
							  <input type="hidden" name="seach_IDCARD_NO" value="${IDCARD_NO}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门--><ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
					
					<input type="hidden" id="eidsForSearch" name="eidsForSearch" value="${eidsForSearch}"/>
					<input type="hidden" id="theType" name="theType" value="${theType}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearALL()"><spring:message code="hr.viewEmpSearchList.title.reset"/><!-- 重置 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" height="350">
	<form id="saveEmpSearch" method="post" action="/hrm/transferOrder/saveEidsForSearch" onsubmit="return validateCallbackEmpSearch('${request_navTabId}');">
		<div class="formBar" >
				<tr>	
				<label style="float: left">
				</label>
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="hr.viewUpgrade.title.SAVE"/>
									<!--保存-->
								</button>
							</div>
						</div>
					</li>
					</ul>
			</tr>
		</div>
	</form>

	<table class="table" width="100%" layoutH="300">
		<thead>
			<tr>
				<th width="5%">
					<spring:message code="public.title.choose"/>
					<!--选择-->
				</th>
				<th width="19%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--社号-->
				</th>
				<th width="19%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="19%">
					<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					<!--身份证号-->
				</th>
				<th width="19%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="19%">
					<spring:message code="hr.viewPersonalInfo.title.DEPT_DISTINGUISH_NO"/>
					<!--部门区分-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empList}" var="item">
				<tr target="EMPID">
					<td>
						<input type="checkbox" id="d1_${item.EMPID}" name="d1" value="${item.EMPID}" onClick="add(this)" title="${item.LOCAL_NAME }" value="${item.EMPID }" alt="${item.LOCAL_NAME }"
							<c:if test="${eidList!=null }">
								<c:forEach items="${eidList}" var="emp">
									<c:if test="${item.EMPID==emp.EMPID}">
										checked="true"
									</c:if>
								</c:forEach>
							</c:if>
						/>
					</td>
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.DEPTNAME}</td>
					<td>${item.DEPT_DISTINGUISH_NO}</td>
				</tr>
			</c:forEach>
		</tbody>
		
		
	</table>

	
	
	<!--  -->
	
		<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span><!--已选项目--><spring:message code="hr.viewCondSql.title.YIXUANXIANGMU"/>
					</span></a></li>
					 
				</ul>
			</div>
		</div>
	
		<div class="tabsContent" style="height:80px;">
			<form id="empSearch" name="empSearch">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td  width="100%" valign="top" class="l-table-edit-td1" >
							<select id="displayItem" name="displayItem" size="650" style="width:200%">
								
								<c:if test="${eidList!=null }">
									<c:forEach items="${eidList}" var="emp">
										<option value="${emp.EMPID}">${emp.LOCAL_NAME}</option>
									</c:forEach>
								</c:if>
								
							</select>
						</td>
				    </tr>	
				</table>
			</form>
		</div>
		
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
		
	</div>
	
	
	<!--  -->
	
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewEmpSearchList?navTabId=${request_navTabId}&&theType=${theType}">
	<input type="hidden" id="eidsForSearch1" name="eidsForSearch1" value="${eidsForSearch1}"/>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
	
	
</div>