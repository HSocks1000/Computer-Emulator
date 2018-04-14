
package main;

import java.util.Arrays;

public class StatFlags{
    private boolean negFlag;
    private boolean zeroFlag;
    private boolean overflowFlag;
    private boolean carryFlag;
    private static StatFlags statflags = new StatFlags();

    public static StatFlags instance(){
         return statflags;
    }

    public boolean getnegFlag(){
      return negFlag;
    }

    public boolean getzeroFlag(){
      return zeroFlag;
    }

    public boolean getoverflowFlag(){
      return overflowFlag;
    }
    public boolean getcarryFlag(){
      return carryFlag;
    }

    public void setFlags(boolean negFlag, boolean zeroFlag, boolean overflowFlag, boolean carryFlag){
      this.negFlag = negFlag;
      this.zeroFlag = zeroFlag;
      this.overflowFlag = overflowFlag;
      this.carryFlag = carryFlag;
    }

    public void setnegFlag(boolean negFlag){
        this.negFlag = negFlag;
    }
    public void setzeroFlag(boolean zeroFlag){
        this.zeroFlag = zeroFlag;
    }
    public void setoverflowFlag(boolean overflowFlag){
        this.overflowFlag = overflowFlag;
    }
    public void setcarryFlag(boolean carryFlag){
        this.carryFlag = carryFlag;
    }

}