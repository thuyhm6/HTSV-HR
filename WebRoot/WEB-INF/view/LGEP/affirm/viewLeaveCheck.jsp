<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>LGE CHRS2.0</title>
<link href="/resources/css/dwzUI/core.css" rel="stylesheet"
	type="text/css" />


<link href="/resources/css/dwzUI/uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/style.css" rel="stylesheet"
	type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/sso_style.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<!-- 
<script src="/resources/js/dwzUI/speedup.js" type="text/javascript"></script>
 -->

<!-- jquery -->
<script src="/resources/js/dwzUI/dwz.min.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script type="text/javascript">
function submitAffirmPreEss0242(){
	var pro_flag = $("#pro_flag").val();
	if(pro_flag == 0){
		$("#pro_flag").val("1");
		$.ajax({
			type:$("#addLeaveAffirmInfo").method || 'POST',
			url:$("#addLeaveAffirmInfo").attr("action"),
			data:$("#addLeaveAffirmInfo").serializeArray(),
			dataType:"json",
			cache: false,
			success: function(json){
				if (json.statusCode == 200){
					alert(json.message);
					window.location.href=window.location.href;
				}else{
					alert(json.message);
					$("#pro_flag").val("0");
				}
			}
		});
	}else{
		alert("审批处理中，请稍等。。。");
	}
	return false;
}
</script>
</head>
<body>
<form id="addLeaveAffirmInfo" method="post" action="/LGEP/affirm/checkLeave" class="pageForm required-validate" onsubmit="return submitAffirmPreEss0242();">
<c:if test="${infoApplyLeave.APPLY_TYPE eq 'BATCH'}">
<div class="pageContent">
	<table class="table" width="100%">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>Leave开始时间</th>
				<th>Leave结束时间</th>
				<th>Leave时长</th>
				<th>Leave类型</th>
				<th>总天数</th>
				<th>已使用天数</th>
				<th>剩余天数</th>
				<th>Leave原因</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${leaveBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.LEAVE_FROM_TIME}</td>
					<td class='td_center'>${item.LEAVE_TO_TIME}</td>
					<td class='td_center'>${item.APPLY_LENGTH_DISPLAY}</td>
					<td class='td_center'>${item.LEAVE_TYPE}</td>
					<td class='td_center'>${item.LEAVE_TOTAL}</td>
					<td class='td_center'>${item.LEAVE_USE}</td>
					<td class='td_center'>${item.LEAVE_SURPLUS}</td>
					<td style="text-align:left">${item.LEAVE_REASON}</td>
					<td style="text-align:left">
						<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
						</c:forEach>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>
</c:if>

<c:if test="${infoApplyLeave.APPLY_TYPE eq 'PERSON'}">
<div class="pageContent">
	<div class="pageFormContent">
			<table class="user_table" layoutH="60" width="100%">
						<tr>
							<td class="td_title" style="text-align: right;width:7%;">申请信息</td>
							<td colspan="6">
								<table class="user_table" width="100%" border="0">
									<tr>
										<td class="td_title" style="text-align: center">申请人</td>
										<td colspan="7">[${infoApplyLeave.EMPID_C}]${infoApplyLeave.LOCAL_NAME_C}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请日期</td>
										<td colspan="7">${infoApplyLeave.CREATE_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
										<td style="width:12%;">[${infoApplyLeave.EMPID}]${infoApplyLeave.LOCAL_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">开始日期时间</td>
										<td style="width:12%;">${infoApplyLeave.LEAVE_FROM_TIME}&nbsp;${infoApplyLeave.FROMTIME}</td>
										<td class="td_title" style="text-align: center;width:12%;">结束日期时间</td>
										<td style="width:12%;">${infoApplyLeave.LEAVE_TO_TIME}&nbsp;${infoApplyLeave.TOTIME}</td>
										<td class="td_title" style="text-align: center;width:12%;">Leave时长</td>
										<td style="width:12%;">${infoApplyLeave.APPLY_LENGTH_DISPLAY}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center;width:12%;">考勤类型</td>
										<td style="width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}</td>
										<td class="td_title" style="text-align: center">Leave|销假</td>
										<td>
											<c:if test="${infoApplyLeave.APPLY_TYPE_NAME eq '销假' }">销假</c:if>
											<c:if test="${infoApplyLeave.APPLY_TYPE_NAME ne '销假' }">Leave</c:if>
										</td>
										<td class="td_title" style="text-align: center">附件</td>
										<td>
											<c:forEach items="${infoApplyLeave.fileList}" var="file" varStatus="j">	
												<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
											</c:forEach>
										</td>
										<td class="td_title" style="text-align: center">申请事由</td>
										<td>${infoApplyLeave.LEAVE_REASON}</td>
									</tr>
									<c:if test="${infoApplyLeave.LEAVE_TYPE_CODE eq '26' or infoApplyLeave.LEAVE_TYPE_CODE eq '18135'}">
										<tr>
										<td class="td_title" style="text-align: center;width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}总天数</td>
										<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_TOTAL}</td>
										<td class="td_title" style="text-align: center">已使用${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
										<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_USE}</td>
										<td class="td_title" style="text-align: center">剩余${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
										<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_SURPLUS}</td>
										<td class="td_title" style="text-align: center"></td>
										<td  class="td_type" style="text-align:left;width:12%;"></td>
									</tr>
									</c:if>
								</table>
							</td>
						</tr>
				</table>
			</div>
		</div>
</c:if>
<div class="pageContent" >
	<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<c:if test="${affirmorListCnt > 0}">
					<td class="td_title" style="text-align: right;width:7%;" rowspan="${affirmorListCnt+1 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<c:if test="${affirmorListCnt == 0}">
					<td class="td_title" style="text-align: center;width:7%;" rowspan="${2 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<td class="td_title" style="text-align: center;width:8%;"><!-- 审批等级 -->
					审批等级
				</td>
				<td class="td_title" style="text-align: center;width:15%;"><!-- 审批者 -->
					审批者
				</td>
				<td class="td_title" style="text-align: center;width:15%;"><!-- 审批情况 -->
					审批情况
				</td>
				<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" style="text-align: center;width:40%;"><!-- 审批批注 -->
					审批批注
				</td>
			</tr>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
				<tr>
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
					<td class="td_type" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
							<!--<font color="blue">未审批</font>-->
							未审批
						</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
							<!--<font color="green">已通过</font>-->
							已通过
						</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
							<!--<font color="red">已否决</font>-->
							已否决
						</c:if>
					</td>
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			<tr>
				<c:if test="${checkorListCnt > 0}">
					<td class="td_title" style="text-align: right" rowspan="${checkorListCnt+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkorListCnt == 0}">
					<td class="td_title" style="text-align: right" rowspan="${2 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<td class="td_title" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" style="text-align: center" colspan="3"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" style="text-align: center"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type"style="text-align: center">Public</td>
					<td class="td_type"colspan="3">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_CREATE_DATE}&nbsp;<br/>
						[Request]：${checkor.CHECK_REASON}
					</td>
					<td class="td_type">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;&nbsp;/&nbsp;
										<c:if test="${checkor.CHECK_FLAG eq '0'}">
											未check 
										</c:if>
										<c:if test="${checkor.CHECK_FLAG ne '0'}">
										${checkor.CHECKED_DATE }
										</c:if><br/>
						<c:if test="${checkor.ESS_CHECK_NO eq essCheckNo}">
							[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
							<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
							<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
							<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
							<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="21" />
							<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${infoApplyLeave.APPLY_NO}" />
						</c:if>
						<c:if test="${checkor.ESS_CHECK_NO ne essCheckNo}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>	
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
					<td class="td_type">Public</td>
					<td class="td_type" colspan="3">无</td>
					<td class="td_type">无</td>
				</tr>
			</c:if>
		</table>
		<c:if test="${essCheckNo ne '' }">
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="submit">
									提交
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</c:if>
	</div>
	</form>
</body>
</html>