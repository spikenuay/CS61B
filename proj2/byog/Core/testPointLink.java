package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;
public class testPointLink {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 287323451;
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

        int x1 = RANDOM.nextInt(59);
        int y1 = RANDOM.nextInt(29);
        int x2 = RANDOM.nextInt(59);
        int y2 = RANDOM.nextInt(29);
        world[x1][y1] = Tileset.WALL;
        world[x2][y2] = Tileset.WALL;
        PointLink(world, x1, y1, x2, y2);
        ter.renderFrame(world);
    }

    public static void PointLink(TETile[][] object, int x1, int y1, int x2, int y2) {
        //如果random为true 先移动x 再移动y 否则反之
        if (random()) {
            while (x1 != x2) {
                if (x1 < x2) {
                    x1++;
                    object[x1][y1] = Tileset.WALL;
                }
                if (x2 < x1) {
                    x2 ++;
                    object[x2][y2] = Tileset.WALL;
                }
            }
            while (y1 != y2) {
                if (y1 < y2) {
                    y1++;
                    object[x1][y1] = Tileset.WALL;
                }
                if (y2 < y1) {
                    y2 ++;
                    object[x2][y2] = Tileset.WALL;
                }
            }
        }
        while (y1 != y2) {
            if (y1 < y2) {
                y1++;
                object[x1][y1] = Tileset.WALL;
            }
            if (y2 < y1) {
                y2 ++;
                object[x2][y2] = Tileset.WALL;
            }
        }
        while (x1 != x2) {
            if (x1 < x2) {
                x1++;
                object[x1][y1] = Tileset.WALL;
            }
            if (x2 < x1) {
                x2 ++;
                object[x2][y2] = Tileset.WALL;
            }
        }
    }

    public static boolean random() {
        switch (RANDOM.nextInt(2)) {
            case 0: return true;
            case 1: return false;
        }
        return false;
    }
}
