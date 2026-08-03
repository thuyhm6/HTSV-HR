<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="pageFormContent nowrap" layoutH="80">
		<div class="panel collapse" >
			<h1>
				<spring:message code="hr.viewHire.title.COMPANYINFORMATIONIN" />
				<!--司内信息  --><!-- 직원기초정보 -->
			</h1>
			<div>
				<table id="info" border='0' width="100%" cellspacing="0"
						cellpadding="0" class="user_table">
						<tr>
							<td class="td_title">法人代码</td>
							<td class="td_type">${personInfo.CPNY_ID}</td>
							<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></td>
							<td class="td_type">${personInfo.DEPT_NAME}</td>
							<td class="td_title">法人入职日期</td>
							<td class="td_type">${personInfo.JOIN_COMPANY_DATE}</td>
                            <td class="td_title"></td>
                            <td class="td_type"></td>
						</tr>
						<tr>
							<td class="td_title">社编 </td>
							<td class="td_type">${personInfo.EMPID}</td>
							<td class="td_title">中文姓名</td>
							<td class="td_type">${personInfo.LOCAL_NAME}</td>
                            <td class="td_title">英文姓名</td>
							<td class="td_type">${personInfo.CHINESE_PINYIN}</td>
                            <td class="td_title">试用期开始日期</td>
							<td class="td_type">${personInfo.PROB_STRT_DATE}</td>
						</tr>
						<tr>
							<td class="td_title">试用期结束日期</td>
							<td class="td_type">${personInfo.END_PROBATION_DATE}</td>
                            <td class="td_title">试用期比例(%)</td>
                            <td class="td_type"> ${personInfo.PROB_PAY_RAT}</td>
                            <td class="td_title">职务</td>
                            <td class="td_type"> ${personInfo.JOB_TITLE_CD}</td>
						</tr>
				</table>
			</div>
		</div>
		<div class="clear"></div>
		<div class="panel collapse" >
			<h1>
				<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION_DETAIL" />
				<!--个人基础信息--><!-- 개인기초정보 -->
			</h1>
			<div>
				<table id="info" cellspacing="0" cellpadding="2" border="0"
					width="100%" class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
							<!--国籍-->
						</td>
						<td class="td_type">${personInfo.NATIONALITY_NM}</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.SEX" />
							<!--性别--><!-- 성별 -->
						</td>
						<td class="td_type">${personInfo.SEXCODE}</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
							<!--身份证号--><!-- 신분증번호 -->
						</td>
						<td class="td_type">${personInfo.IDCARD_NO}</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.NATION_NAME" />
							<!--民族 -->
						</td>
						<td class="td_type">${personInfo.NATION_NM}</td>
					</tr>
					<tr>
						<td class="td_title">最终学历</td>
						<td class="td_type">${personInfo.FINAL_DEGREE_NM}</td>
						<td class="td_title">生日</td>
						<td class="td_type">${personInfo.DOB}</td>
						<td class="td_title">ID卡号</td>
						<td class="td_type">${personInfo.ID_CARD_NO}</td>
						<td class="td_title">
							<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
							<!--手机号码-->
						</td>
						<td class="td_type">${personInfo.CELLPHONE}</td>
					</tr>
					<tr>
						<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.email.chinese" />
						<!--邮箱-->
						</td>
						<td class="td_type">${personInfo.EMAIL}</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
							<!--户口性质-->
						</td>
						<td class="td_type" width="15%">${personInfo.REG_TYPE_CODE}</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
							<!--户口所在地-->
						</td>
						<td class="td_type" colspan="3">${personInfo.REG_PLACE}</td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="panel collapse">
			<h1>
				<spring:message code="hr.viewPersonalInfo.title.payinfo" />
				<!--工资信息 -->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="hr.empinfo.pay.type.num" />
							<!--工资级号-->
						</td>
						<td class="td_type">${personInfo.PAY_GRADE}</td>
						<td class="td_title">
							<spring:message code="hr.empinfo.pay.type.num.leave"/>
							<!--工资级号等级-->
						</td>
						<td class="td_type">${personInfo.PAY_STEP}</td>
						<td class="td_title">
							<spring:message code="ess.viewpersonalpainfo.jibengongzi"/>
							<!--基本工资-->
						</td>
						<td class="td_type">${personInfo.BASE_PAY}</td>
						<td class="td_title">
						<spring:message code="hr.empinfo.pay.VARIABLE_SALARY_MONTH"/>
						<!--变动工资-->
						</td>
						<td class="td_type">${personInfo.VARB_PAY}</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="pa.wagebase.title.openAccountBanks"/>
							<!--开户行-->
						</td>
						<td class="td_type">${personInfo.CARD_NAME}</td>
						<td class="td_title">
							<spring:message code="rp.report.title.bankcardno"/>
							<!-- 银行账号 -->
						</td>
						<td class="td_type">${personInfo.CARD_NO}</td>
					</tr>
				</table>
			</div>
		</div>
	
		<div style="clear: both;"></div>
		<div class="panel collapse">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.workinfo" />
			<!--工作信息 -->
		</h1>

		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<tr>
					<td class=td_title>福利地区</td>
					<td class="td_type">${personInfo.INSRAREA_NM}</td>
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.renyuanleixing.chr" />
						<!--人员类型(CHR)-->
					</td>
					<td class="td_type" width="20%">${personInfo.EMP_TYPE_NM}</td>
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu" />
						<!--工作地区-->
					</td>
					<td class="td_type" colspan="3">${personInfo.WORK_AREA_NAME}</td>
				</tr>
				<tr>
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.gongzuoleixing.chr" />
						<!--工作类型(CHR)-->
					</td>
					<td class="td_type" width="20%">${personInfo.PROMTR_WORK_NM}</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.banhao"/>
						<!--班号-->
					</td>
					<td class="td_type">${personInfo.SHIFT_NO}</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu" />
						<!--促销员所属-->
					</td>
					<td class="td_type">${personInfo.PROMTR_NM}</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.xingjijibie" />
					</td>
					<td class="td_type" width="15%">${personInfo.STAR_NM}</td>
				</tr>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.chanpin" />
					</td>
					<td class="td_type">${personInfo.PROD_NM}</td>
					<td class="td_title">兼卖产品</td>
					<td class="td_type" colspan=3>
						<table class="table" width="100%">
						  <c:forEach items="${codeList}" var="item" varStatus="i">
						    <c:if test="${i.count%4 eq 1}">
						      <tr><td width="80"><input type="checkbox" name="PRODUCT_NO" checked disabled=1/>&nbsp;&nbsp;${item.CONTENT}</td>
						    </c:if>
						    <c:if test="${i.count%4 eq 0}">
						      <td width="80"><input type="checkbox" name="PRODUCT_NO" checked disabled=1/>&nbsp;&nbsp;${item.CONTENT }</td></tr>
						    </c:if>
						    <c:if test="${i.count%4 ne 1 and i.count%4 ne 0}">
						      <td width="80"><input type="checkbox" name="PRODUCT_NO" checked disabled=1/>&nbsp;&nbsp;${item.CONTENT }</td>
						    </c:if>
						  </c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</div>
	
<div class="pageContent">
<div class="pageFormContent">
	<table class="table" width="100%" nowrapTD="false">
		<tbody>
		<tr>
		    <td width="20%" style="text-align:center">
		             附件
		    </td>
		    <td width="80%" class='td_type'>
			<c:forEach items="${fileList}" var="file" varStatus="i">
			    <a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
			    &nbsp;&nbsp;&nbsp;
			</c:forEach>
		    </td>
		</tr>			
		</tbody>
	</table>
</div>
</div>
	
	<table class="user_table" width="100%" border="0">
		<tr>
			<c:if test="${affirmorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<c:if test="${affirmorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- 决裁等级 -->
				决裁等级
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁者 -->
				决裁者
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁情况 -->
				决裁情况
			</td>
			<td class="td_title" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁批注 -->
				决裁批注
			</td>
		</tr>
		<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						未决裁
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						已通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						已否决
					</c:if>
				</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmorListCnt == 0}">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
		</c:if>
		<tr>
			<c:if test="${checkorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<c:if test="${checkorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${3 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- Type -->
				Type
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Requests -->
				Requests
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Reviewed -->
				Reviewed
			</td>
		</tr>
		<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">Public</td>
				<td class="td_type" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br>
					[Request]：${checkor.CHECK_REASON}
				</td>
				<td class="td_type" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;<br>
					[Check]：${checkor.CHECK_CONTENT}
				</td>
			</tr>	
		</c:forEach>	
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_title" style="text-align: center">Public</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
			</tr>	
		</c:if>	
	</table>
	</div>
	
</div>