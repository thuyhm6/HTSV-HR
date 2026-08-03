<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
     <form method="post" action="/pa/wagebase/addPaSendToAdministrationItemInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">        
		   <dl>
				<dt><spring:message code="display.emp.ben.or.sendtoadministrator"/><!--派遣地--></dt>
				<dd>
					 <ait:selectSyCode name="sendto_pa9999" parentNo="125231"/>					
				</dd>
			</dl>
		    
		    <dl>
				<dt><spring:message code="ess.infoApply.title.dutyName"/><!--职责--></dt>
				<dd>
					<ait:selectSyCode name="dutyno_pa9999" parentNo="13813"/>			
				</dd>
			</dl>
			
		    <dl>
				<dt><spring:message code="rp.report.title.amount"/><!--金额--></dt>
				<dd>
					 <input type="text" value="0" class="digits" name="amount_pa9999" id="amount_pa9999">
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="liang.public.title.ItemName"/><!--项目名称--></dt>
				<dd>
					<ait:selectSyCode name="itemName_pa9999" parentNo="125235"/>			
				</dd>
			</dl>       
			
			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ableStatus_pa9999" id="ableStatus_pa9999">
						<option value="1" selected>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
				</dd>
			</dl>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
 </div>
