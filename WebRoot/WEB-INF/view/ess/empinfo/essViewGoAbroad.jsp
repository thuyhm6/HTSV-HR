<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
<style>

        h1, h2 {
            text-align: center;
        }
        h1 {
        	font-size: 2rem;
        	font-family: time;
        }
        .content {
            margin: 0 auto;
            max-width: 800px;
        }
        .header, .footer, .center {
            text-align: center;
        }
        .decision {
            margin-top: 20px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        /* Thêm dấu gạch ngang trước mỗi mục */
        ul.custom-list li::before {
            
            padding-right: 10px; /* Khoảng cách giữa dấu và nội dung */
        }
        p, h2, li, strong {
        line-height: 2;
        font-size: 14px;
        }
        .dashed-hr {
            border: 0; /* Xóa border mặc định */
            border-top: 2px dashed #000; /* Tạo đường kẻ ngang nét đứt */
            margin: 20px 0;
        }
        .td-custom {
         text-align: left;font-size: 14px; line-height: 26px;
        }
        
</style>
</head>
<div class="pageHeader">
	<form id="viewRegPersonalTargetForm" onsubmit="return navTabSearch(this);" action="/ess/empinfo/essViewGoAbroad" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="ess.viewMonthDetailConfirmList.QUEDINGYAO.a"/><!--Quyết định--></td>
					<td>
						<select id="viewTmpEmpBatchList_SEQ" name="SEQ">
							<c:forEach items="${viewRegisterInfoList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq SEQ}">selected</c:if>>${result.REGISTER_DATE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="button.search"/><!--查询-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<c:if test="${not empty viewRegisterInfoList}">
		<div class="content">
			<table width="100%">
				<tr height="80px">
					<td width="30%"><img style="width: 100%" src='/resources/css/dwzUI/themes/partner/images/HVV-logo.png'></td>
					<td width="70%" style="text-align: center; ">
						<p style="font-weight: bold">Lot O-2, Que Vo Industrial Zone extended area, Nam Son ward,</p>
			        	<p style="font-weight: bold">Bac Ninh city, Bac Ninh province, Vietnam</p>
		        	</td>
					
				</tr>
			</table>
			<hr class="dashed-hr">
			<table width="100%">
				<tr>
					<td style="text-align: right; font-weight: bold">Date: <span class="dynamic-date-en">Mar 1<sup>st</sup>, 2025</span></td>			
				</tr>
			</table>
	        <div class="center" style="margin: 20px 0">
		        <h1 style="font-weight: bold">THÔNG BÁO TĂNG LƯƠNG</h1>
		        <h1 style="font-weight: bold">SALARY REVIEW ANNOUNCEMENT</h1>
	        </div>
	
	    <ul class="custom-list">
	        <p>- Based on the the policy of Hanwha Vision Vietnam Co., Ltd./ </p>
	        <p style="font-style: italic;">&nbsp;&nbsp;Căn cứ theo chính sách của Công ty TNHH Hanwha Vision Vietnam.</p>
	        <p>- Based on the performance and ability of the concerned-employee./ </p>
	        <p style="font-style: italic;">&nbsp;&nbsp;Xét trên năng lực và việc thực hiện công việc của anh/chị trong năm vừa qua.</p>
	    </ul>

        <div class="decision">
            <h2 style="font-size: 1.5rem">CÔNG TY THÔNG BÁO - COMPANY ANNOUNCE</h2>

            <p><b style="font-weight: bold">Article 1:</b> The Company inform your new will be as follows:/
            <i style="font-style: italic;">Công ty thông báo mức lương mới của anh/ chị như sau:</i></p>

			<table width="100%" style="margin-top: 14px;">
				<tr style="margin-top: 10px;">
					<td width= 20% class="td-custom">Full Name (Họ và tên)</td>
					<td width= 70% class="td-custom"><span style="margin-right: 15px">:</span>${LoginUser.localName}</td>
				</tr>
				<tr>
					<td width= 30% class="td-custom">Employee code (Mã NV)</td>
					<td width= 70% class="td-custom"><span style="margin-right: 15px">:</span>${LoginUser.empID}</td>
				</tr>
				<tr>
					<td width= 30% class="td-custom">Position (Vị trí)</td>
					<td width= 70% class="td-custom"><span style="margin-right: 15px">:</span>${LoginUser.postGradeName}</td>
				</tr>
				<tr>
					<td width= 30% class="td-custom">Department (Phòng)</td>
					<td width= 70% class="td-custom"><span style="margin-right: 15px">:</span>${LoginUser.content}</td>
				</tr>
			</table>
            <p style="margin-top: 15px; font-weight: bold">New monthly salary (unit: VNĐ):</p>
            <p style="font-style: italic;">Mức lương mới (đơn vị tính: VNĐ):</p>

			<table width="100%">
				<c:forEach items="${goAbroadList}" var="pay">
					<tr >
						<td width= 40% class="td-custom">&#9679; Basic salary (Lương cơ bản)</td>
						<td width= 60% class="td-custom"><span style="margin-right: 15px">:</span>${pay.BASIC_SALARY } VND</td>
					</tr>
					<tr>
						<td width= 40% class="td-custom">&#9679; Position Allowance (Phụ cấp vị trí)</td>
						<td width= 60% class="td-custom"><span style="margin-right: 15px">:</span>${pay.POSITION_SALARY } VND</td>
					</tr>
				</c:forEach>
				
			</table>
            <p style="margin-top: 15px;"><b style="font-weight: bold">Article 2: Effective date/ Ngày hiệu lực</b></p>
            <p>The above new salary will be applied from <span class="dynamic-date-en">Mar 1<sup>st</sup>, 2025</span></p>
            <p style="font-style: italic;">Mức lương mới trên được áp dụng kể từ ngày <span class="dynamic-date-vi">01/03/2025</span></p>

            <p>The company will sign a contract appendix on new salary level with each employee</p>
            <p style="font-style: italic;">Công ty sẽ thực hiện ký kết phụ lục hợp đồng về mức lương mới với từng nhân viên</p>
        </div>

    </div>
	</c:if>
	
	<script type="text/javascript">
	$(document).ready(function() {
	    // Hàm kiểm tra và cập nhật ngày
	    function updateDateBySelection() {
	        // Lấy nội dung text của option đang được chọn (ví dụ: "2026-03-01")
	        var selectedText = $("#viewTmpEmpBatchList_SEQ option:selected").text();
	        
	        // Kiểm tra xem chuỗi text có chứa số "2026" hay không
	        if (selectedText.indexOf("2026") !== -1) {
	            $(".dynamic-date-en").html("Mar 1<sup>st</sup>, 2026");
	            $(".dynamic-date-vi").html("01/03/2026");
	        } else {
	            // Trả về 2025 nếu không phải 2026
	            $(".dynamic-date-en").html("Mar 1<sup>st</sup>, 2025");
	            $(".dynamic-date-vi").html("01/03/2025");
	        }
	    }
	
	    // 1. Chạy hàm ngay khi vừa load trang (để xử lý trường hợp data mặc định đã là 2026)
	    updateDateBySelection();
	
	    // 2. Chạy hàm khi người dùng click chọn một đợt khác trong Dropdown
	    $("#viewTmpEmpBatchList_SEQ").change(function() {
	        updateDateBySelection();
	    });
	});
	</script>
</div>