<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style>
table tr th{
background-image:url("/resources/css/dwzUI/themes/silver/images/grid/tableth.png");
background-repeat:repeat-x;
background-color:rgb(240, 239, 240);
border-color: #d0d0d0;
font-family:Microsoft YaHei,"微软雅黑",Microsoft JhengHei, Arial, sans-serif;
height:21px;
line-height:21px;
white-space:nowrap;
}
</style>
<script type="text/javascript">
function openMonthWorkListExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewMonthWorkList");  
     
     var url = "/ar/attendanceView/viewMonthWorkListExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}
</script>
<div class="pageHeader" >
	<form id="viewMonthWorkList" onsubmit="return navTabSearch(this);" action="/ar/attendanceView/viewMonthWorkList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr><!-- 年份 -->
				<td style="text-align:right">
					<spring:message code="pa.payear.title.payear"/>：
				</td>
				<td style="text-align:left">
					<ait:date yearName="seach_paYear" yearSelected="${paYear}" monthName="seach_paMonth" monthSelected="${paMonth}"/>
				</td>
				<td><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>:
				</td>
				<td>			
			        <input name="seach_condition" type="text" value="${condition}"/>
				</td>
			   	<td>考勤区间</td>
				<td  style="text-align:left">
					<select class="combox" id="combox_STAT_NO" name="seach_STAT_NO">
						<c:forEach items="${qujianList}" var="qujian" varStatus="i">
							<option value="${qujian.STAT_NO}" <c:if test="${STAT_NO eq qujian.STAT_NO }">selected</c:if>>${qujian.STAT_NAME}</option>
					    </c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul><li><div class="buttonActive"><div class="buttonContent"><button type="submit">
		 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div></li></ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			 <li>
				  <a class="edit" onclick="openMonthWorkListExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="228">
	<thead>
			<tr>
				<th  rowspan="2" valign="middle">
					<spring:message code="hr.viewPersonalInfo.title.EMPID" />
					<!--员工工号-->
				</th>
				<th style="text-align: center" rowspan="2" valign="middle">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
					<!--员工姓名-->
				</th>
				<th style="text-align: center" rowspan="2" valign="middle">
					<spring:message code="ar.excelexport.title.armonth" />
					<!--考勤月-->
				</th>
	<%-- 		<th style="text-align: center" colspan="3">
					<spring:message code="ess.viewpersonalpainfo.zhuznyouxiangmuzu" />
					<!--专有项目组-->
				</th>
				<th style="text-align: center" colspan="4">
					<spring:message code="ess.viewpersonalpainfo.jiabanxiangmuzu" />
					<!--加班项目组-->
				</th>
				<th style="text-align: center" colspan="9">
					<spring:message code="ess.viewpersonalpainfo.xiujiaxiangmuzu" />
					<!--休假项目组-->
				</th>
			</tr>
			<tr>
	--%>			
				<th style="text-align: center">
					<spring:message code="ar.monthwork.title.Lateness" />
					<!--迟到-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.monthwork.title.EarlyLeave" />
					<!--早退-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.monthwork.title.kuanggong" />
					<!--旷工-->
				</th>
				<th style="text-align: center">
					<spring:message code="ess.viewpersonalpainfo.pingrijiabanheji" />
					<!--平日加班合计-->
				</th>
				<th style="text-align: center">
					<spring:message code="ess.viewpersonalpainfo.zhoumojiabanheji" />
					<!--周末加班合计-->
				</th>
				<th style="text-align: center">
					<spring:message code="ess.viewpersonalpainfo.jiejiarijiabanheji" />
					<!--节假日加班合计-->
				</th>
				<th style="text-align: center">
					<spring:message code="ess.viewpersonalpainfo.zongtiaoxiu" />
					<!--总调休-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.shijia" />
					<!--事假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.viewArAdjustRest.title.yiyongtiaoxiushu" />
					<!--已用调休-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.viewArAnnualStandard.title.fadininjia" />
					<!--法定年假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.viewArAnnualStandard.title.fulininjia" />
					<!--福利年假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.chuchai" />
					<!--出差-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.bingjia" />
					<!--病假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.hunjia" />
					<!--婚假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.chanjia" />
					<!--产假-->
				</th>
				<th style="text-align: center">
					<spring:message code="ar.menu.title.sangjia" />
					<!--丧假-->
				</th>
			</tr>
			</thead>			
			<tbody>
		<c:forEach items="${monthWorkList}" var="monthWork" varStatus="i">
			<tr>
					<td style="text-align: center">
						${monthWork.EMPID}
					</td>
					<td style="text-align: center">
						${monthWork.LOCAL_NAME}
					</td>
					<td style="text-align: center">
						${monthWork.AR_MONTH}
						<!--考勤月-->
					</td>
					<td style="text-align: center">
						${monthWork.CHIDAO}
						<!--迟到-->
					</td>
					<td style="text-align: center">
						${monthWork.ZAOTUI}
						<!--早退-->
					</td>
					<td style="text-align: center">
						${monthWork.KUANGGONG}
						<!--旷工-->
					</td>
					<td style="text-align: center">
						${monthWork.PINGRIJIABAN}
						<!--平日加班合计-->
					</td>
					<td style="text-align: center">
						${monthWork.ZHOUMO}
						<!--周末加班合计-->
					</td>
					<td style="text-align: center">
							${monthWork.FADING}  
						<!--节假日加班合计-->
					</td>
					<td style="text-align: center">
						${monthWork.TIAOXIU}
						<!--总调休-->
					</td>
					<td style="text-align: center">
						${monthWork.SHIJIA}
						<!--事假-->
					</td>
					<td style="text-align: center">
						${monthWork.YIYONGTIAOXIU}
						<!--已用调休-->
					</td>
					<td style="text-align: center">
						${monthWork.SHIYONG_NIANJIA_DAY}
						<!--法定年假-->
					</td>
					<td style="text-align: center">
						${monthWork.SHIYONG_FULI_DAY}
						<!--福利年假-->
					</td>
					<td style="text-align: center">
						${monthWork.CHUCHAI_DAY}
						<!--出差-->
					</td>
					<td style="text-align: center">
						${monthWork.BINGJIA}
						<!--病假-->
					</td>
					<td style="text-align: center">
						${monthWork.HUNJIA_DAY}
						<!--婚假-->
					</td>
					<td style="text-align: center">
						${monthWork.CHANJIA}
						<!--产假-->
					</td>
					<td style="text-align: center">
						${monthWork.SANGJIA_DAY}
						<!--丧假-->
					</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceView/viewMonthWorkList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>