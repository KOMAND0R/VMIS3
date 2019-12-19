package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final int N = 5;
    private static final ArrayList<Integer> array = new ArrayList<Integer>() {{
        add(1);
        add(4);
        add(9);
        add(2);
        add(3);
    }};

    private int x = -1;
    private int y = 0;
    private int r0 = 0;

    private int programCounter = 1;

    private ArrayList<Integer> memory = new ArrayList<Integer>();
    private HashMap<String, Integer> labels = new HashMap<String, Integer>();

    public static void main(String[] args){
        new Main();
    }

    private Main() {
        initMemory();
        initArray();
        initLabels();
        runProgram();
        System.out.println(r0);
    }

    private void initMemory() {
        for (int i = 0; i < 100; i++) {
            memory.add(null);
        }
    }

    private void initArray() {
        for (int i = 0; i < array.size(); i++) {
            memory.set(i+1, array.get(i));
        }
    }

    private void initLabels() {
        LABEL("begin", 4);
    }

    private void runProgram() {
        begin:
        while (true) {
            System.out.println("PC: " + programCounter);
            switch (programCounter) {
                case 1:
                    x = MOV(x, 0);
                    break;

                case 2:
                    y = MOV(y, array.size());
                    break;

                case 3:
                    r0 = MOV(r0, array.get(x));
                    break;

                case 4:

                    ADD(array.get(x));

                    break;

                case 5:
                    x = INC(x);
                    break;

                case 6:
                    if (CMP(x, y)) {
                        programCounter = 3;
                    }
                    break;

                case 7: {
                    JL("begin");
                    continue begin;
                }
            }
            programCounter++;
            if (programCounter >= 7)
                break;
        }

    }



    private void LABEL(String name, int pc) {
        System.out.print("LABEL");
        labels.put(name, pc);
    }

    private int INC (int a) {
        System.out.println("INC ");
        return ++a;
    }




    private void ADD(int b) {
        System.out.println("ADD ");
        r0 += b;
    }



    private boolean CMP (int a, int b){
        System.out.println("CMP " + a + " " +b);
        if(a < b){
            return true;
        }
        return false;
    }

    private int MOV (int a, int b) {
        System.out.println("MOV");
        a = b;
        return a;
    }

    private void JL(String name) {
        System.out.println("JL");
        System.out.println(x);
        for (Map.Entry<String, Integer> lbl : labels.entrySet()) {
            if (lbl.getKey().equals(name)) {
                programCounter = lbl.getValue();
                return;
            }
        }
    }


    private void JMP(String name) {
        System.out.println("JMP");
        for (Map.Entry<String, Integer> lbl : labels.entrySet()) {
            if (lbl.getKey().equals(name)) {
                programCounter = lbl.getValue();
                return;
            }
        }
        throw new RuntimeException("Specified label not found: " + programCounter + ", '" + name + "'");
    }

}