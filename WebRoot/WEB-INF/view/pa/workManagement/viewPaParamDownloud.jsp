<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(function() {
	$('#TestA', navTab.getCurrentPanel()).hide();
});

function qufenAndNameCheng(obj) {
	if (obj == null || obj.length == 0) {
		return;
	}
	$("#importItemDiv").hide();
	var sel = $("#seach_PARAM_NO", navTab.getCurrentPanel());//项目区分
	sel.empty();
	$
			.ajax( {
				cache : false,
				type : 'post',
				async : false,
				url : "/pa/salary/getqufenAndNameCheng2?",
				data : 'seach_ITEM_DISTINGUISH=' + obj,
				dataType : "json",
				success : function(data) {
					sel
							.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>");
					$.each(data, function(key, value) {
						if ($(data).size() > 0) {
							sel.append('<option value=' + key + '>' + value
									+ '</option>');
						}
					});
				}
			});
}
function exportExcel() {
	var post = document.getElementById('postName');
	post.action = "/pa/paView/viewWageComExcel";
	post.submit();
}

function selectAll(obj) {
	var name = obj.id;
	var PARAM_NO = 'PARAM_NO_' + name;

	$("[alt='" + PARAM_NO + "']").attr("checked", obj.checked);

}
function removeA(obj) {
	obj.removeAttr('href');

}
</script>
<a id="importExcel_pa0824" target="navTab" mask="true"><span
	style="display: none;"><!--工资数据导入结果--><spring:message code="pa.viewPaParamDownloud.GONGZISHUJUDAORUJIEGUO.C" /></span>
</a>

<form action="/pa/excelExport/downloadPaExcelTemplate" method="post"
	id="postName">
	<input type="hidden" name="file" value="wage_upload">
	<div class="tabsContent">
		<div layoutH="10"
			style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
			<ul class="tree treeFolder" style="position: relative; left: 30px">
				<!-- 1 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="BZ" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--标准项目--><spring:message code="pa.viewPaParamDownloud.BIAOZHUNXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '1'}">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_BZ">
								<li>
									<a rel="viewPaInputItemData_${itemType}"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 2 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="ZFTZ" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--支付调整项目--><spring:message code="pa.viewPaMain.ZHIFUTIAOZHENGXIANGMU.C" /></a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '2'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt='PARAM_NO_ZFTZ'>
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 3-->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="ZFLW" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--支付例外项目--><spring:message code="pa.viewPaMain.ZHIFULIWAIXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '3'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_ZFLW">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 4 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="KCXM" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--扣除项目调整--><spring:message code="pa.viewPaMain.KOUCHUTIAOZHENGXIANGMU.C" /></a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '4'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_KCXM">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>

				<!-- 5 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="KCLW" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--扣除例外项目--><spring:message code="pa.viewPaMain.KOUCHULIWAIXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '5'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_KCLW">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>

				<!-- 6-->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="BXBZ" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--保险标准项目--><spring:message code="pa.viewPaMain.BAOXIANBIAOZHUNXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '6'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_BXBZ">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 7 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="BXBJ" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--保险补缴)--><spring:message code="pa.viewPaMain.BAOXIANBUJIAOXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '7'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_BXBJ">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 8 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="BXBK" onclick="javascript:selectAll(this)">
				<li>
					<a href=""><!--保险补扣--><spring:message code="pa.viewPaMain.BAOXIANBUKOUXIANGMU.C" /></a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '8'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_BXBK">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<!-- 9 -->
				<input style="position: relative; bottom: -17px; left: -10px"
					type="checkbox" id="BXLW" onclick="javascript:selectAll(this)">
				<li>
					<a href=""> <!--保险例外项目--><spring:message code="pa.viewPaMain.BAOXIANLIWAIXIANGMU.C" /> </a>
					<ul>
						<c:if test="${empty proList}">
							<li id="TestA">
								<a></a>
							</li>
						</c:if>
						<c:forEach items="${proList}" var="item">
							<c:if test="${item.ITEM_TYPE eq '9'   }">
								<input style="position: relative; bottom: -16px; left: 15px"
									type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
									alt="PARAM_NO_BXLW">
								<li>
									<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>

				<!-- 10 -->
				<c:if test="${LoginUser.cpnyId == 'TSTO'}">
					<input style="position: relative; bottom: -17px; left: -10px"
						type="checkbox" id="SGSCTJ" onclick="javascript:selectAll(this)">
					<li>
						<a href=""><!--手工上传统计项目--><spring:message code="pa.viewPaMain.SHOUGONGSHANGCHUANTONGJIXIANGMU.C" /></a>
						<ul>
							<c:if test="${empty proList}">
								<li id="TestA">
									<a></a>
								</li>
							</c:if>
							<c:forEach items="${proList}" var="item">
								<c:if test="${item.ITEM_TYPE eq '10'   }">
									<input style="position: relative; bottom: -16px; left: 15px"
										type="checkbox" value='${item.PARAM_ITEM_NO}' name="PARAM_NO"
										alt="PARAM_NO_SGSCTJ">
									<li>
										<a rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
										</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
				</c:if>
			</ul>
		</div>

		<div id="viewPaInputItemData_${itemType }" class="unitBox"
			style="margin-left: 242px;">
			<div class="pageContent">

				<div class="tabsFooter">

					<div class="pageHeader">
						<div class="searchBar">
							<table class="searchContent" height="100PX">
								<tr>
									<td>
									</td>
								</tr>

							</table>


							<div class="subBar">
								<ul>
									<li>
										<div class="buttonActive">
											<div  >
												<button type="submit">
													<span><!--下载导入模版--><spring:message code="pa.viewPaParamDownloud.XIAZAIDAORUMUBAN.C" /></span>
												</button>
											</div>

										</div>
									</li>

									<li>
										<div class="buttonActive">

											<a class="add"
												href="/pa/excelImport/importPaExcelData?importFunName=/importPARAMTemp&amp;REGISTER_SEQ=28"
												target="dialog" mask="true"><span><!--数据导入--><spring:message code="pa.viewPaParamDownloud.SHUJUDAORU.C" /></span> </a>
										</div>
									</li>

								</ul>
							</div>
						</div>
					</div>
					<%--<div class="pageContent" id="shangyue">
						<table class="table" width="100%" layoutH="200">
							<thead>
								<tr>
									<th width="12">
										<spring:message code="public.title.name" />
										<!--姓名-->
									</th>
									<th width="12">
										<spring:message code="public.title.empId" />
										<!--工号-->
									</th>
									<th width="12">
										<spring:message code="public.title.deptName" />
										<!--部门-->
									</th>
									<th width="12">
										<spring:message code="pa.viewWageCom.Wageproject" />
										<!--工资项目 -->
									</th>
									<th width="12">
										<spring:message code="pa.viewWageCom.Lastmonthdata" />
										<!--上月数据-->
									</th>
									<th width="12">
										<spring:message code="pa.viewWageCom.monthdata" />
										<!--本月数据-->
									</th>
									<th width="12">
										<spring:message code="pa.viewWageCom.Differencetwo" />
										<!--差额-->
									</th>
									<th width="12">
										<spring:message code="pa.viewWageCom.Percentage" />
										<!--百分比-->
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${afterList}" var="afterList" varStatus="i">
									<tr>
										<td>
											${afterList.PARAM_ITEM_NO }
										</td>
										<td>
											${afterList.PARAM_ITEM_NAME }
										</td>
										<td>
											${afterList.EMPID }
										</td>
										<td>
											${afterList.START_MONTH }
										</td>
										<td>
											${afterList.END_MONTH }
										</td>
										<td>
											${afterList.RETURN_VALUE }
										</td>
										<td>
										<font color="red" >	${afterList.UPLOAD_ERROR_MSG }</font>
										</td>
										<td>
									
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
--%>
</form>
</div>
</div>
</div>
</div>
