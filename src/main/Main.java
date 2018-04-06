import java.util.Arrays;

public class Main {

    /**
     * ARM Assembly Syntax - <operation>{cond}{flags} Rd,Rn,Operand2
     * <operation> A three-letter mnemonic, e.g. MOV or ADD.
     * {cond} An optional two-letter condition code, e.g. EQ or CS.
     * {flags} An optional additional flags. e.g. S.
     * Rd The destination register.
     * Rn The first source register.
     * Operand2 A flexible second operand.
     *
     * <operation> is not in the first column
     * The first column is used to place an label on the instruction
     * Instructions must not begin in the first column
     *     ADD r0, r1, r2 -> R0 = R1 + R2
     *     SUB r5, r3, #10 -> R5 = R3 − 10
     *     RSB r2, r5, #0xFF00 -> R2 = 0xFF00 − R5
     */

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Test a few calls to the ARMInst constructor
         * The ARMInst constructor parses one ARM assembly language instruction
         * The parser is not the best - see comments in ARMInst
         */
        String inst = "label     SUBEQS r5,    r3, #10 ";
        ARMInst ins = new ARMInst(inst);
        System.out.println(ins);

        System.out.println(ins.getOpCode() + " " + ins.getCondCode() + " " + ins.isSetConditionCodes() + "\n\n");
        System.out.println(ins);

        ins = new ARMInst("   ADD r4,r5,r6");
        System.out.println(ins);

        ins = new ARMInst("   MOV r4,r5");
        System.out.println(ins);

        ins = new ARMInst("  LDR r7, [r8]");
        System.out.println(ins);

        ins = new ARMInst("  LDR r7, [r8, #16]");
        System.out.println(ins);

        /**
         * The following simulates C = A + B
         * A is at location 0
         * B is at location 4
         * C is at location 8
         *
         * The program is loaded into memory addresses 100 through 112
         */
        Memory mainMemory = new Memory();
        CPU cpu = new CPU();
        mainMemory.setMemory(0, MemType.DATA, "10");
        mainMemory.setMemory(4, MemType.DATA, "15");

        mainMemory.setMemory(100, MemType.INST, "   LDR r5, [r10]");
        mainMemory.setMemory(104, MemType.INST, "   LDR r6, [r10, #4]");
        mainMemory.setMemory(108, MemType.INST, "   ADD r7,r5,r6");
        mainMemory.setMemory(112, MemType.INST, "   SDR r7, [r10, #8]");

        cpu.setRegister(CPU.PC, 100); // point pc to program
        cpu.step(mainMemory);
        System.out.println("r5: " + cpu.getRegister(5));
        cpu.step(mainMemory);
        System.out.println("r6: " + cpu.getRegister(6));
        cpu.step(mainMemory);
        System.out.println("r7: " + cpu.getRegister(7));
        cpu.step(mainMemory);
        System.out.println(mainMemory.getMemory(8));

    }
}
