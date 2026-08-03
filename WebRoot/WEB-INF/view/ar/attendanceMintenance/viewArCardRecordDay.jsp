<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
$(document).ready(function(){
	$("#insertMacRecordDayList_search",navTab.getCurrentPanel()).click(function(){
		$("#viewarcardrecordDayForm",navTab.getCurrentPanel()).submit();
	});
	$('input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	$("#viewarcardrecordDayForm",navTab.getCurrentPanel()).submit();
	    }
	});
	
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	var stime = $('#seach_STIME', navTab.getCurrentPanel()).val();
        var rtime = $('#seach_RTIME', navTab.getCurrentPanel()).val();
        
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArCardRecordDay&seach_KEY='+name+'&STIME='+stime+'&RTIME='+rtime);
    });

	$(".list",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": $(document.body).width() - 30,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
	    	"sProcessing": "<spring:message code='ess.message.loading' />", //正在加载中......
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />", //查询不到相关数据！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />", //表中无数据存在！
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />", //快速筛选
	        "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />", //每页 _MENU_ 条记录
	        "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",  //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />", //(从 _MAX_ 条记录过滤)
	        "oPaginate": {
	            "sPrevious": "<spring:message code='ess.message.previous_page' />", //上一页
	            "sNext": "<spring:message code='ess.message.next_page' />" //下一页
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
		});
});

function insertMacRecordDayList(form,callback,flag){
    var $form=$('#viewarcardrecordDayForm',navTab.getCurrentPanel());
    var STIME = $("#seach_STIME",navTab.getCurrentPanel()).val();
    STIME_FORMAT = STIME.substring(6,10)+"-"+STIME.substring(3,5)+"-"+STIME.substring(0,2);
	var RTIME = $("#seach_RTIME",navTab.getCurrentPanel()).val();
	RTIME_FORMAT = RTIME.substring(6,10)+"-"+RTIME.substring(3,5)+"-"+RTIME.substring(0,2);
	var EMPID = $("#seach_EMPID",navTab.getCurrentPanel()).val();
    $.ajax({
		type: 'POST',
		url: "/ar/attendanceMintenance/insertMacRecordListLGE?STIME="+STIME_FORMAT+"&RTIME="+RTIME_FORMAT+"&EMPID="+EMPID,
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("viewarcardrecordDayForm");
				alertMsg.correct(data.message);
			}else{
				if(data.result=="2"){
					alertMsg.info(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}   
   	 	}  ,
		error: DWZ.ajaxError
	});
}
</script>
<div class="pageHeader">
	<form id="viewarcardrecordDayForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArCardRecordDay?firstFlag=N" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left">
							<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
							<input type="hidden" name="seach_EMPID" id="seach_EMPID" value="${personInfo.EMPID}"/>
							<input type="hidden" name="seach_PERSON_ID" id="seach_PERSON_ID" value="${personInfo.PERSON_ID}"/>
						</div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td></td>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${STIME }"/>~
						<input type="text" id="seach_RTIME" name="seach_RTIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${RTIME }"/>
					</td>
					<td>
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!-- 职级 --><spring:message code="sys.postManage.title.postGrade" /></td>
					<td>
					 <ait:SelectSyCodeCombinByCpnyID name="seach_POST_GRADE_NO" combinParentNo="14015813,14015815" 
											cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${POST_GRADE_NO }"/>
					</td>
					<td><!--缺卡状态--><spring:message code="ar.viewArCardRecordDay.QUEKAZHUANGTAI.b" /></td>
					<td>
						 <select id="seach_CARD_FLAG" name="seach_CARD_FLAG">
						      <option value="" <c:if test="${CARD_FLAG eq null || CARD_FLAG eq ''}" >selected</c:if>><!--全部--><spring:message code="org.title.ALL" /></option>
						      <option value="1" <c:if test="${CARD_FLAG eq '1'}" >selected</c:if>><!--缺失上班卡--><spring:message code="ar.viewArCardRecordDay.QUESHISHANGBANKA.b" /></option>
						      <option value="2" <c:if test="${CARD_FLAG eq '2'}" >selected</c:if>><!--缺失下班卡--><spring:message code="ar.viewArCardRecordDay.QUESHIXIABANKA.b" /></option>
						      <option value="3" <c:if test="${CARD_FLAG eq '3'}" >selected</c:if>><!--全部缺失--><spring:message code="ar.viewArCardRecordDay.QUANBUQUESHI.b" /></option>
						      <option value="4" <c:if test="${CARD_FLAG eq '4'}" >selected</c:if>><!--不缺卡--><spring:message code="ar.viewArCardRecordDay.BUQUEKA.b" /></option>
						 </select>
					</td>
					<td ><!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/> </td>
					<td >
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${shiftList}" var="result">
								<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<!--<td><spring:message code="hrm.empinfo.POST_FAMILY"/> 职群 </td>
					<td><ait:selectCodeMulti id="seach_POST_FAMILY_Multi"
							name="seach_POST_FAMILY_NAME" parentNo="14015812"
							selected="${POST_FAMILY_Multi}" selectedNm="${POST_FAMILY_NAME}" /></td>
					-->
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			<li>	            
				<a class="buttonActive" id="insertMacRecordDayList" onclick="insertMacRecordDayList('viewarcardrecordDayForm',DWZ.ajaxDone,'1')" href="#"><span><!-- 读取刷卡记录 --><spring:message code="ess.infoApply.getcardinfo"/></span></a> 
			</li>
			<li>
				<a class="buttonActive" id="insertMacRecordDayList_search" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li><a class="buttonActive" onclick="downloadExcel('viewarcardrecordDayForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=152&firstFlag=N&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewArCardRecordDay?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	</ul>
</div>
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(getArCardRecordList)}</div>
	<table class="list" width="98%">
		<thead>
			<tr>
				<th width="2%" align="center" >No.</th>
				<th width="5%"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="7%"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="7%">TEAM</th>
				<th width="10%"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="7%"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></th>
				<th width="5%"><!-- 考勤日期 --><spring:message code="ess.infoApply.attendance_date"/></th>
				<th width="5%"><!-- 进门日期 --><spring:message code="ar.viewArCardRecordDay.JINMENRIQI.b"/></th>
				<th width="5%"><!-- 进门时间 --><spring:message code="ess.infoApply.in_door_time"/></th>
				<th width="5%"><!-- 出门日期 --><spring:message code="ar.viewArCardRecordDay.CHUMENRIQI.b"/></th>
				<th width="5%"><!-- 出门时间 --><spring:message code="ess.infoApply.out_door_time"/></th>
				<th width="7%"><!-- 备注  --><spring:message code="ar.viewarcardrecord.title.beizhu"/></th>
				<th width="7%"><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></th>
				<th width="7%"><!--班组Person--><spring:message code="hrm.empinfo.UPDATED_BY" /> <spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></th>
				<th width="7%"><!--班组类型-->Eat time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${getArCardRecordList}" var="list" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPT_TEAM}</td>
					<td>${list.DEPTNAME}</td>
					<td>${list.POST_GRADE_NO_NAME}</td>
					<td>${list.AR_DATE_STR}</td>
					<td>${list.IN_DAY}</td>
					<td>${list.IN_TIME}</td>
					<td>${list.OUT_DAY}</td>
					<td>${list.OUT_TIME}</td>
					<td>${list.LEAVE_CONTENT}  ${list.REMAX}</td>
					<td>${list.SHIFT_NAME} (${list.SHIFT_TIME})</td>
					<td>${list.CHANGE_SHIFT_PERSON}</td>
					<td>${list.EAT_TIMES}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
