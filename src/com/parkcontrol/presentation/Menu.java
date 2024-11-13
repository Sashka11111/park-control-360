package com.parkcontrol.presentation;

import com.parkcontrol.domain.validation.UserInputHandler;
import com.parkcontrol.service.CategoryService;
import com.parkcontrol.service.operations.AuthorizationService;
import com.parkcontrol.service.ParkingSpotService;
import com.parkcontrol.service.operations.DeleteService;
import com.parkcontrol.service.operations.EditService;
import com.parkcontrol.service.operations.RegistrationService;
import com.parkcontrol.service.operations.SearchService;
import com.parkcontrol.service.ParkingTicketService;

public class Menu {

  public Menu() {
  }

  public static void show() throws IllegalAccessException {
    UserInputHandler userInputHandler = new UserInputHandler();
    SearchService searchService = new SearchService();  // Ініціалізація SearchService
    while (true) {
      if (Application.currentUser == null) {
        String art = "╭╮╭╮╭┳━━━┳╮╱╱╭━━━┳━━━┳━╮╭━┳━━━╮\n"
            + "┃┃┃┃┃┃╭━━┫┃╱╱┃╭━╮┃╭━╮┃┃╰╯┃┃╭━━╯\n"
            + "┃┃┃┃┃┃╰━━┫┃╱╱┃┃╱╰┫┃╱┃┃╭╮╭╮┃╰━━╮\n"
            + "┃╰╯╰╯┃╭━━┫┃╱╭┫┃╱╭┫┃╱┃┃┃┃┃┃┃╭━━╯\n"
            + "╰╮╭╮╭┫╰━━┫╰━╯┃╰━╯┃╰━╯┃┃┃┃┃┃╰━━╮\n"
            + "╱╰╯╰╯╰━━━┻━━━┻━━━┻━━━┻╯╰╯╰┻━━━╯\n";

        System.out.println(art);
        System.out.println("1) Реєстрація");
        System.out.println("2) Авторизація");
        System.out.println("0) Вихід");

        int choice = userInputHandler.getIntInput("Ваш вибір: ");

        switch (choice) {
          case 1:
            RegistrationService.registration();
            AuthorizationService.authorization();
            break;
          case 2:
            AuthorizationService.authorization();
            break;
          case 0:
            System.out.println("Дякую за використання.");
            System.exit(0);
            break;
          default:
            System.out.println("Невірний вибір. Спробуйте ще раз.");
            break;
        }
        continue; // Повернення на початок циклу
      }

      String userRole = Application.currentUser.getRole();

      if ("".equals(userRole)) {
        System.out.println("1) Реєстрація");
        System.out.println("2) Авторизація");
      } else {
        String art = "        _______ \n" +
            "       // ||\\ \\ \n" +
            " _____//___||_\\ \\___\n" +
            " ) _ _ \\ \n" +
            " |_/ \\________/ \\___|\n" +
            "___\\_/________\\_/______\n";

        System.out.println(art);
        System.out.println("1) Перегляд даних");
        System.out.println("2) Пошук паркувальних місць");
        System.out.println("3) Забронювати місце");

        if ("Admin".equals(userRole)) {
          System.out.println("4) Додавання даних");
          System.out.println("5) Редагування даних");
          System.out.println("6) Видалення даних");
        }
      }

      System.out.println("0) Вихід з головного меню");

      int choice = userInputHandler.getIntInput("Ваш вибір: ");

      switch (choice) {
        case 1:
          showViewMenu();
          break;
        case 2:
          showSearchMenu(searchService);  // Викликаємо метод пошуку
          break;
        case 3:
          ParkingTicketService parkingTicketService = new ParkingTicketService();
          parkingTicketService.addParkingTicket();
          break;
        case 4:
          if ("Admin".equals(userRole)) {
            showAddMenu();
          }
          break;
        case 5:
          if ("Admin".equals(userRole)) {
            showEditMenu();
          }
          break;
        case 6:
          if ("Admin".equals(userRole)) {
            showDeleteMenu();
          }
          break;
        case 0:
          Application.currentUser = null; // Вихід з головного меню
          break;
        default:
          System.out.println("Невірний вибір. Спробуйте ще раз.");
          break;
      }
    }
  }

  private static void showSearchMenu(SearchService searchService) {
    System.out.println("1) Пошук за категорією");
    System.out.println("2) Пошук за номером місця");
    System.out.println("3) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        String categoryName = new UserInputHandler().getStringInput("Введіть назву категорії: ");
        searchService.searchParkingSpotsByCategoryName(categoryName);
        break;
      case 2:
        int spotNumber = new UserInputHandler().getIntInput("Введіть номер місця: ");
        searchService.searchParkingSpotsByNumber(spotNumber);
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showViewMenu() {
    System.out.println("1) Переглянути всі паркувальні місця");
    System.out.println("2) Переглянути вільні місця");
    System.out.println("3) Переглянути мої квитки");
    System.out.println("4) Переглянути категорії");
    System.out.println("5) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        ParkingSpotService.main(new String[]{});
        break;
      case 2:
        ParkingSpotService.displayAvailableParkingSpots();
        break;
      case 3:
        ParkingTicketService.main(new String[]{});
        break;
      case 4:
        CategoryService.main(new String[]{});
        break;
      case 5:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showAddMenu() {
    System.out.println("1) Додати нове паркувальне місце");
    System.out.println("2) Додати нову категорію ");
    System.out.println("3) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        ParkingSpotService.addParkingSpot();
        break;
      case 2:
        CategoryService.addCategory();
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showEditMenu() {
    System.out.println("Що ви хочете редагувати?");
    System.out.println("1) Редагувати паркувальне місце");
    System.out.println("2) Редагувати категорії");
    System.out.println("3) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        EditService.editParkingSpot();
        break;
      case 2:
        EditService.editParkingCategory();
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showDeleteMenu() {
    System.out.println("Що ви хочете видалити?");
    System.out.println("1) Видалити паркувальне місце");
    System.out.println("2) Видалити користувача");
    System.out.println("3) Видалити категорію");
    System.out.println("4) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        DeleteService.deleteParkingSpot();
        break;
      case 2:
        DeleteService.deleteUser();
        break;
      case 3:
        DeleteService.deleteParkingCategory();
        break;
      case 4:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }
}
