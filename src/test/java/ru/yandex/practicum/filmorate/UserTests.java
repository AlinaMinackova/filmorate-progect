//package ru.yandex.practicum.filmorate;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.yandex.practicum.filmorate.controller.UserController;
//import ru.yandex.practicum.filmorate.exceptions.InvalidDateException;
//import ru.yandex.practicum.filmorate.exceptions.InvalidDurationException;
//import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
//import ru.yandex.practicum.filmorate.exceptions.InvalidLoginException;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.text.ParseException;
//import java.time.Year;
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//class UserTests {
//
//	private static final Calendar calendar = Calendar.getInstance();
//	static {
//		calendar.set(2022, Calendar.MARCH, 5);
//	}
//	private static final Date date = calendar.getTime();
//
//	@Test
//	void createCorrectUser() throws ParseException {
//		User user = new User("name@.yandex.ru", "nick", "Nick", date);
//		UserController userController = new UserController();
//		userController.create(user);
//		Assertions.assertEquals(userController.get().get(0), user);
//	}
//
//	@Test
//	void createInCorrectEmailUser(){
//		User user = new User("nameyandex.ru", "nick", "Nick", date);
//		User user2 = new User("", "nick", "Nick", date);
//
//		UserController userController = new UserController();
//		assertThrows(InvalidEmailException.class,
//				() -> userController.create(user));
//
//		assertThrows(InvalidEmailException.class,
//				() -> userController.create(user2));
//	}
//
//	@Test
//	void createInCorrectLoginUser(){
//		User user = new User("name@yandex.ru", "", "Nick", date);
//		User user2 = new User("name@yandex.ru", "nick cool", "Nick", date);
//
//		UserController userController = new UserController();
//
//		assertThrows(InvalidLoginException.class,
//				() -> userController.create(user));
//
//		assertThrows(InvalidLoginException.class,
//				() -> userController.create(user2));
//	}
//
//	@Test
//	void createEmptyNameUser() throws ParseException {
//		User user = new User("name@yandex.ru", "Nick", "", date);
//
//		UserController userController = new UserController();
//		userController.create(user);
//
//		Assertions.assertEquals(userController.get().get(0).getName(), "Nick");
//	}
//
//	@Test
//	void createInCorrectDateUser() {
//		Calendar calendar = Calendar.getInstance();
//		int year = Year.now().getValue();
//		calendar.set(year + 1, Calendar.MARCH, 5);
//
//		Date date = calendar.getTime();
//		User user = new User("name@yandex.ru", "Nick", "Nick", date);
//
//		UserController userController = new UserController();
//		assertThrows(InvalidDateException.class,
//				() -> userController.create(user));
//	}
//
//	@Test
//	void updateUser() throws ParseException {
//
//		Calendar calendar2 = Calendar.getInstance();
//		calendar2.set(2024, Calendar.MARCH, 5);
//
//		Date date2 = calendar2.getTime();
//		User user = new User("name@yandex.ru", "Nick", "Nick", date);
//		User user2 = new User("name@yandex.ru", "NickNick", "NickNick", date2);
//
//		UserController userController = new UserController();
//		userController.create(user);
//		userController.update(user2);
//
//		Assertions.assertEquals(userController.get().get(0).getName(), "NickNick");
//		Assertions.assertEquals(userController.get().get(0).getLogin(), "NickNick");
//		Assertions.assertEquals(userController.get().get(0).getBirthday(), date2);
//	}
//}
