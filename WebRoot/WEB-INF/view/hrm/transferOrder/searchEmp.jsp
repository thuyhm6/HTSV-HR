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
			$("#joindatef").ligerDateEditor();
			$("#joindatet").ligerDateEditor();
			grid =$("#info").ligerGrid({						               
		            columns: [
						{ display: '', width: 30, isAllowHide: false, name: 'checkbox', isSort: false,
						    render: function (rowdata,rowindex){                        
						        var html = "<input type ='checkbox' value='"+rowdata.EMPID
						        +"' onclick=\"getSelected(this,"+rowindex+")\">";
						        return html;
						    },
		                    headerRender: function(){
		                        var html = '<input id="checkall" type ="checkbox" onclick="selectAll(this)">';
		                        return html;
		                    }
						    
						},
		            	{ display: '<spring:message code="hrm.empid"/>', name: 'EMPID', minWidth: 70 },
		            	{ display: '<spring:message code="hrm.name"/>', name: 'CHINESENAME', minWidth: 90 },
		            	{ display: '<spring:message code="hrm.dept"/>', name: 'DEPTFULLNAME' ,minWidth: 205},
		            	{ display: '<spring:message code="hrm.EmpStatus"/>', name: 'STATUS' ,minWidth: 90}
		            ], 
		            url: "/hrm/transferOrder/getTransferOrderList?${type}=1",
		            sortName: 'EMPID', 
		            dataAction: 'server',	                   
		            width: 510,	           
		            height: 325,
		            onAfterShowData:shCheck		           
		            			
		        });

			   $("#rightBox").change(function(){
	        	   var rbox = $("#rightBox option:selected");		        	  
	        	   rbox.remove();       	   
	        	   $("#info td[columnname=checkbox]").find(":checkbox[value='"
				        	+$(rbox).val()+"']").removeAttr("checked");
		        	 	        	  
	        	 });

			   parent.$(".l-window-close").click(function(){parent.$("#l-search").show();});		
		        
			});
			function f_search(){
		    	grid.setOptions({parms: [
		    	            { name: 'key', value: $("#key").attr('value')},
		    	            { name: 'deptid', value: $("#dept").attr('value')},
		    	            { name: 'joindatef', value: $("#joindatef").attr('value')},
		    	            { name: 'joindatet', value: $("#joindatet").attr('value')}
		    	            ],
		    	    newPage: 1
		    	 });
		        grid.loadData(true);
			}

			function getSelected(obj,rowindex){				
				var rowobj=$("TR[class^='l-grid-row'][rowindex='"+rowindex+"']");
				var rowdata=grid.getRowByRowIndex(rowindex);

				if(obj.checked){
					$("#rightBox option[value='"+rowdata.EMPID+"']").remove();				
					$("#rightBox").append("<option value='"+rowdata.EMPID+"'>"+rowdata.EMPID+" "+rowdata.CHINESENAME+"</option>");
				}else{
					$("#rightBox option[value='"+rowdata.EMPID+"']").remove();
				}	 
		    }

			function selectAll(obj){
	            $("#info td[columnname=checkbox]").find(":checkbox").each(function (i){
	                this.checked = obj.checked;
					getSelected(obj,i);
					
	            });
	        }
	        function shCheck(){
		        $("#checkall").removeAttr("checked");
		        $("#rightBox option").each(function(){
		        	$("#info td[columnname=checkbox]").find(":checkbox[value='"
				        	+this.value+"']").attr("checked","true");
		        });
	        }
	        function rest(){		
	        	$("#info td[columnname=checkbox]").removeAttr("checked");
	        	 $("#rightBox option").remove();
			}		    
			function send(){		
				parent.$(".l-window-toggle").click();

				var emplist="";
				$("#rightBox option").each(function(){
					emplist+=this.value+",";
				});
				   
				parent.$grid.setOptions(
					{ parms: [
		    	               { name: 'deptid', value: $("#dept").attr('value')},
		    	               { name: 'emplist', value: emplist}		    	                     	
		    	             ],
		    	      newPage: 1
		    	    });
				parent.$grid.loadData(true);
			    
			}
		</script>
		<style type="text/css">           
		        	.l-button{width:60px; float:right; 
		        	margin-right:5px;}
		        	#info{margin-top: 20px;margin-left: 60px;} 
		        	
		</style>
	</head>	
	<body>	
	<form>	
		<input type="button" value='<spring:message code="submit"/>' 
			onclick="send();"
			class="l-button"/>
		<input type="reset" value='重置' 
			onclick="rest();"
			class="l-button"/>
		<input type="button" value='<spring:message code="search"/>' 
			onclick="f_search();"
			class="l-button"/>
		<table border="0" style="font-size: 12px;margin-left: 70px;margin-top: 10px;">
		<tr>
			<td align="right"><spring:message code="hrm.dept"/>:&nbsp;</td>
			<td align="left"><ait:deptTree name="dept" limit="hr"/></td>
			<td align="right">&nbsp;&nbsp;&nbsp;</td>
			<td align="right"><spring:message code="keyWord"/>:&nbsp;</td>
			<td align="left"><input type="text" id="key" name="key" value=""/></td>
		
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><spring:message code="hrm.hireDate"/>:&nbsp;</td>
			<td align="left" colspan="4">
				<table>
					<tr>
					<td>
						<input type="text" id="joindatef" name="joindatef" value=""/>
					</td>
					<td>
						&nbsp;~&nbsp;
					</td>
					<td>
						<input type="text" id="joindatet" name="joindatet" value=""/>
					</td>
					</tr>
				</table>
			</td>
		</tr>
		</table>
		<table border="0"><tr>
			<td><div id="info"></div></td>
			<td>&nbsp;&nbsp;</td>
			<td><select id="rightBox" size="17" style="width:150px;" 
					multiple="multiple" title="单击移除,为空时按部门查询"></select></td>
		</tr></table>
	</form>
	</body>
</html>
