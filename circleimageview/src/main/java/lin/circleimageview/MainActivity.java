package lin.circleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 圆形图片
 */
public class MainActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        mImageView = (ImageView)
        //使用第三方框架显示圆形图片  de.hdodenhof:circleimageview:2.1.0
//        Glide.with(this).load("http://client.yeeaoobox.com//uploadimages//86591466843429.png")
//                .placeholder(R.mipmap.hugh).into(circleImageView);

        //使用Glide显示圆
//        Glide.with(this).load("http://client.yeeaoobox.com//uploadimages//86591466843429.png")
//                .transform(new GlideCircleTransform(this))
//                .placeholder(R.mipmap.hugh).into(mImageView);

        //使用Glide显示圆角图片
        Glide.with(this).load("http://client.yeeaoobox.com//uploadimages//86591466843429.png")
                .transform(new GlideRoundTransform(this,15,50))
                .placeholder(R.mipmap.hugh).into(mImageView);

    }
}
