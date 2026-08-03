<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<html>
 <head> 
 	<title></title>
 	
 	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
 	
	<link href="/resources/js/ligerUI/skins/Silvery/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 

	<script type="text/javascript">
		 var grid ;
		 $(function(){
			grid =$("#info").ligerGrid({						               
		            columns: [
		            	{ display: '<spring:message code="hrm.empid"/>', name: 'EMPID', minWidth: 100 },
		            	{ display: '<spring:message code="hrm.name"/>', name: 'CHINESENAME', minWidth: 130 },
		            	{ display: '<spring:message code="hrm.dept"/>', name: 'DEPTFULLNAME' ,minWidth: 290},
		            	{ display: '<spring:message code="hrm.EmpStatus"/>', name: 'STATUS', minWidth: 130}
		            ], 
		            url: "/hrm/empinfo/getEmpList",
		            sortName: 'EMPID', 
		            dataAction: 'server',	                   
		            width: 680,	           
		            height: '75%',
		            onDblClickRow:send				
		        });			
		        
			});
			function search(){
		    	grid.setOptions({parms: [
		    	            { name: 'key', value: $("#key").attr('value')},
		    	            { name: 'dept', value: $("#dept").attr('value')}
		    	            ],
		    	    newPage: 1
		    	 });
		        grid.loadData(true);
			}
			function send(){		
				var data = grid.getSelectedRow();
				var url=(parent.location.href).split("?");
				parent.location.href=url[0]+"?empid="+data.EMPID;
			}
		</script>
		<style type="text/css">           
		        	.l-button-search{width:80px; float:right; 
		        	margin-right:30px;}
		        	#info{margin-top: 20px;margin-left: 60px;} 
		        	
		</style>
	</head>	
	<body>
		<input type="button" value='<spring:message code="search"/>' 
			onclick="search();"
			class="l-button l-button-search"/>
		<table border="0" style="font-size: 12px;margin-left: 70px;margin-top: 10px;"><tr>
			<td align="right"><spring:message code="hrm.dept"/>:&nbsp;</td>
			<td align="left"><ait:deptTree name="dept" limit="hr"/></td>
			<td align="right">&nbsp;&nbsp;&nbsp;</td>
			<td align="right"><spring:message code="keyWord"/>:&nbsp;</td>
			<td align="left"><input type="text" id="key" name="key" value=""/>	</td>
		</div>
		</tr></table>
		
		<div id="info"></div>
		
	</body>
</html>
