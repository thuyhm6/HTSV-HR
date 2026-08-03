<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewRecruitBatchList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewRecruitBatchListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewRecruitBatchList_SEQ",navTab.getCurrentPanel()).change(function(){
		$("#viewRecruitBatchListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewRecruitBatchList_add",navTab.getCurrentPanel()).click(function(){
		navTab.reload("/hrm/recruitManage/viewRecruitBatchList?SEQ=${SEQ}&FLAG=ADD");
	});
	
	//保存
	$("#viewRecruitBatchList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='ACTIVITY']",navTab.getCurrentPanel()).each(function(i, obj){
			var dob = $("#DOB_" + i,navTab.getCurrentPanel()).html();
			var dateStart = $("#DATE_STARTED_" + i,navTab.getCurrentPanel()).html();
			/* var contractStartD = $("#CONTRACT_START_DATE_" + i,navTab.getCurrentPanel()).html();
			var contractEndD = $("#CONTRACT_END_DATE_" + i,navTab.getCurrentPanel()).html(); */
			var endProDate = $("#END_PROBATION_DATE_" + i,navTab.getCurrentPanel()).html();
			//var startDate = $("#START_DATE_" + i,navTab.getCurrentPanel()).html();
			//var endDate = $("#END_DATE_" + i,navTab.getCurrentPanel()).html();
			var idCarDate = $("#IDCARD_S_DATE_" + i,navTab.getCurrentPanel()).html();
			
			if (dob != "" && dob != null) { var format_dob = dob.substring(6,10)+"/"+dob.substring(3,5)+"/"+dob.substring(0,2);} 
				else { var format_dob = dob; }
			
			if (dateStart != "" && dateStart != null) { var format_dateStart = dateStart.substring(6,10)+"/"+dateStart.substring(3,5)+"/"+dateStart.substring(0,2);} 
			else { var format_dateStart = dateStart; }
			
			/* if (contractStartD != ""  && contractStartD != null) { var format_contractStartD = contractStartD.substring(6,10)+"/"+contractStartD.substring(3,5)+"/"+contractStartD.substring(0,2);} 
			else { var format_contractStartD = contractStartD; }
			
			if (contractEndD != "" && contractEndD != null) { var format_contractEndD = contractEndD.substring(6,10)+"/"+contractEndD.substring(3,5)+"/"+contractEndD.substring(0,2);} 
			else { var format_contractEndD = contractEndD; } */
			
			if (endProDate != "" && endProDate != null) { var format_endProDate = endProDate.substring(6,10)+"/"+endProDate.substring(3,5)+"/"+endProDate.substring(0,2);} 
			else { var format_endProDate = endProDate; }
			/* if (startDate != "") { var format_startDate = startDate.substring(6,10)+"/"+startDate.substring(3,5)+"/"+startDate.substring(0,2);} 
			else { var format_startDate = startDate; }
			
			if (endDate != "") { var format_endDate = endDate.substring(6,10)+"/"+endDate.substring(3,5)+"/"+endDate.substring(0,2);} 
			else { var format_endDate = endDate; } */
			
			if (idCarDate != "" && idCarDate != null) { var format_idCarDate = idCarDate.substring(6,10)+"/"+idCarDate.substring(3,5)+"/"+idCarDate.substring(0,2);} 
			else { var format_idCarDate = idCarDate; }
			
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "SEQ": "' + $("#SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "VIETNAM_NAME": "' + $("#VIETNAM_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "ENGLISH_NAME": "' + $("#ENGLISH_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "EMPID": "' + $("#EMPID_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "DOB": "' + format_dob + '" ,';
			jsonData += ' "DATE_STARTED": "' + format_dateStart + '" ,';
			/* jsonData += ' "CONTRACT_START_DATE": "' + format_contractStartD + '" ,';
			jsonData += ' "CONTRACT_END_DATE": "' + format_contractEndD + '" ,'; */
			jsonData += ' "END_PROBATION_DATE": "' + format_endProDate + '" ,';
			jsonData += ' "TEST_NOT": "' + $("#TEST_NOT_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "JOIN_TYPE_NAME": "' + $("#JOIN_TYPE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "JOIN_DETAIL_TYPE_NAME": "' + $("#JOIN_DETAIL_TYPE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "DEPTNAME": "' + $("#DEPTNO_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "POST_GRADE_NO_NAME": "' + $("#POST_GRADE_NO_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "MAIN_BUSINESS": "' + $("#MAIN_BUSINESS_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "POSITION_NO_NAME": "' + $("#POSITION_NO_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "EMP_TYPE_CODE_NAME": "' + $("#EMP_TYPE_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "COST_CENTER_NAME": "' + $("#COST_CENTER_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "FINAL_DEGREE_CODE_NAME": "' + $("#FINAL_DEGREE_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "START_DATE": "' + $("#START_DATE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "END_DATE": "' + $("#END_DATE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "INSTITUTION_NAME": "' + $("#INSTITUTION_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "SUBJECT_NAME": "' + $("#SUBJECT_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "IDCARD_NO": "' + $("#IDCARD_NO_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "IDCARD_S_DATE": "' + format_idCarDate + '" ,';
			jsonData += ' "ISSUING_AUTHORITY": "' + $("#ISSUING_AUTHORITY_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "SEXCODE_NAME": "' + $("#SEXCODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "NATIONALITY_CODE_NAME": "' + $("#NATIONALITY_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "NATION_CODE_NAME": "' + $("#NATION_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "RELIGION_CODE": "' + $("#RELIGION_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "MARITAL_STATUS_CODE_NAME": "' + $("#MARITAL_STATUS_CODE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "EMAIL": "' + $("#EMAIL_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "EMAIL_SECOND": "' + $("#EMAIL_SECOND_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "SING_ID": "' + $("#SING_ID_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "HOME_PHONE": "' + $("#HOME_PHONE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "TELEPHONE": "' + $("#TELEPHONE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "ADDRESS_CONTENT": "' + $("#ADDRESS_CONTENT_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
			jsonData += ' "REG_PLACE": "' + $("#REG_PLACE_" + i,navTab.getCurrentPanel()).html() + '", ';
			jsonData += ' "PAY_STEP_NO": "' + $("#PAY_STEP_NO_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "POST_FAMILY": "' + $("#POST_FAMILY_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "GRADUATION_ACHIEVEMENT": "' + $("#GRADUATION_ACHIEVEMENT_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "AVERAGE_SCORE": "' + $("#AVERAGE_SCORE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "LANGUAGE_ABILITY": "' + $("#LANGUAGE_ABILITY_" + i,navTab.getCurrentPanel()).html() + '" ';

			jsonData += '}';
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info('<spring:message code="hrm.empinfo.NOTSAVE_DATA"/>');//没有需要保存的数据
			return;
		}
		alertMsg.confirm('<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>',//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/hrm/recruitManage/addRecruitBatchInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	$('.list tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$("#hr0305Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
	
	$('.list tbody tr td:[sysLog="text"]').editable({type:'text'});

	$('.list tbody tr td:[sysLog="select"]').editable({type:'select',
			onblur:function(val,settings){
				$(this).html(val);
				if($(this).attr("id").substr(0,6) == 'DEPTNO'){
					var index = $(this).attr("value");
					$("#COST_CENTER_" + index,navTab.getCurrentPanel()).html(val);
				}
				this.editing = false;
			}
		});
});
function executeRecruitBatch(typeStr) {
	alertMsg.confirm('<spring:message code="hrm.alert.empinfo.Perform_operation"/>',//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/recruitManage/executeRecruit',
  				data:{empIds:$("#viewRecruitBatchList_SEQ",navTab.getCurrentPanel()).val(),type:typeStr},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function changeEmpidType(seq,flag){
	alertMsg.confirm('<spring:message code="hrm.empinfo.SURE_UPDATE_EMPID.Z" />',//确定要修改社号生成方式吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/recruitManage/doSql',
  				data:{sql:"UPDATE HR_EMPLOYEE_RECRUIT_BATCH SET EMPID_TYPE = '" + flag + "' WHERE SEQ = '" + seq + "'"},
  				dataType:"json",
  				cache: false,
  				success: function(data){
  					$("#viewRecruitBatchListForm",navTab.getCurrentPanel()).submit();
  				},
  				error: DWZ.ajaxError
  			});
  	}});
}

function deleteRecruitBatch() {
	var SEQ="";
	var flag=false;
	$("input[name='ACTIVITY']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			SEQ = SEQ  + $(this).val() + ",";
			flag = true;
		}
	});
	SEQ = SEQ + "empty";
	if(flag == false){
		alertMsg.error("<spring:message code="hrm.alert.empinfo.Choice_Perform_operation" />");//请先选择要执行此操作
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete" />",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/recruitManage/deleteRecruitBatchInfo',
  				data:{SEQ:SEQ},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function changeCost(i) {
	var att = $("#DEPTNO_" + i,navTab.getCurrentPanel()).find("option:selected").attr("value");
	alert(att);
	$("#COST_CENTER_" + i,navTab.getCurrentPanel()).html(att);
	
}
</script>
<div class="pageHeader">
<form id="viewRecruitBatchListForm" onsubmit="return navTabSearch(this);" action="/hrm/recruitManage/viewRecruitBatchList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hrm.recruitManage.ZHUCE_DATE.Z"/><!--注册日--></td>
		<td>
			<select id="viewRecruitBatchList_SEQ" name="seach_SEQ">
				<c:forEach items="${viewRegisterInfoList}" var="result">
					<option value="${result.SEQ}" <c:if test="${result.SEQ eq SEQ}">selected</c:if>>${result.REGISTER_DATE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:forEach>
			</select>
		</td>
		<td>
				<a class="w_button" href="/hrm/recruitManage/viewAddRegisterInfo?FLAG=1" target="dialog" mask="true" width="350" height="150" rel="addRegisterInfo">
					<span><spring:message code="hrm.recruitManage.ZHUCE.Z"/><!--注册--></span>
				</a>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<a class="buttonActive" href="/pa/excelExport/downloadExcelTemplate?file=MassNewAction_Hire">
					<span><spring:message code="ar.addempshift.title.downloadmodule" /><!-- 下载导入模板 --></span>
				</a>
			</c:if>
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<a class="buttonActive" href="/pa/excelExport/downloadExcelTemplateHAE?file=MassNewAction_Hire">
					<span><spring:message code="ar.addempshift.title.downloadmodule" /><!-- 下载导入模板 --></span>
				</a>
			</c:if>
		</li>
		<c:if test="${ACTIVITY eq '1'}">
			<li><a class="buttonActive" id="viewRecruitBatchList_Serch" href="#">
			<span><spring:message code="button.search"/><!--查询--></span></a></li>
		</c:if>
		<c:if test="${ACTIVITY ne '1'}">
			<li><a class="buttonActive" href="#" onclick="executeRecruitBatch('CONFIRM_BATCH')">
			<span><spring:message code="hrm.recruitManage.Confirm"/><!--发令确定--></span></a></li>
			<li><a class="buttonActive" id="viewRecruitBatchList_add" href="#">
			<span><spring:message code="button.add"/><!--添加--></span></a></li>
			<!--<li><a class="buttonActive" id="viewRecruitBatchList_Serch" href="#">
			<span><spring:message code="button.search"/>查询</span></a></li>-->
			<li><a class="buttonActive" id="viewRecruitBatchList_Save" href="#">
			<span><spring:message code="button.sys.affirm.save"/><!--保存--></span></a></li>
			<li><a class="buttonActive" href="#" onclick="deleteRecruitBatch()">
			<span><spring:message code="button.delete"/><!--删除--></span></a></li>
			
			<%-- <li><a class="buttonActive" href="/hrm/recruitManage/deleteRecruitBatchInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="hrm.alert.empinfo.Sure.delete"/>"><!-- 确定要删除吗? -->
			<span><spring:message code="button.delete"/><!--删除--></span></a></li> --%>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=38&SEQ=${SEQ}">
					<span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到EXECL--></span>
				</a>
			</li>
			<li>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<a class="add" href="/pa/excelImport/importExcelData?importFunName=/importRecruitTemp&REGISTER_SEQ=${SEQ}" target="dialog" mask="true">
						<span><spring:message code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span>
					</a>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<a class="add" href="/pa/excelImport/importExcelData?importFunName=/importRecruitTempHAE&REGISTER_SEQ=${SEQ}" target="dialog" mask="true">
						<span><spring:message code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span>
					</a>
				</c:if>
			</li>
		</c:if>
	</ul>
</div>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewRecruitBatchListCnt}</div>
				<table class="list" width="3500px" layoutH="120">
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<thead>
						<tr>
							<th>NO.</th>
							<th><input type="checkbox" class="checkboxCtrl" group="ACTIVITY"/><spring:message code="hrm.empinfo.Already_processed"/><!--已处理--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.empid"/><!--社号--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.YUENAN_NAME.Z"/><!--越南姓名--></th>
							<th class="titleColor"><spring:message code="hr.empinfo.english.name"/><!--英文姓名--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.DATE_STARTED" /> <!--入社日期--></th>
							<th class="titleColor"><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE"/><!--试用结束日期--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.JOIN_TYPE.Z"/><!--入社--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.JOIN_DETAIL_TYPE.Z"/><!--入社细节区分--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></th>
							<td class="titleColor"><spring:message code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
							<th class="titleColor"><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></th>
							<th class="titleColor"><spring:message code="hrm.contract.POSITION_NO"/><!--职责--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!--员工类型--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL"/><!--成本中心--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.final_education"/><!--最终学历--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.graduation_date"/><!--毕业日期--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.INSTITUTION_SCHOOL"/><!--毕业学校--></th>
							<th class="titleColor"><spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME"/><!--专业--></th>
							<th class="titleColor"><spring:message code="pa.insurance.title.idNumber"/><!--身份证号--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.IDCARD_QIANFA_DATE.Z"/><!--身份证签发日期--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.IDCARD_QIANFA_JIGUAN.Z"/><!--身份证签发机关--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.SEXCODE" /><!--性别--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.NATION_CODE" /><!--民族--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></th>
							<th class="titleColor"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.present_address" /><!--现住地--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewRecruitBatchList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}
								</td>
								<td class='td_center'>
									<input type="checkbox" name="ACTIVITY" value="${item.SEQ}" />
									<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}"/>
									<input type="hidden" id="PERSON_ID_${i.index}" value="${item.PERSON_ID}"/>
								</td>
								<td sysLog="text" id="EMPID_${i.index}">${item.EMPID}</td>
								<td sysLog="text" id="VIETNAM_NAME_${i.index}">${item.VIETNAM_NAME}</td>
								<td sysLog="text" id="ENGLISH_NAME_${i.index}">${item.ENGLISH_NAME}</td>
								<td sysLog="date" format="dd/MM/yyyy" id="DOB_${i.index}">${item.DOB}</td>
								<td sysLog="date" format="dd/MM/yyyy" id="DATE_STARTED_${i.index}">${item.DATE_STARTED}</td><!-- 入职日期  -->
								<td sysLog="date" format="dd/MM/yyyy" id="END_PROBATION_DATE_${i.index}">${item.END_PROBATION_DATE}</td><!-- 试用期结束日期 -->
								<td sysLog="select" sysValue='${rs}' id="JOIN_TYPE_${i.index}">${item.JOIN_TYPE_NAME}</td><!-- 入社 -->
								<td sysLog="select" sysValue='${rsxj}' id="JOIN_DETAIL_TYPE_${i.index}">${item.JOIN_DETAIL_TYPE_NAME}</td><!-- 入社细节区分 -->
								<td sysLog="select" sysValue='${dept}' id="DEPTNO_${i.index}" value = "${i.index}"  >${item.DEPTNAME}</td>
								<td sysLog="select" sysValue='${zq}' id="POST_FAMILY_${i.index}">${item.POST_FAMILY_NAME}</td>
								<td sysLog="select" sysValue='${dj}' id="POST_GRADE_NO_${i.index}">${item.POST_GRADE_NAME}</td>
								<td sysLog="select" sysValue='${zyyw}' id="MAIN_BUSINESS_${i.index}">${item.MAIN_BUSINESS_NAME}</td>
								<td sysLog="select" sysValue='${zz}' id="POSITION_NO_${i.index}">${item.POSITION_NO_NAME}</td><!--职责-->
								<td sysLog="select" sysValue='${yglx}' id="EMP_TYPE_CODE_${i.index}">${item.EMP_TYPE_CODE_NAME}</td>
								<td sysLog="select" sysValue='${cbzx}' id="COST_CENTER_${i.index}">${item.COST_CENTER_NAME}</td>
								<td sysLog="select" sysValue='${zzxl}' id="FINAL_DEGREE_CODE_${i.index}">${item.FINAL_DEGREE_CODE_NAME}</td><!-- 最终学历 -->
								<td sysLog="date" format="MM/yyyy" id="END_DATE_${i.index}">${item.END_DATE}</td><!-- 毕业日期 -->
								<td sysLog="text" id="INSTITUTION_NAME_${i.index}">${item.INSTITUTION_NAME}</td><!-- 毕业学校 -->
								<td sysLog="text" id="SUBJECT_NAME_${i.index}">${item.SUBJECT_NAME}</td><!-- 专业 -->
								<td sysLog="text" id="IDCARD_NO_${i.index}">${item.IDCARD_NO}</td><!-- 身份证号 -->
								<td sysLog="date" format="dd/MM/yyyy" id="IDCARD_S_DATE_${i.index}">${item.IDCARD_S_DATE}</td><!-- 身份证签发日期 -->
								<td sysLog="text" id="ISSUING_AUTHORITY_${i.index}">${item.ISSUING_AUTHORITY}</td><!-- 身份证签发机关 -->
								<td sysLog="select" sysValue='${xb}' id="SEXCODE_${i.index}">${item.SEXCODE_NAME}</td><!-- 性别 -->
								<td sysLog="select" sysValue='${gj}' id="NATIONALITY_CODE_${i.index}">${item.NATIONALITY_CODE_NAME}</td><!-- 国籍 -->
								<td sysLog="select" sysValue='${minzu}' id="NATION_CODE_${i.index}">${item.NATION_CODE_NAME}</td><!-- 民族 -->
								<td sysLog="text" id="RELIGION_CODE_${i.index}">${item.RELIGION_CODE}</td><!-- 宗教 -->
								<td sysLog="select" sysValue='${jhqf}' id="MARITAL_STATUS_CODE_${i.index}">${item.MARITAL_STATUS_NAME}</td><!-- 结婚状态 -->
								<td sysLog="text" id="EMAIL_SECOND_${i.index}">${item.EMAIL_SECOND}</td><!-- 个人邮箱 -->
								<td sysLog="text" id="SING_ID_${i.index}">${item.SING_ID}</td><!-- EagLem ID -->
								<td sysLog="text" id="HOME_PHONE_${i.index}">${item.HOME_PHONE}</td><!-- 家庭电话 -->
								<td sysLog="text" id="TELEPHONE_${i.index}">${item.TELEPHONE}</td><!-- 手机号码 -->
								<td sysLog="text" id="ADDRESS_CONTENT_${i.index}">${item.ADDRESS_CONTENT}</td><!-- 现住址 -->
								<td sysLog="text" id="REG_PLACE_${i.index}">${item.REG_PLACE}</td><!-- 户口所在地 -->
							</tr>
						</c:forEach>
					</tbody>
				</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<thead>
						<tr>
							<th>NO.</th>
							<th><input type="checkbox" class="checkboxCtrl" group="ACTIVITY"/><spring:message code="hrm.empinfo.Already_processed"/><!--已处理--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.empid"/><!--社号--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.YUENAN_NAME.Z"/><!--越南姓名--></th>
							<th class="titleColor"><spring:message code="hr.empinfo.english.name"/><!--英文姓名--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.DATE_STARTED" /> <!--入社日期--></th>
							<%-- <th class="titleColor"><spring:message code="zxc.hr.contract.CONTRACT_START_DATE" /> <!--合同开始日期--></th>
							<th class="titleColor"><spring:message code="zxc.hr.contract.CONTRACT_END_DATE" /> <!--合同结束日期--></th> --%>
							<th class="titleColor"><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE"/><!--试用结束日期--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.IS_PROBATION.Z"/><!--无试用工资--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.JOIN_TYPE.Z"/><!--入社--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.JOIN_DETAIL_TYPE.Z"/><!--入社细节区分--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></th>
							<th class="titleColor"><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z"/><!--年薪等级--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></th>
							<th class="titleColor"><spring:message code="hr.assignment.group"/><!--职群--></th>
							<th class="titleColor"><spring:message code="hrm.contract.POSITION_NO"/><!--职责--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!--员工类型--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL"/><!--成本中心--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.final_education"/><!--最终学历--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.admission_date"/><!--入学日期--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.graduation_date"/><!--毕业日期--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.INSTITUTION_SCHOOL"/><!--毕业学校--></th>
							<th class="titleColor"><spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME"/><!--专业--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.BIYECHENGJI.Z"/><!--毕业成绩--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.PINGJUNFENSHU.Z"/><!--平均分数--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.Foreign_language_ability"/><!--外语能力--></th>
							<th class="titleColor"><spring:message code="pa.insurance.title.idNumber"/><!--身份证号--></th>
							<th class="titleColor"><spring:message code="hrm.recruitManage.IDCARD_QIANFA_DATE.Z"/><!--身份证签发日期--></th>
							<th class="titleColor"><spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE"/><!--身份证签发地--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.SEXCODE" /><!--性别--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.NATION_CODE" /><!--民族--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></th>
							<th class="titleColor"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机--></th>
							<th class="titleColor"><spring:message code="ess.empInfo.present_address" /><!--现住地--></th>
							<th class="titleColor"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewRecruitBatchList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
									<input type="checkbox" name="ACTIVITY" value="${item.SEQ}" />
									<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}"/>
									<input type="hidden" id="PERSON_ID_${i.index}" value="${item.PERSON_ID}"/>
								</td>
								<td sysLog="text" id="EMPID_${i.index}">${item.EMPID}</td>
								<td sysLog="text" id="VIETNAM_NAME_${i.index}">${item.VIETNAM_NAME}</td>
								<td sysLog="text" id="ENGLISH_NAME_${i.index}">${item.ENGLISH_NAME}</td>
								<td sysLog="date" format="dd/MM/yyyy" id="DOB_${i.index}">${item.DOB}</td>
								<td sysLog="date" format="dd/MM/yyyy" id="DATE_STARTED_${i.index}">${item.DATE_STARTED}</td><!-- 入职日期  -->
								<%-- <td sysLog="date" format="dd/MM/yyyy" id="CONTRACT_START_DATE_${i.index}">${item.CONTRACT_START_DATE}</td>
								<td sysLog="date" format="dd/MM/yyyy" id="CONTRACT_END_DATE_${i.index}">${item.CONTRACT_END_DATE}</td> --%>
								<td sysLog="date" format="dd/MM/yyyy" id="END_PROBATION_DATE_${i.index}">${item.END_PROBATION_DATE}</td><!-- 试用期结束日期 -->
								<td sysLog="text" id="TEST_NOT_${i.index}">${item.TEST_NOT}</td><!-- 无试用工资 -->
								<td sysLog="select" sysValue='${rs}' id="JOIN_TYPE_${i.index}">${item.JOIN_TYPE_NAME}</td><!-- 入社 -->
								<td sysLog="select" sysValue='${rsxj}' id="JOIN_DETAIL_TYPE_${i.index}">${item.JOIN_DETAIL_TYPE_NAME}</td><!-- 入社细节区分 -->
								<td sysLog="select" sysValue='${dept}' id="DEPTNO_${i.index}">${item.DEPTNAME}</td>
								<td sysLog="select" sysValue='${dj}' id="POST_GRADE_NO_${i.index}">${item.POST_GRADE_NAME}</td>
								<td sysLog="select" sysValue='${nxdj}' id="PAY_STEP_NO_${i.index}">${item.PAY_STEP_NAME}</td>
								<td sysLog="select" sysValue='${zyyw}' id="MAIN_BUSINESS_${i.index}">${item.MAIN_BUSINESS_NAME}</td>
								<td sysLog="select" sysValue='${zq}' id="POST_FAMILY_${i.index}">${item.POST_FAMILY_NAME}</td>
								<td sysLog="select" sysValue='${zz}' id="POSITION_NO_${i.index}">${item.POSITION_NO_NAME}</td><!--职责-->
								<td sysLog="select" sysValue='${yglx}' id="EMP_TYPE_CODE_${i.index}">${item.EMP_TYPE_CODE_NAME}</td>
								<td sysLog="select" sysValue='${cbzx}' id="COST_CENTER_${i.index}">${item.COST_CENTER_NAME}</td>
								<td sysLog="select" sysValue='${zzxl}' id="FINAL_DEGREE_CODE_${i.index}">${item.FINAL_DEGREE_CODE_NAME}</td><!-- 最终学历 -->
								<td sysLog="date" format="MM/yyyy" id="START_DATE_${i.index}">${item.START_DATE}</td><!-- 入学日期 -->
								<td sysLog="date" format="MM/yyyy" id="END_DATE_${i.index}">${item.END_DATE}</td><!-- 毕业日期 -->
								<td sysLog="text" id="INSTITUTION_NAME_${i.index}">${item.INSTITUTION_NAME}</td><!-- 毕业学校 -->
								<td sysLog="text" id="SUBJECT_NAME_${i.index}">${item.SUBJECT_NAME}</td><!-- 专业 -->
								<td sysLog="select" sysValue='${bycj}' id="GRADUATION_ACHIEVEMENT_${i.index}">${item.GRADUATION_ACHIEVEMENT_NAME}</td>
								<td sysLog="text" id="AVERAGE_SCORE_${i.index}">${item.AVERAGE_SCORE}</td>
								<td sysLog="select" sysValue='${wynl}' id="LANGUAGE_ABILITY_${i.index}">${item.LANGUAGE_ABILITY_NAME}</td>
								<td sysLog="text" id="IDCARD_NO_${i.index}">${item.IDCARD_NO}</td><!-- 身份证号 -->
								<td sysLog="date" format="dd/MM/yyyy" id="IDCARD_S_DATE_${i.index}">${item.IDCARD_S_DATE}</td><!-- 身份证签发日期 -->
								<td sysLog="text" id="ISSUING_AUTHORITY_${i.index}">${item.ISSUING_AUTHORITY}</td><!-- 签发地 -->
								<td sysLog="select" sysValue='${xb}' id="SEXCODE_${i.index}">${item.SEXCODE_NAME}</td><!-- 性别 -->
								<td sysLog="select" sysValue='${gj}' id="NATIONALITY_CODE_${i.index}">${item.NATIONALITY_CODE_NAME}</td><!-- 国籍 -->
								<td sysLog="select" sysValue='${minzu}' id="NATION_CODE_${i.index}">${item.NATION_CODE_NAME}</td><!-- 民族 -->
								<td sysLog="select" sysValue='${jhqf}' id="MARITAL_STATUS_CODE_${i.index}">${item.MARITAL_STATUS_NAME}</td><!-- 结婚状态 -->
								<td sysLog="text" id="EMAIL_SECOND_${i.index}">${item.EMAIL_SECOND}</td><!-- 个人邮箱 -->
								<td sysLog="text" id="EMAIL_${i.index}">${item.EMAIL}</td>
								<td sysLog="text" id="SING_ID_${i.index}">${item.SING_ID}</td><!-- EagLem ID -->
								<td sysLog="text" id="HOME_PHONE_${i.index}">${item.HOME_PHONE}</td><!-- 家庭电话 -->
								<td sysLog="text" id="TELEPHONE_${i.index}">${item.TELEPHONE}</td><!-- 手机号码 -->
								<td sysLog="text" id="ADDRESS_CONTENT_${i.index}">${item.ADDRESS_CONTENT}</td><!-- 现住址 -->
								<td sysLog="text" id="REG_PLACE_${i.index}">${item.REG_PLACE}</td><!-- 户口所在地 -->
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
				</table>
		</div>