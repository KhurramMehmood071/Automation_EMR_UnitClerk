package EMR.Unit_Clerk;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class G_Cancel_Appt_In_Tentative {

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

		// In Dashboard Click on "Tentative Appointments" Button

		// Switch to Product Page
		String currWindoeHanle = driver.getWindowHandle();
		driver.switchTo().window(currWindoeHanle);

		// Pauses for 2 second
		Thread.sleep(2000);

		// Locate Snack-Br (Toast Message) and Press Ok Button
//		driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		// Locate Tentative Appointments Button by Xpath
		driver.findElement(By.xpath("//button[normalize-space()='TENTATIVE APPOINTMENTS']")).click();

		// Flow
		System.out.println("Step# 02: In Dashboard Click on 'TENTATIVE APPOINTMENTS' Button ");

		// **************TENTATIVE APPOINTMENTS*******************

		// Flow
		System.out.println("                Loading... Please Wait");

		// Pauses for 9 seconds
		Thread.sleep(9000);

		// Locate Snack-Br (Toast Message) and Press Ok Button
//				driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		try {

			// In Main Screen to find Patient and Cancel Appt.

			// Define search criteria
			String Patient_searchByName = ""; // Patient_Name
			String Patient_searchByMRNo = ""; // Patient_MR_No
			String Patient_searchByAKUMRNo = "100-04-05"; // Patient_AKU_MR_No

			// Pauses for 3 seconds
			Thread.sleep(3000);

			// Define XPaths
			String patientNameXPath = ".//table//th[contains(text(), 'Patient Name')]/following-sibling::td";
			String mrNoXPath = ".//table//th[contains(text(), 'MR No')]/following-sibling::td";
			String akuMRNoXPath = ".//table//th[contains(text(), 'AKU MR No')]/following-sibling::td";
			String cancelButtonXPath = "/html/body/app-root/app-tentative-appointments/div/div[3]/div[1]/mat-card/div[3]/div[2]/button[2]";

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
					System.out.println("Step# 03: Search Patient By Name, or MR_No, or AKU_Mr_No.");
					System.out.println("Step# 04: Appointment Cancelled for Patient: " + patientName);
					break; // Stop after first match
				}
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		// Pauses for 5 seconds
		Thread.sleep(5000);

		// Switch to Main Window "Tentative Appointments"
		driver.navigate().to("https://emrunitclerk.virtualdoc.akdndhrc.org/#/emrfrontend/tentative");

		// Pauses for 5 seconds
		Thread.sleep(5000);

		// Flow
		System.out.println("Step# 05: Showing 'Tentative Appointments' Updated Screen");
		System.out.println("              👍 Happy Flow ദ്ദി˙ ᴗ ˙ )");

	}

}
