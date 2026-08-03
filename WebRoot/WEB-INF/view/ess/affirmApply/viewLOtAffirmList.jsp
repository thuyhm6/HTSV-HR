<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script type="text/javascript" src="/resources/js/togglebar.js">
</script>
<script type="text/javascript">

$(document).ready(function() {
	$(".orderList", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		// "bAutoWidth":false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 200,
		"orderClasses" : false,
		"oLanguage" : {
			"sProcessing" : "正在加载中......",
			"sZeroRecords" : "查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
			"sSearch" : "快速筛选"
		}
	//多语言配置
			});
});
function validateAffirmLOtApplyCallbackEss(form, callback, flag) {
	var $form = null;
	if ($('#' + form).length > 0)
		$form = $('#' + form);
	else
		$form = $(form);

	if (!$form.valid()) {
		return false;
	}

	var checked = false;
	var ids = document.getElementsByName("c2");
	for ( var i = 0; i < ids.length; i++) {
		if (ids[i].checked) {
			checked = true;
		}
	}
	if (!checked) {
		alertMsg
				.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>');
		return false;
	}
    
    if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    	  var CONFIRM_FLAG  = document.getElementById("CONFIRM_FLAG"+j).value;
		    	  if(CONFIRM_FLAG=="Y"){
					alertMsg.error('加班审批已锁定！');
					return false;
				  }
				}
			}		  
	}
    
	$form.attr("action",
			"/ess/affirmApply/approveOvertimeApplyInBatch?AFFIRM_FLAG=" + flag
					+ "&AFFIRM_TYPE=OT_AFFIRM");

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : function(data) { //请求成功后处理函数。
			if (data.statusCode == "200") {
				navTabSearch(document.viewLOtAffirmList);
				alertMsg.correct(data.message);
			} else {
				if (data.result == "2") {
					alertMsg.info(data.message);
				} else {
					alertMsg.error(data.message);
				}
			}
		},
		error : DWZ.ajaxError
	});
	return false;
}

//文本fill
function textMuliOt(name, value) {
	var ids = document.getElementsByName("c2");

	if (ids.length > 0) {
		for ( var i = 0; i < ids.length; i++) {
			var j = ids[i].value;
			if (ids[i].checked == true) {
				document.getElementById(name + j).value = value;
			}
		}
	}
}

function fillItemOt() {
	var checked = false;
	var ids = document.getElementsByName("c2");
	for ( var i = 0; i < ids.length; i++) {
		if (ids[i].checked) {
			checked = true;
		}
	}
	if (!checked) {
		alertMsg.error('请选择反应记录');
		return false;
	}
    
    var fillAffirmFlagot = $("#viewLOtAffirmList select[id='FILLAFFIRMFLAG_OT']").val();
	var objot = document.getElementById("FILLAFFIRMFLAG_OT");
	var txtot = objot.options[objot.selectedIndex].text;
 if(fillAffirmFlagot != "" && fillAffirmFlagot != null){
     textMuliOt("valibl_value_AFFIRM_NOS", fillAffirmFlagot);
	 textMuliOt("valibl_input_AFFIRM_NOS", txtot);
  }else{
     return;
  }
}
</script>

<div id="viewLOtAffirmList" class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/affirmApply/viewLOtAffirmList?firstFlag=N" method="post"
		rel="pagerForm" name="viewLOtAffirmList" id="viewLOtAffirmList">
		<div class="searchBar">
			<table class="searchContent">

				<tr>
				<td>
					<!-- 姓名： -->
					<spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" text="姓名" />
				</td>
				<td colspan="4">

					<input id="personId" name="dwz.person.personId" type="hidden"
						lookupGroup="person" />
					<input name="dwz.person.empName" type="text" lookupGroup="person"
						style="float: left;" value="${empName}" />
					<a class="btnLook"
						href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
						lookupGroup="person"> <spring:message
							code="pa.insurance.title.lookUpAndBack" />
					</a>
					<input name="dwz.person.empInfo" type="text" readonly
						lookupGroup="person" size="30" value="${empInfo}" />
				</td>
				<td>
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="seach_FROM_TIME" id="seach_FROM_TIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${FROM_TIME}" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="seach_TO_TIME" id="seach_TO_TIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${TO_TIME}" />
					</td>
				</tr>
				<tr>
				<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
		
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}"limit="manager" 
							id="viewLOtAffirmList_seachDept" selected="${DEPTNO}" />
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}"limit="manager" 
							id="viewLOtAffirmList_seachDept" selected="${DEPTNO}" />
					</td>
					<td colspan="3">
				         <input type="checkbox" name="seach_partYn" value="all" /> 部门长(Y/N)
				    </td>

					<td>
						工作形态：
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value="">
								请选择
							</option>
							<c:forEach items="${workTimeList}" var="item">
								<option value="${item.SHIFT_NO}"
									<c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>>
									${item.SHIFT_SHORTNAME}
							</c:forEach>
						</select>
					</td>
					<td>
						<!-- 决裁状态 -->
						审批状态
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG"
							combinParentNo="14014304" selected="${AFFIRM_FLAG}"
							exclude="14014306" cnpyID="${LoginUser.cpnyId}" limit="all" />
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
									<!-- 检索 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div id="viewLOtAffirmList" class="pageHeader">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td align="right">
					审批状态&nbsp;&nbsp;&nbsp;
					<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG_OT"
						combinParentNo="14014304" exclude="14014306"
						selected="${AFFIRM_FLAG}" cnpyID="${LoginUser.cpnyId}" limit="all" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul class="toolBar">
				<li>
					<a class="buttonActive" onclick="fillItemOt();"><span>全部反应</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="pageContent">
	<div class="formBar">
		<div
			style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">
			Total:${fn:length(otApplyList)}
		</div>
		<ul>
			<li>
				<div class="subBar">
					<div class="buttonActive">
						<a class="update"
							onclick="validateAffirmLOtApplyCallbackEss('updateLOtApplyAffirmForm',DWZ.ajaxDone,'1')"
							href="#"> <span>保存</span>
						</a>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<form name="updateLOtApplyAffirmForm" id="updateLOtApplyAffirmForm"
		method="post" action="/ess/affirmApply/approveLOvertimeApplyInBatch"
		onsubmit="return validateAffirmLOtApplyCallback(this, navTabAjaxDone);">
		<table class="orderList" width="100%">
			<thead>
				<tr>
					<th rowspan="2">
						<!--NO-->
						NO
					</th>
					<th rowspan="2">
						<input type="checkbox" class="checkboxCtrl" group="c2" />
					</th>
					<th rowspan="2">
						<!--状态-->
						状态
					</th>
					<th rowspan="2" width="4%">
						<!--申请人-->
						姓名
					</th>
					<th rowspan="2">
						<!--社号-->
						社号
					</th>
					<th rowspan="2" width="7%">
						<!--日期-->
						日期
					</th>
					<th rowspan="2">
						<!--星期-->
						星期
					</th>
					<th rowspan="2">
						<!--类型-->
						类型
					</th>
					<th rowspan="2">
						<!--考勤-->
						考勤
					</th>
					<th colspan="9">
						<!--申请-->
						申请
					</th>
					<th colspan="2">
						<!--原因-->
						原因
					</th>
					<th rowspan="2">
						<!--审批状态-->
						审批状态
					</th>
					<th colspan="6">
						<!--加班累计-->
						加班累计
					</th>
				</tr>
				<tr>
					<th>
						<!--班组-->
						班组
					</th>
					<th>
						<!--工作形态-->
						工作形态
					</th>
					<th>
						<!--出门-->
						进门
					</th>
					<th>
						<!--进门-->
						出门
					</th>
					<th>
						<!--开始-->
						开始
					</th>
					<th>
						<!--结束-->
						结束
					</th>
					<th>
						<!-- 加班时间-->
						加班时间
					</th>
					<th>
						<!-- 调休-->
						是否调休
					</th>
					<th>
						<!-- 中夜班津贴  -->
						中夜班津贴
					</th>
					<th>
						<!--原因-->
						原因
					</th>
					<th>
						<!--其他原因-->
						其他原因
					</th>
					<th>
						<!--加班合计-->
						加班合计
					</th>
					<th>
						<!--平时-->
						平时
					</th>
					<th>
						<!--周末-->
						周末
					</th>
					<th>
						<!--法定节假日-->
						法定节假日
					</th>
					<th>
						<!--综合加班-->
						综合加班
					</th>
					<th>
						<!--月平均-->
						月平均
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otApplyList}" var="otApply" varStatus="i">
					<tr target="sid" rel="${admin.personId}">
					
					 
						<td style="text-align: center">
							${i.count}
						</td>
						<td style="text-align: center">
							<input type="checkbox" id="c2${otApply.APPLY_NO}" name="c2"
								value="${otApply.APPLY_NO}" />
						</td>
						
						  <input  type="hidden"  id="DATE_TYPE${otApply.APPLY_NO}"  name="DATE_TYPE${otApply.APPLY_NO}"  value="${otApply.DATE_TYPE}"/>
						  <input type="hidden" id="ITEM_NO${otApply.APPLY_NO}"  name="ITEM_NO${otApply.APPLY_NO}"  value="${otApply.ITEM_NO}"/>
						  <input type="hidden" id="APPLY_TYPE_CODE${otApply.APPLY_NO}"  name="APPLY_TYPE_CODE${otApply.APPLY_NO}"  value="${otApply.APPLY_TYPE_CODE}"/>
						<td style="text-align: center">
							 <input type="hidden" id="CONFIRM_FLAG${otApply.APPLY_NO}" name="CONFIRM_FLAG${otApply.APPLY_NO}" value="${otApply.CONFIRM_FLAG}" />
					          ${otApply.CONFIRM_FLAG}
						</td>
						<td style="text-align: center">
							${otApply.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${otApply.EMPID}
						</td>
						<td style="text-align: center">
							${otApply.AR_DATE_STR}
						</td>
						<td style="text-align: center">
							${otApply.WEEKDAY}
						</td>
						<td style="text-align: center">
							${otApply.TYPENAME}
						</td>
						<td style="text-align: center">
							<c:if test="${otApply.KAOQINITEM != '休息' && otApply.KAOQINITEM != '正常出勤' }">${otApply.KAOQINITEM}</c:if>
						</td>
						<td width="40px" style="text-align: center">
							${otApply.GROUPNAME}
						</td>
						<td style="text-align: center">
							${otApply.FROM_TIME_FIRST}-${otApply.TO_TIME_FIRST}
						</td>
						<td style="text-align: center">
							${otApply.INDOOR_DATE}
						</td>
						<td style="text-align: center">
							${otApply.OUTDOOR_DATE}
						</td>
						<td style="text-align: center">
							${otApply.FROM_TIME}
						</td>
						<td style="text-align: center">
							${otApply.TO_TIME}
						</td>
						<td style="text-align: center">
							${otApply.APPLY_LENGTH}
							 <c:if test="${otApply.ITEM_NO == '141452'}">
					               <font color="red"> (√)</font>
					         </c:if>
						</td>
						<td  style="text-align: center">
					       <c:if test="${otApply.ITEM_NO eq '141452'}">
					           <c:if test="${otApply.DATE_TYPE eq '1440'}">
					              <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}" checked="checked"  disabled="disabled" value="1" />
					           </c:if>
					           <c:if test="${otApply.DATE_TYPE ne '1440'}">
					              <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}" checked="checked"  value="1" />
					           </c:if>
					       </c:if>
					       <c:if test="${otApply.ITEM_NO ne '141452'}">
					            <c:if test="${otApply.DATE_TYPE eq '1440'}">
					                 <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}"  disabled="disabled"  value="1" />
					            </c:if>
					            <c:if test="${otApply.DATE_TYPE ne '1440'}">
					                 <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}"   value="1" />
					            </c:if>
					        </c:if>
				         </td>
						<td style="text-align: center">
							${otApply.ALLOWANCE}
						</td>
						<td style="text-align: center">
							${otApply.LEAVEREASON}
						</td>
						<td style="text-align: center">
							${otApply.REASON_OTHER}
						</td>

						<td>
							<input id="valibl_input_AFFIRM_NOS${otApply.APPLY_NO}" type="text"
								size="6" value="${otApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}').css('display', 'block');"
								readonly="readonly" />
							<input id="valibl_value_AFFIRM_NOS${otApply.APPLY_NO}"
								name="AFFIRM_FLAG${otApply.APPLY_NO}" type="hidden"
								value="${otApply.AFFIRM_FLAG}" />
							<div id="valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NOS${otApply.APPLY_NO}')"
								onclick="$('#valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}').css('display', 'none');"
								class="deptContent"
								style="display: none; height: 200px; width: 120px; margin-left: -20px;">
								<div class="ztree_dept"
									style="height: 200px; width: 120px; overflow: auto; overflow-x: hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>
													审批状态
												</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 300px"
															onclick="$('#valibl_input_AFFIRM_NOS${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOS${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}').css('display', 'none');">
															<span>${item.CODE_NAME}</span>
														</li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px"
															onclick="$('#valibl_input_AFFIRM_NOS${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOS${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}').css('display', 'none');">
															<span>${item.CODE_NAME}</span>
														</li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#"
											onclick="$('#valibl_pop_AFFIRM_NOS${otApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span> </a>
									</div>
								</div>
							</div>
						</td>
						<td style="text-align: center">
							${otApply.OT_TOTAIL}
						</td>
						<td style="text-align: center">
							${otApply.WEEKDAY_OT_TOTAIL}
						</td>
						<td style="text-align: center">
							${otApply.WEEKEND_OT_TOTAIL}
						</td>
						<td style="text-align: center">
							${otApply.HOILDAY_OT_TOTAIL}
						</td>
						<td style="text-align: center">
							${otApply.COMPRE_OT_TOTAIL}
						</td>
						<td style="text-align: center">
							${otApply.OT_TOAVG}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<div style="visibility: hidden;">
		<c:set value="/ess/affirmApply/viewLOtAffirmList" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</div>
