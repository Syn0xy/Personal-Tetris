package view;

import java.awt.Point;

import scene.GameScene;
import view.util.Observer;
import view.util.Subject;

public class GameView extends View implements Observer {
    private static final int WIDTH = (int)(SCREEN_WIDTH * (2.0/3.0));
    private static final int HEIGHT = (int)(SCREEN_HEIGHT * (2.0/3.0));
    private static final String TITLE = "Personal - Tetris";

    private GameCanvas gameCanvas;

    public GameView(GameScene gameScene){
        this.gameCanvas = new GameCanvas(gameScene);
        gameScene.attach(this);
        init(WIDTH, HEIGHT);
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    protected void view() {
        add(gameCanvas);
    }

    @Override
    protected Point position() {
        return center();
    }

    @Override
    public void update(Subject subj) {
        repaint();
    }

    @Override
    public void update(Subject subj, Object data) {
        repaint();
    }
    
}
