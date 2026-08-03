<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
		$(document).ready(function(){
				var type=document.getElementById("viewType_BX0201").value;
				//alert(type);
				if(type=="update"){
						document.getElementById("viewBenBlock1_BX0201").style.display="none";
						document.getElementById("viewBenBlock2_BX0201").style.display="none";
						document.getElementById("viewBenNone1_BX0201").style.display="block";
						document.getElementById("viewBenNone2_BX0201").style.display="block";
				}else{
						document.getElementById("viewBenBlock1_BX0201").style.display="block";
						document.getElementById("viewBenBlock2_BX0201").style.display="block";
						document.getElementById("viewBenNone1_BX0201").style.display="none";
						document.getElementById("viewBenNone2_BX0201").style.display="none";
				}
						
						
		});
		function Save_BX0201(){
			if(!confirm('当前作出的修改，只有在生成版本以后才可生效！'))
			{
			    return;
			}
			document.form1.action="/paBenControlServlet?operation=paBen_benchmark_manage&method=save&SearchDate=now&menu_code=${menu_code}";
			showHidObje(maskArray,"","数据加载中");
			document.form1.submit();
		}
		function back_BX0201(){
			document.form1.action="/paBenControlServlet?operation=paBen_benchmark_manage&menu_code=${menu_code}";
			showHidObje(maskArray,"","数据加载中");
			document.form1.submit();
		}
		//修改返回
		function callback_BX0201()
		{
			navTabNum('/is/accumulationfund/ViewCPFBenchmarkManagementForSearch?pageNum=1&menuNo=124911&navTabId=bx0201','bx0201','基准管理');
		}

		//修改
		function updateBenchmark_BX0201(form){
		   
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);
 				
			document.getElementById('logtype_BX0201').value="UPDATE" ; 
			$form.attr("action","/is/accumulationfund/ViewBenchmarkManagementForSearchUpdate");
			
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
					if(data.type="UPDATE"){
						//document.getElementById("viewBenBlock1").style.display="none";
						//document.getElementById("viewBenBlock2").style.display="none";
						//document.getElementById("viewBenNone1").style.display="block";
						//document.getElementById("viewBenNone2").style.display="block";
						navTabNum('/is/accumulationfund/ViewCPFBenchmarkManagementForSearch?pageNum=1&menuNo=124911&navTabId=bx0201&logtype_BX0201=UPDATE','bx0201','基准管理');
						
					}
				}   
   	 		}); 
		}
		
		//修改把保存
		function validateCallback_updateBenchmarkManagementBX0201(form, callback) {
			var $form = $(form);
	
			if (!$form.valid()) {
				return false;
			}
	
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				async:false,
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
							alertMsg.info(data.message);
							navTabNum('/is/accumulationfund/ViewCPFBenchmarkManagementForSearch?pageNum=1&menuNo=124911&navTabId=bx0201&SearchDate=now','bx0201','基准管理');
					}else if(data.statusCode=="300"){
							alertMsg.info(data.message);
					}
				} ,
				error: DWZ.ajaxError
			});
			return false;
		
		}
		
		function search_BX0201(form){//搜索
			
			//document.getElementById('logtype_BX0201').value="" ; 
			
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);
			var SearchDate=document.getElementById("SearchDate_BX0201").value;
			$form.attr("action","/is/insurancesystem/searchBenchmarkManagement");
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			async:false,
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
						
					navTabNum('/is/accumulationfund/ViewCPFBenchmarkManagementForSearch?pageNum=1&menuNo=124911&navTabId=bx0201&SearchDate='+SearchDate,'bx0201','基准管理');
				}   
   	 		}); 
			
		}
		
		
	function createVersion_BX0201(form){//生成版本
		
			
			if(!confirm('<spring:message code="display.emp.statistics.mes189" />'))
			{
			    return;
			}
			
			var $form=null;
			if($('#'+form).length>0)
				$form=$('#'+form);
			else
 				$form = $(form);

			$form.attr("action","/is/accumulationfund/createVersion");
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
							alertMsg.info(data.message);
							navTabNum('/is/accumulationfund/ViewCPFBenchmarkManagementForSearch?pageNum=1&menuNo=124911&navTabId=bx0201','bx0201','基准管理');
					}else if(data.statusCode=="300"){
							alertMsg.info(data.message);
					}
				}   
   	 		}); 
			
			
			//document.searchForm.action="/paBenControlServlet?operation=paBen_benchmark_manage&method=create&menu_code=${menu_code}";
			//showHidObje(maskArray,"","数据加载中");
			//document.searchForm.submit();
		}
</script>
<input type="hidden" value="${type}" id="viewType_BX0201" />

<form id="searchForm_BX0201" name="searchForm_BX0201" method="post"
	onsubmit="return navTabSearch(this);"
	action="/is/insurancesystem/ViewBenchmarkManagementForSearch"
	rel="viewBenNone2">	
	<div class="pageHeader" id="viewBenBlock1_BX0201">
	
	<input type="hidden" name="logtype_BX0201" id="logtype_BX0201" value="READ">
		<div class="searchBar">
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="updateBenchmark_BX0201('searchForm_BX0201');">修改</button>
							</div>
						</div></li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="createVersion_BX0201('searchForm_BX0201')">生成版本</button>
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
				<td><select id="SearchDate_BX0201" name="SearchDate" onchange="search_BX0201('searchForm_BX0201');">
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
					</c:if></td>
			</tr>
		</table>
	</div>
	<div class="pageContent" id="viewBenBlock2_BX0201">
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
			<c:forEach items="${itemList}" var="show" varStatus="i">
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
					<td class="info_content_01"><c:choose>
							<c:when test="${empty show.VERSION_DATE}">当前</c:when>
							<c:otherwise>${show.VERSION_DATE}</c:otherwise>
						</c:choose>
					</td>
					<td class="info_content_01"><c:choose>
							<c:when test="${empty show.UPPER_LIMIT or show.UPPER_LIMIT eq 0}">-</c:when>
							<c:otherwise>${show.UPPER_LIMIT}</c:otherwise>
						</c:choose>
					</td>
					<td class="info_content_01"><c:choose>
							<c:when test="${empty show.LOWER_LIMIT or show.LOWER_LIMIT eq 0}">-</c:when>
							<c:otherwise>${show.LOWER_LIMIT}</c:otherwise>
						</c:choose>
					</td>
					<td class="info_content_01"><c:choose>
							<c:when test="${empty show.COR_RATE or show.COR_RATE eq 0}">-</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${show.COR_RATE}" type="percent"
									maxFractionDigits="2" />
							</c:otherwise>
						</c:choose>
					</td>
					<td class="info_content_01"><c:choose>
							<c:when test="${empty show.PER_RATE or show.PER_RATE eq 0}">-</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${show.PER_RATE}" type="percent"
									maxFractionDigits="2" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
</div>
</form>
<form id="ViewBenchmarkSearchFormBX0201" name="ViewBenchmarkSearchFormBX0201" method="post"  action="/is/accumulationfund/updateCPFBenchmarkManagement" class="pageForm required-validate" onsubmit="return validateCallback_updateBenchmarkManagementBX0201(this,navTabAjaxDone);">
		<!-- 保存  修改 -->

		<div class="pageHeader" id="viewBenNone1_BX0201">
			<div class="searchBar">
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="callback_BX0201()">返回</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>

		</div>
	<div class="pageContent" id="viewBenNone2_BX0201">
		<table class="table" width="100%" layoutH="138" nowrapTD="false"
			id="viewBenNone2table">
			<thead>
				<tr>
					<th width="60" style="text-align: center">
						<!-- 序号 --> <spring:message code="display.mutual.no" /></th>
					<th width="100" style="text-align: center">
						<!-- 保险名称 --> <spring:message code="display.emp.statistics.mes182" />
					</th>
					<th width="140" style="text-align: center">
						<!-- 户口性质 --> <spring:message code="display.mutual.hukou" /></th>
					<th width="140" style="text-align: center">
						<!-- 版本日期 --> <spring:message code="display.emp.statistics.mes180" />
					</th>
					<th width="140" style="text-align: center">
						<!-- 缴费基数上限 --> <spring:message
							code="display.emp.statistics.mes183" /></th>

					<th width="100" style="text-align: center">
						<!-- 缴费基数下限 --> <spring:message
							code="display.emp.statistics.mes184" /></th>
					<th width="200" style="text-align: center">
						<!-- 公司（比例） --> <spring:message
							code="display.emp.statistics.mes185" /></th>
					<th width="80" style="text-align: center">
						<!-- 个人（比例） --> <spring:message
							code="display.emp.statistics.mes186" /></th>

				</tr>
			</thead>

			<c:forEach items="${itemList}" var="show" varStatus="i">

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
					<input type="hidden" name="insturanceCode"
						value="${show.INSURANCE_CODE}">
					<input type="hidden" name="regTypeCode"
						value="${show.REG_TYPE_CODE}">
					<td class="info_content_01">${show.REG_TYPE_NAME}</td>
					<td class="info_content_01">${show.VERSION_DATE}</td>
					<td class="info_content_01"><input type="text"
						name="upperLimit" value="${show.UPPER_LIMIT}" size="10"
						style="text-align: right"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
					<td class="info_content_01"><input type="text"
						name="lowerLimit" value="${show.LOWER_LIMIT}" size="10"
						style="text-align: right"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
					<td class="info_content_01"><input type="text" name="corRate"
						value="${show.COR_RATE}" size="10" style="text-align: right"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
					<td class="info_content_01"><input type="text" name="perRate"
						value="${show.PER_RATE}" size="10" style="text-align: right"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
					<input type="hidden" name="corValue" value="0">
					<input type="hidden" name="perValue" value="0">
				</tr>

			</c:forEach>
		</table>
	</div>
	</form>