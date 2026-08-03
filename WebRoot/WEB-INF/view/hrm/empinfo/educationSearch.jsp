<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var aa = $("#Edulocalname").val();
	var bb = $("#Edulocalempid").val();
	var cc = $("#Edulocalpostgradenoname").val();
	var dd = $("#Edulocalpostgradeotherinf").val();
	var ff = $("#Edulocalcenter").val();
	var gg = $("#Edulocalempofficename").val();
	if (gg != "" && typeof (gg) != "undefined") {
		$('#titlenameEDUCATION').html(
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
function fangdajingEDUCATION(flag) {
	var name = encodeURI(encodeURI($('#seach_KEYEDUCATION').val()));
	var dataSearch = $('#beginSearch').val();
	$('#fangdaEDUCATION')
			.attr(
					'href',
					'/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='
							+ dataSearch
							+ '&pageNum=1&firstFlag=N&searchChange=educationSearch&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$('#fangdaEDUCATION').click();
}

function chaxunEDUCATION(aa) {
	$('#educationSearch').submit();
}
function chooseLowerEDUCATION(aa) {
	var che = $('#lowerDepart_' + aa).prop('checked');
	if (che == true) {
		$('#lowerDepart_' + aa).attr('value', 'Y');
	} else {
		$('#lowerDepart_' + aa).attr('value', 'N');
	}
}
function shanchuEDUCATION() {
	$('#titlenameEDUCATION').html('');
	$('#PERSON_IDEDUCATION').attr('value', '');
	$('#LOCAL_TITLEEDUCATION').attr('value', '');
}
function rendingxueli() {
	var red = $('#FINAL_DEGREE_WHETHER').prop('checked');
	if (red == true) {
		$('#FINAL_DEGREE_WHETHER').attr('value', 'Y');
	} else {
		$('#FINAL_DEGREE_WHETHER').attr('value', 'N');
	}
}

$("#hr3606_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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
     "scrollY": $(document.body).height() - 370,
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

function changeEDUCATIONINFOR(no, status, id, aid) {
	var idvalue = $('#' + id).val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO=' + no
			+ '&firstFlag=N&status=' + status + '&nameid=' + id
			+ '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);

}
function changezhijiEDUCATIONINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoEDUCATIONINFOR = $('#POST_FAMILY_EDUCATIONINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoEDUCATIONINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="educationSearch" method="post"
		action="/hrm/empinfo/educationSearch"
		class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
		<input type="hidden" id="PERSON_IDEDUCATION" name="PERSON_ID"
				value="${PERSON_ID }">
			<input type="hidden" id="LOCAL_TITLEEDUCATION" name="LOCAL_TITLE"
				value="${LOCAL_TITLE }">
			<input type="hidden" id="dataSearch" name="dataSearch"
				value="education">
			<input type="hidden" id="FLAG" name="FLAG" value="1">
<div class="pageHeader">
<div class="searchBar" >

	<table class="searchContent" >
		<tr>
			<td >
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td style="border: 0px;width:30%">
		<div style="float: left"><input type="text" name="seach_KEYEDUCATION" id="seach_KEYEDUCATION" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingEDUCATION('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaEDUCATION" onclick="fangdajingEDUCATION('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=educationSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenameEDUCATION">${LOCAL_TITLE }</span>
			
		</td>
		</tr>
		<tr>
		<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!--部门-->
		</td>
				<td style="border: 0px">
					<ait:deptList name="ISDEPTNO13" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO13"/>
					<ait:deptTreeIcon name="ISDEPTNO13" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO13" selected="${ISDEPTNO13}"/>
                   <input type="checkbox" name="lowerDepart" id="lowerDepart_education" value="N" onclick="chooseLowerEDUCATION('education')">
                   	<spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
   			</td>
   			<td width="5%" >
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群-->
				</td>
				<td width="5%" >
					<input type="text" id="zhiqunEDUCATIONINFOR"
						name="zhiqunEDUCATIONINFOR" value="${zhiqunEDUCATIONINFOR }">
					<a id="zhiEDUCATIONINFOR" class="" href="#"
						onclick="changeEDUCATIONINFOR('14015812','zhiqunEDUCATIONINFOR','POST_FAMILY_EDUCATIONINFOR','zhiEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="POST_FAMILY_EDUCATIONINFOR"
						name="POST_FAMILY" value="${POST_FAMILY }">
				</td>

				<td width="5%" >
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>

				<td width="5%" >
					<input type="text" id="zhijiEDUCATIONINFOR"
						name="zhijiEDUCATIONINFOR" value="${zhijiEDUCATIONINFOR }">
					<a id="jiEDUCATIONINFOR" class="" href="#"
						onclick="changezhijiEDUCATIONINFOR('zhijiEDUCATIONINFOR','GRADE_NO_EDUCATIONINFOR','jiEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="GRADE_NO_EDUCATIONINFOR" name="GRADE_NO"
						value="${GRADE_NO }">
				</td>
   		</tr>
			<tr>
				<td width="5%" >
					<spring:message code="hrm.empinfo.during_school_days" /><!--期间-->
				</td>
				<td  >
					<input type="text" id="START_DATE_EDU" name="START_DATE_EDU"
						class="Wdate" onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
						value="${START_DATE_EDU}" style="float: left;" />
					<div style="float: left;">
						~
					</div>
					<input type="text" id="END_DATE_EDU" name="END_DATE_EDU"
						class="Wdate" onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
						value="${END_DATE_EDU}" style="float: left;" />
				</td>
				<td width="5%">
					<spring:message code="hrm.empinfo.DEGREE_CODE" /><!--学历-->
				</td>
				<td width="5%" >
					<input type="text" id="xueliEDUCATIONINFOR"
						name="xueliEDUCATIONINFOR" value="${xueliEDUCATIONINFOR }">
					<a id="xlEDUCATIONINFOR" class="" href="#"
						onclick="changeEDUCATIONINFOR('13769','xueliEDUCATIONINFOR','DEGREE_CODE_EDUCATIONINFOR','xlEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="DEGREE_CODE_EDUCATIONINFOR"
						name="DEGREE_CODE" value="${DEGREE_CODE }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否-->
				</td>
				<td width="5%" >
					<input type="checkbox" name="FINAL_DEGREE_WHETHER"
						id="FINAL_DEGREE_WHETHER" value="${FINAL_DEGREE_WHETHER }" onclick="rendingxueli()">
				</td>
			</tr>
   		<tr>
   			<td width="5%" >
					<spring:message code="ess.empInfo.date_of_agency" /><!--入社日期-->
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
					<spring:message code="hrm.recruitManage.INSTITUTION_NAME" /><!--学校名-->
				</td>
				<td width="5%" >
					<input type="text" name="INSTITUTION_NAME" id="INSTITUTION_NAME"
						value="${INSTITUTION_NAME }">
				</td>
		
			<td width="5%" style="padding-top:  2px">
					<spring:message code="hrm.recruitManage.SUBJECT" /><!--专业-->
			</td>
			<td width="5%" style="padding-top:  2px">
					<input type="text" name="SUBJECT" id="SUBJECT" value="${SUBJECT }">
			</td>
		</tr>	
			<tr>
				<td width="5%" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhuyaoyewuEDUCATIONINFOR" name="zhuyaoyewuEDUCATIONINFOR"
						value="${zhuyaoyewuEDUCATIONINFOR }">
					<a id="yewuEDUCATIONINFOR" class="" href="#"
						onclick="changeEDUCATIONINFOR('400098','zhuyaoyewuEDUCATIONINFOR','MAIN_BUSINESS_EDUCATIONINFOR','yewuEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_EDUCATIONINFOR"
						name="MAIN_BUSINESS" value="${MAIN_BUSINESS }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>
				<td width="5%" >
					<input type="text" id="zhiyuanleixingEDUCATIONINFOR"
						name="zhiyuanleixingEDUCATIONINFOR"
						value="${zhiyuanleixingEDUCATIONINFOR }">
					<a id="zhiyuanEDUCATIONINFOR" class="" href="#"
						onclick="changeEDUCATIONINFOR('13864','zhiyuanleixingEDUCATIONINFOR','EMP_TYPE_CODE_EDUCATIONINFOR','zhiyuanEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_TYPE_CODE_EDUCATIONINFOR"
						name="EMP_TYPE_CODE" value="${EMP_TYPE_CODE }">
				</td>

				<td width="5%" >
					<!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" />
				</td>
				<td width="5%" >
					<input type="text" id="renzhizhuangtaiEDUCATIONINFOR"
						name="renzhizhuangtaiEDUCATIONINFOR"
						value="${renzhizhuangtaiEDUCATIONINFOR }">
					<a id="renzhiEDUCATIONINFOR" class="" href="#"
						onclick="changeEDUCATIONINFOR('15118','renzhizhuangtaiEDUCATIONINFOR','EMP_OFFICE_EDUCATIONINFOR','renzhiEDUCATIONINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_OFFICE_EDUCATIONINFOR"
						name=EMP_OFFICE value="${EMP_OFFICE }">
				</td>
			</tr>
			
	</table>
	<input type="hidden" id="Edulocalname" value="${personinfo.LOCAL_NAME }">
	<input type="hidden" id="Edulocalempid" value="${personinfo.EMPID }">
	<input type="hidden" id="Edulocalpostgradenoname"
		value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
	<input type="hidden" id="Edulocalpostgradeotherinf"
		value="${personinfo.POST_GRADE_OTHERINF }">
	<input type="hidden" id="Edulocalcenter"
		value="${personinfo.COST_CENTER_TITLE }">
	<input type="hidden" id="Edulocalempofficename"
		value="${personinfo.EMP_OFFICE_NAME_TITLE }">
	<input type="hidden" id="beginSearch" value="education">
</div></div>
<!-- 学历搜索 -->
<div id="education"
	style="width: 99%; margin-left: auto; margin-right: auto;">
		<div class="formBar">

			<ul class="toolBar">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
				</c:if>
				<li>
					<a class="buttonActive" onclick="chaxunEDUCATION('education')"
						href="#"><span><spring:message code="button.search" /><!--查询--></span>
					</a>
				</li>

				<li>
					<a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=49">
						<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span>
					</a>
				</li>
			</ul>
		</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		<table class="orderList" id="hr3606_table" width="99%" border="1" cellpadding="2"
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
						<spring:message code="hrm.empinfo.empid" /><!--工号-->
					</th>
					<th width="5%">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!--部门-->
					</th>
					<th width="5%">
						<spring:message code="hrm.contract.Rank" /><!--职级-->
					</th>
					<th width="5%">
						<spring:message code="hrm.empinfo.DEGREE_CODE" /><!--学历-->
					</th>
					<th width="5%">
						<spring:message code="hrm.recruitManage.START_DATE" /><!--入学日期-->
					</th>
					<th width="5%">
						<spring:message code="hrm.recruitManage.END_DATE" /><!--毕业日期-->
					</th>
					<th width="5%">
						<spring:message code="hrm.recruitManage.INSTITUTION_SCHOOL" /><!--毕业学校-->
					</th>
					<th width="5%">
						<spring:message code="hrm.recruitManage.SUBJECT" /><!--专业-->
					</th>
				</tr>
			</thead>
			<c:forEach items="${educationSearch }" var="a" varStatus="i">
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
						${a.DEGREE_CODE_NAME }
					</td>
					<td class="td_type">
						${a.START_DATE }
					</td>
					<td class="td_type">
						${a.END_DATE }
					</td>
					<td class="td_type">
						${a.INSTITUTION_NAME }
					</td>
					<td class="td_type">
						${a.SUBJECT }
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>
