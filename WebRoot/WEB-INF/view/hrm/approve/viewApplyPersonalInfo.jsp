<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updatePersonalInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}function submitForm1(type){
	alert(type);
	
}
function submitForm2(type){

	if($("#SUBMIT_TYPE").val()==1){
			$("#SUBMIT_TYPE").val(2);
	var $form = $("#updatePersonalInfo").submit();
	}
}
function submitForm3(type){
	if($("#SUBMIT_TYPE").val()==1){
		$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	var $form = $("#updatePersonalInfo").submit();
	}
}
function submitForm4(type){
	alert(type);
	
}
</script>
<%-- <c:if test="${personalInfo.SUBMIT_TYPE eq '1'  }"> --%>

	<div class="pageHeader"
		style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

		<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
			<div class="subBar">
				<ul>
					<li>
						<div>
							<a class="buttonActive" onclick="print()"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span> <!--印刷--><spring:message code="hrm.approve.PRINTING" />  </span> </a>
						</div>
					</li>
					<c:if test="${personalInfo.SUBMIT_TYPE eq '1'  }">
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm2('submit');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 完结 --> <spring:message code="hrm.approve.OVER" /></span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm3('back');" 
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 退回 --><spring:message code="hrm.approve.RETURN"/> </span>
							</a>
						</div>
					</li>
					</c:if>
					<%-- <li>
						<div>
							<a class="buttonActive" onclick="submitForm4('email');"
								alt="点击上传"><span><!-- 制定邮件  --> <spring:message code="hrm.approve.MAKE_MAILE" /> </span> </a>
						</div>
					</li> --%>
				</ul>
			</div>
		</div>

	</div>
	<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv"
	>
		<form id="updatePersonalInfo"
			onsubmit="return validateCallbackViewFamilyInfo(this, navTabAjaxDoneWithForm);"
			action="/hrm/approve/updatePersonalInfo" method="post">
			<c:if test="${personalInfo.PHOTO_PATH eq null}">
				<table class="table" width="100%" 
					style="margin: 0px; padding: 0px;">
					<thead>
						<tr>

							<th width="25%">
								NO
							</th>
							<th width="25%">
								Line
							</th>
							<th width="25%">
								<!-- 申请后 --> <spring:message code="hrm.approve.APPLY_AFTER" />
							</th>
							<th width="25%">
								<!-- 申请前 --> <spring:message code="hrm.approve.APPLY_FRONT" /> 
							</th>
						</tr>
					</thead>
					<tbody>

						<tr>
							<td>
								1
							</td>
							<td>
								<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" /><!-- 身份证号 -->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.IDCARD_NO ne personalInfoPro.IDCARD_NO ?'red':''}">${personalInfo.IDCARD_NO}
							</td>
							<td>
								${personalInfoPro.IDCARD_NO}
							</td>
						</tr>
						<tr>
							<td>
								2
							</td>
							<td>
								<spring:message code="hrm.empinfo.award_date" /><!--获证日期-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.IDCARD_START_DATE ne personalInfoPro.IDCARD_START_DATE ?'red':''}">${personalInfo.IDCARD_START_DATE}
							</td>
							<td>
								${personalInfoPro.IDCARD_START_DATE}
							</td>
						</tr>
						<tr>
							<td>
								3
							</td>
							<td>
								<spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 -->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.ISSUING_AUTHORITY ne personalInfoPro.ISSUING_AUTHORITY ?'red':''}">${personalInfo.ISSUING_AUTHORITY}
							</td>
							<td>
								${personalInfoPro.ISSUING_AUTHORITY}
							</td>
						</tr>
						<tr>
							<td>
								4
							</td>
							<td>
								<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.CV_UPDATE_STATUS ne personalInfoPro.CV_UPDATE_STATUS ?'red':''}">${personalInfo.CV_UPDATE_STATUS}
							</td>
							<td>
								${personalInfoPro.CV_UPDATE_STATUS}
							</td>
						</tr>
						<tr>
							<td>
								5
							</td>
							<td>
								<spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.DOB ne personalInfoPro.DOB ?'red':''}">${personalInfo.DOB}
							</td>
							<td>
								${personalInfoPro.DOB}
							</td>
						</tr>
						<tr>
							<td>
								6
							</td>
							<td>
								<spring:message code="hrm.empinfo.ORIGIN.Z" /><!--出生地-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.ORIGIN ne personalInfoPro.ORIGIN ?'red':''}">${personalInfo.ORIGIN}
							</td>
							<td>
								${personalInfoPro.ORIGIN}
							</td>
						</tr>
						<tr>
							<td>
								7
							</td>
							<td>
								<spring:message code="hrm.empinfo.SEXCODE" /><!--性别-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.SEXCODE ne personalInfoPro.SEXCODE ?'red':''}">${personalInfo.SEXCODE}
							</td>
							<td>
								${personalInfoPro.SEXCODE}
							</td>
						</tr>
						<tr>
							<td>
								8
							</td>
							<td>
								<spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.NATIONALITY_CODE ne personalInfoPro.NATIONALITY_CODE ?'red':''}">${personalInfo.NATIONALITY_CODE}
							</td>
							<td>
								${personalInfoPro.NATIONALITY_CODE}
							</td>
						</tr>
						<tr>
							<td>
								9
							</td>
							<td>
								<spring:message code="hrm.empinfo.NATION_CODE" /><!--民族-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.NATION_CODE ne personalInfoPro.NATION_CODE ?'red':''}">${personalInfo.NATION_CODE}
							</td>
							<td>
								${personalInfoPro.NATION_CODE}
							</td>
						</tr>
						<tr>
							<td>
								10
							</td>
							<td>
								<spring:message code="hrm.empinfo.CELLPHONE" /><!--手机-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.CELLPHONE ne personalInfoPro.CELLPHONE ?'red':''}">${personalInfo.CELLPHONE}
							</td>
							<td>
								${personalInfoPro.CELLPHONE}
							</td>
						</tr>
						<tr>
							<td>
								11
							</td>
							<td>
								<spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.MARITAL_STATUS_CODE ne personalInfoPro.MARITAL_STATUS_CODE ?'red':''}">${personalInfo.MARITAL_STATUS_CODE}
							</td>
							<td>
								${personalInfoPro.MARITAL_STATUS_CODE}
							</td>
						</tr>
						<tr>
							<td>
								12
							</td>
							<td>
								<spring:message code="hr.viewCondSql.title.JIEHUNRIQI" /><!--结婚日期-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.WEDDING_DATE ne personalInfoPro.WEDDING_DATE ?'red':''}">${personalInfo.WEDDING_DATE}
							</td>
							<td>
								${personalInfoPro.WEDDING_DATE}
							</td>
						</tr>
						<tr>
							<td>
								13
							</td>
							<td>
								<spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.EXIST_SINGLE ne personalInfoPro.EXIST_SINGLE ?'red':''}">${personalInfo.EXIST_SINGLE}
							</td>
							<td>
								${personalInfoPro.EXIST_SINGLE}
							</td>
						</tr>
						<tr>
							<td>
								14
							</td>
							<td>
								<spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.SING_ID ne personalInfoPro.SING_ID ?'red':''}">${personalInfo.SING_ID}
							</td>
							<td>
								${personalInfoPro.SING_ID}
							</td>
						</tr>
						<tr>
							<td>
								15
							</td>
							<td>
								<spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 -->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.HOME_PHONE ne personalInfoPro.HOME_PHONE ?'red':''}">${personalInfo.HOME_PHONE}
							</td>
							<td>
								${personalInfoPro.HOME_PHONE}
							</td>
						</tr>
						<tr>
							<td>
								16
							</td>
							<td>
								<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.OFFICE_PHONE ne personalInfoPro.OFFICE_PHONE ?'red':''}">${personalInfo.OFFICE_PHONE}
							</td>
							<td>
								${personalInfoPro.OFFICE_PHONE}
							</td>
						</tr>
						<tr>
							<td>
								17
							</td>
							<td>
								<spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.REG_PLACE ne personalInfoPro.REG_PLACE?'red':''}">${personalInfo.REG_PLACE}
							</td>
							<td>
								${personalInfoPro.REG_PLACE}
							</td>
						</tr>
						<tr>
							<td>
								18
							</td>
							<td>
								<spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.EMAIL_SECOND ne personalInfoPro.EMAIL_SECOND ?'red':''}">${personalInfo.EMAIL_SECOND}
							</td>
							<td>
								${personalInfoPro.EMAIL_SECOND}
							</td>
						</tr>
						<tr>
							<td>
								19
							</td>
							<td>
								<spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.EMAIL ne personalInfoPro.EMAIL ?'red':''}">${personalInfo.EMAIL}
							</td>
							<td>
								${personalInfoPro.EMAIL}
							</td>
						</tr>
						<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<tr>
							<td>
								20
							</td>
							<td>
								<spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.FILE_LOCATION ne personalInfoPro.FILE_LOCATION ?'red':''}">${personalInfo.FILE_LOCATION}
							</td>
							<td>
								${personalInfoPro.FILE_LOCATION}
							</td>
						</tr>
						<tr>
							<td>
								21
							</td>
							<td>
								<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.FILE_ENTER ne personalInfoPro.FILE_ENTER ?'red':''}">${personalInfo.FILE_ENTER}
							</td>
							<td>
								${personalInfoPro.FILE_ENTER}
							</td>
						</tr>
						<tr>
							<td>
								22
							</td>
							<td>
								<spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.FILE_OUT ne personalInfoPro.FILE_OUT ?'red':''}">${personalInfo.FILE_OUT}
							</td>
							<td>
								${personalInfoPro.FILE_OUT}
							</td>
						</tr>
						</c:if>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<tr>
							<td>
								20
							</td>
							<td>
								<spring:message code="ess.empInfo.religion"/><!-- 宗教 -->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.RELIGION ne personalInfoPro.RELIGION ?'red':''}">${personalInfo.RELIGION}
							</td>
							<td>
								${personalInfoPro.RELIGION}
							</td>
						</tr>
						<tr>
							<td>
								21
							</td>
							<td>
								<spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" /><!--政治面貌-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.POLITICAL_OUTLOOK_NAME ne personalInfoPro.POLITICAL_OUTLOOK_NAME ?'red':''}">${personalInfo.POLITICAL_OUTLOOK_NAME}
							</td>
							<td>
								${personalInfoPro.POLITICAL_OUTLOOK_NAME}
							</td>
						</tr>
						<tr>
							<td>
								22
							</td>
							<td>
								<spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.RESIDENTIAL_DISTINCTION ne personalInfoPro.RESIDENTIAL_DISTINCTION ?'red':''}">${personalInfo.RESIDENTIAL_DISTINCTION}
							</td>
							<td>
								${personalInfoPro.RESIDENTIAL_DISTINCTION}
							</td>
						</tr>
						<tr>
							<td>
								23
							</td>
							<td>
								<spring:message code="hrm.empinfo.ARMY_OR_NOT.Z" /><!--参军与否-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.ARMY_OR_NOT ne personalInfoPro.ARMY_OR_NOT ?'red':''}">
									<input type="checkbox" disabled="disabled" <c:if test="${personalInfo.ARMY_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input>
							</td>
							<td>
								<input type="checkbox" disabled="disabled" <c:if test="${personalInfoPro.ARMY_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input>
							</td>
						</tr>
						 <tr>
							<td>
								24
							</td>
							<td>
								 <spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z" /><!--障碍与否-->
							</td>
							<td>
								<font style="line-height: 20px;"
									color="${personalInfo.OBSTACLE_OR_NOT ne personalInfoPro.OBSTACLE_OR_NOT ?'red':''}">
									<input type="checkbox" disabled="disabled" <c:if test="${personalInfo.OBSTACLE_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input>
							</td>
							<td>
								<input type="checkbox" disabled="disabled" <c:if test="${personalInfoPro.OBSTACLE_OR_NOT eq 'Y'}"> checked="checked" </c:if> ></input>
							</td>
						</tr>
					    </c:if>
					</tbody>
				</table>
			  <input type="hidden" id="picDif" name="picDif" value="1"/>
			</c:if>
			
			      	<input type="hidden" value="${personalInfo.APPLY_TYPE_NUM}"
							name="APPLY_TYPE" id="APPLY_TYPE_NUM" />
						<!-- 1提交 2审批 3退回 4取消 -->
						<input type="hidden" value="${personalInfo.SUBMIT_TYPE}"
							id="SUBMIT_TYPE" name="ACTIVITY_NUM" />

						<input type="hidden" value="${personalInfo.PERSON_NO}"
							name="PERSON_NO" id="PERSON_NO" />
						<input type="hidden" value="${personalInfo.UPDATE_PERSON_ID}"
							id="UPDATE_PERSON_ID" name="UPDATE_PERSON_ID" />
						<input type="hidden" value="${personalInfo.PERSON_ID}"
							id="PERSON_ID" name="PERSON_ID" />
						<input type="hidden" value="${personalInfo.ESS_TYPE_CODE }"
							id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
							<input type="hidden" value="${personalInfo.APPLY_TYPE }"
							id="APPLY_TYPES" name="APPLY_TYPES" />

			               
			<!-- 个人照片申请 -->
			<c:if test="${personalInfo.PHOTO_PATH ne null}">

            <input type="hidden" id="picDif" name="picDif" value="2"/>
				<div class="pageContent" style="margin: 0px; padding: 0px;">

					<table class="table" width="100%" layoutH="288"
						style="margin: 0px; padding: 0px;">

						<thead style="height: 20px">
							<tr>

								<th width="50%">
									<!-- 申请后 --> <spring:message code="hrm.approve.APPLY_AFTER" />
								</th>
								<th width="50%">
								<!-- 申请前 --> <spring:message code="hrm.approve.APPLY_FRONT" />
								</th>
							</tr>
						</thead>


					</table>
					<table width="100%">
						<tr>
							<td width="50%">
								<img align="middle" src="${personalInfo.PHOTO_PATH}" border=0
									style='width: 175px; height: 233px;' />
							</td>

							<td width="50%" style="height: 250px">
								<img align="middle" src="${personalInfoPro.PHOTO_PATH}" border=0
									style='width: 175px; height: 233px;' />
							</td>
							</td>
						</tr>
						</tbody>
					</table>
				</div>

			</c:if>


			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							 <!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${personalInfo.CALLBACK}</textarea>
						</td>
					</tr>
				</table>

				<div width="100%">
					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容 --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${personalInfo.EARROR}</textarea>
							</td>
						</tr>
					</table>
				</div>
		</form>
	</div>
<%-- </c:if> --%>