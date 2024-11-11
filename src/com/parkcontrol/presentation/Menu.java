package com.parkcontrol.presentation;

import com.parkcontrol.domain.validation.UserInputHandler;
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
        System.out.println("2) Пошук даних");
        System.out.println("3) Створити паркувальний квиток");

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
          ParkingTicketService.createTicket();
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
        searchService.searchParkingSpotsByCategoryName(categoryName).forEach(spot ->
            System.out.println("Місце: " + spot.getSpotNumber() + " - Категорія ID: " + spot.getCategoryId()));
        break;
      case 2:
        int spotNumber = new UserInputHandler().getIntInput("Введіть номер місця: ");
        searchService.searchParkingSpotsByNumber(spotNumber).forEach(spot ->
            System.out.println("Місце: " + spot.getSpotNumber() + " - " + spot.getCategoryId()));
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showViewMenu() {
    System.out.println("1) Переглянути всі парковки");
    System.out.println("2) Переглянути вільні місця");
    System.out.println("3) Переглянути мій квиток");
    System.out.println("4) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

//    switch (choice) {
//      case 1:
//        ParkingSpotService.viewAllParkingSpots();
//        break;
//      case 2:
//        ParkingSpotService.viewAvailableParkingSpots();
//        break;
//      case 3:
//        ParkingTicketService.viewMyTicket(Application.currentUser);
//        break;
//      case 4:
//        return;
//      default:
//        System.out.println("Невірний вибір. Спробуйте ще раз.");
//        break;
//    }
  }

  private static void showAddMenu() {
    System.out.println("Що ви хочете додати?");
    System.out.println("1) Додати нову парковку");
    System.out.println("2) Додати новий паркувальний квиток");
    System.out.println("3) Додати нового користувача");
    System.out.println("4) Додати нову категорію парковки");
    System.out.println("5) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        ParkingSpotService.addParkingSpot();
        break;
      case 2:
        //ParkingTicketService.addParkingTicket();
        break;
      case 3:
        RegistrationService.registration();
        break;
      case 4:
        // Add new category logic here
        break;
      case 5:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showEditMenu() {
    System.out.println("Що ви хочете редагувати?");
    System.out.println("1) Редагувати парковку");
    System.out.println("2) Редагувати паркувальний квиток");
    System.out.println("3) Редагувати дані користувача");
    System.out.println("4) Редагувати категорію парковки");
    System.out.println("5) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

//    switch (choice) {
//      case 1:
//        EditService.editParkingSpot();
//        break;
//      case 2:
//        EditService.editParkingTicket();
//        break;
//      case 3:
//        EditService.editUserDetails();
//        break;
//      case 4:
//        EditService.editParkingCategory();
//        break;
//      case 5:
//        return;
//      default:
//        System.out.println("Невірний вибір. Спробуйте ще раз.");
//        break;
//    }
  }

  private static void showDeleteMenu() {
    System.out.println("Що ви хочете видалити?");
    System.out.println("1) Видалити парковку");
    System.out.println("2) Видалити паркувальний квиток");
    System.out.println("3) Видалити користувача");
    System.out.println("4) Видалити категорію парковки");
    System.out.println("5) Назад");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        DeleteService.deleteParkingSpot();
        break;
      case 2:
        //DeleteService.deleteParkingTicket();
        break;
      case 3:
        DeleteService.deleteUser();
        break;
      case 4:
       // DeleteService.deleteParkingCategory();
        break;
      case 5:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }
}
