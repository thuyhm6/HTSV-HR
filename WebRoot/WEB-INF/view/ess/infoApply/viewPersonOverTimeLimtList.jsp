<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    
    $("#viewPersonOverTimeLimtList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewPersonOverTimeLimtList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPersonOverTimeLimtList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPersonOverTimeLimtList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
     });
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		//"bAutoWidth": false    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 280,
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
	var ids= document.getElementsByName("ot_limit");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
	calculateAvgShenpi();//计算
   
    $form.attr("action","/ess/infoApply/approveOtLimitBatchHUB?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");
    var msg = "确定要修改吗？";
	 alertMsg.confirm(msg,{okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"), 
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
					    navTabSearch(document.viewPersonOverTimeLimtList);
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
    var  removeCount = 0;
    var  total = 0;
    var ids= document.getElementsByName("ot_limit");
	// 分别统计G、P职级的人数，对应的和
	for(var i=0;i<ids.length;i++){
		 var j = ids[i].value;
		 if($('#REMOVE_ADJUST_TIME'+j).val()!= null && $('#REMOVE_ADJUST_TIME'+j).val() != ''){
		    adjst_time = $('#REMOVE_ADJUST_TIME'+j).val();
		 }else{
		    adjst_time=$('#FINAL_ADJUST_TIME'+j).val();
		 }
		 var DIV_GP=$('#DIV_GP'+j).val();  
		 var REMOVE_YN=$('#OLD_REMOVE_YN'+j).val();  
		 var docRemove = document.getElementById("REMOVE_YN"+j);
		 
		 if(DIV_GP == 'G' && !docRemove.checked) {
		   TOTAL_G = TOTAL_G+1;
		   g_count = g_count+parseFloat(adjst_time);
		 }
		 if(DIV_GP == 'P' && !docRemove.checked) {
		   TOTAL_P = TOTAL_P+1;
		   p_count = p_count+parseFloat(adjst_time);
		 }
		 if( !docRemove.checked){
		   count = count+parseFloat(adjst_time);
		   total = total+1;
		 }
		 if(docRemove.checked==true){
		   removeCount = removeCount+1;
		 }
	}
	
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
   alert('当前有效人数'+total+'人,例外人数 '+removeCount+',G职'+TOTAL_G+'人,P职'+TOTAL_P+'人;部门均值'+GPAvg+'小时,G职部门均值'+GAvg+'小时,P职部门均值'+PAvg+'小时！');
   $("#GPAvg").html(GPAvg);
   $("#GPAvgTEXT").val(GPAvg);
   $("#GAvg").html(GAvg);
   $("#GAvgTEXT").val(GAvg);
   $("#PAvg").html(PAvg);
   $("#PAvgTEXT").val(PAvg);
}


function otLimitImport(){
		$("#importExcelDialogOtLimit").attr('href','/pa/excelImport/importExcelData?importFunName=/importOTLimitTSTO');
		$("#importExcelDialogOtLimit").attr('height', "200");
		$("#importExcelDialogOtLimit").attr('width', "400");
		$("#importExcelDialogOtLimit").click();
}

function searchPop_ess11112(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	$("#searchPop_ess11112", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess11112", navTab.getCurrentPanel()).click();
}
</script>
         
<div id="viewApplyAttenBatch"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPersonOverTimeLimtList?firstFlag=N"  method="post"
		id="viewPersonOverTimeLimtList" name="viewPersonOverTimeLimtList">
		<div class="searchBar">
			<table class="searchContent" >
				<tr>
				    <td width="7%">社号/姓名</td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
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
		</div>
	</form>
</div>
<a id="importExcelDialogOtLimit"  href="#" target="dialog" mask="true" width="500" height="200"></a>
<div class="pageContent" > 
<div class="formBar">
 <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 10px; line-height: 30px;">Total:${fn:length(otLimtList)}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<ul>
	    <li><a class="buttonActive" herf="#" id="viewPersonOverTimeLimtList_Serch"><span>查询</span></a></li>
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
			<li>
				<a class="buttonActive" href="/ess/infoApply/personOTLimitImportDemoLoad?flag=load" ><span>导入模板下载</span></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="otLimitImport()"><span>Excel导入</span></a>
			</li>
			<li>
				<li><a class="buttonActive" onclick="downloadExcel('viewPersonOverTimeLimtList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=168','/ess/infoApply/viewPersonOverTimeLimtList?firstFlag=N')"><span>导出到Excel</span></a></li>
			</li>
		</li>
	</ul>
</div>
<form name="approveOtLimitBatch" id="approveOtLimitBatch" method="post" action="/ess/infoApply/approveOtLimitBatchHUB" 
	  onsubmit="return validateAffirmOtLimitCallbackEss(this, navTabAjaxDone);">  
	<table class="orderList" width="100%"  > 
		<thead>
				<tr>
				    <th width="2%" ><!--NO-->
						NO
					</th>
					<th  >
				    	<input type="checkbox" class="checkboxCtrl" group="ot_limit" />
				    </th>
					<th width="4%">
				    	状态
				    </th>
					<th  width="8%" >
				    	月份
				    </th>
					<th width="4%" style="text-align: center"><!--申请人-->
						姓名
					</th>
				    <th  width="4%" style="text-align: center"><!--社号-->
						社号
					</th>
					<th width="8%"  style="text-align: center"><!--部门-->
						部门
					</th>
					<th  width="7%" style="text-align: center"><!--部门-->
						职级
					</th>
					<th width="7%" style="text-align: center"  ><!--月初加班上限-->
						月初默认值
					</th>
					<th width="8%" style="text-align: center" ><!--月初加班上限-->
						月初加班上限
					</th>
					<th width="5%" style="text-align: center" ><!--月终调整值-->
						月终调整值
					</th>
					<th width="3%" style="text-align: center"  ><!--是否去除-->
						例外
					</th>
					<th  style="text-align: center"><!--修正原因-->
						修正原因
					</th>
					<th width="7%" style="text-align: center"><!--审批状态-->
						审批状态
					</th>
					<th width="13%" style="text-align: center"><!--修改者-->
						修改者
					</th>
					<th width="10%" style="text-align: center"><!--修改时间-->
						修改时间
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
					        <input type="checkbox" id="ot_limit${otLimt.APPLY_NO}" name="ot_limit" value="${otLimt.APPLY_NO}" />
					    </td>
					    <td style="text-align: center">
					           <c:if test="${otLimt.LOCK_YN eq 'N'}">
					                                       未锁定
					           </c:if>
					           <c:if test="${otLimt.LOCK_YN eq 'Y'}">
					                                         锁定
					           </c:if>
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
					        ${otLimt.FINAL_ADJUST_TIME}
					       <input id="FINAL_ADJUST_TIME${otLimt.APPLY_NO}" name="FINAL_ADJUST_TIME${otLimt.APPLY_NO}" value="${otLimt.FINAL_ADJUST_TIME}"  type="hidden" style="width: 100%"  />
					       <input id="DIV_GP${otLimt.APPLY_NO}" name="DIV_GP${otLimt.APPLY_NO}" value="${otLimt.DIV_GP}"  type="hidden" />
					       <input id="OLD_REMOVE_YN${otLimt.APPLY_NO}"  value="${otLimt.REMOVE_YN}"  type="hidden" />
					    </td>
					    <td>
					       <input id="REMOVE_ADJUST_TIME${otLimt.APPLY_NO}" name="REMOVE_ADJUST_TIME${otLimt.APPLY_NO}" value="${otLimt.REMOVE_ADJUST_TIME}" onkeyup="$('#ot_limit${otLimt.APPLY_NO}').attr('checked','checked');" type="text" style="width: 100%"  />
					    </td>
					    <td style="text-align: center">
					       <c:if test="${otLimt.REMOVE_YN eq '1'}">
					         <input type="checkbox"  id="REMOVE_YN${otLimt.APPLY_NO}" name="REMOVE_YN${otLimt.APPLY_NO}" onclick="$('#ot_limit'+${otLimt.APPLY_NO}).attr('checked','checked')"  value="0" />
					       </c:if>
					       <c:if test="${otLimt.REMOVE_YN eq '0'}">
					         <input type="checkbox"  id="REMOVE_YN${otLimt.APPLY_NO}" name="REMOVE_YN${otLimt.APPLY_NO}"  checked="checked"  onclick="$('#ot_limit'+${otLimt.APPLY_NO}).attr('checked','checked')" value="" />
					       </c:if>
				        </td>
					    <td  style="text-align: center" title="${otLimt.ADJUST_RESON}">
					       ${fn:substring( otLimt.ADJUST_RESON,0,5)}...
					    </td>
					   <td style="text-align: center">
					     ${otLimt.AFFIRMNAME}
					   </td>
					   <td style="text-align: center">
					       <c:if test="${not empty otLimt.UPDATED_BY }">
					          ${otLimt.UPDATED_BY} [ ${otLimt.UPDATED_IP}]
					       </c:if>
					   </td>
					   <td style="text-align: center">
					     ${otLimt.UPDATE_DATE}
					   </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			   <tr>
					<td colspan="13" style="text-align: center" align="center">G/P部门均值<span id="GPAvg"></span></td>
					<td colspan="3">G职部门平均值:<span id="GAvg"></span>
					P职部门平均值:<span id="PAvg"></span></td>
				   <input type="hidden"  id ="GPAvgTEXT" value=""/>
				   <input type="hidden"  id ="GAvgTEXT" value=""/>
				   <input type="hidden"  id ="PAvgTEXT" value=""/>
				</tr>
			</tfoot>
	</table>
</form>