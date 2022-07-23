package main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Sound {
	
	GamePanel gp;
	URL soundURL[] = new URL[13];
	FloatControl fc;
	Clip clip;
	public int Count;
	int volumeScale = 3;
	float volume;
	
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sounds/hit.wav");
		soundURL[1] = getClass().getResource("/sounds/receivedamage.wav");
		soundURL[2] = getClass().getResource("/sounds/step1.wav");
		
	}
	
	public void setFile(int i) throws IOException {
		InputStream audioSrc = null;
		InputStream bufferedIn = null;
		try {
			audioSrc = soundURL[i].openStream();
			bufferedIn = new BufferedInputStream(audioSrc);
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.addLineListener((LineListener) new LineListener() 
			{
					@Override
					public void update(LineEvent event) 
					{
						if(event.getType()== LineEvent.Type.STOP)
							clip.close();
					}
			});
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume = -12f;
			fc.setValue(volume);

		}catch(Exception e){
			System.err.println(e);
		} finally {
			if(bufferedIn != null) {
				bufferedIn.close();
			}
			if (audioSrc != null) {
				 audioSrc.close();
			}
		}
		
	}
	
	
	public void play(Boolean loop) {
		
		clip.start();
		Count++;
		
		if(loop) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	
	public void stop(String what) {
		clip.stop();

	}
	
}
