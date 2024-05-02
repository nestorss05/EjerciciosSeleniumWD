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
		driver1.get("https://elenarivero.github.io/ejercicio3/index.html");        
	}
	
	private static Stream<Arguments> testOK() {
		return Stream.of(
				Arguments.of("Felipe Filipino","16/04/2006"), // Test 1
				Arguments.of("Felipe Filipino","01/03/2006"), // Test 12
				Arguments.of("Felipe Filipino","02/03/2006"), // Test 13
				Arguments.of("Felipe Filipino","30/03/2006"), // Test 14
				Arguments.of("Felipe Filipino","31/03/2006"), // Test 15
				Arguments.of("Felipe Filipino","16/01/2006"), // Test 20
				Arguments.of("Felipe Filipino","16/02/2006"), // Test 21
				Arguments.of("Felipe Filipino","16/11/2006"), // Test 22
				Arguments.of("Felipe Filipino","16/12/2006") // Test 23
			);
	}
	
	private static Stream<Arguments> testFailA() {
		return Stream.of(
				Arguments.of("","16/04/2006"), // Test 2
				Arguments.of("5elipe Filipino","16/04/2006"), // Test 3
				Arguments.of("*elipe Filipino","16/04/2006") // Test 4
			);
	}
	
	private static Stream<Arguments> testFailB() {
		return Stream.of(
				Arguments.of("Felipe Filipino",""), // Test 5
				Arguments.of("Felipe Filipino","16-04-2006"), // Test 6
				Arguments.of("Felipe Filipino","16/04/2006.5"), // Test 7
				Arguments.of("Felipe Filipino","-05/04/2006"), // Test 8
				Arguments.of("Felipe Filipino","33/04/2006"), // Test 9 - Falla porque no se controlan si los dias son mayores que 31
				Arguments.of("Felipe Filipino", "00/03/2006"), // Test 10
				Arguments.of("Felipe Filipino", "32/03/2006"), // Test 16 - Falla porque no se controlan si los dias son mayores que 31
				Arguments.of("Felipe Filipino", "16/-04/2006"), // Test 17
				Arguments.of("Felipe Filipino", "16/33/2006"), // Test 18
				Arguments.of("Felipe Filipino", "16/00/2006"), // Test 19
				Arguments.of("Felipe Filipino", "16/13/2006") // Test 24
			);
	}
	
	@ParameterizedTest
	@MethodSource("testOK")
	void testOK(String nombreApellidos, String fechaNac) {
		
		WebElement inputNombreApellidos = driver1.findElement(By.id("nomap"));
		inputNombreApellidos.sendKeys(nombreApellidos);
		
		WebElement inputFechaNac = driver1.findElement(By.id("fecha"));
		inputFechaNac.sendKeys(fechaNac);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement correcto = driver1.findElement(By.xpath("//h3"));
		boolean esCorrecto = correcto.isDisplayed();
		assertTrue(esCorrecto);

	}
	
	@ParameterizedTest
	@MethodSource("testFailA")
	void testFailA(String nombreApellidos, String fechaNac) {
		
		WebElement inputNombreApellidos = driver1.findElement(By.id("nomap"));
		inputNombreApellidos.sendKeys(nombreApellidos);
		
		WebElement inputFechaNac = driver1.findElement(By.id("fecha"));
		inputFechaNac.sendKeys(fechaNac);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement errorNumero = driver1.findElement(By.id("errorNomap"));
		boolean errorN = errorNumero.isDisplayed();
		assertTrue(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorFecha"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertFalse(errorD);
	}
	
	@ParameterizedTest
	@MethodSource("testFailB")
	void testFailB(String nombreApellidos, String fechaNac) {
		
		WebElement inputNombreApellidos = driver1.findElement(By.id("nomap"));
		inputNombreApellidos.sendKeys(nombreApellidos);
		
		WebElement inputFechaNac = driver1.findElement(By.id("fecha"));
		inputFechaNac.sendKeys(fechaNac);
		
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		
		WebElement errorNumero = driver1.findElement(By.id("errorNomap"));
		boolean errorN = errorNumero.isDisplayed();
		assertFalse(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorFecha"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertTrue(errorD);
	}
	
	/*
	@AfterAll
	static void exitDriver() {
		driver1.quit();
	}*/
}
