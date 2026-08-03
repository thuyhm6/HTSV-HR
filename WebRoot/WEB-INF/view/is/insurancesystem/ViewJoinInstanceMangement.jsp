<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		var message = "${message}";
		if (message != '') {
			alertMsg.info("${message}");
		}
	});
		function search_bx0102(form) {//搜索
		var leftDate = $("#leftDate", navTab.getCurrentPanel()).val();
		var year1 = $("#year1", navTab.getCurrentPanel()).val();
		var month1 = $("#month1", navTab.getCurrentPanel()).val();
		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);
		$form.attr("action", "/is/insurancesystem/createInstanceJoinManagement");
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
									'/is/insurancesystem/viewBaseManagementForSearch?pageNum=1&menuNo=124902&navTabId=bx0102&method=create',
									'bx0102', '基数管理');
						} else if (data.statusCode == "300") {
							alertMsg.info(data.message);
						}
					}
				});
	}




	//删除数据，可进行批量的删除或者单一的删除
	function deleteData_bx0103(form) {
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
			$form.attr("action", "/is/insurancesystem/deleteJoinInsureInfo");
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
											'/is/insurancesystem/viewJoinInstanceMangement?pageNum=1&menuNo=124904&navTabId=bx0103',
											'bx0103', '参保管理');
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
	
	 function save_bx0103(){//修改
	     
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
			$("#updateid_bx0103").attr("href","/is/insurancesystem/updateJoinInsuranceNum?ids="+ids);
			$("#updateid_bx0103").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
			
		}else{
			alert('请选择修改项！');
			
		}
	} 
	//导出
	function expJoinInsuranceNumInfo(a,navTabId){
		var $this = $(a);
  		var title = $this.attr("title");
  		var $from = $("#viewJoinInsureInfo");
	     alertMsg.confirm(title, {okCall: function(){ doInstanceBaseNumExport($from);}});
    
	}
	function doInstanceBaseNumExport(from){
  	var $from =$(from);
  	var url ="/is/insurancesystem/insJoinNumListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
   }
   //发令
   function order_bx0103(){
   		var result = confirm("确定要发令么？");
			if (result == true) {
				alert("发令成功");
				navTabNum('/is/insurancesystem/viewJoinInstanceMangement?pageNum=1&menuNo=124904&navTabId=bx0103',
				'bx0103', '参保管理');
			}
   }
   //导入数据
   function importExcelJoinInsure(){
   $("#importJoinExcel").attr('href','/pa/excelImport/importExcelData?importFunName=/importExclInsatanceJoinNum');
	$("#importJoinExcel").click();
   }
   //下载导入模板
   function downloadImportJoinTemplate(){
   		  document.searchForm_bx0103_aa.action="/is/insurancesystem/downloadJoinTemplete";
		document.searchForm_bx0103_aa.submit();
   }
</script>
<a id="importJoinExcel"  href="#" target="dialog" mask="true"></a>
<a id="updateid_bx0103" rel="updateCPFBaseManagement" mask="true" width="1200",height="400" target="dialog" />
<a id="orderid_bx0103" rel="orderCPFBaseManagement" mask="true" width="1200",height="400" target="dialog" />
<div class="pageHeader">
	<form id="viewJoinInsureInfo" onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewJoinInstanceMangement" method="post" rel="pagerForm">
	<div class="searchBar">
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" >
								<spring:message code="ar.viewempcalender.title.search" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="expExpiredContract(this)" >
								<spring:message code="is.viewJoinInstance.remove" />
							</button>
						</div>
					</div>						
				</li>
				<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"  onclick="order_bx0103();">
									<!-- 发令 -->
									<spring:message code="display.emp.statistics.mes202" />
								</button>
							</div>
						</div>
					</li>
			</ul>
		</div>
		<table class="searchContent">
			<tr>
		   		<td>
					<span class="span_left"><spring:message code="is.joininstance.title.Conditionsetting"/><!-- 参保人员条件设定--></span>
					<input type="text" name="seach_joinp_CONTRACT_DATE" class="date" readonly="true" value="${joinp_CONTRACT_DATE }"/><span class="span_left">
					 <spring:message code="is.joininstance.starting"/><!-- 以前进行入社发令，截至--></span>
				</td>
				<td>
					<input type="text" name="seach_joinl_CONTRACT_DATE" class="date" readonly="true" value="${joinl_CONTRACT_DATE }"/>
					<sapn class="span_left"><spring:message code="is.joininstance.message888"/><!-- 在职，未缴纳社会保险的正规职人员--></sapn>
				<td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent" >
							<button type="button" id="exportJoinExcel" onclick="javascript:downloadImportJoinTemplate();">
								<spring:message code="pa.insurance.title.downloadImportTemplate" /> <!--下载导入模板-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="javascript:importExcelJoinInsure();" >
								<spring:message code="ar.addempshift.title.excelimport" /> <!--Excel导入-->
							</button>
						</div>
					</div>						
				</li>
				<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"   onclick="expJoinInsuranceNumInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>">
									<spring:message code="ar.addempshift.title.excelexport" /> <!--Excel导出--> 
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"   onclick="deleteData_bx0103('searchForm_bx0103_aa');">
									 <spring:message code="button.delete" /> <!--删除--> 
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="save_bx0103('searchForm_bx0103_aa');">
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

<div class="pageContent">
<form name="searchForm_bx0103_aa" id="searchForm_bx0103_aa" method="post">
	<table class="table" width="101.8%" layoutH="160" >
		<thead>
			<tr >
				<th width="50">
					<input type="checkbox" name="c1_bx0103_c" id="c1_bx0103_c" class="checkboxCtrl" group="check" >
				</th>
				<th width="100" >
					<!-- 序号--> <spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
				</th>
				<th width="100" >
					<!-- 部门--> <spring:message code="public.title.deptName" />
				</th>
				<th width="100" >
					<!-- 职号--> <spring:message code="display.emp.statistics.mes209" />
				</th>
				<th width="100" >
					<!-- 姓名--> <spring:message code="public.title.name" />
				</th>
                <th width="100" >
					<!-- 户口性质 --> <spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
				</th>
				<th width="100" >
				       <!-- 入社日期--><spring:message code="display.emp.statistics.mes206" />
				</th>
				<th width="100" >
					<!-- 离职日期--> <spring:message code="ess.trans.title.resignDate" />
				</th>
				<th width="100" >
					<!-- 开始缴纳月 --> <spring:message
						code="is.joininstance.title.startwithmonth" />
				</th>
				<th width="100" >
					<!-- 基础参照工资--> <spring:message code="is.joininstance.title.basesalary" />
				</th>
				<th width="100" >
					<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
				</th>
				<th width="100" >
					<!-- 标记--> <spring:message code="is.joininstance.title.remarking" />
				</th>
				<th width="100" >
					<!-- 缴纳基数--> <spring:message code="is.joininstance.title.paybasenum" />
				</th>
				<th width="100" >
					<!-- 状态-->
					<spring:message code="is.joininstance.title.statement"/>
				</th>
				<th width="100" ><!-- class="asc" -->
					<!-- 提示--> <spring:message code="is.joininstance.title.message" />
				</th>
				</tr>
				<%-- <tr>
				<td class="info_title_01">
								<!-- 养老 --><spring:message code="is.joininstance.title.yanglao" />
							</td>
							<td class="info_title_01">
								<!-- 医疗 --><spring:message code="is.joininstance.title.yiliao" />
							</td>
							<td class="info_title_01">
								<!-- 生育 --><spring:message code="is.joininstance.title.shengyu" />
							</td>
							<td class="info_title_01">
								<!-- 工伤 --><spring:message code="is.joininstance.title.gongshang" />
							</td>
							<td class="info_title_01">
								<!-- 失业 --><spring:message code="is.joininstance.title.shiye" />
						</td>
						</tr>		 --%>
		</thead>
			<tbody>
			<c:forEach items="${joinList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
                        	<td class="td_center" style="white-space:nowrap">
								<input type="checkbox" name="check"  value="${show.PA_BEN_MANAGE_ADD_SEQ}" />
							</td>
							<td  style="white-space:nowrap">
								${i.index + 1}&nbsp;
							</td>
							<td style="white-space:nowrap" align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.EMPID}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.CHINESENAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.REG_TYPE_CODE_NAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.DATE_STARTED}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
							<td  style="white-space:nowrap">
								${show.START_DATE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.REFER_VALUE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.JOIN_VALUE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.BASELINE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.ENDOWMENT_BASE}&nbsp;
							</td>
							<%-- <td  style="white-space:nowrap">
								${show.MEDICARE_BASE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.SHENGYU_BASE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COMPO_BASE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.UNEMP_BASE}&nbsp;
							</td> --%>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_TYPE}">-</c:when>
									<c:otherwise>${show.DATE_TYPE}</c:otherwise>
								</c:choose>
							</td>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.ERROR_REMARK}">-</c:when>
									<c:otherwise>${show.ERROR_REMARK}</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/is/insurancesystem/viewJoinInstanceMangement" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>


