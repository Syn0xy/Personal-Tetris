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
    private static final int CELL_SIZE = 10;

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

    public int getStockX(int x){ return getXStockStart() + (x + 1) * CELL_SIZE; }
    public int getStockY(int y, int ystart){ return getYStockStart() + (y + ystart + 1) * CELL_SIZE; }

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
        Piece[] ps = s.getPieces();

        drawMap(g);
        drawStocksMap(g, ps);
        drawPieces(g, p);
        drawStock(g, ps, s.getIndex());
    }

    public void drawMap(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(getXStart() - 1, getYStart() - 1, getMapWidth() + 1, getMapHeight() + 1);
    }

    public void drawStocksMap(Graphics g, Piece[] ps){
        int width = 0;
        int height = ps.length + 1;
        for (Piece p : ps) {
            height += p.getHeight();
            if(p.getWidth() > width) width = p.getWidth();
        }
        g.setColor(Color.WHITE);
        g.drawRect(getXStockStart() - 1, getYStockStart() - 1, (width + 2) * CELL_SIZE + 1, height * CELL_SIZE + 1);
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
        for (int py = 0; py < p.getCells().length; py++) {
            for (int px = 0; px < p.getCells()[py].length; px++) {
                if(p.getCells()[py][px]){
                    g.fillRect(getMapX(px + x), getMapY(py + y), CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
    
    public void drawStock(Graphics g, Piece[] ps, int index){
        int ystart = 0;
        for (int i = 0; i < ps.length; i++) {
            Piece p = ps[(i + index) % ps.length];
            drawPieceStock(g, p, ystart);
            ystart += p.height + 1;
        }
    }

    public void drawPieceStock(Graphics g, Piece p, int ystart){
        g.setColor(p.getColor());
        for (int y = 0; y < p.getCells().length; y++) {
            for (int x = 0; x < p.getCells()[y].length; x++) {
                if(p.getCells()[y][x]){
                    g.fillRect(getStockX(x), getStockY(y, ystart), CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}
