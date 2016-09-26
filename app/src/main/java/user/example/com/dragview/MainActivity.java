package user.example.com.dragview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import user.example.com.dragview.adapter.GridViewAdapter;
import user.example.com.dragview.view.DragGridView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_edit;
    private DragGridView dragview;
    private List<Bitmap> mDatas;
    private GridViewAdapter adapter;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DragView");
        initDatas();
        initView();
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        turnBitmap(R.drawable.a);
        turnBitmap(R.drawable.b);
        turnBitmap(R.drawable.c);
        turnBitmap(R.drawable.d);
        turnBitmap(R.drawable.e);
        turnBitmap(R.drawable.f);
        turnBitmap(R.drawable.add_picture);

    }

    private void turnBitmap(int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        mDatas.add(bitmap);
    }

    private void initView() {
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        dragview = (DragGridView) findViewById(R.id.dragview);
        adapter = new GridViewAdapter(this, mDatas, flag);
        dragview.setAdapter(adapter);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    tv_edit.setText("完成");
                    flag = false;
                } else {
                    tv_edit.setText("编辑");
                    flag = true;
                }
                adapter.setFlag(flag);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
