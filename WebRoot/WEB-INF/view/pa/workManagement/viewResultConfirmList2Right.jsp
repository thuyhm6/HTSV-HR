<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent" style="height: 190px; margin: 0px; padding: 0px;">
	<table class="orderList" width="100%">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					考勤统计种类
				</th>
				<th>
					时长
				</th>

			</tr>
		</thead>


		<c:forEach items="${viewResultConfirmList2Right}" var="item"
			varStatus="i">

			<tr>
				<td class="td_type" width="20%">
					${i.count}
				</td>

				<td class="td_type" width="30%">
					${item.ITEM_NAME}
				</td>
				<td class="td_type" width="25%">
					${item.QUANTITY}
				</td>

			</tr>



		</c:forEach>
	</table>

</div>






