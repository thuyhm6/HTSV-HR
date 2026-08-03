<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var aa=$("#Punishlocalname").val();
	var bb=$("#Punishlocalempid").val();
	var cc=$("#Punishlocalpostgradenoname").val();
	var dd=$("#Punishlocalpostgradeotherinf").val();
	var ff=$("#Punishlocalcenter").val();
	var gg=$("#Punishlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlenamePUNISHMENT').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
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

function fangdajingPUNISHMENT(flag){
	 var name=encodeURI(encodeURI($('#seach_KEYPUNISHMENT').val()));
	 var dataSearch=$('#beginSearch').val();
	$('#fangdaPUNISHMENT').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=punishmentSearch&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangdaPUNISHMENT').click();
}


$("#hr3609_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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

function chaxunPUNISHMENT(aa){
	$('#punishmentSearch').submit();
}
function chooseLowerPUNISHMENT(aa){
	var che=$('#lowerDepart_'+aa).prop('checked');
	if(che==true){
		$('#lowerDepart_'+aa).attr('value','Y');
	}else{
		$('#lowerDepart_'+aa).attr('value','N');
	}
}
function shanchuPUNISHMENT(){
	$('#titlenamePUNISHMENT').html('');
	$('#PERSON_IDPUNISHMENT').attr('value','');
	$('#LOCAL_TITLEPUNISHMENT').attr('value','');
}
$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,//关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
    "orderClasses": false
});

function changePUNISHINFOR(no,status,id,aid){
	var idvalue=$('#'+id).val();
	var idhref='/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+no+'&firstFlag=N&status='+status+'&nameid='+id+'&typeFlag=Y&idvalue='+idvalue;
	$('#'+aid).attr('href',idhref);
	
}
function changeZhijiPUNISHINFOR(status,id,aid){
	var idvalue=$('#'+id).val();
	var parentnoPUNISHINFOR=$('#POST_FAMILY_PUNISHINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoPUNISHINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="punishmentSearch" method="post" action="/hrm/empinfo/punishmentSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
<!-- <div class="pageContent" style="width: 92%;margin-left:auto;margin-right:auto;">
 -->
 	<input type="hidden" id="PERSON_IDPUNISHMENT" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="LOCAL_TITLEPUNISHMENT" name="LOCAL_TITLE" value="${LOCAL_TITLE }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="punishment">
		 <input type="hidden" id="FLAG" name="FLAG" value="1">
 <div class="pageHeader">
<div class="searchBar" >
<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table"> -->
			<table class="searchContent" >
			
	<tr>
		<td ><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td style="border: 0px;width:30%">
		<div style="float: left"><input type="text" name="seach_KEYPUNISHMENT" id="seach_KEYPUNISHMENT" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingPUNISHMENT('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaPUNISHMENT" onclick="fangdajingPUNISHMENT('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=punishmentSearch" lookupGroup="person">
			</a></div>			<span style="margin-left: 10px;" id="titlenamePUNISHMENT">${LOCAL_TITLE }</span>
			
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
					<ait:deptList name="ISDEPTNO18" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO18"/>
										<ait:deptTreeIcon name="ISDEPTNO18" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO18" selected="${ISDEPTNO18}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_punishment" value="N" onclick="chooseLowerPUNISHMENT('punishment')">
                    <spring:message code="hrm.empinfo.Department_include" /><!--下位部门包括-->
	</tr>
		 
		 <tr>
		     <td width="5%" style="padding-bottom:   4px">
					<spring:message code="hrm.empinfo.Period" /><!--期间-->
				</td>
				<td width="10%" style="padding-bottom:   4px">
					<input type="text" id="START_DATE_PUN" name="START_DATE_PUN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
			  value="${START_DATE_PUN}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_PUN" name="END_DATE_PUN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
			   value="${END_DATE_PUN}" style="float:left;"/>           
                    </td>
             <td width="5%" >
					<spring:message code="hrm.empinfo.punishment_code" /><!--惩罚代码-->
			 </td>
			 <td width="5%" >
				<input type="text" id="chengfalxPUNISHINFOR" name="chengfalxPUNISHINFOR" value="${chengfalxPUNISHINFOR }">
		        <a id="chengfaPUNISHINFOR" class="" href="#" onclick="changePUNISHINFOR('13997','chengfalxPUNISHINFOR','PUNISH_CODE_PUNISHINFOR','chengfaPUNISHINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="PUNISH_CODE_PUNISHINFOR" name="PUNISH_CODE"  value="${PUNISH_CODE }">
		     </td>
		   </tr>
		   <tr>
		     <td width="5%"  style="padding-bottom:   6px">
					<spring:message code="hrm.empinfo.DATE_STARTED" /><!--入社日期-->
				</td>
			<td width="5%" style="padding-bottom:   6px">
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
				<td width="5%"  >
				<input type="text" id="zhiqunPUNISHINFOR" name="zhiqunPUNISHINFOR" value="${zhiqunPUNISHINFOR }">
		        <a id="zhiPUNISHINFOR" class="" href="#" onclick="changePUNISHINFOR('14015812','zhiqunPUNISHINFOR','POST_FAMILY_PUNISHINFOR','zhiPUNISHINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="POST_FAMILY_PUNISHINFOR" name="POST_FAMILY"  value="${POST_FAMILY }">
		        </td>
                    
                    <td width="5%"  >
					<spring:message code="hrm.contract.Rank" /><!--职级-->
				</td>
				
				<td width="5%" >
				<input type="text" id="zhijiPUNISHINFOR" name="zhijiPUNISHINFOR" value="${zhijiPUNISHINFOR }">
		        <a id="jiPUNISHINFOR" class="" href="#" onclick="changeZhijiPUNISHINFOR('zhijiPUNISHINFOR','GRADE_NO_PUNISHINFOR','jiPUNISHINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="GRADE_NO_PUNISHINFOR" name="GRADE_NO"  value="${GRADE_NO }">
		        </td>
		   </tr>
		   
		    <tr>
		    <td width="5%">
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="5%" style="padding-top: 3px">
					<input type="text" id="zhuyaoyewuPUNISHINFOR" name="zhuyaoyewuPUNISHINFOR"
						value="${zhuyaoyewuPUNISHINFOR }">
					<a id="yewuPUNISHINFOR" class="" href="#"
						onclick="changePUNISHINFOR('400098','zhuyaoyewuPUNISHINFOR','DUTY_NO_PUNISHINFOR','yewuPUNISHINFOR')"
						lookupGroup="person"> <input type="button" value="......">
					</a>
					<input type="hidden" id="MAIN_BUSINESS_PUNISHINFOR"
						name="MAIN_BUSINESS" value="${MAIN_BUSINESS }">
				</td>
                <td width="5%" >
					<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型-->
				</td>
				<td width="5%" >
				<input type="text" id="zhiyuanleixingPUNISHINFOR" name="zhiyuanleixingPUNISHINFOR" value="${zhiyuanleixingPUNISHINFOR }">
		        <a id="zhiyuanPUNISHINFOR" class="" href="#" onclick="changePUNISHINFOR('13864','zhiyuanleixingPUNISHINFOR','EMP_TYPE_CODE_PUNISHINFOR','zhiyuanPUNISHINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="EMP_TYPE_CODE_PUNISHINFOR" name="EMP_TYPE_CODE"  value="${EMP_TYPE_CODE }">
		        </td>
		        <td width="5%" >
					<spring:message code="hrm.empinfo.Working_status" /><!--任职状态-->
				</td>
				
				<td width="5%" >
				<input type="text" id="renzhizhuangtaiPUNISHINFOR" name="renzhizhuangtaiPUNISHINFOR" value="${renzhizhuangtaiPUNISHINFOR }">
		        <a id="renzhiPUNISHINFOR" class="" href="#" onclick="changePUNISHINFOR('15118','renzhizhuangtaiPUNISHINFOR','EMP_OFFICE_PUNISHINFOR','renzhiPUNISHINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="EMP_OFFICE_PUNISHINFOR" name="EMP_OFFICE"  value="${EMP_OFFICE }">
		        </td>
		   </tr>
		   
</table>
                    <input type="hidden" id="Punishlocalname" value="${personinfo.LOCAL_NAME }">
					<input type="hidden" id="Punishlocalempid" value="${personinfo.EMPID }">
					<input type="hidden" id="Punishlocalpostgradenoname" value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Punishlocalpostgradeotherinf" value="${personinfo.POST_GRADE_OTHERINF }">
					<input type="hidden" id="Punishlocalcenter" value="${personinfo.COST_CENTER_TITLE }">
					<input type="hidden" id="Punishlocalempofficename" value="${personinfo.EMP_OFFICE_NAME_TITLE }">
			<input type="hidden" id="beginSearch" value="punishment">
</div></div>
<!-- 惩戒搜索 -->
<div  id="punishment" style="width: 99%;margin-left:auto;margin-right:auto;">

		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxunPUNISHMENT('punishment')" href="#">
				<span><spring:message code="button.search" /><!--查询--></span></a>
			</li>
			
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=54">
				<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a>					
			</li>
	</ul>
</div>
		<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		 <table class="orderList" id="hr3609_table" width="99%" border="1" cellpadding="2"
			cellspacing="1">
			<thead>
				<tr>
				 <th width="5%" >NO.</th>
				 <th width="5%" ><spring:message code="hrm.empinfo.name" /><!--姓名--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.empid" /><!--社号--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!--部门--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型--></th>
				 <th width="5%" ><spring:message code="hrm.contract.Rank" /><!--职级--></th>
				 <th width="5%" ><spring:message code="hrm.recruitManage.DATE_STARTED" /><!--入职日期--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.punishment_code" /><!--惩罚代码--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.punishment_day" /><!--惩罚日--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.punishment_organ_name" /><!--惩罚机关名--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.pay_cut_start_date" /><!--减薪开始日--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.pay_cut_end_date" /><!--减薪结束日--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.relieve_day" /><!--解除日--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.reason" /><!--惩戒原因--></th>
				 <th width="8%" ><spring:message code="hrm.empinfo.Personnel_card_inquiry" /><!--人事卡查询与否--></th>
				</tr>
			</thead>
		 <c:forEach items="${punishmentSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.EMP_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.PUNISH_CODE_NAME}</td>
		 <td class="td_type" >${a.PUNISH_DATE}</td>
		 <td class="td_type" >${a.PUNISH_DEPARTMENT}</td>
		 <td class="td_type" >${a.PAYCUT_START_DATE}</td>
		 <td class="td_type" >${a.PAYCUT_END_DATE}</td>
		 <td class="td_type" >${a.RELEASE_DATE}</td>
		 <td class="td_type" >${a.PUNISH_REASON}</td>
		 <td class="td_type"><input type="checkbox" disabled="disabled" <c:if test="${a.PERSONNEL_CARD_INQUIRY eq 'Y'}"> checked="checked" </c:if> ></input></td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>			