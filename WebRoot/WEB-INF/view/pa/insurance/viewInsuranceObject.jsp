<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<!--
	function f_ObjectCalcGJJFlagUpdate(index,calc_gjj_flag,person_id,empid){
		var params = [];
		params.push({
			name: 'seach_PERSON_ID',
			value: person_id
		});
		CALC_GJJ_FLAG = calc_gjj_flag == 'Y' ? 'N': 'Y';
		var imgValue=empid+"_gif2";
		var src=$("#"+imgValue,navTab.getCurrentPanel())[0].src;
		var srcNum=src.lastIndexOf(".");
		var srcText=src.substring(parseInt(srcNum)-1);
		if(srcText=="Y.gif"){
			CALC_GJJ_FLAG="N";
		}else if(srcText=="N.gif"){
			CALC_GJJ_FLAG="Y";
		}
		params.push({
			name: 'seach_CALC_GJJ_FLAG',
			value: CALC_GJJ_FLAG
		});
		$.ajax({
		  url: '/pa/insurance/updateInsCalcGJJFlagByPersonId',
		  data: params,
		  type:'post',
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//成功之后不提示，否则太麻烦
				//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
				//页面重载
				//navTabSearch_pa0413(document.viewInsuranceObject);
				url="/resources/images/"+CALC_GJJ_FLAG+".gif";
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

	/* ----------------------------- */
	function f_ObjectCalcFlagUpdate(index,calc_flag,person_id,empid){
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
		  url: '/pa/insurance/updateInsCalcFlagByPersonId',
		  data: params,
		  type:'post',
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//成功之后不提示，否则太麻烦
				//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
				//页面重载
				//navTabSearch_pa0413(document.viewInsuranceObject);
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
	function insertInsuranceCalculationObject(){
		var params={};
		params["CALTYPE"] = "IS";//标识
		params["IS_MONTH"] = $("#seach_INS_YEAR_PA0413").val()+$("#seach_INS_MONTH_PA0413").val();//工资月 
		
		var year = $("#seach_INS_YEAR_PA0413").val();
		var month = $("#seach_INS_MONTH_PA0413").val();
		var yearMonth=year+month;
		
		if (confirm ('<spring:message code="pa.insurance.title.salaryMonth"/>:'+yearMonth
				+', <spring:message code="zxc.paInsurance.title.COMMIT_CONFIRM"/>')){//确定提交吗？
		  	$.ajax({
				url: "/pa/insurance/insuranceCalculationObject",
				data: params,
				cache: false,
				success: function(responseText){
					if (responseText == "Y"){
						alert("初始化保险计算对象成功!");
						//页面重载
						navTabSearch(document.viewInsuranceObject);
					}else{
						alert("初始化保险计算对象失败!");
					}
				}
			});	
		}
	}

	function delInsCalcObject(personId,pa_month){
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
			  url: '/pa/insurance/delInsCalcObject',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.viewInsuranceObject);
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
	function importIsCalcObjectData(){
		$("#importExcelDialog_pa0413").attr('href','/pa/excelImport/importExcelData?importFunName=/importViewInsuranceObject');
		///pa/excelImport/importArItemData?importFunName=importViewInsuranceObject
		$("#importExcelDialog_pa0413").click();			
	}
	
	function expIsCalcObjectExcel(){
		var year = $("#seach_INS_YEAR_PA0413").val();
		var month = $("#seach_INS_MONTH_PA0413").val();
		var key = $("#seach_KEY").val();
		var deptNo = $("#seach_DEPTNO").val();
		var jobTp= $("#seach_JOB_TP").val();
		var sonDeptFlag = document.getElementById("seach_SON_DEPT_FLAG");
		if(sonDeptFlag.checked){
			sonDeptFlag = 'YES';
		}else{
			sonDeptFlag = 'NO';
		}
		
		var url = "/pa/insurance/viewInsuranceObjectExcel?navTabId=pa0413&seach_INS_YEAR_PA0413="+year+"&seach_INS_MONTH_PA0413="+month
			+"&seach_KEY="+key+"&seach_DEPTNO="+deptNo+"&seach_SON_DEPT_FLAG="+sonDeptFlag+"&seach_JOB_TP="+jobTp;
		document.getElementById("expIsCalcObjectExcel").href=encodeURI(url);
	}

	function changeSonDeptFlag(){
		var son_dept_flag = document.getElementById("seach_SON_DEPT_FLAG");
		if(son_dept_flag.checked){
			son_dept_flag.value = 'YES';
		}else{
			son_dept_flag.value = 'NO';
		}
	}
//-->
</script>

<a id="importExcelDialog_pa0413"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0413"  href="#" target="navTab" mask="true">
<span style="display:none;">导入参保对象</span></a>


<div class="pageHeader">
	<form id="viewInsuranceObject" name="viewInsuranceObject" onsubmit="return navTabSearch(this);" 
		action="/pa/insurance/viewInsuranceObject?numPerPage=${numPerPage}" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="20%" style="padding: 4px;">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
						<ait:date yearName="seach_INS_YEAR_PA0413" monthName="seach_INS_MONTH_PA0413" yearSelected="${INS_YEAR_PA0413}" 
							monthSelected="${INS_MONTH_PA0413}"/>
					</td>
					<td width="25%" style="padding: 4px;">
						<spring:message code="public.title.empId"/><!--工号-->/<spring:message code="public.title.name"/><!--姓名-->：
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
					</td>
					<td width="25%" style="padding: 4px;">
						<spring:message code="public.title.deptName"/><!--部门-->：
						<ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO}"/>
						<input type="checkbox" id="seach_SON_DEPT_FLAG" name="seach_SON_DEPT_FLAG" onclick="changeSonDeptFlag();"
							<c:if test="${SON_DEPT_FLAG eq 'YES' }">checked="checked"</c:if>
	                        	title="<spring:message code='hr.viewCondSql.title.BAOHANZIBUMEN'/>"/><!-- 包含子部门  -->
	                            <spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/>
					</td>
					<td width="35%" style="padding: 4px;">
						人员类型：
						<ait:SelectEmpTypeCode name="seach_JOB_TP" id="seach_JOB_TP" limit="pa" type="group" selected="${JOB_TP}"></ait:SelectEmpTypeCode>
					</td>
					<td></td>
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
			<c:set value="/pa/insurance/deleteViewInsuranceObject?PERSON_ID={PERSON_ID}" var="delete_Url" />
			<%--<c:set value="/pa/insurance/updateViewInsuranceObject?PERSON_ID={PERSON_ID}" var="edit_Url" />--%>
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
					<a href="#" onclick="importIsCalcObjectData()">
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
				<%-- <li>
					<div class="buttonActive">
						<a onclick="insertInsuranceCalculationObject()">
							<span><!-- 生成保险发放对象 -->
								<spring:message code="zxc.pa.wagebase.title.INSURANCE_CALCULATE_OBJECT"/>
							</span>
						</a>
					</div>
				</li> --%>
				<li>
					<a class="buttonActive" id ="expIsCalcObjectExcel" onclick="expIsCalcObjectExcel();" href="#">
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
<%--				
				<c:if test="${toolbarInfo.UPDATER == '1'}">
			<c:if test="${edit_Url ne '' && edit_Url ne null}">
				<li id="editLi">
					<a class="edit"
						href="${edit_Url}"
						<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
							target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						</c:if>
							mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }" 
							width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}" 
							height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
							<c:if test="${edit_rel ne '' && edit_rel ne null}">
								rel="${edit_rel}"
							</c:if>
							><span>
								<c:choose>
								   <c:when test="${edit_name eq '' || edit_name eq null}">
								     	<spring:message code="button.update" />
								   </c:when>
								   <c:otherwise>
								   		${edit_name}
								   </c:otherwise>
								</c:choose>
							</span>
					</a>
				</li>
			</c:if>
		</c:if>--%>
			</ul>
		</div>
		
		<table class="table" width="100%" layoutH="215">
			
			<thead>
				<tr>
					<th width="5%"><!--序号-->
						<spring:message code="ar.viewcycle.title.xuhao"/>
					</th>
					<th width="8%" orderField="nlssort(PA_MONTH,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
					</th>
					<th width="8%" orderField="EMPID" class="${orderDirection}">
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
						<spring:message code="pa.wagebase.title.caculateFlag"/>(社保)
					</th>
					<c:if test="${defaultCpny eq 'LGEHN' || defaultCpny eq 'LGEHZ' || defaultCpny eq 'LGEYT'}">
					<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
						<spring:message code="pa.wagebase.title.caculateFlag"/>(公积金)
					</th>
					</c:if>
					<th width="10%"><!--删除-->
						<spring:message code="button.delete"/>
					</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${isCalcList}" var="item" varStatus="i">
					<tr target="PERSON_ID" rel="${item.PERSON_ID}&PA_MONTH=${item.PA_MONTH }&pageNum=${pageNum}">
						<td width="5%" style="text-align:center">${i.index+1 }</td>
						<td width="8%" style="text-align:center">${item.PA_MONTH }</td>
						<td width="8%" style="text-align:center">${item.EMPID}</td>
						<td width="10%" style="text-align:center">${item.CHINESE_NAME}</td>
						<td width="25%" style="text-align:left">${item.DEPTNAME}</td>
						<td width="10%" style="text-align:center">${item.JOIN_COMPANY_DATE}</td>
						<td width="10%" style="text-align:center">${item.DATE_LEFT}</td>
						<td width="10%" style="text-align:center">
							<c:if test="${item.CALC_FLAG ne null && item.CALC_FLAG ne '' }">
								<img id="${item.EMPID}_gif"  src="/resources/images/${item.CALC_FLAG}.gif" style="cursor: pointer" 
									onclick="f_ObjectCalcFlagUpdate(${i.index},'${item.CALC_FLAG}','${item.PERSON_ID}','${item.EMPID}')"/>
							</c:if>
					</td>
					<c:if test="${defaultCpny eq 'LGEHN' || defaultCpny eq 'LGEHZ' || defaultCpny eq 'LGEYT'}">
					<td width="10%" style="text-align:center">
						<c:if test="${item.CALC_GJJ_FLAG ne null && item.CALC_GJJ_FLAG ne '' }">
							<img id="${item.EMPID}_gif2"  src="/resources/images/${item.CALC_GJJ_FLAG}.gif" style="cursor: pointer" 
								onclick="f_ObjectCalcGJJFlagUpdate(${i.index},'${item.CALC_GJJ_FLAG}','${item.PERSON_ID}','${item.EMPID}')"/>
						</c:if>
					</td>
					</c:if>
					<td width="10%" style="text-align:center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delInsCalcObject('${item.PERSON_ID}','${item.PA_MONTH}')"
					 		style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 	
	<c:set value="/pa/insurance/viewInsuranceObject" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>