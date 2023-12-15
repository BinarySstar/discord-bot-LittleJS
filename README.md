# 디스코드 봇 리틀진성
리틀진성은 디스코드 서버에 부가적인 기능을 추가해주는 봇입니다. 많은 봇들이 이미 사용되고 있지만 자바에 대한 이해를 높이고 친숙해지기 위해 자바 사이드 프로젝트를 하고 싶어 디스코드 봇을 제작하게 되었다. 

### 개발에 사용된 언어 및 툴
- Java 19
- Intellj IDEA 2023.1.1
- JDA 4.2.0

### 봇 추가
[서버에 봇 추가하기](https://discord.com/api/oauth2/authorize?client_id=1173287806349615175&permissions=8&scope=bot)

### 기능
리틀진성은 유튜브 검색 결과를 이용하여 음악을 재생할 수 있다. 전체 명령어는 다음과 같다.
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/1f4e1da7-3439-42a7-ab4e-003febfc96a7)

리틀진성으로 음악을 재생하기 위해서는 음성 채널에 참여해야 한다. 참여하지 않고 명령어를 입력하면 다음과 같은 메세지를 띄운다.
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/a284c69d-457b-4261-b36b-89e07e47b302)


#### 재생
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/19e67e78-3278-4863-96b3-fb93bc8b7128)

원하는 노래의 제목을 입력해준다. 유튜브 검색 결과의 최상위 동영상으로 queue에 추가된다.
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/7c6749d5-3454-423a-99af-db4598657344)

유튜브 영상의 제목을 가져와 어떤 노래가 추가되었는지 Embed로 표시한다.
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/fab87703-b97e-433b-bfa9-98d1a6f5ca4f)

노래가 재생되면 해당 노래의 정보를 Embed로 표시한다.

![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/14c19f31-4aa8-4527-9ce8-a626c11b0b58)

노래가 재생 중에 재생 명령어를 사용하면 queue에 추가되어 저장한다. 재생 중인 노래가 끝나면 다음 노래가 재생된다.

#### 재생정보
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/38d8d0dd-8c5f-4b4b-8953-ddd5f6ca8318)
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/fab87703-b97e-433b-bfa9-98d1a6f5ca4f)

현재 재생 중인 노래의 정보를 Embed로 표시한다.

#### 일시정지
- 현재 재생중인 노래를 일시정지한다.

#### 재개
- 일시정지된 노래를 다시 재생한다.

#### 건너뛰기
- 재생 중인 노래를 멈추고 다음 노래를 재생한다.
   
#### 목록
- 현재 queue에 저장되어 있는 노래를 불러온다.

![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/1f552e61-aece-4b3e-937d-08973082023d)
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/939aac50-b3b1-4995-8d03-5c0c54b43e33)

#### 제거
- queue에 저장되어 있는 노래를 번호를 이용하여 제거한다. 번호는 목록 명령어를 이용하여 알 수 있다.

예를 들어 위 목록 명령에서 '곽진언 - 시청 앞 지하철 역에서'의 번호는 3번이다.

![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/80273fc0-dc85-49ef-9169-4cb640366004)
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/efbc2ad0-e4bf-4903-8541-365b1ddf111c)
![image](https://github.com/BinarySstar/discord-bot-LittleJS/assets/117090689/ba1f0bd7-88d9-44dc-801d-e7b4bc39a66f)

목록을 보면 3번 노래가 queue에서 제거되었음을 알 수 있다.

### 버그 및 개선해야할 점(2023-12-16)
- Embed 메세지가 서버의 대표 텍스트 채널에만 표시됨. 다른 채널에서 명령어를 입력하여도 대표 텍스트 채널에 출력
- 음성 채널에 연결이 끊어져도 해당 트랙이 반복상태 시, 재생 메세지가 출력됨
- 음성 채널에 봇을 제외한 다른 사용자가 없으면 자동으로 연결을 끊는 기능 추가 예정
