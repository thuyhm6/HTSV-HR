package com.ait.mac.conn;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * SST考勤机连接
 * @author Administrator
 *
 */
public class ConnBean {

	private static final Logger logger = Logger.getLogger(ConnBean.class);

	/**
	 * 获取数据库连接
	 * @param cardArea
	 * @return
	 */
	public static Connection getConn(String cpnyId) {
        String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        String userName = "YOUR_DB_USER";
        String passwrod = "YOUR_DB_PASSWORD";
        String url = "jdbc:microsoft:sqlserver://YOUR_DB_HOST\\WISENETACS:1433;DatabaseName=WACS";
        Connection conn = null;
        try{
			Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, passwrod);
        } catch (Exception e) {
        	e.printStackTrace();
			logger.error(e.getMessage(), e);
		} 
//        catch (SQLException sqlex) {
//			logger.error(sqlex.getMessage(), sqlex);
//        }
        return conn;
	}


	/**
	 * 获取食堂数据库连接
	 * @param cardArea
	 * @return
	 */
	public static Connection getConnMySql() {
        String driver = "com.mysql.jdbc.Driver";
        String userName = "YOUR_DB_USER";
        String passwrod = "";
        String url = "jdbc:mysql://YOUR_DB_HOST:17770/ZKNET?useUnicode=true&characterEncoding=utf8";
        Connection conn = null;
        try{
			Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, passwrod);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
        }
        return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param rs
	 * @param stmt
	 * @param pstmt
	 * @param conn
	 */
    public static void close(ResultSet rs, Statement stmt,
            PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
                pstmt = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
    }
    
    /**
     * 获取数据库ip
     * @param cardArea
     * @return
     */
    public static String getIp(String cpnyId){
    	String ip = "";
    	if("SPC_DL".equals(cpnyId)){
    		ip="YOUR_DB_HOST:1433;DatabaseName=zkteco_database";
    	}else{
    		ip="YOUR_DB_HOST:1433;DatabaseName=zkteco";
    	}
    	return ip;
    }
    
    /**
     * 获取数据库密码
     * @param cardArea
     * @return
     */
    public static String getPwd(String cpnyId){
    	String pwd = "";
    	if("SPC_DL".equals(cpnyId)){
    		pwd="YOUR_DB_PASSWORD";
    	}else{
    		pwd="YOUR_DB_PASSWORD";
    	}
    	return pwd;
    }
    
    public static void main(String[] args){
    	/*Connection conn = ConnBean.getConnMySql();
		PreparedStatement ps;
		try {
			String  sql =   " SELECT OID,CID,RID,RID_NAME,TID,EMPID,EATNAME,WORKCOMPANY,DEPARTNAME, " + 
			"       NAME,TITLE,EATDATE,DTM,AMT,STAT,CARDTYPE,MEMO,SID " + 
			"  FROM TSOE_2007.eatevent_history " + 
			" WHERE eatdate > DATE_SUB(CURDATE(),INTERVAL 2 DAY) ";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	System.out.print(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5) + rs.getString(6));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
    	Connection con = ConnBean.getConn("");
    	System.out.println(con);
    
//    	String str = "201504";
//    	System.out.print(str.substring(0,4));
    }
}
