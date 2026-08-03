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
    	$grid = $("#itemInfo").ligerGrid({
    		checkbox: true,
            columns: [
            { display: '项目ID', name: 'ITEM_ID',width: 200, align: 'left'},
            { display: '项目名称', name: 'ITEM_NAME', align: 'left'},
            { display: '简称', name: 'SHORT_NAME', align: 'left', width: 45},
            { display: '说明', name: 'DESCRIPTION', align: 'left', title: true},
            { display: '项目组', name: 'ITEM_GROUP', width: 80},
            { display: '活跃状态', name: 'ACTIVITY', width: 60, render: function (row, index)
                {
            		return '<img src="/resources/images/a_' + row.ACTIVITY + '.gif">';
            	}
        	}
            ],
            usePager: true, dataAction: 'server', root: 'itemList', record: 'itemCnt',
            url: '/ar/attendanceSettings/getItemList',
            width: '99%', height: '99%',
            heightDiff : 0
        });
    }

    function f_search()
    {   
		var itemName = $("#ITEM_NAME").attr('value') ; 

		if (itemName == null || itemName.length == 0){
			$.ligerDialog.warn('请输入搜索的条件') ;
			return ;
		}
        
    	$grid.setOptions({ parms: [	
    	                        	{ name: 'ITEM_NAME', value: $("#ITEM_NAME").val()}
    	                           ],
    	                   newPage: 1
    	                 }); //设置数据参数
        $grid.loadData(true); //加载数据
    }

    function f_delete()
    {
    	$.ligerDialog.confirm('是否进行删除操作!!!', function (yes)
        {
            if(yes){
             
		    	 var ITEM_ID = "" ;
		     	 var rows = $grid.getCheckedRows();
		
		         if (!rows || rows.length == 0) { alert('请选择行'); return; }
		
		         if (rows.length > 1){
		         	alert('只能选择一行数据进行删除'); return;
		         }
		         
		         $(rows).each(function ()
		         {
		         	ITEM_ID = this.ITEM_ID ;
		         });
				
		        $.ligerDialog.waitting("删除中...");
		        $.post("/ar/attendanceSettings/deleteItem", 
		                [	
		                 	{ name: 'ITEM_ID', value: ITEM_ID }
		                ]
		        , function (result)
		        {
		        	$.ligerDialog.closeWaitting();
		            if (result == "Y")
		            {
		                $.ligerDialog.success('删除成功!', function ()
		                {
		                	$grid.loadData(true); //加载数据
		                });
		            }
		            else
		            {
		                $.ligerMessageBox.error('提示', result);
		            }
		        });
            }
        });
    }

    function f_ChildWindowClose (){
    	$grid.loadData(true); //加载数据
    	$dialog.close() ;
    }

    function f_add()
    {
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/ar/attendanceSettings/addItemView'
        	  });
    }

    function f_update()
    {
        var ITEM_ID = "" ;
    	var rows = $grid.getCheckedRows();

        if (!rows || rows.length == 0) { alert('请选择行'); return; }

        if (rows.length > 1){
        	alert('只能选择一行数据进行修改'); return;
        }
        
        $(rows).each(function ()
        {
        	ITEM_ID = this.ITEM_ID ;
        });

        
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/ar/attendanceSettings/updateItemView?ITEM_ID=' + ITEM_ID
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
	           	   <td align="right" class="l-table-edit-td">项目名称:</td>
	               <td align="left" class="l-table-edit-td"><input name="ITEM_NAME" type="text" id="ITEM_NAME" /></td>
	               <td align="right">
	               	    <a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_search()">搜索</a>
	               		<a class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_add()">添加</a>
	               		<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_update()">修改</a>
						<a class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_delete()">删除</a>
	               </td>
	           </tr>
	             
	       </table>
	  </div>
      <div position="center" id='itemInfo'></div>
            	
</div>       
</body>
</html>
