<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <!-- CSS -->
    <%@ include file="/WEB-INF/view/inc/initUICss.jsp"%>
     <!-- JS -->
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script>  

    <script type="text/javascript">
    
    // 初始调用
    $(function ()
    {
    	//布局
        $("#layout1").ligerLayout({ 
        	topHeight: 60,
            allowTopResize: false      //是否允许 头部可以调整大小
            });
    });

    function f_checkAll(checkBoxFlag){
    	$("#resultCenter :checkbox").each(function ()
    	{
        	this.checked = checkBoxFlag ;  
	    }); 
    }

    function f_BonusBalance(){
    	$.ligerDialog.confirm('是否进行保险结算?', function (yes)
    	{
    		if(yes){
		    	$.ligerDialog.waitting("结算中...");
		        $.post("/pa/bonus/bonusBalance"
		        , function (result)
		        {
		          $.ligerDialog.closeWaitting();
		          $.ligerDialog.success(result);
		        });
    		}
        });
    }

    function f_BonusExcel(){
    	var jsonData = '[' ;
    	$("#resultCenter :checkbox").each(function ()
    	{
        	if(this.checked){
        		if (jsonData.length > 1){
                 	jsonData += ',{' ;
                 }
                 else{
                 	jsonData += '{' ;
                 }

              	 jsonData += ' "ITEM_ID": "' + this.name + '", ' ;
                 jsonData += ' "ITEM_NAME": "' + this.value + '"';    	
                 jsonData += '}' ;
        	}
	    }); 
    	jsonData += ']' ;

    	if(jsonData.length == 2){
        	alert('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>') ;
        	return ;
    	}
    	
    	alert(jsonData) ;
    }    
	</script>	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px"> 
<div id="layout1" >
			<div position="top" id="resultTop">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<spring:message code="pa.diff.title.salaryItem"/><!--工资项目-->
				( <!--全部选中-->
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
				<input type="checkbox" name="checkbox" onclick="f_checkAll(this.checked) ">)
				<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_BonusBalance()">
				<spring:message code="pa.insurance.title.insuranceSettlement"/><!--保险结算--></a>
			    <a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_BonusExcel()">
			    <spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></a>
			</div>
            <div position="center" id='resultCenter'>
            	<table>
            		<tr>
            			<td colspan="4"><spring:message code="pa.insurance.title.personnelItem"/><!--人事项目--></td>
            		</tr>
	           		<c:forEach items="${hrItemList}" var="item" varStatus="i">
	           			<c:if test="${i.index == 0}">
	           				<tr>
	           			</c:if>
	           			
	           				<td ><input type="checkBox" value="${item.FIELD_NAME}" name="${item.DISTINCT_FIELD}" />&nbsp;&nbsp;${item.FIELD_NAME}&nbsp;&nbsp;</td>
	           			
	           			<c:if test="${(i.index + 1) mod 4 == 0}">
	           				</tr>
	           				<tr>
	           			</c:if>
	           				
	           			<c:if test="${i.index + 1 == fn:length(hrItemList)}">
	           				</tr>
	           			</c:if>
	           		</c:forEach>
            	</table>
            	</br>
            	<table>
            		<tr>
            			<td colspan="4"><spring:message code="pa.diff.title.arItem"/><!--考勤项目--></td>
            		</tr>
	           		<c:forEach items="${arItemList}" var="item" varStatus="i">
	           			<c:if test="${i.index == 0}">
	           				<tr>
	           			</c:if>
	           			
	           				<td ><input type="checkBox" value="${item.FIELD_NAME}" name="${item.DISTINCT_FIELD}" />&nbsp;&nbsp;${item.FIELD_NAME}&nbsp;&nbsp;</td>
	           			
	           			<c:if test="${(i.index + 1) mod 4 == 0}">
	           				</tr>
	           				<tr>
	           			</c:if>
	           				
	           			<c:if test="${i.index + 1 == fn:length(arItemList)}">
	           				</tr>
	           			</c:if>
	           		</c:forEach>
            	</table>
            	</br>
            	<table>
            		<tr>
            			<td colspan="4"><spring:message code="pa.insurance.title.inputItem"/><!--输入项目--></td>
            		</tr>
	           		<c:forEach items="${bonusInputItemList}" var="item" varStatus="i">
	           			<c:if test="${i.index == 0}">
	           				<tr>
	           			</c:if>
	           			
	           				<td ><input type="checkBox" name="${item.PARAM_ID}" value="${item.PARAM_NAME}" />&nbsp;&nbsp;${item.PARAM_NAME}&nbsp;&nbsp;</td>
	           			
	           			<c:if test="${(i.index + 1) mod 4 == 0}">
	           				</tr>
	           				<tr>
	           			</c:if>
	           				
	           			<c:if test="${i.index + 1 == fn:length(bonusInputItemList)}">
	           				</tr>
	           			</c:if>
	           		</c:forEach>
            	</table>
            	</br>
            	<table>
            		<tr>
            			<td colspan="4"><spring:message code="pa.insurance.title.computeItem"/><!--计算项目--></td>
            		</tr>
	           		<c:forEach items="${bonusComputeItemList}" var="item" varStatus="i">
	           			<c:if test="${i.index == 0}">
	           				<tr>
	           			</c:if>
	           			
	           				<td ><input type="checkBox" name="${item.ITEM_ID}" value="${item.ITEM_NAME}
	           				"/>&nbsp;&nbsp;${item.ITEM_NAME}&nbsp;&nbsp;</td>
	           			
	           			<c:if test="${(i.index + 1) mod 4 == 0}">
	           				</tr>
	           				<tr>
	           			</c:if>
	           				
	           			<c:if test="${i.index + 1 == fn:length(bonusComputeItemList)}">
	           				</tr>
	           			</c:if>
	           		</c:forEach>
            	</table>
        	</div>
</div> 
</body>
</html>