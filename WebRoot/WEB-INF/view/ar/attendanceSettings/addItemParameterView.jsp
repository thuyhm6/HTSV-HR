<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	//根据公司获取动态组
	function dynamicGroup(obj){
		var cnpyId = obj.value;
		$.post("/ar/attendanceSettings/getDynamicGroupByCpnyId",
				{seach_cpnyId:cnpyId},
			function(result){
				$("#AR_GROUP_NO").html('');
				// 默认组 
				$("#AR_GROUP_NO").append("<option value='constant'><spring:message code='ar.viewitemparameter.title.morenzu'/></option>");
				$.each(result,function(i,group){
					$("#AR_GROUP_NO").append("<option value='"+group.GROUP_NO+"'>"+group.GROUP_NAME+"</option>");
				});
		});
	}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addItemParameterInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
		
			<dl>
				<dt><!-- 考勤项目 --><spring:message code="ar.viewitemparameter.title.kaoqingxiangmu"/></dt>
				<dd>
					<input name="AR_ITEM_NO" type="hidden" value="${itemInfo.ITEM_NO}"/>
					${itemInfo.ITEM_NAME}
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
				<dd>
					<select name="dis_CPNY_ID" disabled="disabled" onchange="dynamicGroup(this);getSyCodeByCpnyID(this)">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}" <c:if test="${defaultCpnyID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
						</c:forEach>
					</select>
					<input id="CPNY_ID" type="hidden" name="CPNY_ID" value="${defaultCpnyID}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 组名称 --><spring:message code="ar.viewitemparameter.title.zumingcheng"/></dt>
				<dd>
					<select name="AR_GROUP_NO" id="AR_GROUP_NO">
						<option value="constant"><!-- 默认组 --><spring:message code="ar.viewitemparameter.title.morenzu"/></option>	
						<c:forEach items="${arGroup}" var="group">
							<option value="${group.GROUP_NO}">${group.GROUP_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></dt>
				<dd>
					<select name="UNIT" class="combox">
						<option value="MINUTE"><!-- 分钟 --><spring:message code="ar.viewitemparameter.title.fenzhong"/></option>
						<option value="HOUR"><!-- 小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi"/></option>
					    <option value="DAY"><!-- 天 --><spring:message code="ar.viewitemparameter.title.dayofunit"/></option>
					    <option value="TIME"><!-- 次 --><spring:message code="ar.viewitemparameter.title.timeofunit"/></option>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 最小单位 --><spring:message code="ar.viewitemparameter.title.zuixiaodanwei"/></dt>
				<dd>
					<input type="text" name="UNIT_VALUE" value="0.5" class="number">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 最小值 --><spring:message code="ar.viewitemparameter.title.zuixiaozhi"/></dt>
				<dd>
					<input type="text" name="MIN_VALUE"  value="0.5" class="number">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 最大值 --><spring:message code="ar.viewitemparameter.title.zuidazhi"/></dt>
				<dd>
					<input type="text" name="MAX_VALUE" class="number">
				</dd>
			</dl>
			
			<!--<p>
				<label>替代项目</label>
				<input type="text" name="DEPEND_ITEM" class="textInput"/>
			</p>
			<p>
				<label>依赖项目</label>
				<input type="text" name="REPLACE_ITEM" class="textInput"/>
			</p>-->
			
			<dl>
				<dt><!-- 有效日期类型 --><spring:message code="ar.viewitemparameter.title.youxiaoriqileixing"/></dt>
				<dd>
					<input type="checkbox" name="DATE_TYPE" value="1440" checked><!-- 平日--><spring:message code="ar.viewitemparameter.title.pingshi"/>
			        <input type="checkbox" name="DATE_TYPE" value="1441"><!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/>
			        <input type="checkbox" name="DATE_TYPE" value="1442"><!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否参考刷卡 --><spring:message code="ar.viewitemparameter.title.cankaoshuaka"/></dt>
				<dd>
					<select name="CARD_FLAG" class="combox">
				      <option value="1"><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始标志 --><spring:message code="ar.viewitemparameter.title.dakakaishibiaozhi"/></dt>
				<dd>
					<select name="CARD_FROM_FLAG" class="combox">
				      <option value="1"><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始偏移 --><spring:message code="ar.viewitemparameter.title.dakakaishipianyi"/></dt>
				<dd>
					<input name="CARD_FROM_OFFSET" type="text" value="0" class="number">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡开始偏移方向 --><spring:message code="ar.viewitemparameter.title.dakakaishipianyifangxiang"/></dt>
				<dd>
					<select name="CARD_FROM_RELATION" class="combox">
				      <option value="＞"><!-- 大于--><spring:message code="ar.viewitemparameter.title.dayu"/></option>
				      <option value="＜"><!-- 小于--><spring:message code="ar.viewitemparameter.title.xiaoyu"/></option>
				      <option value="＝" selected><!-- 等于--><spring:message code="ar.viewitemparameter.title.dengyu"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束标志 --><spring:message code="ar.viewitemparameter.title.dakajieshubiaozhi"/></dt>
				<dd>
					<select name="CARD_TO_FLAG" class="combox">
				      <option value="1"><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束偏移 --><spring:message code="ar.viewitemparameter.title.dakajieshupianyi"/></dt>
				<dd>
					<input name="CARD_TO_OFFSET" type="text" value="0" class="number">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 打卡结束偏移方向 --><spring:message code="ar.viewitemparameter.title.dakajieshupianyifangxiang"/></dt>
				<dd>
					<select name="CARD_TO_RELATION" class="combox">
				      <option value="＞"><!-- 大于--><spring:message code="ar.viewitemparameter.title.dayu"/></option>
				      <option value="＜"><!-- 小于--><spring:message code="ar.viewitemparameter.title.xiaoyu"/></option>
				      <option value="＝" selected><!-- 等于--><spring:message code="ar.viewitemparameter.title.dengyu"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否参考申请 --><spring:message code="ar.viewitemparameter.title.shifoucankaoshenqing"/></dt>
				<dd>
					<select name="APPLY_FLAG" class="combox">
				      <option value="1" selected><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 申请类型 --><spring:message code="ar.viewitemparameter.title.shenqingleixing"/></dt>
				<dd>
					<select name="APPLY_TYPE">
						<c:forEach items="${applyList}" var="apply">
						  <option value="${apply.CODE_NO}">${apply.CODENAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 申请满一日数值 --><spring:message code="ar.viewitemparameter.title.shenqingmanyirishuzhi"/></dt>
				<dd>
					<input name="APPLY_FULLDAY_VALUE" type="text" value="8" size="5" maxlength="5" class="number">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 申请与打卡优先级 --><spring:message code="ar.viewitemparameter.title.shenqingdakayouxianji"/></dt>
				<dd>
					<select name="APPLY_CARD_PRIORITY" class="combox">
				      <option value="0" selected><!--打卡-->打卡</option>
				      <option value="1"><!--申请-->申请</option>
					  <option value="2"><!--交集-->交集</option>
			        </select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></dt>
				<dd>
					<select name="ACTIVITY" class="combox">
				      <option value="1" selected><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
