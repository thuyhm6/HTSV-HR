<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewShopSummaryConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewShopSummaryConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 280,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [5] }
	                     ],
	    "fixedColumns":{leftColumns: 4},
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
});

function openDialog_ess3449(personId,arMonth,url){
	$.pdialog.open(encodeURI("/ess/tempEmp/" + url + "?PERSON_ID=" + personId + "&AR_MONTH=" + arMonth), "Comment", "<spring:message code='ess.viewMonthDetailConfirmList.YUEKAOQINQUEREN.a' />", {width:900,height:650,mask:true});//月考勤确认
}
</script>
<div class="pageHeader">
<form id="viewShopSummaryConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewShopSummaryConfirmList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>
		<!--姓名/社号--><spring:message code="ess.infoApply.NAME_EMPID" />
        </td>
		<td>
		   <input type="text" name="seach_KEY" value="${KEY }">
		</td>
		<td>
          <!--部门 --><spring:message code="ess.infoApply.DEPT" />
        </td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewShopSummaryConfirmList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewShopSummaryConfirmList_deptList" selected="${DEPTNO}"/>
		</td>
		<td>
		  <!--年月 --><spring:message code="ess.infoApply.YEAR_MONTH" />
		</td>
		<td>
			<input type="text" id="seach_AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${AR_MONTH}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewShopSummaryConfirmList_Serch" href="#"><span><!--查询 --><spring:message code="ess.infoApply.SELECT"/></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="2090px">
					<thead>
						<tr>
							<th width="30px"><!--NO. --><spring:message code="ess.infoApply.NO."/></th>
							<th width="70px"><!--社号--><spring:message code="ess.infoApply.EMPID"/></th>
							<th width="70px"><!--姓名--><spring:message code="ess.infoApply.NAME"/></th>
							<th width="120px"><!--部门--><spring:message code="ess.infoApply.DEPT"/></th>
							<th width="100px"><!--合计工时 --><spring:message code="ess.infoApply.hejigongshi" /></th>
							<th width="100px"><!--加班时数 --><spring:message code="ess.infoApply.jiabanshishu" /></th>
							<th width="100px"><!--出勤--><spring:message code="ar.excelexport.title.zhengchangchuqin"/></th>
							<th width="100px"><!--迟到次数--><spring:message code="ess.infoApply.LATE_TIMES"/></th>
							<th width="100px"><!--早退次数--><spring:message code="ess.infoApply.LEAVE_EARLY_TIMES"/></th>
							<th width="100px"><!--旷工天数--><spring:message code="ess.infoApply.withoutWork_days"/></th>
							<th width="100px"><!--平日加班时数--><spring:message code="ess.infoApply.overtime_weekdays_hours"/></th>
							<th width="100px"><!--周末加班时数--><spring:message code="ess.infoApply.overtime_weekend_hours"/></th>
							<th width="100px"><!--法定加班时数--><spring:message code="ess.infoApply.overtime_legal_hours"/></th>
							<th width="100px"><!--事假天数--><spring:message code="ess.infoApply.thing_leave_hours"/></th>
							<th width="100px"><!--病假天数--><spring:message code="ess.infoApply.sick_leave_hours"/></th>
							<th width="100px"><!--工伤假天数--><spring:message code="ess.infoApply.industrial_injury_hours"/></th>
							<th width="100px"><!--婚假天数--><spring:message code="ess.infoApply.marriage_holiday_hours"/></th>
							<th width="100px"><!--陪产假天数--><spring:message code="ess.infoApply.accompany_maternity_leave_hours"/></th>
							<th width="100px"><!--产假天数--><spring:message code="ess.infoApply.maternity_leave_hours"/></th>
							<th width="100px"><!--丧假天数--><spring:message code="ess.infoApply.funeral_hours"/></th>
							<th width="100px"><!--产检假天数--><spring:message code="ess.infoApply.production_check_hours"/></th>
							<th width="100px"><!--哺乳期时数--><spring:message code="ess.infoApply.lactation_leave_hours"/></th>
							<th width="100px"><!--外出时数--><spring:message code="ess.infoApply.outgoing_hours"/></th>
							<th width="100px"><!--国内出差天数--><spring:message code="ess.infoApply.domestic_travel_hours"/></th>
							<th width="100px"><!--国外出差天数--><spring:message code="ess.infoApply.foreign_business_trip_hours"/></th>
							<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							 	<th width="100px"><!--收银津贴--><spring:message code="ar.menu.title.receivingallowance" /></th>
								<th width="100px"><!--腾讯加班--><spring:message code="ess.infoApply.tengxunot" /></th>
								<th width="100px"><!--活动费--><spring:message code="pa.viewPaResultList.HUODONGFEI.C" /></th>
								<th width="100px"><!--店铺补助--><spring:message code="pa.viewPaResultList.DIANPUBUZHU.C" /></th>
								<th width="100px"><!--卫生津贴--><spring:message code="ess.viewShopSummaryConfirmList.WEISHENGJINTIE.a" /></th>
								<th width="100px"><!--品质津贴--><spring:message code="ess.viewShopSummaryConfirmList.PINZHIJINTIE.a" /></th>
								<th width="100px"><!--职务津贴--><spring:message code="ess.viewShopSummaryConfirmList.ZHIWUJINTIE.a" /></th>
								<th width="100px"><!--生产津贴--><spring:message code="ess.viewShopSummaryConfirmList.SHENGCHANJINTIE.a" /></th>
								<th width="100px"><!--特殊店铺补助--><spring:message code="ess.viewShopSummaryConfirmList.TESHUDIANPUBUZHU.a" /></th>
								<th width="100px"><!--多功能技师补助--><spring:message code="ess.viewShopSummaryConfirmList.DUOGONGNENGJISHIBUZHU.a" /></font></th>
								<th width="100px"><!--车间工人餐补--><spring:message code="ess.viewShopSummaryConfirmList.CHEJIANGONGRENCANBU.a" /></th>
								<th width="100px"><!--高温津贴--><spring:message code="ess.viewShopSummaryConfirmList.GAOWENJINTIE.a" /></th>
								<th width="100px"><!--冷冻补贴--><spring:message code="ess.viewShopSummaryConfirmList.LENGDONGBUTIE.a" /></th>
								<th width="100px"><!--夜补奖金--><spring:message code="ess.viewShopSummaryConfirmList.YEBUJIANGJIN.a" /></th>
								<th width="100px"><!--扣除培训费用--><spring:message code="ess.viewShopSummaryConfirmList.KOUCHUPEIXUNFEIYONG.a" /></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopSummaryConfirmList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>
									<c:if test="${LoginUser.cpnyId eq 'HTSV' or LoginUser.cpnyId eq 'HAE' or LoginUser.cpnyId eq 'SPC_DL' }">
										<a href="#" onclick="openDialog_ess3449('${item.PERSON_ID}','${AR_MONTH}','viewShopDetailPersonId')">${item.LOCAL_NAME}</a>
									</c:if>
									<c:if test="${LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ' or LoginUser.cpnyId eq 'SPC_NJ' }">
										<a href="#" onclick="openDialog_ess3449('${item.PERSON_ID}','${AR_MONTH}','viewShopDetailPersonIdSH')">${item.LOCAL_NAME}</a>
									</c:if>
								</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
							 	<td class='td_center'>${item.WORK_HOUR}</td>
								<td class='td_center'>${item.PINGRIJIABAN + item.ZHOUMOJIABAN + item.JIEJIARIJIABAN}</td>
							 	<td class='td_center'>${item.ATT}</td>
								<td class='td_center'>${item.CHIDAO}</td>
								<td class='td_center'>${item.ZAOTUI}</td>
								<td class='td_center'>${item.KUANGGONG}</td>
								<td class='td_center'>${item.PINGRIJIABAN}</td>
								<td class='td_center'>${item.ZHOUMOJIABAN}</td>
								<td class='td_center'>${item.JIEJIARIJIABAN}</td>
								<td class='td_center'>${item.SHIJIA}</td>
								<td class='td_center'>${item.BINGJIA}</td>
								<td class='td_center'>${item.GONGSHANGJIA}</td>
								<td class='td_center'>${item.HUNJIA}</td>
								<td class='td_center'>${item.PEICHANJIA}</td>
								<td class='td_center'>${item.CHANJIA}</td>
								<td class='td_center'>${item.SANGJIA}</td>
								<td class='td_center'>${item.CHANJIANJIA}</td>
								<td class='td_center'>${item.BURUJIA}</td>
								<td class='td_center'>${item.WAICHU}</td>
								<td class='td_center'>${item.GUONEICHUCHAI}</td>
								<td class='td_center'>${item.GUOWAICHUCHAI}</td>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class='td_center'>${item.RECEIVING_ALLOWANCE}</td>
									<td class='td_center'>${item.TENCENT_OT}</td>
									<td class='td_center'>${item.P_ACTIVITY_FEE}</td>
									<td class='td_center'>${item.P_SHOP_SUB}</td>
									<td class='td_center'>${item.P_HEALTH_BT}</td>
									<td class='td_center'>${item.P_PINZHI_BT}</td>
									<td class='td_center'>${item.P_ZHIWU_BT}</td>
									<td class='td_center'>${item.P_SHENGCHAN_BT}</td>
									<td class='td_center'>${item.P_SPCIALSHOP_SUB}</td>
									<td class='td_center'>${item.P_MULTIFUNCTIONAL_SUB}</td>
									<td class='td_center'>${item.LUNCH_ALLOWANCE}</td>
									<td class='td_center'>${item.P_GAOWEN_BT}</td>
									<td class='td_center'>${item.P_LENGDONG_BT}</td>
									<td class='td_center'>${item.P_YEBU_BT}</td>
									<td class='td_center'>${item.P_TRAIN_FEE}</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
