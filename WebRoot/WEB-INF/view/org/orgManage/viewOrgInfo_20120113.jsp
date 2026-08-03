<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
 
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >
   
    
    <!-- CSS -->
	<style type="text/css">
	.OrgBox{
		font-size:12px;
		padding:5px 5px 5px 5px;
		clear:left;
		float:left;
		text-align:center;
		position:absolute;
		background-image:url(http://www.on-cn.cn/tempimg/org.jpg);
		width:70px;
		height:106px;
	}
	.OrgBox img{
		width:60px;
		height:70px;
	}
	.OrgBox div{
		color:#FFA500;
		font-weight:800;
	}
	</style>    
	
     <!-- JS -->
     <script src="/resources/js/organization.js" type="text/javascript"></script>  
   
</head>
<body style="padding:0px" > 

<table>
	<tr>
		<td>
		
			<form id="levelForm" action="" method="get">
				<input name="deptl" id="deptl" type="hidden" value=""/>
				<spring:message code="org.orgManage.title.pleaseChooseDiv"/><!--请选择需要显示的层级-->：
				<select id="deptlevel" onchange="submitForm();">
					<option value="">--please choose--</option>
					<c:forEach items="${deptLevelList}" var="dlevel" >
						<option value="${dlevel.DEPT_LEVEL}"
							<c:if test="${dlevel.DEPT_LEVEL eq DEPTL}">selected</c:if>
						>${dlevel.DEPT_LEVEL}</option>
					</c:forEach>
				</select>				
			</form>

		</td>
	</tr>
	<tr>
		<td>
		
		    <script type="text/javascript">

		    var deptList = new Array();
		    alert(123) ;
		    var a=new OrgNode();
		    alert(444) ;
			a.Text="WOORIBANK";
			a.customParam.deptid="00110000";
			a.customParam.deptpid="wooribank";
			a.customParam.department='<spring:message code="org.title.YOULI_BANK" />';
			deptList.push(a);
		    
		    <c:forEach items="${deptInfoList}" var="dept" varStatus="i">
		
				var dept${i.index}=new OrgNode();
				dept${i.index}.customParam.deptid="${dept.DEPTID}";
				dept${i.index}.customParam.deptpid="${dept.PARENT_DEPT_NO}";
				dept${i.index}.customParam.department="${dept.DEPT_NAME_ZH}";
				dept${i.index}.customParam.EmpPhoto="/resources/photo/demo.gif";
				
				deptList.push(dept${i.index});
				
			</c:forEach>
			
			 var tempPid;
			 var tempId;
			 for(var k=0;k<deptList.length;k++){
				 tempPid = "";
				 tempPid = deptList[k].customParam.deptpid ;
				 
				 for(var j=0;j<deptList.length;j++){
					 tempId = "";
					 tempId = deptList[j].customParam.deptid ;
					 if(tempId!=null&&tempId == tempPid){
						 deptList[j].Nodes.Add(deptList[k]);
					 }
				 }
			 }
			 
			 var OrgShows=new OrgShow(a);
			OrgShows.Top = 50; //设置顶距离
			OrgShows.Left = 50; //设置左距离
			OrgShows.IntervalWidth = 10; //设置节点间隔宽度
			OrgShows.IntervalHeight = 20; //设置节点间隔高度
			OrgShows.ShowType = 2; //设置节点展示方式  1横向  2竖向
			//OrgShows.BoxHeight = 100; //设置节点默认高度
			//OrgShows.BoxWidth=100; //设置节点默认宽度
			//OrgShows.BoxTemplet="="<div id=\"{Id}\" style=\"font-size:12px;padding:5px 5px 5px 5px;border:thin solid orange;background-color:lightgrey; clear:left;float:left;text-align:center;position:absolute;\" title=\"{Description}\" ><a href=\"{Link}\">{Text}</a></div>";自定义节点模板
			//OrgShows.LineSize = 2; //设置线条大小
			//OrgShows.LineColor = 2; //设置线条颜色	 
		
		    //OrgShows.BoxTemplet="<div id=\"{Id}\" class=\"OrgBox\"><img src=\"{EmpPhoto}\" /><span>{EmpName}</span><div>{department}</div></div>";
			
		    OrgShows.BoxTemplet="<div id=\"{Id}\" class=\"OrgBox\"><img src=\"{EmpPhoto}\" /><div>{department}</div></div>";
			
		    
		    OrgShows.Run();
		    
		    
			</script>			
		
		</td>
	</tr>	


	
</table>


            	
       
       
</body>
</html>
