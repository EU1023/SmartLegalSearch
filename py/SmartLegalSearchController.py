import requests

# 發送 GET 請求
url = "http://localhost:8080/quiz/questions"
response = requests.get(url)

if response.status_code == 200:
    questions = response.json()
    print("Questions:", questions)
else:
    print("Failed to fetch questions. Status Code:", response.status_code)


# 發送 POST 請求
url = "http://localhost:8080/quiz/answer"
data = {
    "question": "What is 2 + 2?",
    "answer": "4"
}
response = requests.post(url, json=data)

if response.status_code == 200:
    print("Response:", response.text)
else:
    print("Failed to submit answer. Status Code:", response.status_code)
