<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >	
    
    <script type="text/javascript">
    var zTree;
	var demoIframe;

	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			expandSpeed: "fast"
		},
		data: {
			key: {
				name: "DEPTNAME",
				open:"true"
			},
			simpleData: {
				enable:true,
				idKey: "DEPTNO",
				pIdKey: "PARENT_DEPT_NO",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				document.getElementById('deptDetail').style.display="";
				document.getElementById('deptid').innerHTML=treeNode.DEPTID;
				document.getElementById('deptName').innerHTML=treeNode.DEPTNAME;
				document.getElementById('deptNo').innerHTML=treeNode.DEPTNO;
				document.getElementById('datecreateSpan').innerHTML=treeNode.DATE_CREATED;
				document.getElementById('parentDept').innerHTML=treeNode.PARENT_DEPT_NAME;
				document.getElementById('DEPT_TYPE_NAME').innerHTML=treeNode.DEPT_TYPE_NAME;
			}
		}
	};

	var zNodes;
	 $.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: "/org/orgManage/getOrgInfoTreeDate?endEddate=${endEddate}",//请求的action路径  
	        error: function () {//请求失败处理函数  
	            alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');  
	        },  
	        success:function(data){ //请求成功后处理函数。    
	        	zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
	        }  
	    }); 
    // 初始调用
   $(document).ready(function(){
        //布局
        $("#layout1").ligerLayout({ leftWidth: 180});
    	var t = $("#deptTree");
		t = $.fn.zTree.init(t, setting, zNodes);
    });
    function onSelect(note){
    	
    	document.getElementById('deptDetail').style.display="";
        document.getElementById('deptid').innerHTML=note.data.DEPTID;
        document.getElementById('deptName').innerHTML=note.data.DEPTNAME;
        document.getElementById('deptNo').innerHTML=note.data.DEPTNO;
        document.getElementById('datecreateSpan').innerHTML=note.data.DATE_CREATED;
        document.getElementById('parentDept').innerHTML=note.data.PARENT_DEPT_NAME;
        document.getElementById('DEPT_TYPE_NAME').innerHTML=note.data.DEPT_TYPE_NAME;
    }
    </script>
</head>

<body style="padding:0px"> 
      <div id="layout1" >
      
            <div position="left"  class="l-scroll" style="height:95%;overflow:auto;" 
                 title="<spring:message code='org.orgManage.title.orgHorizView'/>"><!--组织架构-->
	           <!--不带复选框-->
	           
	           <div style="width:100%;">
					<form  id="levelForm"  onsubmit="return navTabSearch(this);" action="/org/orgManage/viewOrgInfoTree" method="post">
<!-- 					   <input id="endEddate" type="text" name="seach_endEddate" class="date" readonly="true" value="${endEddate}" size="10"/> -->
<!-- 					      <a class="inputDateButton"><spring:message code="org.orgManage.title.choose"/>选择</a>  -->
<!-- 						<div class="buttonActive"><div class="buttonContent"><button type="submit"> -->
<!-- 						<spring:message code="public.title.search"/>检索</button></div></div> -->
					</form>
			   </div><br/>
			   <div style="width:100%;padding-left: 5px">&nbsp;<ul id="deptTree" class="ztree" height="90%"></ul></div> 
			    
            </div>
            <div position="center" id='shiftParameterInfo' 
                 title="<spring:message code='org.orgManage.title.orgLogitView'/>" ><!--组织纵向查看-->
            	<!--不带复选框-->
			    <ul id="deptTree111"></ul>
			  <table id="deptDetail" width="100%" cellspacing="2" cellpadding="1" bordercolor="#C8C8C8" border="1">
				  <tbody>
				  <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
				    <spring:message code="org.orgManage.title.deptId"/><!--部门ID--></div></td>
				    <td id="deptid" style="padding-left:10px "> &nbsp;  </td>
				  </tr>
				  <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
				    <spring:message code="org.orgManage.title.deptName"/><!--部门名称--></div></td>
				    <td  id="deptName"  style="padding-left:10px "> &nbsp;111 </td>
				  </tr>
				  <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
				    <spring:message code="org.orgManage.title.deptCode"/><!--部门编码--></div></td>
				    <td id="deptNo"  style="padding-left:10px "> &nbsp;  </td>
				  </tr>
				  <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
				     <spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></div></td>
				    <td id="datecreateSpan"  style="padding-left:10px "></td>
				  </tr>
				  <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
                    <spring:message code="org.orgManage.title.parentDept"/><!--上级部门--></div></td>
				    <td id="parentDept"  style="padding-left:10px ">
				    	&nbsp; 
				 	</td>
				  </tr>
				   <tr height="30">
				    <td width="26%" bgcolor="#E9EAF8"><div align="center" class="STYLE6">
				    <spring:message code="org.orgManage.title.deptType"/><!--部门类型--></div></td>
				    <td id="DEPT_TYPE_NAME"  style="padding-left:10px ">
				    	&nbsp; 
				 	</td>
				  </tr>
				  </tbody>
				</table>
        	</div> 
       </div>
</body>

</html>
