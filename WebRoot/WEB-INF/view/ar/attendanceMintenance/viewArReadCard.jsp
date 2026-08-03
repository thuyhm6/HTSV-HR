<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
function f_viewArReadCard(callback, filename){
	
	if (confirm ("<spring:message code='ar.alert.message.makesuresubmit'/>" + filename + "<spring:message code='ar.alert.message.data'/>")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceMintenance/getCardInterfacedata',
			data: [{ name: 'jsonData', value: filename }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}

function f_readArReadCardFile(callback, filename){
	
	if (confirm ("确定读取刷卡文件？")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceMintenance/getCardInterfaceFile',
			data: [{ name: 'jsonData', value: filename }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}
</script>

<div class="pageHeader">
	<form id="viewarreadcardrecord" name="viewarreadcardrecord" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArReadCard" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td>
						<input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>
					</td>
					<td><!-- 部门 --><spring:message code="public.title.deptName"/>:</td>
					<td>
						<ait:deptTree name="seach_deptNO" limit="ar" selected="${deptNO}"/>
					</td>
					<td><!-- 开始日期 --><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>:</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME" value="${STIME}" class="date"
										yearstart="-20" yearend="20" readonly="true" />
					</td>
					<td><!-- 结束日期 --><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>:</td>
					<td>
						<input type="text" id="seach_RTIME" name="seach_RTIME" value="${RTIME}" class="date"
										yearstart="-20" yearend="20" readonly="true" />
					</td>
					<td><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/>:</td>
					<td>
						<select id="seach_DoorType" name="seach_DoorType">
							<option value=""><!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
							<option value="IN" <c:if test="${DoorType eq 'IN'}">selected</c:if>><!-- 进门 --><spring:message code="ar.viewarcardrecord.title.jinmen"/></option>
							<option value="OUT" <c:if test="${DoorType eq 'OUT'}">selected</c:if>><!-- 出门 --><spring:message code="ar.viewarcardrecord.title.chumen"/></option>
						</select>
					</td>
					<td><!-- 数据来源 --><spring:message code="ar.viewarcardrecord.title.shujulaiyuan"/>:</td>
					<td>
						<select id="seach_RecordSource" name="seach_RecordSource">
							<option value=""><!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
							<option value="H" <c:if test="${RecordSource eq 'H'}">selected</c:if>><!-- 手动 --><spring:message code="ar.viewarcardrecord.title.shoudong"/></option>
							<option value="M" <c:if test="${RecordSource eq 'M'}">selected</c:if>><!-- 自动 --><spring:message code="ar.viewarcardrecord.title.zidong"/></option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="panelBar">
	<ul class="toolBar">
		
	</ul>
</div>

<div layoutH="121" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
    <ul class="tree treeFolder">
    	<li><a href="#" onclick="javascript:f_readArReadCardFile(navTabAjaxDone,'${list}');"><!-- 刷卡接口数据 --><spring:message code="ar.viewreadrecord.title.cardinterfacedata"/></a>
			<ul>
				<c:forEach items="${nameList}" var="list" varStatus="i">
					<li><a href="#" onclick="javascript:f_viewArReadCard(navTabAjaxDone,'${list}');">${list} <!-- 读取 --><spring:message code="ar.viewreadrecord.title.read"/></a></li>
				</c:forEach>
			</ul>
		</li>
     </ul>
</div>

<div class="pageContent">
	
	<table class="table" width="70%" layoutH="141">
		<thead>
			<tr>
				<th width="10"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="10"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="20"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="20"><!-- 时间 --><spring:message code="ar.viewarcardrecord.title.shijian"/></th>
				<th width="10"><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/></th>
				<th width="10"><!-- 数据来源 --><spring:message code="ar.viewarcardrecord.title.shujulaiyuan"/></th>
				<th width="20"><!-- 备注 --><spring:message code="ar.viewarcardrecord.title.beizhu"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${getArCardRecordList}" var="list" varStatus="i">
			
				<tr target="RECORD_NO" rel="${list.RECORD_NO}">
					<td width="10">${list.EMPID}</td>
					<td width="10">${list.LOCAL_NAME}</td>
					<td width="20">${list.DEPTNAME}</td>
					<td width="20">${list.R_TIME}</td>
					<td>${list.DOOR_TYPE}</td>
					<td>
						<c:if test="${list.INSERT_BY eq 'M'}"><!-- 自动 --><spring:message code="ar.viewarcardrecord.title.zidong"/></c:if>
						<c:if test="${list.INSERT_BY eq 'H'}"><!-- 手动 --><spring:message code="ar.viewarcardrecord.title.shoudong"/></c:if>
					</td>
					<td width="20">${list.REMARK}</td>
				</tr>
			</c:forEach>
			
			<input type="hidden" id="defaultCpny" name="defaultCpny" value="${defaultCpny}" />
		</tbody>
	</table>
</div>
<c:set value="/ar/attendanceMintenance/viewArReadCard" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>