package me.xiaopan.android.spear.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.xiaoapn.android.spear.sample.R;
import me.xiaopan.android.spear.sample.DisplayOptionsType;
import me.xiaopan.android.spear.sample.net.request.StarCatalogRequest;
import me.xiaopan.android.spear.SpearImageView;

/**
 * 明星目录适配器
 */
public class StarCatalogAdapter extends RecyclerView.Adapter{
    private static final int ITEM_TYPE_DATA = 0;
    private static final int ITEM_TYPE_CATEGORY_TITLE = 1;

    private Context context;
    private List<Object> items;
    private View.OnClickListener onClickListener;
    private int itemWidth;

    public StarCatalogAdapter(Context context, StarCatalogRequest.Result result, final OnImageClickListener onImageClickListener) {
        this.context = context;
        append(result);
        int space = (int) context.getResources().getDimension(R.dimen.home_category_margin_border);
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        itemWidth = (screenWidth - (space*4))/3;
        this.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() instanceof StarCatalogRequest.Star){
                    if(onImageClickListener != null){
                        onImageClickListener.onClickImage((StarCatalogRequest.Star) v.getTag());
                    }
                }
            }
        };
    }

    public void append(StarCatalogRequest.Result result){
        if(items == null){
            items = new ArrayList<>(result.getStarList().size());
        }
        items.add(result.getTitle());
        for(int w = 0, size = result.getStarList().size(); w < size;){
            int number = size - w;
            if(number == 1){
                DataItem dataItem = new DataItem();
                dataItem.star1 = result.getStarList().get(w++);
                items.add(dataItem);
            }else if(number == 2){
                DataItem dataItem = new DataItem();
                dataItem.star1 = result.getStarList().get(w++);
                dataItem.star2 = result.getStarList().get(w++);
                items.add(dataItem);
            }else{
                DataItem dataItem = new DataItem();
                dataItem.star1 = result.getStarList().get(w++);
                dataItem.star2 = result.getStarList().get(w++);
                dataItem.star3 = result.getStarList().get(w++);
                items.add(dataItem);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = items.get(position);
        if(object instanceof DataItem){
            return ITEM_TYPE_DATA;
        }else if(object instanceof String){
            return ITEM_TYPE_CATEGORY_TITLE;
        }else{
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == ITEM_TYPE_CATEGORY_TITLE){
            viewHolder = new CategoryTitleHolder(LayoutInflater.from(context).inflate(R.layout.list_item_title, parent, false));
        }else if(viewType == ITEM_TYPE_DATA){
            ItemHolder itemHolder = new ItemHolder(LayoutInflater.from(context).inflate(R.layout.list_item_star_head_portrait, parent, false));

            itemHolder.oneLayout.setOnClickListener(onClickListener);
            itemHolder.oneSpearImageView.setDisplayOptions(DisplayOptionsType.STAR_HEAD_PORTRAIT);
            itemHolder.oneSpearImageView.setEnableClickRipple(true);
            ViewGroup.LayoutParams params = itemHolder.oneSpearImageView.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            itemHolder.oneSpearImageView.setLayoutParams(params);
            params = itemHolder.oneNameTextView.getLayoutParams();
            params.width = itemWidth;
            itemHolder.oneNameTextView.setLayoutParams(params);

            itemHolder.twoLayout.setOnClickListener(onClickListener);
            itemHolder.twoSpearImageView.setDisplayOptions(DisplayOptionsType.STAR_HEAD_PORTRAIT);
            itemHolder.twoSpearImageView.setEnableClickRipple(true);
            params = itemHolder.twoSpearImageView.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            itemHolder.twoSpearImageView.setLayoutParams(params);
            params = itemHolder.twoNameTextView.getLayoutParams();
            params.width = itemWidth;
            itemHolder.twoNameTextView.setLayoutParams(params);

            itemHolder.threeLayout.setOnClickListener(onClickListener);
            itemHolder.threeSpearImageView.setDisplayOptions(DisplayOptionsType.STAR_HEAD_PORTRAIT);
            itemHolder.threeSpearImageView.setEnableClickRipple(true);
            params = itemHolder.threeSpearImageView.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            itemHolder.threeSpearImageView.setLayoutParams(params);
            params = itemHolder.threeNameTextView.getLayoutParams();
            params.width = itemWidth;
            itemHolder.threeNameTextView.setLayoutParams(params);

            viewHolder = itemHolder;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemHolder){
            DataItem dataItem = (DataItem) items.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            itemHolder.oneNameTextView.setText(dataItem.star1.getName());
            itemHolder.oneSpearImageView.setImageFromUri(dataItem.star1.getAvatarUrl());
            itemHolder.oneLayout.setTag(dataItem.star1);

            if(dataItem.star2 != null){
                itemHolder.twoNameTextView.setText(dataItem.star2.getName());
                itemHolder.twoSpearImageView.setImageFromUri(dataItem.star2.getAvatarUrl());
                itemHolder.twoLayout.setTag(dataItem.star2);
                itemHolder.twoLayout.setVisibility(View.VISIBLE);
            }else{
                itemHolder.twoLayout.setVisibility(View.INVISIBLE);
            }

            if(dataItem.star3 != null){
                itemHolder.threeNameTextView.setText(dataItem.star3.getName());
                itemHolder.threeSpearImageView.setImageFromUri(dataItem.star3.getAvatarUrl());
                itemHolder.threeLayout.setTag(dataItem.star3);
                itemHolder.threeLayout.setVisibility(View.VISIBLE);
            }else{
                itemHolder.threeLayout.setVisibility(View.INVISIBLE);
            }
        }else if(holder instanceof CategoryTitleHolder){
            String title = (String) items.get(position);
            CategoryTitleHolder titleHolder = (CategoryTitleHolder) holder;
            titleHolder.categoryTitleTextView.setText(title);
        }
    }

    private static class CategoryTitleHolder extends RecyclerView.ViewHolder{
        private TextView categoryTitleTextView;

        public CategoryTitleHolder(View itemView) {
            super(itemView);
            categoryTitleTextView = (TextView) itemView;
        }
    }

    private static class ItemHolder extends RecyclerView.ViewHolder{
        private View oneLayout;
        private SpearImageView oneSpearImageView;
        private TextView oneNameTextView;

        private View twoLayout;
        private SpearImageView twoSpearImageView;
        private TextView twoNameTextView;

        private View threeLayout;
        private SpearImageView threeSpearImageView;
        private TextView threeNameTextView;

        public ItemHolder(View itemView) {
            super(itemView);

            oneLayout = itemView.findViewById(R.id.layout_starHeadPortraitItem_one);
            oneSpearImageView = (SpearImageView) itemView.findViewById(R.id.spearImage_starHeadPortraitItem_one);
            oneNameTextView = (TextView) itemView.findViewById(R.id.text_starHeadPortraitItem_one);

            twoLayout = itemView.findViewById(R.id.layout_starHeadPortraitItem_two);
            twoSpearImageView = (SpearImageView) itemView.findViewById(R.id.spearImage_starHeadPortraitItem_two);
            twoNameTextView = (TextView) itemView.findViewById(R.id.text_starHeadPortraitItem_two);

            threeLayout = itemView.findViewById(R.id.layout_starHeadPortraitItem_three);
            threeSpearImageView = (SpearImageView) itemView.findViewById(R.id.spearImage_starHeadPortraitItem_three);
            threeNameTextView = (TextView) itemView.findViewById(R.id.text_starHeadPortraitItem_three);
        }
    }

    public interface OnImageClickListener{
        public void onClickImage(StarCatalogRequest.Star star);
    }

    private static class DataItem{
        private StarCatalogRequest.Star star1;
        private StarCatalogRequest.Star star2;
        private StarCatalogRequest.Star star3;
    }
}