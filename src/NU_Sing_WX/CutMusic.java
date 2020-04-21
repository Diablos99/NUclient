package NU_Sing_WX;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 音乐剪切和拼接（音乐串烧）
 * @author 
 *
 */
public class CutMusic 
{	
	protected static File NUC;
	protected static int NUC_index = 1;
	protected static CutMusic cutM = new CutMusic();
	public void setIndex(int NUC_index)
	 {
		 this.NUC_index = NUC_index;
	 }
	public int getIndex()
	{
		return NUC_index;
	}
 public static void cut1(String song, int endTime){
	 
	 
	 File f1 = new File(song);
	 try 
		{
		NUC = new File("D://NU_Garbage","CUT"+ NUC_index+ ".mp3");
		NUC_index++;
		NUC.createNewFile();
		}catch (IOException e){
				e.printStackTrace();};
	  BufferedInputStream bis1 = null;
	  BufferedOutputStream bos = null;
	  
	  int start1 = 128*0*1024/8;//320kbps（比特率）*58s*1024/8=2375680 比特率可以查看音频属性获知
	  //Cut Buffer Time
	  
	  int end1 = 128*endTime*1024/8;//320kbps*120s*1024/8=4915200      
	  int tatol1 = 0;
	  try {
	   //两个输入流
	   bis1 = new BufferedInputStream(new FileInputStream(f1));
	
	   //缓冲字节输出流（true表示可以在流的后面追加数据，而不是覆盖！！）
	   bos = new BufferedOutputStream(new FileOutputStream(NUC,true));
	    
	   //第一首歌剪切、写入
	   byte[] b1= new byte[512];
	   int len1 = 0;
	   while((len1 = bis1.read(b1))!=-1){
	    tatol1+=len1;   //累积tatol
	    if(tatol1<start1 ){  //tatol小于起始值则跳出本次循环
	     continue;
	    }
	    bos.write(b1);   //写入的都是在我们预先指定的字节范围之内
	    if(tatol1>=end1 ){  //当tatol的值超过预先设定的范围，则立刻刷新bos流对象，并结束循环
	     bos.flush();
	     break;
	    }
	     
	   }
	   System.out.println("第一首歌剪切完成！");
	    
	  
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }finally{
	    try {//切记要关闭流！！
	     if(bis1!=null) bis1.close();
	     if(bos!=null) bos.close();
	    } catch (IOException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	  }
	 }
 
}