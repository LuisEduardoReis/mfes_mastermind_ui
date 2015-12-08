package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class Main {
    public Main() {
    }

    public static void Run() {
        new TestGame().testAll();
    }

    public String toString() {
        return "Main{}";
    }

    public static void main(String[] args) {
        Run();
        IO.println(Utils.toString(Utils.VOID_VALUE));
    }
}
