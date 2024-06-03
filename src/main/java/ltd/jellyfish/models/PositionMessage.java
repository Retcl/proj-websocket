package ltd.jellyfish.models;


import com.alibaba.fastjson.JSON;

public class PositionMessage {

    private long x;

    private long y;

    private long z;

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public byte[] toByteBuffer(){
        return JSON.toJSONBytes(this);
    }
}
