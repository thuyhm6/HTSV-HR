.<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSearchOtInfo&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSearchOtInfo&seach_KEY='+name);
    });
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		   // "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 300,
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
		        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
            } //多语言配置
		});
});

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewSearchOtInfo" method="post"
		id="viewSearchOtInfoForm">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
					</td>
					<td></td>
				</tr>
				<tr>
				    <td>
						<!-- 期间 --><spring:message code="ess.workgroup.title.duration"/>
					</td>
					<td>
						<input type="text" name="seach_FROM_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${FROM_DATE }"/>~
						<input type="text" name="seach_TO_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${TO_DATE }"/>
					</td>
					<td><!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/> </td>
					<td>
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_GROUP_NO" parentNo="400223" include="400228,400229,400226" selected="${GROUP_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyLeaveInfoBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyLeaveInfoBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
							    <button type="submit">
							       <spring:message code="public.title.search"/> 
							    </button>
				        	</div>
				        </div>
				    </li>
					<li>
						<div class="buttonActive">
							<a onclick="downloadExcel('viewSearchOtInfoForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=58','/ar/attendanceMintenance/viewSearchOtInfo')"><span>导出到Excel</span></a>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
		<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${otInfoListSize}</div>
		</div>
		<table class="list" width="100%">   
			<thead>
				<tr>
					<th><!--NO-->
						NO
					</th>
					<th>
				    	<!--姓名--><spring:message code="hrm.empinfo.name" />
				    </th>
					<th>
						<!--社号--><spring:message code="hrm.empinfo.empid" />
					</th>
					<th>
						<!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" />
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank" />
					</th>
					<th>
						<!--实际出勤--><spring:message code="ar.viewSearchOtInfo.SHIJICHUQIN.b" />
					</th>
					<th>
						<!--应出勤--><spring:message code="ar.viewSearchOtInfo.YINGCHUQIN.b" />
					</th>
					<th>
						<!--超出勤时间--><spring:message code="ar.viewSearchOtInfo.CHAOCHUQINSHIJIAN.b" />
					</th>
					<th>
						<!--申请加班时数--><spring:message code="ar.viewSearchOtInfo.SHENQINGJIABANSHISHU.b" />
					</th>
					<th>
						<!--加班时数--><spring:message code="ar.viewSearchOtInfo.JIABANSHISHU.b" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otInfoList}" var="item" varStatus="i">
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${item.LOCAL_NAME}</td>
						<td style="text-align: center">${item.EMPID}</td>
						<td style="text-align: center">${item.DEPTNAME}</td>
						<td style="text-align: center">${item.POST_GRADE_NAME}</td>
						<td style="text-align: center">${item.A}</td>
						<td style="text-align: center">${item.B}</td>
						<td style="text-align: center">${item.A - item.B}</td>
						<td style="text-align: center">${item.C}</td>
						<td style="text-align: center">${item.A - item.B + item.C}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>