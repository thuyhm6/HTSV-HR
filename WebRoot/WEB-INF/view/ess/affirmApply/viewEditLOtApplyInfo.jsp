<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
 	
<script type="text/javascript">


var ajaxGet_add_ot_apply_two;
function ajaxAdd_add_ot_apply_two() {
	if (ajaxGet_add_ot_apply_two != null) {
		ajaxGet_add_ot_apply_two.abort();
	}

    var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;
    
    var OT_APPLY_HOUR_2   = document.getElementById("OT_APPLY_HOUR_2").value;
    
    if(otApplyHour==null||otApplyHour==""){
     	otApplyHour=OT_APPLY_HOUR_2;
        }
	   var adjustYn = $("#updateLOtApply input[name='ADJUST_YN']:checked").val();
    var applytypecode=$(":input[name='APPLY_TYPE_CODE']").val();
 	if($("#PERSON_ID").val() != '' && otApplyHour != '' && $(":input[name='APPLY_TYPE_CODE']").val() != ''){
  	 
 
		$.ajaxSettings.global = false;
		 
		ajaxGet_add_ot_apply_two = $.ajax( {
			type : "POST",
			url : "/ess/infoApply/getAffirmList",
			data : {applyParentType : '31',applyType : applytypecode,applyLength : otApplyHour,personId : $("#PERSON_ID").val(),ADJUST_YN:adjustYn,OT_PLACE_TYPE : $(":input[name = 'OT_PLACE_TYPE']").val()},
			dataType : "json",
			success : function(data) {
				$('#addApplyLOTAffirm_list_edit tbody').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {
					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLot' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="33%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="33%">[' + comment['EMPID']
												+ ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
								 
										html += '<td class="td_type" style="text-align: center" width="33%">';
										html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLedit('+ commentIndex +')"/>';
										html += '</td>';
										$("#affirmorListCnt").val(commentIndex + 1);
									});
				}
				$('#addApplyLOTAffirm_list_edit tbody').html(html);
			}
		});
		$.ajaxSettings.global = true;
	}
}
//添加决裁者
function addRowByIDLedit(currentRowID){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.LotpersonId' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_Laffirmor(this,' + count + ',event)" class="required"/>';

		
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLedit(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_edit.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLot" + currentRowID).after(htm);
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeApplyOtLevel();
  	$("#affirmorListCnt").val(++count) ;
}


function addRowByIDApplyLOTEditFirst(){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
	htm +='<td class="td_type" style="text-align: center" width="33%">';
	htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_Laffirmor(this,' + count + ',event)" class="required"/>';

	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="33%">';
	htm +='<img src="/resources/images/+.gif" title="添加"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLedit(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="删除"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_edit.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';


	var tb2 = document.getElementById("addApplyLOTAffirm_list_edit");


   	if(tb2.rows.length == 0){
   		$("#addApplyLOTAffirm_list_edit:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeApplyOtLevel();
  	$("#affirmorListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyOtLevel(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list_edit");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}


//<!--
function submitLUpdateFormPre(flag){
	//$("#AFFIRM_FLAG").val(flag);
	$("#AFFIRM_FLAG").val(flag);
  	var $from = $("#updateLOtApply");
  	$from.submit();
}
function updateLOtApplyCallback(form,callback) {	
	 
	 
	var $form = $(form);
 
	 
  
	var appid = document.getElementById("APPLY_TYPE_CODE_JUECAI").value;
	 
    var applyTypeCode = document.getElementById("APPLY_TYPE_CODE_JUECAI").value;
    var applyTypeCode2 = document.getElementById("APPLY_TYPE_CODE_JUECAI");
    var otApplyhour44   = document.getElementById("OT_APPLY_HOUR");
   
    var adjustYn2 = document.getElementById("ADJUST_YN__ES");
    var adjustYn = document.getElementById("ADJUST_YN__O");
   
   	var applyOtDate    = document.getElementById("APPLY_OT_DATE_WQ").value;
    var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;

    var otApplyhour442   = document.getElementById("OT_APPLY_HOUR_2").value;


    if(otApplyHour==null||otApplyHour==""){
    	otApplyHour=otApplyhour442;
    	$("#OT_APPLY_HOUR").val(otApplyhour442);
        }
    
    var otApplyMinute = document.getElementById("OT_APPLY_MINUTE").value;
    
    var llastMonth   = document.getElementById("LLASTMONTH").value;
	if(applyOtDate <= llastMonth){
		alertMsg.error("不允许做"+llastMonth+"之前的加班申请，请重新选择加班日期！");
		return false;
	}

	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}
	
	var limitFlag   = document.getElementById("LIMIT_FLAG").value;
	if(limitFlag=='1' && applyTypeCode!='34'){//如果加班人员是被限制的人员，则不允许申请节假日以外的加班申请!
		alertMsg.error("该员工为营业职或促销员，只允许申请法定假加班，请重新选择加班日期!");
		return false;
	}
	
   	if(applyOtDate==""){
    	alertMsg.error("加班日期不为空，请选择加班日期！");
    	return false;
    }
    
    if(otApplyHour==""){
    	alertMsg.error("加班申请长度不得少于一小时，请重新选择！");
    	return false;
    }
    
    var but1 = document.getElementById("but1");
	var but2 = document.getElementById("but2");
 	if (confirm ("确定要修改加班申请吗？")){	   
		 adjustYn.disabled=false;
		    adjustYn2.disabled=false;
		applyTypeCode2.disabled=false;
	    otApplyhour44.disabled=false;       
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			//success: callback || DWZ.ajaxDone,  
			success: function(json){
			 DWZ.ajaxDone(json);
			 if (json.statusCode == DWZ.statusCode.ok){
				if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
					navTab.reloadFlag(json.navTabId);
				} else { //重新载入当前navTab页面
					navTabPageBreak({}, json.rel);
				}
				
				if ("closeCurrent" == json.callbackType) {
					setTimeout(function(){navTab.closeCurrentTab();}, 100);
				} else if ("forward" == json.callbackType) {
					navTab.reload(json.forwardUrl);
				}
			}
			adjustYn.disabled=true;
			adjustYn2.disabled=true;
			applyTypeCode2.disabled=true;
		    otApplyhour44.disabled=true;  
			} ,
				
			
			error:DWZ.ajaxError
		});		
  	}
	return false;
}

//-->
</script>

<script type="text/javascript">
 
//获取加班的人事政策
function calLoTRemarkEdit(){
	var otTypeCode = $("#updateLOtApply select[id='APPLY_TYPE_CODE_JUECAI']").val();
	var otPlaceType = $("#updateLOtApply select[id='OT_PLACE_TYPE']").val();
	   var adjustYn = $("#updateLOtApply input[name='ADJUST_YN']:checked").val();
	 
	if(otTypeCode==""){
		otTypeCode = "32";
	}
	if(adjustYn==""){
		adjustYn = "0";
	}
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApply/getOtApplyRemark",
		 data: [{ name: 'ADJUST_YN', value: adjustYn },
		        { name: 'APPLY_TYPE_CODE', value: otTypeCode },
		        { name: 'OT_PLACE_TYPE', value: otPlaceType }],
		 dataType:"json",
		 success: function(response) {
			 document.getElementById('otLApplyRemarkEdit').innerHTML = response;
		 }
	});
}

//-->
</script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addLRowByIDEdit(currentRowID){
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
						+'	onkeydown="submitKeyClick_Laffirmor(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<c:if test="${affirmorListCnt != 1}">'
            			+'   <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addLRowByIDEdit(this.id);"/>'
            			+'</c:if>&nbsp;&nbsp;&nbsp;'
            			+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			//先删除，再排序
            			//+' onclick="javaScript:document.all.LaddAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delLRowByID();"/>'
            			+' onclick="deleteLRowByIDEdit(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   	var tb2 = document.getElementById("LaddAffirm_list");
 	//如果决裁者只有一个时，添加一个决裁者之后需要取消此按钮
 	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
 	if(affirmorListCnt == 1){
 		tb2.rows[0].cells[2].innerHTML = '';
	}
   	var rowCount = tb2.rows.length;
   	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
   	}
}

function deleteLRowByIDEdit(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('LaddAffirm_list').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("LaddAffirm_list");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	var affirmorIdOnly = document.getElementById("affirmorIdOnly").value;
   	if(affirmorListCnt == 1){
   		var addStr = '<img id="'+affirmorIdOnly+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addLRowByIDEdit('+affirmorIdOnly+');"/>';
   		tb2.rows[0].cells[2].innerHTML = addStr;
	}
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function delLRowByID(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("LaddAffirm_list");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}


var keyCodeInit=0;
function submitKeyClick_Laffirmor(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;

   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="LotpersonId"+empIdStr.substring(10);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onck").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.LotempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.LotpersonId" + index + "']").val( jsonObject.personId);
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
function uploadifySuccess_editLot(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
	  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
	  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",navTab.getCurrentPanel()).html(files);
	  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
	  $("#fileName",navTab.getCurrentPanel()).val(fileName);
	}
//-->
</script>

<div class="pageContent">
	<div>
		<form id="updateLOtApply" method="post" action="/ess/affirmApply/updateLOtApply" class="pageForm required-validate" 
			onsubmit="return updateLOtApplyCallback(this,navTabAjaxDone);">
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button id="but1" type="button" onclick="submitLUpdateFormPre(-1)">
									暂存
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button id="but2" type="button" onclick="submitLUpdateFormPre(0)">
									提交
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<!-- 隐藏的一些参数 -->
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${otApplyInfo.PERSON_ID}"/>
                    <input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${otApplyInfo.APPLY_NO}"/>
                    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" value="31"/>
                    <input id="OT_TIME_TYPE" name="OT_TIME_TYPE" type="hidden" value="L"/>
                    <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value=""/>
				    <input type="hidden" id="LLASTMONTH" name="LLASTMONTH" value="${LLASTMONTH }"/>
				    <input type="hidden" id="LIMIT_FLAG" name="LIMIT_FLAG" value="${otApplyInfo.LIMIT_FLAG }"/>
					<tr>
						<td width="20%" class="td_title" style="text-align: center">工号/姓名/部门</td>
						<td width="30%" class="td_type">${otApplyInfo.EMPID} / ${otApplyInfo.LOCAL_NAME} / ${otApplyInfo.DEPARTMENT}</td>
						<td width="20%" class="td_title" style="text-align: center">申请日期</td>
						<td width="30%" class="td_type">${otApplyInfo.CREATE_DATE}</td>
					</tr>
					
					<tr>
						<td width="20%" class="td_title" style="text-align: center"><!-- 开始日期 -->
							<spring:message code="public.title.startDate"/>
						</td>
						<td width="30%" class="td_type">
							<!--触发对了，但是只有Firefox不好使-->
						    <input type="text" id="APPLY_OT_DATE_WQ" name="APPLY_OT_DATE" class="date required" onpropertychange="getLOtApplyTypeEdit();ajaxAdd_add_ot_apply_two();"
						    	format="yyyy-MM-dd" readonly="true" value="${otApplyInfo.APPLY_OT_DATE}"/>
						    <%--Firefox好使,但是触发不对，需要多次执行
						    <input type="text" id="APPLY_OT_DATE" name="APPLY_OT_DATE" class="date required" onblur="getLOtApplyType();"
						    	format="yyyy-MM-dd" readonly="true" value="${otApplyInfo.APPLY_OT_DATE}"/>
						    --%>
						    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						</td>
						<td width="20%" class="td_title" style="text-align: center"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type"><!--
						    <ait:SelectSyCodeByCpnyID  id="APPLY_TYPE_CODE_JUECAI" name="APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
						    	selected="${otApplyInfo.APPLY_TYPE_CODE}"   limit="all" onChangeName="calLoTRemarkEdit();"/>
						 --><select id="APPLY_TYPE_CODE_JUECAI" name="APPLY_TYPE_CODE" onchange="calLoTRemarkEdit();ajaxAdd_add_ot_apply_two();"  disabled="disabled">
						 <option value="">请选择</option>
						 <option value="32"<c:if test="${otApplyInfo.APPLY_TYPE_CODE eq '32' || otApplyInfo.APPLY_TYPE_CODE eq '17378' }">selected</c:if>>平时加班</option>
						 <option value="33" <c:if test="${otApplyInfo.APPLY_TYPE_CODE eq '33' || otApplyInfo.APPLY_TYPE_CODE eq '141472' || otApplyInfo.APPLY_TYPE_CODE eq '17379' || otApplyInfo.APPLY_TYPE_CODE eq '141471'  }">selected</c:if>>周末加班</option>
						 <option value="34"<c:if test="${otApplyInfo.APPLY_TYPE_CODE eq '34' || otApplyInfo.APPLY_TYPE_CODE eq '141470'}">selected</c:if>> 法定假加班</option> </select>   	
						    	
						    &nbsp;&nbsp;
						    <select id="OT_PLACE_TYPE" name="OT_PLACE_TYPE" onchange="calLoTRemarkEdit();ajaxAdd_add_ot_apply_two();">
						    	<option value="INSIDE" <c:if test="${otApplyInfo.OT_PLACE_TYPE eq 'INSIDE' }">selected</c:if>>社内</option>
						    	<option value="OUTSIDE" <c:if test="${otApplyInfo.OT_PLACE_TYPE eq 'OUTSIDE'}">selected</c:if>>社外</option>
						    </select>
						</td> 
					</tr>
					 
						
					<tr>
						<td width="20%" class="td_title" style="text-align: center">
							时间长度
						</td>
						<td width="30%" class="td_type">
						
						
						 	<div id="hour55" style="float:left">
						    <select name="OT_APPLY_HOUR" id="OT_APPLY_HOUR"   onchange="getLOtApplyTypeEdit();ajaxAdd_add_ot_apply_two();">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="3" end="8" step="1">
									<option value="${h}" <c:if test="${h eq otApplyInfo.OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
							</select>
							</div>
							
						 	<div id="hour44" style="display:none;float:left" >
						    <select name="OT_APPLY_HOUR_2" id="OT_APPLY_HOUR_2"  onchange="getLOtApplyTypeEdit();ajaxAdd_add_ot_apply_two();">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="0" end="2" step="1">
									<option value="${h}" <c:if test="${h eq otApplyInfo.OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
							</select>
							</div>
							
						 	 
							<select name="OT_APPLY_MINUTE" id="OT_APPLY_MINUTE">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="m" begin="0" end="59" step="30">
									<option value="${m}" <c:if test="${m eq otApplyInfo.OT_APPLY_MINUTE}">selected</c:if>>
										${m}分
									</option>
								</c:forEach>
							</select>	  
						 
						</td>
						<td width="20%" class="td_title" style="text-align: center"><!--是否转调休-->
					    	是否转调休
					    </td>
					    <td width="30%" class="td_type">
					    	<input type="radio" id="ADJUST_YN__ES" name="ADJUST_YN" value="1" title="是" 
					    		<c:if test="${otApplyInfo.ADJUST_YN eq '1'}">checked="checked"</c:if>/>是&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="ADJUST_YN__O" name="ADJUST_YN" value="0" title="否"
					    		<c:if test="${otApplyInfo.ADJUST_YN eq '0' }">checked="checked"</c:if>/>否
					    </td>
					    
					</tr>
					
					<tr>
					    <td width="20%" class="td_title" style="text-align: center"><!--人事政策-->
					    	人事政策
					    </td>
					     <td width="80%" class="td_type" colspan="3">
					    	<font color="red">
					    		<div id="otLApplyRemarkEdit"></div>
					    	</font>
					    </td>
					</tr>
					<tr>				
					    <td width="20%" class="td_title" style="text-align: center"><!--加班事由-->
					    	加班事由
					    </td>
					    <td width="30%" class="td_type">
					    	<textarea style="width:400px;height:100px" id="APPLY_REMARK" name="APPLY_REMARK">${otApplyInfo.APPLY_OT_REMARK }</textarea>
					    </td>
									<td width="20%" class="td_title" style="text-align:center">
										附件上传
									</td>
									<td width="30%" class="td_type">
									    <input id="testFileInput_editLot" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${otApplyInfo.PERSON_ID}',
													formData:{ajax:1},
													queueID:'fileQueue_editLot',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_editLot,
													removeTimeout:1
												}"
											/><span id="fileNmae">${otApplyInfo.FILE_NAME}</span>
										  <div id="fileQueue_editLot" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value="${otApplyInfo.FILE_URL}"/>
										  <input type="hidden" id="fileName" name="fileName" value="${otApplyInfo.FILE_NAME}"/>
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_editLot').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_editLot').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</td>
								</tr>	
					<tr>
						<td colspan="4">
							<table class="user_table" width="100%">	
								<tr>
									<td class="td_title" rowspan="2" style="text-align:center;" width="25%">决裁线</td>
									<td class="td_title" style="text-align:center;" width="25%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="25%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="25%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLOTEditFirst()"/>)</td>
								</tr>
								<tr>
									<td colspan="3">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list_edit">
											<tbody>
											</tbody>
										</table>
									</td>	
								</tr>
							</table>
						</td>
					</tr>
					<!--
					
					<tr>
						<td colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" id="LaddAffirm_list">
								<tbody>
									<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
										<input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
										<c:if test="${affirmorListCnt == 1}">
											<input type="hidden" id="affirmorIdOnly" name="affirmorIdOnly" value="${affirmor.AFFIRMOR_ID }"/>	
										</c:if>
										<c:if test="${affirmorListCnt != 1}">
											<input type="hidden" id="affirmorIdOnly" name="affirmorIdOnly" value="0"/>
										</c:if>	
										<tr id="${affirmor.AFFIRMOR_ID}">
											<td class="td_type" style="text-align: center" width="37.5%">
												${j.index+1}
												<input type="hidden" id="essAffirmNo" name="essAffirmNo" 
												value="${affirmor.ESS_AFFIRM_NO},${affirmor.AFFIRMOR_ID},${affirmor.AFFIRM_LEVEL},${affirmor.AFFIRM_COM_TYPE}"/>
											</td>
											<td class="td_type" style="text-align: center" width="25%">
												[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
											</td>
											<td class="td_type" style="text-align: center" width="37.5%">
												<c:if test="${j.index+1 < affirmorListCnt || affirmorListCnt == 1}">
													<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加"
														border="0" align="absmiddle" style="cursor:hand" onclick="addLRowByIDEdit(this.id)"/>
												</c:if>
												<c:if test="${j.index+1 == affirmorListCnt}">
													&nbsp;&nbsp;&nbsp;
												</c:if>
												<c:if test="${affirmor.AFFIRM_COM_TYPE eq 'ADD_COM'}">
													<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/-.gif" title="删除"
														border="0" align="absmiddle" style="cursor:hand" onclick="deleteLRowByIDEdit(this)"/>
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<a id="onck" name="onck"  href="" lookupGroup="person"></a>
								</tbody>
							</table>
						</td>	
					</tr>
				--></table>
					  <input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
					  <input type="hidden" name="applyOtCount_1" id="applyOtCount_1" value="">
					  <a id="onck" name="onck"  href="" lookupGroup="person"></a>
			</div>
	  	</form>	
	</div>
</div>
<script type="text/javascript">
 
 
 
  
//获取该日期的加班类型
function getLOtApplyTypeEdit(){
	 
	var otApplyDate = $("#APPLY_OT_DATE_WQ",navTab.getCurrentPanel()).val();
	var applyTypeCode = document.getElementById("APPLY_TYPE_CODE_JUECAI");
    var adjustYn = document.getElementById("ADJUST_YN__ES");
    var adjustYn2 = document.getElementById("ADJUST_YN__O");
    var otApplyMinute2 = document.getElementById("OT_APPLY_MINUTE");
    var opts = document.getElementById("OT_APPLY_HOUR");
   
    var hour55 = document.getElementById("hour55");
    var hour44 = document.getElementById("hour44");
    
    var value ="";  
    var personid = $("#PERSON_ID",navTab.getCurrentPanel()).val();
    
	if(otApplyDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
			data:'DDATE_STR=' + otApplyDate +'&PERSON_ID='+personid,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				if(dateType == '1440'){       
					applyTypeCode.value="32";
			    	adjustYn.value="0";
			     
				 
			    	adjustYn.disabled=true;
			    	
			    	 adjustYn2.disabled=true;
			    		$("#hour55").hide();
						$("#hour44").show();
						$("#OT_APPLY_HOUR").val(""); //55
						$("#ADJUST_YN__DES").attr("disabled",true);
						$("#ADJUST_YN__O").attr("disabled",true);
				}else if(dateType == '1441'){
					applyTypeCode.value="33";
					$("#hour55").show();
					$("#hour44").hide();
					$("#OT_APPLY_HOUR_2").val(""); //44
					adjustYn2.disabled=false;
					 //opts.disabled=false;
		             otApplyMinute2.disabled=false;
					adjustYn.disabled=false;
				}else if(dateType == '1442'){
					applyTypeCode.value="34";
					 opts.disabled=false;
						$("#hour55").show();
						$("#hour44").hide();
						$("#OT_APPLY_HOUR_2").val(""); //44
		             otApplyMinute2.disabled=false;
					adjustYn.value="0";
					adjustYn2.disabled=true;
					adjustYn.disabled=true;
				}else{
					applyTypeCode.value="32";
			    	adjustYn.value="0";
			    	$("#hour55").show();
					$("#hour44").hide();
					$("#OT_APPLY_HOUR_2").val(""); //44
			    	 //opts.disabled=false;
		             otApplyMinute2.disabled=false;
		         	adjustYn2.disabled=true;
			    	adjustYn.disabled=true;
				}
				 var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;
				    
				    var OT_APPLY_HOUR_2   = document.getElementById("OT_APPLY_HOUR_2").value;
					if($("#OT_APPLY_HOUR").val()==null||$("#OT_APPLY_HOUR").val()==""){  
						otApplyHour =OT_APPLY_HOUR_2;
				       }
			 
				if((dateType=='1440' &&otApplyHour==2)||(dateType=='1441'&&otApplyHour==8)||(dateType=='1442'&&otApplyHour==8)){
					otApplyMinute2.value=0;
					otApplyMinute2.disabled=true;
				
				}else{
					otApplyMinute2.disabled=false;

				}
				 
				calLoTRemarkEdit();
				applyTypeCode.disabled=true;
			}
		});
	}
	//setTimeout("getLOtApplyTypeEdit()",1000);
}
</script>