<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function submitContent(){
		$('#batcon').attr('value',$('#repct').val());
		batchAllSubmit();
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<div class="pageFormContent nowrap">
		<table>	
			<tr>			
				<td style="width: 100px;padding-top:5px;">
					决裁批注：
				</td>
			</tr>
			<tr>	
				<td style="width: 100px;padding-top:5px;">
					<input type="text" name="repct" id="repct" value="">
				</td>
			</tr>
		</table>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" onClick="submitContent()">
							<!-- 提交 --><spring:message code="public.title.submit" />
						</button>
					</div>
				</div></li>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<!-- 取消 --><spring:message code="public.title.cancle" />
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
