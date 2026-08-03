<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#ar0153_currentIndex").val('${currentIndex}');
});
</script>
<style type="text/css"></style>
<script type="text/javascript">
function arForDate_changeURL(name,obj) {
	var AR_DATE_STR = obj.title;
	AR_DATE_STR = AR_DATE_STR.substring(6,10)+ "/" +AR_DATE_STR.substring(3,5)+ "/" +AR_DATE_STR.substring(0,2);
	var DEPT_NO = $("input[syslong='arForDateCountInfoList_seachDept']").val();
	var GROUP = $("#arForDateCountInfoList_GROUP").attr("value");
	var KEY = $("#arForDateCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewOtApplySingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + AR_DATE_STR + "&ETIME="
			+ AR_DATE_STR + "&DEPT_NO=" + DEPT_NO + "&GROUP=" + GROUP + "&KEY="
			+ KEY + "&AR_DATE_STR=" + AR_DATE_STR;
	//日期加班现况
	$.pdialog.open(href,"ar0153", "<spring:message code='ar.arForDateCountInfoSonList.RIQIJIABANXIANKUANG.b'/>", {width:1000,height:400,mask:true});
}
function changeURL5(name,obj) {
	var AR_DATE_STR = obj.title;
	AR_DATE_STR = AR_DATE_STR.substring(6,10)+ "/" +AR_DATE_STR.substring(3,5)+ "/" +AR_DATE_STR.substring(0,2);
	var DEPT_NO = $("input[syslong='arForDateCountInfoList_seachDept']").val();
	var GROUP = $("#arForDateCountInfoList_GROUP").attr("value");
	var KEY = $("#arForDateCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewArPersonalSingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + AR_DATE_STR + "&ETIME="
			+ AR_DATE_STR + "&DEPT_NO=" + DEPT_NO + "&GROUP=" + GROUP + "&KEY="
			+ KEY + "&AR_DATE_STR=" + AR_DATE_STR;
	//日期考勤现况
	$.pdialog.open(href,"ar0153", "<spring:message code='ar.arForDateCountInfoSonList.RIQIKAOQINXIANKUANG.b'/>", {width:800,height:400,mask:true});
}
</script>
</head>
<c:if test="${currentIndex eq '0'}">
	<div class="pageContent" id='ar0153_pageContent'>
		<script>
$(document).ready(function() {
	$("#ar0153_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0153_table").css("width", $(document.body).width() * 2);
	$("#ar0153_table", navTab.getCurrentPanel()).dataTable( {
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
			<table class="orderList" border="1" id='ar0153_table' >
				<thead>
					<tr>
						<th>
							NO
						</th>
						<th>
							<!-- 日期 --><spring:message code="ess.infoApply.date" />
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
								${personList.AR_DATE_STR}
							</td>
							<td style="text-align: center">
								${personList.HEJI}
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141441",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.CHIDAO}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141442",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.ZAOTUI}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141456",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.NIANJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141454",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.SHIJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141464",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.YIBANBINGJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000286",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.HAIZIBINGJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000287",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.CHANGQIBINGJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000289",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.GERENHUNJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000290",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.HAIZIHUNJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000288",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.GONGSHANGJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000291",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.YOUXINSANGJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange"
								onclick='javascript:changeURL5("141461",this);' title='${personList.AR_DATE_STR}'>
								<span style="color: blue">${personList.CHANJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000292",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.PEICHANJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000293",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.BURUJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141460",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.CHANQIANJIANCHAJIA}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("90000294",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.CHUCHAI}</span>
							</td>
							<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("141443",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.KUANGGONG}</span>
							</td>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ' or LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
								<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("14015979",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.JHSYJ}</span>
								</td>
							</c:if>
							<c:if test="${LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ'}">
								<td style="text-align: center;cursor: pointer;" id="codeChange" 
								onclick='javascript:changeURL5("14015980",this);' title='${personList.AR_DATE_STR}'>
									<span style="color: blue">${personList.GJ}</span>
								</td>
							</c:if>
							<%-- <td style="text-align: center">
								<a style="cursor: pointer;"  title="${personList.AR_DATE_STR}" width="800" height="400"
									id="codeChange" onclick='javascript:changeURL5(this);'
									name="141467"  title="青年节假"
									target="dialog"> <span>${personList.QNJJ}</span> </a>
							</td>
							<td style="text-align: center">
								<a style="cursor: pointer;"  title="${personList.AR_DATE_STR}" width="800" height="400"
									id="codeChange" onclick='javascript:changeURL5(this);'
									name="141466" title="妇女节假"
									target="dialog"> <span>${personList.FNJJ}</span> </a>
							</td> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
<c:if test="${currentIndex eq '1'}">
<script>
$(document).ready(function() {
	$("#ar0153_pageContent").css("height", $(document.body).height() - 220);
	$(".tabsContent").css("height", $(document.body).height() - 210);
	$("#ar0153_table2").css("width", $(document.body).width());
	$("#ar0153_table2", navTab.getCurrentPanel()).dataTable( {
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
	<table class="orderList" id='ar0153_table2'>
		<thead>
			<tr>
				<th>
					NO
				</th>
				<th>
					<!-- 日期 --><spring:message code="ess.infoApply.date" />
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
						${personList.AR_DATE_STR}
					</td>
					<td style="text-align: center">
						${personList.WEEKDAY_OT_TOTAIL+personList.WEEKEND_OT_TOTAIL+personList.HOILDAY_OT_TOTAIL}
					</td>
					<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForDate_changeURL("90000295,90000296,90000297",this);' title='${personList.AR_DATE_STR}'>
							 <span style="color: blue">${personList.WEEKDAY_OT_TOTAIL}</span>
					</td>
					<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForDate_changeURL("90000298,90000299",this);' title='${personList.AR_DATE_STR}'>
							 <span style="color: blue">${personList.WEEKEND_OT_TOTAIL}</span>
					</td>
					<%-- <td style="text-align: center">
						<a style="cursor: pointer;" title="${personList.AR_DATE_STR}"
							mask="true" width="1000" height="400" id="codeChange"
							onclick='javascript:arForDate_changeURL(this);' name="141446"
							type="${personList.PERSON_ID_ID}" target="dialog"> <span>
								${personList.HOILDAY_OT_TOTAIL}</span> </a> --%>
					<td style="text-align: center;cursor: pointer;" id="codeChange" 
							onclick='javascript:arForDate_changeURL( "90000300,90000301",this);' title='${personList.AR_DATE_STR}'>
							 <span style="color: blue"> ${personList.HOILDAY_OT_TOTAIL}</span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>