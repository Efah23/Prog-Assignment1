import java.util.ArrayList;
import java.util.Scanner;

public class Series {
    private ArrayList<SeriesModel> seriesList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Series app = new Series();
        app.runApplication();
    }

    public ArrayList<SeriesModel> getSeriesList() {
        return seriesList;
    }

    public void runApplication() {
        boolean exit = false;

        while (!exit) {
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    captureSeries();
                    break;
                case "2":
                    searchSeries();
                    break;
                case "3":
                    updateSeries();
                    break;
                case "4":
                    deleteSeries();
                    break;
                case "5":
                    seriesReport();
                    break;
                case "6":
                    exit = confirmExit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nTV Series Management Application");
        System.out.println("1. Capture a new series");
        System.out.println("2. Search for a series");
        System.out.println("3. Update a series");
        System.out.println("4. Delete a series");
        System.out.println("5. Display series report");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }

    public void captureSeries() {
        System.out.println("\nCapture New Series");
        System.out.print("Enter Series ID: ");
        String seriesId = scanner.nextLine();

        System.out.print("Enter Series Name: ");
        String seriesName = scanner.nextLine();

        String seriesAge;
        while (true) {
            System.out.print("Enter Age Restriction (2-18): ");
            seriesAge = scanner.nextLine();
            if (isValidAge(seriesAge)) {
                break;
            }
            System.out.println("Invalid age restriction. Please enter a number between 2 and 18.");
        }

        System.out.print("Enter Number of Episodes: ");
        String seriesNumberOfEpisodes = scanner.nextLine();

        SeriesModel newSeries = new SeriesModel(seriesId, seriesName, seriesAge, seriesNumberOfEpisodes);
        seriesList.add(newSeries);

        System.out.println("\nSeries details have been successfully saved!");
    }

    private boolean isValidAge(String age) {
        try {
            int ageNum = Integer.parseInt(age);
            return ageNum >= 2 && ageNum <= 18;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void searchSeries() {
        System.out.println("\nSearch for a Series");
        System.out.print("Enter Series ID to search: ");
        String searchId = scanner.nextLine();

        boolean found = false;
        for (SeriesModel series : seriesList) {
            if (series.seriesId.equals(searchId)) {
                displaySeriesDetails(series);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No series data could be found with ID: " + searchId);
        }
    }

    private void displaySeriesDetails(SeriesModel series) {
        System.out.println("\nSeries Details:");
        System.out.println("ID: " + series.seriesId);
        System.out.println("Name: " + series.seriesName);
        System.out.println("Age Restriction: " + series.seriesAge);
        System.out.println("Number of Episodes: " + series.seriesNumberOfEpisodes);
    }

    public void updateSeries() {
        System.out.println("\nUpdate a Series");
        System.out.print("Enter Series ID to update: ");
        String updateId = scanner.nextLine();

        SeriesModel seriesToUpdate = null;
        for (SeriesModel series : seriesList) {
            if (series.seriesId.equals(updateId)) {
                seriesToUpdate = series;
                break;
            }
        }

        if (seriesToUpdate == null) {
            System.out.println("No series found with ID: " + updateId);
            return;
        }

        System.out.println("\nCurrent Series Details:");
        displaySeriesDetails(seriesToUpdate);

        System.out.print("\nEnter new Series Name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            seriesToUpdate.seriesName = newName;
        }

        String newAge;
        while (true) {
            System.out.print("Enter new Age Restriction (2-18, leave blank to keep current): ");
            newAge = scanner.nextLine();
            if (newAge.isEmpty()) {
                break;
            }
            if (isValidAge(newAge)) {
                seriesToUpdate.seriesAge = newAge;
                break;
            }
            System.out.println("Invalid age restriction. Please enter a number between 2 and 18.");
        }

        System.out.print("Enter new Number of Episodes (leave blank to keep current): ");
        String newEpisodes = scanner.nextLine();
        if (!newEpisodes.isEmpty()) {
            seriesToUpdate.seriesNumberOfEpisodes = newEpisodes;
        }

        System.out.println("\nSeries details have been successfully updated!");
    }

    public void deleteSeries() {
        System.out.println("\nDelete a Series");
        System.out.print("Enter Series ID to delete: ");
        String deleteId = scanner.nextLine();

        SeriesModel seriesToDelete = null;
        for (SeriesModel series : seriesList) {
            if (series.seriesId.equals(deleteId)) {
                seriesToDelete = series;
                break;
            }
        }

        if (seriesToDelete == null) {
            System.out.println("No series found with ID: " + deleteId);
            return;
        }

        System.out.println("\nSeries to Delete:");
        displaySeriesDetails(seriesToDelete);

        System.out.print("\nAre you sure you want to delete this series? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            seriesList.remove(seriesToDelete);
            System.out.println("Series has been successfully deleted!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public void seriesReport() {
        if (seriesList.isEmpty()) {
            System.out.println("\nNo series data available to display.");
            return;
        }

        System.out.println("\nTV Series Report");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-15s\n", "ID", "Name", "Age Restriction", "Episodes");
        System.out.println("--------------------------------------------------");

        for (SeriesModel series : seriesList) {
            System.out.printf("%-10s %-20s %-15s %-15s\n",
                    series.seriesId,
                    series.seriesName,
                    series.seriesAge,
                    series.seriesNumberOfEpisodes);
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Total Series: " + seriesList.size());
    }

    private boolean confirmExit() {
        System.out.print("\nAre you sure you want to exit? (yes/no): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }
}