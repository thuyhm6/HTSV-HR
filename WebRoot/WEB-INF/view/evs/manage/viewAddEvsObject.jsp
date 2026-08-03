<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//保存
	$("#viewAddEvsObject_save",$.pdialog.getCurrent()).click(function(){
		var affirmor0_personId = $("#affirmor0_personId",$.pdialog.getCurrent()).val();
		var affirmor1_personId = $("#affirmor1_personId",$.pdialog.getCurrent()).val();
		var affirmor2_personId = $("#affirmor2_personId",$.pdialog.getCurrent()).val();
		/* var affirmor3_personId = $("#affirmor3_personId",$.pdialog.getCurrent()).val(); */
		if(affirmor0_personId == '' || affirmor0_personId == null){
			alertMsg.warn("<spring:message code='evs.viewAddEvsObject.QINGXUANZEYIGEKAOHEZHE.a'/>");//请选择一个考核者
			return false;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
		  	{okCall:function(){
				$.ajax({
					type:'POST',
					url:"/evs/manage/addEvsObject",
					data: [{ name: 'RESUME_SEQ', value: '${RESUME_SEQ}' },
					       { name: 'affirmor0', value: affirmor0_personId },
					       { name: 'affirmor1', value: affirmor1_personId },
					       { name: 'affirmor2', value: affirmor2_personId }],
					       /* { name: 'affirmor3', value: affirmor3_personId }], */
					dataType:"json",
					cache: false,
					success: function(json){
						DWZ.ajaxDone(json);
						if (json.statusCode == DWZ.statusCode.ok){
							$.pdialog.closeCurrent();
						}
					},
					error: DWZ.ajaxError
				});	
		}});
		return false;
	});
});

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

function submitKeyClick_affirmorAddEvsAff(level,event){
	var empid=$("#affirmor" + level + "_key",$.pdialog.getCurrent()).val();
	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=super&pageNum=1"
							+'&seach_KEY='+empid
							+'&personidStr=viewAddEvsAffInfo'
							+'&empidStr=' + level));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#affirmor" + level + "_key",$.pdialog.getCurrent()).val(jsonObject.empName);
					$("#affirmor" + level + "_personId",$.pdialog.getCurrent()).val(jsonObject.personId);
					$("#affirmor" + level + "_post",$.pdialog.getCurrent()).html(jsonObject.POST_GRADE_NAME);
				}
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#affirmor" + level + "_personId",$.pdialog.getCurrent()).val("");
		$("#affirmor" + level + "_post",$.pdialog.getCurrent()).html("");
	}
 	}
};

</script>
<div class="pageContent">
	<div>
		<form id="viewAddEvsObjectForm" method="post" action="/evs/manage/addEvsObject" class="required-validate">
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="25%" class="td_title" style="text-align:right">
							<spring:message code="evs.viewAddEvsObject.KAOHEDUIXIANG.a"/><!--考核对象-->
						</td>
						<td width="75%" class="td_type">
					    	<input id="affirmor0_personId" value="" type="hidden"/>
							<input id="affirmor0_key" value="" type="text" size="20" onkeydown="submitKeyClick_affirmorAddEvsAff(0,event)" style="float:left;"/>
							<div id="affirmor0_post"></div>
						</td>
					</tr>
					<tr>
					    <td class="td_title" style="text-align:right">
					    	<spring:message code="evs.viewEvsAffirmorSetup.YICIPINGJIAREN.a"/><!--一次评价人-->
					    </td>
						<td class="td_type">
					    	<input id="affirmor1_personId" value="" type="hidden"/>
							<input id="affirmor1_key" value="" type="text" size="20" onkeydown="submitKeyClick_affirmorAddEvsAff(1,event)" style="float:left;"/>
							<div id="affirmor1_post"></div>
						</td>
					</tr>
					<tr>		
					    <td class="td_title" style="text-align:right">
					    	<spring:message code="evs.viewEvsAffirmorSetup.LAINGCIPINGJIAREN.a"/><!--二次评价人-->
					    </td>
						<td class="td_type">
					    	<input id="affirmor2_personId" value="" type="hidden"/>
							<input id="affirmor2_key" value="" type="text" size="20" onkeydown="submitKeyClick_affirmorAddEvsAff(2,event)" style="float:left;"/>
							<div id="affirmor2_post"></div>
						</td>
					</tr>
					<%-- <tr>
					    <td class="td_title" style="text-align:right">
					    	<spring:message code="evs.viewEvsAffirmorSetup.SANCIPINGJIAREN.a"/><!--三次评价人-->
					    </td>
						<td class="td_type">
					    	<input id="affirmor3_personId" value="" type="hidden"/>
							<input id="affirmor3_key" value="" type="text" size="20" onkeydown="submitKeyClick_affirmorAddEvsAff(3,event)" style="float:left;"/>
							<div id="affirmor3_post"></div>
						</td>
					</tr>		 --%>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" id="viewAddEvsObject_save">
									<spring:message code="ar.viewempcalender.title.save"/><!--保存-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" class="close">
									<spring:message code="ess.title.close"/><!--关闭-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
	</div>
</div>
