<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#viewHistoryOrgPanel_currentIndex1").val('${currentIndex}');
});
</script>
<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<!-- ztree -->
<script src="/resources/js/ztree/jquery.ztree.all-3.1.js" type="text/javascript"></script>
<script type="text/javascript">
function changeURL_MCS_hr3205(obj) {
	var name = "&";
	var year = $("#seach_FROM_DATE1").attr("value");
	name += "seach_FROM_DATE=" + year;
	var year2 = $("#seach_TO_DATE1").attr("value");
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
	
	if(index == '2'){
		var grade = $(obj).attr("grade");
		name += "&GRADE=" + grade;
	}else if(index == '0'){
		var empType = $(obj).attr("empType");
		name += "&EMPTYPE=" + empType;
	}else if(index == '1'){
		var SOCIAL_DIFFERENTIATION = $(obj).attr("social_differentiation");
		name += "&SOCIAL_DIFFERENTIATION=" + SOCIAL_DIFFERENTIATION;
	}
	var href1 = '/hrm/approve/monthPersonCountInfoSonList1?currentIndex=' + 1 + name;
	obj.href = href1;
	obj.click;

}
// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree10').treeTable( {
			expandLevel : 2
		});
		$('#demoTree11').treeTable( {
			expandLevel : 2
		});
		$('#demoTree12').treeTable( {
			expandLevel : 2
		});
		$('#demoTree13').treeTable( {
			expandLevel : 2
		});
		$('#demoTree14').treeTable( {
			expandLevel : 2
		});

	});

/////strat
function exportURL_hr3206() {
	var thead = "";
	var thead_value = [];//定义一个数组    
	var thead_value2 = [];//定义一个数组 放表2    
	var m = new Map();
	var currentIndex = $("#viewHistoryOrgPanel_currentIndex1").attr("value");
	$('#hr3206_tr' + currentIndex + ' td').each(function() {//每次只走一列
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
	$('#hr3206_tr2' + currentIndex + ' td').each(function() {//每次只走一列
				//表头拼接在一起
				//不做处理的
				thead_value2.push($(this).text().trim());
				//处理跨列
			});
	$("#hr3206_text").attr("value", thead_value + "-" + thead_value2); //
	var zero = 0;
	var number = '';

	$('#demoTree1' + currentIndex + ' tr').each(function() {//每次只走一行
				thead_value = [];
				$('#demoTree1' + currentIndex + ' tr:eq(' + zero + ') td')
						.each(function() {//此行的列
									number = $(this).text().trim();

									thead_value.push(number == '' ? "is_null"
											: number);
									//			
									//alert(number);
									number = '';
								});
				$("#hr3206_text").attr("value",
						$("#hr3206_text").attr("value") + '-' + thead_value);
				zero++;
			});

	$("#hr3206_form").submit();

}

/////end
</script>
</head>
<c:if test="${currentIndex eq '0'}">
	<div class="pageContent">
		<table class="user_table" width="100%" id="demoTree10">
			<tr id="hr3206_tr0">
				<td width="20%" class="td_title" style="text-align:left;">
					<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 -->
				</td>
					<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
						<td width="8%" class="td_title" style="text-align:left;">
							${item.CODENAME }
						</td>
					</c:forEach>
			</tr>
			
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="20%" class="td_type">
									<span controller="true">${item.ORG_NAME_LOCAL}</span>
								</td>
									<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
										<td width="8%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" empType="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
							</tr>
						</c:forEach>
		
		</table>
	</div>
</c:if>
<c:if test="${currentIndex eq '1'}">
	<div class="pageContent">
		<table class="user_table" width="100%">
			<tr id="hr3206_tr1">
					<td width="30%" class="td_title">
						<!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
					</td>
					<td width="10%" class="td_title">
						 <!--合计  --> <spring:message code="hrm.approve.AMOUNT" />
					</td>
					<c:forEach items="${positionCodeList}" var="item" varStatus="i">
						<td width="20%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan="5">
					<table id="demoTree11" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="30%" class="td_type">
										<span controller="true">${item.ORG_NAME_LOCAL}</span>
									</td>
									<td width="10%" class="td_type">
										<c:forEach items="${positionCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="social_differentiation" value="${social_differentiation},${item1.CODE_NO}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.AMOUNT' />"
											mask="true" social_differentiation="${social_differentiation}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="social_differentiation" value="${''}" />
									</td>
									<c:forEach items="${positionCodeList}" var="item1" varStatus="i">
										<td width="20%" class="td_type">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" social_differentiation="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
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
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
<c:if test="${currentIndex eq '2'}">
		<div class="pageContent">
			<table class="user_table" width="2000px" layoutH="200">
				<tr id="hr3206_tr2">
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
				<tr id="hr3206_tr22">
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
						<table id="demoTree12" width="2000px">
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="16%" class="td_type">
										<span controller="true">${item.ORG_NAME_LOCAL}</span>
									</td>
									<td width="6%">
										<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.AMOUNT' />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
										<td width="6%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
							
									<td width="6%">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.AMOUNT' />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="6%">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
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
<c:if test="${currentIndex eq '2'}">
		<div class="pageContent">
			<table class="user_table" width="2000px" layoutH="200">
				<tr id="hr3206_tr2">
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
				<tr id="hr3206_tr22">
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
						<table id="demoTree12" width="2000px">
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="18%" class="td_type">
										<span controller="true">${item.ORG_NAME_LOCAL}</span>
									</td>
									<td width="6%">
										<c:forEach items="${postGradeYBZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.AMOUNT' />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeYBZCodeList}" var="item1" varStatus="i">
										<td width="7%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
									<td width="6%">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.AMOUNT' />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="7%">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS_hr3205(this);' target="dialog">
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

<c:if test="${currentIndex eq '11'}">
	<div class="pageContent">

		<table class="user_table" width="100%">
			<tr id="hr3206_tr21">
				<td width="12%" class="td_title" rowspan="2">
					<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 -->
				</td>
				<td width="4%" class="td_title" rowspan="2">
					<spring:message code="hrm.approve.TOTAL" /><!-- 总合计 -->
				</td>
				<td width="48%" class="td_title" style="text-align: center;"
					colspan="8">
					<spring:message code="hrm.approve.MENAGERIAL_POSITION" /><!-- 管理职 -->
				</td>
				<td width="30%" style="text-align: center;" class="td_title"
					colspan="5">
					<spring:message code="hrm.approve.PRODUCTION_JOB" /><!-- 生产职 -->
				</td>
				<td width="6%" style="text-align: center;" class="td_title">
					<spring:message code="hrm.approve.OTHER" /><!-- 其他 -->
				</td>

			</tr>
			<tr id="hr3206_tr21">
				<td width="6%" class="td_title">
					<spring:message code="hrm.approve.SUBTOTAL" /><!-- 小计 -->
				</td>
				<td width="6%" class="td_title">
					G7
				</td>
				<td width="6%" class="td_title">
					G6
				</td>
				<td width="6%" class="td_title">
					G5
				</td>
				<td width="6%" class="td_title">
					G4
				</td>
				<td width="6%" class="td_title">
					G3
				</td>
				<td width="6%" class="td_title">
					G2
				</td>
				<td width="6%" class="td_title">
					G1
				</td>
				<td width="6%" class="td_title">
					<spring:message code="hrm.approve.SUBTOTAL" /><!-- 小计 -->
				</td>
				<td width="6%" class="td_title">
					p4
				</td>
				<td width="6%" class="td_title">
					p3
				</td>
				<td width="6%" class="td_title">
					p2
				</td>
				<td width="6%" class="td_title">
					p1
				</td>
				<td width="6%" class="td_title">

				</td>

			</tr>
			<tr>
				<td colspan="16">

					<table id="demoTree111" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="12%" class="td_type">
									<span controller="true">${item.ORG_NAME_LOCAL}</span>
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH ne 'sss' }">
											<c:set var="allCount" value="${it.COUNTNUM+allCount}" />

										</c:if>

									</c:forEach>
									${allCount}
									<c:set var="allCount" value="${0}" />
								</td>
								<td width="6%" class="td_title">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if
											test="${it.BIRTH eq 'G1' or it.BIRTH eq 'G2'  or it.BIRTH eq 'G3'  or it.BIRTH eq 'G4'  or it.BIRTH eq 'G5'  or it.BIRTH eq 'G6'  or it.BIRTH eq 'G7' }">
											<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
										</c:if>
									</c:forEach>
									${Count1}
									<c:set var="Count1" value="${0}" />
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G7'}">
											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G6'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G5'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G4'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G3'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G2'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'G1'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_title">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if
											test="${it.BIRTH eq 'P1' or it.BIRTH eq 'P2'  or it.BIRTH eq 'P3'  or it.BIRTH eq 'P4' }">
											<c:set var="Count2" value="${it.COUNTNUM+Count2}" />
										</c:if>
									</c:forEach>
									${Count2}
									<c:set var="Count2" value="${0}" />
								</td>

								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'P4'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'P3'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'P2'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq 'P1'}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>

								<td width="6%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq null}">

											<a style="cursor: pointer;" width="800" height="400"
												mask="true" name="${it.PERSON_IDS1},${it.PERSON_IDS2}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>

									</c:forEach>
								</td>
							</tr>
						</c:forEach>
					</table>

				</td>

			</tr>

		</table>
	</div>
</c:if>


