<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_CalcFlagUpdate(index,calc_flag,person_id){
	var params = [];
	params.push({
		name: 'seach_PERSON_ID',
		value: person_id
	});
	CALC_FLAG = calc_flag == 'Y' ? 'N': 'Y';
	params.push({
		name: 'seach_CALC_FLAG',
		value: CALC_FLAG
	});
	$.ajax({
	  url: '/pa/wagebase/updatePaCalcFlagByPersonId',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//成功之后不提示，否则太麻烦
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.viewPaAccount);
		}else{
			//错误之后提示
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
} 

function f_CalcFlagUpdateByPerson_id(){
	var person_id = "";
	var flag = false;
	var params = [];
	var cs = document.getElementsByName("check_pa0502");
	for(var i = 0;i<cs.length;i++){
        if(cs[i].checked==true){
            //var PERSON = ;
        	person_id = document.getElementById("PERSON_ID_"+cs[i].value).value;
        	//alert(person_id);
        	params.push({
    			name: 'seach_PERSON_ID',
    			value: person_id
    		});
    		var CALC_FLAG_ID = "calc_flag_"+person_id;
    		
    		CALC_FLAG = document.getElementById(CALC_FLAG_ID).value; 
    		params.push({
    			name: 'seach_CALC_FLAG',
    			value: CALC_FLAG
    		});
    		
    		var REMARK_ID = "remark_"+person_id;
    		
    		REMARK = document.getElementById(REMARK_ID).value; 
    		params.push({
    			name: 'seach_REMARK',
    			value: REMARK
    		});
        	flag = true;
        	
           }
	}
	if(flag == true){
		$.ajax({
		  url: '/pa/wagebase/updatePaCalcFlagByPersonId?params='+params,
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//成功之后不提示，否则太麻烦
				//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
				//页面重载
				navTabSearch(document.viewPaAccount);
			}else{
				//错误之后提示
				alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
			}
		  }
		});
	}else{
		alert("对不起，您还没有选择!");
	}
	
	
} 

function reportExcel(a){
	var $this=$(a);
    var title = $this.attr("title"); 
    var $form = $("#viewPaAccount");  
    
	  var url ="/pa/wagebase/viewPaAccountTranserExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
					}});
}

function doExceptionChoseCheck(){
	if(!document.getElementById('do_EXCEPTION_PA_COUNT').checked){
		document.getElementById('seach_EXCEPTION_PA_COUNT').value = 'NO';
	}else if(document.getElementById('do_EXCEPTION_PA_COUNT').checked){
		document.getElementById('seach_EXCEPTION_PA_COUNT').value = 'YES';
	}
 }
 
 function doDimissionChoseCheck(){
	if(!document.getElementById('do_DIMISSION_PA_COUNT').checked){
		document.getElementById('do_DIMISSION_PA_COUNT').value = 'NO';
	}else if(document.getElementById('do_DIMISSION_PA_COUNT').checked){
		document.getElementById('do_DIMISSION_PA_COUNT').value = 'YES';
	}
 }

function selectradio(personid){
	var id = "radio_"+personid;
	var radioElement = document.getElementById(id); 
	radioElement.checked=true;			
}

$(document).ready(function(){
	if($("#pa0502_seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#pa0502_seach_EmpTypeCodeNo").val();
		ajaxEmpTypeForGroupToList(EMP_TYPE,"pa0502_seach_JobTypeGroupNo","pa0502_seach_EmpTypeCodeNo",
				"pa0502_seach_CPNY","pa0502_limit");
		//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
	}
});

//删除数据，可进行批量的删除或者单一的删除
	function update_pa0502(form) {
		var flag = false;

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);

		var cs = document.getElementsByName("check_pa0502");
		var PERSONIDS = '';
		var PARAM = '';
		if (cs[0].checked == true) {
				flag = true;
				if($.trim($("#PERSON_ID_" + 0).val()) != ''){
					PERSONIDS = $.trim($("#PERSON_ID_" + 0).val());
					if($.trim($("#PARAM_" + 0).val()) != ''){
				      PARAM = $.trim($("#PARAM_" + 0).val());
			        }else{
			           alert('修改比例不能为空！');
			           return false;
			        }
			    }
		 }
		for ( var i = 1; i < cs.length; i++) {
			if(cs[i].checked == true){
			    flag = true;
			    if(PERSONIDS!=null && PERSONIDS!=''){
			       if($.trim($("#PERSON_ID_" + i).val()) != ''){
			    	  PERSONIDS +="," + $.trim($("#PERSON_ID_" + i).val());
			       if($.trim($("#PARAM_" + i).val()) != ''){
				        PARAM += "," + $.trim($("#PARAM_" + i).val());
			          }else{
			           alert('修改比例不能为空！');
			           return false;
			        }
			       }
			    }else{
			      if($.trim($("#PERSON_ID_" + i).val()) != ''){
			    	PERSONIDS = $.trim($("#PERSON_ID_" + i).val());
			    	if($.trim($("#PARAM_" + i).val()) != ''){
				       PARAM = $.trim($("#PARAM_" + i).val());
			        }else{
			           alert('修改比例不能为空！');
			           return false;
			        }
			      }
			    }
			}
		}
		if (flag) {
			var result = confirm("确定要修改吗？");
			if (result == true) {
			$form.attr("action", "/pa/wagebase/updateC_PROB_PAY_RAT?PERSONIDS="+PERSONIDS+"&PARAM="+PARAM);
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
											'/pa/wagebase/viewPaAccount','pageNum=1&menuNo=2561&navTabId=pa0502');
								} else if (data.statusCode == "300") {
									alertMsg.info(data.message);
								}
							}
						});
			} else {
				return false;
			}
		} else {
			alertMsg.info('请选择修改项！');

		}
	}
</script>
<a id="importExcelDialog_allow0102" href="#" target="dialog" mask="true">
	<span id="allow0102" style="display: none"></span></a> 
<a id="importExcel_pa0519"  href="#" target="navTab" mask="true"><span style="display:none;">工资计算对象导入结果</span></a>
<div class="pageHeader">
	<form id="viewPaAccount" name="viewPaAccount" onsubmit="return navTabSearch(this);" action="/pa/wagebase/viewPaAccount" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="public.title.empId"/><!--工号-->/
						<spring:message code="public.title.name"/><!--姓名-->
						</td>
						<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
						<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
					</td>
					<td>
						<spring:message code="public.title.deptName"/><!--部门-->
						</td>
						<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="pa" id="viewPaAccount_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="pa" id="viewPaAccount_seachDept" selected="${DEPTNO}"/>
					</td>					
					<td>
						<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
						</td>
						<td>
		 			<ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>					
					<td>
						<input type="checkbox" id="do_EXCEPTION_PA_COUNT" name="do_EXCEPTION_PA_COUNT" 
							<c:if test="${EXCEPTION_PA_COUNT ne 'NO'}">checked="checked"</c:if> onclick="doExceptionChoseCheck();"/>
						<input id="seach_EXCEPTION_PA_COUNT" name="seach_EXCEPTION_PA_COUNT" type="hidden" value="${EXCEPTION_PA_COUNT }">
						&nbsp;&nbsp;异常人员(注:姓名不一致、账号为空、银行为空等)							
					</td>
				</tr>
				<tr>
						<td>
						人员类型组
						</td>
						<td>
							<input type="hidden" id="pa0502_limit" name="limit" value="pa">
						<input type="hidden" id="pa0502_seach_CPNY" name="seach_CPNYFYSQ" value="${defaultCpny}">
							<ait:SelectEmpTypeCode id="pa0502_seach_JobTypeGroupNo"name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
						onChangeName="ajaxEmpTypeForGroupToList(-1,pa0502_seach_JobTypeGroupNo,pa0502_seach_EmpTypeCodeNo,pa0502_seach_CPNY,pa0502_limit)"/>
						</td>
						 
						<td>
						人员类型
						</td>
						<td>
		 			<ait:SelectEmpTypeCode id="pa0502_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
						</td>
						<td>
						计算状态
						</td>
						<td>
							<select name="seach_CALC_FLAG">
							<option value="">全部</option>
							<option value="Y" <c:if test="${CALC_FLAG == 'Y'}">selected</c:if>>Y</option>
							<option value="N" <c:if test="${CALC_FLAG == 'N'}">selected</c:if>>N</option>
							</select>
						</td>
						<td>
						<input type="checkbox" id="do_DIMISSION_PA_COUNT" name="do_DIMISSION_PA_COUNT" 
							<c:if test="${do_DIMISSION_PA_COUNT ne 'NO'}">checked="checked"</c:if> onclick="doDimissionChoseCheck();"/>
						<input id="do_DIMISSION_PA_COUNT" name="do_DIMISSION_PA_COUNT" type="hidden" value="${do_DIMISSION_PA_COUNT }">
						&nbsp;&nbsp;包含本考勤月开始前离职人员
					</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	
</div>

<div class="pageContent">
	<c:set value="/pa/wagebase/updatePaAccountCtrollerView?PERSON_ID={sid}" var="edit_Url" />
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>	
	<div class="formBar">
	<ul class="toolBar">
		<li>
		<a class="buttonActive"
			href="/pa/wagebase/exportPaAccountExcelModule"><span>
			<!-- href="/pa/wagebase/exportPaAllowanceExcelModule"><span> -->
			<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
		</a>	
		</li>
		<li>
		<a class="buttonActive"
			href="/pa/excelImport/importExcelData?importFunName=/importPaAccount" target="dialog" mask="true" width="500" height="200" >
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
		</a>
		</li>
		<li>
			<div class="buttonActive">
				<a onclick="reportExcel(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</div>
		</li>
		<li>
			<div class="buttonActive">
							<div class="buttonContent">
								<button type="button"  onclick="f_CalcFlagUpdateByPerson_id();">
									<spring:message code="button.update" /><!-- 修改-->
								</button>
							</div>
						</div>				
					</li>
					<c:if test="${defaultCpny eq 'LGESY'}">
			<li>
				<div class="buttonActive">
					<div class="buttonContent">

						<button type="button"
							onclick="update_pa0502('importExcel_pa0519');">
							支付比例<spring:message code="button.update" />
						</button>
					</div>
				</div></li>
				</c:if>
	</ul>
</div>
	<%-- 
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	--%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="2%"><input type="checkbox" name="c1_bx0103_c"
					id="c1_bx0103_c" class="checkboxCtrl" group="check_pa0502">
				</th>
				<th width="6%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号-->
				</th>
				<th width="4%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.title.message.empHrmName"/><!--人事姓名-->
				</th>
				<th width="6%" orderField="nlssort(CARD_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.title.message.empPaName"/><!--账号名-->
				</th>
				<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.deptName"/><!--部门-->
				</th>
				<th width="6%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
				</th>
				<th width="6%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
				</th>
				<c:if test="${defaultCpny eq 'LGESY'}">
				<th width="8%" orderField="nlssort(PROB_PAY_RAT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					试用支付比例（G）
				</th>
				<th width="8%" orderField="nlssort(C_PROB_PAY_RAT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}" >
					试用支付比例（C）
				</th>
				</c:if>
<!-- 				<th width="8%" orderField="nlssort(BANK_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}"> -->
<!-- 					<spring:message code="pa.wagebase.title.openAccountBanks"/>开户行 -->
<!-- 				</th> -->
				<th width="8%" orderField="nlssort(BANK_BRANCH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.title.message.bankBranchNameInfo"/><%--银行支行名称--%>
				</th>
				<th width="12%" orderField="CARD_NO" class="${orderDirection}">
					<spring:message code="pa.wagebase.title.accountNo"/><!--账号-->
				</th>				
				
				<th width="6%" orderField="REMARK" class="${orderDirection}">
					修改原因
				</th>
				<th width="8%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
					<spring:message code="pa.wagebase.title.caculateFlag"/>(更改)
				</th>
				<th width="6%" orderField="UPDATE_NAME" class="${orderDirection}">
					修改人
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
			    <tr target="sid" rel="${item.PERSON_ID}"> 
			        <td class="td_center" style="white-space:nowrap"><input
						type="checkbox" id="check_pa0502" name="check_pa0502"
						value="${i.index}" />
					<input type="hidden" name="PERSON_ID_${i.index}" id="PERSON_ID_${i.index}" value="${item.PERSON_ID}"/>	
						</td>
					<td style="text-align:center;">${item.EMPID}</td>
					<td style="text-align:center;">${item.CHINESE_NAME}</td>
					<td style="text-align:center;">${item.CARD_NAME}</td>
					<td style="text-align:center;">${item.DEPTNAME}</td>
					<td style="text-align:center;">${item.JOIN_COMPANY_DATE}</td>
					<td style="text-align:center;">${item.DATE_LEFT}</td>
					<c:if test="${defaultCpny eq 'LGESY'}">
					<td style="text-align:center;">${item.PROB_PAY_RAT}</td>
					<td style="text-align:center;"><input type="text" id="PARAM_${i.index}" name="PARAM_${i.index}" value="${item.C_PROB_PAY_RAT}"/></td>
					</c:if>
<!-- 					<td style="text-align:center;">${item.BANK_NAME}</td> -->
					<td style="text-align:center;">${item.BANK_BRANCH_NAME}</td>
					<td style="text-align:center;">${item.CARD_NO}</td>
					<td style="text-align:center;"><input id="remark_${item.PERSON_ID}" type="text" name="remark_${item.PERSON_ID}" value="${item.UPDATE_REMARK }"/> </td>
					<td style="text-align:center;">
						<select id="calc_flag_${item.PERSON_ID }" onchange="selectradio('${item.PERSON_ID }');";>
							<option value="Y" <c:if test="${item.CALC_FLAG eq 'Y' }">selected="selected"</c:if> >Y</option>
						    <option value="N" <c:if test="${item.CALC_FLAG eq 'N' }">selected="selected"</c:if> >N</option>							
						</select>
						<input id="radio_${item.PERSON_ID }" type="radio" name="personid" value="${item.PERSON_ID }" style="display:none;" /> 
					</td>
					<td style="text-align:center;">${item.UPDATE_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
	</form>
	<c:set value="/pa/wagebase/viewPaAccount" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> 
	
</div>
