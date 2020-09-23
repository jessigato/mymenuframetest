package mymenuframetest;

import javax.swing.JFrame;

public class MyMenuFrameTest
{
    public static void main(String[] args)
    {
        MyMenuFrame frame = new MyMenuFrame();
        frame.setSize(600, 400);//size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }  
}