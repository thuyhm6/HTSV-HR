<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addPaForLeftAffirmorRow(currentRowID){
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
          			+'<td style="text-align: center">未决裁</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">'
      					//+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addPaForLeftAffirmorRow(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.affirmor_list_paforleft.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowPaForLeftAffirmor();"/>'
					+'</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("affirmor_list_paforleft");
 	var rowCount = tb2.rows.length;
 	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
function delRowPaForLeftAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("affirmor_list_paforleft");
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
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdListNew?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId='+empIdStr
									+'&personId='+personIdStr  
									+'&empName='+empNameStr 
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
function submitPaForLeftAffirmPre(flag){
	$("#AFFIRM_FLAG").val(flag);
  	var $from = $("#addPaForLeftAffirmInfo");
  	$from.submit();
}

function validatePaForLeftAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var applyNo = $("#APPLY_NO").val(); 
	var essAffirmNo = $("#ESS_AFFIRM_NO").val();
    var person_id = $("#PERSON_ID").val();
    var affirmFlag = $("#AFFIRM_FLAG").val();

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
	if (confirm (result)){	          
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	}
	return false;
}
//-->
</script>

<script type="text/javascript">
<!--
function addRowByIDPaForLeft_Check(applyNo,essAffirmNo){
	$.pdialog.open("/pa/salary/addPaForLeftCheckView?PAGE_FLAG='paForLeft'&seach_APPLY_NO="+applyNo+"&seach_ESS_AFFIRM_NO=" 
			+ essAffirmNo, "addRowByIDPaForLeft_Check", "添加Check人", {width:600,height:350,mask:true});
	
}

//-->
</script>

<div class="pageContent">
	<div style="overflow-y:auto; height:150px;">
		<table width="100%" class="table" layoutH="350">
			<thead>
				<tr>	
					<th width="3%" style="text-align:center">序号</th>
					<th width="10%" style="text-align:center">社号/姓名</th>
					<th width="20%" style="text-align:center">部门</th>
					<th width="8%" style="text-align:center">发放月份</th>
					<th width="8%" style="text-align:center">补发月份</th>
					
					<th width="6%" style="text-align:center">补发类别</th>
					<!-- <th width="15%" style="text-align:center">补发项目</th>
					<th width="5%" style="text-align:center">金额</th> -->
					<th width="20%" style="text-align:center">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paForLeftDetailList}" var="detail" varStatus="i">			
					<tr>
						<td class='td_center'>${i.index+1 }</td>
						<td class='td_center'>(${detail.EMPID})${detail.LOCAL_NAME}</td>
						<td class='td_left'>${detail.DEPARTMENT }</td>
						<td class='td_center'>${detail.PA_MONTH }</td>
						<td class='td_center'>${detail.PA_MONTH_FOR }</td>
						
						<td class='td_center'>
							<c:if test="${detail.ITEM_TYPE eq 'PA' }">薪资</c:if>
							<c:if test="${detail.ITEM_TYPE eq 'IS' }">保险</c:if>
						</td>
						<%-- <td class='td_center'>${detail.ITEM_NAME }</td>
						<td class='td_right'>${detail.ITEM_DATA }</td> --%>
						<td class='td_center'>${detail.REMARK }</td>
					</tr>			
				</c:forEach>
				<c:if test="${paForLeftDetailListCnt == 0}">
					<tr><td colspan="9">&nbsp;</td></tr>
					<tr><td colspan="9">&nbsp;</td></tr>
					<tr><td colspan="9">&nbsp;</td></tr>
					<tr><td colspan="9">&nbsp;</td></tr>
					<tr><td colspan="9">&nbsp;</td></tr>
				</c:if>		
			</tbody>
		</table>
	</div>
</div>
		
<div class="pageContent">
	<form id="addPaForLeftAffirmInfo" method="post" action="/pa/salary/addPaForLeftAffirmInfo" class="pageForm required-validate" 
			onsubmit="return validatePaForLeftAffirmCallback(this,navTabAjaxDone);">
		<div>
			<table class="user_table" width="100%">
				<tr><td class="td_title" style="text-align: left" colspan="8"><font><b>&nbsp;申请信息&nbsp;</b></font></td></tr>
				<tr>
					<td class="td_title" style="text-align: center">申请人</td>
					<td class="td_type"  style="text-align: center">(${paForLeftMap.EMPID})${paForLeftMap.LOCAL_NAME}</td>
					<td class="td_title" style="text-align: center">申请时间</td>
					<td class="td_type"  style="text-align: center">${paForLeftMap.CREATE_DATE }</td>
					<td class="td_title" style="text-align: center">申请内容</td>
					<td class="td_type" colspan="3">${paForLeftMap.APPLY_CONTENT }</td>
				</tr>
				<tr>
					<td class="td_title" width="11%" style="text-align: center"><!-- 决裁线 -->
						决裁线
					</td>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list_paforleft">
							<thead>
								<td class="td_title" width="100" style="text-align: center"><!-- 决裁等级 -->
									决裁等级
								</td>
								<td class="td_title" width="180" style="text-align: center"><!-- 决裁者 -->
									决裁者
								</td>
								<td class="td_title" width="100" style="text-align: center"><!-- 决裁情况 -->
									决裁情况
								</td>
								<td class="td_title" width="180" style="text-align: center"><!-- 审批时间 -->
									审批时间
								</td>
								<td class="td_title" style="text-align: center"><!-- 决裁批注 -->
									决裁批注[不超过50个字符]
								</td>
								<td class="td_title" width="100" style="text-align: center"><!-- 决裁批注 -->
									决裁者(+/-)
								</td>
								<td class="td_title" width="100" style="text-align: center"><!-- 决裁批注 -->
									check(+/-)
								</td>
							</thead>
							<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
									<tr id="${affirmor.AFFIRMOR_ID }">
										<td class="td_type" width="100" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" width="180" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}
										<c:if
													test="${affirmor.AFFIRM_FLAG eq '0' }">
													<input type="hidden" name=AFFIRMOR_ID
														value="${affirmor.AFFIRMOR_ID }" />
												</c:if></td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
										</td>
										<td class="td_type" width="180" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
										<td class="td_type">
											<c:if test="${affirmor.AFFIRM_FLAG ne '0' || affirmor.AFFIRMOR_ID ne PERSON_ID}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
											<c:if test="${affirmor.AFFIRMOR_ID eq PERSON_ID }">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" maxlength="50" size="48" value="${affirmor.AFFIRM_CONTENT}"/>
												<%-- 隐含的一些值 --%>
												<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }"/>
												<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="0"/>
												<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${affirmor.APPLY_NO }"/>
												<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${affirmor.APPLY_TYPE }"/>
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }"/>
												<input type="hidden" name="affirmCount" id="affirmCount_affirm"
					value="${affirmorListCnt }"> 
					<input type="hidden" id="dept_level" name="dept_level"
					value="${dept_level}" />
											</c:if>
										</td>
										<td class="td_type" width="100" style="text-align: center">
											<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加决裁者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addPaForLeftAffirmorRow(this.id)"/>
											</c:if>&nbsp;&nbsp;&nbsp;
											<%-- 如果是自己添加的决裁者，且未决裁时，允许删除自己添加的决裁者 --%>
											<%--<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.CHECK_CREATED_BY eq PERSON_ID}">
												<img src="/resources/images/-.gif" style="cursor:hand" title="删除"
					         						onclick="javaScript:document.all.affirmor_list_paforleft.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>
					         				</c:if>--%>
										</td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
	 												border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPaForLeft_Check(${affirmor.APPLY_NO},${affirmor.ESS_AFFIRM_NO})"/>
	 										</c:if>
											<!--
											<a rel="otApplyRemark" href="/ess/affirmApply/viewFullApplyRemarkInfo?PAGE_FLAG=L_OTAPPLY&seach_APPLY_NO=${APPLY_NO }
												&seach_ESS_AFFIRM_NO=${affirmor.ESS_AFFIRM_NO}" title="添加Checkor" target="dialog" mask="true" width="600" 
												height="350" id="otApplyRemarkHref"><img src="/resources/images/+.gif" border="0" align="absmiddle" 
												style="cursor:hand" ></img></a>-->												
			          						<%-- 												
											<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
												border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow(this.id)"/>
											--%>
										</td>
									</tr>			
									<a id="onck" name="onck"  href="" lookupGroup="person"></a>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_type" colspan="8"><br/></td>
				</tr>
				<tr>
					<c:if test="${checkorListCnt > 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
							Review
						</td>
					</c:if>
					<c:if test="${checkorListCnt == 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${3 }"><!-- Review -->
							Review
						</td>
					</c:if>
					<td class="td_title" width="15%" style="text-align: center"><!-- Type -->
						Type
					</td>
					<td class="td_title" width="30%" style="text-align: center" colspan="3"><!-- Requests -->
						Requests
					</td>
					<td class="td_title" width="45%" style="text-align: center" colspan="3"><!-- Reviewed -->
						Reviewed
					</td>
				</tr>
					
				<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
					<tr>
						<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">
							[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
							&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
							<c:if test="${checkor.AFFIRM_FLAG == 0}">
								<font color="blue">未决裁</font>
							</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 1}">
								<font color="green">已通过</font>
							</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 2}">
								<font color="red">已否决</font>
							</c:if>
						</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">
							[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
							&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
							<c:if test="${checkor.CHECK_FLAG == 0}">
								<font color="blue">未Check</font>
							</c:if>
							<c:if test="${checkor.CHECK_FLAG == 1}">
								<font color="green">已Check</font>
							</c:if>
						</td>
					</tr>	
					<tr>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">
							<textarea name="affirmRemark" cols="50" rows="3" disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
						</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">
							<textarea name="checkRemark" cols="50" rows="3" disabled="disabled">[Check]：${checkor.CHECK_CONTENT}</textarea>
						</td>
					</tr>			
				</c:forEach>
				<c:if test="${checkorListCnt == 0}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
				</c:if>
			</table>
			<div class="formBar">
				<ul>
					<li>
						<div class="button"><!-- 通过 -->
							<div class="buttonContent"><button type="button" onclick="submitPaForLeftAffirmPre(1)">通过</button></div>
						</div>
					</li>
					<li>
						<div class="button"><!-- 否决 -->
							<div class="buttonContent"><button type="button" onclick="submitPaForLeftAffirmPre(2)">否决</button></div>
						</div>
					</li>
					<li>
						<div class="button"><!-- 关闭 -->
							<div class="buttonContent"><button type="button" class="close">关闭</button></div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>