<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$("#resumeInfoTable",navTab.getCurrentPanel()).dataTable({
	"bPaginate": true,    //分页
    "bAutoWidth":false,//表格宽度自动变化
    "bProcessing":true,
    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
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
$("#resumeInfoTable tbody",navTab.getCurrentPanel()).on( 'click', 'tr', function () {
       $(this).toggleClass('selected');
   } );

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
	$.pdialog.open(href,"hr3703", empName, {width:900,height:400,mask:true});
}
function changeURL_view(recInterviewNo){ 
	if (recInterviewNo == null || recInterviewNo =="" ) {
		alertMsg.error("<spring:message code='hrm.resumeSearch.weimianshi.k' />");//未面试！
		return false;
	} else {
		var href = "/hrm/recruit/viewInterviewAffirmList?REC_INTERVIEW_NO="+ recInterviewNo + "";
		$.pdialog.open(href,"hr3703", "<spring:message code='pa.ins.alert.message.title.clickForDetail' />", {width:900,height:400,mask:true});
	}
}

</script>


<div class="pageHeader">
<input id="loginUser_this" type="hidden" value="${LoginUser.adminID}">
<input id="loginUser_cpnyId" type="hidden" value="${LoginUser.cpnyId}">
 
	<form id="viewResumeSearch"
		onsubmit="return navTabSearch(this);"
		action="/hrm/recruit/resumeSearch" method="post">
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
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td>
						<!-- 工作单位 --><spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
					</td>
					<td>
						<input type="text" name="seach_OLD_COMPANY" id="seach_OLD_COMPANY" value="${OLD_COMPANY}" />
					</td>
					<td>
						<!-- 电话 --><spring:message code="empsubject.officePhone" />
					</td>
					<td>
						<input type="text" name="seach_EMP_TELPHONE" id="seach_EMP_TELPHONE" value="${EMP_TELPHONE}" />
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
					
					<td>
						<!-- 状态 -->
						<spring:message code="org.title.status" />
					</td>
					<td>
						<select id='seach_TYPE' name='seach_TYPE'>
						
						<option value = '0' <c:if test="${TYPE eq 0}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.resume.new" />
						</option>
						<option value = '1' <c:if test="${TYPE eq 1}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.anpaimianshi" />
						</option>
						<option value = '2' <c:if test="${TYPE eq 2}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.beiyong" />
						</option>
						<option value = '' <c:if test="${empty TYPE }">selected="selected"</c:if>>
						<spring:message code="org.title.PLEASE_SELECT" />
						</option>
						</select>
					</td>
					<td>
						<!-- 面试情况 -->
						<spring:message code="hr.hrm.empinfo.miashiqingkuang" />
					</td>
					<td>
						
						<select id='seach_INTERVIEW' name='seach_INTERVIEW'>
						
						<option value = '0' <c:if test="${INTERVIEW eq 0}">selected="selected"</c:if>>
						<spring:message code="hr.hrm.empinfo.resume.new" />
						</option>
						<option value = '1' <c:if test="${INTERVIEW eq 1}">selected="selected"</c:if>>
						<spring:message code="ess.viewApply.title.pass" />
						</option>
						<option value = '2' <c:if test="${INTERVIEW eq 2}">selected="selected"</c:if>>
						<spring:message code="ess.viewApply.title.reject" />
						</option>
						<option value = '' <c:if test="${empty INTERVIEW }">selected="selected"</c:if>>
						<spring:message code="org.title.PLEASE_SELECT" />
						</option>
						</select>
					</td>
					<td><!-- 招聘时间--><spring:message code="hrm.addRecPage.interviewPeriod.k" />  </td>
			        <td>
			             <input name="seach_INTERVIEW_PERIOD"  id="seach_INTERVIEW_PERIOD" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${INTERVIEW_PERIOD }"/>
			        </td>
				</tr>
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
					</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table id="resumeInfoTable" class="orderList"  width="100%">
		<thead>
			<tr>
				<th width="1%">No.</th>
				<th width="5%"><!-- 姓名 --><spring:message code="inct.salesman.Name" /></th>
				<th width="5%"><!-- 最终学历 --><spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th width="5%"><!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" /></th>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<th width="5%"><!-- 岗位区分 --><spring:message code="hrm.addRecPage.postDivision.k" /></th>
				</c:if>
				<th width="2%"><!-- 性别 --><spring:message code="hr.viewPersonalInfo.title.SEX" /></th>
				<th width="2%"><!-- 年龄 --><spring:message code="hrm.empinfo.AGE" /></th>
				<th width="5%"><!-- 国籍 --><spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<th width="5%"><!-- 电话 --><spring:message code="empsubject.officePhone" /></th>
				</c:if>
				<th width="8%"><!-- 地址 --><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /></th>
				<th width="5%"><!-- 状态 --><spring:message code="is.joininstance.title.statement" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th width="2%"><!-- 工作经验 --><spring:message code="hrm.addRecPage.workExperience.k" /></th>
					<th width="5%"><!-- 工作单位 --><spring:message code="hr.viewWorkInfo.title.CPNY_NAME" /></th>
					<th width="5%"><!-- 招聘时间--><spring:message code="hrm.addRecPage.interviewPeriod.k" /></th>
				</c:if>
				<th width="5%"><!-- 面试情况--><spring:message code="hr.hrm.empinfo.miashiqingkuang" /></th>
				<!--<th width="5%">  创建者<spring:message code="sys.basic.title.createBy" /></th>
				<th width="5%">创建时间 <spring:message code="sys.basic.title.createDate" /></th>-->
				</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="resumeInfo" varStatus="i">
				<tr >
					<td style="text-align: center" width="1%">${i.count}</td>
					<td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${resumeInfo.REC_EMPLOYEE_NO }, "${resumeInfo.EMP_NAME }");'>
           				<span style="color: blue">${resumeInfo.EMP_NAME }</span></td>
					<%-- <td style="text-align: center" width="5%">
						<a style="color:blue;" href="/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${resumeInfo.REC_EMPLOYEE_NO}" target="dialog" mask="true" width="700" height="250" >
						${resumeInfo.EMP_NAME}
					</td> --%>
					<td style="text-align: center" width="5%">${resumeInfo.FINAL_EDU}</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td style="text-align: center" width="5%">${resumeInfo.FINAL_SCHOOL}</td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<td style="text-align: center" width="5%">${resumeInfo.POST_TYPE}</td>
					</c:if>
					<td style="text-align: center" width="2%">${resumeInfo.SEX}</td>
					<td style="text-align: center" width="2%">${resumeInfo.AGE}</td>
					<td style="text-align: center" width="5%">${resumeInfo.NATIONALITY}</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<td style="text-align: center" width="5%">${resumeInfo.EMP_TELPHONE}</td>
					</c:if>
					<td style="text-align: center" width="8%">${resumeInfo.EMP_ADDRESS}</td>
					<td style="text-align: center" width="5%">
						<c:choose>
						<c:when test="${resumeInfo.REC_TYPE==0}"><spring:message code="hr.hrm.empinfo.resume.new" />
						</c:when>
						<c:when test="${resumeInfo.REC_TYPE==1}"><spring:message code="hr.hrm.empinfo.anpaimianshi" />
						</c:when>
						<c:when test="${resumeInfo.REC_TYPE==2}"><spring:message code="hr.hrm.empinfo.beiyong" />
						</c:when>
						<c:when test="${resumeInfo.REC_TYPE==3}"><spring:message code="hr.hrm.empinfo.mianshiyufou" />
						</c:when>
						</c:choose>
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td style="text-align: center" width="5%">${resumeInfo.WORK_EXPERIENCE}</td>
						<td style="text-align: center" width="5%">${resumeInfo.OLD_COMPANY}</td>
						<td style="text-align: center" width="5%">${resumeInfo.INTERVIEW_PERIOD}</td>
					</c:if>
					<td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_view(${resumeInfo.REC_INTERVIEW_NO });'>
           				<span style="color: blue"><spring:message code="pa.ins.alert.message.title.clickForDetail" /></span></td>
					<%-- <td style="text-align: center" width="2%">
					<a mask="true" style="right: 2px; color: blue;"
							href='/hrm/recruit/resumeSearchAffrim?seach_REC_EMPLOYEE_NO=${resumeInfo.REC_EMPLOYEE_NO}'
							target="dialog"> <spring:message code="pa.ins.alert.message.title.clickForDetail" /></a>
						
					</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
