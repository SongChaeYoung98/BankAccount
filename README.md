# Bank Account

가계부 정리를 위한 간단한 안드로이드 어플리케이션


## Installation

위 파일을 다운 받고 'app > release > app-release.apk'을 안드로이드 기기에 저장 후 설치.


## Configuration

- activity_entry.xml : 소모 금액 레이아웃
- activity_main.xml : 메인 레이아웃
- list_item.xml : 품목명, 금액 분할용 레이아웃
- entry : DB 정의
- MainActivity : 메인
- MyCursorAdapter : 뷰의 속성 지정, 반환
- MyDBHelper : DB 저장


##  How to Use

1. 입력하고 싶은 날짜 클릭
2. 품목명, 소모된 가격 입력 시 총액 표시
3. 특정 리스트 삭제 시 해당 라인 길게 클릭 후 삭제


## Demo

- 메인
![Screenshot_20211108-191110](https://user-images.githubusercontent.com/93585651/144855004-97c86b24-988f-49be-9767-a878b2b76345.jpg)


- 진입 후 화면
![Screenshot_20211108-191406](https://user-images.githubusercontent.com/93585651/144855099-81638edb-596d-4a8e-9e9c-22ac84340488.jpg)


- 품목 삭제
![Screenshot_20211108-191412](https://user-images.githubusercontent.com/93585651/144855152-dd7db38b-b787-4177-823e-919bfd84d4a6.jpg)
