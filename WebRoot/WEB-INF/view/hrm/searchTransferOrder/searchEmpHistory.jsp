<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >

    
    <!-- CSS -->
    <link href="/resources/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
     <!-- JS -->
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script> 
    <script src="/resources/js/common.js" type="text/javascript"></script> 

	
    <script type="text/javascript">
    var $grid ;
   
     $(function ()
    {
        f_initGrid(); 
    });
    function f_initGrid()
    {if('${HISTORYTYPE}'=='')
    	$grid = $("#empHistory").ligerGrid({
    		columns: [
    		{ display: '行号', name: 'EMPID', type:'checkbox' },
    		{ display: '姓名', name: 'CHINESENAME', type:'checkbox' },
    		{ display: '部门', name: 'DEPARTMENT', type:'checkbox' },
    		{ display: '职级', name: 'POSTGRADENAME', type:'checkbox' },
    		{ display: '职位', name: 'POSITIONNAME', type:'checkbox' },
    		{ display: '职务', name: 'POST', type:'checkbox' },
    		{ display: '处/部', name: 'DEPTENNAME', type:'checkbox' },
    		{ display: '担当业务', name: 'BUSINESS', type:'checkbox'
		    },
    		{ display: '员工状态', name: 'STATUS', type:'checkbox' }
            ],
            data:${empHistory},
            width: '99%', height: '99%',
        });
      else
      	$grid = $("#empHistory").ligerGrid({
    		columns: [
    		{ display: '担任起始日', name: 'EFFECTDATE', type:'checkbox' },
    		{ display: '担任结束日', name: 'ENDDATE', type:'checkbox' },
    		{ display: '部门', name: 'DEPTNAME', type:'checkbox' },
    		{ display: '职级', name: 'POSTGRADENAME', type:'checkbox' },
    		{ display: '职位', name: 'POSITIONNAME', type:'checkbox' },
    		{ display: '处/部', name: 'PARTNAME', type:'checkbox' },
    		{ display: '职务', name: 'POSTNAME', type:'checkbox' },
    		{ display: '担当业务', name: 'BIZNAME', type:'checkbox'
		    },
    		{ display: '备注', name: 'STATUS', type:'checkbox' }
            ],
            data:${empHistory},
            width: '99%', height: '99%',
        });
    }
	</script>

</head>
<body style="padding:0px" >

 
      	<div id="empHistory"></div>
   
</body>
</html>
