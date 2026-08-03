<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var initSpecialParam = "${loginUserInfo.SPECIAL_PARAM}";
var initPersonId = "${loginUserInfo.PERSON_ID}";
var submitFlag = "N";
var zTree;
var demoIframe;

var setting = {

	view : {
		dblClickExpand : true,
		showLine : true,
		selectedMulti : false,
		expandSpeed : "fast"
	},
	check : {
		autoCheckTrigger : false,
		chkboxType : {
			"Y" : "s",
			"N" : "ps"
		},
		chkStyle : "checkbox",
		enable : true,
		nocheckInherit : true,
		radionType : "level"
	},
	data : {
		key : {
			checked : "CHECKED",
			name : "DEPTNAME",
			open : "true"
		},
		simpleData : {
			enable : true,
			idKey : "DEPTNO",
			pIdKey : "PARENT_DEPT_NO",
			rootPId : ""
		}
	},
	callback : {
		onCheck : function(treeId, treeNode) {
			var t = $.fn.zTree.getZTreeObj("deptTree_sy0120");
			var nodes = t.getCheckedNodes();
			if (nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					if (i == 0) {
						document.getElementById("deptNos").value = nodes[i].DEPTNO;
					} else {
						document.getElementById("deptNos").value += ","
								+ nodes[i].DEPTNO;
					}
				}
			} else {
				document.getElementById("deptNos").value = "";
			}
		}
	}
};
/**
 决裁者模糊查询开始
 */
var keyCodeInit = 0;
function submitKeyClick_sy0130_update(obj, localName, idcardNo, navTabId, event) {
	var e = event ? event : window.event;
	var keyCode = e.which ? e.which : e.keyCode;
	if (keyCode == 13) {
		keyCodeInit = keyCode;
		var empid = obj.value;
		var empIdStr = obj.id;
		var personIdStr = "personId" + empIdStr.substring(5);
		var empNameStr = "affirmorName" + empIdStr.substring(5);

		$
				.ajax( {
					type : 'POST',
					url : encodeURI('/sys/affirm/getPersonCnt?navTabId='
							+ navTabId + '&EMPID=' + empid + '&LOCAL_NAME='
							+ localName + '&IDCARD_NO=' + idcardNo),
					dataType : "json",
					cache : false,
					success : function(jsonObject) {
						if (jsonObject.perCnt == 0) {
							alertMsg
									.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
							document.getElementById("personId").value = "";
							document.getElementById("PERSON_ID_EXSIT").innerHTML = "查无此人";//empName deptName
						}
						if (jsonObject.perCnt > 1) {
							document.getElementById("onck").href = encodeURI(encodeURI("/sys/affirm/viewAffirmorsList?pageNum=1&navTabId="
									+ navTabId
									+ "&seach_EMPID="
									+ empid
									+ '&seach_LOCAL_NAME='
									+ localName
									+ '&seach_IDCARD_NO='
									+ idcardNo
									+ '&empId_sy0130_add='
									+ empIdStr
									+ '&personId_sy0130_add=' + personIdStr));
							document.getElementById("onck").click();
						}
						if (jsonObject.perCnt == 1) {
							//document.getElementById(empIdStr).value=jsonObject.empId;
							//document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
							///document.getElementById(empNameStr).innerHTML=jsonObject.empName;//empName deptName
							//addTdAdd_sy0130_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
							checkAffirmor(jsonObject.personId,
									jsonObject.empId, jsonObject.empName,
									jsonObject.deptName, '');
						}
					},
					error : DWZ.ajaxError
				});
	}
}
function checkAffirmor(personId, empId, empName, deptName, letters) {
	document.getElementById("EMPID").value = empId;
	document.getElementById("personId").value = personId;
	document.getElementById("affirmorName").innerHTML = empName;
	var specialStr = document.getElementById("SPECIAL_PARAM").value;
	if (personId != "") {
		if (initSpecialParam == "special") {
			var url = "/sys/rightsManagement/validatePersonIdExist?PERSON_ID="
					+ personId;
			validateValue(url, "N");
		} else {
			if (initPersonId == personId) {
				//var url="/sys/rightsManagement/validatePersonIdExist?PERSON_ID="+personId;
				//validateValue(url,"Y");
			} else {
				var url = "/sys/rightsManagement/validatePersonIdExist?PERSON_ID="
						+ personId;
				validateValue(url, "Y");
			}
		}
	}
}
function validateValue(url, flagVal) {
	$
			.ajax( {
				async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				url : url,//"/sys/rightsManagement/validatePersonIdExist?PERSON_ID="+personId,//请求的action路径  
				error : function() {//请求失败处理函数  
					alertMsg
							.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');
				},
				success : function(data) { //请求成功后处理函数。  
					submitFlag = data.flagYn; //把后台封装好的简单Json格式赋给treeNodes
					if (submitFlag == "Y") {
						document.getElementById("PERSON_ID_EXSIT").innerHTML = "该员工已经有账号了，不能再次创建账号!";
					} else {
						document.getElementById("PERSON_ID_EXSIT").innerHTML = "&nbsp;";
					}
				}
			});
}
var zNodes;
$
		.ajax( {
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "/sys/rightsManagement/getLoginUserDeptList?userNo=${loginUserInfo.USER_NO}",//请求的action路径  
			error : function() {//请求失败处理函数  
				alertMsg
						.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');
			},
			success : function(data) { //请求成功后处理函数。    
				zNodes = data; //把后台封装好的简单Json格式赋给treeNodes
			}
		});
// 初始调用
$(document).ready(function() {
	//布局
		$("#layout1").ligerLayout( {
			leftWidth : 180
		});
		var t = $("#deptTree_sy0120");
		t = $.fn.zTree.init(t, setting, zNodes);
		var nodes = t.getCheckedNodes();
		if (nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				if (i == 0) {
					document.getElementById("deptNos").value = nodes[i].DEPTNO;
				} else {
					document.getElementById("deptNos").value += ","
							+ nodes[i].DEPTNO;
				}
			}
		} else {
			document.getElementById("deptNos").value = "";
		}
		if (initSpecialParam == "special") {
			document.getElementById("EMPID").className = "";
		}
	});
function addCpnyIdEditCodeParamView(cpnyId) {
	/**
		$.each($('a',$('#parentTree')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
			$(value).attr("href",backHref);
		});*/
	var hrefUrl = document.getElementById("childLink").href;
	var hrefArr = hrefUrl.split("CPNY_ID");
	var newUrl = hrefArr[0] + "CPNY_ID=" + cpnyId.value;
	document.getElementById("childLink").href = newUrl;

	document.getElementById("childLink").click();

}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {
	var target, code, tag;
	if (!event) {
		event = window.event; //针对ie浏览器  
		target = event.srcElement;
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "TEXTAREA") {
				return true;
			} else {
				return false;
			}
		}
	} else {
		target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "INPUT") {
				return false;
			} else {
				return true;
			}
		}
	}
};
function validateCallbackSy0120_update(form, callback) {
	var $form = $(form);

	if (!$form.valid()) {
		return false;
	}
	if (document.getElementById("SPECIAL_PARAM")
			&& document.getElementById("SPECIAL_PARAM").value != "special") {

		if (document.getElementById("personId")) {
			if (document.getElementById("personId").value == "") {
				document.getElementById("PERSON_ID_EXSIT").innerHTML = "请设置员工!";
				return false;
			}
		}
		if (submitFlag == "Y") {
			return false;
		}
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
}
</script>
<div class="pageContent">
	<form method="post" action="/sys/rightsManagement/updateLoginUserInfo"
		class="pageForm required-validate"
		onsubmit=" createTreeJsonData(); return validateCallbackSy0120_update(this, navTabAjaxDone)">
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!--提交-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>

		<div class="pageFormContent nowrap" layoutH="80">

			<dl>
				<dt>
					<c:if test="${loginUserInfo.SPECIAL_PARAM !='special'}">
						<spring:message code="public.title.empId" />
						<!--工号-->/
				    <spring:message code="public.title.name" />
						<!--姓名-->
					</c:if>
					(
					<spring:message code="sys.rights.title.userName" />
					<!--用户名-->
					):
				</dt>
				<dd>
					<c:if test="${loginUserInfo.SPECIAL_PARAM !='special'}"> 
				${loginUserInfo.EMPID}/${loginUserInfo.LOCAL_NAME}</c:if>
					(${loginUserInfo.USER_NAME})
					<input type="hidden" id="USERNO" name="USERNO"
						value="${loginUserInfo.USER_NO}" />
					<input type="hidden" id="USER_NO" name="USER_NO"
						value="${loginUserInfo.USER_NO}" />
				</dd>
			</dl>


			<dl id="loginUserEmp">
				<dt>
					<spring:message code="sys.rights.title.employee" />
					<!--员工-->
					:
				</dt>
				<dd>
					<input id="EMPID" name="EMP_ID" type="text" class="required"
						value="${loginUserInfo.EMPID}" size="10"
						onkeydown="submitKeyClick_sy0130_update(this,'','','${request_navTabId}',event)" />
					<span id="affirmorName">${loginUserInfo.LOCAL_NAME}&nbsp;</span>
					<span id="PERSON_ID_EXSIT" style="color: red">&nbsp;</span>
					<input type="hidden" id="personId" name="PERSON_ID"
						value="${loginUserInfo.PERSON_ID}">
					<a id="onck" name="onck" href="" lookupGroup="person"></a><span
						onclick="clearInput();" style="cursor: hand"><!--清空--><spring:message code="sys.viewLoginUser.QINGKONG.b" /></span>
				</dd>
			</dl>

			<dl id="loginPasswordEmp">
				<dt>
					<!--密码--><spring:message code="sys.rights.title.password" />
					:
				</dt>
				 <dd>
				 	<input type="hidden" name="PERSON_ID_ID" value="${loginUserInfo.PERSON_ID}">
					<input type="text" name="NEW_PS" value="${loginUserInfo.PASSWORD}"   />
					
					</dd> 
					 
				 
			</dl>
			<dl>
				<dt>
					<spring:message code="sys.rights.title.specialParam" />
					<!--特殊参数-->
					:
				</dt>
				<dd>
					<script>
function changeCombox(value) {
	if (value != 'special') {
		document.getElementById("loginUserDeptTree").style.display = "none";
		document.getElementById("EMPID").className = "required";
		//document.getElementById("loginUserEmp").style.display="";
		//document.getElementById("personId").value="${loginUserInfo.PERSON_ID}";
		//document.getElementById("affirmorName").innerHTML="${loginUserInfo.LOCAL_NAME}";
	} else {
		document.getElementById("loginUserDeptTree").style.display = "";
		document.getElementById("EMPID").className = "";
		//document.getElementById("loginUserEmp").style.display="none";
		//document.getElementById("personId").value="";
		//document.getElementById("affirmorName").innerHTML="&nbsp;";
	}
}
function clearInput() {
	document.getElementById("personId").value = "";
	document.getElementById("EMPID").value = "";
	document.getElementById("affirmorName").innerHTML = "&nbsp;";
}
</script>
					<select class="combox" name="SPECIAL_PARAM" id="SPECIAL_PARAM"
						onchange="changeCombox(this.value)">
						<option value="general"
							<c:if test="${loginUserInfo.SPECIAL_PARAM eq 'general'}">selected</c:if>>
							<spring:message code="sys.rights.title.commonEmployee" />
							<!--普通用户-->
						</option>
						<option value="manager"
							<c:if test="${loginUserInfo.SPECIAL_PARAM eq 'manager'}">selected</c:if>>
							<spring:message code="sys.rights.title.adminstrator" />
							<!--管理者-->
						</option>
						<option value="special"
							<c:if test="${loginUserInfo.SPECIAL_PARAM eq 'special'}">selected</c:if>>
							<spring:message code="sys.rights.title.specialEmployee" />
							<!--特殊用户-->
						</option>
					</select>
					<input type="hidden" name="deptNos" id="deptNos" value="" />
				</dd>
			</dl>

			<dl style="height: 120px">
				<dt>
					<spring:message code="sys.rights.title.privilegeGroup" />
					<!--权限组-->
					:
				</dt>
				<dd>

					<c:if test="${USER_NAME eq 'admin'}">
						<c:forEach items="${loginUserInfoRolesGroupList}" var="rolesGroup">
							<input type="checkbox" name="SCREEN_GRANT_NO"
								value="${rolesGroup.ROLE_GROUP_NO}"
								<c:if test="${ rolesGroup.CHECKED == 1}">checked=true</c:if> />${rolesGroup.GROUPNAME}&nbsp;&nbsp;
                		</c:forEach>
					</c:if>
					<c:if test="${USER_NAME ne 'admin'}">
						<c:if test="${isSuperHrUser eq '1'}">
							<c:forEach items="${loginUserInfoRolesGroupList}" var="rolesGroup">
								<c:if test="${ rolesGroup.GROUPNAME ne '超级用户担当'}">
									<input type="checkbox" name="SCREEN_GRANT_NO"
										value="${rolesGroup.ROLE_GROUP_NO}"
										<c:if test="${ rolesGroup.CHECKED == 1}">checked=true</c:if>
										 />${rolesGroup.GROUPNAME}&nbsp;&nbsp;
								</c:if>
	                		</c:forEach>
						</c:if>
						<c:if test="${isSuperHrUser ne '1'}">
							<c:forEach items="${loginUserInfoRolesGroupList}" var="rolesGroup">
								<c:if test="${ rolesGroup.GROUPNAME ne '超级用户担当' and 
									rolesGroup.GROUPNAME ne '工资担当' and 
									rolesGroup.GROUPNAME ne '保险担当' and 
									rolesGroup.GROUPNAME ne '总部查看专用' and 
									rolesGroup.GROUPNAME ne '超级人事担当-Hub'}">
									<input type="checkbox" name="SCREEN_GRANT_NO"
										value="${rolesGroup.ROLE_GROUP_NO}"
										<c:if test="${ rolesGroup.CHECKED == 1}">checked=true</c:if>
										 />${rolesGroup.GROUPNAME}&nbsp;&nbsp;
								</c:if>
	                		</c:forEach>
						</c:if>
					</c:if>
					<!--
	            <c:forEach items="${loginUserInfoRolesGSODList}" var="rolesGSOD">
	           
	           				${rolesGSOD.ROLE_GSOD_NAME }
	            </c:forEach>
	             
	            -->
				</dd>
			</dl>
			<%-- <dl style="height:130px">
				<dt style="height:130px" ><spring:message code="pa.salary.canShu.renyuanleixing.zu"/> </dt>
				<dd style="height:130px">
					 
					   
								<table width="100%">
				    			 <tr align="center" width="100%"> 
							       <c:forEach items="${empTypeCodeList}" var="vList" varStatus="i">
							             <c:choose>
							              	<c:when test="${i.count % 2 == 0}">
									           <td width="25%" align="left">
									              <input name="isChecked" id="isChecked_${vList.EMP_TYPE_CODE}" value="${vList.EMP_TYPE_CODE}"  type="checkbox" style="border:0px"
									              <c:forEach items="${statisticList}" var="sList" varStatus="j">
									              		<c:if test="${sList.EMP_TYPE_CODE eq vList.EMP_TYPE_CODE}">
									              			checked=true
									              		</c:if>
									              </c:forEach>
									              />
									              	${vList.EMP_TYPE_NAME}
									            </td>	
										    </tr>
											</c:when>
							  				<c:otherwise>
										         <td width="25%" align="left">
										           <input name="isChecked" id="isChecked_${vList.EMP_TYPE_CODE}" value="${vList.EMP_TYPE_CODE}"  type="checkbox" style="border:0px"
										           <c:forEach items="${statisticList}" var="sList" varStatus="j">
									              		<c:if test="${sList.EMP_TYPE_CODE eq vList.EMP_TYPE_CODE}">
									              			checked=true
									              		</c:if>
									              </c:forEach>
										           />
									              		${vList.EMP_TYPE_NAME}
										          </td>		  							
							  				</c:otherwise>
								      </c:choose>	
									</c:forEach>
					    	     </table>
 
	            </dd>
			</dl> --%>


			<dl id="loginUserDeptTree"
				<c:if test="${loginUserInfo.SPECIAL_PARAM !='special'}"> style="display:none"</c:if>>
				<dt>
					<spring:message code="sys.rights.title.deptTree" />
					<!--部门树-->
					:
				</dt>
				<dd>
					<ul id="deptTree_sy0120" class="ztree"></ul>
				</dd>
			</dl>
		</div>

		<input id="jsonData" name="jsonData" type="hidden" value="" />

		<script type="text/javascript">
function createTreeJsonData() {
	var ids = document.getElementsByName("SCREEN_GRANT_NO");
	for ( var i = 0; i < ids.length; i++) {
		if (ids[i].checked) {
			ids[i].disabled = false;
		}
	}

	var jsonData = '[';

	$("#loginUserDept a").each(function(index) {

		if ($(this).parent().find("div.checked").size() > 0) {
			if (jsonData.length > 1) {
				jsonData += ',{'
			} else {
				jsonData += '{'
			}
			jsonData += ' "DEPTNO": "' + $(this).attr("tvalue") + '", ';
			jsonData += ' "USERNO": ' + '${loginUserInfo.USER_NO}';
			jsonData += '}';

		}
	});

	jsonData += ']';

	$('#jsonData').attr('value', jsonData);
}
</script>
	</form>
</div>