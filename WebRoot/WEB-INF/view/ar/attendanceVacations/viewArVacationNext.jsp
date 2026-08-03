<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
$(function(){
	$("#loading_nextVaccalculate").hide();
});

function openYearVacListExcle(a){
    var $this=$(a);
    var title = $this.attr("title"); 
    var $from = $("#viewArVacationNext");  
    
    var url = "/ar/attendanceVacations/viewArVacationNextExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}

function f_Calculate_nextVacation(){
	//是否开始计算?
	alertMsg.confirm("确定要将年假移年么？", {
		okCall: function(){
			$("#loading_nextVaccalculate").show();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceVacations/nextVacationMove?vac_id=' + document.viewArVacationNext.vac_id.value,
	
				dataType: 'json',
				success: function(responseStr) {
					$("#loading_nextVaccalculate").hide();
					alertMsg.info(responseStr);
					$("form[name=viewArVacationNext]").submit();
					//navTabSearch("viewArVacationNext");不带参数刷新 ，不适用
				}
			});
		}
	});
}

</script>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="/ar/attendanceVacations/viewArVacationNext" method="post" name="viewArVacationNext" id="viewArVacationNext"
			rel="pagerForm">
			<div id="loading_nextVaccalculate"
				style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
				<img src="/resources/images/loading.gif">
			</div>
			<div class="searchBar">
				<table class="searchContent" border="0" width="100%">
					<tr>
						<td align="center">
							基准年度
						</td>
						<td>
							${vac_id}-12-20
						</td>
						<td>
							清算范围
						</td>
						<td>
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
												onclick="f_Calculate_nextVacation();"><span>移年</span>
											</a>
										</li>
									</c:if>
									<li>
										<a class="buttonActive" onclick="openYearVacListExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  						<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
									</li>
								</ul>
							</div>
			<table class="table" width="100%" layoutH="206">
				<thead>
					<tr align="center" bgcolor="#F5F5F5">
                                <th >
                                    	工号
                                </th>
                                <th >
                                    	姓名
                                </th>
                                <th >
                                    	部门
                                </th>
                                <th >
                                    	职系
                                </th>
                                <th >
                                    	入社日
                                </th>
                                <th >
                                    	年假数
                                </th>
                            </tr>                            
				</thead>
				<tbody>
					<c:forEach items="${arVacationEmpList}" var="oneResult" varStatus="i">
                                <tr align="center" onclick="band('#f4f7fa','black')">
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
                                         ${oneResult.BENNIAN}天
                                    </td>
                                </tr>
                            </c:forEach>
				</tbody>
			</table>
		<c:set value="/ar/attendanceVacations/viewArVacationNext" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
	
	
