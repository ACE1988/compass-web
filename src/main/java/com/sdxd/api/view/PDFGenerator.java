package com.sdxd.api.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sdxd.api.util.CNFontFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author liujie
 * @Date 2017/6/20
 * 盛大小贷
 */
public class PDFGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    /**
     * <b>Description:</b>〈根据模版内容生成PDF文件〉<br/>
     *
     * @param pdfName 生成的PDF文件路径
     * @return PDF文档file对象
     */
    public static byte[] generatePDF(String sourceDoc, String pdfName) {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = null;
        document.setPageSize(PageSize.A4);
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writer = PdfWriter.getInstance(document, os);
            // step 3
            document.open();
            // step 4
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(sourceDoc.getBytes("UTF-8")),
                    null, Charset.forName("UTF-8"), CNFontFactoryImpl.getInstance());
            logger.info("------>generatePDF() pdf 创建成功! file:{}", pdfName);

            document.close();
            writer.close();
            return os.toByteArray();
        } catch (DocumentException e) {
            logger.error("------>generatePDF() html转pdf报错：DocumentException", e);
        } catch (IOException e) {
            logger.error("------>generatePDF() html转pdf报错：IOException", e);
        } catch (Exception e) {
            logger.error("------>generatePDF() html转pdf报错：Exception", e);
        } finally {
            document.close();
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }

    public static byte[] download(String strUrl, String pdfName) {
        try {
            //创建一个URL实例
            URL url = new URL(strUrl);
            try {
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");

                StringBuffer data = new StringBuffer();
                BufferedReader br = new BufferedReader(isr);
                String str = "";
                while ((str = br.readLine()) != null) {
                    if (str.trim().equals("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">")
                            || str.trim().equals("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">")) {
                        continue;
                    }
                    data.append(str).append(System.getProperty("line.separator"));
                }
                byte[] bytes = generatePDF(data.toString(), pdfName);
                br.close();
                isr.close();
                is.close();
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
