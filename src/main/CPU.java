package main;

public class CPU {

    public final static int PC = 15;
    private int[] registers = new int[16];

    public void setRegister(int register, int value){
        registers[register] = value;
    }

    public int getRegister(int register){
        return registers[register];
    }

}
