import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service as ChromeService
from webdriver_manager.chrome import ChromeDriverManager


@pytest.fixture
def driver():
    # Set up the WebDriver instance
    driver = webdriver.Chrome(service=ChromeService(ChromeDriverManager().install()))
    yield driver
    driver.quit()


def test_saucedemo(driver):
    driver.get("https://www.saucedemo.com/")

    driver.maximize_window()

    page_source = driver.page_source
    assert "Swag Labs" in page_source, "The page source does not contain 'Swag Labs'"

    username = driver.find_element(By.ID, "user-name")
    username.send_keys("standard_user")
    password = driver.find_element(By.ID, "password")
    password.send_keys("secret_sauce")
    login_button = driver.find_element(By.ID, "login-button")
    login_button.click()

    add_to_cart_button = driver.find_element(By.ID, "add-to-cart-sauce-labs-backpack")
    add_to_cart_button.click()

    # Verify the item is added to the cart
    cart_button = driver.find_element(By.ID, "shopping_cart_container")
    assert "1" in cart_button.text, "Item was not added to the cart"

    # Click on the cart button
    cart_button.click()

    driver.find_element(By.ID, "react-burger-menu-btn").click()

    driver.find_element(By.ID, "logout_sidebar_link").click()

    # Clean up
    driver.quit()


if __name__ == "__main__":
    pytest.main()
