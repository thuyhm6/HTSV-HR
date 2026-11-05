CREATE OR REPLACE PACKAGE "PKG_PA_WORK_FLOW" AUTHID CURRENT_USER IS
  /******************************************************************************
     NAME:       PKG_PA_WORK_FLOW
     PURPOSE:    ??????

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2015-9-15    esprince       1. Created this package body.
  /******************************************************************************/

  /************************
  ??????
  *************************/
  procedure PA_WORKFLOW_EXECUTE(IN_PAY_SCHEDULE_NO IN NUMBER,
                                IN_ADMIN_ID        IN VARCHAR2,
                                IN_ADMIN_IP        IN VARCHAR2,
                                IN_CPNY_ID         IN VARCHAR2,
                                IN_EXEC_TYPE       IN VARCHAR2,
                                V_RETURN           IN OUT VARCHAR2);

  /********************************
      ?????????
  ********************************/
  procedure PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO IN NUMBER,
                                IN_ADMIN_ID        IN VARCHAR2,
                                IN_ADMIN_IP        IN VARCHAR2,
                                IN_CPNY_ID         IN VARCHAR2,
                                IN_EXEC_TYPE       IN VARCHAR2,
                                V_RETURN           IN OUT VARCHAR2);

  /************************
  ??????
  *************************/
  procedure PA_CREATE_PAY_OBJ(IN_PAY_SCHEDULE_NO IN NUMBER,
                              IN_ADMIN_ID        IN VARCHAR2,
                              IN_ADMIN_IP        IN VARCHAR2,
                              IN_CPNY_ID         IN VARCHAR2,
                              V_RETURN           IN OUT VARCHAR2);

  /************************
  ??????????
  *************************/
  procedure PA_CHECK_IS_LOCK(IN_PAY_SCHEDULE_NO IN NUMBER,
                              V_RETURN           OUT NUMBER);

end;

 

 
/
CREATE OR REPLACE PACKAGE BODY "PKG_PA_WORK_FLOW" IS
  /******************************************************************************
     NAME:       PKG_PA_WORK_FLOW
     PURPOSE:    ??????

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2015-9-15    esprince       1. Created this package body.
  /******************************************************************************/
  /************************
  工资计算流程
  *************************/
  procedure PA_WORKFLOW_EXECUTE(IN_PAY_SCHEDULE_NO IN NUMBER,
                                IN_ADMIN_ID        IN VARCHAR2,
                                IN_ADMIN_IP        IN VARCHAR2,
                                IN_CPNY_ID         IN VARCHAR2,
                                IN_EXEC_TYPE       IN VARCHAR2,
                                V_RETURN           IN OUT VARCHAR2) IS
  V_RETURN_VAL1                 VARCHAR2(1000);
  V_RETURN_VAL2                 VARCHAR2(1000);
  V_AR_START_DATE               VARCHAR2(10);
  V_AR_END_DATE                 VARCHAR2(10);
  V_TEMP_FLAG                    NUMBER;
  V_TEMP_FLAG2                    NUMBER;
  BEGIN
    SELECT COUNT(PPS.PAY_SCHEDULE_NO)INTO V_TEMP_FLAG2
    FROM PA_PAY_SCHEDULE PPS
    WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO 
    AND TO_CHAR(PPS.PAY_DATE,'YYYY/MM/DD')<'2015/12/31';
    
    SELECT TO_CHAR(PPS.AR_END_DATE,'YYYY/MM/DD'), TO_CHAR(PPS.AR_START_DATE,'YYYY/MM/DD') INTO V_AR_END_DATE, V_AR_START_DATE
    FROM PA_PAY_SCHEDULE PPS
    WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
    
    IF V_TEMP_FLAG2 = 1 THEN
      V_RETURN := 'Dữ liệu trước ngày 01/01/2016 không thể tạo lần nữa!' ;--2016-01-01??????????,??????
    ELSE
      IF IN_EXEC_TYPE = 'createPaObj' THEN
        --添加工资对象
        PA_CHECK_IS_LOCK(IN_PAY_SCHEDULE_NO,V_TEMP_FLAG);
        IF V_TEMP_FLAG = 1 THEN
          V_RETURN := 'Lương tháng này đã chốt, không thể tạo đối tượng!'; --????????,??????!
        ELSE
          PA_CREATE_PAY_OBJ(IN_PAY_SCHEDULE_NO,
                                IN_ADMIN_ID,
                                IN_ADMIN_IP,
                                IN_CPNY_ID,
                                V_RETURN);
        END IF;
      ELSIF IN_EXEC_TYPE = 'arLockYes' THEN
          --Lock this attendance month
          UPDATE PA_PAY_SCHEDULE PPS SET PPS.AR_LOCK_FLAG = 1 WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
          UPDATE AR_DETAIL_HTSV SET LOCK_YN = 'Y' WHERE  LOCK_YN = 'N' AND AR_DATE_STR <= V_AR_END_DATE;
          V_RETURN := 'Công tháng đã chốt, xin thực hiện tính công tháng và tính lương tháng!'; --??????????,???????????
      ELSIF IN_EXEC_TYPE = 'arLockNo' THEN
            --Unlock this attendance month
            UPDATE PA_PAY_SCHEDULE PPS SET PPS.AR_LOCK_FLAG = 0 WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
            UPDATE AR_DETAIL_HTSV SET LOCK_YN = 'N' WHERE  LOCK_YN = 'Y' AND AR_DATE_STR BETWEEN V_AR_START_DATE AND V_AR_END_DATE;
          V_RETURN := 'Công tháng đã mở, tốt nhất nên chốt công tước khi tính công và lương tháng'; --????????????,????????????????
      ELSIF IN_EXEC_TYPE = 'arMonthCal' THEN
        --This attendance month is lock?
        PA_CHECK_IS_LOCK(IN_PAY_SCHEDULE_NO,V_TEMP_FLAG);
        IF V_TEMP_FLAG = 1 THEN
          V_RETURN := 'Lương tháng này đã chốt, không thẻ tạo đối tượng!'; --????????,??????!
        ELSE
            --This month of payment object is create?
            SELECT COUNT(PWF.PA_WORK_FLOW_NO)INTO V_TEMP_FLAG
            FROM PA_WORK_FLOW PWF
            WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
            AND PWF.OBJ_CREATE_FLAG = 1;
            
            IF V_TEMP_FLAG = 0 THEN
                V_RETURN := 'Chưa tạo đối tượng nhận lương tháng này, không thể tổng hợp chấm công!'; --?????????,??????!
            ELSE
              ar_month_cal_p(IN_PAY_SCHEDULE_NO,IN_CPNY_ID,IN_ADMIN_ID,IN_ADMIN_IP,V_RETURN_VAL1);
              --Update workflow status
              PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO,
                           IN_ADMIN_ID,
                           IN_ADMIN_IP,
                           IN_CPNY_ID,
                           'arMonthCal',
                           V_RETURN_VAL2);
              V_RETURN := V_RETURN_VAL1 || '  ' || V_RETURN_VAL2 ;
            END IF;
        END IF;
      ELSIF IN_EXEC_TYPE = 'paMonthCal' THEN
        --Payment is lock?
        PA_CHECK_IS_LOCK(IN_PAY_SCHEDULE_NO,V_TEMP_FLAG);
        IF V_TEMP_FLAG = 1 THEN
          V_RETURN := 'Lương tháng này đã chốt, không thể tạo đối tượng!'; --????????,??????!
        ELSE
            --This month of attendace is exeute
            SELECT COUNT(PWF.PA_WORK_FLOW_NO)INTO V_TEMP_FLAG
            FROM PA_WORK_FLOW PWF
            WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
            AND PWF.AR_MONTH_CAL_FLAG = 1;
            
            IF V_TEMP_FLAG = 0 THEN
                V_RETURN := 'Chưa tổng hợp công tháng này, không thể tính lương!'; --???????,??????!
            ELSE
              pa_month_cal_p(IN_PAY_SCHEDULE_NO,IN_ADMIN_ID,IN_CPNY_ID,'NONE',V_RETURN_VAL1);
              --Update workflow status
              PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO,
                           IN_ADMIN_ID,
                           IN_ADMIN_IP,
                           IN_CPNY_ID,
                           'paMonthCal',
                           V_RETURN_VAL2);
              V_RETURN := V_RETURN_VAL1 || '  ' || V_RETURN_VAL2 ;
            END IF;
        END IF;
      ELSIF IN_EXEC_TYPE IN ( 'paConfirm','paOpen','paUnConfirm','paUnOpen') THEN
            IF IN_EXEC_TYPE = 'paConfirm'THEN
              --Payment isn't execute
              SELECT COUNT(PWF.PA_WORK_FLOW_NO)INTO V_TEMP_FLAG
              FROM PA_WORK_FLOW PWF
              WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
              AND PWF.PA_CAL_FLAG = 1;
              IF V_TEMP_FLAG = 0 THEN
                V_RETURN := 'Chưa tính lương tháng này, không thẻ chốt!'; --???????,??????!
              END IF;
            ELSIF IN_EXEC_TYPE = 'paOpen'THEN
              --Payment isn't execute
              SELECT COUNT(PWF.PA_WORK_FLOW_NO)INTO V_TEMP_FLAG
              FROM PA_WORK_FLOW PWF
              WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
              AND PWF.PA_CONFIRM_FLAG = 1;
              IF V_TEMP_FLAG = 0 THEN
                V_RETURN := 'Chưa tính lương tháng này, không thẻ hủy chốt!'; --???????,????????!
              END IF; 
            ELSIF IN_EXEC_TYPE = 'paUnConfirm'THEN
              --Payment is open
              SELECT COUNT(PWF.PA_WORK_FLOW_NO)INTO V_TEMP_FLAG
              FROM PA_WORK_FLOW PWF
              WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
              AND PWF.PA_OPEN_FLAG = 0;
              IF V_TEMP_FLAG = 0 THEN
                V_RETURN := 'Đã mở lương tháng này, không thể hủy chốt!'; --???????,????????!
              END IF; 
            ELSIF IN_EXEC_TYPE = 'paUnOpen'THEN
              V_TEMP_FLAG := 1;
            END IF;
            IF V_TEMP_FLAG > 0 THEN
              --Update workflow status
              PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO,
                           IN_ADMIN_ID,
                           IN_ADMIN_IP,
                           IN_CPNY_ID,
                           IN_EXEC_TYPE,
                           V_RETURN_VAL2);
              V_RETURN := V_RETURN_VAL1 || '  ' || V_RETURN_VAL2 ;
            END IF;
      END IF;
  END IF;
  EXCEPTION
    WHEN OTHERS THEN
      V_RETURN := SQLCODE ||
               '--ERROR: PKG_PA_WORK_FLOW.PA_WORKFLOW_EXECUTE -- ' ||
               SQLERRM;
      ROLLBACK;
  END;

  /********************************
      Update workflow status
  ********************************/
  procedure PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO IN NUMBER,
                                IN_ADMIN_ID        IN VARCHAR2,
                                IN_ADMIN_IP        IN VARCHAR2,
                                IN_CPNY_ID         IN VARCHAR2,
                                IN_EXEC_TYPE       IN VARCHAR2,
                                V_RETURN           IN OUT VARCHAR2)IS
  V_FLOW_STEP NUMBER := 0;
  BEGIN
    --??????
    IF IN_EXEC_TYPE = 'createPaObj' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.OBJ_CREATE_FLAG = 1
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 1 ;
    ELSIF IN_EXEC_TYPE = 'arMonthCal' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.AR_MONTH_CAL_FLAG = 1
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 2 ;
    ELSIF IN_EXEC_TYPE = 'paMonthCal' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.PA_CAL_FLAG = 1
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 3 ;
    ELSIF IN_EXEC_TYPE = 'paConfirm' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.PA_CONFIRM_FLAG = 1
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 4 ;
    ELSIF IN_EXEC_TYPE = 'paOpen' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.PA_OPEN_FLAG = 1
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
      UPDATE PA_PAY_SCHEDULE PPS
         SET PPS.PA_LOCK_FLAG = 1,
         PPS.CONFIRM_DATE = SYSDATE,PPS.CONFIRM_BY=IN_ADMIN_ID,PPS.CONFIRM_IP=IN_ADMIN_IP
       WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 5 ;
    ELSIF IN_EXEC_TYPE = 'paUnConfirm' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.PA_CONFIRM_FLAG = 0
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 6 ;
    ELSIF IN_EXEC_TYPE = 'paUnOpen' THEN
      UPDATE PA_WORK_FLOW PWF
         SET PWF.PA_OPEN_FLAG = 0
       WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
      UPDATE PA_PAY_SCHEDULE PPS
         SET PPS.PA_LOCK_FLAG = 0,
         PPS.RELIEVE_DATE = SYSDATE,PPS.RELIEVE_BY=IN_ADMIN_ID,PPS.RELIEVE_IP=IN_ADMIN_IP
       WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
       V_FLOW_STEP := 7 ;
    END IF;
    --Add work flow
    INSERT INTO PA_WORK_FLOW_RECORDS(PA_WORK_FLOW_RECORDS_NO,PA_WORK_FLOW_NO,FLOW_STEP,CREATE_DATE,CREATED_BY,CREATED_IP)
    SELECT PA_WORK_FLOW_RECORDS_SEQ.NEXTVAL,PWF.PA_WORK_FLOW_NO,V_FLOW_STEP,SYSDATE,IN_ADMIN_ID,IN_ADMIN_IP
    FROM PA_WORK_FLOW PWF
    WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;

    V_RETURN := 'OK';
  EXCEPTION
    WHEN OTHERS THEN
      V_RETURN := SQLCODE ||
               '--ERROR: PKG_PA_WORK_FLOW.PA_UPD_WORKFLOW_STATUS -- ' ||
               SQLERRM;
      ROLLBACK;
  END;

  /************************
  Create payment object
  *************************/
  procedure PA_CREATE_PAY_OBJ(IN_PAY_SCHEDULE_NO IN NUMBER,
                              IN_ADMIN_ID        IN VARCHAR2,
                              IN_ADMIN_IP        IN VARCHAR2,
                              IN_CPNY_ID         IN VARCHAR2,
                              V_RETURN           IN OUT VARCHAR2) IS
  V_HR_STARTDAY_STR VARCHAR2(10) ;--??????
  V_HR_ENDDAY_STR VARCHAR2(10) ;--??????
  V_SALARY_DISTIN_NO VARCHAR2(20) ;--????
  V_SQL_STR VARCHAR2(800) ;--??sql
  V_DATE_STR VARCHAR2(20) ;--??????
  BEGIN
    --Delete payment object 
    DELETE FROM PA_PAY_OBJECT PPO
    WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
    AND PPO.CREATE_TYPE = 0 AND PPO.ACTIVITY = 1;
  
    --Get payment schedule info
    SELECT PPS.SALARY_DISTIN_NO,
             TO_CHAR(PPS.HR_START_DATE,'YYYY-MM-DD'),TO_CHAR(PPS.HR_END_DATE,'YYYY-MM-DD')
    INTO V_SALARY_DISTIN_NO,
           V_HR_STARTDAY_STR,V_HR_ENDDAY_STR
    FROM PA_PAY_SCHEDULE PPS
    WHERE PPS.CPNY_ID = IN_CPNY_ID
    AND PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
    
    --Add payment object 
    IF V_SALARY_DISTIN_NO = '14015882' THEN --Nomal Salary
       V_SQL_STR := 'INSERT INTO PA_PAY_OBJECT(PAY_SCHEDULE_NO,EMPID,PERSON_ID,DEPTNO,CREATE_DATE,CREATED_BY,CREATED_IP,ACTIVITY,INCLUDE_TYPE) ' ;
       V_SQL_STR := V_SQL_STR || ' SELECT ' || IN_PAY_SCHEDULE_NO || ',HRE.EMPID,HRE.PERSON_ID,HRE.DEPTNO,SYSDATE,' ;
       V_SQL_STR := V_SQL_STR || '''' || IN_ADMIN_ID || ''',''' || IN_ADMIN_IP || ''',1,1 ' ;
       V_SQL_STR := V_SQL_STR || ' FROM HR_EMPLOYEE HRE ' ;
       V_SQL_STR := V_SQL_STR || ' WHERE HRE.CPNY_ID = '''|| IN_CPNY_ID ||''' AND HRE.EMPID NOT LIKE ''11111%'' ' ;
       V_SQL_STR := V_SQL_STR || ' AND HRE.DATE_STARTED <= TO_DATE('''||V_HR_ENDDAY_STR||''',''YYYY-MM-DD'') ' ;
       V_SQL_STR := V_SQL_STR || ' AND HRE.EMP_TYPE_CODE <> ''10418'' ' ;
       V_SQL_STR := V_SQL_STR || ' AND ( HRE.DATE_LEFT IS NULL OR HRE.DATE_LEFT > TO_DATE('''||V_HR_STARTDAY_STR||''',''YYYY-MM-DD''))' ; -- AND TO_DATE('''||V_HR_ENDDAY_STR||''',''YYYY-MM-DD'')
       V_SQL_STR := V_SQL_STR || ' AND HRE.EMPID NOT IN( SELECT PPO.EMPID FROM PA_PAY_OBJECT PPO WHERE PPO.PAY_SCHEDULE_NO = '||IN_PAY_SCHEDULE_NO||'  ) ' ;
       
    ELSE--Not Nomal Salary
       V_SQL_STR := 'INSERT INTO PA_PAY_OBJECT(PAY_SCHEDULE_NO,EMPID,PERSON_ID,DEPTNO,CREATE_DATE,CREATED_BY,CREATED_IP,ACTIVITY,INCLUDE_TYPE) ' ;
       V_SQL_STR := V_SQL_STR || ' SELECT ' || IN_PAY_SCHEDULE_NO || ',HRE.EMPID,HRE.PERSON_ID,HRE.DEPTNO,SYSDATE,' ;
       V_SQL_STR := V_SQL_STR || '''' || IN_ADMIN_ID || ''',''' || IN_ADMIN_IP || ''',1,1 ' ;
       V_SQL_STR := V_SQL_STR || ' FROM HR_EMPLOYEE HRE ' ;
       V_SQL_STR := V_SQL_STR || ' WHERE HRE.CPNY_ID = '''|| IN_CPNY_ID ||''' AND HRE.EMPID NOT LIKE ''11111%'' ' ;
       V_SQL_STR := V_SQL_STR || ' AND ( HRE.DATE_LEFT IS NULL OR HRE.DATE_LEFT > TO_DATE('''||V_HR_STARTDAY_STR||''',''YYYY-MM-DD''))' ;
       --V_SQL_STR := V_SQL_STR || ' AND HRE.DATE_STARTED <= TO_DATE('''||V_AR_ENDDAY_STR||''',''YYYY-MM-DD'') ' ;
       V_SQL_STR := V_SQL_STR || ' AND HRE.EMPID NOT IN( SELECT PPO.EMPID FROM PA_PAY_OBJECT PPO WHERE PPO.PAY_SCHEDULE_NO = '||IN_PAY_SCHEDULE_NO||'  ) ' ;
       
    END IF;
     
    --DBMS_OUTPUT.PUT_LINE(V_SQL_STR);

    EXECUTE IMMEDIATE V_SQL_STR ;
               
    --如果当月的正常出勤小于3天
     update PA_PAY_OBJECT pa
    set pa.include_type = 0
  where pa.pay_schedule_no = IN_PAY_SCHEDULE_NO
    and (select to_char(hr.date_started,'YYYY/MM/DD') from hr_employee hr where hr.person_id = pa.person_id) >= replace(V_HR_STARTDAY_STR, '-', '/')
    and (select hr.date_left from hr_employee hr where hr.person_id = pa.person_id) is not null
    and 1 >= (select count(1)
               from ar_detail_htsv ar
              where ar.person_id = pa.person_id
                and ar.ar_date_str >= replace(V_HR_STARTDAY_STR, '-', '/')
                and ar.ar_date_str <= replace(V_HR_ENDDAY_STR, '-', '/')
                and (ar.item_no in ('141439', '141456') or
                    ar.date_type_real = '90000425'));
                    
    update PA_PAY_OBJECT pa
    set pa.include_type = 0
  where pa.pay_schedule_no = IN_PAY_SCHEDULE_NO
    and 0 = (select count(1)
               from ar_detail_htsv ar
              where ar.person_id = pa.person_id
                and ar.ar_date_str >= replace(V_HR_STARTDAY_STR, '-', '/')
                and ar.ar_date_str <= replace(V_HR_ENDDAY_STR, '-', '/')
                and (ar.item_no in ('141439', '141456') or
                    ar.date_type_real = '90000425'));
      
    --Update workflow status
    PA_UPD_WORKFLOW_STATUS(IN_PAY_SCHEDULE_NO,
                       IN_ADMIN_ID,
                       IN_ADMIN_IP,
                       IN_CPNY_ID,
                       'createPaObj',
                       V_RETURN);
    V_RETURN := 'Created finish'; --Created finish
  EXCEPTION
    WHEN OTHERS THEN
      V_RETURN := SQLCODE ||
               '--ERROR: Created Error -- ' || --??????
               SQLERRM;
      ROLLBACK;
  END;
  /********************************
      Check the payment is lock
  ********************************/
  procedure PA_CHECK_IS_LOCK(IN_PAY_SCHEDULE_NO IN NUMBER,
                                V_RETURN           OUT NUMBER)IS
  BEGIN
      SELECT PWF.PA_CONFIRM_FLAG
      INTO V_RETURN
      FROM PA_WORK_FLOW PWF
      WHERE PWF.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
      AND PWF.ACTIVITY = 1;
    EXCEPTION
    WHEN OTHERS THEN
      V_RETURN  := 0;
  END;
END PKG_PA_WORK_FLOW;
/
