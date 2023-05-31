package com.cbold.logi6522;

import java.util.Arrays;
import java.util.List;

import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;

public class Components extends Library {
    private List<AddTool> tools;

    public Components() {
        tools = Arrays.asList(new AddTool[] {
            new AddTool(new W65C22()),
        });
    }

    public String getDisplayName() {
        return "W65C22";
    }

    public List<AddTool> getTools() {
        return tools;
    }
}
