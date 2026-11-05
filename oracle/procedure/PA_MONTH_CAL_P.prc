CREATE OR REPLACE PROCEDURE "PA_MONTH_CAL_P" (IN_PAY_SCHEDULE_NO   NUMBER,
                                         IN_SUPERVISOR_NO  VARCHAR2,
                                         IN_CPNY_ID        VARCHAR2,
                                         IN_OUTPUT_TYPE    VARCHAR2,
                                         IN_OUTPUT_STR     OUT VARCHAR2)
  AUTHID CURRENT_USER IS
  /******************************************************************************
     NAME:       PA_MONTH_CAL_P
     PURPOSE:    工资计算   IN_PA_SCHEDULE_NO,IN_SUPERVISOR_NO,IN_CPNY_ID,IN_OUTPUT_TYPE,IN_OUTPUT_STR

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2015/10/10   Esprince       1. Created this procedure.

  ******************************************************************************/
  /*
     V_OUTPUT_TYPE     输出模式
                 ALL 全部输出
                 NONE   不输出
  */
  I                      INTEGER;
  V_STR                  VARCHAR2(20000); -- 创建T_PA_RESULT表的SQL
  V_PSTR                 VARCHAR2(20000); -- 更新T_PA_RESULT表的输入项目值的SQL
  V_CASTR                VARCHAR2(20000); -- 更新T_PA_RESULT表的工资项目值的SQL
  V_INDEX_STR            VARCHAR2(20000); -- 创建T_PA_RESULT索引SQL
  VAR_ALERT_STR          VARCHAR2(20000);
  VAR_STIME         DATE := SYSDATE; --计算开始时间
  VAR_T_STR              VARCHAR2(30000);
  V_CONDITION_COUNT      INT; -- 工资项目空条件数量
  V_FOMULAR_COUNT        INT; -- 工资项目公式数量
  R_COUNT                INT;
  V_COUNT                INT := 0; --计数器
  V_PARAM_ID             VARCHAR2(50);
  V_TIME                 DATE := SYSDATE;
  V_EMP_COUNT            VARCHAR2(6);
  V_ITEM_ID              VARCHAR2(50);
  V_BASIC_ITEM_ID        VARCHAR2(50);
  V_FORMULAR_NOCONDITION VARCHAR2(2000);
  VAR_OBJ_LOCK           INT := 0; --公司本月工资对象是否生成
  VAR_ATT_LOCK           INT := 0; --公司本月考勤是否生成
  VAR_PA_LOCK            INT := 0; --公司本月工资是否确认
  V_TABLE_NAME           VARCHAR2(30) := 'T_PA_' || IN_SUPERVISOR_NO;
  VAR_DATE_START    VARCHAR2(10); --区间开始时间
  VAR_DATE_END    VARCHAR2(10); --区间结束时间
  VAR_PAY_DATE    VARCHAR2(10);--工资支付日期
  VAR_PAY_START_DATE    VARCHAR2(10);--工资开始日期
  VAR_PAY_END_DATE    VARCHAR2(10);--工资开始日期
  VAR_SALARY_DISTIN    VARCHAR2(20);--工资区分
  VAR_SALARY_DISTIN_NO    VARCHAR2(20);--工资区分No
  VAR_L_SCHEDULE_NO NUMBER;--上月支付计划No
  VAR_APPLY_TYPE NUMBER :=1;--工资项目区分1为工资2为春节金3为中秋金
  VAR_UPDATE_STR    VARCHAR2(20000); --计算表达式
  VAR_PARAM_START_DATE  VARCHAR2(10); -- Kỳ hiệu lực tham số lương (YYYY/MM/DD) - tính theo ngày
  VAR_PARAM_END_DATE    VARCHAR2(10);
  V_CHECK_MSG           VARCHAR2(4000); -- Kết quả kiểm tra chồng lấn ngày hiệu lực

  -- 人事表游标
  CURSOR CUS_HR IS
    SELECT DISTINCT_FIELD, TABLE_NAME
      FROM PA_DISTINCT_LIST
     WHERE TABLE_NAME = 'PA_HR_V'
       AND CPNY_ID = IN_CPNY_ID
     ORDER BY ORDERNO;

  --考勤游标
  CURSOR CUS_AR IS
    SELECT AR_STA_ITEM.STA_ITEM_ID
      FROM AR_STA_ITEM, AR_STA_ITEM_PARAM
     WHERE AR_STA_ITEM.ITEM_NO = AR_STA_ITEM_PARAM.ITEM_NO
       AND AR_STA_ITEM_PARAM.CPNY_ID = IN_CPNY_ID
       AND AR_STA_ITEM.STA_ITEM_ID IN
           (SELECT COLUMN_NAME
              FROM USER_TAB_COLUMNS PA
             WHERE PA.TABLE_NAME = 'PA_AR_' || IN_CPNY_ID || '_V')
     ORDER BY AR_STA_ITEM_PARAM.CAL_ORDER;

    -- 输入项目游标
  CURSOR CUS_PARAM(APPLY_TYPE_IN NUMBER) IS
    SELECT PARAM_NO,
           DISTINCT_FIELD,
           DISTINCT_FIELD_2ND,
           DATA_TYPE,
           DEFAULT_VAL,
           PA_PARAM_ITEM.PARAM_ITEM_ID,
           NVL(PA_PARAM_ITEM_PARAM.PRORATE_TYPE, 0) PRORATE_TYPE
      FROM PA_PARAM_ITEM, PA_PARAM_ITEM_PARAM
     WHERE PA_PARAM_ITEM.PARAM_ITEM_NO = PA_PARAM_ITEM_PARAM.PARAM_ITEM_NO
       AND CPNY_ID = IN_CPNY_ID
       AND ( PA_PARAM_ITEM_PARAM.APPLY_TYPE = 0 OR PA_PARAM_ITEM_PARAM.APPLY_TYPE = APPLY_TYPE_IN )
       AND PA_PARAM_ITEM_PARAM.ACTIVITY = 1
       ORDER BY PA_PARAM_ITEM.ORDER_NO;

  -- 输入项目游标 取得所有例外的项目
  CURSOR CUS_PARAM_SPECAIL(APPLY_TYPE_IN NUMBER) IS
    SELECT PA_PARAM_ITEM.PARAM_ITEM_ID
      FROM PA_PARAM_ITEM, PA_PARAM_ITEM_PARAM
     WHERE PA_PARAM_ITEM.PARAM_ITEM_NO = PA_PARAM_ITEM_PARAM.PARAM_ITEM_NO
       AND CPNY_ID = IN_CPNY_ID
       AND ( PA_PARAM_ITEM_PARAM.APPLY_TYPE = 0 OR PA_PARAM_ITEM_PARAM.APPLY_TYPE = APPLY_TYPE_IN )
       AND PA_PARAM_ITEM.ITEM_TYPE IN (3,5,9)
       AND PA_PARAM_ITEM_PARAM.ACTIVITY = 1;

  -- 保险输入项目游标
  /*CURSOR CUS_IS_PARAM_ITEM IS
    SELECT A.PARAM_ITEM_NO, PARAM_ID
      FROM IS_PARAM_ITEM A, IS_PARAM_ITEM_PARAM B
     WHERE A.PARAM_ITEM_NO = B.PARAM_ITEM_NO
       AND B.CPNY_ID = IN_CPNY_ID
       AND A.ACTIVITY = 1;*/

  -- 保险项目游标
  /*CURSOR CUS_IS_ITEM IS
    SELECT A.ITEM_NO, DATATYPE, PRICISION, CARRY_BIT, ITEM_ID
      FROM IS_ITEM A, IS_ITEM_PARAM B
     WHERE A.ITEM_NO = B.ITEM_NO
       AND B.CPNY_ID = IN_CPNY_ID
       AND A.ACTIVITY = 1
     ORDER BY CALCU_ORDER;*/

  -- 工资项目游标
  CURSOR CUS_ITEM(APPLY_TYPE_IN NUMBER) IS
    SELECT PA_ITEM.ITEM_NO, DATATYPE, PRICISION, CARRY_BIT, ITEM_ID
      FROM PA_ITEM, PA_ITEM_PARAM
     WHERE PA_ITEM.ITEM_NO = PA_ITEM_PARAM.ITEM_NO
       AND CPNY_ID = IN_CPNY_ID
       AND PA_ITEM_PARAM.APPLY_TYPE = APPLY_TYPE_IN
       AND PA_ITEM.ACTIVITY = 1
     ORDER BY CALCU_ORDER;

  -- 工资公式游标
  CURSOR CUS_FORMULAR(ITEM_NO_IN NUMBER) IS
    SELECT FORMULAR_NO, CONDITION, FORMULAR, CONDITION_SEQ
      FROM PA_FORMULAR
     WHERE ITEM_NO = ITEM_NO_IN
       AND CPNY_ID = IN_CPNY_ID
     ORDER BY CONDITION_SEQ DESC;

BEGIN
  EXECUTE IMMEDIATE 'alter session set cursor_sharing = force';
  -- 设置输出缓冲区大小
  -- SET OUTPUT ON SIZE 50000;
  DBMS_OUTPUT.ENABLE(5000000);

  ---------------------------流程验证部分开始--------------------------------------
  --1.验证工资是否已经确认
  SELECT COUNT(PPS.PAY_SCHEDULE_NO)
    INTO VAR_PA_LOCK
    FROM PA_PAY_SCHEDULE PPS
   WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
     AND PPS.PA_LOCK_FLAG = 1;
  IF VAR_PA_LOCK = 1 THEN
    BEGIN
      --错误: 当月工资已经确认,不能再进行工资计算
      IN_OUTPUT_STR := 'Error: the monthly salary has been confirmed, no more payroll calculation.';
      RETURN;
    END;
  END IF;
  --2.验证是否生成工资对象
  SELECT COUNT(PPO.PAY_SCHEDULE_NO)
    INTO VAR_OBJ_LOCK
    FROM PA_PAY_OBJECT PPO
   WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
     AND PPO.ACTIVITY = 1
     AND PPO.INCLUDE_TYPE = 1 ;
  IF VAR_OBJ_LOCK = 0 THEN
    BEGIN
      --错误: 当月工资对象还未生成,不能进行工资计算.
      IN_OUTPUT_STR := 'Error: the monthly salary object is not yet generated, can not carry out salary calculation.';
      RETURN;
    END;
  END IF;

  ---------------------------流程验证部分结束--------------------------------------
  ---------------------------动态创建临时表开始------------------------------------

  /*获取工资支付计划信息*/
  SELECT TO_CHAR(PPS.PAY_DATE,'YYYY-MM-DD')PAY_DATE,
         get_global_name(PPS.SALARY_DISTIN_NO,'vi')SALARY_DISTIN,
         PPS.SALARY_DISTIN_NO,
         TO_CHAR(PPS.AR_START_DATE,'YYYY/MM/DD')AR_START_DATE,
         TO_CHAR(PPS.AR_END_DATE,'YYYY/MM/DD')AR_END_DATE,
         TO_CHAR(PPS.HR_START_DATE,'YYYY/MM/DD')HR_START_DATE,
         TO_CHAR(PPS.HR_END_DATE,'YYYY/MM/DD')HR_END_DATE
         INTO VAR_PAY_DATE,VAR_SALARY_DISTIN,VAR_SALARY_DISTIN_NO,
         VAR_DATE_START,VAR_DATE_END,VAR_PAY_START_DATE,VAR_PAY_END_DATE
  FROM PA_PAY_SCHEDULE PPS
  WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
  AND PPS.ACTIVITY = 1;

  /* Kỳ hiệu lực tham số lương: kỳ HR của lương thường trong tháng PAY_DATE
     (= HR_START_DATE..HR_END_DATE với lương thường; giữ đúng ý nghĩa "tháng lương" cũ với lương khác) */
  VAR_PARAM_START_DATE := TO_CHAR(PA_GET_PERIOD_DATE_F(TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'), IN_CPNY_ID, 'S'), 'YYYY/MM/DD');
  VAR_PARAM_END_DATE   := TO_CHAR(PA_GET_PERIOD_DATE_F(TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'), IN_CPNY_ID, 'E'), 'YYYY/MM/DD');

  /* Kiểm tra dữ liệu tham số chia theo ngày bị chồng lấn (sẽ bị cộng 2 lần) */
  V_CHECK_MSG := PA_CHECK_PARAM_OVERLAP_F(IN_PAY_SCHEDULE_NO, IN_CPNY_ID, NULL,
                     LEAST(TO_DATE(VAR_PARAM_START_DATE,'YYYY/MM/DD'), TO_DATE(VAR_DATE_START,'YYYY/MM/DD')),
                     GREATEST(TO_DATE(VAR_PARAM_END_DATE,'YYYY/MM/DD'), TO_DATE(VAR_DATE_END,'YYYY/MM/DD')));
  IF V_CHECK_MSG IS NOT NULL THEN
    -- Raise (không RETURN) để PKG_PA_WORK_FLOW rollback, không đánh dấu PA_CAL_FLAG = 1
    RAISE_APPLICATION_ERROR(-20103, V_CHECK_MSG);
  END IF;

  /*BEGIN
    --根据工资区分获取同样的工资区分下的上一次工资的支付计划No
    SELECT PAY_SCHEDULE_NO INTO VAR_L_SCHEDULE_NO FROM(
          SELECT PPS.PAY_SCHEDULE_NO FROM PA_PAY_SCHEDULE PPS
          WHERE PPS.PAY_SCHEDULE_NO NOT IN(
          SELECT PPST.PAY_SCHEDULE_NO
          FROM PA_PAY_SCHEDULE PPST
          WHERE PPST.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO)
          AND PPS.SALARY_DISTIN_NO = VAR_SALARY_DISTIN_NO
          AND PPS.PAY_DATE < TO_DATE(VAR_PAY_DATE,'YYYY/MM/DD')
          AND PPS.ACTIVITY = 1
          AND PPS.CPNY_ID = IN_CPNY_ID
          ORDER BY PPS.PAY_DATE DESC)A
          WHERE ROWNUM = 1;
  EXCEPTION
        WHEN NO_DATA_FOUND THEN
          VAR_L_SCHEDULE_NO := IN_PAY_SCHEDULE_NO ;
        WHEN OTHERS THEN
          VAR_L_SCHEDULE_NO := IN_PAY_SCHEDULE_NO ;
    END;*/

  -- EXECUTE IMMEDIATE 'SET LINESIZE = 5000';
  -- 建表字符串，表的列由参数名称 || 工资项目名称组成。工资项目名称按计算顺序排列。
  V_STR := 'CREATE TABLE ' || V_TABLE_NAME || ' AS SELECT  ''' ||
           IN_PAY_SCHEDULE_NO || ''' as PAY_SCHEDULE_NO, '''
                                    || VAR_PAY_DATE || ''' AS PAY_DATE, '''
                                    || VAR_SALARY_DISTIN_NO || ''' AS SALARY_DISTIN_NO, '''
                                    || VAR_SALARY_DISTIN || ''' AS SALARY_DISTIN, '''
                                    || VAR_DATE_START || ''' AS AR_START_DATE, '''
                                    || VAR_DATE_END || ''' AS AR_END_DATE, '''
                                    || VAR_PAY_START_DATE || ''' AS HR_START_DATE, '''
                                    || VAR_PAY_END_DATE || ''' AS HR_END_DATE, '''
                                    --|| VAR_L_SCHEDULE_NO || ''' AS LASTMONTH_SCHEDULE_NO, '''
                    || IN_SUPERVISOR_NO ||
           ''' AS CREATED_BY, SYSDATE AS CREATE_DATE,';

  -- 验证输入项目名称是否与接口表的字段名称重复
  BEGIN
    SELECT DISTINCT_FIELD
      INTO V_ITEM_ID
      FROM PA_DISTINCT_LIST
     WHERE DISTINCT_FIELD IN (SELECT PARAM_ITEM_ID FROM PA_PARAM_ITEM)
       AND CPNY_ID = IN_CPNY_ID;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_ITEM_ID := NULL;
  END;

  -- 如果输入项目名称与接口表的字段名称重复提示错误
  IF V_ITEM_ID IS NOT NULL THEN
    BEGIN
      --工资输入项目 和人事项目 出现重复。请修改后再次计算
      IN_OUTPUT_STR := 'ERROR：Wage entry project ID“' || V_ITEM_ID || '”and personnel projects ID“' ||
                      V_ITEM_ID || '”repeat. Please revise and calculate again！';
      RETURN;
    END;
  END IF;

  -- 验证计算项目名称是否与接口表的字段名称重复
  BEGIN
    SELECT DISTINCT_FIELD
      INTO V_ITEM_ID
      FROM PA_DISTINCT_LIST
     WHERE CPNY_ID = IN_CPNY_ID
       AND DISTINCT_FIELD IN (SELECT ITEM_ID FROM PA_ITEM);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_ITEM_ID := NULL;
  END;

  -- 如果计算项目名称与接口表的字段名称重复提示错误
  IF V_ITEM_ID IS NOT NULL THEN
    BEGIN
      --工资计算项目 和人事项目 出现重复。请修改后再次计算
      IN_OUTPUT_STR := 'ERROR： Payroll calculate project ID“' || V_ITEM_ID || '”and personnel projects ID“' ||
                      V_ITEM_ID || '”repeat. Please revise and calculate again！';
      RETURN;
    END;
  END IF;

  -- 验证计算项目名称是否与基础项目名称重复
  /*BEGIN
    SELECT PA_BASIC_ITEM.ITEM_ID
      INTO V_BASIC_ITEM_ID
      FROM PA_BASIC_ITEM, PA_ITEM
     WHERE PA_BASIC_ITEM.ITEM_ID = PA_ITEM.ITEM_ID
       AND PA_BASIC_ITEM.ACTIVITY = 1
       AND ROWNUM = 1;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_BASIC_ITEM_ID := NULL;
  END;*/

  -- 如果计算项目名称与基础项目名称重复提示错误
 /* IF V_BASIC_ITEM_ID IS NOT NULL THEN
    BEGIN
      IN_OUTPUT_STR := 'ERROR： 参数名称ID“' || V_BASIC_ITEM_ID || '”和工资项目ID“' ||
                      V_BASIC_ITEM_ID || '”出现重复。请修改后再次计算！';
      RETURN;
    END;
  END IF;*/

  -- 验证计算项目名称是否与输入项目名称重复
  BEGIN
    SELECT PA_PARAM_ITEM.PARAM_ITEM_ID
      INTO V_PARAM_ID
      FROM PA_PARAM_ITEM, PA_ITEM
     WHERE PA_PARAM_ITEM.PARAM_ITEM_ID = PA_ITEM.ITEM_ID
       AND ROWNUM = 1;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_PARAM_ID := NULL;
  END;

  -- 如果计算项目名称与输入项目名称重复提示错误
  IF V_PARAM_ID IS NOT NULL THEN
    BEGIN
      --参数名称 和工资项目 出现重复。请修改后再次计算
      IN_OUTPUT_STR := 'ERROR： The parameter name ID“' || V_PARAM_ID || '”and payroll project ID“' ||
                      V_PARAM_ID || '”repeat. Please revise and calculate again！';
      RETURN;
    END;
  END IF;

  -- 拼接人事数据列
  FOR HR_ROW IN CUS_HR LOOP
    -- 拼接创建表T_PA_RESULT的SQL字符串
    V_STR := V_STR || HR_ROW.TABLE_NAME || '.' || HR_ROW.DISTINCT_FIELD ||
             ' AS "' || HR_ROW.DISTINCT_FIELD || '",' || CHR(13);

  END LOOP;

  -- 拼接考勤数据列
  FOR AR_ROW IN CUS_AR LOOP
    V_STR := V_STR || AR_ROW.STA_ITEM_ID || ' AS "' || AR_ROW.STA_ITEM_ID || '",' ||
             CHR(13);

  END LOOP;

  -- 拼接输入项目列
  FOR PAR_ROW IN CUS_PARAM(VAR_APPLY_TYPE) LOOP
    BEGIN
      V_STR := V_STR || (CASE UPPER(SUBSTR(PAR_ROW.DATA_TYPE, 1, 4))
                 WHEN 'NUMB' THEN
                  '0'
                 WHEN 'VARC' THEN
                  ''' '''
               --此处留50空格确定此列长宽度
               END) || ' AS "' || PAR_ROW.PARAM_ITEM_ID || '" ,' || CHR(13);
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20101,
                                '工资参数数据类型ERROR: [' || PAR_ROW.PARAM_ITEM_ID || ']');
    END;

  END LOOP;

  -- 拼接保险输入项目列(用于计算结果导出)
  /*FOR IS_PARAM_ROW IN CUS_IS_PARAM_ITEM LOOP
    V_STR := V_STR || ' NVL(IS_SUM.' || IS_PARAM_ROW.PARAM_ID || ',0) AS "' ||
             IS_PARAM_ROW.PARAM_ID || '",' || CHR(13);

  END LOOP;*/

  -- 拼接保险项目列
  /*FOR IS_ROW IN CUS_IS_ITEM LOOP
    V_STR := V_STR || ' NVL(IS_SUM.' || IS_ROW.ITEM_ID || ',0) AS "' ||
             IS_ROW.ITEM_ID || '",' || CHR(13);

  END LOOP;*/

  -- 拼接工资项目列
  FOR PA_ROW IN CUS_ITEM(VAR_APPLY_TYPE) LOOP
    V_STR := V_STR || (CASE UPPER(SUBSTR(PA_ROW.DATATYPE, 1, 4))
               WHEN 'NUMB' THEN
                '0'
               WHEN 'VARC' THEN
                '''                                                                                                                                                                                                            '''
             --此处留50空格确定此列长宽度
               ELSE
                ''' '''
             END) || ' AS "' || PA_ROW.ITEM_ID || '" ,' || CHR(13);

  END LOOP;

  --去掉最后逗号
  V_STR := SUBSTR(V_STR, 0, LENGTH(V_STR) - 2);
  ---创建计算结果表 并插入人事及数据
  V_STR := V_STR || ' FROM PA_HR_V, PA_AR_' || IN_CPNY_ID ||
           '_V  PA_AR_V, PA_PAY_OBJECT PPO' ||
           ' WHERE PA_HR_V.PERSON_ID = PPO.PERSON_ID' ||
           ' AND PA_HR_V.PERSON_ID = PA_AR_V.PERSON_ID' ||
           ' AND PA_AR_V.PAY_SCHEDULE_NO = ' || IN_PAY_SCHEDULE_NO ||
           ' AND PPO.PAY_SCHEDULE_NO = ' || IN_PAY_SCHEDULE_NO ||
           ' AND PPO.INCLUDE_TYPE = 1 AND PPO.ACTIVITY = 1 ';

  IF IN_OUTPUT_TYPE IN ('ALL') THEN
     DBMS_OUTPUT.PUT_LINE('THIS IS CREATE TABLE STRING');
  END IF;
  I := 0;

  --DBMS_OUTPUT.PUT_LINE(V_STR);

  -- 日志输出
  IF IN_OUTPUT_TYPE IN ('ALL') THEN
    WHILE I * 150 <= LENGTH(V_STR) LOOP
      DBMS_OUTPUT.PUT_LINE(SUBSTR(V_STR, I * 150, 150));
      I := I + 1;
    END LOOP;
  END IF;

  SELECT COUNT(*)
    INTO R_COUNT
    FROM USER_TAB_COLUMNS
   WHERE TABLE_NAME = V_TABLE_NAME;

  IF R_COUNT > 0 THEN
    BEGIN
      EXECUTE IMMEDIATE 'DROP TABLE ' || V_TABLE_NAME || ' PURGE ';
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20101,
                                '删除ERROR ' || V_TABLE_NAME || ' 失败',
                                TRUE);
    END;
  END IF;

  V_INDEX_STR := 'alter table ' || V_TABLE_NAME || ' add constraint PK_' ||
                 V_TABLE_NAME || ' primary key (PAY_SCHEDULE_NO,PERSON_ID) ';

  -- 执行创建表T_PA_RESULT的SQL
   BEGIN
      --dbms_output.put_line(v_str);
      IF IN_OUTPUT_TYPE IN ('ALL') THEN
         DBMS_OUTPUT.put_line ('执行创建表T_PA_RESULT的SQL前'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
      END IF;
      EXECUTE IMMEDIATE V_STR;
      EXECUTE IMMEDIATE V_INDEX_STR;
      IF IN_OUTPUT_TYPE IN ('ALL') THEN
         DBMS_OUTPUT.put_line ('执行创建表T_PA_RESULT的SQL后'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
      END IF;

      COMMIT;
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20101,
                                '创建计算结果表失败ERROR: [' ||
                                SUBSTR(V_STR, LENGTH(V_STR) - 1000, 1000) || ']',
                                TRUE);
    END;
  -----------------------------------从ar_summary中获取准确的人事信息 ----------------------------------------------
  IF IN_OUTPUT_TYPE IN ('ALL') THEN
      DBMS_OUTPUT.put_line ('获取员工计算工资的信息前'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
  END IF;

  VAR_UPDATE_STR := ' UPDATE '|| V_TABLE_NAME ||' T '
                         ||' SET (T.DEPTNO,T.DEPT_NAME,T.EMP_TYPE_NAME,T.POSITION_NO,T.POSITION_NAME,T.POST_GRADE_NO,T.POST_GRADE,T.POST_FAMILY_NO,T.POST_FAMILY)= '
                         ||' ( SELECT TT.DEPTNO,TT.DEPT_NAME,TT.EMP_TYPE_NAME,TT.POSITION_NO,TT.POSITION_NAME,TT.POST_GRADE_NO,TT.POST_GRADE,TT.POST_FAMILY_NO,TT.POST_FAMILY FROM AR_SUMMARY_'||IN_CPNY_ID
                         ||' TT WHERE TT.PAY_SCHEDULE_NO='''||IN_PAY_SCHEDULE_NO
                         ||''' AND TT.PERSON_ID = T.PERSON_ID ) ';

  IF IN_OUTPUT_TYPE IN ('ALL') THEN
    I := 0;
    WHILE I * 120 <= LENGTH(VAR_UPDATE_STR) LOOP
      DBMS_OUTPUT.PUT_LINE(SUBSTR(VAR_UPDATE_STR, I * 120 + 1, 120));
      I := I + 1;
    END LOOP;
  END IF;

  BEGIN

    EXECUTE IMMEDIATE VAR_UPDATE_STR;
 EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20101,
                              '获取员工计算工资的信息 ERROR: [' ||
                              SQLERRM || ']');
  END;

  IF IN_CPNY_ID = 'HAE' THEN
    VAR_UPDATE_STR := ' UPDATE '|| V_TABLE_NAME ||' T SET (T.PAY_STEP_NO,T.PAY_STEP_NM)='
                      ||' ( SELECT TT.PAY_STEP_NO,TT.PAY_STEP_NM FROM AR_SUMMARY_'||IN_CPNY_ID
                     ||' TT WHERE TT.PAY_SCHEDULE_NO='''||IN_PAY_SCHEDULE_NO
                     ||''' AND TT.PERSON_ID = T.PERSON_ID ) ';
  END IF;
    BEGIN

      EXECUTE IMMEDIATE VAR_UPDATE_STR;
   EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20101,
                                '获取员工计算工资的信息 ERROR: [' ||
                                SQLERRM || ']');
  END;

  IF IN_OUTPUT_TYPE IN ('ALL') THEN
     DBMS_OUTPUT.put_line ('获取员工计算工资的信息后'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
  END IF;

  ----------------------------------------------------------  输入项目计算 ---------------------------------------------------------
  --人事信息中如果是无试用工资 则把当月的试用期比例变成1
  IF IN_CPNY_ID = 'HTSV' THEN

     /*HTSV法人 职级为Worker的员工 基本工资、交通补贴、工龄补助、满勤奖 自动根据PA_WORKER_SALARY_TABLE获取*/
     PA_WORKER_SALARY_TABLE_HTSV_P(IN_PAY_SCHEDULE_NO,VAR_PAY_DATE,VAR_DATE_START); -- VAR_DATE_END

    INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH
                                          ,START_DATE,END_DATE
                                          ,CPNY_ID,CREATE_DATE,CREATED_BY,ACTIVITY)
      SELECT PA_PARAM_DATA_SEQ.NEXTVAL,
               '12',HRE.PERSON_ID,1,
               TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'),
               TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'),
               TO_DATE(VAR_PARAM_START_DATE,'YYYY/MM/DD'),
               TO_DATE(VAR_PARAM_END_DATE,'YYYY/MM/DD'),
               IN_CPNY_ID,SYSDATE,'FORPA',1
         FROM HR_EMPLOYEE HRE
        WHERE HRE.PERSON_ID IN(
              SELECT PPO.PERSON_ID FROM PA_PAY_OBJECT PPO
              WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO )
           AND HRE.IS_PROBATION = 1
           AND HRE.PERSON_ID NOT IN(
               SELECT TTT.PERSON_ID FROM PA_PARAM_DATA TTT
               WHERE TTT.PARAM_NO = 12
                 AND TTT.ACTIVITY = 1
           );
  ELSIF IN_CPNY_ID = 'HAE' THEN
    INSERT INTO PA_PARAM_DATA(PARAM_DATA_NO,PARAM_NO,PERSON_ID,RETURN_VALUE,START_MONTH,END_MONTH
                                          ,START_DATE,END_DATE
                                          ,CPNY_ID,CREATE_DATE,CREATED_BY,ACTIVITY)
      SELECT PA_PARAM_DATA_SEQ.NEXTVAL,
               '1007',HRE.PERSON_ID,1,
               TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'),
               TO_CHAR(TO_DATE(VAR_PAY_DATE,'YYYY-MM-DD'),'MMYYYY'),
               TO_DATE(VAR_PARAM_START_DATE,'YYYY/MM/DD'),
               TO_DATE(VAR_PARAM_END_DATE,'YYYY/MM/DD'),
               IN_CPNY_ID,SYSDATE,'FORPA',1
         FROM HR_EMPLOYEE HRE
        WHERE HRE.PERSON_ID IN(
              SELECT PPO.PERSON_ID FROM PA_PAY_OBJECT PPO
              WHERE PPO.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO )
           AND HRE.IS_PROBATION = 1
           AND HRE.PERSON_ID NOT IN(
               SELECT TTT.PERSON_ID FROM PA_PARAM_DATA TTT
               WHERE TTT.PARAM_NO = 12
                 AND TTT.ACTIVITY = 1
           );
  END IF ;

  --职责津贴初始化
  IF IN_CPNY_ID = 'HAE'THEN
    PA_POSITION_SUB_INIT_P(IN_PAY_SCHEDULE_NO,VAR_PAY_DATE,IN_CPNY_ID);
  END IF;

  IF IN_OUTPUT_TYPE IN ('ALL') THEN
     DBMS_OUTPUT.PUT_LINE(CHR(13) ||
                       ' ----------    THIS IS THE CALCULATE PARAM DATA STRING ----------');
  END IF;
  FOR PAR_VAL IN CUS_PARAM(VAR_APPLY_TYPE) LOOP
    -- Lọc dữ liệu theo START_DATE/END_DATE + cách gộp PRORATE_TYPE (xem PA_BUILD_PARAM_UPDATE_F)
    V_PSTR := PA_BUILD_PARAM_UPDATE_F(V_TABLE_NAME,
                                      IN_CPNY_ID,
                                      VAR_SALARY_DISTIN_NO,
                                      TO_CHAR(PAR_VAL.PARAM_NO),
                                      PAR_VAL.PARAM_ITEM_ID,
                                      PAR_VAL.DISTINCT_FIELD,
                                      PAR_VAL.DISTINCT_FIELD_2ND,
                                      PAR_VAL.DATA_TYPE,
                                      PAR_VAL.DEFAULT_VAL,
                                      PAR_VAL.PRORATE_TYPE,
                                      VAR_PARAM_START_DATE,
                                      VAR_PARAM_END_DATE,
                                      VAR_DATE_START,
                                      VAR_DATE_END);

    I := 0;

    WHILE I * 150 <= LENGTH(V_PSTR) LOOP
      IF IN_OUTPUT_TYPE IN ('ALL', 'PARAM') THEN
        DBMS_OUTPUT.PUT_LINE(SUBSTR(V_PSTR, I * 150, 150));
      END IF;

      I := I + 1;
    END LOOP;

    BEGIN
      IF IN_OUTPUT_TYPE IN ('ALL') THEN
         DBMS_OUTPUT.put_line ('CALCULATE PARAM DATA前'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
      END IF;
        EXECUTE IMMEDIATE V_PSTR;
      IF IN_OUTPUT_TYPE IN ('ALL') THEN
         DBMS_OUTPUT.put_line ('CALCULATE PARAM DATA后'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
      END IF;
        COMMIT;
      EXCEPTION
        WHEN OTHERS THEN
          RAISE_APPLICATION_ERROR(-20101,
                                  '参数计算失败ERROR: [' || V_PSTR || ']');
     END;
  END LOOP;
  IF IN_OUTPUT_TYPE IN ('ALL') THEN
     DBMS_OUTPUT.PUT_LINE('----------CALCULATE PARAM DATA STRING FINISHED-----------');
  END IF;

  ---------------------------------------------------   工资项目计算   -------------------------------------------------------------
  IF IN_OUTPUT_TYPE IN ('ALL') THEN
     DBMS_OUTPUT.PUT_LINE(CHR(13) ||
                       '  -------------------  THIS IS THE CALCU PA ITEMS STRING ---------');
  END IF;
  BEGIN

    FOR PAITEM_VAL IN CUS_ITEM(VAR_APPLY_TYPE) LOOP

      -- 字符类型工资项目
      IF UPPER(SUBSTR(PAITEM_VAL.DATATYPE, 1, 4)) = 'VARC' THEN
        V_CASTR := CHR(13) || 'UPDATE ' || V_TABLE_NAME || ' set "' ||
                   PAITEM_VAL.ITEM_ID || '" = ';
        -- 数值类型工资项目
      ELSE
        V_CASTR := CHR(13) || 'UPDATE ' || V_TABLE_NAME || ' set "' ||
                  --PAITEM_VAL.ITEM_ID || '" = TRUNC((' ||
                   /*PAITEM_VAL.ITEM_ID || '" = NVL(TRUNC((' ||
                   PAITEM_VAL.CARRY_BIT || ' + ';*/
                   PAITEM_VAL.ITEM_ID || '" = NVL(ROUND((' ;
      END IF;

      /************************************************计算所有工资项目**************************************
         公式五种情况：
         1.    无公式                  0
         2.    单一公式无条件          直接列公式
         3.    单一公式有条件          CASE WHEN 条件 THEN 公式 ELSE 0 END
         4.    多公式均有条件          CASE 循环(WHEN 条件 THEN 公式) ELSE 0 END
         5.    多公式，其中一条无条件  CASE 循环(WHEN 条件 THEN 公式) ELSE 公式 END
      *******************************************************************************************************/

      -- 取得当前工资项目的公式数量
      SELECT COUNT(*)
        INTO V_FOMULAR_COUNT
        FROM PA_FORMULAR
       WHERE ITEM_NO = PAITEM_VAL.ITEM_NO
         AND CPNY_ID = IN_CPNY_ID;

      -- 取得当前工资项目的空公式条件数量
      SELECT COUNT(*)
        INTO V_CONDITION_COUNT
        FROM PA_FORMULAR
       WHERE ITEM_NO = PAITEM_VAL.ITEM_NO
         AND CPNY_ID = IN_CPNY_ID
         AND (REPLACE(REPLACE(RTRIM(LTRIM(CONDITION)), CHR(9), ''), ' ', '') = '' OR
             CONDITION IS NULL);

      IF V_FOMULAR_COUNT = 0 THEN
        V_CASTR := V_CASTR || '0';
      ELSIF V_FOMULAR_COUNT = 1 AND V_CONDITION_COUNT = 1 THEN
        SELECT V_CASTR || FORMULAR
          INTO V_CASTR
          FROM PA_FORMULAR
         WHERE ITEM_NO = PAITEM_VAL.ITEM_NO
           AND CPNY_ID = IN_CPNY_ID;
      ELSE
        V_CASTR := V_CASTR || ' CASE ';

        FOR FORMULAR_ROW IN CUS_FORMULAR(PAITEM_VAL.ITEM_NO) LOOP
          IF REPLACE(REPLACE(RTRIM(LTRIM(FORMULAR_ROW.CONDITION)),
                             CHR(9),
                             ''),
                     ' ',
                     '') = '' OR FORMULAR_ROW.CONDITION IS NULL THEN
            V_FORMULAR_NOCONDITION := FORMULAR_ROW.FORMULAR;
          ELSE
            V_CASTR := V_CASTR || ' WHEN ' || FORMULAR_ROW.CONDITION ||
                       ' THEN ' || FORMULAR_ROW.FORMULAR;
          END IF;
        END LOOP;

        IF V_CONDITION_COUNT = 0 THEN

          -- 字符类型工资项目
          IF UPPER(SUBSTR(PAITEM_VAL.DATATYPE, 1, 4)) = 'VARC' THEN
            V_CASTR := V_CASTR || ' ELSE '''' END ';
          ELSE
            V_CASTR := V_CASTR || ' ELSE 0 END ';
          END IF;
        ELSE
          V_CASTR := V_CASTR || ' ELSE ' || V_FORMULAR_NOCONDITION ||
                     ' END ';
        END IF;
      END IF;

      -- 数值类型工资项目
      IF UPPER(SUBSTR(PAITEM_VAL.DATATYPE, 1, 4)) <> 'VARC' THEN
        --V_CASTR := V_CASTR || '),' || PAITEM_VAL.PRICISION || ')';
        V_CASTR := V_CASTR || '),' || PAITEM_VAL.PRICISION || '),0)';
      END IF;

      I := 0;

      WHILE I * 150 <= LENGTH(V_CASTR) LOOP
        IF IN_OUTPUT_TYPE IN ('ALL', 'PAITEM') THEN
          DBMS_OUTPUT.PUT_LINE(SUBSTR(V_CASTR, I * 150, 150));
        END IF;

        I := I + 1;
      END LOOP;

      BEGIN
          IF IN_OUTPUT_TYPE IN ('ALL') THEN
             DBMS_OUTPUT.put_line ('CALCULATE PA CAL DATA  前'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
          END IF;
          EXECUTE IMMEDIATE V_CASTR;
          IF IN_OUTPUT_TYPE IN ('ALL') THEN
             DBMS_OUTPUT.put_line ('CALCULATE PA CAL DATA  后'|| TRUNC((SYSDATE - VAR_STIME) * 24 * 60 * 60 + 0.005, 2));
          END IF;
          COMMIT;
        EXCEPTION
          WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20101,
                                    '计算项目计算失败ERROR: [' || V_CASTR || ']',
                                    TRUE);
       END;
      END LOOP;

  END;

  ----------------------------------------------操作 pa_summary_ 开始----------------------------------
  --检验是否存在以"PA_SUMMARY_"开头
  SELECT COUNT(*)
    INTO V_COUNT
    FROM user_tables
   WHERE table_name = 'PA_SUMMARY_' || IN_CPNY_ID;

  --如果不存在该表，生成该表
  IF V_COUNT = 0 THEN
    EXECUTE IMMEDIATE 'create table PA_SUMMARY_' || IN_CPNY_ID || '( ' ||
                      'PAY_SCHEDULE_NO  VARCHAR2(6),PAY_DATE CHAR(10),SALARY_DISTIN_NO VARCHAR2(20),SALARY_DISTIN VARCHAR2(20),AR_START_DATE CHAR(10),AR_END_DATE CHAR(10),HR_START_DATE CHAR(10),HR_END_DATE CHAR(10),LASTMONTH_SCHEDULE_NO VARCHAR2(10),CREATED_BY VARCHAR2(20),CREATE_DATE DATE, PERSON_ID VARCHAR2(20) )';

    EXECUTE IMMEDIATE 'alter table PA_SUMMARY_' || IN_CPNY_ID ||
                      ' add constraint ' || 'PK_PA_SUMMARY_' || IN_CPNY_ID ||
                      ' primary key (PAY_SCHEDULE_NO, PERSON_ID)';

  END IF;

  -- 提取列信息,更新表结构
  DECLARE
    CURSOR CUR_COLUMN_NAME IS
      SELECT COLUMN_NAME,
             MAX(DATA_TYPE) DATA_TYPE,
             MAX(DATA_LENGTH) DATA_LENGTH
        FROM USER_TAB_COLUMNS PA
       WHERE TABLE_NAME = V_TABLE_NAME
         AND (EXISTS (SELECT *
                        FROM PA_DISTINCT_LIST T
                       WHERE T.TABLE_NAME = 'PA_HR_V'
                         AND T.DISTINCT_FIELD = PA.COLUMN_NAME
                         AND T.CPNY_ID = IN_CPNY_ID) OR EXISTS
              (SELECT *
                 FROM AR_STA_ITEM T
                WHERE T.STA_ITEM_ID = PA.COLUMN_NAME) OR EXISTS
              (SELECT *
                 FROM PA_PARAM_ITEM T
                WHERE T.PARAM_ITEM_ID = PA.COLUMN_NAME) OR EXISTS
              (SELECT * FROM PA_ITEM T WHERE T.ITEM_ID = PA.COLUMN_NAME) OR
              EXISTS
              (SELECT * FROM PA_BASIC_ITEM T WHERE T.ITEM_ID = PA.COLUMN_NAME) OR
              EXISTS
              (SELECT * FROM IS_ITEM T WHERE T.ITEM_ID = PA.COLUMN_NAME) OR
              EXISTS (SELECT *
                        FROM IS_PARAM_ITEM T
                       WHERE T.PARAM_ID = PA.COLUMN_NAME))
         AND NOT EXISTS
       (SELECT T.COLUMN_NAME
                FROM USER_TAB_COLUMNS T
               WHERE T.TABLE_NAME = 'PA_SUMMARY_' || IN_CPNY_ID
                 AND T.COLUMN_NAME = PA.COLUMN_NAME)
       GROUP BY COLUMN_NAME
       ORDER BY MIN(COLUMN_ID);

    COLUMN_NAME_CUR CUR_COLUMN_NAME%ROWTYPE;
  BEGIN
    OPEN CUR_COLUMN_NAME;

    LOOP
      FETCH CUR_COLUMN_NAME
        INTO COLUMN_NAME_CUR;

      EXIT WHEN CUR_COLUMN_NAME%NOTFOUND;
      VAR_ALERT_STR := ' alter table TABLE_NAME_T add ' ||
                       COLUMN_NAME_CUR.COLUMN_NAME || ' ' ||
                       COLUMN_NAME_CUR.DATA_TYPE ||
                       CASE COLUMN_NAME_CUR.DATA_TYPE
                         WHEN 'VARCHAR2' THEN
                          ' (' || TO_CHAR(COLUMN_NAME_CUR.DATA_LENGTH) || ') '
                         WHEN 'CHAR' THEN
                          ' (' || TO_CHAR(COLUMN_NAME_CUR.DATA_LENGTH) || ') '
                         ELSE
                          ''
                       END;

      /*修改 T_PA_RESULT 表*/
      BEGIN
        --DBMS_OUTPUT.PUT_LINE(VAR_ALERT_STR);
        EXECUTE IMMEDIATE REPLACE(VAR_ALERT_STR,
                                  'TABLE_NAME_T',
                                  'PA_SUMMARY_' || IN_CPNY_ID);

      END;
    END LOOP;

    CLOSE CUR_COLUMN_NAME;
  END;

  /*定义T_PA_RESULT表包含的列的CURSOR*/
  DECLARE
    CURSOR CUR_COLUMN_NAME IS
      SELECT COLUMN_NAME
        FROM USER_TAB_COLUMNS
       WHERE TABLE_NAME = V_TABLE_NAME
       GROUP BY COLUMN_NAME
       ORDER BY MIN(COLUMN_ID);

    COLUMN_NAME_CUR CUR_COLUMN_NAME%ROWTYPE;
  BEGIN
    OPEN CUR_COLUMN_NAME;

    LOOP
      FETCH CUR_COLUMN_NAME
        INTO COLUMN_NAME_CUR;

      EXIT WHEN CUR_COLUMN_NAME%NOTFOUND;

      EXIT WHEN CUR_COLUMN_NAME%NOTFOUND;
      VAR_T_STR := VAR_T_STR || COLUMN_NAME_CUR.COLUMN_NAME || ', ';
    END LOOP;

    CLOSE CUR_COLUMN_NAME;

    VAR_T_STR := SUBSTR(VAR_T_STR, 1, LENGTH(VAR_T_STR) - 2);
  END;
  -- 删除已存在的数据(当前计算的数据)
  /*EXECUTE IMMEDIATE 'DELETE PA_SUMMARY_' || IN_CPNY_ID || ' T ' ||
                    'WHERE EXISTS (SELECT T2.PERSON_ID FROM ' ||
                    V_TABLE_NAME || ' T2 ' ||
                    ' WHERE T.PAY_SCHEDULE_NO = T2.PAY_SCHEDULE_NO AND T.PERSON_ID = T2.PERSON_ID)';*/
  EXECUTE IMMEDIATE 'DELETE FROM PA_SUMMARY_' || IN_CPNY_ID || ' T ' ||
                        ' WHERE T.PAY_SCHEDULE_NO = ''' || IN_PAY_SCHEDULE_NO || ''' ';

  /*将临时表的数据插入到PA_SUMMARY_表*/
  EXECUTE IMMEDIATE 'INSERT INTO PA_SUMMARY_' || IN_CPNY_ID || ' (' ||
                    VAR_T_STR || ') SELECT ' || VAR_T_STR || ' FROM ' ||
                    V_TABLE_NAME;

  EXECUTE IMMEDIATE ' SELECT COUNT(PERSON_ID) FROM ' || V_TABLE_NAME
    INTO V_EMP_COUNT;

  -- 删除临时表
  EXECUTE IMMEDIATE 'DROP TABLE ' || V_TABLE_NAME || ' PURGE';

  /*IF IN_CPNY_ID = 'TSTO'THEN
    --激励金扣税补贴计算
    PA_SPECIALTAX_SUB_CAL_P(IN_PAY_SCHEDULE_NO);

    UPDATE PA_SUMMARY_TSTO PST
    SET PST.P_NEWS_BONUS_TAXADD = 0
    WHERE PST.P_NEWS_BONUS_TAXADD = -0.01
    AND PST.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
  END IF;*/

  -- 拼接例外项目列 把所有例外项目是-0.01的都变成0
  FOR PAR_ROW_SPECAIL IN CUS_PARAM_SPECAIL(VAR_APPLY_TYPE) LOOP
    BEGIN
      V_STR := ' UPDATE PA_SUMMARY_'||IN_CPNY_ID ||
                  ' SET '|| PAR_ROW_SPECAIL.PARAM_ITEM_ID || '=NULL'||
                  ' WHERE '|| PAR_ROW_SPECAIL.PARAM_ITEM_ID || '=-0.01'||
                  ' AND PAY_SCHEDULE_NO = '''||IN_PAY_SCHEDULE_NO||'''';

      IF IN_OUTPUT_TYPE IN ('ALL') THEN
         DBMS_OUTPUT.PUT_LINE(V_STR);
      END IF;
      EXECUTE IMMEDIATE V_STR;
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20101,
                                '处理例外项目归0时错误ERROR: [' || PAR_ROW_SPECAIL.PARAM_ITEM_ID || ']');
    END;

  END LOOP;
  --生成工资项目的公式
  IF IN_CPNY_ID = 'HTSV'THEN
     PA_SUMMARY_MANAGE_P(IN_PAY_SCHEDULE_NO,IN_CPNY_ID);
  END IF;
  UPDATE PA_SUMMARY_HTSV P SET P.REAL_WAGES = 0 WHERE P.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO AND  P.REAL_WAGES < 0;
  UPDATE PA_SUMMARY_HTSV SET  P_BASIC_SALARY_STD = P_PROB_BASIC_SALARY_STD WHERE P_BASIC_SALARY_STD = 0 AND PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
  UPDATE PA_SUMMARY_HTSV SET  P_TRANSPORTATION_ALLOW_STD = P_TRANSP_ALLOW_PROB_STD WHERE P_TRANSPORTATION_ALLOW_STD = 0 AND PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO;
  --工资计算完成! 用时
  IN_OUTPUT_STR :=  '   Payroll calculated success,used time ' ||
                  TRUNC((SYSDATE - V_TIME) * 24 * 60 * 60 + 0.005, 2) || ' seconds'; --秒
  COMMIT;

  EXECUTE IMMEDIATE 'alter session set cursor_sharing = exact';
  ----------------------------------------------操作 pa_summary_ 结束----------------------------------

END;
/
