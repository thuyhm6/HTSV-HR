<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >
   
    <!-- CSS -->
   <%@ include file="/WEB-INF/view/inc/initUICss.jsp"%>
     <!-- JS -->
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 
    <script src="/resources/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script> 
    <script src="/resources/js/json2.js" type="text/javascript"></script>
	
    <script type="text/javascript">
    
    var $grid ;
    var $dialog ;
    // 初始调用
    $(function ()
    {
    	//布局
        $("#layout1").ligerLayout({
            allowLeftResize: false,      //是否允许 左边可以调整大小
            allowRightResize: false,     //是否允许 右边可以调整大小
            allowTopResize: false,       //是否允许 头部可以调整大小
            allowBottomResize: false     //是否允许 底部可以调整大小
        });
        
         f_initGrid();
    });

    function f_initGrid()
    {
    	$grid = $("#cycleInfo").ligerGrid({
    		checkbox: true,
            columns: [
            { display: '序号', name: 'STAT_MODE_NO', width: 50, type: 'int' },
            { display: '区间说明', name: 'STAT_TYPE_CODE'},
            { display: '实际开始日期', name: 'VALID_DATE_FROM', align: 'left'},
            { display: '实际结束日期', name: 'VALID_DATE_TO', align: 'left'},
            { display: '开始日', name: 'START_DATE', align: 'left'},
            { display: '结束日', name: 'END_DATE', align: 'left'}
            ],
            usePager: true, dataAction: 'server', root: 'cycleList', record: 'cycleCnt',
            url: '/ar/attendanceSettings/getCycleList',
            width: '99%', height: '99%',
            heightDiff : 0
        });
    }

    function f_delete()
    {
  		
    }

    function f_ChildWindowClose (){
    	$grid.loadData(true); //加载数据
    	$dialog.close() ;
    }

    function f_add()
    {
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/ar/attendanceSettings/addCycleView'
        	  });
    }

    function f_update()
    {
        var STAT_MODE_NO = "" ;
    	var rows = $grid.getCheckedRows();

        if (!rows || rows.length == 0) { alert('请选择行'); return; }

        if (rows.length > 1){
        	alert('只能选择一行数据进行修改'); return;
        }
        
        $(rows).each(function ()
        {
        	STAT_MODE_NO = this.STAT_MODE_NO ;
        });

        
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/ar/attendanceSettings/updateCycleView?STAT_MODE_NO=' + STAT_MODE_NO
       	  });
    }
   
    
	</script>
	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px" > 
<div id="layout1" > 
	 <div position="top">
	       <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	           <tr>
	               <td align="right">
	               		<a class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_add()">添加</a>
	               		<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_update()">修改</a>
						
	               </td>
	           </tr>
	             
	       </table>
	  </div>
      <div position="center" id='cycleInfo'></div>
</div>
       
</body>
</html>
