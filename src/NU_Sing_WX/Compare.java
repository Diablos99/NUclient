package NU_Sing_WX;

import java.io.*;
import com.musicg.wave.*;
import com.musicg.fingerprint.*;
public class Compare
{
		protected static float result;
		public void match(String WORKNAME, String CUTNAME)
		{
			String filename1 = WORKNAME;
			String filename2= CUTNAME;
			try
			{
				InputStream fis1 = null, fis2 = null;
					fis1 = new FileInputStream(filename1);
					fis2 = new FileInputStream(filename2);
					
				Wave wave1 = new Wave(fis1), wave2 = new Wave(fis2);
				FingerprintSimilarity similarity;
				similarity = wave1.getFingerprintSimilarity(wave2);
				result = similarity.getSimilarity()*1000;
				System.out.println(result);
			}
			catch(Exception e) {}

			
		}

		public static void main(String[] args)
		{
			String s1 = "D://NU_Works//5.wav";
			String s2 = "D://NU_Works//55.wav";
			Compare a = new Compare();
			a.match(s1,s2);
		}


}
