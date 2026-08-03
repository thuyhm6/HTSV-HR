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
	var year = $("#seach_YEAR",navTab.getCurrentPanel()).attr("value");
	var yearFlag = '';
	if (year < '2014') {
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
	if(index == '1'){
		var MONTH = $(obj).attr("month");
		name += "&MONTH=" + MONTH;
	}
	var href1 = '/hrm/approve/monthPersonCountInfoSonList?type=lizhi&currentIndex=' + 1 + name;
	obj.href = href1;
	obj.click;

}

// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree31').treeTable( {
			expandLevel : 2
		});
		
		

	});
</script>
</head>

<c:if test="${currentIndex eq '1'}">
	<div class="pageContent">
		<table class="user_table" width="100%">
			<tr>
				<td width="12%" class="td_title" rowspan="1">
					<!--部门--><spring:message code="ess.infoApply.DEPT" />
				</td>
				<td width="4%" class="td_title" rowspan="1">
					<!--总合计--><spring:message code="ess.infoApply.summation_meter" />
				</td>
				<td width="7%" class="td_title" rowspan="1">
					01
				</td>
				<td width="7%" class="td_title" rowspan="1">
					02
				</td>
				<td width="7%" class="td_title" rowspan="1">
					03
				</td>
				<td width="7%" class="td_title" rowspan="1">
					04
				</td>
				<td width="7%" class="td_title" rowspan="1">
					05
				</td>
				<td width="7%" class="td_title" rowspan="1">
					06
				</td>
				<td width="7%" class="td_title" rowspan="1">
					07
				</td>
				<td width="7%" class="td_title" rowspan="1">
					08
				</td>
				<td width="7%" class="td_title" rowspan="1">
				    09
				</td>
				<td width="7%" class="td_title" rowspan="1">
					10
				</td>
				<td width="7%" class="td_title" rowspan="1">
					11
				</td>
				<td width="7%" class="td_title" rowspan="1">
					12
				
				</td>
			</tr>
			<tr>
				<td colspan="14">

					<table id="demoTree31" width="100%">
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if> >
								<td width="12%" class="td_type">
									<span controller="true">${item.DEPTNAME}</span>
									
									
								</td>
								
								<td width="4%" class="td_title">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										
											<c:set var="Count1" value="${it.COUNTNUM+Count1}" />
									</c:forEach>
										<a style="cursor: pointer;" width="800" height="400" title="<spring:message code="ess.infoApply.summation_meter" />"
												mask="true" month="" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${Count1}</span> 
										</a>
									<c:set var="Count1" value="${0}" />
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '01'}">
		                                   <a style="cursor: pointer;" width="800" height="400" title="01"
												mask="true" month="01" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '02'}">
											<a style="cursor: pointer;" width="800" height="400" title="02"
												mask="true" month="02" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '03'}">
											<a style="cursor: pointer;" width="800" height="400" title="03"
												mask="true" month="03" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '04'}">
											<a style="cursor: pointer;" width="800" height="400" title="04"
												mask="true" month="04" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '05'}">
											<a style="cursor: pointer;" width="800" height="400" title="05"
												mask="true" month="05" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '06'}">
		   									<a style="cursor: pointer;" width="800" height="400" title="06"
												mask="true" month="06" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '07'}">
											<a style="cursor: pointer;" width="800" height="400" title="07"
												mask="true" month="07" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '08'}">
											<a style="cursor: pointer;" width="800" height="400" title="08"
												mask="true" month="08" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '09'}">
											<a style="cursor: pointer;" width="800" height="400" title="09"
												mask="true" month="09" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '10'}">
											<a style="cursor: pointer;" width="800" height="400" title="10"
												mask="true" month="10" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '11'}">
											<a style="cursor: pointer;" width="800" height="400" title="11"
												mask="true" month="11" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
			                           </c:if>

									</c:forEach>
								</td>
								<td width="7%" class="td_type">
									<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
										varStatus="i">
										<c:if test="${it.BIRTH eq '12'}">
											<a style="cursor: pointer;" width="800" height="400" title="12"
												mask="true" month="12" deptno="${item.DEPTNO}" index="${currentIndex}"
												onclick='javascript:changeURL_MCS(this);' target="dialog">
												<span> ${it.COUNTNUM}</span> 
											</a>
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


