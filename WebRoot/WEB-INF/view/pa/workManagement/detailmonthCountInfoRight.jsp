<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>




<div class="pageContent" style="margin: 0px; padding: 0px;">
	<table class="table" width="100%" layoutH="450">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailmonthCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '1'}">
				<tr>
					<td class="td_type">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type">
						${item.ITEM_NAME}
					</td>
					<td class="td_type">
						${item.MONTH_ALL}
					</td>

					<td class="td_type">
						${item.FORMULA}
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>

	<table class="table" width="100%" layoutH="450">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailmonthCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '2'}">
				<tr>
					<td class="td_type">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type">
						${item.ITEM_NAME}
					</td>
					<td class="td_type">
						${item.MONTH_ALL}
					</td>

					<td class="td_type">
						${item.FORMULA}
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>

	<table class="table" width="100%" layoutH="450">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailmonthCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '3'}">
				<tr>
					<td class="td_type">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type">
						${item.ITEM_NAME}
					</td>
					<td class="td_type">
						${item.MONTH_ALL}
					</td>

					<td class="td_type">
						${item.FORMULA}
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>
</div>






