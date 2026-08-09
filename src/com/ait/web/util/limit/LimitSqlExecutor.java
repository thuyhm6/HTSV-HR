package com.ait.web.util.limit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.ait.web.util.limit.dialect.Dialect;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class LimitSqlExecutor extends SqlExecutor {
	
	private static final Log logger=LogFactory.getLog(LimitSqlExecutor.class);
	
	private boolean enableLimit=true;

	private Dialect dialect;
	
	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public void executeQuery(StatementScope request, Connection conn, String sql,
			Object[] parameters, int skipResults, int maxResults, RowHandlerCallback callback)
			throws SQLException {

		if( (skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS) && supportsLimit() )
		{
			sql = dialect.getLimitString(sql, skipResults, maxResults);

			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
		logSql(request, sql, parameters);
		super.executeQuery(request, conn, sql, parameters, skipResults, maxResults, callback);
	}

	@Override
	public int executeUpdate(StatementScope statementScope, Connection conn, String sql, Object[] parameters)
			throws SQLException {
		logSql(statementScope, sql, parameters);
		return super.executeUpdate(statementScope, conn, sql, parameters);
	}

	@Override
	public void addBatch(StatementScope statementScope, Connection conn, String sql, Object[] parameters)
			throws SQLException {
		logSql(statementScope, sql, parameters);
		super.addBatch(statementScope, conn, sql, parameters);
	}

	/**
	 * In log dạng: [resource file # statement id] SQL đã thay tham số vào vị trí "?"
	 */
	private void logSql(StatementScope statementScope, String sql, Object[] parameters) {
		if (logger.isDebugEnabled()) {
			MappedStatement statement = statementScope.getStatement();
			logger.debug("[" + statement.getResource() + " # " + statement.getId() + "] " + bindParameters(sql, parameters));
		}
	}

	private String bindParameters(String sql, Object[] parameters) {
		if (parameters == null || parameters.length == 0 || sql.indexOf('?') < 0) {
			return sql;
		}
		StringBuilder sb = new StringBuilder(sql.length() + 32);
		int paramIndex = 0;
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			if (c == '?' && paramIndex < parameters.length) {
				sb.append(formatParameter(parameters[paramIndex++]));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private String formatParameter(Object value) {
		if (value == null) {
			return "NULL";
		}
		if (value instanceof Number || value instanceof Boolean) {
			return value.toString();
		}
		if (value instanceof Date) {
			return "'" + value.toString() + "'";
		}
		return "'" + value.toString().replace("'", "''") + "'";
	}

	public boolean supportsLimit(){
		if(enableLimit&&dialect!=null)
		{
			return dialect.supportsLimit();
		}
		return false;
	}
}
