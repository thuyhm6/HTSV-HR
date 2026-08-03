<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){

	//查询
	$("#viewMonthDetailConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewMonthDetailConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	//确认
	$("#viewMonthDetailConfirmList_Confirm",navTab.getCurrentPanel()).click(function(){
		viewMonthDetailConfirmList_Confirm(1);
	});
	//否决
	$("#viewMonthDetailConfirmList_Reject",navTab.getCurrentPanel()).click(function(){
		viewMonthDetailConfirmList_Confirm(2);
	});
 	$("#viewMonthDetailApplyList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input:[name='BATCH_DETAIL_CONFIRM']",navTab.getCurrentPanel()).each(function(i, obj){
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
			alertMsg.info("<spring:message code='hrm.empinfo.NOTSAVE_DATA' />");//没有需要保存的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
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
		"scrollY": $(document.body).height() - 280,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
		//正在加载中......
    	"sProcessing": "<spring:message code='ess.message.loading' />",
    	//查询不到相关数据
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
			$("#BATCH_DETAIL_CONFIRM_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}

function viewMonthDetailConfirmList_Confirm(activity){
	var empIdsStr="";
	var arMonth ="";
	var flag=false;
	var msg="<spring:message code='ess.infoApply.confirm' />";//确认
	$("input:[name='BATCH_DETAIL_CONFIRM']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
			arMonth = $(this).attr("sysArMonthStr");
			flag = true;
		}
	});
	empIdsStr = empIdsStr + "'empty'";
	if(activity == 2){
		msg="<spring:message code='ess.infoApply.veto' />";//否决
	}
	if(flag == false){
		alertMsg.error('<spring:message code="ess.viewMonthDetailConfirmList.QINGXUANXUANZEYAO.a" />' + msg + '<spring:message code="ess.viewMonthDetailConfirmList.DESHUJU.a" />');//请选选择要    的数据
		return false;
	}
	alertMsg.confirm('<spring:message code="ess.viewMonthDetailConfirmList.QUEDINGYAO.a" />' + msg + '<spring:message code="ess.viewMonthDetailConfirmList.MAO.a" />',//确定要     吗？
  		{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/ess/tempEmp/monthDetailConfirm',
  				data:{AR_MONTH:arMonth,EMPIDS:empIdsStr,ACTIVITY:activity},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
<div class="pageHeader">
<form id="viewMonthDetailConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewMonthDetailConfirmList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 月份 --><spring:message code="ar.excelexport.title.month" /></td>
		<td>
			<input type="text" id="AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${AR_MONTH}"/>
		</td>
		<td><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY }">
		</td>
		<td><!-- 部门 --><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" /></td>
		<td>
			<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
		</td>
		<td><!-- 状态 --><spring:message code="ar.attendanceView.viewNoSwipingCard.status" /></td>
		<td>
			<select name="seach_ACTIVITY">
				<option value="" ><!-- 全部  --><spring:message code="ar.viewarcardrecord.title.quanbu" /></option>
				<option value="3" <c:if test="${ACTIVITY eq '3'}">selected</c:if>><!-- 已申请  --><spring:message code="ar.viewsummaryyishenqing" /></option>
				<option value="1" <c:if test="${ACTIVITY eq '1'}">selected</c:if>><!-- 已确认  --><spring:message code="ar.viewsummaryyiqueren" /></option>
				<option value="2" <c:if test="${ACTIVITY eq '2'}">selected</c:if>><!-- 已否决  --><spring:message code="ar.viewsummaryyifoujue" /></option>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewMonthDetailConfirmList_Serch" href="#"><span><!-- 查询  --><spring:message code="button.search" /></span></a></li>
		<li><a class="buttonActive" id="viewMonthDetailConfirmList_Confirm" href="#"><span><!-- 确认  --><spring:message code="org.title.IS_CONFIRM" /></span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<li><a class="buttonActive" id="viewMonthDetailApplyList_Save" href="#"><span><!-- 保存更改  --><spring:message code="ess.viewMonthDetailConfirmList.BAOCUNGENGGAI.a" /></span></a></li>
		</c:if>
		<li><a class="buttonActive" id="viewMonthDetailConfirmList_Reject" href="#"><span><!-- 否决  --><spring:message code="ess.infoApply.veto" /></span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailConfirmListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=269','/ess/tempEmp/viewMonthDetailConfirmList')"><span><!-- Excel导出  --><spring:message code="ess.infoApply.EXCEL_OUT" /></span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailConfirmListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=295','/ess/tempEmp/viewMonthDetailApplyList')"><span><!-- 月结Excel导出  --><spring:message code="ess.viewMonthDetailConfirmList.YUEJIEEXCELDAOCHU.a" /></span></a></li>
		</c:if>
	</ul>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(viewMonthDetailConfirmList)}</div>
				<table class="orderList" width="3300px">
					<thead>
						<tr>
							<th width="20px">NO.</th>
							<th width="60px"><!-- 工号  --><spring:message code="ess.infoApply.EMP_ID" /></th>
							<th width="60px"><!-- 姓名  --><spring:message code="org.title.LOCAL_NAME" /></th>
							<th width="150px"><!-- 部门  --><spring:message code="org.title.dept" /></th>
							<th width="60px"><!-- 职级  --><spring:message code="org.title.POST_GRADE_NAME" /></th>
							<th width="60px"><!-- 岗位  --><spring:message code="org.title.DUTY_NO" /></th>
							<th width="60px"><!-- 状态  --><spring:message code="org.title.status" /></th>
							<th width="30px"><input type="checkbox" class="checkboxCtrl" group="BATCH_DETAIL_CONFIRM"/></th>
							<th width="70px"><!-- 入职日期  --><spring:message code="org.title.DATE_STARTED" /></th>
							<th width="70px"><!-- 离职日期  --><spring:message code="ess.empInfo.leaveDate" /></th>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="40px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
							<th width="40px"><!-- 未勤  --><spring:message code="ar.view.weiqin" /><br/><!-- 天数  --><spring:message code="ar.view.tianshu" /></th>
							<th width="40px"><!-- 出勤  --><spring:message code="ar.view.chuqin" /><br/><!-- 天数  --><spring:message code="ar.view.tianshu" /></th>
							<th width="40px"><!-- 总工时  --><spring:message code="ess.infoApply.zonggongshi" /></th>
							<th width="40px">1.5<!-- 倍  --><spring:message code="ar.view.bei" /></th>
							<th width="40px">2<!-- 倍  --><spring:message code="ar.view.bei" /></th>
							<th width="40px">3<!-- 倍  --><spring:message code="ar.view.bei" /></th>
							<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<th width="60px"><!-- 收银津贴 --><spring:message code="ar.menu.title.receivingallowance" /></th>
								<th width="60px"><!-- 腾讯加班  --><spring:message code="ess.infoApply.tengxunot" /></th>
								<th width="60px"><!-- 活动费  --><spring:message code="pa.viewPaResultList.HUODONGFEI.C" /></th>
								<th width="60px"><!-- 店铺补助  --><spring:message code="pa.viewPaResultList.DIANPUBUZHU.C" /></th>
								<th width="60px"><!-- 卫生津贴  --><spring:message code="ess.viewShopSummaryConfirmList.WEISHENGJINTIE.a" /></th>
								<th width="60px"><!-- 品质津贴  --><spring:message code="ess.viewShopSummaryConfirmList.PINZHIJINTIE.a" /></th>
								<th width="60px"><!-- 职务津贴  --><spring:message code="ess.viewShopSummaryConfirmList.ZHIWUJINTIE.a" /></th>
								<th width="60px"><!-- 生产津贴  --><spring:message code="ess.viewShopSummaryConfirmList.SHENGCHANJINTIE.a" /></th>
								<th width="70px"><!-- 特殊店铺补助  --><spring:message code="ess.viewShopSummaryConfirmList.TESHUDIANPUBUZHU.a" /></th>
								<th width="80px"><!-- 多功能技师补助  --><spring:message code="ess.viewShopSummaryConfirmList.DUOGONGNENGJISHIBUZHU.a" /></th>
								<th width="60px"><!-- 车间工人餐补 --><spring:message code="ess.viewShopSummaryConfirmList.CHEJIANGONGRENCANBU.a" /></th>
								<th width="60px"><!-- 高温津贴  --><spring:message code="ess.viewShopSummaryConfirmList.GAOWENJINTIE.a" /></th>
								<th width="60px"><!-- 冷冻补贴  --><spring:message code="ess.viewShopSummaryConfirmList.LENGDONGBUTIE.a" /></th>
								<th width="60px"><!-- 夜补奖金  --><spring:message code="ess.viewShopSummaryConfirmList.YEBUJIANGJIN.a" /></th>
								<th width="70px"><!-- 扣除培训费用  --><spring:message code="ess.viewShopSummaryConfirmList.KOUCHUPEIXUNFEIYONG.a" /></th>
							</c:if>
							<th width="40px"><!-- 事假  --><spring:message code="ess.viewpersonalpainfo.shijia" /></th>
							<th width="40px"><!-- 病假  --><spring:message code="ar.menu.title.bingjia" /></th>
							<th width="40px"><!-- 年假  --><spring:message code="ar.viewArAnnualStandard.title.ninjia" /></th>
							<th width="40px"><!-- 调休  --><spring:message code="ar.viewArAnnualStandard.title.tiaoxiu" /></th>
							<th width="40px"><!-- 婚假  --><spring:message code="ar.menu.title.hunjia" /></th>
							<th width="40px"><!-- 丧假  --><spring:message code="ar.menu.title.sangjia" /></th>
							<th width="40px"><!-- 产假  --><spring:message code="ar.menu.title.chanjia" /></th>
							<th width="40px"><!-- 陪产假  --><spring:message code="ar.menu.title.peichanjia" /></th>
							<th width="40px"><!-- 产检假  --><spring:message code="ar.menu.title.chanjianjia" /></th>
							<th width="40px"><!-- 哺乳假  --><spring:message code="ar.menu.title.purujia" /></th>
							<th width="40px"><!-- 工伤假  --><spring:message code="ar.menu.title.gongshangjia" /></th>
							<th width="110px"><!-- 备注  --><spring:message code="ar.viewarcardrecord.title.beizhu" /></th>
							<th width="110px"><!-- 申请时间  --><spring:message code="heran.ess.viewLikeLeaveApplyInfo.shenqingshijian" /></th>
							<th width="100px"><!-- 申请人  --><spring:message code="pa.salary.title.affirm_people" /></th>
							<th width="110px"><!-- 确认时间  --><spring:message code="pa.salary.querenshijian" /></th>
							<th width="100px"><!-- 确认人  --><spring:message code="pa.salary.querenren" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewMonthDetailConfirmList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td>${item.DEPT_NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.DUTY_NAME}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 1}">
										<!-- 已确认  --><spring:message code="ar.viewsummaryyiqueren" />
									</c:if>
									<c:if test="${item.ACTIVITY eq 3}">
										<!-- 已申请  --><spring:message code="ar.viewsummaryyishenqing" />
									</c:if>
									<c:if test="${item.ACTIVITY eq 2}">
										<!-- 已否决  --><spring:message code="ar.viewsummaryyifoujue" />
									</c:if>
								</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq '3' or item.ACTIVITY eq '1'}">
										<input type="checkbox" sysArMonthStr="${item.AR_MONTH}" sysIndex="${i.index}" name="BATCH_DETAIL_CONFIRM" id="BATCH_DETAIL_CONFIRM_${i.index}" value="${item.PERSON_ID}" />
									</c:if>
								</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="i">
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
								<td class='td_center'>${item.REMARK}</td>
								<td class='td_center'>${item.CREATE_DATE}</td>
								<td class='td_center'>${item.CREATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
