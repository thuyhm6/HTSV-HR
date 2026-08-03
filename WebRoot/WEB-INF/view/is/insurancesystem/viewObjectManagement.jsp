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

	
	 function save_bx0105(){//修改
	     
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
			$("#updateid_bx0105").attr("href","/is/insurancesystem/updateObjectMgtNum?ids="+ids);
			$("#updateid_bx0105").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
			
		}else{
			alert('请选择修改项！');
			
		}
	} 
	//导出
	function expObjInsuranceNumInfo(a,navTabId){
		var $this = $(a);
  		var title = $this.attr("title");
  		var $from = $("#viewObjInsuranceInfo");
	     alertMsg.confirm(title, {okCall: function(){ doInstanceBaseNumExport($from);}});
    
	}
	function doInstanceBaseNumExport(from){
  	var $from =$(from);
  	var url ="/is/insurancesystem/insObjNumListExcel";
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
<a id="updateid_bx0105" rel="updateCPFStopManagement" mask="true" width="1200",height="400" target="dialog" />
<a id="orderid_bx0105" rel="orderCPFStopManagement" mask="true" width="1200",height="400" target="dialog" />
<div class="pageHeader" >
<form id="viewObjInsuranceInfo" onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewObjectManagement" method="post" rel="pagerForm">
	<div class="searchBar">

	<table  class="searchContent">
		<tr align="center">
	  				<td>
		  				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
			  				<tr>
								<td class="info_title_01">
									<!-- 缴纳月份 --><spring:message code="is.objmanagement.title.paymonth"/>
								</td>
								<td class="info_content_00">
									<ait:date yearName="year" monthName="month" yearPlus="10" yearSelected="${year}" monthSelected="${month}" yearMinus="10"/>
								</td>
								<td class="info_title_01">
									<!-- 开始缴纳月 --><spring:message code="is.joininstance.title.startwithmonth" />
								</td>
								<td class="info_content_00">
									<ait:date yearName="year" monthName="month" yearSelected="${show.START_YEAR}" monthSelected="${show.START_MONTH}" yearPlus="10" yearMinus="10"/>
								</td>
								<td class="info_title_01">
									<!-- 停止缴纳月 --><spring:message code="display.emp.statistics.mes224" />
								</td>
								<td class="info_content_00">
									<ait:date yearName="endYear" monthName="endMonth" yearSelected="${show.START_YEAR}" monthSelected="${show.START_MONTH}" yearPlus="10" yearMinus="10"/>
								</td>
								<td class="info_title_01">
									<!-- 社保状态 --><spring:message code="is.objmanagement.title.socialstate" />
								</td>
								<td class="info_content_00">
									<select name="socialStatus">
										<option value="">全部</option>
										<option value="LAST">在保</option>
										<option value="STOP">停保</option>
										<option value="JOIN">新参保</option>
										<option value="FUCK">本月应缴</option>
									</select>
								</td>
			  				</tr>
			  				<tr>
								<td class="info_title_01">
									<!-- 社保号码为空 --><spring:message code="is.objmanagement.title.socialnumisnull" />
								</td>
								<td class="info_content_00">
									<select name="socialNoNull">
										<option value="">不筛选</option>
										<option value="Y">筛选</option>
									</select>
								</td>
								<td class="info_title_01">
									<!-- 职系 --><spring:message code="is.objmanagement.title.zhixi" />
								</td>
								<td class="info_content_00">
									<select>
										<option>全部</option>
										<option>事务职</option>
										<option>监督职</option>
										<option>技能职</option>
									</select>
								</td>
								<td class="info_title_01">
									<!-- 在职状态 --><spring:message code="display.emp.statistics.mes204"/>
								</td>
								<td class="info_content_00">
								 <select>
								 	<option>在职</option>
								 	<option>离职</option>
								 </select>
								</td>
								<td class="info_title_01">
									<!-- 户口性质 --><spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
								</td>
								<td class="info_content_00">
									<select>
								 	<option>外地城镇</option>
								 	<option>外地农村</option>
								 	<option>其他</option>
								 </select>
								</td>
			  				</tr>
			  				<tr>
			  					<td class="info_title_01">
									<spring:message code="public.title.deptName"/><%--部门
								--%></td>
								<td class="info_content_00">
								<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
								</td>
								<td class="info_title_01">
									<!-- 职号/姓名 --><spring:message code="is.objmanagement.title.numorname" />
								</td>
								<td class="info_content_00">
								<input id="empID" name="empID" value="<c:out value='${basic.EMPID}'/>"
				          				onkeyup="SearchContent(this.value,this.id)" size="10" />
				          				<%--
				          				title='<ait:message  messageID="alert.emp.staff_info.basic_info.search_name" module="hrm" />'
								--%>
								<span id="empName" >${basic.CHINESENAME}</span>
								</td>
								<td class="info_title_01">
									<!-- 身份证号码 --><spring:message code="ess.personalinfo.title.IDCardNo"/>
								</td>
								<td class="info_content_00">
									<input type="text" size="20" name="cardID">
								</td>
								<td class="info_title_01">
									<!-- 社会保险号码 --><spring:message code="is.objmanagement.title.socialInsureNum"/>
								</td>
								<td class="info_content_00">
									<input type="text" size="20" name="searchSocialNo">
								</td>
			  				</tr>
		  				</table>
	  				</td>
				</tr>
	</table>
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

								<button type="button"   onclick="expObjInsuranceNumInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>">
									<spring:message code="ar.addempshift.title.excelexport" /> <!--Excel导出--> 
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="save_bx0105('searchForm_bx0105_aa');">
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
	<form name="searchForm_bx0105_aa" id="searchForm_bx0105_aa" method="post">
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
				<th width="80">
				<!-- 社保状态 --><spring:message code="is.objmanagement.title.socialstate" />
				</th>
				
				<th width="130">
				<!-- 社会保险号码 --><spring:message code="is.objmanagement.title.socialInsureNum"/>
				</th>
				<th width="100">
				<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
				</th>				
				<th width="100" >
				<!-- 年度基数--> <spring:message code="display.emp.statistics.mes208" />
				</th>
				<th width="100" >
				<!-- 标记--> <spring:message code="is.joininstance.title.remarking" />
				</th>
			
				<th width="50">
				<!-- 养老 --><spring:message code="is.joininstance.title.yanglao" />
				</th>
				<th width="50">
				<!-- 医疗 --><spring:message code="is.joininstance.title.yiliao" />
				</th>
				<th width="50">
				<!-- 生育 --><spring:message code="is.joininstance.title.shengyu" />
				</th>
				<th width="50">
				<!-- 工伤 --><spring:message code="is.joininstance.title.gongshang" />
				</th>
				<th width="50">
				<!-- 失业 --><spring:message code="is.joininstance.title.shiye" />
				</th>	
		</thead>
		<tbody>
			<c:forEach items="${ObjList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
							<td class="td_center" style="white-space:nowrap">
								<input type="checkbox" name="check"  value="${show.PA_BEN_MANAGE_SEQ}" />
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
								${show.SOCIAL_STATUS}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.JOIN_VALUE}&nbsp;
							</td>
							<td >
								${show.AVG_SALARY}&nbsp;
							</td>
							<td >
								${show.BASELINE}&nbsp;
							</td>
							<td >
								${show.ENDOWMENT_BASE}&nbsp;
							</td>
							<td >
								${show.MEDICARE_BASE}&nbsp;
							</td>
							<td >
								${show.SHENGYU_BASE}&nbsp;
							</td>
							<td >
								${show.COMPO_BASE}&nbsp;
							</td>
							<td >
								${show.UNEMP_BASE}&nbsp;
							</td>
						</tr>
						</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/is/insurancesystem/viewObjectManagement" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>


