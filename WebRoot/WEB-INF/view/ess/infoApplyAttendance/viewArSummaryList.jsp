<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>

 /* function changeURL(obj) {

	obj.href="/ess/viewDept/viewArSummarySingleList?PERSON_ID="+obj.type;

}  */
 
 
 function showEmpDetail(obj,name){
	var AR_MONTH = $("#seach_AR_MONTH",navTab.getCurrentPanel().val()).attr("value");
		$.pdialog.open('/ess/viewDept/viewArSummarySingleList?PERSON_ID='+obj+ "&AR_MONTH=" + AR_MONTH  + "&ITEM_NO=" + name , 
				"ar0701_3_showEmpDetail", "<spring:message code='ess.viewArSummaryList.HUIZONGGERENXINXI.a' />", {width:1200,height:500,mask:true});//汇总个人信息
	}

 
 $("#viewArSummaryList_Serch").click(function(){
		$("#viewArSummaryList").submit();
});
 
 $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
	 var dataSearch=$('#beginSearch').val();
     if ( e.keyCode == 13) {
    	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
    	    var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
    		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=viewArSummaryList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
    		$('.btnLook',navTab.getCurrentPanel()).click();
     }
  });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
	 var dataSearch=$('#beginSearch').val();
   	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
   	 var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
   	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=viewArSummaryList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
  });
 
		$(".orderList",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":false,//表格宽度自动变化
		    "bProcessing":false,
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
		     "scrollY": $(document.body).height() - 310,
		     "scrollX": $(document.body).width(),
		     "scrollCollapse": false,
		     "deferRender":true,
		        "columnDefs": [//自定义排序类型
			                     { "orderable": false, "targets": [] }
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
/*
function exportExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewArVacationMonth");  
     
     var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}*/
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewArSummaryList?firstFlag=N" 
		method="post" id="viewArSummaryList" name="viewArSummaryList" > 
		<input type="hidden" name='CODE_NO' />
		<input type="hidden" id="beginSearch" value="viewArSummaryList">
		<input type="hidden" name=defaultRoleGroupName value="${defaultRoleGroupName}" />
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			    <tr>					
					<td width="7%"><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<!-- <div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div> -->
					</td>
					<%-- <td width="50%" colspan="5">
						<c:if test="${not empty personInfo}">
						${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }
						</c:if>
					</td> --%>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
					     <input type="text" name="seach_AR_MONTH" id="seach_AR_MONTH"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM'})" value="${AR_MONTH}"/>
											</td>
					<%-- <td >班组 </td>
					<td >
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_GROUP_NO" parentNo="400223" selected="${GROUP_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td> --%>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewArSummaryList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewArSummaryList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!-- 考勤状态 --><spring:message code="ess.infoApply.attendState" /></td>	 
					<td>
					     <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" ><!-- 请选择 --><spring:message code="org.title.PLEASE_SELECT" /></option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%"><!--员工类型 --><spring:message code="org.title.EMP_TYPE" /></td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="10%"><!--任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
					<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(viewArSummaryList)}</div>
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewArSummaryList_Serch" href="#" ><span><!--查询 --><spring:message code="org.title.SELECT" /></span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('viewArSummaryList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=228&firstFlag=N','/ess/viewDept/viewArSummaryList?firstFlag=N')"><span><!--导出到Excel --><spring:message code="org.title.exportLOtImportExcel" /></span></a></li>
	 </ul>
</div>
	<table class="orderList"  width="2770px">
			<thead>
				<tr>
					<th width="30px">NO</th>
					<th width="70px"><!--姓名 --><spring:message code="org.title.LOCAL_NAME" /></th>
					<th width="70px"><!--社号 --><spring:message code="org.title.EMPID" /></th>
					<th width="170px"><!--部门名 --><spring:message code="ess.infoApply.DEPT_NAME" /></th>
					<th width="100px"><!--合计工时 --><spring:message code="ess.infoApply.hejigongshi" /></th>
					<th width="100px"><!--加班时数 --><spring:message code="ess.infoApply.jiabanshishu" /></th>
					<th width="100px"><!--应出勤天数 --><spring:message code="ess.infoApply.yingchuqintianshu" /></th>
					<th width="100px"><!--实际出勤天数 --><spring:message code="ess.infoApply.shijichuqintianshu" /></th>
					<th width="100px"><!--未出勤天数 --><spring:message code="ess.title.weiqintianshu" /></th>
					 <th width="100px"><!--休息天数 --><spring:message code="ess.title.xiuxitianshu" /></th> 
					<th width="100px"><!--迟到次数 --><spring:message code="ess.infoApply.LATE_TIMES" /></th>
					<th width="100px"><!--早退次数 --><spring:message code="ess.infoApply.LEAVE_EARLY_TIMES" /></th>
					<th width="100px"><!--旷工天数 --><spring:message code="ess.infoApply.withoutWork_days" /></th>
					<th width="100px"><!--平日加班时数 --><spring:message code="ess.infoApply.overtime_weekdays_hours" /></th>
					<th width="100px"><!--周末加班时数 --><spring:message code="ess.infoApply.overtime_weekend_hours" /></th>
					<th width="100px"><!--法定加班时数 --><spring:message code="ess.infoApply.overtime_legal_hours" /></th>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<th width="100px"><!--腾讯加班时数 --><spring:message code="ess.infoApply.tengxunot" /></th>
					</c:if>
					<th width="100px"><!--事假时数 --><spring:message code="ess.infoApply.thing_leave_hours" /></th>
					<th width="100px"><!--病假时数 --><spring:message code="ess.infoApply.sick_leave_hours" /></th>
					<th width="70px"><!--年假 --><spring:message code="ar.viewArAnnualStandard.title.ninjia" /></th>
					<th width="100px"><!--工伤假时数 --><spring:message code="ess.infoApply.industrial_injury_hours" /></th>
					<th width="100px"><!--婚假时数 --><spring:message code="ess.infoApply.marriage_holiday_hours" /></th>
					<th width="100px"><!--陪产假时数 --><spring:message code="ess.infoApply.accompany_maternity_leave_hours" /></th>
					<th width="100px"><!--产假时数 --><spring:message code="ess.infoApply.maternity_leave_hours" /></th>
					<th width="100px"><!--丧假时数 --><spring:message code="ess.infoApply.funeral_hours" /></th>
					<th width="100px"><!--产检假时数 --><spring:message code="ess.infoApply.production_check_hours" /></th>
					<th width="100px"><!--哺乳假时数 --><spring:message code="ess.infoApply.lactation_leave_hours" /></th>
					<th width="100px"><!--外出时数 --><spring:message code="ess.infoApply.outgoing_hours" /></th>
					<th width="100px"><!--国内出差时数 --><spring:message code="ess.infoApply.domestic_travel_hours" /></th>
					<th width="100px"><!--国外出差时数 --><spring:message code="ess.infoApply.foreign_business_trip_hours" /></th>
				</tr>
			
			</thead>
			<tbody>
				<c:forEach items="${viewArSummaryList}" var="personList" varStatus="i" >
					<tr target="sid" rel="">
						<td  style="text-align: center" >
							${i.count}
						</td>
						<td  style="text-align: center" >
							${personList.LOCAL_NAME}
						</td>
						<td  style="text-align: center" >
							${personList.EMPID}
						</td>
						<td  style="text-align: center" >
							${personList.DEPT_NAME}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer;"  </c:if> --%>>
								<span>${personList.WORK_HOURS}</span>
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer;"  </c:if> --%>>
								<span>${personList.PINGRIJIABAN + personList.ZHOUMOJIABAN + personList.JIEJIARIJIABAN}</span>
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer;"  </c:if> --%>>
								<span>${personList.ACTUAL_WORK_DAYS}</span>
						</td>
						<%-- <td width="50px;" style="text-align: center">
							<c:if test="${defaultRoleGroupName eq 'Management'}">
							<a style="cursor: pointer;" width="800"  height="400"  id="codeChange"
								 type="${personList.PERSON_ID}" onclick='javascript:changeURL(this);'
								  target="dialog"></c:if>
								<span>${personList.ACTUAL_WORK_DAYS}</span>
							<c:if test="${defaultRoleGroupName eq 'Management'}"> </a></c:if> 
							
						</td> --%>
						
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141439')" </c:if> --%>>
							${personList.WORK_DAYS}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer;"  </c:if> --%>>
						${personList.ACTUAL_WORK_DAYS - personList.WORK_DAYS}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141440')" </c:if> --%>>
							${personList.RESTDAYS}
						</td> 
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141441')" </c:if> --%>>
							${personList.CHIDAO}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141442')" </c:if> --%>>
							${personList.ZAOTUI}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141443')" </c:if> --%>>
							${personList.KUANGGONG}
						</td>
						<td  style="text-align: center;"
						<%-- <c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141444,218198,14015981')" </c:if> --%>>
							${personList.PINGRIJIABAN}
						</td>
						<td  style="text-align: center;"
						<%-- <c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141445,141452,14016214')" </c:if> --%>>
							${personList.ZHOUMOJIABAN}
						</td>
						<td  style="text-align: center;"
						<%-- <c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141446,141445,278598')" </c:if> --%>>
							${personList.JIEJIARIJIABAN}
						</td>
						<td  style="text-align: center;"
						<%-- <c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141446,141445,278598')" </c:if> --%>>
							${personList.JIEJIARIJIABAN}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
							 <c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141454')" </c:if> --%> >
							${personList.SHIJIA}
						</td>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141464')" </c:if> --%>>
							${personList.TENCENT_OT}
						</td>
						</c:if>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}','141456')" </c:if> --%>>
							${personList.ANNUAL_LEAVE}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141465')" </c:if> --%>>
							${personList.GONGSHANGJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141458')" </c:if> --%>>
							${personList.HUNJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '217878')" </c:if> --%>>
							${personList.PEICHANJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141461')" </c:if> --%>>
							${personList.CHANJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141459')" </c:if> --%>>
							${personList.SANGJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141460')" </c:if> --%>>
							${personList.CHANJIANJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141462')" </c:if> --%>>
							${personList.BURUJIA}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '141468')" </c:if> --%>>
							${personList.WAICHU}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '14013806')" </c:if> --%>>
							${personList.GUONEICHUCHAI}
						</td>
						<td style="text-align: center;"<%-- <c:if test="${defaultRoleGroupName ne 'Management'}"> style="text-align: center;"</c:if>
						<c:if test="${defaultRoleGroupName eq 'Management'}"> style="text-align: center;cursor: pointer; color:blue" onclick="showEmpDetail('${personList.PERSON_ID}', '14013807')" </c:if> --%>>
							${personList.GUOWAICHUCHAI}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>