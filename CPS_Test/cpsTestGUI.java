import java.awt.Dimension;
import java.lang.Math;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class cpsTestGUI{

    private JFrame frame; 
    //buttons
    private JButton button;
    private JButton retry;
    private JButton oneSecTest;
    private JButton twoSecTest;
    private JButton fiveSecTest;
    private JButton tenSecTest;
    private JButton saveScore;

    //labels
    private JLabel clicks;
    private JLabel clicksPerSec;
    private JLabel time;
    private Timer stopwatch;
    private JLabel highestScore;
    private JPanel panel;
    
    private int numClicks = 0;
    private double cps;
    private double highScore = 0;
    private int duration = 1;
    private double second;

    public cpsTestGUI(){
       this.frame = new JFrame("CPS test");
       frame.setSize(800,800);
       this.panel = new JPanel();

       this.button = new JButton("Click to play");
       this.retry = new JButton("Retry");
       this.oneSecTest = new JButton("1s test");
       this.twoSecTest = new JButton("2s test");
       this.fiveSecTest = new JButton("5s test");
       this.tenSecTest = new JButton("10s test");
       this.saveScore = new JButton("Save score to file");

       this.clicks = new JLabel("Score: " + 0);
       this.time = new JLabel("Time: " + 0.0);
       this.highestScore = new JLabel("Highest score this session: " + highScore + " Clicks/s");
       this.clicksPerSec = new JLabel(0.0 + " Clicks/s");

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update(){
        numClicks++;
        clicks.setText("Clicks: " + numClicks);
    }

    public void stopwatch(){
        stopwatch = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                second+= 0.014;
                time.setText("Timer: " + (double)Math.round(second * 100.0)/100.0); 
                clicksPerSec.setText((double)Math.round((numClicks/second) * 100.0)/100.0 + " Clicks/s");
                if(second >= duration){
                    stopwatch.stop();
                    button.setEnabled(false);
                    cps = (double)Math.round((numClicks/second) * 100.0)/100.0;
                    if(cps > highScore){
                        highScore = cps;
                    }
                    highestScore.setText("Highest score this session: " + highScore + " Clicks/s");
                }
            }
        });
    }

    public void addToFrame(){
       panel.add(button);
       panel.add(retry);
       panel.add(oneSecTest);
       panel.add(twoSecTest);
       panel.add(fiveSecTest);
       panel.add(tenSecTest);
       panel.add(clicks);
       panel.add(time);
       panel.add(clicksPerSec);
       panel.add(saveScore);
       panel.add(highestScore);
       frame.getContentPane().add(panel);
    }

    public void setSizes(){
       button.setPreferredSize(new Dimension(200, 200));
       retry.setPreferredSize(new Dimension(200, 200));
       clicks.setPreferredSize(new Dimension(200, 200));
       oneSecTest.setPreferredSize(new Dimension(200, 200));
       twoSecTest.setPreferredSize(new Dimension(200, 200));
       fiveSecTest.setPreferredSize(new Dimension(200, 200));
       tenSecTest.setPreferredSize(new Dimension(200, 200));
       time.setPreferredSize(new Dimension(200, 50));
       clicksPerSec.setPreferredSize(new Dimension(200, 50));
       highestScore.setPreferredSize(new Dimension(300, 50));
       saveScore.setPreferredSize(new Dimension(100, 100));
       panel.setPreferredSize(new Dimension(800, 800));
    }

    public void saveFile(){
        String high_score = String.valueOf(highScore) + " Clicks/s";
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String date = time.format(myFormat);
        String filename = "gameHistory.txt";
        try{
            FileWriter writer = new FileWriter(filename, true);
            writer.write(date + "\n\t- High Score: ≈" + high_score);
            if(duration == 1){
                writer.append("\n\t- Clicks: " + numClicks + "\n\t -Time: " + duration +" second");
            }
            else{
                writer.append("\n\t- Clicks: " + numClicks + "\n\t- Time: " + duration +" seconds");
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void initializeButtons(){
       stopwatch();
        button.addActionListener(ActionEvent ->{
            stopwatch.start();
            if(second >= duration){
                button.setEnabled(false);
                stopwatch.stop();
                time.setText("Timer: " + second);
                //time.setText("Timer: " + (System.currentTimeMillis()-start)/1000.0);
            }
                update();
                //time.setText("Timer: " + (System.currentTimeMillis()-start)/1000.0);
            
        });
        
        retry.addActionListener(ActionEvent ->{
            reset();
        });

        oneSecTest.addActionListener(ActionEvent ->{
            reset();
            duration = 1;
            
        });

        twoSecTest.addActionListener(ActionEvent ->{
            reset();
            duration = 2;            
        });

        fiveSecTest.addActionListener(ActionEvent ->{
            reset();
            duration = 5;
            
        });

        tenSecTest.addActionListener(ActionEvent ->{
            reset();
            duration = 10;
        });

        saveScore.addActionListener(ActionEvent ->{
            saveFile();
        });
    }

    public void showWindow(){
        frame.setVisible(true);
    }

    public void reset(){
        stopwatch.stop();
        numClicks = 0;
        second = 0;
        cps = 0;
        clicks.setText("Score: " + numClicks);
        clicksPerSec.setText(0.0 + " Clicks/s");
        time.setText("Timer: " + 0.0);
        button.setEnabled(true);
    }

}