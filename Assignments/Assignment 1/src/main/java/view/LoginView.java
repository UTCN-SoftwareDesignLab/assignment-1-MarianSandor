package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;

    public LoginView() throws HeadlessException {
        setSize(300, 150);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(new JLabel("Username"));
        add(tfUsername);
        add(new JLabel("Password"));
        add(tfPassword);
        add(btnLogin);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JPasswordField();
        btnLogin = new JButton("Login");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return new String(tfPassword.getPassword());
    }

    public void clearFields() {
        tfUsername.setText("");
        tfPassword.setText("");
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }

}
