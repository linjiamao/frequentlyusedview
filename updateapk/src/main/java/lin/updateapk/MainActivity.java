package lin.updateapk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_view);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVersion();
            }
        });
        checkVersion();

    }

    /**
     * 监测是否有新版本
     */
    private void checkVersion(){
        AlertDialog.Builder builder =new  AlertDialog.Builder(this);
        builder.setTitle("监测到最新版本，是否更新？");
        builder.setPositiveButton("安装", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this,UpdateService.class);
                intent.putExtra("apkUrl",
                        "http://p.gdown.baidu.com/077af45090c4c02d4c568775f9dc5865f67eb6972620c74dc6da2df5217f406f444cbe1f21dd1644c2f0ee0abb47a93a032bff90bfb1fd3a0ea4e61123b44f7720288816e8d89dee33eb5a65bbe34dbfaf85a18ca6df326552578c1bab15a86d68f355e5fc1616d274351951aae1fa45c58f29b5df2eeb112f2cc0365081f591ffa0dc70817bc5cacc2bd268400789365f012fd0a832b01f1f7af8785445af927da721a2c3cf303bcc48881b33201b8bb4f26122cb603bcf9ce37795823995412b8a0b1b96febc4b");
                startService(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
