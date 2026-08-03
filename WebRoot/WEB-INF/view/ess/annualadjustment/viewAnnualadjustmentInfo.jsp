<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
	function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_JUECAI_ZHUANGTAI=$("#seach_JUECAI_ZHUANGTAI",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_JUECAI_ZHUANGTAI",navTab.getCurrentPanel()).val();
	var seach_QUEREN_ZHUANGTAI=$("#seach_QUEREN_ZHUANGTAI",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_QUEREN_ZHUANGTAI",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/annualadjustment/viewAnnualadjustmentInfo?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_JUECAI_ZHUANGTAI="+seach_JUECAI_ZHUANGTAI+"&seach_QUEREN_ZHUANGTAI="+seach_QUEREN_ZHUANGTAI);
}


function delAnnApplyCallback(form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	
		//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
		return false;
	}
	    $form.attr("action","/ess/annualadjustment/delAnnuApplyInBatch?APPLY_TYPE=216691");
	    if (confirm ("确定要删除吗?")){	 
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("viewAnnualadjustmentInfo");
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
</script>

<div class="pageHeader">
	<form id="viewAnnualadjustmentInfopageForm" onsubmit="return navTabSearch(this);"  action="/ess/annualadjustment/viewAnnualadjustmentInfo" rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
					<c:if test="${authority ne '1'}">
					<td>
						部门
					</td>
					<td>
						${personInfo.DEPARTMENT }
					</td>
					<td>社号/姓名</td>
					<td>
						${personInfo.EMPID }/${personInfo.LOCAL_NAME }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${personInfo.PERSON_ID }"/>
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
						<td><!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" /> 
						</td>
						<td>
							<ait:deptList name="seach_DEPT_NO" limit="ar" id="viewAnnualadjustmentInfo_seachDept"/>
							<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewAnnualadjustmentInfo_seachDept" selected="${DEPT_NO}"/>
						</td>
						<td><!-- 社号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
						</td>
						<td> 
						<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${LoginUser.empID}" />
						</c:if>
						 <input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
						</td>
				    </c:if>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess0303_limit" name="limit" value="ar">
						<input type="hidden" id="ess0303_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess0303_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess0303_seach_JobTypeGroupNo,ess0303_seach_EmpTypeCodeNo,ess0303_seach_CPNY,ess0303_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess0303_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
			</tr>
			<tr>	
				<td><!-- 开始日期 -->
				     <spring:message code="public.title.startDate"/>
				</td>
				<td>
					<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${sDate}"/>
					<a class="inputDateButton"><!-- 选择 -->
				      	<spring:message code="public.title.choose"/>
					</a>
				</td>
				<td><!-- 结束日期 -->
				     <spring:message code="public.title.endDate"/>
				</td>				               			
				<td>
					<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${eDate}"/>
		           	<a class="inputDateButton"><!-- 选择 -->
                    	<spring:message code="public.title.choose"/>
					</a>
				</td>
				<td><!-- 审批状态 -->
						 <spring:message code="ess.viewApply.title.affirmStatus"/>
					</td>					
					<td>
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
						 </select>
					</td>		
			     <td>在职状态</td>
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
				    <button type="submit">
				       <spring:message code="public.title.search"/><!-- 检索 -->
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
		<c:if test="${toolbarInfo.INSERTR == '1'}">
				<li>				    
				    <a class="add" href="/ess/annualadjustment/viewAnnualadjustmentApply?APPLY_TYPE_NO=216691&&PERSON_ID=${personInfo.PERSON_ID}" 
				       target="dialog"  width="1100" height="600" mask="true"  rel="ess0311">
				    <span><spring:message code="ess.infoApply.title.apply"/><!-- 申请 --></span>
				    </a>
			    </li>
		</c:if>
		<c:if test="${toolbarInfo.DELETER == '1'}">
			    <li>
					<a class="delete" onclick="delAnnApplyCallback('delAnnApplyAffirmForm',DWZ.ajaxDone)" href="#" ><span>删除</span></a>					
			   </li>
		</c:if>
			</ul>
		</div>
<form name="delAnnApplyAffirmForm" id="delAnnApplyAffirmForm" method="post" action="/ess/annualadjustment/delAnnuApplyInBatch?APPLY_TYPE=216691" 
	  onsubmit="return delAnnApplyCallback(this, navTabAjaxDone);"> 
	<table class="table" width="100%" height="80%" layoutH="235" nowrapTD="false">
		<thead>
			<tr>
			    <th width="30"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="60"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="60"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="100"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="100">调整天数</th>				
				<th width="100">申请事由</th>		
				<th width="80">申请时间</th>		
				<th width="80">附件查看</th>						
				<th width="80">审批情况</th>	
				<th width="80">审批查看</th>				
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${annualadjustmentInfoList}" var="annulist" varStatus="i">			
				 <tr target="sid" rel="${annulist.PERSON_ID}">
				 	<td style="text-align: center"> 
				 	  <c:if test="${annulist.AFFIRM_FLAG eq '0' || annulist.AFFIRM_FLAG eq '-1'}">
				        <input type="checkbox" id="c1" name="c1" value="${annulist.APPLY_NO}" />
				      </c:if>
				    </td>
				    <td style="text-align: center" >${annulist.EMPID}</td>
					<td style="text-align: center">${annulist.LOCAL_NAME}</td>
					<td style="text-align: center">${annulist.DEPT_NAME}</td>
					<td style="text-align: center">${annulist.APPLY_TANSHU}</td>
					<td style="text-align: center">${annulist.ANNUAL_LEAVE_REASON}</td>
					<td style="text-align: center" > ${annulist.APPLY_DATE}</td>
						<td style="text-align: center">
							<c:forEach items="${annulist.fileList}" var="file" varStatus="j">	
								<div style="display:block;line-height:30px;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a></div>
							</c:forEach>
						</td>
					<td style="text-align: center">
						<c:if test="${annulist.AFFIRM_FLAG==-1}">
						    <font color="grey">未提交</font>
						</c:if>
						<c:if test="${annulist.AFFIRM_FLAG==0}" >
						    <font color="blue">未审批</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==3}" >
						    <font color="back">已取消</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==4}" >
						    <font color="green">审批中</font>
						</c:if>
					</td>
					<td style="text-align: center" ><a rel="leaveApplyAffirm" href="/ess/annualadjustment/viewFullAnnuAffirmInfo?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${annulist.APPLY_NO}" title="审批详情"
					          target="dialog" mask="true" width="950" height="450" id="annApplyRemarkHref" >查看</a>
					</td>
				 </tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
    <c:set value="/ess/annualadjustment/viewAnnualadjustmentInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>