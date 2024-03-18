package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.Room;
import byog.Core.RoomArray;
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
        int roomNum = RANDOM.nextInt(35 - 15) + 15;
        //int roomNum = 3;
        //获取room数量 并创建RoomArray
        RoomArray array = new RoomArray(roomNum);

        //createWall(world, Tileset.WALL);

        for (int i = 0; i < roomNum; i++) {
            genRoom(world, array, Tileset.WALL);
        }
        //打印出所有的随机坐标数据
        printArray(array.AllRandomPos(RANDOM));
        System.out.println(" ");
        //输出所有room的位置
        array.printAllRoomPos();
        //链接初始位置
        //linkAllPos(array.AllPos(),world,Tileset.FLOOR);
        //链接到所有的随机坐标
        linkAllPos(array.AllRandomPos(RANDOM), world, Tileset.FLOOR);
        ter.renderFrame(world);
    }


    //genRom 生成房间位置 长宽 调用is_addable判断数据是否合法 调用addRoom传入数据生成房间
    public static void genRoom(TETile[][] object,RoomArray array,TETile flag) {
        int roomPosX = RANDOM.nextInt(WIDTH - 2) + 1;
        int roomPosY = RANDOM.nextInt(HEIGHT - 2) + 1;
        int roomWidth = RANDOM.nextInt(8) + 1;
        int roomHeight = RANDOM.nextInt(8) + 1;
        while (is_notaddable(roomPosX, roomPosY, roomWidth, roomHeight)){
           roomPosX = RANDOM.nextInt(WIDTH - 2) + 1;
           roomPosY = RANDOM.nextInt(HEIGHT - 2) + 1;
           roomWidth = RANDOM.nextInt(8) + 1;
           roomHeight = RANDOM.nextInt(8) + 1;
        }
        //在创建room之前把信息存放在array里面
        array.addRoom(roomPosX, roomPosY, roomWidth, roomHeight);
        //创建room
        addRoom(object,roomPosX,roomPosY,roomWidth,roomHeight,flag);
    }

    //用位置 长宽数据生成矩形房间
    public static void addRoom(TETile[][] object, int pos_x, int pos_y, int width, int height, TETile flag) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                object[pos_x + j][pos_y - i] = flag;
            }
        }
    }

    //根据房间生成随机数据和已有位置的object二维数组的数据进行比较 判断整个矩形是否可以添加
    public static boolean is_notaddable(int pos_x, int pos_y, int width, int height) {
        if (pos_x + width > WIDTH - 2 || pos_y - height < 1) {
            return true;
        }
        return false;
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
        for (int i = 0; i < allpos.length; i = i + 2) {
            if (i + 2 > allpos.length) {
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
                    object[j][i] = flag;
                }
            }
        }
    }
    public static boolean isCreatabe(TETile[][] object, int x, int y) {

        return true;
    }

    public static void printArray(int[][] arr) {
        // 遍历每一行
        for (int i = 0; i < arr.length; i++) {
            // 遍历当前行的每一个元素
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            // 打印换行
            System.out.println();
        }
    }
}
