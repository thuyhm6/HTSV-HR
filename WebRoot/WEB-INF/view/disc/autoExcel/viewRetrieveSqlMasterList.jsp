<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
function f_viewardetail_update() {

	document.searchArDetailForm.action = '/disc/autoExcel/viewUpdateSqlParamList?SQL_SEQ={sid}';
	navTabSearch("searchArDetailForm");
	document.getElementById("seach_condition").value = "999999";
	layer = document.getElementById("viewlist");
	alert(layer.innerHTML);
	layer.innerHTML = "999999";
	alert('22');
}
function delAndCallback(form, callback) {
	var $form = null;
	if ($('#' + form).length > 0)
		$form = $('#' + form);
	else
		$form = $(form);

	if (!$form.valid()) {
		return false;
	}

	$form.attr("action", "/disc/autoExcel/deleteSqlMaster?SQL_SEQ={sid}");
	if (confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />")) {//确定要删除吗?
		$.ajax( {
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : function(data) { //请求成功后处理函数。
				if (data.statusCode == "200") {
					navTabSearch("viewAnnualadjustmentInfo");
					alertMsg.correct(data.message);
				} else {
					if (data.result == "2") {
						alertMsg.info(data.message);
					} else {
						alertMsg.error(data.message);
					}
				}
			},
			error : DWZ.ajaxError
		});
	}
	return false;
}
</script>
<div class="pageHeader">
	<!-- 298976 -->
	<form onsubmit="return navTabSearch(this);"
		action="/disc/autoExcel/viewRetrieveSqlMasterList?firstFlag=N&pageNum=1&menuNo=${menuNo}&navTabId=disc0101"
		method="post" id="viewRetrieveSqlMasterList"
		name="viewRetrieveSqlMasterList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.salary.canShu.faRen" /><!-- 法人 -->：
					</td>
					<td>
						<input type="hidden" name="PGM_NMurl" id="PGM_NMurl"
							value="${PGM_NMurl}" />
						<input type="hidden" name="PGM_NM" id="PGM_NM" value="${PGM_NM}" />
						<input type="hidden" name="menuNo" id="menuNo" value="${menuNo}" />
						${defaultCpny}
						<input type="hidden" id="SQL_CPNY_SELECT" name="seach_CPNY_ID"
							value="${defaultCpny}" />
					</td>
					<td>
						<spring:message code="hrm.empinfo.REPORT_NUMBER.Z" /><!-- 报表序号 -->：
					</td>
					<td>
						<input type="text" name="seach_SQL_SEQ" id="seach_SQL_SEQ"
							class="textinput" value="${SQL_SEQ}" />
					</td>
					<td>
						<spring:message code="hrm.empinfo.REPORT_NAME.Z" /><!-- 报表名 -->：
					</td>
					<td>
						<input type="text" name="seach_SQL_NMLIKE" id="seach_SQL_NMLIKE"
							class="textinput" value="${SQL_NMLIKE}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<c:if test="${POWER_FOR eq null}">
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search" />
										<!-- 检索 -->
									</button>
								</div>
							</div>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">

	<c:set value="600" var="add_width" />
	<c:set value="600" var="add_height" />
	<c:set value="true" var="add_mask" />
	<!-- 新建报表 -->
	<c:set value="<spring:message code='ar.viewRetrieveSqlMasterList.XINJIANBAOBIAO.b' />" var="add_name" />
	<c:set value="/disc/autoExcel/createSqlMaster?PGM_NMurl=${PGM_NMurl}"
		var="add_Url" />
	<c:set value="dialog" var="edit_tab" />
	<c:set value="600" var="edit_width" />
	<c:set value="600" var="edit_height" />
	<c:set value="true" var="edit_mask" />
	<!-- SQL报表修改 -->
	<c:set value="SQL<spring:message code='ar.viewRetrieveSqlMasterList.BAOBIAOXIUGAI.b' />" var="edit_name" />
	<c:set
		value="/disc/autoExcel/updateSqlMasterTo?SQL_SEQ={sid}&PGM_NMurl=${PGM_NMurl}"
		var="edit_Url" />
	<c:set value="dialog" var="updateparam_tab" />
	<c:set
		value="/disc/autoExcel/viewUpdateSqlParamList?SQL_SEQ={sid}&PGM_NMurl=${PGM_NMurl}"
		var="updateparam_url" />
	<c:set value="ajaxTodo" var="delete_tab" />
	<c:set value="/disc/autoExcel/deleteSqlMaster?SQL_SEQ={sid}"
		var="delete_url" />
	<c:set value="navTab" var="run_tab" />
	<c:set value="600" var="run_width" />
	<c:set value="600" var="run_height" />
	<!-- SQL数据导出 -->
	<c:set value="SQL<spring:message code='ar.viewRetrieveSqlMasterList.SHUJUDAOCHU.b' />" var="run_name" />
	<c:set value="/disc/autoExcel/runSql?SQL_SEQ={sid}" var="run_url" />
	<div class="formBar">
		<ul class="toolBar">
			<!--
			初始化按钮： 同删除按钮
			添加按钮：add_Url 必填项 JS链接地址
				  add_target_exit 判断target是否存在：存在-弹出新页面；不存在：本页提交
				  add_tab target页面打开方式：弹出；链接到navTab;本页
				  add_mask mask 页面下方div显示 
				  add_width 新打开页面宽度
				  add_height 新打开页面高度
				  add_rel panel名字（navTabId）
				  add_name <a> 显示的名字
			删除按钮：delete_Url delete_target_exit delete_tab delete_width delete_height delete_name 同上
				  delete_range 如果是新打开页面 需要赋值
		   	修改按钮：同上
		 -->
			<c:if test="${POWER_FOR eq null}">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
					<c:if test="${add_Url ne '' && add_Url ne null}">

						<li id="addLi">

							<a class="add" href="${add_Url}"
								<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
							target="${add_tab eq '' || add_tab eq null ? 'dialog' :add_tab}" 
						
				
				
					</c:if>
							mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
							width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
							height="${add_height eq '' || add_height eq null ? '400' : add_height}"
							<c:if test="${add_rel ne '' && add_rel ne null}">
								rel="${add_rel}"
							</c:if>
							>
							<span>
								<!-- 新建报表 --><spring:message code='ar.viewRetrieveSqlMasterList.XINJIANBAOBIAO.b' />
							</span>
					</a>
				</li>
			 </c:if>
		</c:if>
		
		<c:if test="${toolbarInfo.UPDATER == '1'}">
			<li id="edit">
				 <a class="update" href="${edit_Url}" target="dialog" width="${edit_width}" height="${edit_height}" mask="true">
					<span><!-- SQL报表修改 -->SQL<spring:message code='ar.viewRetrieveSqlMasterList.BAOBIAOXIUGAI.b' /></span>
				</a>

			</li>
		</c:if>
		<c:if test="${toolbarInfo.UPDATER == '1'}">
			<li id="updateparam">
				 <a class="update" href="${updateparam_url}" target="dialog" width="800" height="570" mask="true">
					<span> SQL<!-- SQL报表参数修改 --><spring:message code='ar.viewRetrieveSqlMasterList.BAOBIAOCANSHUXIUGAI.b' /></span>
				</a>
			</li>

		</c:if>
		</c:if>
			<li id="run">
				<a class="updateparam" href="${run_url}" target="dialog" width="800" height="600"  rel="exceldlog" mask="true">
					<span><!-- 数据导出--><spring:message code='ar.viewRetrieveSqlMasterList.SHUJUDAOCHU.b' /></span>
		   </a>
			</li>
			<c:if test="${POWER_FOR eq null}">
		<c:if test="${toolbarInfo.DELETER == '1'}">
				<li>
				  <a id="deletesql" name="deletesql" class="updateparam"  href="${delete_url}"  title="确定要删除吗?" target="ajaxTodo" >
					<span> <!-- 删除--><spring:message code='ess.empInfo.Delete' /></span> </a>
			</li>
		</c:if>
		</c:if>
		<li class="line">
		</li>
	</ul>
</div>
<form name="delPOvertimeApplyAffirmForm" id="delPOvertimeApplyAffirmForm" method="post" action="/disc/autoExcel/deleteSqlMaster?SQL_SEQ={sid}" 
	  onsubmit="return delAndCallback(this, navTabAjaxDone)"> 
<table class="table" width="100%" layoutH="210">
		<thead>
			<tr> 
			    <th width="3%">
			    	<!-- 序号 --><spring:message code="sys.affirm.indexNum" />
			    </th>
				<th width="7%"><!--法人-->
					<!-- 法人 --><spring:message code="sys.essParam.title.legalPerson" />
				</th>
				<th width="10%"><!--模块-->
					<!-- 模块 --><spring:message code="ar.viewRetrieveSqlMasterList.MOKUAI.b" />
				</th>
				<th width="12%"><!--SQL名称-->
					SQL<!-- 名称 --><spring:message code="ar.viewRetrieveSqlMasterList.MINGCHENG.b" />
				</th>
				<th width="8%"><!--排序号-->
					<!-- 排序号 --><spring:message code="ar.viewRetrieveSqlMasterList.PAIXUHAO.b" />
				</th>
				
				<th width="20%"><!--SQL描述-->
					SQL<!-- 描述 --><spring:message code="ar.viewRetrieveSqlMasterList.MIAOSHU.b" />
				</th>
				<th width="10%"><!--执行-->
					<!-- 启用 --><spring:message code="sys.arAffirmPost.title.able" />
				</th>

			</tr>
		</thead>  
			<tbody>
			<c:forEach items="${showList}" var="isData" varStatus="i">			
				<tr target="sid" rel="${isData.SQL_SEQ}">
					<td style="text-align:center">${isData.SQL_SEQ}</td>
					<td style="text-align:center">${isData.CPNY_ID}</td>
					<td style="text-align:center">${isData.PGM_NM}</td>
					<td style="text-align:left">${isData.SQL_NM}</td>
							<td style="text-align: left">
								${isData.SQL_ORDER_BY_ID}
							</td>
							<td style="text-align: left">
								${isData.SQL_DESC}
							</td>


							<td style="text-align: center">
								<c:if test="${isData.SQL_STAT eq 'Y'}">
									<font color="green"><!-- 启用 --><spring:message code="sys.arAffirmPost.title.able" /></font>
								</c:if>
								<c:if test="${isData.SQL_STAT eq 'N'}">
									<font color="red"><!-- 未启用 --><spring:message code="sys.arAffirmPost.title.enable" /></font>
								</c:if>
							</td>
							</tr>
							</c:forEach>
							</tbody>
							</table>
							</form>
	</div>