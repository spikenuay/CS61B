package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 27;
    private static final int HEIGHT = 30;
    private static final Random RANDOM = new Random();
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < 3; i ++) {
            addHexagon(3,world,0,23 - i * 6,randomTile());
        }
        for (int i = 0; i < 4; i ++) {
            addHexagon(3,world,5,26 - i * 6,randomTile());
        }


        for (int i = 0; i < 5; i ++) {
            addHexagon(3,world,10,29 - i * 6,randomTile());
        }


        for (int i = 0; i < 4; i ++) {
            addHexagon(3,world,15,26 - i * 6,randomTile());
        }
        for (int i = 0; i < 3; i ++) {
            addHexagon(3,world,20,23 - i * 6,randomTile());
        }

        //addHexagon(3,world,0,29,randomTile());


        ter.renderFrame(world);
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }
    public static void addHexagon(int size, TETile[][] array, int pos_x, int pos_y, TETile flag) {
        int Row = row(size);
        int Middle_Len = middle_length(size);
        int Up_Down_Row = up_down(Row);

        for (int i = 0; i < Up_Down_Row; i ++) {
            int row_size = size + 2 * i;
            int OffSize = offsize(size + 2 * i,Middle_Len);
            for (int j = 0; j < row_size; j++) {
                array[pos_x + OffSize + j][pos_y - i] = flag;
            }
        }
        for (int i = 0; i < 2; i ++) {
            for (int j = 0; j < Middle_Len; j ++) {
                array[pos_x + j][pos_y - Up_Down_Row - i] = flag;
            }
        }
        for (int i = 0; i < Up_Down_Row; i ++) {
            int row_size = Middle_Len - 2 - 2 * i;
            int OffSize = offsize(row_size,Middle_Len);
            for (int j = 0; j < row_size; j++) {
                array[pos_x + OffSize + j][pos_y - i - Up_Down_Row - 2] = flag;
            }
        }

    }

    public static int row(int size) {
        if (size ==  2) {
            return 4;
        }
        else if (size > 2) {
            return 4 + (size - 2) * 2;
        }
        return 0;
    }
    public static int middle_length(int size) {
        return 4 + (size - 2) * 3;
    }
    public static int offsize(int size, int middle_length) {
        return (middle_length - size) / 2;
    }
    public static int up_down(int row) {
        return (row - 2) / 2;
    }



    @Test
    public void testrow() {
        assertEquals(4,row(2));
        assertEquals(6,row(3));
        assertEquals(8,row(4));
    }
    @Test
    public void testMiddle_length() {
        assertEquals(4,middle_length(2));
        assertEquals(7,middle_length(3));
        assertEquals(10,middle_length(4));
    }
    @Test
    public void testOffize() {
        assertEquals(3,offsize(4,10));
        assertEquals(2,offsize(6,10));
        assertEquals(1,offsize(8,10));
    }
    @Test
    public void testUpdown() {
        assertEquals(1,up_down(4));
        assertEquals(2,up_down(6));
        assertEquals(3,up_down(8));
    }
}
