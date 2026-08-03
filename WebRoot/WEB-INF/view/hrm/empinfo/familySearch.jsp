<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var aa = $("#localname").val();
	var bb = $("#localempid").val();
	var cc = $("#localpostgradenoname").val();
	var dd = $("#localpostgradeotherinf").val();
	var ff = $("#localcenter").val();
	var gg = $("#localempofficename").val();
	if (gg != "" && typeof (gg) != "undefined") {
		$('#titlenameFAMILY').html(
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
function fangdajingFAMILY(flag) {
	var name = encodeURI(encodeURI($('#seach_KEYFAMILY').val()));
	var dataSearch = $('#beginSearch').val();
	$('#fangdaFAMILY')
			.attr(
					'href',
					'/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='
							+ dataSearch
							+ '&pageNum=1&firstFlag=N&searchChange=familySearch&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$('#fangdaFAMILY').click();
}

function chaxunFAMILY(aa) {
	$('#familySearch').submit();
}
function chooseLowerFAMILY(aa) {
	var che = $('#lowerDepart_' + aa).prop('checked');
	if (che == true) {
		$('#lowerDepart_' + aa).attr('value', 'Y');
	} else {
		$('#lowerDepart_' + aa).attr('value', 'N');
	}
}
function shanchuFAMILY() {
	$('#titlenameFAMILY').html('');
	$('#PERSON_IDFAMILY').attr('value', '');
	$('#LOCAL_TITLEFAMILY').attr('value', '');
}



$("#hr3603_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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

function changeFAMILYINFOR(no, status, id, aid) {
	var idvalue = $('#' + id).val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO=' + no
			+ '&firstFlag=N&status=' + status + '&nameid=' + id
			+ '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);

}
function changeZhijiFAMILYINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoFAMILYINFOR = $('#POST_FAMILY_FAMILYINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoFAMILYINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="familySearch" method="post"
		action="/hrm/empinfo/familySearch" class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
		
		<%-- <input type="hidden" id="PERSON_IDFAMILY" name="PERSON_ID" value="${PERSON_ID }"> --%>
		<input type="hidden" id="LOCAL_TITLEFAMILY" name="LOCAL_TITLE" value="${LOCAL_TITLE }">
		<input type="hidden" id="dataSearch" name="dataSearch" value="family">
		<input type="hidden" id="FLAG" name="FLAG" value="1">
<!-- <div class="pageContent"
	style="width: 92%; margin-left: auto; margin-right: auto;">
 --><div class="pageHeader">
<div class="searchBar" >
	<table class="searchContent">

		<tr>
			<td style="border: 0px" >
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td style="border: 0px;width:30%">
				<div style="float: left"><input type="text" name="KEY" id="KEY" value="${KEY}" /></div>
			</td>
			<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
	</td>
				<td style="border: 0px">
					<ait:deptList name="ISDEPTNO11" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO11"/>
										<ait:deptTreeIcon name="ISDEPTNO11" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO11" selected="${ISDEPTNO11}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_family"
						value="N" onclick="chooseLowerFAMILY('family')">
					<spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括--></td>
                <td width="5%" >
					<spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系-->
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="guanxiFAMILYINFOR" name="guanxiFAMILYINFOR"
						value="${guanxiFAMILYINFOR }">
					<a id="gxFAMILYINFOR" class="" href="#"
						onclick="changeFAMILYINFOR('950','guanxiFAMILYINFOR','FAM_TYPE_CODE_FAMILYINFOR','gxFAMILYINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="FAM_TYPE_CODE_FAMILYINFOR" name="FAM_TYPE_CODE" value="${FAM_TYPE_CODE }">
				</td>
		</tr>
			<tr>
				 <td width="5%" style="padding-top: 4px">
					<spring:message code="hrm.recruitManage.DATE_STARTED" /><!--入职日期-->
				</td>
			<td width="5%" style="padding-top: 4px">
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
				<td width="5%" style="padding-top: 3px">
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群-->
				</td>

				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhiqunFAMILYINFOR" name="zhiqunFAMILYINFOR"
						value="${zhiqunFAMILYINFOR }">
					<a id="zhiFAMILYINFOR" class="" href="#"
						onclick="changeFAMILYINFOR('14015812','zhiqunFAMILYINFOR','POST_FAMILY_FAMILYINFOR','zhiFAMILYINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="POST_FAMILY_FAMILYINFOR"
						name="POST_FAMILY" value="${POST_FAMILY }">
				</td>


				<td width="5%" >   <!-- class="td_title" -->
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>

				<td width="5%" >  <!-- class="td_type" -->
					<input type="text" id="zhijiFAMILYINFOR" name="zhijiFAMILYINFOR"
						value="${zhijiFAMILYINFOR }">
					<a id="jiFAMILYINFOR" class="" href="#"
						onclick="changeZhijiFAMILYINFOR('zhijiFAMILYINFOR','GRADE_NO_FAMILYINFOR','jiFAMILYINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="GRADE_NO_FAMILYINFOR" name="GRADE_NO"
						value="${GRADE_NO }">
				</td>
			</tr>

			<tr>
				<td width="5%" style="padding-top: 4px">
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhuyaoyewuFAMILYINFOR" name="zhuyaoyewuFAMILYINFOR"
						value="${zhuyaoyewuFAMILYINFOR }">
					<a id="yewuFAMILYINFOR" class="" href="#"
						onclick="changeFAMILYINFOR('400098','zhuyaoyewuFAMILYINFOR','MAIN_BUSINESS_FAMILYINFOR','yewuFAMILYINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_FAMILYINFOR"
						name="MAIN_BUSINESS" value="${MAIN_BUSINESS }">
				</td>
				<td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>

				<td width="5%" >
					<input type="text" id="zhiyuanleixingFAMILYINFOR"
						name="zhiyuanleixingFAMILYINFOR"
						value="${zhiyuanleixingFAMILYINFOR }">
					<a id="zhiyuanFAMILYINFOR" class="" href="#"
						onclick="changeFAMILYINFOR('13864','zhiyuanleixingFAMILYINFOR','EMP_TYPE_CODE_FAMILYINFOR','zhiyuanFAMILYINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="EMP_TYPE_CODE_FAMILYINFOR"
						name="EMP_TYPE_CODE" value="${EMP_TYPE_CODE }">
				</td>

				<td width="5%" >
					<!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" />
				</td>
				<td width="5%">
					<input type="text" id="renzhizhuangtaiFAMILYINFOR" name="renzhizhuangtaiFAMILYINFOR" value="${renzhizhuangtaiFAMILYINFOR }">
		        	<a id="renzhiFAMILYINFOR" class="" href="#" onclick="changeFAMILYINFOR('15118','renzhizhuangtaiFAMILYINFOR','EMP_OFFICE_FAMILYINFOR','renzhiFAMILYINFOR')" lookupGroup="person">
		        		<input type="button" value="......">
		        	</a>
		       	    <input type="hidden" id="EMP_OFFICE_FAMILYINFOR" name="EMP_OFFICE"  value="${EMP_OFFICE }">
				</td>
			</tr>
			<tr>
				<td width="5%" >
					<spring:message code="hrm.empinfo.family_name"/><!-- 家属姓名 -->
				</td>
				<td width="5%">
					<input type="type" name="FAM_NAME" id="FAM_NAME" value="${FAM_NAME }">
				</td>
				<td width="5%" >
					<spring:message code="pa.viewResultConfirmSonList.TESHUKOUSHUIRENSHU.b"/><!-- Người phụ thuộc -->
				</td>
				<td width="5%">
					<select id="FAM_DEPENDENT" name="FAM_DEPENDENT" >
							<option value="" ><!-- 全部 --> <spring:message code="pa.salary.canShu.quanBu" /></option>
							<option value="1"  <c:if test="${FAM_DEPENDENT eq '1' }">selected</c:if>><!--Yes --> <spring:message code="ar.viewcycle.content.yes" /></option>
							<option value="0"  <c:if test="${FAM_DEPENDENT eq '0' }">selected</c:if>><!-- No --> <spring:message code="ar.viewcycle.content.no" /></option>
						</select>
				</td>
			</tr>
	</table>
	<input type="hidden" id="localname" value="${personinfo.LOCAL_NAME }">
	<input type="hidden" id="localempid" value="${personinfo.EMPID }">
	<input type="hidden" id="localpostgradenoname"
		value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
	<input type="hidden" id="localpostgradeotherinf"
		value="${personinfo.POST_GRADE_OTHERINF }">
	<input type="hidden" id="localcenter"
		value="${personinfo.COST_CENTER_TITLE }">
	<input type="hidden" id="localempofficename"
		value="${personinfo.EMP_OFFICE_NAME_TITLE }">
	<input type="hidden" id="beginSearch" value="family">
</div>
</div>
<!-- 家庭搜索 -->
<div id="family" style="width: 99%;margin-left:auto;margin-right:auto;">
	
		<div class="formBar">

			<ul class="toolBar">
				<c:if test="${toolbarInfo.INSERTR == '1'}">
				</c:if>
				<li>
					<a class="buttonActive" onclick="chaxunFAMILY('family')" href="#">
					<span><spring:message code="button.search" /><!--查询--></span>
					</a>
				</li>
				<li>
					<a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=47">
						<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span>
					</a>
				</li>
			</ul>
		</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		
		<table class="orderList" id="hr3603_table" width="99%" border="1" cellpadding="2"
			cellspacing="1">
			<thead>
				<tr>
					<th width="2%">NO.</th>
					<th width="5%"><spring:message code="inct.salesman.empName" /><!-- 员工姓名 --></th>
					<th width="5%"><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></th>
					<th width="5%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /> <!-- 部门 --></th>
					<th width="5%"><spring:message code="hrm.contract.Rank" /> <!-- 职级 --></th>
					<th width="5%"><spring:message code="ess.infoApply.employee_type" /> <!-- 员工类型 --></th>
					<th width="5%"><spring:message code="ess.empInfo.sexCode" /> <!-- 性别 --></th>
					<th width="5%"><spring:message code="ess.empInfo.date_of_agency" /> <!-- 入社日期 --></th>
					<th width="5%"><spring:message code="hr.viewCondSql.title.GUANXI" /> <!-- 关系 --></th>
					<th width="5%"><spring:message code="hrm.empinfo.family_name" /> <!-- 家属姓名 --></th>
					<th width="5%"><spring:message code="hrm.empinfo.AGE" /> <!--年龄--></th>
					<th width="5%"><spring:message code="hr.viewPersonalInfo.title.DOB" /> <!--出生日期--></th>
					<th width="5%"><spring:message code="hrm.empinfo.FAM_FAMILY_PHONE" /> <!--家庭电话--></th>
					<th width="5%"><spring:message code="hrm.empinfo.DEGREE_CODE" /> <!--学历--></th>
					<th width="5%"><spring:message code="hrm.empinfo.FAM_COMPANY_NAME" /> <!--工作单位--></th>
					<th width="5%"><spring:message code="pa.viewResultConfirmSonList.TESHUKOUSHUIRENSHU.b"/><!-- Người phụ thuộc --></th>
					<th width="5%"><spring:message code="ar.excelexport.title.armonthfrom" /><!-- từ tháng -->  </th>
					<th width="5%"><spring:message code="ar.excelexport.title.armonthto" /><!-- đến tháng --></th>
				</tr>
			</thead>
			<c:forEach items="${familySearch }" var="a" varStatus="i">
				<tr>
					<td class="td_type">${i.count }</td>
					<td class="td_type">${a.EMPID }</td>
					<td style="text-align: center"> 
						  <a style="cursor: pointer; color: blue" 
						  onclick="navTabNum('/hrm/empinfo/viewPersonalInfo?PERSON_ID=${a.PERSON_ID}','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','<spring:message code="ar.alert.message.viewattendencekeeper.personalInfo" />');">
						  ${a.LOCAL_NAME}
						 </a> 
					</td>
					<td class="td_type">${a.DEPTNO_NAME }</td>
					<td class="td_type">${a.POST_GRADE_NO_NAME }</td>
					<td class="td_type">${a.EMP_TYPE_CODE_NAME }</td>
					<td class="td_type">${a.SEXCODE_NAME }</td>
					<td class="td_type">${a.DATE_STARTED }</td>
					<td class="td_type">${a.FAM_TYPE_CODE_NAME }</td>
					<td class="td_type">${a.FAM_NAME }</td>
					<td class="td_type"><c:if test="${a.AGE>0 }">${a.AGE }</c:if>
					</td>
					<td class="td_type">${a.FAM_BORNDATE }</td>
					<td class="td_type">${a.FAM_FAMILY_PHONE }</td>
					<td class="td_type">${a.FAM_EDUCATION_NAME }</td>
					<td class="td_type">${a.FAM_COMPANY_NAME }</td>
					<td class="td_type"><c:if test="${a.FAM_DEPENDENT eq '1' }"><!--Yes --> <spring:message code="ar.viewcycle.content.yes" /></c:if> 
										<c:if test="${a.FAM_DEPENDENT eq '0' }"><!-- No --> <spring:message code="ar.viewcycle.content.no" /></c:if></td>
					<td class="td_type">${a.FAM_TAX_DATE_START }</td>
					<td class="td_type">${a.FAM_TAX_DATE_END }</td>
				</tr>
			
				
			</c:forEach>
		</table>
		</div>
	</form>

