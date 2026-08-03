<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#viewHistoryOrgPanel_currentIndex2").val('${currentIndex}');
});
</script>
<link href=”/resources/css/ztree/zTreeStyle/zTreeStyle.css”
	rel=”stylesheet” type=”text/css” />
<!-- ztree -->
<script src="/resources/js/ztree/jquery.ztree.all-3.1.js"
	type="text/javascript">
</script>
<script type="text/javascript">
function changeURL_MCS(obj) {

	var name = "&";

	var year = $("#seach_FROM_DATE2",navTab.getCurrentPanel()).attr("value");
	name += "seach_FROM_DATE=" + year;
	var year2 = $("#seach_TO_DATE2",navTab.getCurrentPanel()).attr("value");
	name += "&seach_TO_DATE=" + year2;

	var empType="";
	$("input[name='EMP_TYPE_CODE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = empType + "'empty'";
	name += "&EMP_TYPE_CODE=" + empType;
	var index = $(obj).attr("index");
	var dept = $(obj).attr("deptno");
	name += "&INDEX=" + index;
	name += "&seach_DEPTNO=" + dept;
	if(index == '0'){
		var grade = $(obj).attr("grade");
		name += "&GRADE=" + grade;
	}else if(index == '2'){
		var resignResonList = $(obj).attr("resignResonList");
		name += "&RESIGNRESON=" + resignResonList;
	}else if(index == '4'){
		var empType = $(obj).attr("empType");
		name += "&empTypeForTable=" + empType;
	}
	name += "&strFlag=delete";
	var href1 = '/hrm/approve/monthPersonCountInfoSonList?currentIndex=' + 1 + name;
	obj.href = href1;
	obj.click;
	
	//var PERSON_ID = obj.name;
	//var href1 = '/pa/workManagement/monthPersonCountInfoSonList?currentIndex=' + 1 + '&PERSON_ID_ID=' + PERSON_ID;
	//obj.href = href1;
	//obj.click;

}
// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree20').treeTable( {
			expandLevel : 2
		});
		$('#demoTree21').treeTable( {
			expandLevel : 2
		});
		$('#demoTree22').treeTable( {
			expandLevel : 2
		});
		$('#demoTree23').treeTable( {
			expandLevel : 2
		});
		$('#demoTree24').treeTable( {
			expandLevel : 2
		});

	});
/////strat
function exportURL_hr3205() {
	var thead = "";
	var thead_value = [];//定义一个数组    
	var thead_value2 = [];//定义一个数组 放表2    
	var m = new Map();
	var currentIndex = $("#viewHistoryOrgPanel_currentIndex2").attr("value");
	$('#hr3205_tr' + currentIndex + ' td').each(function() {//每次只走一列
				//表头拼接在一起
				//不做处理的
				thead_value.push($(this).text().trim());
				//处理跨列
				if ($(this).attr("colspan") > '1') {
					for ( var i = 1; i < $(this).attr("colspan"); i++) {
						thead_value.push("is_null");
					}
				}
				;
			});
	//因页面特殊所以如此处理
	thead_value2.push("is_null");
	thead_value2.push("is_null");
	$('#hr3205_tr2' + currentIndex + ' td').each(function() {//每次只走一列
				//表头拼接在一起
				//不做处理的
				thead_value2.push($(this).text().trim());
				//处理跨列
			});
	$("#hr3205_text").attr("value", thead_value + "-" + thead_value2); //
	var zero = 0;
	var number = '';

	$('#demoTree2' + currentIndex + ' tr').each(function() {//每次只走一行
				thead_value = [];
				$('#demoTree2' + currentIndex + ' tr:eq(' + zero + ') td')
						.each(function() {//此行的列
									number = $(this).text().trim();

									thead_value.push(number == '' ? "is_null"
											: number);
									//			
									//alert(number);
									number = '';
								});
				$("#hr3205_text").attr("value",
						$("#hr3205_text").attr("value") + '-' + thead_value);
				zero++;
			});

	$("#hr3205_form").submit();

}

/////end
</script>
</head>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
<c:if test="${currentIndex eq '0'}">
		<div class="pageContent">
			<table class="user_table" width="2000px">
				<tr id="hr3205_tr0">
					<td width="16%" class="td_title" rowspan="2">
						<!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
					</td>
					<td width="60%" class="td_title" colspan="10" style="text-align:center">
						<!--  管理职 --> <spring:message code="hrm.approve.MENAGERIAL_POSITION" />
					</td>
					<td width="24%" class="td_title" colspan="4" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr id="hr3205_tr20">
					<td width="6%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeBGZCodeList}" var="item" varStatus="i">
						<td width="6%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
					<td width="6%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeSCZCodeList}" var="item" varStatus="i">
						<td width="6%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan="15">
						<table id="demoTree20" width="100%">
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="16%" class="td_type">
										<span controller="true">${item.ORG_NAME_LOCAL}</span>
									</td>
									
									<td width="6%" class="td_title">
										<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
										<td width="6%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														empType="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
									
									<td width="6%" class="td_title">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="6%">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														empType="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
 </c:if>
<c:if test="${LoginUser.cpnyId eq 'HAE'}">
<c:if test="${currentIndex eq '0'}">
		<div class="pageContent">
			<table class="user_table" width="2000px">
				<tr id="hr3205_tr0">
					<td width="18%" class="td_title" rowspan="2">
						<!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  一般职 --> <spring:message code="hrm.recruitManage.YIBANZHI.Z" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr id="hr3205_tr20">
					<td width="6%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeYBZCodeList}" var="item" varStatus="i">
						<td width="7%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
					<td width="6%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeSCZCodeList}" var="item" varStatus="i">
						<td width="7%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan="13">
						<table id="demoTree20" width="100%">
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="18%" class="td_type">
										<span controller="true">${item.ORG_NAME_LOCAL}</span>
									</td>
									
									<td width="6%" class="td_title">
										<c:forEach items="${postGradeYBZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${postGradeYBZCodeList}" var="item1" varStatus="i">
										<td width="7%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														empType="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
									
									<td width="6%" class="td_title">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="7%">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														empType="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
 </c:if>

<c:if test="${currentIndex eq '2'}">
	<div class="pageContent">
		<table class="user_table" width="1800px">
			<tr id='hr3205_tr2'>
				<td width="21%" class="td_title">
					  <!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
				</td>
				<td width="7%" class="td_title">
				<!-- 总合计 -->  <spring:message code="hrm.approve.TOTAL" />
				</td>
				<c:forEach items="${resignResonList}" var="item" varStatus="i">
					<td width="9%" class="td_title">
						${item.CODENAME }
					</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="10">
					<table id="demoTree22" width="1800px">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="21%" class="td_type">
									<span controller="true">${item.ORG_NAME_LOCAL}</span>
								</td>
								<td width="7%" class="td_title">
										<c:forEach items="${resignResonList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${resignResonList}" var="item1" varStatus="i">
										<td width="9%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														resignResonList="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:if>
<c:if test="${currentIndex eq '4'}">
	<div class="pageContent">
		<table class="user_table" width="1800px">
			<tr height="30px"  id='hr3205_tr4'>
				<td width="15%" class="td_title">
					<!--部门  --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
				</td>
				<td width="10%" class="td_title">
				<!-- 总合计 -->  <spring:message code="hrm.approve.TOTAL" />
				</td>
				<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
						<td width="15%" class="td_title">
							${item.CODENAME }
						</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="7">
					<table id="demoTree24" width="1800px">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="15%" class="td_type">
									<span controller="true">${item.ORG_NAME_LOCAL}</span>
								</td>
								<td width="10%" class="td_title">
										<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400"
											mask="true" name="${PERSON_IDSS}" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
										<c:set var="Grade" value="${''}" />
									</td>
									
									<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
										<td width="15%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400"
														mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}" 
														empType="${item1.CODE_NO}" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</c:if>