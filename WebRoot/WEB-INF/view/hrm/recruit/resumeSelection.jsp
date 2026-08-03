<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(function(){
	$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 270,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
	});
});

function validateCallbackUpdateResume(obj) {

	var $form = $("#updateResume");

	if (!$form.valid()) {
		
		return false;
	}
	jsonData = '[';

	$("input[name='hr3702Check']:checked",	navTab.getCurrentPanel()).each(function() {
                          var i=$(this).val();
						if (jsonData.length > 1) {
							jsonData += ',{';
						} else {
							jsonData += '{';
						}
						jsonData += ' "REC_EMPLOYEE_NO": "' + $.trim($("#REC_EMPLOYEE_NO_" + i).val()) + '" ,';
						
						jsonData += ' "FLAG": "' + obj + '" ';
						jsonData += '}';     
						
						
					});
	jsonData += ']';
	
  
		if (jsonData.length == 2) {
		alertMsg.info('<spring:message code="hrm.empinfo.NOTSAVE_DATA" />');//没有需要保存的数据 
		return;
	}
	;
	alertMsg.confirm('<spring:message code="zxc.hr.viewEvaluate.title.SAVE_CONFIRM" />', {   //确定要保存吗？
		okCall : function() {
	 
		 	 $.ajax( {
				type : 'POST',
				url : $form.attr("action"),
				data : [ {
					name : 'jsonData',
					value : jsonData
				} ],
				dataType : "json",
				cache : false,
				success : navTabAjaxDone,
				error : DWZ.ajaxError
			});

		}
	});
	

}
	
function searchPop_hr0303(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/hrm/contractInfo/viewContractInfoForSearch?seach_FIRST_FLAG=1';
	var refreshMenuCode = 'hr0303';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.contract_search" />'));   //合同查询
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_hr0303", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPop_hr0303", navTab.getCurrentPanel()).click();
}
function downloadExl(url) {
	$('#viewContractInfoForSearch').attr("action", url);
	$('#viewContractInfoForSearch').attr("onsubmit", '');
	$('#viewContractInfoForSearch').submit();
	$('#viewContractInfoForSearch').attr("action",
			'/hrm/contractInfo/viewContractInfoForSearch');
	$('#viewContractInfoForSearch').attr("onsubmit",
			'return navTabSearch(this);');
}
$('#resumeInfoTable tbody tr td:[sysLog="date"]').editable( {
	type : 'date'
});
$('#resumeInfoTable tbody tr td:[sysLog="text"]').editable( {
	type : 'text'
});
$('#resumeInfoTable tbody tr td:[sysLog="select"]').editable( {
	type : 'select'
});

//点击保存   


$("#hr0303_Save", navTab.getCurrentPanel()).click(function() {
//获取页面的值
	jsonData = '[';

	$("input[name='hr0303_ACTIVITY']:checked",	navTab.getCurrentPanel()).each(function() {
                                   var i=$(this).val();
						if (jsonData.length > 1) {
							jsonData += ',{';
						} else {
							jsonData += '{';
						}
						jsonData += ' "CONTRACT_NO": "' + $.trim($("#CONTRACT_NO_" + i,navTab.getCurrentPanel()).val()) + '" ,';
						jsonData += ' "START_CONTRACT_DATE": "' + $.trim($("#START_CONTRACT_DATE_" + i,navTab.getCurrentPanel()).html()) + '" ,';  
						jsonData += ' "END_CONTRACT_DATE": "' + $.trim($("#END_CONTRACT_DATE_" + i,navTab.getCurrentPanel()).html()) + '" ,';  
						jsonData += ' "WORK_POSITION": "' + $.trim($("#WORK_POSITION_" + i,navTab.getCurrentPanel()).html()) + '" ,'; 
						jsonData += ' "WORK_CONTENT": "' + $.trim($("#WORK_CONTENT_" + i,navTab.getCurrentPanel()).html()) + '" ,';
						jsonData += ' "CONTRACT_TYPE": "' + $.trim($("#CONTRACT_TYPE_" + i,navTab.getCurrentPanel()).html())+ '" ,';
						jsonData += ' "WORK_TIME": "' + $.trim($("#WORK_TIME_" + i,navTab.getCurrentPanel()).html())+ '" ,';
						jsonData += ' "UPDATED_BY": "' +$.trim($("#loginUser_this",navTab.getCurrentPanel()).attr("value")) + '" ,';
						jsonData += ' "interCpnyID": "' +$.trim($("#loginUser_cpnyId",navTab.getCurrentPanel()).attr("value")) + '" ,';
						jsonData += ' "SALARY": "' + $.trim($("#SALARY_" + i,navTab.getCurrentPanel()).html()) + '" ,';
						jsonData += ' "REMARK": "' + $.trim($("#REMARK_" + i,navTab.getCurrentPanel()).html()) + '" ';
						jsonData += '}';     
						
						
					});
	jsonData += ']';
		if (jsonData.length == 2) {
		alertMsg.info('<spring:message code="hrm.empinfo.NOTSAVE_DATA" />');//没有需要保存的数据 
		return;
	}
	;
	alertMsg.confirm('<spring:message code="zxc.hr.viewEvaluate.title.SAVE_CONFIRM" />', {   //确定要保存吗？
		okCall : function() {
	 
		 	 $.ajax( {
				type : 'POST',
				url : '/hrm/contractInfo/updateContractInfoForUpdate',
				data : [ {
					name : 'jsonData',
					value : jsonData
				} ],
				dataType : "json",
				cache : false,
				success : navTabAjaxDone,
				error : DWZ.ajaxError
			});

		}
	});

});
function changeURL_person(recEmployeeNo, empName){ 
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
		var href = "/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
		var href = "/hrm/recruit/addSingleRecPageHub?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>
	var empName = empName;
	$.pdialog.open(href,"hr3702", empName, {width:900,height:400,mask:true});
}
</script>


<div class="pageHeader">
<input id="loginUser_this" type="hidden" value="${LoginUser.adminID}">
<input id="loginUser_cpnyId" type="hidden" value="${LoginUser.cpnyId}">
 
	<form id="viewResumeSearch"
		onsubmit="return navTabSearch(this);"
		action="/hrm/recruit/resumeSelection" method="post">
		<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG"
			value="1" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}" />
					</td>
					<!--<td style="text-align: left">
						<a class="btnLook" id="searchPop_hr0303"
							onclick="searchPop_hr0303()" href="#" lookupGroup="person"> </a>
					</td>
					<td>
						${empInfoShow}
					</td>
					-->
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td>
						<!-- 岗位区分 --><spring:message code="hrm.addRecPage.postDivision.k" />:
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_POST"  id="seach_POST" 
							parentNo="90000339" selected="${POST}"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					</c:if>
					<td>
						<!-- 最终学历 -->
						<spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_EDUCATION"
							parentNo="13769" selected="${EDUCATION}"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td>
						<!-- 身份证号 --> <spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" />
					</td>
					<td>
						<input type="text" name="seach_CERT_NUMBER" id="seach_CERT_NUMBER" value="${CERT_NUMBER}" />
					</td>
					<td>
						<!-- 电话 --> <spring:message code="empsubject.officePhone" />
					</td>
					<td>
						<input type="text" name="seach_EMP_TELPHONE" id="seach_EMP_TELPHONE" value="${EMP_TELPHONE}" />
					</td>
					</c:if>
				</tr>
				<tr>
					<td>
						<!-- 工作经验 -->
						<spring:message code="hrm.addRecPage.workExperience.k" />
					</td>
					
						<td>
						<select name="seach_eqOrMore">
							<option value="0"
								<c:if test="${eqOrMore eq 0}">selected="selected"</c:if>>
								<!-- 等于 --><spring:message code="sys.affirm.title.euqal" />
							</option>
							<option value="1"
								<c:if test="${eqOrMore eq 1}">selected="selected"</c:if>>
								<!-- 大于等于 --><spring:message code="sys.affirm.title.moreThanEqualTo" />
							</option>
						</select>
					<input name="seach_CONCOUNT" type="text" value="${CONCOUNT }" size="5" />
					<span><spring:message code="inct.salesman.year" /><!-- 年 --></span>
					</td>
					
					<td><!-- 面试与否 --><spring:message code="hr.hrm.empinfo.mianshiyufou" /></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_MIANYN"
							parentNo="14892" selected="${MIANYN}"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
						
					</td>
					<td><!-- 简历状态 --><spring:message code="hr.hrm.empinfo.jianlitype" /></td>
					<td>
						<select id='seach_TYPE' name='seach_TYPE'>
						<option value = '0' <c:if test="${INTERVIEW eq 0}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.resume.new" />
						</option>
						
						<option value = '2' <c:if test="${INTERVIEW eq 2}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.beiyong" />
						</option>
						
						</select>
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td><!-- 平均分数 --><spring:message code="hrm.recruitManage.PINGJUNFENSHU.Z" /></td>
						<td>
							<input name="seach_S_AVERAGE_SCORE" type="text" value="${S_AVERAGE_SCORE }" size="5" />  ~  
							<input name="seach_E_AVERAGE_SCORE" type="text" value="${E_AVERAGE_SCORE }" size="5" />
						</td>
						<td><!-- 外语能力 --><spring:message code="hrm.empinfo.Foreign_language_ability" /></td>
						<td>
							<ait:SelectSyCodeByCpnyID name="seach_LANGUAGE_ABILITY" parentNo="14015514" 
								selected="${LANGUAGE_ABILITY}" cnpyID="${LoginUser.cpnyId}" limit="all" />
						</td>
						<td><!-- 毕业成绩 --><spring:message code="hrm.recruitManage.BIYECHENGJI.Z" /></td>
						<td>
							<ait:SelectSyCodeByCpnyID name="seach_GRADUATION_ACHIEVEMENT" parentNo="14014324" 
								selected="${GRADUATION_ACHIEVEMENT}" cnpyID="${LoginUser.cpnyId}" limit="all" />
						</td>
					</tr>
				</c:if>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive" onclick="validateCallbackUpdateResume(1);" href="#"> 
						<span><!-- 安排面试 --><spring:message code="hr.hrm.empinfo.anpaimianshi" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="validateCallbackUpdateResume(2);" href="#"> 
						<span><!-- 留作备用 --><spring:message code="hr.hrm.empinfo.beiyong" /></span>
						</a>
					</li>
					</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<form id="updateResume" method="post" action="/hrm/recruit/updateResume" class="pageForm required-validate">
	<table id="viewInfoTable" class="list" width="100%" >
		<thead>
			<tr>
				<th width="1%">No.</th>
				<th width="1%"><input type="checkbox" class="checkboxCtrl" group="hr3702Check" /></th>
				<th width="5%"><!-- 姓名 --><spring:message code="inct.salesman.Name" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="5%"><!-- 身份证号 --> <spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" /></th>
					<th width="5%"><!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />  </th>
				</c:if>
				<th width="5%"><!-- 最终学历 --><spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" /></th>
				<th width="5%"><!-- 地址 --><spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME" /></th>
				<th width="5%"><!-- 地址 --><spring:message code="hrm.addRecPage.workExperience.k" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<th width="5%"><!-- 岗位区分 --><spring:message code="hrm.addRecPage.postDivision.k" /></th>
				</c:if>
				<th width="5%"><!-- 性别 --><spring:message code="hr.viewPersonalInfo.title.SEX" /></th>
				<th width="5%"><!-- 性别 --><spring:message code="main.home.message.chushengriqi" /></th>
				<th width="5%"><!-- 年龄 --><spring:message code="hrm.empinfo.AGE" /></th>
				<th width="5%"><!-- 年龄 --><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /></th>
				<th width="5%"><!-- 国籍 --><spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /></th>
				<th width="5%"><!-- 国籍 --><spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" /></th>
				<th width="5%"><!-- 电话 --><spring:message code="empsubject.officePhone" /></th>
				<th width="5%"><!-- 地址 --><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /></th>
				<th width="5%"><!-- 地址 --><spring:message code="ess.personalinfo.title.email" /></th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="resumeInfo" varStatus="i">
				<tr >
					<td style="text-align: center" width="1%">${i.count}</td>
					<td style="text-align: center; padding-top: 7px;" width="1%">
						<input type="checkbox" id="hr3702Check${i.index}" name="hr3702Check" value="${i.index}" />
						<input type="hidden" name="REC_EMPLOYEE_NO_${i.index}" id="REC_EMPLOYEE_NO_${i.index}" value="${resumeInfo.REC_EMPLOYEE_NO}" />
						<input type="hidden" name="FLAG_${i.index}" id="FLAG_${i.index}" value="" />
					</td>
					<td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${resumeInfo.REC_EMPLOYEE_NO }, "${resumeInfo.EMP_NAME }");'>
           			<span style="color: blue">${resumeInfo.EMP_NAME }</span></td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td style="text-align: center" width="5%">${resumeInfo.CERT_NUMBER}</td>
						<td style="text-align: center" width="5%">${resumeInfo.FINAL_SCHOOL}</td>
					</c:if>
					<td style="text-align: center" width="5%">${resumeInfo.FINAL_EDU}</td>
					<td style="text-align: center" width="5%">${resumeInfo.SUBJECT}</td>
					<td style="text-align: center" width="5%">${resumeInfo.WORK_EXPERIENCE} Năm</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<td style="text-align: center" width="5%">${resumeInfo.POST_TYPE}</td>
					</c:if>
					<td style="text-align: center" width="5%">${resumeInfo.SEX}</td>
					<td style="text-align: center" width="5%">${resumeInfo.EMP_BIRTHDAY}</td>
					<td style="text-align: center" width="5%">${resumeInfo.AGE}</td>
					<td style="text-align: center" width="5%">${resumeInfo.MARITAL_STATUS_CODE}</td>
					<td style="text-align: center" width="5%">${resumeInfo.NATIONALITY}</td>
					<td style="text-align: center" width="5%">${resumeInfo.CERT_NUMBER}</td>
					<td style="text-align: center" width="5%">${resumeInfo.EMP_TELPHONE}</td>
					<td style="text-align: center" width="5%">${resumeInfo.EMP_ADDRESS}</td>
					<td style="text-align: center" width="5%">${resumeInfo.EMP_EMAIL}</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
</div>
