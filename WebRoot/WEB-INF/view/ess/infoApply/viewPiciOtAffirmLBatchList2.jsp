<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
//<!--

$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		//"bAutoWidth": false    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 440,
        "scrollX": true,
        "orderClasses": false
	});
});	
	function delOtApplyCallback(OP_FLAG,form,callback) {
		$("#BATCH_LOT_OP_FLAG").val(OP_FLAG);
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
		
		if(OP_FLAG ==1){
		  var ids= document.getElementsByName("c1");
		   var checked=false;
		  for(var i=0;i<ids.length;i++){
		 if(ids[i].checked){
		    
		    var   today=new   Date();      
            var yesterdayB_milliseconds=today.getTime()-2*1000*60*60*24;
            var   yesterday=new   Date();      
            yesterday.setTime(yesterdayB_milliseconds); 
            var strYear=yesterday.getFullYear(); 
            var strDay=yesterday.getDate();   
            var strMonth=yesterday.getMonth()+1; 
            var qianyitian = strYear+'-'+strMonth+'-'+strDay;
			var	APPLY_DATE = $("#APPLY_DATE"+ids[i].value).val();
			var	AFFIRM_FLAG = $("#valibl_value_AFFIRM_NO"+ids[i].value).val();
			var	otlength = $("#shenqingshichang"+ids[i].value).val();
			var PERSON_ID = document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+ids[i].value).value;
			var GYN;
			var affrimNo;
			
			if(AFFIRM_FLAG=="14014306"){
			alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.BUNENGZUOCHUSHIZHUANGTAIDESHENQING.a" />');//不能做初始状态的申请
			document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			return false;
			}
			if(AFFIRM_FLAG=="14014307"){
			  if(otlength=="0"){
				alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.QINGXUANZEZHENGQUEDEJIABANSHICHANG.a" />');//请选择正确的加班时长
				ids[i].value.focus();
				return false;
			  }
			}
			
			if(ids[i].value != null){
			   $.ajax({
						cache: false,
					    type: 'post',
						async:false,
				        url: "/ess/infoApply/getGradeYn",
						data: [{ name: 'PERSON_ID', value: PERSON_ID }],
						dataType:"json",
						success: function(data) {
						  GYN = data.GYN;
						}
				}); 
			}	
			if(ids[i].value != null){
			   $.ajax({
						cache: false,
					    type: 'post',
						async:false,
				        url: "/ess/infoApplyAttendance/getYesBefAffrimNo",
						data: [{ name: 'PERSON_ID', value: PERSON_ID }],
						dataType:"json",
						success: function(data) {
						  affrimNo = data.affrimNo;
						}
				}); }
		 if(GYN=='Y'){
		  if(APPLY_DATE==qianyitian){
			if(affrimNo=="14014306"){
			  alert('<spring:message code="ess.viewPiciOtAffirmLBatchList2.GZHIBUYUNXUDUIQIANYITIANCHUSHIZHUANGTAI.a" />');//G职不允许对前一天初始状态的数据做申请!
			 ids[i].value.focus();
			  return false;
			      
			}
			}
		  }
		  }
		}
  
		   
	     }
		
	    $form.attr("action","/ess/infoApply/delLOvertimeApplyInBatch");
	    var msg = "<spring:message code='js.upload.msg.confirmToDelete' />";//确定要删除吗？
	    if(OP_FLAG == 1){
	        var msg = "<spring:message code='alert.message.pa.insurance.confirmSubmit' />";//确定要提交吗？
	    }
	    alertMsg.confirm(msg,{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch(document.viewPiciOtAffirmLBatchList);
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
	        }});
		return false;
	}
	 

//-->

function xiujialeixing(id){
	
	if('0' == id){
		document.getElementById('viewApplyOtBatch').style.display = 'none';
		document.getElementById('closeApplyOt').style.display = 'none';
		document.getElementById('openApplyOt').style.display = 'block';
	}else{
		document.getElementById('viewApplyOtBatch').style.display = 'block';
		document.getElementById('closeApplyOt').style.display = 'block';
		document.getElementById('openApplyOt').style.display = 'none';
	}
}

function jsSelectItemByValue(objSelect, objItemText) {        
      //判断是否存在        
      var isExit = false;       
      for (var i = 0; i < objSelect.options.length; i++) { 
          if (objSelect.options[i].value == objItemText) {        
              objSelect.options[i].selected = true;    
              isExit = true;        
              break;        
          }        
      }                      
 }    
 //文本fill
 function textMuli(name,value){
  var ids = document.getElementsByName("c1");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    		document.getElementById(name+j).value=value;
				}
			}		  
		}
}
//下拉框多选
function selMuli(name,value){
 var ids = document.getElementsByName("c1");
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
	           var j=ids[i].value;
				if(ids[i].checked==true){
		    		var sel=document.getElementById(name+j);
		    		jsSelectItemByValue(sel,value);
				}
			}		  
		}
}
function fillItem(){
    var checked=false;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGXUANZEFANYINGJILU.b" />'); //请选择反应记录
			return false;
		}
  var reason=document.getElementById("reason").value;
  var otherReason=document.getElementById("otherReason").value;
  var fromTime=document.getElementById("fromTime").value;
  var APPLY_DATE=document.getElementById("APP_OT_DATE").value; 
  var toTime=document.getElementById("toTime").value;
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG").value;

  textMuli("reason",reason);  
  textMuli("otherReason",otherReason);  
  textMuli("fromTime",fromTime);  
  textMuli("APPLY_DATE",APPLY_DATE);  
  textMuli("toTime",toTime);  
  selMuli("AFFIRM_FLAG",fillAffirmFlag);  	

 calPoTLength();
}


//计算时长
function calPoTLength(){
	var ids = document.getElementsByName("c1");
    var checked=false;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGXUANZESHENQINGJILU.b" />'); //请选择申请记录
			return false;
		}
  if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			 if(ids[i].checked){
			        var j=ids[i].value;
					var	from_date = $("#APPLY_DATE"+j).val();
				    var cpnyId = document.getElementById("CPNY_ID").value;
				    var fromTime =  document.getElementById("fromTime"+j).value;
				    var toTime = document.getElementById("toTime"+j).value;
				    var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
				    var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
				    var SHIFT_NO = document.getElementById("SHIFT_NO"+j).value;
				
					if(from_date!=null&&from_date!=""){
				
						$.ajax({
							 cache: false,
							 type: 'post',
							 async:false,
							 url: "/ess/infoApply/getOtApplyLengthWq",
							 data: [
							        { name: 'fromTime', value: fromTime },
							        { name: 'toTime', value: toTime },
							        { name: 'FIRST_TIME', value: FIRST_TIME },
							        { name: 'LAST_TIME', value: LAST_TIME },
							        { name: 'from_date', value: from_date },
							        { name: 'SHIFT_NO', value: SHIFT_NO }],
							 dataType:"json",
							  success: function(data) {
							  var hour = data.OT_HOUR;
							  var min = data.OT_MINUTE;
							   document.getElementById('shenqingshichangText'+j).innerHTML = hour+"小时"+min+"分";
							   $("#shenqingshichang"+j).val((hour*60+min)/60);
							   $("#Lotlengthonehour"+j).val(hour);
			                    $("#Lotlengthonemin"+j).val(min);
							 }
						});
						}
		             }
		          }
		      }
}

function getWorkTime(){
	var ids = document.getElementsByName("c1");
  if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			        var j=ids[i].value;
					var	from_date = $("#APPLY_DATE"+j).val();
				    var cpnyId = document.getElementById("CPNY_ID").value;
					var SHIFT_NO=$("#SHIFT_NO"+j).val();
						$.ajax({
							 cache: false,
							 type: 'post',
							 async:false,
							 url: "/ess/infoApply/getOtApplyWorkTime",
							 data: [
							        { name: 'from_date', value: from_date },
							        { name: 'cpnyId', value: cpnyId },
							        { name: 'SHIFT_NO', value: SHIFT_NO}],
							 dataType:"json",
							  success: function(data) {
							   document.getElementById('workTime'+j).innerHTML = data.FIRST_TIME+"-"+data.LAST_TIME;
							   document.getElementById('fromTime'+j).value=data.FIRST_TIME;
							   document.getElementById('toTime'+j).value=data.LAST_TIME;
							 }
						});
		          }
		      }
}

function validateAffrim(){
   var ids= document.getElementsByName("c1");
		   var checked=false;
		  for(var i=0;i<ids.length;i++){
		 if(ids[i].checked){
			
			var AFFIRM_FLAG = document.getElementById('AFFIRM_FLAG'+ids[i].value).value;
			var PK_NO = ids[i].value;
			var affrimold;  
			
			if(ids[i].value != null){
			   $.ajax({
						cache: false,
					    type: 'post',
						async:false,
				        url: "/ess/infoApplyAttendance/getChechedAffrim",
						data: [{ name: 'PK_NO', value: PK_NO }],
						dataType:"json",
						success: function(data) {
						  affrimold = data.apply_affrim;
						}
				}); }
			if(affrimold=="14014306"){
			  if(AFFIRM_FLAG == "14014306"||AFFIRM_FLAG == "14014308"||AFFIRM_FLAG == "14014309" || AFFIRM_FLAG == "14014310" || AFFIRM_FLAG == "14014311" ||AFFIRM_FLAG == "14014312" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.CHUSHIZHUANGTAIZHINENGZUOBUMENSHENQING.a" />');//初始状态只能做部门申请
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).value='14014307'
			    return false;
			  }
			}
			if(affrimold=="14014307"){
			  if(AFFIRM_FLAG == "14014306"||AFFIRM_FLAG == "14014308"|| AFFIRM_FLAG == "14014309" || AFFIRM_FLAG == "14014311" ||AFFIRM_FLAG == "14014312" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.QINGCHONGXINXUANZESHENPIZHUANGTAI.a" />');//请重新选择审批状态,只能做修改和上取消
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).value='14014310'
			    return false;
			  }
			}
			if(affrimold=="14014308"){
			  if(AFFIRM_FLAG == "14014306"||AFFIRM_FLAG == "14014307"|| AFFIRM_FLAG == "14014309" || AFFIRM_FLAG == "14014310" ||AFFIRM_FLAG == "14014312"  ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.QINGXUANZEBUMENSHENQING.a" />');//请选择部门申请(后)
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).value='14014311';
			    return false;
			  }
			}
			if(affrimold=="14014309"){
			   if(AFFIRM_FLAG != "14014309" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.FEIFADESHENPICAOZUO.a" />');//非法的审批操作!
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    return false;
			   }
			}
			if(affrimold=="14014310"){
			   if(AFFIRM_FLAG != "14014310" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.FEIFADESHENPICAOZUO.a" />');//非法的审批操作!
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    return false;
			    }
			}
			if(affrimold=="14014311"){
			   if(AFFIRM_FLAG != "14014311" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.BUYUNXUZUOQITACAOZUO.a" />');//不允许做其他操作
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).value='14014311';
			    return false;
			   }
			}
			if(affrimold=="14014312"){
			   if(AFFIRM_FLAG != "14014311" ){
			    alertMsg.error('<spring:message code="ess.viewPiciOtAffirmLBatchList2.BUMENZHANGPIZHUNHOUZHIYUNXUZUOBUMENSHENQ.a" />');//部门长批准后只允许做部门申请(后)
			    document.getElementById('AFFIRM_FLAG'+ids[i].value).focus();
			     document.getElementById('AFFIRM_FLAG'+ids[i].value).value='14014311';
			    return false;
			   }
			}
		  }
		}
  
}


</script>
<div   id="viewApplyOtBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPiciOtAffirmLBatchList?firstFlag=N" rel="pagerForm" method="post"
		id="viewPiciOtAffirmLBatchList" name="viewPiciOtAffirmLBatchList">
		<div class="searchBar">
				<table class="searchContent">
				<tr>
					<c:if test="${authority ne '1'}">
					
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
					<td>
						${personInfo.EMPID }/${personInfo.LOCAL_NAME }
						<input type="hidden" id="dwz.person.empName" name="dwz.person.empName" value="${personInfo.PERSON_ID }"/>
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
					
					<td><!-- 姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" text="姓名"/>
					</td>
					<td colspan="4"> 
						<!--<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${personInfo.EMPID}" />
						</c:if>
						 <input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
						  </c:if>
					    -->
					    <input id="personId" name="dwz.person.personId"  type="hidden" lookupGroup="person"/>
						<input name="dwz.person.empName" type="text"   lookupGroup="person" style="float:left;"  value="${empName}"/>
						<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
						<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
						<input name="dwz.person.empInfo"  type="text" readonly lookupGroup="person" size="80" value="${empInfo}"/>
					</td>
				</tr>
				<tr>
					<td width="10%">
						 <!-- 日期 --><spring:message code="ar.attendanceView.viewNoSwipingCard.dateTime" /> 
					</td>
					<td width="20%">
						<input type="text" id="seach_applyBatchdate" name="seach_applyBatchdate" class="date" format="yyyy-MM-dd" readonly="true" value="${applyBatchdate}"  />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td width="10%"><!-- 审批状态 --><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" /> </td>
					<td width="20%">
						  <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td width="10%"> <!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu" /></td>
					<td width="20%">
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_ID" name="seach_GROUP_ID" parentNo="400223" selected="${GROUP_ID}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
				</tr>
				<tr>
					<td width="10%"><!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /></td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="10%"><!-- 班次--><spring:message code="ess.message.work_shift" /></td>
					<td width="60%">
					       <select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					            <option value=""><!-- 请选择 --><spring:message code="org.title.PLEASE_SELECT" /></option> 
								<c:forEach items="${shiftList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
									        ${item.SHIFT_NAME}
								</c:forEach>
							</select>
					 </td>
				</tr>
				<tr>
				<c:if test="${authority ne '1'}">
				   <td>
						<!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						${personInfo.DEPARTMENT }
					</td>
				</c:if>
				<c:if test="${authority eq '1'}">
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
				</c:if>
				<td>
				<input type="checkbox" name="seach_attenState" value="all" checked="checked"/> ALL
				</td>
			   </tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/>
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div>
  <table>
  <tr>
    <td id="openApplyOt" style="display:none">
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/+.gif" title="打开" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(1)"/>
    </td>
    <td id="closeApplyOt" >
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="关闭" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(0)"/>
    </td>
   </tr>
  </table>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
				   <td width="10%">
				              <!--  时间--><spring:message code="ar.viewarcardrecord.title.shijian" /> 
				   </td>
				   <td width="80%" colspan="3">
						<input type="text" id="APP_OT_DATE" name="APP_OT_DATE" class="date" format="yyyy-MM-dd" readonly="true" value=" " />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						<ait:time name="fromTime" spacing="30" />
						~
						<ait:time name="toTime" spacing="30"  />
				   </td>
			    </tr>
				<tr>
					<td width="10%"><!-- 原因 --><spring:message code="hrm.empinfo.reason" /> </td>
					<td width="40%">
					<ait:SelectSyCodeCombinByCpnyID name="reason" combinParentNo="14014313"   cnpyID="${LoginUser.cpnyId}"  limit="all"/>
				     <input type="text" id="otherReason" name="otherReason"  value=""/>
					</td >
					<td width="10%"><!-- 审批状态 --><spring:message code="ess.infoApply.approval_status" />  </td>
					<td width="40%">
						<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem();"><span><!-- 全部反应 --><spring:message code="hrm.approve.ALL_REACTION" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
     <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(oTAffirmList)}</div>
	<ul class="toolBar">
		<!--
		<li><a class="buttonActive" onclick="delOtApplyCallback(0,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>删除</span></a></li>
		-->
		<li><a class="buttonActive" onclick="delOtApplyCallback(1,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span><!-- 保存 --><spring:message code="ess.message.save" /></span></a></li>
		<li><a class="buttonActive" onclick=""><span><!-- 导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
		<li><a class="buttonActive" onclick=""><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING" /></span></a></li>
			
	</ul>
</div>
	<form name="delOtApplyAffirmForm" id="delOtApplyAffirmForm" method="post" action="/ess/infoApply/delLOvertimeApplyInBatch" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="orderList" width="2000">
			<thead>
				<tr>
				    <th  width="1%" ><!--NO-->
						NO
					</th>
					<th width="1%" >
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th  width="1%"><!--状态-->
						<spring:message code="org.title.status	" />
					</th>
					<th ><!--姓名-->
						<spring:message code="org.title.LOCAL_NAME" />
					</th>
				    <th ><!--社号-->
						<spring:message code="org.title.EMPID" />
					</th>
					<th  ><!--部门名-->
						<spring:message code="hr.viewCondSql.title.BUMENMINGCHENG" />
					</th>
					<th ><!--职级-->
						<spring:message code="hr.viewCondSql.title.ZHIJI" />
					</th>
					<th ><!--日期-->
						<spring:message code="pa.salary.title.date" />
					</th>
					<th ><!--星期-->
						<spring:message code="ess.infoApply.week" />
					</th>
					<th ><!--类型-->
						<spring:message code="ar.viewarcardrecord.title.leixing" />
					</th>
					<th ><!--考勤-->
						<spring:message code="ess.attendance.state" />
					</th>
					<th ><!--班组-->
						<spring:message code="hr.viewPersonalInfo.title.banzu" />
					</th>
					<th  ><!--班次-->
						<spring:message code="ess.message.work_shift" />
					</th>
					<th ><!--工作形态-->
						<spring:message code="ess.infoApply.WORKTYPE" />
					</th>
					<th ><!--进门-->
						<spring:message code="ar.viewarcardrecord.title.jinmen" />
					</th>
					<th ><!--出门-->
						<spring:message code="ar.viewarcardrecord.title.chumen" />
					</th>
					<th ><!--开始-->
						<spring:message code="ar.viewshift.title.start" />
					</th>
					<th ><!--结束-->
						<spring:message code="org.title.END" />
					</th>
					<th ><!-- 加班时间-->
					         <spring:message code="hrm.approve.PRINTING" />
					</th>
					<th ><!-- 中夜班津贴  -->
					         <spring:message code="ar.viewShiftParameter.ZHONGYEBANJINTIE.b" />
					</th>
					<th ><!--原因-->
						<spring:message code="hrm.empinfo.reason" />
					</th>
					<th ><!--其他原因-->
						<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QITAYUANYIN.b" />
					</th>
					<th ><!--审批状态-->
						<spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" />
					</th>
					<th ><!--加班合计-->
						<spring:message code="ess.title.JIABANHEJI" />
					</th>
					<th ><!--平时-->
						<spring:message code="ar.viewArOvertimeManagentFast.PINGSHI.b" />
					</th>
					<th ><!--周末-->
						<spring:message code="ar.viewitemparameter.title.zhoumo" />
					</th>
					<th ><!--法定节假日-->
						<spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" />
					</th>
					<th ><!--综合加班-->
						<spring:message code="ar.viewAdjustLeaveTSTOBatchList.ZONGHEJIABAN.b" />
					</th>
					<th ><!--月平均-->
						<spring:message code="ar.viewAdjustLeaveTSTOBatchList.YUEPINGJUN.b" />
					</th>
					<th ><!--加班上限-->
						<spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHANGXIAN.b" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otApply.AFFIRM_FLAG eq '14014307' || otApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '14014309' || otApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="c1${otApply.APPLY_NO}" name="c1" value="${otApply.APPLY_NO}" />
					         <input type="hidden" id="CPNY_ID" name="CPNY_ID" value="TSTO"/>
					         <input id="DATE_TYPE${otApply.APPLY_NO}" name="DATE_TYPE${otApply.APPLY_NO}" type="hidden"  value="${otApply.DATE_TYPE}"/>
						     <input id="UNIT${otApply.APPLY_NO}" name="UNIT${otApply.APPLY_NO}" type="hidden"  value="${otApply.UNIT}"/>
						     <input id="STATUS_CODE${otApply.APPLY_NO}" name="STATUS_CODE${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_CODE}"/>
						     <input id="STATUS_NAME${otApply.APPLY_NO}" name="STATUS_NAME${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_NAME}"/>
						     <input id="IWEEK${otApply.APPLY_NO}" name="IWEEK${otApply.APPLY_NO}" type="hidden"  value="${otApply.IWEEK}"/>
						     <input id="LOCK_YN${otApply.APPLY_NO}" name="LOCK_YN${otApply.APPLY_NO}" type="hidden"  value="${otApply.LOCK_YN}"/>
					    </td>
					    <td  style="text-align: center">
					      ${otApply.Confirm_Flag}
					    </td>
					    <td  style="text-align: center">
					          <!-- [max=true, mask=true, maxable=true  minable=true, resizable = true ,drawable=true ] -->
                              <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otApply.EMPID}&LOCAL_NAME= ${otApply.LOCAL_NAME}" 
                              target="dialog" style="color: blue;"  title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />"   [ mask=true ] width="1000" height="300"> 
                              ${otApply.LOCAL_NAME}</a>
                              <input id="dwz.person.AFFIRMOR_IDApplyLeave${otApply.APPLY_NO}" name="personid${otApply.APPLY_NO}" type="hidden"  value="${otApply.PERSON_ID}"/>
					    </td>
					    <td style="text-align: center">
					     <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otApply.EMPID}&LOCAL_NAME= ${otApply.LOCAL_NAME}" 
					     target="dialog" style="color: blue;" title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />" title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />"   [ mask=true ] width="1000" height="300"> 
					     ${otApply.EMPID}</a>
					    </td>
					    <td style="text-align: center">
					     <input id="DEPTNO${otApply.APPLY_NO}" name="DEPTNO${otApply.APPLY_NO}" type="hidden"  value="${otApply.DEPTNO}"/>
					    ${otApply.DEPARTMENT}
					    </td>
					    <td   style="text-align: center">
					       <input id="POST_GRADE_NO${otApply.APPLY_NO}" name="POST_GRADE_NO${otApply.APPLY_NO}" type="hidden"  value="${otApply.POST_GRADE_NO}"/>
					    ${otApply.POST_GRADE_NAME}
					    </td>
					    <td style="text-align: center">
					       <input type="text" id="APPLY_DATE${otApply.APPLY_NO}" name="APPLY_DATE${otApply.APPLY_NO}" class="date" 
					    format="yyyy-MM-dd" readonly="true" value="${otApply.AR_DATE_STR}" size="10"  openChange="true"/>
					    </td>
					    <td  style="text-align: center">${otApply.WEEKDAY}</td>
					     <td style="text-align: center">
					       ${otApply.TYPENAME}
					      <input type="hidden" id="dateType${otApply.APPLY_NO}" name="dateType${otApply.APPLY_NO}" value="${otApply.DATE_TYPE}"/>
					     </td>
					    <td style="text-align: center">
					    ${otApply.KAOQINITEM}
					       <input type="hidden" id="ITEM_NO${otApply.APPLY_NO}" name="ITEM_NO${otApply.APPLY_NO}" value="${otApply.ITEM_NO}"/>
					       <input type="hidden" id="APPLY_TYPE_CODE${otApply.APPLY_NO}" name="APPLY_TYPE_CODE${otApply.APPLY_NO}" value="${otApply.APPLY_TYPE_CODE}"/>
					    </td>
					    <td  style="text-align: center">${otApply.GROUPNAME}
					     <input id="GROUP_ID${otApply.APPLY_NO}" name="GROUP_ID${otApply.APPLY_NO}" type="hidden"  value="${otApply.GROUP_ID}"/>
					    </td>
					    <td  style="text-align: center" >
					        <select name="SHIFT_NO${otApply.APPLY_NO}" id="SHIFT_NO${otApply.APPLY_NO}" onchange="getWorkTime();">
					            <option value=""><spring:message code='hr.viewCondSql.title.QINGXUANZE' /><!--请选择--> </option>
								<c:forEach items="${shiftList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq otApply.SHIFT_NO}">selected</c:if>
											>
									        ${item.SHIFT_NAME}
								</c:forEach>
							</select>
							<input type="hidden" id="FIRST_TIME${otApply.APPLY_NO}" name="FIRST_TIME${otApply.APPLY_NO}" value="${otApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME${otApply.APPLY_NO}" name="LAST_TIME${otApply.APPLY_NO}" value="${otApply.LAST_TIME}"/> 
					    </td>
					    <td style="text-align: center">  
					        <div  id="workTime${otApply.APPLY_NO}">
					          ${otApply.FROM_TIME_FIRST}-${otApply.TO_TIME_FIRST}
					        </div>
					    </td>
					      <td style="text-align: center">${otApply.INDOOR_DATE}</td>
					    <td  style="text-align: center">${otApply.OUTDOOR_DATE}</td>
					    <td style="text-align: center">
					        <ait:time name="fromTime${otApply.APPLY_NO}"  spacing="30"   selected="${otApply.FROM_TIME}" onChange="calPoTLength();"/>
					    </td>
					    <td style="text-align: center">
					         <ait:time name="toTime${otApply.APPLY_NO}" spacing="30"   selected="${otApply.TO_TIME}" onChange="calPoTLength();"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="shenqingshichangText${otApply.APPLY_NO}">${otApply.APPLY_LENGTH} </div>
							<input type="hidden" id="shenqingshichang${otApply.APPLY_NO}" name="APPLY_LENGTH${otApply.APPLY_NO}" value="${otApply.APPLY_LENGTH2}" />
							<input type="hidden" id="Lotlengthonehour${otApply.APPLY_NO}" name="Lotlengthonehour${otApply.APPLY_NO}" value=""/> 
				            <input type="hidden" id="Lotlengthonemin${otApply.APPLY_NO}" name="Lotlengthonemin${otApply.APPLY_NO}" value=""/> 
				         </td>
					    <td  style="text-align: center">
					         <input style="width: 100%;"  title="${otApply.ALLOWANCE}" id="allowance${otApply.APPLY_NO}" name="allowance${otApply.APPLY_NO}" value="${otApply.ALLOWANCE}"  type="text"/>
					    </td>
					    <td style="text-align: center">
					        <ait:SelectSyCodeCombinByCpnyID name="reason${otApply.APPLY_NO}" combinParentNo="14014313"   cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${otApply.LEAVEREASON}"/>
					    </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" id="otherReason${otApply.APPLY_NO}" name="otherReason${otApply.APPLY_NO}" value="${otApply.REASON_OTHER}"  type="text"/>
					    </td>
					    <td  style="text-align: center">
					         <ait:SelectSyCodeCombinByCpnyID name="AFFIRM_FLAG${otApply.APPLY_NO}" combinParentNo="14014304" selected="${otApply.AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all" onChangeName="validateAffrim();"/>
					    </td>
					    <td  style="text-align: center">
					      ${otApply.OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					       ${otApply.WEEKDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					        ${otApply.WEEKEND_OT_TOTAIL}
					    </td>
					    <td style="text-align: center">
					         ${otApply.HOILDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					         ${otApply.COMPRE_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					          ${otApply.OT_TOAVG}
					    </td>
					      <!-- 加班上限完了再说 -->
					    <td  style="text-align: center">	
					       
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_LOT_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
	<div style="visibility: hidden;">
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</div>