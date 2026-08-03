<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>LGE CHRS2.0</title>
<link href="/resources/css/dwzUI/core.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<link href="/resources/css/dwzUI/themes/lge/style.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/sso_style.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!-- 
<script src="/resources/js/dwzUI/speedup.js" type="text/javascript"></script>
 -->
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.validate.method.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.navTab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.tab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.ajax.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/dwz.regional.zh.js" type="text/javascript"></script>
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
<form id="addLeaveAffirmInfo" method="post"	action="/LGEP/affirm/checkLeave" 
		class="pageForm required-validate" onsubmit="return submitAffirmPreEss0242();">
<c:if test="${RecordInfo.BATCH_YN eq 'Y'}">
<div class="pageContent">
	<table class="table" width="100%">
		<thead>
			<tr>
				<th>NO</th>
				<th>社号</th>
				<th>姓名</th>
				<th>本月申请次数</th>
				<th>考勤日期</th>
				<th>打卡时间</th>
				<th>进出门类型</th>
				<th>备注</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arMacBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${i.count}</td>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.MAC_APPLY_CNT}</td>
					<td class='td_center'>${item.APPLY_DATE}</td>
					<td class='td_center'>${item.APPLY_TIME}</td>
					<td class='td_center'>${item.DOOR_TYPE}</td>
					<td style="text-align:left">${item.REMARK}</td>
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

<c:if test="${RecordInfo.BATCH_YN eq 'N'}">
<div class="pageContent">
	<table class="user_table" width="99%" >
						<tr>
							<td class="td_title" style="text-align: right;width:7%;">申请信息</td>
							<td colspan="6">
								<table class="user_table" width="100%" border="0">
									<tr>
										<td class="td_title" style="text-align: center">申请人</td>
										<td colspan="7">${RecordInfo.CREATED_BY}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请日期</td>
										<td colspan="7">${RecordInfo.CREATE_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
										<td style="width:12%;">[${RecordInfo.EMPID}]${RecordInfo.LOCAL_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">部门</td>
										<td style="width:12%;">${RecordInfo.DEPT_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">漏刷卡时间</td>
										<td style="width:12%;">${RecordInfo.R_TIME }</td>
										<td class="td_title" style="text-align: center;width:12%;">漏刷卡类型</td>
										<td style="width:12%;">${RecordInfo.DOOR_TYPE}</td>
									</tr>
									
									<tr>
										<c:if test="${RecordInfo.CPNY_ID eq 'LGEPN'}">
										  	<td class="td_title" style="text-align: center">漏刷卡类型</td>
											<td>${RecordInfo.RECORD_TYPE }</td>	
										  	<td class="td_title" style="text-align: center">本月申请次数</td>
											<td>${RecordInfo.MAC_APPLY_CNT }</td>	
									     	<td class="td_title" style="text-align: center"  > 
														 申请事由
											</td>
											<td class="td_type"  style="text-align: left;" > 
														 ${RecordInfo.REMARK }
											</td>
										</c:if>
										<c:if test="${RecordInfo.CPNY_ID ne 'LGEPN'}">
										  	<td class="td_title" style="text-align: center">本月申请次数</td>
											<td>${RecordInfo.MAC_APPLY_CNT }</td>	
									     	<td class="td_title" style="text-align: center"  > 
														 申请事由
											</td>
											<td class="td_type"  style="text-align: left;"  colspan="3"> 
														 ${RecordInfo.REMARK }
											</td>
										</c:if>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">附件</td>
										<td colspan="7">
											<c:forEach items="${RecordInfo.fileList}" var="file" varStatus="j">	
											<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
											</c:forEach>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				</table>
</div>
</c:if>
<div class="pageContent">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table">
				<tr>
				<c:if test="${affirmListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmListCnt+1 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<c:if test="${affirmListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${2 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<td class="td_title" width="7%" style="text-align: center"><!-- 审批等级 -->
					审批等级
				</td>
				<td class="td_title" width="20%" style="text-align: center"><!-- 审批者 -->
					审批者
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 审批情况 -->
					审批情况
				</td>
				<td class="td_title" width="18%" style="text-align: center"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" width="30%" style="text-align: center"><!-- 审批批注 -->
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
					<c:if test="${fn:length(checkorList) > 0}">
						<td class="td_title" style="text-align: center" rowspan="${fn:length(checkorList)*2+1 }">Review</td>
					</c:if>
					<c:if test="${fn:length(checkorList) == 0}">
						<td class="td_title" style="text-align: center" rowspan="${3}">Review</td>
					</c:if>
					<td class="td_title" style="text-align: center">Type</td>
					<td class="td_title" style="text-align: center" colspan="2">Requests</td>
					<td class="td_title" style="text-align: center" colspan="2">Reviewed</td>
				</tr>
				
				<c:forEach items="${checkorList}" var="CHECK" varStatus="i">
			  		<c:if test="${CHECK.CHECKOR_ID eq PERSON_ID && CHECK.AFFIRM_FLAG eq '0'}">
			  			<c:set var="CHECK_FLAGS" value="1"/>
			  			<input type="hidden" id="CHECK_FLAGS" value="1">
					</c:if>
			  	</c:forEach>
				<c:forEach items="${checkorList}" var="checkor" varStatus="i">
					<tr>
						<td class="td_type" style="text-align: center">Public</td>
						<td class="td_type" style="text-align: left" colspan="2">
							[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
							&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br />
							[Request]：${checkor.CHECK_REASON}
						</td>
						<td class="td_type" style="text-align: left" colspan="2">
							[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
							&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
							<c:if test="${checkor.CHECK_FLAG == 0}">未Check</c:if>
							<c:if test="${checkor.CHECK_FLAG == 1}">已Check</c:if><br />
							<c:if test="${checkor.ESS_CHECK_NO ne essCheckNo}">
								[Check]：${checkor.CHECK_CONTENT}
							</c:if>
							<c:if test="${checkor.ESS_CHECK_NO eq essCheckNo}">
								<!-- 隐藏的一些参数 -->
								<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${RecordInfo.RECORD_NO}"/>
								<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID}"/>
								<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
								<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="218294" />
								<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }" />
								[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(checkorList) == 0}">
					<tr>
						<td class="td_type" style="text-align: center">Public</td>
						<td class="td_type" style="text-align: center" colspan="2">&nbsp;</td>
						<td class="td_type" style="text-align: center" colspan="2">&nbsp;</td>
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