<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 240,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
function validateAffirmLOtApplyCallback(form,callback,flag) {
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

    $form.attr("action","/ess/affirmApply/approveOvertimeApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=OT_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch(document.viewLOtAffirmList);
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
	return false;
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
			alertMsg.error('请选择反应记录'); 
			return false;
		}
  
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG").value;
  selMuli("AFFIRM_FLAG",fillAffirmFlag);  	
}
</script>
                                                                          
<div id="viewLOtAffirmList" class="pageHeader">                                                 
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewLOtAffirmList?firstFlag=N" method="post" 
	      rel="pagerForm" name="viewLOtAffirmList" id="viewLOtAffirmList">
		<div class="searchBar">
			<table class="searchContent">
			
			    <td><!-- 姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" text="姓名"/>
					</td>
					<td colspan="4"> 
						
					    <input id="personId" name="dwz.person.personId"  type="hidden" lookupGroup="person"/>
						<input name="dwz.person.empName" type="text"   lookupGroup="person" style="float:left;"  value="${empName}"/>
						<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
						<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
						<input name="dwz.person.empInfo"  type="text" readonly lookupGroup="person" size="30" value="${empInfo}"/>
				    </td>
				  
				  <tr>
	                <td><!-- 决裁状态 -->
						审批状态
					</td>				
					<td>
					      <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  exclude="14014306" cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td >
					  <c:if test="${partYn eq 'all'}">
				         <input type="checkbox" name="seach_partYn" value="all"  checked="checked" /> 部门长(Y/N)
				       </c:if>
				        <c:if test="${partYn eq ''}">
				         <input type="checkbox" name="seach_partYn" value="all"   /> 部门长(Y/N)
				       </c:if>
				    </td>
						
					<td>
					班次：
					       <select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					            <option value="">请选择</option> 
								<c:forEach items="${shiftList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
									        ${item.SHIFT_NAME}
								</c:forEach>
							</select>
					</td> 
				</tr>
				<tr>
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewLOtAffirmList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewLOtAffirmList_seachDept" selected="${DEPTNO}"/>
					</td>
			    </tr>
				<tr>
	                <td><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
	                </td>			
				    <td>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
					    <div class="buttonActive">
							<div class="buttonContent">
							    <button type="submit">
							       <spring:message code="public.title.search"/><!-- 检索 -->
							    </button>
					        </div>
				        </div>	
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div id="viewLOtAffirmList" class="pageHeader" >
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
	     <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(otApplyList)}</div>
		<ul>
			<li>
			    <div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLOtApplyCallback('updateLOtApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span>保存</a>
                            </div>
			   </div>	
			</li>
		</ul>
	</div>
	 <form name="updateLOtApplyAffirmForm" id="updateLOtApplyAffirmForm" method="post" action="/ess/affirmApply/approveLOvertimeApplyInBatch" 
		onsubmit="return validateAffirmLOtApplyCallback(this, navTabAjaxDone);">    
		<table class="orderList" width="1800">
			<thead>
				<tr>
				    <th  width="10" ><!--NO-->
						NO
					</th>
					<th width="10" >
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th  width="20"><!--状态-->
						状态
					</th>
					<th width="30"><!--申请人-->
						姓名
					</th>
				    <th  width="30"><!--社号-->
						社号
					</th>
					<th  width="100"><!--部门-->
						部门名
					</th>
					<th width="70"><!--职级-->
						职级
					</th>
					<th width="70px"><!--日期-->
						日期
					</th>
					<th width="30"><!--星期-->
						星期
					</th>
					<th width="30"><!--类型-->
						类型
					</th>
					<th width="30"><!--考勤-->
						考勤
					</th>
					<th width="30"><!--班组-->
						班组
					</th>
					<th  width="70"><!--班次-->
						班次
					</th>
					<th width="100"><!--工作形态-->
						工作形态
					</th>
					<th width="30"><!--出门-->
						进门
					</th>
					<th width="30"><!--进门-->
						出门
					</th>
					<th width="70"><!--开始-->
						开始
					</th>
					<th width="70"><!--结束-->
						结束
					</th>
					<th width="30"><!-- 加班时间-->
					          加班时间
					</th>
					<th width="30" ><!-- 中夜班津贴  -->
					          中夜班津贴
					</th>
					<th width="100"><!--原因-->
						原因
					</th>
					<th width="30"><!--其他原因-->
						其他原因
					</th>
					<th width="100"><!--审批状态-->
						审批状态
					</th>
					<th width="20"><!--加班合计-->
						加班合计
					</th>
					<th width="20"><!--平时-->
						平时
					</th>
					<th width="20"><!--周末-->
						周末
					</th>
					<th width="20"><!--法定节假日-->
						法定节假日
					</th>
					<th width="20"><!--综合加班-->
						综合加班
					</th>
					<th width="20"><!--月平均-->
						月平均
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otApplyList}" var="otApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" >
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="c1${otApply.APPLY_NO}" name="c1" value="${otApply.APPLY_NO}" />
					    </td>
					    <td  style="text-align: center">
					      ${otApply.Confirm_Flag}
					    </td>
					    <td  style="text-align: center">
                              ${otApply.LOCAL_NAME}
					    </td>
					    <td style="text-align: center">
					     ${otApply.EMPID}
					    </td>
					    <td style="text-align: center">
					    ${otApply.DEPARTMENT}
					    </td>
					    <td   style="text-align: center">
					    ${otApply.POST_GRADE_NAME}
					    </td>
					    <td style="text-align: center">
					      ${otApply.AR_DATE_STR}
					    </td>
					    <td  style="text-align: center">${otApply.WEEKDAY}</td>
					     <td style="text-align: center">
					       ${otApply.TYPENAME}
					     </td>
					    <td style="text-align: center">
					    ${otApply.KAOQINITEM}
					    </td>
					    <td width="40px" style="text-align: center">
					    ${otApply.GROUPNAME}
					    </td>
					    <td width="40px" style="text-align: center" >
				            ${otApply.SHIFNAME}
					    </td>
					    <td style="text-align: center">  
					        ${otApply.FROM_TIME_FIRST}-${otApply.TO_TIME_FIRST}
					    </td>
					    <td style="text-align: center">${otApply.INDOOR_DATE}</td>
					    <td  style="text-align: center">${otApply.OUTDOOR_DATE}</td>
					    <td style="text-align: center">
					        ${otApply.FROM_TIME}
					    </td>
					    <td style="text-align: center">
					         ${otApply.TO_TIME}
					    </td>
					    <td  style="text-align: center">
					        ${otApply.APPLY_LENGTH}
				         </td>
					    <td  style="text-align: center">
					        ${otApply.ALLOWANCE}
					    </td>
					    <td style="text-align: center">
					       ${otApply.LEAVEREASON}
					    </td>
					    <td  style="text-align: center">
					       ${otApply.REASON_OTHER}
					    </td>
					    <td  style="text-align: center">
					         <ait:SelectSyCodeCombinByCpnyID name="AFFIRM_FLAG${otApply.APPLY_NO}" combinParentNo="14014304"  exclude="14014306"  selected="${otApply.AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all" />
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
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<div style="visibility: hidden;">
	<c:set value="/ess/affirmApply/viewLOtAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</div>	