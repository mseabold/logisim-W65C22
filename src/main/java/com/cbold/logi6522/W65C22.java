package com.cbold.logi6522;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;


@SuppressWarnings("unused")
class W65C22 extends InstanceFactory {
    private static final int RS_PORTB = 0x0;
    private static final int RS_PORTA = 0x1;
    private static final int RS_DDRB  = 0x2;
    private static final int RS_DDRA  = 0x3;
    private static final int RS_T1CL  = 0x4;
    private static final int RS_T1CH  = 0x5;
    private static final int RS_T1LL  = 0x6;
    private static final int RS_T1LH  = 0x7;
    private static final int RS_T2CL  = 0x8;
    private static final int RS_T2CH  = 0x9;
    private static final int RS_SR    = 0xA;
    private static final int RS_ACR   = 0xB;
    private static final int RS_PCR   = 0xC;
    private static final int RS_IFR   = 0xD;
    private static final int RS_IER   = 0xE;
    private static final int RS_ANOHS = 0xF;

    private static final int PCR_CA1_CTRL_MASK = 0x01;
    private static final int PCR_CA2_CTRL_MASK = 0x0e;
    private static final int PCR_CB1_CTRL_MASK = 0x10;
    private static final int PCR_CB2_CTRL_MASK = 0xe0;

    private static final int PCR_CA2_CTRL_SHIFT = 1;
    private static final int PCR_CB1_CTRL_SHIFT = 4;
    private static final int PCR_CB2_CTRL_SHIFT = 5;

    private static final int PCR_CA1_CTRL_NEG  = 0x00;
    private static final int PCR_CA1_CTRL_POS  = 0x01;

    private static final int PCR_CA2_CTRL_INT_NEG_EDGE     = 0x0 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_INT_NEG_EDGE_IND = 0x1 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_INT_POS_EDGE     = 0x2 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_INT_POS_EDGE_IND = 0x3 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_HANDSHAKE_OUT    = 0x4 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_PULSE_OUT        = 0x5 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_LOW_OUT          = 0x6 << PCR_CA2_CTRL_SHIFT;
    private static final int PCR_CA2_CTRL_HIGH_OUT         = 0x7 << PCR_CA2_CTRL_SHIFT;

    private static final int PCR_CB1_CTRL_NEG = 0x0 << PCR_CB1_CTRL_SHIFT;
    private static final int PCR_CB1_CTRL_POS = 0x1 << PCR_CB1_CTRL_SHIFT;

    private static final int PCR_CB2_CTRL_INT_NEG_EDGE     = 0x0 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_INT_NEG_EDGE_IND = 0x1 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_INT_POS_EDGE     = 0x2 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_INT_POS_EDGE_IND = 0x3 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_HANDSHAKE_OUT    = 0x4 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_PULSE_OUT        = 0x5 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_LOW_OUT          = 0x6 << PCR_CB2_CTRL_SHIFT;
    private static final int PCR_CB2_CTRL_HIGH_OUT         = 0x7 << PCR_CB2_CTRL_SHIFT;

    private static final int ACR_LATCH_CTRL_MASK     = 0x03;
    private static final int ACR_SHIFT_REG_CTRL_MASK = 0x1c;
    private static final int ACR_T2_CTRL_MASK        = 0x20;
    private static final int ACR_T1_CTRL_MASK        = 0xc0;
    private static final int ACR_T1_TYPE_MASK        = 0x40;
    private static final int ACR_T1_PB7_CRTL_MASK    = 0x80;

    private static final int ACR_SHIFT_REG_CTRL_SHIFT = 2;
    private static final int ACR_T2_CTRL_SHIFT        = 5;
    private static final int ACR_T1_CTRL_SHIFT        = 6;
    private static final int ACR_T1_PB7_CTRL_SHIFT    = 7;

    private static final int ACR_SHIFT_REG_DISABLE  = 0x0 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_IN_T2    = 0x1 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_IN_PHI2  = 0x2 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_IN_EXT   = 0x3 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_OUT_FREE = 0x4 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_OUT_T2   = 0x5 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_OUT_PHI2 = 0x6 << ACR_SHIFT_REG_CTRL_SHIFT;
    private static final int ACR_SHIFT_REG_OUT_EXT  = 0x7 << ACR_SHIFT_REG_CTRL_SHIFT;

    private static final int ACR_T2_CTRL_TIMED_INT = 0x0 << ACR_T2_CTRL_SHIFT;
    private static final int ACR_T2_CTRL_DOWN_PB6  = 0x1 << ACR_T2_CTRL_SHIFT;

    private static final int ACR_T1_CTRL_TIMED_INT = 0x0 << ACR_T1_CTRL_SHIFT;
    private static final int ACR_T1_CTRL_CONT_INT  = 0x1 << ACR_T1_CTRL_SHIFT;

    private static final int ACR_T1_PB7_DISABLED   = 0x0 << ACR_T1_PB7_CTRL_SHIFT;
    private static final int ACR_T1_PB7_ENABLED    = 0x1 << ACR_T1_PB7_CTRL_SHIFT;

    private static final int IFLAG_CA2 = 0x01;
    private static final int IFLAG_CA1 = 0x02;
    private static final int IFLAG_SR = 0x04;
    private static final int IFLAG_CB2 = 0x80;
    private static final int IFLAG_CB1 = 0x10;
    private static final int IFLAG_T2 = 0x20;
    private static final int IFLAG_T1 = 0x40;

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

    private static enum TimerState {
        IDLE,
        ONE_SHOT,
        FREE_ONE,
        PULSE_COUNT
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

    private Value porta;
    private Value portb;

    private Value ca1;
    private Value ca2;
    private Value cb1;
    private Value cb2;

    private int ddra;
    private int ddrb;

    private int pcr;
    private int acr;

    private int t1l;
    private int t1c;
    private int t2l;
    private int t2c;

    private int ier;
    private int ifr;

    private boolean clkState = false;

    private boolean t1active;
    private boolean t2active;

    W65C22() {
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

        BitWidth bits8 = BitWidth.create(8);
        porta = Value.createKnown(bits8, 0);
        portb = Value.createKnown(bits8, 0);
        ddra = 0;
        ddrb = 0;

        ca1 = Value.TRUE;
        ca2 = Value.TRUE;
        cb1 = Value.TRUE;
        cb2 = Value.TRUE;

        t1active = false;
        t2active = false;
    }

    private void processRegWrite(int port, Value data) {
        int dataInt = data.toIntValue();

        switch(port) {
            case RS_PORTB:
                portb = Value.create(data.getAll());
                break;
            case RS_PORTA:
                porta = Value.create(data.getAll());
                break;
            case RS_DDRB:
                ddrb = dataInt;
                break;
            case RS_DDRA:
                ddra = dataInt;
                break;
            case RS_T1CL:
            case RS_T1LL:
                // $04 and $06 writes are identical ops
                t1l = (t1l & 0xff00) | (dataInt & 0xff);
                break;
            case RS_T1CH:
                // Upper 8 bits of the latch is loaded
                t1l = (t1l & 0xff00) | ((dataInt & 0xff) << 8);

                // RS $05 triggers the Counter to be loaded
                t1c = t1l;

                // IFR6 is reset
                ifr &= ~IFLAG_T1;

                t1active = true;
                break;
            case RS_T1LH:
                // Upper 8 bits of the latch is loaded
                t1l = (t1l & 0xff00) | ((dataInt & 0xff) << 8);

                // IFR6 is reset
                ifr &= ~IFLAG_T1;

                break;
            case RS_T2CL:
                // Lower 8 bits are loaded into latch
                t2l = (dataInt & 0xFF);
                break;
            case RS_T2CH:
                // Write upper 8 bits to counter and load lower 8 from latches
                t2c = ((dataInt & 0xFF) << 8) | (t2l & 0xFF);

                // IF5 is reset
                ifr &= ~IFLAG_T2;
                // Arm the timer
                t2active = true;
                break;
            case RS_PCR:
                pcr = dataInt;
                break;
            case RS_ACR:
                acr = dataInt;
                break;
            case RS_IER:
                // Bit 7 determines whether other bits or set or cleared
                if((dataInt & 0x80) == 0) {
                    // Clear operation
                    ier &= ~(dataInt & 0x7F);
                } else {
                    // Set operation
                    ier |= (dataInt & 0x7F);
                }
                break;
            case RS_IFR:
                // Writes to IFR clear any bits 0-6 which are 1 in the write
                ifr &= ~(dataInt & 0x7F);
                break;
        }
    }

    /**
     * Advances the state of active timers
     *
     * @param clkEdge Specifies whether the trigger to process is from a positive clock edge (true)
     *                or a pulse on PB6.
     */
    private void processTimers(boolean clkEdge) {

    }

    private void driveDataBus(InstanceState state) {
        Value v;
        Value a_in = state.getPort(PORT_PA);
        Value b_in = state.getPort(PORT_PB);
        Value rs = state.getPort(PORT_RS);


        switch(rs.toIntValue()) {
            case RS_PORTB:
                v = mergeValues(portb, b_in, ddrb);
                break;
            case RS_PORTA:
                //PORTA always reads back pin state regardless of DDRA
                v = a_in;
                break;
            case RS_DDRB:
                v = Value.createKnown(BitWidth.create(8), ddrb);
                break;
            case RS_DDRA:
                v = Value.createKnown(BitWidth.create(8), ddra);
                break;
            case RS_T1CL:
                v = Value.createKnown(BitWidth.create(8), (t1c & 0xFF));

                // IFR6 is cleared
                ifr &= ~IFLAG_T1;
                break;
            case RS_T1CH:
                v = Value.createKnown(BitWidth.create(8), ((t1c >> 8) & 0xFF));
                break;
            case RS_T1LL:
                v = Value.createKnown(BitWidth.create(8), (t1l & 0xFF));
                break;
            case RS_T1LH:
                v = Value.createKnown(BitWidth.create(8), ((t1c >> 8) & 0xFF));
                break;
            case RS_T2CL:
                v = Value.createKnown(BitWidth.create(8), (t2c & 0xFF));

                // IF5 is cleared
                ifr &= ~IFLAG_T2;
                break;
            case RS_T2CH:
                v = Value.createKnown(BitWidth.create(8), ((t2c >> 8) & 0xFF));
                break;
            case RS_PCR:
                v = Value.createKnown(BitWidth.create(8), pcr);
                break;
            case RS_ACR:
                v = Value.createKnown(BitWidth.create(8), acr);
                break;
            case RS_IER:
                // Bit 7 is always 1
                v = Value.createKnown(BitWidth.create(8), (ier & 0x7f) | 0x80);
                break;
            case RS_IFR:
                // Bit 7 is set if any other bits are set
                int ifr_out = ifr & 0x7f;

                if(ifr_out != 0)
                    ifr_out |= 0x80;

                v = Value.createKnown(BitWidth.create(8), ifr_out);
                break;
            default:
                v = Value.createKnown(BitWidth.create(8), 0);
                break;
        }

        state.setPort(PORT_D, v, 1);
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

        Value rs = state.getPort(PORT_RS);
        Value phi2 = state.getPort(PORT_PHI2);
        Value rwb = state.getPort(PORT_RWB);
        Value cs1 = state.getPort(PORT_CS1);
        Value cs2b = state.getPort(PORT_CS2B);

        boolean newClk = phi2 == Value.TRUE;

        boolean selected = cs1 == Value.TRUE && cs2b == Value.FALSE;

        if(newClk != clkState) {
            if(!newClk) {
                //Falling PHI2 edge
                if(selected && rwb == Value.FALSE) {
                    //Write pulse selected
                    System.out.println("write detected");
                    Value data = state.getPort(PORT_D);

                    processRegWrite(rs.toIntValue(), data);
                }
            }

            System.out.println("Edge detect");
            clkState = newClk;
        }

        if(clkState && rwb == Value.TRUE && selected) {
        } else {
            state.setPort(PORT_D, Value.createUnknown(BitWidth.create(8)), 1);
        }

        Value unknown = Value.createUnknown(BitWidth.create(8));
        state.setPort(PORT_PA, mergeValues(porta, unknown, ddra), 1);
        state.setPort(PORT_PB, mergeValues(portb, unknown, ddrb), 1);
        state.setPort(PORT_CA1, ca1, 1);
        state.setPort(PORT_CA2, ca2, 1);
        state.setPort(PORT_CB1, cb1, 1);
        state.setPort(PORT_CB2, cb2, 1);

        boolean irq = (ifr & ier) != 0;

        state.setPort(PORT_IRQB, irq ? Value.FALSE : Value.TRUE, 1);
    }

    private Value mergeValues(Value a, Value b, int a_mask) {
        if(a.getWidth() != b.getWidth()) {
            throw new IllegalArgumentException("Bit width must match");
        }

        Value merged[] = new Value[8];

        for(int i=0; i<a.getWidth(); i++) {
            if((a_mask & (1 << i)) == 0)
                merged[i] = b.get(i);
            else
                merged[i] = a.get(i);
        }

        return Value.create(merged);
    }
}
