package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import scene.GameScene;
import scene.Piece;
import scene.Stock;

public class GameCanvas extends JPanel {
    private static final Color BACKGROUND = Color.BLACK;
    private static final int CELL_SIZE = 5;

    private GameScene gameScene;

    public GameCanvas(GameScene gameScene){
        this.gameScene = gameScene;
    }

    public int getMapWidth(){ return gameScene.getWidth() * CELL_SIZE; }
    public int getMapHeight(){ return gameScene.getHeight() * CELL_SIZE; }

    public int getXStart(){ return (getWidth() - getMapWidth()) / 2; }
    public int getYStart(){ return (getHeight() - getMapHeight()) / 2; }

    public int getXStockStart(){ return getXStart() + getMapWidth() + CELL_SIZE; }
    public int getYStockStart(){ return getYStart(); }

    public int getMapX(int x){ return getXStart() + x * CELL_SIZE; }
    public int getMapY(int y){ return getYStart() + y * CELL_SIZE; }

    public int getStockX(int x){ return getXStockStart() + (x + 2) * CELL_SIZE; }
    public int getStockY(int y, int index){ return getYStockStart() + (y + index + 1) * CELL_SIZE; }

    @Override
    public void paint(Graphics g){
        clearScreen(g, BACKGROUND);
        draw(g);
    }

    public void clearScreen(Graphics g, Color c){
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void draw(Graphics g){
        List<Piece> p = gameScene.getPieces();
        Stock s = gameScene.getPiecesStock();

        drawMap(g);
        drawStocksMap(g, s);
        drawPieces(g, p);
        drawStock(g, s);
    }

    public void drawMap(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(getXStart() - 1, getYStart() - 1, getMapWidth() + 1, getMapHeight() + 1);
    }

    public void drawStocksMap(Graphics g, Stock s){
        int l = s.getPieces().length;
        g.setColor(Color.WHITE);
        g.drawRect(getXStockStart() - 1, getYStockStart() - 1, 6 * CELL_SIZE + 1, (l * 2 + 1) * CELL_SIZE + 1);
    }

    public void drawPieces(Graphics g, List<Piece> pieces){
        for(Piece p : pieces){
            drawPiece(g, p);
        }
    }
    
    public void drawPiece(Graphics g, Piece p){
        drawPiece(g, p, p.x, p.y);
    }

    public void drawPiece(Graphics g, Piece p, int x, int y){
        g.setColor(p.getColor());
        for (int py = 0; py < p.getCell().length; py++) {
            for (int px = 0; px < p.getCell()[py].length; px++) {
                if(p.getCell()[py][px]){
                    g.fillRect(getMapX(px + x), getMapY(py + y), CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
    
    public void drawStock(Graphics g, Stock s){
        for (int i = 0; i < s.getPieces().length; i++) {
            drawPieceStock(g, s.getPieces()[i], i);
        }
    }

    public void drawPieceStock(Graphics g, Piece p, int index){
        g.setColor(p.getColor());
        for (int py = 0; py < p.getCell().length; py++) {
            for (int px = 0; px < p.getCell()[py].length; px++) {
                if(p.getCell()[py][px]){
                    g.fillRect(getStockX(px), getStockY(py, index), CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}
