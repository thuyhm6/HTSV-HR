<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {

	//为添加按钮增加事件  
		var person = "";
		$("#leftbtn").click(function() {

			//获取选择的值  
				$("#leftop option:selected").each(function(i) {

					//在右边添加所选值，并且添加之后在左边删除所选值  
						if(document.getElementById("c1").value==""){
							person="";
						}else{
							person="";
							person1=document.getElementById("c1").value;
						}
						person = person + this.value + "-";
						$("#rightop").append(
								"<option value='" + this.value + "'>"
										+ this.text + "</option>");
					}
				).remove();				
			});
		$("#leftbtn1").click(function() {

			//获取选择的值  
				$("#leftop option:selected").each(function(i) {

					//在右边添加所选值，并且添加之后在左边删除所选值  
						$("#rightop").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>");

					}).remove();

			});
		//为删除按钮增加事件  
		$("#rightbtn").click(function() {

			//获取所选择的值  
				$("#rightop option:selected").each(function(i) {

					//在左边添加所选值，并且添加之后在右边删除所选值  
						$("#leftop").append(
								"<option>" + this.text + "</option>");
					}).remove();
					
			});

		//增加删除按钮事件  
		$("#del").click(function() {

			//获取要删除的值  
				$("#rightop option:selected,#leftop option:selected").each(
						function(i) {

							//删除所选值  
							$(this).remove();

						});

			});

	});

function serachPeople() {
	var dep = document.getElementById("seach_DEPTNO").value;
	var ggs = document.getElementById("POST_GRADE_NO").value;
	var name = document.getElementById("namePeople").value;
	var code = document.getElementById("OrderType").value;

	var sel = $("#leftop");
	sel.empty();
	$.ajax( {
		cache : false,
		type : 'post',
		url : "/hrm/transferOrder/viewOrderList?",
		data : 'seach_DEPTNO=' + dep + "&ggs=" + ggs + "&name=" + name,
		dataType : "json",
		success : function(data) {
			$.each(data, function(key, value) {
				if ($(data).size() > 0) {

					sel.append('<option value="' + value + '" selected>' + key
							+ '</option>');

				}
			});
		}
	});
}
function titleName(value1) {

	$.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getTranferOrderTitile?",
				data : 'code=' + value1,
				async:	false,
				dataType : "json",
				success : function(data) {
					var htm = "";
					document.getElementById("createTable").innerHTML = "";
					htm += '<table layoutH="600" id="tableTitleName" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
					htm += '<tr>';
					$.each(data, function(key, value) {

						htm += '<td  class="td_title" id="' + key + '">'
								+ value + '</td>';
					});
					htm += '<td class="td_title"><img src="/resources/images/newImages/Add_en.gif" onclick="addNewTable(this)" /> ';
					htm	+='<a class="buttonActive" href="/pa/excelExport/exportDiaoLingInputItemDataExcelIsNullModuleType?code='+value1+'"><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--><\/span><\/a>';
					htm	+='<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData"><span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --><\/span><\/a>';
					htm += '</td>';
					htm += '</tr>';
					htm += '</table>';
					$("#createTable").append(htm);
				}
		});
		$.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getHrExperienceInsideSaveByTransCode?",
				data : 'code=' + value1,
				dataType : "json",
				success : function(data) {
				
					$.each(data, function(key, value) {
							alert(value);
							var tb = document.getElementById("tableTitleName");  
							var row = tb.insertRow(); 
 							row.innerHTML=value;
					});
					
					
				}
			});
}
function titleName1(value) {
	var hrefUrl = "/hrm/transferOrder/getTranferOrderTitile?code=" + value;
	//var cpnyId=document.getElementById("CPNY_ID").value;
	//var backHref=hrefUrl+"MENU_NO="+treeNode.MENU_NO+"&CPNY_ID="+cpnyId;
	document.getElementById("viewZuzhijiegou").href = hrefUrl;
	document.getElementById("viewZuzhijiegou").click();
}
function serachPeople1() {
	var newTr = tableTitleName.insertRow();

	//添加两列
	for ( var i = 0; i < 8; i++) {
		var newTd0 = newTr.insertCell();
		newTd0.style.width = "100px";
		newTd0.style.height = "20px";

	}

}
function falingOk() {

	var code = document.getElementById("OrderType").value;

	var personId = document.getElementById("c1").value;

	var transno = document.getElementById("OrderType").value;
	
	var rightop=document.getElementById("rightop");
	document.getElementById("cloumeNum").value=rightop.length;
	if(rightop.length>0){
		for(var i=0;i<rightop.length;i++){
			personId=personId+rightop[i].value+"-";
		}
	}else{
		alert("'请选择需要发令的员工'  不能为空");
		return false;
	}
	document.getElementById("c1").value=personId;
	var cloumeNum=$("#tableTitleName tr").length;
	
	$
			.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getHrExperienceInsideByPersonId?",
				data : 'personId=' + personId + "&transno=" + transno+"&cloumeNum="+cloumeNum
						+ "&code=" + code,
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
						$("#tableTitleName").append('<tr>' + value + '</tr>');
					});
				$("#personId").val("");
				}
			});

	$("#rightop").empty();
	

}
function addNewTable(row) {
	var code = document.getElementById("OrderType").value;
	
	$.ajax({
			 cache: false,
			 type: 'post',
			 url: "/hrm/transferOrder/getTranferOrderTitile1?",
			 data: 'code=' + code,
			 dataType:"json",
			 success: function(data) {
					var table = document.getElementById("tableTitleName");
					var colums = table.rows[0].cells.length;
					var tr = table.insertRow();
					
					var i=0;
					$.each(data, function(key,value){
						var v=value.split(",");
						var td = tr.insertCell(i);  
							td.className="td_title";
							<%--1：文本框  2:下拉菜单 3:日期控件 --%>
							<%--
								EMPID
								TRANS_ORDER_TYPE
								LOCAL_NAME
								DEPTNO 
							--%>
							//获得table总的行数
							var cloumeNum=$("#tableTitleName tr").length;
						
							
							
							if(key=="EMPID"){
								td.innerHTML ="<input id='"+key+cloumeNum+"' name='EMPID' type='text' value='' size='10' onkeydown='F_HR_SubmitKeyClick1(event,"+cloumeNum+")' /><input type='hidden' id='h_"+key+cloumeNum+"'>";
								
							}
							else if(key=="TRANS_ORDER_TYPE"||key=="LOCAL_NAME"){
								td.innerHTML ="<input id='"+key+cloumeNum+"' name='' type='text' value='' size='10' readonly='readonly'/>";
								
							}
							else if(v[4]==1){
								td.innerHTML = "<input type='text'/>";
							}else if(v[4]==2||v[4]==4){
			
								<%--下拉菜单要取出父ID --%>
								$.ajax({
									 cache: false,
									 type: 'get',
									 async:false,
									 url: "/hrm/transferOrder/selectTag?",
									 data:"parentno="+v[1]+"&table="+v[2]+"&parameter="+v[3]+"&type="+v[4]+"&time="+1234,
									 dataType:'html',
									 success: function(data) {
											
											if(v[4]==4){                           
											
											var num1=data.indexOf("<SCRIPT type='text/javascript'>");                   
											
											var num2=data.indexOf("\<\/SCRIPT\>");
											var data0=data.substring(49,num2);
											var data1=data.substring(parseInt(num2+9));
											//td.innerHTML = ""+data1+"";
											td.innerHTML="<div id='div1234'></div>";
											$('#div1234').html(data);
											eval(data0);
											}else{
												td.innerHTML = ""+data+"";
											}
									 }
								});
							}else if(v[4]==3){
								td.className="td_title";
								td.innerHTML = "<input type='text' id='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);'/>";
							}
							
							if(parseInt(parseInt(colums)-2)==parseInt(i)){
								td=tr.insertCell(parseInt(parseInt(colums)-1)); 
								td.className="td_title";
								td.innerHTML="<input type='button' value='History' onclick='falingHistory("+cloumeNum+")'/>";
								
							}
							
							
						i++;	
				
					});
							
							
				}
		});
	}
<%--				var htm="";--%>
<%--				document.getElementById("createTable1").innerHTML="";--%>
<%--		   		htm+='<table id="tableTitleName1" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';--%>
<%--		   		--%>
<%--				--%>
<%--				--%>
<%--				htm+='<tr>';--%>
<%--				$.each(data, function(key,value){--%>
<%--					var v=value.split(",");--%>
<%--					if(v[1]==1){--%>
<%--						htm+='<td  class="td_title" id="'+key+'"><input type="text" /></td>';--%>
<%--					}else if(v[1]==2){--%>
<%--						alet("下来菜单");--%>
<%--					}--%>
<%--		   			--%>
<%--				});--%>
<%--				htm+="<td  class='td_title'><input type='button' value='History'></td>";--%>
<%--				htm+='</tr>';--%>
<%--				--%>
<%--		   		htm+='</table>';--%>
<%--				$("#createTable1").append(htm) ;--%>
<%--		 }--%>
		
	function historyPersonId(personid){
		alert(personid);
	}
	function F_HR_SubmitKeyClick1(event,i){
		
	   	if(event.keyCode==13){
	   	var emp= document.getElementById("EMPID"+i).value;
	  
								//15119设置默认查找在职员工
								document.getElementById("onck").href=encodeURI(encodeURI("/hrm/transferOrder/viewEmpIdList?pageNum=1&seach_EMPID="+emp+'&seach_LOCAL_NAME='+emp+'&seach_IDCARD_NO='+emp+'&seach_EMP_OFFICE=15119&empid='+i ));
								//document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMP_OFFICE=15119'"));
								document.getElementById("onck").click();	
							}
 	}
	function F_HR_ShowMore1(personId,name,empid,i,deptname){
		//var textId="EMPID,"+"TRANS_ORDER_TYPE,"+"LOCAL_NAME,"+"DEPTNO"; 
		if(document.getElementById("EMPID"+i)!=null){
			document.getElementById("EMPID"+i).value=empid;
		}
	
		
		
		
		if(document.getElementById("LOCAL_NAME"+i)!=null){
			document.getElementById("LOCAL_NAME"+i).value=name;
		}
		
		if(document.getElementById("TRANS_ORDER_TYPE"+i)!=null){
		//获取发令类型
		var falingName=document.getElementById("OrderType");
		 
		var obj=document.getElementById('OrderType');  
  
    	var index=obj.selectedIndex; //序号，取当前选中选项的序号  
  
     	var val = obj.options[index].text;  嗯
			document.getElementById("TRANS_ORDER_TYPE"+i).value=val;
		}
		if(document.getElementById("DEPTNO"+i)!=null){
			document.getElementById("DEPTNO"+i).value=deptname
		}
		if(document.getElementById("h_EMPID"+i)!=null){
			document.getElementById("h_EMPID"+i).value=personId;
		}
		$.pdialog.closeCurrent();
	}
	<%--查询当前人员的历史发令消息三 --%>
	function falingHistory(i){
		//功能尚未开发
		return false;
		if(document.getElementById("EMPID"+i).value==""){
			alertMsg.error("请在对应文本框输入员工号");
			return false;
		}
		var PERSONID=document.getElementById("h_EMPID"+i).value;
		var transno=document.getElementById("OrderType").value;
		document.getElementById("onck1").href=encodeURI(encodeURI("/hrm/transferOrder/getHrExperiencInsideByEmpIdAndTransNo"));						
		document.getElementById("onck1").click();	
	}
	
	
	
</script>
<%--
titleNameOrderOperation1
--%>
<input type="hidden" value="0" id="diaolingType" name=""diaolingType""/>
<input type="hidden" value="0" id="cloumeNum" name="cloumeNum"/>
<input type="hidden" value="" id="c1" name="c1"  size="200"/>
<input type="hidden" id="isEssSystem" value="${isEssSystem }" />
<div class="pageContent">
	<a id="onck" name="onck" href="/hrm/empinfo/viewEmpIdList?pageNum=1"
		lookupGroup="person" width="950"></a>
	<a id="onck1" name="onck1" href="/hrm/empinfo/viewEmpIdList?pageNum=1"
		lookupGroup="person" width="950"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		height="20" class="user_table">
		<tr>
			<td class="td_title">
				 调令类型
			</td>
			<td class="td_type" width="15%">

				<ait:SelectSyCodeByCpnyID name="OrderType" parentNo="123313"
					cnpyID="${defaultCpny}" limit="all"
					onChangeName="titleName(this.value)" />

			</td>
			<td class="td_title">
				调令编号				
			</td>
			<td class="td_type">
				<input type="" value="" name="" />
			</td>
			<td class="td_type">
				<input type="button" value="调令申请" onclick="diaolingSubmit(1)" />
				<input type="button" value="保存"  onclick="diaolingSubmit(2)" />
			</td>
			<td>

			</td>
		</tr>
	</table>
</div>

<div class="pageContent">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="user_table">
		<tr>
			<td class="td_title" width="10%">
				部门
			</td>
			<td class="td_type" width="10%">
				<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"  />
			</td>
			<td class="td_title" rowspan="3" width="10">
				<input type="button" value="查询" onclick="serachPeople()" />
			</td>
			<td class="td_type" rowspan="3" colspan='3' width="25%" height="21"
				align="center" valign="middle">
				<div align="center">
					<h4 align="center">
						查看结果
					</h4>
					<select name="leftop" id="leftop" multiple="multiple" size="12"
						style="width: 150px; height: 180px;">


					</select>

				</div>
			</td>
			<td rowspan="3" width="7%" align="center" valign="middle">
				<div align="center">
					<img id="leftbtn" alt="" src="/resources/images/newImages/arrow_right_all.jpg">
					
					<br/>
					<br/>
					<br/>
					<br/>
					<img alt="" id="rightbtn" src="/resources/images/newImages/arrow_left_all.jpg">
					
				</div>
				
			</td>
				
				<td class="td_type" rowspan="3" colspan='3'  height="21" align="center" valign="middle"  width="25%" >
					<div align="center">
						<h4 align="center">选择调令</h4>
						<select id="rightop" multiple="multiple" size="12"
								style="width: 150px; height: 180px;">
								

						</select>
					</div>
				</td>
					
				
				
				<td  rowspan="3" width="10%">
					<div align="center">
						<input type="button" value="确认" onclick="falingOk()"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<spring:message code="sys.postManage.title.postGrade"/><!--职级-->
				</td>
				<td class="td_type">
					<select name="POST_GRADE_NO" id="POST_GRADE_NO" >
						<option value="">--<spring:message code="sys.affirm.title.choose"/>--</option>
				 		<c:forEach items="${postGradeList}" var="grade">
							<option value="${grade.POST_GRADE_NO}">${grade.GRADENAME}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<!-- 社号/姓名： --> <spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
				</td>
				<td class="td_type">
					<input type="text" id="namePeople" name="namePeople" />
				</td>
			</tr>
			<tr>
			</tr>
		
		</table>
	
</div>




	
	
