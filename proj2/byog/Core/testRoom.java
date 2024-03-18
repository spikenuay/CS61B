package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.Room;
import byog.Core.RoomArray;
import java.util.Random;
public class testRoom {
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
        int roomNum = 3;
        //获取room数量 并创建RoomArray
        RoomArray array = new RoomArray(roomNum);
        //addRoom(world,0,29,5,1,Tileset.FLOWER);
        world[1][0] = Tileset.WALL;
        //createWall(world, Tileset.WALL);
        ter.renderFrame(world);
    }


    //genRom 生成房间位置 长宽 调用is_addable判断数据是否合法 调用addRoom传入数据生成房间

    //用位置 长宽数据生成矩形房间
    public static void addRoom(TETile[][] object, int pos_x, int pos_y, int width, int height, TETile flag) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                object[pos_x + j][pos_y - i] = flag;
            }
        }
    }

    //根据房间生成随机数据和已有位置的object二维数组的数据进行比较 判断整个矩形是否可以添加
    public static boolean is_addable(TETile[][] object, int pos_x, int pos_y, int width, int height) {
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j++) {
                if (pos_x + width > WIDTH - 1 || pos_y  < height || pos_x == 0 ) {
                    return false;
                }
                if (object[pos_x + j][pos_y - i] == Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void PosLink(TETile[][] object, int x1, int y1, int x2, int y2, TETile flag) {
        //如果random为true 先移动x 再移动y 否则反之
        if (random()) {
            while (x1 != x2) {
                if (x1 < x2) {
                    x1++;
                    object[x1][y1] = flag;
                }
                if (x2 < x1) {
                    x2 ++;
                    object[x2][y2] = flag;
                }
            }
            while (y1 != y2) {
                if (y1 < y2) {
                    y1++;
                    object[x1][y1] = flag;
                }
                if (y2 < y1) {
                    y2 ++;
                    object[x2][y2] = flag;
                }
            }
        }
        while (y1 != y2) {
            if (y1 < y2) {
                y1++;
                object[x1][y1] = flag;
            }
            if (y2 < y1) {
                y2 ++;
                object[x2][y2] = flag;
            }
        }
        while (x1 != x2) {
            if (x1 < x2) {
                x1++;
                object[x1][y1] = flag;
            }
            if (x2 < x1) {
                x2 ++;
                object[x2][y2] = flag;
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

    public static void linkAllPos(int[][] allpos,TETile[][] object, TETile flag) {
        for (int i = 0; i < allpos.length; i = i + 1) {
            if (i == allpos.length || i + 2 > allpos.length) {
                return;
            }
            PosLink(object, allpos[i][0], allpos[i][1], allpos[i + 1][0], allpos[i + 1][1], flag);
        }

    }
    public static void createWall(TETile[][] object, TETile flag) {
        int x, y;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                x = j;
                y = i;
                if (isCreatabe(object, x, y)) {
                    object[i][j] = flag;
                }
            }
        }
    }
    public static boolean isCreatabe(TETile[][] object, int x, int y) {
        return true;
    }
}
