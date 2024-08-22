# 모두를위한 학습 서비스 (스프링부트)
CORS(Cross-Origin Resource Sharing)
라고 교차 검증 하는 코드가 유니티와 데이터 통신시 필수적으로 사용되고

springboot<>unity<>python<>opan api
서로에게 비동기 적이지만 기능 부분의 의존적이다
그러니 테스트 케이스를 기반으로 서버에 문제가 없는지, 클라이언트에 문제가 없는지 확인 하고
합해야 비로서 작동한다(CORS는 서버와 클라 2개다 서비스 중이여야 사용가능 하다)
해당 코드의 테스트 코드
https://github.com/r2d2c2/unityServer/blob/master/src/test/java/study/unityserver/controller/UnityChatControllerTest.java
다행이 이미지,get등은 CORS없이 통신이 가능하다
하지만 오디오 부분은 2개중 하나라도 없으면 허용하지 않는다
