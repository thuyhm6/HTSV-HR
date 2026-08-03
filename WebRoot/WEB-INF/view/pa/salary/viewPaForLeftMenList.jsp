<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	function pageFromSea(a){
		var seach_PERSON_ID=$("#seach_PERSON_ID",navTab.getCurrentPanel()).val()==undefined?
				"":$("#seach_PERSON_ID",navTab.getCurrentPanel()).val();
		var seach_AR_MONTH=$("#seach_AR_MONTH",navTab.getCurrentPanel()).val()==undefined?
				"":$("#seach_AR_MONTH",navTab.getCurrentPanel()).val();
		var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?
				"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
		var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?
				"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
		
		$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApply/viewLOtApplyInfoList?seach_PERSON_ID="+seach_PERSON_ID
				+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
	}
	function delLOtApplyCallback(form,callback) {
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 		$form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
	    var checked=false;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
			return false;
		}
	    $form.attr("action","/ess/infoApply/delLOvertimeApplyInBatch");
	    if (confirm ("确定要批量删除吗?")){	  
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("viewLOtApplyInfoList");
						alertMsg.correct(data.message);
					}else{
						if(data.result=="2"){
							alertMsg.info(data.message);
						}else{
							alertMsg.error(data.message);
						}
					}   
		   	 	}  ,
				error: DWZ.ajaxError
			});
	    }
		return false;
	}

	function delLOvertimeApply(apply_no){
		var params = [];
		params.push({
			name: 'APPLY_NO',
			value: apply_no
		});
		if (confirm ("确定要删除吗?")){	  
			$.ajax({
			  url: '/ess/infoApply/delOvertimeApply',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.viewLOtApplyInfoList);
				}else{
					alert("删除失败！");
				}
			  }
			});
		}
	}
</script>

<div class="pageHeader">
	<form name="viewPaForLeftMenList" id="viewPaForLeftMenList" action="/pa/salary/viewPaForLeftMenList" onsubmit="return navTabSearch(this);" 
		method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent" border="0" width="100%">
				<tr>
					<td><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>:
						<input name="seach_condition" type="text" value="${seach_condition}" id="seach_condition"/>
					</td>
					<td><!--支付月-->
				 		<spring:message code="pa.insurance.title.salaryMonthFor"/>
						<ait:date yearName="paYear" monthName="paMonth1" yearSelected="${paYear}" monthSelected="${paMonth1}"/>
			    	</td>
					<td>
						补发项目:${add_flag_return}
						<c:if  test="${add_flag_return  eq 'Y'}">
							add2
						</c:if>
						<select name="seach_itemNo" class="select">
							<option value=""><!-- 全部 -->
								<spring:message code="ar.viewarcardrecord.title.quanbu"/>
							</option>
							<option value="1" 
								<c:if test="${seach_itemNo eq '1'}">selected</c:if>>
								工资项目
							</option>
							<option value="2" 
								<c:if test="${seach_itemNo eq '2'}">selected</c:if>>
								保险项目
							</option>
						</select>
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

<div class="pageContent" >
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="/pa/salary/viewAddPaInfoForEmpLeftList?pageNum=1&numPerPage=10" target="navTab"><span>工资补发申请</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="buttonActive" onclick="delLOtApplyCallback('delLOvertimeApplyAffirmForm',DWZ.ajaxDone)"><span>删除</span></a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>                         
	<form name="updateArDetailForm" id="updateArDetailForm" method="post" action="/ar/attendanceMintenance/updateOrAddArDetail"
 		 onsubmit="return delLOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="206">
			<thead>
				<tr>
					<th width="13%" align="center"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>
					</th>
					<th width="24%" align="center"><!-- 部门 -->
						<spring:message code="public.title.deptName"/>
					</th>
					<th width="7%" align="center"><!--支付月-->
						<spring:message code="pa.insurance.title.salaryMonthFor"/>
					</th>
					<th width="6%" align="center"><!--补发项目-->
						<spring:message code="pa.insurance.title.salaryAdd"/>
					</th>
					<th width="15%" align="center"><!--补发项目明细-->
						<spring:message code="pa.insurance.title.salaryAddItem"/>
					</th>
					<th width="10%" align="center"><!--补发金额-->
						<spring:message code="pa.insurance.title.salaryAddData"/>
					</th>
					<th width="7%" align="center"><!--发放月份-->
						<spring:message code="pa.insurance.title.salaryMonthNow"/>
					</th>
					<th width="18%" align="center"><!-- 备注 -->
						<spring:message code="hr.viewBadArchives.title.REMARK"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paForLeftDetailList}" var="list" varStatus="i">
					<tr target="DATA_NO" rel="${list.APPLY_NO}">
						<td style="text-align:center" width="13%">
							(${list.EMPID})${list.LOCAL_NAME}
						</td>
						<td style="text-align:left" width="24%">
							${list.DEPARTMENT}
						</td>
						<td style="text-align:center" width="7%">
							${list.PA_MONTH_FOR}
						</td>
						<td style="text-align:center" width="6%">
							<c:if test="${list.ITEM_TYPE eq 'PA'}">
								薪资
							</c:if>
							<c:if test="${list.ITEM_TYPE eq 'IS'}">
								保险
							</c:if>
						</td>
						<td style="text-align:center" width="15%">
							${list.ITEM_NAME}
						</td>
						<td style="text-align:right" width="10%">
							${list.ITEM_DATA}
						</td>
						<td style="text-align:center" width="7%">
							${list.PA_MONTH}
						</td>
						<td style="text-align:center" width="18%">
							${list.REMARK}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/pa/salary/viewPaForLeftMenList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>