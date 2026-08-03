<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function sousuoARC(){
	$('#trainArchives').submit();
}
function changeArc(){
	var arcEmpidName=$('#arcEmpidName').val();
	var arcDepartno=$('input[name=arcDepartno]').attr('value');
	var arcCourseName=$('#arcCourseName').val();
	var actStartdate=$('#actStartdate').val();
	var actEnddate=$('#actEnddate').val();
	var arcTrainContent=$('#arcTrainContent').val();
	$('#arcImport').attr('href','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=164&arcEmpidName='
			+arcEmpidName+'&arcDepartno='+arcDepartno+'&arcCourseName='+arcCourseName
			+'&actStartdate='+actStartdate+'&actEnddate='+actEnddate+'&arcTrainContent='+arcTrainContent);
	
}
$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":true,
    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
	"bLengthChange": true,  //按多少条记录显示下拉框
	"iDisplayLength": 50, //默认每页显示的记录数
    "bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
    "bSort": true,   //关闭排序功能
    "bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
    "bScrollInfinite":true,
    "scrollY": true,
    "scrollX": true,
    "orderClasses": false,
    "order":[],//初始化不用自动排序
    "scrollY": $(document.body).height() - 300,
    "scrollCollapse": false,
    "deferRender":true,
    "fixedColumns":false,
    "oLanguage": {//多语言配置
        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
        "oPaginate": {
            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
        }
    }
});
</script>
<form id="trainArchives" onsubmit="return navTabSearch(this);" action="/edu/traineducation/trainArchives" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/></td><!--社号/姓名
		--><td class="td_type" width="4%">
		<input type="text" name="arcEmpidName" id="arcEmpidName" value="${arcEmpidName }"  >
		</td>
		<td class="td_title" width="4%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></td><!--部门
		--><td class="td_type" width="4%">
		 <ait:deptList name="arcDepartno" cpnyId="${defaultCpny}" limit="super" id="arcDepartnoId"  />
		 <ait:deptTreeIcon name="arcDepartno" limit="super" id="arcDepartnoId" selected="${arcDepartno}"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/></td><!--课程名称
		--><td class="td_type" width="4%">
		<input type="text" name="arcCourseName" id="arcCourseName" value="${arcCourseName }"  >
		</td>
		</tr>
		<tr>
		<td class="td_title" width="4%"><spring:message code="edu.trainArchives.SHISHIKAISHIRIQI.a"/></td><!-- 实施开始日期
		--><td class="td_type" width="4%">
		<input name="actStartdate" id="actStartdate"   onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${actStartdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.trainArchives.SHISHIJIESHURIQI.a"/></td><!--实施结束日期
		--><td class="td_type" width="4%">
		<input name="actEnddate" id="actEnddate"   onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${actEnddate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.trainArchives.PEIXUNNEIRONG.a"/></td><!--培训内容
		--><td class="td_type" width="4%">
		<input name="arcTrainContent" id="arcTrainContent"  value="${arcTrainContent}"/>
		</td>
		</tr>
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 150px;">               
		            
		            <li>
						<a class="buttonActive" href="#" onclick="sousuoARC()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索
						--></span></a>
					</li>
					<li>
						<a class="buttonActive" id="arcImport" href="#" onclick="changeArc()">
							<span><spring:message code="inct.salesman.downloadToExcel"/><!--Excel导出--></span>
						</a>
					</li>
				</ul>
</div>
<div style="padding-top: 30px;">
	<table id="viewInfoTable"  class="orderList"  width="1890px">
	<thead>
		<tr >
		    <th >NO.</th>
		    <th ><spring:message code="inct.salesman.empNo"/><!--社号--></th>
			<th ><spring:message code="pa.title.message.empHrmName"/><!--姓名--></th>
			<th ><spring:message code="empsubject.sexName"/><!--性别--></th>
			<th ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></th>
			<th ><spring:message code="hrm.contract.Rank"/><!--职级--></th>
			<th ><spring:message code="ess.empInfo.date_of_agency"/><!--入社日期--></th>
			<th ><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></th>
			<th ><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></th>
			<th ><spring:message code="edu.trainArchives.SHISHIQIJIAN.a"/><!--实施期间--></th>
			<th ><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
			<th ><spring:message code="edu.planManager.ZHUGUANBUMEN.a"/><!--主管部门--></th>
			<th ><spring:message code="empsubject.eduRm"/><!--培训地点--></th>
			<th ><spring:message code="edu.trainArchives.ZONGHECHENGJI.a"/><!--综合成绩--></th>
			<th ><spring:message code="edu.trainArchives.PEIXUNFEI.a"/><!--培训费--></th>
			<th ><spring:message code="edu.trainArchives.BAOGAOSHU.a"/><!--报告书--></th>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${trainArchivesList}" var="s" varStatus="i">
		<tr>
		   <td style="text-align:center;">${i.count }.</td>
           <td style="text-align:center;" >${s.EMPID }</td>
           <td style="text-align:center;" >${s.LOCAL_NAME }</td>
           <td style="text-align:center;" >${s.SEXCODENAME }</td>
           <td style="text-align:center;" ">${s.ORG_NAME_LOCAL }</td>
           <td style="text-align:center;" >${s.POST_GRADE_NO_NAME }</td>
           <td style="text-align:center;" >${s.DATE_STARTED }</td>
           <td style="text-align:center;" >${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
                                                                                                                                      <!--  第 -->                                                                  <!--  期 -->
          <td style="text-align:center;" >${s.TRAIN_CONTENT}</td>
           <td style="text-align:center;" >${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td style="text-align:center;" >${s.IMPLE_CLASS_HOUR }&nbsp
			<c:if test="${s.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
           </td>
           <td style="text-align:center;" >${s.DEPART_MANA_CODE_NAME }</td>
           <td style="text-align:center;" >${s.TRAIN_ADDRESS }</td>
           <td  >${s.EVA_RESULT }</td>
           <td style="text-align:center;" >${s.ALL_COST }</td>
           <td style="text-align:center;" >
           <c:forEach items="${s.fileList}" var="item" varStatus="i">
					<span style="color:blue">${i.count }.</span>
					<a style="color:blue" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a><br>
				    </c:forEach>
           </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>