<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
		function viewPaiQianDiInfoModule(){
			$("#importExcelDialog_pa0801").attr('href','/pa/excelImport/importPaiQianDiGUanLiInfoData?importFunName=/viewPaiQianDiInfoModule');
			$("#importExcelDialog_pa0801").click();			
		}
		function CheckForm(form,navTabId){
			var $form=$(form);

			return true;
		}
		function doCityLevelListExport(from){
		  	//var $from =$(from);
		  	//var url ="${base}/promoter/viewCityLevelListExcel";
		  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
			var sform = document.getElementById("viewCityLevel");
			var eForm = document.getElementById("excelForm_cx0300");
			
			document.getElementById("cx0300Link").innerHTML = "EXCEL密码设置";
			eForm.STATENM.value		= sform.seach_STATENM.value;
			eForm.CITYNM.value		= sform.seach_CITYNM.value;
			eForm.REGION.value		= sform.seach_REGION.value;
			eForm.CITYLEVEL.value   = sform.seach_CITYLEVEL.value;
			
			$("#excelDialog_cx0300").attr('href', "/sys/encryptExcel"
							+"?exportFunName=/promoter/expCityLevelListExcel"
							+"&navTabId=cx0300"
							+"&formId=excelForm_cx0300");
			$("#excelDialog_cx0300").attr('width', "300");
			$("#excelDialog_cx0300").attr('height', "150");
			$("#excelDialog_cx0300").click();
		}
		function expCityLevelList(a,navTabId){
		  	var $this = $(a);
		  	var title = $this.attr("title");
		  	var $from = $("#viewCityLevel");
		  	if(CheckForm($from,navTabId)){
			     alertMsg.confirm(title, {okCall: function(){ doCityLevelListExport($from);}});
		    } 
		}

		$(document).ready(function() {
			var stateCd=$('#seach_STATENM').val();
			var cityCd0=$('#hCITYNM').val();
			var region0=$('#hREGION').val();
			changeState(stateCd,cityCd0);
			
			$('#seach_STATENM').live('change',function(){
				var st=$('#seach_STATENM').val();
				changeState(st,cityCd0);
				changeCity(null, null);
			});
			
			var cityCd=$('#seach_CITYNM select').val();
			changeCity(cityCd0, region0);
			
			$('#seach_CITYNM select').live('change',function(){
				var st=$('#seach_CITYNM select').val();
				changeCity(st, region0);
			});
		});

		function changeState(stateCd, cityCd){
			$.ajax({
				cache: false,
				url : '${base}/promoter/getListBySelect?type=CITY_DIS&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM',
				type : "get",
				dataType : "html",
				success : function(data) {
					$("#seach_CITYNM").html(data);
				}
			});
		}

		function changeCity(cityCd, region){
			$.ajax({
				cache: false,
				url : '${base}/promoter/getListBySelect?type=REGION_DIS&parentNo='+cityCd+"&selected="+region+'&name=seach_REGION',
				type : "get",
				dataType : "html",
				success : function(data) {
					$('#seach_REGION').html(data);
				}
			});
		}
</script>

<a id="importExcelDialog_pa0801"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0801"  href="#" target="navTab" mask="true">
<span style="display:none;"><spring:message code="pa.salary.canShu.pQdDaoRuJieGuo"/><!--派遣地导入结果 --></span></a>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewPaiQianDiGuanLiList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <input id="hCITYNM" name="hCITYNM" type="hidden" value="${searchMap.seach_CITYNM}" />
		<input id="hREGION" name="hREGION" type="hidden" value="${searchMap.seach_REGION}" />
		<table class="searchContent">
			<tr>
				<td>法人:</td>
				<td><!-- 法人： 
					<ait:SyCompany target="login" name="seach_COMPANYID" language="zh" limit="ALL" activity="1" selected="${COMPANYID}"/>-->
					${defaultCpny }
				</td>
				<td>省名称:</td>				
				<td><!-- 省名称： -->
					<ait:SelectState id="seach_STATENM" name="seach_STATENM" type="SHENG" parentNo="" selected="${STATENM}" limit="all"/>
				</td>
				<td>城市名称:</td>
				<td><!-- 城市名称： -->
					<span id="seach_CITYNM" name="seach_CITYNM"></span>
				</td>
				<td>地区名称:</td>
				<td><!-- 地区名称： -->
					<span id="seach_REGION" name="seach_REGION"></span>
				</td>
				<td>城市等级:</td>
				<td><!-- 城市等级： -->
					<ait:selectSyCode  name="seach_CITYLEVEL" parentNo="218067" selected="${CITYLEVEL}"  limit="all"/>
				</td>
				
			</tr>
		</table>
		
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
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
	<c:set value="800" var="add_width"/>
	<c:set value="280" var="add_height"/>
	<c:set value="/pa/salaryCanShu/addPaiQianDiGuanLiView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/pa/salaryCanShu/deletePaiQianDiGuanLiInfo?PQD_NO={PQD_NO}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="280" var="edit_height"/>
	<c:set value="/pa/salaryCanShu/updatePaiQianDiGuanLiView?PQD_NO={PQD_NO}" var="edit_Url"/>
	<div class="formBar">
	<ul class="toolBar">
		<li><a id="exportExcel" href="/hrm/empinfo/exportPaiQianDiGuanLiMoBanModle">
				<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
			</a>
		</li>
		<li><a href="#" onclick="viewPaiQianDiInfoModule()">
				<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
	 		</a>
	 	</li>
	 	<li><a id="exportExcel" href="/pa/excelExport/exportPaiQianDiGuanLiInfoList?seach_faren=${faren}&seach_chengshidengji=${csdj}&seach_diqumingcheng=${dqmc}">
				<span><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></span>
			</a>
		</li>

		  <c:if test="${toolbarInfo.INSERTR == '1'}">
			 <c:if test="${init_Url ne '' && init_Url ne null}">
				<li id="addLi">
					<a class="add"
						href="${init_Url}"
						<c:if test="${init_target_exit eq '' || init_target_exit eq null }">
							target="${init_tab eq '' || init_tab eq null ? 'ajaxTodo' : init_tab}"
						</c:if>
						<c:if test="${init_range ne '' && init_range ne null }">
							width="${init_width eq '' || init_width eq null ? '500' : init_width}" 
							height="${init_height eq '' || init_height eq null ? '400' : init_height}"
						</c:if>
						title="
							<c:choose>
							   <c:when test="${init_title eq '' || init_title eq null}">
							     	<spring:message code="button.init.sure" />
							   </c:when>
							   <c:otherwise>
							   		${init_title}
							   </c:otherwise>
							</c:choose> ">
						<span>
							<c:choose>
							   <c:when test="${init_name eq '' || init_name eq null}">
							     	<spring:message code="button.init" />
							   </c:when>
							   <c:otherwise>
							   		${init_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
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
							</c:if> >
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
		
		<c:if test="${toolbarInfo.DELETER == '1'}">
			<c:if test="${delete_Url ne '' && delete_Url ne null}">
				<li id="deleteLi">
					<a class="delete" href="${delete_Url}"
						<c:if test="${delete_target_exit eq '' || delete_target_exit eq null }">
							target="${delete_tab eq '' || delete_tab eq null ? 'ajaxTodo' : delete_tab}"
						</c:if>
						<c:if test="${delete_mask_exit ne '' && delete_mask_exit ne null }">
							mask="${delete_mask eq '' || delete_mask eq null ? 'true' : delete_mask }" 
						</c:if>
						<c:if test="${delete_range ne '' && delete_range ne null }">
							width="${delete_width eq '' || delete_width eq null ? '500' : delete_width}" 
							height="${delete_height eq '' || delete_height eq null ? '400' : delete_height}"
						</c:if>
						 title="
						 	<c:choose>
							   <c:when test="${delete_title eq '' || delete_title eq null}">
							     	<spring:message code="button.delete.sure" />
							   </c:when>
							   <c:otherwise>
							   		${delete_title}
							   </c:otherwise>
							</c:choose>  ">
						<span>
							<c:choose>
							   <c:when test="${delete_name eq '' || delete_name eq null}">
							     	<spring:message code="button.delete" />
							   </c:when>
							   <c:otherwise>
							   		${delete_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
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
							</c:if> ><span>
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
		
	</ul>
</div>
	

					
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th width="120">法人<!--法人--></th>
				<th width="120"><spring:message code="pa.salary.canShu.shengFen"/><!--省份--></th>
				<th width="120"><spring:message code="pa.salary.canShu.chengShiMingCheng"/><!--城市名称--></th>
				<th width="80"><spring:message code="pa.salary.canShu.diQuMingCheng"/><!--地区名称--></th>
				<th width="100"><spring:message code="pa.salary.canShu.chengShiDengJi"/><!--城市等级--></th>
				<th width="80"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr target="PQD_NO" rel="${pQd.NO1}">
				    <td class="td_center">${i.index + 1}</td>
					<td class="td_center">${pQd.FAREN}</td>
					<td class="td_center">${pQd.STATE_NM}</td>
					<td class="td_center">${pQd.CITY_NM }</td>
					<td class="td_center">${pQd.REGION_NM }</td>
					<td class="td_center">${pQd.CITY_LEVEL}</td>
					<td><center><img src="/resources/images/a_${pQd.PQD_ACTIVITY}.gif"></center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/pa/salaryCanShu/viewPaiQianDiGuanLiList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>