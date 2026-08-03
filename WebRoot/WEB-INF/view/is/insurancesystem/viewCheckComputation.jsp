<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

		function search_bx0106(form) {//搜索
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
									'bx0106', '基数管理');
						} else if (data.statusCode == "300") {
							alertMsg.info(data.message);
						}
					}
				});
	}

//核算
	function hesuan_bx0106(){
		var result = confirm("确定要核算么？");
			if (result == true) {
				alert("核算成功");
				navTabNum('/is/insurancesystem/viewCheckComputation?pageNum=1&menuNo=124907&navTabId=bx0106',
				'bx0106', '汇缴核算'); 
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
		}else{
			alert('请选择修改项！');
			
		}
	} 
	//导出
	function expStopInsuranceNumInfo(a,navTabId){
		var $this = $(a);
  		var title = $this.attr("title");
  		var $from = $("#viewStopInsureInfo");
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
<form id="viewCheckComputation" onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewCheckComputation" method="post" rel="pagerForm">
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
							<button type="button" onclick="hesuan_bx0106()">
								核算
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	<table  class="searchContent">
		<tr align="center">
	  				<td>
		  				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
			  				<tr>
								<td class="info_title_01"> 保险年月 </td>
								<td class="info_content_00"><ait:date yearName="year" monthName="month" yearPlus="10" yearSelected="${year}" monthSelected="${month}" yearMinus="10"/></td>
								<td class="info_title_01">
									保险区分 
								</td>
								<td class="info_content_00">
								<input type="text">
									<%-- <ait:codeClass name="insturanceCode" codeClass="InsuranceTpye" all="all"/> --%> 
								</td>
								<td class="info_title_01">
									部门 
								</td>
								<td class="info_content_00">
									<input type="text">
									<%-- <ait:selDeptTree name="deptID" type="att" selected="${deptID}" deptAll="deptAll"/> --%>
								</td>
			  				</tr>
			  				<tr>
			  				
								
							 	<td class="info_title_01">补缴/返还数据 </td>
								<td class="info_content_00">
									<select name="payment">
										<option value="">不筛选</option>
										<option value="Y">筛选</option>
									</select>
								</td>
								
								<td class="info_title_01">
									职号/姓名 
								</td>
								<td class="info_content_00">
								<input type="text">
									<%-- <input id="empID" name="empID" value="<c:out value='${basic.EMPID}'/>"
				          				onkeyup="SearchContent(this.value,this.id)" size="10" title='<ait:message  messageID="alert.emp.staff_info.basic_info.search_name" module="hrm" />'/> --%>
								<span id="empName" >${basic.CHINESENAME}</span>
								</td>
								<td class="info_title_01">
									 职系 
								</td>
								<td class="info_content_00">
								<input type="text">
									 <%-- <ait:codeClass name="postCoefCode" codeClass="postCoefCode" all="all" remove="postCoefCode004,postCoefCode005"/>  --%>
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
<br>
<hr color="#E9F9F5">
<div class="pageContent" >
	<form name="searchForm_bx0104_aa" id="searchForm_bx0104_aa" method="post">
			
					<table  class="table" width="101.8%" layoutH="180"  >
						<tr style="position: relative; top: expression(this.offsetParent.scrollTop); background-color: #F3F3F1;">
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">序号</td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">部门 </td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">职号 </td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">姓名 </td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">保险类别 </td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">缴纳基数 </td>
					      <td colspan="4" class="info_title_01"> 本月应缴 
					      <td colspan="6" class="info_title_01">本月补缴 
					      <td colspan="2" rowspan="2" class="info_title_01"style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">返还 </td>
					      <td colspan="2" rowspan="2" class="info_title_01"style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01"> 小计</td>
					      <!-- 
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">状态</td>
					      <td rowspan="3" style="position: relative; top: expression(this.offsetParent.scrollTop);"class="info_title_01">提示</td>
					       -->
					    </tr>
						<tr style="position: relative; top: expression(this.offsetParent.scrollTop);">
					      <td colspan="2" class="info_title_01">公司</td>
					      <td colspan="2" class="info_title_01">个人 </td>
					      <td colspan="3" class="info_title_01">公司</td>
					      <td colspan="3" class="info_title_01"> 个人 </td>
					    </tr>
						<tr style="position: relative; top: expression(this.offsetParent.scrollTop);">
					      <td class="info_title_01">比例</td>
					      <td class="info_title_01">金额 </td>
					      <td class="info_title_01"> 比例 </td>
					      <td class="info_title_01">金额 </td>
					      <td class="info_title_01"> 补缴</td>
					      <td class="info_title_01"> 利息 </td>
					      <td class="info_title_01"> 滞纳金 </td>
					      <td class="info_title_01"> 补缴</td>
					      <td class="info_title_01"> 利息</td>
					      <td class="info_title_01">滞纳金 </td>
					      <td class="info_title_01"> 公司 </td>
					      <td class="info_title_01">个人 </td>
					      <td class="info_title_01"> 公司 </td>
					      <td class="info_title_01"> 个人 </td>
					    </tr>
					    <c:set value="0" var="n1"/>
						<c:set value="0" var="n2"/>
						<c:set value="0" var="n3"/>
						<c:set value="0" var="n4"/>
						<c:set value="0" var="n5"/>
						<c:set value="0" var="n6"/>
						<c:set value="0" var="n7"/>
						<c:set value="0" var="n8"/>
						<c:set value="0" var="n9"/>
						<c:set value="0" var="n10"/>
						<c:set value="0" var="n11"/>
						<c:set value="0" var="n12"/>
					
					    <c:forEach items="${list}" var="show" varStatus="i">
						<tr align="center" onclick="band('#f4f7fa','black')">
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
								${show.INSURANCE_CODE_NAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.INSURANCE_BASE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								<fmt:formatNumber value="${show.COR_RATE}" type="percent" maxFractionDigits="2"/>&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.INSURANCE_COR_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								<fmt:formatNumber value="${show.PER_RATE}" type="percent" maxFractionDigits="2"/>&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.INSURANCE_PER_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COR_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COR_INTEREST}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COR_OVERDUE_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.PER_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.PER_INTEREST}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.PER_OVERDUE_PAY}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COR_BACK}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.PER_BACK}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.COR_TOTAL}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.PER_TOTAL}&nbsp;
							</td>
						</tr>
							<c:set value="${show.COR_PAY + n1}" var="n1"></c:set>
					    	<c:set value="${show.COR_INTEREST + n2}" var="n2"></c:set>
					    	<c:set value="${show.COR_OVERDUE_PAY + n3}" var="n3"></c:set>
					    	<c:set value="${show.PER_PAY + n4}" var="n4"></c:set>
					    	<c:set value="${show.PER_INTEREST + n5}" var="n5"></c:set>
					    	<c:set value="${show.PER_OVERDUE_PAY + n6}" var="n6"></c:set>
					    	<c:set value="${show.COR_BACK + n7}" var="n7"></c:set>
					    	<c:set value="${show.PER_BACK + n8}" var="n8"></c:set>
					    	<c:set value="${show.COR_TOTAL + n9}" var="n9"></c:set>
					    	<c:set value="${show.PER_TOTAL + n10}" var="n10"></c:set>
					    	<c:set value="${show.INSURANCE_COR_PAY + n11}" var="n11"></c:set>
					    	<c:set value="${show.INSURANCE_PER_PAY + n12}" var="n12"></c:set>
						</c:forEach>
						<tr>
								<td class="info_title_01" colspan="7">
									合计：
								</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n11}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01">&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n12}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n1}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n2}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n3}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n4}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n5}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n6}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n7}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n8}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n9}" maxFractionDigits="2"/>&nbsp;</td>
								<td class="info_title_01"><fmt:formatNumber type="number" value="${n10}" maxFractionDigits="2"/>&nbsp;</td>
								<!-- 
								<td class="info_title_01" colspan="2">&nbsp;</td>
								 -->
							</tr>
					</table>
					</div>
					<table width="100%" height="20" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td></td>
  						</tr>
					</table>
					
					<table width="60%" border="1" bordercolor="#A8A8A8" cellpadding="0" cellspacing="0" >
							<tr>
								<th class="info_title_00" rowspan="2" colspan="2" align="center"><b>调整事项 </b></th>
								<th class="info_title_00" colspan="2"><b>本月社会保险合计</b></th>
							</tr>
							<tr>
								<td class="info_title_01">公司 </td>
								<td class="info_title_01">个人</td>
							</tr>
							<tr>
								<td >
									调整事由 ： <input type="text" name="reason" value="${reason}" size="20">
								</td>
								<td >
									调整金额   ： <input type="text" name="adjustValue" value="${adjustValue}" size="20" style="text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
								</td>
								<td class="info_title_01">
									<fmt:formatNumber type="number" value="${corValue+adjustValue}" maxFractionDigits="2"/>
								</td>
								<td class="info_title_01">
									<fmt:formatNumber type="number" value="${perValue}" maxFractionDigits="2"/>
								</td>
							</tr>
					</table>
					<table width="100%" height="30" border="0" cellspacing="0" cellpadding="1">
							<tr>
								<td ></td>
							</tr>
					</table>
		</td>
		<td background="/img/tablbk01_r4_c26.gif" width="10">&nbsp;</td>
	</tr>
	
</table>
<div id="emp_list" style="position:absolute;overflow:auto; top:200;width:500; height:210; z-index:-2;visibility: hidden;">   
	</div>
	</form>
	<c:set value="/is/insurancesystem/viewObjectManagement" var="pageUrl"/>
		<form id="pagerForm" name="pagerForm_${totalCount}" method="post" action="${pageUrl}">
		
	</form>
</div>


