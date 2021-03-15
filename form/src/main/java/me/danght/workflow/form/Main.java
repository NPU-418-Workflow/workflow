package me.danght.workflow.form;

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
        Quarkus.run(Form.class, args);
    }

    public static class Form implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            System.out.println("WorkFlow Form starting...");
            Quarkus.waitForExit();
            return 0;
        }
    }

}
