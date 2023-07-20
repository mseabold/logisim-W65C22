package com.cbold.logi6522;

import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

interface PortInterface {
    Value getData(InstanceState state);
    Value getPortA(InstanceState state);
    Value getPortB(InstanceState state);
    Value getCA1(InstanceState state);
    Value getCA2(InstanceState state);
    Value getCB1(InstanceState state);
    Value getCB2(InstanceState state);

    int getRS(InstanceState state);
    boolean isSelected(InstanceState state);
    boolean getClockState(InstanceState state);
    boolean isRead(InstanceState state);
    boolean isReset(InstanceState state);

    void setData(InstanceState state, Value value);
    void setPortA(InstanceState state, Value value);
    void setPortB(InstanceState state, Value value);
    void setCA1(InstanceState state, Value value);
    void setCA2(InstanceState state, Value value);
    void setCB1(InstanceState state, Value value);
    void setCB2(InstanceState state, Value value);
    void setIRQ(InstanceState state, Value value);
}
