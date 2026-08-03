<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
	var parentMenuTree = [];
	$.ajax({
			type:'get',cache:false,contentType:'application/json',
			url:'/sys/menu/getMenuTree',dataType:'json',
			success:function(data){
						$.each(data, function(i, item){
							parentMenuTree.push({ id: item.MENU_NO, pid: item.MENU_PARENT_NO, text: item.MENU_NAME ,depth : item.DEPTH});
						 });
						$('#parentMenu').ligerComboBox({
							width: 250,selectBoxWidth: 300,selectBoxHeight: 300,treeLeafOnly:false,
							tree: {
									data: parentMenuTree,checkbox:false,idFieldName :'id',parentIDFieldName :'pid',
									nodeWidth:300
									},
							onSelected:function (note,newText){ 
									$.each(data, function(i, item){
										if(note==parentMenuTree[i].id)
											$('#Menudepth').attr('value',parentMenuTree[i].depth);
								 	});
									$('#MenuNo').attr('value',note);
									}
						});
			 }
		});
	});
</SCRIPT>
<script type="text/javascript">
    $("#ZHIQUN").attr("disabled","true");
    $("#JIQUN").attr("disabled","true");
    $("#HAOFENG").attr("disabled","true");
</script>
<div class="pageContent">
	<form method="post" action="/pa/salaryCanShu/updateHaoFengSheZhiInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap"> 
		
			 <input type="hidden" name="SEQ" value="${haoFengSheZhiInfo.SEQ}">
			 
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
				<input type="hidden" id="CPNY_ID"  name="CPNY_ID" value="${haoFengSheZhiInfo.CPNY_ID}" />			
					${haoFengSheZhiInfo.CPNY_ID}	
				</dd>
			</dl>
			
			
			<dl>
				<dt><!--职群 --><spring:message code="ess.empInfo.zhiqun" /></dt>
				<dd>
				<ait:SelectSyCodeByCpnyID name="ZHIQUN" id="ZHIQUN" parentNo="14015812" selected="${haoFengSheZhiInfo.POST_FAMILY}" limit="ALL"/>
					
				</dd>
			</dl>
			
			<dl>
				<dt><!--职级 --><spring:message code="ess.infoApply.Rank" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="JIQUN" id="JIQUN" parentNo="14015578" selected="${haoFengSheZhiInfo.POST_GRADE_NO}" limit="ALL"/>	   				
				</dd>
			</dl>
			
			<dl>
				<dt><!--号俸 --><spring:message code="hrm.empinfo.PAY_STEP_NO.Z" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="HAOFENG" id="HAOFENG" parentNo="14016325" selected="${haoFengSheZhiInfo.PAY_STEP}" limit="ALL"/>	   				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.kaishiyuefen"/><!--开始月份--></dt>
				<dd>
					<input  name="START_MONTH" id="START_MONTH" value="${haoFengSheZhiInfo.START_MONTH}"  class="required"/><span style="color:red"><spring:message code="pa.insurance.title.exampleMonth"/></span>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.jieshuyuefen"/><!--结束月份--></dt>
				<dd>
					<input  name="END_MONTH" value="${haoFengSheZhiInfo.END_MONTH}" /><span style="color:red"><spring:message code="pa.insurance.title.exampleMonth"/></span>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.jibengongzi"/><!--基本工资--></dt>
				<dd>
					<input  type="text"  name="BASE_PAY" value="${haoFengSheZhiInfo.BASE_PAY}" />					
				</dd>
			</dl>
			
			 <dl>
				<dt><!--基本津贴 --><spring:message code="pa.viewHaoFengSetList.JIBENJINTIE.b" /></dt>
				<dd>
					<input  type="text"  name="BASE_ALLOWANCE" value="${haoFengSheZhiInfo.BASE_ALLOWANCE}" />					
				</dd>
			</dl>
			
			 <dl>
				<dt><!--级别津贴 --><spring:message code="pa.viewHaoFengSetList.JIBIEJINTIE.b" /></dt>
				<dd>
					<input  type="text"  name="GRADE_ALLOWANCE" value="${haoFengSheZhiInfo.GRADE_ALLOWANCE}" />					
				</dd>
			</dl>
			 <dl>
				<dt><!--营业津贴 --><spring:message code="pa.viewHaoFengSetList.YINGYEJINTIE.b" /></dt>
				<dd>
					<input  type="text"  name="OPERATING_ALLOWANCE" value="${haoFengSheZhiInfo.OPERATING_ALLOWANCE}" />					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="ableStatus_pa0804">
						<option value="1" <c:if test="${haoFengSheZhiInfo.ACTIVITY eq '1' }">selected</c:if> >
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" <c:if test="${haoFengSheZhiInfo.ACTIVITY eq '0' }">selected</c:if> >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
				</dd>
			</dl>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
