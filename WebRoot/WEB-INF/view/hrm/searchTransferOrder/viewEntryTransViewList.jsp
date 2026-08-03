<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//LGECH用（审批线）
function addTempEmpInfo(){
	$("#hr0504Link").html("临时职人员入职录入");
	$("#dialog_hr0504").attr('href', '/hrm/transferOrder/viewHire');
	$("#dialog_hr0504").attr('height', "600");
	$("#dialog_hr0504").attr('width', "1000");
	$("#dialog_hr0504").click();
}
function cancelEntryTransValidateCallback(form, callback,flag) {
	
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
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}

    $form.attr("action","/hrm/searchTransferOrder/cancelTransactionTransInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		//success: callback || DWZ.ajaxDone,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchEntryTransForm");
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
	
	return false;
}

function viewTempEmpReq(){
	var params   = $("#tmpEmpListForm").serialize();
	$("#hr0504Link").html("临时职人员入职申请");
	$("#dialog_hr0504").attr('href', "/hrm/transferOrder/viewConfirmReqHire?" + params);
	$("#dialog_hr0504").attr('height', "600");
	$("#dialog_hr0504").attr('width', "1000");
	$("#dialog_hr0504").click();
}

function reqTempEmpInfo(){
	alertMsg.confirm("确定要提交申请吗?", {
        okCall: function(){
		var params   = $("#tmpEmpListForm").serialize();

	    var checked=false;
		var ids= document.getElementsByName("hr0504Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				break;
			}
		}
		if(checked){
			viewTempEmpReq();
		}else{
			alertMsg.error('请先选择要申请审批的记录');
		}
		return;
        }
    });
}

function delTempEmpInfo(){
	alertMsg.confirm("确定要删除吗?", {
        okCall: function(){
		var params   = $("#tmpEmpListForm").serialize();

	    var checked=false;
		var ids= document.getElementsByName("hr0504Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				break;
			}
		}
		if(!checked){
			alertMsg.error('请先选择要删除的记录');
			return;
		}
		
		$.ajax( {
			type : 'post',
			cache : false,
			url : "/hrm/transferOrder/delTempEmpInfo?" + params,
					success : function(data) {
						if (data.statusCode==200){
							alert(data.message);
							navTabSearch($("#searchEntryTransForm"));
						}else{
							alert(data.message);
						}
					}
		});
        }
    });
}
</script>
<a id="dialog_hr0504" href="#" width="400" height="200" target="dialog" mask="true"><span
		id="hr0504Link" style="display: none"></span></a> 
<a id="importExcel_hr0504" href="#" target="dialog" width="800" height="420" mask="true"><span
	style="display: none;">临时职批量入职导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/viewEntryTransViewList" 
	method="post" id="searchEntryTransForm" name="searchEntryTransForm" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>法人
				</td>
				<td>				
					<input type="text" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${searchMap.CPNY_ID}" readonly/>
			    </td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.interCpnyID}" limit="super" id="viewResign_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.interCpnyID}" limit="super" id="viewResign_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.interCpnyID}" limit="hr" id="viewResign_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.interCpnyID}" limit="hr" id="viewResign_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input id="seach_EMPID" name="dwz.person.empId" type="text" value="${searchMap.EMPID}" lookupGroup="person"/>
					<input id="seach_PERSON_ID" name="dwz.person.personId" type="hidden" value="" readOnly lookupGroup="person"/>
					<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
				</td>
			</tr>
			<tr>
                <%-- td><spring:message code="sys.affirm.title.duty" />：</td>
				<td><ait:ComboJobPositionByCpnyIDTag id="seach_JOB_POSI_CD"
						name="seach_JOB_POSI_CD" parentNo="215918"
						selected="${JOB_POSI_CD}" cnpyID="${searchMap.CPNY_ID}" limit="all" />
				</td> --%>
				<td>
                                                 入职日期：
                </td>			
			    <td>
			        <input type="text" name="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${searchMap.FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
					~
                </td>                			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${searchMap.TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 	
				<!-- 	决裁状态:
				<td>
					<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
				</td>					
				<td>
                	<ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${searchMap.interCpnyID}" selected="${ACTIVITY}" limit="all"/>
                </td>	 
			</tr>
			<tr>-->
				<td><!-- 人员类型： --> <spring:message
					code="hr.enpinfo.title.EMP.TYPE" /> 
				</td>
				<td>
				   <select name="seach_EMP_TYPE_CODE"><option value="">请选择</option>
				     <c:forEach items="${empTypeList}" var="position">
				       <option value="${position.TEMP_EMPTYPE}" <c:if test="${position.TEMP_EMPTYPE eq EMP_TYPE_CODE}">selected</c:if>>${position.EMPTYPE_NAME}
				     </c:forEach>
				   </select>
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
<form name="tmpEmpListForm" 
	id="tmpEmpListForm" 
	method="post" action="/hrm/searchTransferOrder/cancelEntryTransInBatch"
	onsubmit="return cancelEntryTransValidateCallback(this, navTabAjaxDone);">
	  
	<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							 <button type="button" onclick="addTempEmpInfo()">
									临时职入职录入
							 </button>
						</div>
					</div>
				</li><%--
				<li><div class="buttonActive">
						<div class="buttonContent">
							<c:if test="${toolbarInfo.UPDATER == '1'}">
								<a class="downloadExel" href="/hrm/transferOrder/downloadResignationTemplate">						
								<span>
									<spring:message code="inct.salesman.downloadExcelTemplate" />
								</span>
								</a>
							</c:if>
						</div>
					</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<button type="button" onclick="importExcelTempEmpResign()">
								<spring:message code="inct.salesman.uploadExcel" />
								<!--上传excel-->
							</button>
						</c:if>
					</div>
				</div></li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="editTempEmpInfo()">
								修改
							</button>
						</div>
					</div>
				</li> --%>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="reqTempEmpInfo()">
								申请
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="delTempEmpInfo()">
								删除
							</button>
						</div>
					</div>
				</li>
				</ul>
	</div>	  
 	<table class="table" width="100%" layoutH="260" nowrapTD="false">      
		<thead>
			<tr>
				<th width="20"><input type="checkbox" class="checkboxCtrl" group="hr0504Check" /></th>
				<th width="50" orderField="HE.EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="60" orderField="nlssort(HE.LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="160" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th><%-- 
				<th width="80" orderField="nlssort(POSITION_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th width="120" orderField="nlssort(POST_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
			    <th width="40" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			    	<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
				</th> --%>
				<th width="80" orderField="nlssort(EMP_TYPE_NM,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					人员类型
				</th>
			   <%--  <th width="100" orderField="JOIN_COMPANY_DATE" class="${orderDirection}">
			    	<spring:message code="hr.viewPersonalInfo.title.JOIN_COMPANY_DATE"/>
					<!--子公司入司日期  -->
			    </th>	 --%>
			    <th width="60" orderField="JOIN_COMPANY_DATE" class="${orderDirection}">
			    	<spring:message code="hr.viewContractInfoForSearch.title.JOINDATE"/>
			    	<!--入职日期-->
			    </th><%-- 
				<th width="140" orderField="nlssort(JOIN_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.JOIN_TYPE_NAME"/>
					<!--入职类型-->
				</th>	
				<th width="140" orderField="BEFORE_END_PROBATION_DATE" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE"/>
					<!--预转正日期-->
				</th>	
				<th width="100">
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<决裁情况
				<th width="160">
					<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
				</th>
				<th width="80">
					<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
					<!--操作者-->
				</th>	
				<th width="80">
					<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
					<!--是否生效-->
				</th> --%>
				<th width="30" orderField="SEXCODE" class="${orderDirection}">
					性别
				</th>
				<th width="160" orderField="nlssort(WORK_AREA_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME"/>
					<!--工作地-->
				</th>
				<th width="30" orderField="AFFIRM_FLAG" class="${orderDirection}">
					状态
				</th>
				<th width="50">申请人</th>
				<th width="50">申请时间</th>
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrExperienceInsideList}" var="hrExpInside" varStatus="i">			
				<tr target="sid" rel="${hrExpInside.EXP_INSIDE_NO}">
					<td class="td_center" ><c:if test="${hrExpInside.EMPID eq null and hrExpInside.SUBMIT_STATUS eq 0}"><input type="checkbox" name="hr0504Check" value="${hrExpInside.PERSON_ID}"/></c:if></td>
					<td class='td_center'>${hrExpInside.EMPID}</td>
					<td class='td_center'>
					    <a rel="leaveApplyAffirm" href="/hrm/empinfo/viewTempEmpReqDetail?PERSON_ID=${hrExpInside.PERSON_ID}&APPLY_NO=${hrExpInside.REQ_ID}" title="申请详情"
					          target="navTab" rel="viewTempEmpBatchAffirmInfo">${hrExpInside.LOCAL_NAME}</a>
					</td>
					<td>${hrExpInside.DEPT_NAME}<input type="hidden" name="hr0504Dept" value="${hrExpInside.DEPT_NO}"/></td><%--
					<td>${hrExpInside.POSITION_NAME}</td>
					<td>${hrExpInside.POST_NAME}</td>
					<td>${hrExpInside.POST_GRADE_NAME}</td> --%>
					<td>${hrExpInside.EMP_TYPE_NM}</td>
					<td class='td_center'>${hrExpInside.JOIN_COMPANY_DATE}</td><%--
					<td>${hrExpInside.JOIN_TYPE_NAME}</td>
					<td>${hrExpInside.BEFORE_END_PROBATION_DATE}</td>
				    <td style="text-align: center">					
                        <a href="/hrm/searchTransferOrder/viewEntryTrans?EXP_INSIDE_NO=${hrExpInside.EXP_INSIDE_NO}&&PERSON_ID=${hrExpInside.PERSON_ID}" target="navTab">
                            <span><spring:message code="hr.viewEntryTransViewList.title.SELECT_INFORMATION"/><!-- 信息查看 --></span>
						</a>	
					</td> --%>
					<!-- 决裁者
					<td style="text-align: center">
					    <c:forEach items="${hrExpInside.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >
						         	<font color="green">${affirmer.LOCAL_NAME}		                            
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PASS"/>
									</font>			                                
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                     	<font color="red">${affirmer.LOCAL_NAME}
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/>
			                        </font> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >
			                     	<font color="blue">${affirmer.LOCAL_NAME}
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PENDING_CUTTION"/>
			                        </font>
								 </c:if>								 
							</dt>
						</c:forEach>	
					</td>	
					-->				
					<%--td style="text-align: center">${hrExpInside.CREATED_BY_NAME}</td>														                                          
			        <td style="text-align: center">
                        <c:if test="${hrExpInside.ACTIVITY_FLAG== 0 }" >
                         <img src="/resources/images/a_1.gif" style="cursor:hand"/>
                       标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消 >
                                <img src="/resources/images/0.gif" title="<spring:message code='alert.message.approval_status_ing'/>" />
                        </c:if>
                        <c:if test="${hrExpInside.ACTIVITY_FLAG== 1 }" >
                         <img src="/resources/images/1.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_end'/>" />
                        </c:if>
                        <c:if test="${hrExpInside.ACTIVITY_FLAG== 2}" >
                            <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_reject'/>" />
                        </c:if> 
                        <c:if test="${hrExpInside.ACTIVITY_FLAG== 3}" >
                            <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_cancel'/>" />
                        </c:if>                        			            
			        </td> --%>
					<td class='td_center'>${hrExpInside.SEXCODE}</td>
					<td>${hrExpInside.WORK_AREA_NAME}</td>
					<td class='td_center'>
						<c:if test="${hrExpInside.AFFIRM_FLAG eq '-1'}" >
						    <font color="blue">提交</font>
						</c:if>
						<c:if test="${hrExpInside.AFFIRM_FLAG eq '4'}" >
						    <font color="green">审批中</font>
						</c:if>
						<c:if test="${hrExpInside.AFFIRM_FLAG eq '1'}" >
						    <font color="green">通过</font>
						</c:if>	
						<c:if test="${hrExpInside.AFFIRM_FLAG eq '2'}" >
						    <font color="red">否决</font>
						</c:if>
						<c:if test="${hrExpInside.AFFIRM_FLAG eq null}" >
						    <font color="blue">暂存</font>
						</c:if>
					</td>
					<td class="td_center" >${hrExpInside.REQ_BY}</td>
					<td class="td_center" >${hrExpInside.REQ_DATE}</td>
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>	
</form>
<c:set value="/hrm/searchTransferOrder/viewEntryTransViewList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>