package lin.butterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

/**
 * ButterKnife 注解 编译前生成代码 对运行时没有影响
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.listview)
    ListView lv;
    private List<String> mList;
    @BindView(R.id.editText)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setText("try it");
        initData();
        lv.setAdapter(new MyAdapter(this,mList));
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.textView)
    public void tvTitle(){
        Toast.makeText(MainActivity.this, textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.layout)
    public void clickLayout(View v){
        Toast.makeText(MainActivity.this, "点击了layout", Toast.LENGTH_SHORT).show();
    }
    @OnItemClick(R.id.listview)
    public void onItemClick(int position){
        Toast.makeText(MainActivity.this, "position = "+position, Toast.LENGTH_SHORT).show();
    }

    @OnItemSelected(value = R.id.listview,callback = OnItemSelected.Callback.NOTHING_SELECTED)
    void onNo(AdapterView<?> parent){
        Toast.makeText(MainActivity.this, "你妹", Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("item1");
        mList.add("item2");
        mList.add("item3");
        mList.add("item4");
        mList.add("item5");
        mList.add("item6");
        mList.add("item7");
        mList.add("item8");
        mList.add("item9");
        mList.add("item10");
    }
    @OnTextChanged(value = R.id.editText,callback = OnTextChanged.Callback.TEXT_CHANGED)
    void afterText(CharSequence s,int start,int before,int count){
        Toast.makeText(MainActivity.this, "文字 ="+s.toString(), Toast.LENGTH_SHORT).show();
    }
}
