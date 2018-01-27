package com.sdxd.api.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description:</b>〈中文字体提供工厂类〉<br/>
 * Create date: 2016年1月18日
 * @version v1.0.0
 */
public class CNFontFactoryImpl extends FontFactoryImp
{
    private static Logger logger   = LoggerFactory.getLogger(CNFontFactoryImpl.class);

    protected BaseFont    baseFont = null;

    private CNFontFactoryImpl()
    {
        super();
        super.defaultEncoding = BaseFont.IDENTITY_H;
        super.defaultEmbedding = BaseFont.NOT_EMBEDDED;
        try
        {
            String pathString = getClass().getResource("/fonts/ARIALUNI.TTF").getPath();
            //step 1 必须先注册该字体
            register(pathString);
            //step 2 使用注册的该字体创建Font
            baseFont = BaseFont.createFont(pathString, defaultEncoding, defaultEmbedding);
            logger.info("------>CNFontFactoryImpl() info: Generate PDF use newly registered fonts !");
        }
        catch (Exception e)
        {
            logger.error("System error:{}", e.getMessage());
        }
    }

    private static class CNFontFactoryImplHolder
    {
        private static CNFontFactoryImpl fontProvider = new CNFontFactoryImpl();
    }

    public static CNFontFactoryImpl getInstance()
    {
        if (CNFontFactoryImplHolder.fontProvider.baseFont == null)
        {
            try
            {
                // 使用iTextAsian.jar中的字体
                CNFontFactoryImplHolder.fontProvider.baseFont = BaseFont.createFont("STSong-Light", "UniGB-UTF8-H",
                        BaseFont.NOT_EMBEDDED);
                logger.info("------>getInstance() info: Generate PDF use iTestAsian's font !");
            }
            catch (Exception e)
            {
                logger.error("System error:{}", e.getMessage());
            }
        }
        return CNFontFactoryImplHolder.fontProvider;
    }

    /**
     * <b>Description:</b>〈获取支持中文的字体〉<br/>
     * @author hongchaoMao <br/>
     * Create date: 2016年1月18日
     * @see com.itextpdf.text.FontFactoryImp#getFont(String, String, boolean, float, int,
     * com.itextpdf.text.BaseColor, boolean)
     */
    @Override
    public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color,
            boolean cached)
    {
        try
        {
            if (baseFont != null)
            {
                return new Font(baseFont, size, style, color);
            }
            logger.info("------>getFont() info: Get supper font !");
            return super.getFont(fontname, encoding, embedded, size, style, color, cached);
        }
        catch (Exception e)
        {
            logger.error("------>getFont() error:{}", e.getMessage());
            return super.getFont(fontname, encoding, embedded, size, style, color, cached);
        }
    }
}
