
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script src="/resources/js/jquery/jquery.formatCurrency-1.4.0.js" type="text/javascript"></script>


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
//点击确定查询出人员的数据
function serachPeople() {
	var dep = document.getElementById("seach_DEPTNO").value;
	var ggs = document.getElementById("POST_GRADE_NO").value;
	var name = document.getElementById("namePeople").value;
	var code = document.getElementById("OrderType").value;
	if(code==""){
		
		//alertMsg.error("请先选择调令类型");
		alertMsg.error("<spring:message code='alert.trans.message.selectTrans'/>");
		return false;
	}
	alert($("#deptName_test").val());
	if($("#deptName_test").val()==""){
		//alertMsg.error("请选择部门");
		alertMsg.error("<spring:message code='alert.trans.message.selectDept'/>");
		return false;
	}
	
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
//选择调令类型，查询旧的调令数据（未发出的调令）
function titleName(value1) {
	//获得调令类型
	var transno1 =document.getElementById("OrderType");
	//获得调令类型当前选择的text值
	var codeText = document.getElementById("OrderType").options[transno1.selectedIndex].text;
	
	if(transno1.value==""){
		document.getElementById("tableTitleName").style.display="none";
		return false;
	}
	//根据选择的调令查询出对应的表头
	$.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getTranferOrderTitile?",
				data : 'code=' + value1,
				async:	false,
				dataType : "json",
				success : function(data) {
					document.getElementById("TRANSFER_ORDER_NO").value=data.maxTransNum;
					var htm = "";
					//<img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="removeOneRow(\'tr_'+i+'\',\'reward\')" style="cursor:hand;"/>
					document.getElementById("createTable").innerHTML = "";
					//	<table id="punishment_table" width="100%" class="tablea" layoutH="0" id="punishment_tab">
					
					htm += '<table id="tableTitleName" border="0" width="100%" cellpadding="0" cellspacing="0" class="tablea">';
					htm+="<thead>";
					htm += '<tr>';
					htm += '<th class="td_center" width="25"><img src="/resources/css/ligerUI/skins/icons/add.gif" onclick="addNewTable(this)" /> ';
					htm+='<th class="td_center" width="30"><input type="checkbox" name="checkboxAll" id="checkboxAll" onclick="checkBoxAll()"/><!--全选--></th>';
					
					$.each(data, function(key, value) {
						if(key!="maxTransNum"){
							     
						htm += '<th  class="td_center" id="' + key + '">'
								+ value + '</th>';
						}
					});
					
					
					htm += '</th>';
					htm+='<th class="td_center">History</th>';
					
					htm += '</tr>';
					htm+='</thead>';
					htm += '</table>';
					$("#createTable").append(htm);
				}
		});
		//根据选择的调令查询出对应的表头 查询对应的数据
		$.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getHrExperienceInsideSaveByTransCode?",
				data : 'code=' + value1+'&codeText='+codeText,
				dataType : "json",
				success : function(data) {	
					$.each(data, function(key, value) {
<%--							var tb = document.getElementById("tableTitleName");  --%>
<%--							var row = tb.insertRow(); --%>
<%-- 							row.innerText=value;--%>
 							//row.innerHTML="111";
 							
 							$("#tableTitleName").append(value);
 							var orderType=document.getElementById("OrderType").value;
 						//调令类型 是 职级变更时触发 添加事件
						//if(orderType=="123351"||orderType=="123316"){
							//var num=parseInt(parseInt(cloumeNum)-1  var cloumeNum=$("#tableTitleName tr").length;);
							var cloumeNum=$("#tableTitleName tr").length;
							var num=parseInt(parseInt(cloumeNum)-2);
							var id="GRADE_LEVEL_"+num;
							if(document.getElementById(id)!=null){
								$('#'+id).change(function(){ 
								var gradeLevel=document.getElementById(id).value;
								getZhiDengAndZhiJi(gradeLevel,num);
								})
								//$('#'+id).change();
							}
						//}
					});
				}
			});
			//searchPersonNameViewPersonalInfo4  工号姓名
			//deptNamesearchDeptNameViewPersonalInfo4_test 部门	
			//deptContentsearchDeptNameViewPersonalInfo4_test 部门
			//searchPostGradeNameViewPersonalInfo4  职级
			//postNoName  职级名称
          $("#searchPersonNameViewPersonalInfo4").val("");
		  $("#deptNamesearchDeptNameViewPersonalInfo4_test").val("");
		  $("#deptContentsearchDeptNameViewPersonalInfo4_test").val("");
		  $("#searchPostGradeNameViewPersonalInfo4").get(0).selectedIndex = 0;
		  $("#postNoName").get(0).options.length = 0;        
		  $("#select1NameViewPersonalInfo4").empty();
}



		function titleName1(value) {
		$("#tableTitleName").remove();//
			var transno1 =document.getElementById("OrderType");
			//获得调令类型当前选择的text值
			var codeText = document.getElementById("OrderType").options[transno1.selectedIndex].text;
			
			if(transno1.value==""){
				document.getElementById("createTable").style.display="none";
				$("#createTable").append("");
				return false;
			}else{
				document.getElementById("createTable").style.display="block";
			}
			$.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getTranferOrderTitile?",
				data : 'code=' + value,
				async:	false,
				dataType : "json",
				success : function(data) {
					document.getElementById("TRANSFER_ORDER_NO").value=data.maxTransNum;
					}
				});
					var htm = "";
					htm += '<table id="tableTitleName" border="0" width="100%" cellpadding="0" cellspacing="0" class="tablea">';
					htm+='<thead>';
					htm += '<tr>';
					htm+='<th class="td_center" width="30"><input type="checkbox" name="checkboxAll" id="checkboxAll" onclick="checkBoxAll()"/><!--全选--></th>';
					htm += '</th>';
					htm+='<th class="td_center">发令日期</th>';
					htm+='<th class="td_center">生效日期</th>';
					htm+='<th class="td_center">社号</th>';
					htm+='<th class="td_center">姓名</th>';
					htm+='<th class="td_center">现部门</th>';
					htm+='<th class="td_center">现职位</th>';
					htm+='<th class="td_center">现职责</th>';
					htm+='<th class="td_center">现职级</th>';
					htm+='<th class="td_center">现员工类型</th>';
					htm+='<th class="td_center">工作地</th>';
					htm+='<th class="td_center">派遣地</th>';
					htm+='<th class="td_center">备注</th>';
					htm += '</tr>';
					htm+='</thead>';
					htm += '</table>';
					$("#createTable").append(htm);	
		//根据选择的调令查询出对应的表头 查询对应的数据
		$.ajax({
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getHrExperienceInsideSaveByTransCode_1?",
				data : "code=123314",
				dataType : "json",
				success : function(data) {	
					$.each(data, function(key, value) {
 							$("#tableTitleName").append('<tr>' + value + '</tr>');		
 							var orderType=document.getElementById("OrderType").value;
 							var cloumeNum=$("#tableTitleName tr").length;
					});
				}
			});			
					
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
//点击确定查询出选择人的数据
function falingOkleftBtnNameInfo4() {
	
	var code = document.getElementById("OrderType").value
	var personId = document.getElementById("c1").value;
	var transno = document.getElementById("OrderType").value;
	var transno1 =document.getElementById("OrderType");
	var codeText = document.getElementById("OrderType").options[transno1.selectedIndex].text;
	var rightop=document.getElementById("select2NameViewPersonalInfo4");
	document.getElementById("cloumeNum").value=rightop.length;
	var personidName=document.getElementsByName("personid");
	var diaolingDate=document.getElementById("diaolingDate").value;
	if(rightop.length>0){
		for(var i=0;i<rightop.length;i++){
			var personId1=rightop[i].value.split(",");
			var bool=false;
			for(var j=0;j<personidName.length;j++){
				if(personidName[j].value==personId1[0]){
					bool=true;
					break;
				}
			}
			if(bool==false){
				personId=personId+rightop[i].value+"-";
			}
		}
	}else{
		//alert("'请选择需要发令的员工'  不能为空");
		alertMsg.error("<spring:message code='alert.trans.message.selectTransPeople'/>");
		return false;
	}
	document.getElementById("c1").value=personId;
	var cloumeNum=$("#tableTitleName tr").length;
	
	//选择的人显示在table
	$
			.ajax( {
				cache : false,
				type : 'post',
				url : "/hrm/transferOrder/getHrExperienceInsideByPersonId1?",
				data : 'personId=' + personId + "&transno=" + transno+"&cloumeNum="+cloumeNum
						+ "&code=" + code+"&codeText="+codeText+"&diaolingDate="+diaolingDate,
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {	
						
						$("#tableTitleName").append('<tr>' + value + '</tr>');		
						
						var orderType=document.getElementById("OrderType").value;				
					});
				$("#personId").val("");
				}
			});
	//sada
	$("#c1").val("");
	$("#select2NameViewPersonalInfo4").empty();
	

}
//添加新的一行
function addNewTable(row) {
	var code = document.getElementById("OrderType").value;
	if(code==""){
		//alertMsg.error("请先选择发令类型");
		alertMsg.error("<spring:message code='alert.trans.message.selectTrans'/>");
		return false;
	}
	//根据类型不同显示不同控件
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
					var cloumeNum1=$("#tableTitleName tr").length;
					var cloumeNum=parseInt(cloumeNum1)-2;
								    
					//jjy
					var maxNum=0; 
					var tmpStr;
                    $("#tableTitleName tr").find("td").each(function(x) {
						//console.log("x.c.id:::"+$(this).children().attr("id"));
                        //console.log("x.c.name:::"+$(this).children().attr("name"));
                        //console.log("x.c.val:::"+$(this).children().val());
						//if($(this).children().attr("name")=="cloumeNumValue") {
						if($(this).children().attr("name")=="chexkbox") {	
                            tmpStr = $(this).children().attr("id").replaceAll("box_","");
                            if(maxNum < Number(tmpStr)){
                            	maxNum=Number(tmpStr);
                            }
                            //console.log("tmpStr:::::"+tmpStr);
                            //console.log("Number:::::"+(Number(tmpStr)+1));
                            //console.log("max:::::"+maxNum);
                            //console.log("cloumeNum:::::"+cloumeNum);
                            //console.log("-------------------------");
                        }
					});
                    cloumeNum = maxNum+1;
                    //console.log("real cloumeNum:::::"+cloumeNum);
        
					var i=2;
					var td1 = tr.insertCell(0);  
						td1.className="td_center";                                             
						td1.innerHTML="<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' /><input type='hidden' name='cloumeNumValue' value='"+cloumeNum+"'>";
					var td0 = tr.insertCell(1);  
						td0.className="td_center";                                             
						td0.innerHTML="<input id='box_"+cloumeNum+"' type='checkbox' name='chexkbox' value='"+cloumeNum+"' />";
					
					
					$.each(data, function(key,value){
						var v=value.split(",");
						var td = tr.insertCell(i);  
							td.className="td_center";
							<%--1：文本框  2:下拉菜单 3:日期控件 --%>
							<%--
								EMPID
								TRANS_ORDER_TYPE
								LOCAL_NAME
								DEPTNO 
							--%>
							//获得table总的行数
						
						if(v[5]=="2"||key=="EMPID"||key=="TRANS_ORDER_TYPE"){	
							
							if(key=="EMPID"){
								td.innerHTML ="<input id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' type='text' class='required' value='' size='10' onkeydown='F_HR_SubmitKeyClick1(event,"+cloumeNum+")' />";
								
							}
							else if(key=="TRANSFER_ORDER_REASON"){
								if(v[4]==1){
									td.innerHTML ="<input id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' type='text' value='' size='10' />";
								}else if(v[4]==2){
									
									var name=key+"_"+cloumeNum;
									
									$.ajax({
									 cache: false,
									 type: 'get',
									 async:false,
									 url: "/hrm/transferOrder/selectTag?",
									 data:"parentNo="+v[1]+"&table="+v[2]+"&parameter="+v[3]+"&type="+v[4]+"&name="+name+"&cloumeNum1="+cloumeNum,
									 dataType:'html',
									 success: function(data) {
												td.innerHTML = ""+data+"";
									 }
								    });
									
									
								}else {
									td.innerHTML ="<input id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' type='text' value='' size='10' />";
								}
							}
							else if(key=="LOCAL_NAME"){
								td.innerHTML ="<input id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' type='text' value='' size='10' readonly='readonly'/>";
								
							}
							else if(key=="TRANS_ORDER_TYPE"){
								var transno1 =document.getElementById("OrderType");
								var codeText = document.getElementById("OrderType").options[transno1.selectedIndex].text;
								td.innerHTML="<td class='td_title'>"+codeText+"<input type='hidden' name='personid' id='personid_"+cloumeNum+"' /></td>";
							}
							else if(v[4]==1){
								td.innerHTML = "<input type='text' id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' size='10'/>";
							}else if(v[4]==2||v[4]==4){
								var name=key+"_"+cloumeNum;
								
								<%--下拉菜单要取出父ID --%>
								$.ajax({
									 cache: false,
									 type: 'get',
									 async:false,
									 url: "/hrm/transferOrder/selectTag?",
									 data:"parentNo="+v[1]+"&table="+v[2]+"&parameter="+v[3]+"&type="+v[4]+"&time="+1234+"&name="+name+"&cloumeNum1="+cloumeNum+"&key="+key,
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
												
												if((key=="DUTY_NO"&&v[6]=="123359")||(key=="DUTY_NO"&&v[6]=="123360")){
													var duty="DUTY_NO_"+cloumeNum;
													$("#"+duty).change(function(){
														dutySelect(cloumeNum);
													});    
												}
											}
											var orderType=document.getElementById("OrderType").value;
											//if(orderType=="123351"||orderType=="123316"){
												
											   var id="GRADE_LEVEL_"+cloumeNum;
												
												$('#'+id).change(function(){ 
													var gradeLevel=document.getElementById(id).value;
													getZhiDengAndZhiJi(gradeLevel,cloumeNum);
												})
										     	//POST_GRADE_NO
												  var pid="POST_GRADE_NO_"+cloumeNum;
												
												$('#'+pid).change(function(){ 
													var gradeLevel=document.getElementById(pid).value;
													getZhiJiAndZeToMing(gradeLevel,cloumeNum);
												})
											//}
											
											
									 }
								});
							}else if(v[4]==3){
								td.className="td_center";
								if(key =='TRANS_ORDER_ENDDATE' && v[6]=='123314'){
									td.innerHTML = "<input type='text' id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' class='date' readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);'/>";
								}else{
									td.innerHTML = "<input type='text' id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"' class='date required' readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);'/>";
								}
								//TRANS_ORDER_DATE_0
								if(document.getElementById("TRANS_ORDER_DATE_"+cloumeNum)!=null){
									document.getElementById("TRANS_ORDER_DATE_"+cloumeNum).value=document.getElementById("diaolingDate").value;
								}
							}
							
							
						}else{
							td.className="td_center";
							td.innerHTML = "<input type='hidden' value='' id='"+key+"_"+cloumeNum+"' name='"+key+"_"+cloumeNum+"'><div id='"+key+"_"+cloumeNum+"_div'  style='display: none'></div>";
						}	
							
							
							if(parseInt(parseInt(colums)-2)==parseInt(i)){
								td=tr.insertCell(parseInt(parseInt(colums)-1)); 
								td.className="td_center";
								td.innerHTML="<input type='button' value='History' onclick='falingHistory("+cloumeNum+")'/>";
								
							}
						
							
						i++;	
				
					});
							
							
				}
		});
	
		
	
	}

	//添加新行的工号文本框触发
	function F_HR_SubmitKeyClick1(event,i){
	   	if(event.keyCode==13){
	   		var emp= document.getElementById("EMPID_"+i).value;
			//15119设置默认查找在职员工
			document.getElementById("onck").href=encodeURI(encodeURI("/hrm/transferOrder/viewEmpIdList?pageNum=1&seach_EMPID="+emp+'&seach_LOCAL_NAME='+emp+'&seach_IDCARD_NO='+emp+'&seach_EMP_OFFICE=15119&empid='+i+'&viewEmpIdListColnum='+i ));
			//document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMP_OFFICE=15119'"));
			document.getElementById("onck").click();	
		}
 	}
	//双击返回的人员的数据
	function F_HR_ShowMore1(personId,name,empid,i,deptname,deptno,glno,glname,dutyno,dutynoname,postno,postname,postgradeno,postgradename,posino,posiname){
		//personid_
		i=document.getElementById("viewEmpIdListColnum").value;
		//alert(personId,name,empid,i,deptname,deptno,glno,glname,dutyno,dutynoname,postno,postname,postgradeno,postgradename,posino,posiname);
		if(document.getElementById("personid_"+i)!=null){
			document.getElementById("personid_"+i).value=personId;
			var personIdValue=personId+","+i;
			document.getElementById("box_"+i).value=personIdValue;
			
		}
		//工号
		
		if(document.getElementById("EMPID_"+i)!=null){
			document.getElementById("EMPID_"+i).value=empid;
		
			if(document.getElementById("LOCAL_NAME_"+i)!=null){
				document.getElementById("LOCAL_NAME_"+i).value=name;		
				document.getElementById("LOCAL_NAME_"+i+"_div").innerHTML=name;
				document.getElementById("LOCAL_NAME_"+i+"_div").style.display='block';
			}
		}
		
		//部门 DEPTNO_1_div   DEPTNO_1
		if(document.getElementById("DEPTNO_"+i)!=null){
			//div存在代表是单元格，否则是控件
			if(document.getElementById("DEPTNO_"+i+"_div")!=null){
				document.getElementById("DEPTNO_"+i+"_div").innerHTML=deptname;
				document.getElementById("DEPTNO_"+i+"_div").style.display='block';
				document.getElementById("DEPTNO_"+i).value=deptno;
			}else{
				document.getElementById("DEPTNO_"+i).value=deptno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
			
			
			
		}
		//职等GRADE_LEVEL_0
		if(document.getElementById("GRADE_LEVEL_"+i)!=null){
			//GRADE_LEVEL_0_div
			if(document.getElementById("GRADE_LEVEL_"+i+"_div")!=null){
				document.getElementById("GRADE_LEVEL_"+i+"_div").innerHTML=glname;
				document.getElementById("GRADE_LEVEL_"+i+"_div").style.display='block';
				document.getElementById("GRADE_LEVEL_"+i).value=glno;
			}else{
				document.getElementById("GRADE_LEVEL_"+i).value=glno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//职责DUTY_NO
		if(document.getElementById("DUTY_NO_"+i)!=null){
			//DUTY_NO_0_div
			if(document.getElementById("DUTY_NO_"+i+"_div")!=null){
				document.getElementById("DUTY_NO_"+i+"_div").innerHTML=dutynoname;
				document.getElementById("DUTY_NO_"+i+"_div").style.display='block';
				document.getElementById("DUTY_NO_"+i).value=dutyno;
			}else{
				document.getElementById("DUTY_NO_"+i).value=dutyno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//职级名称 POST_NO  
		if(document.getElementById("POST_NO_"+i)!=null){
			if(document.getElementById("POST_NO_"+i+"_div")!=null){
				document.getElementById("POST_NO_"+i+"_div").innerHTML=postname;
				document.getElementById("POST_NO_"+i+"_div").style.display='block';
				document.getElementById("POST_NO_"+i).value=postno;
			}else{
				document.getElementById("POST_NO_"+i).value=postno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		
		//职级POST_GRADE_NO_
		if(document.getElementById("POST_GRADE_NO_"+i)!=null){
			
			if(document.getElementById("POST_GRADE_NO_"+i+"_div")!=null){
				document.getElementById("POST_GRADE_NO_"+i+"_div").innerHTML=postgradename;
				document.getElementById("POST_GRADE_NO_"+i+"_div").style.display='block';
				document.getElementById("POST_GRADE_NO_"+i).value=postgradeno;
			}else{
				document.getElementById("POST_GRADE_NO_"+i).value=postgradeno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//职位名称 POSITION_NO_
		if(document.getElementById("POSITION_NO_"+i)!=null){
			if(document.getElementById("POSITION_NO_"+i+"_div")!=null){
				document.getElementById("POSITION_NO_"+i+"_div").innerHTML=dutynoname;
				document.getElementById("POSITION_NO_"+i+"_div").style.display='block';
				document.getElementById("POSITION_NO_"+i).value=posino;
			}else{
				document.getElementById("POSITION_NO_"+i).value=posino;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//职位名称 POSITION_NO_
		if(document.getElementById("POSITION_NO_"+i)!=null){
			if(document.getElementById("POSITION_NO_"+i+"_div")!=null){
				document.getElementById("POSITION_NO_"+i+"_div").innerHTML=posiname;
				document.getElementById("POSITION_NO_"+i+"_div").style.display='block';
				document.getElementById("POSITION_NO_"+i).value=posino;
			}else{
				document.getElementById("POSITION_NO_"+i).value=posino;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_DUTY_NO	现职责
		if(document.getElementById("CUR_DUTY_NO_"+i)!=null){
			if(document.getElementById("CUR_DUTY_NO_"+i+"_div")!=null){
				document.getElementById("CUR_DUTY_NO_"+i+"_div").innerHTML=dutynoname;
				document.getElementById("CUR_DUTY_NO_"+i+"_div").style.display='block';
				document.getElementById("CUR_DUTY_NO_"+i).value=dutyno;
			}else{
				document.getElementById("CUR_DUTY_NO_"+i).value=dutyno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_POST_GRADE_NO	现职级
		if(document.getElementById("CUR_POST_GRADE_NO_"+i)!=null){
			if(document.getElementById("CUR_POST_GRADE_NO_"+i+"_div")!=null){
				document.getElementById("CUR_POST_GRADE_NO_"+i+"_div").innerHTML=postgradename;
				document.getElementById("CUR_POST_GRADE_NO_"+i+"_div").style.display='block';
				document.getElementById("CUR_POST_GRADE_NO_"+i).value=postgradeno;
			}else{
				document.getElementById("CUR_POST_GRADE_NO_"+i).value=postgradeno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_POST_NO	现职级名称
		if(document.getElementById("CUR_POST_NO_"+i)!=null){
			if(document.getElementById("CUR_POST_NO_"+i+"_div")!=null){
				document.getElementById("CUR_POST_NO_"+i+"_div").innerHTML=postname;
				document.getElementById("CUR_POST_NO_"+i+"_div").style.display='block';
				document.getElementById("CUR_POST_NO_"+i).value=postno;
			}else{
				document.getElementById("CUR_POST_NO_"+i).value=postno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_POSITION_NO	现职位
		if(document.getElementById("CUR_POSITION_NO_"+i)!=null){
			if(document.getElementById("CUR_POSITION_NO_"+i+"_div")!=null){
				document.getElementById("CUR_POSITION_NO_"+i+"_div").innerHTML=posiname;
				document.getElementById("CUR_POSITION_NO_"+i+"_div").style.display='block';
				document.getElementById("CUR_POSITION_NO_"+i).value=posino;
			}else{
				document.getElementById("CUR_POSITION_NO_"+i).value=posino;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_GRADE_LEVEL	现职等
		if(document.getElementById("CUR_GRADE_LEVEL_"+i)!=null){
			if(document.getElementById("CUR_GRADE_LEVEL_"+i+"_div")!=null){
				document.getElementById("CUR_GRADE_LEVEL_"+i+"_div").innerHTML=glname;
				document.getElementById("CUR_GRADE_LEVEL_"+i+"_div").style.display='block';
				document.getElementById("CUR_GRADE_LEVEL_"+i).value=glno;
			}else{
				document.getElementById("CUR_GRADE_LEVEL_"+i).value=glno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		//CUR_DEPTNO	现部门
		if(document.getElementById("CUR_DEPTNO_"+i)!=null){
			if(document.getElementById("CUR_DEPTNO_"+i+"_div")!=null){
				document.getElementById("CUR_DEPTNO_"+i+"_div").innerHTML=deptname;
				document.getElementById("CUR_DEPTNO_"+i+"_div").style.display='block';
				document.getElementById("CUR_DEPTNO_"+i).value=deptno;
			}else{
				document.getElementById("CUR_DEPTNO_"+i).value=deptno;
				//document.getElementById("DEPTNO"+i).value=deptname
			}
		}
		$.pdialog.closeCurrent();
	}
	<%--查询当前人员的历史发令消息三 --%>
	function falingHistory(i){
		if($("#EMPID_"+i).val()==""){
			//alertMsg.error("请在对应文本框输入员工号");
			alertMsg.error("<spring:message code='alert.trans.message.selectTextPeople'/>");
			return false;
		}
		var code=$("#OrderType").val();
		
		 //var per= $("input[name='personid']").value                                          									
		 var box="box_"+i;
		 var per =document.getElementById(box).value;
		 var boxNum=per.lastIndexOf(",");
		 var boxSubStringPersonId=per.substring(0,boxNum);
		 
		document.getElementById("onck1").href=encodeURI(encodeURI("/hrm/transferOrder/viewOrderExamineSerach?code="+code+"&personid="+boxSubStringPersonId));						
		document.getElementById("onck1").click();	
	}
	
/*			
	function deleteNewTable(row){
			var table = document.getElementById("tableTitleName");
			$(row).parent().parent().remove();
			return false;
			$("#tableTitleName td").click(function () {
        		var tdSeq = $(this).parent().find("td").index($(this));
        		var trSeq = $(this).parent().parent().find("tr").index($(this).parent());
        		alert(trSeq+"-"+tdSeq);
        		return false;
        		 if(tdSeq=="0"&&trSeq>=0){
        			
        				table.deleteRow(parseInt(parseInt(trSeq)));
        			
        			
        		}
    		})		
	}
*/
    //jjy add
	function deleteNewTable(row){//alert("new!");
    		//确定要提交吗？

    		if (confirm ('<spring:message code="zxc.hrm.transferOrder.CONFIRM_DELETE"/>')){
                var rowNumStr= 0;
                var expInsideNo = "";
                var isTmp = true;
                var personId ="";
                
                // rownum확인, expInsideNo 확인(tmp data 여부 확인)
    			$(row).parent().parent().find("td").each(function(i,obj) {
    				if($(obj).children().attr("name")=="chexkbox") {
                 		var tmpNo = $(this).children().val().split(",");
                        if(tmpNo.length == 2){
                            tmpNo = $(this).children().val().split(",");
                            personId = tmpNo[0];
                            rowNumStr = tmpNo[1];
                        }else{
                        	rowNumStr = tmpNo[0];
                        }
                    }
    				if($(obj).children().attr("name")=="EXP_INSIDE_NO") {
    					expInsideNo =$(obj).children().val();
    					isTmp = false;
                    }
    			});
                //alert("rowNumStr:::"+rowNumStr);
                //alert("personId:::"+personId);
                //alert("expInsideNo:::"+expInsideNo);
                //alert("isTmp:::"+isTmp);
                
                // 방금 생성한 임시 레코라면 그냥 화면에서 삭제처리해버린다.
                if(isTmp) {
                    $(row).parent().parent().remove();
                    return false;
                }
                
                //서버에 저장된 레코드라면 화면에서 살짝 감춘 후 DB삭제 ajax를 호출한다. temp saved data delete -> display:none
                if(personId!="" || expInsideNo!=""){
                    $(this).parent().attr({ style: "display:none"});
                    //delete record 
                    $.ajax({
                        type: 'POST',
                        url:"/hrm/transferOrder/DeleteEachExperienceInside",
                        data:{del_personid: personId,
                              del_exp_inside_no: expInsideNo
                              },
                        dataType:"json",
                        cache: false,
                        success: function(data) {
                                alertMsg.info(data.message);
                                $(row).parent().parent().remove();
                              //document.getElementById("OrderType").onchange();
                        },
                        error: DWZ.ajaxError
                    });
                }
                
    			//------------------------------------------------------------------------------
                /*
                if($(this).children().attr("name")=="EXP_INSIDE_NO") {
                    //console.log("eNo find:"+$(this).children().val());
                    eNo = $(this).children().val();
                    isTempData = false;
                }
    			
    			$("#tableTitleName td").click(function () {
                    var tdSeq = $(this).parent().find("td").index($(this));
                    if(tdSeq > 0) return false;
                    var trSeq = $(this).parent().parent().find("tr").index($(this).parent());
                    //alert(trSeq+"-"+tdSeq);
                    var pId = "";
                    var eNo = "";
                    var isTempData = true;
                    //var idx = "";
                    var pStr = "";
                    $(this).parent().find("td").each(function(f) {
                        //console.log("f:::"+f);
                        //console.log("f.id:::"+$(this).attr("id"));
                        //console.log("f.c.id:::"+$(this).children().attr("id"));
                        //console.log("f.c.name:::"+$(this).children().attr("name"));
                        //console.log("f.c.val:::"+$(this).children().val());
                        if($(this).children().attr("name")=="chexkbox") {
                        	if($(this).children().val().split(",").length == 2){
                        		pStr = $(this).children().val().split(",");
                        		pId = pStr[0];
                        		//idx = pStr[1];
                        	}
                        }
                        
                        if($(this).children().attr("name")=="EXP_INSIDE_NO") {
                        	//console.log("eNo find:"+$(this).children().val());
                        	eNo = $(this).children().val();
                        	isTempData = false;
                        }
                    });
                    if(isTempData) {
                    	$(row).parent().parent().remove();
                        return false;
                    }
                                      
                    //when first td is clicked
                    if(tdSeq=="0"&&trSeq>=0){
                    	
                    	//temp saved data delete -> display:none
                    	if(pId!="" || eNo!=""){
                    		//alert("server go");
                    		$(this).parent().attr({ style: "display:none"});
                            //delete record 
                            $.ajax({
                                type: 'POST',
                                url:"/hrm/transferOrder/DeleteEachExperienceInside",
                                data:{del_personid: pId,
                                      del_exp_inside_no: eNo
                                      },
                                dataType:"json",
                                cache: false,
                                success: function(data) {
                                        alertMsg.info(data.message);
                                        $(row).parent().parent().remove();
                                      //document.getElementById("OrderType").onchange();
                                },
                                error: DWZ.ajaxError
                            });
                    	}else{
                    		//alert("local");
                            //new temp data delete -> remove
                            $(row).parent().parent().remove();
                    	}
                    }
                });                
                return false;
                */
            }
	}
	function dutySelect(cloumeNum){
		var dutyNo="DUTY_NO_"+cloumeNum;
		var postNo="POST_NO_"+cloumeNum;
		var dutyNoValue=document.getElementById(dutyNo).value;
		var post=document.getElementById(postNo);
			post.length=0;
		$.ajax({
					cache: false,
					type: 'get',
					async:false,
					url: "/hrm/transferOrder/dutySelect?",
					data:"dutyNoValue="+dutyNoValue,
					dataType:'json',
					success: function(data) {
						$.each(data, function(key,value){
							document.getElementById(postNo).options.add(new Option(value,key)); 
							
						});
				}
		});
	
	}
/*	
	//checkbox全选
	function checkBoxAll(){
			$("input[name='chexkbox']").attr("checked","true"); 
	}
*/	
	//jjy checkbox全选
	function checkBoxAll(){
	    if($("#checkboxAll").attr("checked") == "checked"){
	    	//alert("1:::"+$("#checkboxAll").attr("checked"));
	        //$("input[name='chexkbox']").attr("checked","true");
	        $("input[name='T1']").prop("checked",true);
	    	/* var obs = $("input[name='chexkbox']")
            obs.each(function(){
                $(this).prop("checked",true);
            }); */
	    }else{
	    	//alert("2:::"+$("#checkboxAll").attr("name"));
	    	$("input[name='T1']").prop("checked",false);
	    	/* var obs = $("input[name='chexkbox']")
	    	obs.each(function(){
	    		$(this).prop("checked",false);
            });
	    	 */
	    	//$("input[name='chexkbox']").attr("checked","false");
	    }
	}
	function changName(colId,rowID,rowValue,cloumeNum1){
		
		//alert(colId[0]);
		//alert(rowID);
		//alert(rowValue);
		//alert(cloumeNum1);
		//alert(colId.value);
		var colId1 = document.getElementById(rowID).value;
		var rowIdNameNum=rowID.lastIndexOf("_");
		var rowIdName= rowID.substring(0,rowIdNameNum);
		var selectName="";
		if(rowIdName=="POST_GRADE_NO"){
			selectName="POST_NO_";
		}
		
		if(document.getElementById(selectName+cloumeNum1)!=null){
			document.getElementById(selectName+cloumeNum1).length=0;
		}
	
		$.ajax({
					cache: false,
					type: 'get',
					async:false,
					url: "/hrm/transferOrder/viewSelectTag?",
					data:"name="+rowID+"&value="+colId1,
					dataType:'json',
					success: function(data) {
						
						$.each(data, function(key,value){
								
								document.getElementById(selectName+cloumeNum1).options.add(new Option(value,key)); 
						});
				}
		});
		
		
	}
	//通过职等 关联职级
function getZhiDengAndZhiJi(GRADE_LEVEL,cloumeNum){
	if(GRADE_LEVEL == null || GRADE_LEVEL.length == 0){
		return ;
	}
	
	if(GRADE_LEVEL.length>8){
		if(GRADE_LEVEL.substring(0,5)=="GRADE"){
			GRADE_LEVEL=document.getElementById(GRADE_LEVEL).value;
		}
	}
	
	
	var  postGradeNo="POST_GRADE_NO_"+cloumeNum;	
	var sel = $("#"+postGradeNo);//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiDengAndZhiJi?",
		 data: 'GRADE_LEVEL=' + GRADE_LEVEL,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
							
	
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	getZhiJiAndZeToMing($("#"+postGradeNo).val(),cloumeNum);
}

//查询职责和职级名称
function getZhiJiAndZeToMing(POST_GRADE_NO,cloumeNum){
	if(POST_GRADE_NO == null || POST_GRADE_NO.length == 0){
		return ;
	}

	var dutyNo="DUTY_NO_"+cloumeNum;
	var sel = $("#"+dutyNo);//职责
	
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiZe?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
			
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
							
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	var postNo= "POST_NO_"+cloumeNum;
	var sel = $("#"+postNo);//职级名称
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiJiMing?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
//查询职责和职级名称
function getZhiJiAndZeToMing1(POST_GRADE_NO1,cloumeNum){
	var POST_GRADE_NO=document.getElementById(POST_GRADE_NO1).value;
	if(POST_GRADE_NO == null || POST_GRADE_NO.length == 0){
		return ;
	}

	var dutyNo="DUTY_NO_"+cloumeNum;
	var sel = $("#"+dutyNo);//职责
	
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiZe?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
			
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
							
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	var postNo= "POST_NO_"+cloumeNum;
	var sel = $("#"+postNo);//职级名称
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiJiMing?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
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
			<td class="td_title width100">
					
				<spring:message code="heran.examineType.title"/><!--调令类型-->
			</td>
			<td class="td_type width140">

					<ait:SelectSyCodeByCpnyID name="OrderType" parentNo="123313"
					cnpyID="${defaultCpny}" limit="all"
					onChangeName="titleName1(this.value)" />
			
			</td>
			<td class="td_title width100">
				<spring:message code="hr.viewCondSql.title.transNo"/><!--调令编号--> 
					
			</td>
			<td class="td_type">
				<input type=""   id="TRANSFER_ORDER_NO" value="" name="TRANSFER_ORDER_NO" />&nbsp;&nbsp;&nbsp;&nbsp;
				
			</td>
			
		</tr>
	</table>
</div>

<div class="pageContent" style="z-index:0">

</div>
<ait:selTransferEmpInfo  select3Name="OrderType" select1Name="select1NameViewPersonalInfo4" searchPersonName="searchPersonNameViewPersonalInfo4" rightBtnName="rightBtnNameViewPersonalInfo4" searchPostGradeName="searchPostGradeNameViewPersonalInfo4" leftBtnName="leftBtnNameInfo4" searchDeptName="searchDeptNameViewPersonalInfo4" select2Name="select2NameViewPersonalInfo4" />

	



	
	
