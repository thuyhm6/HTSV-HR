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
    <script src="/resources/js/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
    
    <script src="/resources/js/jquery/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="/resources/js/jquery/jquery.metadata.js" type="text/javascript"></script>	
	<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	
	<script src="/resources/js/json2.js" type="text/javascript"></script>
       <script type="text/javascript">
    
    var $grid ;
    var $tree ; 
    // 初始调用
    $(function ()
    {
        //布局
         $("#layout1").ligerLayout({ leftWidth: 150});

         $tree = $("#getIsCanBeBuildPageTree").ligerTree(
	          { 
		          checkbox: false,
	              onSelect: onSelect
	          }
          );

         f_initGrid();

    });

    function onSelect(note)
    {
 
 		 $.ajax({
	       	type:'post',
	       	cache:false,
	       	contentType:'application/json',	            			            	
	       	url:'/sys/sysSettings/getPageStructureInfoList?MENU_CODE=' + note.data.url,            	
	       	dataType:'json',
	       	success:function(response){ 
	        		var jsonObj = {};
	               jsonObj.Rows = response ;
	        		//$grid.showData(jsonObj) ;
	     		   $grid.setOptions({ data : jsonObj }); //设置数据参数
	               $grid.loadData(true); //加载数据
	       	}          	
      	});
    }

    function f_initGrid()
    {
    	$grid = $("#pageStructureInfoList").ligerGrid({
    	checkbox: true,   
            columns: [
            { display: '中文名称', name: 'TABLE_NAME'},
            { display: '英文名称', name: 'TABLE_EN_NAME'}, 
            { display: '韩文名称', name: 'TABLE_KOR_NAME'},
            { display: '显示模式', name: 'VIEW_MODEL', render: function (row, index)
                {
                	if(row.VIEW_MODEL == 1 ){ return '普通样式'; }else{ return '日期样式'; }
        		}
            
            },
            { display: '数据归属', name: 'REPORT_TYPE',
             render: function (row, index)
                {
                	if(row.REPORT_TYPE == 1 ){ return '考勤信息'; }else{ return '工资信息'; }
        		}
        	}
            ],
            enabledEdit: false,  
            width: '100%', height: '100%',
            heightDiff : 0
        });
    }
    
  	 
    function f_add()
    {
        var note = $tree.getSelected();
        if (note == null){ 
        $.ligerMessageBox.error('提示', '选择要添加的页面');
        return ;}
        
        var MENU_CODE = note.data.url ;
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/sys/sysSettings/addPageStructureView?MENU_CODE='+MENU_CODE
        	  });
    }
  

    function f_ChildWindowClose(){
         
    	 var note = $tree.getSelected();
    	 onSelect(note);
        $dialog.close() ;
      }
    
    function f_updatePageView()
    {
     var note = $tree.getSelected();

    	if (note == null){ 
    	 $.ligerMessageBox.error('提示', '请选中要修改的页面');
    	return ;}
    	
    	 var rows = $grid.getCheckedRows();
	     if (!rows || rows.length == 0||rows.length > 1) { 
	              alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChooseOneContent"/>');
	              return false; }
	              var RT_NO = "" ;
	              var REPORT_TYPE="";
	              $(rows).each(function ()
			         {
			         	RT_NO = this.RT_NO ;
			         	REPORT_TYPE = this.REPORT_TYPE ;
			         	 
			         });
       
    	 
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/sys/sysSettings/viewUpdatePageStructureDetail?RT_NO='+RT_NO+'&REPORT_TYPE='+REPORT_TYPE
        	  });
    }
    
   function  f_delete()
   {
      $.ligerDialog.confirm('是否进行删除操作!!!', function (yes)
        {
            if(yes){
   
	     	    var rows = $grid.getCheckedRows();
	            if (!rows || rows.length == 0) { 
	              alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChooseOneContent"/>'); 
	              return false; }
	              var RT_NO = "" ;
	              $(rows).each(function ()
			         {
			         	RT_NO += this.RT_NO+"," ;
			         });
			       var note  = $tree.getSelected();
			      
			        $.ligerDialog.waitting("删除中...");
			         $.post("/sys/sysSettings/deletePageStructure", 
			                [	
			                 	{ name: 'RT_NO', value: RT_NO }
			                  
			                ]
			        , function (result)
			        {
			        	 $.ligerDialog.closeWaitting();
			            if (result == "Y")
			            {
			                $.ligerDialog.success('删除成功!', function ()
		       		                {
		       		                	  onSelect(note);
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
    
   
    
      function f_addPageView()
    {
       var note = $tree.getSelected();

    	if (note == null){ 
    	 $.ligerMessageBox.error('提示', '请选中要添加页面');
    	return ;}
    	
    	 var rows = $grid.getCheckedRows();
	     if (!rows || rows.length == 0||rows.length > 1) { 
	              alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChooseOneContent"/>'); 
	              return false; }
	              var RT_NO = "" ;
	              var REPORT_TYPE="";
	              $(rows).each(function ()
			         {
			         	RT_NO = this.RT_NO ;
			         	REPORT_TYPE = this.REPORT_TYPE ;
			         	 
			         });
       
    	 
    	$dialog = $.ligerDialog.open({isDrag: false, 
    		width: $("#layout1").width(), height: $("#layout1").height(), url: '/sys/sysSettings/viewAddPageStructureDetail?RT_NO='+RT_NO+'&REPORT_TYPE='+REPORT_TYPE
        	  });
           
    }
    
	</script>
	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        .l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px"> 
      <div id="layout1" >
            <div position="left" id="getIsCanBeBuildPage" class="l-scroll" style="height:95%;overflow:auto;" title="构造页面">
	            <!--不带复选框-->
			    <ul id="getIsCanBeBuildPageTree">
			    	<c:forEach items="${getIsCanBeBuildPage}" var="bp" varStatus="i">
		    			<li url="${bp.MENU_CODE_ID}">
				            <span >${bp.MENU_INTRO_NAME}</span>
					 	</li>
					</c:forEach>
			    </ul>
            
            </div>
            <div position="center" id='shiftParameterInfo' title="详细内容" >
            	<div>
            		<a class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_add()">添加详细</a>
               		<a class="l-button" style="width:80px; float:left; margin-left:10px;" onclick="f_addPageView()">添加页面详细内容</a>
               		<a class="l-button" style="width:80px; float:left; margin-left:10px;" onclick="f_updatePageView()">修改页面详细内容</a>
               		<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_delete()">删除页面</a>
				</div>
				<div class="l-clear"></div>
				</br>
               <div id="pageStructureInfoList" ></div>
        	</div> 
       </div>
</body>
</html>
