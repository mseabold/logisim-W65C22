package com.cbold.logi6522;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

class CoreTests {
    private W65C22 core;

    private class TestData implements PortInterface {
        int rs;
        boolean selected;
        boolean read;
        boolean clk;

        Value inD;
        Value inA;
        Value inB;
        Value inCA1;
        Value inCA2;
        Value inCB1;
        Value inCB2;

        Value outD;
        Value outA;
        Value outB;
        Value outCA1;
        Value outCA2;
        Value outCB1;
        Value outCB2;
        Value outIRQ;

        public TestData() {
            rs = 0;
            selected = false;
            read = false;
            clk = false;
        }

        @Override
        public Value getData(InstanceState state) {
            return inD;
        }

        @Override
        public Value getPortA(InstanceState state) {
            return inA;
        }

        @Override
        public Value getPortB(InstanceState state) {
            return inB;
        }

        @Override
        public Value getCA1(InstanceState state) {
            return inCA1;
        }

        @Override
        public Value getCA2(InstanceState state) {
            return inCA2;
        }

        @Override
        public Value getCB1(InstanceState state) {
            return inCB1;
        }

        @Override
        public Value getCB2(InstanceState state) {
            return inCB2;
        }


        @Override
        public int getRS(InstanceState state) {
            return rs;
        }

        @Override
        public boolean isSelected(InstanceState state) {
            return selected;
        }

        @Override
        public boolean getClockState(InstanceState state) {
            return clk;
        }

        @Override
        public boolean isRead(InstanceState state) {
            return read;
        }

        @Override
        public boolean isReset(InstanceState state) {
            return false;
        }

        @Override
        public void setData(InstanceState state, Value value) {
            outD = value;
        }

        @Override
        public void setPortA(InstanceState state, Value value) {
            outA = value;
        }

        @Override
        public void setPortB(InstanceState state, Value value) {
            outB = value;
        }

        @Override
        public void setCA1(InstanceState state, Value value) {
            outCA1 = value;
        }

        @Override
        public void setCA2(InstanceState state, Value value) {
            outCA2 = value;
        }

        @Override
        public void setCB1(InstanceState state, Value value) {
            outCB1 = value;
        }

        @Override
        public void setCB2(InstanceState state, Value value) {
            outCB2 = value;
        }

        @Override
        public void setIRQ(InstanceState state, Value value) {
            outIRQ = value;
        }
    }

    private TestData testData;

    CoreTests() {
        core =  new W65C22();
        testData = new TestData();
    }

    private void halfTick() {
        testData.clk = !testData.clk;

        core.update(testData, null);
    }

    private void tick() {
        halfTick();
        halfTick();
    }

    private void writeRegister(int reg, int value) {
        halfTick();

        testData.inD = Value.createKnown(BitWidth.create(8), value);
        testData.rs = reg;
        testData.selected = true;
        testData.read = false;

        halfTick();

        testData.selected = false;
    }

    private int readRegister(int reg) {
        testData.rs = reg;
        testData.selected = true;
        testData.read = true;

        halfTick();

        int result = testData.outD.toIntValue();

        halfTick();

        testData.selected = false;

        return result;
    }

    @Test
    @DisplayName("DDR Write/Read")
    void testDDRWriteRead() {
        writeRegister(2, 0xAA);

        assertEquals(0xAA, readRegister(2));
    }

    @Test
    @DisplayName("PORTA Input")
    void testPortAIn() {
        // All bits input
        writeRegister(3, 0);

        // Create an input value
        testData.inA = Value.createKnown(BitWidth.create(8), 0xAA);

        // Read input
        assertEquals(0xAA, readRegister(1));

        // Flip Input bits
        testData.inA = Value.createKnown(BitWidth.create(8), 0x55);

        // Read Again
        assertEquals(0x55, readRegister(1));
    }

    @Test
    @DisplayName("PORTA Output")
    void testPortAOut() {
        // All bits output
        writeRegister(3, 0xff);

        // Write output value
        writeRegister(1, 0xff);

        // Make sure all bits are driven
        assertTrue(testData.outA.isFullyDefined());

        // Make sure value matches
        assertEquals(0xff, testData.outA.toIntValue());

        // Turn off some outut bits
        writeRegister(3, 0xaa);

        Value[] partialOut = new Value[8];
        partialOut[0] = Value.UNKNOWN;
        partialOut[1] = Value.TRUE;
        partialOut[2] = Value.UNKNOWN;
        partialOut[3] = Value.TRUE;
        partialOut[4] = Value.UNKNOWN;
        partialOut[5] = Value.TRUE;
        partialOut[6] = Value.UNKNOWN;
        partialOut[7] = Value.TRUE;

        Value expected = Value.create(partialOut);

        // Make sure output matches with both unknowns and driven values
        assertTrue(expected.equals(testData.outA));
    }

    @Test
    @DisplayName("T1 OneShot")
    void testT1OneShot() {
        // Make sure ACR and PCR are in a known state for one-shot
        writeRegister(0xB, 0);
        writeRegister(0xC, 0);

        // Enable T1 Interrupt
        writeRegister(0xE, 0xC0);

        // T1CL = 5
        writeRegister(4, 5);

        // State timer (T1CH = 0)
        writeRegister(5, 0);

        // Timer takes N+1.5 Cycles to expire
        for(int i = 0; i < 6; ++i) {
            tick();

            assertEquals(testData.outIRQ, Value.TRUE);
        }

        halfTick();

        // Timer should now have expired
        assertEquals(testData.outIRQ, Value.FALSE);

        halfTick();

        // IFR 6 should be set
        assertEquals(0x40, readRegister(0xD) & 0x60);
    }

    @Test
    @DisplayName("T1 Continuous")
    void testT1Continuous() {
        // Make sure ACR and PCR are in a known state for cont.
        writeRegister(0xB, 0x40);
        writeRegister(0xC, 0);

        // Enable T1 Interrupt
        writeRegister(0xE, 0xC0);

        // T1CL = 5
        writeRegister(4, 5);

        // State timer (T1CH = 0)
        writeRegister(5, 0);

        // Timer takes N+1.5 Cycles to expire
        for(int i = 0; i < 6; ++i) {
            tick();

            assertEquals(testData.outIRQ, Value.TRUE);
        }

        halfTick();

        // Timer should now have expired
        assertEquals(testData.outIRQ, Value.FALSE);

        halfTick();

        // IFR 6 should be set
        assertEquals(0x40, readRegister(0xD) & 0x60);

        // Clear the IFR
        writeRegister(0xD, 0x40);

        // IRQ should be de-asserted
        assertEquals(testData.outIRQ, Value.TRUE);


        // Timer takes N+1.5 Cycles to expire, but we've killed two cycles on Read/Write
        for(int i = 0; i < 4; ++i) {
            tick();

            assertEquals(testData.outIRQ, Value.TRUE);
        }

        halfTick();

        // Timer should now have expired
        assertEquals(testData.outIRQ, Value.FALSE);
    }

    @Test
    @DisplayName("T1 OneShot PB7")
    void testT1OneShotPB7() {
        // Start by making PB7 an input
        writeRegister(W65C22.RS_DDRB, 0x00);

        // Set PB7 control to T1
        writeRegister(W65C22.RS_ACR, W65C22.ACR_T1_PB7_ENABLED);

        // Check PB7 is now driven high
        Value pb7 = testData.outB.get(7);

        assertEquals(pb7, Value.TRUE);

        // Perpare and and start the timer
        writeRegister(W65C22.RS_PCR, 0);
        writeRegister(W65C22.RS_T1CL, 5);
        writeRegister(W65C22.RS_T1CH, 0);

        // N + 2 cycles to expire the timer
        for(int i = 0; i < 7; ++i) {
            // PB7 should stay low
            pb7 = testData.outB.get(7);
            assertEquals(pb7, Value.FALSE);

            tick();
        }

        // Timer should have expired and PB7 should be high
        pb7 = testData.outB.get(7);
        assertEquals(pb7, Value.TRUE);

        // Make sure that changing DDRB and driving DATAB doesn't affect PB7
        writeRegister(W65C22.RS_PORTB, 0x00);
        writeRegister(W65C22.RS_DDRB, 0x80);

        pb7 = testData.outB.get(7);
        assertEquals(pb7, Value.TRUE);

        // Turn off PB7 T1 control
        writeRegister(W65C22.RS_ACR, 0);

        // PB7 should be driven by PORTB register now
        pb7 = testData.outB.get(7);
        assertEquals(pb7, Value.FALSE);
    }

    @Test
    @DisplayName("T1 Continuous PB7")
    void testT1ContinuousPB7() {
        writeRegister(W65C22.RS_PCR, 0);

        // Setup ACR for FreeRun + PB7
        writeRegister(W65C22.RS_ACR, W65C22.ACR_T1_CTRL_CONT_INT | W65C22.ACR_T1_PB7_ENABLED);

        // PB7 should be high
        Value pb7 = testData.outB.get(7);
        assertEquals(pb7, Value.TRUE);

        // Start T1
        writeRegister(W65C22.RS_T1CL, 5);
        writeRegister(W65C22.RS_T1CH, 0);

        // Expire once
        for(int i = 0; i < 7; ++i) {
            // PB7 should be low
            pb7 = testData.outB.get(7);
            assertEquals(pb7, Value.FALSE);

            tick();
        }

        // Expire again
        for(int i = 0; i < 7; ++i) {
            // PB7 should be high now
            pb7 = testData.outB.get(7);
            assertEquals(pb7, Value.TRUE);

            tick();
        }

        // PB7 should have toggled again
        pb7 = testData.outB.get(7);
        assertEquals(pb7, Value.FALSE);
    }
}
