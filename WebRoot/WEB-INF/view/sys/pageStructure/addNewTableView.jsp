<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
var initNum=0;
function addPageStructureTable(){
	//添加的表的信息
    var fillInitNum = document.getElementById("add_table_index").value;
    if(fillInitNum!=""){
    	initNum = fillInitNum;
    }
	var closeDialogFlag=true;
	var obj = $('.required').each(function(){//这里 this获得的就是每一个dom对象 如果需要jquery对象 需要写成$(this)
	  	var idStr=this.id;
	  	if(idStr!=null&&idStr=="tableName_${interLanguage}"&& this.value==""){
	  		alertMsg.info("有必填项!");
	  		closeDialogFlag=false;
	  	} 
	});
	//关闭当前的dialog
	if(closeDialogFlag==true){
	  var cellInner;
	  var rowInner;
      var txtNode;
	  var inputName;
	  var showModelSelect;
	  var showModeOption;
	  var rowNum=document.getElementById('pageStructureTable').rows.length;
  	  var nTr = document.getElementById('pageStructureTable').insertRow(rowNum);
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
	 	  inputName.setAttribute("id","proName_${language.LANGUAGE}_"+initNum);
	 	  inputName.setAttribute("name","proName_${language.LANGUAGE}_"+initNum);
	 	  inputName.setAttribute("type","text");
	 	  var tableName=document.getElementById("tableName_${language.LANGUAGE}").value;
	 	  inputName.setAttribute("value",tableName);
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
	  showModelSelect = document.createElement("select");
	  showModelSelect.setAttribute("id","REPORT_TYPE_"+initNum);//REPORT_TYPE_  VIEW_MODEL_
	  showModelSelect.setAttribute("name","REPORT_TYPE_"+initNum);
	  var sValue=document.getElementById("showModelPs1").value;
	  for(var i=0;i<document.getElementById("showModelPs1").options.length;i++){
		  showModeOption = document.createElement("option");
		  var sText=document.getElementById("showModelPs1").options[i].text;
		  if(document.getElementById("showModelPs1").options[i].value==sValue){
			  showModeOption.setAttribute("selected","selected"); 
		  }
		  txtNode =document.createTextNode(sText);//普通样式
		  showModeOption.appendChild(txtNode);
		  showModeOption.setAttribute("value",document.getElementById("showModelPs1").options[i].value);
		  showModelSelect.appendChild(showModeOption);
	  }
	  cell.appendChild(showModelSelect);
	  cell.style.textAlign="center";
	  
	  cell =nTr.insertCell(2);//数据归属
	  showModelSelect = document.createElement("select");
	  showModelSelect.setAttribute("id","VIEW_MODEL_"+initNum);
	  showModelSelect.setAttribute("name","VIEW_MODEL_"+initNum);
	  showModelSelect.onchange=function(){
			var reportType=this.value;
			var num=this.id.replace("VIEW_MODEL_","");
			if(reportType=="2"){
				var option=document.createElement("option");
				option.setAttribute("value","2");
				var txtNode =document.createTextNode("日期样式");//数据归属
				option.appendChild(txtNode);
				var selectModel=document.getElementById("REPORT_TYPE_"+num);
				selectModel.appendChild(option);
			}else{
				var childs1=document.getElementById("REPORT_TYPE_"+num).childNodes;
				for(var i=0;i<childs1.length;i++){
		 			if(childs1[i].value=="2"){
		 				document.getElementById("REPORT_TYPE_"+num).removeChild(childs1[i]);
		 				break;
		 	 		}
				}
			}
	  }
	  var modeValue=document.getElementById("showModelPs2").value;
	  for(var i=0;i<document.getElementById("showModelPs2").options.length;i++){
		  showModeOption = document.createElement("option");
		  var modeText=document.getElementById("showModelPs2").options[i].text;
		  if(document.getElementById("showModelPs2").options[i].value==modeValue){
			  showModeOption.setAttribute("selected","selected"); 
		  }
		  txtNode =document.createTextNode(modeText);//数据归属
		  showModeOption.appendChild(txtNode);
		  showModeOption.setAttribute("value",document.getElementById("showModelPs2").options[i].value);
		  showModelSelect.appendChild(showModeOption);
	  }
	  cell.appendChild(showModelSelect);
	  cell.style.textAlign="center";
	  
	  cell = nTr.insertCell(3); //操作列
	  cell.style.textAlign="center";
	  var hrefA = document.createElement("a");
	  hrefA.innerHTML="操作列"; 
	  hrefA.setAttribute("id",initNum);
	  hrefA.onclick=function(){
		if(modeValue=="1"){
			document.getElementById("operateAliasButton").href="/sys/pageStructure/addNewAliasView?TABLE_NAME=PA_SUMMARY&&ROW_NUM="+this.id;
		}else{
			document.getElementById("operateAliasButton").href="/sys/pageStructure/addNewAliasView?TABLE_NAME=AR_SUMMARY&&ROW_NUM="+this.id;
		}
		document.getElementById("operateAliasButton").click();
	  }
	  hrefA.style.cursor="hand";
	  cell.appendChild(hrefA);
	  var inputNo=document.createElement("input");
	  inputNo.setAttribute("type","hidden");
	  inputNo.setAttribute("name","delete_rt_no");
	  inputNo.setAttribute("value","delete_rt_no");
	  cell =nTr.insertCell(4); //删除
	  cell.style.textAlign="center";
	  var deleteSpan = document.createElement("span");
	  deleteSpan.style.cursor="hand";
	  deleteSpan.innerHTML="删除";
	  deleteSpan.onclick=function(){
		  var rowIndex=deleteSpan.parentElement.parentElement.rowIndex;
		  document.getElementById('pageStructureTable').deleteRow(rowIndex);
	  }
	  cell.appendChild(deleteSpan);
	  var inputItemNo=document.createElement("input");
	  inputItemNo.setAttribute("type","hidden");
	  inputItemNo.setAttribute("id","inputItemNo_"+initNum);
	  inputItemNo.setAttribute("name","inputItemNo_"+initNum);
	  inputItemNo.setAttribute("value","");

	  var inputItemID=document.createElement("input");
	  inputItemID.setAttribute("type","hidden");
	  inputItemID.setAttribute("id","inputItemID_"+initNum);
	  inputItemID.setAttribute("name","inputItemID_"+initNum);
	  inputItemID.setAttribute("value","");
	  cell.appendChild(inputItemNo);
	  cell.appendChild(inputItemID);
	  initNum++;
	  document.getElementById("add_table_index").value=initNum;
	  $.pdialog.closeCurrent();
	}
}
function changeViewModel(reportType){
	if(reportType=="2"){
		var option=document.createElement("option");
		option.setAttribute("value","2");
		var txtNode =document.createTextNode("日期样式");//数据归属
		option.appendChild(txtNode);
		var selectModel=document.getElementById("showModelPs1");
		selectModel.appendChild(option);
	}else{
		var childs1=document.getElementById("showModelPs1").childNodes;
		for(var i=0;i<childs1.length;i++){
 			if(childs1[i].value=="2"){
 				document.getElementById("showModelPs1").removeChild(childs1[i]);
 				break;
 	 		}
		}
	}
}
</SCRIPT>
<div class="pageContent">
  	<table border="0">
  		<c:forEach items="${languageList}" var="language" varStatus="i"> 
		  	<tr>
		  	<td style="padding:5px">请输入新建表${language.DESCRIPTION}:</td>
		  	<td style="padding:5px">
			  	<input id="tableName_${language.LANGUAGE}" name="tableName_${language.LANGUAGE}" value="" 
			  	<c:if test="${language.LANGUAGE eq interLanguage}">class="required"</c:if>
			  	<c:if test="${language.LANGUAGE eq interLanguage}">class="textInput"</c:if>
			  	/>
		  	</td>
		  	</tr>
  		</c:forEach>
  		<tr>
		  	<td style="padding:5px">请选择该表要显示的模式:</td>
		  	<td style="padding:5px">
				<select id="showModelPs1">
					<option value="1">普通样式</option>
					<!--<option value="2">日期样式</option>  -->
				</select>
			</td>
		</tr>
		<tr>
		  	<td style="padding:5px">请选择该表要显示的模式:</td>
		  	<td style="padding:5px">
		  		<select id="showModelPs2" onchange="changeViewModel(this.value);">
					<option value="1">工资信息</option>
					<option value="2">考勤信息</option>
				</select>
			</td>
		</tr>
  	</table>
	 <div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button onclick="addPageStructureTable()">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	 </div>
</div>
