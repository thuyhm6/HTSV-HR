<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var aa = $("#Recognilocalname").val();
	var bb = $("#Recognilocalempid").val();
	var cc = $("#Recognilocalpostgradenoname").val();
	var dd = $("#Recognilocalpostgradeotherinf").val();
	var ff = $("#Recognilocalcenter").val();
	var gg = $("#Recognilocalempofficename").val();
	if (gg != "" && typeof (gg) != "undefined") {
		$('#titlenameRECOGNITION').html(
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
function fangdajingRECOGNITION(flag) {
	var name = encodeURI(encodeURI($('#seach_KEYRECOGNITION').val()));
	var dataSearch = $('#beginSearch').val();
	$('#fangdaRECOGNITION')
			.attr(
					'href',
					'/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='
							+ dataSearch
							+ '&pageNum=1&firstFlag=N&searchChange=recognitionSearch&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$('#fangdaRECOGNITION').click();
}

function chaxunRECOGNITION(aa) {
	$('#recognitionSearch').submit();
}
function chooseLowerRECOGNITION(aa) {
	var che = $('#lowerDepart_' + aa).prop('checked');
	if (che == true) {
		$('#lowerDepart_' + aa).attr('value', 'Y');
	} else {
		$('#lowerDepart_' + aa).attr('value', 'N');
	}
}
function shanchuRECOGNITION() {
	$('#titlenameRECOGNITION').html('');
	$('#PERSON_IDRECOGNITION').attr('value', '');
	$('#LOCAL_TITLERECOGNITION').attr('value', '');
}
$("#hr3608_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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
     "scrollY": $(document.body).height() - 330,
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

function changeRECOGNINFOR(no, status, id, aid) {
	var idvalue = $('#' + id).val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO=' + no
			+ '&firstFlag=N&status=' + status + '&nameid=' + id
			+ '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);

}
function changeZhijiRECOGNINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoRECOGNINFOR = $('#POST_FAMILY_RECOGNINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoRECOGNINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="recognitionSearch" method="post"
		action="/hrm/empinfo/recognitionSearch"
		class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
<!-- <div class="pageContent"
	style="width: 92%; margin-left: auto; margin-right: auto;"> -->
	<div class="pageHeader">
<div class="searchBar" >

	<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="user_table"> -->
		<table class="searchContent" >

		<tr>
			<td >
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td style="border: 0px;width:30%">
		<div style="float: left"><input type="text" name="seach_KEYRECOGNITION" id="seach_KEYRECOGNITION" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingRECOGNITION('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaRECOGNITION" onclick="fangdajingRECOGNITION('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=recognitionSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenameRECOGNITION">${LOCAL_TITLE }</span>
			
		<!-- </td>
			<td  style="border: 0px">
			
			 <a class="buttonActive" onclick="shanchuEMERGENCYADDRESS()">
							<span>删除</span>
			</a> -->
		</td>
		<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门 -->
	</td>
				<td style="border: 0px ;padding-top:  4px">
					<ait:deptList name="ISDEPTNO17" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO17"/>
										<ait:deptTreeIcon name="ISDEPTNO17" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO17" selected="${ISDEPTNO17}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_recognition" value="N" onclick="chooseLowerRECOGNITION('recognition')">
                    <spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
    			</td>
		</tr>
			<input type="hidden" id="PERSON_IDRECOGNITION" name="PERSON_ID"
				value="${PERSON_ID }">
			<input type="hidden" id="LOCAL_TITLERECOGNITION" name="LOCAL_TITLE"
				value="${LOCAL_TITLE }">
			<input type="hidden" id="dataSearch" name="dataSearch"
				value="recognition">
			<input type="hidden" id="FLAG" name="FLAG" value="1">
			<tr>
				<td width="5%">
					<spring:message code="hrm.empinfo.Period" /><!--期间-->
				</td>
				<td width="10%">
					<input type="text" id="START_DATE_REC" name="START_DATE_REC"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${START_DATE_REC}" style="float: left;" />
					<div style="float: left;">
						~
					</div>
					<input type="text" id="END_DATE_REC" name="END_DATE_REC"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${END_DATE_REC}" style="float: left;" />
				</td>
				<td width="5%">
					<spring:message code="hrm.empinfo.praise_prize" /><!--表扬/得奖-->
				</td>
				<td width="5%">
					<input type="text" id="biaoyangRECOGNINFOR" name="biaoyangRECOGNINFOR"
						value="${biaoyangRECOGNINFOR }">
					<a id="byRECOGNINFOR" class="" href="#"
						onclick="changeRECOGNINFOR('14014334','biaoyangRECOGNINFOR','REWARD_TYPE_RECOGNINFOR','byRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="REWARD_TYPE_RECOGNINFOR" name="REWARD_TYPE"
						value="${REWARD_TYPE }">
				</td>
			</tr>
			<tr>
				 <td width="5%"  >
					<spring:message code="hrm.empinfo.WORK_DATE_COUNT" /><!--在职期间-->
				</td>
			<td width="5%"  style="padding-top:  4px">
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
				<td width="5%">
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群-->
				</td>
				<td width="5%">
					<input type="text" id="zhiqunRECOGNINFOR" name="zhiqunRECOGNINFOR"
						value="${zhiqunRECOGNINFOR }">
					<a id="zhiRECOGNINFOR" class="" href="#"
						onclick="changeRECOGNINFOR('14015812','zhiqunRECOGNINFOR','POST_FAMILY_RECOGNINFOR','zhiRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="POST_FAMILY_RECOGNINFOR"
						name="POST_FAMILY" value="${POST_FAMILY }">
				</td>
				<td width="5%">
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>
				<td width="5%">
					<input type="text" id="zhijiRECOGNINFOR" name="zhijiRECOGNINFOR"
						value="${zhijiRECOGNINFOR }">
					<a id="jiRECOGNINFOR" class="" href="#"
						onclick="changeZhijiRECOGNINFOR('zhijiRECOGNINFOR','GRADE_NO_RECOGNINFOR','jiRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="GRADE_NO_RECOGNINFOR" name="GRADE_NO"
						value="${GRADE_NO }">
				</td>
			</tr>
			<tr>
				<td width="5%">
					<spring:message code="org.title.MAIN_BUSINESS" /><!--主要业务-->
				</td>
				<td width="5%">
					<input type="text" id="zhuyaoyewuRECOGNINFOR" name="zhuyaoyewuRECOGNINFOR"
						value="${zhuyaoyewuRECOGNINFOR }">
					<a id="yewuRECOGNINFOR" class="" href="#"
						onclick="changeRECOGNINFOR('400098','zhuyaoyewuRECOGNINFOR','MAIN_BUSINESS_RECOGNINFOR','yewuRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_RECOGNINFOR" name="MAIN_BUSINESS"
						value="${MAIN_BUSINESS }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>
				<td width="5%" >
					<input type="text" id="zhiyuanleixingRECOGNINFOR"
						name="zhiyuanleixingRECOGNINFOR"
						value="${zhiyuanleixingRECOGNINFOR }">
					<a id="zhiyuanRECOGNINFOR" class="" href="#"
						onclick="changeRECOGNINFOR('13864','zhiyuanleixingRECOGNINFOR','EMP_TYPE_CODE_RECOGNINFOR','zhiyuanRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_TYPE_CODE_RECOGNINFOR"
						name="EMP_TYPE_CODE" value="${EMP_TYPE_CODE }">
				</td>
				<td width="5%">
					<spring:message code="hrm.empinfo.Working_status" /><!--任职状态-->
				</td>
				<td width="5%" >
					<input type="text" id="renzhizhuangtaiRECOGNINFOR"
						name="renzhizhuangtaiRECOGNINFOR"
						value="${renzhizhuangtaiRECOGNINFOR }">
					<a id="renzhiRECOGNINFOR" class="" href="#"
						onclick="changeRECOGNINFOR('15118','renzhizhuangtaiRECOGNINFOR','EMP_OFFICE_RECOGNINFOR','renzhiRECOGNINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_OFFICE_RECOGNINFOR" name="EMP_OFFICE"
						value="${EMP_OFFICE }">
				</td>
			</tr>
	</table>
	<input type="hidden" id="Recognilocalname" value="${personinfo.LOCAL_NAME }">
	<input type="hidden" id="Recognilocalempid" value="${personinfo.EMPID }">
	<input type="hidden" id="Recognilocalpostgradenoname"
		value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
	<input type="hidden" id="Recognilocalpostgradeotherinf"
		value="${personinfo.POST_GRADE_OTHERINF }">
	<input type="hidden" id="Recognilocalcenter"
		value="${personinfo.COST_CENTER_TITLE }">
	<input type="hidden" id="Recognilocalempofficename"
		value="${personinfo.EMP_OFFICE_NAME_TITLE }">
	<input type="hidden" id="beginSearch" value="recognition">
</div></div>
<!-- 表彰搜索 -->
<div id="recognition"
	style="width: 99%; margin-left: auto; margin-right: auto;">
		
		<div class="formBar">

			<ul class="toolBar">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
				</c:if>
				<li>
					<a class="buttonActive" onclick="chaxunRECOGNITION('recognition')"
						href="#"><span><spring:message code="button.search" /><!--查询--></span>
					</a>
				</li>

				<li>
					<a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=53">
						<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span>
					</a>
				</li>
			</ul>
		</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		<table class="orderList" id="hr3608_table" width="99%" border="1" cellpadding="2"
			cellspacing="1">
			<thead>
				<tr>
					<th width="3%">
						NO.
					</th>
					<th width="4%">
						<spring:message code="hrm.empinfo.name" /><!--姓名-->
					</th>
					<th width="4%">
					    <spring:message code="hrm.empinfo.empid" /><!--社号-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!--部门-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
					</th>
					<th width="5%">
						<spring:message code="hrm.contract.Rank" /><!--职级-->
					</th>
					<th width="4%">
						<spring:message code="display.emp.statistics.mes206" /><!--入社日期-->
					</th>
					<th width="4%">
						<spring:message code="ess.infoApply.renzhizhuangtai" /><!--任职状态-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.praise_prize" /><!--表扬/得奖-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.praise_prize_date" /><!--表扬(得奖)日-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Awarding_authority" /><!--授予机关-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.BONUS" /><!--奖金-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Bonus_payment_code" /><!--奖金支付代码-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.REMARK" /><!--备注-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.Personnel_card_inquiry" /><!--人事卡查询与否-->
					</th>
				</tr>
			</thead>
			<c:forEach items="${recognitionSearch }" var="a" varStatus="i">
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
						${a.EMP_TYPE_CODE_NAME }
					</td>
					<td class="td_type">
						${a.POST_GRADE_NO_NAME }
					</td>
					<td class="td_type">
						${a.DATE_STARTED }
					</td>
					<td class="td_type">
						${a.EMP_OFFICE_NAME }
					</td>
					<td class="td_type">
						${a.REWARD_TYPE_NAME }
					</td>
					<td class="td_type">
						${a.REWARD_DATE }
					</td>
					<td class="td_type">
						${a.REWARD_CNPY }
					</td>
					<td class="td_type">
						${a.REWARD }
					</td>
					<td class="td_type">
						${a.REWARD_TYPE_CODE_NAME }
					</td>
					<td class="td_type">
						${a.REMARKS }
					</td>
					<td class="td_type" style="text-align:center" >
						<input type="checkbox" disabled="disabled" <c:if test="${a.PERSONNEL_CARD_INQUIRY == 'Y' }"> checked="checked" </c:if>></input>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>
