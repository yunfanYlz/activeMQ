package com.ylz.activemq;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015-4-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StartServer
{

	private static final Logger logger = Logger.getLogger(StartServer.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring.xml" });
			context.start();
			// 监听端口
			String port = CustomConfig.getContextProperty("stop.port");
			System.out.println(port);

			// 启动监听程序
			Listener listener = new Listener(Integer.parseInt(port));
			Thread run = new Thread(listener);

			run.start();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			System.exit(0);
		}
	}
}

class Listener implements Runnable
{
    
    private static final Logger logger = Logger.getLogger(StopServer.class);
    
    ServerSocket server = null;
    
    Socket socket = null;
    
    InputStream in = null;
    
    BufferedReader br = null;
    
    final int port;
    
    protected Listener(int port)
    {
        this.port = port;
    }
    
    @Override
    public void run()
    {
        
        try
        {
            server = new ServerSocket(port);
            logger.info("Server stop listener == > \t port = > " + port);
            socket = server.accept();
            logger.info("Server stop action   == > \t port = > " + port);
            in = socket.getInputStream();
            //InputStreamReader将字节流转化为字符流
            br = new BufferedReader(new InputStreamReader(in));
            //行读取客户端数据
            String info = br.readLine();
            logger.info(info);
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        finally
        {
            close(server, socket, in, br);
            
            try
            {
                // 此处可以执行关闭数据库连接等操作
                Thread.sleep(5000l);
            }
            catch (InterruptedException e)
            {
                logger.error(e);
            }
            System.exit(0);
        }
    }
    
    private void close(ServerSocket server, Socket socket, InputStream in, BufferedReader br)
    {
        try
        {
            if (null != br)
                br.close();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        try
        {
            if (null != in)
                in.close();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        try
        {
            if (null != socket && !socket.isClosed())
                socket.close();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        try
        {
            if (server != null && !server.isClosed())
                server.close();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
    }
}



