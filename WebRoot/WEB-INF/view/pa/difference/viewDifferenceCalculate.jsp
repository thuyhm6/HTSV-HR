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
    <script src="/resources/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
     <script src="/resources/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>

    <script type="text/javascript">
    
    // 初始调用
    $(function ()
    {

    });

    function f_Calculate(){
        var basisMonth = $("#basisYear").val() + $("#basisMonth").val() ;
        var formularMonth = $("#formularYear").val() + $("#formularMonth").val() ;
        var paMonth = $("#paYear").val() + $("#paMonth").val() ;
        
        $.ligerDialog.confirm('是否以[' + basisMonth + ']月数据和[' + formularMonth + ']月公式为基础,对[' + paMonth + ']月进行补差计算?', function (yes)
        {
            if(yes){
            	$("#loading").show();
            	$("#differenceCalculate").hide() ;
            	$.ajax({
        	       	type:'post',
        	       	cache:false,
        	       	contentType:'application/json',	            			            	
        	       	url:'/pa/difference/differenceCalculate?PA_BASIS_MONTH=' + basisMonth + "&PA_MONTH=" + paMonth + "&FORMULAR_MONTH=" + formularMonth,            	
        	       	dataType:'json',
        	       	success:function(responseStr){ 
                		$("#calculateResult").html(responseStr) ;
                		$("#loading").hide();
                		$("#differenceCalculate").show() ;
        	       	}          	
              	});
            }
        });
        
    }
    
	</script>
	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px"> 
      		<div id="loading" style="width:100%;text-align:center;padding-top: 200px;position: absolute; display:none">
				<img src="/resources/images/loading.gif">
			</div>	
            <div id='differenceCalculate' title='<spring:message code="pa.insurance.title.conveyanceCalculation"/>' >
            	<div>
            		<a class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_Calculate()">
            		<spring:message code="pa.insurance.title.conveyanceCalculation"/><!--补差计算--></a>
				</div>
				<div class="l-clear"></div>
            	<div id="calculate" >
            		<table border="0">
            			<tr>
            				<td><spring:message code="pa.insurance.title.salaryBasicMonth"/><!--工资基础月-->: 
            				</td><td><ait:date yearName="basisYear" monthName="basisMonth"/>&nbsp;&nbsp;</td>
            				<td><spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->: 
            				</td><td><ait:date yearName="paYear" monthName="paMonth"/>&nbsp;&nbsp;</td>
            				<td><spring:message code="pa.bonus.title.formulaMonth"/><!--公式月-->: 
            				</td><td><ait:date yearName="formularYear" monthName="formularMonth"/>&nbsp;&nbsp;</td>
            			</tr>
            		</table>
            	</div>
            	<div id="calculateResult"></div>
        	</div> 
</body>
</html>