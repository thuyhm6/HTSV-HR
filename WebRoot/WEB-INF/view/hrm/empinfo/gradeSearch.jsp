<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var aa=$("#Gradelocalname").val();
	var bb=$("#Gradelocalempid").val();
	var cc=$("#Gradelocalpostgradenoname").val();
	var dd=$("#Gradelocalpostgradeotherinf").val();
	var ff=$("#Gradelocalcenter").val();
	var gg=$("#Gradelocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlenameGRADE').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
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
function fangdajingGRADE(flag){
	 var name=encodeURI(encodeURI($('#seach_KEYGRADE').val()));
	 var dataSearch=$('#beginSearch').val();
	$('#fangdaGRADE').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=gradeSearch&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangdaGRADE').click();
}

function chaxunGRADE(aa){
	$('#gradeSearch').submit();
}
function chooseLowerGRADE(aa){
	var che=$('#lowerDepart_'+aa).prop('checked');
	if(che==true){
		$('#lowerDepart_'+aa).attr('value','Y');
	}else{
		$('#lowerDepart_'+aa).attr('value','N');
	}
}
function shanchuGRADE(){
	$('#titlenameGRADE').html('');
	$('#PERSON_IDGRADE').attr('value','');
	$('#LOCAL_TITLEGRADE').attr('value','');
}
$("#hr3607_table",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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


function changeGRADEINFOR(no,status,id,aid){
	var idvalue=$('#'+id).val();
	var idhref='/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+no+'&firstFlag=N&status='+status+'&nameid='+id+'&typeFlag=Y&idvalue='+idvalue;
	$('#'+aid).attr('href',idhref);
	
}
function changeZhijiGRADEINFOR(status,id,aid){
	var idvalue=$('#'+id).val();
	var parentnoGRADEINFOR=$('#POST_FAMILY_GRADEINFOR').val();
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoGRADEINFOR + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<form id="gradeSearch" method="post" action="/hrm/empinfo/gradeSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		<input type="hidden" id="PERSON_IDGRADE" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="LOCAL_TITLEGRADE" name="LOCAL_TITLE" value="${LOCAL_TITLE }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="grade">
		 <input type="hidden" id="FLAG" name="FLAG" value="1">
<div class="pageHeader">
<div class="searchBar" >
					<input type="hidden" id="Gradelocalname" value="${personinfo.LOCAL_NAME }">
					<input type="hidden" id="Gradelocalempid" value="${personinfo.EMPID }">
					<input type="hidden" id="Gradelocalpostgradenoname" value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Gradelocalpostgradeotherinf" value="${personinfo.POST_GRADE_OTHERINF }">
					<input type="hidden" id="Gradelocalcenter" value="${personinfo.COST_CENTER_TITLE }">
					<input type="hidden" id="Gradelocalempofficename" value="${personinfo.EMP_OFFICE_NAME_TITLE }">
			<input type="hidden" id="beginSearch" value="grade">
<table class="searchContent">
	<tr>
		<td style="border: 0px;"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td style="border: 0px;width:35%">
		<div style="float: left"><input type="text" name="seach_KEYGRADE" id="seach_KEYGRADE" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajingGRADE('onkeyup');"/></div>
		<div style="float: left"><a class="btnLook" id="fangdaGRADE" onclick="fangdajingGRADE('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=gradeSearch" lookupGroup="person">
			</a></div>			<span  id="titlenameGRADE">${LOCAL_TITLE }</span>
			
		<!-- </td>
			<td  style="border: 0px">
			
			 <a class="buttonActive" onclick="shanchuEMERGENCYADDRESS()">
							<span>删除</span>
			</a> -->
		</td>
		<td style="border: 0px;">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
	</td>
				<td style="border: 0px;">
					<ait:deptList name="ISDEPTNO15" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO15"/>
										<ait:deptTreeIcon name="ISDEPTNO15" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO15" selected="${ISDEPTNO15}"/>
    				 <input type="checkbox" name="lowerDepart" id="lowerDepart_grade" value="N" onclick="chooseLowerGRADE('grade')">
    				 <spring:message code="hrm.empinfo.Department_include"/><!--下位部门包括-->
                    </td>
 <td style="border: 0px;" >
					<spring:message code="hrm.recruitManage.DATE_STARTED"/><!--入职日期-->
				</td>
			<td style="border: 0px;" >
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
	</tr>
	<tr style="height: 35px;">
		 <td style="border: 0px;" >
					<spring:message code="hrm.empinfo.POST_FAMILY"/><!--职群-->
				</td>
				<td style="border: 0px;">
				<input type="text" id="zhiqunGRADEINFOR" name="zhiqunGRADEINFOR" value="${zhiqunGRADEINFOR }">
		        <a id="zhiGRADEINFOR" class="" href="#" onclick="changeGRADEINFOR('14015812','zhiqunGRADEINFOR','POST_FAMILY_GRADEINFOR','zhiGRADEINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="POST_FAMILY_GRADEINFOR" name="POST_FAMILY"  value="${POST_FAMILY }">
		        </td>
		          <td style="border: 0px;" >
					<spring:message code="hrm.contract.Rank"/><!--职级-->
				</td>
				
				<td style="border: 0px;" >
				<input type="text" id="zhijiGRADEINFOR" name="zhijiGRADEINFOR" value="${zhijiGRADEINFOR }">
		        <a id="jiGRADEINFOR" class="" href="#" onclick="changeZhijiGRADEINFOR('zhijiGRADEINFOR','GRADE_NO_GRADEINFOR','jiGRADEINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="GRADE_NO_GRADEINFOR" name="GRADE_NO"  value="${GRADE_NO }">
		        </td>
		        <td style="border: 0px;">
					<spring:message code="hrm.empinfo.Working_status"/><!--任职状态-->
		   </td>
		   
		   <td style="border: 0px;" >
				<input type="text" id="renzhizhuangtaiGRADEINFOR" name="renzhizhuangtaiGRADEINFOR" value="${renzhizhuangtaiGRADEINFOR }">
		        <a id="renzhiGRADEINFOR" class="" href="#" onclick="changeGRADEINFOR('15118','renzhizhuangtaiGRADEINFOR','EMP_OFFICE_GRADEINFOR','renzhiGRADEINFOR')" lookupGroup="person">
		        <input type="button" value="......">
		        </a>
		        <input type="hidden" id="EMP_OFFICE_GRADEINFOR" name="EMP_OFFICE"  value="${EMP_OFFICE }">
		        </td>
	</tr>
</table>
</div></div>
<!-- 职级搜索 -->
<div  id="grade" style="width: 99%;margin-left:auto;margin-right:auto;">

		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxunGRADE('grade')" href="#">
				<span><spring:message code="button.search"/><!--查询--></span></a>
			</li>
			
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=51">
				<span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到EXECL--></span></a>					
			</li>
	</ul>
</div>
	<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${count }</div>
		 <table class="orderList" id="hr3607_table" width="99%">
			<thead>
				<tr>
				 <th width="5%" >NO.</th>
				 <th width="5%" ><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.empid"/><!--工号--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></th>
				 <th width="5%" ><spring:message code="hrm.contract.Rank"/><!--职级--></th>
				 <th width="5%" ><spring:message code="hrm.contract.POSITION_NO"/><!--职责--></th>
				 <th width="5%" ><spring:message code="hrm.empinfo.DATE_STARTED"/><!--入社日期--></th>
				 <th width="5%" ><spring:message code="liang.hr.viewPersonalInfo.title.ADVANCEMENT_DATE"/><!--晋升日期--></th>
				</tr>
			</thead>
		 <c:forEach items="${gradeSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.POSITION_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.PROMOTION_DAY }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>			
						