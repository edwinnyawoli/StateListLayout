package com.edwinnyawoli.statelistlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * The StateListLayout is a layout whose visible content is dependent on the state in which it is.
 */

public class StateListLayout extends FrameLayout {
    private OnStateChangedListener onStateChangedListener;
    private State activeState;
    // A map of states to their index positions in the parent (child index).
    private Map<State, Integer> stateIndexMap = new HashMap<>();

    public StateListLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateListLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateListLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StateListLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateListLayout);

        try {
            activeState = State.fromName(a.getString(R.styleable.StateListLayout_active_state), State.UNKNOWN);
        } finally {
            a.recycle();
        }
    }


    public View getView(State state) {
        View view = null;
        int childIndex = stateIndexMap.get(state);
        if (childIndex >= 0)
            view = getChildAt(childIndex);
        return view;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        boolean isActiveStateUnknown = activeState.equals(State.UNKNOWN);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            State state = ((LayoutParams) child.getLayoutParams()).state;

            if (!isActiveStateUnknown && state.equals(activeState)) {
                child.setVisibility(VISIBLE);
            } else
                child.setVisibility(GONE);

            if (!state.equals(State.UNKNOWN)) {
                stateIndexMap.put(state, i);
            }
        }
    }

    public State getState() {
        return activeState;
    }

    public void setState(State state) {
        if (!this.activeState.equals(state)) {
            int viewIndex = stateIndexMap.get(state);
            if (viewIndex >= 0) {

                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    if (i == viewIndex)
                        child.setVisibility(VISIBLE);
                    else
                        child.setVisibility(GONE);
                }

                State oldState = activeState;
                this.activeState = state;
                if (onStateChangedListener != null)
                    onStateChangedListener.onStateChanged(oldState, activeState);

            }
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    public interface OnStateChangedListener {
        void onStateChanged(State oldState, State newState);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        private State state = State.UNKNOWN;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.StateListLayout_Layout);

            try {
                this.state = State.fromName(a.getString(R.styleable.StateListLayout_Layout_state), State.UNKNOWN);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }
    }
}
