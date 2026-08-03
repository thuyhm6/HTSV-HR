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
 	// 初始调用
 	 function f_save() {
         $("form .l-text,.l-textarea").ligerHideTip() ;
          
         $.ligerDialog.waitting("保存中...");

          var options = {	                        
              url:'/sys/sysSettings/addPageStructure',
              type:'POST',	                        
              success: function (result){
            
           		$.ligerDialog.closeWaitting();
 		            if (result == "Y")
 		            {
 		                $.ligerDialog.success('保存成功!', function ()
 		                {
 		                	parent.f_ChildWindowClose();
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
<div id = 'layout1'  >

     <form name="form1" method="post" action="" id="form1" >
    
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
                <tr>   
	                <td align="right" class="l-table-edit-td">
	                <spring:message code="sys.postManage.title.chineseName"/><!--中文名称-->:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input name="TABLE_NAME" type="text" id="TABLE_NAME" ltype="text"  validate="{required:true}" />
	                </td>
	                <td align="left"></td> 
	             </tr>  
	             <tr>  
	                <td align="right" class="l-table-edit-td">
	                <spring:message code="sys.postManage.title.englishName"/><!--英文名称-->:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input name="TABLE_EN_NAME" type="text" id="TABLE_EN_NAME" ltype="text"  />
	                </td>
	                <td align="left"></td>
	             </tr>
	             <tr> 
	                <td align="right" class="l-table-edit-td"><!-- 韩文名称-->韩文名称:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input name="TABLE_KOR_NAME" type="text" id="TABLE_KOR_NAME" ltype="text"  />
	                </td>
	                <td align="left"></td>
	             </tr>
	             <tr> 
	                <td align="left" class="l-table-edit-td">显示方式</td>
	               <td align="left" class="l-table-edit-td">
	                	<select id="VIEW_MODEL" name="VIEW_MODEL">
	                	  <option value="1">普通样式</option>
	                	  <option value="2">日期样式</option>
	                	</select>
	                </td>
	              <td align="left"></td>
	                
	            </tr>
	              <tr> 
	                <td align="left" class="l-table-edit-td">数据来源</td>
	               <td align="left" class="l-table-edit-td">
	                	<select id="REPORT_TYPE" name="REPORT_TYPE">
	                	  <option value="1">考勤信息</option>
	                	  <option value="2">工资信息</option>
	                	</select>
	                </td>
	              <td align="left"><input type="hidden" id="MENU_CODE" name="MENU_CODE" value="<%=request.getParameter("MENU_CODE") %>"/></td>
	                
	            </tr>
	            <tr> 
	                <td align="left" class="l-table-edit-td"> </td>
	               <td align="left" class="l-table-edit-td">
	                	  <input type="submit" value="<spring:message code='public.title.submit'/>" id="Button1" class="l-button l-button-submit" /> 
	                </td>
	                <td align="left"></td>
	                
	            </tr>
	         
	             
	           
	        </table>
	 </form>
	 
     
       
 </div>
</body>
</html>
