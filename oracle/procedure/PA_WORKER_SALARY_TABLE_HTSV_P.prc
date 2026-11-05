CREATE OR REPLACE PROCEDURE PA_WORKER_SALARY_TABLE_HTSV_P (IN_PAY_SCHEDULE_NO IN NUMBER, --支付计划号
                                                                                      IN_PAY_DATE IN VARCHAR2, --支付日期
                                                                                      IN_HR_END_DATE  IN VARCHAR2--发令结束日期
                                                                                                     )
  AUTHID CURRENT_USER IS
  /******************************************************************************
     NAME:       PA_WORKER_SALARY_TABLE_HTSV_P
     PURPOSE:    HTSV 职级Worker的 基本工资、交通补贴、工龄补助、满勤奖
                     根据员工的入职日期和发令结束日期的获取工龄 使用工龄匹配PA_WORKER_SALARY_TABLE表进行取值

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2018/01/30   Esprince       1. Created this procedure.

  ******************************************************************************/
  VAR_LOG_FLAG       NUMBER :=1;
  -- Kỳ hiệu lực tham số lương (theo ngày) của tháng trả lương - xem PA_GET_PERIOD_DATE_F
  V_PERIOD_START     DATE := PA_GET_PERIOD_DATE_F(TO_CHAR(TO_DATE(IN_PAY_DATE,'YYYY/MM/DD'),'MMYYYY'), 'HTSV', 'S');
  V_PERIOD_END       DATE := PA_GET_PERIOD_DATE_F(TO_CHAR(TO_DATE(IN_PAY_DATE,'YYYY/MM/DD'),'MMYYYY'), 'HTSV', 'E');
  -- Worker工资Table游标
   CURSOR CUS_ITEM IS
     SELECT *
        FROM PA_WORKER_SALARY_TABLE PWST
       WHERE TO_CHAR(TO_DATE(IN_PAY_DATE,'YYYY/MM/DD'),'YYYYMM') BETWEEN
               TO_CHAR(TO_DATE(PWST.START_MONTH,'MM/YYYY'),'YYYYMM') AND
               TO_CHAR(TO_DATE(PWST.END_MONTH,'MM/YYYY'),'YYYYMM')
          AND PWST.ACTIVITY = 1
  ORDER BY PWST.DIVISION;

BEGIN
  EXECUTE IMMEDIATE 'alter session set cursor_sharing = force';
  DBMS_OUTPUT.ENABLE(5000000);
  IF VAR_LOG_FLAG = 1 THEN
     DBMS_OUTPUT.PUT_LINE('IN_PAY_SCHEDULE_NO = '||IN_PAY_SCHEDULE_NO);
     DBMS_OUTPUT.PUT_LINE('IN_PAY_DATE = '||IN_PAY_DATE);
     DBMS_OUTPUT.PUT_LINE('IN_HR_END_DATE = '||IN_HR_END_DATE);
  END IF;
  --清除掉之前的插入过的数据
  DELETE FROM PA_PARAM_DATA PPD
            WHERE PPD.CPNY_ID = 'HTSV'
               AND PPD.CREATED_BY = 'BEFORE_CAL';

  FOR ITEM_ROW IN CUS_ITEM LOOP
    --插入Probation的数据
    IF ITEM_ROW.DIVISION = 1 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入试用基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,2,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 2
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );

      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准试用
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,4,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 4
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 2-3Months 的数据
    ELSIF ITEM_ROW.DIVISION = 2 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) > 0
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 3
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) > 0
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 3
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) > 0
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 3
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) > 0
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 3
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;

    --插入 4-6Months 的数据
    ELSIF ITEM_ROW.DIVISION = 3 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 3
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 6
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 3
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 6
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 3
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 6
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 3
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 6
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 7-12Months 的数据
    ELSIF ITEM_ROW.DIVISION = 4 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 6
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 12
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 6
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 12
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 6
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 12
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 6
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 12
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 1 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 5 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 12
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 24
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 12
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 24
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 12
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 24
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 12
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 24
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 2 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 6 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 24
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 36
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 24
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 36
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 24
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 36
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 24
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 36
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 3 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 7 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 36
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 48
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 36
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 48
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 36
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 48
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 36
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 48
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 4 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 8 THEN
      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 48
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 60
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 48
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 60
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 48
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 60
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 48
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 60
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    --插入 5 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 9 THEN

      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 60
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 60
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 60
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 60
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) < 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;

      --插入 6 year ～ 的数据
    ELSIF ITEM_ROW.DIVISION = 10 THEN

      IF ITEM_ROW.BASIC > 0 THEN
        --插入基本工资标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,1,PPO.PERSON_ID,
                         ITEM_ROW.BASIC,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 1
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.FULL_ATTEND > 0 THEN
        --插入满勤奖标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,7,PPO.PERSON_ID,
                         ITEM_ROW.FULL_ATTEND,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 7
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.TRANSPORTATION > 0 THEN
        --插入交通补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,5,PPO.PERSON_ID,
                         ITEM_ROW.TRANSPORTATION,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 5
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
      IF ITEM_ROW.LONG_ATTENDANCE > 0 THEN
        --插入工龄补贴标准
        INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH,CPNY_ID
                                          ,CREATE_DATE,CREATED_BY,ACTIVITY)
               SELECT PA_PARAM_DATA_SEQ.NEXTVAL,6,PPO.PERSON_ID,
                         ITEM_ROW.LONG_ATTENDANCE,ITEM_ROW.START_MONTH,ITEM_ROW.END_MONTH,
                         'HTSV',SYSDATE,'BEFORE_CAL',1
                  FROM PA_PAY_OBJECT PPO
                 WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
                    AND PPO.PERSON_ID IN(
                        SELECT HRE.PERSON_ID FROM HR_EMPLOYEE HRE WHERE HRE.CPNY_ID = 'HTSV'AND HRE.POST_GRADE_NO IN ('90000325', '90000382')
                        AND MONTHS_BETWEEN(TO_DATE(IN_HR_END_DATE,'YYYY/MM/DD'),NVL(HRE.WORKER_DATE_STARTED, HRE.DATE_STARTED)) >= 72
                    )
                    AND PPO.PERSON_ID NOT IN(
                        SELECT PPD.PERSON_ID FROM PA_PARAM_DATA PPD
                        WHERE PPD.CPNY_ID = 'HTSV'
                           AND PPD.PARAM_NO = 6
                           AND PPD.START_DATE <= V_PERIOD_END
                           AND NVL(PPD.END_DATE, DATE '9999-12-31') >= V_PERIOD_START
                    );
      END IF;
    END IF;
  END LOOP;

  COMMIT;
  EXECUTE IMMEDIATE 'alter session set cursor_sharing = exact';
END;
/
