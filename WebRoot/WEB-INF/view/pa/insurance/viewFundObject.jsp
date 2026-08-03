<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<!--
	function f_FundCalcFlagUpdate(index,calc_flag,person_id,empid){
		var params = [];
		params.push({
			name: 'seach_PERSON_ID',
			value: person_id
		});
		CALC_FLAG = calc_flag == 'Y' ? 'N': 'Y';
		var imgValue=empid+"_gif";
		var src=$("#"+imgValue,navTab.getCurrentPanel())[0].src;
		var srcNum=src.lastIndexOf(".");
		var srcText=src.substring(parseInt(srcNum)-1);
		if(srcText=="Y.gif"){
			CALC_FLAG="N";
		}else if(srcText=="N.gif"){
			CALC_FLAG="Y";
		}
		params.push({
			name: 'seach_CALC_FLAG',
			value: CALC_FLAG
		});
		$.ajax({
		  url: '/pa/insurance/updateFundCalcFlagByPersonId',
		  data: params,
		  type:'post',
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//成功之后不提示，否则太麻烦
				//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
				//页面重载
				//navTabSearch_pa0413(document.viewFundObject);
				url="/resources/images/"+CALC_FLAG+".gif";
				$("#"+imgValue).attr("src",url);
			}else{
				//错误之后提示
				url="/resources/images/N.gif";
				$("#"+imgValue).attr("src",url);
				alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
			}
		  }
		});
	} 
	
	//保险计算对象
	function insertFundCalculationObject(){
		var params={};
		params["CALTYPE"] = "IS";//标识
		params["IS_MONTH"] = $("#seach_FUND_YEAR_PA0421").val()+$("#seach_FUND_MONTH_PA0421").val();//工资月 
		
		var year = $("#seach_FUND_YEAR_PA0421").val();
		var month = $("#seach_FUND_MONTH_PA0421").val();
		var yearMonth=year+month;
		
		if (confirm ('<spring:message code="pa.insurance.title.salaryMonth"/>:'+yearMonth
				+', <spring:message code="zxc.paInsurance.title.COMMIT_CONFIRM"/>')){//确定提交吗？
		  	$.ajax({
				url: "/pa/insurance/fundCalculationObject",
				data: params,
				cache: false,
				success: function(responseText){
					if (responseText == "Y"){
						alert("初始化公积金计算对象成功!");
						//页面重载
						navTabSearch(document.viewFundObject);
					}else{
						alert("初始化公积金计算对象失败!");
					}
				}
			});	
		}
	}

	function delFundCalcObject(personId,pa_month){
		var params = [];
		params.push({
			name: 'PERSON_ID',
			value: personId
		});
		params.push({
			name: 'PA_MONTH',
			value: pa_month
		});
		if (confirm ("确定要删除吗?")){	  
			$.ajax({
			  url: '/pa/insurance/delFundCalcObject',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.viewFundObject);
				}else{
					alert("删除失败！");
				}
			  }
			});
		}
	}
//-->
</script>	

<script>
<!--
	function importFundCalcObjectData(){
		$("#importExcelDialog_pa0421").attr('href','/pa/excelImport/importExcelData?importFunName=/importViewFundCalcObject');
		///pa/excelImport/importArItemData?importFunName=importViewInsuranceObject
		$("#importExcelDialog_pa0421").click();			
	}
	
	function expFundCalcObjectExcel(){
		var year = $("#seach_FUND_YEAR_PA0421").val();
		var month = $("#seach_FUND_MONTH_PA0421").val();
		var key = $("#seach_FUND_KEY").val();
		var deptNo = $("#seach_FUND_DEPTNO").val();
		
		var sonDeptFlag = document.getElementById("seach_FUND_SON_DEPT_FLAG");
		if(sonDeptFlag.checked){
			sonDeptFlag = 'YES';
		}else{
			sonDeptFlag = 'NO';
		}
		
		var url = "/pa/insurance/viewFundObjectExcel?navTabId=pa0421&seach_FUND_YEAR_PA0421="+year+"&seach_FUND_MONTH_PA0421="+month
			+"&seach_FUND_KEY="+key+"&seach_FUND_DEPTNO="+deptNo+"&seach_FUND_SON_DEPT_FLAG="+sonDeptFlag;
		document.getElementById("expFundCalcObjectExcel").href=encodeURI(url);
	}

	function changeFundSonDeptFlag(){
		var son_dept_flag = document.getElementById("seach_FUND_SON_DEPT_FLAG");
		if(son_dept_flag.checked){
			son_dept_flag.value = 'YES';
		}else{
			son_dept_flag.value = 'NO';
		}
	}
//-->
</script>

<a id="importExcelDialog_pa0421"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0421"  href="#" target="navTab" mask="true">
<span style="display:none;">导入公积金对象</span></a>


<div class="pageHeader">
	<form id="viewFundObject" name="viewFundObject" onsubmit="return navTabSearch(this);" 
		action="/pa/insurance/viewFundObject?numPerPage=${numPerPage}" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</td>
				<td>
					<ait:date yearName="seach_FUND_YEAR_PA0421" monthName="seach_FUND_MONTH_PA0421" yearSelected="${FUND_YEAR_PA0421}" 
						monthSelected="${FUND_MONTH_PA0421}"/>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/<spring:message code="public.title.name"/><!--姓名-->
				</td>
				<td>
					<input type="text" id="seach_FUND_KEY" name="seach_FUND_KEY" value="${FUND_KEY}" />
				</td>
				<td>
					<spring:message code="public.title.deptName"/><!--部门-->
				</td>
				<td>
					<ait:deptList name="seach_FUND_DEPTNO" limit="pa" id="vviewFundObject_seachDept"/>
					<ait:deptTreeIcon name="seach_FUND_DEPTNO" limit="pa" id="vviewFundObject_seachDept" selected="${FUND_DEPTNO}"/>
			
					<input type="checkbox" id="seach_FUND_SON_DEPT_FLAG" name="seach_FUND_SON_DEPT_FLAG" onclick="changeFundSonDeptFlag();"
						<c:if test="${FUND_SON_DEPT_FLAG eq 'YES' }">checked="checked"</c:if>
                        	title="<spring:message code='hr.viewCondSql.title.BAOHANZIBUMEN'/>"/><!-- 包含子部门  -->
                            <spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/>
				</td>
			</tr>
		</table>
		
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
	<div class="pageContent">
		<c:set value="/pa/insurance/deleteViewFundObject?PERSON_ID={PERSON_ID}" var="delete_Url" />
		<c:set value="600" var="edit_width"/>
		<c:set value="400" var="edit_height"/>
		<div class="formBar">
		<ul class="toolBar">
			<li>
				<a class="buttonActive" href="/pa/excelExport/exportViewInsuranceObjectModule">
					<span><!--下载导入模板-->
						<spring:message code="pa.insurance.title.downloadImportTemplate"/>
					</span>
				</a>
			</li>
			<li>
				<a href="#" onclick="importFundCalcObjectData()">
					<span><!-- EXCEL导入 -->
						<spring:message code="ar.addempshift.title.excelimport"/>
					</span>
	 			</a>
	 		</li>
	 		<%-- 
	 		<li>
				<a class="buttonActive" target="dialog" mask="true" width="500" height="200"
					href="/pa/excelImport/importArItemData?importFunName=importViewInsuranceObject">
					<span><!-- EXCEL导入 -->
						<spring:message code="ar.addempshift.title.excelimport"/>
					</span>
				</a>
			</li>
			--%>
			<li>
				<div class="buttonActive">
					<a onclick="insertFundCalculationObject()">
						<span><!-- 生成公积金计算对象 -->
							生成公积金计算对象
						</span>
					</a>
				</div>
			</li>
			<li>
				<a class="buttonActive" id ="expFundCalcObjectExcel" onclick="expFundCalcObjectExcel();" href="#">
					<span><spring:message code="ar.addempshift.title.excelexport"/><%--导出Excel--%></span>
				</a>
			</li>
			<%-- 
			<li>
				<div class="buttonActive">
					<a onclick="reportExcel(this)" title="<spring:message code='rp.report.title.exportYN'/>"><!--是否导出?-->
					  	<span><!-- Excel导出 -->
					  		<spring:message code="ar.addempshift.title.excelexport"/>
					  	</span>
					</a>
				</div>
			</li>
			--%>
			<c:if test="${toolbarInfo.DELETER == '1'}">
				<c:if test="${delete_Url ne '' && delete_Url ne null}">
					<li id="deleteLi">
						<a class="delete" href="${delete_Url}"
							<c:if test="${delete_target_exit eq '' || delete_target_exit eq null }">
								target="${delete_tab eq '' || delete_tab eq null ? 'ajaxTodo' : delete_tab}"
							</c:if>
							<c:if test="${delete_mask_exit ne '' && delete_mask_exit ne null }">
								mask="${delete_mask eq '' || delete_mask eq null ? 'true' : delete_mask }" 
							</c:if>
							<c:if test="${delete_range ne '' && delete_range ne null }">
								width="${delete_width eq '' || delete_width eq null ? '500' : delete_width}" 
								height="${delete_height eq '' || delete_height eq null ? '400' : delete_height}"
							</c:if>
							 title="
							 	<c:choose>
								   <c:when test="${delete_title eq '' || delete_title eq null}">
								     	<spring:message code="button.delete.sure" />
								   </c:when>
								   <c:otherwise>
								   		${delete_title}
								   </c:otherwise>
								</c:choose>
							 ">
							<span>
								<c:choose>
								   <c:when test="${delete_name eq '' || delete_name eq null}">
								     	<spring:message code="button.delete" />
								   </c:when>
								   <c:otherwise>
								   		${delete_name}
								   </c:otherwise>
								</c:choose>
							</span>
						</a>
					</li>
				</c:if>
			</c:if>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="215">
		<thead>
			<tr>
				<th width="5%"><!--序号-->
					<spring:message code="ar.viewcycle.title.xuhao"/>
				</th>
				<th width="10%" orderField="nlssort(PA_MONTH,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</th>
				<th width="10%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号-->
				</th>
				<th width="10%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名-->
				</th>
				<th width="25%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.deptName"/><!--部门-->
				</th>
				<th width="10%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
				</th>
				<th width="10%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
				</th>
				<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
					<spring:message code="pa.wagebase.title.caculateFlag"/>
				</th>
				<th width="10%"><!--删除-->
					<spring:message code="button.delete"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${isCalcList}" var="item" varStatus="i">
				<tr target="PERSON_ID" rel="${item.PERSON_ID}&PA_MONTH=${item.PA_MONTH }&pageNum=${pageNum}">
					<td width="5%" style="text-align:center">${i.index+1 }</td>
					<td width="10%" style="text-align:center">${item.PA_MONTH }</td>
					<td width="10%" style="text-align:center">${item.EMPID}</td>
					<td width="10%" style="text-align:center">${item.CHINESE_NAME}</td>
					<td width="25%" style="text-align:left">${item.DEPTNAME}</td>
					<td width="10%" style="text-align:center">${item.JOIN_COMPANY_DATE}</td>
					<td width="10%" style="text-align:center">${item.DATE_LEFT}</td>
					<td width="10%" style="text-align:center">
						<c:if test="${item.CALC_FLAG ne null && item.CALC_FLAG ne '' }">
							<img id="${item.EMPID}_gif"  src="/resources/images/${item.CALC_FLAG}.gif" style="cursor: hand" 
								onclick="f_FundCalcFlagUpdate(${i.index},'${item.CALC_FLAG}','${item.PERSON_ID}','${item.EMPID}')"/>
						</c:if>
					</td>
					<td width="10%" style="text-align:center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delFundCalcObject('${item.PERSON_ID}','${item.PA_MONTH}')"
					 		style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 	
	<c:set value="/pa/insurance/viewFundObject" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>