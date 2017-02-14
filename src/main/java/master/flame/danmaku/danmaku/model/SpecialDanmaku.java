package master.flame.danmaku.danmaku.model;

public class SpecialDanmaku extends BaseDanmaku {
    public long alphaDuration;
    public int beginAlpha;
    public float beginX;
    public float beginY;
    private float[] currStateValues = new float[4];
    public int deltaAlpha;
    public float deltaX;
    public float deltaY;
    public int endAlpha;
    public float endX;
    public float endY;
    public LinePath[] linePaths;
    public float pivotX;
    public float pivotY;
    public float rotateX;
    public float rotateZ;
    public long translationDuration;
    public long translationStartDelay;

    public class LinePath {
        public long beginTime;
        float delatX;
        float deltaY;
        public long duration;
        public long endTime;
        Point pBegin;
        Point pEnd;

        public void setPoints(Point pBegin, Point pEnd) {
            this.pBegin = pBegin;
            this.pEnd = pEnd;
            this.delatX = pEnd.x - pBegin.x;
            this.deltaY = pEnd.y - pBegin.y;
        }

        public float getDistance() {
            return this.pEnd.getDistance(this.pBegin);
        }

        public float[] getBeginPoint() {
            return new float[]{this.pBegin.x, this.pBegin.y};
        }

        public float[] getEndPoint() {
            return new float[]{this.pEnd.x, this.pEnd.y};
        }
    }

    private class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getDistance(Point p) {
            float _x = Math.abs(this.x - p.x);
            float _y = Math.abs(this.y - p.y);
            return (float) Math.sqrt((double) ((_x * _x) + (_y * _y)));
        }
    }

    public void layout(IDisplayer displayer, float x, float y) {
        getRectAtTime(displayer, this.mTimer.currMillisecond);
    }

    public float[] getRectAtTime(IDisplayer displayer, long currTime) {
        if (!isMeasured()) {
            return null;
        }
        long deltaTime = currTime - this.time;
        if (this.alphaDuration > 0 && this.deltaAlpha != 0) {
            if (deltaTime >= this.alphaDuration) {
                this.alpha = this.endAlpha;
            } else {
                this.alpha = this.beginAlpha + ((int) (((float) this.deltaAlpha) * (((float) deltaTime) / ((float) this.alphaDuration))));
            }
        }
        float currX = this.beginX;
        float currY = this.beginY;
        long dtime = deltaTime - this.translationStartDelay;
        if (this.translationDuration > 0 && dtime >= 0 && dtime <= this.translationDuration) {
            float tranalationProgress = ((float) dtime) / ((float) this.translationDuration);
            if (this.linePaths != null) {
                LinePath currentLinePath = null;
                for (LinePath line : this.linePaths) {
                    if (dtime >= line.beginTime && dtime < line.endTime) {
                        currentLinePath = line;
                        break;
                    }
                    currX = line.pEnd.x;
                    currY = line.pEnd.y;
                }
                if (currentLinePath != null) {
                    float deltaX = currentLinePath.delatX;
                    float deltaY = currentLinePath.deltaY;
                    tranalationProgress = ((float) (deltaTime - currentLinePath.beginTime)) / ((float) currentLinePath.duration);
                    float beginX = currentLinePath.pBegin.x;
                    float beginY = currentLinePath.pBegin.y;
                    if (deltaX != 0.0f) {
                        currX = beginX + (deltaX * tranalationProgress);
                    }
                    if (deltaY != 0.0f) {
                        currY = beginY + (deltaY * tranalationProgress);
                    }
                }
            } else {
                if (this.deltaX != 0.0f) {
                    currX = this.beginX + (this.deltaX * tranalationProgress);
                }
                if (this.deltaY != 0.0f) {
                    currY = this.beginY + (this.deltaY * tranalationProgress);
                }
            }
        } else if (dtime > this.translationDuration) {
            currX = this.endX;
            currY = this.endY;
        }
        this.currStateValues[0] = currX;
        this.currStateValues[1] = currY;
        this.currStateValues[2] = this.paintWidth + currX;
        this.currStateValues[3] = this.paintHeight + currY;
        setVisibility(!isOutside());
        return this.currStateValues;
    }

    public float getLeft() {
        return this.currStateValues[0];
    }

    public float getTop() {
        return this.currStateValues[1];
    }

    public float getRight() {
        return this.currStateValues[2];
    }

    public float getBottom() {
        return this.currStateValues[3];
    }

    public int getType() {
        return 7;
    }

    public void setTranslationData(float beginX, float beginY, float endX, float endY, long translationDuration, long translationStartDelay) {
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
        this.deltaX = endX - beginX;
        this.deltaY = endY - beginY;
        this.translationDuration = translationDuration;
        this.translationStartDelay = translationStartDelay;
    }

    public void setAlphaData(int beginAlpha, int endAlpha, long alphaDuration) {
        this.beginAlpha = beginAlpha;
        this.endAlpha = endAlpha;
        this.deltaAlpha = endAlpha - beginAlpha;
        this.alphaDuration = alphaDuration;
        if (this.deltaAlpha != 0 && beginAlpha != AlphaValue.MAX) {
            this.alpha = beginAlpha;
        }
    }

    public void setLinePathData(float[][] points) {
        int i = 0;
        if (points != null) {
            int length = points.length;
            this.beginX = points[0][0];
            this.beginY = points[0][1];
            this.endX = points[length - 1][0];
            this.endY = points[length - 1][1];
            if (points.length > 1) {
                LinePath line;
                this.linePaths = new LinePath[(points.length - 1)];
                for (int i2 = 0; i2 < this.linePaths.length; i2++) {
                    this.linePaths[i2] = new LinePath();
                    this.linePaths[i2].setPoints(new Point(points[i2][0], points[i2][1]), new Point(points[i2 + 1][0], points[i2 + 1][1]));
                }
                float totalDistance = 0.0f;
                for (LinePath line2 : this.linePaths) {
                    totalDistance += line2.getDistance();
                }
                LinePath lastLine = null;
                LinePath[] linePathArr = this.linePaths;
                int length2 = linePathArr.length;
                while (i < length2) {
                    line2 = linePathArr[i];
                    line2.duration = (long) ((line2.getDistance() / totalDistance) * ((float) this.translationDuration));
                    line2.beginTime = lastLine == null ? 0 : lastLine.endTime;
                    line2.endTime = line2.beginTime + line2.duration;
                    lastLine = line2;
                    i++;
                }
            }
        }
    }

    public void updateData(float scale) {
    }
}
