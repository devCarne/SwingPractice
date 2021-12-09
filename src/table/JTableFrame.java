package table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JTableFrame extends JFrame implements MouseListener, KeyListener {

    private final String[] labels = {"Name", "Age", "Sex", "Korean", "English", "Math"};

    private JTextField[] textFields = new JTextField[labels.length];
    private JTable table;
    private JScrollPane scrollPane;
    private JButton addBtn;
    private JButton delBtn;

    public JTableFrame() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(6, 0, 10, 10));
        for (int i = 0; i < labels.length; i++) {
            topPanel.add(new JLabel(labels[i]));
            textFields[i] = new JTextField(30);
            topPanel.add(textFields[i]);
        }
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add("North", topPanel);

        table = new JTable(new DefaultTableModel((labels), 0));
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add("Center", scrollPane);

        JPanel rightPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        addBtn = new JButton("레코드 추가");
        delBtn = new JButton("레코드 삭제");
        rightPanel.add(addBtn);
        rightPanel.add(delBtn);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.add("East", rightPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(620, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        addBtn.addMouseListener(this);
        delBtn.addMouseListener(this);

        for (int i = 0; i < labels.length; i++) {
            textFields[i].addKeyListener(this);
        }

        table.addMouseListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            addRecord();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object src = e.getSource();
        if(src == addBtn) {
            addRecord();
        } else if (src == delBtn) {
            delRecord(table.getSelectedRow());
        } else if (src == table) {
            printCell(table.getSelectedRow(), table.getSelectedColumn());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addRecord() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String[] record = new String[6];

        for (int i = 0; i < 6; i++) {
            if (isInvalidInput(textFields[i].getText())) {
                System.out.println("Invalid Input");
                return;
            }
            record[i] = textFields[i].getText();
        }
        model.addRow(record);
        for (JTextField textField : textFields) {
            textField.setText("");
        }
        textFields[0].requestFocus();
    }

    public void delRecord(int index) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if (index < 0) {
            if (table.getRowCount() == 0) return;
            index = 0;
        }
        model.removeRow(index);
    }

    private void printCell(int selectedRow, int selectedColumn) {
        System.out.println(table.getValueAt(selectedRow, selectedColumn));
    }

    public boolean isInvalidInput(String input) {
        return (input == null) || (input.length() == 0);
    }

    public static void main(String[] args) {
        new JTableFrame();
    }
}
