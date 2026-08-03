<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addAffirmorRow_hr0306(currentRowID){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('table:last tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('table:last tbody tr:eq('+i+')');
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
          			+'<td style="text-align: center">未审批</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">'
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow_hr0306(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新审批者为第二位出现错位现象
	var tb2 = document.getElementById("affirmor_list");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}

var keyCodeInit=0;
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
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	var target, code, tag;  
  	if (!event) {  
       event = window.event; //针对ie浏览器  
       target = event.srcElement;  
       code = event.keyCode;  
       if (code == 13) {  
           tag = target.tagName;  
           if (tag == "TEXTAREA") {
	           return true;
	       }else{ 
		       return false;
		   }  
       }  
  	}else {  
       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
       code = event.keyCode;  
       if (code == 13) {  
           tag = target.tagName;  
           if (tag == "INPUT"){ 
	           return false; 
	       }else {
		        return true;
		   }   
      }  
 	}  
}; 
//-->
</script>
<script type="text/javascript">
<!--
function submitAffirmPrehr0306(flag){
	$("#contract_affirm_hr0306_flag").val(flag);
  	var $from = $("#addContractAffirmInfo");
  	$from.submit();
}

function validateContractAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
    var affirmFlag = document.getElementById("contract_affirm_hr0306_flag").value;
    
	var result = "确定要执行此次操作？";
	if(affirmFlag==1){
		result = "确定要通过此条申请信息？";
	}else if(affirmFlag==2){
		result = "确定要否决此条申请信息？";
	}
	alertMsg.confirm(result,{
		okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}});
	return false;
}

function change_name(thisvalue){
	//拼接字符串
	var personId_str="";
	var empId_str="";
	var empName_str="";
	//显示DIV
	var temp_div_name="";
	//隐藏值
	var temp_input_personId = $("#temp_personId").val()!=""?$("#temp_personId").val().split(","):new Array();
	//回传值
	var oldpersonId = $("#personId").val()!=""?$("#personId").val().split(","):new Array();
	var oldempId = $("#empId").val()!=""?$("#empId").val().split(","):new Array();
	var oldempName = thisvalue != "" ? thisvalue.split(",") : new Array();

	//根据隐藏值 嵌套回传值 循环
	if(temp_input_personId.length > 0){
		if(oldpersonId.length > 0){
			for(k = 0 ; k < oldpersonId.length ; k++){
				//boolean
				var flag = true ;
				for(j = 0 ; j < temp_input_personId.length ; j++){
					if(temp_input_personId[j] == oldpersonId[k]){
						flag=false;
						break;
					}
				} 
				if(flag){
					$("#temp_personId").attr("value",$("#temp_personId").val()+","+oldpersonId[k]);
					temp_div_name+="<div id='"+oldpersonId[k]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[k]+"("+oldempId[k]+")</div>";
				}
			} 
			$("#addempshift_name").append(temp_div_name);
		}
		
	}else{
		if(oldpersonId.length > 0){
			for(i = 0 ; i < oldempName.length ; i++){
				temp_div_name+="<div id='"+oldpersonId[i]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[i]+"("+oldempId[i]+")</div>";
			} 
			$("#temp_personId").attr("value",$("#personId").val());
			$("#addempshift_name").append(temp_div_name);
		} 
	}
	
}

function addRowByIDhr0306_Check(ESS_AFFIRM_NO){
	$.pdialog.open("/ess/affirmLeaveApply/viewLeaveApplyRemarkInfo?PAGE_FLAG=LEAVE_APPLY&seach_APPLY_NO=${APPLY_NO }&seach_ESS_AFFIRM_NO=" + ESS_AFFIRM_NO, "addRowByIDhr0306_Check", "添加Check人", {width:600,height:350,mask:true});
}
//-->
</script>
<div class="pageContent">
	<form id="addContractAffirmInfo" method="post" action="/hrm/contractInfo/approveExpiredContract" class="pageForm required-validate" 
			onsubmit="return validateContractAffirmCallback(this,dialogAjaxDone);">
		<div>
			<table class="user_table" layoutH="60" width="100%">	
				<tr>
					<td colspan="7">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center"><!-- 社号/姓名 --><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
								<td>[${contractInfo.EMPID}]${contractInfo.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 合同次数 --><spring:message code="hrm.contract.Contract_TOTAL_PERIOD" /></td>
								<td>${contractInfo.TOTAL_PERIOD}</td>
								<td class="td_title" style="text-align: center"><!-- 合同编号  --><spring:message code="hrm.contractInfo.CONTRACT_ID" /> </td>
								<td>${contractInfo.CONTRACT_NUMBER}</td>
								<td class="td_title" style="text-align: center"><!-- 部门  --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /></td>
								<td>${contractInfo.DEPTNAME}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center"><!--合同类型   --><spring:message code="hrm.contract.CONTRACT_TYPE" />        </td>
								<td>${contractInfo.CONTRACT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 合同版本 --> <spring:message code="hrm.contract.Contract_version" />     </td>
								<td>${contractInfo.CONTRACT_VERSION}</td>
								<td class="td_title" style="text-align: center"><!--起始日期--><spring:message code=""/>      </td>
								<td>${contractInfo.START_CONTRACT_DATE}</td>
								<td class="td_title" style="text-align: center">终止日期</td>
								<td>${contractInfo.END_CONTRACT_DATE}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center"><!-- 工作地区 --><spring:message code="hr.viewPersonalInfo.title.gongzuodiqu" /></td>
								<td>${contractInfo.WORK_AREA_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 签订意见 --><spring:message code="hr.contract.title.xuqian.yijian" /> </td>
								<td colspan="5">${contractInfo.REMARK}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="11%" style="text-align: center"><!-- 审批线 -->
						<!-- 审批线 --><spring:message code="hrm.contractInfo.APPROVAL_LINE" />
					</td>
					<td colspan="6">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list">
							<thead>
							<td class="td_title" width="100" style="text-align: center"><!-- 审批等级 -->
						<!-- 审批等级 --><spring:message code="hrm.contractInfo.APPROVAL_GRADE" />
					</td>
					<td class="td_title" width="180" style="text-align: center"><!-- 审批者 -->
						<!-- 审批者 --> <spring:message code="hrm.contractInfo.APPROVAL_PERSON" />
					</td>
					<td class="td_title" width="100" style="text-align: center"><!-- 审批情况 -->
						<!-- 审批情况 --> <spring:message code="hrm.contractInfo.APPROVAL_SITUATION" />
					</td>
					<td class="td_title" width="180" style="text-align: center"><!-- 审批时间 -->
						<!-- 审批时间 --> <spring:message code="hrm.contractInfo.APPROVAL_TIME" />
					</td>
					<td class="td_title" style="text-align: center"><!-- 审批批注 -->
						<!-- 审批批注 --> <spring:message code="hrm.contractInfo.APPROVAL_COMMENT" />
					</td>
					<td class="td_title" width="100" style="text-align: center"><!-- 审批批注 -->
						<!-- 审批者(+/-) --> <spring:message code="hrm.contractInfo.APPROVAL_PERSON" />(+/-)
					</td>
					</thead>
					<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
									<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
										<td class="td_type" width="100" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" width="180" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}"><!-- 未审批 --> <spring:message code="ess.affirmApply.title.remark.weishenpi" /></c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}"><!-- 已通过 --><spring:message code="ess.affirmApply.title.remark.yitongguo" /> </c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}"><!-- 已否决 --><spring:message code="ess.affirmApply.title.remark.yifoujue" /></c:if>
										</td>
										<td class="td_type" width="180" style="text-align: center">${affirmor.UPDATE_DATE}</td>
										<td class="td_type">
											<c:if test="${affirmor.ESS_AFFIRM_NO ne ESS_AFFIRM_NO}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq ESS_AFFIRM_NO}">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT_0" title="批注"  size="55" maxlength="200" 
													value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="CONTRACT_NO" name="CONTRACT_NO_0" type="hidden" value="${contractInfo.CONTRACT_NO}" />
												<input name="hr0306Check" type="hidden" value="0" />
												<input type="hidden" name="ESS_AFFIRM_NO_0" value="${affirmor.ESS_AFFIRM_NO }" />
											<input name="AFFIRM_LEVEL_CURRENT_0" type="hidden" value="${affirmor.AFFIRM_LEVEL }" />
											<input type="hidden" name="AFFIRMOR_ID"
												value="${affirmor.AFFIRMOR_ID }" />
											<input id="APPLY_PERSON_ID" name="APPLY_PERSON_ID" type="hidden" value="${contractInfo.PERSON_ID}" />
											</c:if>
										</td>
										<td class="td_type" width="100" style="text-align: center">
											<%-- 如果是自己审批时，且未审批时，允许添加审批者 --%>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq ESS_AFFIRM_NO }">
												<img id="${affirmor.AFFIRMOR_ID}_${j.index}" src="/resources/images/+.gif" title="添加审批者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow_hr0306(this.id)"/>
											</c:if>&nbsp;&nbsp;&nbsp;
										</td>
									</tr>			
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<input id="contract_affirm_hr0306_flag" name="FLAG" type="hidden" value="" />
		</div>
		
		<div class="formBar">
			<ul>
				<c:if test="${'' ne ESS_AFFIRM_NO}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="submitAffirmPrehr0306(1)">
								<!-- 通过 --><spring:message code="hrm.contractInfo.ADOPT"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="submitAffirmPrehr0306(2)">
								 <!-- 否决 --><spring:message code="hrm.contractInfo.VETO" />
							</button>
						</div>
					</div>
				</li>
				</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>