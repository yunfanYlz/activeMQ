package com.ylz.activemq;
/*
 * 文 件 名:  ServerStop.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015-4-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015-4-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StopServer
{
    
    private static final Logger logger = Logger.getLogger(StopServer.class);
    
    public static void main(String[] args)
    {
        String filename = "/config_prod.properties";
        Socket socket = null;
        Writer writer = null;
        try
        {
            
            Properties p = readProperties(StopServer.class.getResource(filename).getFile());
            String port = p.getProperty("stop.port");
            
            logger.info("close isp server, stop port is " + port);
            socket = new Socket("127.0.0.1", Integer.parseInt(port));
            //建立连接后就可以往服务端写数据了  
            writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("close server ");
            writer.flush();//写完后要记得flush
        }
        catch (NumberFormatException e)
        {
            logger.error(e);
        }
        catch (UnknownHostException e)
        {
            logger.error(e);
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        
    }
    
    public static Properties readProperties(String filename) throws IOException
    {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(filename);
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            logger.error(e);
            throw e;
        }
        finally
        {
            try
            {
                inputStream.close(); //关闭流
            }
            catch (Exception e)
            {logger.error(e); }
        }
        return properties;
    }
}
