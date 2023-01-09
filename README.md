# HawkTalk Project

### 소켓과 MySQL을 사용한 채팅 프로그램

---

## 프로젝트 개요

- Java Swing 환경의 텍스트 채팅 애플리케이션으로, Socket 통신과 Multi Thread 프로그래밍으로 구현한 채팅 프로그램
- 그룹 채팅 및 실시간 그림판 기능을 제공하며 MySQL을 이용하여 유저 정보를 데이터베이스에 저장 및 수정, 삭제가 가능한 프로그램

---

## 참여 인원

조장 : 이승호(B989037, UI 환경 구축 및 크롤링을 이용한 실시간 날씨 정보 확인 기능과 스레드를 이용한 채팅 기능 구현)

조원 : 권덕재(B989003, 서버와 MySQL을 이용한 DB 구축 및 스레드를 이용한 채팅방과 실시간 온라인 그림판 구현)

---

## 주요 기능

- [로그인/로그아웃 기능, 사용자 등록/수정/삭제](#로그인로그아웃-기능-사용자-등록수정삭제)
- [온라인 유저 목록 확인 기능](#온라인-유저-목록-확인-기능)
- [온라인 채팅 기능](#온라인-채팅-기능)
- [그룹 채팅 기능](#그룹-채팅-기능)
- [온라인 그림판 기능](#온라인-그림판-기능)
- [Jsoup을 사용한 실시간 날씨 크롤링 기능](#jsoup을-사용한-실시간-날씨-크롤링-기능)

---

## 프로그램 구성도

![Untitled](https://user-images.githubusercontent.com/112773313/209364871-7b074d59-5833-4ecc-9b21-b9579e868654.png)

---

## 실행시 주의사항
-   jdk 17 버전으로 작성되었으며 mysql connector 라이브러리와 jsoup 라이브러리가 필요함
-   다른 ip의 서버에 접속하기 위해 `LogInLayout` 클래스에 있는 `ipAddress` 를 `localhost` 에서 서버의 IP주소로 수정하여 사용해야 하며 같은 wifi를 사용하고 있어야 함
-   MySQL 관련 설정이 필요함 (`usersDB` 스키마에 `usertable` 테이블이 있어야 하며 root 유저의 비밀번호가 "admin"이 아닌 경우 DB 패키지의 `JDBCconnector` 클래스에서 변경해야 함)
-   usertable의 구조는 다음과 같음

| Name | ClassNumber | NickName | ID | Pwd |
| --- | --- | --- | --- | --- |
| varchar(10) | varchar(10) | varchar(10) | varchar(10) PK | varchar(10) |

---

## **실행화면과 기능설명**

### 로그인/로그아웃 기능, 사용자 등록/수정/삭제

- `JDBCconnector` 클래스를 이용하여 `MySQL` DB 접속을 통해 사용자의 정보를 저장 및 관리한다.

![Untitled 1](https://user-images.githubusercontent.com/112773313/209364647-d0f8ef45-3301-4f68-ad13-580a04fdac48.png)
![Untitled 2](https://user-images.githubusercontent.com/112773313/209364673-e8eef56b-34d1-4eff-9132-44820ac89d60.png)

### 온라인 유저 목록 확인 기능

- 클라이언트가 접속하면 `ServerBack` 클래스 에 있는 `clientThreadList` 에 해당 스레드를 추가한다.
- 그 후 `ClientBack` 클래스 에서 접속한 클라이언트의 닉네임을 서버로 전송한다.
- `ServerBack` 클래스가 받은 닉네임은 `nickNameList` 에 추가하고 접속중인 모든 클라이언트의 유저목록을 Clear한 후 다시 `ServerBack` 클래스 에 있는  `nickNameList` 에 있는 모든 닉네임을 추가한다.

![Untitled 3](https://user-images.githubusercontent.com/112773313/209364688-b5278eb6-fbec-4238-9d33-731394a40c2c.png)

### 온라인 채팅 기능

- 입력창에 보낼 메세지를 입력하고 전송버튼을 누르거나 엔터를 입력하면 해당 메세지는 서버로 전송된다.
- 메세지를 받은 서버는 모든 클라이언트에게 보낸 유저와 메세지 내용을 전달한다.

![Untitled 4](https://user-images.githubusercontent.com/112773313/209364714-17911bd2-6a56-417d-b85d-50ac2f8fc781.png)

### 그룹 채팅 기능

- 채팅방을 만들면 `ServerBack` 클래스 의 `roomMap` 에 채팅방의 이름과 새로운 포트번호를 갖는 `ServerBack` 클래스를 추가한다.
- 각 채팅방은 각각의 `ServerBack` 클래스와 통신한다.

![Untitled 5](https://user-images.githubusercontent.com/112773313/209364726-6abdf437-ff1d-4038-aaaf-3be99695277d.png)

### 온라인 그림판 기능

- 채팅방에서 그림을 그리면 해당 좌표를 명령어와 함께 서버로 보낸다.
- 서버는 해당 좌표와 명령어를 채팅방에 있는 모든 유저들의  `GroupChatBack` 클래스로 보낸 후 해당 좌표에 실시간으로 그린다.

![Untitled 6](https://user-images.githubusercontent.com/112773313/209364742-094d144d-0931-45a0-960d-47dd219a6cf5.png)

### Jsoup을 사용한 실시간 날씨 크롤링 기능

- 햇님모양 버튼을 누르면 [https://weather.naver.com/today](https://weather.naver.com/today) 사이트의 HTML정보를 크롤링하여 날씨 정보를 추출한다.

![Untitled 7](https://user-images.githubusercontent.com/112773313/209364756-aaecbdae-9b52-4a62-89c4-5f2ca184ef84.png)

## 기타 사항

지도교수 : 유동영 교수님

개발환경 : <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"> <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=Visual Studio Code&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">

사용 라이브러리 : jsoup-1.15.3.jar, mysql-connector-j-8.0.31.jar

제작기간 : 약 4주(18시간 ＋ 개인 개발 시간)

## 참고

[https://recipes4dev.tistory.com/153](https://recipes4dev.tistory.com/153)

[https://lanace.blogspot.com/2017/08/intellij-swing-1.html](https://lanace.blogspot.com/2017/08/intellij-swing-1.html)
