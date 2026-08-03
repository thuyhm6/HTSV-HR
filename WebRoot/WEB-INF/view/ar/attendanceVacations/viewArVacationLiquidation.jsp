<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	var myDate = new Date();
	var dt=myDate.getFullYear();    
	var lastdt=parseInt(dt)-1;
	$("#liquidation", navTab.getCurrentPanel()).empty();  
	$("#liquidation").append("<option value=''>请选择</option>");
	$("#liquidation").append("<option value='125080'>转年剩余("+lastdt+")</option>");
	$("#liquidation").append("<option value='125081'>今年剩余("+dt+")</option>");
	$("#liquidation").append("<option value='125082'>全部剩余年假</option>");
	$("#liquidation option").eq(0).attr('selected', 'true');
 }); 
function CheckFormExpiredContract(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doVacationLiquidationExport(from){
  	var $from =$(from);
  	var url ="/ar/attendanceVacations/viewArVacationLiquidationExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expLiquidationView(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewArVacationLiquidation");
  	if(CheckFormExpiredContract($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doLeaveViewExport($from);}});
    } 
}
function Save() { 
 
    //确定要提交吗？
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){
				var $form = $("#viewArVacationLiquidation");	
			  	$.ajax({
					type: 'POST',                
					url:"/ar/attendanceVacations/saveVacationLiquidation",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					//success :callback || DWZ.ajaxDone,
			  		success: function(data) {
			  			
			  				alertMsg.info(data.message);
			  		}	,
					error: DWZ.ajaxError
				});	
				return false;
			}
    
}

</script>

<div class="pageHeader">
	<form id="viewArVacationLiquidation" onsubmit="return navTabSearch(this);" action="/ar/attendanceVacations/viewArVacationLiquidation" method="post" rel="pagerForm">
	<input type="hidden" value="${year}" id="year" name="yaer_0506">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 基准年度 -->
					<spring:message code="display.mutual.baseyear"/>
					<input type="text" id="baseyear_ar0506" name="baseyear_ar0506" value="${year}-12-20" readonly="readonly">
				</td>
				<td><!-- 清算范围 -->
					<spring:message code="display.mutual.scopeliquidation"/>
					<ait:SelectSyCodeByCpnyID id="liquidation" name="seach_liquidation" parentNo="125079" cnpyID="${defaultCpny}" onChangeName="" limit="all" selected="${liquidation}"/>	
				</td>
			</tr>
			
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="Save()">
								<spring:message code="display.mutual.liquidation"/><!-- 清算 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="doVacationLiquidationExport(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
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
	<table width="100.8%" class="list" layoutH="100">
	<thead>
	  <tr>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">职号</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">姓名</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">部门</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">职系</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">入社日期</th>
	    <th width="5%" colspan="3" class="td_center" nowrap="nowrap">年假数</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">累计年假补偿单价</th>
	    <th width="5%" colspan="3" class="td_center" nowrap="nowrap">金额</th>
	  </tr>
	  <tr>
	    <th width="5%" class="td_center" nowrap="nowrap">转年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">今年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">计</th>
	    <th width="5%" class="td_center" nowrap="nowrap">转年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">今年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">计</th>
	  </tr>
	  </thead>
	  
	  
	  <c:forEach items="${arVacationLiquidationList}" var="oneResult"varStatus="i">
			<tr align="center">
				<td nowrap="nowrap"> 
                   <input type="hidden" name="yinian_${oneResult.VACATION_NO}"value="${oneResult.YINIAN}" />
                   <input type="hidden" name="bennian_${oneResult.VACATION_NO}"value="${oneResult.BENNIAN}" />&nbsp;
                                        <c:set var="empsum" value="${empsum+1}">
                                        </c:set>
                                        <c:set var="yiniansum" value="${yiniansum+oneResult.YINIAN}">
                                        </c:set>
                                        <c:set var="benniansum"
                                            value="${benniansum+oneResult.BENNIAN}">
                                        </c:set>
                                        <c:set var="xiajisum"
                                            value="${xiajisum+oneResult.YINIAN+oneResult.BENNIAN}">
                                        </c:set>
                                        <c:set var="yinianjinsum"
                                            value="${yinianjinsum+oneResult.YINIANJE}">
                                        </c:set>
                                        <c:set var="bennianjinsum"
                                            value="${bennianjinsum+oneResult.BENNIANJE}">
                                        </c:set>
                                        <c:set var="xiajijinsum"
                                            value="${xiajijinsum+oneResult.YINIANJE+oneResult.BENNIANJE}">
                                        </c:set>
                                        <c:set var="danjiaheji"
                                            value="${danjiaheji+oneResult.DANJIA}">
                                        </c:set>
										
										${oneResult.EMPID}
									</td>
									<td  nowrap="nowrap">${oneResult.LOCAL_NAME} &nbsp;</td>
									<td  nowrap="nowrap">${oneResult.DEPTNAME} &nbsp;</td>
									<td nowrap="nowrap">${oneResult.POST_COEFNAME} &nbsp;</td>
									<td nowrap="nowrap">${oneResult.DATE_STARTED}</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125081'}">-</c:if>
										<c:if test="${liquidation eq '125080' || liquidation eq '125082'}"> ${oneResult.YINIAN}<spring:message code="display.mutual.day"/></c:if>
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125080'}">-</c:if>
										<c:if test="${liquidation eq '125081' || liquidation eq '125082'}">${oneResult.BENNIAN}<spring:message code="display.mutual.day"/></c:if>
									</td>
									<td nowrap="nowrap">
										${oneResult.YINIAN+oneResult.BENNIAN}
										<spring:message code="display.mutual.day"/>
									</td>
									<td nowrap="nowrap">
										${oneResult.DANJIA}
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125081'}">-</c:if>
										<c:if test="${liquidation eq '125080' || liquidation eq '125082'}"> ${oneResult.YINIANJE}</c:if>
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125080'}">-</c:if>
										<c:if test="${liquidation eq '125081' || liquidation eq '125082'}"> ${oneResult.BENNIANJE} </c:if>
									</td>

									<td nowrap="nowrap">${oneResult.YINIANJE+oneResult.BENNIANJE}</td>
								</tr>
							</c:forEach>
	</table>
		
</table>
	<c:set value="/ar/attendanceVacations/viewArVacationLiquidation" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
