package NightAudit;
import java.util.Timer;
import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import NightAudit.TimeThreadNight.TimeThreadNight;

public class TimerNightAudit implements ServletContextListener {
	private Timer timer = null;
	public void contextInitialized(ServletContextEvent event){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY , 23);//����ʱ
		calendar.set(Calendar.MINUTE , 45);  //���Ʒ�
		calendar.set(Calendar.SECOND, 0 ); //������
		Date time = calendar.getTime(); //�ó������ʱ��Ϊ12:00:00
		//�����һ��ִ�ж�ʱ�����ʱ�䣬С�ڵ�ǰ��ʱ�䣬�жϵ�һ��ִ�м�һ��
		if (time.before(new Date())) {  
            time = this.addDay(time, 1);  
        } 
		timer = new Timer();
		event.getServletContext().log("ҹ�����϶�ʱ��������");
		timer.schedule(new TimeThreadNight(), time , 1000*60*60*24);
	}
	public void contextDestroyed(ServletContextEvent event) 
	{ 
		  //������رռ��������������������ٶ�ʱ���� 
		  timer.cancel(); 
		  event.getServletContext().log("��ʱ������"); 

	 }
	 Date addDay(Date date , int num){
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH,num);
		return startDT.getTime();
	 }
}