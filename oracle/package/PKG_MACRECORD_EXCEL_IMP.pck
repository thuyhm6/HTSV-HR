CREATE OR REPLACE PACKAGE "PKG_MACRECORD_EXCEL_IMP" is

  -- Author  : chenff
  -- Created : 2014-10-13 9:57:16
  -- Purpose :

  procedure PR_VALID_TEMP_MACRECORD_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2);
  procedure PR_IMPORT_MACRECORD_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2);

end PKG_MACRECORD_EXCEL_IMP;

 

 
/
CREATE OR REPLACE PACKAGE BODY "PKG_MACRECORD_EXCEL_IMP" is
  --刷？？？？据？？
  procedure PR_VALID_TEMP_MACRECORD_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2) is
    V_ERR_CODE VARCHAR2(10);
    V_ERR_CNT  NUMERIC(10);
  BEGIN
    V_ERR_CODE := 'OK';
  
    --上？？据重？？据？？
    UPDATE AR_MAC_RECORDS_TEMP A
       SET A.UPLOAD_ERROR_MSG = A.UPLOAD_ERROR_MSG || '[Upload data duplication]', --上？？据重？
           A.RESULT_FLAG      = 'E'
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND (EMPID, R_DATE, R_TIME, DOOR_TYPE, CPNY_ID) IN
           (SELECT B.EMPID, R_DATE, B.R_TIME, B.DOOR_TYPE, B.CPNY_ID
              FROM AR_MAC_RECORDS_TEMP B
             WHERE B.CPNY_ID = V_CPNY_ID
               AND B.UPLOAD_BY = V_PERSON_ID
             GROUP BY B.EMPID, R_DATE, B.R_TIME, B.DOOR_TYPE, B.CPNY_ID
            HAVING COUNT(1) > 1);
  
    --？限？？
    UPDATE AR_MAC_RECORDS_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[This employee is not within your manage.]', --此社？不在？的管？范？？
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS
     (SELECT 1
              FROM HR_EMPLOYEE HR
             WHERE EXISTS (SELECT 1
                      FROM AR_SUPERVISOR_INFO AR
                     WHERE AR.PERSON_ID = V_PERSON_ID
                       AND HR.DEPTNO = AR.DEPTNO));
  
    --？工唯一？？
    UPDATE AR_MAC_RECORDS_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[The employee no is incorrect or empty.]', --？社？不正？或？空
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS (SELECT PERSON_ID
              FROM HR_EMPLOYEE
             WHERE EMPID = A.EMPID
               AND CPNY_ID = A.CPNY_ID);
  
    --刷？？型
    UPDATE AR_MAC_RECORDS_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[Card type error.]', --刷？？型？？
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.DOOR_TYPE NOT IN ('IN','OUT');
       
    --？果返回
    DELETE FROM AR_MAC_RECORDS_HTSV_EAT
                  WHERE rowid not in (SELECT MIN(rowid)
                    FROM AR_MAC_RECORDS_HTSV_EAT 
                   GROUP BY EMPID, AR_DATE_STR);
  
    v_ret := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      v_ret := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_VALIDATE_TEMP_MACRECORD_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_VALID_TEMP_MACRECORD_DATA;

  /******************************************************
  Desc: 刷？？？？据？果？入
  *******************************************************/
  procedure PR_IMPORT_MACRECORD_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2) is
    V_VALID_FLAG VARCHAR2(10);
    V_ERR_CODE   VARCHAR2(10);
  BEGIN
    V_ERR_CODE := '';
    --？据？？
    PR_VALID_TEMP_MACRECORD_DATA(V_PERSON_ID, V_CPNY_ID, V_RET);
    V_VALID_FLAG := V_RET;
    IF V_VALID_FLAG != 'OK' THEN
      V_ERR_CODE := 'NG';
    ELSE
      -- INSERT_BY = 'P': Nhap tay - chua phe duyet (quan ly phe duyet -> 'H')
      EXECUTE IMMEDIATE 'INSERT INTO AR_MAC_RECORDS_' || V_CPNY_ID || ' A
                    ( RECORD_NO,
                      PERSON_ID,
                      R_TIME,
                      DOOR_TYPE,
                      INSERT_BY,
                      REMARK,
                      CREATE_DATE,
                      CREATED_BY,
                      ACTIVITY,
                      CREATED_IP,
                      AR_DATE_STR
                     )
          (SELECT
                    AR_RECORDS_SEQ.NEXTVAL,
                   (select person_id from hr_employee hr where hr.empid = T.empid and hr.cpny_id=T.cpny_id),
                      TO_DATE(R_DATE || R_TIME,''DDMMYYYYHH24:MI''),
                      DOOR_TYPE,
                      ''P'',
                      REMARK,
                       sysdate,
                       ''' || V_PERSON_ID || ''',
                       1,
                       CREATED_IP,
                       TO_CHAR(TO_DATE(T.AR_DATE_STR,''DDMMYYYY''),''YYYY/MM/DD'')
                   FROM AR_MAC_RECORDS_TEMP T
                   WHERE T.CPNY_ID = ''' ||
                        V_CPNY_ID || '''
                   AND T.UPLOAD_BY= ''' ||
                        V_PERSON_ID || '''
                   AND T.RESULT_FLAG = ''N''
                  )';
    
      --？入ar_shift_change
      INSERT INTO AR_SHIFT_CHANGE
        (NO,
         PERSON_ID,
         CPNY_ID,
         DATE_STR,
         ACTIVITY,
         CREATE_DATE,
         END_DATE_STR,
         CHANGE_TYPE,
         CREATED_IP)
        SELECT AR_SHIFT_CHANGE_SEQ.NEXTVAL,
               HR.PERSON_ID,
               HR.CPNY_ID,
               TO_CHAR(TO_DATE(R_DATE || R_TIME,'DDMMYYYYHH24:MI') - 1, 'YYYY/MM/DD'),
               0,
               SYSDATE,
               TO_CHAR(TO_DATE(R_DATE || R_TIME,'DDMMYYYYHH24:MI'), 'YYYY/MM/DD'),
               '刷？信息？入',
               T.CREATED_IP
          FROM AR_MAC_RECORDS_TEMP T, HR_EMPLOYEE HR
         WHERE T.EMPID = HR.EMPID
           AND HR.CPNY_ID = T.CPNY_ID
           AND T.CPNY_ID = V_CPNY_ID
           AND T.UPLOAD_BY = V_PERSON_ID
           AND T.RESULT_FLAG = 'N'
           AND TO_CHAR(TO_DATE(R_DATE || R_TIME,'DDMMYYYYHH24:MI'), 'YYYY-MM-DD') <
               TO_CHAR(SYSDATE, 'YYYY-MM-DD');
      --？据上？成功，？理？前用？下的？？？据
      DELETE FROM AR_MAC_RECORDS_TEMP WHERE UPLOAD_BY = V_PERSON_ID;
      V_ERR_CODE := 'OK';
    END IF;
    V_RET := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      V_RET := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_IMPORT_ACCOUNT_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_IMPORT_MACRECORD_DATA;

end PKG_MACRECORD_EXCEL_IMP;
/
