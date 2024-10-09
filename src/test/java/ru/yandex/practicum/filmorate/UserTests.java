package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class UserTests {

	@Test
	void createCorrectUser() throws ParseException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.MARCH, 5);
		Date date = calendar.getTime();

		User user = new User("name@.yandex.ru", "nick", "Nick", date);
		UserController userController = new UserController();
		userController.create(user);

		Assertions.assertEquals(userController.get().get(0), user);
	}

	@Test
	void createInCorrectEmailUser(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.MARCH, 5);

		Date date = calendar.getTime();
		User user = new User("nameyandex.ru", "nick", "Nick", date);
		User user2 = new User("", "nick", "Nick", date);

		UserController userController = new UserController();

		try {
			userController.create(user);
		} catch (Exception e){
			Assertions.assertEquals("InvalidEmailException", e.getMessage());
		}

		try {
			userController.create(user2);
		} catch (Exception e){
			Assertions.assertEquals("InvalidEmailException", e.getMessage());
		}
	}

	@Test
	void createInCorrectLoginUser(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.MARCH, 5);

		Date date = calendar.getTime();
		User user = new User("name@yandex.ru", "", "Nick", date);
		User user2 = new User("name@yandex.ru", "nick cool", "Nick", date);

		UserController userController = new UserController();

		try {
			userController.create(user);
		} catch (Exception e){
			Assertions.assertEquals("InvalidLoginException", e.getMessage());
		}

		try {
			userController.create(user2);
		} catch (Exception e){
			Assertions.assertEquals("InvalidLoginException", e.getMessage());
		}
	}

	@Test
	void createEmptyNameUser() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.MARCH, 5);

		Date date = calendar.getTime();
		User user = new User("name@yandex.ru", "Nick", "", date);

		UserController userController = new UserController();
		userController.create(user);

		Assertions.assertEquals(userController.get().get(0).getName(), "Nick");
	}

	@Test
	void createInCorrectDateUser() {
		Calendar calendar = Calendar.getInstance();
		int year = Year.now().getValue();
		calendar.set(year + 1, Calendar.MARCH, 5);

		Date date = calendar.getTime();
		User user = new User("name@yandex.ru", "Nick", "Nick", date);

		UserController userController = new UserController();
		try {
			userController.create(user);
		} catch (Exception e){
			Assertions.assertEquals("InvalidDateException", e.getMessage());
		}
	}

	@Test
	void updateUser() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.MARCH, 5);
		Date date = calendar.getTime();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2024, Calendar.MARCH, 5);

		Date date2 = calendar2.getTime();
		User user = new User("name@yandex.ru", "Nick", "Nick", date);
		User user2 = new User("name@yandex.ru", "NickNick", "NickNick", date2);

		UserController userController = new UserController();
		userController.create(user);
		userController.update(user2);

		Assertions.assertEquals(userController.get().get(0).getName(), "NickNick");
		Assertions.assertEquals(userController.get().get(0).getLogin(), "NickNick");
		Assertions.assertEquals(userController.get().get(0).getBirthday(), date2);
	}
}
