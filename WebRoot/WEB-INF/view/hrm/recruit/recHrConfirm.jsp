<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#viewRTHCInfoTable",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 280,
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
	$("#viewRTHCInfoTable tbody",navTab.getCurrentPanel()).on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
});

function validateRecHrAffirmCallback(flag,form,callback) {	
	var $form = $("#" + form);
	var selectFlag = false;	
	var ids = document.getElementsByName("hr3706Check");
	for ( var i = 0; i < ids.length; i++) {
		if (ids[i].checked) {
			selectFlag = true;
             /*var remark = $("AFFIRM_REMARK_"+ids[i].val()).val();
             alert(remark);
             if(flag==2 && (remark==null||remark=="")){
            	 alertMsg.error('否决需填写否决原因');
             }*/
		}
	}
	if(selectFlag){
		alertMsg.confirm("<spring:message code='zxc.hr.viewEvaluate.title.SAVE_CONFIRM' />",//确定要保存吗?
	  		  	{okCall:function(){	
			  	$.ajax({
	  				type: form.method || 'POST',
	  				url:$form.attr("action")+'?affirmFlag='+flag,
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: callback || DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	}else{
		alertMsg.error("<spring:message code='hrm.interviewProcess.pleaseFirstSelectInformation.k' />");//请选择要审批的信息再进行操作！
		return false;
	}	
	return false;
}

function changeURL_person(recEmployeeNo, empName){ 
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
		var href = "/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
		var href = "/hrm/recruit/addSingleRecPageHub?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>
	var empName = empName;
	$.pdialog.open(href,"hr3706", empName, {width:900,height:400,mask:true});
}
function changeURL_view(recInterviewNo){ 
	var href = "/hrm/recruit/viewInterviewAffirmList?REC_INTERVIEW_NO="+ recInterviewNo + "";
	$.pdialog.open(href,"hr3706", "<spring:message code='pa.ins.alert.message.title.clickForDetail' />", {width:900,height:400,mask:true});
}

</script>

<div class="pageHeader">
<input id="loginUser_this" type="hidden" value="${LoginUser.adminID}">
<input id="loginUser_cpnyId" type="hidden" value="${LoginUser.cpnyId}">
 
	<form id="viewRecHrConfirm"
		onsubmit="return navTabSearch(this);"
		action="/hrm/recruit/recHrConfirm" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 姓名： -->
						<spring:message
							code="alert.pa.pasalarycanshu.xingming" />
					</td>
					<td>
						<input type="text" name="seach_EMP_NAME" id="seach_EMP_NAME" value="${EMP_NAME}"/>
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td>
						<!-- 岗位区分 --><spring:message code="hrm.addRecPage.postDivision.k" />:
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_POST_TYPE_CODE"
							parentNo="90000339" selected="${POST_TYPE_CODE }"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					</c:if>
					<td>
						<!-- 最终学历 -->
						<spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_FINAL_EDU"
							parentNo="13769" selected="${FINAL_EDU }"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					<td>
						<!-- 人事确认 -->
						<spring:message code="ess.viewApply.title.humanAffirm" />
					</td>
					<td>
						<select id='seach_FINAL_CONFIRM'name='seach_FINAL_CONFIRM'>
							<option value = '0'<c:if test="${FINAL_CONFIRM == 0 }">selected</c:if>><spring:message code="ess.affirmApply.title.remark.jinxingzhong" /></option>
							<option value = '1'<c:if test="${FINAL_CONFIRM == 1 }">selected</c:if>><spring:message code="ess.affirmApply.title.remark.tongguo" /></option>
							<option value = '2'<c:if test="${FINAL_CONFIRM == 2 }">selected</c:if>><spring:message code="ess.affirmApply.title.remark.foujue"/></option>
						</select>
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
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="validateRecHrAffirmCallback('1','RecHrConfirmForm',navTabAjaxDone)" href="#"><span><!-- 通过 --><spring:message code="ess.affirmApply.title.remark.tongguo" /></span></a>					
			</li>
			<li>
				<a class="buttonActive" onclick="validateRecHrAffirmCallback('2','RecHrConfirmForm',navTabAjaxDone)" href="#"><span><!-- 否决 --><spring:message code="ess.affirmApply.title.remark.foujue"/></span></a>					
			</li>
	    </ul>
</div>
<form name="RecHrConfirmForm" id="RecHrConfirmForm" method="post" action="/hrm/recruit/recruitmentHrConfirm"> 
	<table id="viewRTHCInfoTable" class="orderList"  
		width="100%">
		<thead>
			<tr>
				<th width="2%">No.</th>
				<th width="3%">
					<input type="checkbox" class="checkboxCtrl" group="hr3706Check" />
				</th>
				<th width="10%">
					<!-- 姓名 --><spring:message code="alert.pa.pasalarycanshu.xingming" />
				</th>
				<th width="10%">
					<!-- 最终学历 -->
					<spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />
				</th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th width="10%"><!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" /></th>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<th width="10%">
					<!-- 岗位区分 -->
					<spring:message code="hrm.addRecPage.postDivision.k" />
				</th>
				</c:if>
				<th width="5%">
					<!-- 性别 --><spring:message code="hr.viewPersonalInfo.title.SEX" />
				</th>
				<th width="5%">
						<!-- 年龄 --><spring:message code="hrm.empinfo.AGE" />
					</th>
				
				<th width="10%">
					<!-- 国籍 --><spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
				</th>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<th width="10%">
					<spring:message code="empsubject.officePhone" />
					<!-- 电话 -->
				</th>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="5%">
					<spring:message code="hr.viewCompetence.title.MARK" />
					<!-- 分数-->
				</th>
				</c:if>
				<%-- <th width="10%">
					<spring:message code="hr.hrm.empinfo.Interview.opinion" />
					<!-- 面试意见-->
				</th> --%>
				<th width="15%">
					<!-- 备注 --><spring:message code="hr.viewBadArchives.title.REMARK" />
				</th>
				<th width="10%">
					<spring:message code="hr.hrm.empinfo.miashiqingkuang" />
					<!-- 面试情况-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${readyToHrConfirmInfoList}" var="hrConfirmInfoList" varStatus="i">
				<tr >
					<td style="text-align: center" width="2%">${i.index+1}</td>
					<td style="text-align: center" width="3%">
					     <c:if test="${hrConfirmInfoList.FINAL_CONFIRM eq '0' }">
					        <input type="checkbox" id="hr3706Check${i.index}" name="hr3706Check" value="${i.index}" />
					    </c:if>
							<input type="hidden" name="REC_INTERVIEW_NO_${i.index}" value="${hrConfirmInfoList.REC_INTERVIEW_NO}" />
							<input type="hidden" name="REC_EMPLOYEE_NO_${i.index }" value="${hrConfirmInfoList.REC_EMPLOYEE_NO }"/>
						</td>
					<td class="td_type" width="10%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${hrConfirmInfoList.REC_EMPLOYEE_NO }, "${hrConfirmInfoList.EMP_NAME }");'>
           				<span style="color: blue">${hrConfirmInfoList.EMP_NAME }</span></td>
					<%-- <td style="text-align: center" width="10%">
						<a style="color:blue;" href="/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${hrConfirmInfoList.REC_EMPLOYEE_NO}" target="dialog" mask="true" width="700" height="250" >
						${hrConfirmInfoList.EMP_NAME }
					</td> --%>
					<td style="text-align: center" width="10%">
						${hrConfirmInfoList.FINAL_EDU}
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					    <td style="text-align: center" width="10%">${hrConfirmInfoList.FINAL_SCHOOL}</td>
				    </c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td style="text-align: center" width="10%">
						${hrConfirmInfoList.POST_TYPE}
					</td>
					</c:if>
					<td style="text-align: center" width="5%">
						${hrConfirmInfoList.SEX}
					</td>
					<td style="text-align: center" width="5%">
						${hrConfirmInfoList.AGE}
					</td>
					<td style="text-align: center" width="10%">
						${hrConfirmInfoList.NATIONALITY_CODE}
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td style="text-align: center" width="10%">
						${hrConfirmInfoList.EMP_TELPHONE}
					</td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td style="text-align: center" width="5%">
						${hrConfirmInfoList.SCORE}
					</td>
					</c:if>
					<%-- <td style="text-align: center" width="10%">
						${hrConfirmInfoList.AFFIRM_REMARK}
					</td> --%>
					<td style="text-align: center" width="15%">
						<c:if test="${hrConfirmInfoList.FINAL_CONFIRM eq '0' }">
					        <input type="text" name="FINAL_REMARK_${i.index}" class="textInput"
								maxlength="200" />
					    </c:if>
					    <c:if test="${hrConfirmInfoList.FINAL_CONFIRM ne '0' }">
					        ${hrConfirmInfoList.FINAL_REMARK }
					    </c:if>
					</td>
					<td class="td_type" width="10%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_view(${hrConfirmInfoList.REC_INTERVIEW_NO });'>
           					<span style="color: blue"><spring:message code="pa.ins.alert.message.title.clickForDetail" /></span></td>
					<%-- <td style="text-align: center" width="10%">
						<a href="/hrm/recruit/viewInterviewAffirmList?REC_INTERVIEW_NO=${hrConfirmInfoList.REC_INTERVIEW_NO }" target="dialog" mask="true" 
								width="600" height="300" style="text-decoration:none ;"><spring:message code="pa.ins.alert.message.title.clickForDetail" />
							</a>
					</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
</div>
