package com.ouchadam.psr.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame {

    private final JFrame frame;
    private final UiReadyListener uiListener;

    public static MainFrame newInstance(UiReadyListener uiListener) {
        MainFrame mainFrame = new MainFrame(new JFrame(), uiListener);
        mainFrame.createAndShowGui();
        return mainFrame;
    }

    MainFrame(JFrame frame, UiReadyListener uiListener) {
        this.frame = frame;
        this.uiListener = uiListener;
    }

    void createAndShowGui() {
        SwingUtilities.invokeLater(createGui);
    }

    private final Runnable createGui = new Runnable() {
        @Override
        public void run() {
            initFrame();
            uiListener.onUiReady(frame);
        }
    };

    void initFrame() {
        frame.setUndecorated(true);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice myDevice = ge.getDefaultScreenDevice();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(500, 500);
//        frame.setLocation(100, 100);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        myDevice.setFullScreenWindow(frame);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);

        frame.addKeyListener(escapeKey);
    }

    private final KeyListener escapeKey = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            exitOnEscape(e);
        }

        private void exitOnEscape(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };

}
