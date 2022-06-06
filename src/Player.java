import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class Player
{
	private static Clip clip;
	
	public Player() {
		
	}
	
	public static  void playmusic(String musicfile) {
		File soundFile = new File(musicfile);
		try {
	//		Clip clip = AudioSystem.getClip();
			if(musicfile.equals("stop")){
				clip.stop();
				clip.flush();
				clip.close();
			
			}
			else if (musicfile.equals("pause")) {
				clip.stop();
			}
			else {
				AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);
				if (clip == null || !clip.isOpen()){
					clip = AudioSystem.getClip();
					clip.open(inputStream);
				}
			//	clip.loop(clip.LOOP_CONTINUOUSLY);
				clip.start();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}