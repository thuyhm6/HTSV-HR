<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<link href="/resources/js/ligerUI/skins/Silvery/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="/resources/js/jquery/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 
	<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>	
	<script src="/resources/js/myhome.js" type="text/javascript"></script>
	<script src="/resources/js/myhomeGbar.js" type="text/javascript"></script>		
	<SCRIPT type="text/javascript">
		$(function() {	
			$(this).myhome('${page}'); 
			$("#navtab1").ligerTab({contextmenu:false});
			getTips();			
			//setInterval("getTips()",60000);	

			$('#birthday').before('123123');
			
        });
        function getTips(){
        	$.ajax({
            	type:'get',
            	cache:false,
            	contentType:'application/json',	            			            	
            	url:'/myhome/getTips',            	
            	dataType:'json',
            	success:function(data){			
	        		var tabManager = $("#navtab1").ligerGetTabManager();

	        		if(data.pview == 'true'){
        				$("div[tabid='tab1']").html(data.pviewinfo);
	        		}else{
	        			tabManager.removeTabItem('tab1');
			        }
	        		if(data.jview == 'true'){
	        			$("div[tabid='tab2']").html(data.jviewinfo);
	        		}else{	
	        			tabManager.removeTabItem('tab2');
	        		}

        			if(data.cview == 'true'){
        				$("div[tabid='tab3']").html(data.cviewinfo);
	        		}else{	
	        			tabManager.removeTabItem('tab3');
	        		}
        			
            	}             	           	
        	});
        }        
	</SCRIPT>	

	<style type="text/css"> 
		body{overflow-x:hidden;}         
       	.l-button-edit,.l-button-update{width:45px; float:right;
       	margin-right:1px;margin-top:1px;}
       	.l-button-update{display:none;}
       	#addbtn,#delbtn,#appbtn{width:25px;}
       	#header{position:fixed;_position:absolute;   
	        z-index:1000;width:99%;top:2px;
	    }	    
	    #footer{position:fixed;_position:absolute;   
	        z-index:1000;width:100%;left:0px; bottom:3px;
	    }
	    #right{position:fixed;_position:absolute;width:100%;height:100%;} 	  
	    #righter{ margin-top:80px;margin-right:10px;width:180px;float:right;height:100%;overflow:hidden;}
   		#gbar {position: absolute; float:right;right: -325px; top: 180px; width: 350px; }
		#glider {position: absolute;float:right; right: 350px;top:0px; width: 10px}
   	</style>
</head>

<body id="body">

	<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
	</div>		
	<div id="header">
		<a id="app">
			<input type="button" calss="appbtn" value='Page1' onclick="changePage('1');"/>
			<c:forEach var="s" begin="2" end="${appcnt}">
				<input type="button" calss="appbtn" value='Page${s}' onclick="changePage('${s}');"/>
			</c:forEach>
		</a>
		<input type="button" id="edit" value='<spring:message code="edit"/>' onclick="edit('${page}');" class="l-button l-button-edit" />
		<input type="button" id="update" value='<spring:message code="submit"/>' onclick="update('${page}');" class="l-button l-button-update" />
	</div>
	<div id="right">     
	    <div id="righter" style="display:none;">    		
	   		<div title="<spring:message code='myApplication'/>">
	   			<p id="appinfo"></p>
	   		</div>
	   		<div title="<spring:message code='myShortcut'/>">
	   			<p id="menutree"></p>
	   		</div>
	   		<div title="<spring:message code='myBg'/>">
	   			<p id="mybg"><br/>
	   				<c:forEach begin="0" end="21" var="cc">
	   					<input type="radio" name="bg" onclick="changebg('${cc}')"/>
	   					<img width="50" height="50" src="/resources/images/homebg/${cc}.gif"/>
	   					&nbsp;&nbsp;
	   					<c:if test="${cc mod 2 == 1}">
	   					<br/>
	   					</c:if>
	   				</c:forEach>
	   			</p>
	   		</div>    	
	    </div>
    </div>		
    <div id="mainBody">
		<form id="infoForm">
			
		</form>
	</div> 
	<div id="footer">
	 	<input type="button" id="addbtn" value='+' 
	 	onclick="addApp('${appcnt }','<spring:message code="prompt"/>','<spring:message code="isFull"/>');"/>
	 	<c:if test="${page ne 1}">
	 		<input type="button" id="delbtn" value='-' onclick="delApp('${page }','${appcnt }');"/>
	 	</c:if>
	 	${page }/${appcnt }
	</div> 
	<div ID="gbar"> 
		<table width="350" border="0" cellspacing="0" cellpadding="0">
			<tr> 
			  <td> 
				<table width="100%" border="0" cellpadding="8" cellspacing="1">
				  <tr> 
					<td width="24">  
					</td>
					<td bgcolor="#F7F6F6" > 
					   <div id="navtab1" style="width: auto;overflow:hidden; border:3px solid #A3C0E8; ">
							<div tabid="tab1" title="<spring:message code='personalView'/>" style="height:200px;">
								
							</div>
							<div tabid="tab2" title="<spring:message code='judgementWaiting'/>" style="height:200px;">
								
							</div>
							<div tabid="tab3" title="<spring:message code='confirmWaiting'/>" style="height:200px;">
								
							</div>
						</div>
					</td>                        
				  </tr>
				</table>
			  </td>
			</tr>
		  </table>
		  <span ID="glider"> 
			  <table border="0" cellspacing="0" cellpadding="0">
				<tr align="center"> 
					<td bgcolor="#cccccc" id="glidetextLink">						
						<img src="/resources/images/button/open.jpg" style="cursor:hand;" onClick="Proj7GlideBack(); return false">
					</td>
				</tr>
			  </table>
		  </span> 
	</div> 
</body>
</html>
