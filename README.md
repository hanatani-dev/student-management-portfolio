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

---

## ☁️ AWS構成とデプロイへのこだわり

本アプリケーションは、AWSを使って本番運用を想定した構成でデプロイしています。

- 🔗 本番URL（ALB経由でEC2へルーティング）

http://StudentManagementALB-xxxxxxxx.ap-northeast-1.elb.amazonaws.com/studentList

---

## 🏗️ インフラ構成図（イメージ）

  [GitHub Actions] ──▶ build & deploy
        │
        ▼
  [SCP/SSH経由で.jarを転送]
        │
        ▼
┌────────────────────┐
│   EC2 (Amazon Linux) │
│  - Java実行環境       │
│  - systemdでjar管理   │
└────────────────────┘
        ▲
        │
      ALB
        ▲
        │
    Route53（ドメイン未使用）
※図はGitHubのREADMEに画像で載せるのがオススメ！
図解ツール（例：draw.io）で作って、/docs/aws-diagram.pngなどに保存してREADMEから参照するといいよ✨

## ✅ 使用AWSサービス

サービス名	用途
EC2	アプリのデプロイ先（Amazon Linux）
ALB	外部からのトラフィックをEC2へ
RDS（MySQL）	アプリの本番用DBとして使用
GitHub Actions	CI/CDでビルドとEC2への自動デプロイ

---

## 💪 工夫＆苦労したポイント

Elastic IPが使えない環境でも動くように、GitHub ActionsのYAMLを毎回手動でIP更新する工夫を実施

.jar を scpでEC2に転送し、systemdで自動再起動できるよう構成

RDSのデータベース名の大文字小文字の違いでAPIが動かず、EC2とローカルでDBを統一し直して再構築

application.properties の接続情報は .gitignore とGitHub Secretsで厳重に管理

---

## 🔁 GitHub Actions（CI/CD）

- ./gradlew bootJar でビルド
- SCPでEC2へJar転送（秘密鍵はGitHub Secrets管理）
- EC2内で systemctl restart StudentManagement.service を実行

# 一部抜粋
- name: SSH Application Deploy
  uses: appleboy/ssh-action@master
  with:
    host: ${{ env.EC2_HOST }}
    username: ${{ env.EC2_USER }}
    key: ${{ secrets.AWS_EC2_PRIVATE_KEY }}
    script: |
      sudo systemctl restart StudentManagement

---

## 🧠 こんな人に届けたい
Java + Spring Bootの学習者

AWSでJavaアプリを本番運用してみたい人

EC2 + RDS + GitHub Actions でのデプロイ構成を実践したい人

---

## 🗒️ まとめ
このプロジェクトでは、Javaのバックエンド開発だけでなく、本番運用を想定したAWS構築や自動デプロイも実践しました。
ただコードを書くのではなく、「どうやって人に届けるか・継続運用するか」に向き合った経験を込めています🌱

