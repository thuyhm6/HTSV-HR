<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

		function search_bx0104(form) {//搜索
		var leftDate = $("#leftDate", navTab.getCurrentPanel()).val();
		var year1 = $("#year1", navTab.getCurrentPanel()).val();
		var month1 = $("#month1", navTab.getCurrentPanel()).val();
		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);
		$form.attr("action", "/is/insurancesystem/createInstanceStopManagement");
		$.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					async : false,
					data : "seach_year1=" + year1 + "&seach_month1=" + month1
							+ "&seach_leftDate=" + leftDate,
					dataType : "json",
					cache : false,
					success : function(data) { //请求成功后处理函数。
						//alert(data.statusCode);
						if (data.statusCode == "200") {
							navTabNum(
									'/is/insurancesystem/viewBaseManagementForSearch?pageNum=1&menuNo=124902&navTabId=bx0104&method=create',
									'bx0104', '基数管理');
						} else if (data.statusCode == "300") {
							alertMsg.info(data.message);
						}
					}
				});
	}




	//删除数据，可进行批量的删除或者单一的删除
	function deleteData_bx0104(form) {
		var flag = false;

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);

		var cs = document.getElementsByName("check");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i].checked == true) {
				flag = true;
			}
		}
		if (flag) {
			var result = confirm("确定要删除么？");
			if (result == true) {
			$form.attr("action", "/is/insurancesystem/deleteStopInsureInfo");
				$.ajax({
							type :'POST',
							url : $form.attr("action"),
							async : false,
							data : $form.serializeArray(),
							dataType : "json",
							cache : false,
							success : function(data) { //请求成功后处理函数。
								if (data.statusCode == "200") {
									alertMsg.correct(data.message);
									navTabNum(
											'/is/insurancesystem/viewStopInsure?pageNum=1&menuNo=124905&navTabId=bx0104',
											'bx0104', '参保管理');
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
	//发令
   function order_bx0104(){
   		var result = confirm("确定要发令么？");
			if (result == true) {
				alert("发令成功");
				navTabNum('/is/insurancesystem/viewStopInsure?pageNum=1&menuNo=124905&navTabId=bx0104',
				'bx0104', '停保管理'); 
			}
   }
	 function save_bx0104(){//修改
	     
		var flag = false;
		var cs = document.getElementsByName("check");
		var ids="";
		for ( var i = 0; i < cs.length; i++) {
			if(cs[i].checked == true){
				ids+=cs[i].value+",";
				flag = true;
			}
		}
		if(flag){
			//alert(ids);
			$("#updateid_bx0104").attr("href","/is/insurancesystem/updateStopInsuranceNum?ids="+ids);
			$("#updateid_bx0104").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
			
		}else{
			alert('请选择修改项！');
			
		}
	} 
	//导出
	function expStopInsuranceNumInfo(a,navTabId){
		var $this = $(a);
  		var title = $this.attr("title");
  		var $from = $("#viewStopInsuranceInfo");
	     alertMsg.confirm(title, {okCall: function(){ doInstanceBaseNumExport($from);}});
    
	}
	function doInstanceBaseNumExport(from){
  	var $from =$(from);
  	var url ="/is/insurancesystem/insStopNumListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
   }
   
   //导入数据
   function importExcelStopInsure(){
   $("#importExcel").attr('href','/pa/excelImport/importExcelData?importFunName=/importExclInsatanceStopNum');
	$("#importExcel").click();
   }
   //下载导入模板
   function downloadImportStop(){
   document.InstanceBaseNumList.action="/pa/excelExport/downloadInstanceNum";
		document.InstanceBaseNumList.submit();
   
   }
	</script>
<a id="updateid_bx0104" rel="updateCPFStopManagement" mask="true" width="1200",height="400" target="dialog" />
<a id="orderid_bx0104" rel="orderCPFStopManagement" mask="true" width="1200",height="400" target="dialog" />
<div class="pageHeader" >
<form id="viewStopInsuranceInfo" onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewStopInsure" method="post" rel="pagerForm">
	<div class="searchBar">
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!--  搜索-->
								<spring:message code="ar.viewempcalender.title.search" />
							</button>
						</div>
					</div></li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="order_bx0104();"><!-- 发令 -->
								<spring:message code="display.emp.statistics.mes202" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	<table  class="searchContent">
		<tr>
			<td rowspan="2">
				<!-- 停保人员条件设定 --><b>停保人员条件设定</b>
			</td>
			<td width="200px"><!-- 上月缴纳住房公积金的人员中，截至 --><spring:message code="display.emp.statistics.mes220" /></td>
			<td align="left">
				<input type="text"  name="beforeDate" id="beforeDate" class="date"  readonly="true" style="width: 100px; " format="yyyy-MM-dd" yearstart="-40" yearend="5" onClick="setdate(this);"/>
				&nbsp;<spring:message code="display.emp.statistics.mes221" /><!--  离职人员 -->
			</td>
		</tr>
		<tr>
				<td width="220px"><spring:message code="display.emp.statistics.mes222" /><!--本月缴纳住房公积金的增加对象中，截至--></td>
				<td align="left">
					<input type="text"  name="afterDate" id="afterDate" class="date"  readonly="true" style="width: 100px; " format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					&nbsp;<spring:message code="display.emp.statistics.mes221" /><!--  离职人员 -->
				</td>
		</tr>
	</table>
			<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent" >
							<button type="button" id="exportExcel" onclick="javascript:downloadImportStop();">
								<spring:message code="pa.insurance.title.downloadImportTemplate" /> <!--下载导入模板-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="javascript:importExcelStopInsure();" >
								<spring:message code="ar.addempshift.title.excelimport" /> <!--Excel导入-->
							</button>
						</div>
					</div>						
				</li>
				<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"   onclick="expStopInsuranceNumInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>">
									<spring:message code="ar.addempshift.title.excelexport" /> <!--Excel导出--> 
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"   onclick="deleteData_bx0104('searchForm_bx0104_aa');">
									 <spring:message code="button.delete" /> <!--删除--> 
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="save_bx0104('searchForm_bx0104_aa');">
									<spring:message code="button.update" />  <!--修改--> 
								</button>
							</div>
						</div>
					</li>				
			</ul>
		</div>
		</div>
	</form>
</div>

<div class="pageContent" >
	<form name="searchForm_bx0104_aa" id="searchForm_bx0104_aa" method="post">
	<table class="table" width="101.8%" layoutH="160" >
		<thead>
			<tr>
			    <th width="50">
					<input type="checkbox" name="c1_bx0104_c" id="c1_bx0104_c" class="checkboxCtrl" group="check" >
				</th>
				<th width="50"><!-- 序号-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM"/>
				</th>
				<th width="100" ><!-- 部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="100" ><!-- 职号-->
					<spring:message code="display.emp.statistics.mes209"/>
				</th>
				<th width="80"><!-- 姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="50"><!-- 性别-->
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="130"><!-- 身份证号 -->
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="100"><!-- 公积金账号-->
					<spring:message code="display.emp.statistics.mes223" />
				</th>				
				<th width="100" ><!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="100" ><!-- 入社日期-->
					<spring:message code="display.emp.statistics.mes206"/>
				</th>
			
				<th width="100" ><!-- 离职日期-->
					<spring:message code="ess.trans.title.resignDate"/>
				</th>				
				<th width="100" ><!-- 终止缴纳月-->
					<spring:message code="display.emp.statistics.mes224" />
				</th>				
		</thead>
		<tbody>
			<c:forEach items="${stopList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
							<td class="td_center" style="white-space:nowrap">
								<input type="checkbox" name="check"  value="${show.PA_BEN_MANAGE_DEL_SEQ}" />
							</td>
							<td >
								${i.index + 1}&nbsp;
							</td>
							<td align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td >
								${show.EMPID}&nbsp;
							</td>
							<td >
								${show.CHINESENAME}&nbsp;
							</td>
							<td >
								${show.SEX_NAME}&nbsp;
							</td>
							<td >
								${show.IDCARD_NO}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.EMP_OFFICE_NAME}&nbsp;
							</td>
							<td >
								${show.DATE_STARTED}&nbsp;
							</td>
							<td >
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
							<td >
								<input type="hidden" name="empID" value="${show.EMPID}">
								<input type="hidden" name="yearMonth" value="${show.END_DATE}">
								${show.END_DATE}
							</td>
						</tr>
						</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/is/insurancesystem/viewStopInsure" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>


