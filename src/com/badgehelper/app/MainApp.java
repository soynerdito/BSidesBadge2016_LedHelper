package com.badgehelper.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainApp extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -3029273708001622954L;

    private LedMatrix mMatrix = new LedMatrix();

    public static void main(String[] args) {
        MainApp e = new MainApp();
        e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e.setSize(400, 300);
        e.setVisible(true);
    }

    public MainApp() {
        super("My new Frame");
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(getHeader(), BorderLayout.NORTH);
        pane.add(getMatrixArea(), BorderLayout.CENTER);
        pane.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    protected JComponent getHeader() {
        JLabel label = new JLabel("Badge Matrix Helper", JLabel.CENTER);
        label.setFont(new Font("Courier", Font.BOLD, 24));
        return label;
    }

    protected JComponent getMatrixArea() {
        JPanel inner = new JPanel();
        JButton button;
        inner.setLayout(new GridLayout(8, 8));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                button = new JButton(String.format("R%dC%d", row, col));

                registerOnClickListener(button, row, col);
                inner.add(button);
            }
        }
        return inner;
    }

    protected JComponent getButtonPanel() {
        JPanel innerJPanel = new JPanel();
        JButton button;

        innerJPanel.setLayout(new GridLayout(1, 2, 10, 0));
        button = new JButton("Create Command");
        button.addActionListener(e -> {
            mMatrix.createCommands().forEach(System.out::println);
        });
        innerJPanel.add(button);
        button = new JButton("Cancel");
        button.addActionListener(e -> closeMe());
        innerJPanel.add(button);
        return innerJPanel;
    }

    protected void closeMe() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void registerOnClickListener(JButton button, int row, int column) {
        button.addActionListener(new ActionListener() {
            private int row, column;

            public ActionListener init(int row, int column) {
                this.row = row;
                this.column = column;

                return this;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                onClicked((JButton) e.getSource(), row, column);

            }

        }.init(row, column));
    }

    Color defaultColor = null;

    protected void onClicked(JButton button, int row, int column) {

        if (button.getBackground() == Color.blue) {
            button.setBackground(defaultColor);
        } else {
            if (defaultColor == null) {
                defaultColor = button.getBackground();
            }
            button.setBackground(Color.blue);
        }
        mMatrix.set(7 - row, 7 - column, (button.getBackground() == Color.blue));
    }
}

