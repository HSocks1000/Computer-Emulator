import java.util.HashMap;
import java.util.Map;

public class Memory {
    /**
     * memory is simulator's main memory
     */
    private MemWd[] memory = new MemWd[1024];

    /**
     * labels maps an assembly language label to a MemWd
     */
    private Map<String,MemWd> labels = new HashMap<>();


    public Memory() {
        for (int i = 0; i < memory.length; i++)
            memory[i] = new MemWdData(i*4, "0");
    }

    /**
     * Sets contents of a memory address
     * @param address
     * @param contents
     */
    public void setMemory(int address, MemType mt, String contents) {
        if (mt == MemType.DATA)
            memory[address] = new MemWdData(address, contents);
        else
            memory[address] = new MemWdInst(address, contents);
    }

    public MemWd getMemory(int address) {
        return memory[address];
    }

}