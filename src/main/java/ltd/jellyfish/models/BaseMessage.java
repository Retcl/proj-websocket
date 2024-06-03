package ltd.jellyfish.models;

import com.alibaba.fastjson.JSON;

public class BaseMessage {

    private PositionMessage positionMessage;

    private DeviceMessage deviceMessage;

    public PositionMessage getPositionMessage() {
        return positionMessage;
    }

    public void setPositionMessage(PositionMessage positionMessage) {
        this.positionMessage = positionMessage;
    }

    

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public byte[] toBytes(){
        return JSON.toJSONBytes(this);
    }

    public DeviceMessage getDeviceMessage() {
        return deviceMessage;
    }

    public void setDeviceMessage(DeviceMessage deviceMessage) {
        this.deviceMessage = deviceMessage;
    }

    public BaseMessage toObject(String json) {
        return (BaseMessage) JSON.parse(json);
    }

}
