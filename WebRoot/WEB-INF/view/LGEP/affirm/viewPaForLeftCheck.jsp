<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
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

<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>


<script type="text/javascript">
function subCheck(){
	var essCheckNo = $('#ESS_CHECK_NO').attr("value");
	var checkContent = $('#CHECK_CONTENT').attr("value");
	if(essCheckNo == ''){
		//Check信息出错，不能进行Check操作，请联系管理员!
		alertMsg.error("Check信息出错，不能进行Check操作，请联系管理员!");
		return false;
	}
	if(checkContent == ''){
		//Check内容不能为空，请填写Check内容!
		alertMsg.error("Check内容不能为空，请填写Check内容!");
		return false;
	}
	 


	var pro_flag = $("#pro_flag").val();
	 
	 if(pro_flag == 0){
		  
		$("#pro_flag").val("1");
				$.ajax({
					type:'POST',
					url:$("#checkApplyInfo").attr("action"),
					data:$("#checkApplyInfo").serializeArray(),
					dataType:"json",
					cache: false,
					success: function(json){
			 
					if (json.result == 1){
						
						alert(json.message);
					 
						  window.location.reload();
					}else{
						alert(json.message);
						$("#pro_flag").val("0");
					}
				}
				});
	
	 

	 }else{
			alert("check处理中，请稍后");

		 }
		 
		 return false;
	}
function closeWindow(){
    window.close();
}
</script>
</head>
<body>

<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
			 	<th width="3%" style="text-align:center">序号</th>
				<th width="10%" style="text-align:center">社号/姓名</th>
				<th width="20%" style="text-align:center">部门</th>
				<th width="8%" style="text-align:center">发放月份</th>
				<th width="8%" style="text-align:center">补发月份</th>
				
				<th width="6%" style="text-align:center">补发类别</th>
				<th width="15%" style="text-align:center">补发项目</th>
				<th width="5%" style="text-align:center">金额</th>
				<th width="20%" style="text-align:center">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paForLeftDetailList}" var="detail" varStatus="i">			
				<tr>
					<td class='td_center'>${i.index+1 }</td>
					<td class='td_center'>(${detail.EMPID})${detail.LOCAL_NAME}</td>
					<td class='td_left'>${detail.DEPARTMENT }</td>
					<td class='td_center'>${detail.PA_MONTH }</td>
					<td class='td_center'>${detail.PA_MONTH_FOR }</td>
					
					<td class='td_center'>
						<c:if test="${detail.ITEM_TYPE eq 'PA' }">薪资</c:if>
						<c:if test="${detail.ITEM_TYPE eq 'IS' }">保险</c:if>
					</td>
					<td class='td_center'>${detail.ITEM_NAME }</td>
					<td class='td_right'>${detail.ITEM_DATA }</td>
					<td class='td_right'>${detail.REMARK }</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/affirmApply/viewFullApplyAffirmorList?APPLY_NO=${infoApplyOt.APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<div class="pageContent"   >
	<form id="checkApplyInfo" method="post" action="/LGEP/affirm/checkLGEPInfoOT" class="pageForm required-validate" onsubmit="return subCheck()">
		<table class="table" width="100%" border="1">
			<tr><td class="td_title" style="text-align: left" colspan="6"><font><b>&nbsp;申请信息&nbsp;</b></font></td></tr>
			<tr>
				<td class="td_title" style="text-align: center">申请人</td>
				<td class="td_type"  style="text-align: center" colspan="2">(${paForLeftMap.EMPID})${paForLeftMap.LOCAL_NAME}</td>
				<td class="td_title" style="text-align: center">申请时间</td>
				<td class="td_type"  style="text-align: center" colspan="2">${paForLeftMap.CREATE_DATE }</td>
			</tr>				
			<tr>
				<td class="td_title" style="text-align: center">申请内容</td>
				<td class="td_type" colspan="5">${paForLeftMap.APPLY_CONTENT }</td>
			</tr>
			<tr>
				<c:if test="${affirmorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<c:if test="${affirmorListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
					决裁等级
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
					决裁者
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
					决裁情况
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
					决裁批注
				</td>
			</tr>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
					<td class="td_type" width="15%" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
							<!--<font color="blue">未决裁</font>-->
							未决裁
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
					<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			
			<tr>
				<td class="td_title" colspan="6"><br/></td>
			</tr>
			<tr>
				<c:if test="${checkorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkorListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${3 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" width="30%" style="text-align: center" colspan="2"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" width="45%" style="text-align: center" colspan="2"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
							&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
						<c:if test="${checkor.AFFIRM_FLAG == 0}">
							未决裁
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 1}">
							已通过
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 2}">
							已否决
						</c:if>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">
							未Check
						</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">
							已Check
						</c:if>
					</td>
				</tr>	
				<tr>
					<td class="td_type" width="30%" colspan="2">
						<textarea name="affirmRemark" cols="75" rows="2" disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
					</td>
					<td class="td_type" width="45%" colspan="2">
						<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
						<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }"/>
					 	input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
						<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="31" />
						<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${infoApplyOt.APPLY_NO}" />
						 
						<c:if test="${checkor.ESS_CHECK_NO eq essCheckNo}">
							[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
						</c:if>
						<c:if test="${checkor.ESS_CHECK_NO ne essCheckNo}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>			
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
				<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
			</c:if>
		</table>
		<div class="formBar">
			<ul>
				<li>
					<c:forEach items="${checkorList}" var="checkor" varStatus="i">
						<c:if test="${ checkor.CURRENT_CHECKOR_ID ne checkor.CHECKOR_ID  &&  checkor.CHECK_FLAG ne '1'}">
							<div class="button">
								<div class="buttonContent"><!--提交-->
									<button type="submit">提交</button>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</li>
			</ul>
		</div>
	</form>
</div>
</body>
</html>