package NU_Sing_WX;

import javax.swing.JSlider;

import javafx.scene.media.MediaPlayer;

public class NU_Slider 
{
	
	protected static MediaPlayer mediaPlayer;
	protected static MediaPlayer accomPlayer; 
	
	protected static JSlider volume;
	protected static double a_volume;
	protected static boolean a_mute;
	

	public void setMute(boolean a_mute)
	{
		NU_Slider.a_mute = a_mute;
		mediaPlayer.setMute(a_mute);
		accomPlayer.setMute(a_mute);
	}
	public boolean getMute()
	{
		return a_mute;
	}
}
