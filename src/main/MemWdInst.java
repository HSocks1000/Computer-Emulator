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
public class MemWdInst extends MemWd{

    private ARMInst inst;
    
    public MemWdInst(int address, String contents) {
        super(address, MemType.INST);
        this.set(contents);
    }

    @Override
    public String get() {
        return inst.getInst();
    }

    @Override
    public void set(String s) {
        inst = new ARMInst(s);
    }
    
    public ARMInst getInst(){
        return inst;
    }
    
}
