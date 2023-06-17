/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import messagetype.AudioMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author tinhlam
 */
public class AudioHandler {

    public TargetDataLine line;
    public AudioFormat format;

    public AudioHandler() {
        format = MyAudioFormat.format;
        this.line = getLine();
    }

    public TargetDataLine getLine() {
        TargetDataLine temp;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        try {
            temp = (TargetDataLine) AudioSystem.getLine(info);
            temp.open(format, temp.getBufferSize());
        } catch (LineUnavailableException ex) {
            return null;
        }
        return temp;
    }

    ByteArrayOutputStream temp_out;

    public AudioMessage getAudioMessage() {
        if (temp_out == null){
            return null;
        }
        byte[] buf = temp_out.toByteArray();
        AudioMessage message = new AudioMessage("", buf);
        temp_out = null;
        return message;
    }

    Thread recordingThread;

    public void startRecording() {
        recordingThread = new Thread() {
            @Override
            public void run() {
                recordingThread = this;
                temp_out = new ByteArrayOutputStream();
                byte[] buf = new byte[line.getBufferSize()];
                int numBytesRead;
                line.start();
                while (true) {
                    if ((numBytesRead = line.read(buf, 0, buf.length)) == -1) {
                        break;
                    }
                    temp_out.write(buf, 0, numBytesRead);
                    System.out.println("Starting....");
                }
            }
        };
        recordingThread.start();
    }

    public void stopRecording() {
        recordingThread.stop();
        System.out.println("Stopped...");
    }

    public static void playAudio(byte[] buf) {
        try {
            ByteArrayInputStream inp = new ByteArrayInputStream(buf);
            AudioFormat format = MyAudioFormat.format;
            AudioInputStream audio = new AudioInputStream(
                    inp,
                    format,
                    buf.length / 2
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(AudioHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
