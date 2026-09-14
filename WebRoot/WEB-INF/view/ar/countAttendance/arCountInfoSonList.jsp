<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#ar0152List_currentIndex").val('${currentIndex}');
});
function showEmpDetail(pEmpid,pEmpName){

}
function arCountInfoList_changeURL(name,PERSON_ID) {
	var STIMESS = $("#arCountInfoList_seach_STIME").attr("value");
	STIMESS = STIMESS.substring(6,10)+ "/" +STIMESS.substring(3,5)+ "/" +STIMESS.substring(0,2);
	var ETIMESS = $("#arCountInfoList_seach_ETIME").attr("value");
	ETIMESS = ETIMESS.substring(6,10)+ "/" +ETIMESS.substring(3,5)+ "/" +ETIMESS.substring(0,2);
	var DEPT_NO = $("input[syslong='arCountInfoList_seachDept']").val();
	var GROUP = $("#arCountInfoList_GROUP").attr("value");
	var KEY = $("#arCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewOtApplySingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO +  "&KEY="
			+ KEY + "&PERSON_ID=" + PERSON_ID;
	//个人加班现况		
	$.pdialog.open(href,"ar0152", "<spring:message code='ar.arCountInfoList.GERENJIABANXIANKUANG.b'/>", {width:1000,height:400,mask:true});
}
function changeURL2(name,PERSON_ID) {
	var STIMESS = $("#arCountInfoList_seach_STIME").attr("value");
	STIMESS = STIMESS.substring(6,10)+ "/" +STIMESS.substring(3,5)+ "/" +STIMESS.substring(0,2);
	var ETIMESS = $("#arCountInfoList_seach_ETIME").attr("value");
	ETIMESS = ETIMESS.substring(6,10)+ "/" +ETIMESS.substring(3,5)+ "/" +ETIMESS.substring(0,2);
	var DEPT_NO = $("input[syslong='arCountInfoList_seachDept']").val();
	var GROUP = $("#arCountInfoList_GROUP").attr("value");
	var KEY = $("#arCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewArPersonalSingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO  + "&KEY="
			+ KEY + "&PERSON_ID=" + PERSON_ID;
    //个人考勤现况
	$.pdialog.open(href,"ar0152", "<spring:message code='ar.arCountInfoList.GERENKAOQINXIANKUANG.b'/>", {width:800,height:400,mask:true});
}
</script>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
</head>
<div class="pageContent" id="ar0152_pageContent">
<c:if test="${currentIndex eq '0'}">
<script>
$(document).ready(function() {
	$("#ar0152_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0152_table").css("width", $(document.body).width() * 2);
	$("#ar0152_table", navTab.getCurrentPanel()).dataTable( {
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": $(document.body).width()+ 100,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns":{leftColumns: 7},
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
</script>
			<table class="orderList" border="1" id="ar0152_table">
				<thead>
					<tr>
						<th>
							NO
						</th>
						<th>
							<!-- 社号 --><spring:message code="ess.infoApply.EMPID" />
						</th>
						<th>
							<!-- 姓名 --><spring:message code="ess.infoApply.NAME" />
						</th>
						<th style="width:150px">
							<!-- 部门名 --><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG" />
						</th>
						<th>
							<!-- 职级 --><spring:message code="ess.trans.title.postGradeName" />
						</th>
						<th>
							<!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/>
						</th>
						<th>
							<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
						</th>
						<!--<th>
							 工作地 <spring:message code="ess.trans.title.workArea" />
						</th>-->
						<th>
							<!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" />
						</th>
						<th>
							<!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /> (late, early, go out)
						</th>
						<th>
							<!--年假 --><spring:message code="ar.viewArNavigationPage.NIANJIA.b" />
						</th>
						<th>
							<!--事假 --><spring:message code="ess.viewpersonalpainfo.shijia" />
						</th>
						<th>
							<!--一般病假 --><spring:message code="ess.viewArPersonalList.YIBANBINGJIA.b" />
						</th>
						<th>
							<!--孩子病假 --><spring:message code="ess.viewArPersonalList.HAIZIBINGJIA.b" />
						</th>
						<th>
							<!--长期病假 --><spring:message code="ess.viewArPersonalList.CHANGQIBINGJIA.b" />
						</th>
						<th>
							<!--个人婚假 --><spring:message code="ess.viewArPersonalList.GERENHUNJIA.b" />
						</th>
						<th>
							<!--孩子婚假 --><spring:message code="ess.viewArPersonalList.HAIZIHUNJIA.b" />
						</th>
						<th>
							<!--工伤假 --><spring:message code="ar.menu.title.gongshangjia" />
						</th>
						<th>
							<!--有薪丧假 --><spring:message code="ess.viewArPersonalList.YOUXINSANGJIA.b" />
						</th>
						<th>
							<!--产假 --><spring:message code="ar.menu.title.chanjia" />
						</th>
						<th>
							<!--陪产假 --><spring:message code="ar.menu.title.peichanjia" />
						</th>
						<th>
							<!--哺乳假--><spring:message code="ar.menu.title.purujia" />
						</th>
						<th>
							<!--产前检查假--><spring:message code="ar.arForDeptCountInfoList.CHANQIANJIANCHAJIA.b" />
						</th>
						<th>
							<!--出差--><spring:message code="ar.menu.title.chuchai" />
						</th>
						<th>
							<!--旷工--><spring:message code="ar.monthwork.title.kuanggong" />
						</th>
						<th>
							<!--旷工--><spring:message code="ess.title.LOUKA" />
						</th>
						<th>
							<spring:message code="ar.menu.title.keepHelth" />
						</th>
						<th>
							<spring:message code="ar.menu.title.chumen" />
						</th>
						<th>
							<!--迟到--><spring:message code="ar.monthwork.title.Lateness" />
						</th>
						<th>
							<!--早退--><spring:message code="ess.infoApply.leave_early" />
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${personList}" var="personList" varStatus="i">
						<tr target="sid" rel="${personList.PERSON_ID_ID}">
							<td style="text-align: center">
								${i.count}
							</td>
							<td style="text-align: center">
								${personList.EMPID_ID}
							</td>
							<td style="text-align: center">
								${personList.LOCAL_NAME}
							</td>
							<td style="text-align: center">
								${personList.DEPT_NAME}
							</td>
							<td style="text-align: center">
								${personList.POST_GRADE_NO}
							</td>
							<td style="text-align: center">
								${personList.EMP_TYPE_NAME}
							</td>
							<td style="text-align: center">
								${personList.SHIFT_NAME}
							</td>
							<!--<td style="text-align: center">
								${personList.WORK_AREA_NAME}
							</td>-->
							<td style="text-align: center">
								${personList.HEJI}
							</td>
							<td style="text-align: center">
								${personList.HEJI_CHIDAO_ZAOTUI_CHUMEN}
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141456","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.NIANJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141454","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.SHIJIA}</span> 
							</td>							
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141464","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.YIBANBINGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000286","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.HAIZIBINGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000287","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHANGQIBINGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000289","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.GERENHUNJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000290","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.HAIZIHUNJIA}</span>
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000288","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.GONGSHANGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000291","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.YOUXINSANGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141461","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHANJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000292","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.PEICHANJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000293","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.BURUJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141460","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHANQIANJIANCHAJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000294","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHUCHAI}</span>
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141443","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.KUANGGONG}</span> 
							</td>
							
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("14015448","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.FORGETCARDS}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("14013784","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.HOUBINGJIA}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141468","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHUMEN}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141441","${personList.PERSON_ID_ID}");'>
								<span style="color: blue">${personList.CHIDAO}</span> 
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141442","${personList.PERSON_ID_ID}");'>
									<span style="color: blue">${personList.ZAOTUI}</span> 
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
<c:if test="${currentIndex eq '1'}">
<script>
$(document).ready(function() {
	$("#ar0152_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0152_table2").css("width", $(document.body).width() -50);
	$("#ar0152_table2", navTab.getCurrentPanel()).dataTable( {
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
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
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns":false,
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
</script>
			<table class="orderList" border="1" id="ar0152_table2">
				<thead>
					<tr>
						<th>
							NO
						</th>
						<th>
							<!-- 社号 --><spring:message code="ess.infoApply.EMPID" />
						</th>
						<th>
							<!-- 姓名 --><spring:message code="ess.infoApply.NAME" />
						</th>
						<th>
							<!-- 部门名 --><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG" />
						</th>
						<th>
							<!-- 职级 --><spring:message code="ess.trans.title.postGradeName" />
						</th>
						<th>
							<!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/>
						</th>
						<th>
							<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
						</th>
						<!--<th>
							 工作地 <spring:message code="ess.trans.title.workArea" />
						</th>-->
						<th>
							<!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" />
						</th>
						<th>
							<!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" />
						</th>
						<th>
							<!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" />
						</th>
						
						<th>
							<!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${viewOtApplyPersonalList}" var="personList"
						varStatus="i">
						<tr target="sid" rel="">
							<td style="text-align: center">
								${i.count}
							</td>
							<td style="text-align: center">
								${personList.EMPID_ID}
							</td>
							<td style="text-align: center">
								${personList.LOCAL_NAME}
							</td>
							<td style="text-align: center" width="300px">
								${personList.DEPT_NAME}
							</td>
							<td style="text-align: center">
								${personList.POST_GRADE_NO}
							</td>
							<td style="text-align: center">
								${personList.EMP_TYPE_NAME}
							</td>
							<td style="text-align: center">
								${personList.SHIFT_NAME}
							</td>
							<!--<td style="text-align: center">
								${personList.WORK_AREA_NAME}
							</td>-->
							<td style="text-align: center">
								${personList.WEEKDAY_OT_TOTAIL+personList.WEEKEND_OT_TOTAIL+personList.HOILDAY_OT_TOTAIL}
							</td>
							<td style="text-align: center; cursor: pointer;" id="codeChange" 
							onclick='javascript:arCountInfoList_changeURL("90000295,90000296,90000297","${personList.PERSON_ID_ID}");'>
									<span style="color: blue">${personList.WEEKDAY_OT_TOTAIL}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arCountInfoList_changeURL("90000298,90000299","${personList.PERSON_ID_ID}");'>
									<span style="color: blue">${personList.WEEKEND_OT_TOTAIL}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arCountInfoList_changeURL("90000300,90000301","${personList.PERSON_ID_ID}");'>
									<span style="color: blue"> ${personList.HOILDAY_OT_TOTAIL}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
<c:if test="${currentIndex eq '2'}">
<script>
$(document).ready(function() {
	$("#ar0152_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0152_table3").css("width", $(document.body).width() *2);
	$("#ar0152_table3", navTab.getCurrentPanel()).dataTable( {
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 350,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns":{leftColumns: 6},
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
</script>
			<table class="orderList" border="1" id="ar0152_table3">
				<thead>
					<tr>
						<th rowspan='2' >NO</th>
						<th rowspan='2' ><!-- 社号 --><spring:message code="ess.infoApply.EMPID" /></th>
						<th rowspan='2' ><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></th>
						<th rowspan='2' ><!-- 部门名 --><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG" /></th>
						<th rowspan='2'><!-- 职级 --><spring:message code="ess.trans.title.postGradeName" /></th>
						<th rowspan='2'><!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/></th>
						<th rowspan='2'><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th colspan='8'><!--一月 --><spring:message code="hrm.empinfo.January" /></th>
						<th colspan='8'><!--二月 --><spring:message code="hrm.empinfo.February" /></th>
						<th colspan='8'><!--三月 --><spring:message code="hrm.empinfo.March" /></th>
						<th colspan='8'><!--四月 --><spring:message code="hrm.empinfo.April" /></th>
						<th colspan='8'><!--五月 --><spring:message code="hrm.empinfo.May" /></th>
						<th colspan='8'><!--六月 --><spring:message code="hrm.empinfo.June" /></th>
						<th colspan='8'><!--七月 --><spring:message code="hrm.empinfo.July" /></th>
						<th colspan='8'><!--八月 --><spring:message code="hrm.empinfo.August" /></th>
						<th colspan='8'><!--九月 --><spring:message code="hrm.empinfo.September" /></th>
						<th colspan='8'><!--十月 --><spring:message code="hrm.empinfo.October" /></th>
						<th colspan='8'><!--十一月 --><spring:message code="hrm.empinfo.November" /></th>
						<th colspan='8'><!--十二月 --><spring:message code="hrm.empinfo.December" /></th>
					</tr>
					<tr>
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
						
						<th><!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" /></th>
						<th><!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b" /></th>
						<th><!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" /></th>
						<th><!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" /></th>
						<th><!-- 加班餐补奖励 --><spring:message code="ess.viewMonthDetailList.OT_INCENTIVE.b" /></th>
						<th><!-- 夜间激励 --><spring:message code="ess.viewMonthDetailList.NIGHT_INCENTIVE.b" /></th>
						<th><!-- 周六加班激励 --><spring:message code="ess.viewMonthDetailList.OT_SATURDAY_INCENTIVE.b" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${viewOtYearPersonalList}" var="personList" varStatus="i">
						<tr target="sid" rel="${personList.PERSON_ID_ID}">
							<td style="text-align: center">${i.count}</td>
							<td style="text-align: center">${personList.EMPID_ID}</td>
							<td style="text-align: center">${personList.LOCAL_NAME}</td>
							<td style="text-align: center">${personList.DEPT_NAME}</td>
							<td style="text-align: center">${personList.POST_GRADE_NO}</td>
							<td style="text-align: center">${personList.EMP_TYPE_NAME}</td>
							<td style="text-align: center">${personList.OT_TOTAIL}</td>
							<td style="text-align: center">${personList.JANUARY}</td>
							<td style="text-align: center">${personList.JANUARY_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.JANUARY_SATURDAY}</td>
							<td style="text-align: center">${personList.JANUARY_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.JANUARY_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.JANUARY_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.JANUARY_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.JANUARY_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.FEBRUARY}</td>
							<td style="text-align: center">${personList.FEBRUARY_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.FEBRUARY_SATURDAY}</td>
							<td style="text-align: center">${personList.FEBRUARY_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.FEBRUARY_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.FEBRUARY_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.FEBRUARY_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.FEBRUARY_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.MARCH}</td>
							<td style="text-align: center">${personList.MARCH_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.MARCH_SATURDAY}</td>
							<td style="text-align: center">${personList.MARCH_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.MARCH_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.MARCH_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.MARCH_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.MARCH_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.APRIL}</td>
							<td style="text-align: center">${personList.APRIL_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.APRIL_SATURDAY}</td>
							<td style="text-align: center">${personList.APRIL_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.APRIL_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.APRIL_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.APRIL_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.APRIL_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.MAY}</td>
							<td style="text-align: center">${personList.MAY_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.MAY_SATURDAY}</td>
							<td style="text-align: center">${personList.MAY_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.MAY_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.MAY_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.MAY_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.MAY_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.JUNE}</td>
							<td style="text-align: center">${personList.JUNE_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.JUNE_SATURDAY}</td>
							<td style="text-align: center">${personList.JUNE_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.JUNE_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.JUNE_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.JUNE_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.JUNE_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.JULY}</td>
							<td style="text-align: center">${personList.JULY_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.JULY_SATURDAY}</td>
							<td style="text-align: center">${personList.JULY_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.JULY_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.JULY_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.JULY_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.JULY_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.AUGUST}</td>
							<td style="text-align: center">${personList.AUGUST_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.AUGUST_SATURDAY}</td>
							<td style="text-align: center">${personList.AUGUST_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.AUGUST_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.AUGUST_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.AUGUST_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.AUGUST_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.SEPTEMBER}</td>
							<td style="text-align: center">${personList.SEPTEMBER_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.SEPTEMBER_SATURDAY}</td>
							<td style="text-align: center">${personList.SEPTEMBER_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.SEPTEMBER_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.SEPTEMBER_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.SEPTEMBER_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.SEPTEMBER_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.OCTOBER}</td>
							<td style="text-align: center">${personList.OCTOBER_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.OCTOBER_SATURDAY}</td>
							<td style="text-align: center">${personList.OCTOBER_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.OCTOBER_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.OCTOBER_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.OCTOBER_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.OCTOBER_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.NOVEMBER}</td>
							<td style="text-align: center">${personList.NOVEMBER_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.NOVEMBER_SATURDAY}</td>
							<td style="text-align: center">${personList.NOVEMBER_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.NOVEMBER_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.NOVEMBER_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.NOVEMBER_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.NOVEMBER_SATURDAY_INCEN}</td>
							<td style="text-align: center">${personList.DECEMBER}</td>
							<td style="text-align: center">${personList.DECEMBER_NORMAL_WORK}</td>
							<td style="text-align: center">${personList.DECEMBER_SATURDAY}</td>
							<td style="text-align: center">${personList.DECEMBER_WEEKLY_HOLIDAY}</td>
							<td style="text-align: center">${personList.DECEMBER_PUBLIC_HOLIDAY}</td>
							<td style="text-align: center">${personList.DECEMBER_MEAL_OT_INCEN}</td>
							<td style="text-align: center">${personList.DECEMBER_NIGHT_INCEN}</td>
							<td style="text-align: center">${personList.DECEMBER_SATURDAY_INCEN}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</div>