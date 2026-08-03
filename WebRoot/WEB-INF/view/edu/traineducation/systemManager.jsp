<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanSysno="";
	var quanSys="";
	var xiu=0;
	function xiugai(){
			if(quanSysno!=""){
				var remark=$('#REMARK_'+quanSysno).val();
				$('#updateSys').attr('href','/edu/traineducation/systemManagerInfo?SYSMANA_NO='+quanSysno);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhong(no,nono){
		var count="${systemManagerListCount}";
		for(var i=1;i<=count;i++){
			$('#list_'+i).attr('style','');
		}
		$('#list_'+no).attr('style','background:#aaccf6');
		$('#updateSys').attr('href','/edu/traineducation/systemManagerInfo?SYSMANA_NO='+nono);
		$('#yin').attr('style','display:none');
		$('#xian').attr('style','');
		quanSys=no;
		quanSysno=nono;
	}
	function sousuoSYS(){
		$('#systemManager').submit();
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

function trainDiv(value){
    codeRelation(value,'TRAIN_TYPE_CODE','${TRAIN_TYPE_CODE}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
function deleteSystemMan(){
	if(quanSysno!=""){
			$('#deleteSystemMan').attr('href','/edu/traineducation/deleteSystemMan?SYSMANA_NO='+quanSysno);
	}else{
		$('#deleteSystemMan').attr('href','/edu/traineducation/deleteSystemMan?{sysMange}');
    }
}
	
</script>
<form id="systemManager" onsubmit="return navTabSearch(this);" action="/edu/traineducation/systemManager" method="post">
<div class="pageHeader" >
<div class="searchBar">
    <div style="margin-left:auto;margin-right:auto;width:97%;" > 
    <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%">
		<spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/>
		<!--培训区分--></td>
		<td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TRAIN_DIFF_CODE" id="TRAIN_DIFF_CODE"
                    parentNo="14014478" cnpyID="${defaultCpny}" selected="${TRAIN_DIFF_CODE }" limit="all"  onChangeName="trainDiv(this.value);"/>
		</td>
		<td class="td_title" width="4%">
		<spring:message code="edu.systemManager.PEIXUNLEIXING.a"/>
		<!--培训类型--></td>
		<td class="td_type" width="4%">
			<%-- <div id="transReasonDiv1">
		        <ait:SelectSyCodeByCpnyID name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE"
	                    parentNo="14014481" cnpyID="${defaultCpny}" selected="${TRAIN_TYPE_CODE }" limit="all"/>
			</div> --%>
			<select name ="TRAIN_TYPE_CODE" id = "TRAIN_TYPE_CODE"></select>
		</td>
		</tr>
		</table>
		 </div> 
		</div>
		
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
	
		
		<ul style = "width: 200px;">
            <li>
				<a class="buttonActive" href="#" onclick="sousuoSYS()" width="800" height="250" >
					<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" href="/edu/traineducation/addSystemManager" target="dialog" mask="true" width="800" height="250" >
					<span><spring:message code="button.add"/><!--添加--></span>
				</a>
			</li>
			<li id="yin" style="">
				<a class="buttonActive"  onclick="xiugai()" href="#"  width="800" height="250">
					<span><spring:message code="button.update"/><!--修改--></span>
				</a>
			</li>
			<li id="xian" style="display:none">
				<a class="buttonActive" id="updateSys" onclick="xiugai()" href="#" target="dialog" mask="true"  width="800" height="250">
					<span><spring:message code="button.update"/><!--修改--></span>
				</a>
			</li>
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<li><!--确定是否删除? -->
				<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/>"  id="deleteSystemMan" onclick="deleteSystemMan()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
					<span><spring:message code="button.delete"/></span><!--删除--></a>
			</li>
		   </c:if>
		</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
	<thead>
		<tr>
		    <th  width="1%">NO.</th>
			<th  width="5%"><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/><!--培训区分--></th>
			<th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="5%"><spring:message code="edu.systemManager.LEIXINGBIANHAO.a"/><!--类型编号--></th>
			<th  width="5%"><spring:message code="org.title.REMARK"/><!--备注--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${systemManagerList}" var="s" varStatus="i">
		<tr target="sysMange" id="list_${i.count }" onclick="xuanzhong('${i.count }','${s.SYSMANA_NO }')" >
		   <td class="td_type" width="1%" style="text-align: center;">${i.count }.</td>
           <td class="td_type" width="5%">${s.TRAIN_DIFF_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_NO }</td>
           <td class="td_type" width="5%">${s.REMARK }</td>
           </tr>
		</c:forEach>
	</tbody>	
	</table>

</div>
</div>
</form>