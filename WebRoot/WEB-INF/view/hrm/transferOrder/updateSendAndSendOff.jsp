<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(function (){
	$("#seach_ACTIVITY option").each(function(index, obj){
        if($(obj).val() == "${ACTIVITY}"){
            obj.selected = true ;
        }
    });
});



var keyCodeInit=0;
function submitKeyClick_sy0130_add(obj,localName,idcardNo,navTabId,event){
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
 	
 	
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="PERSON_ID"+empIdStr.substring(6);
		var empIdStr="EMP_ID"+empIdStr.substring(6);
		var deptidStr="DEPTNO"+empIdStr.substring(6);
		var empNameStr="EMP_NAME"+empIdStr.substring(6);
		var dutyStr="DUTY"+empIdStr.substring(6);
		var empTypeStr="EMPTYPE"+empIdStr.substring(6);
		var workAreaStr="WORKAREA"+empIdStr.substring(6);
		var checkStr="c1"+empIdStr.substring(6);
		//alert(empid+','+personIdStr+','+empIdStr.substring(6));
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt2?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>'); 
						}//alert(jsonObject.perCnt);
						if(jsonObject.perCnt>1 ){
						obj.name='dwz.person.empId';
 	                  //  alert(obj.name);
 	                  //   alert(document.getElementById(deptidStr).name);
 	                  document.getElementById(personIdStr).name='dwz.person.personId';
 	                    document.getElementById(deptidStr).name='dwz.person.empDept';
 	                    document.getElementById(empNameStr).name='dwz.person.empName';
 	                    document.getElementById(dutyStr).name='dwz.person.empDuty';
 	                   document.getElementById(workAreaStr).name='dwz.person.empWorkArea';
 	                  document.getElementById(empTypeStr).name='dwz.person.empJobType';
 	                   // alert(document.getElementById(dutyStr).name);
							document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceSettings/viewKeeperList2?pageNum=1"
									));
							
							document.getElementById("onck").click();
							document.getElementById(checkStr).checked=true;
						//	obj.name=obj.id;
						//	alert(obj.name);
						}
						if(jsonObject.perCnt==1){
							//document.getElementById(empIdStr).value=jsonObject.empId;
							//document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
							//alert(jsonObject.empName);
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(deptidStr).value=jsonObject.deptName;
							document.getElementById(empIdStr).value=jsonObject.empId;
							//alert(jsonObject.dutyName);
							document.getElementById(dutyStr).value=jsonObject.dutyName;
							document.getElementById(empTypeStr).value=jsonObject.empTypeName;
							document.getElementById(workAreaStr).value=jsonObject.workArea;
							//document.getElementById(empNameStr).innerHTML=jsonObject.empName;
							document.getElementById(checkStr).checked=true;
						//	addTdAdd_sy0130_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
						}
					},
			error: DWZ.ajaxError
		});
    }
 }



function cancelPluralityCallback(form, callback) {
	
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
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}

    $form.attr("action","/hrm/transferOrder/cancelPlurality");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("transactionTransForm");
				alertMsg.correct(data.message);
				document.getElementById("seach_OrderType").value=data.type;
				document.getElementById("jiansuo").click();
				
				
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

function excelIsNot(){
	if($("#seach_OrderType").val()!=""){
		$("#excelIsNot").attr('href','/hrm/transferOrder/viewOrderExamineExcel?seach_OrderType='+$("#seach_OrderType").val());

	}else{
		alertMsg.error("请先选择调令类型");
	}
}

function f_viewardetail_add() {

	document.searchArDetailForm.action='/hrm/transferOrder/viewSendAndSendOff?add_flag='+'Y';
	 navTabSearch("searchArDetailForm"); 
	document.getElementById("seach_condition").value="999999";
	layer=document.getElementById("viewlist");
	 
	layer.innerHTML="999999";
	 
	}



//提交按钮
function f_paleftmen_submit_p(form, callback) {
	//选中检查
	var checked=false;
	/* var panqiandi=$("#PAIQIANDI_"+i).val();
	if(panqiandi==null||paiqiandi==""){
		alert(panqiandi+"s");
	 } */
	var paitemcheck='y';
	var ids= document.getElementsByName("c1");//alert(ids.length);
	 
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行修改操作!
		alert("<spring:message code='ar.alert.message.viewardetail.choosetoupdate'/>");
		return;
	}

	//json传值
	var jsonData = '[';
//alert('2');
	$.each($("input[name='c1']"),
	function(i, obj) {
		
		if (obj.checked) {
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{' ;
			}
			 if($("#PAIQIANDI_"+i).val()==''){
	        	  paitemcheck='n';
	              alert("派遣地不可空");
	              return;
	          }
 			 if($("#AITIVITY_"+i).val()==1&&($("#PAIQIANDI_"+i).val()!=$("#VALI_PAIQIANDI_"+i).val()||$("#VALI_CREATEDATE_"+i).val()!=$("#CREATEDATE_"+i).val())){
	        	  paitemcheck='n';
	              alert("已生效的派遣地不可变更，只可变更开始或结束月份。");
	              return;
	          }
			 
	//		jsonData += ' "PK_NO": "' + obj.value + '" ,';
//			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+i).val()  + '" ,';
//			jsonData += ' "LOCK_YN": "' + $("#LOCK_YN_"+i).val()  + '" ,';
//			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+i).val()  + '" ,';
            if ($("#bigItem_"+i).val()=='')
            {
            paitemcheck='n';
            alert("<spring:message code='ar.alert.message.viewardetail.choosepaitem'/>");
            return;
            }
            if ($("#smallItem_"+i).val()=='')
            {
             paitemcheck='n';
            alert("<spring:message code='ar.alert.message.viewardetail.choosepaitem_detail'/>");
            return;
            }
			//jsonData += ' "TRANSNO": "' + $("#TRANSNO_"+i).val()  + '",';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+i).val()  + '",';
			jsonData += ' "DEPTNO": "' + $("#DEPTNO_"+i).val()  + '" ,';
			jsonData += ' "DUTY": "' + $("#DUTY_"+i).val()  + '" ,';

			jsonData += ' "EXP_INSIDE_NO": "' + $("#EXP_INSIDE_NO_"+i).val()  + '" ,';
			jsonData += ' "EMPTYPE": "' + $("#EMPTYPE_"+i).val()  + '" ,';
			jsonData += ' "CREATEDATE": "' + $("#CREATEDATE_"+i).val()  + '" ,';
			jsonData += ' "paYearStartDate": "' + $("#paYearStartDate_"+i).val()  + '" ,';
			jsonData += ' "paMonthStartDate": "' + $("#paMonthStartDate_"+i).val()  + '" ,';
			jsonData += ' "paYearEndDate": "' + $("#paYearEndDate_"+i).val()  + '" ,';
			jsonData += ' "paMonthEndDate": "' + $("#paMonthEndDate_"+i).val()  + '" ,';
			jsonData += ' "PAIQIANDI": "' + $("#PAIQIANDI_"+i).val()  + '" , ';
			jsonData += ' "PAIQIANLEIXING": "' + $("#PAIQIANLEIXING_"+i).val()  + '" , ';
			jsonData += ' "REMARK": "' + $("#REMARK_"+i).val()  + '" ';

			
			//paYearZhifu_
//			jsonData += ' "EMP_ID": "' + $("#EMP_ID_"+i).val()  + '" ,';
//			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val()  + '"';
			jsonData += '}';

		}
	});
	jsonData += ']';
//alert(jsonData);
 if  (paitemcheck=='n')
 {
  return;
 }
  
	if (jsonData.length == 2) {
		//请选择要修改的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetomodify'/>");
		return;
	}
	
	var $form = $(form);

//	if (!$form.valid()) {
//		return;
//	}
 
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url:'/hrm/transferOrder/updatesubmitSendAndSendOff',
			data:[{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
				//function(data){ //请求成功后处理函数。
				//navTabSearch("searchArDetailForm");
				//alertMsg.correct('添加成功');
				//alert('2');
				//alert(data.message);
				//exportExcel();
				//layer=document.getElementById("viewlist");
				//layer.innerHTML=data.addFlag;
   	 		//}  ,
			//error: DWZ.ajaxError
		});
		
	//	alert('4');
	}

	
}

function exportExcel(){
	
		//var post = document.getElementById('transactionTransForm');
	$("#transactionTransForm").attr("action","/hrm/transferOrder/viewSendAndSendOff?pageNum=1&menuNo=125227&navTabId=hr0919");
	$("#transactionTransForm").submit();

}

function revokes(id){
	alertMsg.confirm("<spring:message code='pa.low.adjust.shifouchexiao'/>",{
			okCall:function(){
				$("#transactionTransForm").attr("action","/hrm/transferOrder/viewSendAndSendOff?transId="+id+"&pageNum=1&menuNo=125227&navTabId=hr0919");
				$("#transactionTransForm").submit();
			}
		});
}



</script>
<body> <div class="pageHeader">
	<form  action="/hrm/transferOrder/updateSendAndSendOff?pageNum=1&menuNo=125227&navTabId=hr0919" rel="pagerForm" method="post"  onsubmit="return navTabSearch(this);"  id="transactionTransForm" name="transactionTransForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>	
				<td>
					
					<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
				</td>
                <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID}" />
				</td>					    			
				<td>
					<spring:message code="is.joininstance.title.statement"/>
					<!--决裁状态:-->
				</td>					
				<td> 
                    <select name="seach_ACTIVITY" id="seach_ACTIVITY">
                        <option value="">请选择</option>
                        <option value="3">全部 </option>
                        <option value="0">未生效</option>
                        <option value="1">已生效 </option>
                        <option value="2">已撤销</option>
                    </select>
				 </td>
                <td>
                	<spring:message code="sys.affirm.title.duty"/>
                    <!--职责:-->
                </td>			
			    <td>
			        <input type="text" name="seach_DUTY" value="${DUTY}" >		   
			    </td>	 
			</tr>
		</table>
		<div class="subBar">
								<ul>
									<li>
										<div class="buttonActive">
											<div class="buttonContent">
												<button type="submit" id="submitClick_ar0201">
													<!-- 查询 --><spring:message code="button.search"/>
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
<div class="formBar">
<font style="color:red">注意：已生效的只能去修改开始月份或结束月份，其他列要有变动请在新添加一条。</font>
			<ul class="toolBar">
		 
									<li><!-- f_viewardetail_update('searchArDetailForm',DWZ.ajaxDone); -->
											<a class="buttonActive"
											onclick="f_paleftmen_submit_p('searchArDetailForm',navTabAjaxDone); "  ><span><!-- 提交 --><spring:message code="public.title.submit"/></span>
										</a>
									</li>	
									
			<c:if test="${toolbarInfo.INSERTR eq '1'}"><li><a class="add" onclick="f_viewardetail_add('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 添加 --><spring:message code="button.add" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.DELETER eq '1'}"><li><a class="delete" onclick="f_viewardetail_delete('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 删除 --><spring:message code="button.delete" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.UPDATER eq '1'}"><li><a class="edit" onclick="f_viewardetail_update('updateArDetailForm',DWZ.ajaxDone);" ><span><!-- 修改 --><spring:message code="button.update" /></span></a></li></c:if>
			
		</ul>
	</div>
<div id="viewlist">   
<form name="cancelTransForm" id="cancelTransForm" method="post" action="/hrm/transferOrder/cancelPlurality"
	  onsubmit="return cancelPluralityCallback(this, navTabAjaxDone);" rel="pagerForm" >
	  <div id="viewlist">
 	<table class="table" width="100%" layoutH="206" >      
		<thead>
			<tr>
				 <th align="center" width="1%" >
				  <input type="checkbox" class="checkboxCtrl" group="c1">
				</th>
			    <%--th align="center" width="5%">
				    <spring:message code="hr.viewPersonalInfo.title.falingbianhao"/><!--发令编号-->
				</th --%>
				<th align="center" width="10%">
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名：-->
				</th>
				<th align="center" width="7%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!-- 部门  -->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="sys.affirm.title.duty"/><!--职责-->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="inct.salesman.empType"/><!--人员类型-->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu"/><!--工作地区-->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="hr.enpinfo.title.EMP.EXPDATE"/><!--发令日期-->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="pa.insurance.title.endMonth"/><!-- 结束月  -->
				</th>
				<th align="center" width="6.5%">
					省份
				</th>
				<th align="center" width="6.5%">
					城市
				</th>
				<th align="center" width="6.5%">
					地区(变更地区修改派遣地)->
				</th>
				<th align="center" width="6.5%">
					 <spring:message code="display.emp.ben.or.sendtoadministrator"/><!-- 派遣地  -->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="display.emp.ben.sendtype"/><!-- 派遣类型  -->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="hr.viewBadArchives.title.REMARK"/><!-- 备注  -->
				</th>
				<th align="center" width="6.5%">
					<spring:message code="is.joininstance.title.statement"/><!-- 状态  -->
				</th>
			 
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${getHrDispatchList}" var="list" varStatus="i">
			
			
				<tr   >
			    <%--td align="center" width="5%">
				    ${list.TRANS_NO}
				</td --%>
				
				<td align="center"  width="2%">
				 
				 <c:if test="${list.ACTIVITY eq '0'|| list.ACTIVITY eq '1'}"> <input type="checkbox" id="c1_${i.index}" name="c1" value="c1_${i.index}"></c:if>
				 
				</td>
				<td align="center" width="10%">
				<input type="hidden"   id="EXP_INSIDE_NO_${i.index}" name="EXP_INSIDE_NO_${i.index}"  value="${list.EXP_INSIDE_NO}" />
					 ${list.EMPID}/${list.LOCAL_NAME} 
				</td>
				<td align="center" width="7%">
					 ${list.ORG_NAME_LOCAL}
				</td>
				<td align="center" width="6.5%">
					 ${list.POSITION_NAME}
				</td>
				<td align="center" width="6.5%">
					 ${list.EMP_TYPE_NAME}
				</td>
				<td align="center" width="6.5%">
					 ${list.WORK_AREA}
					 	 <input type="hidden" id="VALI_CREATEDATE_${i.index}" name="VALI_CREATEDATE_${i.index}"         
						value="${list.ORDER_DATE}" />
				</td>
				<td align="center" width="6.5%"> 
				
				
					 <input type="text" id="CREATEDATE_${i.index}" name="CREATEDATE_${i.index}" class="required" style="background:#FFF" readonly="true" format="yyyy-MM-dd"
						yearstart="-50" yearend="5" onClick="setdate(this);"
						value="${list.ORDER_DATE}" />
					 
				</td>
				<td align="center" width="6.5%">
					 	 <ait:date yearName="paYearStartDate_${i.index}" monthName="paMonthStartDate_${i.index}" yearSelected="${list.S_YEAR}" monthSelected="${list.S_MONTH}"/>
				</td>
				<td align="center" width="6.5%">
				<ait:date yearName="paYearEndDate_${i.index}" monthName="paMonthEndDate_${i.index}" yearSelected="${list.E_YEAR}" monthSelected="${list.E_MONTH}"/>	  
				</td>
			 
				
			<td align="center" width="6.5%">

			<ait:SelectState id="seach_TWO_SHENG_${i.index}" name="seach_TWO_SHENG_${i.index}" onChange="changeSheng(${i.index})"  type="SHENG" parentNo="" selected="" limit="all"/>
				</td>
				
				<td align="center" width="6.5%">
				
			 
			 <select id="seach_TWO_CITYNM_${i.index}" name="seach_TWO_CITYNM_${i.index}" onchange="changeCity(${i.index})">
				 	
			</select>
			 </td><td align="center" width="6.5%">
				 <SELECT name="PAIQIAN_AREA_${i.index}" id="PAIQIAN_AREA_${i.index}"  onchange="changArea(${i.index})"  >
					 
					 </SELECT>
				  </td>
				  	<td align="center" width="6.5%">
				  	
				<SELECT name="PAIQIANDI_${i.index}" id="PAIQIANDI_${i.index}" disabled="disabled" > 			 
					 		<option value="${list.DIQU_CODE }"  >   ${list.CONTENTS }</option>
					 </SELECT>
					 <input type="hidden" id="VALI_PAIQIANDI_${i.index}" name="VALI_PAIQIANDI_${i.index}" value="${list.DIQU_CODE}">
				</td>
				
				<script type="text/javascript">
					
			 
				 function  changeSheng(i){
					var stateCd=$('#seach_TWO_SHENG_'+i).val();
				 
					var cityCd=$('#seach_TWO_CITYNM_'+i).val(); 
					$.ajax({
						cache: false,
						url : '${base}/hrm/transferOrder/getListBySelect?type=CITY2&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_TWO_CITYNM_'+i+'&seq='+i,
						type : "get",
						dataType : "html",
						success : function(data) {
							 
							$("#seach_TWO_CITYNM_"+i).html(data);
						}
					});
				
				}
				function changeCity(i){
					var stateCd=$('#seach_TWO_CITYNM_'+i).val();
					var cityCd=$('#PAIQIAN_AREA_'+i).val(); 
					$.ajax({
						cache: false,
						url : '${base}/hrm/transferOrder/getListBySelect?type=REGIONTWO&parentNo='+stateCd+'&selected='+cityCd+'&name=PAIQIAN_AREA_'+i+'&seq='+i,
						type : "get",
						dataType : "html",
						success : function(data) {
						 
							$("#PAIQIAN_AREA_"+i).html(data);
						}
					});
				}
				function changArea(i){
					 
					var  areaid = $("#PAIQIAN_AREA_"+i).val();
					//var areid = $("#PAIQIAN_AREA_"+i).innerHTML;
					  
					 var checkText=$("#PAIQIAN_AREA_"+i).find("option:selected").text();
					 
					 
					 $("#PAIQIANDI_"+i).html('<option value=\"'+areaid+'\">'+checkText+'</option>');
				}
				
				</script>
				<td align="center" width="6.5%">
				
				<SELECT name="PAIQIANLEIXING_${i.index}" id="PAIQIANLEIXING_${i.index}"> 
						<c:forEach items="${paiQianTypes}" var="pQd1" varStatus="j">
					 		<option value="${pQd1.CODE_NO}" <c:if test="${list.TRANS_NAME eq pQd1.CODENAME}"> selected</c:if>>${pQd1.CODENAME}</option>
					 	</c:forEach>
					 </SELECT>
				
					 
				</td>
				<td align="center" width="6.5%">
				
				<input type="text" id="REMARK_${i.index}" name="REMARK_${i.index}" value="${list.REMARK}"/>
					 
				</td>
				<td align="center" width="6%">
					<input type="hidden" id="AITIVITY_${i.index}" name="AITIVITY_${i.index}" value="${list.ACTIVITY}"/>
					 <c:if test="${list.ACTIVITY eq '1'}"><font color="green">已生效</font></c:if>
					 <c:if test="${list.ACTIVITY eq '0'}"><font color="red">未生效</font></c:if>
					 <c:if test="${list.ACTIVITY eq '2'}"><font color="orange">已撤销</font></c:if>
				</td><!--
				<td align="center" width="6%"> 
				<c:if test="${list.STATE eq '0'}"><font color="green"><a href="#" onclick="revokes('${list.EXP_INSIDE_NO}')">撤销</a></font></c:if>
					 <c:if test="${list.STATE eq '1'}"><font color="red"><a href="#" onclick="revokes('${list.EXP_INSIDE_NO}')">撤销</a></font></c:if>
					 <c:if test="${list.STATE eq '2'}"><font color="orange">-</font></c:if>
				</td>
			--></tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<input type="hidden" value="${OrderType}" name="seach_biaohao" />
</form>
<div id="addlist">
		
</div>
	<c:set value="/hrm/transferOrder/updateSendAndSendOff?menuNo=125227&navTabId=hr0919" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</div>
      	


