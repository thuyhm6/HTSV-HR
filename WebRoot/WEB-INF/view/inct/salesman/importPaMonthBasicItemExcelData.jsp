<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><!-- EXCEL导入 --><spring:message
		code="ar.addempshift.title.excelimport" /></title>
<script type="text/javascript">
	function uploadExcel(form, callback) {

		var imgpath = document.getElementById("filename").value;
		var imgPostfix = imgpath.split(".");

		if (imgpath != "") {
			if (imgPostfix[imgPostfix.length - 1] == "xls") {

				var $form = $(form), $iframe = $("#callbackframe");
				if (!$form.valid()) {
					return false;
				}

				var url = $form.attr("action");
				url = url + (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
				$form.attr("action", url.replace(/\\$/g, "&"));
				if ($iframe.size() == 0) {
					$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
					$form.appendTo($iframe);

				}
				if (!form.ajax) {
					$form
							.append('<input type="hidden" name="ajax" value="1" />');
				}
				_iframeResponse($iframe[0], function(data) {
					alertMsg.info(data.message);
					if (data.statusCode == 200) {
						$.pdialog.closeCurrent();
						//打开excel导入结果画面
						var url = data.forwardUrl;
						if (typeof($("#searchForm_"+ data.navTabId +"_aa")  != 'undefined' )){
							url = url + (url.indexOf('?') == -1 ? "?" : "&")
								+ $("#searchForm_"+ data.navTabId +"_aa").serialize();
						}						
						$("#importExcel_" + data.navTabId).attr('href', url);
						$("#importExcel_" + data.navTabId).click();
					}
				});

			} else {
				//格式错误，请正确选择excel格式。
				alert("<spring:message code='ar.alert.message.excelimport.typeerror'/>");
			}
		} else {
			//上传文件不能为空！
			alert("<spring:message code='ar.alert.message.excelimport.fillnull'/>");
		}
	}

	function getExcelImportResult() {
		//需要通过两次编码  
		//var loginUser = encodeURI($("#loginUser").val());  
		//loginUser = encodeURI(loginUser);  		
	}
</script>
</head>
<body>
	<center>
		<div>
			<a id="errorExcel" href="#" target="dialog" mask="true"></a>
			<form id="addimgform" method="post"
				action="/pa/excelImport${importFunName}?id=${id}&DISTINCT_FIELD=${DISTINCT_FIELD}"
				enctype="multipart/form-data" class="pageForm required-validate"
				onsubmit="return uploadExcel(this,dialogAjaxDone);"
				target="callbackframe">
				<input type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="${paramMap.PAY_AREA_CD}" />
				<input type="hidden" id="ACCRUAL_YN" name="ACCRUAL_YN" value="${paramMap.ACCRUAL_YN}" />
				<table width="100%">
					<tr>
						<td style="text-align: center; padding: 5px"><font color=red>
								<!-- 提示：目前只支持以xls后缀结尾的excel格式（2003版本） --> <spring:message
									code="ar.alert.message.excelimport.friendasu" />
						</font></td>
					</tr>
					<tr>
						<td style="text-align: center; padding: 5px">&nbsp;&nbsp;<input
							type="file" name="filename" id="filename" /> <iframe
								id='callbackframe' name='callbackframe' src='about:blank'
								style='display: none'></iframe>
						</td>
						<td style="text-align: center; padding: 5px">
							<div class="buttonActive" style="text-align: center;">
								<div class="buttonContent" style="text-align: center;">
									<button type="submit" style="text-align: center;">
										<spring:message code="submit" />
										<!-- 提交 -->
									</button>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</center>
</body>
</html>