package utilities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AssertCollection {
    private ArrayList<String> description = new ArrayList();
    private ArrayList<String> expected = new ArrayList();
    private ArrayList<String> actual = new ArrayList();

    public AssertCollection() {
    }

    public void addItem(String description, String expected, String actual) {
        this.description.add(description);
        this.expected.add(expected);
        this.actual.add(actual);
    }

    public void addItem(String description, Integer expected, Integer actual) {
        this.description.add(description);
        this.expected.add(expected.toString());
        this.actual.add(actual.toString());
    }

    public void addItem(String description, Double expected, Double actual) {
        this.description.add(description);
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        this.expected.add(formatter.format(expected));
        this.actual.add(formatter.format(actual));
    }

    public void addItem(String description, int expected, int actual) {
        this.description.add(description);
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        this.expected.add(formatter.format((long) expected));
        this.actual.add(formatter.format((long) actual));
    }

    public void addItem(String description, int expected, Integer actual) {
        this.description.add(description);
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        this.expected.add(formatter.format((long) expected));
        this.actual.add(actual.toString());
    }

    public void addItem(String description, boolean actual) {
        this.description.add(description);
        this.expected.add("true");
        this.actual.add(actual ? "true" : "false");
    }

    public void addItem(String description, boolean expected, boolean actual) {
        this.description.add(description);
        this.expected.add(expected ? "true" : "false");
        this.actual.add(actual ? "true" : "false");
    }

    public boolean getAssertResult() {
        boolean result = true;
        int max = this.description.size();

        for (int count = 0; count < max; ++count) {
            String exp = (String) this.expected.get(count);
            String act = (String) this.actual.get(count);
            if (exp == null & act != null) {
                result = false;
            } else if (exp != null & act == null) {
                result = false;
            } else if (!exp.equalsIgnoreCase(act)) {
                result = false;
            }
        }

        return result;
    }

    public String getAssertMessage() {
        String result = "Assertion Results : ";
        int max = this.description.size();

        for (int count = 0; count < max; ++count) {
            String act = (String) this.actual.get(count);
            String exp = (String) this.expected.get(count);
            if (!act.equals(exp)) {
                String desc = (String) this.description.get(count);
                result = result + "[Description=" + desc;
                result = result + ", Expected=" + exp;
                result = result + ", Actual=" + act + "] ";
            }
        }

        return result;
    }
}
