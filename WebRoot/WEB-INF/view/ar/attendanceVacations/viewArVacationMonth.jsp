<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$("#loading_monthVaccalculate").hide();
});

function openMonthWorkListExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewArVacationMonth");  
     
     var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}

function f_Calculate_monthVaction(){
	//是否开始计算?
	alertMsg.confirm("你确定要计算么？计算后将重新生成年假数据！", {
		okCall: function(){
			$("#loading_monthVaccalculate").show();
			//$("#pageContent").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				//var start_date = $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_START_"+ids[i].value+"']").val();
				url: '/ar/attendanceVacations/monthVacationCal?year=' + document.viewArVacationMonth.year.value + '&month=' + document.viewArVacationMonth.month.value,
	
				dataType: 'json',
				success: function(responseStr) {
					//$("#arVac_calculateResult").html(responseStr);
					$("#loading_monthVaccalculate").hide();
					//$("#pageContent").show();
					alertMsg.info(responseStr);
					$("form[name=viewArVacationMonth]").submit();
				}
			});
		}
	});
}

function f_Create_monthVaction(){
	if (document.viewArVacationMonth.month.value == '12' || document.viewArVacationMonth.flag.value == 1){
		alertMsg.error("当月已经生成");
		return;
	}
	alertMsg.confirm("确定要生成年假么？", {
		okCall: function(){
		$("#loading_monthVaccalculate").show();
		//$("#pageContent").hide();
		$.ajax({
			type: 'post',
			cache: false,
			contentType: 'application/json',
			url: '/ar/attendanceVacations/monthVacationCreate?year=' + document.viewArVacationMonth.year.value + '&month=' + document.viewArVacationMonth.month.value,

			dataType: 'json',
			success: function(responseStr) {
				//$("#arVac_calculateResult").html(responseStr);
				$("#loading_monthVaccalculate").hide();
				//$("#pageContent").show();
				//navTabNum('/ar/attendanceVacations/viewArVacationMonth?menuNo=125073&navTabId=ar0502&year=' + document.viewArVacationMonth.year.value + '&month=' + document.viewArVacationMonth.month.value,'ar0502','月年休假更新');
				alertMsg.info(responseStr);
				$("form[name=viewArVacationMonth]").submit();
			}
		});
		}
	});
}

</script>
<form id="viewArVacationMonth" name="viewArVacationMonth" onsubmit="return navTabSearch(this);" action="/ar/attendanceVacations/viewArVacationMonth" method="post">
<input type="hidden" id='flag' name='flag' value="0"/> 
<div class="pageHeader">
		<div id="loading_monthVaccalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						休假周期
					</td>
					<td>
						<ait:date yearName="year" monthName="month" yearSelected="${year}" monthSelected="${month}" yearPlus="10"  />
					</td>
					<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">
						<!-- 查询 --><spring:message code="button.search"/></button></div>
					</div>
					<c:if test="${toolbarInfo.INSERTR eq '1'}">
						<a class="buttonActive"
							onclick="f_Calculate_monthVaction();"><span>计算</span>
						</a>
						<a class="buttonActive"
							onclick="f_Create_monthVaction();"><span>生成</span>
						</a>
					</c:if>
					<a class="buttonActive" onclick="openMonthWorkListExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  		<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
				   </td>
				</tr>
			</table>
		</div>
</div>
<div id="arVac_calculateResult" align="center"></div>
<div class="pageContent">
						<table class="list"  width="100%" layoutH="141">
							<thead>
							<tr>
	                            <th class="th_title" rowspan="3">
	                                       	区分
	                                </th>
	                                <th class="th_title" colspan="6">
	                                    	对象
	                                </th>
	                                <th class="th_title" colspan="6">
	                                    	非对象
	                                </th>    
                            </tr>
                            <tr> 
                                <th class="th_title" colspan="2">
                                    	事物职
                                </th>
                                <th class="th_title" colspan="2">
                                    	技能职
                                </th>
                                <th class="th_title" colspan="2">
                                   		小计
                                </th>
                                <th class="th_title" colspan="3">
                                    	上年度排除人数
                                </th>
                            </tr>
                             <tr> 
                                <th class="th_title">
                                    	对象数
                                </th>
                                <th class="th_title">
                                 		天数
                                </th>
                                <th class="th_title">
                                    	对象数
                                </th>
                                <th class="th_title">
                                 		天数
                                </th>
                                <th class="th_title">
                                    	对象数
                                </th>
                                <th class="th_title">
                                 		天数
                                </th>
                                <th class="th_title">
                                    	事物职
                                </th>
                                <th class="th_title">
                                   		技能职
                                </th>
                                <th class="th_title">
                                		合计
                                </th>    
                            </tr>
                           </thead>
                           <tbody>
							<c:forEach items="${arVacationUpdateMonthList}" var="oneResult" varStatus="i">
							<!-- 
								<c:if test="${i.count==1}">
								<input type="hidden" id='flag' name='flag' value="${oneResult.FLAG}"/> 
								 </c:if>
								 <c:if test="${oneResult.FLAG==1&&i.count==1}"> 
                                <tr><td colspan="13"><font color="red">年假数据已经生效或没有计算或当月没有变化</font></td></tr>
                                </c:if>
							<c:if test="${i.count==1}">
                                <input type="hidden" id='flag' value="${oneResult.FLAG}"/> 
                                
                                </c:if>
                                 -->
								<tr>
									<td style="text-align:center;">
									   ${oneResult.QUFEN}
									</td>
									<td>
                                       ${oneResult.SHIWURENSHU}
                                    </td>
                                    <td>
                                       ${oneResult.SHIWUTIANSHU}
                                    </td>
                                    <td>
                                       ${oneResult.JINENGRENSHU}
                                    </td>
                                    <td>
                                       ${oneResult.JINENGTIANSHU}
                                    </td>
                                    <td>
                                       ${oneResult.XIAOJIRENSHU}
                                    </td>
                                    <td>
                                       ${oneResult.XIAOJITIANSHU}
                                    </td>
                                    <td>
                                       ${oneResult.SHIWUPAICHU}
                                    </td>
                                    <td>
                                       ${oneResult.JINENGPAICHU}
                                    </td>
                                    <td>
                                       ${oneResult.HEJIPAICHU}
                                    </td>
								</tr> 
							</c:forEach> 
							</tbody>
						</table>
</div>
</form>	
