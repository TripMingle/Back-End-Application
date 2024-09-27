

## 목차

- [1. 프로젝트 개요](#1-프로젝트-개요)
  - [1-1. 프로젝트 소개](#1-1-프로젝트-소개)
  - [1-2. 시스템 구성도](#1-2-시스템-구성도)
  - [1-3. 주요 기능](#1-3-주요-기능)
  - [1-4. 개발 환경](#1-4-개발-환경)
- [2. 프로젝트 설계](#2-프로젝트-설계)
  - [2-1. 헥사고날 아키텍처](#2-1-헥사고날-아키텍처)
- [3. 개발 과정](#3-개발-과정)
- [4. 프로젝트 관리](#4-프로젝트-관리)

## 1. 프로젝트 개요

### 1-1. 프로젝트 소개


<div align="center">
  <img src=https://github.com/user-attachments/assets/107d3693-2918-4339-9ad5-019d593762d5 alt='logo' width='600px' />
  <h1>
    TripMingle 여행 동행 서비스
  </h1>
  <p>
    Tripmingle은 글로벌 여행 동행 서비스로,
</br>
  여행 동행 찾기, 여행 일정 관리, 여행 동행 매칭 및 추천 기능을 제공합니다.
  </p>
</div>


### 1-2. 시스템 구성도
![structure.png](./image/structure.png)

### 1-3. 주요 기능


### 1-4. 개발 환경

- Front-end: `TypeScript`, `React.js`, `NextJs14`, `zustand`, `vanilla-extract`, `pnpm`
- Back-end: <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=Spring Data JPA&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=MongoDB&logoColor=white"/> <img src="https://img.shields.io/badge/EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white"/> <img src="https://img.shields.io/badge/AWS OpenSearch-005EB8?style=flat-square&logo=OpenSearch&logoColor=white"/> <img src="https://img.shields.io/badge/S3-569A31?style=flat-square&logo=Amazon S3&logoColor=white"/>


## 2. 프로젝트 설계

### 2-1. 헥사고날 아키텍처
![Hexagonal_Architecture2.png](./image/Hexagonal_Architecture2.png)
![Hexagonal_Architecture1.png](./image/Hexagonal_Architecture1.png)


## 3. 개발 과정

## 4. 프로젝트 관리

### 4-1. Jira ticket을 활용한 task 관리

효율적인 협업과 팀원간의 빠른 피드백을 통해 변화에 신속하게 대응하기 위해 개발 프로세스에는 스크럼 방식을 채택하였습니다.

![burn_down_chart.png](./image/burn_down_chart.png)
 
 번다운 차트 등의 지표를 확인할 수 있고, task의 세부 조정이 가능한 Jira를 통해 task를 관리하였습니다.

![jira_ticket.png](./image/jira_ticket.png)
![github_pr.png](./image/github_pr.png)

 Jira의 티켓 번호와 GitHub의 커밋 및 PR을 연동시켜, 작업 내역을 추적하고 효율적인 협업을 도모하였습니다.


