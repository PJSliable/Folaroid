from selenium import webdriver
from selenium.webdriver.common.by import By
import selenium
import time
import csv
import urllib
import urllib.request
import os

print(selenium.__version__)

imgtype = 'jpg'

if not os.path.exists('hash_tag_images'):
    os.mkdir('hash_tag_images')

f = open('hash_tag_with_image.csv','w', newline='')
wr = csv.writer(f)
wr.writerow(['hash_no','hash_name','hash_image_location'])

idx = 1
for i in range(1,21):

    driver = webdriver.Chrome()
    driver.get("https://www.codenary.co.kr/techstack/list?page={0}".format(i))
    time.sleep(10)

    elements = driver.find_elements(By.CLASS_NAME, 'card-short__title')

    print(i,"번째 페이지, 총", len(elements),'개의 요소')

    for element in elements:
        time.sleep(2)
        span_list = element.find_elements(By.TAG_NAME, 'span')

        src = span_list[0].find_element(By.TAG_NAME, 'img').get_attribute('src')
        print('src:',src)
        image_name = src.split('/')[-1]

        try:
            raw_img = urllib.request.urlopen(src).read()
            File = open(os.path.join('hash_tag_images/' + image_name), "wb")
            File.write(raw_img)
            File.close()
            print(idx, "succesfully downloaded")
        except:
            print('error')
            break

        s3_url = 'https://folaroid-image.s3.ap-northeast-2.amazonaws.com/' + image_name
        title = span_list[1].find_element(By.TAG_NAME, 'span').text

        print('idx:', idx, 'title:', title, 'image_name:', image_name)
        wr.writerow([idx, title, s3_url])
        idx += 1
    else:
        driver.close()
        continue

    driver.close()
    print('오류로 인해 종료하였습니다.')
    break
f.close()