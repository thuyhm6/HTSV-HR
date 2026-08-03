<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
		$(document).ready(function(){
				var type=document.getElementById("viewType").value;
				if(type=="update"){
						document.getElementById("viewBenBlock1").style.display="none";
						document.getElementById("viewBenBlock2").style.display="none";
						document.getElementById("viewBenNone1").style.display="block";
						document.getElementById("viewBenNone2").style.display="block";
				}else{
						document.getElementById("viewBenBlock1").style.display="block";
						document.getElementById("viewBenBlock2").style.display="block";
						document.getElementById("viewBenNone1").style.display="none";
						document.getElementById("viewBenNone2").style.display="none";
				
				}
						
						
		});
		function search(form){//搜索
			/* document.getElementById('logtype').value="READ" ; 
			document.searchForm.action="/paBenControlServlet?operation=paBen_benchmark_manage&menu_code=${menu_code}";
			showHidObje(maskArray,"","数据加载中");
			document.searchForm.submit(); */
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);
			var SearchDate=document.getElementById("SearchDate").value;
			$form.attr("action","/is/insurancesystem/searchBenchmarkManagement");
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
					navTabNum('/is/insurancesystem/ViewBenchmarkManagementForSearch?pageNum=1&menuNo=124901&navTabId=bx0101&SearchDate='+SearchDate,'bx0101','基准管理');
				}   
   	 		}); 
			
		}
		function callback()//修改
		{
			navTabNum('/is/insurancesystem/ViewBenchmarkManagementForSearch?pageNum=1&menuNo=124901&navTabId=bx0101','bx0101','基准管理');
		}
		function updateBenchmark1(form){
						document.getElementById("viewBenBlock1").style.display="none";
						document.getElementById("viewBenBlock2").style.display="none";
						document.getElementById("viewBenNone1").style.display="block";
						document.getElementById("viewBenNone2").style.display="block";
						
		}
		function updateBenchmark(form){
		   if(!confirm('当前作出的修改，只有在生成版本以后才可生效！'))
			{
			    return;
			}
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);
 				
			document.getElementById('logtype').value="UPDATE" ; 
			$form.attr("action","/is/insurancesystem/ViewBenchmarkManagementForSearchUpdate");
			 
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
					if(data.type="UPDATE"){
						document.getElementById("viewBenBlock1").style.display="none";
						document.getElementById("viewBenBlock2").style.display="none";
						document.getElementById("viewBenNone1").style.display="block";
						document.getElementById("viewBenNone2").style.display="block";
						navTabNum('/is/insurancesystem/ViewBenchmarkManagementForSearch?pageNum=1&menuNo=124901&navTabId=bx0101&logtype=UPDATE','bx0101','基准管理');
						
					}
				}   
   	 		}); 
		
		}
		function createVersion(form){//生成版本
		
			
			if(!confirm('<spring:message code="display.emp.statistics.mes189" />'))
			{
			    return;
			}
			
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);

			$form.attr("action","/is/insurancesystem/createVersion");
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
							alertMsg.info(data.message);
							navTabNum('/is/insurancesystem/ViewBenchmarkManagementForSearch?pageNum=1&menuNo=124901&navTabId=bx0101','bx0101','基准管理');
					}else if(data.statusCode=="300"){
							alertMsg.info(data.message);
					}
				}   
   	 		}); 
			
			
			//document.searchForm.action="/paBenControlServlet?operation=paBen_benchmark_manage&method=create&menu_code=${menu_code}";
			//showHidObje(maskArray,"","数据加载中");
			//document.searchForm.submit();
		}
		function update(form){
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);

			$form.attr("action","/is/insurancesystem/updateBenchmarkManagement");
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
			    
					if(data.statusCode=="200"){
							
					}else if(data.statusCode=="300"){
							alertMsg.info(data.message);
					}
				}   
   	 		}); 
		}
		function validateCallback_updateBenchmarkManagement(form, callback) {
			var $form = $(form);
	
			if (!$form.valid()) {
				return false;
			}
	
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
							alertMsg.info(data.message);
							navTabNum('/is/insurancesystem/ViewBenchmarkManagementForSearch?pageNum=1&menuNo=124901&navTabId=bx0101&SearchDate=now','bx0101','基准管理');
					}else if(data.statusCode=="300"){
							alertMsg.info(data.message);
					}
				} ,
				error: DWZ.ajaxError
			});
	
			return false;
		
		}
	</script>

<input type="hidden" value="${type}"  id="viewType"/>
<div class="pageHeader" id="viewBenBlock1">
<form id="ViewBenchmarkSearchForm" name="ViewBenchmarkSearchForm" method="post"  action="/is/insurancesystem/updateBenchmarkManagement" class="pageForm required-validate" onsubmit="return validateCallback_updateBenchmarkManagement(this,navTabAjaxDone);">
	
                <input type="hidden" name="logflag" id="logflag" value="true">
                <input type="hidden" name="logtype" id="logtype" value="READ">
                <input type="hidden" name="logcode" id="logcode" value="RE">
	<div class="searchBar">
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="updateBenchmark('ViewBenchmarkSearchForm')">修改</button>
						</div>
					</div></li>
					
					
				
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="createVersion('ViewBenchmarkSearchForm')">生成版本</button>
						</div>
					</div></li>
			</ul>
		</div>
	</div>
	<table width="100%">
		<tr>
			<td width="25%" style="padding: 4px;"><spring:message
					code="display.emp.statistics.mes178" />
				<!-- 版本日期 -->
			</td>
			<td>
			<select id="SearchDate" name="SearchDate" onchange="search('ViewBenchmarkSearchForm')">
					<c:if test="${allow}">
						<option value="now">
							<spring:message code="display.emp.statistics.mes181" />

						</option>
					</c:if>
					<c:forEach items="${versionList}" var="version">
						<option
							<c:if test="${version.VERSION_DATE eq searchDate}">
														selected
													</c:if>
							value="${version.VERSION_DATE }">${version.VERSION_DATE
							}</option>
					</c:forEach>
					<c:if test="${not allow}">
						<option value="now">
							<spring:message code="display.emp.statistics.mes181" />
						</option>
					</c:if>
			</select></td>
		</tr>
	</table>
</div>

<div class="pageContent" id="viewBenBlock2" >
	<table class="table" width="100%" layoutH="138" nowrapTD="false">
		<thead>
			<tr>
				<th width="60" style="text-align: center">
					<!-- 序号 --> <spring:message code="display.mutual.no" />
				</th>
				<th width="100" style="text-align: center">
					<!-- 保险名称 --> <spring:message code="display.emp.statistics.mes182" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 户口性质 --> <spring:message code="display.mutual.hukou" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 版本日期 --> <spring:message code="display.emp.statistics.mes180" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 缴费基数上限 --> <spring:message
						code="display.emp.statistics.mes183" />
				</th>

				<th width="100" style="text-align: center">
					<!-- 缴费基数下限 --> <spring:message
						code="display.emp.statistics.mes184" />
				</th>
				<th width="200" style="text-align: center">
					<!-- 公司（比例） --> <spring:message
						code="display.emp.statistics.mes185" />
				</th>
				<th width="80" style="text-align: center">
					<!-- 个人（比例） --> <spring:message
						code="display.emp.statistics.mes186" />
				</th>

			</tr>
		</thead>
		
		<c:forEach items="${standardNotSerious}" var="show" varStatus="i">
			<tr>
				<c:if test="${i.first}">
					<td class="info_content_01" rowspan="5">1</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<c:if test="${i.index eq 5}">
					<td class="info_content_01" rowspan="5">2</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<c:if test="${i.index eq 10}">
					<td class="info_content_01" rowspan="5">3</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<c:if test="${i.index eq 15}">
					<td class="info_content_01" rowspan="5">4</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<c:if test="${i.index eq 20}">
					<td class="info_content_01" rowspan="5">5</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<td class="info_content_01">${show.REG_TYPE_NAME}</td>
				<td class="info_content_01">
				<c:choose>
						<c:when test="${empty show.VERSION_DATE}">当前</c:when>
						<c:otherwise>${show.VERSION_DATE}</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.UPPER_LIMIT or show.UPPER_LIMIT eq 0}">-</c:when>
						<c:otherwise>${show.UPPER_LIMIT}</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.LOWER_LIMIT or show.LOWER_LIMIT eq 0}">-</c:when>
						<c:otherwise>${show.LOWER_LIMIT}</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.COR_RATE or show.COR_RATE eq 0}">-</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${show.COR_RATE}" type="percent"
								maxFractionDigits="2" />
						</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.PER_RATE or show.PER_RATE eq 0}">-</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${show.PER_RATE}" type="percent"
								maxFractionDigits="2" />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
		
	</table>
	<table width="100%" height="20" border="0" cellpadding="0"
		cellspacing="1">
		<tr>
			<td></td>
		</tr>
	</table>

	<table class="table" width="100%" layoutH="138" nowrapTD="false">
		<thead>
			<tr>
			<th width="120" style="text-align: center">
				<!-- 序号 --> <spring:message code="display.mutual.no" />
			</th>
			<th width="200" style="text-align: center">
				<!-- 保险名称 --> <spring:message code="display.emp.statistics.mes182" />
			</th>
			<th width="240" style="text-align: center">
				<!-- 户口性质 --> <spring:message code="display.mutual.hukou" />
			</th>
			<th width="240" style="text-align: center">
				<!-- 版本日期 --> <spring:message code="display.emp.statistics.mes180" />
			</th>
			<th class="info_title_01" width="240">
				<!-- 公司承担（固定） --> 
				<!-- 户口性质 --> <spring:message code="display.emp.statistics.mes187" />
			</th>
			<th class="info_title_01" width="240">
				<!-- 个人承担（固定） -->
				 <spring:message code="display.emp.statistics.mes188" />
			</th>
		</tr>
		</thead>
		<c:forEach items="${standardSerious}" var="show" varStatus="i">
			<tr>
				<c:if test="${i.first}">
					<td class="info_content_01" rowspan="5">1</td>
					<td class="info_content_01" rowspan="5">
						${show.INSURANCE_NAME}</td>
				</c:if>
				<td class="info_content_01">${show.REG_TYPE_NAME}</td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.VERSION_DATE}">当前</c:when>
						<c:otherwise>${show.VERSION_DATE}</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.COR_VALUE or show.COR_VALUE eq 0}">-</c:when>
						<c:otherwise>${show.COR_VALUE}</c:otherwise>
					</c:choose></td>
				<td class="info_content_01"><c:choose>
						<c:when test="${empty show.PER_VALUE or show.PER_VALUE eq 0}">-</c:when>
						<c:otherwise>${show.PER_VALUE}</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="1">
		<c:forEach var="i" begin="1" end="4" step="1">
			<tr>
				<td class="info_content_01" height="30"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
			</tr>
		</c:forEach>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		height="15">
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
</form>

<!-- 保存  修改 -->
<form id="ViewBenchmarkSearchForm1" name="ViewBenchmarkSearchForm1" method="post"  action="/is/insurancesystem/updateBenchmarkManagement" class="pageForm required-validate" onsubmit="return validateCallback_updateBenchmarkManagement(this,navTabAjaxDone);">
<div class="pageHeader" id="viewBenNone1" >
	<div class="searchBar">
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
				
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="callback()">返回</button>
						</div>
					</div></li>
			</ul>
		</div>
	</div>
	
</div>

<div class="pageContent" id="viewBenNone2">
	<table class="table" width="100%" layoutH="138" nowrapTD="false" id="viewBenNone2table">
		<thead>
			<tr>
				<th width="60" style="text-align: center">
					<!-- 序号 --> <spring:message code="display.mutual.no" />
				</th>
				<th width="100" style="text-align: center">
					<!-- 保险名称 --> <spring:message code="display.emp.statistics.mes182" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 户口性质 --> <spring:message code="display.mutual.hukou" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 版本日期 --> <spring:message code="display.emp.statistics.mes180" />
				</th>
				<th width="140" style="text-align: center">
					<!-- 缴费基数上限 --> <spring:message
						code="display.emp.statistics.mes183" />
				</th>

				<th width="100" style="text-align: center">
					<!-- 缴费基数下限 --> <spring:message
						code="display.emp.statistics.mes184" />
				</th>
				<th width="200" style="text-align: center">
					<!-- 公司（比例） --> <spring:message
						code="display.emp.statistics.mes185" />
				</th>
				<th width="80" style="text-align: center">
					<!-- 个人（比例） --> <spring:message
						code="display.emp.statistics.mes186" />
				</th>

			</tr>
		</thead>
		
		<c:forEach items="${standardNotSerious}" var="show" varStatus="i">
		
			<tr>
				<c:if test="${i.first}">
								<td class="info_content_01" rowspan="5">
									1
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if>
							<c:if test="${i.index eq 5}">
								<td class="info_content_01" rowspan="5">
									2
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if>
							<c:if test="${i.index eq 10}">
								<td class="info_content_01" rowspan="5">
									3
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if>
							<c:if test="${i.index eq 15}">
								<td class="info_content_01" rowspan="5">
									4
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if><c:if test="${i.index eq 20}">
								<td class="info_content_01" rowspan="5">
									5
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if>
							<input type="hidden" name="insturanceCode" value="${show.INSURANCE_CODE}">
							<input type="hidden" name="regTypeCode" value="${show.REG_TYPE_CODE}">
							<td class="info_content_01">
								${show.REG_TYPE_NAME}
							</td>
							<td class="info_content_01">
								${show.VERSION_DATE}
							</td>
							<td class="info_content_01">
								<input type="text" name="upperLimit" value="${show.UPPER_LIMIT}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
							<td class="info_content_01">
								<input type="text" name="lowerLimit" value="${show.LOWER_LIMIT}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
							<td class="info_content_01">
								<input type="text" name="corRate" value="${show.COR_RATE}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
							<td class="info_content_01">
								<input type="text" name="perRate" value="${show.PER_RATE}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
							<input type="hidden" name="corValue" value="0">
							<input type="hidden" name="perValue" value="0">
			</tr>
			
		</c:forEach>
	</table>
	<table width="100%" height="20" border="0" cellpadding="0"
		cellspacing="1">
		<tr>
			<td></td>
		</tr>
	</table>

	<table class="table" width="100%" layoutH="138" nowrapTD="false">
		<thead>
			<tr>
			<th width="120" style="text-align: center">
				<!-- 序号 --> <spring:message code="display.mutual.no" />
			</th>
			<th width="200" style="text-align: center">
				<!-- 保险名称 --> <spring:message code="display.emp.statistics.mes182" />
			</th>
			<th width="240" style="text-align: center">
				<!-- 户口性质 --> <spring:message code="display.mutual.hukou" />
			</th>
			<th width="240" style="text-align: center">
				<!-- 版本日期 --> <spring:message code="display.emp.statistics.mes180" />
			</th>
			<th style="text-align: center" width="240">
				<!-- 公司承担（固定） --> 
				<!-- 户口性质 --> <spring:message code="display.emp.statistics.mes187" />
			</th>
			<th  width="240" style="text-align: center">
				<!-- 个人承担（固定） -->
				 <spring:message code="display.emp.statistics.mes188" />
			</th>
		</tr>
		</thead>
		<c:forEach items="${standardSerious}" var="show" varStatus="i">
			
				<tr>
							<c:if test="${i.first}">
								<td class="info_content_01" rowspan="5">
									1
								</td>
								<td class="info_content_01" rowspan="5">
									${show.INSURANCE_NAME}
								</td>
							</c:if>
							<td class="info_content_01">
							<input type="hidden" name="insturanceCode" value="${show.INSURANCE_CODE}">
							<input type="hidden" name="regTypeCode" value="${show.REG_TYPE_CODE}">
							<input type="hidden" name="upperLimit" value="0">
							<input type="hidden" name="lowerLimit" value="0">
							<input type="hidden" name="corRate" value="0">
							<input type="hidden" name="perRate" value="0">
								${show.REG_TYPE_NAME}
							</td>
							<td class="info_content_01">
								${show.VERSION_DATE}
							</td>
							<td class="info_content_01">
								<input type="text" name="corValue" value="${show.COR_VALUE}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
							<td class="info_content_01">
								<input type="text" name="perValue" value="${show.PER_VALUE}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
							</td>
						
			</tr>
		</c:forEach>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="1">
		<c:forEach var="i" begin="1" end="4" step="1">
			<tr>
				<td class="info_content_01" height="30"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
				<td class="info_content_01"></td>
			</tr>
		</c:forEach>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		height="15">
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>

</form>