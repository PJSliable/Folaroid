from selenium import webdriver
from selenium.webdriver.common.by import By
import selenium
import time
import csv

print(selenium.__version__)


driver = webdriver.Chrome()

driver.get("https://github.com/toptal/gitignore/tree/master/templates")
time.sleep(10)
# driver.implicitly_wait(10)

title_set = set()

elements = driver.find_elements(By.CLASS_NAME, 'Box-row--focus-gray')

print(len(elements))

# i = 0
for element in elements:
    time.sleep(2)
    word = element.find_element(By.CLASS_NAME, 'js-navigation-open').text
    title, type = word.split('.')[0], word.split('.')[-1]
    if type == 'gitignore':

        # i += 1
        # if i > 10:
        #     break
        print(title)
        title_set.add(title)
title_list = sorted(list(title_set))

f = open('hash_tag.csv','w', newline='')
wr = csv.writer(f)
wr.writerow(['hash_no','hash_name'])

for idx , title in enumerate(title_list, start=1):
    wr.writerow([idx,title])
f.close()
