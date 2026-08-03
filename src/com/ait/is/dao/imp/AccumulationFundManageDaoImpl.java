package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ait.is.dao.AccumulationFundManageDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AccumulationFundManageDaoImpl extends SqlMapClientSupport
		implements AccumulationFundManageDao {
	// 公积金--对象增加List
	@Override
	public List getPaBenBaseNumList(Object object) throws SQLException {
		List result;
		try {
			result = this
					.queryForList("is.accumulationfundmanage.getTargetAddList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"getTargetAddList information Exception. ", e);
		} finally {

		}
		return result;
	}

	// 公积金--对象减少List
	@Override
	public List getviewCPFStopInsure(Object object) throws SQLException {
		List result;
		try {
			result = this
					.queryForList("is.accumulationfundmanage.getviewCPFStopInsure");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"getviewCPFStopInsure information Exception. ", e);
		} finally {

		}
		return result;
	}

	// 公积金--对象管理List
	@Override
	public List getviewBenshObjectManage(Object object) throws SQLException {
		List result;
		try {
			result = this
					.queryForList("is.accumulationfundmanage.getviewBenshObjectManage");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"getviewBenshObjectManage information Exception. ", e);
		} finally {

		}
		return result;
	}

	@Override
	public void createDataToPaBenManageAddBz(Object object) throws SQLException {
		this.insert("is.accumulationfundmanage.callPaCheckCalBase1", object);
		this.insert("is.accumulationfundmanage.callPaCheckCalBase2", object);
		this.insert("is.accumulationfundmanage.callPaCheckCalBase3", object);
		this.insert("is.accumulationfundmanage.callPaCheckCalBase4", object);
		this.insert("is.accumulationfundmanage.callPaCheckCalBase5", object);

	}

	@Override
	public String getMaxMonthPaParamItem() {

		try {
			return (String) this
					.queryForObject("is.accumulationfundmanage.getMaxMonthPaParamItem");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void allowPaBenJoinInsureUpdate(Object object) throws Exception {
		try {
			this.update("is.accumulationfundmanage.allowPaBenJoinInsureUpdate",
					object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"allowPaBenJoinInsureUpdate information Exception. ", e);
		}
	}

	public List getAllowPaBenJoinInsureUpdate(Object object) throws Exception {
		List result;
		try {
			result = this
					.queryForList("is.accumulationfundmanage.getAllowPaBenJoinInsureUpdate");
		} catch (Exception e) {
			throw new GlRuntimeException(
					"getAllowPaBenJoinInsureUpdate Exception. ", e);
		}
		return result;
	}

	/**
	 * 
	 * TODO 修改ManageAdd信息
	 * 
	 * @param param
	 * @throws GlRuntimeException
	 */
	public void updatePaBenManageAddInfo(Object param) throws Exception {
		try {
			this.update("is.accumulationfundmanage.updateBenshObjectManage",
					param);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"update BenshObjectManage information Exception. ", e);
		}
	}

	@Override
	public void allowBenshObjectManageNumUpdate(Object object)
			throws SQLException {
		this.update(
				"is.accumulationfundmanage.allowBenshObjectManageNumUpdate",
				object);

	}

	@Override
	public void allowPaBenStopInsureUpdate(Object object) throws Exception {
		try {
			this.update("is.accumulationfundmanage.allowPaBenStopInsureUpdate",
					object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"allowPaBenStopInsureUpdate information Exception. ", e);
		}

	}

	@Override
	public List getAllowPaBenStopInsureUpdate(Object object) {
		List result;
		try {
			result = this
					.queryForList("is.accumulationfundmanage.getAllowPaBenStopInsureUpdate");
		} catch (Exception e) {
			throw new GlRuntimeException(
					"getAllowPaBenStopInsureUpdate Exception. ", e);
		}
		return result;
	}

	@Override
	public void updatePaBenManageStopInfo(Object param) throws Exception {
		try {
			this.update(
					"is.accumulationfundmanage.updateStopBenshObjectManage",
					param);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"update StopBenshObjectManage information Exception. ", e);
		}

	}

	@Override
	public void allowStopBenshObjectManageNumUpdate(Object object)
			throws SQLException {
		this.update(
				"is.accumulationfundmanage.allowStopBenshObjectManageNumUpdate",
				object);

	}

	/**
	 * 对象增加 修改时没有修改而返回
	 */
	@Override
	public void AddBenshObjectManageNotUpdate(Object object)
			throws SQLException {
		try {
			this.update(
					"is.accumulationfundmanage.AddBenshObjectManageNotUpdate",
					object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"AddBenshObjectManageNotUpdate information Exception. ", e);
		}
	}

	/**
	 * 对象减少 修改时没有修改而返回
	 */
	@Override
	public void StopBenshObjectManageNotUpdate(Object object)
			throws SQLException {
		try {
			this.update(
					"is.accumulationfundmanage.StopBenshObjectManageNotUpdate",
					object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException(
					"StopBenshObjectManageNotUpdate information Exception. ", e);
		}
	}

	@Override
	public void deleteBenshObjectManageAdd(Object object) throws SQLException {
		this.delete("is.accumulationfundmanage.deleteBenshObjectManageAdd",
				object);

	}

	@Override
	public void deleteBenshObjectManageDel(Object object) throws SQLException {
		this.delete("is.accumulationfundmanage.deleteBenshObjectManageDel",
				object);
	}

	// 清空临时表：PA_BENHS_MANAGE_ADD_IMP @author wendi
	@Override
	public void deleteManageAddImp() {
		try {
			this.delete("is.accumulationfundmanage.deleteManageAddImp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 查询所有的基数管理临时表数据
	@Override
	public List<Map> getManageAddImplList() {
		try {
			return this
					.queryForList("is.accumulationfundmanage.getManageAddImplList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map> checkManageAdd(Object object) {
		try {
			return this.queryForList(
					"is.accumulationfundmanage.checkManageAdd", object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 更新基数管理数据-公积金
	@Override
	public void updateManageAdd(Object object) {
		try {
			this.update("is.accumulationfundmanage.updateManageAdd", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insertManageAdd(Object object) {
		try {
			this.insert("is.accumulationfundmanage.insertManageAdd", object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteManageDelImp() {
		try {
			this.delete("is.accumulationfundmanage.deleteManageDelImp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Map> getManageDelImplList() {
		try {
			return this
					.queryForList("is.accumulationfundmanage.getManageDelImplList");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map> checkManageDel(Object object) {
		try {
			return this.queryForList(
					"is.accumulationfundmanage.checkManageDel", object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Map> checkManage(Object object) {
		try {
			return this.queryForList(
					"is.accumulationfundmanage.checkManage", object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void updateManageDel(Object object) {
		try {
			this.update("is.accumulationfundmanage.updateManageDel", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateManage(Object object) {
		try {
			this.update("is.accumulationfundmanage.updateManage", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insertManageDel(Object object) {
		try {
			this.insert("is.accumulationfundmanage.insertManageDel", object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertManage(Object object) {
		try {
			this.insert("is.accumulationfundmanage.insertManage", object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteManageImp() {
		try {
			this.delete("is.accumulationfundmanage.deleteManageImp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public List<Map> getManageImplList() {
		try {
			return this
					.queryForList("is.accumulationfundmanage.getManageImplList");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
