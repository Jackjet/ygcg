import java.util.Calendar;
import java.util.TimerTask;
import javax.servlet.ServletContext;
public class exportHistoryBean extends TimerTask {  
    private static final int C_SCHEDULE_HOUR = 0;  
    private static boolean isRunning = false;  
    private ServletContext context = null;  
  
    public exportHistoryBean(ServletContext context) {  
        this.context = context;  
    }  
  
    public void run() {  
          
        
        context.log("��ʼִ��ָ������");  
        
		 //(calendar.get(Calendar.HOUR_OF_DAY)==17){
			new DOWORK().dowork();
		//
        context.log("ָ������ִ�н���");  
      
    }  
}  