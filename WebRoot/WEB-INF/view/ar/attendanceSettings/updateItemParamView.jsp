<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateItemParameterInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="NO" value="${itemInfo.AR_PARAM_NO}"/>
		<input type="hidden" name="OLD_ITEM_ID" value="${itemInfo.ITEM_ID}"/>
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><!-- 考勤项目 --><spring:message code="ar.viewitemparameter.title.kaoqingxiangmu"/></dt>
				<dd>
					<select name="AR_ITEM_NO" >
							<option value="${itemInfo.ITEM_NO}">${itemInfo.ITEM_NAME}</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
			   <dd>
					<select name="CPNY_ID" >
						<option value="${itemInfo.CPNY_ID}">${itemInfo.CPNY_ID}</option>	
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 组名称 --><spring:message code="ar.viewitemparameter.title.zumingcheng"/></dt>
				<dd>
<!-- 					<select name="AR_GROUP_NO" id="AR_GROUP_NO"> -->
<!-- 						<option value="constant">默认组<spring:message code="ar.viewitemparameter.title.morenzu"/></option>	 -->
<!-- 						<c:forEach items="${arGroup}" var="group"> -->
<!-- 							<option value="${group.GROUP_NO}">${group.GROUP_NAME}</option> -->
<!-- 						</c:forEach> -->
<!-- 					</select> -->
					<input type="text" id="AR_GROUP_NAME"   value="<spring:message code="ar.viewitemparameter.title.morenzu"/>">
					<input type="hidden" name="AR_GROUP_NO" id="AR_GROUP_NO"  value="constant">
				</dd>
			</dl>
			<dl>
				<dt><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></dt>
				<dd>
					<select name="UNIT"  id="UNIT" >
						<option value="MINUTE" <c:if test="${itemInfo.UNIT eq 'MINUTE'}">selected</c:if>><!-- 分钟 --><spring:message code="ar.viewitemparameter.title.fenzhong"/></option>
						<option value="HOUR" <c:if test="${itemInfo.UNIT eq 'HOUR'}">selected</c:if>><!-- 小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi"/></option>
					    <option value="DAY" <c:if test="${itemInfo.UNIT eq 'DAY'}">selected</c:if>><!-- 天 --><spring:message code="ar.viewitemparameter.title.dayofunit"/></option>
					    <option value="TIME" <c:if test="${itemInfo.UNIT eq 'TIME'}">selected</c:if>><!-- 次 --><spring:message code="ar.viewitemparameter.title.timeofunit"/></option>
					
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 最小单位 --><spring:message code="ar.viewitemparameter.title.zuixiaodanwei"/></dt>
				<dd>
					<input type="text" name="UNIT_VALUE"  value="${itemInfo.UNIT_VALUE}" class="number" />
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 最小值 --><spring:message code="ar.viewitemparameter.title.zuixiaozhi"/></dt>
				<dd>
					<input type="text" name="MIN_VALUE"  value="${itemInfo.MIN_VALUE}" class="number"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 最大值 --><spring:message code="ar.viewitemparameter.title.zuidazhi"/></dt>
				<dd>
					<input type="text" name="MAX_VALUE" type="text" class="textInput" value="${itemInfo.MAX_VALUE}" class="number"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 有效日期类型 --><spring:message code="ar.viewitemparameter.title.youxiaoriqileixing"/></dt>
				<dd>
					<input type="checkbox" name="DATE_TYPE"  value="1440"  <c:if test="${itemInfo.DATE_TYPE0 eq '1440' or itemInfo.DATE_TYPE1 eq '1440' or itemInfo.DATE_TYPE2 eq '1440'}">checked</c:if>/><!-- 平日--><spring:message code="ar.viewitemparameter.title.pingshi"/>
			        <input type="checkbox" name="DATE_TYPE"  value="1441" <c:if test="${itemInfo.DATE_TYPE0 eq '1441' or itemInfo.DATE_TYPE1 eq '1441' or itemInfo.DATE_TYPE2 eq '1441'}">checked</c:if>/><!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/>
			        <input type="checkbox" name="DATE_TYPE"   value="1442" <c:if test="${itemInfo.DATE_TYPE0 eq '1442' or itemInfo.DATE_TYPE1 eq '1442' or itemInfo.DATE_TYPE2 eq '1442'}">checked</c:if>/><!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 是否参考刷卡 --><spring:message code="ar.viewitemparameter.title.cankaoshuaka"/></dt>
				<dd>
					<select name="CARD_FLAG"  >
				      <option value="1" <c:if test="${itemInfo.CARD_FLAG eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${itemInfo.CARD_FLAG eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始标志 --><spring:message code="ar.viewitemparameter.title.dakakaishibiaozhi"/></dt>
				<dd>
					<select name="CARD_FROM_FLAG"  class="combox">
				      <option value="1" <c:if test="${itemInfo.CARD_FROM_FLAG eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${itemInfo.CARD_FROM_FLAG eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始偏移 --><spring:message code="ar.viewitemparameter.title.dakakaishipianyi"/></dt>
				<dd>
					<input name="CARD_FROM_OFFSET"  value="${itemInfo.CARD_FROM_OFFSET}" class="number"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始偏移方向 --><spring:message code="ar.viewitemparameter.title.dakakaishipianyifangxiang"/></dt>
				<dd>
					<select name="CARD_FROM_RELATION"  >
				      <option value="＞" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＞'}">selected</c:if>><!-- 大于--><spring:message code="ar.viewitemparameter.title.dayu"/></option>
				      <option value="＜" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＜'}">selected</c:if>><!-- 小于--><spring:message code="ar.viewitemparameter.title.xiaoyu"/></option>
				      <option value="＝" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＝'}">selected</c:if>><!-- 等于--><spring:message code="ar.viewitemparameter.title.dengyu"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束标志 --><spring:message code="ar.viewitemparameter.title.dakajieshubiaozhi"/></dt>
				<dd>
					<select name="CARD_TO_FLAG"   >
				      <option value="1" <c:if test="${itemInfo.CARD_TO_FLAG eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${itemInfo.CARD_TO_FLAG eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束偏移 --><spring:message code="ar.viewitemparameter.title.dakajieshupianyi"/></dt>
				<dd>
					<input name="CARD_TO_OFFSET"    type="text" value="${itemInfo.CARD_TO_OFFSET}" class="number"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束偏移方向 --><spring:message code="ar.viewitemparameter.title.dakajieshupianyifangxiang"/></dt>
				<dd>
					<select name="CARD_TO_RELATION"   >
				      <option value="＞" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＞'}">selected</c:if>><!-- 大于--><spring:message code="ar.viewitemparameter.title.dayu"/></option>
				      <option value="＜" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＜'}">selected</c:if>><!-- 小于--><spring:message code="ar.viewitemparameter.title.xiaoyu"/></option>
				      <option value="＝" <c:if test="${itemInfo.CARD_FROM_RELATION eq '＝'}">selected</c:if>><!-- 等于--><spring:message code="ar.viewitemparameter.title.dengyu"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否参考申请 --><spring:message code="ar.viewitemparameter.title.shifoucankaoshenqing"/></dt>
				<dd>
					<select name="APPLY_FLAG"   >
				      <option value="1" <c:if test="${itemInfo.APPLY_FLAG eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${itemInfo.APPLY_FLAG eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 申请类型 --><spring:message code="ar.viewitemparameter.title.shenqingleixing"/></dt>
				<dd>
					<select name="APPLY_TYPE">
						<c:forEach items="${applyList}" var="apply">
						  <option value="${apply.CODE_NO}" <c:if test="${apply.CODE_NO eq itemInfo.APPLY_TYPE}">selected</c:if>>${apply.CODENAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 申请满一日数值 --><spring:message code="ar.viewitemparameter.title.shenqingmanyirishuzhi"/></dt>
				<dd>
					<input name="APPLY_FULLDAY_VALUE"   type="text" value="${itemInfo.APPLY_FULLDAY_VALUE}" class="number"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 申请与打卡优先级 --><spring:message code="ar.viewitemparameter.title.shenqingdakayouxianji"/></dt>
				<dd>
					<select name="APPLY_CARD_PRIORITY"  >
				      <option value="0" <c:if test="${itemInfo.APPLY_CARD_PRIORITY eq 0}">selected</c:if>><!--打卡-->打卡</option>
				      <option value="1" <c:if test="${itemInfo.APPLY_CARD_PRIORITY eq 1}">selected</c:if>><!--申请-->申请</option>
					  <option value="2" <c:if test="${itemInfo.APPLY_CARD_PRIORITY eq 2}">selected</c:if>><!--交集-->交集</option>
			        </select>
				</dd>
			</dl>
			<dl style="height:auto;">
				<table>
					<tr>
						<td class="td_title" style="width:122px"><!-- 人事政策 --><spring:message code="ar.attend.title.hr_policy"/></td>
						<td class="td_type"><textarea name="DETAIL_CONTENT" cols="60" rows="2">${itemInfo.DETAIL_CONTENT}</textarea>
										
						</td>
					</tr>
				</table>
			</dl>
			<dl>
				<dt><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></dt>
				<dd>
					<select name="ACTIVITY"  >
				      <option value="1" <c:if test="${itemInfo.ACTIVITY eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${itemInfo.ACTIVITY eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
