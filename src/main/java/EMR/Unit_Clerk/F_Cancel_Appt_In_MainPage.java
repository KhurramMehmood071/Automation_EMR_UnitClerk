package EMR.Unit_Clerk;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class F_Cancel_Appt_In_MainPage {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// ******************Launch Browser*****************

		// Launch Chrome Browser
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\AKDNdHRC\\Selenium\\Training Sessions\\Session 01\\MavenLoginTest\\Login\\chromedriver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		// Explicit Wait for 60 Seconds [Specific WebElement]
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		// Wait 10 Seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

		// **********************Open Browser****************

		// Browser Open in Maximise Window
		driver.manage().window().maximize();

		// Open URL EMR Staging Site (Login Page)
		driver.get("https://emrunitclerk.virtualdoc.akdndhrc.org/#/emrfrontend/");

		// *********************Login Page********************

		// Locate User name By CSS Locator -- [tag#id] -- CSS Locator # 01
		driver.findElement(By.cssSelector("input#mat-input-0")).sendKeys("asher.hussnain@aku.edu");

		// Locate Password by By CSS Locator -- (tag[attribute=value]) -- CSS Locator #
		// 03
		driver.findElement(By.cssSelector("input[type=password]")).sendKeys("asher12");

		// Locate Login Button by CSS Locator using [tag.value of class name] -- CSS
		// Locator # 02
		driver.findElement(By.cssSelector("button.btn-Signin")).click();

		// Flow
		System.out.println("Step# 01: Successfully Login the EMR Unit Clerk App.");

		// *******************Dashboard************************

		// Pauses for 2 second
		Thread.sleep(2000);

		// Locate Snack-Br (Toast Message) and Press Ok Button
		driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		try {
			
			// In Main Screen to find Patient and Cancel Appt.

			// Define search criteria
			String Patient_searchByName = "";
			String Patient_searchByMRNo = "1010190";
			String Patient_searchByAKUMRNo = "";

			// Define XPaths
			String patientNameXPath = ".//table//th[contains(text(), 'Patient Name')]/following-sibling::td";
			String mrNoXPath = ".//table//th[contains(text(), 'MR No')]/following-sibling::td";
			String akuMRNoXPath = ".//table//th[contains(text(), 'AKU MR No')]/following-sibling::td";
			String cancelButtonXPath = ".//button[contains(@class, 'btn-Appointment')]";

			// Find all patient cards
			List<WebElement> patientCards = driver.findElements(By.xpath("//mat-card[contains(@class, 'gridBorder')]"));

			// Loop through cards to find a match
			for (WebElement card : patientCards) {
				String patientName = card.findElement(By.xpath(patientNameXPath)).getText().trim();
				String mrNo = card.findElement(By.xpath(mrNoXPath)).getText().trim();
				String akuMRNo = card.findElement(By.xpath(akuMRNoXPath)).getText().trim();

				// Check if this card matches any search criteria
				if (patientName.equalsIgnoreCase(Patient_searchByName) || mrNo.equalsIgnoreCase(Patient_searchByMRNo)
						|| akuMRNo.equalsIgnoreCase(Patient_searchByAKUMRNo)) {

					// Click the cancel button
					WebElement cancelButton = wait.until(
							ExpectedConditions.elementToBeClickable(card.findElement(By.xpath(cancelButtonXPath))));
					cancelButton.click();
					System.out.println("Step# 02: Search Patient By Name, or MR_No, or AKU_Mr_No.");
					System.out.println("Step# 03: Appointment Cancelled for: " + patientName);
					break; // Stop after first match
				}
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		// *******************Cancel Appointment************************

		// Reason For Cancel Appointment
		String Cancel_Appt_Reason = "Dr. Not Available";

		System.out.println("Step# 04: Confirmed For Cancel Appointment.");

		// Click the Cancel Appointment button
		WebElement cancelApptButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn-cancel']")));
		cancelApptButton.click();

		// Input Reason for Cancel Appointment
		WebElement Reason = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@formcontrolname='cancellationReason' and contains(@class, 'mat-input-element')]")));
		Reason.clear();
		Reason.sendKeys(Cancel_Appt_Reason);

		System.out.println("Step# 05: Enter Reason For Cancel Appointment.");

		// Click on Submit Button
		WebElement Submit = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(@class, 'btn-Appointment') and .//span[text()='Submit']]")));
		Submit.click();

		// Pauses for 4 second
		Thread.sleep(1000);

		System.out.println("Step# 06: Appointment Cancelled.");

		driver.navigate().refresh(); // Refresh the page

		// Locate Snack-Br (Toast Message) and Press Ok Button
	//	driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		System.out.println("Step# 07: Return to Main Screen.");
		System.out.println(" 👍 Happy Flow ദ്ദി˙ ᴗ ˙ )");

	}

}
