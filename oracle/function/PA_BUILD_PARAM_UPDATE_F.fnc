CREATE OR REPLACE FUNCTION PA_BUILD_PARAM_UPDATE_F(IN_TABLE_NAME         VARCHAR2, -- Bảng tạm tính lương T_PA_xxx
                                                   IN_CPNY_ID            VARCHAR2,
                                                   IN_SALARY_DISTIN_NO   VARCHAR2,
                                                   IN_PARAM_NO           VARCHAR2,
                                                   IN_PARAM_ITEM_ID      VARCHAR2,
                                                   IN_DISTINCT_FIELD     VARCHAR2,
                                                   IN_DISTINCT_FIELD_2ND VARCHAR2,
                                                   IN_DATA_TYPE          VARCHAR2,
                                                   IN_DEFAULT_VAL        VARCHAR2,
                                                   IN_PRORATE_TYPE       NUMBER,
                                                   IN_PERIOD_START       VARCHAR2, -- YYYY/MM/DD: kỳ hiệu lực tham số
                                                   IN_PERIOD_END         VARCHAR2, -- YYYY/MM/DD
                                                   IN_AR_START           VARCHAR2, -- YYYY/MM/DD: kỳ công (dùng cho PRORATE_TYPE = 3)
                                                   IN_AR_END             VARCHAR2) -- YYYY/MM/DD
  RETURN VARCHAR2 IS
  /******************************************************************************
     NAME:       PA_BUILD_PARAM_UPDATE_F
     PURPOSE:    Sinh câu UPDATE gán giá trị input item (PA_PARAM_DATA / PA_PARAM_DATA_OTHER)
                 vào bảng tạm tính lương, lọc theo START_DATE/END_DATE (thay cho START_MONTH/END_MONTH).
                 Dùng chung cho PA_MONTH_CAL_P và PA_MONTH_CAL_FOR_EMP_P.

                 PA_PARAM_ITEM_PARAM.PRORATE_TYPE - cách gộp các bản ghi giao với kỳ:
                   0 (mặc định) : SUM giá trị các bản ghi (khoản phát sinh: thưởng, khấu trừ...)
                   1            : Lấy bản ghi hiệu lực sau cùng trong kỳ (tham số VARCHAR luôn dùng cách này)
                   2            : Chia theo ngày lịch  = SUM(giá trị * số ngày hiệu lực trong kỳ) / số ngày của kỳ
                   3            : Chia theo ngày công  = SUM(giá trị của bản ghi hiệu lực tại từng ngày công) / tổng ngày công
                                  (ngày công lấy từ AR_DETAIL_HTSV trong kỳ công, cùng điều kiện với PA_CREATE_PAY_OBJ)

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2026/10/14                   1. Created - chuyển tính lương theo tháng sang theo ngày
  ******************************************************************************/
  V_PERIOD_S    VARCHAR2(100) := 'TO_DATE(''' || IN_PERIOD_START || ''',''YYYY/MM/DD'')';
  V_PERIOD_E    VARCHAR2(100) := 'TO_DATE(''' || IN_PERIOD_END || ''',''YYYY/MM/DD'')';
  V_MAX_DATE    VARCHAR2(30) := 'DATE ''9999-12-31''';
  -- Điều kiện ngày công thực tế (giống PKG_PA_WORK_FLOW.PA_CREATE_PAY_OBJ)
  V_WORKDAY     VARCHAR2(300) := '(AR.ITEM_NO IN (''141439'',''141456'') OR AR.DATE_TYPE_REAL = ''90000425'')';
  V_AR_RANGE    VARCHAR2(200) := 'AR.AR_DATE_STR BETWEEN ''' || IN_AR_START || ''' AND ''' || IN_AR_END || '''';
  V_TYPE        NUMBER := NVL(IN_PRORATE_TYPE, 0);
  V_IS_RESIGN   BOOLEAN := NVL(IN_SALARY_DISTIN_NO = '14015083' AND IN_DISTINCT_FIELD = 'PERSON_ID', FALSE);
  V_KEEP        VARCHAR2(300);
  V_AGG         VARCHAR2(4000);
  V_FROM        VARCHAR2(200);
  V_DATE_FILTER VARCHAR2(2000);
BEGIN
  V_FROM := CASE IN_DISTINCT_FIELD WHEN 'PERSON_ID' THEN 'PA_PARAM_DATA' ELSE 'PA_PARAM_DATA_OTHER' END || ' P';

  -- Lương nghỉ việc (14015083): ưu tiên bản ghi "Employee Resign" nếu có
  IF V_IS_RESIGN THEN
    V_KEEP := ' KEEP (DENSE_RANK FIRST ORDER BY CASE WHEN P.REMARK = ''Employee Resign'' THEN 1 ELSE 2 END)';
  END IF;

  -- Mặc định: bản ghi có khoảng hiệu lực giao với kỳ
  V_DATE_FILTER := ' AND P.START_DATE <= ' || V_PERIOD_E ||
                   ' AND NVL(P.END_DATE, ' || V_MAX_DATE || ') >= ' || V_PERIOD_S;

  IF UPPER(IN_DATA_TYPE) LIKE 'VARC%' OR V_TYPE = 1 THEN
    V_AGG := 'MAX(NVL(P.RETURN_VALUE,0)) KEEP (DENSE_RANK LAST ORDER BY ' ||
             CASE WHEN V_IS_RESIGN THEN 'CASE WHEN P.REMARK = ''Employee Resign'' THEN 2 ELSE 1 END, ' END ||
             'P.START_DATE)';
  ELSIF V_TYPE = 2 THEN
    V_AGG := 'SUM(NVL(P.RETURN_VALUE,0) * (LEAST(NVL(P.END_DATE, ' || V_MAX_DATE || '), ' || V_PERIOD_E ||
             ') - GREATEST(P.START_DATE, ' || V_PERIOD_S || ') + 1) / (' ||
             V_PERIOD_E || ' - ' || V_PERIOD_S || ' + 1))' || V_KEEP;
  ELSIF V_TYPE = 3 THEN
    -- Mỗi ngày công được ghép với bản ghi hiệu lực tại ngày đó -> SUM = Σ(giá trị_i * ngày công_i)
    V_FROM := V_FROM || ', AR_DETAIL_HTSV AR';
    V_DATE_FILTER := ' AND AR.PERSON_ID = T.PERSON_ID AND ' || V_WORKDAY || ' AND ' || V_AR_RANGE ||
                     ' AND TO_DATE(AR.AR_DATE_STR,''YYYY/MM/DD'') BETWEEN P.START_DATE AND NVL(P.END_DATE, ' || V_MAX_DATE || ')';
    V_AGG := 'SUM(NVL(P.RETURN_VALUE,0))' || V_KEEP ||
             ' / NULLIF((SELECT COUNT(1) FROM AR_DETAIL_HTSV AR WHERE AR.PERSON_ID = T.PERSON_ID AND ' ||
             V_WORKDAY || ' AND ' || V_AR_RANGE || '), 0)';
  ELSE
    V_AGG := 'SUM(NVL(P.RETURN_VALUE,0))' || V_KEEP;
  END IF;

  RETURN 'UPDATE ' || IN_TABLE_NAME || ' T SET T.' || IN_PARAM_ITEM_ID || ' = NVL((SELECT ' || V_AGG ||
         ' FROM ' || V_FROM ||
         ' WHERE P.CPNY_ID = ''' || IN_CPNY_ID || '''' ||
         CASE WHEN V_IS_RESIGN THEN ' AND NVL(P.IS_APPLY, ''Y'') <> ''N''' END ||
         ' AND T.' || IN_DISTINCT_FIELD ||
         CASE IN_DISTINCT_FIELD WHEN 'PERSON_ID' THEN ' = P.PERSON_ID' ELSE ' = P.FIELD1_VALUE' END ||
         CASE WHEN TRIM(IN_DISTINCT_FIELD_2ND) IS NOT NULL
              THEN ' AND T.' || IN_DISTINCT_FIELD_2ND || ' = P.FIELD2_VALUE' END ||
         ' AND P.PARAM_NO = ' || IN_PARAM_NO ||
         ' AND P.ACTIVITY = 1' ||
         V_DATE_FILTER ||
         '), ''' || IN_DEFAULT_VAL || ''')';
END;
/
