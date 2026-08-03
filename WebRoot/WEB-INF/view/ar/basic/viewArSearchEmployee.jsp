<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<html>
 <head> 
 	<title></title>
 	
 	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
 	
	<%@ include file="/WEB-INF/view/inc/initUICss.jsp"%>
	
	<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 

	<script type="text/javascript">
		 var grid ;
		 
		 $(function(){
			grid =$("#arSearchEmp").ligerGrid({						               
		            columns: [
		            	{ display: '<spring:message code="hrm.empid"/>', name: 'EMPID'},
		            	{ display: '<spring:message code="hrm.name"/>', name: 'CHINESENAME', align: 'left'},
		            	{ display: '<spring:message code="hrm.dept"/>', name: 'DEPTNAME', align: 'left' }
		            ], 
		            usePager: true, dataAction: 'server', root: 'arSearchEmployeeList', record: 'arSearchEmployeeCnt', 
		            url: '/ar/basic/getArSearchEmployeeList?SELECT_TYPE=${request_SELECT_TYPE}',               
		            width: '99%',	           
		            height: '95%',
		            onDblClickRow:send				
		        });			
		        
			});
			
			function search(){
		    	grid.setOptions({parms: [
		    	            { name: 'KEY', value: $("#KEY").val()},
		    	            { name: 'DEPTID', value: $("#DEPTID").val()}
		    	            ],
		    	    newPage: 1
		    	 });
		        grid.loadData(true);
			}
			
			function send(){		
				var data = grid.getSelectedRow();
				parent.initEmpId(data) ;
			}
		</script>
		<style type="text/css">           
		        	.l-button-search{width:50px; float:right; 
		        	margin-right:30px;}
		        	#info{margin-top: 20px;margin-left: 60px;} 
		        	
		</style>
	</head>	
	<body>
		<input type="button" value='<spring:message code="search"/>' 
			onclick="search();"
			class="l-button l-button-search"/>
		<table border="0" style="font-size: 12px;margin-left: 70px;margin-top: 10px;">
			<tr>
				<td align="right"><spring:message code="hrm.dept"/>:&nbsp;</td>
				<td align="left"><ait:deptTree name="DEPTID" limit="hr"/></td>
				<td align="right"><spring:message code="keyWord"/>:&nbsp;</td>
				<td align="left"><input type="text" id="KEY" name="KEY" value=""/></td>
			</tr>
		</table>
		
		<div id="arSearchEmp"></div>
		
	</body>
</html>
