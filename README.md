# kakaopaysec

## 빌드 & 실행 방법

```bash
# 빌드
gradle wrapper
./gradlew build

# spring boot 프로젝트 실행
./gradlew bootrun

# unit test
./gradlew clean test
```

- Swagger : http://localhost:8080/swagger-ui.html
- H2-console : http://localhost:8080/h2-console

</br>

#### 환경


- Spring Boot, JAVA 1.8, Gradle 7.1.1
- MySQL (test : H2), JPA, Querydsl
- JUnit 5  

</br>

#### 프로젝트 구성 방식

![Screen Shot 2021-08-22 at 9 06 46 PM](https://user-images.githubusercontent.com/33947168/130354486-dcf39c54-dfd5-4dc7-be43-76ca88e171e2.png)


- 데이터 전송 객체 분리
  - DTO : [프론트 -> 컨트롤러 -> 서비스] 구간
  - VO : [프론트 <- 컨트롤러 <- 서비스 <- 레포지터리] 구간

</br>

#### 테이블 구성 방식

<img width="500" alt="Screen Shot 2021-08-22 at 8 54 41 PM" src="https://user-images.githubusercontent.com/33947168/130354150-a2c023db-4cb6-4e11-9a33-640eb8bd7cab.png">

- 주어진 csv 데이터를 기반으로 각각 account, branch, transaction 3개의 테이블을 생성했습니다.
- 세 테이블의 연도별/계좌번호별 합계금액을 statistics 테이블에 저장했습니다.
  - 이유)
    - 4 문제 모두 transaction을 가공한 통계 데이터를 요구했습니다.
    - 통계 데이터 api를 호출할 떄마다 각 테이블을 매번 join 하는 것은 불필요 하다고 생각했습니다.

</br>

### 단위 테스트

- JUnit 5 를 이용하여 레포지터리/서비스/컨트롤러의 단위테스트를 각각 진행하였습니다.
- 다른 레이어와의 결합 분리를 위해 mock객체를 활용했습니다.

</br>

</br>

## 문제 해결 방법

</br>

### Q1

문제 : 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발

해결방법

- statistics 테이블에서 각 연도별 합계금액이 가장 많은 고객을 조회하였습니다.
  - year을 기준으로 합계 금액에 대한 rank를 매긴 다음, 각 연도별로 rank가 1인 고객리스트를 반환하도록 했습니다.
  - Native query로 작성했습니다. (JPA/Querydsl은 from 절 서브쿼리를 지원하지 않음)

</br>

</br>

### Q2

문제 : 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발

해결방법

- 기능 동작 방식
  1. 주어진 연도에 해당하는 statstics 엔티티 리스트와 모든 account 엔티티 리스트를 조회합니다.
  2. 서비스 레이어에서 statistics 엔티티 리스트에 해당 연도, 해당 계좌번호와 일치하는 요소가 없다면 결과리스트에 추가합니다.

</br>

</br>

### Q3

문제 : 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.

해결방법

- statistics 테이블에서 연도별, 지점별로 합계금액을 조회했습니다. 
- 서비스 레이어에서 <연도 : 지점 합계 금액 리스트> 의 형식으로 매핑하여 리스트를 반환했습니다.

</br>

</br>

### Q4

문제 : 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발

제약 사항

- 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관
- 분당점 출력시 에러 메시지 출력

해결방법

- 기능 동작 방식
  1. 서비스 레이어에서 주어진 지점명으로 branch 엔티티를 조회하여 지점 코드를 얻습니다.
  2. branch_code 또는 merged_to 컬럼값이 해당 지점 코드인 지점들의 합계금액을 statistics에서 조회합니다.
- branch테이블에 merged_to 컬럼을 더하여, 해당 지점이 통합된 지점을 가리키도록 했습니다. 

</br>

</br>

덧붙임) [이 링크](https://ivorymood.notion.site/544efb749b5043c7be89a74680b6c859?v=44bf739c7dc648e8bb5bf3b46f14f666)는 제가 과제를 진행하면서 고민했던 것, 찾아본 것들을 메모해둔 것입니다.  
혼자 보려고 메모한 것이라 구어체적인 표현이 많지만..! 과제 진행과정을 보여드리기 위해 첨부합니다..!

