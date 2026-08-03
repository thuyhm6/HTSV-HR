<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

　
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">



<script type="text/javascript">
 
function addRowByIDL(currentRowID){
    //遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
 
 
 	 var startYearHtml= document.getElementById("paYearStartDate_1").innerHTML
 	 var startMonthHtml = document.getElementById("paMonthStartDate_1").innerHTML;

 	 var endYearHtml = document.getElementById("paYearEndDate_1").innerHTML;
 	 var endMonthHtml = document.getElementById("paMonthEndDate_1").innerHTML;
 	 
  	var   paiqiantype=document.getElementById("PAIQIANLEIXING_1").innerHTML;

	var  province = document.getElementById("seach_STATENM_SHENG_1").innerHTML;

	var city  = document.getElementById("seach_CITYNM_1").innerHTML;
  	
  	var paiqian1 = document.getElementById("PAIQIANDI_1").innerHTML;
  	
 	 

 
   
     var  currentRowID=$("table:last tr:last").index();
	 
	$.each( $('table:last tbody tr'), function(i, tr){
 
        if($(this).attr('id')==currentRowID){
            //获取当前行
            var currentRow=$('table:last tbody tr:eq('+i+')');
            //要添加的行的id
            var addRowID=i+1;
            str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center"> <input type="checkbox" id="c1_'+addRowID+'" name="c1" value="c1_'+addRowID+'"></td>'
	            	+'<td style="text-align: center"> <input type="hidden" id="PERSON_ID_'+addRowID+'" name="PERSON_ID_'+addRowID+'" lookupGroup="person" value=""/>'
					+'<input type="text" id="EMP_ID_'+addRowID+'" name="EMP_ID_'+addRowID+'" lookupGroup="person" value="" onkeydown="submitKeyClick_sy0130_add(this,\'\',\'\',\'\',event);" />'
					+' </td>'
	            	+'<td style="text-align: center"> <input type="text" id="DEPTNO_'+addRowID+'" name="DEPTNO_'+addRowID+'" value="" lookupGroup="person" readonly/></td>'
	            	+'<td style="text-align: center"> <input type="text" id="DUTY_'+addRowID+'" name="DUTY_'+addRowID+'" value="" lookupGroup="person" readonly/></td>'
	            	+'<td style="text-align: center"><input type="text" id="EMPTYPE_'+addRowID+'" name="EMPTYPE_'+addRowID+'" lookupGroup="person" value="" readonly/></td>'
	            	+'<td style="text-align: center"><input type="text" id="WORKAREA_'+addRowID+'" name="WORKAREA_'+addRowID+'" lookupGroup="person" value="" readonly/></td>'
	            	+'<td style="text-align: center"><input type="text" id="CREATEDATE_'+addRowID+'" name="CREATEDATE_'+addRowID+'" class="required" style="background:#FFF" readonly="true" format="yyyy-MM-dd"'
						+'yearstart="-50" yearend="5" onClick="setdate(this);" value="${archive.HAPPEN_DATE}" /></td>'
	            	+'<td style="text-align: center"><select id="paYearStartDate_'+addRowID+'" class="input_select_short" name="paYearStartDate_'+addRowID+'">'
	              	 
	            	+startYearHtml 
	            	+'</select><select id="paMonthStartDate_'+addRowID+'" class="input_select_short" name="paMonthStartDate_'+addRowID+'">'
	         
	            	+startMonthHtml
	            	+'</select></td>'
	            	+'<td style="text-align: center"><select id="paYearEndDate_'+addRowID+'" class="input_select_short" name="paYearEndDate_'+addRowID+'">'
	            	+endYearHtml
	            	+'</select><select id="paMonthEndDate_'+addRowID+'" class="input_select_short" name="paMonthEndDate_'+addRowID+'">'
	            	+endMonthHtml
	            	+'</select></td>'
	            	+'<td style="text-align: center"><SELECT onChange="changeSheng('+addRowID+')" name="seach_STATENM_SHENG_'+addRowID+'" id="seach_STATENM_SHENG_'+addRowID+'"> '
	            	 +province
			 	+'</SELECT> </td>'
			 	+'<td style="text-align: center"><SELECT  onChange="changeCity('+addRowID+')" name="seach_CITYNM_'+addRowID+'" id="seach_CITYNM_'+addRowID+'"> '
           	 +city
		 	+'</SELECT> </td>'
	            	+'<td style="text-align: center"><SELECT name="PAIQIANDI_'+addRowID+'" id="PAIQIANDI_'+addRowID+'"> '
	            	 +paiqian1
			 	+'</SELECT> </td>'
            		+'<td style="text-align: center"> <SELECT name="PAIQIANLEIXING_'+addRowID+'" id="PAIQIANLEIXING_'+addRowID+'"> '
					 +paiqiantype
			 		+'</SELECT> </td>'
            		+'<td style="text-align: center">'
            		+'<input type="text" id="REMARK_'+addRowID+'" name="REMARK_'+addRowID+'" value=""/>'
            		//	+'   <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDL(this.id);"/>'
            			 
            		//+'   <img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			     //先删除，再排序--上面一种方法Firefox不支持
            			//+'	onclick="javaScript:document.all.addAffirm_listL.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowByIDL();"/>'
            			//+'      onclick="deleteRowL(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            $("table:last tr:last").after(str);
            
            //currentRow.after(str);
        }
    });
 
   	var tb2 = document.getElementById("paiqiantable");
 	//如果决裁者只有一个时，添加一个决裁者之后需要取消此按钮
  	 
   
   	var rowCount = tb2.rows.length;
    
   	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
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

//提交按钮
function f_paleftmen_submit_v(form, callback) {

	//选中检查
	var checked=false;
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
				jsonData += '{';
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
          if($("#PAIQIANDI_"+i).val()==''){
        	  paitemcheck='n';
              alert("派遣地不可空");
              return;
          }
			//jsonData += ' "TRANSNO": "' + $("#TRANSNO_"+i).val()  + '",';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+i).val()  + '",';
			jsonData += ' "DEPTNO": "' + $("#DEPTNO_"+i).val()  + '" ,';
			jsonData += ' "DUTY": "' + $("#DUTY_"+i).val()  + '" ,';
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
			url:'/hrm/transferOrder/submitSendAndSendOff',
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

function deleteRowL(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('paiqiantable').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("paiqiantable");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
 
    
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

</script>
<div class="pageContent">
		<div class="formBar">
			<ul class="toolBar">
			<li>
			
			 <a class="buttonActive"
											onclick="addRowByIDL('0'); "  ><span>添加</span>
										</a>
		    
			 </li>
									<li><!-- f_viewardetail_update('searchArDetailForm',DWZ.ajaxDone); -->
										<a class="buttonActive"
											onclick="f_paleftmen_submit_v('searchArDetailForm',navTabAjaxDone); "  ><span><!-- 提交 --><spring:message code="public.title.submit"/></span>
										</a>
									</li>	
									
		 
		</ul>
	</div>
		  
	<form name="updateArDetailForm" id="updateArDetailForm" method="post" action="/ar/attendanceMintenance/updateOrAddArDetail"
	 		 onsubmit="return cancelAgentTransValidateCallback(this, navTabAjaxDone);">
	 	
           <table id="paiqiantable" class="table"   width="200%" layoutH="80"  name="paiqiantable"  >
				<thead>
					<tr>
				 <th align="center" width="1%" >
				  <input type="checkbox" class="checkboxCtrl" group="c1">
				</th>
			    <%-- th align="center" width="5%">
				    <spring:message code="hr.viewPersonalInfo.title.falingbianhao"/><!--发令编号-->
				</th--%>
				<th align="center" width="8%">
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
				<th align="center" width="9%">
					<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
				</th>
				<th align="center" width="9%">
					<spring:message code="pa.insurance.title.endMonth"/><!-- 结束月  -->
				</th>
				<th align="center" width="6.5%">
					省份
				</th>
				<th align="center" width="6.5%">
					城市
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
			</tr>
				</thead>
				
				<tbody>
				
					<c:forEach var="i" begin="0" end="9">
					
					
					<tr id="${i}">   
			    <td align="center" width="1%">
			  
				
			   <input type="checkbox" id="c1_${i}" name="c1" value="c1_${i}">
				</td>
				<%--td align="center" width="10%">
					<input type="text" id="TRANSNO_${i}" name="TRANSNO_${i}" value=""/>
				</td --%>
				<td align="center" width="7%"> 
					<input type="hidden" id="PERSON_ID_${i}" name="PERSON_ID_${i}" lookupGroup="person" value=""/>
					<input type="text" id="EMP_ID_${i}" name="EMP_ID_${i}" value="" onkeydown="submitKeyClick_sy0130_add(this,'','','${request_navTabId}',event)" lookupGroup="person"/>
					<div   id="EMP_NAME_${i}" name="EMP_NAME_${i}"  lookupGroup="person"/> </div>
				</td>
				<td align="center" width="6.5%">
					 <input type="text" id="DEPTNO_${i}" name="DEPTNO_${i}" value="" lookupGroup="person" readonly/>
				</td>
				<td align="center" width="6.5%">
					 <input type="text" id="DUTY_${i}" name="DUTY_${i}" value="" lookupGroup="person" readonly/>
				</td>
				<td align="center" width="6.5%">
					<input type="text" id="EMPTYPE_${i}" name="EMPTYPE_${i}" lookupGroup="person" value="" readonly/>
				</td>
				<td align="center" width="6.5%">
					<input type="text" id="WORKAREA_${i}" name="WORKAREA_${i}" lookupGroup="person" value="" readonly/>
				</td>
				<td align="center" width="6.5%">
					<input type="text" id="CREATEDATE_${i}" name="CREATEDATE_${i}" class="required" style="background:#FFF" readonly="true" format="yyyy-MM-dd"
						yearstart="-50" yearend="5" onClick="setdate(this);"
						value="${archive.HAPPEN_DATE}" />
				</td>
				<td align="center" width="9%">
					 <ait:date yearName="paYearStartDate_${i}" monthName="paMonthStartDate_${i}"/>
				</td>
				<td align="center" width="9%">
					<ait:date yearName="paYearEndDate_${i}" monthName="paMonthEndDate_${i}"/>
				</td>
				
				<script type="text/javascript">
					
			 
				 function  changeSheng(i){
					var stateCd=$('#seach_STATENM_SHENG_'+i).val();
				 
					var cityCd=$('#seach_CITYNM_'+i).val(); 
					$.ajax({
						cache: false,
						url : '${base}/hrm/transferOrder/getListBySelect?type=CITY2&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM_'+i+'&seq='+i,
						type : "get",
						dataType : "html",
						success : function(data) {
							 
							$("#seach_CITYNM_"+i).html(data);
						}
					});
				
				}
				function changeCity(i){
					var stateCd=$('#seach_CITYNM_'+i).val();
					var cityCd=$('#PAIQIANDI_'+i).val(); 
					$.ajax({
						cache: false,
						url : '${base}/hrm/transferOrder/getListBySelect?type=REGIONTWO&parentNo='+stateCd+'&selected='+cityCd+'&name=PAIQIANDI_'+i+'&seq='+i,
						type : "get",
						dataType : "html",
						success : function(data) {
						 
							$("#PAIQIANDI_"+i).html(data);
						}
					});
				}
				
				</script>
				<td align="center" width="6.5%">

			<ait:SelectState id="seach_STATENM_SHENG_${i}" name="seach_STATENM_SHENG_${i}" onChange="changeSheng(${i})"  type="SHENG" parentNo="" selected="${SHENG}" limit="all"/>
				</td>
				
				<td align="center" width="6.5%">
				
				<!--<span id="seach_CITYNM_TWO_${i}" name="seach_CITYNM_TWO_${i}"></span>
			  
			 -->
			 <select id="seach_CITYNM_${i}" name="seach_CITYNM_${i}" onchange="changeCity(${i})">
				 
			</select>
			 
			 </td>
				
				<td align="center" width="6.5%">
					<SELECT name="PAIQIANDI_${i}" id="PAIQIANDI_${i}"    >
					 
					 </SELECT>
				 
			</select>
				</td>
				<td align="center" width="6.5%">
					 <SELECT name="PAIQIANLEIXING_${i}" id="PAIQIANLEIXING_${i}"> 
						<c:forEach items="${paiQianTypes}" var="pQd1" varStatus="j">
					 		<option value="${pQd1.CODE_NO}" >${pQd1.CODENAME}</option>
					 	</c:forEach>
					 </SELECT>
				</td>
				<td align="center" width="6%">
				<input type="text" id="REMARK_${i}" name="REMARK_${i}" value=""/>
				</td>
				
			</tr>
					</c:forEach>
				 
				 <input  type="hidden"  id="ceshi" value="haha"></input>
				</tbody>
				
				
			</table>
		     
			<a id="onck" name="onck"  href="" lookupGroup="person"></a>
	 </FORM>
	
</div>