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
    <script src="/resources/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="/resources/js/util/StringUtil.js" type="text/javascript"></script>
    <script src="/resources/js/common.js" type="text/javascript"></script> 
    <script src="/resources/js/json2.js" type="text/javascript"></script>
	
    <script type="text/javascript">
    var $grid ;
   
    // 初始调用
    $(function ()
    {
		 $("form").ligerForm();
		 
		 f_initGrid();
    });
	/**
	*提交改变数据
	*/
	 function f_save(){
        var rows = $grid.getData() ;
        var RT_NO=${RT_NO};
        var REPORT_TYPE=${REPORT_TYPE};
       var manager = $("#viewInfo").ligerGetGridManager();
               
                var jsonData = '[' ;
                
               $.each($("input[name='ITEM_NO']"),function(index,obj){
                    if(obj.checked){ 
                     
                    
                    var col=manager.getRowByRowIndex(index);

                      if (jsonData.length > 1){
	              	             
	              	             jsonData += ',{' ;
	                      }else{
	             	             jsonData += '{' ;
	                             }

	                   jsonData += ' "REF_ITEM_NO": "' + obj.value + '" ,' ;
	                   jsonData += ' "REF_ITEM_ID": "' + col.ITEM_ID + '" ,' ;
	                   jsonData += ' "ITEM_NAME": "' + col.ITEM_NAME + '" ,' ;
	                   jsonData += ' "ITEM_KOR_NAME": "' + $.StringUtil.checkNull(col.ITEM_KOR_NAME)  + '",' ;
	                   jsonData += ' "ITEM_EN_NAME": "' + $.StringUtil.checkNull(col.ITEM_EN_NAME)  + '"' ;
                       jsonData += '}' ;
                       
               	    }
               });
                 jsonData += ']' ;
         
        if(jsonData.length == 2){
        	$.ligerMessageBox.error('提示', "请进行数据的修改,再保存!") ;
            return ;
        }
           
        $.ligerDialog.waitting("保存中...");
        $.post('/sys/sysSettings/AddPageStructureDetailInfo', 
                [	
                 	{ name: 'jsonData', value: jsonData },
                 	{ name: 'RT_NO', value: RT_NO },
                 	{ name: 'REPORT_TYPE', value: REPORT_TYPE },
                ]
        , function (result)
        {
            if (result == "Y")
            {
                $.ligerDialog.closeWaitting();
                $.ligerDialog.success('保存成功!', function ()
                {
                	 parent.f_ChildWindowClose();
                });
            }
            else
            {
                $.ligerDialog.closeWaitting();
                $.ligerMessageBox.error('提示', result);
            }
        });
        
    }
	  
	
	
    function f_initGrid()
    {
       var RT_NO=${RT_NO};
       var REPORT_TYPE=${REPORT_TYPE};
       
    	$grid = $("#viewInfo").ligerGrid({
    	
    		columns: [
    		{ display: '选择', name: 'ITEM_NO' ,minWidth: 40, render: function (item)
	                    {
	                        return "<input type='checkbox' name='ITEM_NO' id='ITEM_NO' value='"+item.ITEM_NO+"' />"
	                    } },
    		{ display: '名称', name: 'ITEM_NAME'},
    		
    		{ display: '英文显示名称', name:'ITEM_EN_NAME', minWidth: 200 ,editor:{type : 'string'}} ,
	        { display: '韩文显示名称',  name:'ITEM_KOR_NAME', minWidth:200 ,editor:{type : 'string'}} 
	        
            
            ],
            enabledEdit: true,
            dataAction: 'server', 
            sortName: 'ORDER_NO',
            url: '/sys/sysSettings/getAddPageStructureDetail',
            parms: [	
	          	{ name: 'RT_NO', value: RT_NO },
	          	{ name: 'REPORT_TYPE', value: REPORT_TYPE}
	          	
             ], 
            width: '99%', height: '99%'
            
        });

    }
   
    
	  function f_delete()
    {
    	$.ligerDialog.confirm('是否进行删除操作!!!', function (yes)
        {
            if(yes){
               var manager = $("#viewInfo").ligerGetGridManager();
               
                var jsonData = '[' ;
                
               $.each($("input[name='PK_NO']"),function(index,obj){
                    if(obj.checked){ 
                       var col=manager.getRowByRowIndex(obj.tagname-1);
                        
                      if (jsonData.length > 1){
	              	             
	              	             jsonData += ',{' ;
	                      }else{
	             	             jsonData += '{' ;
	                             }

	                   jsonData += ' "PK_NO": "' + obj.value + '" ,' ;
	                   jsonData += ' "AR_ITEM_NO": "' + col.AR_ITEM_NO  + '"' ;
                       jsonData += '}' ;
                      
               	    }
               });
                 jsonData += ']' ;
                 
                if(jsonData.length == 2){
        	        $.ligerMessageBox.error('提示', "请选择要删除的数据") ;
                    return ;
                   }
       
		          $.ligerDialog.waitting("删除中...");
                  $.post('/ar/attendanceMintenance/deleteArDetailInfo', 
		                [	
		                 	{ name: 'jsonData', value: jsonData },
		                ]
				
		      
		        , function (result)
		        {
		            if (result == "Y")
		            {
		                $.ligerDialog.closeWaitting();
		                $.ligerDialog.success('删除成功!', function ()
		                {
		                	$grid.loadData(true); //加载数据
		                });
		            }
		            else
		            {
		                $.ligerDialog.closeWaitting();
		                $.ligerMessageBox.error('提示', result);
		            }
		        });
            }
        });
    }
	</script>
	
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        .l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px" >
		 
		<div id="mainBody">
 		<form action="" name="form1">
	       <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	           <tr>
	               <td align="left" > 
	               <a id="addButton" class="l-button" style="width:60px;float:left; margin-left:10px;" onclick="f_save()">
	               <spring:message code="button.sys.affirm.save"/><!--保存--></a>
	                </td>
	            </tr>
	       </table>
	  
      <div position="center" id='viewInfo'></div>
      </form>
      </div>
</body>
</html>
