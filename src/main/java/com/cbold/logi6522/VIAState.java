package com.cbold.logi6522;

import com.cburch.logisim.data.Value;

class VIAState {

    Value data;
    Value portA;
    Value portB;

    Value CA1;
    Value CA2;
    Value CB1;
    Value CB2;

    int rs;

    boolean selected;
    boolean read;
    boolean clkState;

    boolean irq;

    public VIAState() { }
}
