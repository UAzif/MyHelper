
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


class FilteringJList  extends JList {
    private JTextField input;

    public FilteringJList() {
        FilteringModel model = new FilteringModel();
        setModel(new FilteringModel());
    }

    public void installJTextField(JTextField input) {
        if (input != null) {
            this.input = input;
            FilteringModel model = (FilteringModel) getModel();
            input.getDocument().addDocumentListener(model);
        }
    }

    public void uninstallJTextField(JTextField input) {
        if (input != null) {
            FilteringModel model = (FilteringModel) getModel();
            input.getDocument().removeDocumentListener(model);
            this.input = null;
        }
    }

    public void setModel(ListModel model) {
        if (!(model instanceof FilteringModel)) {
            throw new IllegalArgumentException();
        } else {
            super.setModel(model);
        }
    }

    public void addElement(Object element) {
        ((FilteringModel) getModel()).addElement(element);
    }

    private class FilteringModel extends AbstractListModel implements DocumentListener {
        List<Object> list;
        List<Object> filteredList;
        String lastFilter = "";

        public FilteringModel() {
            list = new ArrayList<Object>();
            filteredList = new ArrayList<Object>();
        }

        public void addElement(Object element) {
            list.add(element);
            filter(lastFilter);
        }

        public int getSize() {
            return filteredList.size();
        }

        public Object getElementAt(int index) {
            Object returnValue;
            if (index < filteredList.size()) {
                returnValue = filteredList.get(index);
            } else {
                returnValue = null;
            }
            return returnValue;
        }

        void filter(String search) {
            filteredList.clear();
            for (Object element : list) {
                if (element.toString().indexOf(search, 0) != -1) {
                    filteredList.add(element);
                }
            }
            fireContentsChanged(this, 0, getSize());
        }

        public void insertUpdate(DocumentEvent event) {
            Document doc = event.getDocument();
            try {
                lastFilter = doc.getText(0, doc.getLength());
                filter(lastFilter);
            } catch (BadLocationException ble) {
                System.err.println("Bad location: " + ble);
            }
        }

        public void removeUpdate(DocumentEvent event) {
            Document doc = event.getDocument();
            try {
                lastFilter = doc.getText(0, doc.getLength());
                filter(lastFilter);
            } catch (BadLocationException ble) {
                System.err.println("Bad location: " + ble);
            }
        }

        public void changedUpdate(DocumentEvent event) {
        }
    }
}

class Filters {
    public static void main(ArrayList<String> elements, String name) {
        Runnable runner = new Runnable() {
            public void run() {
                JFrame frame = new JFrame(name);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                // frame.setVisible(false);
                //Ferst.getWindows().
                FilteringJList list = new FilteringJList();
                JScrollPane pane = new JScrollPane(list);
                frame.add(pane, BorderLayout.CENTER);
                JTextField text = new JTextField();
                list.installJTextField(text);
                frame.add(text, BorderLayout.NORTH);

                for (String element :elements) {
                    list.addElement(element);
                }

                frame.setSize(350, 250);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }
}



