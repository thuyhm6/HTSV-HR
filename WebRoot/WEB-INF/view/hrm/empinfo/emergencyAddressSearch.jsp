<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var aa=$("#Emerlocalname").val();
	var bb=$("#Emerlocalempid").val();
	var cc=$("#Emerlocalpostgradenoname").val();
	var dd=$("#Emerlocalpostgradeotherinf").val();
	var ff=$("#Emerlocalcenter").val();
	var gg=$("#Emerlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlenameEMERGENCYADDRESS').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	
	
	var dataSearch='${dataSearch}';
	if('${lowerDepart}'=='Y'){
		$('#lowerDepart_'+dataSearch).attr('checked','checked');
		$('#lowerDepart_'+dataSearch).attr('value','Y');
	}
	if('${MAIN_LIAISON_OFFICE}'=='Y'){
		$('#mainLianluo').attr('checked','checked');
	}
	if('${FINAL_DEGREE_WHETHER}'=='Y'){
		$('#FINAL_DEGREE_WHETHER').attr('value','Y');
		$('#FINAL_DEGREE_WHETHER').attr('checked','checked');
	}
});
function fangdajingEMERGENCYADDRESS(flag){
	 var name=encodeURI(encodeURI($('#seach_KEYEMERGENCYADDRESS').val()));
	 var dataSearch=$('#beginSearch').val();
	$('#fangdaEMERGENCYADDRESS').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=emergencyAddressSearch&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangdaEMERGENCYADDRESS').click();
}

function chaxunEMERGENCYADDRESS(aa){
$('#emergencyAddressSearch').submit();
}
function chooseLowerEMERGENCYADDRESS(aa){
	var che=$('#lowerDepart_'+aa).prop('checked');
	if(che==true){
		$('#lowerDepart_'+aa).attr('value','Y');
	}else{
		$('#lowerDepart_'+aa).attr('value','N');
	}
}
function shanchuEMERGENCYADDRESS(){
	$('#titlenameEMERGENCYADDRESS').html('');
	$('#PERSON_IDEMERGENCYADDRESS').attr('value','');
	$('#LOCAL_TITLEEMERGENCYADDRESS').attr('value','');
	
}
$(".list",navTab.getCurrentPanel()).dataTable({
	"bPaginate": true,    //分页
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
     "scrollY": $(document.body).height() - 340,
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

function changeEMERINFOR(no,status,id,aid){
	var idvalue=$('#'+id).val();
	var idhref='/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+no+'&firstFlag=N&status='+status+'&nameid='+id+'&typeFlag=Y&idvalue='+idvalue;
	$('#'+aid).attr('href',idhref);
	
}
function changezhijiEMERINFOR(status, id, aid) {
	var idvalue = $('#' + id).val();
	var parentnoEMERINFOR = $('#POST_FAMILY_EMERINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoEMERINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="emergencyAddressSearch" method="post" action="/hrm/empinfo/emergencyAddressSearch" class="pageForm required-validate" 
onsubmit="return navTabSearch(this);" >
<div class="pageHeader">
<div class="searchBar" >
	<input type="hidden" id="PERSON_IDEMERGENCYADDRESS" name="PERSON_ID" value="${PERSON_ID }">
	<input type="hidden" id="LOCAL_TITLEEMERGENCYADDRESS" name="LOCAL_TITLE" value="${LOCAL_TITLE }">
	<input type="hidden" id="dataSearch" name="dataSearch" value="emergencyAddress">
	<input type="hidden" id="FLAG" name="FLAG" value="1">
    <table class="searchContent" >
    <tr>
    	<td style="border: 0px">
    		<!-- 主要联络处与否 --><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" />
    	</td>
    	<td>
    	 	<input type="radio" id="MAIN_LIAISON_OFFICE" name="MAIN_LIAISON_OFFICE" <c:if test="${MAIN_LIAISON_OFFICE ne 'Y'}"> checked="checked" </c:if> value="" />
    	 		<!-- 全部 --><spring:message code="ess.infoApply.whole" />
    	 	<input type="radio" id="MAIN_LIAISON_OFFICE" name="MAIN_LIAISON_OFFICE" <c:if test="${MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> value="Y" />
    	 		<!-- 主要联络处 --><spring:message code="hrm.empinfo.ZHUYAOLIANLUOCHU" />
    	</td>
    </tr>
	<tr>
		<td style="border: 0px"><!-- 工号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td style="border: 0px">
		<div style="float: left"><input type="text" name="seach_KEYEMERGENCYADDRESS" id="seach_KEYEMERGENCYADDRESS" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingEMERGENCYADDRESS('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaEMERGENCYADDRESS" onclick="fangdajingEMERGENCYADDRESS('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=emergencyAddressSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenameEMERGENCYADDRESS">${LOCAL_TITLE }</span>
			
		</td>
		<td style="border: 0px">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!--部门 --></td>
		<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO10" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO10"/>
					<ait:deptTreeIcon name="ISDEPTNO10" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO10" selected="${ISDEPTNO10}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_emergencyAddress" value="N" onclick="chooseLowerEMERGENCYADDRESS('emergencyAddress')">
                    	<spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
        </td>
        <td width="5%">
					<spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系 -->
		</td>
		<td width="5%"  class="td_type">
					<input type="text" id="guanxiEMERINFOR" name="guanxiEMERINFOR" value="${guanxiEMERINFOR }">
		        	<a id="gxEMERINFOR" class="" href="#" onclick="changeEMERINFOR('1693','guanxiEMERINFOR','EMER_TYPE_CODE_EMERINFOR','gxEMERINFOR')" lookupGroup="person">
		        		<input type="button" value="......">
		        	</a>
		        	<input type="hidden" id="EMER_TYPE_CODE_EMERINFOR" name="EMER_TYPE_CODE"  value="${EMER_TYPE_CODE }">
        </td>
    </tr>
    <tr>
		    <td width="5%"  style="padding-bottom:   4px">
					<spring:message code="ess.empInfo.date_of_agency" /><!--入社日期 -->
				</td>
			<td width="25%" style="padding-bottom:   4px">
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
                    <td width="5%"  >
					<spring:message code="hrm.empinfo.POST_FAMILY" /><!--职群 -->
				</td>
				<td width="5%" class="td_type">
				<input type="text" id="zhiqunEMERINFOR" name="zhiqunEMERINFOR" value="${zhiqunEMERINFOR }">
		        <a id="zhiEMERINFOR" class="" href="#" onclick="changeEMERINFOR('14015812','zhiqunEMERINFOR','POST_FAMILY_EMERINFOR','zhiEMERINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="POST_FAMILY_EMERINFOR" name="POST_FAMILY"  value="${POST_FAMILY }">
		        </td>
                <td width="5%" >
					<spring:message code="hrm.contract.Rank" /><!--职级 -->
				</td>
				<td width="5%"  class="td_type">
				<input type="text" id="zhijiEMERINFOR" name="zhijiEMERINFOR" value="${zhijiEMERINFOR }">
		        <a id="jiEMERINFOR" class="" href="#" onclick="changezhijiEMERINFOR('zhijiEMERINFOR','GRADE_NO_EMERINFOR','jiEMERINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="GRADE_NO_EMERINFOR" name="GRADE_NO"  value="${GRADE_NO }">
		        </td>
		        
		   </tr>
    <tr> 
    	<td width="5%" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
		</td>
		<td width="5%">
				<input type="text" id="zhuyaoyewuEMERINFOR" name="zhuyaoyewuEMERINFOR" value="${zhuyaoyewuEMERINFOR }">
		        <a id="yewuEMERINFOR" class="" href="#" onclick="changeEMERINFOR('400098','zhuyaoyewuEMERINFOR','MAIN_BUSINESS_EMERINFOR','yewuEMERINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="MAIN_BUSINESS_EMERINFOR" name="MAIN_BUSINESS"  value="${MAIN_BUSINESS }">
	    </td>
    	<td width="5%" >
					<spring:message code="org.title.EMP_TYPE" /><!--员工类型 -->
				</td>
		<td width="5%"  class="td_type">
				<input type="text" id="yuangongzhuangtaiEMERINFOR" name="yuangongzhuangtaiEMERINFOR" value="${yuangongzhuangtaiEMERINFOR }">
		        <a id="yuangongEMERINFOR" class="" href="#" onclick="changeEMERINFOR('13864','yuangongzhuangtaiEMERINFOR','EMP_TYPE_CODE_EMERINFOR','yuangongEMERINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="EMP_TYPE_CODE_EMERINFOR" name="EMP_TYPE_CODE"  value="${EMP_TYPE_CODE }">
		</td>
		<td width="5%" >
					<!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" />
		</td>
		<td width="5%"  class="td_type">
				<input type="text" id="renzhizhuangtaiEMERINFOR" name="renzhizhuangtaiEMERINFOR" value="${renzhizhuangtaiEMERINFOR }">
		        <a id="renzhiEMERINFOR" class="" href="#" onclick="changeEMERINFOR('15118','renzhizhuangtaiEMERINFOR','EMP_OFFICE_EMERINFOR','renzhiEMERINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="EMP_OFFICE_EMERINFOR" name="EMP_OFFICE"  value="${EMP_OFFICE }">
		</td>
	</tr>
</table>

                    <input type="hidden" id="Emerlocalname" value="${personinfo.LOCAL_NAME }">
					<input type="hidden" id="Emerlocalempid" value="${personinfo.EMPID }">
					<input type="hidden" id="Emerlocalpostgradenoname" value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Emerlocalpostgradeotherinf" value="${personinfo.POST_GRADE_OTHERINF }">
					<input type="hidden" id="Emerlocalcenter" value="${personinfo.COST_CENTER_TITLE }">
					<input type="hidden" id="Emerlocalempofficename" value="${personinfo.EMP_OFFICE_NAME_TITLE }">
			<input type="hidden" id="beginSearch" value="emergencyAddress">
</div>
</div>
<!-- 紧急联络处搜索 -->
<div  id="emergencyAddress" style="width: 99%;margin-left:auto;margin-right:auto;">
		 <div class="formBar">

	  <ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxunEMERGENCYADDRESS('emergencyAddress')" href="#">
				<span><spring:message code="button.search" /><!--查询 --></span></a>
			</li>
			
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=46">
				<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL --></span></a>					
			</li>
	</ul>  
</div>
<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		 <table class="list" width="100%" border="1" cellpadding="2"
			cellspacing="1">
			<thead>
				 
				<tr>
					<th width="5%" >NO.</th>
					<th width="5%" ><spring:message code="hrm.empinfo.empname"/><!-- 员工姓名 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.empid" /><!--社号 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!--部门 --></th>
					<th width="5%" ><spring:message code="hrm.contract.Rank" /><!--职级 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.EMERGENCY_NAME" /><!--联系人姓名 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA"/><!-- 主要联络处与否 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.FAM_PHONE" /><!--联系电话 --></th>
					<th width="5%" ><spring:message code="hrm.empinfo.EMAIL" /><!--E-Mail --></th>
					<th width="5%" ><spring:message code="org.title.ADDRESS"/><!-- 地址 --></th>
				</tr>
			</thead>
		 <c:forEach items="${emergencyAddress }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.EMER_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.EMER_NAME }</td>
		 <td class="td_type"><input type="checkbox" disabled="disabled" <c:if test="${a.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input></td>
		 <td class="td_type" >${a.EMER_PHONE}</td>
		 <td class="td_type" >${a.EMER_EMAIL }</td>
		 <td class="td_type" >${a.EMER_ADDRESS }</td>
		 </tr>
		 </c:forEach>
		 </table>
		 </div>
	</form>	
					
						