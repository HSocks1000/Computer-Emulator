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
public abstract class MemWd {
    private MemType mt;
    private int address;
    
    public MemWd(int address, MemType mt){
        this.address = address;
        this.mt = mt;
    }
    
    public abstract String get();
    
    public abstract void set(String value);
    
    @Override
    public String toString(){
        return ("MemWd{" + "mt=" + mt + ", address=" + address + "}");
    }
}
