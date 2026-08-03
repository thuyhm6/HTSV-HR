<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
  	
  	<link href="/resources/js/ligerUI/skins/Silvery/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script> 
  	<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
  	<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
  	<script type="text/javascript">
  	$(function(){
  	  	$("#view").click(function(){
  	  	  if($("#type01").attr("checked")){
  	  		var options = {	                        
  	            url:'viewReport?reportName=humanResources',
  	            type:'POST',	                        
  	            success: function(back){  	            		
  	            	var temp="<table align='center' border='1'>"+
			  	       		 	"<tr>"+
			  	       		 		"<td align='center' colspan='4'>人力资源情况表("+$("#year").val()+"年"+$("#month").val()+"月)</td>";
			 	            	"</tr>"; 
			 	            	 	
			 	      $.each(back.Rows, function(i, d){ 
			 	       		temp+="<tr><td align='center' ";
			 	       		    if(d.COL==0){
			 	       		    	temp+="width='150' colspan=1";
			 	       		    }

				 	       		if(d.COL==3){
			 	       		    	temp+="colspan=4";
			 	       		    }
			 	       		    			 	       		    
			 	       		temp+=">"+d.RNAME+"</td>";	
			 	       				 	       				
					 	       	if(d.COL==0){
			 	       		    	temp+="<td align='center' colspan=3>"+d.CNT+"</td>";
			 	       		    }
						 	    if(d.COL==1){
						 	    	temp+="<td align='center' width='150'>"+d.CNT+"</td>"+
			 	       		    		  "<td align='center' width='150'>所占比例</td>"+
			 	       		    		  "<td align='center' width='150'>"+d.BL+"%</td>";
			 	       		    }
			 	       		    
							temp+="</tr>";
							
			 	     	 });
			 	     	 	
			 	     	temp+="</table>";
			 	     	
			 	        $("#viewInfo").html(temp);		
  	               }                                           
  	            }; 
	           $('#applyForm').ajaxSubmit(options); 
	           return false;
  	  		}

    	  	if($("#type02").attr("checked")){
    	  		$("#viewInfo").html("<div id='detail' style='left:10%'></div>");
    	  		$("#detail").ligerGrid({
    	    		columns: [   	    					
    	    		    		{ display: '行号', name: 'EMPID',width:70 },
    	    		    		{ display: '姓名', name: 'CHINESENAME',width:70 },
    	    		    		{ display: '性别', name: 'SEX',width:70 },
    	    		    		{ display: '年龄', name: 'AGE',width:70 },
    	    		    		{ display: '学位', name: 'DEGREE',width:70 },
    	    		    		{ display: '司龄', name: 'WORKAGE',width:70 },
    	    		    		{ display: '员工区分', name: 'EMPTYPE',width:70 },
    	    		    		{ display: '离职日期', name: 'DATESTARTED',width:150 },
    	    		    		{ display: '离职日期', name: 'DATELEFT',width:150 }
    	    		            ],
    	    		            dataAction: 'server', 
    	    		            sortName: 'EMPID',
    	    		            pageSize: 10,
    	    		            url: 'viewReport?reportName=humanResourcesDetail',
    	    		            parms:[ 
    	    	    		            { name: 'reportName', value: 'humanResourcesDetail'},
    	    	    		            { name: 'year', value: $("#year").val()},
    	    	    		            { name: 'month', value: $("#month").val()}
    	    	    		          ],
    	    		            width: '80%', 
    	    		            height: '300'
    	    		        });
		    	  	}
			  });

	  	  $("#exp").click(function(){
		  	  var reportName="";
	  	  	  if($("#type01").attr("checked")){
	  	  		reportName="humanResources";
		  	  }
	  	  	  if($("#type02").attr("checked")){
	  	  		reportName="humanResourcesDetail";
		  	  }
		  	  
	  	  	  document.applyForm.action=reportName+"Exp?reportName="+reportName;
			  document.applyForm.submit();
	  	  
	  	  });
	  	  	
	  	});
  	</script>
	<style>
		body{padding: 5px;}
		table{border-color:#5588AA;}
		table tr{height:25px;}
		table td{border-color:#5588AA}
		.con01{text-align: center; font-size:11pt;background: #D0DAEB;padding:5px;}
		.con02{text-align: left;background: #E1EFCB;padding:5px;}		
		.l-page-top{width:100%; height:50px; }
		#mainBody{padding-top: 20px;padding-bottom: 20px;}
		#exp,#view{width:60px; float:right;margin-right:15px;}	
		#viewInfo{height:300px;overflow:auto;padding:15px;border:1px solid #cccccc;}	
	</style>
  </head>  
  <body>	
	<div id="mainBody">		
		<div class="l-page-top">
				<input type="button" value="导出" id="exp" class="l-button"/>
				<input type="button" value="预览" id="view" class="l-button"/>			
		</div>			
		<form id="applyForm" name="applyForm" method="post">				
		    <table align="center" width="70%" border=1>
		    	<tr>
		    		<td class="con01" width="35%">
		    			月份选择
		    		</td>
		    		<td class="con02" width="65%">
		    			<ait:date yearName="year" monthName="month"/>
		    		</td>
		    	</tr>
		    	<tr>    		
		    		<td class="con01" width="35%" >
		    			统计类型
		    		</td>
		    		<td class="con02" width="65%">
		    			<input type="radio" name="type" id="type01" checked="checked">汇总
		    			<input type="radio" name="type" id="type02">明细
		    		</td>
		    	</tr>
		    </table>
		</form>
		<br/>
		<div id="viewInfo"></div>
	</div>	
  </body>
</html>
