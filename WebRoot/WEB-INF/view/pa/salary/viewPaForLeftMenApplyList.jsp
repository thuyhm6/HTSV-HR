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
	function delPaForLeftApplyCallback(form,callback) {
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
	    $form.attr("action","/pa/salary/delPaForLeftApplyInBatch");
	    if (confirm ("确定要批量删除吗?")){	  
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("viewPaForLeftMenApplyList");
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

	function delPaForLeftApply(apply_no){
		var params = [];
		params.push({
			name: 'APPLY_NO',
			value: apply_no
		});
		if (confirm ("确定要删除吗?")){	  
			$.ajax({
			  url: '/pa/salary/delPaForLeftApply',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.viewPaForLeftMenApplyList);
				}else{
					alert("删除失败！");
				}
			  }
			});
		}
	}
	$(document).ready(function(){
		if($("#pa0708_seach_JobTypeGroupNo").val() != ''){
			var EMP_TYPE = $("#pa0708_seach_EmpTypeCodeNo").val();
			ajaxEmpTypeForGroupToList(EMP_TYPE,"pa0708_seach_JobTypeGroupNo","pa0708_seach_EmpTypeCodeNo",
					"pa0708_seach_CPNY","pa0708_limit");
			//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
		}
	});
</script>

<div class="pageHeader">
	<form name="viewPaForLeftMenApplyList" id="viewPaForLeftMenApplyList" action="/pa/salary/viewPaForLeftMenApplyList" onsubmit="return navTabSearch(this);" 
		method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent" border="0" width="100%">
				<tr>
					<td width="6%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>
					</td> 
					<td width="10%">						
						<input id="seach_EMP_KEY" name="seach_EMP_KEY" type="text" value="${EMP_KEY}"/>
					</td>
					<td width="6%"><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
					</td> 
					<td>	                    
				        <input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td width="6%"><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
					</td> 
					<td>	                    
					    <input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td>
					<td width="6%"><!-- 决裁状态 -->
						审批状态
					</td> 						
					<td width="10%">
					    <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="">全部</option>	 
							<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未审批</option>
							<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							<option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						</select>       
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
			<td>人员类型组 </td>
						<td>
						<input type="hidden" id="pa0708_limit" name="limit" value="pa">
						<input type="hidden" id="pa0708_seach_CPNY" name="seach_CPNYFYSQ" value="${defaultCpny}">
						<ait:SelectEmpTypeCode id="pa0708_seach_JobTypeGroupNo" name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
						onChangeName="ajaxEmpTypeForGroupToList(-1,pa0708_seach_JobTypeGroupNo,pa0708_seach_EmpTypeCodeNo,pa0708_seach_CPNY,pa0708_limit)"/>
						</td>
						<td>人员类型</td>
						<td>
		 					<ait:SelectEmpTypeCode id="pa0708_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
						</td>
				  		<td><!-- 在职区分： --> <spring:message
							code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /> 
						</td>
						<td>
		 					<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
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
				<a class="add" href="/pa/salary/viewAddPaInfoForEmpLeftList?pageNum=1&numPerPage=10&APPLY_TYPE_NO=224" target="navTab"><span>工资补发申请</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="buttonActive" onclick="delPaForLeftApplyCallback('delPaForLeftApplyInBatch',DWZ.ajaxDone)"><span>删除</span></a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>                         
	<form name="delPaForLeftApplyInBatch" id="delPaForLeftApplyInBatch" method="post" action="/pa/salary/delPaForLeftApplyInBatch"
 		 onsubmit="return delPaForLeftApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="206">
			<thead>
				<tr>
					<th width="5%" style="text-align:center"><!-- 序号 -->
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
					</th>
					<th width="15%" style="text-align:center"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>
					</th>
					<th width="20%" style="text-align:center"><!-- 部门 -->
						<spring:message code="public.title.deptName"/>
					</th>
					<th width="20%" style="text-align:center"><!-- 申请内容 -->
						申请内容
					</th>
					<th width="10%" style="text-align:center"><!-- 审批状态 -->
						审批状态
					</th>
					<th width="10%" style="text-align:center"><!-- 审批详细 -->
						审批详细
					</th>
					<th width="15%" style="text-align:center"><!--申请时间-->
						申请时间
					</th>
					<th width="5%" style="text-align:center"><!-- 删除 -->
						删除
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paForLeftApplyList}" var="apply" varStatus="i">
					<tr target="BATCH_NO" rel="${apply.BATCH_NO}">
						<td width="5%" style="text-align:center">
							<c:if test="${apply.AFFIRM_FLAG eq '0'}">
								<input type="checkbox" id="c1" name="c1" value="${apply.BATCH_NO }" />
							</c:if>
							<c:if test="${apply.AFFIRM_FLAG ne '0'}">
								<input type="hidden" id="c1" name="c1" value="" />
							</c:if>
						</td>
						<td width="15%" style="text-align:center">
							(${apply.EMPID }) ${apply.LOCAL_NAME }
						</td>
						<td style="text-align:left" width="20%">
							${apply.DEPART }
						</td>
						<td width="20%" style="text-align:center">
							<a rel="paForLeftRemark" href="/pa/salary/viewPaForLeftApplyContentInfo?seach_BATCH_NO=${apply.BATCH_NO}" title="详细内容"
					          target="dialog" mask="true" width="300" height="300" id="paForLeftRemarkLHref">
					        	<font color="blue">${apply.APPLY_INFO}..</font>
					         </a>
						</td>
						<td width="10%" style="text-align:center">
							<c:if test="${apply.AFFIRM_FLAG==0}" >
							    未审批
							</c:if>	
							<c:if test="${apply.AFFIRM_FLAG==1}" >
							     通过
							</c:if>	
							<c:if test="${apply.AFFIRM_FLAG==2}" >
							     否决
							</c:if>
							<c:if test="${apply.AFFIRM_FLAG==4}" >
							    审批中
							</c:if>		
						</td>
						<td width="10%" style="text-align:center">
							<a rel="paForLeftAffirmRemarkL" title="审批详情" target="dialog" mask="true" width="1250" height="500" id="paForLeftAffirmRemarkLHref" 
								href="/pa/salary/viewFullPaForLeftApplyCheckInfo?APPLY_TYPE_NO=224&BATCH_NO=${apply.BATCH_NO}">
								<font color="red">查看</font>
							</a>
						</td>					          		
						
						<td width="15%" style="text-align:center">
							${apply.CREATE_DATE }
						</td>
						<td width="5%" style="text-align:center">
							<c:if test="${apply.AFFIRM_FLAG eq '0'}">
								<img src="/resources/images/button/Delete_little.gif" onclick="delPaForLeftApply('${apply.BATCH_NO}')"
						 			style="cursor: hand" />
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/pa/salary/viewPaForLeftMenApplyList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>