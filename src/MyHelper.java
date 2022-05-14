import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

//public class MyHelper {
//}

//import java.awt.*;
//        import java.awt.event.ActionEvent;
//        import java.awt.event.ActionListener;
//        import java.awt.event.WindowEvent;
//        import java.awt.event.WindowListener;
//        import java.io.*;
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import javax.swing.*;

class MyHelper extends JFrame {
    private JTextField nameOfBook = new JTextField();
    private JTextField nameOfAutor = new JTextField();
    public ArrayList<String> books = new ArrayList<>();
    private static ArrayList<String> autros = new ArrayList<>();
    public String book;
    final JPanel labPanel = new JPanel();
    //final JPanel butPanel = new JPanel();
    final JScrollPane scrollPane = new JScrollPane(labPanel);
    final JFrame frame = new JFrame("Помощник библиотекаря");
    final Font font = new Font("Verdana", Font.PLAIN, 10);

    MyHelper() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel butPanel = new JPanel();

        JButton addButton = new JButton("Добавить книгу");
        addButton.setFont(font);
        addButton.setFocusable(false);
        butPanel.add(addButton);
        add(butPanel);

        JButton nameSearchButton = new JButton("Поиск по названию");
        nameSearchButton.setFont(font);
        nameSearchButton.setFocusable(false);
        butPanel.add(nameSearchButton);

        JButton searchByAuthorButton = new JButton("Поиск по автору");
        searchByAuthorButton.setFont(font);
        searchByAuthorButton.setFocusable(false);
        butPanel.add(searchByAuthorButton);


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = createDialog("Добавление книги", false);
                frame.setVisible(false);
                dialog.setVisible(true);
            }
        });

        nameSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Filters.main(books, "Поиск книги по названию");
            }
        });
        searchByAuthorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Filters.main(autros, "Поиск книги по автору");
            }
        });


        frame.getContentPane().add(butPanel, BorderLayout.NORTH);


        frame.setPreferredSize(new Dimension(550, 80));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void readText() {
        try {
            books.clear();
            File file = new File("books.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                books.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeText() {
        FileWriter writer;// = null;
        try {
            writer = new FileWriter("books.txt", false);
            for (String book : books) {
                writer.write(book + " " + System.getProperty("line.separator"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void writeText(String book) {

        FileWriter writer;
        try {
            writer = new FileWriter("books.txt", true);
            writer.write(book + " " + System.getProperty("line.separator"));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void panelFilling() {
        labPanel.removeAll();
        labPanel.repaint();
        labPanel.revalidate();
        readText();
        frame.setVisible(true);
        frame.add(labPanel);
        labPanel.setLayout(new BoxLayout(labPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < books.size(); i++) {
            JLabel label = new JLabel();
            label.setFont(font);

            label.setText(books.get(i));
            label.setAlignmentX(JLabel.RIGHT);
            labPanel.add(label);
            JButton remButton = new JButton("Удалить");
            remButton.setFont(font);
            labPanel.add(remButton);


            remButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    labPanel.remove(remButton);
                    labPanel.remove(label);

                    labPanel.repaint();
                    labPanel.revalidate();
                    scrollPane.revalidate();
                }
            });
        }
        frame.revalidate();
        frame.repaint();

        labPanel.repaint();
        labPanel.revalidate();
        scrollPane.revalidate();
        frame.revalidate();
    }


    private JDialog createDialog(String title, boolean modal) {
        {
            // Подключение украшений для окон
            JDialog.setDefaultLookAndFeelDecorated(true);
            JDialog dialog = new JDialog(this, title, modal);
            dialog.setLocationRelativeTo(null);

            dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            dialog.addWindowListener(new WindowListener() {
                public void windowActivated(WindowEvent event) {
                }

                public void windowClosed(WindowEvent event) {
                }

                public void windowClosing(WindowEvent event) {
                    Object[] options = {"Да", "Нет!"};
                    int n = JOptionPane.showOptionDialog(event.getWindow(), "Вы закончили ввод данных?",
                            "Подтверждение", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
                    if (n == 0) {

                        labPanel.removeAll();
                        labPanel.repaint();
                        labPanel.revalidate();

                        panelFilling();
                        event.getWindow().setVisible(false);
                        frame.setVisible(true);
                    }
                }

                public void windowDeactivated(WindowEvent event) {

                }

                public void windowDeiconified(WindowEvent event) {

                }

                public void windowIconified(WindowEvent event) {

                }

                public void windowOpened(WindowEvent event) {

                }

            });

            dialog.setSize(600, 150);

            nameOfBook.setBounds(10, 40, 160, 20);
            nameOfAutor.setBounds(200, 40, 160, 20);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JLabel label1 = new JLabel("Ведите название книги");
            label1.setBounds(10, 10, 150, 20);
            JLabel label2 = new JLabel("Ведите автора книги");
            label2.setBounds(200, 10, 150, 20);
            JButton addbutton = new JButton("Добавить");


            addbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (nameOfBook.getText().equals("")) {
                        JOptionPane.showMessageDialog(dialog, "Вы не ввели название книги", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
                    } else if (nameOfAutor.getText().equals("")) {
                        JOptionPane.showMessageDialog(dialog, "Вы не ввели автора книги", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String book = nameOfBook.getText();
                        writeText(book);
                        books.add(book);
                        nameOfBook.setText("");
                        String autor = nameOfAutor.getText();
                        autros.add(autor);
                        nameOfAutor.setText("");
                        //  setVisible(true);
                        // start();
                        System.out.println("Это BookList - " + books.size());
                        System.out.println(String.join("-", books));
                        System.out.print("Это AutorList - " + autros.size());
                        System.out.println(String.join("-", autros));
                    }
                }
            });
            addbutton.setBounds(400, 40, 100, 20);
            addbutton.setFocusable(false);
            panel.setLayout(null);
            panel.add(label1);
            panel.add(nameOfBook);
            panel.add(label2);
            panel.add(nameOfAutor);
            panel.add(addbutton);
            dialog.add(panel);
            return dialog;
        }
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);

                new MyHelper().panelFilling();
            }
        });
    }
}
