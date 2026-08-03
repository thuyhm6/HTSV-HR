<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsTargetInfoList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsTargetInfoListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewAffirmTarget1ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsTargetInfoListForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsTargetInfoList&seach_KEY='+name+'&seach_RESUME_SEQ=${RESUME_SEQ}' + '&seach_evsType=${evsType }');
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsTargetInfoList&seach_KEY='+name+'&seach_RESUME_SEQ=${RESUME_SEQ}' + '&seach_evsType=${evsType }');
   });

	$(".evsList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 340,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "fixedColumns":false,
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
});
function changeURL_item(personId,resumeSEQ,seq,evsItemCnt){ 
	var href = "/evs/manage/viewConfirmTargetInfo?EVS_PERSON_ID="+ personId + "&EVS_PERSON_ID="+ personId +"&RESUME_SEQ="+ resumeSEQ +"&EVS_OBJECT_SEQ="+ seq +"";
	var evsItemCnt = evsItemCnt;
	$.pdialog.open(href,"evs0106", evsItemCnt, {width:1000,height:600,mask:true});
}
</script>
<div class="pageHeader">
	<form id="viewEvsTargetInfoListForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsTargetInfoList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewAffirmTarget1ResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
				<tr>
					<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="hr" id="viewEvsTargetInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewEvsTargetInfoList_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
					</td>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_GROUP" name="seach_EVS_GROUP_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_GROUP" selected="${EVS_GROUP}" selectedNm="${EVS_GROUP_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsTargetInfoList_Serch">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExcel('viewEvsTargetInfoListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=183','/evs/manage/viewEvsTargetInfoList')">
							<span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到Excel--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${objectListSize}</div>
				<table  class="evsList" width="1200px;">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="80px"><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
							<th width="80px"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
							<th width="80px"><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG"/><!--部门名--></th>
							<th width="80px"><spring:message code="hr.viewCondSql.title.ZHIJI"/><!--职级--></th>
							<th width="80px"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
							<th width="80px"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="80px"><spring:message code="evs.viewEvsTargetInfoList.PINGJIAXIANGMUSHU.a"/><!--评价项目数--></th>
							<th width="60px"><spring:message code="evs.viewConfirmTargetInfoHTSVAbility.QUANZHONG.a"/><!--体重--></th>
							<th width="150px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="100px"><spring:message code="org.title.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${objectList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.LOCAL_NAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.EMPID}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.DEPTNAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.POST_GRADE_NAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;" class='td_center'>${item.DATE_STARTED}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;" class='td_center'>${item.ACTIVITY_NAME}</td>
								<td class="td_type" style="text-align:center; cursor: pointer; white-space:nowrap;text-overflow:ellipsis;" id="codeChange" onclick='javascript:changeURL_item(${item.PERSON_ID },"${RESUME_SEQ }","${item.SEQ}", "${item.EVS_ITEM_CNT }");'>
           							<span style="color: blue">${item.EVS_ITEM_CNT }</span></td>
								<%-- <td style="white-space:nowrap;text-overflow:ellipsis;" class='td_center'><a href="/evs/manage/viewConfirmTargetInfo?EVS_PERSON_ID=${item.PERSON_ID}&RESUME_SEQ=${RESUME_SEQ }&EVS_OBJECT_SEQ=${item.SEQ}" 
								target="dialog" width="800" height="600" mask="true" style="color:blue;">${item.EVS_ITEM_CNT}</a></td> --%>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.EVS_SCORE_SUM}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.UPDATED_BY}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;text-align:center;">${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
