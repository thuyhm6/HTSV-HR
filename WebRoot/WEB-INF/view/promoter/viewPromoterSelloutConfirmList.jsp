<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function selloutConfirm(form1)
{
	var params   = $("#selloutConfirm").serialize();
	$.ajax( {
		type : 'post',
		cache : false,
		url : "/promoter/selloutConfirm?" + params,
				success : function(data) {
					if (data.statusCode==200){
						alert(data.message);
						//页面重载
						navTabSearch($("#viewPromoterSelloutConfirm"));
					}else{
						alert(data.message);
					}
				}
	});
}
</script>

<div class="pageHeader">
	<form id="viewPromoterSelloutConfirm" name="viewPromoterSelloutConfirm" onsubmit="return navTabSearch(this);" action="/promoter/viewPromoterSelloutConfirmList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 大区： -->
						大区
					</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="hr" level="2" 
						selected="${PAY_AREA_CD}" selectedNm="${PAY_AREA_NM}" />
					</td>
					<td><!-- 月份： -->
						月份
					</td>
					<td>
						<ait:date yearName="year" yearSelected="${year}" monthName="month" monthSelected="${month}"/>
					</td>
					<td>确认状态</td>
						<td><select id="seach_CONFIRM_FLAG" name="seach_CONFIRM_FLAG">
							<option value="ALL">全部</option>
							<option value="N" <c:if test="${confirmFlag eq 'N' || confirmFlag == null}">selected</c:if>>待确认</option>
							<option value="Y" <c:if test="${confirmFlag eq 'Y'}">selected</c:if>>已确认</option>
						</select></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!-- 检索 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
  <form id="selloutConfirm" method="post" action="/promoter/selloutConfirm" onsubmit="return navTabSearch(this);">
    <div class="formBar">
      <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:selloutConfirm(this)" title="确认">确认</button></div></div></li>
      </ul>
    </div>
	<table class="table" layoutH="230">
	  <thead>
	    <tr>
	      <th width="50"><input type="checkbox" class="checkboxCtrl" group="cx1500Check" /></th>
	      <th width="100">月份</th>
	      <th width="200">大区</th>
	      <th width="200">支社</th>
	      <th width="100">产品</th>
	      <th width="100">销售数量</th>
	      <th width="100">支社核对</th>
	      <th width="100">差异</th>
	      <th width="100">状态</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach items="${items}" var="item" varStatus="i">
	      <tr>
	        <c:if test="${item.PNUM eq 1}">
	        <td style="text-align: center;padding-top:7px;" rowspan="${item.PCOUNT}">
	         <c:if test="${item.CONFIRM_FLAG eq 'N'}">
	          <input type="checkbox" name="cx1500Check" value="${i.index}"/>
	         </c:if>
	          <input type="hidden" name="BRANCH_${i.index}" value="${item.BRANCH}" />
	          <input type="hidden" name="SALE_MONTH_${i.index}" value="${item.SALE_MONTH}" />
	        </td>
	        <td style="text-align:center" rowspan="${item.PCOUNT}">${item.SALE_MONTH}</td>
	        <td style="text-align:left" rowspan="${item.PCOUNT}">${item.PAY_AREA_NM}</td>
	        <td style="text-align:left" rowspan="${item.PCOUNT}">${item.BRANCH_NM}</td>
	        </c:if>
	        <c:if test="${item.PNUM ne 1 and i.count eq 1}">
	        <td style="text-align: center;padding-top:7px;" rowspan="${item.PCOUNT - item.PNUM + 1}"></td>
	        <td style="text-align:center" rowspan="${item.PCOUNT - item.PNUM + 1}">${item.SALE_MONTH}</td>
	        <td style="text-align:left" rowspan="${item.PCOUNT - item.PNUM + 1}">${item.PAY_AREA_NM}</td>
	        <td style="text-align:left" rowspan="${item.PCOUNT - item.PNUM + 1}">${item.BRANCH_NM}</td>
	        </c:if>
	        <td style="text-align:center">${item.PROD_TP_NM}</td>
	        <td style="text-align:right">${item.QTY}</td>
	        <td style="text-align:right">${item.BRANCH_QTY}</td>
	        <td style="text-align:right">${item.QTY - item.BRANCH_QTY}</td>
	        
	         <c:if test="${item.PNUM eq 1}">
	          <c:if test="${item.CONFIRM_FLAG eq 'N'}">
	            <td style="text-align:center" rowspan="${item.PCOUNT}"><font color=red>待确认</font></td>
	          </c:if>
	          <c:if test="${item.CONFIRM_FLAG eq 'Y'}">
	            <td style="text-align:center" rowspan="${item.PCOUNT}"><font color=green>已确认</font></td>
	          </c:if>
	        </c:if>
	        
	      </tr>
	    </c:forEach>
	  </tbody>
	</table>
  </form>
  <c:set value="/promoter/viewPromoterSelloutConfirmList" var="pageUrl"/>
  <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>