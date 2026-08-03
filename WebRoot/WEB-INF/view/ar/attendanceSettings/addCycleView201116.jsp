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
    
    <script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	
    <%@ include file="/resources/js/validateForm.jsp"%>
	
<script type="text/javascript">
    var $grid ;
 	// 初始调用
 	
   function f_save() {
         $("form .l-text,.l-textarea").ligerHideTip() ;
         
         $.ligerDialog.waitting("保存中...");

         var options = {	                        
             url:'/ar/attendanceSettings/addCycleInfo',
             type:'POST',	                        
             success: function (result){
          		$.ligerDialog.closeWaitting();
		            if (result == "Y")
		            {
		                $.ligerDialog.success('保存成功!', function ()
		                {
		                	parent.f_ChildWindowClose() ;
		                });
		            }
		            else
		            {
		                $.ligerMessageBox.error('提示', result);
		            }
		        }
             }; 
             $('#form1').ajaxSubmit(options); 
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
	  <div id = 'layout1'>
		     <form name="form1" method="post" action="" id="form1">
				        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
				            <tr>
				                <td align="right" class="l-table-edit-td">开始日:</td>
				                <td align="left" class="l-table-edit-td">
				                	<input name="START_DATE" type="text" id="START_DATE" 
				                	type='spinner' ligerui="{type:'int'}" value="20" class="required" validate="{digits:true,min:1,max:100}" />
				                </td>
				                <td align="left"></td>
				             </tr>
				             <tr>   
				                <td align="right" class="l-table-edit-td">结束日:</td>
				                <td align="left" class="l-table-edit-td">
				                	<input name="END_DATE" type="text" id="END_DATE" 
				                	type='spinner' ligerui="{type:'int'}" value="19" class="required" validate="{digits:true,min:1,max:100}" />
				                </td>
				                <td align="left"></td>
				            </tr>
				            <tr>
				                <td align="right" class="l-table-edit-td">实际开始日期:</td>
				                <td align="left" class="l-table-edit-td">
				                	<input name="VALID_DATE_FROM" type="text" id="VALID_DATE_FROM" ltype="date" validate="{required:true}" />
				                </td>
				                <td align="left"></td>
				            </tr>
				             <tr>
				                <td align="right" class="l-table-edit-td">实际结束日期:</td>
				                <td align="left" class="l-table-edit-td">
				                	<input name="VALID_DATE_TO" type="text" id="VALID_DATE_TO" ltype="date" validate="{required:true}" />
				                </td>
				                <td align="left"></td>
				            </tr>
				            <tr>
				                <td align="right" class="l-table-edit-td">区间说明:</td>
				                <td align="left" class="l-table-edit-td">
				                	<input name="STAT_TYPE_CODE" type="text" id="STAT_TYPE_CODE" ltype="text"  />
				                </td>
				                <td align="left"></td>
				            </tr>
				        </table>
				<input type="submit" value="提交" id="Button1" class="l-button l-button-submit" /> 
	         </form>
	  </div>
</body>
</html>
