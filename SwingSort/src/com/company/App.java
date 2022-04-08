package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;



public class App {
    static JFrame jFrame = new JFrame();
    static JPanel result = new JPanel();

    public static void main(String[] args) {
        jFrame= getFrame();

        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);
        jPanel.setBounds(0,0,300,150);

        JLabel label = new JLabel("Введите количество для сорировки");
        jPanel.add(label);

        JTextField textField = new JTextField("10",4);
        jPanel.add(textField);

        JButton jButtonYes = new JButton("yes");
        jPanel.add(jButtonYes);
        jButtonYes.setBackground(Color.red);
        jFrame.revalidate();

        jButtonYes.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame=getFrame();
                jFrame.setTitle("game");
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Toolkit toolkit =Toolkit.getDefaultToolkit();
                Dimension dimension = toolkit.getScreenSize();
                jFrame.setBounds(dimension.width/2-500,dimension.height/2-500,1000,500);
                jFrame.setVisible(true);

                JPanel jPanel1 = new JPanel();
                jPanel1.setBackground(Color.CYAN);
                jFrame.add(jPanel1,BorderLayout.NORTH);
                jPanel1.setBorder(BorderFactory.createLineBorder(Color.PINK));
                jFrame.add(result,BorderLayout.SOUTH);
                result.setBackground(Color.GREEN);
                result.setBorder(BorderFactory.createLineBorder(Color.PINK));
                int n=0;
                try{
                    n = Integer.parseInt(textField.getText());
                }
                catch (NumberFormatException ex){
                    jFrame.add(new JLabel("Некоректный ввод!!!"));
                }
                jFrame.add(new MyComponent());
                MyLabel [] jLabels = new  MyLabel[n];
                for (int i=0; i<jLabels.length; i++){
                    int s =(int) (Math.random()*1000);
                    jLabels[i] = new MyLabel(Integer.toString(s));
                    jLabels[i].setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
                    jPanel1.add(jLabels[i],BorderLayout.CENTER);
                }

                JButton sort = new JButton("Sort");
                jFrame.add(sort,BorderLayout.WEST);
                sort.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int h = jLabels.length-1;
                        quickSort(jLabels,0,h);
                        revers(jLabels);
                        int i = 0;
                        for (i = 0; i < jLabels.length; i++) {
                            try {
                                Thread.sleep(100);
                                result.add(jLabels[i], BorderLayout.AFTER_LAST_LINE);
                                jLabels[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                JButton restart = new JButton("Restart");
                jFrame.add(restart,BorderLayout.EAST);
                restart.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (MyLabel el: jLabels){
                            result.remove(el);
                            jPanel1.remove(el);
                        }
                        App.main(new String[]{"h"});
                    }
                });
            }
        });
    }

    static JFrame getFrame(){
        JFrame jFrame= new JFrame("Sort matrix");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        Toolkit toolkit =Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2-250,dimension.height/2-250,500,300);
        return jFrame;

    }

    static class MyLabel extends JLabel implements Comparable<MyLabel>{
        public MyLabel(String text) {
            super(text);
        }
        public int compareTo(MyLabel o) {
            return (Integer.parseInt(super.getText())-Integer.parseInt(o.getText()));
        }

        @Override
        public String toString() {
            String s = super.getText();
            return s;
        }
    }

    public static void quickSort(MyLabel[] jLabels, int low, int high) {

        if (jLabels.length == 0)
            return;

        if (low >= high)
            return;


        int middle = low + (high - low) / 2;
        int opora =Integer.parseInt(jLabels[middle].getText());


        int i = low, j = high;
        while (i <= j) {
            while (Integer.parseInt(jLabels[i].getText())< opora) {
                i++;
            }

            while (Integer.parseInt(jLabels[j].getText()) > opora) {
                j--;
            }

            if (i <=j) {
                int temp = Integer.parseInt(jLabels[i].getText());
                jLabels[i] = new MyLabel(jLabels[j].getText());
                jLabels[j] = new MyLabel(Integer.toString(temp));
                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(jLabels, low, j);
        }
        if (high > i) {
            quickSort(jLabels, i, high);
        }

    }
    public static void revers(MyLabel []l) {
        for (int i = 0; i < l.length / 2; i++) {
            String t = l[i].getText();
            l[i] = l[l.length - i - 1];
            l[l.length - i - 1] = new MyLabel(t);
        }
    }
    static class MyComponent extends JComponent{

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2= (Graphics2D) g;
            String s = "img/logo.png";
            Image image = new ImageIcon(s).getImage();
            g.drawImage(image,220,50, null);
        }
    }
}

