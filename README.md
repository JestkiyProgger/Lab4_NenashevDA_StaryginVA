Практическая работа №4 Java Message Service


Выполнили студенты 6132-010402D Ненашев Данила, Старыгин Виталий

```
IntelliJ IDEA Ultimate

OpenJDK 17.0.15
```

```
Предметная область - система управления задачами

Сущности: пользователь (User), задача (Task)
```

СУБД PostgreSQL

Скрипт для создания и заполнения базы данных:
```
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INTEGER NOT NULL CHECK (age >= 18 AND age <= 75)
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO users (name, email, age) VALUES 
('Старыгин Виталий', 'vs@mail.ru', 22),
('Ненашев Данила', 'nd@gmail.com', 22),
('Иванов Иван', 'ii@yandex.ru', 18),
('Петров Петр', 'pp@inbox.ru', 75);

INSERT INTO tasks (title, user_id, created_date, completed) VALUES 
('Написать код программы', 1, '2025-10-20', false),
('Протестировать код программы', 2, '2025-10-21', false),
('Написать фидбек', 2, '2025-10-22', false),
('Написать README', 3, '2025-10-23', false);
```

# Результаты запросов в Postman:

## 1. Создание User:
<img width="1280" height="439" alt="image" src="https://github.com/user-attachments/assets/86730803-89f0-43e6-b667-dc2c444c3cb4" />


<img width="829" height="272" alt="image" src="https://github.com/user-attachments/assets/fbc1f7c3-dd4b-4991-aeda-a99d17001153" />


<img width="1280" height="428" alt="image" src="https://github.com/user-attachments/assets/c0ff224f-bcce-4b81-a868-08d13b8bc1f4" />


<img width="803" height="252" alt="image" src="https://github.com/user-attachments/assets/37644591-55f0-4ab8-b93b-9f61e86ee4e9" />


## 2. Изменение User:
<img width="738" height="479" alt="image" src="https://github.com/user-attachments/assets/a36d30bd-20b5-48cf-9218-c1b41c600ec8" />


<img width="765" height="264" alt="image" src="https://github.com/user-attachments/assets/3bbbb246-8676-4677-bcb7-a79a2f83078f" />


<img width="1280" height="414" alt="image" src="https://github.com/user-attachments/assets/9f8126d6-cf10-47e8-ab8e-1b050b0d403e" />


<img width="797" height="264" alt="image" src="https://github.com/user-attachments/assets/39f43f56-d58b-4676-9950-953288ecb63f" />


## 3. Удаление User:
<img width="1280" height="321" alt="image" src="https://github.com/user-attachments/assets/2ca68cff-73f0-4bac-94ef-ceb9f7f3ada2" />


<img width="769" height="246" alt="image" src="https://github.com/user-attachments/assets/591d77d4-d462-4976-8cc5-8fbf3c6c92ea" />


<img width="1280" height="315" alt="image" src="https://github.com/user-attachments/assets/7ffb9ca4-772a-4880-abc9-842755a61fea" />


<img width="788" height="258" alt="image" src="https://github.com/user-attachments/assets/264cc513-ca6b-48f1-bfca-5f4d5ce406d8" />


## 4. Добавление Task:
<img width="1280" height="401" alt="image" src="https://github.com/user-attachments/assets/2ab588ad-b7d7-4c09-9178-ed389931a1af" />


<img width="435" height="256" alt="image" src="https://github.com/user-attachments/assets/b0659564-53e7-48ef-8930-b0c7cef9026c" />


<img width="1280" height="429" alt="image" src="https://github.com/user-attachments/assets/c40d18e8-7197-40d7-b072-863073f7a177" />


<img width="434" height="252" alt="image" src="https://github.com/user-attachments/assets/b67cae4f-c810-4ac9-82b8-a492919e3a18" />


## 5. Изменение Task:
<img width="1280" height="437" alt="image" src="https://github.com/user-attachments/assets/9582cf88-80db-4b5d-a8e1-d57426251a7f" />


<img width="425" height="251" alt="image" src="https://github.com/user-attachments/assets/d327d55d-2504-4653-859f-eaace9e8d1d5" />


<img width="1280" height="436" alt="image" src="https://github.com/user-attachments/assets/55c59c61-4bc0-49ce-9921-b25f76b3f5f9" />


<img width="487" height="265" alt="image" src="https://github.com/user-attachments/assets/a432b712-e796-4653-b68c-808ea062e02a" />


## 6. Удаление Task:
<img width="1280" height="306" alt="image" src="https://github.com/user-attachments/assets/0babed21-0675-41a2-beb5-c34762539d83" />


<img width="448" height="267" alt="image" src="https://github.com/user-attachments/assets/35681af3-7437-4639-a177-0b25e2f92050" />


<img width="1280" height="317" alt="image" src="https://github.com/user-attachments/assets/ef20439e-e842-4663-827a-86d01893b631" />


<img width="450" height="256" alt="image" src="https://github.com/user-attachments/assets/b6b2919f-2004-4819-8ae2-f55d14569d7c" />


## 7. Результаты в БД:
<img width="805" height="344" alt="image" src="https://github.com/user-attachments/assets/e7c78be5-b241-4cd1-b4bf-c9764dfd787b" />
