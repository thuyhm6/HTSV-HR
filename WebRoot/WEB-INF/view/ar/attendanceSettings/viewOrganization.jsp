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
			grid =$("#info").ligerGrid({
			    checkbox: true,						               
		            columns: [
		            	{ display: '<spring:message code="hrm.empid"/>', name: 'EMPID', minWidth: 100 },
		            	{ display: '<spring:message code="hrm.name"/>', name: 'CHINESENAME', minWidth: 110 },
		            	{ display: '<spring:message code="hrm.dept"/>', name: 'DEPTFULLNAME' ,minWidth: 290},
		            	{ display: '<spring:message code="hrm.EmpStatus"/>', name: 'STATUS', minWidth: 130}
		            ], 
		            url: "/hrm/empinfo/getEmpList",
		            sortName: 'EMPID', 
		            dataAction: 'server',	                   
		            width: 680,	           
		            height: '75%'
		             	
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
		   function f_add()
		   {
		   
		   
		   	var rows = grid.getCheckedRows();
             
            if (!rows || rows.length == 0) { alert('请选择人员'); return false; }
              var EMPID = "" ;
              $(rows).each(function ()
		         {
		         	EMPID += this.EMPID+"," ;
		         });
		       var GROUP_NO  =$("#GROUP_NO").val();
		       
		        $.ligerDialog.waitting("保存中...");
		         $.post("/ar/attendanceSettings/addDynamicGroupPerson", 
		                [	
		                 	{ name: 'EMPID', value: EMPID },
		                 	{ name: 'GROUP_NO', value: GROUP_NO }
		                ]
		        , function (result)
		        {
		        	 $.ligerDialog.closeWaitting();
		            if (result == "Y")
		            {
		                $.ligerDialog.success('保存成功!', function ()
	       		                {
	       		                	parent.f_ChildWindowClose();
	       		                });
		            }
		            else
		            {
		                $.ligerMessageBox.error('提示', result);
		            }
		        });
		         
		         
		         
               
   
            }
			
		</script>
		<style type="text/css">           
		        	.l-button-search{width:80px; float:right; 
		        	margin-right:30px;}
		        	#info{margin-top: 20px;margin-left: 60px;} 
		        	
		</style>
	</head>	
	<body>
	 <form name="form1" method="post" action="" id="form1" >
		 <input type="button" value="添加" id="Button1" class="l-button l-button-submit"  onclick="f_add()"/> 
		 <input type="button" value='<spring:message code="search"/>' 
			onclick="search();"
			class="l-button l-button-search"/>
		<table border="0" style="font-size: 12px;margin-left: 70px;margin-top: 10px;"><tr>
			<td align="right"><spring:message code="hrm.dept"/>:&nbsp;</td>
			<td align="left"><ait:deptTree name="dept" limit="hr"/></td>
			<td align="right">&nbsp;&nbsp;&nbsp;</td>
			<td align="right"><spring:message code="keyWord"/>:&nbsp;</td>
			<td align="left"><input type="text" id="key" name="key" value=""/>
			<input type="hidden" value="<%=request.getParameter("GROUP_NO") %>" name="GROUP_NO" id="GROUP_NO"/></td>
		</div>
		</tr></table>
		
		<div id="info"></div>
	 </form>
	</body>
</html>
