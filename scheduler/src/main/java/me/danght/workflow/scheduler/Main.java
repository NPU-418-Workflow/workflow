package me.danght.workflow.scheduler;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * @author DangHT
 * @date 2021/03/13
 */
@QuarkusMain
public class Main {

    public static void main(String[] args) {
        Quarkus.run(Scheduler.class, args);
    }

    public static class Scheduler implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            System.out.println("WorkFlow Scheduler starting...");
            Quarkus.waitForExit();
            return 0;
        }
    }

}
