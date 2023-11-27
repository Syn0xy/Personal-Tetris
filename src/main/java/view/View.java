package view;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public abstract class View extends JFrame {
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();
    public static final int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();

    protected void init(int width, int height){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocation(position());
        setTitle(title());
        view();
        setVisible(true);
    }

    public abstract String title();
    protected abstract void view();
    protected abstract Point position();

    protected Point center(){
        return new Point((SCREEN_WIDTH - getWidth()) / 2, (SCREEN_HEIGHT - getHeight()) / 2);
    }
}
