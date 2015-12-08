package mfes_mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class MyTestCase {
    public MyTestCase() {
    }

    protected void assertTrue(final Boolean arg) {
        return;
    }

    protected void assertEqual(final Object expected, final Object actual) {
        if (!(Utils.equals(expected, actual))) {
            IO.print("Actual value (");
            IO.print(((Object) actual));
            IO.print(") different from expected (");
            IO.print(((Object) expected));
            IO.println(")\n");
        }
    }

    public String toString() {
        return "MyTestCase{}";
    }
}
