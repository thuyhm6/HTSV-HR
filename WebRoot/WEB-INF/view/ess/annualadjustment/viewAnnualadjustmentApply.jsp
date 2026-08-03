<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateannuAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	 
	return false;
}
//注意input的id和tr的id要一样
function addRowByIDL(currentRowID){
	var count = parseInt($("#affirmCountAnnual").val());
	str = '<tr id = "rowIdApplyAnnual'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.annupersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.annuempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_annu(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDL(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addAffirm_listL.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyAnnualLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyAnnual" + currentRowID).after(str);
	$("[id='dwz.person.annuempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyAnnualLevel();
	$("#affirmCountAnnual").val(++count) ;
}


function addRowByIDApplyAnnualFirst(){
	var count = parseInt($("#affirmCountAnnual").val());
	str = '<tr id = "rowIdApplyAnnual'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.annupersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.annuempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_annu(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addAffirm_listL.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyAnnualLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addAffirm_listL");

   	if(tb2.rows.length == 1){
   		$("#addAffirm_listL").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.annuempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyAnnualLevel();
	$("#affirmCountAnnual").val(++count) ;
}

//修改决裁者等级
function changeApplyAnnualLevel(){
	var tb2 = document.getElementById("addAffirm_listL");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_annu(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="annupersonId"+empIdStr.substring(11);
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
					  	$("[id='dwz.person.annuempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.annupersonId" + index + "']").val( jsonObject.personId);
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

function submitKeyClick_apply(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="PERSON_ID";
		var empNameStr="empName_apply";
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?limit=ar&navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPersonAuthority"/>');
						}
						if(jsonObject.perCnt>1 ){
							alertMsg.error('请填写准确工号！');
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								uploadfy_destory();
								$.pdialog.reload("/ess/annualadjustment/viewAnnualadjustmentApply" + "?navTabId=" + "ess0303" + "&PERSON_ID=" + jsonObject.personId + "&APPLY_TYPE_NO=" + "216691");
							}
						}
					},
			error: DWZ.ajaxError
		});
    }
 }
function uploadifySuccess_annual(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",$.pdialog.getCurrent()).html();
	  var fileUrl = $("#fileUrl",$.pdialog.getCurrent()).val();
	  var fileName = $("#fileName",$.pdialog.getCurrent()).val();
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
	  $("#fileNmae",$.pdialog.getCurrent()).html(files);
	  $("#fileUrl",$.pdialog.getCurrent()).val(fileUrl);
	  $("#fileName",$.pdialog.getCurrent()).val(fileName);
}
//-->
</script>


<div class="pageContent"  layouth="10">
 <div>
	<form id="addOtAffirmInfo" method="post" action="/ess/annualadjustment/addAnnualadjustmentApply" class="pageForm required-validate" 
	onsubmit="return validateannuAffirmCallback(this,dialogAjaxDoneWithForm);">
	   <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="ess.infoApply.title.apply"/><!--申请-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>    
		<div>
			<!-- 显示年假与调休 -->
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<!-- 显示年假与调休 -->
							<table  class="user_table" width="100%">
							   <thead>
								   	<tr>
								   		<td width="50%" class="td_title" colspan="6" style="text-align:center">年假</td>
								   		<td width="50%" class="td_title" colspan="3" style="text-align:center">调休</td>
								   	</tr>
							   </thead>
							   <tbody>
								   	<tr>
								   		<td class="td_title" colspan="2" style="text-align:center">法定年假</td>
								   		<td class="td_title" colspan="2" style="text-align:center">福利年假</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">调休时数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用时数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余时数</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center">本年年假</td>
								   		<td class="td_type td_center">移年年假</td>
								   		<td class="td_type td_center">福利年假</td>
								   		<td class="td_type td_center">福利年假调整</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TOT_VAC_CNT1 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.LAST_YEAR_VAC1 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TOT_VAC_CNT2 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.ADD_VAC }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.USE_VAC }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.SURPLUS_VAC }</td>
								   		<td class="td_type td_center" width="11%"><fmt:formatNumber type="number" value="${empVacInfo.TX_TOTAL * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="11%"><fmt:formatNumber type="number" value="${empVacInfo.TX_USE * 8 }" maxFractionDigits="1"/></td>
								   		<td class="td_type td_center" width="12%"><fmt:formatNumber type="number" value="${empVacInfo.TX_SHENGYU * 8 }" maxFractionDigits="1"/></td>
								   	</tr>
							   	</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="100%" class="td_title" colspan="8" style="text-align: center">年假调整申请</td>
								</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="30%" class="td_type" colspan="3">
				   <c:if test="${authority ne '1'}">
									<input id="empId_apply_b" name="dwz.person.empId1"
										value="${personInfo.EMPID}" type="hidden" />
						 
						            ${personInfo.EMPID}
						 </c:if> <c:if test="${authority eq '1'}">
									<input id="empId_apply" name="dwz.person.empId1"
										value="${personInfo.EMPID}" type="text" lookupGroup="person"
										onkeydown="submitKeyClick_apply(this,event)" class="required" rel=""/>

								</c:if> <input id="empName_apply" name="empName_apply" type="hidden"
								value="${personInfo.LOCAL_NAME}" readonly
								style="border:0;background:transparent;" type="text"
								lookupGroup="person" />${personInfo.LOCAL_NAME}
								<input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
								value="${personInfo.PERSON_ID}" lookupGroup="person"  rel="submitKeyClick_apply_assigmentLeave"/>
				    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30"
						   value="216691" />		   
				</td>
				<td width="20%" class="td_title" ><!-- 部门 --> <spring:message code="public.title.deptName"/> </td>	
				<td width="30%" class="td_type" >${personInfo.DEPARTMENT } <!--  <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPTNO_NAME }"/> --> </td>
			</tr>
			<tr>
				<td width="20%" class="td_title">年假调整天数</td>
				<td width="30%" class="td_type" colspan="3">
				    <input type="text" id="FULINIANJIATIAOZHENGTIANSHU" name="FULINIANJIATIAOZHENGTIANSHU" class="required"  size="7">
				</td>
				<td width="20%" class="td_title">申请日期</td>
				<td width="30%" class="td_type">
				    ${nowday }
				    <input type="hidden" id="SHENQINGDAY" name="SHENQINGDAY" value="${nowday}" >
				</td>    
			</tr>		
								<tr>
			    <td width="20%" class="td_title"> 申请事由</td>
			    <td width="80%" class="td_type" colspan="3"><textarea name="APPLY_REMARK"  style="width:400px;height:100px"></textarea></td>
									<td width="20%" class="td_title" style="text-align:center">
										附件上传
									</td>
									<td width="80%" class="td_type">
									    <input id="testFileInput_annual" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${personInfo.PERSON_ID}',
													formData:{ajax:1},
													queueID:'fileQueue_annual',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_annual,
													removeTimeout:1
												}"
											/><span id="fileNmae"></span>
										  <div id="fileQueue_annual" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value=""/>
										  <input type="hidden" id="fileName" name="fileName" value=""/>
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_annual').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_annual').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</td>
								</tr>		
			</table>
						</td>
					</tr>
					<tr>
						<td>
			
				    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				      <tr>
						<td class="td_title" width="11%" style="text-align: center"><!-- 决裁线 -->
						决裁线
						</td>
						<td colspan="7">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listL">
									<tr>
										<td class="td_title" style="text-align:center;" >决裁等级</td>
										<td class="td_title" style="text-align:center;">决裁者</td>
										<td class="td_title" style="text-align:center;">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyAnnualFirst()"/>)
										</td>
									</tr>
									<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
										<tr id="rowIdApplyAnnual${j.index }">
											<td class="td_type" width="100" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
											<td class="td_type" width="180" style="text-align: center">
												[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}
												<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/>
											</td>
											<td class="td_type" width="100" style="text-align: center">
												<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
													<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加决裁者"
														border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDL(${j.index })"/>
											</td>
										</tr>			
									</c:forEach>
							</table>
							<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_apply_assigmentLeave_affirm"></a>
						</td>
					</tr>
			      </table>
    			<input type="hidden" name="affirmCountAnnual" id="affirmCountAnnual" value="${affirmorListCnt }">
    		</td>
    	</tr>
    </table>
	</div>
  </form>
  </div>
</div>