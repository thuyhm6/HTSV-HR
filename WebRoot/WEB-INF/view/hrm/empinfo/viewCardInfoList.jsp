<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewCardInfoList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewCardInfoListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewCardInfoList_SEQ").change(function(){
		$("#viewCardInfoListForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCardInfoList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCardInfoList&seach_KEY='+name);
    });
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50, 1000], [10,20, 35, 50, 1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 315,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1,2] }
	                     ],
	    "fixedColumns":{leftColumns: 2},
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
        "buttons": [] 
	});
//人事卡
	$("#hrmCard",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var personIds = '';
		var language = "${language }";
		
		$("input[name='viewCardInfoList']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				var index = obj.value;
				personIds = personIds + $("#viewCardInfoList_personId_" + index,navTab.getCurrentPanel()).html() + ",";
			}
		});
		personIds = personIds + "1234951753";
		if (personIds.length == 10) {
			alertMsg.info('<spring:message code="hrm.alert.empinfo.No_selected_objects" />');   //没有选择的对象
			return;
		}
		downloadExcel('viewCardInfoListForm','/hrm/report/payReport04?checkVal=' + $("#reportType",navTab.getCurrentPanel()).val() + '&filename=card&EMPID_STR=' + personIds + '&LANG=' + language,'/hrm/empinfo/viewCardInfoList');
	});
	
	//人事卡（无评价）
	$("#hrmCardNoEvs",navTab.getCurrentPanel()).click(function(){	
	    var reportType = $("#reportType option:selected").val();
		//获取页面的值
		var personIds = '';
		var language = "${language }";
		$("input[name='viewCardInfoList']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				var index = obj.value;
				personIds = personIds + $("#viewCardInfoList_personId_" + index,navTab.getCurrentPanel()).html() + ",";
			}
		});
		personIds = personIds + "1234951753";
		if (personIds.length == 10) {
			alertMsg.info('<spring:message code="hrm.alert.empinfo.No_selected_objects" />');   //没有选择的对象
			return;
		}
		if(reportType == 'report6'){
		   downloadExcel('viewCardInfoListForm','/hrm/report/payReport04?checkVal=report9&filename=cardNoEvs&EMPID_STR=' + personIds + '&LANG=' + language,'/hrm/empinfo/viewCardInfoList');
		}else if(reportType == 'report8'){
		   downloadExcel('viewCardInfoListForm','/hrm/report/payReport04?checkVal=report10&filename=cardDetailNoEvs&EMPID_STR=' + personIds + '&LANG=' + language,'/hrm/empinfo/viewCardInfoList');
		}
	});
	$("#reportType",navTab.getCurrentPanel()).change(function(){
		var reportType = $("#reportType option:selected").val();
		if(reportType != "report7"){
		    $("#hrmCardHidden",navTab.getCurrentPanel()).attr("style","visibility: visible;")
		}else{
		    $("#hrmCardHidden",navTab.getCurrentPanel()).attr("style","visibility: hidden;")
		}
	});
});
//HAE人事卡   越南语
$("#hrmCardHAE",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var personIds = '';
		
		$("input[name='viewCardInfoList']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				var index = obj.value;
				personIds = personIds + $("#viewCardInfoList_personId_" + index,navTab.getCurrentPanel()).html() + ",";
			}
		});
		personIds = personIds + "1234951753";
		if (personIds.length == 10) {
			alertMsg.info('<spring:message code="hrm.alert.empinfo.No_selected_objects" />');   //没有选择的对象
			return;
		}
		downloadExcel('viewCardInfoListForm','/hrm/report/payReport04?checkVal=report11&filename=card&EMPID_STR=' + personIds,'/hrm/empinfo/viewCardInfoList');
	});
//HAE人事卡   英语版
$("#hrmCardNoEvsHAE",navTab.getCurrentPanel()).click(function(){	
	//获取页面的值
	var personIds = '';
	
	$("input[name='viewCardInfoList']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			var index = obj.value;
			personIds = personIds + $("#viewCardInfoList_personId_" + index,navTab.getCurrentPanel()).html() + ",";
		}
	});
	personIds = personIds + "1234951753";
	if (personIds.length == 10) {
		alertMsg.info('<spring:message code="hrm.alert.empinfo.No_selected_objects" />');   //没有选择的对象
		return;
	}
	downloadExcel('viewCardInfoListForm','/hrm/report/payReport04?checkVal=report12&filename=card&EMPID_STR=' + personIds,'/hrm/empinfo/viewCardInfoList');
});


function changeZhijiCARD(status, id, aid) {
	var idvalue = $('#' + id).val();
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
		var parentnoCARD = "'14015813','14015815'";
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	var parentnoCARD = "'14015813','14015814'";
</c:if>
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoCARD + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}
</script>
<div>
<form id="viewCardInfoListForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewCardInfoList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td width="7%"><spring:message code="hrm.empinfo.nameAndEmpid"/><!-- 社号/姓名 --></td>
		<td width="23%">
			<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
			<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
		</td>
		<td width="50%" colspan="3">
			<c:if test="${not empty personInfo}">
				<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.STATUS_CODE_NAME }</span>
			</c:if>
		</td>
		<td width="20%"></td>
	</tr>
	<tr>
		<td><spring:message code="hrm.recruitManage.DATE_STARTED"/><!-- 入职日期 --></td>
		<td>
			<input type="text" id="seach_START_DATE_JOIN" name="seach_START_DATE_JOIN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE_JOIN }"/>~
			<input type="text" id="seach_END_DATE_JOIN" name="seach_END_DATE_JOIN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE_JOIN }"/>
		</td>
		<td><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="hr" id="viewCardInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewCardInfoList_seachDept" selected="${DEPTNO}"/>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
			<spring:message code="hrm.empinfo.Department_include"/><!-- 下位部门包括 -->
		</td>
		<td><spring:message code="ess.infoApply.renzhizhuangtai"/><!-- 任职状态 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_OFFICE" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE}" selectedNm="${EMP_OFFICE_NAME}"/></td>
	</tr>
	<tr>
		<td><spring:message code="hrm.recruitManage.LEAVE_DATE"/><!-- 离职日期 --></td>
		<td>
			<input type="text" id="seach_START_DATE_LEFT" name="seach_START_DATE_LEFT" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE_LEFT }"/>~
			<input type="text" id="seach_END_DATE_LEFT" name="seach_END_DATE_LEFT" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE_LEFT }"/>
		</td>
		<td><spring:message code="ess.trans.title.postGradeName"/><!-- 职级 --></td>
		<td>
			<!--<ait:selectCodeMulti id="seach_POST_GRADE_NO" name="seach_POST_GRADE_NO_NAME" 
				parentNo="14015578" selected="${POST_GRADE_NO}" selectedNm="${POST_GRADE_NO_NAME}"/>
			--><input type="text" id="zhijiCARD" name="zhijiCARD"
						value="${zhijiCARD }">
			<a id="jiCARD" class="" href="#"
				onclick="changeZhijiCARD('zhijiCARD','GRADE_NO_CARD','jiCARD')"
				lookupGroup="person"> <input type="button" value="......">
			</a>
			<input type="hidden" id="GRADE_NO_CARD" name="GRADE_NO" value="${GRADE_NO }">
		</td>
		<td><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!-- 员工类型 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE_NAME" parentNo="13864" selected="${EMP_TYPE_CODE}" selectedNm="${EMP_TYPE_CODE_NAME}"/></td>
	</tr>
</table>
</div>
</form>
</div>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
<div class="formBar">
	<ul class="toolBar">
		<li><!--格式  --><spring:message code="hrm.empinfo.GESHI.Z" />
			<select id="reportType" onchange="hrmCardHidden()">
				<option value="report6">Summary<!-- <spring:message code="hrm.empinfo.ZONGJIE.Z" /></option>总结 -->
				<option value="report8">Detail<!-- <spring:message code="hrm.empinfo.XIANGXI.Z" /></option>详细 -->
				<option value="report7">List<!-- <spring:message code="hrm.empinfo.LIEBIAO.Z" /></option>列表 -->
			</select>
		</li>
		<li><a class="buttonActive" id="viewCardInfoList_Serch" href="#"><span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
		<li><a class="delete" id="hrmCard" href="#"><span><!-- 人事卡 --><spring:message code="hrm.empinfo.personnel_card" /></span></a></li>
		<li id="hrmCardHidden" ><a class="delete" id="hrmCardNoEvs" href="#"><span><!-- 人事卡(无评价) --><spring:message code="hrm.empinfo.RENSHIKA_WUPINJIA.Z" /></span></a></li>
	</ul>
</div>
</c:if>
<c:if test="${LoginUser.cpnyId eq 'HAE'}">
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewCardInfoList_Serch" href="#"><span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
		<li><a class="delete" id="hrmCardHAE" href="#"><span><!-- 人事卡 --><spring:message code="hrm.empinfo.personnel_card" />（<spring:message code="hrm.login.YUENANYU.Z" />）</span></a></li>
		<li><a class="delete" id="hrmCardNoEvsHAE" href="#"><span><!-- 人事卡 --><spring:message code="hrm.empinfo.personnel_card" />（<spring:message code="hrm.login.ENGLISH.Z" />）</span></a></li>
	</ul>
</div>
</c:if>
<div class="pageContent">
				
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th width="5%">No.</th>
							<th width="5%">
						    	<input type="checkbox" class="checkboxCtrl" group="viewCardInfoList">
						    </th>
							<th width="10%"><spring:message code="hrm.empinfo.name"/><!-- 姓名 --></th>
							<th width="10%"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --></th>
							<th width="15%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></th>
							<th width="15%"><spring:message code="hrm.empinfo.HEAD_DEPARTMENT"/><!-- 部门长 --></th>
							<th width="15%"><spring:message code="hrm.contract.Rank"/><!-- 职级 --></th>
							<th width="14%"><spring:message code="hrm.empinfo.DATE_STARTED"/><!-- 入社日期 --></th>
							<th width="10%"><spring:message code="hrm.empinfo.EMP_OFFICE_NAME"/><!-- 员工状态 --></th>
							<!--<th width="7%"><spring:message code="hrm.empinfo.personnel_card"/> 人事卡 </th>
						--></tr>
					</thead>
					<tbody>
						<c:forEach items="${viewCardInfoList}" var="item" varStatus="i">
							<tr target="PERSON_ID" rel="${item.PERSON_ID}">
								<td class='td_center'>${i.count}</td>
							   	<td class='td_center'>
							   		<input type="checkbox" name="viewCardInfoList" value= "${i.index}"/>
									<div id="viewCardInfoList_personId_${i.index}" style="display:none">${item.PERSON_ID}</div>
									
								</td>
								<td>${item.LOCAL_NAME}</td>
								<td>${item.EMPID}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.HEAD_DEPARTMENT}</td>
								<td>${item.POST_GRADE_NO_NAME}</td>
								<td>${item.DATE_STARTED}</td>
								<td>${item.EMP_OFFICE_NAME}</td>
								<!--<td class='td_center'><a href="#" onclick="downloadHrmCard(${item.PERSON_ID})">
								<spring:message code="hrm.empinfo.personnel_card"/> 人事卡 </a></td>
							--></tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
