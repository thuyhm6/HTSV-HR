<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//离职发令审批
function submitAffirmPreEss0253(flag){
	var validFlag=true;
	$("#AFFIRM_FLAG_RESIGN_APPLY").val(flag);
  	var $from = $("#AffirmResignForm");
	var cnt = parseInt('${fn:length(infoApplyResign)}');
	if(cnt==0){
		alertMsg.error('没有要审批的内容，请确认！');
		return false;
	}
	var affirmContent= $("#RESIGN_AFFIRM_CONTENT").val();
  	if(affirmContent.length>190){
  		alert("批注内容不可以超过190字符！"); 
  		return false;
  	}
  	if(flag==1){//审批通过时检查审批人PERSONID
	  	var oldAffirmCnt = parseInt('${fn:length(affirmorList)}');
	  	var affirmTab = document.getElementById("resign_affirmor_list");
	 	var newAffirmCnt = affirmTab.rows.length-1;
	 	if(newAffirmCnt > oldAffirmCnt){
	 		for(var m=0;m<newAffirmCnt;m++){ 
	 			var newAffirmorStr = "#personId"+m;
	 			var newAffirmorId = $(newAffirmorStr).val();
	 			if (!document.getElementById("personId"+m)) {
	 				continue;
	 			}else{
	 				var affirmorStr = "dwz.person.personId"+m;
	 				var temp = document.getElementsByName(affirmorStr);
	 				if(temp.length>0){
	 					for(var i=0;i<temp.length;i++){ 						
 							if(typeof(temp[i].value) == 'undefined' || temp[i].value == ''){
 								validFlag=false;
 								break;
 							};	 									
	 					}
	 				}
	 				if(!validFlag) break;
	 			} 			
	 		} 		
	 		if(!validFlag){
	 	  		alertMsg.error("审批线未设置！");
	 	  		return false;
	 	  	}
	 	}  	
  	}
  	$from.submit();  
}
function validateCallbackAffirmResign(form,callback) {	
	var $form = $("#AffirmResignForm");	
	if (!$form.valid()) {
		return false;
	}
	var applyNo = document.getElementById("APPLY_NO").value;
	var essAffirmNo  = document.getElementById("ESS_AFFIRM_NO").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var affirmFlag = document.getElementById("AFFIRM_FLAG_RESIGN_APPLY").value;
    
	if(applyNo=="" || essAffirmNo=="" || person_id=="" || affirmFlag==""){
		alertMsg.error("信息决裁出现问题，请联系管理员!");
		return false;
	}
	var result = "确定要执行此次操作？";
	if(affirmFlag==1){
		result = "确定要通过此条申请信息？";
	}else if(affirmFlag==2){
		result = "确定要否决此条申请信息？";
	}
	validFlag = true;
	if(validFlag){		
		//确定要提交吗？
		if (confirm (result)){				
		  	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(result) {
					if (result.statusCode == 200) {
						alert("离职发令审批成功！");
						$.pdialog.closeCurrent();
						navTabSearch("searchResignAffirmListForm");
					}else{
						alert("离职发令审批失败！");
					}
				},
				error: DWZ.ajaxError
			});				
			return false;
		}
	}	
	return false ;
}

//注意input的id和tr的id要一样
function addAffirmorRow_ess0253(currentRowID){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('#resign_affirmor_list tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('#resign_affirmor_list tbody tr:eq('+i+')');
          	//要添加的行的id
          	var addRowID=i+2;
          	str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
          			+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmor(this,event)" class="required"/>'
          			+'</td>'
          			+'<td style="text-align: center">未决裁</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">'
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow_ess0253(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.resign_affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
          			//+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("resign_affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("resign_affirmor_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function submitKeyClick_affirmor(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt>1 ){
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdList?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId_sy0482='+empIdStr
									+'&personId_sy0482='+personIdStr  
									+'&empName_sy0482='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
						}
					},
			error: DWZ.ajaxError
		});
    }
 }
</script>
<div class="pageContent">
<div class="pageFormContent">
	<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align: right;width:10%;">申请人</td>
			<td style="text-align: left;width:20%;">[${essAffirmReq.REQ_EMPID}]${essAffirmReq.REQ_EMPNM}</td>
			<td class="td_title" style="text-align: center;width:10%;">申请日期</td>
			<td style="text-align: left;width:20%;">${essAffirmReq.REQ_DATE}</td>
			<td class="td_title" style="text-align: center;width:10%;">申请类型</td>
			<td style="text-align: left;width:30%;">
				<c:if test="${essAffirmReq.TRANS_CODE eq 'RESIGN'}">离职</c:if>
				<c:if test="${essAffirmReq.TRANS_CODE eq 'RESIGNREVOKE'}">离职撤销</c:if>
			</td>
		</tr>
	</table>
</div>
<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
	<table class="table" width="100%" layoutH="310" targetType="dialog" >
		<thead>
			<tr >
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%">
					人员类型
				</th>
				<th width="7%">
					职责
				</th>
				<th width="9%">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<font color="red">*</font>
					<!--离职日期-->
				</th>
				<th width="12%">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<font color="red">*</font>
					<!--离职类型-->
				</th>
				<th width="13%">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<font color="red">*</font>
					<!--离职原因-->
				</th>
				<th width="6%" >
					<spring:message code="hr.viewResign.title.BLACKYN"/>
					<!--能否再入职-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewResign.title.BLACKREMARK"/>
					<!--黑名单理由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${infoApplyResign}" var="resignApply">
				<tr target="sid" rel="${resignApply.EMPID}">
					<td class="td_center">
						<input type="hidden" name="PERSON_ID_${resignApply.EMPID}" value="${resignApply.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${resignApply.EMPID}" value="${resignApply.DEPTNO}"/>
						<input type="hidden" name="POSITION_NO_${resignApply.EMPID}" value="${resignApply.POSITION_NO}"/>
						<input type="hidden" name="POST_NO_${resignApply.EMPID}" value="${resignApply.POST_NO}"/>
						<input type="hidden" name="EXP_INSIDE_NO_${resignApply.EMPID}" value="${resignApply.EXP_INSIDE_NO}"/>
						<input type="hidden" name="REQTYPE" id="REQTYPE" value="" />
						${resignApply.EMPID}
					</td>					
					<td class='td_center'>
						${resignApply.LOCAL_NAME}
					</td>					
					<td>
						${resignApply.DEPT_NAME}
					</td>					
					<td>
						${resignApply.EMP_TYPE_NAME}
					</td>					
					<td class='td_center'>
						${resignApply.POSITION_NAME}
					</td>					
					<td class='td_center'>
						${resignApply.RESIGN_DATE}
					</td>						
					<td>
						${resignApply.RESIGN_TYPE_NAME}
					</td>
					<td>
						${resignApply.RESIGN_REASON_DESC}
					</td>					
					<td class='td_center'>	
						${resignApply.BLACKLIST_YN_DESC}
					</td>					
					<td>
						${resignApply.REMARK}
					</td>					
					<td  class='td_center'>
						${resignApply.BLACKLIST_REASON}
					</td>					
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewResignAffirmDoList?pageNum=1&REQ_ID=${searchMap.APPLY_NO}&navTabId=ess0253">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</td></tr>
<tr><td>
	<form style="margin:0px;padding:0px;" id="AffirmResignForm" name="AffirmResignForm" 
	onsubmit="return validateCallbackAffirmResign(this,navTabAjaxDone);" 
	action="/ess/affirmApply/affirmReqResign" 
	method="post" 
	class="pageForm required-validate">
	<input type="hidden" name="REQ_ID" id="REQ_ID" value="${essAffirmReq.EXP_INSIDE_NO}"/>
	<input type="hidden" name="APPLY_PERSON_ID" id="APPLY_PERSON_ID" value="${essAffirmReq.REQ_BY}" />
	<input type="hidden" name="TRANS_CODE" id="TRANS_CODE" value="${essAffirmReq.TRANS_CODE}"/>
	<input type="hidden" name="STATE" id="STATE" value="${essAffirmReq.STATE}"/>
	<input type="hidden" name="APPLY_TYPE" id="APPLY_TYPE" value="${searchMap.APPLY_TYPE}"/>
	<input type="hidden" name="REF_STATE" id="REF_STATE" value="${essAffirmReq.REF_STATE}"/>	
		<table class="table" width="100%">
		<tbody>
		<tr>
		    <td width="7%" style="text-align:center">
		             附件
		    </td>
		    <td class='td_type'>
			<c:forEach items="${fileList}" var="file" varStatus="i">
			    <a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
			    &nbsp;&nbsp;&nbsp;
			</c:forEach>
		    </td>
		</tr>			
		</tbody>
		</table>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align:right;width:7%;"><!-- 决裁线 -->
					决裁线
				</td>
				<td colspan="7">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="resign_affirmor_list">
						<thead>
							<td class="td_title" style="text-align: center;width:7%;"><!-- 审批等级 -->
							审批等级</td>
							<td class="td_title" style="text-align: center;width:16%;"><!-- 审批者 -->
							审批者</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批情况 -->
							审批情况</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
							审批时间</td>
							<td class="td_title" style="text-align: center;width:34%;"><!-- 审批批注 -->
							审批批注</td>
							<td class="td_title" style="text-align: center;width:7%;"><!-- 审批批注 -->
							审批者(+/-)</td>
							<!-- td class="td_title" style="text-align: center;width:6%;">
								check(+/-)
							</td> -->
						</thead>
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
								<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
									<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
									<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">否决</c:if>
									</td>
									<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
									<td class="td_type">
										<c:if test="${affirmor.ESS_AFFIRM_NO ne essAffirmNo}">
											${affirmor.AFFIRM_CONTENT}
										</c:if>
										<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
											<input type="text" id="RESIGN_AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" size="55" maxlength="200" 
												value="${affirmor.AFFIRM_CONTENT}"/>
											<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
											<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
											<!-- 隐藏的一些参数 -->
											<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${searchMap.APPLY_NO}" />
											<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
											<input id="AFFIRM_FLAG_RESIGN_APPLY" name="AFFIRM_FLAG" type="hidden" value="" />
											<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID}" />
											<input id="affirm_count" name="affirm_count" type="hidden" value="${fn:length(affirmorList)}" />
										</c:if>
									</td>
									<td class="td_type" style="text-align: center">
										<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
										<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo  && j.count<fn:length(affirmorList)}">
											<img id="${affirmor.AFFIRMOR_ID}_${j.index}" src="/resources/images/+.gif" title="添加决裁者"
												border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow_ess0253(this.id)"/>
										</c:if>
									</td>
									<!-- td class="td_type" style="text-align: center">
										<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
										<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
											border="0" align="absmiddle" style="cursor:hand" onclick="addCheck(${affirmor.ESS_AFFIRM_NO},${affirmor.AFFIRMOR_ID})"/>
										</c:if>
									</td> -->
								</tr>			
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</form>
</td></tr>
</table>
<div class="formBar">
	<ul>
	<c:if test="${essAffirmNo ne ''}">
			<li>
				<div class="button">
					<div class="buttonContent"><!-- 通过 -->
						<button type="button" onclick="submitAffirmPreEss0253(1)">
							通过
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent"><!-- 否决 -->
						<button type="button" onclick="submitAffirmPreEss0253(2)">
							否决
						</button>
					</div>
				</div>
			</li>
		</c:if>
			<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" id="btnClose" name="btnClose" class="close">
					关闭</button>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>