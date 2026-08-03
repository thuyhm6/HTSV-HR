<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function qufenAndNameCheng(obj){
	if(obj == null || obj.length == 0){
		return ;
	}
	$("#importItemDiv").hide();
	var sel = $("#seach_PARAM_NO",navTab.getCurrentPanel());//项目区分
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/salary/getqufenAndNameCheng2?",
		 data: 'seach_ITEM_DISTINGUISH=' + obj,
		 dataType:"json",
		 success: function(data) {
		 sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
var ajaxGet_add_hr2100;
function ajaxAdd_add_hr2100(EMP_TYPE) {
		if (ajaxGet_add_hr2100 != null) {
			ajaxGet_add_hr2100.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_hr2100 = $.ajax( {
			type : "POST",
			url : "/hrm/jobType/getEmpJobType",
			data : { EMP_TYPE_GROUP : $("#seach_EMP_TYPE_GROUP").val() , defaultCpny : $("#hr2100_seach_CPNY_ID").val()},
			dataType : "json",
			success : function(data) {
				$('#seach_EMP_TYPE_hr2100').html("");
				var html = '<option value="">请选择</option>';
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#seach_EMP_TYPE_hr2100').html(html);
				if(EMP_TYPE != -1){
					$("#seach_EMP_TYPE_hr2100").val(EMP_TYPE);
				}
			}
		});
		$.ajaxSettings.global = true;
}
var t1 = window.setTimeout("aa()",10);

function aa(){
	
	var temp = document.getElementById("seach_TYPE1").value;
	if(temp=="shang" )
		{
		
		 document.getElementById("shangyue").style.display="";
		 document.getElementById("benyue").style.display="none";
		 document.getElementById("search").style.display="";

		}else if (temp=="ben"){
			 document.getElementById("benyue").style.display="";
			 document.getElementById("shangyue").style.display="none";
			 document.getElementById("search").style.display="none";

		}else{
	 document.getElementById("benyue").style.display="none";
	 document.getElementById("shangyue").style.display="none";
		}
	 window.clearTimeout(t1);
	 
}
 function change(a){
	 var none = a.value;
	 if(none=='shang')
	 {
	 document.getElementById("shangyue").style.display="";
	 document.getElementById("benyue").style.display="none";
	 document.getElementById("search").style.display="";

	 }
	 else if(none=='ben')
	 {
	 document.getElementById("benyue").style.display="";
	 document.getElementById("shangyue").style.display="none";
	 document.getElementById("search").style.display="none";

	 }else {
		 document.getElementById("benyue").style.display="none";
		 document.getElementById("shangyue").style.display="none";

	 }
 }
	function exportExcel(){
		var post = document.getElementById('postName');
		post.action="/pa/paView/viewWageComExcel";
		post.submit();
	}

</script>

<form onsubmit="return navTabSearch(this);" action="/pa/paView/viewWageCom" method="post" id="postName">
	<div class="pageHeader" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td > 
					<!--项目区分--><spring:message code="liang.public.title.ItemDistinguish"/>
					</td>
					<td>
						<select id="seach_ITEM_DISTINGUISH" name="seach_ITEM_DISTINGUISH" value="${seach_ITEM_DISTINGUISH }" onchange = "qufenAndNameCheng(this.value);"> 
						<option value="" >
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>

						<option value="507" <c:if test="${seach_ITEM_DISTINGUISH eq '507'}">selected</c:if>>
							<spring:message code="pa.wagebase.title.basicItemInfo"/>
						</option>
						<option value="508" <c:if test="${seach_ITEM_DISTINGUISH eq '508'}">selected</c:if>>
							<spring:message code="pa.salary.title.inputItem"/>
						</option>
						<option value="2372" <c:if test="${seach_ITEM_DISTINGUISH eq '2372'}">selected</c:if>>
							计算项目<!--<spring:message code="pa.salary.title.inputItem"/>-->
						</option>
						</select>
					</td>
					<td>
					<!-- 项目名称--><spring:message code="liang.public.title.ItemName"/>
					</td>
					<td>
						<select name="seach_PARAM_NO" id="seach_PARAM_NO" value="${seach_PARAM_NO }">
							<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
							<c:forEach items="${getItemNameList}" var="item">
								<option <c:if test="${seach_PARAM_NO eq item.PARAM_NO }"> selected</c:if> value="${item.PARAM_NO}">${item.ALIAS_NAME}</option>
							</c:forEach>
						</select>
					</td>
			<td>
			<spring:message code="pa.viewWageCom.Monitoring"/>
					</td>
					<td>
						<select id="seach_TYPE1" name="seach_TYPE1" value="${seach_TYPE1 }" onchange = "change(this)"> 
						<option value="shang" <c:if test="${seach_TYPE1 eq 'shang'}">selected</c:if>>
							<spring:message code="pa.viewWageCom.Contrast"/>
						</option>
						<option value="ben" <c:if test="${seach_TYPE1 eq 'ben'}">selected</c:if>>
							<spring:message code="pa.viewWageCom.Screening"/>
						</option>
						</select>
					</td>

					<td colspan="2">
						<select id="TYPE2" name="TYPE2" value="${TYPE2 }"> 
						<option value="da" <c:if test="${TYPE2 eq 'da'}">selected</c:if>>
							<spring:message code="pa.viewWageCom.More"/>
						</option>
						<option value="xiao" <c:if test="${TYPE2 eq 'xiao'}">selected</c:if>>
							<spring:message code="pa.viewWageCom.Less"/>
						</option>
						</select>
						<spring:message code="pa.viewWageCom.Difference"/>
						<input type="text" name="money" value="${money }" />
						%<spring:message code="pa.viewWageCom.Input"/>
					</td>
				</tr>
				<tr>
					<td> 
					<spring:message code="public.title.deptName"/><!--部门-->
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="pa" id="viewWageCom_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="pa" id="viewWageCom_seachDept" selected="${seach_DEPTNO}"/>
					</td>

					<td>
					<!-- 考勤月开始--> <spring:message code="pa.viewWageCom.month"/>
					</td>
					<td>
						<ait:date yearName="Yearfrom" yearSelected="${Yearfrom}" monthName="Monthfrom" monthSelected="${Monthfrom}"/>
					</td>

					<td id="search">
						<!-- 考勤月结束--> <spring:message code="pa.viewWageCom.lastmonth"/>
					</td>
					<td>
							<ait:date yearName="Yearto" yearSelected="${Yearto}" monthName="Monthto" monthSelected="${Monthto}"/>
					</td>
		<td><!-- 工号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
		<input
			type="text" name="seach_KEY" value="${seach_KEY}" /></td>
				</tr>
				<tr>
					<td><!-- 人员类型： --> <spring:message
						code="hr.enpinfo.title.EMP.TYPE" /> 
								</td>
								<td>
					 	<ait:SelectEmpTypeCode id="seach_EMP_TYPE" name="seach_EMP_TYPE" selected="${seach_EMP_TYPE}" limit="pa"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="button.search"/></button>
					</div>
					<a onClick="exportExcel();">
						<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
					</a>	
					
					
					
					</div></li>
				
				</ul>
			</div>
		</div>
	</div>		
<div class="pageContent" id="shangyue" >
			<table class="table" width="100%" layoutH="200">
				<thead>
				<tr>
					<th width="12"><spring:message code="public.title.name"/><!--姓名--></th>
					<th width="12"><spring:message code="public.title.empId"/><!--工号--></th>
					<th width="12"><spring:message code="public.title.deptName"/><!--部门--></th>
					<th width="12"><spring:message code="pa.viewWageCom.Wageproject"/><!--工资项目 --></th>
					<th width="12"><spring:message code="pa.viewWageCom.Lastmonthdata"/><!--上月数据--></th>
					<th width="12"><spring:message code="pa.viewWageCom.monthdata"/><!--本月数据--></th>
					<th width="12"><spring:message code="pa.viewWageCom.Differencetwo"/><!--差额--></th>
					<th width="12"><spring:message code="pa.viewWageCom.Percentage"/><!--百分比--></th>
				</tr>
			</thead>
				<tbody>
		<c:forEach items="${getList}" var="getList" varStatus="i">
				<tr>	
					<td>${getList.LOCAL_NAME }</td>
					<td>${getList.PERSONID }</td>
					<td>${getList.DEPTNAME }</td>
					<td>${getList.ITEM }</td>
					<td>${getList.SHANGYUE }</td>
					<td>${getList.BENYUE }</td>
					<td>${getList.CHAE }</td>
					<td>${getList.BAIFENBI }%</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
</div>
<div class="pageContent" id="benyue" >

			<table class="table" width="100%" layoutH="200">
				<thead>
					<tr>
						<th width="12%"><spring:message code="public.title.name"/><!--姓名--></th>
						<th width="12%"><spring:message code="public.title.empId"/><!--工号--></th>
						<th width="12%"><spring:message code="public.title.deptName"/><!--部门--></th>
						<th width="12%"><spring:message code="pa.viewWageCom.Wageproject"/><!--工资项目 --></th>
						<th width="12%"><spring:message code="pa.viewWageCom.monthdata"/><!--本月数据--></th>
						<th width="12%"><spring:message code="pa.viewWageCom.Contrastjine"/><!--对比金额--></th>
						<th width="12%"><spring:message code="pa.viewWageCom.Differencetwo"/><!--差额--></td>
					</tr>
				</thead>
				<tbody>
				<%-- <c:forEach items="8" var="item" varStatus="i">
					<tr>	 --%>
		<c:forEach items="${getList}" var="getList" varStatus="i">
				<tr>	
					<td>${getList.LOCAL_NAME }</td>
					<td>${getList.PERSONID }</td>
					<td>${getList.DEPTNAME }</td>
					<td>${getList.ITEM }</td>
					<td>${getList.BENYUE }</td>
					<td>${getList.SHANGYUE }</td>
					<td>${getList.CHAE }</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
	</div>
</form>