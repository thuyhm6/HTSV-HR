<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//保存
	$("#viewAddTempEmp_save",$.pdialog.getCurrent()).click(function(){
		var ITEM_NO_1 = $("#ITEM_NO_1",$.pdialog.getCurrent()).val();
		var ITEM_NO_2 = $("#ITEM_NO_2",$.pdialog.getCurrent()).val();
		var ITEM_LEN_1 = parseInt($("#ITEM_LEN_1",$.pdialog.getCurrent()).val());
		var ITEM_LEN_2 = parseInt($("#ITEM_LEN_2",$.pdialog.getCurrent()).val());
		var index = $("#index",$.pdialog.getCurrent()).val();
		var sysWeek = $("#sysWeek",$.pdialog.getCurrent()).val();
		var panelType = '${panelType}';
		var $form = $("#viewAddTempEmp_form",$.pdialog.getCurrent());
		if (!$form.valid()) {
			return false;
		}
		//时长限制
		var timeLimit = parseInt('${timeLimit}');
		if(ITEM_LEN_1 + ITEM_LEN_2 > timeLimit){
			alertMsg.warn("总时长不能超过${timeLimit}小时");
			return false;
		}

		if(panelType == 'dialog'){
			$("#ITEM_NO_" + sysWeek + "_" + index).attr("sysItem1",ITEM_NO_1);
			$("#ITEM_NO_" + sysWeek + "_" + index).attr("sysItem2",ITEM_NO_2);
			$("#ITEM_NO_" + sysWeek + "_" + index).attr("sysLen1",ITEM_LEN_1);
			$("#ITEM_NO_" + sysWeek + "_" + index).attr("sysLen2",ITEM_LEN_2);

			$("#ITEM_NO_" + sysWeek + "_" + index).html($("#ITEM_NO_1 option:selected",$.pdialog.getCurrent()).text() + " " + ITEM_LEN_1 + "<br/>" +
					$("#ITEM_NO_2 option:selected",$.pdialog.getCurrent()).text() + " " + ITEM_LEN_2);
		}else{
			$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).attr("sysItem1",ITEM_NO_1);
			$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).attr("sysItem2",ITEM_NO_2);
			$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).attr("sysLen1",ITEM_LEN_1);
			$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).attr("sysLen2",ITEM_LEN_2);

			$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html($("#ITEM_NO_1 option:selected",$.pdialog.getCurrent()).text() + " " + ITEM_LEN_1 + "<br/>" +
					$("#ITEM_NO_2 option:selected",$.pdialog.getCurrent()).text() + " " + ITEM_LEN_2);
		}

		$.pdialog.closeCurrent();
	});
});
</script>
	<div>
		<form id="viewAddTempEmp_form" method="post" action="/ess/tempEmp/addTempEmp">
			<div>
				<table  class="user_table" width="100%">
					<tr>
						<td width="15%" style="text-align:right" class="td_title">
							考勤状态1
						</td>
						<td width="35%" class="td_type">
							<select id="ITEM_NO_1">
								<c:forEach items="${shiftNoItem}" var="item" varStatus="i">
								<option value="${item.CODENAME }">${item.CODENAME }</option>
								</c:forEach>
							</select>
						</td>
						<td width="15%" style="text-align:right" class="td_title">
							时长
						</td>
						<td width="35%" class="td_type">
							<input type="text" id="ITEM_LEN_1" name="ITEM_LEN_1" value="4" class="required number" size="15" />
						</td>
					</tr>
					<tr>
						<td width="15%" style="text-align:right" class="td_title">
							考勤状态2
						</td>
						<td width="35%" class="td_type">
							<select id="ITEM_NO_2">
								<c:forEach items="${shiftNoItem}" var="item" varStatus="i">
								<option value="${item.CODENAME }">${item.CODENAME }</option>
								</c:forEach>
							</select>
						</td>
						<td width="15%" style="text-align:right" class="td_title">
							时长
						</td>
						<td width="35%" class="td_type">
							<input type="text" id="ITEM_LEN_2" name="ITEM_LEN_2" value="4" class="required number" size="15" />
							<input type="hidden" id="index" name="index" value="${index }"/>
							<input type="hidden" id="sysWeek" name="sysWeek" value="${sysWeek }"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" id="viewAddTempEmp_save">
									保存
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
	</div>
