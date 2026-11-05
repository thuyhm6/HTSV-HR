CREATE OR REPLACE FUNCTION PA_CHECK_PARAM_OVERLAP_F(IN_PAY_SCHEDULE_NO NUMBER,
                                                    IN_CPNY_ID         VARCHAR2,
                                                    IN_PERSON_ID       VARCHAR2, -- NULL = toàn bộ đối tượng nhận lương
                                                    IN_START_DATE      DATE,
                                                    IN_END_DATE        DATE)
  RETURN VARCHAR2 IS
  /******************************************************************************
     NAME:       PA_CHECK_PARAM_OVERLAP_F
     PURPOSE:    Kiểm tra PA_PARAM_DATA có 2 bản ghi cùng nhân viên + cùng tham số chồng lấn ngày hiệu lực
                 trong kỳ, với các tham số chia theo ngày (PRORATE_TYPE = 2, 3).
                 Chồng lấn -> giá trị bị cộng 2 lần khi tính lương.
                 Trả về NULL nếu hợp lệ, ngược lại trả về thông báo lỗi (tối đa 20 trường hợp).
                 Bỏ qua bản ghi REMARK = 'Employee Resign' (được phép tồn tại song song bản ghi thường).

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2026/10/14                   1. Created
  ******************************************************************************/
  V_MSG VARCHAR2(4000);
BEGIN
  FOR R IN (SELECT EMPID, PARAM_ITEM_ID
              FROM (SELECT DISTINCT HRE.EMPID, PI.PARAM_ITEM_ID
                      FROM PA_PARAM_DATA       A,
                           PA_PARAM_DATA       B,
                           PA_PARAM_ITEM_PARAM C,
                           PA_PARAM_ITEM       PI,
                           PA_PAY_OBJECT       PPO,
                           HR_EMPLOYEE         HRE
                     WHERE A.CPNY_ID = IN_CPNY_ID
                       AND B.CPNY_ID = IN_CPNY_ID
                       AND A.PERSON_ID = B.PERSON_ID
                       AND A.PARAM_NO = B.PARAM_NO
                       AND A.PARAM_DATA_NO < B.PARAM_DATA_NO
                       AND A.ACTIVITY = 1
                       AND B.ACTIVITY = 1
                       AND NVL(A.REMARK, ' ') <> 'Employee Resign'
                       AND NVL(B.REMARK, ' ') <> 'Employee Resign'
                       -- Dữ liệu tự sinh từ PA_WORKER_SALARY_TABLE_* sẽ bị xóa & sinh lại khi tính lương
                       AND NVL(A.CREATED_BY, ' ') <> 'BEFORE_CAL'
                       AND NVL(B.CREATED_BY, ' ') <> 'BEFORE_CAL'
                       -- 2 bản ghi chồng lấn nhau
                       AND A.START_DATE <= NVL(B.END_DATE, DATE '9999-12-31')
                       AND B.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                       -- phần chồng lấn rơi vào kỳ đang tính
                       AND GREATEST(A.START_DATE, B.START_DATE) <= IN_END_DATE
                       AND LEAST(NVL(A.END_DATE, DATE '9999-12-31'), NVL(B.END_DATE, DATE '9999-12-31')) >= IN_START_DATE
                       AND C.PARAM_NO = A.PARAM_NO
                       AND C.CPNY_ID = IN_CPNY_ID
                       AND C.ACTIVITY = 1
                       AND NVL(C.PRORATE_TYPE, 0) IN (2, 3)
                       AND PI.PARAM_ITEM_NO = C.PARAM_ITEM_NO
                       AND PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                       AND PPO.PERSON_ID = A.PERSON_ID
                       AND PPO.ACTIVITY = 1
                       AND PPO.INCLUDE_TYPE = 1
                       AND (IN_PERSON_ID IS NULL OR A.PERSON_ID = IN_PERSON_ID)
                       AND HRE.PERSON_ID = A.PERSON_ID
                     ORDER BY 1, 2)
             WHERE ROWNUM <= 20) LOOP
    V_MSG := V_MSG || R.EMPID || '(' || R.PARAM_ITEM_ID || ') ';
  END LOOP;

  -- PA_PARAM_DATA_OTHER: chồng lấn cùng tham số + cùng FIELD1/FIELD2 (không phụ thuộc nhân viên)
  IF IN_PERSON_ID IS NULL THEN
    FOR R IN (SELECT FIELD1_VALUE, FIELD2_VALUE, PARAM_ITEM_ID
                FROM (SELECT DISTINCT A.FIELD1_VALUE, A.FIELD2_VALUE, PI.PARAM_ITEM_ID
                        FROM PA_PARAM_DATA_OTHER A,
                             PA_PARAM_DATA_OTHER B,
                             PA_PARAM_ITEM_PARAM C,
                             PA_PARAM_ITEM       PI
                       WHERE A.CPNY_ID = IN_CPNY_ID
                         AND B.CPNY_ID = IN_CPNY_ID
                         AND A.PARAM_NO = B.PARAM_NO
                         AND A.FIELD1_VALUE = B.FIELD1_VALUE
                         AND NVL(A.FIELD2_VALUE, '#') = NVL(B.FIELD2_VALUE, '#')
                         AND A.PARAM_DATA_NO < B.PARAM_DATA_NO
                         AND A.ACTIVITY = 1
                         AND B.ACTIVITY = 1
                         AND A.START_DATE <= NVL(B.END_DATE, DATE '9999-12-31')
                         AND B.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                         AND GREATEST(A.START_DATE, B.START_DATE) <= IN_END_DATE
                         AND LEAST(NVL(A.END_DATE, DATE '9999-12-31'), NVL(B.END_DATE, DATE '9999-12-31')) >= IN_START_DATE
                         AND C.PARAM_NO = A.PARAM_NO
                         AND C.CPNY_ID = IN_CPNY_ID
                         AND C.ACTIVITY = 1
                         AND NVL(C.PRORATE_TYPE, 0) IN (2, 3)
                         AND PI.PARAM_ITEM_NO = C.PARAM_ITEM_NO
                       ORDER BY 3, 1, 2)
               WHERE ROWNUM <= 20) LOOP
      V_MSG := V_MSG || R.PARAM_ITEM_ID || '[' || R.FIELD1_VALUE ||
               CASE WHEN R.FIELD2_VALUE IS NOT NULL THEN '/' || R.FIELD2_VALUE END || '] ';
    END LOOP;
  END IF;

  IF V_MSG IS NOT NULL THEN
    RETURN 'Lỗi: dữ liệu tham số lương bị chồng lấn ngày hiệu lực, vui lòng điều chỉnh Ngày bắt đầu/Ngày kết thúc: ' || V_MSG;
  END IF;
  RETURN NULL;
END;
/
