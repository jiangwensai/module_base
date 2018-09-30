package com.example.jiangwensai.modulebase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiangwensai.modulebase.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<B, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    public static final int HEADER_VIEW = 1;
    public static final int FOOTER_VIEW = 2;
    public static final int ITEM_VIEW = 0;
    private List<B> mDataList;
    private Context mContext;
    private View mHeaderView;
    private View mFooterView;

    public BaseRecyclerViewAdapter(List<B> list) {
        mDataList = list;
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        V baseViewHolder;
        switch (viewType) {
            case ITEM_VIEW:
                baseViewHolder = createBaseViewHolder(LayoutInflater.from(mContext).inflate(getItemResourceId(), parent, false));
                break;
            case HEADER_VIEW:
                baseViewHolder = createBaseViewHolder(mHeaderView);
                break;
            case FOOTER_VIEW:
                baseViewHolder = createBaseViewHolder(mFooterView);
                break;
            default:
                baseViewHolder = createBaseViewHolder(LayoutInflater.from(mContext).inflate(getItemResourceId(), parent, false));
                break;
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        int type = holder.getItemViewType();
        switch (type) {
            case 0:
                bindItemView(holder, mDataList.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                bindItemView(holder, mDataList.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mHeaderView != null) {
            count++;
        }
        if (mFooterView != null) {
            count++;
        }
        if (!ObjectUtils.isEmpty(mDataList)) {
            count += mDataList.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return HEADER_VIEW;
        }
        if (mFooterView != null) {
            int index = 0;
            if (!ObjectUtils.isEmpty(mDataList)) {
                index += mDataList.size();
            }
            if (mHeaderView != null) {
                index++;
            }
            if (index == position) {
                return FOOTER_VIEW;
            }
        }
        return ITEM_VIEW;
    }

    public List<B> getDataList() {
        return mDataList;
    }

    public void updateDataList(List<B> list) {
        mDataList = list;
        notifyDataSetChanged();
    }

    public void addItem(B item) {
        if (ObjectUtils.isEmpty(mDataList)) {
            return;
        }
        mDataList.add(item);
        notifyItemChanged(mDataList.size() - 1);
    }

    public void addItem(B item, int position) {
        if (ObjectUtils.isEmpty(mDataList)) {
            return;
        }
        mDataList.add(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(B item) {
        if (ObjectUtils.isEmpty(mDataList)) {
            return;
        }
        int i;
        for (i = 0; i < mDataList.size(); i++) {
            if (item.equals(mDataList.get(i))) {
                mDataList.remove(item);
                notifyItemChanged(i);
            }
        }
    }

    public void setHeaderView(View view) {
        mHeaderView = view;
    }

    public void setFooterView(View view) {
        mFooterView = view;
    }

    public void removeHeaderView() {
        if (mHeaderView == null) return;
        mHeaderView = null;
        notifyDataSetChanged();
    }

    public void removeFooterView() {
        if (mFooterView == null) return;
        mFooterView = null;
        notifyDataSetChanged();
    }

    public int getHeaderLayoutCount() {
        if (mHeaderView == null) {
            return 0;
        }
        return 1;
    }

    public int getFooterLayoutCount() {
        if (mFooterView == null) {
            return 0;
        }
        return 1;
    }

    protected V createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        return createGenericKInstance(z, view);
    }

    private V createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (V) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (V) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (RecyclerView.ViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    public View getHeaderLayout() {
        return mHeaderView;
    }

    public View getFooterLayout() {
        return mFooterView;
    }

    protected abstract int getItemResourceId();

    protected abstract void bindItemView(V holder, B item);
}
