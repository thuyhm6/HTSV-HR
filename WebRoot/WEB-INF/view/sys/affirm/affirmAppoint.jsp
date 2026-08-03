<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function ar_appoint(){
	var seach_AFFIRMOR_NAME=document.getElementById("seach_AFFIRMOR_NAME").value;
	var seach_APPOINT_NAME=document.getElementById("seach_APPOINT_NAME").value;
    alert(seach_APPOINT_NAME);
    	if (seach_AFFIRMOR_NAME==''||seach_APPOINT_NAME==''){
    		alert("请输入委任者或被委任者");
			return false;
    	}
    	 $.ajax( {
				type : 'post',
				cache : false,
				contentType : 'application/json',
				url : '/sys/affirm/updateaffirmAppoints?seach_AFFIRMOR_NAME=' + seach_AFFIRMOR_NAME + '&seach_APPOINT_NAME='+seach_APPOINT_NAME,
			    dataType : "json",
				success : function() {
                alert("委任成功");
				}
			    });
						
		
		}
function search(){
	var seach_AFFIRMOR_NAME_BYNAME=document.getElementById("seach_AFFIRMOR_NAME_BYNAME").value;
	alert(seach_AFFIRMOR_NAME_BYNAME);
	 $.ajax( {
			type : 'post',
			cache : false,
			contentType : 'application/json',
			url : '/sys/affirm/affirmAppoint?seach_AFFIRMOR_NAME_BYNAME=' + seach_AFFIRMOR_NAME_BYNAME,
		    dataType : "json",
			success : function() {
            alert("委任成功");
			}
		    });
					
	
	}
function searchempid(){
	var seach_AFFIRMOR_NAME_BYNAME = $("#seach_AFFIRMOR_NAME_BYNAME").val();
	alert(seach_AFFIRMOR_NAME_BYNAME);
	$("#seach_AFFIRMOR_NAME_BYNAME").attr("value",seach_AFFIRMOR_NAME_BYNAME);
	navTabSearch(document.affirmAppoint);
}

 function cancleAppoint(AFFIRMOR_NAME,APPOINT_NAME){
	 var params = [];
		params.push({
			name: 'AFFIRMOR_NAME',
			value: AFFIRMOR_NAME
		});
		params.push({
			name: 'APPOINT_NAME',
			value: APPOINT_NAME
		});
		if (confirm ("确定要取消吗?")){	  
			$.ajax({
			  url: '/sys/affirm/cancleAppoint',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.affirmAppoint);
				}else{
					alert("取消失败！");
				}
			  }
			});
		}
	 }
 
</script>
<div class="pageHeader">
	<form id='affirmAppoint' name='affirmAppoint' onsubmit="return navTabSearch(this);" action="/sys/affirm/affirmAppoint" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
					<spring:message code="sys.affirm.title.affirmPerson"/><!--决裁者-->：&nbsp;&nbsp;    
					<!--<input type="hidden" name="seach_AFFIRMOR_ID" id="seach_AFFIRMOR_ID" value="${AFFIRMOR_ID}"/>-->
					<input type="text" name="seach_AFFIRMOR_NAME" id="seach_AFFIRMOR_NAME" value="${AFFIRMOR_NAME}" /> 
				 <!-- <input type="text" name="seach_EMPID" id="seach_EMPID" value="${EMPID}" onKeyUp="submitKeyClick_sy0130_view(this,'','','${request_navTabId}',event)"/>  
				    <a id="onck" name="onck"  href="" lookupGroup="person"></a> --> 
				         被委任者<!--被决裁部门-->&nbsp;&nbsp;
				          <input type="text" name="seach_APPOINT_NAME" id="seach_APPOINT_NAME" value="${EMP_ID}"/> 
	              </td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button onclick="ar_appoint()">委任<!--检索--></button>
					    </div></div>
					</li>
				</ul>
			</div>
		</div>
		<div class="searchBar">
	<tr>
	<td>
	<spring:message code="sys.affirm.title.affirmPerson"/><!--决裁者-->：&nbsp;&nbsp;    
    <input type="text" name="seach_AFFIRMOR_NAME_BYNAME" id="seach_AFFIRMOR_NAME_BYNAME" value="${AFFIRMOR_NAME_S}" /> 
    
  <div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit"><spring:message code="public.title.search"/><!--检索--></button>
					    </div></div>
					</li>
				</ul>
			</div>
	</td>
	</tr>
	</div>
	</form>	
</div>
	
<div class="pageContent">
	
	<table class="table" style="overflow: auto" width="100%"  layoutH="206" nowrapTD="false">
			<thead>
			<tr>
				<th >委任者</th>
				<th >被委任者</th>
				<th >委任时间</th>
				<th >取消委任</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${affirmList}" var="affirmList" varStatus="i">
			    <tr>
				<td >${affirmList.EMPID}</td>
				<td >${affirmList.WAS_APPOINT_EMPID}</td>
				<td >${affirmList.APPOINT_DATE}</td>
				<td ><a href="#" title="取消" onclick="cancleAppoint('${affirmList.EMPID}','${affirmList.WAS_APPOINT_EMPID}')" style="cursor: hand">
									<font color="red">取消</font>
								</a></td>
		    	</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
	 
	<c:set value="/sys/affirm/viewAffirmList" var="pageUrl"/>
	 <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>