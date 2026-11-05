CREATE OR REPLACE PACKAGE "PKG_WAGEBASE_EXCEL_TEMP" is

  -- Author  : chenff
  -- Created : 2014-09-28 15:45:44
  -- Purpose :

  procedure PR_VALID_TEMP_BASICITEM_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2);
  procedure PR_IMPORT_BASICITEM_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2);

  procedure PR_VALID_TEMP_PAPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_DISTINCT_FIELD IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2);
  procedure PR_IMPORT_PAPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_DISTINCT_FIELD IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2);
   procedure PR_VALID_TEMP_ISPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2);
 procedure PR_IMPORT_ISPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2);

end PKG_WAGEBASE_EXCEL_TEMP;
 

 

 
/
CREATE OR REPLACE PACKAGE BODY "PKG_WAGEBASE_EXCEL_TEMP" is
  --工资基础项目数据验证
  procedure PR_VALID_TEMP_BASICITEM_DATA(V_PERSON_ID IN VARCHAR2,
                                         V_CPNY_ID   IN VARCHAR2,
                                         V_RET       IN OUT VARCHAR2) is
    V_ERR_CODE   VARCHAR2(10);
    V_ERR_CNT    NUMERIC(10);
    E_start_date VARCHAR2(20);
  BEGIN
    V_ERR_CODE := 'OK';

    --上传数据重复数据检查
    UPDATE PA_BASIC_DATA_TEMP A
       SET A.Upload_Error_Msg = A.UPLOAD_ERROR_MSG || '[上传数据重复]',
           A.RESULT_FLAG      = 'E'
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND EXISTS (SELECT 1
              FROM PA_BASIC_DATA_TEMP B
             WHERE B.CPNY_ID = V_CPNY_ID
               AND B.UPLOAD_BY = V_PERSON_ID
             GROUP BY B.EMPID,
                      B.START_DATE,
                      B.PARAM_NO,
                      B.Cpny_Id,
                      B.RETURN_VALUE,
                      B.REMARK
            HAVING COUNT(1) > 1);

    --权限验证
    /*UPDATE PA_BASIC_DATA_TEMP A
     SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[此社号不在你的管辖范围内]',
          A.RESULT_FLAG      = 'E'
    WHERE A.CPNY_ID = V_CPNY_ID
      AND A.UPLOAD_BY = V_PERSON_ID
      AND NOT EXISTS
      (SELECT 1 FROM HR_EMPLOYEE HR
            WHERE hr.empid = a.empid
            and hr.cpny_id = a.Cpny_Id
            AND hr.deptno in (
                SELECT DEPTNO FROM PA_SUPERVISOR_INFO WHERE PERSON_ID = V_PERSON_ID)
            AND hr.EMP_TYPE_CODE IN (SELECT EMP_TYPE_CODE CODE_NO
            FROM PA_SUPERVISOR_EMPTYPE_INFO P,
                            HR_JOB_TYPE_SETUP HP
                            WHERE P.EMP_TYPE_CODE = HP.JOBTYPE_NO
                             AND HP.CPNY_ID = V_CPNY_ID
                             AND P.PERSON_ID = V_PERSON_ID));*/

    --员工唯一验证
    UPDATE PA_BASIC_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[该社号不正确或为空]',
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS (SELECT PERSON_ID
              FROM HR_EMPLOYEE
             WHERE EMPID = A.EMPID
               AND CPNY_ID = A.Cpny_Id);

    --日期格式验证
    UPDATE PA_BASIC_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[开始日期格式不正确或为空!]',
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS
     (select 1
              from dual
             where IS_DATE(A.Start_Date, 'yyyy-mm-dd') is not null);

    --日期正确再执行以下验证
    Select A.Start_Date
      into E_start_date
      from PA_BASIC_DATA_TEMP A
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND ROWNUM = 1;

    IF IS_DATE(E_start_date, 'yyyy-mm-dd') is not null THEN
      --此数据已经存在，请核对数据后再进行导入!
      UPDATE PA_BASIC_DATA_TEMP A
         SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                  '[此数据已经存在，请核对数据后再进行导入!]',
             A.RESULT_FLAG      = 'E'
       WHERE A.UPLOAD_BY = V_PERSON_ID
         AND A.Cpny_Id = V_CPNY_ID
         AND EXISTS
       (SELECT 1
                FROM PA_BASIC_DATA B
               WHERE B.Cpny_Id = V_CPNY_ID
                 AND B.Person_Id =
                     (SELECT PERSON_ID
                        FROM HR_EMPLOYEE
                       WHERE EMPID = A.Empid
                         AND CPNY_ID = A.Cpny_Id)
                 AND B.Param_No = A.Param_No
                 AND B.Start_Date >= to_date(A.Start_Date, 'yyyy-mm-dd')
               GROUP BY B.PERSON_ID, B.Start_Date, B.Param_No, B.Cpny_Id
              HAVING COUNT(1) >= 1);
    END IF;
    --结果返回
    SELECT COUNT(1)
      INTO V_ERR_CNT
      FROM PA_BASIC_DATA_TEMP A
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND A.Cpny_Id = V_CPNY_ID
       AND A.RESULT_FLAG = 'E';
    IF V_ERR_CNT > 0 THEN
      V_ERR_CODE := 'NG';
    ELSE
      V_ERR_CODE := 'OK';
    END IF;

    v_ret := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      v_ret := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_VALIDATE_TEMP_basicItem_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_VALID_TEMP_BASICITEM_DATA;

  /******************************************************
  Desc: 工资基础项目数据结果导入
  *******************************************************/
  procedure PR_IMPORT_BASICITEM_DATA(V_PERSON_ID IN VARCHAR2,
                                     V_CPNY_ID   IN VARCHAR2,
                                     V_RET       IN OUT VARCHAR2) is
    V_VALID_FLAG VARCHAR2(10);
    V_ERR_CODE   VARCHAR2(10);
    RETURN_NUM   int;
    V_start_date VARCHAR2(20);
    V_param_no   NUMBER;
  BEGIN
    V_ERR_CODE := '';
    --数据验证
    PR_VALID_TEMP_BASICITEM_DATA(V_PERSON_ID, V_CPNY_ID, V_RET);
    V_VALID_FLAG := V_RET;
    IF V_VALID_FLAG != 'OK' THEN
      V_ERR_CODE := 'NG';
    ELSE
      SELECT COUNT(*)
        into RETURN_NUM
        FROM PA_BASIC_DATA A, PA_BASIC_DATA_TEMP B
       WHERE A.Param_No = B.PARAM_NO
         AND A.PERSON_ID = (SELECT PERSON_ID
                              FROM HR_EMPLOYEE
                             WHERE EMPID = B.EMPID
                               AND CPNY_ID = B.Cpny_Id)
         AND ROWNUM = 1
         AND A.Start_Date < to_date(B.Start_Date, 'yyyy-mm-dd');

      IF RETURN_NUM > 0 THEN
        Select A.Start_Date, A.Param_No
          into V_start_date, V_param_no
          from PA_BASIC_DATA A, PA_BASIC_DATA_TEMP B
         WHERE A.Param_No = B.PARAM_NO
           AND A.PERSON_ID = (SELECT PERSON_ID
                                FROM HR_EMPLOYEE
                               WHERE EMPID = B.EMPID
                                 AND CPNY_ID = B.Cpny_Id)
           AND ROWNUM = 1;
        UPDATE PA_BASIC_DATA
           SET END_DATE    = CASE WHEN END_DATE < LAST_DAY(START_DATE) THEN END_DATE ELSE LAST_DAY(START_DATE) END,
               UPDATE_DATE = SYSDATE,
               UPDATED_BY  = V_PERSON_ID,
               ACTIVITY    = 0
         WHERE ROWID IN
               (SELECT ROWID
                  FROM (SELECT ROWID,
                               ROW_NUMBER() OVER(ORDER BY START_DATE DESC) RN
                          FROM PA_BASIC_DATA
                         WHERE PARAM_NO = V_param_no
                           AND PERSON_ID =
                               (SELECT HR.PERSON_ID
                                  FROM HR_EMPLOYEE HR, PA_BASIC_DATA_TEMP B
                                 WHERE HR.EMPID = B.Empid
                                   AND HR.CPNY_ID = B.CPNY_ID))
                 WHERE RN = 1);
      END IF;
      INSERT INTO PA_BASIC_DATA A
        (BASIC_DATA_NO,
         PARAM_NO,
         PERSON_ID,
         START_DATE,
         RETURN_VALUE,
         REMARK,
         CPNY_ID,
         CREATE_DATE,
         CREATED_BY,
         ACTIVITY)
        (SELECT PA_BASE_DATA_SEQ.NEXTVAL,
                PARAM_NO,
                (select person_id
                   from hr_employee hr
                  where hr.empid = t.empid
                    and hr.cpny_id = t.cpny_id),
                to_date(START_DATE, 'yyyy-mm-dd'),
                RETURN_VALUE,
                REMARK,
                CPNY_ID,
                sysdate,
                V_PERSON_ID,
                1
           FROM PA_BASIC_DATA_TEMP T
          WHERE T.CPNY_ID = V_CPNY_ID
            AND T.UPLOAD_BY = V_PERSON_ID
            AND T.RESULT_FLAG = 'N');

      --数据上传成功，清理当前用户下的临时数据
      DELETE FROM PA_BASIC_DATA_TEMP WHERE UPLOAD_BY = V_PERSON_ID;
      V_ERR_CODE := 'OK';
    END IF;
    V_RET := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      V_RET := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_IMPORT_ACCOUNT_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_IMPORT_BASICITEM_DATA;
  ------------------------------------------------------------------------------------------------------

  ---
  --工资输入项目数据验证
  procedure PR_VALID_TEMP_PAPARAM_DATA(V_PERSON_ID      IN VARCHAR2,
                                       V_CPNY_ID        IN VARCHAR2,
                                       V_DISTINCT_FIELD IN VARCHAR2,
                                       V_RET            IN OUT VARCHAR2) is
    V_ERR_CODE VARCHAR2(10);
    V_ERR_CNT  NUMERIC(10);
  BEGIN
    V_ERR_CODE := 'OK';

    --上传数据重复数据检查
    /* UPDATE PA_PARAM_DATA_TEMP A
      SET A.Upload_Error_Msg = A.UPLOAD_ERROR_MSG || '[上传数据重复]',
          A.RESULT_FLAG      = 'E'
    WHERE  A.UPLOAD_BY = V_PERSON_ID
      AND EXISTS (SELECT 1
             FROM PA_PARAM_DATA_TEMP B
            WHERE B.CPNY_ID = V_CPNY_ID
              AND B.UPLOAD_BY = V_PERSON_ID
            GROUP BY B.EMPID,
                     B.START_MONTH,
                     B.END_MONTH,
                     B.Return_Value,
                     B.Remark,
                     B.PARAM_NO,
                     B.Cpny_Id
           HAVING COUNT(1) > 1);*/
    --工号进行去空格

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.EMPID = trim(a.empid)
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID;

    --去除金额逗号

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.RETURN_VALUE = REPLACE(A.RETURN_VALUE, ',', '')
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID;
    --验证数值

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = A.UPLOAD_ERROR_MSG || ' ' || A.RETURN_VALUE ||
                                ' It is not a valid number.', -- 不是有效的数值
           A.RESULT_FLAG      = 'E'
     WHERE ISNUMERIC(A.RETURN_VALUE) = 0

       AND A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID;

    --日期格式验证
    -- [2026/10/14] Tính lương theo ngày: Excel nhập START_DATE/END_DATE (theo ngày) hoặc START_MONTH/END_MONTH
    -- (MMYYYY, như cũ). Nhập theo tháng -> quy đổi sang ngày đầu/cuối kỳ lương bằng PA_GET_PERIOD_DATE_F
    -- (giống trigger TRG_PA_PARAM_DATA_DATE).
    UPDATE PA_PARAM_DATA_TEMP A
       SET A.START_MONTH = CASE WHEN LENGTH(A.START_MONTH) = 5 THEN '0' || A.START_MONTH ELSE A.START_MONTH END,
           A.END_MONTH   = CASE WHEN LENGTH(A.END_MONTH) = 5 THEN '0' || A.END_MONTH ELSE A.END_MONTH END
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID;

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[Start date / start month format error or empty!]', --开始日期/开始月格式错误或为空!
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.START_DATE IS NULL
       AND NOT EXISTS
     (select 1 from dual where IS_DATE(A.START_MONTH, 'mmyyyy') = '1');

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[End date / end month format error!]',
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.END_DATE IS NULL
       AND A.END_MONTH IS NOT NULL
       AND NOT EXISTS
     (select 1 from dual where IS_DATE(A.END_MONTH, 'mmyyyy') = '1');

    -- Quy đổi tháng -> ngày (chỉ dòng có tháng hợp lệ)
    UPDATE PA_PARAM_DATA_TEMP A
       SET A.START_DATE = PA_GET_PERIOD_DATE_F(A.START_MONTH, A.CPNY_ID, 'S')
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.START_DATE IS NULL
       AND EXISTS (select 1 from dual where IS_DATE(A.START_MONTH, 'mmyyyy') = '1');

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.END_DATE = PA_GET_PERIOD_DATE_F(A.END_MONTH, A.CPNY_ID, 'E')
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.END_DATE IS NULL
       AND A.END_MONTH IS NOT NULL
       AND EXISTS (select 1 from dual where IS_DATE(A.END_MONTH, 'mmyyyy') = '1');

    UPDATE PA_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[End date is before start date!]',
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.START_DATE IS NOT NULL
       AND A.END_DATE IS NOT NULL
       AND A.END_DATE < A.START_DATE;

    IF V_DISTINCT_FIELD = 'POST_GRADE_NO' THEN
      --职级别导入，去除职级两段的空格
      UPDATE PA_PARAM_DATA_TEMP A
         SET A.EMPID = trim(a.post_grade_no)
       WHERE A.CPNY_ID = V_CPNY_ID
         AND A.UPLOAD_BY = V_PERSON_ID;
      --验证职级是否存在
      IF V_CPNY_ID = 'TSTO' THEN
        UPDATE PA_PARAM_DATA_TEMP A
           SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                    '[Rank is empty or rank does not exist!]', --职级为空或者职级不存在!
               A.RESULT_FLAG      = 'E'
         WHERE A.CPNY_ID = V_CPNY_ID
           AND A.UPLOAD_BY = V_PERSON_ID
           AND A.POST_GRADE_NO NOT IN
               (SELECT S.CODE_NO
                  FROM SY_CODE S, SY_CODE_PARAM SP
                 WHERE S.CODE_NO = SP.CODE_NO(+)
                   AND SP.CPNY_ID = V_CPNY_ID
                   AND S.PARENT_CODE_NO in
                       ('14014289', '14014290', '14014291', '14014292',
                        '14014293', '14015578'))
            OR A.POST_GRADE_NO IS NULL;
      ELSE
        UPDATE PA_PARAM_DATA_TEMP A
           SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                    '[Rank is empty or rank does not exist!]', --职级为空或者职级不存在!
               A.RESULT_FLAG      = 'E'
         WHERE A.CPNY_ID = V_CPNY_ID
           AND A.UPLOAD_BY = V_PERSON_ID
           AND A.POST_GRADE_NO NOT IN
               (SELECT S.CODE_NO
                  FROM SY_CODE S, SY_CODE_PARAM SP
                 WHERE S.CODE_NO = SP.CODE_NO(+)
                   AND SP.CPNY_ID = V_CPNY_ID
                   AND S.PARENT_CODE_NO in
                       ('14015088', '14015089', '14015090', '14015091',
                        '14015578'))
            OR A.POST_GRADE_NO IS NULL;
      END IF;

      -- Chồng lấn ngày hiệu lực (tham số chia theo ngày PRORATE_TYPE 2, 3): cùng chức danh + loại lương,
      -- trong file import hoặc với PA_PARAM_DATA_OTHER đã có (sẽ bị cộng 2 lần khi tính lương)
      UPDATE PA_PARAM_DATA_TEMP A
         SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                  '[Effective dates overlap with existing data!]',
             A.RESULT_FLAG      = 'E'
       WHERE A.CPNY_ID = V_CPNY_ID
         AND A.UPLOAD_BY = V_PERSON_ID
         AND A.START_DATE IS NOT NULL
         AND EXISTS (SELECT 1 FROM PA_PARAM_ITEM_PARAM C
                      WHERE C.PARAM_NO = A.PARAM_NO
                        AND C.CPNY_ID = A.CPNY_ID
                        AND NVL(C.PRORATE_TYPE, 0) IN (2, 3))
         AND (EXISTS (SELECT 1 FROM PA_PARAM_DATA_OTHER P
                       WHERE P.CPNY_ID = A.CPNY_ID
                         AND P.PARAM_NO = A.PARAM_NO
                         AND P.FIELD1_VALUE = A.POST_GRADE_NO
                         AND NVL(P.FIELD2_VALUE, '#') = NVL(A.WAGE_TYPE, '#')
                         AND P.ACTIVITY = 1
                         AND P.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                         AND NVL(P.END_DATE, DATE '9999-12-31') >= A.START_DATE)
              OR EXISTS (SELECT 1 FROM PA_PARAM_DATA_TEMP B
                          WHERE B.CPNY_ID = A.CPNY_ID
                            AND B.UPLOAD_BY = A.UPLOAD_BY
                            AND B.PARAM_NO = A.PARAM_NO
                            AND B.POST_GRADE_NO = A.POST_GRADE_NO
                            AND NVL(B.WAGE_TYPE, '#') = NVL(A.WAGE_TYPE, '#')
                            AND B.ROWID <> A.ROWID
                            AND B.START_DATE IS NOT NULL
                            AND B.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                            AND NVL(B.END_DATE, DATE '9999-12-31') >= A.START_DATE));
    ELSE
      --权限验证

      /*UPDATE PA_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[此社号不在你的管辖范围内]',
            A.RESULT_FLAG      = 'E'
      WHERE A.CPNY_ID = V_CPNY_ID
        AND A.UPLOAD_BY = V_PERSON_ID
        AND NOT EXISTS
        (SELECT 1 FROM HR_EMPLOYEE HR
              WHERE hr.empid = a.empid
              and hr.cpny_id = a.Cpny_Id
              AND hr.deptno in (
                  SELECT DEPTNO FROM PA_SUPERVISOR_INFO WHERE PERSON_ID = V_PERSON_ID));*/

      --员工唯一验证
      UPDATE PA_PARAM_DATA_TEMP A
         SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                  '[The employee no is incorrect or empty.]', --该社号不正确或为空
             A.RESULT_FLAG      = 'E'
       WHERE A.CPNY_ID = V_CPNY_ID
         AND A.UPLOAD_BY = V_PERSON_ID
         AND NOT EXISTS (SELECT PERSON_ID
                FROM HR_EMPLOYEE
               WHERE EMPID = A.EMPID
                 AND CPNY_ID = A.Cpny_Id);

      -- Chồng lấn ngày hiệu lực (tham số chia theo ngày PRORATE_TYPE 2, 3): cùng nhân viên,
      -- trong file import hoặc với PA_PARAM_DATA đã có (cùng điều kiện PA_CHECK_PARAM_OVERLAP_F)
      UPDATE PA_PARAM_DATA_TEMP A
         SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                  '[Effective dates overlap with existing data!]',
             A.RESULT_FLAG      = 'E'
       WHERE A.CPNY_ID = V_CPNY_ID
         AND A.UPLOAD_BY = V_PERSON_ID
         AND A.START_DATE IS NOT NULL
         AND EXISTS (SELECT 1 FROM PA_PARAM_ITEM_PARAM C
                      WHERE C.PARAM_NO = A.PARAM_NO
                        AND C.CPNY_ID = A.CPNY_ID
                        AND NVL(C.PRORATE_TYPE, 0) IN (2, 3))
         AND (EXISTS (SELECT 1 FROM PA_PARAM_DATA P, HR_EMPLOYEE HR
                       WHERE HR.EMPID = A.EMPID
                         AND HR.CPNY_ID = A.CPNY_ID
                         AND P.PERSON_ID = HR.PERSON_ID
                         AND P.CPNY_ID = A.CPNY_ID
                         AND P.PARAM_NO = A.PARAM_NO
                         AND P.ACTIVITY = 1
                         AND NVL(P.REMARK, ' ') <> 'Employee Resign'
                         AND NVL(P.CREATED_BY, ' ') <> 'BEFORE_CAL'
                         AND P.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                         AND NVL(P.END_DATE, DATE '9999-12-31') >= A.START_DATE)
              OR EXISTS (SELECT 1 FROM PA_PARAM_DATA_TEMP B
                          WHERE B.CPNY_ID = A.CPNY_ID
                            AND B.UPLOAD_BY = A.UPLOAD_BY
                            AND B.PARAM_NO = A.PARAM_NO
                            AND B.EMPID = A.EMPID
                            AND B.ROWID <> A.ROWID
                            AND B.START_DATE IS NOT NULL
                            AND B.START_DATE <= NVL(A.END_DATE, DATE '9999-12-31')
                            AND NVL(B.END_DATE, DATE '9999-12-31') >= A.START_DATE));
    END IF;
    --结果返回
    SELECT COUNT(1)
      INTO V_ERR_CNT
      FROM PA_PARAM_DATA_TEMP A
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND A.Cpny_Id = V_CPNY_ID
       AND A.RESULT_FLAG = 'E';
    IF V_ERR_CNT > 0 THEN
      V_ERR_CODE := 'NG';
    ELSE
      V_ERR_CODE := 'OK';
    END IF;

    v_ret := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      v_ret := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_VALID_TEMP_PAPARAM_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_VALID_TEMP_PAPARAM_DATA;

  /******************************************************
  Desc: 工资基础项目数据结果导入
  *******************************************************/
  procedure PR_IMPORT_PAPARAM_DATA(V_PERSON_ID      IN VARCHAR2,
                                   V_CPNY_ID        IN VARCHAR2,
                                   V_DISTINCT_FIELD IN VARCHAR2,
                                   V_RET            IN OUT VARCHAR2) is
    V_VALID_FLAG VARCHAR2(10);
    V_ERR_CODE   VARCHAR2(10);
  BEGIN
    V_ERR_CODE := '';
    --数据验证
    PR_VALID_TEMP_PAPARAM_DATA(V_PERSON_ID,
                               V_CPNY_ID,
                               V_DISTINCT_FIELD,
                               V_RET);
    V_VALID_FLAG := V_RET;
    IF V_VALID_FLAG != 'OK' THEN
      V_ERR_CODE := 'NG';
    ELSE
      IF V_DISTINCT_FIELD = 'POST_GRADE_NO' THEN
        INSERT INTO PA_PARAM_DATA_OTHER A
          (PARAM_DATA_NO,
           PARAM_NO,
           FIELD1_VALUE,
           FIELD2_VALUE,
           START_MONTH,
           END_MONTH,
           START_DATE,
           END_DATE,
           RETURN_VALUE,
           REMARK,
           CPNY_ID,
           CREATE_DATE,
           CREATED_BY,
           ACTIVITY)
          (SELECT PA_PARAM_DATA_SEQ.NEXTVAL,
                  PARAM_NO,
                  POST_GRADE_NO,
                  Wage_Type,
                  -- START_MONTH/END_MONTH đã chuẩn hóa ở bước validate; nhập theo ngày thì suy ra tháng
                  NVL(START_MONTH, TO_CHAR(START_DATE, 'MMYYYY')),
                  NVL(END_MONTH, TO_CHAR(END_DATE, 'MMYYYY')),
                  START_DATE,
                  END_DATE,
                  RETURN_VALUE,
                  REMARK,
                  CPNY_ID,
                  sysdate,
                  V_PERSON_ID,
                  1
             FROM PA_PARAM_DATA_TEMP T
            WHERE T.CPNY_ID = V_CPNY_ID
              AND T.UPLOAD_BY = V_PERSON_ID
              AND T.RESULT_FLAG = 'N');
      ELSE
        INSERT INTO PA_PARAM_DATA A
          (PARAM_DATA_NO,
           PARAM_NO,
           PERSON_ID,
           START_MONTH,
           END_MONTH,
           START_DATE,
           END_DATE,
           RETURN_VALUE,
           REMARK,
           CPNY_ID,
           CREATE_DATE,
           CREATED_BY,
           ACTIVITY)
          (SELECT PA_PARAM_DATA_SEQ.NEXTVAL,
                  PARAM_NO,
                  (select person_id
                     from hr_employee hr
                    where hr.empid = t.empid
                      and hr.cpny_id = t.cpny_id),
                  -- START_MONTH/END_MONTH đã chuẩn hóa ở bước validate; nhập theo ngày thì suy ra tháng
                  NVL(START_MONTH, TO_CHAR(START_DATE, 'MMYYYY')),
                  NVL(END_MONTH, TO_CHAR(END_DATE, 'MMYYYY')),
                  START_DATE,
                  END_DATE,
                  RETURN_VALUE,
                  REMARK,
                  CPNY_ID,
                  sysdate,
                  V_PERSON_ID,
                  1
             FROM PA_PARAM_DATA_TEMP T
            WHERE T.CPNY_ID = V_CPNY_ID
              AND T.UPLOAD_BY = V_PERSON_ID
              AND T.RESULT_FLAG = 'N');
      END IF;
      --数据上传成功，清理当前用户下的临时数据
      DELETE FROM PA_PARAM_DATA_TEMP WHERE UPLOAD_BY = V_PERSON_ID;
      V_ERR_CODE := 'OK';
    END IF;
    V_RET := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      V_RET := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_IMPORT_PAPARAM_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_IMPORT_PAPARAM_DATA;

  /*--------------------------------保险输入项目数据验证----------------------------------------*/

  --保险输入项目数据验证
  procedure PR_VALID_TEMP_ISPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                       V_CPNY_ID   IN VARCHAR2,
                                       V_RET       IN OUT VARCHAR2) is
    V_ERR_CODE   VARCHAR2(10);
    V_ERR_CNT    NUMERIC(10);
    E_start_date VARCHAR2(20);
  BEGIN
    V_ERR_CODE := 'OK';

    --上传数据重复数据检查
    UPDATE IS_PARAM_DATA_TEMP A
       SET A.Upload_Error_Msg = A.UPLOAD_ERROR_MSG || '[Upload data duplication]', --上传数据重复
           A.RESULT_FLAG      = 'E'
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND EXISTS (SELECT 1
              FROM IS_PARAM_DATA_TEMP B
             WHERE B.CPNY_ID = V_CPNY_ID
               AND B.UPLOAD_BY = V_PERSON_ID
             GROUP BY B.EMPID,
                      B.START_MONTH,
                      B.PARAM_NO,
                      B.Cpny_Id,
                      B.RETURN_VALUE,
                      B.REMARK
            HAVING COUNT(1) > 1);

    --权限验证
    UPDATE IS_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[This employee no is not within your jurisdiction.]', --此社号不在你的管辖范围内
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS
     (SELECT 1
              FROM HR_EMPLOYEE HR
             WHERE hr.empid = a.empid
               and hr.cpny_id = a.Cpny_Id
               AND hr.deptno in
                   (SELECT DEPTNO
                      FROM PA_SUPERVISOR_INFO
                     WHERE PERSON_ID = V_PERSON_ID)
               AND hr.EMP_TYPE_CODE IN
                   (SELECT EMP_TYPE_CODE CODE_NO
                      FROM PA_SUPERVISOR_EMPTYPE_INFO P, HR_JOB_TYPE_SETUP HP
                     WHERE P.EMP_TYPE_CODE = HP.JOBTYPE_NO
                       AND HP.CPNY_ID = V_CPNY_ID
                       AND P.PERSON_ID = V_PERSON_ID));

    --员工唯一验证
    UPDATE IS_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[The employee no is incorrect or empty.]', --该社号不正确或为空
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS (SELECT PERSON_ID
              FROM HR_EMPLOYEE
             WHERE EMPID = A.EMPID
               AND CPNY_ID = A.Cpny_Id);

    --日期格式验证
    UPDATE IS_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[The start month date format is incorrect or empty!]', --开始月日期格式不正确或为空!
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND NOT EXISTS
     (select 1
              from dual
             where IS_DATE(CASE WHEN LENGTH(A.Start_MONTH) = 5 THEN '0'||A.Start_MONTH ELSE A.Start_MONTH END, 'mmyyyy') is not null);

    UPDATE IS_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') ||
                                '[The end date format is not correct!]', --结束月日期格式不正确!
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.END_MONTH IS NOT NULL
       AND NOT EXISTS
     (select 1
              from dual
             where IS_DATE(A.END_MONTH, 'mmyyyy') is not null);

    UPDATE IS_PARAM_DATA_TEMP A
       SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[The end of the month is less than the beginning of the month!]', --结束月小于开始月!
           A.RESULT_FLAG      = 'E'
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND A.END_MONTH IS NOT NULL
       AND NOT EXISTS
     (select 1 from dual where TO_DATE(A.END_MONTH,'MMYYYY') >= TO_DATE(A.START_MONTH,'MMYYYY'));

    --日期正确再执行以下验证
    Select A.Start_MONTH
      into E_start_date
      from IS_PARAM_DATA_TEMP A
     WHERE A.CPNY_ID = V_CPNY_ID
       AND A.UPLOAD_BY = V_PERSON_ID
       AND ROWNUM = 1;

    /* IF IS_DATE(E_start_date,'yyyymm') is not null
       THEN
    --此数据已经存在，请核对数据后再进行导入!
     UPDATE IS_PARAM_DATA_TEMP A
        SET A.UPLOAD_ERROR_MSG = NVL(A.UPLOAD_ERROR_MSG, '') || '[此数据已经存在，请核对数据后再进行导入!]',
            A.RESULT_FLAG      = 'E'
       WHERE  A.UPLOAD_BY = V_PERSON_ID
       AND    A.Cpny_Id = V_CPNY_ID
        AND  EXISTS (SELECT 1
               FROM IS_PARAM_DATA B
              WHERE B.Cpny_Id = V_CPNY_ID
                AND B.Person_Id = (SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID = A.Empid AND CPNY_ID = A.Cpny_Id)
                AND B.Param_No = A.Param_No
                AND TO_NUMBER(B.Start_MONTH,'999999')>=TO_NUMBER(A.Start_MONTH,'999999')
              GROUP BY B.PERSON_ID,
                       B.Start_MONTH,
                       B.Param_No,
                       B.Cpny_Id
             HAVING COUNT(1) >= 1);
     END IF;*/
    --结果返回
    SELECT COUNT(1)
      INTO V_ERR_CNT
      FROM IS_PARAM_DATA_TEMP A
     WHERE A.UPLOAD_BY = V_PERSON_ID
       AND A.Cpny_Id = V_CPNY_ID
       AND A.RESULT_FLAG = 'E';
    IF V_ERR_CNT > 0 THEN
      V_ERR_CODE := 'NG';
    ELSE
      V_ERR_CODE := 'OK';
    END IF;

    v_ret := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      v_ret := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_VALID_TEMP_ISPARAM_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_VALID_TEMP_ISPARAM_DATA;

  /******************************************************
  Desc: 工资基础项目数据结果导入
  *******************************************************/
  procedure PR_IMPORT_ISPARAM_DATA(V_PERSON_ID IN VARCHAR2,
                                   V_CPNY_ID   IN VARCHAR2,
                                   V_RET       IN OUT VARCHAR2) is
    V_VALID_FLAG      VARCHAR2(10);
    V_ERR_CODE        VARCHAR2(10);
    RETURN_NUM        int;
    V_start_date      VARCHAR2(20);
    V_param_no        NUMBER;
    V_param_item_name VARCHAR2(500);
    V_STR             VARCHAR2(500);
  BEGIN
    V_ERR_CODE := '';
    --数据验证
    PR_VALID_TEMP_ISPARAM_DATA(V_PERSON_ID, V_CPNY_ID, V_RET);
    V_VALID_FLAG := V_RET;
    IF V_VALID_FLAG != 'OK' THEN
      V_ERR_CODE := 'NG';
    ELSE
      SELECT COUNT(*)
        into RETURN_NUM
        FROM IS_PARAM_DATA A, IS_PARAM_DATA_TEMP B
       WHERE A.Param_No = B.PARAM_NO
         AND A.PERSON_ID = (SELECT PERSON_ID
                              FROM HR_EMPLOYEE
                             WHERE EMPID = B.EMPID
                               AND CPNY_ID = B.Cpny_Id);
      --  AND  ROWNUM = 1
      --  AND TO_NUMBER(A.Start_MONTH,'999999') < TO_NUMBER(B.Start_MONTH,'999999') ;

      IF RETURN_NUM > 0 THEN
        Select A.Start_MONTH, A.Param_No
          into V_start_date, V_param_no
          from IS_PARAM_DATA A, IS_PARAM_DATA_TEMP B
         WHERE A.Param_No = B.PARAM_NO
           AND A.PERSON_ID = (SELECT PERSON_ID
                                FROM HR_EMPLOYEE
                               WHERE EMPID = B.EMPID
                                 AND CPNY_ID = B.Cpny_Id)
           AND ROWNUM = 1;
        /* UPDATE IS_PARAM_DATA SET
        END_MONTH =   CASE
                       WHEN TO_NUMBER(END_MONTH,'999999') < TO_NUMBER(START_MONTH,'999999')
                         THEN
                        END_MONTH
                        ELSE  Start_MONTH
                          END,
                     UPDATE_DATE = SYSDATE,
                     UPDATED_BY  = V_PERSON_ID ,
                     ACTIVITY = 0
                     WHERE ROWID IN (SELECT ROWID
               FROM (SELECT ROWID,
                            ROW_NUMBER() OVER(ORDER BY START_MONTH DESC) RN
                       FROM IS_PARAM_DATA
                      WHERE PARAM_NO = V_param_no
                        AND PERSON_ID = (SELECT HR.PERSON_ID FROM HR_EMPLOYEE HR,IS_PARAM_DATA_TEMP B WHERE HR.EMPID = B.Empid AND HR.CPNY_ID = B.CPNY_ID))
              WHERE RN = 1)  ;*/
        --删除正式表中存在导入表里的数据
        SELECT GET_GLOBAL_NAME(T.PARAM_ITEM_NO, 'zh')
          into V_param_item_name
          FROM IS_PARAM_ITEM_PARAM T
         WHERE T.PARAM_NO = V_param_no;
        IF V_param_item_name != '保险补扣(个人)' AND
           V_param_item_name != '保险补扣(公司)' THEN

          V_STR := 'DELETE IS_PARAM_DATA IPD  WHERE IPD.PARAM_NO = ''' ||
                   V_param_no || '''' ||
                   ' AND IPD.PERSON_ID IN (select get_emp_personid(ipdp.empid, ''' ||
                   V_CPNY_ID || '''' || ') from IS_PARAM_DATA_TEMP ipdp)';

          EXECUTE IMMEDIATE V_STR;

          COMMIT;
        END IF;
        -- DELETE IS_PARAM_DATA IPD WHERE IPD.PARAM_NO=V_param_no AND IPD.PERSON_ID IN (
        --   select get_emp_personid(ipdp.empid,V_CPNY_ID) from IS_PARAM_DATA_TEMP ipdp
        --   );

      END IF;
      INSERT INTO IS_PARAM_DATA A
        (PARAM_DATA_NO,
         PARAM_NO,
         PERSON_ID,
         START_MONTH,
         END_MONTH,
         RETURN_VALUE,
         REMARK,
         CPNY_ID,
         CREATE_DATE,
         CREATED_BY,
         ACTIVITY)
        (SELECT PA_BASE_DATA_SEQ.NEXTVAL,
                PARAM_NO,
                (select person_id
                   from hr_employee hr
                  where hr.empid = t.empid
                    and hr.cpny_id = t.cpny_id),
                START_MONTH,
                END_MONTH,
                RETURN_VALUE,
                REMARK,
                CPNY_ID,
                sysdate,
                V_PERSON_ID,
                1
           FROM IS_PARAM_DATA_TEMP T
          WHERE T.CPNY_ID = V_CPNY_ID
            AND T.UPLOAD_BY = V_PERSON_ID
            AND T.RESULT_FLAG = 'N');

      --数据上传成功，清理当前用户下的临时数据
      DELETE FROM IS_PARAM_DATA_TEMP WHERE UPLOAD_BY = V_PERSON_ID;
      V_ERR_CODE := 'OK';
    END IF;
    V_RET := V_ERR_CODE;
  EXCEPTION
    WHEN OTHERS THEN
      V_RET := SQLCODE ||
               '--ERROR: PKG_ALLOWANCE_EXCEL_IMP.PR_IMPORT_ISPARAM_DATA -- ' ||
               SQLERRM;
      ROLLBACK;
  END PR_IMPORT_ISPARAM_DATA;

end PKG_WAGEBASE_EXCEL_TEMP;
/
