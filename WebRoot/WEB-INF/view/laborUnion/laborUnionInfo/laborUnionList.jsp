<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
</head>
<body>
<script type="text/javascript">


function expLaborUnionInfo(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#laborUnionList");
  	
	     alertMsg.confirm(title, {okCall: function(){ doLaborUnionInfoExport($from);}});
    
}

function doLaborUnionInfoExport(from){
  	var $from =$(from);
  	var url ="/laborUnion/laborUnionInfo/laborUnionListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}


function downloadImportBase(){
	document.laborUnionList.action="/pa/excelExport/downloadPaLaborUnion";
	document.laborUnionList.submit();
}

function importExcelBase2(){
				
			//确定要提交吗？
			
		if (confirm ('确定要提交吗')){
				var $form = $("#laborUnionList");	
			  	$.ajax({
					type: 'POST',
					url:"/pa/excelImport/importExcelBaseForHrLaborUnion",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					//success :callback || DWZ.ajaxDone,
			  		success: function(data) {
			  				
			  				alertMsg.info(data.message);
			  				
			  		}	,
					error: DWZ.ajaxError
				});	
				return false;
		}	
		
	}

function importExcelBase(){

	
		$("#importExcel").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelBaseForHrLaborUnion');
		$("#importExcel").click();
		

}	



</script>

<div class="pageHeader">
	<form id="laborUnionList" name="laborUnionList" onsubmit="return navTabSearch(this);" action="/laborUnion/laborUnionInfo/laborUnionList" method="post" rel="pagerForm" >
<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="3%" style="text-align: center">
				     工资年月
				</td>
				<td width="8%">
					 <input type="text" name="seach_paMonth" class="date" readonly="true" value="${paMonth}"/>
					 <input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${CPNY_ID}" />
					 
				</td>
                <a   id="importExcel"  href="#" target="dialog" mask="true"></a>
			</tr>
			
			<tr>
				<td width="3%" style="text-align: center">
				     是否在会(EXCEL)
				</td>
				<td width="8%">
					 <select id="seach_laborFlag" name="seach_labor">
					         <option value="">请选择</option>
					         <option value="join">在会</option>
					         <option value="left">退会</option>
					 </select>
				</td>
                
			</tr>
			
			
			<tr>
				<td width="3%" style="text-align: center">
				     是否在职(EXCEL)
				</td>
				<td width="8%">
					 <select id="seach_EmpStatus" name="seach_EmpStatus">
					         <option value="">请选择</option>
					         <option value="EmpStatus6">在职</option>
					         <option value="EmpStatus3">离职</option>
					 </select>
				</td>
                
			</tr>
			
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
					     <div class="buttonContent"><!-- 检索 -->
						       <button type="submit"><spring:message code="public.title.search"/></button>
					     </div>
				   </div>
				</li>
				<li><div class="buttonActive">
				          <div class="buttonContent">
							<button type="button" onclick="expLaborUnionInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
							</button>
					     </div>
					</div>						
				</li>
				
				<li><div class="buttonActive">
				          <div class="buttonContent">
							<button type="button" onclick="javascript:downloadImportBase();" title="下载模板">
								下载模板
							</button>
					     </div>
					</div>						
				</li>
				
				<li><div class="buttonActive">
				          <div class="buttonContent">
							<button type="button" onclick="javascript:importExcelBase();" title="导入数据">
								导入数据
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
	<c:set value="600" var="edit_width" />
	<c:set value="550" var="edit_height" />
	<c:set value="dialog" var="edit_tab"/>

	
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table id="1" class="table" width="100%" >
		
			<tr>
				<td colspan="2"  width="300">区分</td>
				<td>总人员</td>
				<td>离职
				</td>
				<td>现在员
				</td>
				
				<td>入会
				</td>
				<td>非入会
				</td>				
			</tr>			
			
			<tr>
				<td rowspan="3"  class="info_title_01">
				   人数 
				</td>
				<td  class="info_title_01">
				   男 
				</td>
				<c:forEach var="itemList" items="${itemListM}">
				<td style="text-align:center">${itemList.ALLCOUNT }</td>
				<td style="text-align:center">${itemList.LEFTCOUNT } </td>
				<td style="text-align:center">${itemList.STAYCOUNT } </td>
				<td style="text-align:center">${itemList.JOINCOUNT }</td>
				<td style="text-align:center">${itemList.NOTJOINCOUNT }</td>
				</c:forEach>
			</tr>
			
			<tr>
							    <td  class="info_title_01">女 </td>
							    
							    <c:forEach var="itemList" items="${itemListF}">
							    <td style="text-align:center">${itemList.ALLCOUNT }</td>
							    <td style="text-align:center">${itemList.LEFTCOUNT } </td>
							    <td style="text-align:center">${itemList.STAYCOUNT } </td>
							    <td style="text-align:center">${itemList.JOINCOUNT }</td>
							    <td style="text-align:center">${itemList.NOTJOINCOUNT }</td>
							    </c:forEach>
			</tr>
			
			
			<tr>
							    <td class="info_title_01">计 
							  
							    </td>
							     <c:forEach var="itemList" items="${itemListS}">
							    <td style="text-align:center">${itemList.ALLCOUNT }</td>
							    <td style="text-align:center">${itemList.LEFTCOUNT } </td>
							    <td style="text-align:center">${itemList.STAYCOUNT } </td>
							    <td style="text-align:center">${itemList.JOINCOUNT }</td>
							    <td style="text-align:center">${itemList.NOTJOINCOUNT }</td>
							    </c:forEach>
			</tr>
	

	</table>

	<br><br><br><br>
	
	<table id="2" class="table" width="100%" >
						  <tr>
						    <td colspan="2" width="300">区分 </td>
						    <td>上月会员</td>
						    <td>本月退会</td>
						    <td>本月入会</td>
						    <td>本月会员</td>
						    <td>增减 </td>
						  </tr>
						  
						   <c:forEach var="itemList" items="${itemListM2}">
							  <tr>
							    <td rowspan="3" >人数 </td>
							    <td >男 </td>
							    <td style="text-align:center">${itemList.LASTCOUNT} </td>
							    <td style="text-align:center">${itemList.LEFTCOUNT} </td>
							    <td style="text-align:center">${itemList.JOINCOUNT} </td>
							    <td style="text-align:center">${itemList.LASTCOUNT+itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
							    <td style="text-align:center">${itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
							  </tr>
						  </c:forEach>
						  
						   <c:forEach var="itemList" items="${itemListF2}">
							  <tr>
							    <td >女 </td>
							    <td style="text-align:center">${itemList.LASTCOUNT } </td>
							    <td style="text-align:center">${itemList.LEFTCOUNT } </td>
							    <td style="text-align:center">${itemList.JOINCOUNT } </td>
							    <td style="text-align:center">${itemList.LASTCOUNT+itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
							    <td style="text-align:center">${itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
							  </tr>
						   </c:forEach>
						   <c:forEach var="itemList" items="${itemListS2}">
						  <tr>
						  
						    <td  class="info_title_01">计 
						   
						    </td>
						        <td style="text-align:center">${itemList.LASTCOUNT} </td>
							    <td style="text-align:center">${itemList.LEFTCOUNT } </td>
							    <td style="text-align:center">${itemList.JOINCOUNT } </td>
							    <td style="text-align:center">${itemList.LASTCOUNT+itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
							    <td style="text-align:center">${itemList.JOINCOUNT-itemList.LEFTCOUNT} </td>
						  </tr>
						  </c:forEach>
						</table>


	
</div>

	<c:set value="/laborUnion/laborUnionInfo/laborUnionList" var="pageUrl"/>
	
	<div style="display: none;"><%@ include file="/WEB-INF/view/inc/initPagination.jsp"%></div>
</body>
</html>