package com.kotlin.ourmemories.view.place

/**
 * Created by hee on 2017-11-28.
 */
import java.util.*

object AddressBook {

    fun makeAddress(): List<SiDo> {
        return Arrays.asList(
                makeSeoul(),
                makeBusan(),
                makeDaegu(),
                makeIncheon(),
                makeGwangju(),
                makeDaejeon(),
                makeUlsan(),
                makeSejong(),
                makeGyeonggi()
        )
    }


    fun makeSeoul(): SiDo {
        return SiDo("서울특별시", makeSeoulSubAddress())
    }

    fun makeSeoulSubAddress(): List<SiGunGu> {
        val Seoul1 = SiGunGu("강남구")
        val Seoul2 = SiGunGu("강동구")
        val Seoul3 = SiGunGu("강북구")
        val Seoul4 = SiGunGu("강서구")
        val Seoul5 = SiGunGu("관악구")
        val Seoul6 = SiGunGu("광진구")
        val Seoul7 = SiGunGu("구로구")
        val Seoul8 = SiGunGu("금천구")
        val Seoul9 = SiGunGu("노원구")
        val Seoul10 = SiGunGu("도봉구")
        val Seoul11 = SiGunGu("동대문구")
        val Seoul12 = SiGunGu("동작구")
        val Seoul13 = SiGunGu("마포구")
        val Seoul14 = SiGunGu("서대문구")
        val Seoul15 = SiGunGu("서초구")
        val Seoul16 = SiGunGu("성동구")
        val Seoul17 = SiGunGu("성북구")
        val Seoul18 = SiGunGu("송파구")
        val Seoul19 = SiGunGu("양천구")
        val Seoul20 = SiGunGu("영등포구")
        val Seoul21 = SiGunGu("용산구")
        val Seoul22 = SiGunGu("은평구")
        val Seoul23 = SiGunGu("종로구")
        val Seoul24 = SiGunGu("중구")
        val Seoul25 = SiGunGu("중랑구")

        return Arrays.asList(Seoul1, Seoul2, Seoul3, Seoul4, Seoul5,
                Seoul6, Seoul7, Seoul8, Seoul9, Seoul10,
                Seoul11, Seoul12, Seoul13, Seoul14, Seoul15,
                Seoul16, Seoul17, Seoul18, Seoul19, Seoul20,
                Seoul21, Seoul22, Seoul23, Seoul24, Seoul25)
    }

    fun makeBusan(): SiDo {
        return SiDo("부산광역시", makeBusanSubAddress())
    }

    fun makeBusanSubAddress(): List<SiGunGu> {
        val Busan1 = SiGunGu("강서구")
        val Busan2 = SiGunGu("금정구")
        val Busan3 = SiGunGu("기장군")
        val Busan4 = SiGunGu("남구")
        val Busan5 = SiGunGu("동구")
        val Busan6 = SiGunGu("동래구")
        val Busan7 = SiGunGu("부산진구")
        val Busan8 = SiGunGu("북구")
        val Busan9 = SiGunGu("사상구")
        val Busan10 = SiGunGu("사하구")
        val Busan11 = SiGunGu("서구")
        val Busan12 = SiGunGu("수영구")
        val Busan13 = SiGunGu("연제구")
        val Busan14 = SiGunGu("영도구")
        val Busan15 = SiGunGu("중구")
        val Busan16 = SiGunGu("해운대구")

        return Arrays.asList(Busan1, Busan2, Busan3, Busan4, Busan5,
                Busan6, Busan7, Busan8, Busan9, Busan10,
                Busan11, Busan12, Busan13, Busan14, Busan15,
                Busan16)
    }

    fun makeDaegu(): SiDo {
        return SiDo("대구광역시", makeDaeguSubAddress())
    }

    fun makeDaeguSubAddress(): List<SiGunGu> {
        val Daegu1 = SiGunGu("남구")
        val Daegu2 = SiGunGu("달서구")
        val Daegu3 = SiGunGu("달성군")
        val Daegu4 = SiGunGu("동구")
        val Daegu5 = SiGunGu("북구")
        val Daegu6 = SiGunGu("서구")
        val Daegu7 = SiGunGu("수성구")
        val Daegu8 = SiGunGu("중구")

        return Arrays.asList(Daegu1, Daegu2, Daegu3, Daegu4, Daegu5,
                Daegu6, Daegu7, Daegu8)
    }

    fun makeIncheon(): SiDo {
        return SiDo("인천광역시", makeIncheonSubAddress())
    }

    fun makeIncheonSubAddress(): List<SiGunGu> {
        val Incheon1 = SiGunGu("강화군")
        val Incheon2 = SiGunGu("계양구")
        val Incheon3 = SiGunGu("남구")
        val Incheon4 = SiGunGu("남동구")
        val Incheon5 = SiGunGu("동구")
        val Incheon6 = SiGunGu("부평구")
        val Incheon7 = SiGunGu("서구")
        val Incheon8 = SiGunGu("연수구")
        val Incheon9 = SiGunGu("옹진군")
        val Incheon10 = SiGunGu("중구")

        return Arrays.asList(Incheon1, Incheon2, Incheon3, Incheon4, Incheon5,
                Incheon6, Incheon7, Incheon8, Incheon9, Incheon10)
    }

    fun makeGwangju(): SiDo {
        return SiDo("광주광역시", makeGwangjuSubAddress())
    }

    fun makeGwangjuSubAddress(): List<SiGunGu> {
        val Gwangju1 = SiGunGu("광산구")
        val Gwangju2 = SiGunGu("남구")
        val Gwangju3 = SiGunGu("동구")
        val Gwangju4 = SiGunGu("북구")
        val Gwangju5 = SiGunGu("서구")

        return Arrays.asList(Gwangju1, Gwangju2, Gwangju3, Gwangju4, Gwangju5)
    }

    fun makeDaejeon(): SiDo {
        return SiDo("대전광역시", makeDaejeonSubAddress())
    }

    fun makeDaejeonSubAddress(): List<SiGunGu> {
        val Daejeon1 = SiGunGu("대덕구")
        val Daejeon2 = SiGunGu("동구")
        val Daejeon3 = SiGunGu("서구")
        val Daejeon4 = SiGunGu("유성구")
        val Daejeon5 = SiGunGu("중구")

        return Arrays.asList(Daejeon1, Daejeon2, Daejeon3, Daejeon4, Daejeon5)
    }

    fun makeUlsan(): SiDo {
        return SiDo("울산산광시", makeUlsanSubAddress())
    }

    fun makeUlsanSubAddress(): List<SiGunGu> {
        val Ulsan1 = SiGunGu("남구")
        val Ulsan2 = SiGunGu("동구")
        val Ulsan3 = SiGunGu("북구")
        val Ulsan4 = SiGunGu("울주군")
        val Ulsan5 = SiGunGu("중구")

        return Arrays.asList(Ulsan1, Ulsan2, Ulsan3, Ulsan4, Ulsan5)
    }

    fun makeSejong(): SiDo {
        return SiDo("세종특별자치시", makeSejongSubAddress())
    }

    fun makeSejongSubAddress(): List<SiGunGu> {
        return Arrays.asList()
    }

    fun makeGyeonggi(): SiDo {
        return SiDo("경기도", makeGyeonggiSubAddress())
    }

    fun makeGyeonggiSubAddress(): List<SiGunGu> {
        val Gyeonggi1 = SiGunGu("가평군")
        val Gyeonggi2 = SiGunGu("고양시 덕양구")
        val Gyeonggi3 = SiGunGu("고양시 일산동구")
        val Gyeonggi4 = SiGunGu("고양시 일산서구")
        val Gyeonggi5 = SiGunGu("과천시")
        val Gyeonggi6 = SiGunGu("광명시")
        val Gyeonggi7 = SiGunGu("광주시")
        val Gyeonggi8 = SiGunGu("구리시")
        val Gyeonggi9 = SiGunGu("군포시")
        val Gyeonggi10 = SiGunGu("김포시")
        val Gyeonggi11 = SiGunGu("남양주시")
        val Gyeonggi12 = SiGunGu("동두천시")
        val Gyeonggi13 = SiGunGu("부천시")
        val Gyeonggi14 = SiGunGu("성남시 분당구")
        val Gyeonggi15 = SiGunGu("성남시 수정구")
        val Gyeonggi16 = SiGunGu("성남시 중원구")
        val Gyeonggi17 = SiGunGu("수원시 권선구")
        val Gyeonggi18 = SiGunGu("수원시 영통구")
        val Gyeonggi19 = SiGunGu("수원시 장안구")
        val Gyeonggi20 = SiGunGu("수원시 팔달구")
        val Gyeonggi21 = SiGunGu("시흥시")
        val Gyeonggi22 = SiGunGu("안산시 단원구")
        val Gyeonggi23 = SiGunGu("안산시 상록구")
        val Gyeonggi24 = SiGunGu("안성시")
        val Gyeonggi25 = SiGunGu("안양시 동안구")
        val Gyeonggi26 = SiGunGu("안양시 만안구")
        val Gyeonggi27 = SiGunGu("양주시")
        val Gyeonggi28 = SiGunGu("양평군")
        val Gyeonggi29 = SiGunGu("여주시")
        val Gyeonggi30 = SiGunGu("연천군")
        val Gyeonggi31 = SiGunGu("용인시 기흥구")
        val Gyeonggi32 = SiGunGu("용인시 수지구")
        val Gyeonggi33 = SiGunGu("용인시 처인구")
        val Gyeonggi34 = SiGunGu("의왕시")
        val Gyeonggi35 = SiGunGu("의정부시")
        val Gyeonggi36 = SiGunGu("이천시")
        val Gyeonggi37 = SiGunGu("파주시")
        val Gyeonggi38 = SiGunGu("평택시")
        val Gyeonggi39 = SiGunGu("포천시")
        val Gyeonggi40 = SiGunGu("하남시")
        val Gyeonggi41 = SiGunGu("화성시")

        return Arrays.asList(Gyeonggi1, Gyeonggi2, Gyeonggi3, Gyeonggi4, Gyeonggi5,
                Gyeonggi6, Gyeonggi7, Gyeonggi8, Gyeonggi9, Gyeonggi10,
                Gyeonggi11, Gyeonggi12, Gyeonggi13, Gyeonggi14, Gyeonggi15,
                Gyeonggi16, Gyeonggi17, Gyeonggi18, Gyeonggi19, Gyeonggi20,
                Gyeonggi21, Gyeonggi22, Gyeonggi23, Gyeonggi24, Gyeonggi25,
                Gyeonggi26, Gyeonggi27, Gyeonggi28, Gyeonggi29, Gyeonggi30,
                Gyeonggi31, Gyeonggi32, Gyeonggi33, Gyeonggi34, Gyeonggi35,
                Gyeonggi36, Gyeonggi37, Gyeonggi38, Gyeonggi39, Gyeonggi40,
                Gyeonggi41)
    }

}