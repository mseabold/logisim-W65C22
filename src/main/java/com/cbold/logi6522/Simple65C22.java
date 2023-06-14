package com.cbold.logi6522;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

public class Simple65C22 extends InstanceFactory {
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
        VIAState coreState = new VIAState();

        coreState.data = state.getPort(PORT_D);
        coreState.portA = state.getPort(PORT_PA);
        coreState.portB = state.getPort(PORT_PB);
        coreState.CA1 = state.getPort(PORT_CA1);
        coreState.CB1 = state.getPort(PORT_CB1);
        coreState.CA1 = state.getPort(PORT_CA1);
        coreState.CB2 = state.getPort(PORT_CB2);

        coreState.rs = state.getPort(PORT_RS).toIntValue();

        coreState.selected = (state.getPort(PORT_CS1) == Value.TRUE && state.getPort(PORT_CS2B) == Value.FALSE);
        coreState.read = state.getPort(PORT_RWB) == Value.TRUE;
        coreState.clkState = state.getPort(PORT_PHI2) == Value.TRUE;

        core.update(coreState);

        state.setPort(PORT_D, coreState.data, 1);
        state.setPort(PORT_PA, coreState.portA, 1);
        state.setPort(PORT_PB, coreState.portB, 1);
        state.setPort(PORT_CA1, coreState.CA1, 1);
        state.setPort(PORT_CB1, coreState.CB1, 1);
        state.setPort(PORT_CA1, coreState.CA1, 1);
        state.setPort(PORT_CB2, coreState.CB2, 1);

        state.setPort(PORT_IRQB, coreState.irq ? Value.FALSE : Value.TRUE, 1);
    }
}
