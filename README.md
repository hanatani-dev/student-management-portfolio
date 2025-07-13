# デプロイ確認用の更新です（2025/07/13）

# StudentManagement

受講生の情報を登録・編集・削除できる、教育現場向けのWebアプリケーションです。  
Java / Spring Boot を使用し、実務を想定したCRUD操作を実装しています。

---

## 🔧 使用技術

- Java 21  
- Spring Boot 3.2  
- MySQL  
- JPA (Hibernate)  
- Thymeleaf  
- JUnit 5 / Mockito  
- Gradle  
- Git / GitHub

---

## 📚 主な機能

| 機能カテゴリ         | 概要                                 |
|----------------------|--------------------------------------|
| 受講生管理機能       | 新規登録 / 一覧表示 / 編集 / 削除     |
| REST API             | Spring Bootによるエンドポイント構成 |
| 永続化               | JPA（Hibernate）によるDB操作         |
| テスト               | Service層を中心にJUnit＋Mockitoで実装 |

---

## 💡 工夫ポイント

- 実際の業務でよくある「CRUD構成」「Service層分離」「DTOの活用」を意識。
- パッケージ構成を分かりやすく整理し、将来的な機能追加にも対応しやすい設計。
- テストコードは、依存注入とMockを使って**ユニットテストとして完結するよう設計**。

---

<details>
<summary>📁 ディレクトリ構成を見る</summary>

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── studentmanagement
│   │               ├── controller
│   │               ├── service
│   │               ├── repository
│   │               └── entity
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── example
                └── studentmanagement
                    └── service
```

</details>

---

## 🚀 ローカルでの起動方法

1. プロジェクトを IntelliJ などで開く  
2. `src/main/resources/application.properties` を作成し、以下の内容を記述
<pre> ```properties 
    spring.datasource.url=jdbc:mysql://localhost:3306/student_db 
    spring.datasource.username=root spring.datasource.password=（セキュリティのため非公開） 
    spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true server.port=8080 
    
    ``` </pre>
📌 パスワードなどの機密情報は .gitignore に含めるよう注意し、GitHub等に公開しないようにしています。


    
---


## 🔗 リンク

- 本リポジトリ：[https://github.com/hanatani-dev/student-management-portfolio](https://github.com/hanatani-dev/student-management-portfolio)

---

## 🙋‍♀️ 制作背景

病棟クラークとして3年勤務した経験から、業務における**「個人情報を正確・効率的に扱う」重要性**を痛感。  
その経験を活かし、教育現場の受講生管理業務を想定したアプリケーションとして本プロジェクトを開発しています。

