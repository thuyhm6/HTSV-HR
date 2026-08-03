<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#viewHistoryOrgPanel_currentIndex").val('${currentIndex}');
});
</script>
<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css" rel=”stylesheet” type=”text/css” />
<!-- ztree -->
<script src="/resources/js/ztree/jquery.ztree.all-3.1.js" type="text/javascript">
</script>

<script type="text/javascript">
function changeURL_MCS(obj) {
	var name = "&";
	var year = $("#seach_YEAR",navTab.getCurrentPanel()).attr("value");
	var yearFlag = '';
	if (year < '2014/06') {
		yearFlag = 'flag';
	}
	name += "seach_YEAR=" + year;
	name += "&yearFlag=" + yearFlag;
	var empType="";
	$("input[name='EMP_TYPE_CODE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = empType + "'empty'";
	name += "&seach_EMP_TYPE_CODE=" + empType;
	var scount = $("#STATUS_CODE_COUNT").val();
	for ( var i = 1; i < scount; i++) {
		if ($("#STATUS_CODE" + i).prop("checked")) {
			var STATUS_CODE = $("#STATUS_CODE" + i).attr("value");
			name += "&seach_STATUS_CODE=" + STATUS_CODE;
		}
	}
	var index = $(obj).attr("index");
	var dept = $(obj).attr("deptno");
	name += "&INDEX=" + index;
	name += "&seach_DEPTNO=" + dept;
	if(index == '3'){
		var maxAge = $(obj).attr("maxAge");
		var minAge = $(obj).attr("minAge");
		name += "&MINAGE=" + minAge;
		name += "&MAXAGE=" + maxAge;
	}else if(index == '2'){
		var edu = $(obj).attr("edu");
		var sex = $(obj).attr("sex");
		name += "&EDU=" + edu;
		name += "&SEX=" + sex;
		name += "&strFlag=add";
	}else if(index == '0'){
		var grade = $(obj).attr("grade");
		name += "&GRADE=" + grade;
		name += "&strFlag=add";
	}else if(index == '4'){
		var empType = $(obj).attr("empType");
		name += "&empTypeForTable=" + empType;
		name += "&strFlag=add";
	}
	var href1 = '/hrm/approve/monthPersonCountInfoSonList?currentIndex=' + 1 + name;
	obj.href = href1;
	obj.click;

}
// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree0').treeTable( {
			expandLevel : 2
		});
		$('#demoTree1').treeTable( {
			expandLevel : 2
		});
		$('#demoTree2').treeTable( {
			expandLevel : 2
		});
		$('#demoTree3').treeTable( {
			expandLevel : 2
		});
		$('#demoTree4').treeTable( {
			expandLevel : 2
		});
		$('#demoTree5').treeTable( {
			expandLevel : 2
		});

	});
</script>
</head>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
<c:if test="${currentIndex eq '0'}">
<c:if test="${LoginUser.language eq 'vi'}">
		<div class="pageContent">
			<table class="user_table" width="2000px" id="demoTree0" layoutH="200">
				<tr id="ess3501_tr0">
					<td width="120px" class="td_title" rowspan="2">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td width="40px" class="td_title" colspan="10" style="text-align:center">
						<!--  管理职 --> <spring:message code="hrm.approve.MENAGERIAL_POSITION" />
					</td>
					<td width="40px" class="td_title" colspan="4" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr>
					<td width="40px" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeBGZCodeList}" var="item" varStatus="i">
						<td width="50px" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
					<td width="40px" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeSCZCodeList}" var="item" varStatus="i">
						<td width="50px" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
				</tr>
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="120px" class="td_type">
										<span controller="true">${item.DEPTNAME}</span>
									</td>
									<td width="40px" class="td_title">
										<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
										<td width="50px">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
									
									<td width="40px" class="td_title">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="50px">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
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
<c:if test="${LoginUser.language ne 'vi'}">
		<div class="pageContent">
			<table class="user_table" width="100%" id="demoTree0" layoutH="200">
				<tr id="ess3501_tr0">
					<td width="30%" class="td_title" rowspan="2">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td width="50%" class="td_title" colspan="10" style="text-align:center">
						<!--  管理职 --> <spring:message code="hrm.approve.MENAGERIAL_POSITION" />
					</td>
					<td width="20%" class="td_title" colspan="4" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr>
					<td width="5%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeBGZCodeList}" var="item" varStatus="i">
						<td width="5%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
					<td width="5%" class="td_title">
						<!--小计  --> <spring:message code="hrm.approve.SUBTOTAL" />
					</td>
					<c:forEach items="${postGradeSCZCodeList}" var="item" varStatus="i">
						<td width="5%" class="td_title">
							${item.CODENAME }
						</td>
					</c:forEach>
				</tr>
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="30%" class="td_type">
										<span controller="true">${item.DEPTNAME}</span>
									</td>
									<td width="5%" class="td_title">
										<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeBGZCodeList}" var="item1" varStatus="i">
										<td width="5%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
														<span> ${it.COUNTNUM}</span> </a>
												</c:if>
											</c:forEach>
										</td>
									</c:forEach>
									
									<td width="5%" class="td_title">
										<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
													<c:set var="Grade" value="${item1.CODE_NO},${Grade}" />
												</c:if>
											</c:forEach>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${Count1}</span> </a>
										<c:set var="Count1" value="${0}" />
										<c:set var="Grade" value="${''}" />
									</td>
									<c:forEach items="${postGradeSCZCodeList}" var="item1" varStatus="i">
										<td width="5%">
											<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" grade="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
														onclick='javascript:changeURL_MCS(this);' target="dialog">
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
</c:if>
</c:if>

<c:if test="${LoginUser.cpnyId eq 'HAE'}">
<c:if test="${currentIndex eq '0'}">
<c:if test="${LoginUser.language eq 'vi'}">
		<div class="pageContent">
			<table class="user_table" width="2000px" id="demoTree0" layoutH="200">
				<tr id="ess3501_tr0">
					<td width="18%" class="td_title" rowspan="2">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  一般职 --> <spring:message code="hrm.recruitManage.YIBANZHI.Z" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr>
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
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="18%" class="td_type">
										<span controller="true">${item.DEPTNAME}</span>
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
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
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
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
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
														onclick='javascript:changeURL_MCS(this);' target="dialog">
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
<c:if test="${LoginUser.language ne 'vi'}">
		<div class="pageContent">
			<table class="user_table" width="100%" id="demoTree0" layoutH="200">
				<tr id="ess3501_tr0">
					<td width="18%" class="td_title" rowspan="2">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  一般职 --> <spring:message code="hrm.recruitManage.YIBANZHI.Z" />
					</td>
					<td width="41%" class="td_title" colspan="6" style="text-align:center">
						<!--  生产职 --> <spring:message code="hrm.approve.PRODUCTION_JOB" />
					</td>
				</tr>
				<tr>
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
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="18%" class="td_type">
										<span controller="true">${item.DEPTNAME}</span>
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
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
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
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
											mask="true" grade="${Grade}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
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
														onclick='javascript:changeURL_MCS(this);' target="dialog">
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
</c:if>
</c:if>

<c:if test="${currentIndex eq '2'}">
<c:if test="${LoginUser.language eq 'vi'}">
	<div class="pageContent">
		<table class="user_table" width="2000px" layoutH="200">
			<tr id="ess3501_tr2">
				<td width="16%" class="td_title" rowspan="2">
				 <!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
				</td>
				<td width="72%" style="text-align: center;" class="td_title"
					colspan="9">
				<!-- 学历 --> <spring:message code="hrm.empinfo.DEGREE_CODE" />
				</td>
				<td width="12%" style="text-align: center;" class="td_title"
					colspan="3">
				<!--性别 --> <spring:message code="hrm.empinfo.SEXCODE" />
				</td>
			</tr>
			<tr id="ess3501_tr22">
				<td width="8%" class="td_title">
					<!--小计 --><spring:message code="hrm.approve.SUBTOTAL" /> 
				</td>
				<td width="8%" class="td_title">
					<!-- 高等大学 --> <spring:message code="hrm.approve.GAODENG_DAXUE.Z" />
				</td>
				<td width="8%" class="td_title">
					<!--大学 --> <spring:message code="hrm.approve.DAXUE.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--大专 --> <spring:message code="hrm.approve.SPECIALTY" />
				</td>
				<td width="8%" class="td_title">
				   <!--职专 --> <spring:message code="hrm.approve.ZHIZHUAN.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--中专 --> <spring:message code="hrm.approve.ZHONGZHUAN.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--技校 --> <spring:message code="hrm.approve.JIXIAO.Z" />
				</td>
				<td width="8%" class="td_title">
				<!--高中 --> <spring:message code="hrm.approve.HIGH_SCHOOL" />
				</td>
				<td width="8%" class="td_title">
				<!-- 初中以下 --> <spring:message code="hrm.approve.UNDER_JUNIOR_MIDDLE_SCHOOL" />
				</td>
				<td width="4%" class="td_title">
				<!--小计 --> <spring:message code="hrm.approve.SUBTOTAL" />
				</td>
				<td width="4%" class="td_title">
				<!--男 --> <spring:message code="hrm.approve.MAN"/>
				</td>
				<td width="4%" class="td_title">
				<!--女 --> <spring:message code="hrm.approve.WOMAN" />
				</td>
			</tr>
			<tr>
				<td colspan="13">
					<table id="demoTree2" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="16%" class="td_type">
									<span controller="true">${item.DEPTNAME}</span>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH != '88' }">
											<c:set var="CountA" value="${it.COUNTNUM+CountA}" />
										</c:if>
									</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
										mask="true" edu="10743,10742,10748,10746,10741,14013904,10738,10735,10733,0"  deptno="${item.DEPTNO}" index="${currentIndex}"
										onclick='javascript:changeURL_MCS(this);' target="dialog">
										<span>${CountA}</span> </a>
									<c:set var="CountA" value="${0}" />
									<c:set var="PERSON_IDSS" value="${''}" />
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='GDDX'}"><!-- 高等大学 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.GAODENG_DAXUE.Z' />"
												mask="true" edu="10733" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='DX'}"><!-- 大学 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.DAXUE.Z' />"
												mask="true" edu="10735" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='DZ'}"><!-- 大专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.SPECIALTY' />"
												mask="true"edu="10738" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='ZHIZ'}"><!-- 职专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.ZHIZHUAN.Z' />"
												mask="true"edu="14013904" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='ZHONGZ'}"><!-- 中专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.ZHONGZHUAN.Z' />"
												mask="true" edu="10741" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='JZ'}"><!-- 技校 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.JIXIAO.Z' />"
												mask="true" edu="10746" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='GZ'}"><!-- 高中 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.HIGH_SCHOOL" />"
												mask="true" edu="10748" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='CZ'}"><!-- 初中以下 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.UNDER_JUNIOR_MIDDLE_SCHOOL" />"
												mask="true" edu="10743,10742,0" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH != '88' }">
											<c:set var="CountS" value="${it.COUNTNUM+CountS}" />
										</c:if>
									</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.SUBTOTAL' />"
										mask="true" edu="0" sex="1326,1325" deptno="${item.DEPTNO}" index="${currentIndex}"
										onclick='javascript:changeURL_MCS(this);' target="dialog">
										<span>${CountS}</span> </a>
									<c:set var="CountS" value="${0}" />
									<c:set var="PERSON_IDSS" value="${''}" />
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='M'}">
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.MAN"/>"
												mask="true" edu="0" sex="1326" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='F'}">
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.WOMAN" />"
												mask="true" edu="0" sex="1325" deptno="${item.DEPTNO}" index="${currentIndex}"
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
<c:if test="${LoginUser.language ne 'vi'}">
	<div class="pageContent">
		<table class="user_table" width="100%" layoutH="200">
			<tr id="ess3501_tr2">
				<td width="16%" class="td_title" rowspan="2">
				 <!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
				</td>
				<td width="72%" style="text-align: center;" class="td_title"
					colspan="9">
				<!-- 学历 --> <spring:message code="hrm.empinfo.DEGREE_CODE" />
				</td>
				<td width="12%" style="text-align: center;" class="td_title"
					colspan="3">
				<!--性别 --> <spring:message code="hrm.empinfo.SEXCODE" />
				</td>
			</tr>
			<tr id="ess3501_tr22">
				<td width="8%" class="td_title">
					<!--小计 --><spring:message code="hrm.approve.SUBTOTAL" /> 
				</td>
				<td width="8%" class="td_title">
					<!-- 高等大学 --> <spring:message code="hrm.approve.GAODENG_DAXUE.Z" />
				</td>
				<td width="8%" class="td_title">
					<!--大学 --> <spring:message code="hrm.approve.DAXUE.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--大专 --> <spring:message code="hrm.approve.SPECIALTY" />
				</td>
				<td width="8%" class="td_title">
				   <!--职专 --> <spring:message code="hrm.approve.ZHIZHUAN.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--中专 --> <spring:message code="hrm.approve.ZHONGZHUAN.Z" />
				</td>
				<td width="8%" class="td_title">
				   <!--技校 --> <spring:message code="hrm.approve.JIXIAO.Z" />
				</td>
				<td width="8%" class="td_title">
				<!--高中 --> <spring:message code="hrm.approve.HIGH_SCHOOL" />
				</td>
				<td width="8%" class="td_title">
				<!-- 初中以下 --> <spring:message code="hrm.approve.UNDER_JUNIOR_MIDDLE_SCHOOL" />
				</td>
				<td width="4%" class="td_title">
				<!--小计 --> <spring:message code="hrm.approve.SUBTOTAL" />
				</td>
				<td width="4%" class="td_title">
				<!--男 --> <spring:message code="hrm.approve.MAN"/>
				</td>
				<td width="4%" class="td_title">
				<!--女 --> <spring:message code="hrm.approve.WOMAN" />
				</td>
			</tr>
			<tr>
				<td colspan="13">
					<table id="demoTree2" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="16%" class="td_type">
									<span controller="true">${item.DEPTNAME}</span>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH != '88' }">
											<c:set var="CountA" value="${it.COUNTNUM+CountA}" />
										</c:if>
									</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.SUBTOTAL" />"
										mask="true" edu="10743,10742,10748,10746,10741,14013904,10738,10735,10733,0"  deptno="${item.DEPTNO}" index="${currentIndex}"
										onclick='javascript:changeURL_MCS(this);' target="dialog">
										<span>${CountA}</span> </a>
									<c:set var="CountA" value="${0}" />
									<c:set var="PERSON_IDSS" value="${''}" />
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='GDDX'}"><!-- 高等大学 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.GAODENG_DAXUE.Z' />"
												mask="true" edu="10733" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='DX'}"><!-- 大学 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.DAXUE.Z' />"
												mask="true" edu="10735" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='DZ'}"><!-- 大专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.SPECIALTY' />"
												mask="true"edu="10738" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='ZHIZ'}"><!-- 职专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.ZHIZHUAN.Z' />"
												mask="true"edu="14013904" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='ZHONGZ'}"><!-- 中专 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.ZHONGZHUAN.Z' />"
												mask="true" edu="10741" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='JZ'}"><!-- 技校 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.JIXIAO.Z' />"
												mask="true" edu="10746" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='GZ'}"><!-- 高中 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.HIGH_SCHOOL" />"
												mask="true" edu="10748" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="8%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='CZ'}"><!-- 初中以下 -->
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.UNDER_JUNIOR_MIDDLE_SCHOOL" />"
												mask="true" edu="10743,10742,0" sex="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH != '88' }">
											<c:set var="CountS" value="${it.COUNTNUM+CountS}" />
										</c:if>
									</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='hrm.approve.SUBTOTAL' />"
										mask="true" edu="0" sex="1326,1325" deptno="${item.DEPTNO}" index="${currentIndex}"
										onclick='javascript:changeURL_MCS(this);' target="dialog">
										<span>${CountS}</span> </a>
									<c:set var="CountS" value="${0}" />
									<c:set var="PERSON_IDSS" value="${''}" />
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='M'}">
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.MAN"/>"
												mask="true" edu="0" sex="1326" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> </a>
										</c:if>
									</c:forEach>
								</td>
								<td width="4%" class="td_type">
									<c:forEach items="${deptListCount3[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH=='F'}">
											<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="hrm.approve.WOMAN" />"
												mask="true" edu="0" sex="1325" deptno="${item.DEPTNO}" index="${currentIndex}"
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
</c:if>
<c:if test="${currentIndex eq '3'}">
		<div class="pageContent">
			<table class="user_table" width="100%" layoutH="200">
				<tr id="ess3501_tr3">
					<td width="20%" class="td_title" rowspan="2">
					<!-- 部门 --> <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
					</td>
					<td width="8%" class="td_title" rowspan="2">
					<!-- 总合计  --> <spring:message code="hrm.approve.TOTAL" />
					</td>
					<td width="48%" style="text-align: center;" class="td_title"
						colspan="9">
					<!-- 年龄 --> <spring:message code="hrm.empinfo.AGE" />
					</td>
				</tr>
				<tr id="ess3501_tr23">
					<td width="8%" class="td_title">
						~19
					</td>
					<td width="8%" class="td_title">
						20~24
					</td>
					<td width="8%" class="td_title">
						25~29
					</td>
					<td width="8%" class="td_title">
						30~34
					</td>
					<td width="8%" class="td_title">
						35~39
					</td>
					<td width="8%" class="td_title">
						40~44
					</td>
					<td width="8%" class="td_title">
						45~49
					</td>
					<td width="8%" class="td_title">
						50~
					</td>
				</tr>
				<tr>
					<td colspan="11">
						<table id="demoTree3" width="100%">
							<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
								<tr id="${item.DEPTNO}"
									<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
									<td width="20%" class="td_type">
										<span controller="true">${item.DEPTNAME}</span>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH != '88' }">
												<c:set var="CountA" value="${it.COUNTNUM+CountA}" />
											</c:if>
										</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='ess.viewpersonalpainfo.heji'/>"
												mask="true"  maxAge="100" minAge="0" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${CountA}</span> </a>
										<c:set var="CountA" value="${0}" />
										<c:set var="PERSON_IDSS" value="${''}" />
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='19'}">
												<a style="cursor: pointer;" width="800" height="400" title="~19"
													mask="true" maxAge="19" minAge="0" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='24'}">
												<a style="cursor: pointer;" width="800" height="400" title="20~24"
													mask="true" maxAge="24" minAge="19" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='29'}">
												<a style="cursor: pointer;" width="800" height="400" title="25~29"
													mask="true" maxAge="29" minAge="24" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='34'}">
												<a style="cursor: pointer;" width="800" height="400" title="30~34"
													mask="true" maxAge="34" minAge="29" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='39'}">
												<a style="cursor: pointer;" width="800" height="400" title="35~39"
													mask="true" maxAge="39" minAge="34" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='44'}">
												<a style="cursor: pointer;" width="800" height="400" title="40~44"
													mask="true" maxAge="44" minAge="39" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='49'}">
												<a style="cursor: pointer;" width="800" height="400" title="45~49"
													mask="true" maxAge="49" minAge="44" deptno="${item.DEPTNO}" index="${currentIndex}"
													onclick='javascript:changeURL_MCS(this);' target="dialog">
													<span> ${it.COUNTNUM}</span> </a>
											</c:if>
										</c:forEach>
									</td>
									<td width="8%" class="td_type">
										<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
											varStatus="i">
											<c:if test="${it.BIRTH=='50'}">
												<a style="cursor: pointer;" width="800" height="400" title="50~"
													mask="true" maxAge="100" minAge="49" deptno="${item.DEPTNO}" index="${currentIndex}"
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

<c:if test="${currentIndex eq '4'}">
<c:if test="${LoginUser.language eq 'vi'}">
	<div class="pageContent">
		<table class="user_table" width="1800px"  layoutH="200">
			<tr id="ess3501_tr4">
				<td width="15%" class="td_title">
				<!--部门--><spring:message code="ess.infoApply.DEPT"/>
				</td>
				<td width="10%" class="td_title">
					<!--总合计--><spring:message code="hrm.approve.TOTAL"/>
				</td>
				<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
						<td width="15%" class="td_title">
							${item.CODENAME }
						</td>
				</c:forEach>
			</tr>

			<tr>
				<td colspan="7">

					<table id="demoTree5" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="15%" class="td_type">
									<span controller="true">${item.DEPTNAME}</span>
								</td>
								<td width="10%" class="td_type">
								
										<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="CountA" value="${it.COUNTNUM+CountA}" />
													<c:set var="EMP_TYPE" value="${item1.CODE_NO},${EMP_TYPE}" />
												</c:if>
											</c:forEach>
										</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='ess.viewpersonalpainfo.heji' />"
											mask="true" empType="${EMP_TYPE}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${CountA}</span> </a>
									<c:set var="CountA" value="${0}" />
									<c:set var="EMP_TYPE" value="${''}" />
								</td>
									<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
										<td width="15%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													 <a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" empType="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
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
<c:if test="${LoginUser.language ne 'vi'}">
	<div class="pageContent">
		<table class="user_table" width="100%"  layoutH="200">
			<tr id="ess3501_tr4">
				<td width="30%" class="td_title">
				<!--部门--><spring:message code="ess.infoApply.DEPT"/>
				</td>
				<td width="10%" class="td_title">
					<!--总合计--><spring:message code="hrm.approve.TOTAL"/>
				</td>
				<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
						<td width="10%" class="td_title">
							${item.CODENAME }
						</td>
				</c:forEach>
			</tr>

			<tr>
				<td colspan="7">

					<table id="demoTree5" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="30%" class="td_type">
									<span controller="true">${item.DEPTNAME}</span>
								</td>
								<td width="10%" class="td_type">
								
										<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													<c:set var="CountA" value="${it.COUNTNUM+CountA}" />
													<c:set var="EMP_TYPE" value="${item1.CODE_NO},${EMP_TYPE}" />
												</c:if>
											</c:forEach>
										</c:forEach>
									<a style="cursor: pointer;" width="800" height="400" title="<spring:message code='ess.viewpersonalpainfo.heji' />"
											mask="true" empType="${EMP_TYPE}" deptno="${item.DEPTNO}" index="${currentIndex}"
											onclick='javascript:changeURL_MCS(this);' target="dialog">
											<span>${CountA}</span> </a>
									<c:set var="CountA" value="${0}" />
									<c:set var="EMP_TYPE" value="${''}" />
								</td>
									<c:forEach items="${empTypeCodeList}" var="item1" varStatus="i">
										<td width="10%">
											<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
												varStatus="i">
												<c:if test="${it.BIRTH eq item1.DES_PAGE}">
													 <a style="cursor: pointer;" width="800" height="400" title="${item1.CODENAME}"
														mask="true" empType="${item1.CODE_NO}" deptno="${item.DEPTNO}" index="${currentIndex}"
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