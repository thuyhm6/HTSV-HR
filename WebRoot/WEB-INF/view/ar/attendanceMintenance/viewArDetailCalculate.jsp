<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
var $dialog;
$(function(){
	$("#loading_viewardetailcalculate").hide();
});
//function searchEmp() {

//	$dialog = $.ligerDialog.open({
//		isDrag: false,
//		width: $("#layout1").width() - 50,
//		height: 350,
//		url: '/ar/basic/viewArSearchEmployee?SELECT_TYPE=AR_SUPERVISOR'
//	});

//}
//function initEmpId(data) {

//	$("#SUPERVISOR_ID").val(data.EMPID);
//	$("#name").html(data.CHINESENAME + "( " + data.DEPTNAME + " )");
//	$dialog.close();


//}     

function changeType(no){
	var allTr = new Array(1, 2, 3);
	for (var i = 0; i < allTr.length; i++){
		if (no == allTr[i]){
			$("#tr" + allTr[i]).show();
		} else {
			$("#tr" + allTr[i]).hide();
		}
	}
}

function f_Calculate_viewardetailcalculate(){
	var str = "";
	$("#layout1 input:radio").each(function() {
		if (this.checked) {
			str = this.value;
		}
	});

	if (document.form_viewardetailcalculage.FROM_DATE.value == "" || document.form_viewardetailcalculage.TO_DATE.value == ""){
		//开始或结束时间不能为空！
		alertMsg.error("<spring:message code='ar.alert.message.viewCompanyCalendar.datenull'/>");
		return;
	}

	if (document.form_viewardetailcalculage.FROM_DATE.value > document.form_viewardetailcalculage.TO_DATE.value){
		//计算结束时间应大于等于计算起始时间
		alertMsg.error("<spring:message code='ar.viewardetailcaculate.title.caldate'/>");
		return;
	}

	var urlParam = "";
	//部门别
	if (str == "部门别"){//}"<spring:message code='ar.viewardetailcaculate.title.bydept'/>"){
		if (document.form_viewardetailcalculage.DEPTID.value == "") {
			//请选择部门
			alertMsg.error("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
			return;
		}
		var sonDeptFlag = "";
		if(document.getElementById('includeSonDept_SHIFT').checked){
			sonDeptFlag = "YES";
		}else{
			sonDeptFlag = "NO";
		}
		urlParam = 'caltype=dept&deptid=' + document.form_viewardetailcalculage.DEPTID.value + '&sonDeptFlag=' + sonDeptFlag;
	}
	//员工别
	else if (str == "员工别"){//}"<spring:message code='ar.viewardetailcaculate.title.byperson'/>"){

		document.form_viewardetailcalculage.PERSON_ID.value = document.form_viewardetailcalculage.personId.value;
		
		if (document.form_viewardetailcalculage.PERSON_ID.value == "") {
			//请选择员工
			alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.chooseperson'/>");
			return;
		}
		urlParam = 'caltype=emp&empid=' + document.form_viewardetailcalculage.PERSON_ID.value;
	}
	//考勤员别
	else if (str == "考勤员别"){//}"<spring:message code='ar.viewardetailcaculate.title.bysupervisior'/>"){
		if (document.form_viewardetailcalculage.arSupervisorId.value == "") {
			//请选择考勤员
			alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.choosesupervisior'/>");
			return;
		}
		urlParam = 'caltype=supervisor&supervisorId=' + document.form_viewardetailcalculage.arSupervisorId.value;

	}
   
	//是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
		    icon=1;
			$("#loading_viewardetailcalculate").show();
			$("#detailCalculate").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/detailCalculate?' + urlParam + '&from_date=' + document.form_viewardetailcalculage.FROM_DATE.value + '&to_date=' + document.form_viewardetailcalculage.TO_DATE.value,
	
				dataType: 'json',
				success: function(responseStr) {
				    
					$("#ardetail_calculateResult").html(responseStr);
					$("#loading_viewardetailcalculate").hide();
					$("#detailCalculate").show();
					icon=0;
					checkSession();
				}
			});
		}
	});
}
function f_Calculate_shihou(){
	var str = "";
	 
  //是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
		    icon=1;
			$("#loading_viewardetailcalculate").show();
			$("#detailCalculate").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/detailShiHouCalculate',
	
				dataType: 'json',
				success: function(responseStr) {
				    
					$("#ardetail_calculateResult").html(responseStr);
					$("#loading_viewardetailcalculate").hide();
					$("#detailCalculate").show();
					icon=0;
					checkSession();
				}
			});
		}
	});
}
function f_Calculate_LastMonth(){
	var str = "";
	 
  //是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
		    icon=1;
			$("#loading_viewardetailcalculate").show();
			$("#detailCalculate").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/detailLastMonthCalculate',
	
				dataType: 'json',
				success: function(responseStr) {
				    
					$("#ardetail_calculateResult").html(responseStr);
					$("#loading_viewardetailcalculate").hide();
					$("#detailCalculate").show();
					icon=0;
					checkSession();
				}
			});
		}
	});
}

</script>
<form name="form_viewardetailcalculage" method="post" action="" id="form_viewardetailcalculage">
	<div id="layout1">
		<div id="loading_viewardetailcalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div class="formBar">
			<ul class="toolBar">
			 <c:if test="${cpny_id eq 'TSTO'}">
			    <li>
					<a onclick="f_Calculate_LastMonth();"><span><!-- 追溯考勤计算  --><spring:message code="ar.viewArDetailCalculate.ZHUISUKAOQINJISUAN.b"/></span></a>
				</li>
			</c:if>	
			<c:if test="${LoginUser.adminID eq '11111111' }">
			    <li>
					<a onclick="f_Calculate_shihou();"><span><!-- 事后申请计算  --><spring:message code="ar.viewArDetailCalculate.SHIHOUSHENQINGJISUAN.b"/></span></a>
				</li>
			</c:if>
				<li>
					<a onclick="f_Calculate_viewardetailcalculate();"><span><!-- 明细计算 --><spring:message code="ar.viewardetailcaculate.title.detailcal"/></span></a>
				</li>
			</ul>
		</div>
		<div id='detailCalculate' title="<spring:message code='ar.viewardetailcaculate.title.detailcal'/>">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="user_table">

				<tr>
					<td class="td_title">
						<!-- 计算类型 --><spring:message code="ar.viewardetailcaculate.title.caltype"/>
					</td>
					<td class="td_type">
									<input type="radio" value="部门别" name="group" checked="checked"
										onclick="changeType('1')" />
									&nbsp;<!-- 部门别 --><spring:message code="ar.viewardetailcaculate.title.bydept"/>
									<input type="radio" value="员工别" name="group"
										onclick="changeType('2')" />
									&nbsp;<!-- 员工别 --><spring:message code="ar.viewardetailcaculate.title.byperson"/>
									<input type="radio" value="考勤员别" name="group"
										onclick="changeType('3');" />
									&nbsp;<!-- 考勤员别 --><spring:message code="ar.viewardetailcaculate.title.bysupervisior"/>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<!-- 计算时间 --><spring:message code="ar.viewardetailcaculate.title.caltime"/>
					</td>
					<td class="td_type">
					<ul class="td_type_ul">
					<li>
						<!-- 开始 --><spring:message code="ar.viewshift.title.start"/>：
					</li>
					<li>
						<input type="text" id="FROM_DATE" name="FROM_DATE" class="Wdate "
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>						
					<li>
						<!-- 结束 --><spring:message code="ar.viewshift.title.end"/>：
					</li>
					<li>
						<input type="text" id="TO_DATE" name="TO_DATE" class="Wdate "
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
						
					    
					      		
					      			
					      		
					      			
					</ul>      		
					<td>
				</tr>

				<tr id="tr1" name="tr1">
					<td class="td_title">
						<!-- 部门 --><spring:message code="public.title.deptName"/>
					</td>
					<td class="td_type">
						<ait:deptList name="DEPTID" limit="ar" id="viewArDetailRecordList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="DEPTID" limit="ar" id="viewArDetailRecordList_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" id="includeSonDept_SHIFT" name="includeSonDept_SHIFT"  
	            			title="<spring:message code='hr.viewCondSql.title.BAOHANZIBUMEN'/>"/><!-- 包含子部门  -->
	            			<spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/> 
					</td>
				</tr>
				<tr id="tr2" name="tr2" style="display: none;">
					<td class="td_title">
						<!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					</td>
					<td class="td_type">
						<%--
						<input id="SUPERVISOR_ID" name="SUPERVISOR_ID" type="text"
							onclick="searchEmp();" />
						<div id="name" name="name" />
							<input id="jsonData" name="jsonData" type="hidden" value="" />
						</div>
						 --%>
					      			<input id="PERSON_ID" name="PERSON_ID" type="hidden" value=""/>
									<input id="personId" name="dwz.person.personId" value="" readOnly type="hidden" lookupGroup="person"/>
						 			<input id="empId" name="dwz.person.empId" type="text" readOnly class="required"  readOnly lookupGroup="person"/>
					      		
					      			<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=ar&pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					      		
					</td>
				</tr>
				<tr id="tr3" name="tr3" style="display: none;">
					<td class="td_title">
						<!-- 考勤员 --><spring:message code="ar.viewardetailcaculate.title.supervisior"/>
					</td>
					<td class="td_type">
						<select name="arSupervisorId" id="arSupervisorId"
							style="width: 180px; position: static; visibility: inherit;">
							<c:forEach items="${supervisorlist}" var="supervisorlist">
								<c:choose>
									<c:when test="${supervisorId==supervisorlist.SUPERVISOR_ID}">
										<option value="${supervisorlist.SUPERVISOR_ID}"
											selected="selected">
											${supervisorlist.EMPID}- ${supervisorlist.LOCAL_NAME}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${supervisorlist.SUPERVISOR_ID}">
											${supervisorlist.EMPID }-
											${supervisorlist.LOCAL_NAME}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>

			</table>
			<div id="ardetail_calculateResult"></div>
		</div>
	</div>
</form>
