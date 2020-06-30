# StudyCoroutine

### 적용 기술
## JetPack
- DataBinding
- LiveData
- Room
- ViewModel
- ViewPager2
- ActivityResultLauncher
- Hilt

## Network
- Retrofit

## Asynchronous
- Coroutine

## Etc
- MockInterceptor

### 구현 내용

1. 화면 구성
 - 2개의 리스트와 상세 화면으로 구성

 <리스트 화면>
  - 전체 리스트와 즐겨찾기 리스트 2개를 탭으로 구성
  - 리스트 아이템 클릭시 해당 아이템 상세 화면으로 이동

 가. 전체 리스트
  - 리스트는 이미지, 제목, 평점, 즐겨찾기 토글 버튼으로 구성, 페이징 기능 구현
  - 페이지 구성은 20개 단위로

 나. 즐겨찾기 리스트
  - 리스트는 이미지, 제목, 평점, 즐겨찾기 등록시간, 즐겨찾기 토글 버튼으로 구성
  - 즐겨찾기 관련 정보는 로컬 저장
  - 즐겨찾기 최근등록순, 평점순 정렬 기능 구현 (오름차순, 내림차순)

<상세 화면>
 - 원본 이미지, 제목, 상세정보들을 화면에 표기
 - 즐겨찾기 토글 기능
