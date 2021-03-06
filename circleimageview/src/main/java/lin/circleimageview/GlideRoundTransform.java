package lin.circleimageview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by yo on 2016/8/5.
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius_left = 0f;
    private static float radius_right = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4,4);
    }

    public GlideRoundTransform(Context context, int left_dp,int right_dp) {
        super(context);
        this.radius_left = Resources.getSystem().getDisplayMetrics().density * left_dp;
        this.radius_right = Resources.getSystem().getDisplayMetrics().density * right_dp;
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius_left, radius_right, paint);
        return result;
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius_left);
    }
}
