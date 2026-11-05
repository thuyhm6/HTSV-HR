CREATE OR REPLACE PROCEDURE PA_SUMMARY_MANAGE_P(IN_PAY_SCHEDULE_NO  IN NUMBER, --工资支付计划No
                                             IN_CPNY_ID       IN VARCHAR2)
  AUTHID CURRENT_USER IS
  /******************************************************************************
     NAME:       PA_SUMMARY_MANAGE_P
     PURPOSE:    工资生成工资项目的公式 第一步 先将所有可以产生公式的项目和金额放进 PA_CAL_FORMULAR

     REVISIONS:
     Ver        Date        Author           Description
     ---------  ----------  ---------------  ------------------------------------
     1.0        2015/12/24   Esprince       1. Created this procedure.

  ******************************************************************************/
  VAR_INSERT_STR    VARCHAR2(20000); --拼接插入语句sql
  VAR_DELETE_STR    VARCHAR2(20000); --拼接更新语句sql
  --VAR_SALARY_DISTIN_NO VARCHAR2(20);--工资区分号
  VAR_APPLY_TYPE VARCHAR2(20);--工资项目类型区分号 1为工资2为春节金3为中秋金
  VAR_LOG_FLAG       NUMBER :=1;
  -- 工资项目游标
  CURSOR CUS_ITEM IS
    SELECT PA_ITEM.ITEM_NO, ITEM_ID,GET_GLOBAL_NAME(PA_ITEM_PARAM.ITEM_NO,'zh')ITEM_NAME
      FROM PA_ITEM, PA_ITEM_PARAM
     WHERE PA_ITEM.ITEM_NO = PA_ITEM_PARAM.ITEM_NO
       AND CPNY_ID = IN_CPNY_ID
       AND PA_ITEM_PARAM.APPLY_TYPE = 1
       AND PA_ITEM_PARAM.HAVE_FORMULAR = 1
       AND PA_ITEM.ACTIVITY = 1
     ORDER BY CALCU_ORDER;
BEGIN
  EXECUTE IMMEDIATE 'alter session set cursor_sharing = force';
  DBMS_OUTPUT.ENABLE(5000000);
  --清楚当月的数据
  VAR_DELETE_STR := ' DELETE FROM PA_CAL_FORMULAR_'||IN_CPNY_ID||
                            ' WHERE PAY_SCHEDULE_NO='''||IN_PAY_SCHEDULE_NO||'''  ';
  EXECUTE IMMEDIATE VAR_DELETE_STR ;

  /*根据工资支付计划No获取对应的工资区分*/
 /* SELECT PPS.SALARY_DISTIN_NO INTO VAR_SALARY_DISTIN_NO
  FROM PA_PAY_SCHEDULE PPS
  WHERE PPS.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO
  AND PPS.ACTIVITY = 1;*/

  /*循环工资项目, 创建建表语句*/
  FOR ITEM_ROW IN CUS_ITEM LOOP
    VAR_INSERT_STR := ' INSERT INTO PA_CAL_FORMULAR_'||IN_CPNY_ID||'(PA_CAL_FORMULAR_NO,PAY_SCHEDULE_NO, '||
                              ' PERSON_ID,ITEM_NO,ITEM_ID,ITEM_NAME,CAL_VALUE,CREATE_DATE,CREATED_BY,CREATED_IP,IS_CAL_BY_DAY) '||
                              ' SELECT PA_CAL_FORMULAR_'||IN_CPNY_ID||'_SEQ.NEXTVAL,PA_SUMMARY.PAY_SCHEDULE_NO,'||
                              ' PA_SUMMARY.PERSON_ID,'''||ITEM_ROW.ITEM_NO||''','''||ITEM_ROW.ITEM_ID||''','||
                              ' '''||ITEM_ROW.ITEM_NAME||''',PA_SUMMARY.'||ITEM_ROW.ITEM_ID||','||
                              ' SYSDATE,''SYS'',NULL,''N'''||
                              ' FROM PA_SUMMARY_'||IN_CPNY_ID||' PA_SUMMARY '||
                              ' WHERE PA_SUMMARY.PAY_SCHEDULE_NO = '''||IN_PAY_SCHEDULE_NO||'''';
    IF VAR_LOG_FLAG > 0 THEN
        DBMS_OUTPUT.PUT_LINE(VAR_INSERT_STR);
    END IF;
    EXECUTE IMMEDIATE VAR_INSERT_STR ;
  END LOOP;

  IF IN_CPNY_ID = 'HTSV'THEN
    --更新公式到公式表格
    UPDATE PA_CAL_FORMULAR_HTSV PCFT
        SET PCFT.FORMULAR_VALUE = PA_SUMMARY_FORMULAR_HTSV_F(IN_PAY_SCHEDULE_NO,PCFT.PERSON_ID,PCFT.ITEM_NO)
     WHERE PCFT.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO ;
    --更新日割公式标记
    UPDATE PA_CAL_FORMULAR_HTSV PCFT
        SET PCFT.IS_CAL_BY_DAY = SUBSTR(PCFT.FORMULAR_VALUE,INSTR(PCFT.FORMULAR_VALUE,'#')+1,INSTR(PCFT.FORMULAR_VALUE,'#')),
             PCFT.FORMULAR_VALUE = SUBSTR(PCFT.FORMULAR_VALUE,0,INSTR(PCFT.FORMULAR_VALUE,'#')-1)
     WHERE PCFT.PAY_SCHEDULE_NO = IN_PAY_SCHEDULE_NO ;
  END IF;

  EXECUTE IMMEDIATE 'alter session set cursor_sharing = exact';
END;
/
