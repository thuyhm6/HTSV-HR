<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var aa = $("#Retirelocalname").val();
	var bb = $("#Retirelocalempid").val();
	var cc = $("#Retirelocalpostgradenoname").val();
	var dd = $("#Retirelocalpostgradeotherinf").val();
	var ff = $("#Retirelocalcenter").val();
	var gg = $("#Retirelocalempofficename").val();
	if (gg != "" && typeof (gg) != "undefined") {
		$('#titlenameRETIRE').html(
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
function fangdajingRETIRE(flag) {
	var name = encodeURI(encodeURI($('#seach_KEYRETIRE').val()));
	var dataSearch = $('#beginSearch').val();
	$('#fangdaRETIRE')
			.attr(
					'href',
					'/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='
							+ dataSearch
							+ '&pageNum=1&firstFlag=N&searchChange=retireSearch&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$('#fangdaRETIRE').click();
}

function chaxunRETIRE(aa) {
	$('#retireSearch').submit();
}
function chooseLowerRETIRE(aa) {
	var che = $('#lowerDepart_' + aa).prop('checked');
	if (che == true) {
		$('#lowerDepart_' + aa).attr('value', 'Y');
	} else {
		$('#lowerDepart_' + aa).attr('value', 'N');
	}
}
function shanchuRETIRE() {
	$('#titlenameRETIRE').html('');
	$('#PERSON_IDRETIRE').attr('value', '');
	$('#LOCAL_TITLERETIRE').attr('value', '');
}
//发令区分和发令原因的联动
function codeReasonInfor() {
	var parentno = $('#TRANS_CODE').val();
	$.ajax( {
		type : 'post',
		dateType : 'json',
		url : '/hrm/empinfo/codeReason',
		async : false,
		data : {
			parentno : parentno
		},
		success : function(data) {
			var codereasonlist = data.codeReasonList;
			if (codereasonlist != "") {
				var str = '';
				str = '<select name="TRANS_REASON" id="TRANS_REASON">';
				var s = '';
				for ( var i = 0; i < codereasonlist.length; i++) {
					var codeno = codereasonlist[i]['CODE_NO'];
					var content = codereasonlist[i]['CONTENT'];
					s = s + '<option value="' + codeno + '">' + content
							+ '</option>';
				}
				var strlast = '</select>';
				str = str + s + strlast;
				$('#falingyuanyin').html(str);
			} else {
				$('#falingyuanyin').html('');
			}
		}
	});
}

$("#hr3605_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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
     "scrollY": $(document.body).height() - 350,
     "scrollX": true,
     "scrollCollapse": false,
     "deferRender":true,
     //"scroller":true,
    "oLanguage": {//多语言配置
		"sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
		"sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
		"sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
		"sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
		"sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
		"sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
		"sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
		"oPaginate": {
			"sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
			"sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
        }
    },
    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
    "buttons": [
          ] 
});

function changeRETIREINFOR(no, status, id, aid) {
	var idvalue = $('#' + id).val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO=' + no
			+ '&firstFlag=N&status=' + status + '&nameid=' + id
			+ '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);

}
function changeZhijiRETIREINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoRETIREINFOR = $('#POST_FAMILY_RETIREINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoRETIREINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="retireSearch" method="post"
		action="/hrm/empinfo/retireSearch" class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
		<input type="hidden" id="PERSON_IDRETIRE" name="PERSON_ID"
				value="${PERSON_ID }">
			<input type="hidden" id="LOCAL_TITLERETIRE" name="LOCAL_TITLE"
				value="${LOCAL_TITLE }">
			<input type="hidden" id="dataSearch" name="dataSearch" value="retire">
			<input type="hidden" id="FLAG" name="FLAG" value="1">
<div class="pageHeader">
<div class="searchBar" >

	<table class="searchContent">

		<tr>
			<td  >
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td style="border: 0px;width:30%">
		<div style="float: left"><input type="text" name="seach_KEYRETIRE" id="seach_KEYRETIRE" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingRETIRE('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaRETIRE" onclick="fangdajingRETIRE('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=retireSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenameRETIRE">${LOCAL_TITLE }</span>
			
		</td>
		<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
	</td>
				<td style="border: 0px ;">
					<ait:deptList name="ISDEPTNO19" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO19"/>
										<ait:deptTreeIcon name="ISDEPTNO19" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO19" selected="${ISDEPTNO19}"/>
                     <input type="checkbox" name="lowerDepart" id="lowerDepart_retire" value="N" onclick="chooseLowerRETIRE('retire')">
                     <spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
    			</td>
		        <td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>

				<td width="5%" >
					<input type="text" id="zhiyuanleixingRETIREINFOR"
						name="zhiyuanleixingRETIREINFOR"
						value="${zhiyuanleixingRETIREINFOR }">
					<a id="zhiyuanRETIREINFOR" class="" href="#"
						onclick="changeRETIREINFOR('13864','zhiyuanleixingRETIREINFOR','EMP_TYPE_CODE_RETIREINFOR','zhiyuanRETIREINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_TYPE_CODE_RETIREINFOR"
						name="EMP_TYPE_CODE" value="${EMP_TYPE_CODE }">
				</td>	
		</tr>
			<tr>
				<td width="5%"  >
					<spring:message code="hrm.empinfo.Period" /><!--期间-->
				</td>
				<td  >
					<input type="text" id="START_DATE_RET" name="START_DATE_RET"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${START_DATE_RET}" style="float: left;" />
					<div style="float: left;">
						~
					</div>
					<input type="text" id="END_DATE_RET" name="END_DATE_RET"
						class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${END_DATE_RET}" style="float: left;" />
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群-->
				</td>

				<td width="5%" >
					<input type="text" id="zhiqunRETIREINFOR" name="zhiqunRETIREINFOR"
						value="${zhiqunRETIREINFOR }">
					<a id="zhiRETIREINFOR" class="" href="#"
						onclick="changeRETIREINFOR('14015812','zhiqunRETIREINFOR','POST_FAMILY_RETIREINFOR','zhiRETIREINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="POST_FAMILY_RETIREINFOR"
						name="POST_FAMILY" value="${POST_FAMILY }">
				</td>
				<td width="5%"  >
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>

				<td width="5%" >
					<input type="text" id="zhijiRETIREINFOR" name="zhijiRETIREINFOR"
						value="${zhijiRETIREINFOR }">
					<a id="jiRETIREINFOR" href="#"
						onclick="changeZhijiRETIREINFOR('zhijiRETIREINFOR','GRADE_NO_RETIREINFOR','jiRETIREINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="GRADE_NO_RETIREINFOR" name="GRADE_NO"
						value="${GRADE_NO }">
				</td>
			</tr>
			<tr>
				<td width="5%" style="padding-top:  3px" >
					<spring:message code="display.emp.statistics.mes206" /><!--入社日期-->
				</td>
				<td width="5%" style="padding-top:  3px">
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
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhuyaoyewuRETIREINFOR" name="zhuyaoyewuRETIREINFOR"
						value="${zhuyaoyewuRETIREINFOR }">
					<a id="yewuRETIREINFOR" class="" href="#"
						onclick="changeRETIREINFOR('400098','zhuyaoyewuRETIREINFOR','MAIN_BUSINESS_RETIREINFOR','yewuRETIREINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_RETIREINFOR"
						name="MAIN_BUSINESS" value="${MAIN_BUSINESS }">
				</td>
				
				<td width="5%" >
					<!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" />
				</td>
				<td width="5%" >
					<input type="text" id="renzhizhuangtaiRETIREINFOR"
						name="renzhizhuangtaiRETIREINFOR"
						value="${renzhizhuangtaiRETIREINFOR }">
					<a id="renzhiRETIREINFOR" class="" href="#"
						onclick="changeRETIREINFOR('15118','renzhizhuangtaiRETIREINFOR','EMP_OFFICE_RETIREINFOR','renzhiRETIREINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_OFFICE_RETIREINFOR"
						name=EMP_OFFICE value="${EMP_OFFICE }">
				</td>
			</tr>
	</table>
	<input type="hidden" id="Retirelocalname" value="${personinfo.LOCAL_NAME }">
	<input type="hidden" id="Retirelocalempid" value="${personinfo.EMPID }">
	<input type="hidden" id="Retirelocalpostgradenoname"
		value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
	<input type="hidden" id="Retirelocalpostgradeotherinf"
		value="${personinfo.POST_GRADE_OTHERINF }">
	<input type="hidden" id="Retirelocalcenter"
		value="${personinfo.COST_CENTER_TITLE }">
	<input type="hidden" id="Retirelocalempofficename"
		value="${personinfo.EMP_OFFICE_NAME_TITLE }">
	<input type="hidden" id="beginSearch" value="retire">
</div>
</div>
<!-- 退职搜索 -->
<div id="retire"
	style="width: 99%; margin-left: auto; margin-right: auto;">
		
		<div class="formBar">

			<ul class="toolBar">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
				</c:if>
				<li>
					<a class="buttonActive" onclick="chaxunRETIRE('retire')" href="#">
					<span><spring:message code="button.search" /><!--查询--></span>
					</a>
				</li>
				<li>
					<a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=55">
						<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span>
					</a>
				</li>
			</ul>
		</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		<table class="orderList" id='hr3605_table' width="99%" border="1" cellpadding="2"
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
						<spring:message code="hrm.empinfo.empid" /> <!--社号-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!--部门-->
					</th>
					<th width="5%">
						<spring:message code="hrm.contract.Rank" /><!--职级-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.DATE_STARTED" /><!--入社日期-->
					</th>
					<th width="5%">
						<spring:message code="hrm.recruitManage.LEAVE_DATE" /><!--离职日期-->
					</th>
					<th width="5%" style="display:none">
						<spring:message code="hrm.empinfo.Leave_Distinguish" /><!-- 离职区分 -->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.FAM_PHONE" /><!--联系电话-->
					</th>
					<th width="5%">
						<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.REMARK" /><!--备注-->
					</th>
				</tr>
			</thead>
			<c:forEach items="${retireSearch }" var="a" varStatus="i">
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
						${a.DATE_STARTED }
					</td>
					<td class="td_type">
						${a.DATE_LEFT }
					</td>
					<td class="td_type"  style="display:none">
						${a.DUTY_NO_NAME }
					</td>
					<td class="td_type">
						${a.CELLPHONE }
					</td>
					<td class="td_type">
						${a.MAIN_BUSINESS_NAME }
					</td>
					<td class="td_type">
						${a.LIZHIREASON_NAME }
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>
