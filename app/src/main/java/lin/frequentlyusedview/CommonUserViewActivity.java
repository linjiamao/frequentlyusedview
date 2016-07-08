package lin.frequentlyusedview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 常用的的控件集合
 */
public class CommonUserViewActivity extends BaseActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonuserview);

    }
    public void indicator(View view){
        intent = new Intent();
        intent.setClass(this,MagicIndicatorActivity.class);
        startActivity(intent);
    }

    public void recycleView(View view){
        intent = new Intent();
        intent.setClass(this,RecycleViewActivity.class);
        startActivity(intent);
    }

}
