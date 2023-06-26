import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMInterface extends JFrame {
    private double balance;
    private String pin;
    private JLabel balanceLabel;

    public ATMInterface(double balance, String pin) {
        this.balance = balance;
        this.pin = pin;

        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        JLabel pinLabel = new JLabel("Enter your PIN:");
        JPasswordField pinField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");
        JButton changePinButton = new JButton("Change PIN");
        balanceLabel = new JLabel("Current Balance: $" + balance);
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] enteredPin = pinField.getPassword();
                if (String.valueOf(enteredPin).equals(pin)) {
                    pinLabel.setVisible(false);
                    pinField.setVisible(false);
                    loginButton.setVisible(false);
                    changePinButton.setVisible(true);
                    withdrawButton.setVisible(true);
                    depositButton.setVisible(true);
                    exitButton.setVisible(true);
                    balanceLabel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(ATMInterface.this, "Invalid PIN. Access denied.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String newPin = JOptionPane.showInputDialog(ATMInterface.this, "Enter the new PIN:");
                if (newPin != null && !newPin.isEmpty()) {
                    ATMInterface.this.pin = newPin;
                    JOptionPane.showMessageDialog(ATMInterface.this, "PIN successfully changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(ATMInterface.this, "Enter the amount to withdraw:");
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        withdrawMoney(amount);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ATMInterface.this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(ATMInterface.this, "Enter the amount to deposit:");
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        depositMoney(amount);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ATMInterface.this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        changePinButton.setVisible(false);
        withdrawButton.setVisible(false);
        depositButton.setVisible(false);
        exitButton.setVisible(false);
        balanceLabel.setVisible(false);

        add(pinLabel);
        add(pinField);
        add(loginButton);
        add(changePinButton);
        add(balanceLabel);
        add(withdrawButton);
        add(depositButton);
        add(exitButton);

        setVisible(true);
    }

    public void withdrawMoney(double amount) {
        if (amount <= balance) {
            balance -= amount;
            JOptionPane.showMessageDialog(this, "Withdrawal of $" + amount + " successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateBalanceLabel();
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void depositMoney(double amount) {
        balance += amount;
        JOptionPane.showMessageDialog(this, "Deposit of $" + amount + " successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateBalanceLabel();
    }

    public void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: $" + balance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ATMInterface atm = new ATMInterface(1000.0, "1234");
            }
        });
    }
}
