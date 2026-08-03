<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
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


	var   adust_yn= document.getElementById("ADJUST_YN_CANCEL").value();
	if(adust_yn==1){
		alert("调休加班不可取消");
		return  false;
     }
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

function cancelLOvertimeApply(apply_no){

	var   adust_yn= document.getElementById("ADJUST_YN_CANCEL").value;
 
	if(adust_yn==1){
		alert("调休加班不可取消");
		return  false;
     }
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/cancelOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewLOtApplyInfoList);
			}else{
				alert(" 本月考勤已锁定或此加班已明细中锁定，取消失败！");
			}
		  }
		});
	}
}

function cancelLOvertimeApply_batch(apply_no,flag){
	var   adust_yn= document.getElementById("ADJUST_YN_CANCEL").value;
	if(adust_yn==1){
		alert("调休加班不可取消");
		return  false;
     }
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{name:'FLAG',
		  value : flag});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/cancelOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewLOtApplyInfoList);
			}else{
				alert(" 本月考勤已锁定或此加班已明细中锁定，取消失败！");
			}
		  }
		});
	}
}

<%--
function CheckFormEatMealCount(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doEatMealCountExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceSettings/viewArEatCountInfoListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expEatMealCount(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewLOtApplyInfoList");
  	if(CheckFormEatMealCount($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doEatMealCountExport($form);}});
    } 
}
--%>
//-->
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewLOtApplyInfoList" rel="pagerForm" method="post"
		id="viewLOtApplyInfoList" name="viewLOtApplyInfoList">
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
					<td><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" limit="ar" id="viewEmpInfoListlotapply_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewEmpInfoListlotapply_seachDept" selected="${DEPT_NO}"/>
					</td>	
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					</c:if>
					
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess0236_limit" name="limit" value="ar">
						<input type="hidden" id="ess0236_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess0236_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess0236_seach_JobTypeGroupNo,ess0236_seach_EmpTypeCodeNo,ess0236_seach_CPNY,ess0236_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess0236_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
						
				</tr>
				<tr>
					<%-- <td><!-- 考勤月 -->
						年/月
					</td>
					<td>
	    				<ait:date yearName="seach_arYear" yearSelected="${arYear}" monthName="seach_arMonth" monthSelected="${arMonth}"/>
					</td> --%>
					 <td><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
	                </td>			
				    <td>
				    <input type="hidden" id="seach_arYear" name="seach_arYear" value="DEFAULT"></input>
				    <input type="hidden" id="seach_arMonth" name="seach_arYear" value="DEFAULT"></input>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td> 
					<td><!-- 加班类型 -->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>
					</td>
					<td>
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
					</td>
					<td><!-- 决裁状态 -->
						审批状态
					</td>					
					<td>
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">请选择</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>> 暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>> 提交</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>><spring:message code="ess.affirmApply.title.remark.shenpizhong"/></option>
						 </select>
					</td>
						
				</tr>
				<tr>
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
			<li>
				<a class="add" href="/ess/infoApply/viewLOtApplyInfo?APPLY_TYPE_NO=31" 
					target="navTab"><span>申请加班</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="delLOtApplyCallback('delLOvertimeApplyAffirmForm',DWZ.ajaxDone)"><span>删除</span></a>
			</li>
		</ul>
	</div> 
	<form name="delLOvertimeApplyAffirmForm" id="delLOvertimeApplyAffirmForm" method="post" action="/ess/infoApply/delLOvertimeApplyInBatch" 
	  onsubmit="return delLOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
					<th>
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th><!--批次号 -->
						批次号
					</th>
				   	<th><!-- 社号 -->
						社号
					</th>
				    <th><!-- 姓名 -->
						姓名
					</th>
					<th><!-- 部门 -->
						部门
					</th>
					<th><!--申请日期-->
						加班日期
					</th>
					<th><!--申请时长-->
						申请时长
					</th>
					<th><!--加班类型-->
						加班类型
					</th>
					
					<th><!--加班事由-->
						加班事由
					</th>
					<th><!--详细查看-->
						审批查看
					</th>
					<th><!--附件查看-->
						附件查看
					</th>
					<th><!--决裁状态-->
						审批状态
					</th>
					<th  style="text-align: center"><!--是否取消-->
						是否批量
					</th>
					<th><!--是否取消-->
						是否取消
					</th><!--
					<th width="60" style="text-align: center">是否删除
						删除
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">
					    	<c:if test="${otApply.AFFIRM_FLAG eq '-1' || otApply.AFFIRM_FLAG eq '0'}">
					        	<input type="checkbox" id="c1" name="c1" value="${otApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">
							${otApply.BATCH_APPLY_NO_XIANSHI}
					    </td>
					    <td style="text-align: center">
					   
					    	<c:if test="${otApply.AFFIRM_FLAG eq '-1' &&  otApply.CREATED_BY eq admin.personId }">
					    		<a class="update" href="/ess/affirmApply/viewEditLOtApplyInfo?seach_APPLY_TYPE_WQ=31&APPLY_NO=${otApply.APPLY_NO}" title="修改加班"
									target="navTab"><font color="blue">${otApply.EMPID}</font></a>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG ne '-1' || otApply.CREATED_BY ne admin.personId }">
								${otApply.EMPID}
							</c:if>
					    </td>
					    <td style="text-align: center">${otApply.LOCAL_NAME}</td>
					    <td style="text-align: center">${otApply.DEPARTMENT}</td>
					    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>
					    	
						<td style="text-align: center">${otApply.OT_APPLY_HOUR}时${otApply.OT_APPLY_MINUTE}分</td>
						<td style="text-align: center">${otApply.OT_TYPE_NAME}
						<input type="hidden" id="ADJUST_YN_CANCEL" name="ADJUST_YN_CANCEL" value="${otApply.ADJUST_YN}"/>
						</td>
						
						<td style="text-align: center">
							<a rel="otApplyRemark" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_TYPE_WQ=31&seach_APPLY_NO=${otApply.APPLY_NO}" title="加班事由"
					          target="dialog" mask="true" width="300" height="300" id="otApplyRemarkHref" >${otApply.APPLY_OT_REMARK}...</a>
						</td>
						<td style="text-align: center">
							<a rel="otApplyAffirm" href="/ess/infoApply/viewFullApplyAffirmInfo?seach_APPLY_TYPE_WQ=31&seach_APPLY_NO=${otApply.APPLY_NO}" title="决裁详情"
					          target="dialog" mask="true" width="950" height="450" id="otApplyRemarkHref" >查看</a>
						</td>
						<td style="text-align: center">
							<c:forEach items="${otApply.fileList}" var="file" varStatus="j">	
								<div style="display:block;line-height:30px;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a></div>
							</c:forEach>
						</td>
						<td style="text-align: center">
							<c:if test="${otApply.AFFIRM_FLAG eq '-1'}">
								暂存
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '0'}">
							 提交
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '1' && otApply.ACTIVITY ne '3' }">
								 通过
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '2'}">
								 否决
							</c:if>
						 
					 <c:if test="${ (otApply.ACTIVITY eq '3' && otApply.APPLY_TYPE ne 'PERSON' ) || otApply.AFFIRM_FLAG eq '3'}">
								 取消
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '4'}">
								 审批中
							</c:if>
						</td>	
						<td style="text-align: center">
								<c:if test="${otApply.APPLY_TYPE eq 'PERSON'}">
									个人
								</c:if>
								<c:if test="${otApply.APPLY_TYPE ne 'PERSON'}">
									批量
								</c:if>
							</td>
						<td style="text-align: center">
						 
							
							<c:if test="${otApply.AFFIRM_FLAG eq '1' && otApply.APPLY_TYPE eq 'PERSON'}">
								 <a href="#" title="取消" onclick="cancelLOvertimeApply('${otApply.APPLY_NO}')" style="cursor: hand">
									<font color="red">取消</font>
								</a>
							</c:if>
							
							<c:if test="${otApply.ACTIVITY eq '3' }">
								 已取消 
							 
							</c:if>
							
							<c:if test="${otApply.ACTIVITY ne '3' && otApply.AFFIRM_FLAG eq '1' && otApply.APPLY_TYPE ne 'PERSON'}">
								 <a href="#" title="取消" onclick="cancelLOvertimeApply_batch('${otApply.BATCH_APPLY_NO}','BATCH')" style="cursor: hand">
									<font color="red">取消</font>
								</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
    <div id="otApplyRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <c:set value="/ess/infoApply/viewLOtApplyInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>