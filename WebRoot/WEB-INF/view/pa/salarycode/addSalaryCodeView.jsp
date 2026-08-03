<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/salarycode/addSalaryCodeInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
		    <dl>
				<dt>
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</dt>
				<dd>
					<select id="ITEM_TYPE" name="ITEM_TYPE" class="required " > 
						<option value=""><!--全部--><spring:message code="pa.salary.canShu.quanBu"/></option>
						<option value="1" >
							<!--标准项目--><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b"/>
						</option>
						<option value="2" >
							<!--支付调整项目--><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b"/>
						</option>
						<option value="3" >
							<!--支付例外项目--><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b"/>
						</option>
						<option value="4" >
							<!--扣除调整项目--><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b"/>
						</option>
						<option value="5" >
							<!--扣除例外项目--><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b"/>
						</option>
						<option value="6" >
							<!--计算项目--><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b"/>
						</option>
						</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.wagebase.title.basicItemID"/><!--基础项目ID-->:
				</dt>
				<dd>
					<input name="ITEM_ID" type="text" id="ITEM_ID" class="required " maxlength="30"/>
				</dd>
			</dl>
			<ait:SyLanguage />
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:
				</dt>
				<dd>
					<select id="DATA_TYPE" name="DATA_TYPE">
						<option value="NUMBER(14,4)" selected>
							<spring:message code="pa.insurance.title.numberType"/><!--数字类型-->
						</option>
						<option value="VARCHAR(100)">
							<spring:message code="pa.insurance.title.varcharType"/><!--字符类型-->
						</option>
					</select>
				</dd>
			</dl>
			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="DESCR"
						id="DESCR" style="width: 400px"></textarea>
						</td>
					</tr>
				</table>
			</dl>

			<div class="formBar" layoutH="106">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.submit"/><!-- 提交 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>