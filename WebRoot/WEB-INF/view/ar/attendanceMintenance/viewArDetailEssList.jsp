<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
function pageFromSea(a){
	var seach_sDate  = $("#seach_sDate",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_sDate",navTab.getCurrentPanel()).val();
	var seach_eDate  = $("#seach_eDate",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_eDate",navTab.getCurrentPanel()).val();
	var seach_itemNo = $("#seach_itemNo",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_itemNo",navTab.getCurrentPanel()).val();
	var seach_status = $("#seach_status",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_status",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ar/attendanceMintenance/viewArDetailEssList?seach_sDate="+seach_sDate
			+"&seach_eDate="+seach_eDate+"&seach_itemNo="+seach_itemNo+"&seach_status="+seach_status);
}

function exportArDetailEssExcel(obj){
	var sDate = document.searchArDetailEssForm.seach_sDate.value;;
	var eDate = document.searchArDetailEssForm.seach_eDate.value;;
	var itemNo = document.searchArDetailEssForm.seach_itemNo.value;;
	var status = document.searchArDetailEssForm.seach_status.value;;

	document.getElementById("exportArDetailEssExcel").href="/pa/excelExport/exportArDetailEssListExcel?sDate="+sDate
		+"&eDate="+eDate+"&itemNo="+itemNo+"&status="+status;
	document.getElementById("exportArDetailEssExcel").click();
}
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArDetailEssList" method="post" 
		name="searchArDetailEssForm" id="searchArDetailEssForm" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent" border="0" width="100%">
				<tr>
					<td><!-- 开始日期 -->
						<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>:
					</td>
					<td>
						<input type="text" name="seach_sDate" class="date" value="${sDate}" format="yyyy/MM/dd" yearstart="-20" yearend="20" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td><!-- 结束日期 -->
						<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>:
					</td>
					<td style="padding-left: 1px">
						<input type="text" name="seach_eDate" class="date" value="${eDate}"format="yyyy/MM/dd"  yearstart="-20" yearend="20" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td><!-- 考勤区分 -->
						<spring:message code="ar.viewardetail.title.kaoqinqufen"/>:
					</td>
					<td>
						<select name="seach_itemNo" class="select">
							<option value=""><!-- 全部 -->
								<spring:message code="ar.viewarcardrecord.title.quanbu"/>
							</option>
							<c:forEach items="${getItemList}" var="item">
								<option value="${item.ITEM_NO}" 
									<c:if test="${item.ITEM_NO eq itemNo}">selected</c:if>>
									${item.ITEM_NAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<!--  <td><!-- 状态 
						<spring:message code="ar.viewcycle.title.zhuangtai"/>:
					</td>
					<td>
						<select name="seach_status" class="select">
							<option value="" <c:if test="${status ne 'Y' && status ne 'N'}">selected</c:if>>
								<spring:message code="ar.viewarcardrecord.title.quanbu"/><!-- 全部 
							</option>
							<option value="Y" <c:if test="${status eq 'Y'}">selected</c:if>>
								<spring:message code="ar.viewardetail.title.lock"/><!-- 锁定 --
							</option>
							<option value="N" <c:if test="${status eq 'N'}">selected</c:if>>
								<spring:message code="ar.viewardetail.title.release"/><!-- 开放 --
							</option>
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess0115_limit" name="limit" value="ar">
						<input type="hidden" id="ess0115_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess0115_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess0115_seach_JobTypeGroupNo,ess0115_seach_EmpTypeCodeNo,ess0115_seach_CPNY,ess0115_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess0115_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
						
						<td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>-->
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><!-- 查询 -->
									<spring:message code="button.search"/>
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
	<!--<div class="formBar">
		<ul class="toolBar">
			<li>
				<a id="exportArDetailEssExcel" class="buttonActive" onclick="exportArDetailEssExcel(this);">
					<span> EXCEL导出 <spring:message code="ar.addempshift.title.excelexport"/></span>
				</a>
			</li>
		</ul>
	</div>
	--><table class="table" width="100%" layoutH="208">
		<thead>
			<tr>
				<th width="4%" style="text-align:center"><!-- 序号 -->
					<spring:message code="display.mutual.no"/>
				</th>
				<th width="8%" style="text-align:center"><!-- 日期 -->
					<spring:message code="ar.viewardetail.title.date"/>
				</th>
				<th width="10%" style="text-align:center"><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>
				</th>
				<th width="19%" style="text-align:center"><!-- 部门 -->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="8%" style="text-align:center"><!-- 班次 -->
					<spring:message code="ar.viewCompanyCalendar.title.banci"/>
				</th>
				<th width="10%" style="text-align:center"><!-- 状态 -->
					<spring:message code="ar.viewcycle.title.zhuangtai"/>
				</th>
				<th width="10%" style="text-align:center">
				   周末/节假日/平日
				</th>
				<th width="24%" style="text-align:center"><!-- 时间段 -->
					<spring:message code="ar.viewardetail.title.dateduan"/>
				</th>
				<th width="4%" style="text-align:center"><!-- 长度 -->
					<spring:message code="ess.viewApply.title.length"/>
				</th>
				<th width="5%" style="text-align:center"><!-- 锁定 -->
					<spring:message code="ar.viewardetail.title.lock"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arDetailList}" var="list" varStatus="i">
				<tr>
					<td style="text-align:center">${i.index+1 }</td>
					<td style="text-align:center">${list.AR_DATE_STR}</td>
					<td style="text-align:left">(${list.EMPID})${list.LOCAL_NAME}</td>
					<td style="text-align:left">${list.DEPTNAME}</td>
					<td style="text-align:center">${list.SHIFT_NAME}</td>
					
					<td style="text-align:center">${list.ITEM_NAME}</td>
					<td style="text-align:center"> 
						<c:if test="${list.ITEM_TO_NAME eq '公休'}">周末		 
 					    </c:if>  
  					    <c:if test="${list.ITEM_TO_NAME ne '公休'}">${list.ITEM_TO_NAME}	
 					    </c:if>  
					  </td>
					<!--20150204 zhangyinghui start-->
							<td style="text-align:center">${list.FROMTIME}~~${list.TOTIME}
<!-- 								<c:if test="${cpny_id eq 'TSTO'}"> -->
<!-- 									${list.YFROMTIME}&nbsp;${list.DFROMTIME}~~${list.YTOTIME}&nbsp;${list.DTOTIME} -->
<!-- 								</c:if> -->
<!-- 								<c:if test="${cpny_id ne 'TSTO'}"> -->
									
<!-- 								</c:if> -->
							</td>
					<!--20150204 zhangyinghui end  -->
					<td style="text-align:center">${list.QUANTITY}</td>
					<td style="text-align:center">
						<c:if test="${list.LOCK_YN eq '' || list.LOCK_YN eq null || list.LOCK_YN eq 'Y'}">
							<spring:message code="ar.viewardetail.title.lock"/><!-- 锁定 -->
						</c:if>
						<c:if test="${list.LOCK_YN eq 'N'}"><!-- 开放 -->
							<spring:message code="ar.viewardetail.title.release"/>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArDetailEssList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>