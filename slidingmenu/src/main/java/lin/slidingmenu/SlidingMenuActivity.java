package lin.slidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import lin.slidingmenu.view.SlidingMenu;

/**
 * slidingMenu的简单实现
 */

public class SlidingMenuActivity extends AppCompatActivity {
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        getSupportActionBar().hide();
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
    }
    public void toggleMenu(View view){
        slidingMenu.toggle();
    }
}
