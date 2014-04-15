package com.ouchadam.psr.presentation;

import javax.swing.*;

public class UiInvoker {

    public interface InvokeLater<T> {
        void invokeLater(T what);
    }

    public <T> void invokeLater(final InvokeLater<T> invokeLater, final T objects) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                invokeLater.invokeLater(objects);
            }
        });
    }

}
