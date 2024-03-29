# 🎁 [세종이의 집] - 세종대학교 공동구매, 굿즈샵

프로젝트명 : [세종이의 집] 세종대학교 공동구매, 굿즈샵   
개발 기간 : 2023. 01 ~ 2023. 03   
팀원 및 역할 : UI/UX 디자이너 1명, Android 개발 1명, IOS 개발 1명,  Server 개발 1명

## 📝 Description
> 대학교마다 애교심과 소속감을 위해 자체적으로 굿즈(기념품)을 제작하고 홍보하지만 우리 학교의 경우 그렇지 않은 것 같아 직접 앱을 제작하였습니다.   
또한, 에브리타임이라는 대학생 커뮤니티에서 학생이 개인적으로 굿즈 제작 및 홍보, 공동구매 게시글을 작성하는 것을 종종 보았습니다. 이 과정에서 번거롭고, 효과적이지 못한 부분들이 많다고 느꼈고, 인스타/블로그로 모두 흩어져 판매 및 홍보가 이루어지던 상품을 학생들이 앱으로 편리하게 확인, 구매가 가능할 수 있도록 기획했습니다.

## 🛠️ Tech Stack
```Kotlin```, ```MVVM```, ```Repository```, ```Coroutine```, ```Room```, ```DataBinding```, ```Retrofit2```, ```Gson```, ```JWT Token```, ```SharedPreference```

## ✅ Main Function
### 1️⃣ 굿즈/상품 정보 제공   
* 교내 학생들이 제작한 세종대학교 굿즈 상품을 한눈에 확인 가능
* 개별적으로 판매하던 굿즈 판매자와 직접 연락해 상품/판매자 정보를 받아 앱에 게시했습니다.
<div>
  <img src="https://user-images.githubusercontent.com/98886487/230816770-08542932-a62a-449c-a9b3-117d8897b81b.png" width="200" height="400" />
  <img src="https://user-images.githubusercontent.com/98886487/230827102-14feb11b-7eec-423a-a337-b80db6a07660.png" width="200" height="400" />
  <img src="https://user-images.githubusercontent.com/98886487/230818265-96dec8b1-6d91-4124-8a8c-3302c3058565.png" width="200" height="400" />
  <img src="https://user-images.githubusercontent.com/98886487/230827249-6eeca30d-34b0-4c99-a458-516cd7b5a5f9.png" width="200" height="400" />
</div> 
<br>

### 2️⃣ 상품 주문 및 주문 내역 확인
* 사용자는 앱에서 굿즈를 확인하고, 구매할 수 있습니다. 상품은 현장수령과 택배수령이 가능하며 구매방법은 계좌이체로 이루어 집니다.
* [앱] 사용자가 앱에서 굿즈 구매 후 계좌이체 완료 → [서버] 구매 정보 DataBase 추가 → [개발자→판매자] 판매자에게 구매자 목록 전달(1일 1회) → [판매자→개발자] 계좌이체가 확인된 구매자 목록 전달(1일 1회) → [앱] 계좌이체가 확인된 구매자 대상 주문상태 갱신 → [사용자] 주문상품 수령
<div>
  <img src="https://user-images.githubusercontent.com/98886487/230829867-2ac7505d-c4af-45b6-8bfc-f2a8f1ebf69b.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230830489-761f1f6a-ced3-42d9-81bf-759fabccd00f.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230830340-f71b5eb3-bdf2-4c1c-b9de-13446bdd2e97.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230830351-d9aca093-4d30-41df-b257-ed86327f6144.png" width="200" height="400" /> 
</div>  
<br>

### 3️⃣ 장바구니 및 마이페이지
* 장바구니 상품은 현장수령/택배수령으로 구분해 담을 수 있습니다.
<div> 
  <img src="https://user-images.githubusercontent.com/98886487/230840519-10da98f1-aa11-4214-b5be-845e4b3f6645.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230840737-49713ea4-a673-4b84-8986-3e27fe273b6e.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230840803-a6f05ac4-e115-4176-a8eb-f1311d9f7c61.png" width="200" height="400" /> 
  <img src="" width="200" height="400" /> 
</div>
<br>

### 4️⃣ 상품 검색 및 로그인 기능
* 적은 상품 수에서 필수적인 기능은 아니지만 Jetpack Room 라이브러리를 이용하기 위해 검색 기능을 구현해 보았습니다.
* 회원가입 및 로그인에는 JWT Token이 사용됩니다. 또한, 이메일 인증을 통한 비밀번호 찾기가 가능합니다.
<div> 
  <img src="https://user-images.githubusercontent.com/98886487/230842675-cfaacfde-46cc-4c43-90cf-2927425565ff.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230842722-a5d2050a-4cfd-4863-872f-ddad782f0ba6.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230843338-b7b7f994-f0ed-4c67-9616-e992d5f2da90.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230843370-b42abbab-3fc5-4ec1-83bd-c9ffdd34b97e.png" width="200" height="400" /> 
</div>
<br>

### #️⃣ [추가]
* '우편번호 검색'에는 Git Pages와 다음에서 제공하는 우편번호 검색 API를 활용했습니다.
<div> 
  <img src="https://user-images.githubusercontent.com/98886487/230846229-b35cb79a-5496-4ab7-9597-35a7d3a975c7.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230846052-3f47cd22-f118-4e6c-8eab-7dfcd94aa9ef.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230846474-ad0a82d9-3eb6-4eca-9cd6-9bdb25c12182.png" width="200" height="400" /> 
  <img src="https://user-images.githubusercontent.com/98886487/230848092-391eaf4b-27c0-4406-8441-e4ddddcadfd2.png" width="200" height="400" /> 
</div>
<br>

## 📌 Issue
### 서버와 API 통신을 위해 기존의 http → https 통신
안드로이드에서 기본적으로 http 통신을 허용하지 않지만 manifest에서 android:usesCleartextTraffic 속성을 true로 설정하면 http로 접근이 가능합니다. 하지만 해당 방법은 권장되지 않고, 사이트에서 http 접근을 보안상의 이유로 허용하지 않는다면 https 통신을 강제해야 합니다.   
https 통신의 경우 웹사이트에 SSL/TLS 인증서가 적용된 경우 정상적인 인증서가 패키지에 포함되어야 하지만 인증서없이 우회하여 접속이 가능합니다.   
이 방법 또한 권장되지 않는 방식이지만 인증서 발급에 어려움이 있어 우회하여 https 주소에 접근하는 방식을 채택했습니다.

## 🤔 Learned
* 로그인 및 회원가입에 JWT Token과 회원 인증방식에는 Bearer Authentication 방식을 이용했습니다.
* MVVM 패턴을 개념적으로만 알고 있었지만 프로젝트에 적용해 보았지만 “이게.. 맞나..?” 많이 어설퍼 보였고(?), 계층이 제대로 분리되지 않았음을 느꼈습니다.
* 앱의 UI/UX, 요구사항, 기능도 중요하지만 개발 전 아이디어에 대한 검증, 시장 조사 및 분석, 리서치 등 기획 과정 또한 매우 중요하다는 것을 다시 한번 느꼈습니다.

## 🔗 PlayStore Link
https://play.google.com/store/apps/details?id=com.sejong.sejonggoodsmallproject
