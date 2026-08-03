<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

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
function validateAffirmOtLimitCallbackEss(form,callback,flag) {
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
    var LOCK_YN = $("#LOCK_YN").val();
	if(LOCK_YN =='Y'){
		alertMsg.error("本月加班上限已锁定！！"); 
		return false;
	}  
	calculateAvgShenpi();//计算
	var GPAvg = $("#GPAvgTEXT").val();
	var PAvg = $("#PAvgTEXT").val();
	var GAvg = $("#GAvgTEXT").val();
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
		$.each($("input[name='c1']"),function(i, obj) {
			 if (obj.checked) {
			     var j = obj.value;
			     var AFFIRM_FLAG =  $("#valibl_value_AFFIRM_NO"+j).val();
			     var OLD_AFFIRM_FLAG =  $("#OLD_AFFIRM_FLAG"+j).val();
			     if(AFFIRM_FLAG =='14014306'){
			       $("#valibl_value_AFFIRM_NO"+j).val('14014307');
			       $("#valibl_input_AFFIRM_NO"+j).val('部门申请');
			     }
			 }
		});
	}
	
	
	
    
    $form.attr("action","/ess/infoApply/approveOtLimitBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");
    var msg = "确定要部门申请吗？";
	 alertMsg.confirm(msg,{okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"), 
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
					    navTabSearch(document.viewOverTimeLimtList);
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

function vildAffriorSelected(){
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
	
   if(checked){
		$.each($("input[name='c1']"),function(i, obj) {
			 if (obj.checked) {
			     var j = obj.value;
			     var AFFIRM_FLAG =  $("#valibl_value_AFFIRM_NO"+j).val();
			     var OLD_AFFIRM_FLAG =  $("#OLD_AFFIRM_FLAG"+j).val();
			     if(OLD_AFFIRM_FLAG =='14014307'){
			        alertMsg.error("已申请的数据,等待部门长批准");
			         $("#valibl_value_AFFIRM_NO"+j).val("14014307");
			         $("#valibl_input_AFFIRM_NO"+j).val("部门申请");
			     }
			     if(OLD_AFFIRM_FLAG =='14014308'){
			        alertMsg.error("部门长已批准,不允许操作");
			         $("#valibl_value_AFFIRM_NO"+j).val("14014307");
			         $("#valibl_input_AFFIRM_NO"+j).val("部门申请");
			     }
			     if(OLD_AFFIRM_FLAG =='14014306'){
			        if(AFFIRM_FLAG != '14014307'){
			          alertMsg.error("只能操作部门申请");
			          $("#valibl_value_AFFIRM_NO"+j).val("14014306");
			          $("#valibl_input_AFFIRM_NO"+j).val("初始");
			          }
			     }
			 }
		});
	}
 

}


function checkNumGP(TimeVlue,applyno){   
        $('#c1'+applyno).attr('checked','checked');
        if(parseFloat(TimeVlue)>36){        
	        alertMsg.error("超出了36小时"); 
			$('#FINAL_ADJUST_TIME'+applyno).val('');   
        }
}
//round
function round2(Num1,Num2){    
     if(isNaN(Num1)||isNaN(Num2)){        
         return(0);     
     }else{     
         return(Num1.toFixed(Num2));       
     }
}

function calculateAvgShenpi(){
    var adjst_time;//调整时间
    var count = 0;//整个部门下所有人累计的和
    var g_count = 0;//G职级和
    var p_count = 0;//P职和
    var TOTAL_G = 0;//G职级人数
	var TOTAL_P = 0;//P职级人数
    var GPAvg;//部门均值
    var  GAvg;//G职级均值
    var  PAvg;//p职级均值
	var total =  parseFloat($("#total").val());//部门下人数（含G、NULL、P）
	if(isNaN(total)){
	  return;
	}else{
	  total = total;
	}
    var ids= document.getElementsByName("c1");
	// 分别统计G、P职级的人数，对应的和
	for(var i=0;i<ids.length;i++){
		 var j = ids[i].value;
		 
		 if($('#REMOVE_ADJUST_TIME'+j).val()!= null && $('#REMOVE_ADJUST_TIME'+j).val() != ''){
		    adjst_time = $('#REMOVE_ADJUST_TIME'+j).val();
		 }else{
		    adjst_time=$('#FINAL_ADJUST_TIME'+j).val();
		 }
		
		 var DIV_GP=$('#DIV_GP'+j).val();  
		 if(DIV_GP == 'G') {
		   TOTAL_G = TOTAL_G+1;
		   g_count = g_count+parseFloat(adjst_time);
		 }
		 if(DIV_GP == 'P') {
		   TOTAL_P = TOTAL_P+1;
		   p_count = p_count+parseFloat(adjst_time);
		 }
		 count = count+parseFloat(adjst_time);
	}
	
	total = parseFloat($("#total").val());
	
	//部门均值 
   if(total != 0){
      GPAvg = round2(count/total,2);
   }else{
      GPAvg=0;
    }
    //G均值 
   if(parseInt(TOTAL_G) != 0){
      GAvg = round2(g_count/parseInt(TOTAL_G),2);
   }else{
      GAvg=0;
   }
   //P均值
   if(parseInt(TOTAL_P) != 0){
      PAvg = round2(p_count/parseInt(TOTAL_P),2);
   }else{
      PAvg=0;
   }
   alert('当前共'+total+'人,G职'+TOTAL_G+'人,P职'+TOTAL_P+'人;部门均值'+GPAvg+'小时,G职部门均值'+GAvg+'小时,P职部门均值'+PAvg+'小时！');
   $("#GPAvg").html(GPAvg);
   $("#GPAvgTEXT").val(GPAvg);
   $("#GAvg").html(GAvg);
   $("#GAvgTEXT").val(GAvg);
   $("#PAvg").html(PAvg);
   $("#PAvgTEXT").val(PAvg);
}

 //文本fill
 function textMuli2(name,value){
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

function fillItem(){
    var checked=false;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择反应记录'); 
			return false;
		}
	var fillAffirmFlag = $("#viewOverTimeLimtList select[id='FILLAFFIRMFLAG']").val();
    var obj = document.getElementById("FILLAFFIRMFLAG");
    var txt = obj.options[obj.selectedIndex].text;
     if(fillAffirmFlag != '' && fillAffirmFlag != null){
       textMuli2("valibl_value_AFFIRM_NO",fillAffirmFlag);  	
       textMuli2("valibl_input_AFFIRM_NO",txt);
    }else{
       textMuli2("valibl_value_AFFIRM_NO",'14014307');  	
       textMuli2("valibl_input_AFFIRM_NO",'部门申请');
    }  
}

function valibl_mouseover_item_pop(idStr){
	$("#valibl_input_"+idStr).unbind("blur");
	$("#valibl_pop_"+idStr).mouseout(
					function() {
						$("#valibl_input_"+idStr).blur(
										function() {
											$('#valibl_pop_'+idStr).css('display', 'none');
										});
					});
}

</script>
         
<div id="viewApplyAttenBatch"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOverTimeLimtList?firstFlag=N"  method="post"
		id="viewOverTimeLimtList" name="viewOverTimeLimtList">
		<div class="searchBar">
			<table class="searchContent" >
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
						<ait:deptList limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!-- 审批状态： --> 
						  审批状态
					</td>
					<td > 
					     <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}" exclude="14014309,14014310,14014311,14014312"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td><!-- 职群--> 
						 职群
					</td>
					<td > 
					     <select id="seach_DIV_GP" name="seach_DIV_GP">
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

<div id="viewOverTimeLimtList" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent" >
				<tr >
					<td  align="right">
					审批状态&nbsp;&nbsp;&nbsp;<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" exclude="14014306" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem();"><span>全部反应</span></a>
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
						<a class="update" onclick="validateAffirmOtLimitCallbackEss('approveOtLimitBatch',DWZ.ajaxDone,'1')" href="#" >
		         <span>保存</span></a>
                 </div>
			</div>	
		</li>
		<li>
			<div class="subBar">
				  <div class="buttonActive">
						<a class="update" onclick="calculateAvgShenpi()" href="#" >
		         <span>计算平均值</span></a>
                 </div>
			</div>	
		</li>
	</ul>
</div>
<form name="approveOtLimitBatch" id="approveOtLimitBatch" method="post" action="/ess/infoApply/approveOtLimitBatch" 
	  onsubmit="return validateAffirmOtLimitCallbackEss(this, navTabAjaxDone);">  
	<table class="orderList" width="100%"  > 
		<thead>
				<tr>
				    <th  ><!--NO-->
						NO
					</th>
					<th  >
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
					<th width="8%" >
				    	月份
				    </th>
					<th  width="8%" style="text-align: center"><!--申请人-->
						姓名
					</th>
				    <th  width="7%" style="text-align: center" ><!--社号-->
						社号
					</th>
					<th   width="10%" style="text-align: center"><!--部门-->
						部门
					</th>
					<th  width="10%" style="text-align: center"><!--职级-->
						职级
					</th>
					<th style="text-align: center" width="10%" ><!--月初默认值-->
						月初默认值
					</th>
					<th style="text-align: center" width="8%" ><!--月初加班上限-->
						月初加班上限
					</th>
					<th  style="text-align: center" width="8%" ><!--月终调整值-->
						月终调整值
					</th>
					<th width="25%" style="text-align: center"><!--修正原因-->
						修正原因
					</th>
					<th style="text-align: center" width="10%"><!--审批状态-->
						审批状态
					</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otLimtList}" var="otLimt" varStatus="i">	 
					<tr target="sid" rel="${admin.personId}" >
					     <input type="hidden" id="LOCK_YN" name="LOCK_YN" value="${otLimt.LOCK_YN}" />
					     <input type="hidden" id="total" name="total" value="${fn:length(otLimtList)}" />
					    <td    style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="c1${otLimt.APPLY_NO}" name="c1" value="${otLimt.APPLY_NO}" />
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
					       <input id="FINAL_ADJUST_TIME${otLimt.APPLY_NO}" name="FINAL_ADJUST_TIME${otLimt.APPLY_NO}" value="${otLimt.FINAL_ADJUST_TIME}"  min="-99999999"  type="text" style="width: 100%"  onkeyup="checkNumGP(this.value,${otLimt.APPLY_NO});"/>
					       <input id="DIV_GP${otLimt.APPLY_NO}" name="DIV_GP${otLimt.APPLY_NO}" value="${otLimt.DIV_GP}"  type="hidden" />
					    </td>
					    <td  style="text-align: center">
					       ${otLimt.REMOVE_ADJUST_TIME}
					       <input id="REMOVE_ADJUST_TIME${otLimt.APPLY_NO}"  value="${otLimt.REMOVE_ADJUST_TIME}"   type="hidden" />
					    </td>
					    <td  style="text-align: center">
					      <input id="ADJUST_RESON${otLimt.APPLY_NO}" name="ADJUST_RESON${otLimt.APPLY_NO}" value="${otLimt.ADJUST_RESON}" onkeyup="$('#c1${otLimt.APPLY_NO}').attr('checked','checked');" type="text" style="width: 100%"/>
					    </td>
					   <td style="text-align: center">
					    <input id="valibl_input_AFFIRM_NO${otLimt.APPLY_NO}" type="text"  style="text-align: center"  size="20" value="${otLimt.AFFIRMNAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NO${otLimt.APPLY_NO}" name="AFFIRM_FLAG${otLimt.APPLY_NO}" type="hidden" value="${otLimt.AFFIRM_FLAG}" />
							<input id="OLD_AFFIRM_FLAG${otLimt.APPLY_NO}" name="OLD_AFFIRM_FLAG${otLimt.APPLY_NO}" type="hidden" value="${otLimt.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${otLimt.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}').css('display', 'none');"class="deptContent"
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
														onclick="$('#valibl_input_AFFIRM_NO${otLimt.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otLimt.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#c1${otLimt.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}').css('display', 'none');vildAffriorSelected();"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li   style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO${otLimt.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otLimt.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#c1${otLimt.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}').css('display', 'none');vildAffriorSelected();"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO${otLimt.APPLY_NO}').css('display', 'none');"
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
					  <td colspan="9" style="text-align: center">G/P部门均值<span id="GPAvg"></span></td>
					  <td colspan="3">G职部门平均值:<span id="GAvg"></span>
					   P职部门平均值:<span id="PAvg"></span></td>
					   <input type="hidden"  id ="GPAvgTEXT" value=""/>
					   <input type="hidden"  id ="GAvgTEXT" value=""/>
					   <input type="hidden"  id ="PAvgTEXT" value=""/>
				</tr>
	</table>
</form>