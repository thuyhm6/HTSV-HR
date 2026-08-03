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
    
    <script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="/resources/js/json2.js" type="text/javascript"></script>
	
    <%@ include file="/resources/js/validateForm.jsp"%>
	
<script type="text/javascript">
    var $deptTree ;
 	// 初始调用
 	
    $(function() {

    	//布局
        $("#layout1").ligerLayout({
        		topHeight: 110,
	            allowLeftResize: false,      //是否允许 左边可以调整大小
	            allowRightResize: false,     //是否允许 右边可以调整大小
	            allowTopResize: false,      //是否允许 头部可以调整大小
	            allowBottomResize: false     //是否允许 底部可以调整大小
            }
          );
        
    	$('input:checkbox').ligerCheckBox();

    	$deptTree = $("#deptTree").ligerTree(
    	    	{ 
        	    	url: '/sys/rightsManagement/getLoginUserDeptList?ADMIN_NO=${loginUserInfo.ADMINNO}', 
        	    	idFieldName: 'DEPTID', parentIDFieldName: 'PARENT_DEPT_ID', textFieldName: 'DEPTNAME',
        	    	topParentIDValue: '${LoginUser.cpnyId}', ischeckedFieldName: 'ISCHECKED', isexpandFieldName: 'ISEXPAND'  
            	 }
        );

    });

    function f_save(){
          $("form .l-text,").ligerHideTip();

          $.ligerDialog.waitting("修改中...");
          getChecked() ;
          
          var options = {	                        
              url:'/sys/rightsManagement/updateLoginUserInfo',
              type:'POST',	                        
              success:function (result){
        	  $.ligerDialog.closeWaitting();
		            if (result == "Y")
		            {
		                $.ligerDialog.success('修改成功!', function ()
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

    function collapseAll()
    {
    	$deptTree.collapseAll();
    }
    function expandAll()
    {
    	$deptTree.expandAll();
    }
    function getChecked()
    {
        var notes = $deptTree.getChecked();
        
        var jsonData = '[' ;
        for (var i = 0; i < notes.length; i++)
        {
        	if (jsonData.length > 1){
            	jsonData += ',{'
            }
            else{
            	jsonData += '{'
            }
            jsonData += ' "ADMIN_DEPTID": "' + notes[i].data.DEPTID + '", ' ;
            jsonData += ' "ADMIN_NO": ' + '${loginUserInfo.ADMINNO}' ;
            jsonData += '}' ;
        }
        jsonData += ']' ;

        $('#jsonData').attr('value' , jsonData) ;
        
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
	     <form name="form1" method="post" action="" id="form1">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="right" class="l-table-edit-td">
	                <spring:message code="public.title.empId"/><!--工号-->
	                (<spring:message code="sys.rights.title.userName"/><!--用户名-->):</td>
	                <td align="left" class="l-table-edit-td">${loginUserInfo.EMPID}</td>
	                <td align="right" class="l-table-edit-td">
	                <spring:message code="sys.rights.title.password"/><!--密码-->:</td>
	                <td align="left" class="l-table-edit-td">
	                    <input id="ADMINNO" name="ADMINNO" type="hidden" value="${loginUserInfo.ADMINNO}"/>
	                	<input name="PASSWORD" type="text" value="${loginUserInfo.PASSWORD}" id="PASSWORD" ltype="text" validate="{required:true}" />
	                	<input id="jsonData" name="jsonData" type="hidden" value=""/>
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	                <td align="right" class="l-table-edit-td">
	                <spring:message code="sys.rights.title.privilegeGroup"/><!--权限组-->:</td>
	                <td align="left" class="l-table-edit-td" colspan="4" id='checkBoxTd'>
	                	<c:forEach items="${loginUserInfoRolesGroupList}" var="rolesGroup">
	                		<input type="checkbox" name="SCREEN_GRANT_NO"  value="${rolesGroup.SCREEN_GRANT_NO}"
	                			<c:if test="${ rolesGroup.CHECKED == 1}">checked=true</c:if> 
	                		/>${rolesGroup.SCREEN_GRANT_NAME}&nbsp;&nbsp;
	                	</c:forEach>
	                </td>
	            </tr>
	        </table>
			<input type="submit" value="<spring:message code='public.title.submit'/><!--提交-->" 
			       id="Button1" class="l-button l-button-submit" /> 
         </form>
	  </div>
      <div position="center" id="dept" style="height:99%;overflow:auto;" 
           title="<spring:message code='sys.rights.title.permissionsSet'/>"><!--权限集-->
      		<a class="l-button" onclick="collapseAll()" style="float:left;margin-right:10px;">
      		<spring:message code="sys.rights.title.foldingAll"/><!--折叠全部--></a> 
    		<a class="l-button" onclick="expandAll()" style="float:left;margin-right:10px;">
    		<spring:message code="sys.rights.title.openAll"/><!--张开全部--></a> 
      	<ul id="deptTree">
      	</ul>
      </div>
 </div>	
</body>
</html>