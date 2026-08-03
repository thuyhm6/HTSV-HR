<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent" layoutH="10">
		<div class="pageFormContent nowrap">
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.PEIXUNJIGOUMINGCHENG.a"/><!--培训机构名称--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.ORGAN_NAME }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.LIANXIREN.a"/> <!--联系人
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.LINKMAN }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS"/><!--地址
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.ADDRESS }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewHire.title.OFFICE_PHONE"/><!--办公室电话
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.OFFICE_PHONE }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hrm.empinfo.MOBILE_TELEPHONE"/><!--移动电话
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.CELLPHONE }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="pa.ins.alert.message.exportdata.netAddress"/><!--网址
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.URL_NET }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.ZHUYINGLINGYU.a"/><!--主营领域
			--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.MAIN_FIELD }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOQINGKUANGJIPINGJIA.a"/><!--合作情况及评价
		--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.WORK_TOGETHER }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.JIGOUJIANJIE.a"/><!--机构简介
		--></td>
		<td class="td_type"  width="20%">
		<span>${trainOrganInfo.ORGAN_ABSTRACT }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOXIEYI.a"/><!--合作协议
		--></td>
		<td class="td_type"  width="20%">
		 <c:forEach items="${trainOrganInfo.fileList}" var="item" varStatus="i">
			<span style="color:blue;">${i.count }.</span>
			<a style="color:blue;" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}&nbsp&nbsp</a>
		    </c:forEach>
		</td>
		</tr>
		</table>
		</div>
</div>
