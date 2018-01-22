package com.weizhengzhou.androidsdfjdsstudy.view.utils;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.ItemType;

/**
 * Created by 75213 on 2018/1/18.
 */

public class VertexGeomertyHelper {
    private static List<Dpoint> mPoints = new ArrayList<Dpoint>();
    private static Rect mBound = new Rect();

    public static Path getVertexGeomerPath(List<Dpoint> points, ItemType itemType) {

        Path path = null;
        mBound.setEmpty();

        switch (itemType) {
            case Triangle:
            case Righttriangle:
            case Laddershape:
            case Parallelogram:
                path = getGraphicsClosePath(points);
                break;

            case Line:
            case DottedLine:
                path = getGraphicsUnclosePath(points);
                break;

            case Circle: {
                int x1 = points.get(0).x;
                int y1 = points.get(0).y;
                int x2 = points.get(1).x;
                int y2 = points.get(1).y;
                path = getCirclePath(x1, y1, x2, y2);
            }
            break;

            case Square: {
                int x1 = points.get(0).x;
                int y1 = points.get(0).y;
                int x2 = points.get(1).x;
                int y2 = points.get(1).y;
                path = getSquarePath(x1, y1, x2, y2);
            }
            break;

            case Rectangle: {
                int x1 = points.get(0).x;
                int y1 = points.get(0).y;
                int x2 = points.get(1).x;
                int y2 = points.get(1).y;
                path = getRectPath(x1, y1, x2, y2);
            }
            break;

            case Oval: {
                int x1 = points.get(0).x;
                int y1 = points.get(0).y;
                int x2 = points.get(1).x;
                int y2 = points.get(1).y;
                path = getOvalPath(x1, y1, x2, y2);
            }
            break;

            case Pencil:
            case BlurPen:
            case TexturePen:
            case EmbossPen:
                path = getPenPath(points);
                break;

            default:
                break;
        }

        if (null != path && !path.isEmpty()) {
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            rectF.round(mBound);
        }

        return path;
    }

    public static Path getVertexGeomerPath(Rect rect, ItemType itemType) {

        Path path = null;
        mPoints.clear();

        switch (itemType) {
            case Circle:
                path = getCirclePath(rect);
                break;

            case Square:
                path = getSquarePath(rect);
                break;

            case Rectangle:
                path = getRectPath(rect);
                break;

            case Oval:
                path = getOvalPath(rect);
                break;

            case Triangle:
                rect.sort();
                mPoints.add(new Dpoint((rect.left + rect.right) / 2, rect.top));
                mPoints.add(new Dpoint(rect.right, rect.bottom));
                mPoints.add(new Dpoint(rect.left, rect.bottom));
                path = getGraphicsClosePath(mPoints);
                break;

            case Righttriangle:
                if (rect.left >= rect.right && rect.top >= rect.bottom) // left-top
                {
                    mPoints.add(new Dpoint(rect.right, rect.bottom));
                    mPoints.add(new Dpoint(rect.left, rect.bottom));
                    mPoints.add(new Dpoint(rect.left, rect.top));
                }
                else if (rect.left <= rect.right && rect.top >= rect.bottom) // right-top
                {
                    mPoints.add(new Dpoint(rect.left, rect.bottom));
                    mPoints.add(new Dpoint(rect.right, rect.bottom));
                    mPoints.add(new Dpoint(rect.left, rect.top));
                }
                else if (rect.left <= rect.right && rect.top <= rect.bottom) // right-bottom
                {
                    mPoints.add(new Dpoint(rect.left, rect.top));
                    mPoints.add(new Dpoint(rect.right, rect.bottom));
                    mPoints.add(new Dpoint(rect.left, rect.bottom));
                }
                else if (rect.left >= rect.right && rect.top <= rect.bottom) // left-bottom
                {
                    mPoints.add(new Dpoint(rect.left, rect.top));
                    mPoints.add(new Dpoint(rect.left, rect.bottom));
                    mPoints.add(new Dpoint(rect.right, rect.bottom));
                }
                path = getGraphicsClosePath(mPoints);
                break;

            case Laddershape:
                rect.sort();
                int l = (int) (rect.width() * 0.13f);
                mPoints.add(new Dpoint(rect.left + l, rect.top));
                mPoints.add(new Dpoint(rect.right - l, rect.top));
                mPoints.add(new Dpoint(rect.right, rect.bottom));
                mPoints.add(new Dpoint(rect.left, rect.bottom));
                path = getGraphicsClosePath(mPoints);
                break;

            case Parallelogram:
                Rect tempRect = new Rect();
                tempRect.set(rect);
                tempRect.sort();
                int p = (int) (tempRect.width() * 0.15f);
                if (rect.left >= rect.right && rect.top >= rect.bottom) // left-top
                {
                    mPoints.add(new Dpoint(tempRect.left, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right - p, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right, tempRect.bottom));
                    mPoints.add(new Dpoint(tempRect.left + p, tempRect.bottom));
                }
                else if (rect.left <= rect.right && rect.top >= rect.bottom) // right-top
                {
                    mPoints.add(new Dpoint(tempRect.left + p, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right - p, tempRect.bottom));
                    mPoints.add(new Dpoint(tempRect.left, tempRect.bottom));
                }
                else if (rect.left <= rect.right && rect.top <= rect.bottom) // right-bottom
                {
                    mPoints.add(new Dpoint(tempRect.left, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right - p, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right, tempRect.bottom));
                    mPoints.add(new Dpoint(tempRect.left + p, tempRect.bottom));
                }
                else if (rect.left >= rect.right && rect.top <= rect.bottom) // left-bottom
                {
                    mPoints.add(new Dpoint(tempRect.left + p, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right, tempRect.top));
                    mPoints.add(new Dpoint(tempRect.right - p, tempRect.bottom));
                    mPoints.add(new Dpoint(tempRect.left, tempRect.bottom));
                }
                path = getGraphicsClosePath(mPoints);
                break;

            case Line:
            case DottedLine:
                mPoints.add(new Dpoint(rect.left, rect.top));
                mPoints.add(new Dpoint(rect.right, rect.bottom));
                path = getGraphicsUnclosePath(mPoints);
                break;

            default:
                break;
        }
        if (null != path && !path.isEmpty()) {
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            rectF.round(mBound);
        }

        return path;
    }

    private static Path getGraphicsClosePath(List<Dpoint> points) {

        if (points.size() <= 1)
            return null;

        Path path = new Path();
        int i = 0;
        for (Dpoint point : points) {
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }

            i++;
        }

        path.close();
        return path;
    }

    private static Path getGraphicsUnclosePath(List<Dpoint> points) {

        if (points.size() <= 1)
            return null;

        Path path = new Path();
        int i = 0;
        for (Dpoint point : points) {
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }

            i++;
        }

        return path;
    }

    private static Path getCirclePath(int x1, int y1, int x2, int y2) {

        Rect rect = new Rect();
        rect.set(x1, y1, x2, y2);
        return getCirclePath(rect);
    }

    private static Path getCirclePath(Rect rect) {

        Path path = new Path();
        int w = Math.abs(rect.width());
        int h = Math.abs(rect.height());
        Rect tempRect = new Rect();

        if (rect.left >= rect.right && rect.top >= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right + (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom + (h - w));

            tempRect.sort();
            path.addCircle(tempRect.centerX(), tempRect.centerY(), tempRect.width() / 2, Path.Direction.CW);
        }
        else if (rect.left <= rect.right && rect.top >= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right - (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom + (h - w));

            tempRect.sort();
            path.addCircle(tempRect.centerX(), tempRect.centerY(), tempRect.width() / 2, Path.Direction.CW);
        }
        else if (rect.left <= rect.right && rect.top <= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right - (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom - (h - w));

            tempRect.sort();
            path.addCircle(tempRect.centerX(), tempRect.centerY(), tempRect.width() / 2, Path.Direction.CW);
        }
        else if (rect.left >= rect.right && rect.top <= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right + (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom - (h - w));

            tempRect.sort();
            path.addCircle(tempRect.centerX(), tempRect.centerY(), tempRect.width() / 2, Path.Direction.CW);
        }

        mPoints.add(new Dpoint(tempRect.left, tempRect.top));
        mPoints.add(new Dpoint(tempRect.right, tempRect.bottom));

        return path;
    }

    // 正方形
    private static Path getSquarePath(int x1, int y1, int x2, int y2) {

        Rect rect = new Rect();
        rect.set(x1, y1, x2, y2);
        return getSquarePath(rect);
    }

    private static Path getSquarePath(Rect rect) {

        Path path = new Path();
        int w = Math.abs(rect.width());
        int h = Math.abs(rect.height());
        RectF tempRect = new RectF();

        if (rect.left >= rect.right && rect.top >= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right + (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom + (h - w));

            tempRect.sort();
            path.addRect(tempRect, Path.Direction.CW);
        }
        else if (rect.left <= rect.right && rect.top >= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right - (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom + (h - w));

            tempRect.sort();
            path.addRect(tempRect, Path.Direction.CW);
        }
        else if (rect.left <= rect.right && rect.top <= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right - (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom - (h - w));

            tempRect.sort();
            path.addRect(tempRect, Path.Direction.CW);
        }
        else if (rect.left >= rect.right && rect.top <= rect.bottom) {

            if (w > h)
                tempRect.set(rect.left, rect.top, rect.right + (w - h), rect.bottom);
            else
                tempRect.set(rect.left, rect.top, rect.right, rect.bottom - (h - w));

            tempRect.sort();
            path.addRect(tempRect, Path.Direction.CW);
        }

        mPoints.add(new Dpoint((int) tempRect.left, (int) tempRect.top));
        mPoints.add(new Dpoint((int) tempRect.right, (int) tempRect.bottom));

        return path;
    }

    // 矩形
    private static Path getRectPath(int x1, int y1, int x2, int y2) {

        Rect rect = new Rect();
        rect.set(x1, y1, x2, y2);
        return getRectPath(rect);
    }

    private static Path getRectPath(Rect rect) {

        rect.sort();
        Path path = new Path();
        RectF rectF = new RectF();
        rectF.set(rect);
        path.addRect(rectF, Path.Direction.CW);

        mPoints.add(new Dpoint(rect.left, rect.top));
        mPoints.add(new Dpoint(rect.right, rect.bottom));

        return path;
    }

    // 椭圆
    private static Path getOvalPath(int x1, int y1, int x2, int y2) {

        Rect rect = new Rect();
        rect.set(x1, y1, x2, y2);
        return getOvalPath(rect);
    }

    private static Path getOvalPath(Rect rect) {

        rect.sort();
        Path path = new Path();
        RectF rectF = new RectF();
        rectF.set(rect);
        path.addOval(rectF, Path.Direction.CW);

        mPoints.add(new Dpoint(rect.left, rect.top));
        mPoints.add(new Dpoint(rect.right, rect.bottom));

        return path;
    }

    private static Path getPenPath(List<Dpoint> points) {

        if (points.size() < 1)
            return null;

        Path path = new Path();
        int x1 = 0;
        int y1 = 0;
        int i = 0;
        for (Dpoint point : points) {
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                if (point.isFill) {
                    i++;
                    continue;
                }
                path.quadTo(x1, y1, (int) ((x1 + point.x) / 2), (int) ((y1 + point.y) / 2));
            }

            x1 = point.x;
            y1 = point.y;
            i++;
        }

        return path;
    }

    public static List<Dpoint> getPoints() {
        return mPoints;
    }

    public static Rect getGraphicsBound() {
        return mBound;
    }
}
