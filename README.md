# :sparkles:[ PacBoy(팩보이) ]:sparkles: : Game Project JAVA


> 스레드를 이용한 GUI 게임 구현 / 고전게임 pacman 참고
> 
> 팩보이가 제한시간 안에 유령을 피해 코인을 다먹어야 성공하는 게임

## 개발 기간
- 23.09.13 - 23.09.19 :hourglass_flowing_sand:
  
## 팀원소개 | PacCo(팩코더) :sunglasses::computer:
- 김정림(팀장) : [junaa111@nate.com](junaa111@nate.com)
  + 아키텍처 설계
  + 미로 & 게임화면 GUI
  + 통합 및 리팩토링
- 김은영 : [lovekyy1201@naver.com](mailto:lovekyy1201@naver.com)
  + 시작화면 GUI
  + BGM
  + 이미지 작업
  + 소스코드 버전 관리 
- 김효범 : [kimkishi97@gmail.com](mailto:kimkishi97@gmail.com)
  + 종료화면 GUI
  + 랭킹 시스템
  + 닉네임 및 점수
- 임성균 : [tjdrbs0607@naver.com](mailto:tjdrbs0607@naver.com)
  + 팩보이 
  + 몬스터 
  + 코인
  + 에러처리 
  
##  개발환경 

    Java      
    JDK SE 11
    IDE : Eclipse 4.26
    Notion / Adobe illustrator / Adobe photoshop
***
:crystal_ball: :grinning: :ghost: - - -  
## 1. 프로젝트 소개

프로젝트 명 : PacBoy [ 젊은 PacMan => 팩보이 ]

팀 명 : PacCo [ PacBoy + Coder 의 줄임말 ]

개요 : 팩보이가 제한시간 안에 유령을 피해 코인을 다 먹어야 성공하는 게임

목적 : 고전 게임을 사랑하는 사용자에게 하나의 재미를 선사하기 위해 


## 2. 문제분석

- 시작화면
    - 게임 타이틀 (Pacboy) 출력
    - 닉네임 입력
    - 시작버튼, 종료버튼
- 게임순서
    - 제한 시간 20초, 1 코인당 1 점
    - 성공 : 제한 시간 안에 코인 다 먹은 경우
    - 실패 : 유령에게 닿이거나, 제한 시간안에 코인을 먹지 못한 경우
- 종료화면
    - 성공시 You win 타이틀 출력 , 실패시 Game over 타이틀 출력
    - 닉네임, 등급, 점수 차례대로 출력
    - 랭킹 Top 5 닉네임과 점수가 순서대로 출력 
    - 재시작 버튼, 종료 버튼

## 3. 설계도

클래스 다이어그램 이미지 업로드 예정
  
<img src="/path/to/img.jpg" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>

## 4. 개발계획

<img src="/path/to/img.jpg" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>

## 5. 소스코딩

#### 메인프레임 pacboy.frame - [상세보기 - WIKI 이동](https://github.com/KimEunYoungs/Pacboy/wiki/pacboy.frame)
- InGameFrame 
- IntroFrame :star:메인메서드:star:
- OutroFrame

#### 객체 pacboy - [상세보기 - WIKI 이동](https://github.com/KimEunYoungs/Pacboy/wiki/pacboy)
- Coin
- Ghost
- GhostMoveManager
- Pacboy
- PacboyMoveManager
  
#### 데이터베이스 pacboy.database - [상세보기 - WIKI 이동](https://github.com/KimEunYoungs/Pacboy/wiki/pacboy.database)
- BGM
- Maze
- Ranking

#### 인터페이스 pacboy.frame.inter - [상세보기 - WIKI 이동](https://github.com/KimEunYoungs/Pacboy/wiki/pacboy.frame.inter)
- UserInterface

#### Source File - [상세보기 - WIKI 이동](https://github.com/KimEunYoungs/Pacboy/wiki/Source File)
- C://pacco//resource
  - audio
  - database
  - images

## 6. 포트폴리오 / jar 배포

<div>
  <img src="captureImg/01.png" width="40%" height="40%" title="px(픽셀) 크기 설정" alt="pacboyImg01"></img>
  <img src="captureImg/02.png" width="40%" height="40%" title="px(픽셀) 크기 설정" alt="pacboyImg02"></img>
</div>
<div>
  <img src="captureImg/03.png" width="30%" height="30%" title="px(픽셀) 크기 설정" alt="pacboyImg03"></img>
  <img src="captureImg/04.png" width="30%" height="30%" title="px(픽셀) 크기 설정" alt="pacboyImg04"></img>
  <img src="captureImg/05.png" width="30%" height="30%" title="px(픽셀) 크기 설정" alt="pacboyImg05"></img>
</div>
<div>
  <img src="captureImg/06.png" width="40%" height="40%" title="px(픽셀) 크기 설정" alt="pacboyImg06"></img>
  <img src="captureImg/07.png" width="40%" height="40%" title="px(픽셀) 크기 설정" alt="pacboyImg07"></img>
</div>
