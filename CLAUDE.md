# CLAUDE.md — Hanwha HTSV HR System

> Quy tắc trả lời: xem [AGENTS.md](AGENTS.md) — **luôn trả lời/bình luận bằng tiếng Việt**, ngắn gọn, giữ thuật ngữ kỹ thuật tiếng Anh. Trước khi sửa nhiều file thì giải thích ngắn; sau khi sửa thì tóm tắt thay đổi + lý do.

## 1. Tổng quan

Hệ thống HR web (legacy, gốc từ LDCC/AIT Trung Quốc) cho Hanwha Techwin Security Vietnam (HTSV): nhân sự, chấm công/tăng ca (AR), lương (PA), ESS (employee self-service), phê duyệt, đào tạo, đánh giá…
Comment cũ trong code phần lớn là **tiếng Trung**; code mới comment tiếng Việt/Anh.

| Thành phần | Phiên bản / ghi chú |
|---|---|
| Java | JDK 1.8.0_231, nhưng compile `-source 1.7 -target 1.7` → **không dùng lambda, stream, `java.time`** |
| Web | Servlet 2.5, Tomcat (`D:/Java/apache-tomcat`), JSP + JSTL 1.2 |
| Framework | Spring 3.1.0 MVC (annotation `@Controller`/`@Service`/`@Repository`, `@Autowired`) |
| ORM | **iBATIS 2.3.4** (không phải MyBatis) qua `SqlMapClientSupport` |
| DB | Oracle (c3p0 pool), driver ojdbc nằm trong `apache-tomcat/lib` |
| UI | DWZ (jQuery UI framework: `navTab`, `$.pdialog`, `alertMsg`), DataTables, My97 `WdatePicker` |
| Excel | POI 5.2.2 (`WEB-INF/lib`) + stack cũ POI 3.8/JXLS 1.0 cô lập trong `WEB-INF/isolated-lib` |
| Report | JasperReports 3.7.6, iText 2.1.7, jFreeChart |
| Không có | Maven/Gradle, unit test thực sự — jar quản lý thủ công trong `WebRoot/WEB-INF/lib` |

## 2. Build & Run (VS Code)

- **Build**: task `Build: Compile Java` → [.vscode/scripts/build.bat](.vscode/scripts/build.bat)
  - Incremental: chỉ compile `.java` mới hơn `.class` (qua `find-stale-sources.ps1`), output vào `WebRoot/WEB-INF/classes`.
  - Copy toàn bộ resource không phải `.java` (sqlmap XML, `.properties`) từ `src/` sang `classes/` bằng robocopy.
  - Bắt buộc `-g` (AOP binding cần local variable table, thiếu sẽ lỗi `AmbiguousBindingException`).
- **Clean rebuild**: task `Build: Clean Rebuild` (`build.bat clean`) — dùng khi rename/xóa class hoặc gặp `NoSuchMethodError`/`NoClassDefFoundError`.
- **Run/Debug**: launch config `Debug Hanwha_HTSV (launch Tomcat...)` trong [.vscode/launch.json](.vscode/launch.json); `CATALINA_BASE` = `.tomcat/`, port **8081**, context root `/` trỏ tới `WebRoot` ([.tomcat/conf/Catalina/localhost/ROOT.xml](.tomcat/conf/Catalina/localhost/ROOT.xml)).
  - `reloadable="false"` → sửa Java/SqlMap XML/properties phải **build + restart Tomcat**. JSP thì tự reload.
- Lệnh build thủ công (PowerShell): `& .\.vscode\scripts\build.bat` — kết thúc bằng `BUILD OK` nếu thành công.
- `build6.xml`, `server.xml` ở root là file cũ của Eclipse/MyEclipse, **không dùng**.

## 3. Cấu trúc thư mục

```
src/
  com/ait/<module>/
    action/        Controller (*Ctroller.java)
    service/       Interface (*Ser.java) + impl/ (*SerImpl.java; đôi khi *SerImp / thư mục imp/)
    dao/           Interface (*Dao.java) + impl/ (*DaoImpl.java) + sql/ (sqlXxx.xml – iBATIS SqlMap)
    bean/          (tùy module) JavaBean
  com/ait/web/     Hạ tầng dùng chung: filter, login, i18n, messages, taglib, task (scheduler), util, mail
  com/ait/Interface/ ParentCtroller, ParentBean
  *.properties     system.properties, mail.properties, authority.properties, typecode.properties
WebRoot/
  WEB-INF/view/<module>/<chức năng>/*.jsp   View (prefix /WEB-INF/view/, suffix .jsp)
  WEB-INF/view/inc/                          JSP include dùng chung (initTaglibs.jsp, initPagination*.jsp, includeButton*.jsp…)
  WEB-INF/conf/                              spring-servlet.xml, sqlMapConfig.xml, log4j.xml
  WEB-INF/tld/ait.tld                        Custom tag <ait:...>
  resources/                                 js, css, images, template (Excel template), dataTables…
tools/legacy-xls-worker/                     Source worker chuyển đổi .xls cũ (build ra WEB-INF/isolated-lib/legacy-xls-worker.jar)
```

**Module chính** (`com/ait/*`): `ar` (chấm công/ca/tăng ca), `ess` (self-service, phê duyệt, xem theo phòng ban), `hrm` (hồ sơ NV, hợp đồng, tuyển dụng), `pa`/`paEcc` (lương), `sys` (login, menu, quyền, code), `org` (tổ chức), `edu` (đào tạo), `ev`/`evs` (đánh giá), `disc` (autoExcel – export Excel theo SQL cấu hình), `affirm` (luồng duyệt), `report`, `inct`, `laborUnion`, `promoter`, `empsubject`, `interf`/`mac` (interface SAP, máy chấm công).

## 4. Pattern code (bắt buộc tuân theo khi thêm chức năng)

Luồng: **JSP → Controller → Service → DAO → SqlMap XML → Oracle**. Dữ liệu truyền chủ yếu bằng `LinkedHashMap` (không dùng DTO), key là **TÊN CỘT IN HOA**.

### Controller — `com/ait/<module>/action/XxxCtroller.java`
```java
@Controller
@RequestMapping(value = "/ess/viewDept")
public class viewDeptPerCtroller {
	@Autowired private ViewDeptPerSer ViewDeptPerSer;
	@Autowired private ToolMenuSer toolMenuSer;

	@RequestMapping(value = "/viewOtApplyPersonalList")
	public ModelAndView viewOtApplyPersonalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("personList", ViewDeptPerSer.getXxxList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, ViewDeptPerSer.getXxxListCnt(request));
		modelMap.put("toolbarInfo", toolMenuSer.getToolMenu(request));   // toolbar theo menu/quyền
		return new ModelAndView("/ess/viewDept/viewOtApplyPersonalList", modelMap);
	}
}
```
- Tên class viết sai chính tả **`Ctroller`** là quy ước của dự án → giữ nguyên khi tạo mới.
- Trả JSON/AJAX: `@ResponseBody` + `String`/`Map` (Jackson 1.x `MappingJacksonHttpMessageConverter`).
- Có thể kế thừa `com.ait.Interface.ParentCtroller` (`initMenu()` đưa `toolbarInfo` + param `seach_*` vào model).

### Service — `service/XxxSer.java` + `service/impl/XxxSerImpl.java` (`@Service`)
- Lấy user: `AdminBean admin = SessionUtil.getLoginUserFromSession(request);` (session key `"LoginUser"`; có `getPersonId()`, `getCpnyId()`, `getAdminID()`, `getDeptNo()`, `getLanguagePreference()`…).
- Lấy tham số tìm kiếm: `ObjectBindUtil.getRequestParamData(request, "seach_")` → bỏ prefix `seach_` (**chú ý: viết `seach_`, không phải `search_`**), trả `LinkedHashMap`.
- Phân trang: `if (UiUtil.getPageNum(request) > 0) dao.getXxx(map, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)); else dao.getXxx(map);`

### DAO — `dao/XxxDao.java` + `dao/impl/XxxDaoImpl.java`
```java
@Repository
public class ViewDeptPerDaoImpl extends SqlMapClientSupport implements ViewDeptPerDao {
	public List getPersonList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1)
				returnList = this.queryForList("ess.deptper.getOtPersonList", obj, currentPage, pageSize);
			else
				returnList = this.queryForList("ess.deptper.getOtPersonList", obj);
		} catch (SQLException e) { e.printStackTrace(); }
		return returnList;
	}
}
```
- Hàm có sẵn: `queryForObject`, `queryForList` (có overload phân trang – dùng Oracle dialect `LimitSqlExecutor`), `insert`, `update`, `delete`, `insertForList/updateForList/deleteForList` (batch).
- **Transaction** (AOP trên `execution(* com.ait.*.dao.*.*(..))`) dựa theo **tên method DAO**:
  - `save*`, `add*`, `insert*`, `update*`, `change*`, `delete*`, `remove*` → `REQUIRED`, rollback khi `Exception`.
  - `find*`, `get*`, còn lại → read-only. ⇒ Method ghi dữ liệu **phải** đặt tên theo các prefix trên.

### SqlMap — `dao/sql/sqlXxx.xml` (iBATIS 2)
```xml
<sqlMap namespace="ess.deptper">
  <select id="getOtPersonList" parameterClass="java.util.Map" resultClass="java.util.LinkedHashMap">
    SELECT HR.PERSON_ID, HR.EMPID, ...
      FROM HR_EMPLOYEE HR, HR_DEPARTMENT HD
     WHERE HR.DEPTNO = HD.DEPTNO
    <isNotEmpty prepend="AND" property="CPNY_ID"><![CDATA[ HR.CPNY_ID = #CPNY_ID:VARCHAR# ]]></isNotEmpty>
  </select>
</sqlMap>
```
- Cú pháp tham số iBATIS 2: **`#PARAM:VARCHAR#`** (bind) và `$PARAM$` (nối chuỗi). **Không dùng `#{param, jdbcType=...}` của MyBatis** — nếu yêu cầu đưa vào dạng `#{year, jdbcType=VARCHAR}` thì chuyển thành `#YEAR:VARCHAR#`.
- Dynamic tag: `<isNotEmpty>`, `<isEqual>`, `<isNotNull>`, `<iterate>`, `<include refid="..."/>`; dùng `<![CDATA[ ]]>` cho điều kiện có `<`, `>`.
- `useStatementNamespaces="true"` → gọi bằng `"namespace.id"`.
- **File SqlMap mới phải đăng ký** trong [WebRoot/WEB-INF/conf/sqlMapConfig.xml](WebRoot/WEB-INF/conf/sqlMapConfig.xml): `<sqlMap resource="com/ait/<module>/dao/sql/sqlXxx.xml" />`.
- Logic nghiệp vụ nặng nằm trong **Oracle function/package** (vd. `GET_AR_OT_TOTAIL(date 'YYYY/MM/DD', cpnyId, personId, itemCode)`, `GET_AR_OT_TOTAIL_INTERVAL(fromDate, toDate, cpnyId, personId, itemCode)`). Ngày truyền từ UI dạng `dd/MM/yyyy` → convert `TO_CHAR(TO_DATE(#STIME:VARCHAR#,'DD/MM/YYYY'),'YYYY/MM/DD')`.
- Dữ liệu nhạy cảm (IDCARD_NO, CELLPHONE, HOME_PHONE…) **đã bỏ** `PKG_DECRYPT.DECRYPT_DES` → dùng trực tiếp cột gốc.
- Bảng hay dùng: `HR_EMPLOYEE` (PERSON_ID, EMPID, CPNY_ID, DEPTNO), `HR_DEPARTMENT`, `SY_CODE` (code master: `CODE_NO`, `PARENT_CODE_NO`, `DESCRIPTION`, `ACTIVITY`), các bảng `AR_*`, `ESS_*`, `PA_*`. Loại trừ NV test: `HR.EMPID NOT LIKE '%11111%'`.

### JSP — `WebRoot/WEB-INF/view/<module>/<func>/xxx.jsp`
- Header chuẩn:
  ```jsp
  <%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>   <%-- spring, c, fn, fmt, ait --%>
  ```
- JSP là **fragment load vào DWZ navTab/dialog** (không có `<html>`): selector luôn kèm context `$("#id", navTab.getCurrentPanel())`.
- Form tìm kiếm: `<div class="pageHeader"><form onsubmit="return navTabSearch(this);" action="/module/func/xxxList" method="post">`, input tên `seach_XXX`; kết quả trong `<div class="pageContent">`, bảng `class="list"` + DataTables.
- Popup: `$.pdialog.open(url, "dialogId", title, {width:800,height:400,mask:true})`; xác nhận: `alertMsg.confirm(...)`; AJAX submit: `navTabAjaxDone`/`dialogAjaxDone`.
- Custom tag hay dùng: `<ait:deptList>`, `<ait:deptTreeIcon>`, `<ait:SelectSyCodeByCpnyID parentNo="..." cnpyID="${LoginUser.cpnyId}" limit="all"/>` (dropdown từ `SY_CODE`), xem [ait.tld](WebRoot/WEB-INF/tld/ait.tld) và `com/ait/web/taglib`.
- Biến session trong JSP: `${LoginUser.cpnyId}`, `${LoginUser.adminID}`, `${defaultCpny}`.
- Text giao diện: `<spring:message code="..."/>` kèm comment nghĩa (thường tiếng Trung, vd. `<!--部门：-->`). Code mới có thể hardcode tiếng Việt nếu chưa có key, nhưng ưu tiên thêm key i18n.

## 5. i18n

- 4 file UTF-8 (đọc trực tiếp, không cần native2ascii): `src/com/ait/web/messages/messages_{vi_VN,en_US,ko_KR,zh_CN}.properties`.
- Thêm key mới → thêm vào **cả 4 file**. Key theo dạng `<module>.<screen>.<name>` (vd. `ar.arForDeptCountInfoList.FADINGJIEJIARI.b`).
- Java: `Messages.getMessage(request, "key")`; `TipMessage` cho thông báo kết quả.

## 6. Menu, quyền, Excel

- Menu/toolbar lưu trong DB; `toolMenuSer.getToolMenu(request)` (dựa trên param `menuNo`) hoặc `getToolMenuForNo(request, "<menuNo>")`. Thêm màn hình mới cần đăng ký menu + quyền trong DB (bảng SY menu/screen grant) — không có trong code.
- Quyền dữ liệu theo phòng ban/quản lý: `AuthorityUtil`, tham số `limit="manager"` trên tag phòng ban, `AR_SUPERVISIOR_INFO = admin.getPersonId()` trong query.
- **Export Excel chung**: `/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=<id>&CPNY=...` (`RetrieveMasterListCtroller`) — SQL export cấu hình trong DB theo `SQL_SEQMEAN`; JSP gọi `downloadExcel(formId, url, originalAction)`.
- Excel theo template: template trong `WebRoot/resources/template/`; `.xls` dùng JXLS 1.0 tag (`jx:forEach`) qua `ExcelTemplateUtil` + `LegacyXlsClassLoader` (child-first, nạp `WEB-INF/isolated-lib`) — **không đưa POI 3.8/JXLS vào `WEB-INF/lib`** (xung đột với POI 5.x). Code tại `com/ait/pa/service/imp/excelUtil/`.
- Scheduler: `@Scheduled` trong `com/ait/web/task` (đọc dữ liệu máy chấm công, đồng bộ SAP, gửi mail…), bật bằng `<task:annotation-driven/>`.

## 7. Cấu hình & bảo mật

- [WebRoot/WEB-INF/conf/spring-servlet.xml](WebRoot/WEB-INF/conf/spring-servlet.xml): dataSource (nhiều block DB comment sẵn — DB dev đang bật là block "越南开发数据库"), iBATIS, transaction, view resolver, interceptor.
- `spring-servlet_real.xml`, `log4j_real.xml`, `system_real.properties`: cấu hình production. `spring-servlet_real.xml` và `database.sql` **bị gitignore — không commit, không in credential ra output**.
- Không commit: `WebRoot/WEB-INF/classes/`, `.tomcat/`, `.svn/`, ảnh NV `resources/photo/`, `resources/reportFile/`, file `*Personal Data Card*`.
- `LoginInterceptor.preHandle` hiện **luôn `return true`** (chỉ forward về `/` khi hết session nhưng vẫn cho đi tiếp), `UserAuthenFilter` chỉ xử lý `/index.jsp` → không có cơ chế chặn truy cập thực sự; Service luôn tự lấy `AdminBean` và xử lý trường hợp `null` khi cần.

## 8. Lưu ý khi phát triển

- **Tuân theo format code hiện tại** của file đang sửa (tab indent, `LinkedHashMap` raw type, `@SuppressWarnings("unchecked")`, style SQL Oracle cũ `FROM A, B WHERE A.X = B.X`). Không refactor/format lại code không liên quan.
- Thêm cột mới vào màn hình list thường phải sửa đồng bộ: SQL `select` (alias cột) → JSP (`<th>` + `<td>${row.ALIAS}</td>`) → (nếu có) SQL export Excel theo `SQL_SEQMEAN`.
- Các file lớn (Controller/ServiceImpl/SqlMap hàng nghìn dòng): dùng Grep theo `id="..."`/`@RequestMapping` thay vì đọc toàn bộ.
- Tìm màn hình từ URL: `/ess/viewDept/xxx` → `@RequestMapping("/ess/viewDept")` trong `com/ait/ess/action/*` → `ModelAndView("/ess/viewDept/xxx")` → `WEB-INF/view/ess/viewDept/xxx.jsp`.
- Kiểm tra compile sau khi sửa Java: chạy `build.bat`, phải ra `BUILD OK`. SQL chỉ kiểm chứng được khi chạy app (không có DB local/test).
- File rác ở root (`hs_err_pid*.log`, `replay_pid*.log`, `xxyyzzz.md` – ghi chú yêu cầu tạm) không thuộc source.
