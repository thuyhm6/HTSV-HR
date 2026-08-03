<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
		$(document).ready(function(){
		});
		
		
		function save_bx0204(){//修改
		var flag = false;
		var cs = document.getElementsByName("checkid");
		var ids="";
		for ( var i = 0; i < cs.length; i++) {
			if(cs[i].checked == true){
				ids+=cs[i].value+",";
				flag = true;
			}
		}
		if(flag){
			$("#updateid_bx0204").attr("href","/is/accumulationfundmanage/updateStopBenshObjectManage?ids="+ids);
			$("#updateid_bx0204").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
			
		}else{
			alert('请选择修改项！');
		}
	}
	
	
	function deleteDate_bx0204(form) {//删除
		//"确定要删除吗?"
		// document.getElementById('logtype').value="DELETE" ;
		var flag = false;

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);

		var cs = document.getElementsByName("checkid");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i].checked == true) {
				flag = true;
			}
		}

		if (flag) {
			//var result = confirm("确定要删除么？");
			var result = confirm("确定要删除么？");
			if (result == true) {
			$form.attr("action", "/is/accumulationfundmanage/deleteBenshObjectManageDel");
			
				$.ajax({
							type : form.method || 'POST',
							url : $form.attr("action"),
							async : false,
							data : $form.serializeArray(),
							dataType : "json",
							cache : false,
							success : function(data) { //请求成功后处理函数。
								if (data.statusCode == "200") {
									alertMsg.correct(data.message);
									navTabNum(
'/is/accumulationfundmanage/ViewCPFStopInsure?pageNum=1&menuNo=124914&navTabId=bx0204',
											'bx0204', '对象减少');
								} else if (data.statusCode == "300") {
									alertMsg.info(data.message);
								}
							}
						});
			} else {
				return false;
			}
		} else {
			alertMsg.info('请选择删除项！');

		}
	}
	
	function importManageDelFundNum() {
		$("#importExcel")
				.attr('href',
						'/pa/excelImport/importExcelData?importFunName=/importManageDelFundNum');
		$("#importExcel").click();

	}
	
		
	</script>

<a id="updateid_bx0204" rel="updateStopBenshObjectManage" mask="true" width="1200",height="400" target="dialog"></a>
<a id="orderid_bx0204" rel="orderCPFBaseManagement" mask="true" width="1200",height="400" target="dialog"></a>
<form name="searchForm_bx0204a" id="searchForm_bx0204a" method="post">
<div class="pageHeader" >
    
	<div class="searchBar">
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							
							<button type="button"><!--  搜索-->
								<spring:message code="ar.viewempcalender.title.search" />
							</button>
						</div>
					</div></li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="javascript:deleteDate_bx0204('searchForm_bx0204a');">
							<spring:message code="button.delete"/><!--删除-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
						
							<button type="button"><!-- 发令 -->
								<spring:message code="display.emp.statistics.mes202" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		
	</div>
	
	<table width="100%" class="searchContent">
		
		<tr>
			<td rowspan="2">
				
				<!-- 减少人员条件设定 -->
			</td>
			<td width="200px"><!-- 上月缴纳住房公积金的人员中，截至 --><spring:message code="display.emp.statistics.mes220" /></td>
			<td align="left">
				
				<input type="text"  name="beforeDate" id="beforeDate" class="date"  readonly="true" style="width: 100px; " format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				<spring:message code="display.emp.statistics.mes221" /><!--  离职人员 -->
			</td>
			
		</tr>
		<tr>
				<td width="220px"><spring:message code="display.emp.statistics.mes222" /><!--本月缴纳住房公积金的增加对象中，截至--></td>
				<td align="left">
					
					<input type="text"  name="afterDate" id="afterDate" class="date"  readonly="true" style="width: 100px; " format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					<spring:message code="display.emp.statistics.mes221" /><!--  离职人员 -->
				</td>
				<td>
					<a style="float:right; " class="buttonActive" id ="exportExcel" onclick="downloadImportTemplate();"  href="#"><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span></a>
					<a style="float:right; " class="buttonActive" id ="importExcel" onclick="importManageDelFundNum();" href="#" target="dialog"><span><spring:message code="ar.addempshift.title.excelimport"/><!--Excel导入--></span></a>
					<a style="float:right; " class="buttonActive" id ="exportExcel" onclick="downloadImportTemplate();" href="#"><span><spring:message code="ar.addempshift.title.excelexport"/><!--Excel导出--></span></a>
					<a style="float:right; " class="buttonActive" id ="exportExcel" onclick="save_bx0204();" href="#"><span><spring:message code="button.update"/><!--修改--></span></a>
				</td>
		</tr>
		
	</table>
</div>
<a id="updateid_bx0204" rel="updateStopBenshObjectManage" mask="true" width="1200",height="400" target="dialog"></a>
<a id="orderid_bx0204" rel="orderCPFBaseManagement" mask="true" width="1200",height="400" target="dialog"></a>
<div class="pageContent" >
	<table class="table" width="101.8%" layoutH="100"asc="asc" desc="desc" >
		<thead>
			<tr>
			    <th width="100" ><!-- 复选框-->
			    	 <input type="checkbox" name="c1_hr0302" id="c1_hr0302">
			    </th>
				<th width="100"><!-- 序号-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM"/>
				</th>
				<th width="100" class="asc"><!-- 部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="100" class="asc"><!-- 职号-->
					<spring:message code="display.emp.statistics.mes209"/>
				</th>
				<th width="100"><!-- 姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="100"><!-- 性别-->
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="100"><!-- 身份证号 -->
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="100"><!-- 公积金账号-->
					<spring:message code="display.emp.statistics.mes223" />
				</th>				
				<th width="100" class="asc"><!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="100" class="asc"><!-- 入社日期-->
					<spring:message code="display.emp.statistics.mes206"/>
				</th>
			
				<th width="100" class="asc"><!-- 离职日期-->
					<spring:message code="ess.trans.title.resignDate"/>
				</th>				
				<th width="100" class="asc"><!-- 终止缴纳月-->
					<spring:message code="display.emp.statistics.mes224" />
				</th>				
		</thead>
		<tbody>
			<c:forEach items="${stopList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
							<td >
								<input type="checkbox" name="checkid" value="${show.PA_BENHS_MANAGE_DEL_SEQ}"/>
							</td>
							<td >
								${i.index + 1}&nbsp;
							</td>
							<td align="left">
								${show.CONTENT}&nbsp;
							</td>
							<td >
								${show.EMPID}&nbsp;
							</td>
							<td >
								${show.LOCAL_NAME}&nbsp;
							</td>
							<td >
								${show.SEXNAME}&nbsp;
							</td>
							<td >
								${show.IDCARD_NO}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.STATUS_CODE_NAME}&nbsp;
							</td>
							<td >
								${show.DATE_STARTED1}&nbsp;
							</td>
							<td >
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
							<td >
								<input type="hidden" name="empID" value="${show.PERSON_ID}">
								<input type="hidden" name="yearMonth" value="${show.END_DATE}">
								${show.END_DATE}
							</td>
						</tr>
						</c:forEach>
		</tbody>
	</table>
	
</div>
</form>

