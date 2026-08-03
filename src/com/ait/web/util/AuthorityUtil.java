package com.ait.web.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ait.sys.dao.RolesGroupDao;

/**
 * 权限判断
 * 
 * @author weizhengchen
 * 
 */
@Component
@SuppressWarnings("unchecked")
public class AuthorityUtil {

	@Autowired
	RolesGroupDao rolesGroupDao;

	/**
	 * 超级用户
	 */
	@Value("${super.user}")
	private String SUPER_USER;

	/**
	 * 合同担当
	 */
	@Value("${contract.user}")
	private String CONTRACT_USER;

	/**
	 * 超级人事用户
	 */
	@Value("${super.hr.user}")
	private String SUPER_HR_USER;

	/**
	 * 考勤担当
	 */
	@Value("${ar.user}")
	private String AR_USER;

	/**
	 * 工资担当
	 */
	@Value("${wage.user}")
	private String WAGE_USER;

	/**
	 * 保险担当
	 */
	@Value("${baoxian.user}")
	private String BAOXIAN_USER;

	/**
	 * 组织担当
	 */
	@Value("${organization.user}")
	private String ORG_USER;

	/**
	 * 人事担当
	 */
	@Value("${profile.user}")
	private String HR_USER;

	/**
	 * 招聘担当
	 */
	@Value("${recruit.user}")
	private String RECRUIT_USER;

	/**
	 * 评价担当
	 */
	@Value("${evs.user}")
	private String EVS_USER;

	/**
	 * 培训担当
	 */
	@Value("${training.user}")
	private String TRA_USER;
	
	/**
	 * 信息查看权限
	 */
	@Value("${information.user}")
	private String INFORMATION_USER;

	//2018/11 Start 新增考勤申请查看 页面
	/**
	 * 信息查看权限
	 */
	@Value("${ga.view.user}")
	private String GA_VIEW_USER;
	//2018/11 End 新增考勤申请查看 页面
		
	/**
	 * 首页权限
	 */
	@Value("${index.user}")
	private String INDEX_USER;

	/**
	 * 首页权限(预转正人员)
	 */
	@Value("${index.user.hr}")
	private String INDEX_USER_HR;

	/**
	 * 首页权限（未签合同，续签合同）
	 */
	@Value("${index.user.contract}")
	private String INDEX_USER_CONTRACT;

	/**
	 * 离职担当
	 */
	@Value("${dimission.user}")
	private String DIMISSION_USER;

	/**
	 * 财务报表下载权限
	 */
	@Value("${account.user}")
	private String ACCOUNT_USER;

	/**
	 * 财务报表txt下载权限
	 */
	@Value("${accounttxt.user}")
	private String ACCOUNTTXT_USER;
	
	/**
	 * 申报个人所得税权限
	 */
	@Value("${persontax.user}")
	private String PERSONTAX_USER;
	

	/**
	 * 获取权限id
	 * 
	 * @param personId
	 * @return
	 */
	public List getRoleId(String personId) {

		List roleIdList = this.rolesGroupDao.getRoleIdByPersonId(personId);
		return roleIdList;
	}

	/**
	 * 判断是否超级用户
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isSuperUser(String personId) {

		return this.isAuthority(personId, SUPER_USER);
	}

	/**
	 * 判断是否考勤员
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isArUser(String personId) {

		return this.getArSupervisor(personId);
	}
	
	//2018/11 Start 新增考勤申请查看 页面
	/**
	 * 判断是否总务查看担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isGaViewUser(String personId) {

		return this.isAuthority(personId, GA_VIEW_USER);
	}
	//2018/11 End 新增考勤申请查看 页面

	/**
	 * 判断是否工资担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isWageUser(String personId) {

		return this.isAuthority(personId, WAGE_USER);
	}

	/**
	 * 判断是否保险担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isBaoXianUser(String personId) {

		return this.isAuthority(personId, BAOXIAN_USER);
	}

	/**
	 * 判断是否超级人事担当-Hub
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isSuperHrUser(String personId) {

		return this.isAuthority(personId, SUPER_HR_USER);
	}

	/**
	 * 判断是否人事考勤担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isHrArUser(String personId) {

		return this.isAuthority(personId, AR_USER);
	}

	/**
	 * 判断是否组织担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isOrgUser(String personId) {

		return this.isAuthority(personId, ORG_USER);
	}

	/**
	 * 判断是否人事担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isHrUser(String personId) {

		return this.isAuthority(personId, HR_USER);
	}

	/**
	 * 判断是否招聘担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isRecruitUser(String personId) {

		return this.isAuthority(personId, RECRUIT_USER);
	}

	/**
	 * 判断是否评价担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isEvsUser(String personId) {

		return this.isAuthority(personId, EVS_USER);
	}

	/**
	 * 判断是否培训担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isTraUser(String personId) {

		return this.isAuthority(personId, TRA_USER);
	}
	
	/**
	 * 判断是否信息查看担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isInformationUser(String personId) {

		return this.isAuthority(personId, INFORMATION_USER);
	}

	/**
	 * 判断是否合同担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isContractUser(String personId) {

		return this.isAuthority(personId, CONTRACT_USER);
	}

	/**
	 * 判断是否离职担当
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isDimissionUser(String personId) {

		return this.isAuthority(personId, DIMISSION_USER);
	}

	/**
	 * 判断是否具有首页权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isIndexUser(String personId) {

		return this.isAuthority(personId, INDEX_USER);
	}

	/**
	 * 判断是否具有财务报表下载权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isAccountUser(String personId) {

		return this.isAuthority(personId, ACCOUNT_USER);
	}

	/**
	 * 判断是否具有财务报表下载权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isAccountTxtUser(String personId) {

		return this.isAuthority(personId, ACCOUNTTXT_USER);
	}
	
	
	/**
	 * 判断是否具有申报个人所得税权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isPersonTaxUser(String personId) {

		return this.isAuthority(personId, PERSONTAX_USER);
	}

	/**
	 * 判断是否具有首页权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isIndexUserHr(String personId) {

		return this.isAuthority(personId, INDEX_USER_HR);
	}

	/**
	 * 判断是否具有首页权限
	 * 
	 * @param personId
	 * @return 1:具有此权限 0：无此权限
	 */
	public int isIndexUserContract(String personId) {

		return this.isAuthority(personId, INDEX_USER_CONTRACT);
	}

	/**
	 * 判断用户是否具有某个权限
	 * 
	 * @param personId
	 * @param roleId
	 * @return 1:具有此权限 0：无此权限
	 */
	private int isAuthority(String personId, String roleId) {
		// 按','分权限
		String[] rolId = roleId.split(",");
		// 权限标识
		int flag = 0;
		List roleIdList = this.rolesGroupDao.getRoleIdByPersonId(personId);
		for (int j = 0; j < rolId.length; j++) {
			if (roleIdList.contains(rolId[j])) {
				flag = 1;
				break;
			}
			// 找到对应权限，跳出循环
			if (flag == 1) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 查询是否存在于考勤员表
	 * 
	 * @param personId
	 * @param roleId
	 * @return 1:具有此权限 0：无此权限
	 */
	private int getArSupervisor(String personId) {
		// 权限标识
		int flag = 0;
		List roleIdList = this.rolesGroupDao.getArSupervisor(personId);
		if (roleIdList != null && roleIdList.size() > 0) {
			flag = 1;
		}
		return flag;
	}

	/**
	 * 获取员工的总数 首页显示
	 */
	public int getTotalEmpCountForMain(Object obj) {
		return this.rolesGroupDao.getTotalEmpCountForMain(obj);
	}

	/**
	 * 获取在职的员工 首页显示
	 */
	public int getInCpnyTotalEmpCountForMain(Object obj) {
		return this.rolesGroupDao.getInCpnyTotalEmpCountForMain(obj);
	}

	/**
	 * 获取离职的员工 首页显示
	 */
	public int getLeftManTotalEmpCountForMain(Object obj) {
		return this.rolesGroupDao.getLeftManTotalEmpCountForMain(obj);
	}

	/**
	 * 获取新入职的员工 首页显示
	 */
	public int getNewManTotalEmpCountForMain(Object obj) {
		return this.rolesGroupDao.getNewManTotalEmpCountForMain(obj);
	}
}
