package edu.csulb.android.assigment3_drawcustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private Path path = new Path();

    private static final String EXTRA_EVENT_LIST = "event_list";
    private static final String EXTRA_STATE = "instance_state";
    private ArrayList<MotionEvent> motionEventArrayList = new ArrayList<MotionEvent>(100);

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);
    }

    public boolean onTouchEventFromRestore(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }

        postInvalidate();
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }

        postInvalidate();
        motionEventArrayList.add(MotionEvent.obtain(event));
        return true;
    }

    public void clear()
    {
        path = new Path();
        motionEventArrayList.clear();
        invalidate();
    }
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_STATE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(EXTRA_EVENT_LIST, motionEventArrayList);

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            ArrayList<MotionEvent> eventListLocal = new ArrayList<MotionEvent>();

            super.onRestoreInstanceState(bundle.getParcelable(EXTRA_STATE));
            motionEventArrayList = bundle.getParcelableArrayList(EXTRA_EVENT_LIST);
            eventListLocal = motionEventArrayList;
            if (motionEventArrayList == null) {
                motionEventArrayList = new ArrayList<MotionEvent>(100);
            }
            for (MotionEvent event : eventListLocal) {
                onTouchEventFromRestore(event);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    static class SavedState extends View.BaseSavedState {
        int stateToSave;
        Path pathToSave;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.stateToSave = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.stateToSave);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

}
