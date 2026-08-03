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
	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        .l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
        h4{ margin:20px;}
    </style>	
    	
	<!-- CSS -->
    <link href="/resources/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    
     <!-- JS -->
     <!-- JS -->
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script>  
    <script src="/resources/js/json2.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
	
    
    <script type="text/javascript">
    
    var $grid ;
    var $tree ; 
    // 初始调用
    $(function ()
    {
        //布局
         $("#layout1").ligerLayout({ leftWidth: 180});

 		$deptTree = $("#deptTree").ligerTree(
		    	{ 
	    	    	url: '/hrm/org/getOrgInfoTreeDate', 
	    	    	checkbox: false,
	    	    	idFieldName: 'DEPTID', parentIDFieldName: 'PARENT_DEPT_ID', textFieldName: 'DEPTNAME',
	    	    	topParentIDValue: 'wooribank', isexpandFieldName: 'ISEXPAND'  
	        	 }
		    	onSelected:function (note,newText){  
					alert("fdsaf");
					}
	    );   

    });
    </script>
    
    
    
    
</head>

<body style="padding:0px"> 
      <div id="layout1" >
            <div position="left" id="shiftList" class="l-scroll" style="height:95%;overflow:auto;" title="明细项目">
	           <!--不带复选框-->
			    <ul id="deptTree"></ul>
            </div>
            <div position="center" id='shiftParameterInfo' title="明细代码" >
            	<!--不带复选框-->
			    <ul id="deptTree"></ul>
        	</div> 
       </div>
</body>

</html>
