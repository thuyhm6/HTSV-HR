<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	
	$("[name='stated']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
	 	var sysDeptNo = $(obj).attr("sysDeptNo");
	 	var sysDutyNo = $(obj).attr("sysDutyNo");
	 	var sysPost = $(obj).attr("sysPost");
		if(sysDeptNo != 'BJ010201030103' && sysDeptNo != 'BJ01040101030103' && sysDeptNo != 'BJ01040101030203' && sysDeptNo != 'BJ01040101030303' && sysDeptNo != 'BJ01040101030403'){
			$("#TENGXUN_OT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010' && sysDutyNo !='14015683' && sysDutyNo !='14015697'){
			$("#P_ACTIVITY_FEE_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010'){
			$("#P_SHOP_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015690' && sysDutyNo !='14015683' && sysDutyNo !='14015697' && sysDutyNo !='80000011' && sysDutyNo !='80000012' && sysDutyNo !='14015663'){
			$("#P_HEALTH_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_PINZHI_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_ZHIWU_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_SHENGCHAN_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_SPCIALSHOP_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo ='80000009'){
			$("#P_ZHIWU_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","text");
		}
		if(sysDutyNo !='14015663'){
			$("#P_MULTIFUNCTIONAL_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysPost !='14015817'){
			$("#LUNCH_ALLOWANCE_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_GAOWEN_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015673' && sysDutyNo !='14016395'){
			$("#P_LENGDONG_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_YEBU_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015690'){
			$("#P_TRAIN_FEE_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
	}); 
	//查询
	$("#viewMonthDetailApplyList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewMonthDetailApplyListForm",navTab.getCurrentPanel()).submit();
	});
	//申请
	$("#viewMonthDetailApplyList_Apply",navTab.getCurrentPanel()).click(function(){
		var empIdsStr="";
		var arMonth ="";
		var flag=false;
		$("input:[name='BATCH_DETAIL_APPLY']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
				arMonth = $(this).attr("sysArMonthStr");
				flag = true;
			}
		});
		empIdsStr = empIdsStr + "'empty'";
		if(flag == false){
			alertMsg.error('请选择要申请的数据');
			return false;
		}
		alertMsg.confirm('确定要申请吗？',
	  		{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/ess/tempEmp/monthDetailApply',
	  				data:{AR_MONTH:arMonth,EMPIDS:empIdsStr},
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
		return false;
	});
	
	$("#viewMonthDetailApplyList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input:[name='BATCH_DETAIL_APPLY']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = obj.id.substring(19);
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				jsonData += ' "P_ACTIVITY_FEE": "' + $("#P_ACTIVITY_FEE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SHOP_SUB": "' + $("#P_SHOP_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_HEALTH_BT": "' + $("#P_HEALTH_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_PINZHI_BT": "' + $("#P_PINZHI_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_ZHIWU_BT": "' + $("#P_ZHIWU_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SHENGCHAN_BT": "' + $("#P_SHENGCHAN_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SPCIALSHOP_SUB": "' + $("#P_SPCIALSHOP_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_MULTIFUNCTIONAL_SUB": "' + $("#P_MULTIFUNCTIONAL_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "LUNCH_ALLOWANCE": "' + $("#LUNCH_ALLOWANCE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_GAOWEN_BT": "' + $("#P_GAOWEN_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_LENGDONG_BT": "' + $("#P_LENGDONG_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_YEBU_BT": "' + $("#P_YEBU_BT_" + index,navTab.getCurrentPanel()).html() + '" ,'; 
				jsonData += ' "P_TRAIN_FEE": "' + $("#P_TRAIN_FEE_" + index,navTab.getCurrentPanel()).html() + '" ,'; 
				</c:if> 
				jsonData += ' "PERSON_ID": "' + $(this).val() + '" ,';
				jsonData += ' "AR_MONTH": "' + $(this).attr("sysArMonthStr") + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
				jsonData += ' "REMARK": "' + $("#REMARK_" + index,navTab.getCurrentPanel()).html() + '"'; 
				jsonData += '}';
			}
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info("没有需要保存的数据");
			return;
		}
		alertMsg.confirm("确定要保存吗？",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/monthDetailSave',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});


	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 290,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
		//正在加载中......
    	"sProcessing": "<spring:message code='ess.message.loading' />",
        //查询不到相关数据！
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
        //表中无数据存在！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
        //快速筛选
        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
        }, //多语言配置
	     "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0,7] }
	                     ],
	    "fixedColumns":{leftColumns: 8}
	});
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3453();
	});
	initEditFun_ess3453();
});
function initEditFun_ess3453(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var sysType = $(this).attr("sysType");
			var index = $(this).attr("sysIndex");
			if(sysType == 'ALLOWANCE'){
				$(this).html(val.replace(/[^\-?\d.]/g,''));
			}else{
				$(this).html(val);
			}
			$("#BATCH_DETAIL_APPLY_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}
</script>
<div class="pageHeader">
<form id="viewMonthDetailApplyListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewMonthDetailApplyList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 月份 --><spring:message code="ar.excelexport.title.month" /></td>
		<td>
			<input type="text" id="AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${AR_MONTH}"/>
		</td>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY }">
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
		</td>
		<td><!--状态--><spring:message code="ar.viewcycle.title.zhuangtai" /></td>
		<td>
			<select name="seach_ACTIVITY">
				<option value="" ><!--全部--><spring:message code="org.title.ALL" /></option>
				<option value="0" <c:if test="${ACTIVITY eq '0'}">selected</c:if>><!--未申请--><spring:message code="ess.title.WEIQUEREN" /></option>
				<option value="3" <c:if test="${ACTIVITY eq '3'}">selected</c:if>><!--已申请--><spring:message code="ar.viewsummaryyishenqing" /></option>
				<option value="1" <c:if test="${ACTIVITY eq '1'}">selected</c:if>><!--已确认--><spring:message code="ar.viewsummaryyiqueren" /></option>
				<option value="2" <c:if test="${ACTIVITY eq '2'}">selected</c:if>><!--已否决--><spring:message code="ar.viewsummaryyifoujue" /></option>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewMonthDetailApplyList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
		<li><a class="buttonActive" id="viewMonthDetailApplyList_Apply" href="#"><span><!--申请--><spring:message code="display.paecc.shenqing" /></span></a></li>
		<li><a class="buttonActive" id="viewMonthDetailApplyList_Save" href="#"><span>保存</span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailApplyListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=268','/ess/tempEmp/viewMonthDetailApplyList')"><span>Excel导出</span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailApplyListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=295','/ess/tempEmp/viewMonthDetailApplyList')"><span>月结Excel导出</span></a></li>
		</c:if>
	</ul>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(viewMonthDetailApplyList)}</div>
				<table class="orderList" width="3300px">
					<thead>
						<tr>
							<th width="20px">NO.</th>
							<th width="60px"><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th width="60px"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th width="150px"><!--部门--><spring:message code="ess.infoApply.DEPT" /></th>
							<th width="60px"><!--职级--><spring:message code="ess.trans.title.postGradeName" /></th>
							<th width="60px"><!--岗位--><spring:message code="rp.report.title.dutyinfo" /></th>
							<th width="60px"><!--状态--><spring:message code="is.joininstance.title.statement" /></th>
							<th width="30px"><input type="checkbox" class="checkboxCtrl" group="BATCH_DETAIL_APPLY"/></th>
							<th width="70px"><!--入职日期--><spring:message code="ess.trans.title.entryJobDate" /></th>
							<th width="70px"><!--离职日期--><spring:message code="hrm.recruitManage.LEAVE_DATE" /></th>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="40px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
							<th width="40px"><!--未勤<br/>天数--><spring:message code="ess.title.WEIQINTIANSHU" /></th>
							<th width="40px"><!--出勤<br/>天数--><spring:message code="ess.title.CHUQINTIANSHU" /></th>
							<th width="40px"><!--总工时--><spring:message code="ess.infoApply.zonggongshi" /></th>
							<th width="40px">1.5<!--倍--><spring:message code="ar.view.bei" /></th>
							<th width="40px">2<!--倍--><spring:message code="ar.view.bei" /></th>
							<th width="40px">3<!--倍--><spring:message code="ar.view.bei" /></th>
							 <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							 	<th width="60px"><!--收银津贴--><spring:message code="ar.menu.title.receivingallowance" /></th>
								<th width="60px"><!--腾讯加班--><spring:message code="ess.infoApply.tengxunot" /></th>
								<th width="60px"><font color="red">活动费</font></th>
								<th width="60px"><font color="red">店铺补助</font></th>
								<th width="60px"><font color="red">卫生津贴</font></th>
								<th width="60px"><font color="red">品质津贴</font></th>
								<th width="60px"><font color="red">职务津贴</font></th>
								<th width="60px"><font color="red">生产津贴</font></th>
								<th width="70px"><font color="red">特殊店铺补助</font></th>
								<th width="85px"><font color="red">多功能技师补助</font></th>
								<th width="70px"><font color="red">车间工人餐补</font></th>
								<th width="60px"><font color="red">高温津贴</font></th>
								<th width="60px"><font color="red">冷冻补贴</font></th>
								<th width="60px"><font color="red">夜补奖金</font></th>
								<th width="70px"><font color="red">扣除培训费用</font></th>
							</c:if>
							<th width="40px"><!--事假--><spring:message code="ar.menu.title.shijia" /></th>
							<th width="40px"><!--病假--><spring:message code="ar.menu.title.bingjia" /></th>
							<th width="40px"><!--年假--><spring:message code="ar.viewArAnnualStandard.title.ninjia" /></th>
							<th width="40px"><!--调休--><spring:message code="ar.viewArAnnualStandard.title.tiaoxiu" /></th>
							<th width="40px"><!--婚假--><spring:message code="ar.menu.title.hunjia" /></th>
							<th width="40px"><!--丧假--><spring:message code="ar.menu.title.sangjia" /></th>
							<th width="40px"><!--产假--><spring:message code="ar.menu.title.chanjia" /></th>
							<th width="40px"><!--陪产假--><spring:message code="ar.menu.title.peichanjia" /></th>
							<th width="40px"><!--产检假--><spring:message code="ar.menu.title.chanjianjia" /></th>
							<th width="40px"><!--哺乳假--><spring:message code="ar.menu.title.purujia" /></th>
							<th width="40px"><!--工伤假--><spring:message code="ar.menu.title.gongshangjia" /></th>
							<th width="150px"><font color="red">备注</font></th>
							<th width="110px"><!--申请时间--><spring:message code="heran.ess.viewLikeLeaveApplyInfo.shenqingshijian" /></th>
							<th width="100px"><!--申请人--><spring:message code="pa.salary.title.affirm_people" /></th>
							<th width="110px"><!--确认时间--><spring:message code="pa.salary.querenshijian" /></th>
							<th width="100px"><!--确认人--><spring:message code="pa.salary.querenren" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewMonthDetailApplyList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td>${item.DEPT_NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.DUTY_NAME}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 0}">
										未申请
									</c:if>
									<c:if test="${item.ACTIVITY eq 1}">
										已确认
									</c:if>
									<c:if test="${item.ACTIVITY eq 3}">
										已申请
									</c:if>
									<c:if test="${item.ACTIVITY eq 2}">
										已否决
									</c:if>
								</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq '0' or item.ACTIVITY eq '2'}">
										<input type="checkbox" sysArMonthStr="${item.AR_MONTH}" sysIndex="${i.index}" name="BATCH_DETAIL_APPLY" id="BATCH_DETAIL_APPLY_${i.index}" value="${item.PERSON_ID}" />
									</c:if>
								</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="j">
									<td class='td_center' <c:if test="${item1.TYPEID ne 1440}">style="background-color:#cccccc"</c:if>>${item[item1.DATE_KEY]}</td>
								</c:forEach>
								<td class='td_center'>${item.NOATT}</td>
								<td class='td_center'>${item.ATT}</td>
								<td class='td_center'>${item.WORK_HOUR}</td>
								<td class='td_center'>${item.OT1}</td>
								<td class='td_center'>${item.OT2}</td>
								<td class='td_center'>${item.OT3}</td>
								 <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class='td_center'>${item.RECEIVING_ALLOWANCE}</td>
									<td class='td_center'>${item.TENCENT_OT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_ACTIVITY_FEE_${i.index}">${item.P_ACTIVITY_FEE}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SHOP_SUB_${i.index}">${item.P_SHOP_SUB}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_HEALTH_BT_${i.index}">${item.P_HEALTH_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_PINZHI_BT_${i.index}">${item.P_PINZHI_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_ZHIWU_BT_${i.index}">${item.P_ZHIWU_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SHENGCHAN_BT_${i.index}">${item.P_SHENGCHAN_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SPCIALSHOP_SUB_${i.index}">${item.P_SPCIALSHOP_SUB}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_MULTIFUNCTIONAL_SUB_${i.index}">${item.P_MULTIFUNCTIONAL_SUB}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="LUNCH_ALLOWANCE_${i.index}">${item.LUNCH_ALLOWANCE}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_GAOWEN_BT_${i.index}">${item.P_GAOWEN_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_LENGDONG_BT_${i.index}">${item.P_LENGDONG_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_YEBU_BT_${i.index}">${item.P_YEBU_BT}</td>
									<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_TRAIN_FEE_${i.index}">${item.P_TRAIN_FEE}</td>
								</c:if>
								<td class='td_center'>${item.CASUAL_LEAVE}</td>
								<td class='td_center'>${item.SICK_LEAVE}</td>
								<td class='td_center'>${item.ANNUAL_VACATION}</td>
								<td class='td_center'>${item.PAY_LEAVE}</td>
								<td class='td_center'>${item.MARRIAGE_LEAVE}</td>
								<td class='td_center'>${item.BEREFT_LEAVE}</td>
								<td class='td_center'>${item.MATERNITY_LEAVE}</td>
								<td class='td_center'>${item.PEICHAN_JIA}</td>
								<td class='td_center'>${item.CHECK_MATERNITY_LEAVE}</td>
								<td class='td_center'>${item.NURSING_LEAVE}</td>
								<td class='td_center'>${item.INDUSTRY_INJURY}</td>
								<td sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}">${item.REMARK}</td>
								<td class='td_center'>${item.CREATE_DATE}</td>
								<td class='td_center'>${item.CREATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<input type="hidden" id="stated_${i.index}" name="stated" sysActivity="${item.ACTIVITY}" sysDeptNo="${item.DEPTNO }" sysPost="${item.POST_FAMILY}" sysDutyNo="${item.DUTY_NO}"   sysIndex="${i.index}">
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
