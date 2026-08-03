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
	<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
    <%@ include file="/resources/js/validateForm.jsp"%>
    <script src="/resources/js/json2.js" type="text/javascript"></script>
	
    <script type="text/javascript">
 	
    var NewOrEdit="/hrm/org/newOREditOrganizationInfo";
    var del="/hrm/org/delOrganizationInfo";
    // 初始调用
    $(function ()
    {
		// $("#areaCode").ligerComboBox();
		 $("#areaCode option[value='${editDept.AREAID}']").attr("selected", true); 
		// $("#parentDeptId").ligerComboBox({initValue: '${editDept.PARENT_DEPT_ID}'});
		$("#parentDeptId option[value='${editDept.PDEPTNAMEID}']").attr("selected", true); 
		 $("#createdDate").ligerDateEditor();
		 <c:if test="${not empty deptPost}">
			<c:forEach items="${deptPost}" var="deptPostid">
			 	$.each($(":checkbox"),function(index,chbox){
		 			if(chbox.value=='${deptPostid.POSTID}')
		 				chbox.checked='checked';
		 		});
			</c:forEach>
			$("#endEddate").ligerDateEditor();
			$("#datecreateSpan").html('${editDept.DATE_CREATED }'.substring(0,10));
		</c:if>
    });
    
    function f_back(){
    	window.history.go(-1);
    }
    
   
    function f_save(){
    	 $.ligerDialog.waitting("<spring:message code="alert.message.org.orgManage.saving"/>...");/* 保存中 */

        var options = {	                        
            url:'/hrm/org/saveOrganization',
            type:'POST',	                        
            success: function (result){
            		$.ligerDialog.closeWaitting();
  		            if (result == "Y")
  		            {
  		                $.ligerDialog.success('<spring:message code="alert.message.org.orgManage.saving"/>', function ()
  		                {
  		                	location.href= "/hrm/org/viewOrganizationInfo" ;
  		                });
  		            }
  		            else
  		            {
  		                $.ligerMessageBox.error('<spring:message code="alert.message.org.orgManage.saveFailed"/>');
  		            }
  		        }              
            }; 
        $('#from1').ajaxSubmit(options);
    }


    
   

	</script>
	<style type="text/css"> 
        body{ padding:5px; margin:0; padding-bottom:15px;}
        #layout1{  width:99%;margin:0; padding:0;  }  
        .l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .STYLE6 {color: #003399; font-weight: bold; font-size: 18px; }
        h4{ margin:20px;}
    </style>
</head>
<body style="padding:0px" >
		<div id="mainBody">
		<div class="l-page-top" onkeydown="if(event.keyCode==13) f_search();">
	       <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	           <tr>
	           	   <td align="left">
	               		<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_save()">
	               		<spring:message code="public.title.submit"/><!--保存--></a>
	               		<a class="l-button" style="width:60px; float:left; margin-left:10px;" onclick="f_back()">
	               		<spring:message code="org.orgManage.title.return"/><!--返回--></a>
	               </td>
	           </tr>
	       </table>
	  </div>
     <div>
     <form action="/hrm/org/" method="post" id="from1">
     	<table width="100%" border="1" cellpadding="1" cellspacing="2" bordercolor="#C8C8C8">
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptId"/><!--部门ID--></div></td>
    <td><input name="deptId" value="${editDept.DEPTID }" type="text" /></td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptName"/><!--部门名称--></div></td>
    <td><input name="deptName" value="${editDept.DEPTNAME }" type="text" /></td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptCode"/><!--部门编码--></div></td>
    <td><input name="deptcode" value="${editDept.DEPTNO }" type="text" /></td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptEnglishName"/><!--部门英文名称--></div></td>
    <td><input name="deptEnName" value="${editDept.DEPT_EN_NAME }" type="text" /></td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
     <spring:message code="org.orgManage.title.ownArea"/><!--所属地区--></div></td>
    <td><ait:selectSyCode parentNo='WorkAreaCode' name='areaCode' limit='all'/></td>
  </tr>
  <c:if test="${empty editDept}">
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></div></td>
    <td><input id="createdDate" value="${editDept.DATE_CREATED }" name="createdDate" type="text" /></td>
  </tr>
  </c:if>
  <c:if test="${not empty editDept}">
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
     <spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></div></td>
    <td><span id="datecreateSpan"></span><input id="createdDate" value="${editDept.DATE_CREATED }" name="createdDate" type="hidden" /></td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
    <spring:message code="org.orgManage.title.deptEndTime"/><!--部门结束时间--></div></td>
    <td> <input id="endEddate" value="${editDept.DATE_ENDED }" name="endEddate" type="text" ></td>
  </tr>
  </c:if>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
     <spring:message code="org.orgManage.title.parentDept"/><!--上级部门--></div></td>
    <td>
    	<select id="parentDeptId" name="parentDeptId" >
    	<c:if test="${not empty deptList}">
    		<c:forEach items="${deptList}" var="dept">
    			<option value="${dept.DEPTID }">
    				<c:if test="${dept.DEPTLEVEL eq 1}">${dept.DEPTNAME}</c:if>
    				<c:if test="${dept.DEPTLEVEL eq 2}">&nbsp;&nbsp;${dept.DEPTNAME}</c:if>
    				<c:if test="${dept.DEPTLEVEL eq 3}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dept.DEPTNAME}</c:if>
    				<c:if test="${dept.DEPTLEVEL eq 4}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dept.DEPTNAME}</c:if>
    				<c:if test="${dept.DEPTLEVEL eq 5}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dept.DEPTNAME}</c:if>
    			</option>
    		</c:forEach>
    	</c:if>
    	</select>
 	</td>
  </tr>
  <tr>
    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
         <spring:message code="org.orgManage.title.post"/><!--职务--></div></td>
    <td>
    	<table width="100%">
    			 <tr align="center" width="100%"> 
			       <c:forEach items="${postList}" var="vList" varStatus="i">
			             <c:choose>
			              	<c:when test="${i.count % 5 == 0}">
					           <td width="20%" align="left">
					              <input name="isChecked" id="isChecked_${vList.POST_ID}" value="${vList.POST_ID}"  type="checkbox" style="border:0px"/>
					              	${vList.POST_NAME}
					            </td>	
					</tr>
					              		
					           <c:if test="${i.count != postListSize}">
					              	<tr align="center" width="100%"> 
					           </c:if>
							</c:when>
		  				<c:otherwise>
					         <td width="20%" align="left">
					           <input name="isChecked" id="isChecked_${vList.POST_ID}" value="${vList.POST_ID}" type="checkbox" style="border:0px"/>					              		
					             ${vList.POST_NAME}
					          </td>		  							
		  				</c:otherwise>
			      </c:choose>	
				</c:forEach>
    	</table>
    </td>
  </tr>
</table>
</form>
     </div>
            	
            </div>
</body>
</html>
