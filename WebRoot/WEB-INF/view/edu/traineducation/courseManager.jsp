<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanCouno="";
	var quanCou="";
	var xiu=0;
	function xiugaiCou(){
			if(quanCouno!=""){
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongCou(no,nono){
		var count="${courseManagerListCount}";
		for(var i=1;i<=count;i++){
			$('#listCou_'+i).attr('style','');
		}
		$('#listCou_'+no).attr('style','background:#aaccf6');
		$('#updateCou').attr('href','/edu/traineducation/courseManagerInfo?COURSE_NO='+nono);
		$('#yinCou').attr('style','display:none');
		$('#xianCou').attr('style','');
		quanCou=no;
		quanCouno=nono;
	}
	function sousuoCOU(){
		$('#courseManager').submit();
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
    "scrollY": $(document.body).height() - 270,
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
function courseDiff(value){
	codeRelation(value,'TRAIN_TYPE_CODE','${TRAIN_TYPE_CODE}', '<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
</script>
<form id="courseManager" onsubmit="return navTabSearch(this);" action="/edu/traineducation/courseManager" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/></td><!--培训区分-->
		<td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TRAIN_DIFF_CODE" id="TRAIN_DIFF_CODE"
                    parentNo="14014478" cnpyID="${defaultCpny}" selected="${TRAIN_DIFF_CODE }" limit="all" onChangeName="courseDiff(this.value)"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/></td><!--培训类型-->
		<td class="td_type" width="4%">
	        <%-- <ait:SelectSyCodeByCpnyID name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE"
	                    parentNo="14014481" cnpyID="${defaultCpny}" selected="${TRAIN_TYPE_CODE }" limit="all"/> --%>
	        <select name = "TRAIN_TYPE_CODE" id = "TRAIN_TYPE_CODE"></select>
	    </td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/></td><!--课程名称-->
		<td class="td_type" width="4%">
		<input type="text" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE" value="${COURSE_NAME_CODE }">
		</td>
		</tr>
		</table>
		</div>
		</div>
		
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
	
		<ul style = "width: 200px;">
		            <li>
						<a class="buttonActive" href="#" onclick="sousuoCOU()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/></span><!--搜索
						--></a>
					</li>
					<li>
						<a class="buttonActive" href="/edu/traineducation/addCourseManager" target="dialog" mask="true" width="800" height="250" >
							<span><spring:message code="button.add"/></span><!--添加
						--></a>
					</li>
					<li id="yinCou" style="">
						<a class="buttonActive"  onclick="xiugaiCou()" href="#"  width="800" height="250">
							<span><spring:message code="button.update"/></span><!--修改
						--></a>
					</li>
					<li id="xianCou" style="display:none">
						<a class="buttonActive" id="updateCou" onclick="xiugaiCou()" href="#" target="dialog" mask="true"  width="800" height="250">
							<span><spring:message code="button.update"/></span><!--修改
						-->
						</a>
					</li>
				</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
	   <thead>
		<tr>
		    <th  width="1%">NO.</th>
			<th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/></th><!--培训类型-->
			<th  width="5%"><spring:message code="empsubject.subjectNm"/></th><!--课程名称-->
			<th  width="5%"><spring:message code="edu.courseManager.KECHENGBIANHAO.a"/></th><!--课程编号-->
			<th  width="5%"><spring:message code="ar.viewarcardrecord.title.beizhu"/></th><!--备注-->
		</tr>
	   </thead>
	   <tbody>
		<c:forEach items="${courseManagerList}" var="c" varStatus="i">
		<tr id="listCou_${i.count }" onclick="xuanzhongCou('${i.count }','${c.COURSE_NO }')" >
		   <td class="td_type" width="1%" style="text-align: center;">${i.count }.</td>
           <td class="td_type" width="5%">${c.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${c.COURSE_NAME_CODE }</td>
           <td class="td_type" width="5%">${c.COURSE_NUMBER }</td>
           <td class="td_type" width="5%">${c.REMARK }</td>
           </tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>