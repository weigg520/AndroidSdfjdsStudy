package com.weizhengzhou.androidsdfjdsstudy.view.interfaces;

import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;

import com.weizhengzhou.androidsdfjdsstudy.view.AppConstants;
import com.weizhengzhou.androidsdfjdsstudy.view.data.PaintModel;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.Dpoint;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.VertexGeomertyHelper;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.pentools.BlurPen;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.pentools.Pencil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



/**
 * Created by 75213 on 2018/1/18.
 */

public abstract class SkyworthGraphicsItem extends GraphicsItemBase {
    protected Path mStrokePath = null;
    protected Paint mStrokePaint = new Paint();
    protected List<Dpoint> mPoints;
    private boolean mErase = false; // 橡皮是否擦过

    public SkyworthGraphicsItem() {
        mStrokePaint.set(AppConstants.getInstance().defaultPaint);
        mPoints = new ArrayList<Dpoint>();
    }

    public SkyworthGraphicsItem(List<Dpoint> points) {
        mStrokePaint.set(AppConstants.getInstance().defaultPaint);
        mPoints = new ArrayList<Dpoint>(points);
    }

    public Paint getStrokePaint() {
        return mStrokePaint;
    }

    public void setStrokePaint(Paint paint) {
        mStrokePaint.set(paint);
    }

    public void setStrokeWidth(float strokewidth) {
        mStrokePaint.setStrokeWidth(strokewidth);

        if (mItemType == ItemType.DottedLine)
            setDashPath(strokewidth * 2);
    }

    public float getStrokeWidth() {
        return mStrokePaint.getStrokeWidth();
    }

    public void setDashPath(float w) {
        PathEffect effects = new DashPathEffect(new float[] { w, w, w, w }, 2);
        mStrokePaint.setPathEffect(effects);
    }

    public void setStrokeColor(int color) {
        mStrokePaint.setColor(color);
    }

    public int getStrokeColor() {
        return mStrokePaint.getColor();
    }

    public void setStrokeFillStyle(Paint.Style fillStyle) {
        mStrokePaint.setStyle(fillStyle);
    }

    public Paint.Style getStrokeFillStyle() {
        return mStrokePaint.getStyle();
    }

    public void setVertexPoints(List<Dpoint> points) {
        mPoints.addAll(points);
    }

    public Path getItemPath() {
        return mStrokePath;
    }

    public void creatGraphicsPath(List<Dpoint> points, ItemType drawingType) {
        mStrokePath = VertexGeomertyHelper.getVertexGeomerPath(points, drawingType);
        setItemBound(VertexGeomertyHelper.getGraphicsBound());
    }

    public boolean creatGraphicsPath(Rect rect, ItemType drawingType) {

        switch (drawingType) {
            case Line:
            case DottedLine:
            case Triangle:
            case Righttriangle:
            case Laddershape:
            case Parallelogram:
            case Oval:
            case Rectangle:
            case Square:
            case Circle:
                mStrokePath = VertexGeomertyHelper.getVertexGeomerPath(rect, drawingType);
                if (mStrokePath == null || mStrokePath.isEmpty())
                    return false;
                else {
                    setItemBound(VertexGeomertyHelper.getGraphicsBound());
                    return true;
                }

            default:
                break;
        }

        return false;
    }

    @Override
    public boolean collision(Rect rect) {
        switch (mItemType) {
            case Line:
            case DottedLine:
                List<Dpoint> matrixPoints = new ArrayList<Dpoint>();
                matrixPoints = getMatrixPoint();
                return checkLine(rect, matrixPoints.get(0), matrixPoints.get(1));

            case Triangle:
            case Righttriangle:
            case Laddershape:
            case Parallelogram:
            case Oval:
            case Rectangle:
            case Square:
            case Circle:
                return checkCloseGraphic(rect, getMatrixBoundRect(), getMatrixPath());

            case Pencil:
            case BlurPen:
            case TexturePen:
            case EmbossPen:
                return checkPen(rect, getMatrixPoint());

            default:
                break;
        }

        return false;
    }

    private boolean checkPen(Rect chooseRect, List<Dpoint> points) {
        if (chooseRect == null)
            return false;

        if (points.size() == 1)
            return false;

        Rect r = new Rect();
        float w1 = getStrokeWidth() * 0.5f + 0.5f;
        int w2 = (int) (AppConstants.DIS_COLLISION + w1);

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int y = points.get(i).y;
            if (i == 0 || i == (points.size() - 1)) {
                r.set(x - (int) w1, y - (int) w1, x + (int) w1, y + (int) w1);
                if (Rect.intersects(chooseRect, r)) {
                    return true;
                }
            } else {
                r.set(x - w2, y - w2, x + w2, y + w2);
                if (Rect.intersects(chooseRect, r)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkLine(Rect chooseRect, Point startPoint, Point endPoint) {
        if (chooseRect == null)
            return false;

        int h = startPoint.y - endPoint.y;
        int w = endPoint.x - startPoint.x;
        int result = startPoint.x * endPoint.y - endPoint.x * startPoint.y;
        int a = h * chooseRect.left + w * chooseRect.top + result;
        int b = h * chooseRect.right + w * chooseRect.bottom + result;
        int c = h * chooseRect.left + w * chooseRect.bottom + result;
        int d = h * chooseRect.right + w * chooseRect.top + result;
        if (((a > 0 || a == 0) && (b < 0 || b == 0))
                || ((a < 0 || a == 0) && (b > 0 || b == 0))
                || ((c > 0 || c == 0) && (d < 0 || d == 0)
                || ((c < 0 || c == 0) && (d > 0 || d == 0)))) {
            if (chooseRect.left > chooseRect.right) {
                int temp = chooseRect.left;
                chooseRect.left = chooseRect.right;
                chooseRect.right = temp;
            }
            if (chooseRect.top < chooseRect.bottom) {
                int temp = chooseRect.top;
                chooseRect.top = chooseRect.bottom;
                chooseRect.bottom = temp;
            }
            if ((startPoint.x < chooseRect.left && endPoint.x < chooseRect.left)
                    || (startPoint.x > chooseRect.right && endPoint.x > chooseRect.right)
                    || (startPoint.y > chooseRect.top && endPoint.y > chooseRect.top)
                    || (startPoint.y < chooseRect.bottom && endPoint.y < chooseRect.bottom)) {
                chooseRect.sort();
                return false;
            } else {
                chooseRect.sort();
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean checkCloseGraphic(Rect chooseRect, Rect boundRect, Path path) {
        if (chooseRect == null)
            return false;

        Region region = new Region();
        region.setPath(path, new Region(boundRect));
        RegionIterator iter = new RegionIterator(region);
        Rect r = new Rect();
        while (iter.next(r)) {
            if ( Rect.intersects(chooseRect, r)) {
                return true;
            }
        }
        return false;
    }

    private List<Dpoint> getMatrixPoint() {
        if (mMatrix.isIdentity())
            return mPoints;

        List<Dpoint> points = new ArrayList<Dpoint>();
        for (int i = 0; i < mPoints.size(); i++) {
            float pts[] = new float[] { mPoints.get(i).x, mPoints.get(i).y };
            mMatrix.mapPoints(pts);
            points.add(new Dpoint((int) pts[0], (int) pts[1]));
        }
        return points;
    }

    private Path getMatrixPath() {
        if (mMatrix.isIdentity())
            return mStrokePath;

        Path path = new Path();
        mStrokePath.transform(mMatrix, path);
        return path;
    }

    private Rect getMatrixBoundRect() {
        if (mMatrix.isIdentity())
            return mItemBound;

        RectF src = new RectF(mItemBound);
        Rect dst = new Rect();
        mMatrix.mapRect(src);
        src.round(dst);
        return dst;
    }

    /**
     * 拆分画笔
     * @param context
     * @param sence
     */
    public boolean breakupPen(Context context, GraphicsSenceCallback sence) {

        if (mErase) {
            List<Dpoint> piecewise = new ArrayList<Dpoint>();
            int count = 1;
            for (int i = 0; i < mPoints.size(); i++) {
                if (!mPoints.get(i).isDelete) {
                    piecewise.add(mPoints.get(i));
                }

                if (mPoints.get(i).isDelete && piecewise.size() > 0) {
                    // 当拆分的线最后一个点为补点时，为了能绘制出来，修改为FALSE
                    if (piecewise.get(piecewise.size() - 1).isFill == true)
                        piecewise.get(piecewise.size() - 1).isFill = false;

                    clonePen(context, sence, piecewise);
                    piecewise.clear();
                }

                if (count == mPoints.size() && piecewise.size() > 0) {
                    if (piecewise.get(piecewise.size() - 1).isFill == true)
                        piecewise.get(piecewise.size() - 1).isFill = false;

                    clonePen(context, sence, piecewise);
                    piecewise.clear();
                }

                count++;
            }

            // 撤销、重做保持原有状态
            for (int i = 0; i < mPoints.size(); i++) {
                if (mPoints.get(i).isDelete) {
                    mPoints.get(i).isDelete = false;
                }
            }
            mErase = false;
            return true;
        }

        return false;
    }

    private void clonePen(Context context, GraphicsSenceCallback sence,
                          List<Dpoint> points) {

        GraphicsItemBase pen = null;
        switch (mItemType) {
            case Pencil:
                pen = new Pencil(points);
                break;
            case EmbossPen:
                //pen = new EmbossPen(points);
                break;
            case BlurPen:
                //pen = new BlurPen(points);
                break;
            case TexturePen:
              /*  TexturePen tp = (TexturePen) this;
                if (tp.getDrawableId() == -1 && tp.getTexturePath() != null)
                    pen = new TexturePen(tp.getTexturePath(), points);
                if (tp.getDrawableId() != -1 && tp.getTexturePath() == null)
                    pen = new TexturePen(context.getResources(), tp.getDrawableId(), points);*/
                break;

            default:
                break;
        }

        if (pen != null) {
            pen.setItemId(UUID.randomUUID());
            ((SkyworthGraphicsItem) pen).setStrokePaint(getStrokePaint());
            ((SkyworthGraphicsItem) pen).creatGraphicsPath(points, mItemType);
            if (!getItemMatrix().isIdentity()) {
                pen.setItemMatrix(getItemMatrix());
            }
            sence.addBreakupItem(pen);
        }
    }

    /**
     * 表示画笔的点已擦除
     * @param rect
     */
    public void eraserPoint(Rect rect) {

        if (rect == null || rect.isEmpty())
            return;

        List<Dpoint> points = getMatrixPoint();
        for (int i = 0; i < points.size(); i++) {
            if (rect.contains(points.get(i).x, points.get(i).y)) {
                mPoints.get(i).isDelete = true;
                mErase = true;
            }
        }
    }

    public PaintModel toPaintModel() {
        PaintModel model = new PaintModel();
        model.setColor(this.getStrokeColor());
        model.setFillStyle(this.getStrokeFillStyle());
        model.setStrokeWidth(this.getStrokeWidth());
        return model;
    }

}
