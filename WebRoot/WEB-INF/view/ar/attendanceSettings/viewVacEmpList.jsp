<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewVacEmpList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewVacEmpListForm",navTab.getCurrentPanel()).submit();
	});
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	 	var YEAR=encodeURI(encodeURI($('#seach_YEAR',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewVacEmpList&seach_KEY='+name+'&seach_YEAR='+YEAR);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var YEAR=encodeURI(encodeURI($('#seach_YEAR',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewVacEmpList&seach_KEY='+name+'&seach_YEAR='+YEAR);
   });
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
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
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [] }
	                     ],
	    "fixedColumns":{leftColumns: 3},
        "oLanguage": {//多语言配置
	    	 //正在加载中......
	     	 "sProcessing": "<spring:message code='ess.message.loading' />",
	         //查询不到相关数据！
	         "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	         //表中无数据存在！
	         "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	         //快速筛选
	         "sSearch": "<spring:message code='ess.message.rapid_screening' />",
	         //每页 _MENU_ 条记录
	         "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
	         //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	         "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
	         //(从 _MAX_ 条记录过滤)
	         "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
	         "oPaginate": {
	             //上一页
	             "sPrevious": "<spring:message code='ess.message.previous_page' />",
	             //下一页
	             "sNext": "<spring:message code='ess.message.next_page' />"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [] 
	});
	
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ar0232();
	});

	initEditFun_ar0232();
	
	//年假生成
	$("#viewVacEmpList_Create",navTab.getCurrentPanel()).click(function(){
		var year = $("#seach_YEAR",navTab.getCurrentPanel()).val();
		//确定要生成年假吗？
		alertMsg.confirm("<spring:message code='ar.viewVacEmpList.QUEDINGSHENGCHENGNIANJIAMA.b' />",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ar/attendanceVacations/yearVacationCal',
					data: { YEAR:year },
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	//保存
	$("#viewVacEmpList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");
				//jsonData += ' "MENT_VAC": "' + $("#MENT_VAC" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "ADD_VAC": "' + $("#ADD_VAC" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "REMARK": "' + $("#REMARK" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "VACATION_NO": "' + $("#VACATION_NO_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			//没有需要保存的数据
			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA' />");
			return;
		}
        //确定要保存吗？
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ar/attendanceSettings/saveEmpVacInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});
function changeModifyFlag(obj,index){
	$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
}
function initEditFun_ar0232(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var type = $(this).attr("sysType");
	        $(this).html(val);
			this.editing = false;
			if(type == 'ADD_VAC'){
				var value = $(this).attr("sysOldValue");
				$("#leaveVac_" + index,navTab.getCurrentPanel()).html(parseFloat($("#leaveVac_" + index,navTab.getCurrentPanel()).html()) - parseFloat(value) + parseFloat(val));
				$("#totalVac_" + index,navTab.getCurrentPanel()).html(parseFloat($("#totalVac_" + index,navTab.getCurrentPanel()).html()) - parseFloat(value) + parseFloat(val));

				$(this).attr("sysOldValue",val);
			}
			/*if(type == 'MENT_VAC'){
				var value = $(this).attr("sysOldValue");
				$("#leaveVac_" + index,navTab.getCurrentPanel()).html(parseFloat($("#leaveVac_" + index,navTab.getCurrentPanel()).html()) + parseFloat(value) - parseFloat(val));
				$(this).attr("sysOldValue",val);
			}*/
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		}
	});
}
$("#viewVacEmpList_Clear",navTab.getCurrentPanel()).click(function(){	
	var search_year = $("#seach_YEAR",navTab.getCurrentPanel()).val();
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1; 
	if(search_year != year){
		//请选择当前年,进行年假清算!
		alertMsg.error("<spring:message code='alert.message.QINGXUANZEDANGQIANNIAN.b' />"); 
		return false;
    }
	if(month != 1 && month != 2){
		alertMsg.error("<spring:message code='alert.message.QINGSUANZHINENGZAIYIERYUEFEN.b' />"); //上一年剩余年假只能在今年1,2月份清算!
		return false;
    }
	var href = "/ar/attendanceSettings/viewVacClearInfo?year=" + year;
	$.pdialog.open(href,"ar0234", "<spring:message code='ar.viewVacEmpList.NIANJIAQINGSUAN.b' />", {width:300,height:120,mask:true}); //年假清算
    
});
</script>
<div class="pageHeader">
<form id="viewVacEmpListForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewVacEmpList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
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
		<td><!-- 年份 --><spring:message code="pa.payear.title.payear" /></td>
		<td>
			<ait:date yearName="seach_YEAR"  yearSelected="${YEAR}"  yearPlus="10"/>
		</td>
		
		<!--<td> 基准日 <spring:message code="ess.title.JIZHUNRI" /></td>
		<td>
			<input type="text" id="seach_YEAR" name="seach_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${YEAR}"/>
		</td>-->
		
		<td ><!-- 部门 --><spring:message code="org.title.dept" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="hr" id="viewVacEmpList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewVacEmpList_seachDept" selected="${DEPTNO}"/>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
		</td>
		<td><!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
		<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"  limit="all"/>
		</td>
		<td><!-- 职级 --><spring:message code="sys.postManage.title.postGrade" /></td>
		<td>
		  <ait:SelectSyCodeCombinByCpnyID name="seach_POST_GRADE_NO" combinParentNo="14015813,14015815" 
			cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${POST_GRADE_NO }"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewVacEmpList_Clear" href="#"><span><!-- 年假清算 --><spring:message code="ar.viewVacEmpList.NIANJIAQINGSUAN.b" /></span></a></li>
		<li><a class="buttonActive" id="viewVacEmpList_Serch" href="#"><span><!-- 查询 --><spring:message code="ess.infoApply.SELECT" /></span></a></li>
		<li><a class="buttonActive" id="viewVacEmpList_Save" href="#"><span><!-- 保存 --><spring:message code="hrm.contract.save" /></span></a></li>
		<li><a class="delete" href="#" onclick="downloadExcel('viewVacEmpListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=280','/ar/attendanceSettings/viewVacEmpList')"><span><!-- 导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1950px">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<!--<th style="text-align: center" width="5px;">
							    <input type="checkbox" class="checkboxCtrl" group="BATCH_LEAVE" />
							</th>-->
							<th width="80px"><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID" /></th>
							<th width="120px"><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></th>
							<th width="200px"><!-- 部门名 --><spring:message code="ess.infoApply.DEPT_NAME" /></th>
							<th width="90px"><!-- 职级 --><spring:message code="sys.postManage.title.postGrade" /></th>
							<!--<th width="100px"> 工作地 <spring:message code="pa.wagebase.title.workArea" /></th>-->
							<th width="80px"><!-- 入社日期 --><spring:message code="hrm.empinfo.DATE_STARTED" /></th>
							<!--<th width="100px"> 在职时间 <spring:message code="ess.empInfo.in_service_time" /></th>-->
							<th width="80px"><!-- 开始日期 --><spring:message code="public.title.startDate" /></th>
							<th width="80px"><!-- 结束日期 --><spring:message code="public.title.endDate" /></th>
							<th width="100px"><!-- 上年剩余年假 --><spring:message code="ar.viewVacEmpList.SHANGNIANSHENGYUNIANJIA.b" /></th>
							<th width="100px"><!-- 生成年假 --><spring:message code="ar.viewVacEmpList.SHENGCHENGNIANJIA.b" /></th>
							<th width="100px" class="titleColor"><!-- 特殊年假 --><spring:message code="ar.viewVacEmpList.TESHUNIANJIA.b" /></th>
							<th width="100px"><!-- 总年假 --><spring:message code="liang.ess.infoApply.title.totalAnnualLeave" /></th>
							<th width="100px"><!-- 已休年假 --><spring:message code="ar.viewVacEmpList.YIXIUNIANJIA.b" /></th>
							<th width="100px"><!-- 审批个数 --><spring:message code="ar.viewVacEmpList.SHENPIGESHU.b" /></th>
							<th width="40px"><!-- 一月 --><spring:message code="hrm.empinfo.January" /></th>
							<th width="40px"><!-- 二月 --><spring:message code="hrm.empinfo.February" /></th>
							<th width="40px"><!-- 三月 --><spring:message code="hrm.empinfo.March" /></th>
							<th width="40px"><!-- 四月 --><spring:message code="hrm.empinfo.April" /></th>
							<th width="40px"><!-- 五月 --><spring:message code="hrm.empinfo.May" /></th>
							<th width="40px"><!-- 六月 --><spring:message code="hrm.empinfo.June" /></th>
							<th width="40px"><!-- 七月 --><spring:message code="hrm.empinfo.July" /></th>
							<th width="40px"><!-- 八月 --><spring:message code="hrm.empinfo.August" /></th>
							<th width="40px"><!-- 九月 --><spring:message code="hrm.empinfo.September" /></th>
							<th width="50px"><!-- 十月 --><spring:message code="hrm.empinfo.October" /></th>
							<th width="50px"><!-- 十一月 --><spring:message code="hrm.empinfo.November" /></th>
							<th width="50px"><!-- 十二月 --><spring:message code="hrm.empinfo.December" /></th>
							<!--<th width="100px" class="titleColor"> 调整个数 <spring:message code="ar.viewVacEmpList.TIAOZHENGGESHU.b" /></th>
							<th width="100px" class="titleColor"> 结算年假 <spring:message code="ar.viewVacEmpList.JIESUANNIANJIA.b" /></th>-->
							<th width="60px"><!-- 剩余 --><spring:message code="ess.infoApply.Remainder" /></th>
							<!--<th width="130px">未生成年假原因 <spring:message code="ar.viewVacEmpList.WEISHENGCHENGNIANJIAYUANYIN.b" /></th>-->
							<th width="100px"><!-- 备注 --><spring:message code="org.title.REMARK" /></th>
							<th width="240px"><!-- 变更者 --><spring:message code="org.title.UPDATED_IP" /></th>
							<th width="120px"><!-- 变更时间 --><spring:message code="hrm.empinfo.UPDATE_DATE" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewVacEmpList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<!--<td class='td_center'>
							    	<input type="checkbox" id="BATCH_LEAVE_${i.index}" sysIndex="${i.index}" name="BATCH_LEAVE" value="${item.PERSON_ID}" />
							    </td>-->
								<td>${item.EMPID}</td>
								<td>${item.LOCAL_NAME}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<!--<td>${item.WORK_AREA_NAME}</td>-->
								<td class='td_center'>${item.DATE_STARTED}</td>
								<!--<td class='td_center'>${item.WORK_TIME}</td>-->
								<td class='td_center'>${item.STRT_DATE}</td>
								<td class='td_center'>${item.END_DATE}</td>
								<td class='td_center'>${item.LAST_YEAR_VAC}</td> <!-- 上年剩余年假 -->
								<td>${item.TOT_VAC_CNT}</td><!-- 生成年假 -->
								<td sysLog="text" sysIndex="${i.index}" sysOldValue="${item.ADD_VAC}" sysType="ADD_VAC" id="ADD_VAC${i.index}">${item.ADD_VAC}</td> <!-- 特殊年假 -->
								<td id="totalVac_${i.index}">${item.TOT_VAC_CNT + item.LAST_YEAR_VAC + item.ADD_VAC}</td> <!-- 总年假 -->
								<td id="useVac_${i.index}">${item.USE_VAC + item.USE_VAC_CNT}</td><!-- 已休年假 -->
								<td>${item.AFFIRM_USE_VAC}</td><!-- 审批个数 -->
								<td>${item.USE_VAC_1}</td>
								<td>${item.USE_VAC_2}</td>
								<td>${item.USE_VAC_3}</td>
								<td>${item.USE_VAC_4}</td>
								<td>${item.USE_VAC_5}</td>
								<td>${item.USE_VAC_6}</td>
								<td>${item.USE_VAC_7}</td>
								<td>${item.USE_VAC_8}</td>
								<td>${item.USE_VAC_9}</td>
								<td>${item.USE_VAC_10}</td>
								<td>${item.USE_VAC_11}</td>
								<td>${item.USE_VAC_12}</td>
								<!--<td sysLog="text" sysIndex="${i.index}" sysOldValue="${item.ADD_VAC}" sysType="ADD_VAC" id="ADD_VAC${i.index}">${item.ADD_VAC}</td>
								<td sysLog="text" sysIndex="${i.index}" sysOldValue="${item.MENT_VAC}" sysType="MENT_VAC" id="MENT_VAC${i.index}">${item.MENT_VAC}</td>-->
								<td id="leaveVac_${i.index}">
									${item.LAST_YEAR_VAC + item.TOT_VAC_CNT + item.ADD_VAC - item.USE_VAC - item.AFFIRM_USE_VAC - item.USE_VAC_CNT}
								</td> <!-- 剩余 -->
								<!--<td>
									${item.REASON_WUJIA}
								</td>-->
								<td sysLog="text" sysIndex="${i.index}" sysType="REMARK" id="REMARK${i.index}">${item.REMARK}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
									<div id="VACATION_NO_${i.index}" style="display:none;">${item.VACATION_NO}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>