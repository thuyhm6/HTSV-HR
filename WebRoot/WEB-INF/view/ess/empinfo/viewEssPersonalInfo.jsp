<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

// JavaScript Document
jQuery
		.extend( {

			createUploadIframe : function(id, uri) {
				//create frame
			var frameId = 'jUploadFrame' + id;

			if (window.ActiveXObject) {
				//var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
				if (jQuery.browser.version == "9.0"
						|| jQuery.browser.version == "10.0") {
					var io = document.createElement('iframe');
					io.id = frameId;
					io.name = frameId;
				} else if (jQuery.browser.version == "6.0"
						|| jQuery.browser.version == "7.0"
						|| jQuery.browser.version == "8.0") {
					var io = document.createElement('<iframe id="' + frameId
							+ '" name="' + frameId + '" />');
					if (typeof uri == 'boolean') {
						io.src = 'javascript:false';
					} else if (typeof uri == 'string') {
						io.src = uri;
					}
				}
			} else {
				var io = document.createElement('iframe');
				io.id = frameId;
				io.name = frameId;
			}
			io.style.position = 'absolute';
			io.style.top = '-1000px';
			io.style.left = '-1000px';

			document.body.appendChild(io);

			return io;
		},
		createUploadForm : function(id, fileElementId, data) {
			//create form 
			var formId = 'jUploadForm' + id;
			var fileId = 'jUploadFile' + id;
			var form = jQuery('<form  action="" method="POST" name="' + formId
					+ '" id="' + formId
					+ '" enctype="multipart/form-data"></form>');
			var oldElement = jQuery('#' + fileElementId);
			var newElement = jQuery(oldElement).clone();
			jQuery(oldElement).attr('id', fileId);
			jQuery(oldElement).before(newElement);
			jQuery(oldElement).appendTo(form);

			//add data
			if (data) {
				for ( var i in data) {
					$(
							'<input type="hidden" name="' + i + '" value="'
									+ data[i] + '" />').appendTo(form);
				}
			}
			//set attributes
			jQuery(form).css('position', 'absolute');
			jQuery(form).css('top', '-1200px');
			jQuery(form).css('left', '-1200px');
			jQuery(form).appendTo('body');
			return form;
		},

		ajaxFileUpload : function(s) {
			// TODO introduce global settings, allowing the client to modify them for all requests, not only timeout  
			s = jQuery.extend( {}, jQuery.ajaxSettings, s);
			var id = s.id;
			//var id = s.fileElementId;        
			var form = jQuery.createUploadForm(id, s.fileElementId, s.data);
			var io = jQuery.createUploadIframe(id, s.secureuri);
			var frameId = 'jUploadFrame' + id;
			var formId = 'jUploadForm' + id;

			if (s.global && !jQuery.active++) {
				// Watch for a new set of requests
				jQuery.event.trigger("ajaxStart");
			}
			var requestDone = false;
			// Create the request object
			var xml = {};
			if (s.global) {
				jQuery.event.trigger("ajaxSend", [ xml, s ]);
			}

			var uploadCallback = function(isTimeout) {
				// Wait for a response to come back 
				var io = document.getElementById(frameId);
				try {
					if (io.contentWindow) {
						xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML
								: null;
						xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument
								: io.contentWindow.document;
					} else if (io.contentDocument) {
						xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML
								: null;
						xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument
								: io.contentDocument.document;
					}
				} catch (e) {
					jQuery.handleError(s, xml, null, e);
				}
				if (xml || isTimeout == "timeout") {
					requestDone = true;
					var status;
					try {
						status = isTimeout != "timeout" ? "success" : "error";
						// Make sure that the request was successful or notmodified
						if (status != "error") {
							// process the data (runs the xml through httpData regardless of callback)
							var data = jQuery.uploadHttpData(xml, s.dataType);
							if (s.success) {
								// ifa local callback was specified, fire it and pass it the data
								s.success(data, status);
							}
							;
							if (s.global) {
								// Fire the global callback
								jQuery.event.trigger("ajaxSuccess", [ xml, s ]);
							}
							;
						} else {
							jQuery.handleError(s, xml, status);
						}

					} catch (e) {
						status = "error";
						jQuery.handleError(s, xml, status, e);
					}
					;
					if (s.global) {
						// The request was completed
						jQuery.event.trigger("ajaxComplete", [ xml, s ]);
					}
					;

					// Handle the global AJAX counter
					if (s.global && !--jQuery.active) {
						jQuery.event.trigger("ajaxStop");
					}
					;
					if (s.complete) {
						s.complete(xml, status);
					}
					;

					jQuery(io).unbind();
					setTimeout(function() {
						try {
							jQuery(io).remove();
							jQuery(form).remove();
						} catch (e) {
							jQuery.handleError(s, xml, null, e);
						}
					}, 100);
					xml = null;
				}
				;
			}
			// Timeout checker
			if (s.timeout > 0) {
				setTimeout(function() {
					if (!requestDone) {
						uploadCallback("timeout");
					}
				}, s.timeout);
			}
			try {
				var form = jQuery('#' + formId);
				jQuery(form).attr('action', s.url);
				jQuery(form).attr('method', 'POST');
				jQuery(form).attr('target', frameId);
				if (form.encoding) {
					form.encoding = 'multipart/form-data';
				} else {
					form.enctype = 'multipart/form-data';
				}
				jQuery(form).submit();

			} catch (e) {
				jQuery.handleError(s, xml, null, e);
			}
			/*if(window.attachEvent){
			    document.getElementById(frameId).attachEvent('onload', uploadCallback);
			}
			else{
			    document.getElementById(frameId).addEventListener('load', uploadCallback, false);
			}   */
			jQuery('#' + frameId).load(uploadCallback);
			return {
				abort : function() {
				}
			};

		},

		uploadHttpData : function(r, type) {
			var data = !type;
			data = type == "xml" || data ? r.responseXML : r.responseText;
			// ifthe type is "script", eval it in global context
			if (type == "script") {
				jQuery.globalEval(data);
			}

			// Get the JavaScript object, ifJSON is used.
			if (type == "json") {
				data = r.responseText;
				var start = data.indexOf(">");
				if (start != -1) {
					var end = data.indexOf("<", start + 1);
					if (end != -1) {
						data = data.substring(start + 1, end);
					}
				}
				eval("data = " + data);
			}

			// evaluate scripts within html
			if (type == "html") {
				jQuery("<div>").html(data).evalScripts();
			}

			return data;
		},
		/*handleError: function( s, xml, status, e ) {
			// If a local callback was specified, fire it
			if ( s.error )
				s.error( xml, status, e );

			// Fire the global callback
			if ( s.global )
				jQuery.event.trigger( "ajaxError", [xml, s, e] );
		}*/
		handleError : function(s, xhr, status, e) {
			// If a local callback was specified, fire it
			if (s.error) {
				s.error.call(s.context || s, xhr, status, e);
			}
			// Fire the global callback
			if (s.global) {
				(s.context ? jQuery(s.context) : jQuery.event).trigger(
						"ajaxError", [ xhr, s, e ]);
			}
		}
		});
</script>

<script type="text/javascript">
$(function() {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var birthyear = "${personInfo.DOB}".substring(0, 4);
	var time = parseInt(year) - parseInt(birthyear);
	$('#age').html(time);

});

$(document).ready(function() {

	$("#viewAddrecruitInfo_upload").click(function() {
		var personid = $("#PERSON_ID").val();

		if ($("#viewAddrecruitInfo_uploadPhoto").val() != '') {

			$.ajaxFileUpload( {
				url : '/ess/empinfo/upload?PERSON_ID=' + personid, //用于文件上传的服务器端请求地址
				secureuri : false, //是否需要安全协议，一般设置为false
				fileElementId : 'viewAddrecruitInfo_uploadPhoto', //文件上传域的ID
				dataType : 'text', //返回值类型 一般设置为json
				success : function(data, status) { //服务器成功响应处理函数
					var repObj = $.parseJSON(data);
					$("#newImage").attr("src", repObj.photoPath);
					$("#hiddenFile").css("display","none");
					//申请成功
					alertMsg.info("<spring:message code='ess.empInfo.successful_application' />");
				},
				error : function(data, status, e) {//服务器响应失败处理函数
					//申请成功
					alertMsg.info("<spring:message code='ess.empInfo.successful_application' />");
				}
			});
		} else {
			//请先选择要上传的照片
			alertMsg.info("<spring:message code='ess.empInfo.select_photos_want_upload' />");
		}
	});

});

function openFile() {
	
	$("#hiddenFile").css("display","");

}
function closeFile() {
	
	$("#hiddenFile").css("display","none");

}
</script>
<div class="pageContent">
	<div class="panel">
		<h1>
		<!--工作信息 --><spring:message code="ess.empInfo.work_information" />
		</h1>
		<div>
		<h1>
				<!--基本信息 --><spring:message code="ess.empInfo.essential_information" />
			</h1>
		<%@ include
			file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess1.jsp"%>
			
			</br>
			<h1>
			<!--人事命令--><spring:message code="ess.empInfo.personnel_command" />
			</h1>
			</br>

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">

				<tr>
				<td class="td_title"><spring:message
					code="hrm.empinfo.starter_START_DATE" /> <!--发令日期--></td>
				<td class="td_title"><spring:message
					code="org.title.EXPERIENCE_TYPE_NAME" /> <!--发令区分--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.TRANS_REASON" /> <!--发令原因--></td>
				<td class="td_title"><spring:message
					code="hrm.empinfo.ORG_NAME_LOCAL" /> <!--部门--></td>
				<td class="td_title"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!--主要业务--></td>
			</tr>
				<c:forEach items="${expInsideList}" var="item">
					<tr>
						<td class="td_type">
							${item.START_DATE} 
						</td>
						<td class="td_type">
							${item.TRANS_CODE_NAME}
						</td>
						<td class="td_type">
							${item.TRANS_RESOURCE_NAME}
						</td>
						<td class="td_type">
							${item.DEPTNAME}
						</td>
						<td class="td_type">
 							${item.MAIN_BUSINESS_NAME} 
						</td>
					</tr>
				</c:forEach>
			</table>

			</br>
			<table width="99%">
				<tr>
					<td width="90%">
						<h1>
						<!--经历事项--><spring:message code="ess.empInfo.experience_matters" />
						</h1>

					</td>
					<td >
						<a mask="true" class="buttonActive"
							href="/ess/empinfo/essViewWorkInfo?APPLY_TYPE=1" target="dialog" width="700" height="400">
							<span><!--添加 --><spring:message code="ess.empInfo.insert" /></span> </a>
					</td>
				</tr>
			</table>
			</br>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<tr>
					<td class="td_title">
					<!--公司名称--><spring:message code="ess.empInfo.corporate_name" />
					</td>
					<td class="td_title">
					<!--入职日期--><spring:message code="ess.empInfo.entry_date" />
					</td>
					<td class="td_title">
					<!--离职日期--><spring:message code="ess.empInfo.leaveDate" />
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td class="td_title">
					<!--离职事由--><spring:message code="hr.viewCondSql.title.LIZHIYUANYIN" />
					</td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td class="td_title">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td class="td_title">
						<spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!--月薪-->
					</td>
					</c:if>
				</tr>
				<c:forEach items="${workExperienceList}" var="item">
					<tr>
						<td class="td_type">
							<a mask="true" style="right: 2px; color: blue;" class='add' width="700" height="400"
								href='/ess/empinfo/essViewWorkInfo?APPLY_TYPE=2&WORK_EXPER_NO=${item.WORK_EXPER_NO}'
								target="dialog" title="<spring:message code='ess.empInfo.experience_matters' />">${item.CPNY_NAME}</a><!-- 经历事项 -->
						</td>
						<td class="td_type">${item.START_DATE}</td>
						<td class="td_type">${item.END_DATE}</td>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<td class="td_type">${item.RESIGN_REASON}</td>
						</c:if>
						<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td class="td_type">${item.DEPT_NAME}</td>
						<td class="td_type">${item.PAY_YEAR}</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<div id="hiddenFile"
	style="width: 450px; height: 300px; position: absolute; top: 30%; left: 50%; margin-left: -300px; margin-top: -50px; background: #DDD;z-index: 100;display:none;border-top:1px solid #00F">
	<form id="viewAddrecruitInfoForm5" method="post"
		action="/ess/empinfo/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback(this,navTabAjaxDoneWithForm);">
		<div class="pageContent">
			
			<div style='text-align: center;'>
			</br>
				<img id='newImage' name='newImage'
					src='${personInfo.PHOTO_PATH}' border=0
					style='width: 175px; height: 233px;'>
			</div>
		</div>
		<div class="searchBar" style='border: 0px; margin-top: 20px;'>
			<table class="searchContent"  style="background: #fff">
				<tr>
					<td>
					<!--附件：--><spring:message code="ess.empInfo.enclosure" />
					</td>
					<td>
						<input type="file" id="viewAddrecruitInfo_uploadPhoto" name="file" />
						<input type="hidden" id="PERSON_ID" name="PERSON_ID"
							value="${personInfo.PERSON_ID}" />
						<input type="hidden" id="currentIndex" name="currentIndex"
							value="5" />
					</td>
					<td>
						<a class="buttonActive" id="viewAddrecruitInfo_upload"><span><!--保存 --><spring:message code="ess.message.save" /></span>
						</a>
						
					</td>
					<td><a class="buttonActive" onclick="closeFile()" ><span><!--取消 --><spring:message code="ess.empInfo.cancel" /></span>
						</a></td>
				</tr>
			</table>
		</div>
	</form>
</div>