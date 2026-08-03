<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

//加载样式
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		//"bAutoWidth": false    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 200,
        "orderClasses": false
	});
});
//提交
function validateAffirmOtLimitCallbackEssShenpi(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("c2");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    var LOCK_YN =  $("#LOCK_YN1").val();
	if(LOCK_YN =='Y'){
		alertMsg.error("本月加班上限已锁定！！"); 
		return false;
	} 
	calculateAvg();//计算
    var GPAvg = $("#SHEN_GPAvgTEXT").val();
	var PAvg = $("#SHEN_PAvgTEXT").val();
	var GAvg = $("#SHEN_GAvgTEXT").val();
	if(parseFloat(GPAvg)>36){
	   alertMsg.error("部门平均值超出了36小时！"); 
		return false;
	}
	if(parseFloat(PAvg)>36){
	   alertMsg.error("部门P职平均值超出了36小时！"); 
		return false;
	}
	if(parseFloat(GAvg)>22){
	   alertMsg.error("部门G职平均值超出了22小时！"); 
		return false;
	}
   if(checked){
		$.each($("input[name='c2']"),function(i, obj) {
			 if (obj.checked) {
			     var j = obj.value;
			     var AFFIRM_FLAG =  $("#valibl_value_AFFIRM_NO1"+j).val();
			     var OLD_AFFIRM_FLAG =  $("#OLD_AFFIRM_FLAG"+j).val();
			     if(AFFIRM_FLAG =='14014307'){
			       $("#valibl_value_AFFIRM_NO1"+j).val('14014308');
			       $("#valibl_input_AFFIRM_NO1"+j).val('部门长批准');
			     }
			 }
		});
	}
    
    $form.attr("action","/ess/infoApply/approveOtLimitBatchShenPi?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");
    var msg = "确定要部门长审批吗？";
	 alertMsg.confirm(msg,{okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"), 
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
					    navTabSearch(document.viewOverTimeLimtShenPiList);
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

//限制文本框的输入
function checkNumGP(TimeVlue,applyno){ 
   $('#c2'+applyno).attr('checked','checked');     
    if(parseFloat(TimeVlue)>36){        
	    alertMsg.error("超出了36小时"); 
	    $('#FINAL_ADJUST_TIME_shen'+applyno).val('');   
    }
}
//保留小数位
function round2(Num1,Num2){    
     if(isNaN(Num1)||isNaN(Num2)){        
         return(0);     
     }else{     
         return(Num1.toFixed(Num2));       
     }
}

function calculateAvg(){
    var adjst_time_shen;//调整时间
    var count_shen = 0;//整个部门下所有人累计的和
    var g_count_shen = 0;//G职级和
    var p_count_shen = 0;//P职和
    var TOTAL_G_shen = 0;//G职级人数
	var TOTAL_P_shen = 0;//P职级人数
    var GPAvg_shen;//部门均值
    var  GAvg_shen;//G职级均值
    var  PAvg_shen;//p职级均值
	var total_shen = parseFloat($("#total_shen").val());//部门下人数（含G、NULL、P）
	if(isNaN(total_shen)){
	  return;
	}else{
	  total_shen = total_shen;
	}
    var ids= document.getElementsByName("c2");
	// 分别统计G、P职级的人数，对应的和
	for(var i=0;i<ids.length;i++){
		 var j = ids[i].value;
		 if($('#REMOVE_ADJUST_TIME_shen'+j).val()!= null && $('#REMOVE_ADJUST_TIME_shen'+j).val() != ''){
		    adjst_time_shen = $('#REMOVE_ADJUST_TIME_shen'+j).val();
		 }else{
		    adjst_time_shen=$('#FINAL_ADJUST_TIME_shen'+j).val();
		 }
		
		 var DIV_GP_shen=$('#DIV_GP_shen'+j).val();  
		 if(DIV_GP_shen == 'G') {
		   TOTAL_G_shen = TOTAL_G_shen+1;
		   g_count_shen = g_count_shen+parseFloat(adjst_time_shen);
		 }
		 if(DIV_GP_shen == 'P') {
		   TOTAL_P_shen = TOTAL_P_shen+1;
		   p_count_shen = p_count_shen+parseFloat(adjst_time_shen);
		 }
		 count_shen = count_shen+parseFloat(adjst_time_shen);
		 
	}
	//部门均值
   if(total_shen != 0){
      GPAvg_shen = round2(count_shen/total_shen,2);
   }else{
      GPAvg_shen=0;
    }
    //G均值 
   if(parseInt(TOTAL_G_shen) != 0){
      GAvg_shen = round2(g_count_shen/parseInt(TOTAL_G_shen),2);
   }else{
      GAvg_shen=0;
   }
   //P均值
   if(parseInt(TOTAL_P_shen) != 0){
      PAvg_shen = round2(p_count_shen/parseInt(TOTAL_P_shen),2);
   }else{
      PAvg_shen=0;
   }
   
    alert('当前共'+total_shen+'人,G职'+TOTAL_G_shen+'人,P职'+TOTAL_P_shen+'人;部门均值'+GPAvg_shen+'小时,G职部门均值'+GAvg_shen+'小时,P职部门均值'+PAvg_shen+'小时！');
   $("#SHEN_GPAvg").html(GPAvg_shen);
   $("#SHEN_GPAvgTEXT").val(GPAvg_shen);
   $("#SHEN_GAvg").html(GAvg_shen);
   $("#SHEN_GAvgTEXT").val(GAvg_shen);
   $("#SHEN_PAvg").html(PAvg_shen);
   $("#SHEN_PAvgTEXT").val(PAvg_shen);
}


function vildAffriorSelected1(){
  var checked=false;
	var ids= document.getElementsByName("c2");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
	
	
   if(checked){
		$.each($("input[name='c2']"),function(i, obj) {
			 if (obj.checked) {
			     var j = obj.value;
			     var AFFIRM_FLAG =  $("#valibl_value_AFFIRM_NO1"+j).val();
			     var OLD_AFFIRM_FLAG =  $("#OLD_AFFIRM_FLAG"+j).val();
			     if(OLD_AFFIRM_FLAG =='14014307'){
			        if(AFFIRM_FLAG != '14014308'){
			          alertMsg.error("请进行部门长批准");
			          $("#valibl_value_AFFIRM_NO1"+j).val("14014308");
			          $("#valibl_input_AFFIRM_NO1"+j).val("部门长批准");
			          }
			     }
			 }
		});
	}
 

}



 //文本fill
 function textMuli2(name,value){
  var ids = document.getElementsByName("c2");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    	 document.getElementById(name+j).value=value;
				}
			}		  
		}
}

function fillItem1(){
    var checked=false;
		var ids= document.getElementsByName("c2");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择反应记录'); 
			return false;
		}
	var fillAffirmFlag = $("#viewOverTimeLimtShenPiList select[id='FILLAFFIRMFLAG1']").val();
    var obj = document.getElementById("FILLAFFIRMFLAG1");
    var txt = obj.options[obj.selectedIndex].text;
    if(fillAffirmFlag != ''){
       textMuli2("valibl_value_AFFIRM_NO1",fillAffirmFlag);  	
       textMuli2("valibl_input_AFFIRM_NO1",txt);
    }else{
       alertMsg.error('请选择全部反应的审批状态！');
       return;
    }
}

</script>
         
<div id="viewOverTimeLimtShenPiList"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOverTimeLimtShenPiList?firstFlag=N"  method="post"
		id="viewOverTimeLimtShenPiList" name="viewOverTimeLimtShenPiList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					        年月
					</td>
					<td>
						<input type="text" id="seach_AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" value="${AR_MONTH}" />
					</td>
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtShenPiList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtShenPiList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!-- 审批状态： --> 
						  审批状态
					</td>
					<td > 
					     <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" id="seach_AFFIRM_FLAG_P" combinParentNo="14014304" selected="${AFFIRM_FLAG}" exclude="14014309,14014310,14014311,14014312"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td><!-- 职群--> 
						 职群
					</td>
					<td > 
					     <select id="seach_DIV_GP_P" name="seach_DIV_GP">
					       <option value="">请选择</option>
					       <option value="G"  <c:if test="${DIV_GP eq 'G'}">selected</c:if>>G职</option>
					       <option value="P"  <c:if test="${DIV_GP eq 'P'}">selected</c:if>>P职</option>
					     </select>
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
<div id="viewOverTimeLimtShenPiList" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			<tr>
			   <td width="20%">审批状态</td>
			   <td width="80%">
					 <ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG1" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  exclude="14014306"   cnpyID="${LoginUser.cpnyId}"  limit="all"/>
			   </td>
			</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem1();"><span>全部反应</span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" > 
<div class="formBar">
       <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 10px; line-height: 30px;">Total:${fn:length(otLimtList)}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<ul>
		<li>
			<div class="subBar">
				  <div class="buttonActive">
						<a class="update" onclick="validateAffirmOtLimitCallbackEssShenpi('approveOtLimitBatchShenPi',DWZ.ajaxDone,'1')" href="#" >
		         <span>保存</span></a>
                 </div>
			</div>	
		</li>
		<li>
			<div class="subBar">
				  <div class="buttonActive">
						<a class="update" onclick="calculateAvg()" href="#" >
		         <span>计算平均值</span></a>
                 </div>
			</div>	
		</li>
	</ul>
</div>

<form name="approveOtLimitBatchShenPi" id="approveOtLimitBatchShenPi" method="post" action="/ess/infoApply/approveOtLimitBatchShenPi" 
	  onsubmit="return validateAffirmOtLimitCallbackEssShenpi(this, navTabAjaxDone);">  
	<table class="orderList" width="100%"  > 
		<thead>
				<tr>
				    <th  ><!--NO-->
						NO
					</th>
					<th  >
				    	<input type="checkbox" class="checkboxCtrl" group="c2" />
				    </th>
					<th width="8%" >
				    	月份
				    </th>
					<th width="8%" style="text-align: center"><!--申请人-->
						姓名
					</th>
				    <th   style="text-align: center"><!--社号-->
						社号
					</th>
					<th   style="text-align: center"><!--部门-->
						部门
					</th>
					<th   style="text-align: center"><!--部门-->
						职级
					</th>
					<th style="text-align: center" width="10%" ><!--月初初始值-->
						月初默认值
					</th>
					<th style="text-align: center" width="10%" ><!--月初加班上限-->
						月初加班上限
					</th>
					<th width="10%" style="text-align: center" ><!--月终调整值-->
						月终调整值
					</th>
					<th  style="text-align: center"><!--修正原因-->
						修正原因
					</th>
					<th  width="10%" style="text-align: center"><!--审批状态-->
						审批状态
					</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otLimtList}" var="otLimt" varStatus="i">	 
					<tr target="sid" rel="${admin.personId}" >
					    <input type="hidden" id="LOCK_YN1" name="LOCK_YN" value="${otLimt.LOCK_YN}" />
					     <input type="hidden" id="total_shen" name="total" value="${fn:length(otLimtList)}" />
					    <td    style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="c2${otLimt.APPLY_NO}" name="c2" value="${otLimt.APPLY_NO}" />
					    </td>
					    <td  style="text-align: center">
					        ${otLimt.AR_MONTH_STR}
					    </td>
					    <td  style="text-align: center">
                            ${otLimt.LOCAL_NAME}
					    </td>
					    <td  style="text-align: center">
					        ${otLimt.EMPID}
					    </td>
					    <td  style="text-align: center">
					        ${otLimt.DEPARTMENT}
					    </td>
					    <td style="text-align: center">
					        ${otLimt.POST_GRADE_NAME}
					    </td>
					     <td style="text-align: center">
					        ${otLimt.DEFAULT_TIME}
					    </td>
					    <td  style="text-align: center">
					       <input id="FINAL_ADJUST_TIME_shen${otLimt.APPLY_NO}" name="FINAL_ADJUST_TIME${otLimt.APPLY_NO}" value="${otLimt.FINAL_ADJUST_TIME}" min="-99999999" type="text" style="width: 100%" onkeyup="checkNumGP(this.value,${otLimt.APPLY_NO});"/>
					         <input id="DIV_GP_shen${otLimt.APPLY_NO}" name="DIV_GP${otLimt.APPLY_NO}" value="${otLimt.DIV_GP}"  type="hidden" />
					    </td>
					     <td  style="text-align: center">
					       ${otLimt.REMOVE_ADJUST_TIME}
					       <input id="REMOVE_ADJUST_TIME_shen${otLimt.APPLY_NO}"  value="${otLimt.REMOVE_ADJUST_TIME}"   type="hidden" />
					    </td>
					    <td  style="text-align: center">
					      <input id="ADJUST_RESON${otLimt.APPLY_NO}" name="ADJUST_RESON${otLimt.APPLY_NO}" value="${otLimt.ADJUST_RESON}" onkeyup="$('#c2${otLimt.APPLY_NO}').attr('checked','checked');" type="text" style="width: 100%"/>
					    </td>
					    <td style="text-align: center">
					    <input style="text-align: center" id="valibl_input_AFFIRM_NO1${otLimt.APPLY_NO}" type="text" size="20" value="${otLimt.AFFIRMNAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NO1${otLimt.APPLY_NO}" name="AFFIRM_FLAG${otLimt.APPLY_NO}" type="hidden" value="${otLimt.AFFIRM_FLAG}" />
							<input id="OLD_AFFIRM_FLAG${otLimt.APPLY_NO}" name="OLD_AFFIRM_FLAG${otLimt.APPLY_NO}" type="hidden" value="${otLimt.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO1${otLimt.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 110px;margin-left:20px;">
								<div class="ztree_dept" style="height: 200px;width: 110px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>审批状态</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi"  style="width: 300px"
														onclick="$('#valibl_input_AFFIRM_NO1${otLimt.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO1${otLimt.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#c2${otLimt.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}').css('display', 'none');vildAffriorSelected1();"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li   style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO1${otLimt.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO1${otLimt.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#c2${otLimt.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}').css('display', 'none');vildAffriorSelected1();"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO1${otLimt.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					</tr>
				</c:forEach>
			</tbody>
			    <tr>
					  <td colspan="9" style="text-align: center">G/P部门均值<span id="SHEN_GPAvg"></span></td>
					  <td colspan="3">G职部门平均值:<span id="SHEN_GAvg"></span>
					   P职部门平均值:<span id="SHEN_PAvg"></span></td>
					   <input type="hidden"  id ="SHEN_GPAvgTEXT" value=""/>
					   <input type="hidden"  id ="SHEN_GAvgTEXT" value=""/>
					   <input type="hidden"  id ="SHEN_PAvgTEXT" value=""/>
				</tr>
	</table>
</form>