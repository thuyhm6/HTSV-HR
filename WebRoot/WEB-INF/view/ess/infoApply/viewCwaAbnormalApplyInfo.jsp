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
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApply/viewCwaAbnormalApplyInfo?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_JUECAI_ZHUANGTAI="+seach_JUECAI_ZHUANGTAI+"&seach_QUEREN_ZHUANGTAI="+seach_QUEREN_ZHUANGTAI);
}

function delCwaAbnormalApplyInfoCallback(form,callback) {
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
	    $form.attr("action","/ess/infoApply/delCwaAbnormalApplyInfo?APPLY_TYPE=218197");
	    if (confirm ("确定要删除吗?")){	 
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("delCwaAbnormalApplyInfoForm");
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
	<form id="pageForm" onsubmit="return navTabSearch(this);"  action="/ess/infoApply/viewCwaAbnormalApplyInfo" rel="pagerForm" method="post" >
	<div class="searchBar">
		<table class="searchContent">
			<tr>
					<c:if test="${authority ne '1'}">
					<td>社号/姓名</td>
					<td>
						${admin.empID }/${admin.localName }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${admin.personId }"/>
					</td>
					<td>
						部门
					</td>
					<td>
						${admin.content }
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${admin.empID}" />
						</c:if>
					<input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
					</td>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
				      <ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"  limit="ar" id="viewAnnualadjustmentInfo_seachDept"/>
				      <ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"  limit="ar" id="viewAnnualadjustmentInfo_seachDept" selected="${DEPT_NO}"/>
				    </td>
				   </c:if>
				   
				<td><!-- 决裁状态 -->
						 <spring:message code="ess.viewApply.title.affirmStatus"/>
				</td>	
					<td width="10%">
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>未提交</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未决裁</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>已取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>决裁中</option>
						 </select>
					</td>
			</tr>
			<tr>
				<td><!-- 开始日期 -->
				     <spring:message code="public.title.startDate"/>
				</td>
				<td>
					<input id="seach_sDate" type="text" name="seach_sDate"   class="date required" readonly="true" value="${sDate}"  />
					<a class="inputDateButton"><!-- 选择 --> 
				      	<spring:message code="public.title.choose"/>
					</a>
				</td>
				<td><!-- 结束日期 -->
				     <spring:message code="public.title.endDate"/>
				</td>				               			
				<td>
					<input id="seach_eDate" type="text" name="seach_eDate" class="date required" readonly="true" value="${eDate}"/>
		           	<a class="inputDateButton"><!-- 选择 -->
                    	<spring:message code="public.title.choose"/>
					</a>
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
<div class="pageContent">
		<c:if test="${toolbarInfo.DELETER == '1'}">
	    	<div class="formBar">
				<ul class="toolBar">
				    <li>
						<a class="delete" onclick="delCwaAbnormalApplyInfoCallback('delCwaAbnormalApplyInfoForm',DWZ.ajaxDone)" href="#" ><span>删除</span></a>					
				   </li>
				</ul>
			</div>
		</c:if>
<form name="delCwaAbnormalApplyInfoForm" id="delCwaAbnormalApplyInfoForm" method="post" action="/ess/infoApply/delCwaAbnormalApplyInfo?APPLY_TYPE=218197" 
	  onsubmit="return delCwaAbnormalApplyInfoCallback(this, navTabAjaxDone);"> 
	<table class="table" width="100%"  height="80%" layoutH="200" nowrapTD="false">
		<thead>
			<tr>
			    <th width="5%"><input type="checkbox" class="checkboxCtrl" group="c1" /></th> 
				<th width="10%"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="10%"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="15%"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th>考勤时间</th>
				<th width="15%">打卡时间</th>
				<th width="5%">异常类型</th>
				<th width="15%">异常原因</th>				
				<th width="10%">审批查看</th>						
				<th width="10%">决裁情况</th>				
			</tr>
		</thead>
		<tbody>
		    <c:forEach items="${CwaAbnormalApplyInfoList}" var="cwalist" varStatus="i">			
					<tr target="sid" rel="${cwalist.PERSON_ID}">
					    <td style="text-align: center"> 
				 	    <c:if test="${cwalist.AFFIRM_FLAG eq '0' || cwalist.AFFIRM_FLAG eq '-1'}">
				           <input type="checkbox" id="c1" name="c1" value="${cwalist.APPLY_NO}" />
				        </c:if>
				        </td>
					    <td style="text-align: center">${cwalist.EMPID}</td>
					    <td style="text-align: center">${cwalist.LOCAL_NAME}</td>
						<td style="text-align: center">${cwalist.DEPARTMENT}</td>
						<td style="text-align: center"> ${ cwalist.AR_DATE_STR} </td>
						<td style="text-align: center">${cwalist.FROM_TIME} </BR> ${cwalist.TO_TIME} </td>
						<td style="text-align: center">${cwalist.YICHANGTYPENAME}</td>
						<td style="text-align: center">${cwalist.APPLY_REASON}</td>
						<td style="text-align: center">
						<a rel="leaveApplyAffirm" href="/ess/infoApply/viewFullApplyAffirmInfo?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${cwalist.APPLY_NO}" title="决裁详情"
					          target="dialog" mask="true" width="950" height="450" id="leaveApplyRemarkHref" >查看</a>
						</td>
						<td style="text-align: center">
							<c:if test="${cwalist.AFFIRM_FLAG eq '-1'}">
								<font color="blue">未提交</font>
							</c:if>
							<c:if test="${cwalist.AFFIRM_FLAG eq '0'}">
								<font color="back">未决裁</font>
							</c:if>
							<c:if test="${cwalist.AFFIRM_FLAG eq '1'}">
								<font color="green">已通过</font>
							</c:if>
							<c:if test="${cwalist.AFFIRM_FLAG eq '2'}">
								<font color="red">已否决</font>
							</c:if>
							<c:if test="${cwalist.AFFIRM_FLAG eq '3'}">
								<font color="grey">已取消</font>
							</c:if>
							<c:if test="${cwalist.AFFIRM_FLAG eq '4'}">
								<font color="grey">决裁中</font>
							</c:if>
						</td>	
                         
					</tr>
				</c:forEach>
		</tbody>
	</table>
	</form>
    <c:set value="/ess/infoApply/viewCwaAbnormalApplyInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>



