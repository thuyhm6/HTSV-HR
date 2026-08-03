<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewRegPersonalTargetProbation_SEQ").change(function(){
		$("#viewRegPersonalTargetProbationForm",navTab.getCurrentPanel()).submit();
	});
	<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
	sumObjectTargetScore();
	</c:if>
	<c:if test="${viewEvsObjectInfo.ACTIVITY ne '1' and viewEvsObjectInfo.ACTIVITY ne '0'}">
	var total = 0;
	$('td:[sysLong="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).html() != ''){
			total += parseInt($(obj).html());
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
	</c:if>
});

//添加决裁者
function addRowByIDObjectTarget(currentRowID){
	var count = parseInt($("#objectTargetCnt").val());
    var htm  ='<tr id="rowIdObjectTarget_' + count + '"><td class="td_type" style="text-align:center"></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="" type="text" size="40" class="required"></td>';
    htm +='<td class="td_type" style="text-align:center"><textarea style="width:100%;height:100px" name="ITEM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen"></textarea></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="0" type="text" size="12" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>';
	htm +='<td class="td_type" style="text-align: center">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	//添加
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	//删除
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewRegPersonalTargetProbation_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdObjectTarget_" + currentRowID).after(htm);

   	var op = {html5Upload:false, skin: 'default',tools: 'Cut,Copy,Paste,|,Fullscreen'};
   	$("#rowIdObjectTarget_" + count).find("textarea").xheditor(op);
   	
   	changeObjectTargetLevel();
  	$("#objectTargetCnt").val(++count) ;
}
//修改No
function changeObjectTargetLevel(){
	var tb2 = document.getElementById("viewRegPersonalTargetProbation_table");
	var rowCount = tb2.rows.length;
	for(var m=1;m < rowCount - 1;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function sumObjectTargetScore(){
	var total = 0;
	$('input:[name="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val());
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
}

function saveObjectTarget(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		if($("#objectTargetSum",navTab.getCurrentPanel()).html() != 50){
			alertMsg.error("<spring:message code='evs.viewRegPersonalProbation.BILVBUSHIQINGQUEREN.a'/>");//比率不是50%,请确认
			return false;
		}
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	$("#objectTargetFlag",navTab.getCurrentPanel()).val(flag);
	var form = $("#viewRegPersonalTargetProbationSaveForm",navTab.getCurrentPanel());
	if (!form.valid()) {
		return false;
	}							//确定要 																			吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addRegPersonalTargetProbation',
  				data:form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
}

//添加决裁者
function addRowByIDLTwoPro(currentRowID){
	var count = parseInt($("#affirmorListCntPro",navTab.getCurrentPanel()).val());
  var htm  ='<tr id="rowIdApplyLotPro'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
      htm +='<td class="td_type" style="text-align: center" width="20%">';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" /><spring:message code="evs.viewRegPersonalProbation.KAOHEZHE.a"/>';//考核者
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" /><spring:message code="ess.infoApply.confirm_person"/>';//确认者
      htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
	    htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<input id="dwz.person.LotpersonIdPro'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempNamePro'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorPPro(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<input id="dwz.person.InfoLotempNamePro' + count + '"  type="text"  size="25" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="15%">';
		htm +='<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>"';	//添加
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoPro(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="<spring:message code="ess.empInfo.Delete"/>"';	//删除
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Pro.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelPro();"/></td></tr>';

 	//当前行之后插入一行
 	$("#rowIdApplyLotPro" + currentRowID).after(htm);
	$("[id='dwz.person.LotempNamePro" + count + "']").attr("alt","<spring:message code='org.title.INPUT_KEY_SELECT'/>").attr("size","25").inputAlert();//请输入关键字按回车检索
 	changeApplyOtLevelPro();
	$("#affirmorListCntPro").val(++count) ;
}
//添加第一行审判者
function addRowByIDApplyPOTFirstPro(){
	var count = parseInt($("#affirmorListCntPro").val());
  	var htm  ='<tr id="rowIdApplyLotPro'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" /><spring:message code="evs.viewRegPersonalProbation.KAOHEZHE.a"/>';//考核者
	htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" /><spring:message code="ess.infoApply.confirm_person"/>'; //确认者
	htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.LotpersonIdPro'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempNamePro'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorPPro(this,' + count + ',event)" class="required"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.InfoLotempNamePro' + count + '"  type="text"  size="25" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>"';	//添加
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoPro(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="ess.empInfo.Delete"/>"';	//删除
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Pro.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelPro();"/></td></tr>';

	var tb2 = document.getElementById("addApplyLOTAffirm_list_Pro");

 	if(tb2.rows.length == 0){
 		$("#addApplyLOTAffirm_list_Pro:last tbody").html(htm);
 	} else {
 	   	//当前行之后插入一行
 	   	$("#" + tb2.rows[0].id).before(htm);
 	}
	$("[id='dwz.person.LotempNamePro" + count + "']").attr("alt","<spring:message code='org.title.INPUT_KEY_SELECT'/>").attr("size","25").inputAlert();//请输入关键字按回车检索
 	changeApplyOtLevelPro();
	$("#affirmorListCntPro").val(++count) ;
}
//修改决裁者等级
function changeApplyOtLevelPro(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list_Pro");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmorPPro(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
	
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var empIdStr=obj.id.substring(11);
		var personIdStr="LotpersonIdPro"+empIdStr.substring(12);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onckPro").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onckPro").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onckPro").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onckPro").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.LotempNamePro" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.LotpersonIdPro" + index + "']").val( jsonObject.personId);
					  	$("[id='dwz.person.InfoLotempNamePro" + index + "']").val( jsonObject.empName + "/" + jsonObject.POST_GRADE_NAME + "/" + jsonObject.deptName);
					}
				},
				error: DWZ.ajaxError
			});
		}
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
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewRegPersonalTargetProbationForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewRegPersonalProbation" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewRegPersonalTargetProbationResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
						<input type="hidden" name="seach_LIMIT" value="${LIMIT }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="button.search"/><!--查询-->
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
	<form id="viewRegPersonalTargetProbationSaveForm" action="/evs/manage/addRegPersonalTargetProbation" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evsProbation.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Objective Confirm</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveObjectTarget(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveObjectTarget(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%" id="viewRegPersonalTargetProbation_table">	
			<tr id="rowIdObjectTarget_100">
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title" style="text-align:center;" width="50%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="inct.salesman.ratio"/><!--比率-->(%)</td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="org.title.ADD"/><!--新增-->(<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(100)"/>)</td>
				</c:if>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr id="rowIdObjectTarget_${i.index }">
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="${item.ITEM_NAME }" type="text" size="40" class="required"></td>
			    	<td class="td_type" style="text-align:center"><textarea style="width:100%;height:100px" name="ITEM_CONTENT"  class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${item.ITEM_CONTENT }</textarea></td>
			    	<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="${item.ITEM_SCORE }" type="text" size="12" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>
					<td class="td_type" style="text-align: center">
						<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>"
							border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget('${i.index }')"/>&nbsp;&nbsp;&nbsp;	<!--添加-->
						<img src="/resources/images/-.gif" title="<spring:message code="ess.empInfo.Delete"/>"	
							border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewRegPersonalTargetProbation_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/><!--删除
					--></td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '1' and viewEvsObjectInfo.ACTIVITY ne '0'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr id="rowIdObjectTarget_101">
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">0</td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
				<td class="td_title"></td>
				</c:if>
			</tr>
		</table>
		<input type="hidden" id="objectTargetCnt" name="objectTargetCnt" value="${objectTargetCnt }"/>
		<input type="hidden" name="RESUME_SEQ" value="${RESUME_SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.EVS_OBJECT_SEQ }"/>
		<input type="hidden" name="SEQ" value="${viewEvsAffirmInfo.SEQ }"/>
		<input type="hidden" id="objectTargetFlag" name="FLAG" value="0"/>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.YUANGONGZIPING.a"/><!--员工自评--></div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title"  style="text-align:center;">Comment</td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
			<tr id="rowIdEvsBySelf_100">
				<td><textarea style="width:100%;height:100px" id="AFFIRM_CONTENT"  name="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsAffirmInfo.AFFIRM_CONTENT }</textarea></td>
			</tr>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '1' and viewEvsObjectInfo.ACTIVITY ne '0'}">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_type">${viewEvsAffirmInfo.AFFIRM_CONTENT }</td>
			</tr>
			</c:if>
		</table>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:50px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewRegPersonalProbation.PINGJIAZHESHEDING.a"/><!--评价者设定--></div>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%"><spring:message code="ar.viewcycle.title.xuhao"/><!--序号--></td>
				<td class="td_title"  style="text-align:center;" width="20%"><spring:message code="evs.viewProbationEvsResult.PINGJIAQUFEN.a"/><!--评价区分--></td>
				<td class="td_title" style="text-align:center;" width="30%"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></td>
				<td class="td_title" style="text-align:center;" width="30%"><spring:message code="evs.viewRegPersonalProbation.PINGJIAZHEXINXI.a"/><!--评价者信息--></td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
				<td class="td_title" style="text-align:center;" width="15%"><spring:message code="evs.viewRegPersonalProbation.SHIFOUXINZENG.a"/><!--是否新增-->(<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyPOTFirstPro()"/>)</td><!--添加
				--></c:if>
			</tr>
			<tr>
				<td colspan="5">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list_Pro">
						<tbody>
							<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
								<tr id="rowIdApplyLotPro${i.index}">
									<td class="td_type" style="text-align: center" width="5%"><span name="rowIndex">${i.count}</span></td>
								    <td class="td_type" style="text-align: center" width="20%">
										<input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="1" <c:if test="${item.AFFIRM_TYPE eq '1' }">checked="checked"</c:if>/><spring:message code="evs.viewRegPersonalProbation.KAOHEZHE.a"/><!--考核者-->
										<input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="2" <c:if test="${item.AFFIRM_TYPE eq '2' }">checked="checked"</c:if>/><spring:message code="ess.infoApply.confirm_person"/><!--确认者-->
										<input type="hidden" name="approvTypeIndex" value="${i.index}" />
									</td>
									<td class="td_type" style="text-align: center" width="30%">
										<input id="dwz.person.LotpersonIdPro${i.index}" name="AFFIRMOR_ID" value="${item.AFFIRMOR_ID}" type="hidden" lookupGroup="person"/><!--  请输入关键字按回车检索
										--><input id="dwz.person.LotempNamePro${i.index}" name="empid" value="${item.AFFIRMOR}" type="text" size="25" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" lookupGroup="person" onkeydown="submitKeyClick_affirmorPPro(this,'${i.index}',event)" class="required"/>
									</td>
									<td class="td_type" style="text-align: center" width="30%">
										<input id="dwz.person.InfoLotempNamePro${i.index}" type="text" value="${item.AFFIRMOR_INFO}" size="25" disabled="disabled"/>
									</td>
									<c:if test="${viewEvsObjectInfo.ACTIVITY eq '1' or viewEvsObjectInfo.ACTIVITY eq '0'}">
									<td class="td_type" style="text-align: center" width="15%">
										<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>"	border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoPro(${i.index})"/>&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="删除" border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Pro.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelPro();"/>
									</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<a id="onckPro" name="onckPro"  href="" lookupGroup="person"></a>
					<input type="hidden" id="affirmorListCntPro" name="affirmorListCntPro" value="${viewEvsAffirmListSize }"/>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
