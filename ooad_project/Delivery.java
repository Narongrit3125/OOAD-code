import java.util.Scanner;

public class Delivery {
    private String[] options;
    private String selectedOption;

    public Delivery() {
        this.options = new String[] {"Standard Delivery - $5", "Express Delivery - $10"};
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public void displayDeliveryOptions() {
        System.out.println("===================================");
        System.out.println("          DELIVERY OPTIONS");
        System.out.println("===================================");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("===================================");
    }

    public void selectDeliveryOption() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Select delivery option: ");
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= options.length) {
                selectedOption = options[choice - 1];
                System.out.println(selectedOption.split(" - ")[0] + " selected.");
            } else {
                System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
