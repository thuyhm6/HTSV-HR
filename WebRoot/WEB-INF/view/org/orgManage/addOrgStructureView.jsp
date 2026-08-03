<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/**
 * 开始加载部门树
 */
var setting_org0103_add = { 
		view: {
		  dblClickExpand: false,
		  showLine: true,
		  selectedMulti: false,
		  expandSpeed: "fast"
		 },
		 data: {
		   key: {
		     children:"children",
		     name:"CONTENT",
		     title:""
		   },
		   simpleData: {
		       enable:true,
		       idKey:"DEPTNO",
		       pIdKey:"PARENT_DEPT_NO",
		       rootPId: ""  
		   }
		 },
		 callback: {
			 beforeClick: function(treeId, treeNode) {
			    var deptName = $("#deptName_org0103_add_0").val();
				if(deptName == treeNode.CONTENT){
					 $("#deptName_org0103_add_0").attr("value", '');
					 $("#seach_DEPTNO_0").attr("value",'');
			    }else{
					 $("#deptName_org0103_add_0").attr("value", treeNode.CONTENT);
					 $("#seach_DEPTNO_0").attr("value",treeNode.DEPTNO);
				}
				document.getElementById("DEPT_LEVEL").value=treeNode.DLEVEL+1;
			  //  deptChange_org0103_add("",treeNode.DEPTNO);
			 }
		 }
		};
var zNodes_org0103_add;
$.ajax({  
    async : false,  
    cache:false,  
    type: 'POST',  
    dataType : "json",  
    url: "/sys/arAffirmPost/getOrgInfoTreeData",//请求的action路径  
    error: function () {//请求失败处理函数  
        alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');  
    },  
    success:function(data){ //请求成功后处理函数。   
    	zNodes_org0103_add = data;   //把后台封装好的简单Json格式赋给treeNodes
    }  
}); 
var nodeNameId='';
function showTree_org0103_add(obj) {
		nodeNameId=obj.id;
		var cityObj = $("#"+obj.id);
		var cityOffset = $("#"+obj.id).offset();
		$("#deptContent_org0103_add").offset(
				{top:cityOffset.top +30+ "px",
				 left:cityOffset.left + cityObj.outerHeight()+ "px" 
				 }).slideDown("fast");$("body").bind("mousedown", onBodyDown_org0103_add);
}
function hideMenu_org0103_add() {
	 	 $("#deptContent_org0103_add").fadeOut("fast");
	 	 $("body").unbind("mousedown", onBodyDown_org0103_add);
}
function onBodyDown_org0103_add(event) {
	 var str=event.target.id;
	 if(str.indexOf("switch")==-1&&(str.indexOf("_span")>-1||str.indexOf("_ico")>-1)){
		 if(str!="deptContent_org0103_add"){
			 hideMenu_org0103_add();
		 }
	 }
}
$(document).ready(function(){
	$.fn.zTree.init($("#deptTree_org0103_add"), setting_org0103_add, zNodes_org0103_add);
});

//加载部门树结束
</script>
<div class="pageContent">
	<form method="post" action="/org/orgManage/addOrgStructureInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<ait:SyLanguage/>
			
			<dl>
				<dt><spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分--></dt>
				<dd> 
					<select name="DEPT_DISTINGUISH_NO" class="combox" class="required">
						 <c:forEach items="${distinguishList}" var="distinguish">
						 	<option value="${distinguish.CODE_NO}">${distinguish.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptType"/><!--部门类型--></dt>
				<dd> 
					<select name="DEPT_TYPE" class="combox" class="required">
						 <c:forEach items="${deptTypeList}" var="deptType">
						 	<option value="${deptType.CODE_NO}">${deptType.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.ownArea"/><!--所属地区--></dt>
				<dd> 
					<select name="WORK_AREA" class="combox" class="required">
						 <c:forEach items="${areaList}" var="area">
						 	<option value="${area.CODE_NO}">${area.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></dt>
				<dd>
					<input id="createdDate" type="text" name="createdDate" class="date required" readonly="true"/>
					<a class="inputDateButton"><spring:message code="org.orgManage.title.choose"/><!--选择--></a>
					<input type="hidden" name="DEPT_LEVEL" id="DEPT_LEVEL" value="${organizationInfo.DEPT_LEVEL}"/>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.parentDept"/><!--上级部门-->
					<input id="deptName_org0103_add_0" type="text" onclick="showTree_org0103_add(this);" readonly class="required" value="${organizationInfo.PARENT_DEPT_NAME_ZH}"/> 
				</dt>
				<dd>
				    <input id="seach_DEPTNO_0" name="PARENT_DEPT_NO" type="hidden" sysLong="sy0482"  value="${organizationInfo.PARENT_DEPT_NO}"/>	
					<div id="deptContent_org0103_add" style="display:none;position:absolute; z-index:1;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
						<ul id="deptTree_org0103_add" class="ztree" style="margin-top:0; width:300px;">
						</ul>
					</div>
				</dd>
			</dl>
			
			<dl><dt><spring:message code="org.orgManage.title.post"/><!-- 职务 --></dt>
			<dd>
				<table width="100%">
	    			 <tr align="center" width="100%"> 
				       <c:forEach items="${postList}" var="vList" varStatus="i">
				             <c:choose>
				              	<c:when test="${i.count % 5 == 0}">
						           <td width="20%" align="left">
						              <input name="isChecked" id="isChecked_${vList.POST_NO}" value="${vList.POST_NO}"  type="checkbox" style="border:0px"/>
						              	${vList.POST_NAME}
						            </td>	
							    </tr>
								</c:when>
				  				<c:otherwise>
							         <td width="20%" align="left">
							           <input name="isChecked" id="isChecked_${vList.POST_NO}" value="${vList.POST_NO}" type="checkbox" style="border:0px"/>					              		
							             ${vList.POST_NAME}
							          </td>		  							
				  				</c:otherwise>
					      </c:choose>	
						</c:forEach>
		    	</table>
		    </dd>
		  </dl> 
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!--保存-->
								<spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close"><!--取消-->
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
