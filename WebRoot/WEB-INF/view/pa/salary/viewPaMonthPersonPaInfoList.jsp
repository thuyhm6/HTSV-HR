<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//判断工资是否开放
	function getSalaryDispark() {

		var paMonth = $("#seach_year_ar0106", navTab.getCurrentPanel()).val()
				+ $("#seach_month_ar0106", navTab.getCurrentPanel()).val();
		$
				.ajax({
					cache : false,
					type : 'post',
					async : false,
					url : "/ess/infoView/getSalaryDispark?",
					data : 'paMonth=' + paMonth,
					dataType : "json",
					success : function(data) {
						if (data.no != 0) {
							$("#viewPaMonthPersonPaInfoList",
									navTab.getCurrentPanel()).submit();
						} else {
							alertMsg
									.error('<spring:message code="liang.pa.salary.title.salary_NotDispark"/>');//工资没有开放
						}
					}
				});
		return false;
	}
	
	/**
		 * 禁用textArea以及Input框的enter键的自动提交
		 */
		document.onkeydown = function(event) {  
			  var target, code, tag;  
			  if (!event) {  
			       event = window.event; //针对ie浏览器  
			       target = event.srcElement;  
			       code = event.keyCode;  
			       if (code == 13) {  
			           tag = target.tagName;  
			           if (tag == "TEXTAREA") {
				           return true;
				       }else{ 
					       return false;
					   }  
			       }  
			  }else {  
			       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
			       code = event.keyCode;  
			       if (code == 13) {  
			           tag = target.tagName;  
			           if (tag == "INPUT"){ 
				           return false; 
				       }else {
					        return true;
					   }   
			      }  
			 }  
		}; 
		
		
			var keyCodeInit=0;
		function submitKeyClick_sy0010sp_add(obj,localName,idcardNo,navTabId,event){
		 	var e= event ? event : window.event; 
		 	var keyCode = e.which ? e.which : e.keyCode;
		   	if(keyCode==13){
		   		keyCodeInit=keyCode;
				var empid=obj.value.replace(/[ ]/g,"");
				var empIdStr=obj.id;
				var personIdStr="personId"+empIdStr.substring(5);
				if(empid == ''){
					alert("请填写准确社号！");
				}else{
			   		$.ajax({
						type: 'POST',
						url: encodeURI('/sys/affirmSpecial/getPersonCntByEmpid?EMPID=' + empid),
						dataType:"json",
						cache: false,
						success: function(jsonObject){
									if(jsonObject.perCnt==1){
										document.getElementById(empIdStr).value=jsonObject.empId;
										document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
									}else{
										alert("请填写准确社号！");
									}
								},
						error: DWZ.ajaxError
					});
				}
		    }
		 }
</script>
<div class="pageHeader">
	<form id="viewPaMonthPersonPaInfoList" name="viewPaMonthPersonPaInfoList"
		onsubmit="return navTabSearch(this);"
		action="/pa/salary/viewPaMonthPersonPaInfoList?pageNum=1&numPerPage=0"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 考勤月 --> <spring:message code='ar.excelexport.title.armonth' />:
					</td>

					<td><ait:date yearName="seach_year_ar0106"
							yearSelected="${year_ar0106}" monthName="seach_month_ar0106"
							monthSelected="${month_ar0106}" /></td>

					<td>
						 准确社号:</td>
				    <td> <input id="seach_EMP_ID" name="seach_EMP_ID" type="text" value="${EMP_ID }"/>
<!-- 					<div><input id="EMPID" name="seach_EMP_ID" type="text" value="${EMP_ID }" size="30" alt="请输入准确社号或姓名按回车" -->
<!-- 	  onkeydown="submitKeyClick_sy0010sp_add(this,'','','${request_navTabId}',event)" /> -->
<!-- 		    <input type="hidden" id="personId" name="PERSON_ID" value="${PERSON_ID }">  -->
		    <input type="hidden" id="personId_flag" name="personId_flag" value="1"> 
<!-- 			<a id="onck" name="onck" href="" lookupGroup="person"></a> -->
<!-- 			</div> -->
					</td>

					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="button" onclick="getSalaryDispark();">
												<!-- 查询 -->
												<spring:message code="button.search" />
											</button>
										</div>
									</div></li>

							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">

	<table width='100%'>${dateHrtable}
	</table>
	<table width='100%'>${dateArtable}
	</table>
	<table width='100%'>${datePatable}
	</table>
	<table width='100%'>${dateIstable}
	</table>
	<c:if test="${CPNY_ID ne '' and CPNY_ID ne null}">
	<table width='100%'>

		<tr>
			<td width='100%'><div class='panel'>
					<h1 style='text-align: center'>手工输入项目明细</h1>
					<div>
						<table width='100%' border='1' cellpadding='0' cellspacing='0'
							class='user_table'>
							<tr>
								<td class='td_title' style='text-align: center'>工资项目</td>
								<td class='td_title' style='text-align: center'>详细描述</td>
								<td class='td_title' style='text-align: center'>金额</td>
							</tr>

							<c:forEach items="${paParamDateList}" var="item" varStatus="i">

								<tr>
									<td class='td_type' style='text-align: center'>${item.ITEM_NAME}</td>
									<td class='td_type' style='text-align: center'>${item.REMARK}</td>
									<td class='td_type' style='text-align: center'>${item.RETURN_VALUE}</td>
								</tr>

							</c:forEach>
						</table>
					</div>
				</div></td>
		</tr>
	</table>
	</c:if>
</div>
</br>

