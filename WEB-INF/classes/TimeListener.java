import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import javax.servlet.ServletContextEvent;    
import javax.servlet.ServletContextListener;
public class TimeListener implements ServletContextListener {  
    private Timer timer = null;  
  
    public void contextInitialized(ServletContextEvent event) {  
        // �������ʼ������������tomcat������ʱ�����������������������ʵ�ֶ�ʱ������  
        timer = new Timer(true);  
        System.out.println("�����ɹ�");  
        // �����־������tomcat��־�в鿴��  
        event.getServletContext().log("��ʱ��������--------------"); 
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,12);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		Date date=cal.getTime();
        // ����exportHistoryBean��0��ʾ�������ӳ٣�4*60*60*1000��ʾһ��ִ��һ�Ρ�  
        timer.schedule(new exportHistoryBean(event.getServletContext()),date,   
                24 * 60 * 60 * 1000);  
        event.getServletContext().log("�Ѿ��������-------------");  
    }  
  
    // ������رռ��������������������ٶ�ʱ����  
    public void contextDestroyed(ServletContextEvent event) {  
        timer.cancel();  
        event.getServletContext().log("��ʱ������---------------");  
    }  
  
}  