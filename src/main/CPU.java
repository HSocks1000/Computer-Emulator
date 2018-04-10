package main;

public class CPU {

    public final static int PC = 15;
    private int[] registers = new int[16];
    private static CPU cpu = new CPU();
    //Memory mem = new Memory();
    
    public static CPU instance(){
        return cpu;
    }

    public void setRegister(int register, int value){

        registers[register] = value;
    }

    public void step(CpuMemory m){
        ARMInst inst = ((MemWdInst)m.getMemory(registers[PC])).getInst();
        StatFlags instat = StatFlags.instance();
        registers[PC] += 4; // advance the Process Counter to next instruction
        System.out.println(inst);
        int rd = 0, rn = 0, rm = 0, immediate = 0, cpsr = 0;
        switch(inst.getOpCode()) {
            case LDR:
                rd = inst.getRd();
                rn = inst.getRn();
                immediate = inst.getLdrStrImmediate();
                registers[rd] = ((MemWdData)m.getMemory(registers[rn] +
                    immediate)).getData();
                break;

            case ADD:
                // todo - update so ADD processes second operand correctly,
                // including shifts and rotates

                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rn] + registers[rm];
                break;
            case SUB: //subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rn] - registers[rm];
                break;

            case RSB: //reverse subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rm] - registers[rn];
                break;

            case SDR: //STR?
                rd = inst.getRd();
                rn = inst.getRn();
                immediate = inst.getLdrStrImmediate();
                m.setMemory(registers[rn] + immediate, MemType.DATA, ""
                        + registers[rd]);
                break;
            case AND:
                rd= inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rn] & registers[rm];
                break;

            case BIC: //Bit Clear
                rd= inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                //registers[rd] = registers[rn] !& registers[rm];
                registers[rd] = ~(registers[rn] & registers[rm]);
                break;
            case EOR: //Exclusive Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rn] ^ registers[rm];
                break;
            case ORR: //bitwise Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                registers[rd] = registers[rn] | registers[rm];
                break;


             //cpsr based opcode.

            case CMN: //compare negative
                //TODO:  Sanity Check this later.

                rn = inst.getRn();
                rm = inst.getRm();
                int subtraction = registers[rn] - registers[rm];
        }
    }
    public int getRegister(int register)
    {
        return registers[register];
    }

}
