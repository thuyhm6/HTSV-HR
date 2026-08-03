<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#arForPositionCountInfoList_currentIndex").val('${currentIndex}');
});
</script>
<script type="text/javascript">
function arForPosition_changeURL(name,POSITION) {
	var STIMESS = $("#arForPositionCountInfoList_seach_STIME").attr("value");
	var ETIMESS = $("#arForPositionCountInfoList_seach_ETIME").attr("value");
	var DEPT_NO = $("input[syslong='arForPositionCountInfoList_seachDept']")
			.val();
	var href = "/ess/viewDept/viewOtApplySingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO +  "&POSITION=" + POSITION +  "&ISHTML=arForPositionCount";
	//职责加班现况
	$.pdialog.open(href,"ar0155", "<spring:message code='ar.arForPositionCountInfoSonList.ZHIZEJIABANXIANKUANG.b' />", {width:1000,height:400,mask:true});
}
function changeURL9(name,POSITION) {
	var STIMESS = $("#arForPositionCountInfoList_seach_STIME").attr("value");
	var ETIMESS = $("#arForPositionCountInfoList_seach_ETIME").attr("value");
	var DEPT_NO = $("input[syslong='arForPositionCountInfoList_seachDept']")
			.val();
	var href = "/ess/viewDept/viewArPersonalSingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO +  "&POSITION=" + POSITION +  "&ISHTML=arForPositionCount";
	//职责考勤现况
	$.pdialog.open(href,"ar0155", "<spring:message code='ar.arForPositionCountInfoSonList.ZHIZEKAOQINXIANKUANG.b' />", {width:800,height:400,mask:true});
}
</script>
</head>
<div class="pageContent" id="ar0155_pageContent">
	<c:if test="${currentIndex eq '0'}">
		<script>
$(document).ready(function() {
	$("#ar0155_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0155_table").css("width", $(document.body).width() * 2);
	$("#ar0155_table", navTab.getCurrentPanel()).dataTable( {
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
	     "fixedColumns":{leftColumns: 5},
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
			<table class="orderList" id="ar0155_table" border="1px">
				<thead>
					<tr>
						<th>
							NO
						</th>
						<th>
							<!-- 职责 --><spring:message code="ess.trans.title.dutyName" />
						</th>
						<th>
							<!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" />
						</th>
						<th>
							<!-- 迟到 --><spring:message code="ar.monthwork.title.Lateness" />
						</th>
						<th>
							<!-- 早退 --><spring:message code="ar.monthwork.title.EarlyLeave" />
						</th>
						<th>
							<!-- 旷工 --><spring:message code="ar.monthwork.title.kuanggong" />
						</th>
						<th>
							<!-- 事假 --><spring:message code="ess.viewpersonalpainfo.shijia" />
						</th>
						<th>
							<!-- 年假 --><spring:message code="ar.viewArNavigationPage.NIANJIA.b" />
						</th>
						<th>
							<!-- 病假 --><spring:message code="ar.menu.title.bingjia" />
						</th>
						<th>
							<!-- 工伤假--><spring:message code="ar.menu.title.gongshangjia" />
						</th>
						<th>
							<!-- 婚假--><spring:message code="ar.menu.title.hunjia" />
						</th>
						<th>
							<!-- 丧假--><spring:message code="ar.menu.title.sangjia" />
						</th>
						<th>
							<!-- 产前检查假--><spring:message code="ar.arForDeptCountInfoList.CHANQIANJIANCHAJIA.b" />
						</th>
						<th>
							<!-- 产假--><spring:message code="ar.menu.title.chanjia" />
						</th>
						<th>
							<!-- 哺乳假--><spring:message code="ar.menu.title.purujia" />
						</th>
						<th>
							<!-- 陪产假--><spring:message code="ar.menu.title.peichanjia" />
						</th>
						<th>
							<!--调休--><spring:message code="ar.viewArAnnualStandard.title.tiaoxiu" />
						</th>
						<th>
							<!-- 国内出差 --><spring:message code="ar.arForDeptCountInfoList.GUONEICHUCHAI.b" />
						</th>
						<th>
							<!-- 国外出差 --><spring:message code="ar.arForDeptCountInfoList.GUOWAICHUCHAI.b" />
						</th>
						<th>
							<!-- 外出 --><spring:message code="ar.arForDeptCountInfoList.WAICHU.b" />
						</th>
						<c:if test="${LoginUser.cpnyId eq 'SPC_NJ' or LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
							<th>
							<!-- 计划生育假 --><spring:message code="ar.arForDeptCountInfoList.JIHUASHENGYUJIA.b" />
							</th>
						</c:if>
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
							<th>
							<!-- 公假 --><spring:message code="ar.arForDeptCountInfoList.GONGJIA.b" />
							</th>
						</c:if>
						<!-- <th>
							青年节假
						</th>
						<th>
							妇女节假
						</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${arForDateCountInfoSonArList}" var="personList"
						varStatus="i">
						<tr target="sid" rel="${personList.PERSON_ID}">
							<td style="text-align: center">
								${i.count}
							</td>
							<td style="text-align: center">
								${personList.POSITION_NO_NAME}
							</td>
							<td style="text-align: center">
								${personList.ALLNUM}
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141441","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.CD}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141442","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.ZT}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141443","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.KG}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141454","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.SJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141456","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.NJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141464","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.BJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141465","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.GSJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141458","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.HJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141459","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.DJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange"
							    onclick='javascript:changeURL2("141460","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.CQJCJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141461","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.CJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141462,14013787",${personList.POSITION_NO});'>
									<span style="color: blue">${personList.BRJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("217878","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.PCJ}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("14013845","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.DX}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("14013806","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.GNCC}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("14013807","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.GYCC}</span>
							</td>
							<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("141468","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.WC}</span>
							</td>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ' or LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
								<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("14015979","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.JHSYJ}</span>
							</td>
							</c:if>
							<c:if test="${LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
								<td style="text-align: center;cursor: pointer;"id="codeChange"
								onclick='javascript:changeURL9("14015980","${personList.POSITION_NO}");'>
									<span style="color: blue">${personList.GJ}</span>
							</td>
							</c:if>
							<%-- <td style="text-align: center">
								<a style="cursor: pointer;" title="${personList.POSITION_NO}" width="800" height="400"
									id="codeChange" onclick='javascript:changeURL9(this);'
									name="141467"  title="青年节假"
									target="dialog"> <span>${personList.QNJJ}</span> </a>
							</td>
							<td style="text-align: center">
								<a style="cursor: pointer;" title="${personList.POSITION_NO}" width="800" height="400"
									id="codeChange" onclick='javascript:changeURL9(this);'
									name="141466"  title="妇女节假"
									target="dialog"> <span>${personList.FNJJ}</span> </a>
							</td> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
<c:if test="${currentIndex eq '1'}">
<script>
$(document).ready(function() {
	$("#ar0155_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0155_table2").css("width", $(document.body).width() - 50);
	$("#ar0155_table2", navTab.getCurrentPanel()).dataTable( {
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
		<table class="orderList" id="ar0155_table2">
			<thead>
				<tr>
					<th>
						NO
					</th>
					<th>
						<!-- 职责 --><spring:message code="ess.trans.title.dutyName" />
					</th>
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
				<c:forEach items="${arForDateCountInfoSonOtList}" var="personList"
					varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">
							${i.count}
						</td>
						<td style="text-align: center">
							${personList.POSITION_NO_NAME}
						</td>
						<td style="text-align: center">
							${personList.WEEKDAY_OT_TOTAIL+personList.WEEKEND_OT_TOTAIL+personList.HOILDAY_OT_TOTAIL}
						</td>
						<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForPosition_changeURL("141444,218198,14015981,14016213","${personList.POSITION_NO}");'>
								<span style="color: blue">${personList.WEEKDAY_OT_TOTAIL}</span>
						</td>
						<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForPosition_changeURL("141445,278598,141452,14016214","${personList.POSITION_NO}");'>
								<span style="color: blue">${personList.WEEKEND_OT_TOTAIL}</span>
						</td>
						<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForPosition_changeURL("141446,278599","${personList.POSITION_NO}");'>
								<span style="color: blue"> ${personList.HOILDAY_OT_TOTAIL}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>