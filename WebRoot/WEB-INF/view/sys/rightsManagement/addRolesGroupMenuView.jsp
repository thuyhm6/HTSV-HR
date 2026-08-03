<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
 	 <c:forEach items="${selectLists}" var="select" varStatus="i">
 	 	if($('#TR${select.MENU_NO}').length>0){
 	 		if(${select.SELECTR}){
 	 			$('#SELECTR',$('#TR${select.MENU_NO}')).attr("value","1|${select.MENU_NO}");
 	 			$('#SELECTR',$('#TR${select.MENU_NO}')).attr("checked",true);}
 	 		if(${select.INSERTR}){
 	 			$('#INSERTR',$('#TR${select.MENU_NO}')).attr("value","1|${select.MENU_NO}");
 	 			$('#INSERTR',$('#TR${select.MENU_NO}')).attr("checked",true);}
 	 		if(${select.UPDATER}){
 	 			$('#UPDATER',$('#TR${select.MENU_NO}')).attr("value","1|${select.MENU_NO}");
 	 			$('#UPDATER',$('#TR${select.MENU_NO}')).attr("checked",true);}
 	 		if(${select.DELETER}){
 	 			$('#DELETER',$('#TR${select.MENU_NO}')).attr("value","1|${select.MENU_NO}");
 	 			$('#DELETER',$('#TR${select.MENU_NO}')).attr("checked",true);}
 	 	}
 	 </c:forEach>
 	<c:if test="${not empty selectedMenu}">
 		$('#Menudepth').attr('value',${selectedMenu.DEPTH});
 		$('#parentMenu').attr('value','${selectedMenu.MENU_NAME_ZH}');
 		$('#MenuNo').attr('value',${selectedMenu.MENU_PARENT_NO});
 	</c:if>
 	<c:forEach items="${info}" var="item" varStatus="i">
 			parentMenuTree.push({ id: item.MENU_NO, pid: item.MENU_PARENT_NO, text: item.MENU_NAME_ZH ,depth : item.DEPTH});
 	</c:forEach>
 		
  
 
 $('#parentMenu').ligerComboBox({
							width: 250,selectBoxWidth:300,selectBoxHeight: 300,isMultiSelect: false,
							tree:$("#menuTreeForRoles").ligerTree({checkbox:false,nodeWidth:300
							}),
							onSelected:function (note,newText){  
									$.each(parentMenuTree, function(i, item){
										if(note==parentMenuTree[i].id)
											$('#Menudepth').attr('value',parentMenuTree[i].depth);
								 	});
									$('#MenuNo').attr('value',note);
									}
						});
						 
	});
	function choseVale(check,menu_No){
		if($(check).attr("checked")) 
			check.value=1+"|"+menu_No;
		else
			check.value=0+"|"+menu_No;
	}
</SCRIPT>
					<!-- 表格主体开始 -->
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
						<div class="panelBar">
							<ul class="toolBar" style="float:right;">
							<li class="line">line</li>
								<li><a class="add" href="#"><div class="buttonContent"><button type="submit">
								<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></a></li>
								
							</ul>
						</div>
						<table class="table" width="99%" layoutH="160">
							<thead>
								<tr>
									<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th>
									<!--<th><spring:message code="sys.rights.depth"/>深度</th>
									--><th><spring:message code="sys.rights.menuNo"/><!--屏幕编号--></th>
									<th><spring:message code="sys.rights.menuExplain"/><!--屏幕解释--></th>
									<th><spring:message code="button.sys.view"/><!--查看--></th>
									<th><spring:message code="button.add"/><!--添加--></th>
									<th><spring:message code="button.update"/><!--修改--></th>
									<th><spring:message code="button.delete"/><!--删除--></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${subMenuLists}" var="menu" varStatus="i">
								<tr target="CodeNO" rel="${menu.MENU_NO}" id="TR${menu.MENU_NO}">
									<td>${i.index+1}</td>
									<!--<td> ${menu.DEPTH}</td>
									--><td>${ menu.MENU_NO }<input type="hidden" name="MENU_NOS" value="${menu.MENU_NO}"/></td>
									<td>${ menu.CONTENT}</td>
									<td><input type="checkbox" value="0" id="SELECTR" name="SELECTR" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" id="INSERTR" name="INSERTR" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" id="UPDATER" name="UPDATER" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" id="DELETER" name="DELETER" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<div class="panelBar">
							<div class="pages">													 
							<span> <spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
							</div>						 
						</div>
					</div>