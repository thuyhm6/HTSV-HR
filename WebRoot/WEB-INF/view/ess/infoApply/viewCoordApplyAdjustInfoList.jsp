<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
     $("#viewCoordApplyAdjustInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewCoordApplyAdjustInfoList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyAdjustInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyAdjustInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
     });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 200,
           // "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
function searchPop_ess3420(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	var seach_FROM_DATE = $("#seach_FROM_DATE", navTab.getCurrentPanel()).val();
	var seach_TO_DATE = $("#seach_TO_DATE", navTab.getCurrentPanel()).val();
	var refreshUrl = '/ess/infoApply/viewCoordApplyAdjustInfoList?firstFlag=N&seach_FROM_DATE='+seach_FROM_DATE+'&seach_TO_DATE='+seach_TO_DATE;
	var refreshMenuCode = 'ess3420';
	var refreshMenuName = encodeURI(encodeURI('倒休查询'));
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_ess3420", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess3420", navTab.getCurrentPanel()).click();
}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}

function selectAdjust(){
   if(document.getElementById("seach_ADJSTYN").checked){
   $('#ADJSTYN').val(1);
   }else{
    $('#ADJSTYN').val(0);
   }
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewCoordApplyAdjustInfoList?firstFlag=N" method="post"
		id="viewCoordApplyAdjustInfoList" name="viewCoordApplyAdjustInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			    <tr>					
					<td width="7%">社号/姓名</td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td width="50%" colspan="5">
						<c:if test="${not empty personInfo}">
						<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
					     <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE}"/>
					  ~
					   <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE}"/>
					</td>
					</td>
					<td >班组 </td>
					<td >
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_GROUP_NO" parentNo="400223" selected="${GROUP_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
					<td colspan="2">工作形态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					        <option value="">请选择</option> 
						    <c:forEach items="${shiftList}" var="item">
							 <option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
							   ${item.SHIFT_SHORTNAME}
						   </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				   <td>考勤状态</td>	 
					<td>
					     <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" >请选择</option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
							
					</td>
					<td>
					    审批状态 
					</td>
					<td>
					  <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td >
					   状态
					</td>
					<td >
					  <select id="seach_CONFIRM_FLAG" name="seach_CONFIRM_FLAG" >
					     <option value="">请选择</option>
					     <option value="1"  <c:if test="${CONFIRM_FLAG eq 1}">selected</c:if>
											>Confirmed</option>
					     <option value="0"<c:if test="${CONFIRM_FLAG eq 0}">selected</c:if>
											>Unconfirmed</option>
					  </select>
					</td>
				</tr>
				<tr>
					<td width="10%">员工类型</td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="10%">任职状态</td>
					<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td >
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewCoordApplyAdjustInfoList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewCoordApplyAdjustInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(otCoordList)}</div>
	<ul class="toolBar">
	     <li><a class="buttonActive" id="viewCoordApplyAdjustInfoList_Serch" href="#"><span>查询</span></a></li>
		 <li><a class="buttonActive" onclick="downloadExcel('viewCoordApplyAdjustInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=133','/ess/infoApply/viewCoordApplyAdjustInfoList?firstFlag=N')"><span>导出到Excel</span></a></li>
	 </ul>
</div>         
		<table class="orderList" >
			<thead>
				<tr><th width="10px;" rowspan="2"><!--NO-->
						NO
					</th >
					<th width="25px;" rowspan="2">
				    	状态
				    </th>
					<th rowspan="2">
				    	姓名
				    </th>
					<th width="55px;" rowspan="2">
						社号
					</th>
					<th rowspan="2"> 
						部门名
					</th>
					<th rowspan="2">
						职级
					</th rowspan="2">
					<th width="75px;" rowspan="2"><!--申请日期-->
						日期
					</th>
					<th width="45px;" rowspan="2"><!--星期-->
						星期
					</th>
					<!-- <th rowspan="2">班次
						班次
					</th> -->
					<th width="75px;" rowspan="2">
						工作时间
					</th>
					<th colspan="5"><!--工作时间-->
						加班
					</th>
					<th colspan="2"><!--原因-->
						原因
					</th>
					<th rowspan="2"><!--决裁情况-->
						审批状态
					</th>
				</tr>
				<tr>
					<th width="25px;"><!--工作时间-->
						进门
					</th>
					<th width="25px;"><!--工作时间-->
						出门
					</th>
					<th width="55px;"><!--申请时长-->
						开始时间
					</th>
					<th width="55px;"><!--申请时长-->
						结束时间
					</th>
					<th width="55px;"><!--时长-->
						加班时间
					</th>
					<th><!--原因-->
						原因
					</th>
					<th><!--原因-->
						其他原因
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otCoordList}" var="CoordOtApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${CoordOtApply.CONFIRM_FLAG}</td>
					    <td style="text-align: center">${CoordOtApply.LOCAL_NAME}</td>
						<td style="text-align: center">${CoordOtApply.EMPID}</td>
						<td style="text-align: center">${CoordOtApply.DEPARTMENT}</td>
						<td style="text-align: center">${CoordOtApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordOtApply.AR_DATE_STR}</td>
						<td style="text-align: center">${CoordOtApply.IWEEK}</td>
						<%-- <td style="text-align: center">${CoordOtApply.SHIFNAME}</td> --%>
						<td style="text-align: center">${CoordOtApply.FROM_TIME_FIRST}-${CoordOtApply.TO_TIME_FIRST}</td>
						<td style="text-align: center">${CoordOtApply.INDOOR_DATE}</td>
						<td style="text-align: center">${CoordOtApply.OUTDOOR_DATE}</td>
						<td style="text-align: center">${CoordOtApply.FROM_TIME}</td>
						<td style="text-align: center">${CoordOtApply.TO_TIME}</td>
						<td style="text-align: center">
						    ${CoordOtApply.APPLY_LENGTH}
						     <c:if test="${CoordOtApply.ITEM_NO == '141452'}">
					               <font color="red"> (√)</font>
					         </c:if>
						 </td>
						<td style="text-align: center">${CoordOtApply.LEAVEREASON}</td>
						<c:choose>
							<c:when test="${fn:length(CoordOtApply.REASON_OTHER) > 6 }">
								<td style="text-align: center" title="${CoordOtApply.REASON_OTHER}">
										${fn:substring(CoordOtApply.REASON_OTHER,0,6) }...
								</td>
							</c:when>
							<c:otherwise>
								<td style="text-align: center" >
										${CoordOtApply.REASON_OTHER}
								</td>
							</c:otherwise>
						</c:choose>
						<td style="text-align: center">${CoordOtApply.AFFRIMNAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>