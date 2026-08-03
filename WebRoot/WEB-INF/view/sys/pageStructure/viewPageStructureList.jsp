<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
	function delDetailPageStructure(param,rt_no){
		//var param=obj.parentElement.parentElement.rowIndex;
		var tempTable =document.getElementById('showPageStructureTable') ;
		if(rt_no != 0){
			var delRtNo=document.getElementById('del_rt_no').value;
			var delRtNoArr=delRtNo.split(",");
			var isFlag=true;
			for(var i=0;i<delRtNoArr.length;i++){
				if(delRtNoArr[i]==rt_no){
					isFlag=false;
					break;
				}
			}
			if(isFlag==true){
				if(document.getElementById('del_rt_no').value == '')
		    		document.getElementById('del_rt_no').value = rt_no ;
		    	else{
		    		document.getElementById('del_rt_no').value += ',' + rt_no ;
		    	}
			}
	    }
		//if(confirm("是否确定要删除?"))
	    	tempTable.deleteRow(param);
	} 
	function recordUpdateAlias(rt_no){
		var updateRtNo = document.getElementById("update_rt_no").value;
		var upNo;
		var rtNos=updateRtNo.split(",");
		var isFlag=false;
		for(var i=0;i<rtNos.length;i++){
			if(rtNos[i]==rt_no){
				isFlag=true;
				break;
			}
		}
		if(isFlag==false){
			if(updateRtNo==""){
				upNo = rt_no ;
			}else{
				upNo = updateRtNo+"," + rt_no ;
			}
			document.getElementById("update_rt_no").value=upNo;
		}
	} 
	function startRequestPS(menuNo){
		$.ajax({  
		    async : false,  
		    cache:false,  
		    type: 'POST',  
		    dataType : "json",  
		    url: "/sys/pageStructure/getPsDataList?MENU_NO="+menuNo,//请求的action路径  
		    error: function () {//请求失败处理函数  
		        alert('请求失败');  
		    },  
		    success:function(data){ //请求成功后处理函数。   
		    	initDataPs(data,data.length);
		    }  
		}); 
	}
	var parameterPa = {};
	var initRtNo=0;
	function initDataPs(obj){
		var rownum=document.getElementById('showPageStructureTable').rows.length;
		for(var i=rownum-1;i>0;i--){
			document.getElementById('showPageStructureTable').deleteRow(i);
		} 
		if(obj.length>0){
			for(var i=0;i<obj.length;i++){
				if(initRtNo==0||initRtNo==obj[i].RT_NO){
					parameterPa['proName_'+obj[i].LANGUAGE]=obj[i].CONTENT;
				}
				if((initRtNo!=0&&initRtNo!=obj[i].RT_NO)||i==obj.length-1){//换行或者最后一个
					if(i==0&&i==obj.length-1){//如果只有一条数据，只有一中国际化表名
						addPageStructureTable2(obj[i].REPORT_TYPE,obj[i].VIEW_MODEL,obj[i].RT_NO);
					}else{
						addPageStructureTable2(obj[i-1].REPORT_TYPE,obj[i-1].VIEW_MODEL,obj[i-1].RT_NO);
					}
					parameterPa = {};//换行之后，清空map,准备放入新的键值对
					if(initRtNo!=0&&initRtNo!=obj[i].RT_NO){
						parameterPa['proName_'+obj[i].LANGUAGE]=obj[i].CONTENT;
					}
				}
				if((initRtNo!=0&&initRtNo!=obj[i].RT_NO)&&i==obj.length-1){//换行的同时，也是最后一个
					addPageStructureTable2(obj[i].REPORT_TYPE,obj[i].VIEW_MODEL,obj[i].RT_NO);
				}
				initRtNo=obj[i].RT_NO;
			}
		}
		initRtNo=0;
	}

	function addPageStructureTable2(REPORT_TYPE,VIEW_MODEL,rt_no){
		//添加的表的信息
		  var cellInner;
		  var rowInner;
	      var txtNode;
		  var inputName;
		  var showModelSelect;
		  var showModeOption;
		  var rowNum=document.getElementById('showPageStructureTable').rows.length;
	  	  var nTr = document.getElementById('showPageStructureTable').insertRow(rowNum);
		  var cell;
		  cell =nTr.insertCell(0); 
		  cell.className="td_type"; 
		  cell.style.textAlign="center" ;
		  var nameTable=document.createElement("table");
		  nameTable.style.width="100%";
		  
		  <c:forEach items="${languageList}" var="language" varStatus="i">
			  var rowCountInner="${fn:length(languageList)/3}";
			  var cellMore=3-"${fn:length(languageList)%3}"+1;//用于cellSpan,该值不为4时
		 	  var rowNumInner="${i.count%3}"; 
		 	  if(rowNumInner==1){
		 		 rowInner = nameTable.insertRow("${i.count/3}");
		 	  }
		 	  cellInner =rowInner.insertCell("${i.count%3}"-1);
		      if(rowNumInner==2){ 
		 	 	  cellInner.style.width="34%";
		 	  }else{  
		 		  cellInner.style.width="33%";
			  }
		 	  txtNode=document.createTextNode('${language.DESCRIPTION}:');
		 	  inputName=document.createElement("input");
		 	  inputName.setAttribute("id","proName_${language.LANGUAGE}_"+rt_no);
		 	  inputName.setAttribute("name","proName_${language.LANGUAGE}_"+rt_no);
		 	  inputName.setAttribute("type","text");
		 	  var tableName=parameterPa['proName_${language.LANGUAGE}'];
		 	  if(tableName&&tableName!=null){
		 		 inputName.setAttribute("value",tableName+"");
			  }else{
				  inputName.setAttribute("value","");
			  }
		 	  inputName.onchange=function(){
		 		recordUpdateAlias(rt_no);
			  }
		 	  <c:if test="${language.LANGUAGE eq interLanguage}">
		 	  	 inputName.className="required";
		 	  </c:if>
		 	  <c:if test="${language.LANGUAGE ne interLanguage}">
		 	   	 inputName.className="textInput";
		 	  </c:if> 
		 	  cellInner.appendChild(txtNode);
		 	  cellInner.appendChild(inputName);
		  </c:forEach>
		  cell.appendChild(nameTable);	
		  cell = nTr.insertCell(1);//普通样式
		  cell.style.textAlign="center";
		  if(REPORT_TYPE=="1"){
			  cell.innerHTML="普通样式"
		  }else{
			  cell.innerHTML="日期样式";
		  }
		  
		  cell =nTr.insertCell(2);//数据归属
		  cell.style.textAlign="center";
		  if(VIEW_MODEL=="1"){
			  cell.innerHTML="工资信息"
		  }else{
			  cell.innerHTML="考勤信息";
		  }
		  
		  cell = nTr.insertCell(3); //操作列
		  cell.style.textAlign="center";
		  var hrefA = document.createElement("a");
		  hrefA.innerHTML="添加列";
		  hrefA.onclick=function(){
			if(VIEW_MODEL=="1"){
				document.getElementById("addAliasButton").href="/sys/pageStructure/addNewAliasView?TABLE_NAME=PA_SUMMARY&&RT_NO="+rt_no;
			}else{
				document.getElementById("addAliasButton").href="/sys/pageStructure/addNewAliasView?TABLE_NAME=AR_SUMMARY&&RT_NO="+rt_no;
			}
			document.getElementById("addAliasButton").click();
		  }
		  hrefA.style.cursor="hand";
		  cell.appendChild(hrefA);
		  var blankSpan = document.createElement("span");
		  blankSpan.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;";
		  cell.appendChild(blankSpan);
		  var hrefB = document.createElement("a");
		  hrefB.innerHTML="修改列";
		  hrefB.onclick=function(){
			if(VIEW_MODEL=="1"){
				document.getElementById("editAliasButton").href="/sys/pageStructure/updateNewAliasView?TABLE_NAME=PA_SUMMARY&&RT_NO="+rt_no;
			}else{
				document.getElementById("editAliasButton").href="/sys/pageStructure/updateNewAliasView?TABLE_NAME=AR_SUMMARY&&RT_NO="+rt_no;
			}
			document.getElementById("editAliasButton").click();
		  }
		  hrefB.style.cursor="hand";
		  cell.appendChild(hrefB);
		  
		  cell =nTr.insertCell(4); //删除
		  cell.style.textAlign="center";
		  var deleteSpan = document.createElement("span");
		  deleteSpan.style.cursor="hand";
		  deleteSpan.innerHTML="删除";
		  deleteSpan.onclick=function(){
			  var rowIndex=deleteSpan.parentElement.parentElement.rowIndex;
			  delDetailPageStructure(rowIndex,rt_no);
		  }
		  cell.appendChild(deleteSpan);
	}
</SCRIPT>
 
 
<div class="pageContent">
	<a id="operateAliasButton"  href="/sys/pageStructure/addNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">操作列</a>
	<a id="addAliasButton"  href="/sys/pageStructure/addNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">添加列</a>
	<a id="editAliasButton"  href="/sys/pageStructure/updateNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">修改列</a>
	<form method="post" action="/sys/pageStructure/saveAliasInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	
		<input type="hidden" name="del_rt_no" id="del_rt_no" value="" />
		<input type="hidden" name="update_rt_no" id="update_rt_no" value="" />
		<input type="hidden" name="add_table_index" id="add_table_index" value="" />
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<div class="formBar">
		<select onchange="startRequestPS(this.value);" id="MENU_NO" name="MENU_NO">
			<option value="">--请选择--</option>
			<c:forEach items="${pageStructureList}" var="menu">
				<option value="${menu.MENU_NO}">${menu.MENU_NAME}</option>
			</c:forEach>
		</select>
		</div>		
		<div class="panel">
		<h1>&nbsp;</h1>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table"  id="showPageStructureTable" name="showPageStructureTable">
				<tr>
					<td class="td_title" align="center" style="text-align: center" width="50%">名称</td>
					<td class="td_title" align="center" style="text-align: center">显示模式</td>
					<td class="td_title" align="center" style="text-align: center">数据归属</td>
					<td class="td_title" align="center" style="text-align: center">&nbsp;</td>
					<td class="td_title" align="center" style="text-align: center">&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="clear:both;"></div>
	<div class="panel" style="font-weight:lighter">
		<h1>
			<div class="formBar">
				<ul class="toolBar">
					<li><a class="add" href="/sys/pageStructure/addNewTableView" target="dialog" width="400" height="240" mask="true"><span style="cursor:hand">新建表</span></a></li>
					<!-- <li><a class="delete" href="#" onclick="deleteRow();"><span style="cursor:hand">删除</span></a></li>	 -->		
					<li class="line">line</li>
				</ul>
			</div>
		</h1>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table" id="pageStructureTable">
				<tr>
					<td class="td_title" align="center" style="text-align: center">名称</td>
					<td class="td_title" align="center" style="text-align: center">显示模式</td>
					<td class="td_title" align="center" style="text-align: center">数据归属</td>
					<td class="td_title" align="center" style="text-align: center">&nbsp;</td>
					<td class="td_title" align="center" style="text-align: center">&nbsp;</td>
				</tr>
			</table>
		</div>
		</div>
		
	</form>
	
</div>
