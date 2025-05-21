package EMR.Unit_Clerk;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class D_FollowUp_Search_Then_Book_Appt {

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

		// In Dashboard Click on "FOLLOWUP DETAILS" Button

		// Switch to Product Page
		String currWindoeHanle = driver.getWindowHandle();
		driver.switchTo().window(currWindoeHanle);

		// Pauses for 3 second
		Thread.sleep(3000);

		// Locate Snack-Br (Toast Message) and Press Ok Button
		driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		// Locate FollowUP Details Button by Xpath
		driver.findElement(By.xpath("//button[normalize-space()='FollowUp Details']")).click();

		// Flow
		System.out.println("Step# 02: In Dashboard Click on 'FOLLOWUP DETAILS' Button ");

		// *********************FollowUP Details******************

		// Wait for page elements to stabilize
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//mat-paginator")));

//		==============Search Patient By Name Or NR No=============

		// Define search parameters with improved flexibility
		String patientName = ""; // Can be null if searching by MR only
		String targetMrNo = "0166988"; // Can be null if searching by name only
		boolean patientFound = false;

		// ________________________________________________________________
		// Select Payment Options
		// 1. Default = (PKR. 750)
		// 2. Adults = (PKR. 650)
		// 3. Zero = (PKR. 0)
		// 4. YPH = (PKR. 300)
		// 5. Peads = (PKR. 500)
		// 6. Service Charges = (PKR. 150)
		// 7. Discounted Fees = (PKR. 650)
		String Patient_Discount = "Adults";
		// ________________________________________________________________

		try {
			// Validate that at least one search parameter is provided
			if ((patientName == null || patientName.trim().isEmpty())
					&& (targetMrNo == null || targetMrNo.trim().isEmpty())) {
				throw new IllegalArgumentException("Either Patient Name or MR No must be provided.");
			}

			while (true) {
				// Wait for table to load with improved stability check
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));

				// Find all rows in the table with enhanced error handling
				List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
				if (rows.isEmpty()) {
					System.out.println("Warning: No patient records found on current page");
					break;
				}

				for (WebElement row : rows) {
					try {
						// Get name (2nd column) if provided
						boolean nameMatch = false;
						if (patientName != null && !patientName.trim().isEmpty()) {
							WebElement nameCell = row.findElement(By.xpath("./td[2]"));
							String name = nameCell.getText().trim();
							nameMatch = name.equalsIgnoreCase(patientName);
						}

						// Get MR No (3rd column) if provided
						boolean mrNoMatch = false;
						if (targetMrNo != null && !targetMrNo.trim().isEmpty()) {
							WebElement mrNoCell = row.findElement(By.xpath("./td[3]"));
							String mrNo = mrNoCell.getText().trim();
							mrNoMatch = mrNo.equals(targetMrNo);
						}

						// Check if either condition matches
						if ((patientName != null && !patientName.trim().isEmpty() && nameMatch)
								|| (targetMrNo != null && !targetMrNo.trim().isEmpty() && mrNoMatch)) {
							// Wait for button to be clickable before clicking
							WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(
									row.findElement(By.xpath(".//button[contains(@class, 'btn-RegNewPatient')]"))));
							bookButton.click();

							String successMessage = "Step# 03: Successfully searched patient";
							if (patientName != null && !patientName.trim().isEmpty()) {
								successMessage += " with Name: " + patientName;
							}
							if (targetMrNo != null && !targetMrNo.trim().isEmpty()) {
								successMessage += " with MR No: " + targetMrNo;
							}
							successMessage += " and proceeded to book appointment.";

							System.out.println(successMessage);
							patientFound = true;
							break;
						}
					} catch (Exception e) {
						System.out.println("Error processing row: " + e.getMessage());
						continue;
					}
				}

				if (patientFound) {
					break;
				}

				// Handle pagination with improved stability
				WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"/html/body/app-root/app-followup-appointment-details/div/div[2]/mat-paginator/div/div/div[2]/button[3]")));

				if (!nextButton.isEnabled() || nextButton.getDomAttribute("disabled") != null) {
					String notFoundMessage = "Step# 03: Patient not found";
					if (patientName != null && !patientName.trim().isEmpty()) {
						notFoundMessage += " with Name: " + patientName;
					}
					if (targetMrNo != null && !targetMrNo.trim().isEmpty()) {
						notFoundMessage += " with MR No: " + targetMrNo;
					}
					notFoundMessage += " in any page.";
					System.out.println(notFoundMessage);
					break;
				}

				// Click "Next" button and wait for page refresh
				nextButton.click();
				wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
				Thread.sleep(500); // Small buffer for page transition
			}
		} catch (Exception e) {
			System.out.println("FollowUp Details Error: " + e.getMessage());
			e.printStackTrace();
		}

		// ********************Search Doctor********************

		// Pauses for 2 second
		Thread.sleep(2000);

		// Specialities List dropdown trigger is Optional Field Locate By XPath
		WebElement SpecialitiesdownTrigger = driver.findElement(By.xpath(
				"/html/body/app-root/app-search-doctor/div/mat-card/form/div[1]/div[1]/mat-form-field/div/div[1]/div[3]/mat-select/div"));
		SpecialitiesdownTrigger.click();

		// Specialities List dropdown options Family Medicine Locate By XPath
		WebElement FamilyMedicine = driver.findElement(By.xpath("//span[@class='mat-option-text']"));
		FamilyMedicine.click();

		// Search Doctor by Specific Name "Dr. Sabeen Shah"
		driver.findElement(By.xpath(
				"/html/body/app-root/app-search-doctor/div/mat-card/form/div[1]/div[2]/mat-form-field/div/div[1]/div[3]/input"))
				.sendKeys("Dr. Sabeen Shah");

		// Click on Search Button Locate By XPath
		driver.findElement(By.xpath("//button[@class='btn-Search mr-4']")).click();

		// Flow
		System.out.println("Step# 04: Search Doctor by Specific Name");

		// *********************Select Doctor*******************

		Thread.sleep(1000);

		// Click on Select Button Locate By XPath
		driver.findElement(By.xpath("//button[@class='btn-Select select-css']")).click();

		// Flow
		System.out.println("Step# 05: Select Doctor by Specific Name");

		// ********************Select Schedule*******************

		Thread.sleep(1000);

		// Click on Select Schedule Button Locate By XPath
		driver.findElement(By.xpath(
				"/html/body/app-root/app-doctorschedule/div/mat-card/div/div[1]/mat-card/mat-card-actions/button"))
				.click();

		// Flow
		System.out.println("Step# 06: Select Schedule");

		// *********************Select Slot***********************

		Thread.sleep(1000);

		// Click on Select Slot Button Locate By XPath
		driver.findElement(By.xpath("//button[@class='mat-focus-indicator btn-Book mt-1 mat-button mat-button-base']"))
				.click();

		// Flow
		System.out.println("Step# 07: Select Slot");

		// **************Appointment Confirmation*******************

		Thread.sleep(1000);

		// Click on CONFIRM APPOINTMENT WITH PAY Button Locate By XPath
		driver.findElement(By.xpath("//button[@class='btn-ConfirmApp left']")).click();

		// Flow
		System.out.println("Step# 08: Confirm Appointment With Pay");

		// ***********************Payment***************************

		// Pauses for 2 second
		Thread.sleep(2000);

		// Locate Snack-Br (Toast Message) and Press Ok Button
		driver.findElement(By.xpath("//div[@aria-live='assertive']//div//simple-snack-bar//div//button")).click();

		// Discount dropdown trigger is Mandatory Field Locate By XPath
		WebElement DiscountDropdownTrigger = driver
				.findElement(By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select"));
		DiscountDropdownTrigger.click();

		// Discount selection based on Patient_Discount value
		switch (Patient_Discount.toLowerCase()) {
		case "default":
			WebElement defaultOption = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[2]")));
			defaultOption.click();
			break;

		case "adults":
			WebElement adults = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[3]")));
			adults.click();
			break;

		case "zero":
			WebElement zero = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[4]")));
			zero.click();
			break;

		case "yph":
			WebElement yph = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[5]")));
			yph.click();
			break;

		case "peads":
			WebElement peads = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[6]")));
			peads.click();
			break;

		case "service charges":
			WebElement serviceCharges = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[7]")));
			serviceCharges.click();
			break;

		case "discounted fees":
			WebElement discountedFees = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/table/tr[8]/td/select/option[8]")));
			discountedFees.click();
			break;

		default:
			System.out.println("Invalid discount option specified: " + Patient_Discount);
			break;
		}

		// Click on PAY Button Locate By XPath
		driver.findElement(By.xpath("/html/body/app-root/app-paymentsummary/div/mat-card/mat-card-actions/button[1]"))
				.click();

		// Flow
		System.out.println("Step# 09: Payment Confirmed With Option [ " + Patient_Discount + " ] ");

		// *****************Print Invoice Thermal***************
		//
//				// Click on PRINT INVOICE (Thermal) Button Locate By XPath
//				driver.findElement(By.xpath("/html/body/app-root/app-confirmpayment/div/mat-card/mat-card-actions/button[1]"))
//						.click();
		//
//				// Flow
//				System.out.println("Step# 10: Confirm Appointment Print With Thermal");

		// Pauses for 2 second
//				Thread.sleep(2000);
		//
//				// Switch to Main Window "Todays Appointment"
//				driver.navigate().to("https://emrunitclerk.virtualdoc.akdndhrc.org/#/emrfrontend/emrhome");

//				// Flow
//				System.out.println("Step# 11: Showing Patient Card In Main Screen");
//				System.out.println(" 👍 Happy Flow ദ്ദി˙ ᴗ ˙ )");

		// *****************Print Invoice (A5)***************

		// Click on PRINT INVOICE (Thermal) Button Locate By XPath
		driver.findElement(By.xpath("/html/body/app-root/app-confirmpayment/div/mat-card/mat-card-actions/button[2]"))
				.click();

		// Flow
		System.out.println("Step# 10: Confirm Appointment Print With (A5)");

		// Pauses for 2 second
		Thread.sleep(2000);

		// Click on Export to PDF Button Locate By XPath
		driver.findElement(By.xpath("/html/body/app-root/app-invoiceslip-a5/button")).click();

		// Flow
		System.out.println("Step# 11: FMHC Cash Slip in New Tab (A5) Size.");

		// Switch to Main Window "Todays Appointment"
		driver.navigate().to("https://emrunitclerk.virtualdoc.akdndhrc.org/#/emrfrontend/emrhome");

		// Flow
		System.out.println("Step# 12: Showing Patient Card In Main Screen");
		System.out.println(" 👍 Happy Flow ദ്ദി˙ ᴗ ˙ )");

	}

}
