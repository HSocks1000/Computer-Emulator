package main;
//Executing Condition Code


public class ExeCond {
    private boolean condEQ;
    private boolean condNE;
    private boolean condCS;
    private boolean condCC;
    private boolean condMI;
    private boolean condPL;
    private boolean condVS;
    private boolean condVC;
    private boolean condHI;
    private boolean condLS;
    private boolean condGE;
    private boolean condLT;
    private boolean condGT;
    private boolean condLE;
    private boolean condAL;

    private static ExeCond execond = new ExeCond();

    public static ExeCond instance(){
        return execond;
    }



}
