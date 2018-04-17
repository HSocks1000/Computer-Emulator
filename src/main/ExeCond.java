package main;
//Executing Condition Code


import static main.CondCode.EQ;

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


    public boolean getCondStatus(CondCode aCondCode) {
        StatFlags statFlags = StatFlags.instance();
        boolean zFlag = statFlags.getzeroFlag();
        boolean nFlag = statFlags.getnegFlag();
        boolean cFlag = statFlags.getcarryFlag();
        boolean oFlag = statFlags.getoverflowFlag();
        switch (aCondCode){
            case EQ:
                return zFlag;
                //break;
            case NE:
                return (!zFlag);
                //break;
            case CS:
                return cFlag;
                //break;
            case CC:
                return (!cFlag);
                //break;
            case MI:
                return nFlag;
                //break;
            case PL:
                return (!nFlag);
                //break;
            case VS:
                return oFlag;
                //break;
            case VC:
                return (!oFlag);
                //break;
            case HI:
                if(cFlag && (!zFlag)){
                    return true;
                }
                else{
                    return false;
                }
                //break;
            case LS:
                if((!cFlag) || zFlag){
                    return true;
                }
                else{
                    return false;
                }
                //break;
            case GE:
                if(nFlag == oFlag){
                    return true;
                }
                else {
                    return false;
                }
                //break;
            case LT:
                if(nFlag != oFlag){
                    return true;
                }
                else {
                    return false;
                }
                //break;
            case GT:
                if((!zFlag) && (nFlag == oFlag)){
                    return true;
                }
                else {
                    return false;
                }
                //break;
            case LE:
                if((zFlag) && (nFlag != oFlag)){
                    return true;
                }
                else {
                    return false;
                }
                //break;
            case AL:
                return true;
                //break;
        }
        return false;
    }
}
