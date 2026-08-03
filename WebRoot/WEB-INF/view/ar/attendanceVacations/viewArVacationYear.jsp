<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
$(function(){
	$("#loading_yearVaccalculate").hide();
});

function openYearVacListExcle(a){
    var $this=$(a);
    var title = $this.attr("title"); 
    var $from = $("#viewArVacationYear");  
    
    var url = "/ar/attendanceVacations/viewArVacationYearExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}

function Update(no,form, callback){

	alertMsg.confirm("确定要更改年假么？", {
		okCall: function(){
		$.ajax({
			type: form.method || 'POST',
			url:'/ar/attendanceVacations/updateArVacationYear?',
			data:'vacation_no='+no+'&vacation_num='+$("#nian_"+no).val(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		}
	});
	
	//return false;
}

function f_Calculate_yearVaction(){
	//是否开始计算?
	alertMsg.confirm("您确定要计算吗？计算后将重新生成年假数据！", {
		okCall: function(){
			$("#loading_yearVaccalculate").show();
			//$("#pageContent").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceVacations/yearVacationCal?vac_id=' + document.viewArVacationYear.vac_id.value,
	
				dataType: 'json',
				success: function(responseStr) {
					$("#loading_yearVaccalculate").hide();
					alertMsg.info(responseStr);
					$("form[name=viewArVacationYear]").submit();
					//navTabSearch("viewArVacationYear");不带参数刷新 ，不适用
				}
			});
		}
	});
}

function f_Create_yearVaction(){
	//是否开始计算?
	alertMsg.confirm("确定要生成年假么？", {
		okCall: function(){
			$("#loading_yearVaccalculate").show();
			//$("#pageContent").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceVacations/yearVacationCreate?vac_id=' + document.viewArVacationYear.vac_id.value,
	
				dataType: 'json',
				success: function(responseStr) {
					$("#loading_yearVaccalculate").hide();
					alertMsg.info(responseStr);
					$("form[name=viewArVacationYear]").submit();
					//navTabSearch("viewArVacationYear");不带参数刷新 ，不适用
				}
			});
		}
	});
}

</script>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="/ar/attendanceVacations/viewArVacationYear" method="post" name="viewArVacationYear" id="viewArVacationYear"
			rel="pagerForm">
			<div id="loading_yearVaccalculate"
				style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
				<img src="/resources/images/loading.gif">
			</div>
			<div class="searchBar">
				<table class="searchContent" border="0" width="100%">
					<tr>
						<td>
							<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:
							<input name="seach_key" type="text" value="${key}" id="seach_key"/>
						</td>
						<td>
							<!-- 部门 --><spring:message code="public.title.deptName"/>:
							<ait:deptTree name="seach_deptID" limit="ar" selected="${deptID}"/>
						</td>
						<td>
							是否为对象
							<select name="seach_flag">
									<option value="yes" <c:if test="${flag eq 'yes'}">selected</c:if>>
										是
									</option>
									<option value="no" <c:if test="${flag eq 'no'}">selected</c:if>>
										否
									</option>
							</select>
						</td>
						<td>
								休假周期
								<ait:date yearName="vac_id"  yearSelected="${vac_id}"  yearPlus="10"  />
						</td>
					</tr>
				</table>
				<div class="subBar">
								<ul>
									<li>
										<div class="buttonActive">
											<div class="buttonContent">
												<button type="submit" id="submitClick_ar0201">
													<!-- 查询 --><spring:message code="button.search"/>
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
								<ul>
									
									<c:if test="${toolbarInfo.INSERTR eq '1'}">
										<li>
											<a class="buttonActive"
												onclick="f_Calculate_yearVaction();"><span>计算</span>
											</a>
										</li>
										<li>
											<a class="buttonActive"
												onclick="f_Create_yearVaction();"><span>生成</span>
											</a>
										</li>
									</c:if>
									<li>
										<a class="buttonActive" onclick="openYearVacListExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  						<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
									</li>
								</ul>
							</div>
		<form onsubmit="return navTabSearch(this);"
			action="/ar/attendanceVacations/updateArVacationYear" method="post" name="updateArVacationYear" id="updateArVacationYear"
			onsubmit="return cancelAgentTransValidateCallback(this, navTabAjaxDone);">
			<table class="table" width="100%" layoutH="206">
				<thead>
					<tr>
                                <th width="5%">
                                   		状态
                                </th>
                                <th width="10%">
                                    	工号	
                                </th>
                                <th width="3%">
                                    	姓名
                                </th>
                                <th width="10%">
                                    	部门
                                </th>
                                <th width="5%">
	                                       职系	
                                </th>
                                <th width="5%">
                                    	入社日
                                </th>
                                <th>
                                    	工龄明细 本公司
                                </th>
                                <th>
                                    	工龄明细 其他公司
                                </th>
                                <th>
                                    	工龄明细 总计
                                </th>
                                <th>
                                    	发生基准月
                                </th>
                                <th>
                                   	           对象与否
                                </th>
                                <th>
                                  	 	影响年休假的休假天数
                                </th>
                                <th>
                                   		基本休假
                                </th>
                                <th>
                                    	基本扣除
                                </th>
                                 
                                <th>
                                    	年假合计
                                </th>
                            </tr>
				</thead>
				<tbody>
					<c:forEach items="${arVacationUpdateYearList}" var="oneResult" varStatus="i">
                                <tr align="center" onclick="band('#f4f7fa','black')">
                                    <td nowrap="nowrap">
                                         <c:if test="${oneResult.ACTIVITY==1}">
							                                       已生成</c:if> 
							             <c:if test="${oneResult.ACTIVITY==0||oneResult.ACTIVITY==2}">
							                                        未生成</c:if> 
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.EMPID}
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.LOCAL_NAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.DEPTNAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.POST_COEFNAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.DATE_STARTED}
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.INSIDE}月
                                    </td>
                                    <td nowrap="nowrap">
                                       ${oneResult.OUTSIDE}月
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.OUTSIDE+oneResult.INSIDE}月
                                    </td>
                                    <td nowrap="nowrap">
                                        ${vac_idb}-12-20
                                    </td>
                                    <td nowrap="nowrap">
                                         <c:if test="${oneResult.ACTIVITY==0||oneResult.ACTIVITY==1}">
                                                   Y</c:if> 
                                                   <c:if test="${oneResult.ACTIVITY==2}">
                                                   N</c:if> 
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.LEAVE_TOTAL}&nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                     <c:if test="${oneResult.ACTIVITY==0}">
                                     <input  type="text"  size="2" maxlength="2" name="nian_${oneResult.VACATION_NO}" value="${oneResult.TOT_VAC_CNT}"/>
                                       	天
                                       	<a class="buttonActive"
												onclick="Update(${oneResult.VACATION_NO},this,DWZ.ajaxDone);"><span>修改</span>
										</a>
                                     </c:if>
                                     <c:if test="${oneResult.ACTIVITY==1}">
                                      ${oneResult.TOT_VAC_CNT}&nbsp;天
                                     </c:if>
                                     </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.LAST_YEAR_VAC}&nbsp;天
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.TOT_VAC_CNT+oneResult.LAST_YEAR_VAC}&nbsp;天
                                    </td>
                                </tr>
                            </c:forEach>
				</tbody>
			</table>
		</form>
		<c:set value="/ar/attendanceVacations/viewArVacationYear" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
	
	
