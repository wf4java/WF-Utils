package main.main25;

import wf.utils.java.time.cooldown.CoolDown;

public class Main3 {

    private static CoolDown<String> coolDown = new CoolDown<>();

    public static void main(String[] args) {
        coolDown.addItem("wf",10000);
        System.out.println(coolDown.secondsLeftCorrected("wf"));
        System.out.println(coolDown.secondsLeftCorrected("wf",2));
        System.out.println(coolDown.secondsLeftCorrected("wf",3));
        System.out.println(coolDown.secondsLeftCorrected("wf",5));
        System.out.println("\n\n");
        try {
            Thread.sleep(5200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(coolDown.secondsLeftCorrected("wf"));
        System.out.println(coolDown.secondsLeftCorrected("wf",2));
        System.out.println(coolDown.secondsLeftCorrected("wf",3));
        System.out.println(coolDown.secondsLeftCorrected("wf",5));
        System.out.println("\n\n");
        try {
            Thread.sleep(5200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(coolDown.secondsLeftCorrected("wf"));
        System.out.println(coolDown.secondsLeftCorrected("wf",2));
        System.out.println(coolDown.secondsLeftCorrected("wf",3));
        System.out.println(coolDown.secondsLeftCorrected("wf",5));
        System.out.println("\n\n");
    }


}
