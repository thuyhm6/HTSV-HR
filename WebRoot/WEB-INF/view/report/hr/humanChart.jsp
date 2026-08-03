<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
	<script type="text/javascript" src="/resources/js/swfobject.js"></script>  
		<script type="text/javascript">  
		    var flashvars = {"data-file":'/resources/data/data1.txt'};  
		    //alert(flashvars.value);
		    var params = {menu: "false",scale: "noScale",wmode:"opaque"};   
		    swfobject.embedSWF("/resources/swf/open-flash-chart.swf?r="+ Math.random() *1000, "chart1", "600px", "400px", "9.0.0",   
		    "expressInstall.swf",flashvars,params);   
		</script>  
		
		<script type="text/javascript">  
		    var flashvars = {"data-file":'/resources/data/data2.txt'};  
		    //alert(flashvars.value);
		    var params = {menu: "false",scale: "noScale",wmode:"opaque"};   
		    swfobject.embedSWF("/resources/swf/open-flash-chart.swf?r="+ Math.random() *1000, "chart2", "600px", "400px", "9.0.0",   
		    "expressInstall.swf",flashvars,params);   
		</script>  		

		<script type="text/javascript">  
		    var flashvars = {"data-file":'/resources/data/data3.txt'};  
		    //alert(flashvars.value);
		    var params = {menu: "false",scale: "noScale",wmode:"opaque"};   
		    swfobject.embedSWF("/resources/swf/open-flash-chart.swf?r="+ Math.random() *1000, "chart3", "600px", "400px", "9.0.0",   
		    "expressInstall.swf",flashvars,params);   
		</script>  			
  </head>  

  </head>  
  <body>	
			
		<div id="chart1">  
		</div> 
		<table border="1" cellspacing="0.5">
			<tr>
				<td colspan="2" width="500" align="center">职务统计分析</td>
			</tr>
			<tr>
				<td>职务</td>
				<td>人数</td>
			</tr>
			<tr>
				<td>部长</td>
				<td>6</td>
			</tr>
			<tr>
				<td>经理</td>
				<td>16</td>
			</tr>
			<tr>
				<td>主管</td>
				<td>73</td>
			</tr>
			<tr>
				<td>职员</td>
				<td>183</td>
			</tr>
			<tr>
				<td>实习生</td>
				<td>7</td>
			</tr>
			<tr>
				<td>外包人员</td>
				<td>7</td>
			</tr>												
						
		</table>
		
		
		<br>
		<div id="chart2"> 
		</div> 	
		<table border="1" cellspacing="0.5">
			<tr>
				<td colspan="2" width="500" align="center">职务统计分析</td>
			</tr>
			<tr>
				<td>职务</td>
				<td>人数</td>
			</tr>
			<tr>
				<td>部长</td>
				<td>6</td>
			</tr>
			<tr>
				<td>经理</td>
				<td>16</td>
			</tr>
			<tr>
				<td>主管</td>
				<td>73</td>
			</tr>
			<tr>
				<td>职员</td>
				<td>183</td>
			</tr>
			<tr>
				<td>实习生</td>
				<td>7</td>
			</tr>
			<tr>
				<td>外包人员</td>
				<td>7</td>
			</tr>												
						
		</table>			
		<br>
		<div id="chart3"> 
		</div> 
		<table border="1" cellspacing="0.5">
			<tr>
				<td colspan="2" width="500" align="center">职务统计分析</td>
			</tr>
			<tr>
				<td>职务</td>
				<td>人数</td>
			</tr>
			<tr>
				<td>部长</td>
				<td>6</td>
			</tr>
			<tr>
				<td>经理</td>
				<td>16</td>
			</tr>
			<tr>
				<td>主管</td>
				<td>73</td>
			</tr>
			<tr>
				<td>职员</td>
				<td>183</td>
			</tr>
			<tr>
				<td>实习生</td>
				<td>7</td>
			</tr>
			<tr>
				<td>外包人员</td>
				<td>7</td>
			</tr>												
						
		</table>		
  </body>
</html>
