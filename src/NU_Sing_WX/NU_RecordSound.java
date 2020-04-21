package NU_Sing_WX;
import javax.swing.*; 
import javax.sound.sampled.*;
import java.io.*;

public class NU_RecordSound extends JFrame 
{ 
		 
	static long RECORD_TIME;	
	static File NUF;
	
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	TargetDataLine line;
	private ByteArrayOutputStream recordBytes;
	AudioFormat format;
	
	public void setRecordTime(long RECORD_TIME)
	{
		this.RECORD_TIME = RECORD_TIME;
	}
	public long getRecordTime()
	{
		return RECORD_TIME;
	}
	public AudioFormat getAudioFormat()
	{
		 float sampleRate = 16000;
	     int sampleSizeInBits = 8;
	     int channels = 2;
	     boolean signed = true;
	     boolean bigEndian = true;	     
	     AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	     return format;
	}
	
	public void start()
	{
		try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) 
            {
                System.out.println("Line not supported");
                System.exit(0);
            }
            else
            {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
            
            
            
            
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            
            AudioSystem.write(ais, fileType, NUF);
            }
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
	public void finish()
	{
		line.stop();
	    line.close();
	    System.out.println("Finished");
	}		
}
