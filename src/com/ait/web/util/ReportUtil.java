/**
 * 
 * @fileName ReportUtil.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
package com.ait.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.jxcell.CellException;
import com.jxcell.View;

/**
 * 加密util
 */
public class ReportUtil {

	final static int PDF = 0;

    final static int EXCEL = 1;

    final static int HTML = 2;

    /**
     * @param result:
     *            Ctr返回的结果集
     * @param reportParams:
     *            传给jasper的参数集
     * @param reportID:
     *            报表的名字
     * @param fileType:
     *            生成报表的格式 0-PDF,1-XLS,2-HTML
     * @param fileName:
     *            生成报表过程中生成临时文件名
     * @return BufferedInputStream类型
     * @throws Exception
     */
    public static BufferedInputStream generateReport(List result,
            Map reportParams, String reportID, int fileType, String fileName)
            throws Exception {

        //Make JRDataSource
        JRMapArrayDataSource jrds = new JRMapArrayDataSource(result.toArray());
        result = null;
        BufferedInputStream bis = null;
        
        FileOutputStream os = new FileOutputStream(new File(fileName));

        //Get JasperFile
        String jasperDir = "report.jasper.folder"; //路径

        //Compile jrxml file to jasper
        //TODO:After System Open,delete the statement below
        //String jasperFile = JasperCompileManager
        //        .compileReportToFile(jasperDir + reportID + ".jrxml");

        File reportFile = new File(jasperDir + reportID + ".jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

        //Make JasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParams, jrds);

        //Prepare Export File:pdf or xls or html
        try {
            switch (fileType) {
            case PDF:
                JRPdfExporter pdfExporter = new JRPdfExporter();
                pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
                pdfExporter.exportReport();
                break;
            case EXCEL:
                JRXlsExporter xlsExporter = new JRXlsExporter();
                xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.FALSE);
                xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
                xlsExporter.exportReport();
                break;
            case HTML:
                JRHtmlExporter htmlExporter = new JRHtmlExporter();
                htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"common/images/");
                htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
                htmlExporter.exportReport();
                break;
            default:
                break;
            }
            
            //backup old
            //bis = new BufferedInputStream(new FileInputStream(fileName));
            return bis;
        } finally {
            os.close();
        }
        
    }

    /**
     * @return  生成临时文件名
     */
    public static String generateTempFileName(String tmpDir,String name) {
        String tmpFile = "";
        try {
            tmpFile = tmpDir + "\\" + name + "_" + System.currentTimeMillis() + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpFile;
    }
    /**
     * @return  生成临时文件名
     */
    public static void rebuildExcel(String tmpFilePath, String exportFilePath) {
    	try {
			POIFSFileSystem fs = new  POIFSFileSystem( new  FileInputStream(tmpFilePath));
			org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new  org.apache.poi.hssf.usermodel.HSSFWorkbook(fs); 
			int sheetNum = wb.getNumberOfSheets();
			if(sheetNum>0)
			{
				HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0); 
				int lastRowNum = sheet.getLastRowNum();
				HSSFRow row = sheet.getRow(lastRowNum);  
				int i = row.getLastCellNum();
				
				HSSFCell cell = row.createCell((short) i); 
				cell.setCellValue(" " );
			}else
			{
				HSSFSheet sheet = (HSSFSheet)wb.createSheet();
				HSSFRow row = sheet.createRow(0);
				HSSFCell cell = row.createCell((short) 0); 
				cell.setCellValue(" " );
			}
			FileOutputStream fileOut = new  FileOutputStream(exportFilePath);    
			wb.write(fileOut);    
			fileOut.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void excelEncrypt(String tmpFilePath, String pwd) {
    	View  m_view  =  new  View();
    	try {
			m_view.read(tmpFilePath);
			m_view.editCopyRight();
			m_view.write(tmpFilePath, pwd);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
}