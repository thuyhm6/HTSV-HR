<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>

<script type="text/javascript">
function pageFromSea(a){
	var seach_INSRAREA_ID=$("#seach_INSRAREA_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_INSRAREA_ID",navTab.getCurrentPanel()).val();
	var seach_INSURE_ID=$("#seach_INSURE_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_INSURE_ID",navTab.getCurrentPanel()).val();
	var seach_ACTIVITY_FLAG=$("#seach_ACTIVITY_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_ACTIVITY_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/is/insurancesystem/viewInsuranceParamDataNChList?&seach_INSRAREA_ID="+seach_INSRAREA_ID
			+"&seach_INSURE_ID="+seach_INSURE_ID+"&seach_ACTIVITY_FLAG="+seach_ACTIVITY_FLAG);
}

function exportIsParamDataNoPayAreaModel(){
	var url = "/is/insurancesystem/exportIsParamDataNoPayAreaModule?navTabId=bx0122&type=NO_AREA_FLAG";
	document.getElementById("exportIsParamDataNoPayAreaExcel").href=encodeURI(url);
}

function importIsParamDataNoPayArea(){
	$("#importExcelDialog_bx0122").attr('href','/pa/excelImport/importExcelData?importFunName=/importIsParamDataNoPayAreaTemp');
	$("#importExcelDialog_bx0122").click();
}

function doIsParamDataNoPayAreaExport(from){
  	var $from =$(from);
  	var url ="/is/insurancesystem/viewInsuranceParamDataNChExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}

function expIsParamDataNoPayAreaPre(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewInsuranceParamDataNChList");
	alertMsg.confirm(title, {okCall: function(){ doIsParamDataNoPayAreaExport($from);}}); 
}
//-->
</script>

<script type="text/javascript">
	var ajaxGet_add;
	function searchInsContent(key){
 		var ok = "ok";
	  	var id=1;
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		ajaxGet_add = $.ajax( {
			type : "POST",
			url : "/is/insurancesystem/getInsrareaInfoListByKey",
			data : {KEY   : key,
					CHECK : ok}, 
			dataType : "json",
			success : function(data) {			 
				$('#insarea_list').html("");
				var html = '';
				if (typeof (data['insAreaList']) != "undefined") {
					$('#insarea_list').show();
					$.each(data['insAreaList'],
						function(commentIndex, comment) {
							html += '<li class="deptTreeLi" onclick="selectedIt_addInsArea(\'' + comment.CODE_NO + '\',\'' 
								+ comment.CODE_NAME + '\',\'' + comment.DESCRIPTION+'\')">'
								+ '<div>'+ comment.CODE_NO + '</div><div>'+ comment.CODE_NAME + '</div><i>'+ comment.DESCRIPTION+ '</i></li>';
						});
					}
					$('#insarea_list').html(html);
					$("#insarea_list_panel").show();
				}
			});
	}
	function selectedIt_addInsArea(code_no,code_name,description){
		$("#seach_INSRAREA_ID").val('');
		$("#seach_INSRAREA_ID").val(code_no);
		$("#seach_INSRAREA_NAME").val(code_name);
		$('#insarea_list_panel').hide();
	}
	function closeInsArea() {                                                                                              
		$('#insarea_list_panel').css('display', 'none');                                                       
	}   

	var ajaxGet_add2;
	function searchInsureContent(key){
 		var ok = "ok";
	  	var id=1;
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		ajaxGet_add = $.ajax( {
			type : "POST",
			url : "/is/insurancesystem/getInsureInfoListByKey",
			data : {KEY   : key,
					CHECK : ok}, 
			dataType : "json",
			success : function(data) {			 
				$('#insure_list').html("");
				var html = '';
				if (typeof (data['insureList']) != "undefined") {
					$('#insure_list').show();
					$.each(data['insureList'],
						function(commentIndex, comment) {
							html += '<li class="deptTreeLi" onclick="selectedIt_addInsure(\'' + comment.CODE_NO + '\',\'' 
								+ comment.CODE_NAME + '\',\'' + comment.DESCRIPTION+'\')">'
								+ '<div>'+ comment.CODE_NO + '</div><div>'+ comment.CODE_NAME + '</div><i>'+ comment.DESCRIPTION+ '</i></li>';
						});
					}
					$('#insure_list').html(html);
					$("#insure_list_panel").show();
				}
			});
	}
	function selectedIt_addInsure(code_no,code_name,description){
		$("#seach_INSURE_ID").val('');
		$("#seach_INSURE_ID").val(code_no);
		$("#seach_INSURE_NAME").val(code_name);
		$('#insure_list_panel').hide();
	}	
	function closeInsure() {                                                                                              
		$('#insure_list_panel').css('display', 'none');                                                       
	}  
</script>

<a id="importExcelDialog_bx0122" href="#" target="dialog" mask="true"></a>
<a id="importExcel_bx0122" href="#" target="navTab" mask="true">
	<span style="display:none;">保险参数导入</span>
</a>
<input type="hidden" value="${defaultCpny}" id="CPNY_ID"  name="CPNY_ID"/>
<div class="pageHeader">
  	<form onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewInsuranceParamDataNChList" method="post" 
    	rel="pagerForm" id="viewInsuranceParamDataNChList" name="viewInsuranceParamDataNChList">
    	<div class="searchBar">
			<table class="searchContent">
		        <tr>
		        	<td><!-- 法人 -->
						 法人：
					</td>	
					<td>
						${defaultCpny }
					</td>
					<td><!-- 福利地区 -->
						福利地区：
					</td>
					<td>
						<%--
						<ait:SelectSyCodeByCpnyID name="seach_INSRAREA_ID" parentNo="216736" cnpyID="${defaultCpny}" selected="${INSRAREA_ID}" limit="all"/>
						--%>
						<input id="seach_INSRAREA_ID" name="seach_INSRAREA_ID" type="hidden" value=""/>
						<input id="seach_INSRAREA_NAME" onfocus="searchInsContent(this.value)" onkeyup="searchInsContent(this.value)" type="text" 
							 name="seach_INSRAREA_NAME" value="${INSRAREA_NAME }"/>
						<div id="insarea_list_panel" class="deptContent_sso" style="display:none;">
							<div class="ztree_dept_sso">
								<div class="ztree_dept_title"><%--  
									<ul class="ztree_dept_table">
										<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>编号</div><div>名称</div><i>描述</i></li>
									</ul>--%>
									<table width="100%">
										<tr>
											<td>Code</td>
											<td>地区名称</td>
											<td>描述</td>
										</tr>
									</table>
								</div>
								<div class="ztree_dept_type_sso">
									<ul id="insarea_list" class="ztree_dept_table"></ul>
								</div>
								<div class="ztree_dept_color">
									<a href="#" onclick="closeInsArea()" class="ztree_dept_color_a">
										<span>关闭</span>
									</a>
								</div>
							</div>
						</div>
					</td>
					<td><!-- 福利项目 -->
                      	福利项目：
	                </td>                			     
					<td>
						<%--
						<ait:SelectSyCodeByCpnyID name="seach_INSURE_ID" parentNo="219677" cnpyID="${defaultCpny}" selected="${INSURE_ID}" limit="all"/>
						--%>
						<input id="seach_INSURE_ID" name="seach_INSURE_ID" type="hidden" value=""/>
						<input id="seach_INSURE_NAME" name="seach_INSURE_NAME" onfocus="searchInsureContent(this.value)" onkeyup="searchInsureContent(this.value)" 
							type="text" value="${INSURE_NAME }"/>
						<div id="insure_list_panel" class="deptContent_sso" style="display:none;">
							<div class="ztree_dept_sso">
								<div class="ztree_dept_title"><%-- 
									<ul class="ztree_dept_table">
										<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>Code</div><div>项目名称</div></li>
									</ul>--%>
									<table width="100%">
										<tr>
											<td>Code</td>
											<td>项目名称</td>
										</tr>
									</table>
								</div>
								<div class="ztree_dept_type_sso">
									<ul id="insure_list" class="ztree_dept_table" ></ul>
								</div>
								<div class="ztree_dept_color">
									<a href="#" onclick="closeInsure()" class="ztree_dept_color_a">
										<span>关闭</span>
									</a>
								</div>
							</div>
						</div>
					</td> 
					<td><!-- 启用状态 -->
                      	启用状态：
	                </td>                			     
					<td>
						<select name="seach_ACTIVITY_FLAG" id="seach_ACTIVITY_FLAG">
							<option value=""><!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
							<option value="1" <c:if test="${ACTIVITY_FLAG eq '1' }">selected</c:if>><!-- 是 -->是</option>
							<option value="0" <c:if test="${ACTIVITY_FLAG eq '0' }">selected</c:if>><!-- 否 -->否</option>
						</select>
					</td> 
					<td>&nbsp;</td>
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
	<c:set value="dialog" var="add_tab"/>
	<c:set value="450" var="add_width"/>
	<c:set value="380" var="add_height"/>
	<c:set value="/is/insurancesystem/addIsParamDataNoPayAreaInfo" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="380" var="edit_height"/>
	<c:set value="/is/insurancesystem/viewEditIsParamDataNoPayAreaInfo?DATA_NO={sid}" var="edit_Url"/>
	<div class="formBar">
		<ul class="toolBar">
			<c:if test="${toolbarInfo.INSERTR == '1'}">
				 <c:if test="${add_Url ne '' && add_Url ne null}">
					<li id="addLi">
					 
						<a class="add" href="${add_Url}"
							<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
								target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
							</c:if>
								mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
								width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
								height="${add_height eq '' || add_height eq null ? '400' : add_height}"
								<c:if test="${add_rel ne '' && add_rel ne null}">
									rel="${add_rel}"
								</c:if>
								>
								<span>
									<c:choose>
									   <c:when test="${add_name eq '' || add_name eq null}">
									     	<spring:message code="button.add" />
									   </c:when>
									   <c:otherwise>
									   		${add_name}
									   </c:otherwise>
									</c:choose>
								</span>
						</a>
					</li>
				 </c:if>
			</c:if>
			
			<li class="line">
				line
			</li>
			
			<c:if test="${toolbarInfo.UPDATER == '1'}">
				<c:if test="${edit_Url ne '' && edit_Url ne null}">
					<li id="editLi">
						<a class="edit"
							href="${edit_Url}"
							<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
								target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
							</c:if>
								mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }" 
								width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}" 
								height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
								<c:if test="${edit_rel ne '' && edit_rel ne null}">
									rel="${edit_rel}"
								</c:if>
								><span>
									<c:choose>
									   <c:when test="${edit_name eq '' || edit_name eq null}">
									     	<spring:message code="button.update" />
									   </c:when>
									   <c:otherwise>
									   		${edit_name}
									   </c:otherwise>
									</c:choose>
								</span>
						</a>
					</li>
				</c:if>
			</c:if>
			
			<li class="line">
				line
			</li>
			
			<li>
				<a class="delete" id ="exportIsParamDataNoPayAreaExcel" onclick="exportIsParamDataNoPayAreaModel();" href="#">
					<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a>
			</li>
			
			<li class="line">
				line
			</li>
			
			<li>
				<a class="add" onclick="importIsParamDataNoPayArea();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
			</li>
			 
			<li class="line">
				line
			</li>
			
			<li>
				<a class="delete" onclick="expIsParamDataNoPayAreaPre(this)" title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="200">
		<thead>
			<tr> 
			    <th width="3%">
			    	序号
			    </th>
				<th width="7%"><!--法人-->
					法人
				</th>
				<th width="12%"><!--福利地区-->
					福利地区
				</th>
				<th width="13%"><!--福利项目-->
					福利项目
				</th>
				
				<th width="10%"><!--地区比率-->
					地区比率
				</th>
				<th width="10%"><!--地区金额-->
					地区金额
				</th>
				<th width="10%"><!--启用状态-->
					启用状态
				</th>
				<c:if test="${defaultCpny eq 'LGEQA' }">
				<th width="10%"><!-- 四舍五入 -->
					四舍五入
				</th>
				</c:if>	
				<th width="20%"><!--备注-->
					备注
				</th>
				<th width="15%"><!--创建时间-->
					创建时间
				</th>
			</tr>
		</thead>  
		<tbody>		 
			<c:forEach items="${isParamDataList}" var="isData" varStatus="i">			
				<tr target="sid" rel="${isData.DATA_NO }">
					<td style="text-align:center">${i.index+1 }</td>
					<td style="text-align:center">${isData.CPNY_ID }</td>
					<td style="text-align:center">${isData.INSRAREA_NAME }</td>
					<td style="text-align:center">${isData.INSURE_NAME }</td>
					
					<td style="text-align:right">${isData.INSURE_RATE }</td>
					<td style="text-align:right">${isData.INSURE_VALUE }</td>
					<td style="text-align:center">
						<c:if test="${isData.ACTIVITY eq '1'}">
							<font color="green">启用</font>
						</c:if>
						<c:if test="${isData.ACTIVITY eq '0'}">
							<font color="blue">未启用</font>
						</c:if>
					</td>
					
					<c:if test="${defaultCpny eq 'LGEQA' }">
					<td style="text-align:center">
					   	<c:if test="${isData.CARRY_WAY eq 'A' }">向上进一位</c:if>
						<c:if test="${isData.CARRY_WAY eq 'B' }">四舍五入，保留两位小数 </c:if>
						<c:if test="${isData.CARRY_WAY eq 'C' }">四舍五入后取整</c:if>
						<c:if test="${isData.CARRY_WAY eq 'D' }">向下取整</c:if>
						<c:if test="${isData.CARRY_WAY eq 'E' }">保留一位小数，第二位进1 </c:if>
						<c:if test="${isData.CARRY_WAY eq null }">未选择 </c:if>
						<c:if test="${isData.CARRY_WAY eq '' }">未选择 </c:if>
					</td><!-- 四舍五入 -->
					</c:if>
					
					<td style="text-align:left">${isData.REMARK }</td>
					<td style="text-align:center">${isData.CREATE_DATE }</td>	
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/is/insurancesystem/viewInsuranceParamDataNChList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
