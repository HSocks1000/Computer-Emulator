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
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = registers[rn] + inst.getImmedVal();
                }
                else {
                    registers[rd] = registers[rn] + registers[rm];
                }
                break;

            case SUB: //subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = registers[rn] - inst.getImmedVal();
                }
                else {
                    registers[rd] = registers[rn] - registers[rm];
                }
                break;

            case RSB: //reverse subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                // TODO: Double check this is how immediate is implemented
                if(inst.isImmed()) {
                    registers[rd] = inst.getImmedVal() - registers[rn];
                }
                else {
                    registers[rd] = registers[rm] - registers[rn];
                }
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
                if(inst.isImmed()) {
                    registers[rd] = registers[rn] & inst.getImmedVal();
                }
                else {
                    registers[rd] = registers[rm] & registers[rn];
                }
                break;

            case BIC: //Bit Clear
                rd= inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                //registers[rd] = registers[rn] !& registers[rm];
                if(inst.isImmed()) {
                    registers[rd] = ~(registers[rn] & inst.getImmedVal());
                }
                else {
                    registers[rd] = ~(registers[rn] & registers[rm]);
                }
                break;
            case EOR: //Exclusive Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = registers[rn] ^ inst.getImmedVal();
                }
                else {
                    registers[rd] = registers[rn] ^ registers[rm];
                }
                break;
            case ORR: //bitwise Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = registers[rn] | inst.getImmedVal();
                }
                else {
                    registers[rd] = registers[rn] | registers[rm];
                }
                registers[rd] = registers[rn] | registers[rm];
                break;


             //cpsr based opcode.

            case CMN: //compare negative
                //TODO:  Sanity Check this later, add on other flags

                rn = inst.getRn();
                rm = inst.getRm();
                int resultCMN;
                if(inst.isImmed()){
                    resultCMN = registers[rn] + inst.getImmedVal();
                }
                else {
                    resultCMN = registers[rn] + registers[rm];
                }
                if (resultCMN < 0) {
                    instat.setnegFlag(true);
                }
                else {
                    instat.setnegFlag(false);
                }
                if (resultCMN == 0) {
                    instat.setzeroFlag(true);
                }
                else {
                    instat.setnegFlag(false);
                }
                long testLong = registers[rn] + registers[rm];
                if (resultCMN != testLong){
                    instat.setcarryFlag(true);
                }
                else {
                    instat.setcarryFlag(false);
                }
                if ((registers[rn] > 0) && (registers[rm] > 0)) {
                    if (resultCMN < 0) {
                        instat.setoverflowFlag(true);
                    }
                    else {
                        instat.setoverflowFlag(false);
                    }
                }
                else if ((registers[rn] < 0) && (registers[rm] < 0)) {
                    if (resultCMN > 0) {
                        instat.setoverflowFlag(true);
                    }
                    else {
                        instat.setoverflowFlag(false);
                    }
                }
                break;

            case CMP: //compare negative
                //TODO:  Sanity Check this later, add on other flags

                rn = inst.getRn();
                rm = inst.getRm();
                int resultCMP;
                if(inst.isImmed()){
                    resultCMP = registers[rn] - inst.getImmedVal();
                }
                else {
                    resultCMP = registers[rn] - registers[rm];
                }
                if (resultCMP < 0) {
                    instat.setnegFlag(true);
                }
                else {
                    instat.setnegFlag(false);
                }
                if (resultCMP == 0) {
                    instat.setzeroFlag(true);
                }
                else {
                    instat.setnegFlag(false);
                }
                break;

            case MOV:

                rd = inst.getRd();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = registers[rm];
                }
                else {
                    registers[rd] = inst.getImmedVal();
                }
                break;

            case MVN:

                rd = inst.getRd();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers[rd] = ~(registers[rm]);
                }
                else {
                    registers[rd] = ~(inst.getImmedVal());
                }
                break;
                //if(resultCMN )
        }
    }
    public int getRegister(int register)
    {
        return registers[register];
    }

}
