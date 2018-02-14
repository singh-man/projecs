package com.generic;

import java.util.Arrays;
import java.util.List;

public class GenericExample {

    static class TA {

    }

    static class TB extends TA {

    }

    public static void met(List<TA> a) {
        
    }
    public static void create() {
        Z z = new Z();
        Integer i = z.createView(Integer.class);
        //String s = z.createView(String.class); - Compiler error
        Long l = z.createView(Long.class);
    }

    public static void main(String[] args) {
        N<String> n = new N<String>();

        A<?> a = new AImpl();	// means a of any type - here it works as Integer
        A<Integer> aI = new AImpl();
        //A<Long> aL = new AImpl(); - compile error
        A<String> as = new A_1_Impl<String>();

        //Bounded type example
        B<Integer> b = new BImpl<Integer>();
        B<Long> bL = new BImpl<Long>();

        create();

        met(Arrays.asList(new TB()));
    }
}

/*
 * If anywhere this is done <? extends T> in the method parameters (This <E extends T> is not allowed in method parameter)
 * that particular parameter can not add anything to itself 
 * specially in case of Collections.
 * Used anywhere else <? extends T> doesn't create any problems
 */
interface A<T> {

    void add(T t);
}

// The impl class defines which type in interface
class AImpl implements A<Integer> {

    public void add(Integer t) {
    }
}

// The impl class doesn't define which type in interface but during new operator
class A_1_Impl<T> implements A<T> {

    public void add(T t) {
    }
}

// Abstract class 
abstract class AbstractA<T> implements A<T> {

    public void add(T t) {
    }
}

// Solid impl class only - type set in new operator
class N<T> {

    void addN(T t) {
    }
}

// Bounded type interface and impl example
interface B<T extends Number> {

    void add(T t);
}

class BImpl<T extends Number> implements B<T> {

    public void add(T t) {
    }
}

class Z {

    protected <T extends Number> T createView(Class<T> c) {
        T t = null;
        try {
            t = (T) c.getConstructors()[1].newInstance("10");
            //t = c.newInstance(); // Only if class has default constructor
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
