package main;

import java.util.Hashtable;

public class CPU {

    public final static int PC = 15;
    //private int[] registers = new int[16];
    private final Hashtable<Integer, Integer> registers = new Hashtable<>();
    private static CPU cpu = new CPU();
    //Memory mem = new Memory();
    
    public static CPU instance(){
        return cpu;
    }

    public void setRegister(int register, int value){

        registers.put(register, value);
    }

    public void step(CpuMemory m){
        
        ARMInst inst = ((MemWdInst)m.getMemory(registers.get(PC))).getInst();
        //StatFlags instat = StatFlags.instance();


        registers.put(PC, registers.get(PC) + 4); // advance the Process Counter to next instruction
        System.out.println(inst);
        int rd = 0, rn = 0, rm = 0, immediate = 0, cpsr = 0;
        switch(inst.getOpCode()) {
            case LDR:
                rd = inst.getRd();
                rn = inst.getRn();
                immediate = inst.getLdrStrImmediate();
                registers.put(rd, ((MemWdData)m.getMemory(registers.get(rn) + immediate)).getData());
                //registers[rd] = ((MemWdData)m.getMemory(registers[rn] +
                  //  immediate)).getData();
                break;

            case ADD:
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers.put(rd, registers.get(rn) + inst.getImmedVal());
                }
                else {
                    registers.put(rd, registers.get(rn) + registers.get(rm));
                }
                break;

            case SUB: //subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers.put(rd, registers.get(rn) - inst.getImmedVal());
                }
                else {
                    registers.put(rd, registers.get(rn) - registers.get(rm));
                }
                break;

            case RSB: //reverse subtract
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                // TODO: Double check this is how immediate is implemented
                if(inst.isImmed()) {
                    registers.put(rd, inst.getImmedVal() - registers.get(rn));
                }
                else {
                    registers.put(rd, registers.get(rm) - registers.get(rn));
                }
                break;

            case SDR: //STR?
                rd = inst.getRd();
                rn = inst.getRn();
                immediate = inst.getLdrStrImmediate();
                m.setMemory(registers.get(rn) + immediate, MemType.DATA, ""
                        + registers.get(rd));
                break;
            case AND:
                rd= inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers.put(rd, registers.get(rn) & inst.getImmedVal());
                }
                else {
                    registers.put(rd, registers.get(rn) & registers.get(rm));
                }
                break;

            case BIC: //Bit Clear
                rd= inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                //registers[rd] = registers[rn] !& registers[rm];
                if(inst.isImmed()) {
                    registers.put(rd, ~(registers.get(rn) & inst.getImmedVal()));
                }
                else {
                    registers.put(rd, ~(registers.get(rn) & registers.get(rm)));
                }
                break;
            case EOR: //Exclusive Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers.put(rd, registers.get(rn) ^ inst.getImmedVal());
                }
                else {
                    registers.put(rd, registers.get(rn) ^ registers.get(rm));
                }
                break;
            case ORR: //bitwise Or
                rd = inst.getRd();
                rn = inst.getRn();
                rm = inst.getRm();
                if(inst.isImmed()) {
                    registers.put(rd, registers.get(rn) | inst.getImmedVal());
                }
                else {
                    registers.put(rd, registers.get(rn) | registers.get(rm));
                }
                //registers[rd] = registers[rn] | registers[rm];
                break;


             //cpsr based opcode.

            case CMN: //compare negative
                //TODO:  Sanity Check this later, add on other flags

                rn = inst.getRn();
                rm = inst.getRm();
                int resultCMN;
                if(inst.isImmed()){
                    resultCMN = registers.get(rn) + inst.getImmedVal();
                }
                else {
                    resultCMN = registers.get(rn) + registers.get(rm);
                }
                if (resultCMN < 0) {
                    StatFlags.instance().setnegFlag(true);
                }
                else {
                    StatFlags.instance().setnegFlag(false);
                }
                if (resultCMN == 0) {
                    StatFlags.instance().setzeroFlag(true);
                }
                else {
                    StatFlags.instance().setzeroFlag(false);
                }
                break;

            case CMP: //compare 
                //TODO:  Sanity Check this later, add on other flags

                rn = inst.getRn();
                rd = inst.getRd();  
              //rm = inst.getRm();
                int resultCMP = 0;
                if(inst.isImmed()){
                    resultCMN = registers.get(rd) - inst.getImmedVal();
                }
                else {
                    resultCMN = registers.get(rd) - registers.get(rn);
                }
                if(inst.isImmed()){
                    if(registers.get(rd) > inst.getImmedVal()){
                        StatFlags.instance().setoverflowFlag(true);
                    }
                    else{
                        StatFlags.instance().setoverflowFlag(false);
                    }    
                }
                else{
                    if(registers.get(rd) > registers.get(rn)){
                        StatFlags.instance().setoverflowFlag(true);
                    }
                    else{
                        StatFlags.instance().setoverflowFlag(false);
                    }
                    
                }
                if (resultCMP < 0) {
                    StatFlags.instance().setnegFlag(true);
                }
                else {
                    StatFlags.instance().setnegFlag(false);
                }
                if (resultCMP == 0) {
                    StatFlags.instance().setzeroFlag(true);
                }
                else {
                    StatFlags.instance().setzeroFlag(false);
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
