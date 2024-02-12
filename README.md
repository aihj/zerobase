## 🌏 Project - Wifi
- 서울 공공 데이터 API를 이용해 자신의 위치 및 특정 위치에서 1km 내에 존재하는 공공 와이파이를 찾는 프로젝트
- Project to find public Wi-Fi around you using public data API

## ⚙ Tech Stack
- Language : `Java`
- Build : `Maven`
- Database : `SQLITE`
- Server : `Tomcat 8.5`
- JDK : `JDK 1.8`
- Web : `CSS`, `HTML5`, `JSP`
- Library : `Lombok`, `Okhttp3`, `Gson`


## ❗ 프로젝트 작동 순서 및 기능
- OPEN API를 활용하여 서울시의 모든 공공 와이파이 정보를 가져옵니다.
- 사용자가 입력한 위치 좌표를 기반으로 주변에 있는 공공 와이파이 정보 20개를 보여줍니다.
- 사용자가 입력한 위치 정보와 조회한 시점을 기준으로 DB에 정보를 저장하고, 저장된 정보를 조회할 수 있습니다.
- 특정 공공 와이파이의 상세 정보를 제공합니다.
- 사용자는 북마크 그룹을 생성하고, 그룹 목록을 확인하며 그룹을 수정하거나 삭제할 수 있습니다.
- 사용자는 공공 와이파이 정보를 북마크로 추가하거나 삭제할 수 있습니다.

## 📌 ERD
![Wifi_북마크추가](https://github.com/kdmin0706/Public-Wifi/assets/124044861/e92f07a8-7062-412e-a9a7-6eb6e28b1a9d)

## 📌공공 와이파이 DB 시연영상
[![video Label](http://img.youtube.com/vi/kGk7caUQXY4/0.jpg)](https://youtu.be/kGk7caUQXY4)

## 📌공공 와이파이 북마크 시연영상
[![video Label](http://img.youtube.com/vi/FVyeQ4SgxTI/0.jpg)](https://youtu.be/FVyeQ4SgxTI)