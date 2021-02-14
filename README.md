
# Klip Android SDK

abi를 실행하는 예제입니다. 예제로 커스텀 토큰을 전송할 수 있습니다.



=======================================


# Klip Android SDK
Klip Android SDK는 Klip을 이용하여 Android 플랫폼 위에서 BApp을 만들수 있도록 제공하는 라이브러리 입니다.

## Environment/Prerequisites
요구 사항 
- Android API 16: Android 4.1(Jelly Bean) 이상
- Android Studio 3.0.0 이상
- Java 8 이상 

필요 권한 
- Internet
```
// ...AndroidManifest.xml
<uses-permission android:name="android.permission.INTERNET" />
```

## SDK Build
Klip SDK 빌드 완료 후, `<project>/sdk/build/outputs/aar`위치에서 빌드된 AAR파일 확인이 가능합니다.
```
$ ./gradlew :sdk:build
```

## SDK Test 
Unit Test 실행
```
$ ./gradlew :sdk:test
```

Instrumented Unit Test 실행 (Android Device 혹은 Android Emulator 필요)
```
$ ./gradlew :sdk:connectedAndroidTest
```

## SDK Download
배포가 완료된 zip 파일은 아래 URL을 통해 다운로드할 수 있습니다. 
* url : https://docs.klipwallet.com/a2a-sdk/a2a-sdk-download

## Sample Application Build/Install
**Gradle Wrapper를 사용**한 Sample Application을 빌드 및 설치 
1. Project 다운
2. Terminal 열기
3. 아래 명령 실행하여 빌드 (빌드가 완료되면 `<project>/app/build/outputs/apk`위치에서 APK파일 확인이 가능하며, 빌드된 Sample Application은 **Cypress Klaytn Network 위에서 실행**됩니다.)
```
$ ./gradlew :app:installDebug
```

**Android Studio를 사용**한 Sample Application을 빌드 및 설치 
1. Project 다운  
2. Android Studio로 프로젝트 열기 
3. 상단 메뉴 바에서 "app" 선택 
4. 상단 메뉴 바에서 "running device" 선택 (emulator or 연결된 device)
5. 상단 메뉴 바에서 "run"(="▶") 선택

## Sample Application Run
Sample Application 실행 후, 아래의 5가지 따라하기로 실행이 가능합니다. 
Sample Application 내의 로그를 확인하기 원한다면 `adb logcat -s klip.sdk` 명령을 실행하여 확인이 가능합니다.

* [따라하기 1] Klip 사용자 인증 정보(ex, Klaytn Address) 가져오기 
* [따라하기 2] Klip 사용자 KLAY 전송하기  
* [따라하기 3] Klip 사용자 Token 전송하기  
* [따라하기 4] Klip 사용자 Card 전송하기  
* [따라하기 5] Klip 사용자 Contract 실행하기  
* [따라하기 6] Klip 처리 상태 확인하기 

### 따라하기 1. Klip 사용자 인증 정보 가져오기
**순서**  
1. "prepare (Link)" 버튼 클릭 
    1. BApp Name : Klip 화면에 출력될 BApp Name 입력 
    2. BApp Success URL : *(optional)* Klip 실행 성공 후, 이동될 URL 입력
    3. BApp Fail URL : *(optional)* Klip 실행 실패 후, 이동될 URL 입력
    4. "Execute" 버튼 클릭 
2. "request" 버튼 클릭 
3. (Klip 화면 이동 후) "확인" 버튼 클릭
4. (샘플 화면 이동 후) "getResult" 버튼 클릭

### 따라하기 2. Klip 사용자 KLAY 전송하기
**주의**  
* KLAY 전송에 사용되는 **Value 값의 기본 단위는 KLAY**입니다. 예를 들어, 1 KLAY 전송을 시도한다면 1 입력해야 합니다.

**순서**  
1. "prepare (KLAY)" 버튼 클릭 
    1. To Address : KLAY 전송받을 사용자 Klaytn Address 입력
    2. From Address : *(optional)* KLAY 전송시킬 사용자 Klaytn Address 입력
    3. Value : KLAY 전송량 입력
    4. BApp Name : Klip 화면에 출력될 BApp Name 입력
    5. BApp Success URL : *(optional)* Klip 실행 성공 후, 이동될 URL 입력
    6. BApp Fail URL : *(optional)* Klip 실행 실패 후, 이동될 URL 입력
    7. "Execute" 버튼 클릭 
2. "request" 버튼 클릭 
3. (Klip 화면 이동 후) "확인" 버튼 클릭
4. (샘플 화면 이동 후) "getResult" 버튼 클릭

### 따라하기 3. Klip 사용자 Token 전송하기
**순서**  
1. "prepare (Token)" 버튼 클릭 
    1. Contract Address : 전송할 Token Contract Address 입력
    2. To Address : Token 전송받을 사용자 Klaytn Address 입력
    3. From Address : *(optional)* KLAY 전송시킬 사용자 Klaytn Address 입력
    4. Amount : Token 전송량 입력 
    5. BApp Name : Klip 화면에 출력될 BApp Name 입력 
    6. BApp Success URL : *(optional)* Klip 실행 성공 후, 이동될 URL 입력
    7. BApp Fail URL : *(optional)* Klip 실행 실패 후, 이동될 URL 입력
    8. "Execute" 버튼 클릭 
2. "request" 버튼 클릭 
3. (Klip 화면 이동 후) "확인" 버튼 클릭
4. (샘플 화면 이동 후) "getResult" 버튼 클릭

### 따라하기 4. Klip 사용자 Card 전송하기
**순서**  
전송하려는 사용자의 Card ID를 이미 알고 있을 경우, 아래 순서의 1번과 2번은 생략이 가능합니다. 
Sample Application은 1번과 2번 과정을 순서대로 수행할 경우, 3번 과정에서 필요한 Card ID값이 자동으로 세팅되도록 개발되었습니다.
1. (optional) [따라하기 1] 수행해서 사용자의 Klaytn Address 가져오기 
2. (optional) "getCardList" 버튼 클릭 
    1. Card Address : 조회할 Card Contract Address 입력
    2. User Address : 조회할 User Klaytn Address 입력
    3. Cursor : 조회 Card 내용이 100개가 넘어갈 경우 다음 Cursor 입력
3. "prepare (Card)" 버튼 클릭 
    1. Contract Address : 전송할 Card Contract Address 입력
    2. To Address : Card 전송받을 사용자 Klaytn Address 입력
    3. From Address : *(optional)* KLAY 전송시킬 사용자 Klaytn Address 입력
    4. Card ID : 전송할 Card Id 입력 
    5. BApp Name : Klip 화면에 출력될 BApp Name 입력
    6. BApp Success URL : *(optional)* Klip 실행 성공 후, 이동될 URL 입력
    7. BApp Fail URL : *(optional)* Klip 실행 실패 후, 이동될 URL 입력
    8. "Execute" 버튼 클릭 
4. "request" 버튼 클릭 
5. (Klip 화면 이동 후) "확인" 버튼 클릭
6. (샘플 화면 이동 후) "getResult" 버튼 클릭

### 따라하기 5. Klip 사용자 Contract 실행하기  
**주의**  
* Contract 실행에 사용되는 **Value 값의 기본 단위는 peb**입니다. 예를 들어, 1 KLAY 전송을 시도한다면 1000000000000000000 입력해야 합니다. Klaytn에서 사용되는 KLAY 단위는 [Klaytn Document > KLAY 단위](https://ko.docs.klaytn.com/klaytn/design/klaytn-native-coin-klay)를 참고할 수 있습니다.
* Contract 실행에 사용되는 ABI와 Params의 개수와 타입은 동일해야 합니다. 예를 들어, inputs에 포함된 내용이 3개라면, params에 포함된 내용이 3개이어야 합니다.
* Contract 실행에 사용되는 ABI는 실행할 Method 부분만을 발췌하여 "{..}" 형태로 입력되어야 합니다. 
* Contract 실행에 사용되는 Params는 ABI에 입력된 inputs과 동일한 순서로 "[..]" 형태로 입력되어야 합니다. 

**순서**  
1. "prepare (Card)" 버튼 클릭 
    1. To Contract Address : 실행할 Contract address 입력
    2. From Address : *(optional)* KLAY 전송시킬 사용자 Klaytn Address 입력
    3. Value : KLAY 전송량 입력
    4. ABI : 실행할 Contract의 Method에 대한 ABI 입력
    5. Params : 실행할 Contract의 Method에 대한 Params 입력
    6. BApp Name : Klip 화면에 출력될 BApp Name 입력
    7. BApp Success URL : *(optional)* Klip 실행 성공 후, 이동될 URL 입력
    8. BApp Fail URL : *(optional)* Klip 실행 실패 후, 이동될 URL 입력
    9. "Execute" 버튼 클릭 
2. "request" 버튼 클릭 
3. (Klip 화면 이동 후) "확인" 버튼 클릭
4. (샘플 화면 이동 후) "getResult" 버튼 클릭

### 따라하기 6. Klip 처리 상태 확인하기 
**주의**  
* 사용자 승인이 완료되었지만, Transaction 처리가 실패되었다면, 실패가 나는 경우가 발생할 수 있습니다. 
* 예를 들면, [따라하기 5]를 통해 Contract 실행을 했지만, 해당 Contract 내부에서 에러가 발생하여 Revert가 일어난 경우가 있을 수 있습니다.
* 만약 Revert로 실패가 나는 경우, 응답 결과로 받은 "tx_hash"값을 [Klayn Scope](https://baobab.scope.klaytn.com/)로 조회함으로써 Revert 여부를 직접 확인할 수 있습니다.  

**순서**  
Sample Application은 [따라하기 1~5]과정을 수행할 경우, 2번 과정에서 필요한 Request Key값이 자동으로 세팅되어 동작하도록 개발었습니다. 
1. [따라하기 1~5] 수행 
2. "getResult" 버튼 클릭 

## Configure
`gradle.properties` 파일을 통해, Android SDK Version 등의 빌드 속성들을 설정할 수 있습니다.
|property key                       | property description              |
|-----------------------------------|-----------------------------------|
|ANDROID_BUILD_MIN_SDK_VERSION      | android build minimum sdk verison |
|ANDROID_BUILD_TARGET_SDK_VERSION   | android build target sdk version  |
|ANDROID_BUILD_SDK_VERSION          | android build sdk version         |
|ANDROID_BUILD_TOOL_VERSION         | android build tool version        |
|KLIP_SDK_VERSION_CODE              | klip sdk version code             |
|KLIP_SDK_VERSION_NAME              | klip sdk version name             |

## Directory Structure
```
project
├── app/
│   ├── src/
│   │   ├── main
│   │   ├── test
│   │   ├── androidTest
│   ├── build.gradle
├── sdk/
│   ├── src/
│   │   ├── main
│   │   ├── test
│   │   ├── androidTest
│   ├── build.gradle
│   ├── javadoc.gradle
├── build.gradle
├── gradle.properties
├── settings.gradle
```
