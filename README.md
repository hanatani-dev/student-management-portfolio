# StudentManagement（受講生管理Webアプリ / Spring Boot × MyBatis × AWS構成）

受講生の情報を登録・編集・削除できる、教育現場向けのWebアプリケーションです。  
Java / Spring Boot を使用し、実務を想定したCRUD操作を実装しています。

---

## 🔧 使用技術

- Java 21  
- Spring Boot 3.2  
- MySQL  
- Thymeleaf
- MyBatis（XMLベースのMapperを使用）
- Spring MVC（REST API開発）
- Lombok（DTOやEntityで活用）
- JUnit 5 / Mockito  
- Gradle  
- Git / GitHub

---

## 📚 主な機能

| 機能カテゴリ         | 概要                                 |
|----------------------|--------------------------------------|
| 受講生管理機能       | 登録 / 一覧表示 / ID検索 / 条件検索 / 編集 / 論理削除 |
| ステータス管理       | 仮申込 / 本申込 / 受講中 / 受講終了 をコースごとに保持 |
| 検索機能             | 名前・性別・地域・削除済み など複数条件で検索可能 |
| テスト               | Service層のユニットテストを中心に実装（Mockito使用） |

---


---

## 🧪 テスト・動作確認の実施状況

アプリの品質と動作確認のため、以下のテスト・確認を実施しています。

### ✅ 単体テスト（JUnit / Mockito）

- Service層、Repository層、Converter層などを対象に33件のテストを実装
- Gradleで `./gradlew test` 実行し、HTMLレポートで結果確認（全件成功）
- テスト対象：登録・更新・検索・論理削除・例外ハンドリング など
<details>
<img src= "https://github.com/user-attachments/assets/956f4d45-9d3a-4293-b5b7-d642b371ac20" width="800" />
</details>

### ✅ MySQL からのデータ確認

- `statuses` や `students` `students_courses` に対し、手動でデータを挿入して検証
- 外部キー制約、日付データの整合性も含めてチェック済み

<pre>
sql
SELECT * FROM students_courses;
 </pre>


 
---
    
### ✅ Postman / Swagger でAPI動作確認
Swagger UIでAPI一覧を確認しながら実行可能

Postmanで実際のJSONを使って PUT /updateStudent を送信し、DBに反映されることを確認

GET /testException によるエラーハンドリングも検証済
<details>
<img src= "https://github.com/user-attachments/assets/39192a4e-4641-4e54-a106-745ec2342d4c" width="900" /> 
</details>
---

<details>
<summary>📁 ディレクトリ構成を見る</summary>
<pre>
src
├── main
│   ├── java
│   │   └── raisetech
│   │       └── StudentManagement
│   │           ├── config              // 起動設定用クラス（WAR対応など）
│   │           ├── controller          // APIエンドポイント
│   │           │   └── converter       // ドメイン⇔DTOの変換ロジック
│   │           ├── data                // DBエンティティ
│   │           ├── domain              // ビジネス用DTO（受講生詳細・検索条件など）
│   │           ├── exceptionHandler    // 共通エラーハンドラー
│   │           ├── repository          // MyBatisによるDBアクセス
│   │           ├── service             // ビジネスロジック
│   │           └── validaion           // バリデーショングループ（登録・更新用）
│   └── resources
│       ├── application.properties      // 本番・開発環境の設定
│       └── mapper
│           └── StudentRepository.xml   // MyBatisマッパー
├── test
│   ├── java
│   │   └── raisetech
│   │       └── StudentManagement
│   │           ├── controller          // Controller層の単体テスト
│   │           ├── converter           // Converter層の単体テスト
│   │           ├── repository          // MyBatisリポジトリテスト
│   │           └── service             // サービス層テスト（Mock注入）
│   └── resources
│       ├── application.properties      // H2用設定
│       ├── schema.sql                  // テスト用スキーマ定義
│       └── data.sql                    // テスト用初期データ
</details>

---

## 🚀 ローカルでの起動方法

1. プロジェクトを IntelliJ などで開く  
2. `src/main/resources/application.properties` を作成し、以下の内容を記述
<pre> 　properties 
    spring.datasource.url=jdbc:mysql://localhost:3306/student_db 
    spring.datasource.username=root spring.datasource.password=（セキュリティのため非公開） 
    spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true server.port=8080 
    </pre>
📌 パスワードなどの機密情報は .gitignore に含めるよう注意し、GitHub等に公開しないようにしています。


    
---


## 🔗 リンク

- 本リポジトリ：[https://github.com/hanatani-dev/student-management-portfolio](https://github.com/hanatani-dev/student-management-portfolio)

---

## 🙋‍♀️ 制作背景

病棟クラークとして3年勤務した経験から、業務における**「個人情報を正確・効率的に扱う」重要性**を痛感。  
その経験を活かし、教育現場の受講生管理業務を想定したアプリケーションとして本プロジェクトを開発しています。  
また、教育現場での受講生管理の煩雑さを減らし、「誰が・どのコースを・どのステータスで受講しているか」を把握しやすいように工夫しました。


---


## ☁️ AWS構成とデプロイへのこだわり

本アプリケーションは、AWSを使って本番運用を想定した構成でデプロイしています。

- 🔗 本番URL（ALB経由でEC2へルーティング）

http://StudentManagementALB-xxxxxxxx.ap-northeast-1.elb.amazonaws.com/studentList

---


## 🗂️ システム構成図（ユーザー視点）

ユーザーアクセスの流れを示した全体構成です。

<img width="350" height="350" alt="システム構成図" src="https://github.com/user-attachments/assets/bb1eb094-68b8-47af-9dca-b14713a297a3" />

---


## ✅ 使用AWSサービス


| サービス名 | 用途 |
|------------|------|
| EC2 | アプリのデプロイ先（Amazon Linux） |
| ALB | 外部からのトラフィックをEC2へ |
| RDS（MySQL） | アプリの本番用DBとして使用 |
| GitHub Actions | CI/CDでビルドとEC2への自動デプロイ |

---

## 💪 工夫＆苦労したポイント

- Elastic IPが使えない環境でも動くように、GitHub ActionsのYAMLを毎回手動でIP更新する工夫を実施

- .jar を scpでEC2に転送し、systemdで自動再起動できるよう構成

- RDSのデータベース名の大文字小文字の違いでAPIが動かず、EC2とローカルでDBを統一し直して再構築

- application.properties の接続情報は .gitignore とGitHub Secretsで厳重に管理
- 
## 💡 工夫ポイント　やりたかったこと・苦労した点（最後にまとめて）　

- **MyBatisの動的SQLを活用**し、検索条件に応じた柔軟なWHERE句を構築。
- **DTO設計**を意識し、`StudentDetail` や `StudentSearchCondition` でドメインロジックを分離。
- **パッケージ構成を明確化**し、controller / service / domain / repository を役割ごとに整理。
- **ステータス管理機能**により、受講生の進捗状況を可視化。
- テストでは**依存注入・モック**を使用し、ユニットテストとして完結する設計に。

---

## 🔁 GitHub Actions（CI/CD）

- ./gradlew bootJar でビルド
- SCPでEC2へJar転送（秘密鍵はGitHub Secrets管理）
- EC2内で systemctl restart StudentManagement.service を実行

## 一部抜粋

<pre> 
JavaTest.yml 
    
  name: SSH Application Deploy
  uses: appleboy/ssh-action@master
  with:
    host: ${{ env.EC2_HOST }}
    username: ${{ env.EC2_USER }}
    key: ${{ secrets.AWS_EC2_PRIVATE_KEY }}
    script: |
      sudo systemctl restart StudentManagement
 </pre>

---

## 🧠 こんな人に届けたい

- Java + Spring Boot で**実務に近いWebアプリを作ってみたい**学習者さん
 
- **GitHub Actions × EC2 × RDS でCI/CD環境を体験してみたい**人

- 実務に近い構成（プロパティ管理・systemd再起動など）を**手を動かして学びたい**人

---

## 🗒️ まとめ
このプロジェクトでは、Javaのバックエンド開発だけでなく、本番運用を想定したAWS構築や自動デプロイも実践しました。
ただコードを書くのではなく、「どうやって人に届けるか・継続運用するか」に向き合った経験を込めています🌱

