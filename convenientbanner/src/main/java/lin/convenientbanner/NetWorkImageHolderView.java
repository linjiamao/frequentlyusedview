package lin.convenientbanner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

/**
 * Created by yo on 2016/8/4.
 *
 */
public class NetWorkImageHolderView implements Holder<BannerItem>{
    private View mView;
    @Override
    public View createView(Context context) {
        mView = View.inflate(context,R.layout.item_banner,null);
        return mView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerItem data) {
        ImageView iv_banner = (ImageView) mView.findViewById(R.id.iv_banner);
        Glide.with(context).load(data.getImageUrl()).placeholder(R.mipmap.default_pic).into(iv_banner);
    }
}
