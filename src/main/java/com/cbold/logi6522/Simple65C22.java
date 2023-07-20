package com.cbold.logi6522;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

public class Simple65C22 extends InstanceFactory implements PortInterface {
    private static final class PortLabel {
        public Direction dir;
        public String label;

        public PortLabel(String label, Direction dir) {
            this.label = label;
            this.dir = dir;
        }
    }

    private static final PortLabel labels[] = {
        new PortLabel("RS", Direction.EAST),
        new PortLabel("D", Direction.EAST),
        new PortLabel("PHI2", Direction.EAST),
        new PortLabel("RWB", Direction.EAST),
        new PortLabel("CS1", Direction.EAST),
        new PortLabel("CS2B", Direction.EAST),
        new PortLabel("IRQB", Direction.EAST),

        new PortLabel("PA", Direction.WEST),
        new PortLabel("PB", Direction.WEST),
        new PortLabel("CA1", Direction.WEST),
        new PortLabel("CA2", Direction.WEST),
        new PortLabel("CB1", Direction.WEST),
        new PortLabel("CB2", Direction.WEST),
    };

    private static final int PORT_RS   = 0;
    private static final int PORT_D    = 1;
    private static final int PORT_PHI2 = 2;
    private static final int PORT_RWB  = 3;
    private static final int PORT_CS1  = 4;
    private static final int PORT_CS2B = 5;
    private static final int PORT_IRQB = 6;
    private static final int PORT_PA   = 7;
    private static final int PORT_PB   = 8;
    private static final int PORT_CA1  = 9;
    private static final int PORT_CA2  = 10;
    private static final int PORT_CB1  = 11;
    private static final int PORT_CB2  = 12;

    private W65C22 core;

    public Simple65C22() {
        super("W65C22 VIA");

        setOffsetBounds(Bounds.create(-80, -80, 160, 160));
        setPorts(new Port[] {
            new Port(-80, -60, Port.INPUT,  4), //RS
            new Port(-80, -40, Port.INOUT,  8), //D
            new Port(-80, -20, Port.INPUT,  1), //PHI2
            new Port(-80, 0,   Port.INPUT,  1), //RWB
            new Port(-80, 20,  Port.INPUT,  1), //CS1
            new Port(-80, 40,  Port.INPUT,  1), //CS2B
            new Port(-80, 60,  Port.OUTPUT, 1), //IRQB

            new Port(80, -50,   Port.INOUT, 8), //PA
            new Port(80, -30,   Port.INOUT, 8), //PB
            new Port(80, -10,   Port.INOUT, 1), //CA1
            new Port(80, 10,   Port.INOUT, 1), //CA2
            new Port(80, 30,   Port.INOUT, 1), //CB1
            new Port(80, 50,   Port.INOUT, 1), //CB2

        });

        core = new W65C22();
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        painter.drawRectangle(painter.getBounds(), "");

        for(int i=0; i<labels.length; i++)
        {
            painter.drawPort(i, labels[i].label, labels[i].dir);
        }

    }

    @Override
    public void propagate(InstanceState state) {
        core.update(this, state);
    }

    @Override
    public Value getData(InstanceState state) {
        return state.getPort(PORT_D);
    }

    @Override
    public Value getPortA(InstanceState state) {
        return state.getPort(PORT_PA);
    }

    @Override
    public Value getPortB(InstanceState state) {
        return state.getPort(PORT_PB);
    }

    @Override
    public Value getCA1(InstanceState state) {
        return state.getPort(PORT_CA1);
    }

    @Override
    public Value getCA2(InstanceState state) {
        return state.getPort(PORT_CA2);
    }

    @Override
    public Value getCB1(InstanceState state) {
        return state.getPort(PORT_CB1);
    }

    @Override
    public Value getCB2(InstanceState state) {
        return state.getPort(PORT_CB2);
    }

    @Override
    public int getRS(InstanceState state) {
        return state.getPort(PORT_RS).toIntValue();
    }

    @Override
    public boolean isSelected(InstanceState state) {
        Value CS1 = state.getPort(PORT_CS1);
        Value CS2B = state.getPort(PORT_CS2B);

        return CS1 == Value.TRUE && CS2B == Value.FALSE;
    }

    @Override
    public boolean getClockState(InstanceState state) {
        return state.getPort(PORT_PHI2) == Value.TRUE;
    }

    @Override
    public boolean isRead(InstanceState state) {
        return state.getPort(PORT_RWB) == Value.TRUE;
    }

    @Override
    public boolean isReset(InstanceState state) {
        //TODO
        return false;
    }

    @Override
    public void setData(InstanceState state, Value value) {
        state.setPort(PORT_D, value, 1);
    }

    @Override
    public void setPortA(InstanceState state, Value value) {
        state.setPort(PORT_PA, value, 1);
    }

    @Override
    public void setPortB(InstanceState state, Value value) {
        state.setPort(PORT_PB, value, 1);
    }

    @Override
    public void setCA1(InstanceState state, Value value) {
        state.setPort(PORT_CA1, value, 1);
    }

    @Override
    public void setCA2(InstanceState state, Value value) {
        state.setPort(PORT_CA2, value, 1);
    }

    @Override
    public void setCB1(InstanceState state, Value value) {
        state.setPort(PORT_CB1, value, 1);
    }

    @Override
    public void setCB2(InstanceState state, Value value) {
        state.setPort(PORT_CB2, value, 1);
    }

    @Override
    public void setIRQ(InstanceState state, Value value) {
        state.setPort(PORT_IRQB, value, 1);
    }

}
