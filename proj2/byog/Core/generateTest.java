package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
public class generateTest {
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
        int roomNum = RANDOM.nextInt(25 - 20) + 25;

        //RoomArray array = new RoomArray(roomNum);

        for (int i = 0; i < roomNum; i++) {
            genRoom(world);
        }
        ter.renderFrame(world);
    }


    //genRom 生成房间位置 长宽 调用is_addable判断数据是否合法 调用addRoom传入数据生成房间
    public static void genRoom(TETile[][] object) {
        int roomPosX = RANDOM.nextInt(WIDTH - 1);
        int roomPosY = RANDOM.nextInt(HEIGHT - 1);
        int roomWidth = RANDOM.nextInt(8);
        int roomHeight = RANDOM.nextInt(8);
        while (!is_addable(object, roomPosX, roomPosY, roomWidth, roomHeight)){
           roomPosX = RANDOM.nextInt(WIDTH - 1);
           roomPosY = RANDOM.nextInt(HEIGHT - 1);
           roomWidth = RANDOM.nextInt(8);
           roomHeight = RANDOM.nextInt(8);
        }
        addRoom(object,roomPosX,roomPosY,roomWidth,roomHeight);
    }

    //用位置 长宽数据生成矩形房间
    public static void addRoom(TETile[][] object, int pos_x, int pos_y, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                object[pos_x + j][pos_y + i] = Tileset.WALL;
            }
        }
    }

    //根据房间生成随机数据和已有位置的object二维数组的数据进行比较 判断整个矩形是否可以添加
    public static boolean is_addable(TETile[][] object, int pos_x, int pos_y, int width, int height) {
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j++) {
                if (pos_x + width > WIDTH - 1 || pos_y + height > HEIGHT - 1) {
                    return false;
                }
                if (object[pos_x + j][pos_y + i] == Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
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
