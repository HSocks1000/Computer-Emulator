/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Owner
 */
public class MemWdData extends MemWd{
    
    private int value;

    public MemWdData(int address, String value) {
        super(address, MemType.DATA);
        this.value = Integer.parseInt(value);
    }
    
    @Override
    public String get() {
        return ""+value;
    }
    
    public int getData(){
        return value;
    }
    
    @Override
    public void set(String value){
        this.value = Integer.parseInt(value);
    }

    public void set(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return (super.toString() + " MemWdData{" + "value=" + value + '}');
    }
    
}
