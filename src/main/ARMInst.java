package main;

import java.util.Arrays;

public class ARMInst {
    private String label; //Instructions label
    private OpCode opcode;
    private CondCode condcode;
    private boolean isBranch;

    //setConditionCodes == true: updates instructions in CPSR

    private boolean setConditionCodes;
    private int rd; //destination register
    private int rn; //operand register
    private int rm; //second operand register
    private int rmShift; //shift value for second operand register
    private int ldrStrImmediate;

    //immediate == false: rm is part of the instruction
    //immediate == true: immediate value and immediate rotate are part of instruction
    private boolean immediate;
    private int immediateValue;
    private int immediateRotate;

    public String inst; //String of instructions to be decoded

    public OpCode getOpCode(){
        return opcode;
    }
    
    public CondCode getCondCode(){
        return condcode;
    }
    
    public boolean isSetConditionCodes(){
        return setConditionCodes;
    }
    
    public String getInst(){
        return inst;
    }
    
    @Override
    public String toString() {
        return "ARMInst{" +
                "\n\tlabel=" + label +
                "\n\topcode=" +
                "\n\tcondCode=" +
                "\n\tsetConditionCodes=" + setConditionCodes +
                "\n\trd=" + rd +
                "\n\trn=" + rn +
                "\n\trm=" + rm +
                "\n\trmShift=" + rmShift +
                "\n\tldrStrImmediate" + ldrStrImmediate +
                "\n\timmediate=" + immediate +
                "\n\timmediateValue=" + immediateValue +
                "\n\timmediateRotate=" + immediateRotate +
                "\n\tinst=" + inst +
                "\n}";
    }

    public ARMInst(String inst){
        this.inst = inst;
        rd = -1;
        rn = -1;
        rm = -1;
        rmShift = 0;
        immediate = false;
        immediateValue = 0;
        immediateRotate = 0;
        ldrStrImmediate = 0;
        label = "";

        String[] instParts;
        if(inst.charAt(0) != '\u0000' && inst.charAt(0) != '\t') {//if inst has a label
            inst = inst.replaceFirst("","^");
            instParts = inst.split("\\^"); //[0] has label, [1] has rest of instructions
            label = instParts[0];
            inst = instParts[1].trim(); //removes leading and trailing white space
        }
        else{
            inst = inst.trim();
        }

        String regExp = "\\s*(\\s|,)\\s*";
        instParts = inst.split(regExp);
        System.out.println(label + " " + Arrays.toString(instParts));

        String code = instParts[0];
        // Add in support to decode branch
        if(code.equals("B")) {
            isBranch = true;
            condcode = null;
            label = instParts[1];
        }
        // Check if opcode is BIC, and that there is no second operand
        else if(!(code.equals("BIC")) && (instParts.length == 2)) {
            isBranch = true;
            condcode = condcode.valueOf(code.substring(1,2));
            label = instParts[1];
        }


        if(code.length() == 6 || code.length() == 4){ //has something like SUBEQS or SUBS
            setConditionCodes = true;
            code = code.substring(0, code.length() - 1);
        }
        if(code.length() == 5){
            String cond = code.substring(3, 5);
            condcode = CondCode.valueOf(cond);
            code = code.substring(0, 3);
        }
        System.out.println("ARMInst: " + code);
        opcode = opcode.valueOf(code);

        if(opcode == opcode.LDR || opcode == OpCode.SDR){
            System.out.println("instParts: " + Arrays.toString(instParts) + instParts.length);
            String operand1 = instParts[1].toLowerCase();
            rd = Integer.parseInt(operand1.substring(1, operand1.length()));
            String operand2 = instParts[2].toLowerCase().replaceFirst("\\[","\u0000");
            rn = Integer.parseInt(operand2.substring(1, operand2.length()));
            if(instParts.length > 3){
                String immediate = instParts[3].toLowerCase().replaceFirst("\\]","\u0000");
                ldrStrImmediate = Integer.parseInt(immediate.substring(1, immediate.length()));
            }
            return;
        }

        //process operands
        for(int i = 1; i < instParts.length; i++){
            String operand = instParts[i].toLowerCase();
            char operand1stChar = operand.charAt(0);
            int operandNum = 0;
            if(operand.length() > 2 && operand.substring(1, 3).equals("0x")){
                operandNum = Integer.parseInt(operand.substring(3, operand.length()), 16);
            }
            else{
                operandNum = Integer.parseInt(operand.substring(1, operand.length()));
            }
            if(i == 1 && operand1stChar == 'r'){
                rd = operandNum;
            }
            if(i == 1 && operand1stChar == 'r'){
                rn = operandNum;
            }
            if(i == 3 && operand1stChar == 'r'){
                rm = operandNum;
            }
            if(i == 2 && operand1stChar == '#'){
                immediate = true;
                immediateValue = operandNum;
                //process rotate on MOV immediate
            }
            if(i == 3 && operand1stChar == '#'){
                immediate = true;
                immediateValue = operandNum;
                //process shifts on (for example) ADD immediate
            }
        }

        //throew illegalArgumentException("my illeagl arg exception")
    }

    public int getRd() {
        return rd;
    }

    public int getRn() {
        return rn;
    }

    public int getLdrStrImmediate() {
        return ldrStrImmediate;
    }

    public int getRm() {
        return rm;
    }

    public boolean isImmed() { return immediate; }

    public int getImmedVal() { return immediateValue; }
}
