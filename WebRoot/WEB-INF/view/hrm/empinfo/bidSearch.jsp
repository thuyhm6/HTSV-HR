<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var aa = $("#Bidlocalname").val();
	var bb = $("#Bidlocalempid").val();
	var cc = $("#Bidlocalpostgradenoname").val();
	var dd = $("#Bidlocalpostgradeotherinf").val();
	var ff = $("#Bidlocalcenter").val();
	var gg = $("#Bidlocalempofficename").val();
	if (gg != "" && typeof (gg) != "undefined") {
		$('#titlenameBID').html(
				aa + " / " + bb + " / " + cc + "(" + dd + ") / " + ff + " / "
						+ gg);
	}

	var dataSearch = '${dataSearch}';
	if ('${lowerDepart}' == 'Y') {
		$('#lowerDepart_' + dataSearch).attr('checked', 'checked');
		$('#lowerDepart_' + dataSearch).attr('value', 'Y');
	}
	if ('${MAIN_LIAISON_OFFICE}' == 'Y') {
		$('#mainLianluo').attr('checked', 'checked');
	}
	if ('${FINAL_DEGREE_WHETHER}' == 'Y') {
		$('#FINAL_DEGREE_WHETHER').attr('value', 'Y');
		$('#FINAL_DEGREE_WHETHER').attr('checked', 'checked');
	}
});
function fangdajingBID(flag) {
	var name = encodeURI(encodeURI($('#seach_KEYBID').val()));
	var dataSearch = $('#beginSearch').val();
	$('#fangdaBID')
			.attr(
					'href',
					'/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='
							+ dataSearch
							+ '&pageNum=1&firstFlag=N&searchChange=bidSearch&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$('#fangdaBID').click();
}

function chaxunBID(aa) {
	$('#bidSearch').submit();
}
function chooseLowerBID(aa) {
	var che = $('#lowerDepart_' + aa).prop('checked');
	if (che == true) {
		$('#lowerDepart_' + aa).attr('value', 'Y');
	} else {
		$('#lowerDepart_' + aa).attr('value', 'N');
	}
}
function shanchuBID() {
	$('#titlenameBID').html('');
	$('#PERSON_IDBID').attr('value', '');
	$('#LOCAL_TITLEBID').attr('value', '');
}

$("#hr36010_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
    "bAutoWidth":false,//表格宽度自动变化
    "bProcessing":true,
    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
	"bLengthChange": true,  //按多少条记录显示下拉框
	"iDisplayLength": 15, //默认每页显示的记录数
	"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
 	"searching": true,//本地搜索
	"bSort": true,   //排序功能
	"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
     "orderClasses": false,
     "order":[],//初始化不用自动排序
     "scrollY": $(document.body).height() - 360,
     "scrollX": true,
     "scrollCollapse": false,
     "deferRender":true,
     //"scroller":true,
    "oLanguage": {//多语言配置
		"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
		"sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！
		"sEmptyTable": "<spring:message code='hrm.alert.empinfo.No_data_in_table'/>",//表中无数据存在！
		"sSearch": "<spring:message code='hrm.alert.contractInfo.Rapid_screening'/>",//快速筛选
		"sLengthMenu": "<spring:message code='hrm.alert.contractInfo.Record_page'/>",//每页 _MENU_ 条记录
		"sInfo": "<spring:message code='hrm.alert.contractInfo.START_END_TOTAL'/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
		"sInfoFiltered": "(<spring:message code='hrm.alert.contractInfo.Record_filter'/>)",//从 _MAX_ 条记录过滤
		"oPaginate": {
			"sPrevious": "<spring:message code='hrm.alert.contractInfo.Previous_page'/>",//上一页
			"sNext": "<spring:message code='hrm.alert.contractInfo.NEXT_PAGE'/>"//下一页
        }
    },
    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
    "buttons": [
          ] 
});

function changeBIDINFOR(no, status, id, aid) {
	var idvalue = $('#' + id).val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO=' + no
			+ '&firstFlag=N&status=' + status + '&nameid=' + id
			+ '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);

}
function changeZhijiBIDINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoBIDINFOR = $('#POST_FAMILY_BIDINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoBIDINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="bidSearch" method="post" action="/hrm/empinfo/bidSearch"
		class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
<!-- <div class="pageContent"
	style="width: 92%; margin-left: auto; margin-right: auto;">
 -->
 <div class="pageHeader">
<div class="searchBar" >
	<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="user_table">
 -->
 <table class="searchContent" >
		<tr>
			<td style="padding-top:  4px">
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td style="border: 0px;width:30%">
		<div style="float: left"><input type="text" name="seach_KEYBID" id="seach_KEYBID" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingBID('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaBID" onclick="fangdajingBID('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=bidSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenameBID">${LOCAL_TITLE }</span>
			
		<!-- </td>
			<td  style="border: 0px">
			
			 <a class="buttonActive" onclick="shanchuEMERGENCYADDRESS()">
							<span>删除</span>
			</a> -->
		</td>
		<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
	    </td>
				<td style="border: 0px">
					<ait:deptList name="ISDEPTNO14" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO14"/>
										<ait:deptTreeIcon name="ISDEPTNO14" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO14" selected="${ISDEPTNO14}"/>
                     <input type="checkbox" name="lowerDepart" id="lowerDepart_bid" value="N" onclick="chooseLowerBID('bid')">
                     	<spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
   			</td>
   			<input type="hidden" style="display: none;" id="PERSON_IDBID" name="PERSON_ID"
				value="${PERSON_ID }">
			<input type="hidden" id="LOCAL_TITLEBID" name="LOCAL_TITLE"
				value="${LOCAL_TITLE }">
			<input type="hidden" id="dataSearch" name="dataSearch" value="bid">
			<input type="hidden" id="FLAG" name="FLAG" value="1">
		</tr>
			<tr>
				<td width="5%" >
					<spring:message code="hrm.empinfo.Period" /><!--期间-->
				</td>
				<td >
					<input type="text" id="START_DATE_BID" name="START_DATE_BID"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${START_DATE_BID}" style="float: left;" />
					<div style="float: left;">
						~
					</div>
					<input type="text" id="END_DATE_BID" name="END_DATE_BID"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${END_DATE_BID}" style="float: left;" />
				</td>
				<td width="5%" >
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--资格证书-->
				</td>
				<td width="5%" >
					<input type="text" name="QUAL_NAME" id="QUAL_NAME"
						value="${QUAL_NAME }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--资格等级-->
				</td>
				<td width="5%" style="padding-top:  4px">
					<input type="text" id="QUAL_LEVEL" name="QUAL_LEVEL"
						value="${QUAL_LEVEL}">
				</td>
			</tr>
			<tr>
				<td width="5%"  >
					<spring:message code="hrm.empinfo.DATE_STARTED" /><!--入社日期-->
				</td>
			<td width="5%" >
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" 
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${START_DATE}" style="float: left;" />
					<div style="float: left;">
						~
					</div>
					<input type="text" id="END_DATE" name="END_DATE" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"
						style="float: left;" />
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群-->
				</td>
				<td width="5%" >
					<input type="text" id="zhiqunBIDINFOR" name="zhiqunBIDINFOR"
						value="${zhiqunBIDINFOR }">
					<a id="zhiBIDINFOR" class="" href="#"
						onclick="changeBIDINFOR('14015812','zhiqunBIDINFOR','POST_FAMILY_BIDINFOR','zhiBIDINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="POST_FAMILY_BIDINFOR" name="POST_FAMILY"
						value="${POST_FAMILY }">
				</td>

				<td width="5%" >
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>

				<td width="5%" style="padding-top:  4px">
					<input type="text" id="zhijiBIDINFOR" name="zhijiBIDINFOR"
						value="${zhijiBIDINFOR }">
					<a id="jiBIDINFOR" class="" href="#"
						onclick="changeZhijiBIDINFOR('zhijiBIDINFOR','GRADE_NO_BIDINFOR','jiBIDINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="GRADE_NO_BIDINFOR" name="GRADE_NO"
						value="${GRADE_NO }">
				</td>
			</tr>
			<tr>
				<td width="5%" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhuyaoyewuBIDINFOR" name="zhuyaoyewuBIDINFOR"
						value="${zhuyaoyewuBIDINFOR }">
					<a id="yewuBIDINFOR" class="" href="#"
						onclick="changeBIDINFOR('400098','zhuyaoyewuBIDINFOR','DUTY_NO_BIDINFOR','yewuBIDINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_BIDINFOR"
						name="MAIN_BUSINESS" value="${MAIN_BUSINESS }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>
				<td width="5%" >
					<input type="text" id="zhiyuanleixingBIDINFOR"
						name="zhiyuanleixingBIDINFOR" value="${zhiyuanleixingBIDINFOR }">
					<a id="zhiyuanBIDINFOR" class="" href="#"
						onclick="changeBIDINFOR('13864','zhiyuanleixingBIDINFOR','EMP_TYPE_CODE_BIDINFOR','zhiyuanBIDINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_TYPE_CODE_BIDINFOR"
						name="EMP_TYPE_CODE" value="${EMP_TYPE_CODE }">
				</td>
				<td width="5%" >
					<!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" />
				</td>
				<td width="5%" >
					<input type="text" id="renzhizhuangtaiBIDINFOR"
						name="renzhizhuangtaiBIDINFOR"
						value="${renzhizhuangtaiBIDINFOR }">
					<a id="renzhiBIDINFOR" class="" href="#"
						onclick="changeBIDINFOR('15118','renzhizhuangtaiBIDINFOR','EMP_OFFICE_BIDINFOR','renzhiBIDINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_OFFICE_BIDINFOR"
						name=EMP_OFFICE value="${EMP_OFFICE }">
				</td>
			</tr>
	</table>
	<input type="hidden" id="Bidlocalname" value="${personinfo.LOCAL_NAME }">
	<input type="hidden" id="Bidlocalempid" value="${personinfo.EMPID }">
	<input type="hidden" id="Bidlocalpostgradenoname"
		value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
	<input type="hidden" id="Bidlocalpostgradeotherinf"
		value="${personinfo.POST_GRADE_OTHERINF }">
	<input type="hidden" id="Bidlocalcenter"
		value="${personinfo.COST_CENTER_TITLE }">
	<input type="hidden" id="Bidlocalempofficename"
		value="${personinfo.EMP_OFFICE_NAME_TITLE }">
	<input type="hidden" id="beginSearch" value="bid">
</div>
<!-- 资格搜索 -->
<div id="bid" style="width: 99%; margin-left: auto; margin-right: auto;">
		<div class="formBar">

			<ul class="toolBar">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
				</c:if>
				<li>
					<a class="buttonActive" onclick="chaxunBID('bid')" href="#">
					<span><spring:message code="button.search" /><!--查询--></span>
					</a>
				</li>

				<li>
					<a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=50">
						<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span>
					</a>
				</li>
			</ul>
		</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		<table class="orderList" id="hr36010_table" width="99%" border="1" cellpadding="2"
			cellspacing="1">
			<thead>
				<tr>
					<th width="5%">
						NO.
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.name" /><!--姓名-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.empid" /><!--社号-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!--部门-->
					</th>
					<th width="5%">
						<spring:message code="hrm.contract.Rank" /><!--职级-->
					</th>
					<th width="5%">
						<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--资格证书-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.award_date" /><!--获证日期-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Valid_date" /><!--有效日期-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Qualification_grade" /><!--资格等级-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Issuing_authority" /><!--发证机关-->
					</th>
				</tr>
			</thead>
			<c:forEach items="${bidSearch }" var="a" varStatus="i">
				<tr>
					<td class="td_type">
						${i.count }
					</td>
					<td class="td_type">
						${a.LOCAL_NAME }
					</td>
					<td class="td_type">
						${a.EMPID }
					</td>
					<td class="td_type">
						${a.ORG_NAME_LOCAL }
					</td>
					<td class="td_type">
						${a.POST_GRADE_NO_NAME }
					</td>
					<td class="td_type">
						${a.QUAL_NAME }
					</td>
					<td class="td_type">
						${a.DATE_OBTAINED }
					</td>
					<td class="td_type">
						${a.VALIDITY_DATE }
					</td>
					<td class="td_type">
						${a.QUAL_LEVEL }
					</td>
					<td class="td_type">
						${a.QUAL_INSTITUTE }
					</td>
				</tr>
			</c:forEach>
		</table>

	</form>
</div>
