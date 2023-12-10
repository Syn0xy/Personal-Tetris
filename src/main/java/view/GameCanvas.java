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

    private GameScene gameScene;
    private int cellSize;
    private int cellStockSize;

    public GameCanvas(GameScene gameScene){
        this.gameScene = gameScene;
    }

    private int getMinWindowSize(){ return getWidth() < getHeight() ? getWidth() : getHeight(); }
    private int getMaxGameSize(){ return gameScene.getWidth() > gameScene.getHeight() ? gameScene.getWidth() : gameScene.getHeight(); }
    private int getCellSize(){ return (int)(getMinWindowSize() * (9.0 / 10.0)) / getMaxGameSize(); }

    private int getMapWidth(){ return gameScene.getWidth() * cellSize; }
    private int getMapHeight(){ return gameScene.getHeight() * cellSize; }

    private int getXStart(){ return (getWidth() - getMapWidth()) / 2; }
    private int getYStart(){ return (getHeight() - getMapHeight()) / 2; }

    private int getXStockStart(){ return getXStart() + getMapWidth() + cellSize; }
    private int getYStockStart(){ return getYStart(); }

    private int getMapX(int x){ return getXStart() + x * cellSize; }
    private int getMapY(int y){ return getYStart() + y * cellSize; }

    private int getStockX(int x){ return getXStockStart() + (x + 1) * cellStockSize; }
    private int getStockY(int y, int ystart){ return getYStockStart() + (y + ystart + 1) * cellStockSize; }

    @Override
    public void paint(Graphics g){
        clearScreen(g, BACKGROUND);
        refreshProportion();
        draw(g);
    }

    private void clearScreen(Graphics g, Color c){
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void refreshProportion(){
        cellSize = getCellSize();
        cellStockSize = cellSize / 2;
    }

    private void draw(Graphics g){
        List<Piece> p = gameScene.getPieces();
        Stock s = gameScene.getPiecesStock();
        Piece[] ps = s.getPieces();

        drawMap(g);
        drawStocksMap(g, ps);
        drawPieces(g, p);
        drawStock(g, ps, s.getIndex());
        drawDebug(g);
    }

    private void drawDebug(Graphics g){
        g.setColor(Color.WHITE);
        String s1 = "Appuyez sur 'K' pour relancer";
        String s2 = "Il y a " + gameScene.getPieces().size() + " pieces";
        g.drawString(s1, 30, 30);
        g.drawString(s2, 30, 60);
    }

    private void drawMap(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(getXStart() - 1, getYStart() - 1, getMapWidth() + 1, getMapHeight() + 1);
    }

    private void drawStocksMap(Graphics g, Piece[] ps){
        int width = 0;
        int height = ps.length + 1;
        for (Piece p : ps) {
            height += p.getHeight();
            if(p.getWidth() > width) width = p.getWidth();
        }
        g.setColor(Color.WHITE);
        g.drawRect(getXStockStart() - 1, getYStockStart() - 1, (width + 2) * cellStockSize + 1, height * cellStockSize + 1);
    }

    private void drawPieces(Graphics g, List<Piece> pieces){
        for(Piece p : pieces){
            drawPiece(g, p);
        }
    }

    private void drawPiece(Graphics g, Piece p){
        g.setColor(p.getColor());
        for (int y = 0; y < p.getCells().length; y++) {
            for (int x = 0; x < p.getCells()[y].length; x++) {
                if(p.getCells()[y][x]){
                    g.fillRect(getMapX(p.x + x), getMapY(p.y + y), cellSize, cellSize);
                }
            }
        }
    }
    
    private void drawStock(Graphics g, Piece[] ps, int index){
        int ystart = 0;
        for (int i = 0; i < ps.length; i++) {
            Piece p = ps[(i + index) % ps.length];
            drawPieceStock(g, p, ystart);
            ystart += p.height + 1;
        }
    }

    private void drawPieceStock(Graphics g, Piece p, int ystart){
        g.setColor(p.getColor());
        for (int y = 0; y < p.getCells().length; y++) {
            for (int x = 0; x < p.getCells()[y].length; x++) {
                if(p.getCells()[y][x]){
                    g.fillRect(getStockX(x), getStockY(y, ystart), cellStockSize, cellStockSize);
                }
            }
        }
    }
}
