
<div align="center">
  <img src=https://github.com/user-attachments/assets/107d3693-2918-4339-9ad5-019d593762d5 alt='logo' width='600px' />
  <h1>
    TripMingle 여행 동행 서비스
  </h1>
</div>

## 목차

- [1. 프로젝트 개요](#1-프로젝트-개요)
  - [1-1. 프로젝트 소개](#1-1-프로젝트-소개)
  - [1-2. 시스템 구성도](#1-2-시스템-구성도)
  - [1-3. 주요 기능](#1-3-주요-기능)
  - [1-4. 개발 환경](#1-4-개발-환경)
- [2. 프로젝트 설계](#2-프로젝트-설계)
  - [2-1. 헥사고날 아키텍처](#2-1-헥사고날-아키텍처)
  - [2-2. ERD 다이어그램](#2-2-ERD-다이어그램)
  - [2-3. UseCase 다이어그램](#2-3-UseCase-다이어그램)
- [3. 개발 과정](#3-개발-과정)
- [4. 프로젝트 관리](#4-프로젝트-관리)
  - [4-1. Jira ticket을 활용한 task 관리](#4-1-Jira)
  - [4-2. API 서버 개발 및 API 문서화/테스트](#4-2-Swagger-Postman)

## 1. 프로젝트 개요

### 1-1. 프로젝트 소개

TripMingle은 글로벌 여행 동행 서비스로, 여행 동행 찾기, 여행 일정 관리, 여행 동행 매칭 및 추천 서비스, 여행 동행 채팅 서비스를 제공합니다.



### 1-2. 시스템 구성도
![structure.png](./image/structure.png)

### 1-3. 주요 기능
![image](https://github.com/user-attachments/assets/2b777c75-9c49-48bc-a6c6-c35a3da83e13)


### 1-4. 개발 환경

- Front-end: `TypeScript`, `React.js`, `NextJs14`, `zustand`, `vanilla-extract`, `pnpm`
- Back-end: <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=Spring Data JPA&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=MongoDB&logoColor=white"/> <img src="https://img.shields.io/badge/EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white"/> <img src="https://img.shields.io/badge/AWS OpenSearch-005EB8?style=flat-square&logo=OpenSearch&logoColor=white"/> <img src="https://img.shields.io/badge/S3-569A31?style=flat-square&logo=Amazon S3&logoColor=white"/>


## 2. 프로젝트 설계

### 2-1. 헥사고날 아키텍처
<div>
  <p align="center">
    <img src="./image/Hexagonal_Architecture2.png" width="800">
  </p>
  <p align="center">
    <img src="./image/Hexagonal_Architecture1.png" width="800">
  </p> 
</div>

### 2-2. ERD 다이어그램
<p align="center">
  <img src="https://github.com/user-attachments/assets/5ee18433-5900-4597-95bf-599caf378210" width="1000" height="665"/>
</p>

### 2-3. UseCase 다이어그램
#### 동행게시판
<p align="center">
  <img src="https://github.com/user-attachments/assets/0106f4ec-76ad-41c6-a3b0-707eb13df502" alt="동행게시판_유즈케이스" width="700" />
</p>

#### 정보게시판 & 마이페이지
![정보게시판_유즈케이스_수정본](https://github.com/user-attachments/assets/b9a5ee93-5508-4e02-b5be-78ebb7cfe79c) |![마이페이지_유즈케이스_수정본](https://github.com/user-attachments/assets/1b589c3e-6664-40fc-8bab-380b10c93c01)
--|--|

## 3. 개발 과정

## 4. 프로젝트 관리

### 4-1. Jira ticket을 활용한 task 관리

효율적인 협업과 팀원간의 빠른 피드백을 통해 변화에 신속하게 대응하기 위해 개발 프로세스에는 스크럼 방식을 채택하였습니다.

![burn_down_chart.png](./image/burn_down_chart.png)
 
 번다운 차트 등의 지표를 확인할 수 있고, task의 세부 조정이 가능한 Jira를 통해 task를 관리하였습니다.

![jira_ticket.png](./image/jira_ticket.png)
![github_pr.png](./image/github_pr.png)

 Jira의 티켓 번호와 GitHub의 커밋 및 PR을 연동시켜, 작업 내역을 추적하고 효율적인 협업을 도모하였습니다.
### 4-2. API 서버 개발 및 API 문서화/테스트
백엔드 API 서버 개발은 Java와 Spring Boot 기반으로 진행하였으며, OpenAPI Spec에 맞는 RESTful한 API를 개발하였습니다.
<br />
API 서버 개발은 Swagger와 Postman을 통해 문서화 및 테스트를 진행하였습니다.
#### Swagger
![리드미_첨부_스웨거](https://github.com/user-attachments/assets/67f8353f-a0c7-40e2-9b57-f3b28c369671)
OpenAPI 2.5 Spec을 준수하는 Swagger를 사용하여 API 문서화를 했습니다.
#### Postman
<img width="1275" alt="리드미_첨부_포스트맨" src="https://github.com/user-attachments/assets/2c34501f-bf0a-446a-bfc8-561934cad6f5">
API Platform인 Postman을 이용하여, 개발 환경과 배포 환경 모두 API 테스팅을 할 수 있도록 구성하였습니다.

