<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<head>
</head>
<c:if test="${currentIndex eq '1'}">
<script type="text/javascript">
	function importExcel3(obj){
		
		href1 = $("#monthPersonCISListExport").attr("action");
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
		name += "&seach_EMP_TYPE_CODE=" + empType;
		var index = $(obj).attr("index");
		var dept = $(obj).attr("deptno");
		name += "&INDEX=" + index;
		name += "&seach_DEPTNO=" + dept;
		if(index == '1'){
			var social_differentiation = $(obj).attr("social_differentiation");
			name += "&SOCIAL_DIFFERENTIATION=" + social_differentiation;
		}else if(index == '0'){
			var emptype = $(obj).attr("emptype");
			name += "&EMPTYPE=" + emptype;
		}else if(index == '2'){
			var grade = $(obj).attr("grade");
			name += "&GRADE=" + grade;
		}
	href1 = $("#monthPersonCISListExport").attr("action") + "?currentIndex=1" + name;
	$("#monthPersonCISListExport").attr("action",href1);
	$("#monthPersonCISListExport").submit();
	}
</script>
	<div class="pageHeader">
		<div class="searchBar">
			<form class="j-ajax"
				action="/pa/workManagement/monthPersonCISListExport1"
				method="post" id = "monthPersonCISListExport">
				<input type="hidden" name="currentIndex" value="${currentIndex}">
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div>
									<button id="importExcel" deptno="${DEPTNO}" index="${INDEX}" onclick="javascript:importExcel3(this);"
										social_differentiation="${SOCIAL_DIFFERENTIATION}" emptype="${EMPTYPE}" grade="${GRADE}">
										<span><!-- 导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span>
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</c:if>
<div class="pageContent">
<c:if test="${currentIndex eq '1'}">
</script>
		<table class="table" width="100%" layoutH="50">
			<thead>
				<tr>
					<th>
						No.
					</th>
					<th>
						<!-- 姓名 --><spring:message code="inct.salesman.Name" />
					</th>
					<th>
						<!-- 社号 --><spring:message code="inct.salesman.empNo" />
					</th>
					<th>
						<!-- 部门 --><spring:message code="pa.salary.canShu.buMen" />
					</th>
					<th>
						<!-- 职级 --><spring:message code="sys.postManage.title.postGrade" />
					</th>
					<th>
						<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
					</th>
					<th>
						<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
					</th>
					<th>
						<!-- 状态 --><spring:message code="org.title.status" />
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monthPersonCountInfoSonList}" var="item"
					varStatus="i">

					<tr>
						<td style="text-align: center">
							${i.count}
							<span id="isSelectRow_${i.count}"></span>
						</td>
						<td style="text-align: center">
							${item.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${item.EMPID}
						</td>
						<td style="text-align: center">
							${item.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${item.POST_GRADE}
						</td>
						<td style="text-align: center">
							${item.MAIN_BUSINESS}
						</td>
						<td style="text-align: center">
							${item.EMP_TYPE}
						</td>
						<td style="text-align: center">
							${item.EMP_OFFICE}
						</td>
					</tr>
				</c:forEach>
				<c:if test="${totalCount == 0 }">
					<tr>
						<td style="text-align: left;" colspan="9">
							<!--  没有查找的数据--><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" />
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
</div>

