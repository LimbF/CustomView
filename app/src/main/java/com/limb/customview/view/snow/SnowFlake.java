package com.limb.customview.view.snow;

/**
 * Created by limb on 2016/5/5.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class SnowFlake {

    private static final float ANGE_RANGE = 0.1f;    //
    private static final float HALF_ANGLE_RANGE = ANGE_RANGE / 2f;
    private static final float HALF_PI = (float) Math.PI / 2f;
    private static final float ANGLE_SEED = 25f;
    private static final float ANGLE_DIVISOR = 10000f;
    private static final float INCREMENT_LOWER = 2f;
    private static final float INCREMENT_UPPER = 4f;
    private static final float FLAKE_SIZE_LOWER = 7f;//最小雪花大小
    private static final float FLAKE_SIZE_UPPER = 20f;//最大雪花大小

    private final Random random;
    private final Point position;
    private float angle;
    private final float increment;
    private final float flakeSize;
    private final Paint paint;

    public static SnowFlake create(int width, int height, Paint paint) {
        Random random = new Random();
        int x = random.getRandom(width);//得到[0~width)的整数width与height都是外层view的尺寸。
        int y = random.getRandom(height);
        Point position = new Point(x, y);//设置雪花刚一出来的随机位置。
        //设置Random.getRandom(ANGLE_SEED)/ANGLE_SEED得到[0~1)再*ANGE_RANGE得到[0~0.1f)的数据，再减去0.05得到[-0.05~0.05)的数据。
        float angle = random.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE;
        //得到[2f~4f)的随机数据
        float increment = random.getRandom(INCREMENT_LOWER, INCREMENT_UPPER);
        //得到[7f~20f)的随机数据
        float flakeSize = random.getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER);
        //返回雪花对象。
        return new SnowFlake(random, position, angle, increment, flakeSize, paint);
    }

    SnowFlake(Random random, Point position, float angle, float increment, float flakeSize, Paint paint) {
        this.random = random;
        this.position = position;
        this.angle = angle;//[-0.05~0.05)
        this.increment = increment;//[2f~4f)
        this.flakeSize = flakeSize;//[7f~20f)
        this.paint = paint;
    }

    private void move(int width, int height) {
        //x方向的偏移量小，y方向的大，是为了让雪花快点落下。
        double x = position.x + (increment * Math.cos(angle));//Math.cos(angle)约为1
        double y = position.y + (increment * Math.sin(angle));//Math.sin(0.05~-0.05) = +-[0~8.7)
      /*
       *设置雪花的位置为正，不跑出屏幕。[0~1)*2*ANGLE_SEED-ANGLE_SEED等于+-(0~ANGLE_SEED],为+-(0~25f]。然后再/10000f====+-(0~0.0025],
       *然后再加[-0.05~0.05)泥妈，快算晕了。大神们果然不好理解。（-0.0525~0.0525）
       */

        angle += random.getRandom(-ANGLE_SEED, ANGLE_SEED) / ANGLE_DIVISOR;

        position.set((int) x, (int) y);//设置新的位置

        if (!isInside(width, height)) {
            //如果雪花不在view视图内，则重设置他们的位置。
            reset(width);
        }
    }

    private boolean isInside(int width, int height) {
        //TODO 设置雪花位置
        int x = position.x;
        int y = position.y;
        //判断x坐标x>雪花尺寸加1距原点的距离（负方向）。x<background宽，同样的方式理解y。由于上面的设置，其实x方向,雪花是不可能跑出屏幕的，只有y方向上会。
        return x >= -flakeSize - 1 && x + flakeSize <= width && y >= -flakeSize - 1 && y - flakeSize < height;

    }

    private void reset(int width) {
        //当雪花落到屏幕最下方以下时，重新设置雪花从
        position.x = random.getRandom(width);//x轴设设置一个view内的随机位置就行
        position.y = (int) (-flakeSize - 1);//view的（0,0）点在view的左上角，所以，当y为负时，则在view的上方。看起来像是一个新的雪花从上落下。
        angle = random.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE;
    }

    public void draw(Canvas canvas) {
        //绘制方法
        int width = canvas.getWidth();
        int height = canvas.getHeight();//雪花所整个view高度与宽度
        move(width, height);//雪花移动,传的参数为view的的北景大小。
        //画一个个雪花。其实就是画一个个球。其坐标，尺寸，画笔都是原来在create方法时就定义 了的，之后保存在flakesnow数组里了。
        canvas.drawCircle(position.x, position.y, flakeSize, paint);
    }


}