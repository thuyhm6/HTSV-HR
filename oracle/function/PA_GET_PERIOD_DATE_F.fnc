CREATE OR REPLACE FUNCTION PA_GET_PERIOD_DATE_F(IN_MONTH   VARCHAR2, -- Tháng dạng MMYYYY (chấp nhận MM/YYYY, MM-YYYY)
                                                IN_CPNY_ID VARCHAR2,
                                                IN_TYPE    VARCHAR2) -- 'S' = ngày bắt đầu kỳ, 'E' = ngày kết thúc kỳ
  RETURN DATE IS
  /******************************************************************************
     NAME:       PA_GET_PERIOD_DATE_F
     PURPOSE:    Quy đổi "tháng lương" (MMYYYY) sang ngày bắt đầu / kết thúc kỳ lương.
                 Kỳ lương = HR_START_DATE..HR_END_DATE của kế hoạch trả lương thường
                 (SALARY_DISTIN_NO = 14015882) có PAY_DATE nằm trong tháng đó.
                 Không có kế hoạch nào -> lấy ngày đầu tháng / cuối tháng.
                 Dùng cho:
                   - Migrate START_MONTH/END_MONTH cũ sang START_DATE/END_DATE
                   - Trigger TRG_PA_PARAM_DATA_DATE (code cũ còn insert theo tháng)
                   - Xác định kỳ hiệu lực tham số khi tính lương (PA_MONTH_CAL_P...)

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2026/10/14                   1. Created - chuyển tính lương theo tháng sang theo ngày
  ******************************************************************************/
  V_MONTH  DATE;
  V_RESULT DATE;
BEGIN
  IF TRIM(IN_MONTH) IS NULL THEN
    RETURN NULL;
  END IF;

  V_MONTH := TO_DATE(REPLACE(REPLACE(TRIM(IN_MONTH), '/', ''), '-', ''), 'MMYYYY');

  SELECT CASE WHEN IN_TYPE = 'S' THEN MIN(PPS.HR_START_DATE) ELSE MAX(PPS.HR_END_DATE) END
    INTO V_RESULT
    FROM PA_PAY_SCHEDULE PPS
   WHERE PPS.CPNY_ID = IN_CPNY_ID
     AND PPS.ACTIVITY = 1
     AND PPS.SALARY_DISTIN_NO = '14015882' -- Normal Salary
     AND PPS.PAY_DATE >= V_MONTH
     AND PPS.PAY_DATE <  ADD_MONTHS(V_MONTH, 1);

  RETURN TRUNC(NVL(V_RESULT, CASE WHEN IN_TYPE = 'S' THEN V_MONTH ELSE LAST_DAY(V_MONTH) END));
EXCEPTION
  WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20102, 'PA_GET_PERIOD_DATE_F: tháng không hợp lệ [' || IN_MONTH || '] - ' || SQLERRM);
END;
/
