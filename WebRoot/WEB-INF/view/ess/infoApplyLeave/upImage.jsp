<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<%@page import="com.ait.util.StringUtil"%><html>
	<head>
		<title>上传文件</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/css/default.css" rel="stylesheet" type="text/css">
	</head>
	<%
		String File1Url = request.getParameter("File1Url");
		String File1Name = StringUtil.checkNull(request
				.getParameter("File1Name"), "");
		//父级控件第几个
		String index = request.getParameter("index");
		//父级 上传文件 名 控件名称
		String objectName = request.getParameter("objectName");
		//父级 上传文件 路径 控件名称
		String objectUrl = request.getParameter("objectUrl");
		//判断是否上传成功
		String uploadFlag = request.getParameter("uploadFlag");
		request.setAttribute("File1Url", File1Url);
		request.setAttribute("File1Name", File1Name);
		request.setAttribute("index", index);
		request.setAttribute("objectName", objectName);
		request.setAttribute("objectUrl", objectUrl);
		request.setAttribute("uploadFlag", uploadFlag);
	%>
	<script language="JavaScript">
function checkForm() {
	var name1 = '';
	name1 = document.getElementById("file1").value;
	var names;
	if (name1 != "") {
		names = name1.split(".");
		var length = names.length;
		if (names[length - 1] != "docx" && names[length - 1] != "pptx"
				&& names[length - 1] != "xlsx" && names[length - 1] != "xls"
				&& names[length - 1] != "ppt" && names[length - 1] != "doc"
				&& names[length - 1] != "txt" && names[length - 1] != "pdf"
				&& names[length - 1] != "jpg") {
			alert("上传文件类型不符合要求！");
			return false;
		}
	} else {
		alert("请选择文件！");
		return false;
	}
	return true;
}
function submits() {
	if (checkForm()) {
		document.sf.submit();
	}
}

function loadvalue() {
	if (document.getElementById('uploadFlag').value) {
		alert('上传成功');
		window.opener.document.getElementById('${objectUrl}' + '${index}').value = document
				.getElementById('uploadFile').value;
		window.opener.document.getElementById('${objectName}' + '${index}').value = document
				.getElementById('File1Name').value;
		this.close();
	}
}
</script>
<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 onload="loadvalue();">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="27">&nbsp;</td>
						<td height="30" class="admin1" width="711">&nbsp;</td>
						<td width="32">&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table width="100%">
											<form enctype="multipart/form-data" method="post" name="sf"
												action="/controlServlet?operation=upImage&flag=insert&index=${index}&objectName=${objectName}&objectUrl=${objectUrl}">
												<tr>
													<input type="hidden" id="index" name="index" value="${index }" />
													<input type="hidden" id="File1Name" name="File1Name" value="${File1Name }" />
													<input type="hidden" id="objectName" name="objectName" value="${objectName }" />
													<input type="hidden" id="objectUrl" name="objectUrl" value="${objectUrl }" />
													<input type="hidden" id="uploadFlag" name="uploadFlag" value="${uploadFlag }" />
													<input type="hidden" id="uploadFile" name="uploadFile" value="/upload/files/${File1Url }" />
													<td colspan="4" class="font1" style="padding-left: 40px">
														<font color="red"><b>上传文件类型：.xls/ .xlsx/	.txt/ .doc/ .docx/ .ppt/ .pptx/ .pdf/ .jpg ,上传文件不能大于10M</b></font>
													</td>
												</tr>
												<tr><td colspan="4" bgcolor="#ABABAB" height="3"></td></tr>
												<tr><td colspan="4" bgcolor="#ABABAB" height="1"></td></tr>
												<tr>
													<td align=left bgcolor="F2F2F2" width="150" class="font1" style="padding-left: 40px"><b>文件</b></td>
													<td colspan="3" style="padding-left: 40px" class="ir">
														<input type="file" id="file1" name="file1" class="box" size="50">
													</td>
												</tr>
												<tr>
													<td></td>
													<td align="center">
														<input name="确定" type="button" value="确定" onclick="submits();">
													</td>
													<td>
														<input name="重置" type="reset" value="重置">
													</td>
													<td align="right">&nbsp;</td>
												</tr>
											</form>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td valign="bottom">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
