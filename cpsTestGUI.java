import java.awt.Dimension;

import javax.swing.*;

public class cpsTestGUI{

    private JFrame frame; 
    //buttons
    private JButton button;
    private JButton retry;
    private JButton oneSecTest;
    private JButton twoSecTest;
    private JButton fiveSecTest;
    private JButton tenSecTest;

    //labels
    private JLabel clicks;
    private JLabel clicksPerSec;
    private JLabel time;

    private JPanel panel;
    
    private int numClicks = 0;
    private int highScore = 0;
    private int duration = 1;
    private long end;
    private long start;
    private double elapsed = 0;
    private boolean firstPress;


    public cpsTestGUI(){
       this.frame = new JFrame("CPS test");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(800,800);
       this.panel = new JPanel();

       this.button = new JButton("Click to play");
       this.retry = new JButton("Retry");
       this.oneSecTest = new JButton("1s test");
       this.twoSecTest = new JButton("2s test");
       this.fiveSecTest = new JButton("5s test");
       this.tenSecTest = new JButton("10s test");

       this.clicks = new JLabel("Clicks: " + 0);
       this.time = new JLabel("Time: " + 0.0);
       time.setPreferredSize(new Dimension(100, 50));

       initializeButtons();

       panel.add(button);
       panel.add(retry);
       panel.add(clicks);
       panel.add(time);
       panel.add(oneSecTest);
       panel.add(twoSecTest);
       panel.add(fiveSecTest);
       panel.add(tenSecTest);

       panel.setPreferredSize(new Dimension(500, 500));

       frame.getContentPane().add(panel);
       //TODO: save highscore for current session 

    }

    public void update(){
        numClicks++;
        clicks.setText("Clicks: " + numClicks);
    }

    public void initializeButtons(){
       button.setPreferredSize(new Dimension(200, 200));
       retry.setPreferredSize(new Dimension(200, 200));
       clicks.setPreferredSize(new Dimension(200, 200));
       oneSecTest.setPreferredSize(new Dimension(200, 200));
       twoSecTest.setPreferredSize(new Dimension(200, 200));
       fiveSecTest.setPreferredSize(new Dimension(200, 200));
       tenSecTest.setPreferredSize(new Dimension(200, 200));

        button.addActionListener(ActionEvent ->{
            if(!firstPress){
                start = System.currentTimeMillis();
                end = System.currentTimeMillis() + (duration * 1000);
                firstPress = true;
                update();
                time.setText("Timer: " + (System.currentTimeMillis()-start)/1000.0);
            }
            if(System.currentTimeMillis() >= end){
                button.setEnabled(false);
                time.setText("Timer: " + (System.currentTimeMillis()-start)/1000.0);
            }
                update();
                time.setText("Timer: " + (System.currentTimeMillis()-start)/1000.0);
            
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
    }

    public void showWindow(){
        frame.setVisible(true);
    }

    public void reset(){
        numClicks = 0;
        elapsed = 0;
        firstPress = false;
        clicks.setText("Clicks: " + numClicks);
        time.setText("Timer: " + elapsed);
        button.setEnabled(true);
    }

}