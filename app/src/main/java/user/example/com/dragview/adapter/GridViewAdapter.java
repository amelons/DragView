package user.example.com.dragview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import user.example.com.dragview.R;

/**
 * Created by on 2016.8.30
 * @author amelons
 */
public class GridViewAdapter extends BaseAdapter {
    private List<Bitmap> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private boolean flag=true;
    /**
     * 点击影藏的position
     */
    private int hidePosition = AdapterView.INVALID_POSITION;
    private DisplayMetrics metrics;

    public GridViewAdapter(Context context, List<Bitmap> mDatas, boolean flag) {
        this.mContext = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
        metrics = mContext.getResources().getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.images_gallary_item,
                    null);
            holder = new ViewHolder();
            holder.ivThumb = (ImageView) convertView
                    .findViewById(R.id.iv_cs_gallay_item);
            holder.ivThumb.setLayoutParams(new RelativeLayout.LayoutParams(
                    setWidth_px(), setWidth_px() * 3 / 4));
            holder.ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.tvdelete = (ImageView) convertView
                    .findViewById(R.id.iv_cs_gallay_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (! flag && position !=mDatas.size()-1) {
            holder.tvdelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvdelete.setVisibility(View.INVISIBLE);
        }

        final Bitmap bitmap = mDatas.get(position);
        if (bitmap != null) {
            holder.ivThumb.setImageBitmap(bitmap);
        }


        holder.tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });

        //hide时隐藏imageview
        if (position != hidePosition) {
            convertView.setVisibility(View.VISIBLE);
        } else {
            convertView.setVisibility(View.INVISIBLE);
        }
        convertView.setId(position);

        return convertView;
    }

    class ViewHolder {
        private ImageView ivThumb;
        private ImageView tvdelete;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    /**
     * 更新拖动时的gridView
     *
     * @param draggedPos 拖动的position
     * @param destPos    目表position
     */
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if (draggedPos < destPos) {
            mDatas.add(destPos + 1, getItem(draggedPos));
            mDatas.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if (draggedPos > destPos) {
            mDatas.add(destPos, getItem(draggedPos));
            mDatas.remove(draggedPos + 1);
        }
        hidePosition = destPos;
        //       notifyDataSetChanged();
    }

    public int setWidth_px() {
        return dip2px((px2dip(metrics.widthPixels) - 24 - 24) / 3);
    }

    public int dip2px(float dipValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dip(float pxValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
