package ejercicio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class Pruebas {
	
	static WebDriver driver1;

	@BeforeAll
	static void setURL() {
		driver1 = new ChromeDriver();
		
	}

	@BeforeEach
	void visitaPagina() {
		driver1.get("https://elenarivero.github.io/Ejercicio2/index.html");        
	}
	
	private static Stream<Arguments> testOK() {
		return Stream.of(
				Arguments.of("333","+"), // Test 1
				Arguments.of("001","+"), // Test 10
				Arguments.of("002","+"), // Test 11
				Arguments.of("998","+"), // Test 12
				Arguments.of("999","+") // Test 13
			);
	}
	
	private static Stream<Arguments> testFailA() {
		return Stream.of(
				Arguments.of("33A","+"), // Test 2
				Arguments.of("33_","+"), // Test 3
				Arguments.of("3","+"), // Test 4
				Arguments.of("33333","+"), // Test 5
				Arguments.of("44","+"), // Test 6
				Arguments.of("4444","+"), // Test 7
				Arguments.of("-222","+"), // Test 8
				Arguments.of("000","+"), // Test 9: Falla: El programa solo detecta si hay 3 caracteres.
				Arguments.of("1000","+"), // Test 14
				Arguments.of("","+") // Test 15
			);
	}
	
	private static Stream<Arguments> testFailB() {
		return Stream.of(
				Arguments.of("333",""), // Test 16
				Arguments.of("333","+++"), // Test 17: Falla: El programa no detecta si hay 2 o mas simbolos en el campo.
				Arguments.of("333","++"), // Test 18: Falla: El programa no detecta si hay 2 o mas simbolos en el campo.
				Arguments.of("333","A"), // Test 19
				Arguments.of("333","4"), // Test 20
				Arguments.of("333", "_") // Test 21
			);
	}
	
	@ParameterizedTest
	@MethodSource("testOK")
	void testOK(String numEmp, String directivo) {
		
		WebElement inputEmpleado = driver1.findElement(By.id("numero"));
		inputEmpleado.sendKeys(numEmp);
		
		WebElement inputDirectivo = driver1.findElement(By.id("directivo"));
		inputDirectivo.sendKeys(directivo);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement correcto = driver1.findElement(By.xpath("//h3"));
		boolean esCorrecto = correcto.isDisplayed();
		assertTrue(esCorrecto);

	}
	
	@ParameterizedTest
	@MethodSource("testFailA")
	void testFailA(String numEmp, String directivo) {
		
		WebElement inputEmpleado = driver1.findElement(By.id("numero"));
		inputEmpleado.sendKeys(numEmp);
		
		WebElement inputDirectivo = driver1.findElement(By.id("directivo"));
		inputDirectivo.sendKeys(directivo);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement errorNumero = driver1.findElement(By.id("errorNumero"));
		boolean errorN = errorNumero.isDisplayed();
		assertTrue(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorDirectivo"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertFalse(errorD);
	}
	
	@ParameterizedTest
	@MethodSource("testFailB")
	void testFailB(String numEmp, String directivo) {
		
		WebElement inputEmpleado = driver1.findElement(By.id("numero"));
		inputEmpleado.sendKeys(numEmp);
		
		WebElement inputDirectivo = driver1.findElement(By.id("directivo"));
		inputDirectivo.sendKeys(directivo);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement errorNumero = driver1.findElement(By.id("errorNumero"));
		boolean errorN = errorNumero.isDisplayed();
		assertFalse(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorDirectivo"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertTrue(errorD);
	}
	
	/*
	@AfterAll
	static void exitDriver() {
		driver1.quit();
	}*/
}
